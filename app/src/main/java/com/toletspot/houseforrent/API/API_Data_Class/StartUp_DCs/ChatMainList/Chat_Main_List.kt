package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.ChatMainList


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Chat_Main_List(
    @SerialName("Chat_Main_List_Data")
    val `data`: List<Chat_Main_List_Data>,
    @SerialName("nxtpage")
    val nxtpage: Int,
    @SerialName("recCnt")
    val recCnt: Int,
    @SerialName("result")
    val result: String,
    @SerialName("totalPages")
    val totalPages: Int
)