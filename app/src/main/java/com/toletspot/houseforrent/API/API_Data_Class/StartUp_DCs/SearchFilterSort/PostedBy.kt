package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.SearchFilterSort


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostedBy(
    @SerialName("id")
    val id: Int,
    @SerialName("posted_by")
    val posted_by: String
)