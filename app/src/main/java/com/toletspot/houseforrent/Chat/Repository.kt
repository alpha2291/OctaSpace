package com.toletspot.houseforrent.Chat

import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ValueEventListener


object FirebaseRepository {
    private const val DB_URL = "https://rental-app-a46a3-default-rtdb.firebaseio.com/"
    private val database = FirebaseDatabase.getInstance(DB_URL).reference

    fun blockUser(currentUserId: String, blockedUserId: String) {
        database.child("users").child(currentUserId).child("blocks").child(blockedUserId)
            .setValue(true)
    }

    fun unblockUser(currentUserId: String, blockedUserId: String) {
        database.child("users").child(currentUserId).child("blocks").child(blockedUserId)
            .setValue(false)
    }

    fun isUserBlocked(
        currentUserId: String,
        targetUserId: String,
        callback: (Boolean) -> Unit
    ) {
        database.child("users").child(currentUserId).child("blocks").child(targetUserId)
            .get()
            .addOnSuccessListener { snapshot ->
                callback(snapshot.getValue(Boolean::class.java) ?: false)
            }
            .addOnFailureListener { callback(false) }
    }

    fun updateMessageStatus(
        chatId: String,
        messageId: String,
        status: String,
        isDelivered: Boolean = false,
        isRead: Boolean = false
    ) {
        val updates = mutableMapOf<String, Any>(
            "messages/$messageId/status" to status
        )
        if (isDelivered) updates["messages/$messageId/isDelivered"] = true
        if (isRead) updates["messages/$messageId/isRead"] = true

        database.child("chats").child(chatId).updateChildren(updates)
    }

    private var seenListener: ChildEventListener? = null

    fun observeMessagesAndMarkSeen(chatId: String, receiverId: String) {
        val ref = database.child("chats").child(chatId).child("messages")

        seenListener?.let { ref.removeEventListener(it) }

        seenListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val msg = snapshot.getValue(ChatMessage::class.java) ?: return

                // 🔒 HARD STOP — NEVER TOUCH BLOCKED MESSAGES
                if (msg.status == "blocked") return
                if (msg.blocked) return
                if (msg.blockedFor == receiverId) return

                if (
                    msg.receiverId == receiverId &&
                    !msg.isRead
                ) {
                    snapshot.ref.updateChildren(
                        mapOf(
                            "status" to "seen",
                            "isDelivered" to true,
                            "isRead" to true
                        )
                    )
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        }

        ref.addChildEventListener(seenListener!!)
    }


    fun removeSeenListener(chatId: String) {
        seenListener?.let {
            database.child("chats").child(chatId).child("messages")
                .removeEventListener(it)
        }
        seenListener = null
    }



    fun markMessagesAsDelivered(chatId: String, receiverId: String) {
        val messagesRef = database.child("chats").child(chatId).child("messages")

        messagesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { msgSnap ->
                    val msg = msgSnap.getValue(ChatMessage::class.java) ?: return@forEach

                    // 🔒 HARD BLOCK
                    if (msg.status == "blocked") return@forEach
                    if (msg.blocked) return@forEach
                    if (msg.blockedFor == receiverId) return@forEach

                    if (msg.receiverId == receiverId && !msg.isDelivered) {
                        msgSnap.ref.updateChildren(
                            mapOf(
                                "status" to "delivered",
                                "isDelivered" to true
                            )
                        )
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }



    // ✅ Mark messages as delivered when receiver comes online
    fun markMessagesAsDeliveredold(chatId: String, receiverId: String) {
        database.child("chats").child(chatId).child("messages")
            .get()
            .addOnSuccessListener { snapshot ->
                val updates = mutableMapOf<String, Any>()
                snapshot.children.forEach { msgSnap ->
                    val msg = msgSnap.getValue(ChatMessage::class.java)
                    // ✅ CRITICAL: Only mark as delivered if:
                    // 1. Message is FOR this receiver
                    // 2. Status is "sent" (not already delivered/seen)
                    // 3. Not already delivered
                    // 4. Not already read
                    if (msg?.receiverId == receiverId &&
                        msg.status == "sent" &&
                        !msg.isDelivered &&
                        !msg.isRead
                    ) {
                        updates["${msgSnap.key}/status"] = "delivered"
                        updates["${msgSnap.key}/isDelivered"] = true
                    }
                }
                if (updates.isNotEmpty()) {
                    database.child("chats").child(chatId).child("messages")
                        .updateChildren(updates)
                        .addOnSuccessListener {
                            Log.d("FirebaseRepo", "✅ Marked ${updates.size / 2} messages as delivered")
                        }
                }
            }
    }


    // ✅ Mark messages as seen when receiver OPENS the chat
    fun markMessagesAsSeen(chatId: String, receiverId: String) {
        database.child("chats").child(chatId).child("messages")
            .get()
            .addOnSuccessListener { snapshot ->
                val updates = mutableMapOf<String, Any>()
                snapshot.children.forEach { msgSnap ->
                    val msg = msgSnap.getValue(ChatMessage::class.java)
                    // ✅ Only mark as seen if message is FOR this receiver and not already read
                    if (msg?.receiverId == receiverId && !msg.isRead) {
                        updates["${msgSnap.key}/status"] = "seen"
                        updates["${msgSnap.key}/isDelivered"] = true
                        updates["${msgSnap.key}/isRead"] = true
                    }
                }
                if (updates.isNotEmpty()) {
                    database.child("chats").child(chatId).child("messages")
                        .updateChildren(updates)
                        .addOnSuccessListener {
                            Log.d("FirebaseRepo", "✅ Marked ${updates.size / 3} messages as seen")
                        }
                        .addOnFailureListener { e ->
                            Log.e("FirebaseRepo", "❌ Failed to mark seen: ${e.message}")
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseRepo", "❌ Failed to get messages: ${e.message}")
            }
    }

    // ✅ Reset unread count when chat is opened
    fun resetUnreadCount(chatId: String, userId: String) {
        database.child("chats").child(chatId)
            .child("unreadCount").child(userId).setValue(0)
            .addOnSuccessListener {
                Log.d("FirebaseRepo", "✅ Reset unread count for $userId")
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseRepo", "❌ Failed to reset unread: ${e.message}")
            }
    }

    fun getChatsReference() = database.child("chats")
    fun getUsersReference() = database.child("users")

    fun generateChatId(propertyId: String, sellerId: String, buyerId: String): String {
        return "${propertyId}_${sellerId}_${buyerId}"
    }



    /**
     * ✅ Set or unset 'isAccountDeleted' for a user.
     */
    fun setAccountDeleted(userId: String, isDeleted: Boolean, onComplete: (Boolean) -> Unit = {}) {
        val userRef = database.child("users").child(userId).child("isAccountDeleted")
        userRef.setValue(isDeleted)
            .addOnSuccessListener {
                Log.d("FirebaseRepository", "✅ isAccountDeleted updated for $userId -> $isDeleted")
                onComplete(true)
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseRepository", "❌ Failed to update isAccountDeleted: ${e.message}")
                onComplete(false)
            }
    }

    fun getTotalUnreadCountForProperty(
        propertyId: String,
        currentUserId: String,
        onResult: (Int) -> Unit
    ) {
        val chatsRef = getChatsReference()
        chatsRef.get().addOnSuccessListener { snapshot ->
            var totalUnread = 0

            snapshot.children.forEach { chatSnapshot ->
                val chatPropertyId = chatSnapshot.child("propertyId").getValue(String::class.java)
                if (chatPropertyId != propertyId) return@forEach

                chatSnapshot.child("messages").children.forEach { msgSnap ->
                    val isRead = msgSnap.child("isRead").getValue(Boolean::class.java) ?: true
                    val senderId = msgSnap.child("senderId").getValue(String::class.java)

                    // Count unread messages sent by others
                    if (!isRead && senderId != currentUserId) {
                        totalUnread++
                    }
                }
            }

            onResult(totalUnread)
        }.addOnFailureListener { e ->
            Log.e("FirebaseRepository", "❌ Failed to get unread count: ${e.message}")
            onResult(0)
        }
    }

    fun getTotalUnreadChatsForUser(
        currentUserId: String,
        onResult: (Int) -> Unit
    ) {
        val chatsRef = getChatsReference()
        chatsRef.get().addOnSuccessListener { snapshot ->
            var unreadChatCount = 0

            snapshot.children.forEach { chatSnapshot ->
                val buyerId = chatSnapshot.child("buyerId").getValue(String::class.java)
                val sellerId = chatSnapshot.child("sellerId").getValue(String::class.java)

                // ✅ Skip chats not involving the current user
                if (buyerId != currentUserId && sellerId != currentUserId) return@forEach

                // ✅ Check if this chat has at least one unread message from the *other* user
                var hasUnread = false

                chatSnapshot.child("messages").children.forEach { msgSnap ->
                    val isRead = msgSnap.child("isRead").getValue(Boolean::class.java) ?: true
                    val senderId = msgSnap.child("senderId").getValue(String::class.java)

                    if (!isRead && senderId != currentUserId) {
                        hasUnread = true
                        return@forEach // stop checking this chat
                    }
                }

                if (hasUnread) unreadChatCount++
            }

            onResult(unreadChatCount)
        }.addOnFailureListener { e ->
            Log.e("FirebaseRepository", "❌ Failed to load unread chats: ${e.message}")
            onResult(0)
        }
    }

    fun setUserActiveInChat(chatId: String, userId: String, isActive: Boolean) {
        val ref = FirebaseDatabase.getInstance()
            .getReference("chats")
            .child(chatId)
            .child("active")
            .child(userId)
        ref.setValue(isActive)
        ref.onDisconnect().removeValue()

    }


    fun setUserActiveInChatNew(chatId: String, userId: String) {
        val ref = database.database
            .getReference("chats")
            .child(chatId)
            .child("active")
            .child(userId)

        ref.setValue(true)
        ref.onDisconnect().removeValue()
    }

    fun clearUserActiveInChatNew(chatId: String, userId: String) {
        database.database
            .getReference("chats")
            .child(chatId)
            .child("active")
            .child(userId)
            .removeValue()
    }



    fun isOtherUserActive(chatId: String, otherUserId: String, onResult: (Boolean) -> Unit) {
        database.database
            .getReference("chats")
            .child(chatId)
            .child("active")
            .child(otherUserId)
            .get()
            .addOnSuccessListener { snapshot ->
                onResult(snapshot.getValue(Boolean::class.java) == true)
            }
            .addOnFailureListener {
                onResult(false)
            }
    }

    fun observeOtherUserOnlineStatus(
        otherUserId: String,
        onResult: (Boolean) -> Unit
    ) {
        database.database
            .getReference("users")
            .child(otherUserId)
            .child("isOnline")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    onResult(snapshot.getValue(Boolean::class.java) == true)
                }

                override fun onCancelled(error: DatabaseError) {
                    onResult(false)
                }
            })
    }



}


object FirebasePresence {


//    private val db = FirebaseDatabase.getInstance("https://rental-app-a46a3-default-rtdb.firebaseio.com/")
    private val db = FirebaseDatabase.getInstance("https://rental-app-a46a3-default-rtdb.firebaseio.com/")
    private val usersRef = db.getReference("users")
    private val connectedRef = db.getReference(".info/connected")

    fun startListening(userId: String) {
        connectedRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val connected = snapshot.getValue(Boolean::class.java) ?: false
                Log.d("FirebasePresence", "Connection state for $userId: $connected")

                if (connected) {
                    usersRef.child(userId).child("isOnline").onDisconnect().setValue(false)
                        .addOnSuccessListener {
                            Log.d("FirebasePresence", "onDisconnect handler registered for $userId")
                        }
                        .addOnFailureListener { e ->
                            Log.e("FirebasePresence", "Failed to register onDisconnect: ${e.message}")
                        }

                    usersRef.child(userId).child("lastSeen").onDisconnect().setValue(ServerValue.TIMESTAMP)

                    usersRef.child(userId).updateChildren(mapOf(
                        "isOnline" to true,
                        "lastSeen" to System.currentTimeMillis()
                    )).addOnSuccessListener {
                        Log.d("FirebasePresence", "✅ User $userId is now ONLINE")
                    }.addOnFailureListener { e ->
                        Log.e("FirebasePresence", "❌ Failed to set online: ${e.message}")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebasePresence", "Connection listener cancelled: ${error.message}")
            }
        })
    }

    fun setOnlineNow(userId: String) {
        usersRef.child(userId).updateChildren(mapOf(
            "isOnline" to true,
            "lastSeen" to System.currentTimeMillis()
        )).addOnSuccessListener {
            Log.d("FirebasePresence", "✅ Explicitly set $userId ONLINE")
        }
    }

    fun setOfflineNow(userId: String) {
        usersRef.child(userId).updateChildren(mapOf(
            "isOnline" to false,
            "lastSeen" to ServerValue.TIMESTAMP
        )).addOnSuccessListener {
            Log.d("FirebasePresence", "✅ Set $userId OFFLINE")
        }
    }
}


object FirebaseHelper {
    private const val DB_URL = "https://rental-app-a46a3-default-rtdb.firebaseio.com/"
    private val db = FirebaseDatabase.getInstance(DB_URL).reference

    fun addUser(user: User, onComplete: (Boolean) -> Unit = {}) {
        val userRef = db.child("users").child(user.userId)
        userRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                Log.d("FirebaseHelper", "⚠️ User already exists: ${user.userId}")
                onComplete(false)
            } else {
                userRef.setValue(user)
                    .addOnSuccessListener {
                        Log.d("FirebaseHelper", "✅ User added: ${user.userId}")
                        onComplete(true)
                    }
                    .addOnFailureListener { e ->
                        Log.e("FirebaseHelper", "❌ Error adding user", e)
                        onComplete(false)
                    }
            }
        }
    }


    fun addOrUpdateUser(user: User, onComplete: (Boolean) -> Unit = {}) {

        val userRef = db.child("users").child(user.userId)

        userRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists())
            {
                // 🔄 Update existing user — only specific fields if needed
                val updates = mapOf(
                    "name" to user.name,
                    "email" to user.email,
                    "userName" to user.userName,
                    "mobileNumber" to user.mobileNumber,
                    "mobileNumberCC" to user.mobileNumberCC,
                    "deviceToken" to user.deviceToken,
                    "isOnline" to user.isOnline,
                    "isAccountDeleted" to user.isAccountDeleted,
                    "lastSeen" to user.lastSeen,
                    "whatsappNumberCC" to user.whatsappNumberCC,
                    "whatsappNumber" to user.whatsappNumber,
                    "profileImage" to user.profileImage,
                    "state" to user.state,
                    "city" to user.city,
                    "blocks" to user.blocks
                )

                Log.d("FirebaseHelper", "Updating user ${user.userId} with: $updates")


                userRef.updateChildren(updates)
                    .addOnSuccessListener {
                        Log.d("FirebaseHelper", "✅ User updated: ${user.userId}")
                        Log.d("FirebaseHelper", "Existing user data: ${snapshot.value}")

                        onComplete(true)
                    }
                    .addOnFailureListener { e ->
                        Log.e("FirebaseHelper", "❌ Failed to update user", e)
                        onComplete(false)
                    }

            }
            else
            {
                // 🆕 Add new user if not found
                userRef.setValue(user)
                    .addOnSuccessListener {
                        Log.d("FirebaseHelper", "✅ User added: ${user.userId}")
                        onComplete(true)
                    }
                    .addOnFailureListener { e ->
                        Log.e("FirebaseHelper", "❌ Error adding user", e)
                        onComplete(false)
                    }
            }
        }.addOnFailureListener { e ->
            Log.e("FirebaseHelper", "❌ Failed to fetch user: ${e.message}")
            onComplete(false)
        }
    }



    fun createPropertyChat(
        propertyId: String,
        sellerId: String,
        buyerId: String,
        initialMessage: String = "I'd like to enquire about this property.",
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit = {}
    ) {
        val dbRef = FirebaseRepository.getChatsReference()
        val chatId = FirebaseRepository.generateChatId(propertyId, sellerId, buyerId)
        val chatRef = dbRef.child(chatId)

        chatRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                Log.d("FirebaseHelper", "✅ Chat already exists: $chatId")
                onSuccess(chatId)
                return@addOnSuccessListener
            }

            val msgId = chatRef.child("messages").push().key ?: return@addOnSuccessListener

            val now = System.currentTimeMillis()

            val firstMessage = ChatMessage(
                messageId = msgId,
                message = initialMessage,
                senderId = buyerId,
                receiverId = sellerId,
                time = now,
                timestamp = now,
                status = "sent",
                isDelivered = false,
                isRead = false,
                blocked = false,
                blockedFor = ""
            )

            val updates = mapOf(
                "propertyId" to propertyId,
                "sellerId" to sellerId,
                "buyerId" to buyerId,
                "lastMessage" to firstMessage.message,
                "lastUpdated" to now,
                "messages/$msgId" to firstMessage,
                "unreadCount/$sellerId" to 1,
                "unreadCount/$buyerId" to 0,
                "typing/$sellerId" to false,
                "typing/$buyerId" to false
            )

            chatRef.updateChildren(updates)
                .addOnSuccessListener {
                    Log.d("FirebaseHelper", "✅ Chat created: $chatId")
                    onSuccess(chatId)
                }
                .addOnFailureListener { e ->
                    Log.e("FirebaseHelper", "❌ Failed to create chat: ${e.message}")
                    onFailure(e.message ?: "Error")
                }
        }.addOnFailureListener { e ->
            Log.e("FirebaseHelper", "❌ Failed to check chat: ${e.message}")
            onFailure(e.message ?: "Error")
        }
    }
}
