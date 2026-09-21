package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs

import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.dummy.SearchedFor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Get_My_Leads_Data(

    @SerialName("isWhich")
    val isWhich: Int = 1,

    @SerialName("is_deleted")
    val is_deleted: Int,
    @SerialName("is_expired")
    val is_expired: Int,

    @SerialName("enquiry_details")
    val enquiry_details: EnquiryDetailsXX,
    @SerialName("post_user")
    val post_user: PostUser,
    @SerialName("search_type")
    val search_type: Int,
    @SerialName("searched_for")
    val searched_for: SearchedFor? = null

)
