package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.RentalApp


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("Area_Dimensions")
    val areaDimensions: List<AreaDimension>,
    @SerialName("Available_From")
    val availableFrom: String,
    @SerialName("Built_Up_Area")
    val builtUpArea: String,
    @SerialName("Carpet_Area")
    val carpetArea: String,
    @SerialName("Floor_Details")
    val floorDetails: List<FloorDetail>,
    @SerialName("Preferred_Tenants")
    val preferredTenants: List<String>,
    @SerialName("Property_Facing")
    val propertyFacing: List<String>,
    @SerialName("Property_Name")
    val propertyName: String,
    @SerialName("Select_Floor_Plane")
    val selectFloorPlane: List<String>,
    @SerialName("step_5")
    val step5: List<Step5>,
    @SerialName("step_6")
    val step6: List<Step6>,
    @SerialName("Super_Built_Up_Area")
    val superBuiltUpArea: String
)