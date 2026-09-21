package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Post_Like_Dislike(
    @SerialName("data")
    val `data`: List<String>,
    @SerialName("error")
    val error: String,
    @SerialName("liked")
    val liked: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: String
)
