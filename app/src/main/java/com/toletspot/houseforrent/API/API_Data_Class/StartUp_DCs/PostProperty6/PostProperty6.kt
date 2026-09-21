package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.PostProperty6


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostProperty6(
    @SerialName("data")
    val `data`: List<List<Data>>,
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: String
)