package com.toletspot.houseforrent.Chat

import androidx.annotation.Keep
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName


@Keep
@IgnoreExtraProperties
data class User(

    @get:PropertyName("userId")
    @set:PropertyName("userId")
    var userId: String = "",

    @get:PropertyName("name")
    @set:PropertyName("name")
    var name: String = "",

    @get:PropertyName("userName")
    @set:PropertyName("userName")
    var userName: String = "",

    @get:PropertyName("mobileNumber")
    @set:PropertyName("mobileNumber")
    var mobileNumber: String = "",

    @get:PropertyName("mobileNumberCC")
    @set:PropertyName("mobileNumberCC")
    var mobileNumberCC: String = "",

    @get:PropertyName("deviceToken")
    @set:PropertyName("deviceToken")
    var deviceToken: String = "",

    @get:PropertyName("isOnline")
    @set:PropertyName("isOnline")
    var isOnline: Boolean = false,

    @get:PropertyName("isAccountDeleted")
    @set:PropertyName("isAccountDeleted")
    var isAccountDeleted: Boolean = false,

    @get:PropertyName("lastSeen")
    @set:PropertyName("lastSeen")
    var lastSeen: Long = 0L,

    @get:PropertyName("whatsappNumberCC")
    @set:PropertyName("whatsappNumberCC")
    var whatsappNumberCC: String = "",

    @get:PropertyName("whatsappNumber")
    @set:PropertyName("whatsappNumber")
    var whatsappNumber: String = "",

    @get:PropertyName("profileImage")
    @set:PropertyName("profileImage")
    var profileImage: String = "",

    @get:PropertyName("state")
    @set:PropertyName("state")
    var state: String = "",

    @get:PropertyName("email")
    @set:PropertyName("email")
    var email: String = "",

    @get:PropertyName("city")
    @set:PropertyName("city")
    var city: String = "",

    @get:PropertyName("blocks")
    @set:PropertyName("blocks")
    var blocks: Map<String, Boolean> = emptyMap()
)



@Keep
@IgnoreExtraProperties
data class ChatMessage(

    @get:PropertyName("messageId")
    @set:PropertyName("messageId")
    var messageId: String = "",

    @get:PropertyName("message")
    @set:PropertyName("message")
    var message: String = "",

    @get:PropertyName("senderId")
    @set:PropertyName("senderId")
    var senderId: String = "",

    @get:PropertyName("receiverId")
    @set:PropertyName("receiverId")
    var receiverId: String = "",

    @get:PropertyName("status")
    @set:PropertyName("status")
    var status: String = "sent", // sent, delivered, seen, deleted

    @get:PropertyName("deletedForEveryone")
    @set:PropertyName("deletedForEveryone")
    var deletedForEveryone: Boolean = false,

    @get:PropertyName("deletedFor")
    @set:PropertyName("deletedFor")
    var deletedFor: Any? = null,

    @get:PropertyName("blocked")
    @set:PropertyName("blocked")
    var blocked: Boolean = false,

    @get:PropertyName("blockedFor")
    @set:PropertyName("blockedFor")
    var blockedFor: String = "",

    @get:PropertyName("isDelivered")
    @set:PropertyName("isDelivered")
    var isDelivered: Boolean = false,

    @get:PropertyName("isRead")
    @set:PropertyName("isRead")
    var isRead: Boolean = false,

    @get:PropertyName("time")
    @set:PropertyName("time")
    var time: Long = System.currentTimeMillis(),

    @get:PropertyName("timestamp")
    @set:PropertyName("timestamp")
    var timestamp: Long? = null
)


@Keep
data class UserWithUnread(
    var user: User,
    var unreadCount: Int = 0,
    var isTyping: Boolean = false,
    var lastMessage: String = "",
    var lastMessageTime: Long = 0L
)