package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Particular_User_Interest


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User_Interests_Particular(
    @SerialName("data")
    val `data`: List<User_Interests_Particular_Data>,
    @SerialName("result")
    val result: String
)