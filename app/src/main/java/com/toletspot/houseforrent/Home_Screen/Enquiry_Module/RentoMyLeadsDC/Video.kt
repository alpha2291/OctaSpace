package com.toletspot.houseforrent.Home_Screen.Enquiry_Module.RentoMyLeadsDC

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize

@Serializable
data class Video(
    @SerialName("heading")
    val heading: String,
    @SerialName("url")
    val url: String
) : Parcelable
