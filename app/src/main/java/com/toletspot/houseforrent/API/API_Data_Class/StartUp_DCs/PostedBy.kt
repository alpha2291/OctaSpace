package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostedBy(
    @SerialName("id")
    val id: Int,
    @SerialName("posted_by")
    val postedBy: String
)