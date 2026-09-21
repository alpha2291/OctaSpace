package com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentoPriceForm


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RentoPriceForm5(
    @SerialName("data")
    val `data`: List<List<Data>>,
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: String
)