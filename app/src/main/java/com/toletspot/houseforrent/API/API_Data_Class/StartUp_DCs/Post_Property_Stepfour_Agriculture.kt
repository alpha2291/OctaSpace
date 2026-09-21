package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Post_Property_Stepfour_Agriculture(
    @SerialName("data")
    val `data`: List<Post_Property_Stepfour_Agriculture_Data>,
    @SerialName("error")
    val error: String,
    @SerialName("result")
    val result: String
)
