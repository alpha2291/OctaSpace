package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Get_Blocked_Users_List(
    @SerialName("data")
    val `data`: List<Get_Blocked_Users_List_Data>,
    @SerialName("error")
    val error: String,
    @SerialName("message")
    val message: String,
    @SerialName("nxtpage")
    val nxtpage: Int,
    @SerialName("recCnt")
    val recCnt: Int,
    @SerialName("result")
    val result: String,
    @SerialName("totalPages")
    val totalPages: Int
)