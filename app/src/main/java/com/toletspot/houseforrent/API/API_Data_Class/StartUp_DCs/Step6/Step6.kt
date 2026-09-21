package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Step6


import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Reels_Data
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Step6(
    @SerialName("message")
    val message: String,
    @SerialName("post_type")
    val postType: String,
    @SerialName("result")
    val result: String,
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("video")
    val video: String,
    @SerialName("video_model")
    val video_model: List<Get_Reels_Data>
)