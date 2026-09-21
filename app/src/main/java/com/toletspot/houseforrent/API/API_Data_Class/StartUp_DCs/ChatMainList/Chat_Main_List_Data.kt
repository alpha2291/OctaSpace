package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.ChatMainList

import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.PostUser
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Chat_Main_List_Data(
    @SerialName("chat_type")
    val chat_type: Int,
    @SerialName("delete_post")
    val delete_post: Int,
    @SerialName("enquire_details")
    val enquire_details: EnquireDetails,
    @SerialName("user_details")
    val user_details: List<UserDetail>,
    @SerialName("video_model")

    val video_model: PostUser
)
