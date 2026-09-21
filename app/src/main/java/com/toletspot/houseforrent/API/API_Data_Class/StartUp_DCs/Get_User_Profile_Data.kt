package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Get_User_Profile_Data(
    @SerialName("bio")
    val bio: String,
    @SerialName("followers")
    val followers: Int,
    @SerialName("following")
    val following: Int,
    @SerialName("posts")
    val posts: Int,
    @SerialName("profile_image")
    val profile_image: String,
    @SerialName("user_id")
    val user_id: Int,
    @SerialName("username")
    val username: String,
    @SerialName("name")
    val name: String,
    @SerialName("phone_num")
    val phone_num: String,
    @SerialName("phone_num_cc")
    val phone_num_cc: String,
    @SerialName("is_blocked")
    val is_blocked: Int,
    @SerialName("is_report")
    val is_report: Int,
    @SerialName("others_page")
    val others_page: String,
    @SerialName("latitude")
    val latitude: String,
    @SerialName("longitude")
    val longitude: String,
    @SerialName("is_followed")
    val is_followed: String,
    @SerialName("im_followed")
    val im_followed: String,
    @SerialName("country")
    val country: String,
    @SerialName("state")
    val state: String,
    @SerialName("city")
    val city: String,
    @SerialName("pincode")
    val pincode: String
)
