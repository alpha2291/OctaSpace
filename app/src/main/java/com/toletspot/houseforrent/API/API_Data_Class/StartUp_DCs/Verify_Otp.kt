package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Verify_Otp(
    @SerialName("data")
    val `data`: List<Verify_Otp_Data>,
    @SerialName("error")
    val error: String,
    @SerialName("result")
    val result: String
)
