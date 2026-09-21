package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Get_Self_Enquiry_Data(
    @SerialName("enquiry_details")
    val enquiry_details: EnquiryDetailsX,
    @SerialName("post_property")
    val post_property: PostPropertyXX
)
