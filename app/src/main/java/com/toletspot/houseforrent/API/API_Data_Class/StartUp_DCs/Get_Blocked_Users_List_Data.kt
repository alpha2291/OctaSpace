package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Get_Blocked_Users_List_Data(
    @SerialName("followers")
    val followers: Int,
    @SerialName("following")
    val following: Int,
    @SerialName("im_followed")
    val im_followed: Int,
    @SerialName("is_blocked")
    val is_blocked: Int,
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
