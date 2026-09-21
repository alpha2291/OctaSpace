package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Get_Comment_Data(
    @SerialName("comment")
    val comment: String,
    @SerialName("comment_id")
    val comment_id: Int,
    @SerialName("created_at")
    val created_at: String,
    @SerialName("like_count")
    val like_count: Int,
    @SerialName("profile_image")
    val profile_image: String,
//    @SerialName("replies")
//    val replies: List<Any>,
    @SerialName("user_id")
    val user_id: Int,
    @SerialName("username")
    val username: String
)