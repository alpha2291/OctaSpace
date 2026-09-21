package com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentoMediaDC

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RentoMediaApiDC(
    @SerialName("image_ids")
    val image_ids: List<Int>,
    @SerialName("message")
    val message: String,
    @SerialName("post_type")
    val post_type: String,
    @SerialName("result")
    val result: String,
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("video_ids")
    val video_ids: List<Int>,
    @SerialName("video_model")
    val video_model: List<VideoModel>
)
