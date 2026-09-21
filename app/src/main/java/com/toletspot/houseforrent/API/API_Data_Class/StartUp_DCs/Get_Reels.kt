package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Get_Reels(
    @SerialName("data")
    val `data`: List<Get_Reels_Data>,
    @SerialName("limit")
    val limit: Int,
    @SerialName("page")
    val page: Int,
    @SerialName("result")
    val result: String,
    @SerialName("recCnt")
    val recCnt: Int,
    @SerialName("totalPages")
    val totalPages: Int,
    @SerialName("nxtpage")
    val nxtpage: Int
)

