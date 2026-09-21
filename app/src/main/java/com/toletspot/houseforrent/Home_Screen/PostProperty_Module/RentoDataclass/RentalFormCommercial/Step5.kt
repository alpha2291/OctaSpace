package com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentalFormCommercial


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Step5(
    @SerialName("Amenities")
    val Amenities: List<String>,
    @SerialName("Central_AC")
    val Central_AC: List<String>,
    @SerialName("Conference_Room")
    val Conference_Room: List<String>,
    @SerialName("Fire_Safety_Measures")
    val Fire_Safety_Measures: List<String>,
    @SerialName("Furnishing_Status")
    val Furnishing_Status: List<String>,
    @SerialName("Is_boundary_wall_around_property")
    val Is_boundary_wall_around_property: List<String>,
    @SerialName("Is_your_office_fire_NOC_Certified")
    val Is_your_office_fire_NOC_Certified: List<String>,
    @SerialName("Lifts")
    val Lifts: List<String>,
    @SerialName("Max_No_of_Seats")
    val Max_No_of_Seats: List<String>,
    @SerialName("Min_No_of_Seats")
    val Min_No_of_Seats: List<String>,
    @SerialName("No_of_Bathrooms")
    val No_of_Bathrooms: List<String>,
    @SerialName("No_of_Cabins")
    val No_of_Cabins: List<String>,
    @SerialName("No_of_Meeting_Rooms")
    val No_of_Meeting_Rooms: List<String>,
    @SerialName("No_of_Open_sides")
    val No_of_Open_sides: List<String>,
    @SerialName("No_of_Staircases")
    val No_of_Staircases: List<String>,
    @SerialName("Occupancy_Certificate")
    val Occupancy_Certificate: List<String>,
    @SerialName("Oxygen_Duct")
    val Oxygen_Duct: List<String>,
    @SerialName("Pantry")
    val Pantry: List<String>,
    @SerialName("Pantry_Size")
    val Pantry_Size: String,
    @SerialName("Parking_Available")
    val Parking_Available: List<String>,
    @SerialName("Property_Condition")
    val Property_Condition: List<String>,
    @SerialName("Property_Highlights")
    val Property_Highlights: List<String>,
    @SerialName("Reception_Area")
    val Reception_Area: List<String>,
    @SerialName("Suitable_Business_Type")
    val Suitable_Business_Type: List<String>,
    @SerialName("UPS")
    val UPS: List<String>,
    @SerialName("Washrooms")
    val Washrooms: List<String>
)