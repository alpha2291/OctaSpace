package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Post_Form_7(
    @SerialName("data")
    val `data`: List<Post_Form_7_Data>,
    @SerialName("error")
    val error: String,
    @SerialName("result")
    val result: String
)