package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.UserNameUpdate


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserName_Update_Data(
    @SerialName("user_id")
    val user_id: String,
    @SerialName("username")
    val username: String
)