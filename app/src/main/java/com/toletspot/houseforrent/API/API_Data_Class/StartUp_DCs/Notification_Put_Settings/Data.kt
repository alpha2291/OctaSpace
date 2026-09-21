package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Notification_Put_Settings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("message")
    val message: String
)
