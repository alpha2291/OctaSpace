package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Notification_Settings


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("allow_notification")
    val allow_notification: Boolean,
    @SerialName("notification_ids")
    val notification_ids: List<String>
)