package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShopFacade(
    @SerialName("facade_height")
    val facade_height: String,
    @SerialName("facade_width")
    val facade_width: String
)
