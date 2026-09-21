package com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentalFormCommercial


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FloorDetail(
    @SerialName("Rent_Property_Floor_No")
    val Rent_Property_Floor_No: String,
    @SerialName("Total_Floors_in_Property")
    val Total_Floors_in_Property: String
)