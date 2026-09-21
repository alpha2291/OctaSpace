package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs


import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.RentalApp.AreaDimension
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.RentalApp.FloorDetail
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.RentalApp.Step5
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.RentalApp.Step6
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostProperty_Stepfour_Residential_Data(
//    @SerialName("Area_Dimensions")
//    val area_dimensions: List<AreaDimension>,
//    @SerialName("availability_status")
//    val availability_status: List<String>,
//    @SerialName("Built_Up_Area")
//    val built_up_area: String,
//    @SerialName("Carpet_Area")
//    val Carpet_area: String,
//    @SerialName("Floor_Details")
//    val floor_details: List<FloorDetail>,
//    @SerialName("step_5")
//    val step_5: List<Optional>,
//    @SerialName("step_6")
//    val step_6: List<Optional>,
//    @SerialName("property_area")
//    val property_area: String,
//    @SerialName("Property_Facing")
//    val property_facing: List<String>,
//    @SerialName("Property_Name")
//    val property_name: String,
//    @SerialName("property_ownership")
//    val property_ownership: List<String>,
//    @SerialName("Select_Floor_Plane")
//    val select_floor_plane: List<String>,
//    @SerialName("Preferred_Tenants")
//    val Preferred_Tenants: List<String>,
//    @SerialName("Super_Built_Up_Area")
//    val super_built_up_area: String,
//    @SerialName("Available_From")
//    val Available_From: String,

    @SerialName("Area_Dimensions")
    val Area_Dimensions: List<AreaDimension>,
    @SerialName("Available_From")
    val Available_From: String,
    @SerialName("Built_Up_Area")
    val Built_Up_Area: String,
    @SerialName("Carpet_Area")
    val Carpet_Area: String,
    @SerialName("Floor_Details")
    val Floor_Details: List<FloorDetail>,
    @SerialName("Preferred_Tenants")
    val Preferred_Tenants: List<String>,
    @SerialName("Property_Facing")
    val Property_Facing: List<String>,
    @SerialName("Property_Name")
    val Property_Name: String,
    @SerialName("Select_Floor_Plane")
    val Select_Floor_Plane: List<String>,
    @SerialName("step_5")
    val step_5: List<Step5>,
    @SerialName("step_6")
    val step_6: List<Step6>,
    @SerialName("Super_Built_Up_Area")
    val Super_Built_Up_Area: String
)

