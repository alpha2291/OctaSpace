package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AreaDimension(
    @SerialName("Length")
    val length: String,
    @SerialName("Width")
    val width: String
)