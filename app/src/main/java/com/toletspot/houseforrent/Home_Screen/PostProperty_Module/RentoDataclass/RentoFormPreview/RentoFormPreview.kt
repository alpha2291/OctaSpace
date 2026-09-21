package com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentoFormPreview


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RentoFormPreview(
    @SerialName("data")
    val `data`: List<FormPreviewRento>,
    @SerialName("result")
    val result: String
)