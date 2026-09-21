package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostProperty(
    @SerialName("address")
    val address: String,
    @SerialName("amenities")
    val amenities: String,
    @SerialName("area_length")
    val area_length: String,
    @SerialName("area_width")
    val area_width: String,
    @SerialName("bhk_type")
    val bhk_type: String,
    @SerialName("boundary_wall")
    val boundary_wall: String,
    @SerialName("built_up_area")
    val built_up_area: String,
    @SerialName("carpet_area")
    val carpet_area: String,
    @SerialName("central_ac")
    val central_ac: String,
    @SerialName("city")
    val city: String,
    @SerialName("conference_room")
    val conference_room: String,
    @SerialName("country")
    val country: String,
    @SerialName("created_at")
    val created_at: String,
    @SerialName("facade_height")
    val facade_height: String,
    @SerialName("facade_width")
    val facade_width: String,
    @SerialName("fire_safety_measures")
    val fire_safety_measures: String,
    @SerialName("furnishing_status")
    val furnishing_status: String,
    @SerialName("image_urls")
    val image_urls: List<String>,
    @SerialName("is_report")
    val is_report: Int,
    @SerialName("land_categorie_id")
    val land_categorie_id: Int,
    @SerialName("landCategoryText")
    val landCategoryText: String,
    @SerialName("land_type_id")
    val land_type_id: Int,
    @SerialName("landTypeText")
    val landTypeText: String,
    @SerialName("latitude")
    val latitude: String,
    @SerialName("lifts")
    val lifts: String,
    @SerialName("local_authority")
    val local_authority: String,
    @SerialName("locality")
    val locality: String,
    @SerialName("longitude")
    val longitude: String,
    @SerialName("max_of_seats")
    val max_of_seats: String,
    @SerialName("min_of_seats")
    val min_of_seats: String,
    @SerialName("no_of_balconies")
    val no_of_balconies: String,
    @SerialName("no_of_bathrooms")
    val no_of_bathrooms: String,
    @SerialName("no_of_bedrooms")
    val no_of_bedrooms: String,
    @SerialName("no_of_cabins")
    val no_of_cabins: String,
    @SerialName("no_of_meeting_rooms")
    val no_of_meeting_rooms: String,
    @SerialName("no_of_open_sides")
    val no_of_open_sides: String,
    @SerialName("no_of_staircases")
    val no_of_staircases: String,
    @SerialName("noc_certified")
    val noc_certified: String,
    @SerialName("occupancy_certificate")
    val occupancy_certificate: String,
    @SerialName("other_rooms")
    val other_rooms: String,
    @SerialName("oxygen_duct")
    val oxygen_duct: String,
    @SerialName("pantry")
    val pantry: String,
    @SerialName("pantry_size")
    val pantry_size: String,

    @SerialName("property_area")
    val property_area: String,
    @SerialName("property_facing")
    val property_facing: String,
    @SerialName("property_highlights")
    val property_highlights: String,
    @SerialName("property_name")
    val property_name: String,
    @SerialName("reception_area")
    val reception_area: String,
    @SerialName("state")
    val state: String,
    @SerialName("suitable_business_type")
    val suitable_business_type: String,
    @SerialName("super_built_up_area")
    val super_built_up_area: String,
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("total_floor")
    val total_floor: String,
    @SerialName("ups")
    val ups: String,
    @SerialName("user_post_id")
    val user_post_id: Int,
    @SerialName("user_type")
    val user_type: String,
    @SerialName("video")
    val video: String,
    @SerialName("washroom_details")
    val washroom_details: String
)
