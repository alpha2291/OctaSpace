package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.SearchFilterSort

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Search_Filter_Sort_Fields(
    @SerialName("data")
    val `data`: List<Search_Filter_Sort_Fields_Data>,
    @SerialName("error")
    val error: String,
    @SerialName("result")
    val result: String
)
