package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Account_Activate_Deactivate(
    @SerialName("data")
    val `data`: List<String>,
    @SerialName("error")
    val error: String,
    @SerialName("result")
    val result: String
)
