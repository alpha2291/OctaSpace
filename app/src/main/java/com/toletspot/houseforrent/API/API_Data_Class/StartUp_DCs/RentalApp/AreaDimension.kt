package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.RentalApp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AreaDimension(
    @SerialName("Length")
    val Length: String,
    @SerialName("Width")
    val Width: String
)
