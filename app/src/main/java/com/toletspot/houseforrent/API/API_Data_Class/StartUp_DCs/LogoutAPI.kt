package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LogoutAPI(
    @SerialName("data")
    val `data`: List<String>,
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: String
)
