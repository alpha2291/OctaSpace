package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs

import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentoMediaDC.Image
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentoMediaDC.Video
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Get_Reels_Property_Data(

    @SerialName("address")
    val address: String? = null,

    @SerialName("agreement_type")
    val agreement_type: String? = null,

    @SerialName("amenities")
    val amenities: String? = null,

    @SerialName("area_length")
    val area_length: String? = null,

    @SerialName("area_length_unit")
    val area_length_unit: String? = null,

    @SerialName("area_width")
    val area_width: String? = null,

    @SerialName("area_width_unit")
    val area_width_unit: String? = null,

    @SerialName("availability_from")
    val availability_from: String? = null,

    @SerialName("bhk_type")
    val bhk_type: String? = null,

    @SerialName("boundary_wall")
    val boundary_wall: String? = null,

    @SerialName("built_up_area")
    val built_up_area: String? = null,

    @SerialName("built_up_area_unit")
    val built_up_area_unit: String? = null,

    @SerialName("carpet_area")
    val carpet_area: String? = null,

    @SerialName("carpet_area_unit")
    val carpet_area_unit: String? = null,

    @SerialName("central_ac")
    val central_ac: String? = null,

    @SerialName("city")
    val city: String? = null,

    @SerialName("conference_room")
    val conference_room: String? = null,

    @SerialName("country")
    val country: String? = null,

    @SerialName("created_at")
    val created_at: String? = null,

    @SerialName("deposit_amount_month_of_rents")
    val deposit_amount_month_of_rents: String? = null,

    @SerialName("deposit_amount_month_of_rents_type")
    val deposit_amount_month_of_rents_type: String? = null,

    @SerialName("does_local_authority")
    val does_local_authority: String? = null,

    @SerialName("draft")
    val draft: String? = null,

    @SerialName("is_sold")
    val is_sold: Int? = null,

    @SerialName("duration_of_agreement")
    val duration_of_agreement: String? = null,

    @SerialName("duration_of_agreement_type")
    val duration_of_agreement_type: String? = null,

    @SerialName("facade_height")
    val facade_height: String? = null,

    @SerialName("facade_height_unit")
    val facade_height_unit: String? = null,

    @SerialName("facade_width")
    val facade_width: String? = null,

    @SerialName("facade_width_unit")
    val facade_width_unit: String? = null,

    @SerialName("fire_safety_measures")
    val fire_safety_measures: String? = null,

    @SerialName("food_preferences")
    val food_preferences: String? = null,

    @SerialName("furnishing_status")
    val furnishing_status: String? = null,

    @SerialName("status")
    val status: String? = null,

    @SerialName("images")
    val images: List<Image>? = null,

    @SerialName("is_report")
    val is_report: Int? = null,

    @SerialName("is_this_property_for_rent_or_lease")
    val is_this_property_for_rent_or_lease: String? = null,

    @SerialName("land_categorie_id")
    val land_categorie_id: Int? = null,

    @SerialName("landCategoryText")
    val landCategoryText: String? = null,

    @SerialName("land_type_id")
    val land_type_id: Int? = null,

    @SerialName("landTypeText")
    val landTypeText: String? = null,

    @SerialName("latitude")
    val latitude: String? = null,

    @SerialName("lease_amount")
    val lease_amount: String? = null,

    @SerialName("lease_duration_in_years")
    val lease_duration_in_years: String? = null,

    @SerialName("lease_negotiable")
    val lease_negotiable: String? = null,

    @SerialName("lifts")
    val lifts: String? = null,

    @SerialName("locality")
    val locality: String? = null,

    @SerialName("lock_in_period")
    val lock_in_period: String? = null,

    @SerialName("lock_in_period_type")
    val lock_in_period_type: String? = null,

    @SerialName("longitude")
    val longitude: String? = null,

    @SerialName("map_config")
    val map_config: String? = null,

    @SerialName("max_of_seats")
    val max_of_seats: String? = null,

    @SerialName("min_of_seats")
    val min_of_seats: String? = null,

    @SerialName("no_of_Balconies")
    val no_of_Balconies: String? = null,

    @SerialName("no_of_Bathrooms")
    val no_of_Bathrooms: String? = null,

    @SerialName("no_of_bedrooms")
    val no_of_bedrooms: String? = null,

    @SerialName("no_of_cabins")
    val no_of_cabins: String? = null,

    @SerialName("no_of_meeting_rooms")
    val no_of_meeting_rooms: String? = null,

    @SerialName("no_of_open_sides")
    val no_of_open_sides: String? = null,

    @SerialName("no_of_Staircases")
    val no_of_Staircases: String? = null,

    @SerialName("noc_certified")
    val noc_certified: String? = null,

    @SerialName("notice_period")
    val notice_period: String? = null,

    @SerialName("occupancy_certificate")
    val occupancy_certificate: String? = null,

    @SerialName("other_rooms")
    val other_rooms: String? = null,

    @SerialName("oxygen_duct")
    val oxygen_duct: String? = null,

    @SerialName("pantry")
    val pantry: String? = null,

    @SerialName("pantry_size")
    val pantry_size: String? = null,

    @SerialName("pantry_size_unit")
    val pantry_size_unit: String? = null,

    @SerialName("parking_available")
    val parking_available: String? = null,

    @SerialName("pets_allowed")
    val pets_allowed: String? = null,

    @SerialName("pincode")
    val pincode: String? = null,

    @SerialName("post_type")
    val post_type: String? = null,

    @SerialName("preferred_tenants")
    val preferred_tenants: String? = null,

    @SerialName("property_area")
    val property_area: String? = null,

    @SerialName("property_area_unit")
    val property_area_unit: String? = null,

    @SerialName("property_facing")
    val property_facing: String? = null,

    @SerialName("property_highlights")
    val property_highlights: String? = null,

    @SerialName("property_name")
    val property_name: String? = null,

    @SerialName("reception_area")
    val reception_area: String? = null,

    @SerialName("rent")
    val rent: String? = null,

    @SerialName("rent_floor_no")
    val rent_floor_no: String? = null,

    @SerialName("rent_negotiable")
    val rent_negotiable: String? = null,

    @SerialName("state")
    val state: String? = null,

    @SerialName("suitable_business_type")
    val suitable_business_type: String? = null,

    @SerialName("super_built_up_area")
    val super_built_up_area: String? = null,

    @SerialName("super_built_up_area_unit")
    val super_built_up_area_unit: String? = null,

    @SerialName("U_ID")
    val U_ID: Int? = null,

    @SerialName("thumbnail")
    val thumbnail: String? = null,

    @SerialName("total_deposit")
    val total_deposit: String? = null,

    @SerialName("total_floor")
    val total_floor: String? = null,

    @SerialName("ups")
    val ups: String? = null,

    @SerialName("user_post_id")
    val user_post_id: Int? = null,

    @SerialName("user_type")
    val user_type: String? = null,

    @SerialName("video")
    val video: List<Video>? = null,

    @SerialName("washroom_details")
    val washroom_details: String? = null
)
