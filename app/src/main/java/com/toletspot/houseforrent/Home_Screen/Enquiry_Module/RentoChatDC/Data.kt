package com.toletspot.houseforrent.Home_Screen.Enquiry_Module.RentoChatDC

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("chat_type")
    val chatType: Int,
    @SerialName("delete_post")
    val deletePost: Int,
    @SerialName("enquire_details")
    val enquireDetails: EnquireDetails,
    @SerialName("user_details")
    val userDetails: List<UserDetail>,
    @SerialName("video_model")
    val videoModel: VideoModel
)
