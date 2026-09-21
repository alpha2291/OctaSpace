package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Post_Form2_Land_Types(
    @SerialName("data")
    val `data`: List<PostForm2_Land_Data>,
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: Int
)
