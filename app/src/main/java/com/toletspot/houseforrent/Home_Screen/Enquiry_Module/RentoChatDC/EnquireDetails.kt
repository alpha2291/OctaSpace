package com.toletspot.houseforrent.Home_Screen.Enquiry_Module.RentoChatDC

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EnquireDetails(
    @SerialName("enquiry_created")
    val enquiryCreated: String,
    @SerialName("enquiry_updated")
    val enquiryUpdated: String
)
