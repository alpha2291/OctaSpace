package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Post_Property_Stepfour_Residential(
    @SerialName("data")
    val `data`: List<PostProperty_Stepfour_Residential_Data>,
    @SerialName("error")
    val error: String,
    @SerialName("result")
    val result: String
)
