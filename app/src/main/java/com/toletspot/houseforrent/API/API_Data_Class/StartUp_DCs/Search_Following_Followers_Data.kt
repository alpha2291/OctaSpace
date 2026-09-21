package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Search_Following_Followers_Data(
    @SerialName("followers_count")
    val followers_count: Int,
    @SerialName("following_count")
    val following_count: Int,
    @SerialName("im_followed")
    val im_followed: Int,
    @SerialName("is_followed")
    val is_followed: Int,
    @SerialName("name")
    val name: String,
    @SerialName("profile_image")
    val profile_image: String,
    @SerialName("user_id")
    val user_id: Int,
    @SerialName("username")
    val username: String
)
