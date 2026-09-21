package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Get_User_Settings_Data(
    @SerialName("allow_notification")
    val allow_notification: Boolean,
    @SerialName("notification_ids")
    val notification_ids: List<String>
)