package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Post_Property_Stepfour_Agriculture_Data(
    @SerialName("area_dimensions")
    val area_dimensions: List<AreaDimensionXX>,
    @SerialName("availability_status")
    val availability_status: List<String>,
    @SerialName("built_up_area")
    val built_up_area: String,
    @SerialName("Carpet_area")
    val Carpet_area: String,
    @SerialName("floor_details")
    val floor_details: List<FloorDetailXX>,
    @SerialName("optional")
    val optional: List<OptionalXX>,
    @SerialName("property_area")
    val property_area: String,
    @SerialName("property_facing")
    val property_facing: List<String>,
    @SerialName("property_name")
    val property_name: String,
    @SerialName("property_ownership")
    val property_ownership: List<String>,
    @SerialName("select_floor_plane")
    val select_floor_plane: List<String>,
    @SerialName("super_built_up_area")
    val super_built_up_area: String
)
