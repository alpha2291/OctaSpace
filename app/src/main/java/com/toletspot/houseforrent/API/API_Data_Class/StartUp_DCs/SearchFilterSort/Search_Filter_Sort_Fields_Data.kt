package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.SearchFilterSort


import com.toletspot.houseforrent.Home_Screen.Search_Module.RentoSortFilterDC.AvailableFrom
import com.toletspot.houseforrent.Home_Screen.Search_Module.RentoSortFilterDC.PostedDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/*
@Serializable
data class Search_Filter_Sort_Fields_Data(

    @SerialName("amenities")
    val amenities: List<String> = emptyList(),



    @SerialName("budget")
    val budget: String? = "1",

    // NOTE: API actually returns "floor_plan", not "floor_plane"
    @SerialName("floor_plan")
    val floor_plan: List<String> = emptyList(),

    @SerialName("floor_preferences")
    val floor_preferences: List<FloorPreference> = emptyList(),

    @SerialName("furnishing_status")
    val furnishing_status: List<String> = emptyList(),

    @SerialName("no_of_open_sides")
    val no_of_open_sides: List<Int>? = emptyList(),

    @SerialName("ownership")
    val ownership: List<String> = emptyList(),

    @SerialName("parking_available")
    val parking_available: List<String> = emptyList(),

    @SerialName("posted_by")
    val posted_by: List<PostedBy> = emptyList(),

    @SerialName("property_area")
    val property_area: String? = "1",

    @SerialName("property_area_unit")
    val property_area_unit: String? = null,

    @SerialName("property_facing")
    val property_facing: List<String> = emptyList(),

    @SerialName("property_highlights")
    val property_highlights: List<String> = emptyList(),

    @SerialName("property_type")
    val property_type: List<PropertyType> = emptyList(),

    @SerialName("approved")
    val approved: List<String> = emptyList(),

    @SerialName("business_type")
    val business_type: List<String> = emptyList(),

    @SerialName("sort_by")
    val sort_by: List<String> = emptyList(),


    ////
    @SerialName("rent_type")
    val rent_type: List<String> = emptyList(),


    @SerialName("posted_date")
    val posted_date: List<PostedDate>,

    @SerialName("available_from")
    val available_from: List<AvailableFrom>,


    @SerialName("available_for")
    val available_for: List<String>,

    @SerialName("bedrooms")
    val bedrooms: List<String>,


    @SerialName("food_preferences")
    val food_preferences: List<String>,


    @SerialName("pets_allowed")
    val pets_allowed: List<String>,


    @SerialName("with_photos")
    val with_photos: String,

    @SerialName("agreement")
    val agreement: List<String>,
)
*/


@Serializable
data class Search_Filter_Sort_Fields_Data(

    @SerialName("sort_by")
    val sort_by: List<String> = emptyList(),

    @SerialName("property_type")
    val property_type: List<PropertyType> = emptyList(),

    @SerialName("property_area")
    val property_area: String? = null,

    @SerialName("property_area_unit")
    val property_area_unit: String? = null,

    @SerialName("rent_type")
    val rent_type: List<String> = emptyList(),

    @SerialName("budget")
    val budget: String? = null,

    @SerialName("posted_by")
    val posted_by: List<PostedBy> = emptyList(),

    @SerialName("posted_date")
    val posted_date: List<PostedDate> = emptyList(),

    @SerialName("available_for")
    val available_for: List<String> = emptyList(),

    @SerialName("floor_plan")
    val floor_plan: List<String> = emptyList(),

    @SerialName("floor_preferences")
    val floor_preferences: List<FloorPreference> = emptyList(),

    @SerialName("available_from")
    val available_from: List<AvailableFrom> = emptyList(),

    @SerialName("property_facing")
    val property_facing: List<String> = emptyList(),

    @SerialName("food_preferences")
    val food_preferences: List<String> = emptyList(),

    @SerialName("pets_allowed")
    val pets_allowed: List<String> = emptyList(),

    @SerialName("furnishing_status")
    val furnishing_status: List<String> = emptyList(),

    @SerialName("agreement")
    val agreement: List<String> = emptyList(),

    @SerialName("with_photos")
    val with_photos: String? = null,

    @SerialName("parking_available")
    val parking_available: List<String> = emptyList(),

    @SerialName("amenities")
    val amenities: List<String> = emptyList(),

    @SerialName("property_highlights")
    val property_highlights: List<String> = emptyList(),

    @SerialName("bedrooms")
    val bedrooms: List<String> = emptyList(),

    @SerialName("open_sides")
    val open_sides: List<String> = emptyList(),

    @SerialName("approved")
    val approved: List<String> = emptyList()
)





