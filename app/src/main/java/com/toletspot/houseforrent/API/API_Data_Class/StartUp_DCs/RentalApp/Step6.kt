package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.RentalApp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Step6(
    @SerialName("Is_this_property_for_rent_or_Lease")
    val Is_this_property_for_rent_or_Lease: List<String>,
    @SerialName("Lease")
    val Lease: List<Lease>,
    @SerialName("Rent")
    val Rent: List<Rent>
)
