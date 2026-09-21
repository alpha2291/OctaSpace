package com.toletspot.houseforrent.Home_Screen.Video_Module.RentoReelsDC

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RentoGetReelsDC(
    @SerialName("data")
    val `data`: List<Data>,
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
