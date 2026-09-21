package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FloorDetailX(
    @SerialName("total_floors_in_property")
    val total_floors_in_property: String,
    @SerialName("your_property_floor_no")
    val your_property_floor_no: String
)