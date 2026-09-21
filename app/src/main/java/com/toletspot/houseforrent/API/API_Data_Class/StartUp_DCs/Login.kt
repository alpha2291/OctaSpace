package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Login(
    @SerialName("data")
    val `data`: List<Login_Data>,
    @SerialName("error")
    val error: String,
    @SerialName("result")
    val result: String
)
