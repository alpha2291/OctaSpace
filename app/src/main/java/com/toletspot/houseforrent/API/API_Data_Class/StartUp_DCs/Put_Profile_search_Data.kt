package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//
//"user_id": 9,
//"username": "user009",
//"name": "nagappan",
//"phone_num": "7708404774",
//"whatsapp_num": "",
//"email": "",
//"profile_image": "",
//"posts": 0,
//"following": 0,
//"followers": 0,
//"isBlocked": 0,
//"is_report": 0,
//"others_page": 1,
//"im_followed": 0,
//"is_followed": 0,
//"thumbnails": []
@Serializable
data class Put_Profile_search_Data(
    @SerialName("email")
    val email: String,
    @SerialName("followers")
    val followers: Int,
    @SerialName("following")
    val following: Int,
    @SerialName("im_followed")
    val im_followed: Int,
    @SerialName("isBlocked")
    val isBlocked: Int,
    @SerialName("is_followed")
    val is_followed: Int,
    @SerialName("is_report")
    val is_report: Int,
    @SerialName("name")
    val name: String,
    @SerialName("others_page")
    val others_page: Int,
    @SerialName("phone_num")
    val phone_num: String,
    @SerialName("posts")
    val posts: Int,
    @SerialName("profile_image")
    val profile_image: String,
    @SerialName("user_id")
    val user_id: Int,
    @SerialName("username")
    val username: String,
    @SerialName("whatsapp_num")
    val whatsapp_num: String,
    @SerialName("thumbnails")
    val thumbnails: List<String>
)