package com.toletspot.houseforrent.Home_Screen.Search_Module.RentoSortFilterDC


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostedBy(
    @SerialName("id")
    val id: Int,
    @SerialName("posted_by")
    val postedBy: String
)