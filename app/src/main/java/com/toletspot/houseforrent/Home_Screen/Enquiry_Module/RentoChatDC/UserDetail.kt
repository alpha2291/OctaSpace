package com.toletspot.houseforrent.Home_Screen.Enquiry_Module.RentoChatDC

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDetail(
    @SerialName("cities")
    val cities: String,
    @SerialName("country")
    val country: String,
    @SerialName("email")
    val email: String,
    @SerialName("name")
    val name: String,
    @SerialName("phone_num")
    val phoneNum: String,
    @SerialName("phone_num_cc")
    val phoneNumCc: String,
    @SerialName("profile_image")
    val profileImage: String,
    @SerialName("state")
    val state: String,
    @SerialName("user_id")
    val userId: Int,
    @SerialName("username")
    val username: String,
    @SerialName("whatsapp_num")
    val whatsappNum: String,
    @SerialName("whatsapp_num_cc")
    val whatsappNumCc: String
)
