package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Notification_Settings


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Get_Notification_Settings(
    @SerialName("data")
    val `data`: List<Data>,
    @SerialName("error")
    val error: String,
    @SerialName("result")
    val result: String
)