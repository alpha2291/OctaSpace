package com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentalFormCommercial


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Rent(
    @SerialName("Deposit_Amount_month_of_rents")
    val depositAmountMonthOfRents: List<String>,
    @SerialName("Lock_in_Period")
    val lockInPeriod: List<String>,
    @SerialName("Notice_Period")
    val noticePeriod: List<String>,
    @SerialName("Rent")
    val rent: String,
    @SerialName("Rent_Negotiable")
    val rentNegotiable: String
)