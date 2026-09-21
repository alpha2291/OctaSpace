package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Get_Reply_Comments_Data(
    @SerialName("author")
    val author: Int,
    @SerialName("comment")
    val comment: String,
    @SerialName("comment_id")
    val comment_id: Int,
    @SerialName("created_at")
    val created_at: String,
    @SerialName("mention_username")
    val mention_username: String,
    @SerialName("is_liked")
    val is_liked: Int,
    @SerialName("like_count")
    val like_count: Int,
    @SerialName("parent_comment_id")
    val parent_comment_id: Int,
    @SerialName("is_report")
    val is_report: Int,
    @SerialName("profile_image")
    val profile_image: String,
    @SerialName("user_id")
    val user_id: Int,
    @SerialName("username")
    val username: String
)
