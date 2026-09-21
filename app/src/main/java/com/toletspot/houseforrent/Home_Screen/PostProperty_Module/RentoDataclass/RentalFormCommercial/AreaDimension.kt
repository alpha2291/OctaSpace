package com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentalFormCommercial

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AreaDimension(
    @SerialName("Length")
    val Length: String,
    @SerialName("Width")
    val Width: String
)
