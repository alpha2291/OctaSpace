package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Popular_Cities_Search_Data(
    @SerialName("city")
    val city: String,
    @SerialName("total")
    val total: Int
)