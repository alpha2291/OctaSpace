package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs

import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.RentalApp.AreaDimension
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.RentalApp.FloorDetail
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.RentalApp.Step5
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.RentalApp.Step6
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostProperty_Stepfour_Residential_Data(

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
