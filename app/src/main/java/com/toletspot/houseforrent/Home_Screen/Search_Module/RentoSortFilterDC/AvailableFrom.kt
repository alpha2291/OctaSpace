package com.toletspot.houseforrent.Home_Screen.Search_Module.RentoSortFilterDC

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AvailableFrom(
    @SerialName("available_from")
    val available_from: String,
    @SerialName("id")
    val id: Int
)
