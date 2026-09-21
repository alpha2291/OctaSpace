package com.toletspot.houseforrent.Chat

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.MutableData
import com.google.firebase.database.ServerValue
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BuyerListViewModel : ViewModel() {

    private val _buyers = MutableStateFlow<List<UserWithUnread>>(emptyList())
    val buyers: StateFlow<List<UserWithUnread>> = _buyers

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val chatMap = mutableMapOf<String, UserWithUnread>()
    private val allUsers = mutableMapOf<String, User>()

    private var chatListener: ChildEventListener? = null
    private var usersChildListener: ChildEventListener? = null

    private var currentPropertyId = ""
    private var currentSellerId = ""
    private var currentUserId = ""

    fun loadBuyers(propertyId: String, sellerId: String, loggedInUserId: String) {
        if (chatListener != null &&
            currentPropertyId == propertyId &&
            currentSellerId == sellerId &&
            currentUserId == loggedInUserId
        ) return

        chatListener?.let { FirebaseRepository.getChatsReference().removeEventListener(it) }
        usersChildListener?.let { FirebaseRepository.getUsersReference().removeEventListener(it) }

        chatMap.clear()
        allUsers.clear()
        _buyers.value = emptyList()
        _isLoading.value = true

        currentPropertyId = propertyId
        currentSellerId = sellerId
        currentUserId = loggedInUserId

        val chatsRef = FirebaseRepository.getChatsReference()
        chatListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) =
                handleChatChange(snapshot)

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) =
                handleChatChange(snapshot)

            override fun onChildRemoved(snapshot: DataSnapshot) {
                snapshot.key?.let { chatMap.remove(it); updateBuyersList() }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {
                Log.e("BuyerListVM", "Chat listener cancelled", error.toException())
                _isLoading.value = false
            }
        }
        chatsRef.addChildEventListener(chatListener!!)

        val usersRef = FirebaseRepository.getUsersReference()
        usersChildListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                if (!snapshot.hasChild("name")) return

                val userId = snapshot.key ?: return
                val rawUser = snapshot.getValue(User::class.java) ?: return

                val user = rawUser.copy(
                    userId = userId,
                    name = if (rawUser.isAccountDeleted) "[Deleted User]" else rawUser.name
                )

                allUsers[userId] = user
                updateUserInChatList(user)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

                val value = snapshot.value
                if (value !is Map<*, *>) {
                    Log.w("BuyerListVM", "⚠️ Skipping invalid user node (onChildChanged): ${snapshot.key}, value=$value")
                    return
                }

                val user = snapshot.getValue(User::class.java) ?: return

                val effectiveUser = if (user.isAccountDeleted) {
                    user.copy(name = "[Deleted User]")
                } else {
                    user
                }

                allUsers[user.userId] = effectiveUser
                updateUserInChatList(effectiveUser)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java) ?: return
                allUsers.remove(user.userId)
                updateBuyersList()
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        }
        usersRef.addChildEventListener(usersChildListener!!)

        _isLoading.value = false
    }

    private fun handleChatChange(chatSnap: DataSnapshot) {
        val chatId = chatSnap.key ?: return
        val chatProperty = chatSnap.child("propertyId").getValue(String::class.java)
        val chatSeller = chatSnap.child("sellerId").getValue(String::class.java)
        var chatBuyer = chatSnap.child("buyerId").getValue(String::class.java)

        if (chatBuyer.isNullOrEmpty() && chatId.contains("_")) {
            val parts = chatId.split("_")
            if (parts.size >= 3) chatBuyer = parts[2]
        }

        if (chatProperty != currentPropertyId || chatSeller != currentSellerId || chatBuyer.isNullOrEmpty()) return

        val lastMessageText = chatSnap.child("lastMessage").getValue(String::class.java)
            ?: chatSnap.child("messages").children.lastOrNull()?.child("message")?.getValue(String::class.java)
            ?: ""

        val lastMessageTime = chatSnap.child("lastUpdated").getValue(Long::class.java)
            ?: getMessageTime(chatSnap.child("messages").children.lastOrNull())

        val unreadCount = chatSnap.child("unreadCount").child(currentUserId)
            .getValue(Int::class.java) ?: 0

        val targetUserId = if (currentUserId == currentSellerId) chatBuyer!! else currentSellerId
        val isTyping = getTypingStatus(chatSnap, targetUserId)

        val userObj = allUsers[targetUserId] ?: User(userId = targetUserId, name = "Loading...", isOnline = false)

        chatMap[chatId] = UserWithUnread(
            user = userObj.copy(),
            unreadCount = unreadCount,
            isTyping = isTyping,
            lastMessage = lastMessageText,
            lastMessageTime = lastMessageTime
        )
        updateBuyersList()
    }

    private fun getMessageTime(msgSnap: DataSnapshot?): Long {
        if (msgSnap == null) return 0L
        val timestamp = msgSnap.child("timestamp").getValue(Long::class.java)
        return timestamp ?: msgSnap.child("time").getValue(Long::class.java) ?: 0L
    }

    private fun getTypingStatus(chatSnap: DataSnapshot, userId: String): Boolean {
        val typingNode = chatSnap.child("typing")
        return try {
            val typingMap = typingNode.getValue(object : GenericTypeIndicator<Map<String, Boolean>>() {})
            typingMap?.get(userId) == true
        } catch (e: Exception) {
            false
        }
    }

    private fun updateUserInChatList(user: User) {
        var changed = false
        chatMap.entries.forEach { (chatId, uwu) ->
            if (uwu.user.userId == user.userId) {
                chatMap[chatId] = uwu.copy(user = user.copy())
                changed = true
            }
        }
        if (changed) updateBuyersList()
    }

    private fun updateBuyersList() {
        val uniqueUsers = mutableMapOf<String, UserWithUnread>()
        for ((_, userWithUnread) in chatMap) {
            val userId = userWithUnread.user.userId
            val existing = uniqueUsers[userId]
            if (existing == null || userWithUnread.lastMessageTime > existing.lastMessageTime) {
                uniqueUsers[userId] = userWithUnread
            }
        }
        _buyers.value = uniqueUsers.values.sortedByDescending { it.lastMessageTime }.toList()
    }

    override fun onCleared() {
        super.onCleared()
        chatListener?.let { FirebaseRepository.getChatsReference().removeEventListener(it) }
        usersChildListener?.let { FirebaseRepository.getUsersReference().removeEventListener(it) }
        chatMap.clear()
        allUsers.clear()
    }
}

class ChatViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages

    private val _isOtherTyping = MutableStateFlow(false)
    val isOtherTyping: StateFlow<Boolean> = _isOtherTyping

    private var messagesListener: ValueEventListener? = null
    private var typingListener: ValueEventListener? = null
    private var blockedListener: ValueEventListener? = null
    private var presenceListener: ValueEventListener? = null
    private lateinit var chatNode: DatabaseReference
    private lateinit var chatId: String

    private var blockedUsers = setOf<String>()
    private var currentUserId = ""
    private var otherUserId = ""
    private var otherUserOnline = false
    private var chatActive = false

    fun setChatActive(isActive: Boolean) {
        chatActive = isActive
    }

    fun initChat(currentUserId: String, otherUserId: String, propertyId: String, sellerId: String) {
        this.currentUserId = currentUserId
        this.otherUserId = otherUserId
        val buyerId = if (currentUserId == sellerId) otherUserId else currentUserId
        chatId = FirebaseRepository.generateChatId(propertyId, sellerId, buyerId)
        chatNode = FirebaseRepository.getChatsReference().child(chatId)

        listenToBlockedUsers(currentUserId)
        listenToPresence(otherUserId)
        loadMessages(currentUserId)
        listenToTyping(otherUserId)
    }

    private fun listenToPresence(otherUserId: String) {
        val userRef = FirebaseRepository.getUsersReference().child(otherUserId)
        presenceListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val wasOffline = !otherUserOnline
                otherUserOnline = snapshot.getValue(Boolean::class.java) ?: false

                if (wasOffline && otherUserOnline) {
                    Log.d("ChatVM", "✅ User $otherUserId came online, marking messages as delivered")

                    FirebaseRepository.markMessagesAsDelivered(chatId, otherUserId)

                }
            }

            override fun onCancelled(error: DatabaseError) {}
        }
        userRef.child("isOnline").addValueEventListener(presenceListener!!)
    }

    private fun listenToBlockedUsers(currentUserId: String) {
        blockedListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val blocksMap =
                    snapshot.getValue(object : GenericTypeIndicator<Map<String, Boolean>>() {}) ?: emptyMap()
                blockedUsers = blocksMap.filterValues { it }.keys.toSet()
            }

            override fun onCancelled(error: DatabaseError) {}
        }
        FirebaseRepository.getUsersReference().child(currentUserId)
            .child("blocks").addValueEventListener(blockedListener!!)
    }

    private fun loadMessages(currentUserId: String) {
        messagesListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val msgList = snapshot.child("messages").children.mapNotNull { msgSnap ->
                    val msg = msgSnap.getValue(ChatMessage::class.java) ?: return@mapNotNull null

                    if (blockedUsers.contains(msg.senderId)) return@mapNotNull null
                    if (msg.blocked && msg.blockedFor == currentUserId) return@mapNotNull null

                    val isDeletedForUser = when (msg.deletedFor) {
                        is Map<*, *> -> (msg.deletedFor as Map<String, Boolean>)[currentUserId] ?: false
                        else -> false
                    }
                    if (isDeletedForUser) return@mapNotNull null

                    msg
                }.sortedBy { it.timestamp ?: it.time }

                _messages.value = msgList

                if (chatActive) {
                }

            }

            override fun onCancelled(error: DatabaseError) {}
        }
        chatNode.addValueEventListener(messagesListener!!)
    }

    private fun listenToTyping(otherUserId: String) {
        typingListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val typingMap =
                    snapshot.getValue(object : GenericTypeIndicator<Map<String, Boolean>>() {}) ?: emptyMap()

                _isOtherTyping.value = typingMap[otherUserId] == true

            }

            override fun onCancelled(error: DatabaseError) {}
        }
        chatNode.child("typing").addValueEventListener(typingListener!!)
    }

    fun setTypingold(currentUserId: String, isTyping: Boolean) {
        if (!blockedUsers.contains(otherUserId)) {
            chatNode.child("typing").child(currentUserId).setValue(isTyping)
        }
    }

    fun onChatClosed() {
        setTyping(currentUserId, false)
        FirebaseRepository.clearUserActiveInChatNew(chatId, currentUserId)
    }

    fun setTyping(currentUserId: String, isTyping: Boolean) {
        if (blockedUsers.contains(otherUserId)) return

        val typingRef = chatNode.child("typing").child(currentUserId)

        typingRef.setValue(isTyping)

        if (isTyping) {

            typingRef.onDisconnect().setValue(false)
        }
    }

    fun clearSetTyping(currentUserId: String, ){
        val typingRef = chatNode.child("typing").child(currentUserId)

        typingRef.setValue(false)
        typingRef.onDisconnect().setValue(false)

    }

    fun sendMessageold(
        messageText: String,
        currentUserId: String,
        otherUserId: String,
        propertyId: String,
        sellerId: String,

    ) {
        if (messageText.isBlank() || blockedUsers.contains(otherUserId)) return

        val buyerId = if (currentUserId == sellerId) otherUserId else currentUserId
        val msgId = chatNode.child("messages").push().key ?: return

        val msg = mapOf(
            "messageId" to msgId,
            "message" to messageText,
            "senderId" to currentUserId,
            "receiverId" to otherUserId,
            "status" to "sent",
            "isDelivered" to false,
            "isRead" to false,
            "deletedForEveryone" to false,
            "blocked" to false,
            "blockedFor" to "",
            "timestamp" to ServerValue.TIMESTAMP,
            "time" to ServerValue.TIMESTAMP
        )

        val updates = mapOf(
            "propertyId" to propertyId,
            "sellerId" to sellerId,
            "buyerId" to buyerId,
            "lastMessage" to messageText,
            "lastUpdated" to ServerValue.TIMESTAMP,
            "messages/$msgId" to msg
        )

        chatNode.updateChildren(updates) { error, _ ->
            if (error == null) {
                incrementUnread(otherUserId)
                setTyping(currentUserId, false)

                FirebaseRepository.isOtherUserActive(chatId, otherUserId) { isOtherChatOpen ->

                    when {

                        otherUserOnline && isOtherChatOpen -> {
                            FirebaseRepository.updateMessageStatus(
                                chatId = chatId,
                                messageId = msgId,
                                status = "seen",
                                isDelivered = true,
                                isRead = true
                            )
                        }

                        otherUserOnline -> {
                            FirebaseRepository.updateMessageStatus(
                                chatId = chatId,
                                messageId = msgId,
                                status = "delivered",
                                isDelivered = true,
                                isRead = false
                            )
                        }

                    }
                }

            }
        }
    }

    fun sendMessageold2(
        messageText: String,
        currentUserId: String,
        otherUserId: String,
        propertyId: String,
        sellerId: String
    ) {
        if (messageText.isBlank()) return

        FirebaseRepository.getUsersReference()
            .child(otherUserId)
            .child("blocks")
            .child(currentUserId)
            .get()
            .addOnSuccessListener { snapshot ->

                val isBlockedByReceiver = snapshot.getValue(Boolean::class.java) == true

                val buyerId = if (currentUserId == sellerId) otherUserId else currentUserId
                val msgId = chatNode.child("messages").push().key ?: return@addOnSuccessListener

                val msg = mapOf(
                    "messageId" to msgId,
                    "message" to messageText,
                    "senderId" to currentUserId,
                    "receiverId" to otherUserId,

                    "blocked" to isBlockedByReceiver,
                    "blockedFor" to if (isBlockedByReceiver) otherUserId else "",

                    "status" to if (isBlockedByReceiver) "blocked" else "sent",
                    "isDelivered" to false,
                    "isRead" to false,
                    "deletedForEveryone" to false,
                    "timestamp" to ServerValue.TIMESTAMP,
                    "time" to ServerValue.TIMESTAMP
                )

                val updates = mapOf(
                    "propertyId" to propertyId,
                    "sellerId" to sellerId,
                    "buyerId" to buyerId,
                    "lastMessage" to messageText,
                    "lastUpdated" to ServerValue.TIMESTAMP,
                    "messages/$msgId" to msg
                )

                chatNode.updateChildren(updates)
            }
    }

    fun sendMessage(
        messageText: String,
        currentUserId: String,
        otherUserId: String,
        propertyId: String,
        sellerId: String
    ) {
        if (messageText.isBlank()) return

        FirebaseRepository.getUsersReference()
            .child(otherUserId)
            .child("blocks")
            .child(currentUserId)
            .get()
            .addOnSuccessListener { snapshot ->

                val isBlockedByReceiver = snapshot.getValue(Boolean::class.java) == true
                val msgId = chatNode.child("messages").push().key ?: return@addOnSuccessListener

                val msg = mapOf(
                    "messageId" to msgId,
                    "message" to messageText,
                    "senderId" to currentUserId,
                    "receiverId" to otherUserId,

                    "blocked" to isBlockedByReceiver,
                    "blockedFor" to if (isBlockedByReceiver) otherUserId else "",

                    "status" to if (isBlockedByReceiver) "blocked" else "sent",
                    "isDelivered" to false,
                    "isRead" to false,

                    "timestamp" to ServerValue.TIMESTAMP,
                    "time" to ServerValue.TIMESTAMP
                )

                val updates = hashMapOf<String, Any>(
                    "/messages/$msgId" to msg,
                    "/lastMessage" to messageText,
                    "/lastUpdated" to ServerValue.TIMESTAMP,
                    "/unreadCount/$otherUserId" to ServerValue.increment(1)
                )

                chatNode.updateChildren(updates)

                chatNode.child("messages").child(msgId).setValue(msg)
            }
    }

    private fun incrementUnread(receiverId: String) {
        chatNode.child("unreadCount").child(receiverId)
            .runTransaction(object : Transaction.Handler {
                override fun doTransaction(currentData: MutableData): Transaction.Result {
                    val current = currentData.getValue(Int::class.java) ?: 0
                    currentData.value = current + 1
                    return Transaction.success(currentData)
                }

                override fun onComplete(error: DatabaseError?, committed: Boolean, snapshot: DataSnapshot?) {}
            })
    }

    fun deleteForMe(messageId: String, currentUserId: String) {
        chatNode.child("messages").child(messageId)
            .child("deletedFor").child(currentUserId).setValue(true)
    }

    fun deleteForEveryone(messageId: String, messageTime: Long) {
        val now = System.currentTimeMillis()
        if (now - messageTime > 5 * 60 * 1000) return
        chatNode.child("messages").child(messageId)
            .updateChildren(mapOf(
                "deletedForEveryone" to true,
                "message" to "This message was deleted",
                "status" to "deleted"
            ))
    }

    fun blockUserMessages(currentUserId: String) {
        chatNode.child("messages").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val updates = mutableMapOf<String, Any>()
                for (msgSnap in snapshot.children) {
                    val msg = msgSnap.getValue(ChatMessage::class.java) ?: continue
                    if (msg.senderId == otherUserId && !msg.blocked) {
                        updates["messages/${msg.messageId}/blocked"] = true
                        updates["messages/${msg.messageId}/blockedFor"] = currentUserId
                        Log.d("ChatVM", "Marking message ${msg.messageId} as blocked for $currentUserId")
                    }
                }
                if (updates.isNotEmpty()) {
                    chatNode.updateChildren(updates)
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
    fun unblockUserMessages(currentUserId: String) {
        chatNode.child("messages").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val updates = mutableMapOf<String, Any>()
                for (msgSnap in snapshot.children) {
                    val msg = msgSnap.getValue(ChatMessage::class.java) ?: continue
                    if (msg.blockedFor == currentUserId) {
                        updates["messages/${msg.messageId}/blocked"] = false
                        updates["messages/${msg.messageId}/blockedFor"] = ""
                        Log.d("ChatVM", "Unblocking message ${msg.messageId} for $currentUserId")
                    }
                }
                if (updates.isNotEmpty()) {
                    chatNode.updateChildren(updates)
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun clearChatForMe(currentUserId: String) {
        chatNode.child("messages").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val updates = mutableMapOf<String, Any>()

                snapshot.children.forEach { msgSnap ->
                    val msg = msgSnap.getValue(ChatMessage::class.java) ?: return@forEach

                    val deletedMap = when (msg.deletedFor) {
                        is Map<*, *> -> (msg.deletedFor as Map<String, Boolean>).toMutableMap()
                        else -> mutableMapOf()
                    }

                    deletedMap[currentUserId] = true
                    updates["messages/${msg.messageId}/deletedFor"] = deletedMap
                }

                if (updates.isNotEmpty()) {
                    chatNode.updateChildren(updates)
                        .addOnSuccessListener {
                            Log.d("ChatVM", "✅ Cleared chat for $currentUserId")
                            _messages.value = emptyList()
                        }
                        .addOnFailureListener { e ->
                            Log.e("ChatVM", "❌ Failed to clear chat: ${e.message}")
                        }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun onCleared() {
        super.onCleared()
        messagesListener?.let { chatNode.removeEventListener(it) }
        typingListener?.let { chatNode.child("typing").removeEventListener(it) }
        blockedListener?.let {
            FirebaseRepository.getUsersReference()
                .child(currentUserId)
                .child("blocks").removeEventListener(it)
        }
        presenceListener?.let {
            FirebaseRepository.getUsersReference()
                .child(otherUserId).child("isOnline").removeEventListener(it)
        }
    }
}
