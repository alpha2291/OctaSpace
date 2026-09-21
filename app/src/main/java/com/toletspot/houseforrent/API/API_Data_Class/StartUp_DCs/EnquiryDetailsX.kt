package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EnquiryDetailsX(
    @SerialName("created_at")
    val created_at: String,
    @SerialName("enquire_id")
    val enquire_id: Int,
    @SerialName("enquiry_by_profile_image")
    val enquiry_by_profile_image: String,
    @SerialName("enquiry_by_user_email")
    val enquiry_by_user_email: String,
    @SerialName("enquiry_by_user_id")
    val enquiry_by_user_id: Int,
    @SerialName("enquiry_by_user_name")
    val enquiry_by_user_name: String,
    @SerialName("enquiry_by_user_phone")
    val enquiry_by_user_phone: String,
    @SerialName("enquiry_by_user_whatsapp")
    val enquiry_by_user_whatsapp: String,
    @SerialName("land_category_para")
    val land_category_para: String
)