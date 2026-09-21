package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.PopularSellersSearch


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Popular_Sellers_Search_Data(
    @SerialName("followers")
    val followers: Int,
    @SerialName("following")
    val following: Int,
    @SerialName("name")
    val name: String,
    @SerialName("profile_image")
    val profile_image: String,
    @SerialName("total_posts")
    val total_posts: Int,
    @SerialName("user_id")
    val user_id: Int,
    @SerialName("username")
    val username: String
)