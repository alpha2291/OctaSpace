package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Post_Property_Step4_Completed(
    @SerialName("error")
    val error: String,
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: String
)