package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.ChatNotification


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Chat_Notification(
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: String
)