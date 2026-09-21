package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.RentalApp


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Rent(
    @SerialName("Deposit_Amount_month_of_rents")
    val Deposit_Amount_month_of_rents: List<String>,
    @SerialName("Duration_of_Agreement")
    val Duration_of_Agreement: List<String>,
    @SerialName("Lock_in_Period")
    val Lock_in_Period: List<String>,
    @SerialName("Notice_Period")
    val Notice_Period: List<String>,
    @SerialName("Rent")
    val Rent: String,
    @SerialName("Rent_Negotiable")
    val Rent_Negotiable: String
)