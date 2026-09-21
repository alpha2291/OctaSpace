package com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentalFormCommercial

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShopFacade(
    @SerialName("Facade_Height")
    val Facade_Height: String,
    @SerialName("Facade_Width")
    val Facade_Width: String
)
