package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OptionalXX(
    @SerialName("amenities")
    val amenities: List<String>,
    @SerialName("does_your_property_authority_approved")
    val does_your_property_authority_approved: List<String>,
    @SerialName("furnishing_status")
    val furnishing_status: List<String>,
    @SerialName("is_boundary_wall_around_property")
    val is_boundary_wall_around_property: List<String>,
    @SerialName("no_of_balconies")
    val no_of_balconies: List<String>,
    @SerialName("no_of_bathrooms")
    val no_of_bathrooms: List<String>,
    @SerialName("no_of_bedrooms")
    val no_of_bedrooms: List<String>,
    @SerialName("no_of_open_sides")
    val no_of_open_sides: List<String>,
    @SerialName("other_rooms")
    val other_rooms: List<String>,
    @SerialName("parking_available")
    val parking_available: List<String>,
    @SerialName("property_highlights")
    val property_highlights: List<String>
)