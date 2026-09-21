package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Get_Main_Comments(
    @SerialName("data")
    val `data`: List<Get_Main_Comments_Data>,
    @SerialName("error")
    val error: String,
    @SerialName("nxtpage")
    val nxtpage: Int,
    @SerialName("recCnt")
    val recCnt: Int,
    @SerialName("result")
    val result: String,
    @SerialName("totalPages")
    val totalPages: Int
)