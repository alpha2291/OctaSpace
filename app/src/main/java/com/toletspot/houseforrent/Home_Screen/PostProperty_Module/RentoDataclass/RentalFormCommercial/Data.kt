package com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentalFormCommercial

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Post_Property_Stepfour_Commercial_Data(
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
    @SerialName("Property_Area")
    val Property_Area: String,
    @SerialName("Property_Facing")
    val Property_Facing: List<String>,
    @SerialName("Property_Name")
    val Property_Name: String,
    @SerialName("Shop_Facade")
    val Shop_Facade: List<ShopFacade>,
    @SerialName("step_5")
    val step_5: List<Step5>,
    @SerialName("step_6")
    val step_6: List<Step6>,
    @SerialName("Super_Built_Up_Area")
    val Super_Built_Up_Area: String
)
