package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.SearchFilterSort

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FloorPreference(
    @SerialName("floor_preferences")
    val floor_preferences: String,
    @SerialName("id")
    val id: Int
)
