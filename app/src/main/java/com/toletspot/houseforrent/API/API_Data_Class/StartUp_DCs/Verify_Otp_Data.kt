package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Verify_Otp_Data(
    @SerialName("email")
    val email: String,
    @SerialName("name")
    val name: String,
    @SerialName("username")
    val username: String,
    @SerialName("phone_num")
    val phone_num: String,
    @SerialName("phone_num_cc")
    val phone_num_cc: String,
    @SerialName("token")
    val token: String,
    @SerialName("user_id")
    val user_id: Int,
    @SerialName("interest_page")
    val interest_page: Int,
    @SerialName("location_page")
    val location_page: Int,
    @SerialName("whatsapp_num")
    val whatsapp_num: String,
    @SerialName("whatsapp_num_cc")
    val whatsapp_num_cc: String,
    @SerialName("country")
    val country: String,
    @SerialName("state")
    val state: String,
    @SerialName("city")
    val city: String,
    @SerialName("pincode")
    val pincode: String,
    @SerialName("latitude")
    val latitude: String,
    @SerialName("longitude")
    val longitude: String
)

