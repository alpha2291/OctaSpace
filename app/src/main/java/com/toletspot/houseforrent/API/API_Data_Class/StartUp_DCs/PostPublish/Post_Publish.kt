package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.PostPublish

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Post_Publish(
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: String
)
