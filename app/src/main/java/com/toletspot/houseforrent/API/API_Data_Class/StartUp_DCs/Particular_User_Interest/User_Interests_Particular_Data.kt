package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Particular_User_Interest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User_Interests_Particular_Data(
    @SerialName("user_id")
    val user_id: String,
    @SerialName("user_interest")
    val user_interest: String,
    @SerialName("is_Selected")
    val is_Selected : Boolean = false
)
