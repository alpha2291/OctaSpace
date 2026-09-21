package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Notification_Put_Settings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Put_Notification_settings(
    @SerialName("data")
    val `data`: List<Data>,
    @SerialName("error")
    val error: String,
    @SerialName("result")
    val result: String
)
