package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.AppNotifcation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class App_Notification(
    @SerialName("data")
    val `data`: List<App_Notification_Data>,
    @SerialName("error")
    val error: String,
    @SerialName("nxtpage")
    val nxtpage: Int,
    @SerialName("recCnt")
    val recCnt: Int,
    @SerialName("result")
    val result: String,
    @SerialName("totalPages")
    val totalPages: Int
)
