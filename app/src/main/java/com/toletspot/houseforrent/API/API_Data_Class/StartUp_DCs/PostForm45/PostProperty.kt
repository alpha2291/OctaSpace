package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.PostForm45


import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentoMediaDC.Image
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentoMediaDC.Video
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*
@Serializable
data class PostProperty(
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
    @SerialName("availability_status")
    val availability_status: String,
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
    @SerialName("does_local_authority")
    val does_local_authority: String,
    @SerialName("draft")
    val draft: Int,
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
    @SerialName("image_urls")
    val image_urls: List<String>,
    @SerialName("is_it_pre_leased_pre_rented")
    val is_it_pre_leased_pre_rented: String,
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
    @SerialName("office_previously_used_for")
    val office_previously_used_for: String,
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
    @SerialName("pincode")
    val pincode: String,
    @SerialName("price")
    val price: String,
    @SerialName("price_negotiable")
    val price_negotiable: String,
    @SerialName("property_area")
    val property_area: String,
    @SerialName("property_area_unit")
    val property_area_unit: String,
    @SerialName("property_facing")
    val property_facing: String,
    @SerialName("property_floor_no")
    val property_floor_no: String,
    @SerialName("property_highlights")
    val property_highlights: String,
    @SerialName("property_name")
    val property_name: String,
    @SerialName("property_ownership")
    val property_ownership: String,
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
    @SerialName("ups")
    val ups: String,
    @SerialName("user_post_id")
    val user_post_id: Int,
    @SerialName("user_type")
    val user_type: String,
    @SerialName("video")
    val video: String,
    @SerialName("washroom_details")
    val washroom_details: String,
    @SerialName("which_local_authority")
    val which_local_authority: String
)*/



@Serializable
data class PostProperty(
    @SerialName("address")
    val address: String,
    @SerialName("agreement_type")
    val agreementType: String,
    @SerialName("amenities")
    val amenities: String,
    @SerialName("area_length")
    val areaLength: String,
    @SerialName("area_length_unit")
    val areaLengthUnit: String,
    @SerialName("area_width")
    val areaWidth: String,
    @SerialName("area_width_unit")
    val areaWidthUnit: String,
    @SerialName("availability_from")
    val availabilityFrom: String,
    @SerialName("bhk_type")
    val bhkType: String,
    @SerialName("boundary_wall")
    val boundaryWall: String,
    @SerialName("built_up_area")
    val builtUpArea: String,
    @SerialName("built_up_area_unit")
    val builtUpAreaUnit: String,
    @SerialName("carpet_area")
    val carpetArea: String,
    @SerialName("carpet_area_unit")
    val carpetAreaUnit: String,
    @SerialName("central_ac")
    val centralAc: String,
    @SerialName("city")
    val city: String,
    @SerialName("conference_room")
    val conferenceRoom: String,
    @SerialName("country")
    val country: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("deposit_amount_month_of_rents")
    val depositAmountMonthOfRents: String,
    @SerialName("deposit_amount_month_of_rents_type")
    val depositAmountMonthOfRentsType: String,
    @SerialName("does_local_authority")
    val doesLocalAuthority: String,
    @SerialName("draft")
    val draft: Int,
    @SerialName("duration_of_agreement")
    val durationOfAgreement: String,
    @SerialName("duration_of_agreement_type")
    val durationOfAgreementType: String,
    @SerialName("facade_height")
    val facadeHeight: String,
    @SerialName("facade_height_unit")
    val facadeHeightUnit: String,
    @SerialName("facade_width")
    val facadeWidth: String,
    @SerialName("facade_width_unit")
    val facadeWidthUnit: String,
    @SerialName("fire_safety_measures")
    val fireSafetyMeasures: String,
    @SerialName("food_preferences")
    val foodPreferences: String,
    @SerialName("furnishing_status")
    val furnishingStatus: String,
    @SerialName("images")
    val images: List<Image>,
    @SerialName("is_report")
    val isReport: String,
    @SerialName("is_this_property_for_rent_or_lease")
    val isThisPropertyForRentOrLease: String,
    @SerialName("land_categorie_id")
    val landCategorieId: Int,
    @SerialName("landCategoryText")
    val landCategoryText: String,
    @SerialName("land_type_id")
    val landTypeId: Int,
    @SerialName("landTypeText")
    val landTypeText: String,
    @SerialName("latitude")
    val latitude: String,
    @SerialName("lease_amount")
    val leaseAmount: String,
    @SerialName("lease_duration_in_years")
    val leaseDurationInYears: String,
    @SerialName("lease_negotiable")
    val leaseNegotiable: String,
    @SerialName("lifts")
    val lifts: String,
    @SerialName("locality")
    val locality: String,
    @SerialName("lock_in_period")
    val lockInPeriod: String,
    @SerialName("lock_in_period_type")
    val lockInPeriodType: String,
    @SerialName("longitude")
    val longitude: String,
    @SerialName("map_config")
    val mapConfig: String,
    @SerialName("max_of_seats")
    val maxOfSeats: String,
    @SerialName("min_of_seats")
    val minOfSeats: String,
    @SerialName("no_of_Balconies")
    val noOfBalconies: String,
    @SerialName("no_of_Bathrooms")
    val noOfBathrooms: String,
    @SerialName("no_of_bedrooms")
    val noOfBedrooms: String,
    @SerialName("no_of_cabins")
    val noOfCabins: String,
    @SerialName("no_of_meeting_rooms")
    val noOfMeetingRooms: String,
    @SerialName("no_of_open_sides")
    val noOfOpenSides: String,
    @SerialName("no_of_Staircases")
    val noOfStaircases: String,
    @SerialName("noc_certified")
    val nocCertified: String,
    @SerialName("notice_period")
    val noticePeriod: String,
    @SerialName("occupancy_certificate")
    val occupancyCertificate: String,
    @SerialName("other_rooms")
    val otherRooms: String,
    @SerialName("oxygen_duct")
    val oxygenDuct: String,
    @SerialName("pantry")
    val pantry: String,
    @SerialName("pantry_size")
    val pantrySize: String,
    @SerialName("pantry_size_unit")
    val pantrySizeUnit: String,
    @SerialName("parking_available")
    val parkingAvailable: String,
    @SerialName("pets_allowed")
    val petsAllowed: String,
    @SerialName("pincode")
    val pincode: String,
    @SerialName("post_type")
    val postType: String,
    @SerialName("preferred_tenants")
    val preferredTenants: String,
    @SerialName("property_area")
    val propertyArea: String,
    @SerialName("property_area_unit")
    val propertyAreaUnit: String,
    @SerialName("property_facing")
    val propertyFacing: String,
    @SerialName("property_highlights")
    val propertyHighlights: String,
    @SerialName("property_name")
    val propertyName: String,
    @SerialName("reception_area")
    val receptionArea: String,
    @SerialName("rent")
    val rent: String,
    @SerialName("rent_floor_no")
    val rentFloorNo: String,
    @SerialName("rent_negotiable")
    val rentNegotiable: String,
    @SerialName("state")
    val state: String,
    @SerialName("suitable_business_type")
    val suitableBusinessType: String,
    @SerialName("super_built_up_area")
    val superBuiltUpArea: String,
    @SerialName("super_built_up_area_unit")
    val superBuiltUpAreaUnit: String,
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("total_deposit")
    val totalDeposit: String,
    @SerialName("total_floor")
    val totalFloor: String,
    @SerialName("ups")
    val ups: String,
    @SerialName("user_post_id")
    val userPostId: Int,
    @SerialName("user_type")
    val userType: String,
    @SerialName("video")
    val video: List<Video>,
    @SerialName("washroom_details")
    val washroomDetails: String
)