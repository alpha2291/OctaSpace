package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.dummy


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchedFor(
    @SerialName("price_range")
    val price_range: String?,
    @SerialName("property_type")
    val property_type: Int?
)