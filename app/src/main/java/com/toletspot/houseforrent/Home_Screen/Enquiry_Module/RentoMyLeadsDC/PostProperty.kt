package com.toletspot.houseforrent.Home_Screen.Enquiry_Module.RentoMyLeadsDC

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize

@Serializable
data class PostProperty(
    @SerialName("address")
    val address: String,
    @SerialName("agreement_type")
    val agreement_type: String,
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
    @SerialName("availability_from")
    val availability_from: String,
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
    @SerialName("created_at")
    val created_at: String,
    @SerialName("deposit_amount_month_of_rents")
    val deposit_amount_month_of_rents: String,
    @SerialName("deposit_amount_month_of_rents_type")
    val deposit_amount_month_of_rents_type: String,
    @SerialName("does_local_authority")
    val does_local_authority: String,
    @SerialName("draft")
    val draft: Int,
    @SerialName("duration_of_agreement")
    val duration_of_agreement: String,
    @SerialName("duration_of_agreement_type")
    val duration_of_agreement_type: String,
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
    @SerialName("food_preferences")
    val food_preferences: String,
    @SerialName("furnishing_status")
    val furnishing_status: String,
    @SerialName("images")
    val images: List<Image>,
    @SerialName("is_report")
    val is_report: String,
    @SerialName("is_this_property_for_rent_or_lease")
    val is_this_property_for_rent_or_lease: String,
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
    @SerialName("lease_amount")
    val lease_amount: String,
    @SerialName("lease_duration_in_years")
    val lease_duration_in_years: String,
    @SerialName("lease_negotiable")
    val lease_negotiable: String,
    @SerialName("lifts")
    val lifts: String,
    @SerialName("locality")
    val locality: String,
    @SerialName("lock_in_period")
    val lock_in_period: String,
    @SerialName("lock_in_period_type")
    val lock_in_period_type: String,
    @SerialName("longitude")
    val longitude: String,
    @SerialName("map_config")
    val map_config: String,
    @SerialName("max_of_seats")
    val max_of_seats: String,
    @SerialName("min_of_seats")
    val min_of_seats: String,
    @SerialName("no_of_Balconies")
    val no_of_Balconies: String,
    @SerialName("no_of_Bathrooms")
    val no_of_Bathrooms: String,
    @SerialName("no_of_bedrooms")
    val no_of_bedrooms: String,
    @SerialName("no_of_cabins")
    val no_of_cabins: String,
    @SerialName("no_of_meeting_rooms")
    val no_of_meeting_rooms: String,
    @SerialName("no_of_open_sides")
    val no_of_open_sides: String,
    @SerialName("no_of_Staircases")
    val no_of_Staircases: String,
    @SerialName("noc_certified")
    val noc_certified: String,
    @SerialName("notice_period")
    val notice_period: String,
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
    @SerialName("pets_allowed")
    val pets_allowed: String,
    @SerialName("pincode")
    val pincode: String,
    @SerialName("post_type")
    val post_type: String,
    @SerialName("preferred_tenants")
    val preferred_tenants: String,
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
    @SerialName("rent")
    val rent: String,
    @SerialName("rent_floor_no")
    val rent_floor_no: String,
    @SerialName("rent_negotiable")
    val rent_negotiable: String,
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
    @SerialName("total_deposit")
    val total_deposit: String,
    @SerialName("total_floor")
    val total_floor: String,
    @SerialName("ups")
    val ups: String,
    @SerialName("user_post_id")
    val user_post_id: Int,
    @SerialName("status")
    val status: Int,
    @SerialName("is_sold")
    val is_sold: Int,
    @SerialName("user_type")
    val user_type: String,
    @SerialName("video")
    val video: List<Video>,
    @SerialName("washroom_details")
    val washroom_details: String
) : Parcelable
