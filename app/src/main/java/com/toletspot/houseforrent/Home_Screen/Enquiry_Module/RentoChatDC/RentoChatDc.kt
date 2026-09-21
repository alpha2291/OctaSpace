package com.toletspot.houseforrent.Home_Screen.Enquiry_Module.RentoChatDC

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RentoChatDc(
    @SerialName("data")
    val `data`: List<Data>,
    @SerialName("nxtpage")
    val nxtpage: Int,
    @SerialName("recCnt")
    val recCnt: Int,
    @SerialName("result")
    val result: String,
    @SerialName("totalPages")
    val totalPages: Int
)
