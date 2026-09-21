package com.toletspot.houseforrent.Home_Screen.PostProperty_Module.CommonFormDataClass

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CommonPropertyResponse(
    @SerialName("result") val result: String? = null,
    @SerialName("error") val error: String? = null,
    @SerialName("data") val data: List<PostFormCommonPropertyData>? = null
)

@Serializable
data class PostFormCommonPropertyData(
    @SerialName("Property_Name") val Property_Name: String? = null,
    @SerialName("Property_Area") val Property_Area: String? = null,
    @SerialName("Carpet_Area") val Carpet_Area: String? = null,
    @SerialName("Built_Up_Area") val Built_Up_Area: String? = null,
    @SerialName("Super_Built_Up_Area") val Super_Built_Up_Area: String? = null,

    @SerialName("Select_Floor_Plane") val Select_Floor_Plane: List<String>? = null,
    @SerialName("Preferred_Tenants") val Preferred_Tenants: List<String>? = null,

    @SerialName("Area_Dimensions") val Area_Dimensions: List<AreaDimension>? = null,
    @SerialName("Property_Facing") val Property_Facing: List<String>? = null,
    @SerialName("Floor_Details") val Floor_Details: List<FloorDetail>? = null,
    @SerialName("Shop_Facade") val Shop_Facade: List<ShopFacade>? = null,

    @SerialName("Available_From") val Available_From: String? = null,

    @SerialName("step_5") val step_5: List<Step5>? = null,
    @SerialName("step_6") val step_6: List<Step6>? = null
)

@Serializable
data class AreaDimension(
    @SerialName("Length") val Length: String? = null,
    @SerialName("Width") val Width: String? = null
)

@Serializable
data class FloorDetail(
    @SerialName("Total_Floors_in_Property") val Total_Floors_in_Property: String? = null,
    @SerialName("Rent_Property_Floor_No") val Rent_Property_Floor_No: String? = null
)

@Serializable
data class ShopFacade(
    @SerialName("Facade_Width") val Facade_Width: String? = null,
    @SerialName("Facade_Height") val Facade_Height: String? = null
)

@Serializable
data class Step5(
    @SerialName("Agreement_Type") val Agreement_Type: List<String>? = null,
    @SerialName("No_of_Bathrooms") val No_of_Bathrooms: List<String>? = null,
    @SerialName("No_of_Balconies") val No_of_Balconies: List<String>? = null,
    @SerialName("No_of_Bedrooms") val No_of_Bedrooms: List<String>? = null,
    @SerialName("Reception_Area") val Reception_Area: List<String>? = null,
    @SerialName("Food_Preferences") val Food_Preferences: List<String>? = null,
    @SerialName("Pets_Allowed") val Pets_Allowed: List<String>? = null,
    @SerialName("Other_Rooms") val Other_Rooms: List<String>? = null,
    @SerialName("Furnishing_Status") val Furnishing_Status: List<String>? = null,
    @SerialName("Parking_Available") val Parking_Available: List<String>? = null,
    @SerialName("Amenities") val Amenities: List<String>? = null,
    @SerialName("Property_Highlights") val Property_Highlights: List<String>? = null,

    @SerialName("No_of_Open_sides") val No_of_Open_sides: List<String>? = null,
    @SerialName("Does_your_property_authority_approved") val Does_your_property_authority_approved: List<String>? = null,

    @SerialName("Property_Condition") val Property_Condition: List<String>? = null,
    @SerialName("Washrooms") val Washrooms: List<String>? = null,
    @SerialName("No_of_Staircases") val No_of_Staircases: List<String>? = null,
    @SerialName("Conference_Room") val Conference_Room: List<String>? = null,
    @SerialName("Max_No_of_Seats") val Max_No_of_Seats: List<String>? = null,
    @SerialName("Min_No_of_Seats") val Min_No_of_Seats: List<String>? = null,
    @SerialName("No_of_Meeting_Rooms") val No_of_Meeting_Rooms: List<String>? = null,
    @SerialName("No_of_Cabins") val No_of_Cabins: List<String>? = null,

    @SerialName("Pantry_Size") val Pantry_Size: String? = null,
    @SerialName("Pantry") val Pantry: List<String>? = null,
    @SerialName("Central_AC") val Central_AC: List<String>? = null,
    @SerialName("Oxygen_Duct") val Oxygen_Duct: List<String>? = null,
    @SerialName("UPS") val UPS: List<String>? = null,
    @SerialName("Lifts") val Lifts: List<String>? = null,
    @SerialName("Fire_Safety_Measures") val Fire_Safety_Measures: List<String>? = null,

    @SerialName("Is_your_office_fire_NOC_Certified") val Is_your_office_fire_NOC_Certified: List<String>? = null,
    @SerialName("Occupancy_Certificate") val Occupancy_Certificate: List<String>? = null,
    @SerialName("Suitable_Business_Type") val Suitable_Business_Type: List<String>? = null,

    @SerialName("Is_boundary_wall_around_property") val Is_boundary_wall_around_property: List<String>? = null
)

@Serializable
data class Step6(
    @SerialName("Is_this_property_for_rent_or_Lease") val Is_this_property_for_rent_or_Lease: List<String>? = null,
    @SerialName("Rent") val Rent: List<Rent>? = null,
    @SerialName("Lease") val Lease: List<Lease>? = null
)

@Serializable
data class Lease(
    @SerialName("Lease_Amount") val Lease_Amount: String? = null,
    @SerialName("Lease_Duration_in_Years") val Lease_Duration_in_Years: List<String>? = null,
    @SerialName("Lease_Negotiable") val Lease_Negotiable: String? = null
)

@Serializable
data class Rent(
    @SerialName("Rent") val Rent: String? = null,
    @SerialName("Rent_Negotiable") val Rent_Negotiable: String? = null,
    @SerialName("Deposit_Amount_month_of_rents") val Deposit_Amount_month_of_rents: List<String>? = null,
    @SerialName("Duration_of_Agreement") val Duration_of_Agreement: List<String>? = null,
    @SerialName("Lock_in_Period") val Lock_in_Period: List<String>? = null,
    @SerialName("Notice_Period") val Notice_Period: List<String>? = null
)

