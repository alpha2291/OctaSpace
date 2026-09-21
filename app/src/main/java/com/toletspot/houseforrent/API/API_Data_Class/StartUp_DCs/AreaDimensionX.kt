package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AreaDimensionX(
    @SerialName("length")
    val length: String,
    @SerialName("width")
    val width: String
)
