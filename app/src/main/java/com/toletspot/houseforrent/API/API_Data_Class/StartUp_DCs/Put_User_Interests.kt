package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Put_User_Interests(
    @SerialName("data")
    val `data`: String,
    @SerialName("error")
    val error: String,
    @SerialName("result")
    val result: String
)