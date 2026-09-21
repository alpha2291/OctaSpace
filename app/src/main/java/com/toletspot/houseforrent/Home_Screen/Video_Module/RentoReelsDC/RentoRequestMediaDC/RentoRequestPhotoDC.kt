package com.toletspot.houseforrent.Home_Screen.Video_Module.RentoReelsDC.RentoRequestMediaDC


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RentoRequestPhotoDC(
    @SerialName("data")
    val `data`: List<String >,
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: String
)