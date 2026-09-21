package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.NewDraftFlow


import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentoMediaDC.Image
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentoMediaDC.Video
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*
@Serializable
data class New_Draft_Flow_Data(
    @SerialName("account_status")
    val account_status: Int?,
    @SerialName("address")
    val address: String?,
    @SerialName("amenities")
    val amenities: String?,
    @SerialName("area_length")
    val area_length: String?,
    @SerialName("area_length_unit")
    val area_length_unit: String?,
    @SerialName("area_width")
    val area_width: String?,
    @SerialName("area_width_unit")
    val area_width_unit: String?,
    @SerialName("availability_status")
    val availability_status: String?,
    @SerialName("bhk_type")
    val bhk_type: String?,
    @SerialName("boundary_wall")
    val boundary_wall: String?,
    @SerialName("built_up_area")
    val built_up_area: String?,
    @SerialName("built_up_area_unit")
    val built_up_area_unit: String?,
    @SerialName("carpet_area")
    val carpet_area: String?,
    @SerialName("carpet_area_unit")
    val carpet_area_unit: String?,
    @SerialName("central_ac")
    val central_ac: String?,
    @SerialName("city")
    val city: String?,
    @SerialName("conference_room")
    val conference_room: String?,
    @SerialName("country")
    val country: String?,
    @SerialName("does_local_authority")
    val does_local_authority: String?,
    @SerialName("facade_height")
    val facade_height: String?,
    @SerialName("facade_height_unit")
    val facade_height_unit: String?,
    @SerialName("facade_width")
    val facade_width: String?,
    @SerialName("facade_width_unit")
    val facade_width_unit: String?,
    @SerialName("fire_safety_measures")
    val fire_safety_measures: String?,
    @SerialName("furnishing_status")
    val furnishing_status: String?,
    @SerialName("image_ids")
    val image_ids: String?,
    @SerialName("images")
    val images: List<String?>,
    @SerialName("is_it_pre_leased_pre_rented")
    val is_it_pre_leased_pre_rented: String?,
    @SerialName("land_categorie_id")
    val land_categorie_id: Int?,
    @SerialName("landCategoryText")
    val landCategoryText: String?,
    @SerialName("land_type_id")
    val land_type_id: Int?,
    @SerialName("landTypeText")
    val landTypeText: String?,
    @SerialName("latitude")
    val latitude: String?,
    @SerialName("lifts")
    val lifts: String?,
    @SerialName("locality")
    val locality: String?,
    @SerialName("longitude")
    val longitude: String?,
    @SerialName("max_of_seats")
    val max_of_seats: String?,
    @SerialName("min_of_seats")
    val min_of_seats: String?,
    @SerialName("no_of_balconies")
    val no_of_balconies: String?,
    @SerialName("no_of_bathrooms")
    val no_of_bathrooms: String?,
    @SerialName("no_of_bedrooms")
    val no_of_bedrooms: String?,
    @SerialName("no_of_cabins")
    val no_of_cabins: String?,
    @SerialName("no_of_meeting_rooms")
    val no_of_meeting_rooms: String?,
    @SerialName("no_of_open_sides")
    val no_of_open_sides: String?,
    @SerialName("no_of_staircases")
    val no_of_staircases: String?,
    @SerialName("noc_certified")
    val noc_certified: String?,
    @SerialName("occupancy_certificate")
    val occupancy_certificate: String?,
    @SerialName("office_previously_used_for")
    val office_previously_used_for: String?,
    @SerialName("other_rooms")
    val other_rooms: String?,
    @SerialName("oxygen_duct")
    val oxygen_duct: String?,
    @SerialName("pantry")
    val pantry: String?,
    @SerialName("pantry_size")
    val pantry_size: String?,
    @SerialName("pantry_size_unit")
    val pantry_size_unit: String?,
    @SerialName("parking_available")
    val parking_available: String?,
    @SerialName("pincode")
    val pincode: String?,
    @SerialName("price")
    val price: String?,
    @SerialName("price_negotiable")
    val price_negotiable: String?,
    @SerialName("property_area")
    val property_area: String?,
    @SerialName("property_area_unit")
    val property_area_unit: String?,
    @SerialName("property_facing")
    val property_facing: String?,
    @SerialName("property_floor_no")
    val property_floor_no: String?,
    @SerialName("property_highlights")
    val property_highlights: String?,
    @SerialName("property_name")
    val property_name: String?,
    @SerialName("property_ownership")
    val property_ownership: String?,
    @SerialName("reception_area")
    val reception_area: String?,
    @SerialName("state")
    val state: String?,
    @SerialName("suitable_business_type")
    val suitable_business_type: String?,
    @SerialName("super_built_up_area")
    val super_built_up_area: String?,
    @SerialName("super_built_up_area_unit")
    val super_built_up_area_unit: String?,
    @SerialName("thumbnail")
    val thumbnail: String?,
    @SerialName("total_floor")
    val total_floor: String?,
    @SerialName("U_ID")
    val U_ID: Int?,
    @SerialName("ups")
    val ups: String?,
    @SerialName("user_post_id")
    val user_post_id: Int?,
    @SerialName("user_type")
    val user_type: String?,
    @SerialName("video")
    val video: String?,
    @SerialName("washroom_details")
    val washroom_details: String?,
    @SerialName("which_local_authority")
    val which_local_authority: String?
)
*/




@Serializable
data class New_Draft_Flow_Data(
    @SerialName("account_status")
    val account_status: Int?,
    @SerialName("pincode")
    val pincode: String? = null,
    @SerialName("draft")
    val draft: String? = null,
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
    @SerialName("facade_height_unit")
    val facade_height_unit: String,
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
    @SerialName("land_categorie_id")
    val land_categorie_id: Int,
    @SerialName("land_type_id")
    val land_type_id: Int,
    @SerialName("latitude")
    val latitude: String,
    @SerialName("lifts")
    val lifts: String,
    @SerialName("does_local_authority")
    val does_local_authority: String,
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
    @SerialName("parking_available")
    val parking_available: String,
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
    @SerialName("user_id")
    val user_id: Int,
    @SerialName("is_report")
    val is_report: Int,
    @SerialName("landCategoryText")
    val landCategoryText: String?,
    @SerialName("landTypeText")
    val landTypeText: String?,
    @SerialName("user_type")
    val user_type: String,
    @SerialName("washroom_details")
    val washroom_details: String,
    @SerialName("is_sold")
    val is_sold: Int,

    @SerialName("U_ID")
    val U_ID: Int,


    ///
    @SerialName("agreement_type")
    val agreement_type: String,
    @SerialName("status")
    val status: String,
    @SerialName("area_length_unit")
    val area_length_unit: String,
    @SerialName("area_width_unit")
    val area_width_unit: String,
    @SerialName("availability_from")
    val availability_from: String,

    @SerialName("built_up_area_unit")
    val built_up_area_unit: String,
    @SerialName("carpet_area_unit")
    val carpet_area_unit: String,
    @SerialName("deposit_amount_month_of_rents")
    val deposit_amount_month_of_rents: String,
    @SerialName("deposit_amount_month_of_rents_type")
    val deposit_amount_month_of_rents_type: String,

    @SerialName("duration_of_agreement")
    val duration_of_agreement: String,
    @SerialName("duration_of_agreement_type")
    val duration_of_agreement_type: String,
    @SerialName("facade_width_unit")
    val facade_width_unit: String,

    @SerialName("food_preferences")
    val food_preferences: String,



    @SerialName("is_this_property_for_rent_or_lease")
    val is_this_property_for_rent_or_lease: String,

    @SerialName("lease_amount")
    val lease_amount: String,
    @SerialName("lease_duration_in_years")
    val lease_duration_in_years: String,
    @SerialName("lease_negotiable")
    val lease_negotiable: String,

    @SerialName("lock_in_period")
    val lock_in_period: String,
    @SerialName("lock_in_period_type")
    val lock_in_period_type: String,
    @SerialName("map_config")
    val map_config: String,

    @SerialName("notice_period")
    val notice_period: String,
    @SerialName("pantry_size_unit")
    val pantry_size_unit: String,
    @SerialName("pets_allowed")
    val pets_allowed: String,
    @SerialName("post_type")
    val post_type: String,
    @SerialName("preferred_tenants")
    val preferred_tenants: String,
    @SerialName("property_area_unit")
    val property_area_unit: String,
    @SerialName("rent")
    val rent: String,
    @SerialName("rent_floor_no")
    val rent_floor_no: String,
    @SerialName("rent_negotiable")
    val rent_negotiable: String,

    @SerialName("super_built_up_area_unit")
    val super_built_up_area_unit: String,
    @SerialName("total_deposit")
    val total_deposit: String,

    @SerialName("images")
    val images: List<Image>,
    @SerialName("video")
    val video: List<Video>,
)




