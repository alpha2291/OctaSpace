package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Post_Property_Stepfour_Put(
    @SerialName("message")
    val message: String,
    @SerialName("success")
    val success: Boolean
)