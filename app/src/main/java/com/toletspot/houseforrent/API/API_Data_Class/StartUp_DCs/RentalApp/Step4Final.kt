package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.RentalApp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Step4Final(
    @SerialName("data")
    val `data`: List<Data>,
    @SerialName("error")
    val error: String,
    @SerialName("result")
    val result: String
)
