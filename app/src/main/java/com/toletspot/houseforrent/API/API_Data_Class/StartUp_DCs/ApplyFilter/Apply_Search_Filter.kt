package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.ApplyFilter


import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Reels_Data
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Apply_Search_Filter(
    @SerialName("data")
    val `data`: List<Get_Reels_Data>,
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