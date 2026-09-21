package com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentoPhotouploadheadings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RentoPhotoHeadings(
    @SerialName("data")
    val `data`: List<String>,
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: String,

)
