package com.toletspot.houseforrent.Home_Screen.Search_Module.RentoSortFilterDC


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PropertyType(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)