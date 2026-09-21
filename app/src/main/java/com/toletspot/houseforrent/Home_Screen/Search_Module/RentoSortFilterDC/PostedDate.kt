package com.toletspot.houseforrent.Home_Screen.Search_Module.RentoSortFilterDC


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostedDate(
    @SerialName("floor_preferences")
    val floor_preferences: String,
    @SerialName("id")
    val id: Int
)