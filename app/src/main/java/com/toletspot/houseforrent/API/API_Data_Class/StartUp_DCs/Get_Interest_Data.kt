package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Get_Interest_Data(
    @SerialName("image")
    val image: String,
    @SerialName("land_categorie_id")
    val land_categorie_id: Int,
    @SerialName("land_type_id")
    val land_type_id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("is_Selected")
    val is_Selected : Boolean = false
)