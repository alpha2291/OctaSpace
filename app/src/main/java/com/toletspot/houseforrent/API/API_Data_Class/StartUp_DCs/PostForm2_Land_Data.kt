package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostForm2_Land_Data(
    @SerialName("land_categorie_id")
    val land_categorie_id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("on_Selected")
    val on_Selected : Boolean
)