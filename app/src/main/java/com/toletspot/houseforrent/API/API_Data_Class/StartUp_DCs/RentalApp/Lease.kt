package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.RentalApp


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Lease(
    @SerialName("Lease_Amount")
    val Lease_Amount: String,
    @SerialName("Lease_Duration_in_Years")
    val Lease_Duration_in_Years: List<String>,
    @SerialName("Lease_Negotiable")
    val Lease_Negotiable: String
)