package com.toletspot.houseforrent.Home_Screen.Enquiry_Module.RentoMyLeadsDC

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchedFor(
    @SerialName("price_range")
    val priceRange: String,
    @SerialName("property_type")
    val propertyType: Int
)
