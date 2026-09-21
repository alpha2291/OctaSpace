package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Get_User_Profile(
    @SerialName("data")
    val `data`: List<Get_User_Profile_Data>,
    @SerialName("result")
    val result: String
)