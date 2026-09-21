package com.toletspot.houseforrent.Home_Screen.Enquiry_Module.RentoChatDC

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Video(
    @SerialName("heading")
    val heading: String,
    @SerialName("url")
    val url: String
)
