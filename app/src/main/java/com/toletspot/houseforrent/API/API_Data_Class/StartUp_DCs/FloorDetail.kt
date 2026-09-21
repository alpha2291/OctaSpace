package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FloorDetail(
    @SerialName("Total_Floors_in_Property")
    val Total_Floors_in_Property: String,
    @SerialName("Rent_Property_Floor_No")
    val Rent_Property_Floor_No: String
)
