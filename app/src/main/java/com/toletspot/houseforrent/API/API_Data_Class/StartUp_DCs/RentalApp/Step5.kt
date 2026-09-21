package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.RentalApp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Step5(
    @SerialName("Agreement_Type")
    val Agreement_Type: List<String>,
    @SerialName("Amenities")
    val Amenities: List<String>,
    @SerialName("Food_Preferences")
    val Food_Preferences: List<String>,
    @SerialName("Furnishing_Status")
    val Furnishing_Status: List<String>,
    @SerialName("No_of_Balconies")
    val No_of_Balconies: List<String>,
    @SerialName("no_of_Bathrooms")
    val no_of_Bathrooms: List<String>,
    @SerialName("Other_Rooms")
    val Other_Rooms: List<String>,
    @SerialName("parking_available")
    val parking_available: List<String>,
    @SerialName("Pets_Allowed")
    val Pets_Allowed: List<String>,
    @SerialName("Property_Highlights")
    val Property_Highlights: List<String>,
    @SerialName("Reception_Area")
    val Reception_Area: List<String>
)
