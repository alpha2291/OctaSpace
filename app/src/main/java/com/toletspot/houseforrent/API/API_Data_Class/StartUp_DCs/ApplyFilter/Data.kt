package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.ApplyFilter


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("cities")
    val cities: String,
    @SerialName("country")
    val country: String,
    @SerialName("email")
    val email: String,
    @SerialName("enquiry")
    val enquiry: Int,
    @SerialName("is_liked")
    val is_liked: Int,
    @SerialName("is_saved")
    val is_saved: Int,
    @SerialName("name")
    val name: String,
    @SerialName("phone_num")
    val phone_num: String,
    @SerialName("phone_num_cc")
    val phone_num_cc: String,
    @SerialName("post_property")
    val post_property: PostProperty,
    @SerialName("profile_image")
    val profile_image: String,
    @SerialName("state")
    val state: String,
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("total_comments")
    val total_comments: Int,
    @SerialName("total_likes")
    val total_likes: Int,
    @SerialName("user_id")
    val user_id: Int,
    @SerialName("user_post_id")
    val user_post_id: Int,
    @SerialName("username")
    val username: String,
    @SerialName("video")
    val video: String,
    @SerialName("whatsapp_num")
    val whatsapp_num: String,
    @SerialName("whatsapp_num_cc")
    val whatsapp_num_cc: String
)