package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.PostProperty6

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
    val isLiked: Int,
    @SerialName("is_saved")
    val isSaved: Int,
    @SerialName("name")
    val name: String,
    @SerialName("phone_num")
    val phoneNum: String,
    @SerialName("phone_num_cc")
    val phoneNumCc: String,
    @SerialName("post_property")
    val postProperty: PostProperty,
    @SerialName("profile_image")
    val profileImage: String,
    @SerialName("state")
    val state: String,
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("total_comments")
    val totalComments: Int,
    @SerialName("total_likes")
    val totalLikes: Int,
    @SerialName("user_id")
    val userId: Int,
    @SerialName("user_post_id")
    val userPostId: Int,
    @SerialName("username")
    val username: String,
    @SerialName("video")
    val video: String,
    @SerialName("whatsapp_num")
    val whatsappNum: String,
    @SerialName("whatsapp_num_cc")
    val whatsappNumCc: String
)
