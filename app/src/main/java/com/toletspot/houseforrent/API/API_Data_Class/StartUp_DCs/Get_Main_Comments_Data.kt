package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Get_Main_Comments_Data(
    @SerialName("author")
    val author: Int,
    @SerialName("comment")
    val comment: String,
    @SerialName("comment_id")
    val comment_id: Int,
    @SerialName("created_at")
    val created_at: String,
    @SerialName("is_liked")
    val is_liked: Int,
    @SerialName("last_reply")
    val last_reply: List<LastReply>,
    @SerialName("like_count")
    val like_count: Int,
    @SerialName("total_reply")
    val total_reply: Int,
    @SerialName("is_report")
    val is_report: Int,
    @SerialName("profile_image")
    val profile_image: String,
    @SerialName("user_id")
    val user_id: Int,
    @SerialName("username")
    val username: String
)
