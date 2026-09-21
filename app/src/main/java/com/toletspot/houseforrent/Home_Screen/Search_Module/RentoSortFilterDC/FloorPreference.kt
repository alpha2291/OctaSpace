package com.toletspot.houseforrent.Home_Screen.Search_Module.RentoSortFilterDC

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FloorPreference(
    @SerialName("floor_preferences")
    val floorPreferences: String,
    @SerialName("id")
    val id: Int
)
