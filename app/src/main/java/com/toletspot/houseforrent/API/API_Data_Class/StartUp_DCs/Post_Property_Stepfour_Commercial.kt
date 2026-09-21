package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs


import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentalFormCommercial.Post_Property_Stepfour_Commercial_Data
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Post_Property_Stepfour_Commercial(
    @SerialName("data")
//    val `data`: List<Post_Property_Stepfour_Commercial_Data>,
    val `data`: List<Post_Property_Stepfour_Commercial_Data>,
    @SerialName("error")
    val error: String,
    @SerialName("result")
    val result: String
)