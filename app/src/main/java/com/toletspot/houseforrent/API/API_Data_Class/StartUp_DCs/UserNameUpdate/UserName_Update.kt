package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.UserNameUpdate

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserName_Update(
    @SerialName("data")
    val `data`: List<UserName_Update_Data>,
    @SerialName("error")
    val error: String,
    @SerialName("result")
    val result: String
)
