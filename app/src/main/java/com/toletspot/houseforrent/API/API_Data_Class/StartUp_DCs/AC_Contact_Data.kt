package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AC_Contact_Data(
    @SerialName("email")
    val email: String,
    @SerialName("otp")
    val otp: String,
    @SerialName("otp_sent")
    val otpSent: Boolean,
    @SerialName("user_id")
    val userId: String,
    @SerialName("whatsapp_num")
    val whatsappNum: String,
    @SerialName("whatsapp_num_cc")
    val whatsappNumCc: String
)
