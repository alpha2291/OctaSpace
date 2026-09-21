package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Login_Data(
    @SerialName("user_id")
    val user_id: Int,
    @SerialName("otp")
    val otp: String,
    @SerialName("otp_sent")
    val otpSent: Boolean,
    @SerialName("phone_num")
    val phoneNum: String,
    @SerialName("phone_num_cc")
    val phone_num_cc: String,
    @SerialName("account_banned")
    val account_banned: Int,
    @SerialName("account_deactivated")
    val account_deactivated: Int,
    @SerialName("account_deactivate_days")
    val account_deactivate_days: Int
)
