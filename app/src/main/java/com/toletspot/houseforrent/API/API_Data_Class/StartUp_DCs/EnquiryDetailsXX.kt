package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EnquiryDetailsXX(

    @SerialName("address")
    val address: String? = null,

    @SerialName("cities")
    val cities: String? = null,

    @SerialName("country")
    val country: String? = null,

    @SerialName("created_at")
    val created_at: String? = null,

    @SerialName("enquire_id")
    val enquire_id: Int? = null,

    @SerialName("enquiry_by_name")
    val enquiry_by_name: String? = null,

    @SerialName("enquiry_by_profile_image")
    val enquiry_by_profile_image: String? = null,

    @SerialName("enquiry_by_user_email")
    val enquiry_by_user_email: String? = null,

    @SerialName("enquiry_by_user_id")
    val enquiry_by_user_id: Int? = null,

    @SerialName("enquiry_by_user_phone")
    val enquiry_by_user_phone: String? = null,

    @SerialName("enquiry_by_user_whatsapp")
    val enquiry_by_user_whatsapp: String? = null,

    @SerialName("enquiry_by_username")
    val enquiry_by_username: String? = null,

    @SerialName("enquiry_by_user_whatsapp_cc")
    val enquiry_by_user_whatsapp_cc: String? = null,

    @SerialName("enquiry_by_user_phone_cc")
    val enquiry_by_user_phone_cc: String? = null,

    @SerialName("is_declain")
    val is_declain: Int? = null,

    @SerialName("land_category_para")
    val land_category_para: String? = null,

    @SerialName("pincode")
    val pincode: String? = null,

    @SerialName("state")
    val state: String? = null,

)
