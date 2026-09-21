package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Post_Form_7_Data(
    @SerialName("account_status")
    val account_status: Int,
    @SerialName("address")
    val address: String,
    @SerialName("amenities")
    val amenities: String,
    @SerialName("area_length")
    val area_length: String,
    @SerialName("area_length_unit")
    val area_length_unit: String,
    @SerialName("area_width")
    val area_width: String,
    @SerialName("area_width_unit")
    val area_width_unit: String,
    @SerialName("bhk_type")
    val bhk_type: String,
    @SerialName("boundary_wall")
    val boundary_wall: String,
    @SerialName("built_up_area")
    val built_up_area: String,
    @SerialName("built_up_area_unit")
    val built_up_area_unit: String,
    @SerialName("carpet_area")
    val carpet_area: String,
    @SerialName("carpet_area_unit")
    val carpet_area_unit: String,
    @SerialName("central_ac")
    val central_ac: String,
    @SerialName("city")
    val city: String,
    @SerialName("conference_room")
    val conference_room: String,
    @SerialName("country")
    val country: String,
    @SerialName("pincode")
    val pincode: String,
    @SerialName("does_local_authority")
    val does_local_authority: String,
    @SerialName("facade_height")
    val facade_height: String,
    @SerialName("facade_height_unit")
    val facade_height_unit: String,
    @SerialName("facade_width")
    val facade_width: String,
    @SerialName("facade_width_unit")
    val facade_width_unit: String,
    @SerialName("fire_safety_measures")
    val fire_safety_measures: String,
    @SerialName("furnishing_status")
    val furnishing_status: String,
    @SerialName("image_ids")
    val image_ids: String,
    @SerialName("land_categorie_id")
    val land_categorie_id: Int,
    @SerialName("land_type_id")
    val land_type_id: Int,
    @SerialName("latitude")
    val latitude: String,
    @SerialName("lifts")
    val lifts: String,
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
    @SerialName("pantry_size_unit")
    val pantry_size_unit: String,
    @SerialName("parking_available")
    val parking_available: String,

    @SerialName("property_area")
    val property_area: String,
    @SerialName("property_area_unit")
    val property_area_unit: String,
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
    @SerialName("super_built_up_area_unit")
    val super_built_up_area_unit: String,
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("total_floor")
    val total_floor: String,
    @SerialName("U_ID")
    val U_ID: Int,
    @SerialName("ups")
    val ups: String,
    @SerialName("user_post_id")
    val user_post_id: Int,
    @SerialName("user_type")
    val user_type: String,
    @SerialName("video")
    val video: String,
    @SerialName("views")
    val views: String,
    @SerialName("washroom_details")
    val washroom_details: String,
    @SerialName("images")
    val images: List<String>,
    @SerialName("rent_floor_no")
    val rent_floor_no: String
)
