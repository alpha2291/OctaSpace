package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Register_Data(
    @SerialName("name")
    val name: String,
    @SerialName("otp")
    val otp: String,
    @SerialName("phone_num")
    val phone_num: String,
    @SerialName("phone_num_cc")
    val phone_num_cc: String,
    @SerialName("user_id")
    val user_id: Int
)