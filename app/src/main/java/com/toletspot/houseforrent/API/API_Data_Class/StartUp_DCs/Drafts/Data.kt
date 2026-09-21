package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Drafts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("draft")
    val draft: Int,
    @SerialName("post_property")
    val post_property: PostProperty
)
