package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Put_Comment_Reply(
    @SerialName("data")
    val `data`: List<Put_Comment_Reply_Data>,
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: String
)