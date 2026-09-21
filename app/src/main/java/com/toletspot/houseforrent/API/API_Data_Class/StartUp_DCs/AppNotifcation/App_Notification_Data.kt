package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.AppNotifcation


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class App_Notification_Data(
    @SerialName("comment_id")
    val comment_id: Int,
    @SerialName("created_at")
    val created_at: String,
    @SerialName("enquiry_id")
    val enquiry_id: Int,
    @SerialName("im_followed")
    val im_followed: Int,
    @SerialName("is_followed")
    val is_followed: Int,
    @SerialName("message")
    val message: String,
    @SerialName("notification_type")
    val notification_type: Int,
    @SerialName("notification_id")
    val notification_id: Int,
    @SerialName("profile_image")
    val profile_image: String,
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("user_id")
    val user_id: Int,
    @SerialName("user_name")
    val user_name: String,
    @SerialName("user_post_id")
    val user_post_id: Int,
    @SerialName("id_deleted")
    val id_deleted: Int,
    @SerialName("post_deleted")
    val post_deleted: Int,
    @SerialName("is_sold")
    val is_sold: Int,
    @SerialName("reported")
    val reported: Int,

)

