package com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.NewDraftFlow


import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentoFormPreview.FormPreviewRento
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class New_Draft_Flow(
    @SerialName("data")
    val `data`: List<FormPreviewRento>,
//    val `data`: List<New_Draft_Flow_Data>,
    @SerialName("result")
    val result: String
)