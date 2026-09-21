package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FloorPreference(
    @SerialName("floor_preferences")
    val floorPreferences: String,
    @SerialName("id")
    val id: Int
)