package com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentalFormCommercial


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