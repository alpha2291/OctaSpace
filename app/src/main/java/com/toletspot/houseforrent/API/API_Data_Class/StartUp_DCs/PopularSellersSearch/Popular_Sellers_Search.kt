package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.PopularSellersSearch


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Popular_Sellers_Search(
    @SerialName("data")
    val `data`: List<Popular_Sellers_Search_Data>,
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: String
)