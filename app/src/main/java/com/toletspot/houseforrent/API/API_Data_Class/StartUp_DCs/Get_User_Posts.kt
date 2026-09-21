package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Get_User_Posts(
    @SerialName("data")
    val `data`: List<Get_User_Posts_Data>,
    @SerialName("nxtpage")
    val nxtpage: Int,
    @SerialName("page")
    val page: Int,
    @SerialName("recCnt")
    val recCnt: Int,
    @SerialName("result")
    val result: String,
    @SerialName("totalPages")
    val totalPages: Int
)
