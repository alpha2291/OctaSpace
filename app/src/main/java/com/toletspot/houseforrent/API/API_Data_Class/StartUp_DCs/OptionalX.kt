package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OptionalX(
    @SerialName("amenities")
    val amenities: List<String>,
    @SerialName("central_ac")
    val central_ac: List<String>,
    @SerialName("conference_room")
    val conference_room: List<String>,
    @SerialName("fire_safety_measures")
    val fire_safety_measures: List<String>,
    @SerialName("furnishing_status")
    val furnishing_status: List<String>,
    @SerialName("is_it_Pre_leased_pre_rented")
    val is_it_Pre_leased_pre_rented: List<String>,
    @SerialName("is_your_office_fire_noc_certified")
    val is_your_office_fire_noc_certified: List<String>,
    @SerialName("lifts")
    val lifts: List<String>,
    @SerialName("max_no_of_seats")
    val max_no_of_seats: List<String>,
    @SerialName("min_no_of_seats")
    val min_no_of_seats: List<String>,
    @SerialName("no_of_cabins")
    val no_of_cabins: List<String>,
    @SerialName("no_of_meeting_rooms")
    val no_of_meeting_rooms: List<String>,
    @SerialName("no_of_open_sides")
    val no_of_open_sides: List<String>,
    @SerialName("no_of_staircases")
    val no_of_staircases: List<String>,
    @SerialName("occupancy_certificate")
    val occupancy_certificate: List<String>,
    @SerialName("office_previously_used_for")
    val office_previously_used_for: List<String>,
    @SerialName("oxygen_duct")
    val oxygen_duct: List<String>,
    @SerialName("pantry")
    val pantry: List<String>,
    @SerialName("pantry_size")
    val pantry_size: String,
    @SerialName("parking_available")
    val parking_available: List<String>,
    @SerialName("property_highlights")
    val property_highlights: List<String>,
    @SerialName("reception_area")
    val reception_area: List<String>,
    @SerialName("suitable_business_type")
    val suitable_business_type: List<String>,
    @SerialName("ups")
    val ups: List<String>,
    @SerialName("washroom_details")
    val washroom_details: List<String>,
    @SerialName("which_authority_the_property_is_approved_by")
    val which_authority_the_property_is_approved_by: List<String>
)
