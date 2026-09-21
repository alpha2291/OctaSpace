package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.ChatMainList

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EnquireDetails(
    @SerialName("enquiry_created")
    val enquiry_created: String,
    @SerialName("enquiry_updated")
    val enquiry_updated: String
)
