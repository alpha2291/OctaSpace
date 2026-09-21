package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.SearchFilterSort

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PropertyType(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)
