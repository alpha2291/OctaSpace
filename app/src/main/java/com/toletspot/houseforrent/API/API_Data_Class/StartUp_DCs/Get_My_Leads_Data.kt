package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs


import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.dummy.SearchedFor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable



/*@Serializable
data class Get_My_Leads_Data(
//    @SerialName("enquiry_details")
//    val enquiry_details: EnquiryDetailsXX,
//    @SerialName("post_user")
//    val post_user: PostUser,
    @SerialName("isWhich")
    val isWhich: Int = 1,
    @SerialName("search_type")
    val search_type: Int,
    @SerialName("is_deleted")
    val is_deleted: Int,
    @SerialName("searched_for")
    val searched_for: SearchedFor?,


    @SerialName("cities")
    val cities: String? = null,
    @SerialName("country")
    val country: String? = null,
    @SerialName("email")
    val email: String,
    @SerialName("enquiry")
    val enquiry: Int,
    @SerialName("enquiry_details")
    val enquiry_details: EnquiryDetailsXX,
//    @SerialName("is_deleted")
//    val isDeleted: Int,
    @SerialName("is_expired")
    val is_expired: Int,
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
//    @SerialName("search_type")
//    val searchType: Int,
    @SerialName("state")
    val state: String? = null,
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
    @SerialName("whatsapp_num")
    val whatsapp_num: String,
    @SerialName("whatsapp_num_cc")
    val whatsapp_num_cc: String


)*/

@Serializable
data class Get_My_Leads_Data(

    @SerialName("isWhich")
    val isWhich: Int = 1,
//    @SerialName("enquiry_details")
//    val enquiry_details: EnquiryDetails,
    @SerialName("is_deleted")
    val is_deleted: Int,
    @SerialName("is_expired")
    val is_expired: Int,
//    @SerialName("post_user")
//    val post_user: PostUser,

    @SerialName("enquiry_details")
    val enquiry_details: EnquiryDetailsXX,
    @SerialName("post_user")
    val post_user: PostUser,
    @SerialName("search_type")
    val search_type: Int,
    @SerialName("searched_for")
    val searched_for: SearchedFor? = null
//
//    @SerialName("search_type")
//    val search_type: Int,
//
//    @SerialName("is_deleted")
//    val is_deleted: Int,
//
//    @SerialName("searched_for")
//    val searched_for: SearchedFor? = null,
//
//    @SerialName("cities")
//    val cities: String? = null,
//
//    @SerialName("country")
//    val country: String? = null,
//
//    @SerialName("email")
//    val email: String? = null,      // ✅ FIXED (was crashing)
//
//    @SerialName("enquiry")
//    val enquiry: Int,
//
//    @SerialName("enquiry_details")
//    val enquiry_details: EnquiryDetailsXX? = null,   // backend may send null
//
//    @SerialName("is_expired")
//    val is_expired: Int,
//
//    @SerialName("is_liked")
//    val is_liked: Int,
//
//    @SerialName("is_saved")
//    val is_saved: Int,
//
//    @SerialName("name")
//    val name: String? = null,        // can be null
//
//    @SerialName("phone_num")
//    val phone_num: String? = null,
//
//    @SerialName("phone_num_cc")
//    val phone_num_cc: String? = null,
//
//    @SerialName("post_property")
//    val post_property: PostProperty? = null,   // API may skip this in some cases
//
//    @SerialName("profile_image")
//    val profile_image: String? = null,
//
//    @SerialName("state")
//    val state: String? = null,
//
//    @SerialName("thumbnail")
//    val thumbnail: String? = null,
//
//    @SerialName("total_comments")
//    val total_comments: Int? = null,
//
//    @SerialName("total_likes")
//    val total_likes: Int? = null,
//
//    @SerialName("user_id")
//    val user_id: Int? = null,
//
//    @SerialName("user_post_id")
//    val user_post_id: Int? = null,
//
//    @SerialName("username")
//    val username: String? = null,
//
//    @SerialName("whatsapp_num")
//    val whatsapp_num: String? = null,
//
//    @SerialName("whatsapp_num_cc")
//    val whatsapp_num_cc: String? = null
)


