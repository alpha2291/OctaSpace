package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Account_Settings_Contact(
    @SerialName("data")
    val `data`: List<AC_Contact_Data>,
    @SerialName("result")
    val result: String,
)
