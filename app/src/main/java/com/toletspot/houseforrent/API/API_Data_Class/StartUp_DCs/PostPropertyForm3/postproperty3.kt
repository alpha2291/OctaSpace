package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.PostPropertyForm3


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class postproperty3(
    @SerialName("data")
    val `data`: List<List<Data>>,
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: String
)