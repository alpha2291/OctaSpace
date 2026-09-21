package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.PostForm45


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostProperty45(
    @SerialName("data")
    val `data`: List<List<Data>>,
    @SerialName("error")
    val error: String,
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: String
)