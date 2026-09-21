package com.toletspot.houseforrent.Home_Screen.PostProperty_Module

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Drafts.PostProperty
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.NewDraftFlow.New_Draft_Flow_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.PostForm2_Land_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.PostProperty_Stepfour_Residential_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Post_Form_7_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Post_Property_Stepfour_Agriculture_Data
import com.toletspot.houseforrent.AppPreferences
import com.toletspot.houseforrent.Custom_Assets.generateAndUploadVideoThumbnail
import com.toletspot.houseforrent.Custom_Assets.toast
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.CommonFormDataClass.PostFormCommonPropertyData
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.CommonFormDataClass.Step5
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.CommonFormDataClass.Step6
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentalFormCommercial.Post_Property_Stepfour_Commercial_Data
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentoFormPreview.FormPreviewRento
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentoMediaDC.Image
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentoMediaDC.Video
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentoPhotouploadheadings.RentoPhotoHeadings
import com.toletspot.houseforrent.S3Uploader
import com.toletspot.houseforrent.UI_DataClass.All_Form_Handler
import com.toletspot.houseforrent.UI_DataClass.ApiMediaItem
import com.toletspot.houseforrent.UI_DataClass.ImageNamingOptions
import com.toletspot.houseforrent.UI_DataClass.PP3_API_DC
import com.toletspot.houseforrent.UI_DataClass.RentalForm5
import com.toletspot.houseforrent.UI_DataClass.Selected_Options_Form4_DC
import com.toletspot.houseforrent.UI_DataClass.UploadPostRequest
import com.toletspot.houseforrent.UI_DataClass.UploadPropertyMedia
import com.toletspot.houseforrent.constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class PostFlow { NONE, NEW, DRAFT, REPOST, EDIT  , REQUESTMEDIA }

enum class ViewDetailsFlow { NONE , OWN , OTHERS , RENTOUT, RENEW ,  EXPIRY , ACTIVE  }


class PostProperty_ViewModel: ViewModel() {




    //// rental

    private val _postFlow = MutableStateFlow(PostFlow.NONE)
    val postFlow = _postFlow.asStateFlow()

    fun setPostFlow(flow: PostFlow) { _postFlow.value = flow }


    //// rental view details flow

    private val _viewDetailsFlow = MutableStateFlow(ViewDetailsFlow.NONE)
    val viewDetailsFlow = _viewDetailsFlow.asStateFlow()

    fun setViewDetailsFlow(flow: ViewDetailsFlow) { _viewDetailsFlow.value = flow }


    fun reset_ViewDetailsFlow(){
        _viewDetailsFlow.value = ViewDetailsFlow.NONE
    }


    // form 1 error state
    private val _form1Error = MutableStateFlow(false)
    val form1Error = _form1Error.asStateFlow()

    fun set1formError(flow: Boolean) { _form1Error.value = flow }


    // form 2 error state

    private val _form2Error = MutableStateFlow(false)
    val form2Error = _form2Error.asStateFlow()

    fun set2formError(flow: Boolean) { _form2Error.value = flow }

    /// form 3 show map var

    private val _form3ShowMap = MutableStateFlow(false)
    val form3ShowMap = _form3ShowMap.asStateFlow()

    fun set3formShowMap(flow: Boolean) { _form3ShowMap.value = flow }

    ////////////////////////////////////////////////////////////////////////////


    private var _imageNamingOptionsList = MutableStateFlow(listOf(
        ImageNamingOptions(
            id = 0,
            title = "Kitchen",
            isCover = false
        ),
        ImageNamingOptions(
            id = 0,
            title = "Living Room",
            isCover = false
        ),ImageNamingOptions(
            id = 0,
            title = "Bedroom",
            isCover = false
        ),ImageNamingOptions(
            id = 0,
            title = "Garage",
            isCover = false
        ),ImageNamingOptions(
            id = 0,
            title = "Home Office",
            isCover = false
        ),ImageNamingOptions(
            id = 0,
            title = "Basement",
            isCover = false
        ),ImageNamingOptions(
            id = 0,
            title = "Laundry Room",
            isCover = false
        ),ImageNamingOptions(
            id = 0,
            title = "Exterior",
            isCover = false
        ),ImageNamingOptions(
            id = 0,
            title = "Playroom",
            isCover = false
        ),ImageNamingOptions(
            id = 0,
            title = "Hallway",
            isCover = false
        ),ImageNamingOptions(
            id = 0,
            title = "Porch",
            isCover = false
        ),ImageNamingOptions(
            id = 0,
            title = "Entryway",
            isCover = false
        ),
    ))




    ////// rental





    fun clearAllData_PPVM() {
        // Reset post form flow
        _which_Post_Forms_Flow.value = -1

        // Reset land type selections
        _pp_Form2_LandSubTypes_Selected.value = -1
        _pp_secondform_options.value = null
        _repost_Land_Cat_Type_Ids.value = Pair(0, 0)
        _selected_Land_Type_PF2.value = 1
        _selected_Land_Cat_Id.value = -1

        // Clear media
        _mediaList.value = emptyList()

        // Reset form page navigation
        _postPropertyFormPage.value = 0

        // Reset form selections
        _fisrt_Form_selected_PP.value = -1
        _selected_User_Type_1PF.value = -1
        _onSelected_ProType.value = 1

        // Reset loader states
        step6_API_Loader.value = false
        _status_PFs.value = false
        _status_Land_Types.value = false

        // Reset draft state
        _save_Draft_PP_State.value = false
        save_Changes_Draft.value = 0

        // Clear location data
        _selected_Locality.value = ""
        _pinned_Lat_Long.value = null
        _pincode3.value = ""
        _country3.value = ""
        _state3.value = ""
        _city3.value = ""
        _latLng3.value = null
        _selectedLocality3.value = ""

        // Clear API data
        _pp_3_API_Data.value = null

        // Clear pricing data
        _budget_Price_PF5.value = ""
        _price_Negotiation_PF5.value = false

        // Clear step 4 fields data
        _pp_form_Residential_Fields.value = null
        _pp_form_Commercial_Fields.value = null
        _pp_form_Agriculture_Fields.value = null
        _selected_Options_Form4.value = Selected_Options_Form4_DC()

        // Clear errors
        _errors4.value = emptyMap()
        _errors5.value = emptyMap()
        _errors6.value = emptyMap()

        // Clear preview data
        _preview_Data_PP.value = null

        // Clear draft data
        _new_Draft_Data.value = null

        // Clear all form handler
        _all_Form_Handler.value = All_Form_Handler()

        // Clear saved post ID from preferences
        AppPreferences.save_Post_Id(0)

        println("🧹 PostProperty_ViewModel cleared successfully")
    }

    ///// post forms flow through drafts , repost , editing , new

    /// NEW POST = 0
    /// DRAFTS = 1
    /// REPOST = 2
    /// EDITING = 3
    private var _which_Post_Forms_Flow = MutableStateFlow<Int>(-1)
    var which_Post_Forms_Flow : StateFlow<Int> = _which_Post_Forms_Flow.asStateFlow()

    fun set_Post_Form_Flow(which : Int) {
        _which_Post_Forms_Flow.update { which }
    }
    fun get_Post_Form_Flow() : Int {
        return _which_Post_Forms_Flow.value
    }

    private var _pp_Form2_LandSubTypes_Selected = MutableStateFlow<Int>(-1)
    var pp_Form2_LandSubTypes_Selected : StateFlow<Int> = _pp_Form2_LandSubTypes_Selected.asStateFlow()

    fun LandSubType_Selected_Click(id: Int){
        _pp_Form2_LandSubTypes_Selected.update { id }
    }

    fun get_LandSubType_Selected_Click() : Int {
        return _pp_Form2_LandSubTypes_Selected.value
    }

    fun clear_Form2LandCat(){
        _pp_Form2_LandSubTypes_Selected.value = -1
    }

    var step6_API_Loader = mutableStateOf(false)



    private var _pp_secondform_options = MutableStateFlow<List<PostForm2_Land_Data>?>(null)
//        listOf(
//            PostPro_Second_Form_Options_DC(
//                id = 0,
//                title = "Flat/Apartment",
//                onSelected = false
//            ),
//            PostPro_Second_Form_Options_DC(
//                id = 1,
//                title = "Villa/Independent House",
//                onSelected = false
//            ),
//            PostPro_Second_Form_Options_DC(
//                id = 2,
//                title = "Builder Floor Apartment",
//                onSelected = false
//            ),
//            PostPro_Second_Form_Options_DC(
//                id = 3,
//                title = "Studio Apartment",
//                onSelected = false
//            ),
//            PostPro_Second_Form_Options_DC(
//                id = 4,
//                title = "Land/ Plot",
//                onSelected = false
//            ),
//            PostPro_Second_Form_Options_DC(
//                id = 5,
//                title = "Other",
//                onSelected = false
//            ),
//        )
//    )

    val pp_SecondForm_OptionsList: StateFlow<List<PostForm2_Land_Data>?> = _pp_secondform_options.asStateFlow()


    fun add_Data_PP_2_Options(data : List<PostForm2_Land_Data>){
        _pp_secondform_options.update { data }
    }

    fun clear_LandType_Data(){
        _pp_secondform_options.value = emptyList()
    }

    fun pp_SecondForm_Residential_Select_Option(onSelectedOption: Int) {
        _pp_secondform_options.update { currentList ->
            currentList?.map { item ->
                if (item.land_categorie_id == onSelectedOption) {
                    item.copy(on_Selected = true)
                }
                else item.copy(on_Selected = false)
            }
        }
    }


    ///////  land cat .//// land type idss for repost holdings

    var _repost_Land_Cat_Type_Ids = mutableStateOf(Pair(0 , 0))




    /// second form pp commercial data
//    private var _pp_secondform_options_commercial = MutableStateFlow<List<Post_Form_2>?>(null)
//        listOf(
//            PostPro_Second_Form_Options_DC(
//                id = 0,
//                title = "Office",
//                onSelected = false
//            ),
//            PostPro_Second_Form_Options_DC(
//                id = 1,
//                title = "Retail",
//                onSelected = false
//            ),
//            PostPro_Second_Form_Options_DC(
//                id = 2,
//                title = "Plot / Land",
//                onSelected = false
//            ),
//            PostPro_Second_Form_Options_DC(
//                id = 3,
//                title = "Industry",
//                onSelected = false
//            ),
//            PostPro_Second_Form_Options_DC(
//                id = 4,
//                title = "Office Space",
//                onSelected = false
//            ),
//            PostPro_Second_Form_Options_DC(
//                id = 5,
//                title = "Storage",
//                onSelected = false
//            ),
//            PostPro_Second_Form_Options_DC(
//                id = 5,
//                title = "Warehouse/ Godown",
//                onSelected = false
//            ),
//            PostPro_Second_Form_Options_DC(
//                id = 5,
//                title = "Other",
//                onSelected = false
//            ),
//        )
//    )

//    val pp_SecondForm_Commercial_OptionsList: StateFlow<List<Post_Form_2>?> = _pp_secondform_options_commercial.asStateFlow()

//    fun pp_SecondForm_Commercial_Select_Option(onSelectedOption: Int) {
//        _pp_secondform_options_commercial.update { currentList ->
//            currentList.map { item ->
//                if (item.id == onSelectedOption) {
//                    item.copy(onSelected = !item.onSelected)
//                } else item
//            }
//        }
//    }





    /// second form pp agriculture data
//    private var _pp_secondform_options_agriculture = MutableStateFlow(
//        listOf(
//            PostPro_Second_Form_Options_DC(
//                id = 0,
//                title = "Office",
//                onSelected = false
//            ),
//            PostPro_Second_Form_Options_DC(
//                id = 1,
//                title = "Retail",
//                onSelected = false
//            ),
//            PostPro_Second_Form_Options_DC(
//                id = 2,
//                title = "Plot / Land",
//                onSelected = false
//            ),
//            PostPro_Second_Form_Options_DC(
//                id = 3,
//                title = "Industry",
//                onSelected = false
//            ),
//            PostPro_Second_Form_Options_DC(
//                id = 4,
//                title = "Office Space",
//                onSelected = false
//            ),
//            PostPro_Second_Form_Options_DC(
//                id = 5,
//                title = "Storage",
//                onSelected = false
//            ),
//            PostPro_Second_Form_Options_DC(
//                id = 5,
//                title = "Warehouse/ Godown",
//                onSelected = false
//            ),
//            PostPro_Second_Form_Options_DC(
//                id = 5,
//                title = "Other",
//                onSelected = false
//            ),
//        )
//    )
//
//    val pp_SecondForm_Agriculture_OptionsList: StateFlow<List<PostPro_Second_Form_Options_DC>> = _pp_secondform_options_agriculture.asStateFlow()
//
//    fun pp_SecondForm_Agriculture_Select_Option(onSelectedOption: Int) {
//        _pp_secondform_options_commercial.update { currentList ->
//            currentList.map { item ->
//                if (item.id == onSelectedOption) {
//                    item.copy(onSelected = !item.onSelected)
//                } else item
//            }
//        }
//    }


    private var _postSuccessfulBtm = MutableStateFlow(false)
    var postSuccessfulBtm = _postSuccessfulBtm.asStateFlow()

    fun set_postSuccessful_State(state : Boolean){
        _postSuccessfulBtm.value = state
    }

    //// edit form index navigatort varibale
    var indexClicked by  mutableIntStateOf(-1)






    /// preview fomr rento

    private val _previewForm = MutableStateFlow<FormPreviewRento?>(null)
    val previewForm = _previewForm.asStateFlow()


    fun add_previewFormData(data : FormPreviewRento?){
        _previewForm.value = data
    }

    fun get_previewFormData(): FormPreviewRento? {
        return _previewForm.value
    }

    fun clear_previewFormData(){
        _previewForm.value = null
    }

    // media list exposed to UI
    private val _mediaList = MutableStateFlow<List<UploadPropertyMedia>>(emptyList())
    val mediaList = _mediaList.asStateFlow()


    var loadCopy = mutableStateOf(false)




    var copyMediaList : List<UploadPropertyMedia> = emptyList()

    fun add_CopyMedia(data : List<UploadPropertyMedia>) {
        println("Updating Media Copy data -- ${data}")
         copyMediaList = data
        println("List Holding Media Copy data -- ${copyMediaList}")

    }

    fun loadCopyToOriginal(){
        _mediaList.value = copyMediaList
    }

    fun loadOriginalToCopy(){
        copyMediaList = emptyList()
        copyMediaList = _mediaList.value
    }

    fun clear_CopyMedia(){
        copyMediaList = emptyList()
    }


    // cover id
    private val _coverPhotoId = MutableStateFlow<String?>(null)
    val coverPhotoId = _coverPhotoId.asStateFlow()

    // headings from API (PhotoHeadingItem is simple data class)
    private val _headingItems = MutableStateFlow<List<PhotoHeadingItem>>(emptyList())
    val headingItems = _headingItems.asStateFlow()

    // default headings rotate
    private val defaultHeadings = listOf("Kitchen", "Bedroom", "Hall", "Balcony", "Living Room", "Bathroom", "Other")
    private var headingIndex = 0

    // Limits
    private val MAX_IMAGES = 10
    private val MAX_VIDEOS = 5

    // --- Helpers for UI to update heading items (from API) ---
    fun setHeadingItems(list: List<String>) {
        _headingItems.value = list.map { PhotoHeadingItem(it) }
    }

    // call this to assign default heading rotation
    private fun nextDefaultHeading(): String {
        val value = defaultHeadings[headingIndex % defaultHeadings.size]
        headingIndex++
        return value
    }

    // Add multiple images (picked locally) and start upload for each
    fun addImagesAndUpload0(context: Context, uris: List<Uri>) {
        // enforce count
        val currentImagesCount = _mediaList.value.count { !it.isVideo && it.uploadedUrl != null } + _mediaList.value.count { !it.isVideo && it.isUploading }
        val newImagesAllowed = MAX_IMAGES - currentImagesCount
        val toAdd = if (uris.size > newImagesAllowed) uris.take(newImagesAllowed) else uris

        if (toAdd.isEmpty()){
            toast("Max upload of images is 10")
            return
        }
        val hasCoverAlready = _mediaList.value.any { it.isCover }

        val newMedia = toAdd.mapIndexed { index, uri ->
            UploadPropertyMedia(
                localUri = uri,
                isVideo = false,
                heading = nextDefaultHeading(),
                isCover = !hasCoverAlready && index == 0, // ✅ FIX
                uploadedUrl = null,
                isUploading = true,
                uploadProgress = 0
            )
        }

//
//        val newMedia = toAdd.mapIndexed { index, uri ->
//            UploadPropertyMedia(
//                localUri = uri,
//                isVideo = false,
//                heading = nextDefaultHeading(),
//                isCover = _mediaList.value.isEmpty() && index == 0,   // 🎯 First ever item auto-cover
//                uploadedUrl = null,
//                isUploading = true,
//                uploadProgress = 0
//            )
//        }





        _mediaList.value = _mediaList.value + newMedia

        // start upload for each
        newMedia.forEach { media ->
            viewModelScope.launch {
                uploadSingleMedia(context, media)
            }
        }
    }

    fun addImagesAndUpload(context: Context, uris: List<Uri>) {

        val currentImagesCount =
            _mediaList.value.count { !it.isVideo && it.uploadedUrl != null } +
                    _mediaList.value.count { !it.isVideo && it.isUploading }

        val remainingSlots = MAX_IMAGES - currentImagesCount

        if (remainingSlots <= 0) {
            toast("You can upload a maximum of $MAX_IMAGES images")
            return
        }

        // 🚨 User selected more than allowed
        if (uris.size > remainingSlots) {
            toast("Only $remainingSlots more images can be uploaded (max $MAX_IMAGES)")
        }

        val toAdd = uris.take(remainingSlots)

        val hasCoverAlready = _mediaList.value.any { it.isCover }

        val newMedia = toAdd.mapIndexed { index, uri ->
            UploadPropertyMedia(
                localUri = uri,
                isVideo = false,
                heading = nextDefaultHeading(),
                isCover = !hasCoverAlready && index == 0,
                uploadedUrl = null,
                isUploading = true,
                uploadProgress = 0
            )
        }

        _mediaList.value = _mediaList.value + newMedia

        newMedia.forEach { media ->
            viewModelScope.launch {
                uploadSingleMedia(context, media)
            }
        }
    }


    fun loadDraftFromServer(
        imageUrls: List<Image>,
        videoUrls: List<Video>,
        coverUrl: String
    )
    {
        val imageMedia = imageUrls.map {
            UploadPropertyMedia(
                uploadedUrl = it.url,
                isVideo = false,
                heading = it.heading,
                isCover = it.url == coverUrl,
                uploadProgress = 100,
                isUploading = false
            )
        }

        val videoMedia = videoUrls.map {
            UploadPropertyMedia(
                uploadedUrl = it.url,
                isVideo = true,
                heading = it.heading,
                isCover = it.url == coverUrl,
                uploadProgress = 100,
                isUploading = false
            )
        }

        if (imageMedia.isNotEmpty() || videoMedia.isNotEmpty()){
            println("VIDEO MEDIA COPY LIST ADDDING ")
            copyMediaList = imageMedia + videoMedia
        }
        _mediaList.value = imageMedia + videoMedia
    }

    // Add single video and upload
    fun addVideoAndUpload0(context: Context, uri: Uri) {
        val currentVideos = _mediaList.value.count { it.isVideo }
        if (currentVideos >= MAX_VIDEOS) return



        val media = UploadPropertyMedia(
            localUri = uri,
            isVideo = true,
            heading = nextDefaultHeading(),
            isCover = false,
            uploadedUrl = null,
            isUploading = true,
            uploadProgress = 0
        )

        _mediaList.value = _mediaList.value + media

        viewModelScope.launch {
            uploadSingleMedia(context, media)
        }
    }

    // Add single video and upload
    fun addVideoAndUpload(context: Context, uri: Uri) {

        val currentVideos =
            _mediaList.value.count { it.isVideo && (it.isUploading || it.uploadedUrl != null) }

        if (currentVideos >= MAX_VIDEOS) {
            toast("You can upload a maximum of $MAX_VIDEOS videos")
            return
        }

        val media = UploadPropertyMedia(
            localUri = uri,
            isVideo = true,
            heading = nextDefaultHeading(),
            isCover = false,
            uploadedUrl = null,
            isUploading = true,
            uploadProgress = 0
        )

        _mediaList.value = _mediaList.value + media

        viewModelScope.launch {
            uploadSingleMedia(context, media)
        }
    }


    // Upload a single media (update states with progress)
    private suspend fun uploadSingleMedia(context: Context, media: UploadPropertyMedia) {
        val s3Uploader = S3Uploader(
            bucket = constants.BUCKET_NAME,
            cloudFront = constants.CLOUD_FRONT_URL,
            accessId = constants.ACCESS_ID,
            secretKey = constants.SECRET_KEY
        )

        try {
            val result = s3Uploader.uploadSingle(
                context = context,
                userId = AppPreferences.getUserId().toString(),
                uri = media.localUri!!,
                false,
                ""
            )
            { progress ->

                val safeProgress = progress.coerceIn(0, 100)

                _mediaList.value = _mediaList.value.map {
                    if (it.id == media.id) {
                        it.copy(
                            uploadProgress = safeProgress,
                            isUploading = safeProgress < 100
                        )
                    } else it
                }
            }
//            { progress ->
//                _mediaList.value = _mediaList.value.map {
//                    if (it.id == media.id) it.copy(uploadProgress = progress, isUploading = true) else it
//                }
//            }



            // Update list with uploaded URL
            _mediaList.value = _mediaList.value.map {
                if (it.id == media.id) it.copy(uploadedUrl = result.url, isUploading = false, uploadProgress = 100)
                else it
            }

        } catch (e: Exception) {
            e.printStackTrace()
            _mediaList.value = _mediaList.value.map {
                if (it.id == media.id) it.copy(isUploading = false, uploadProgress = 0)
                else it
            }
        }
    }


    fun clear_Media(){
        _mediaList.value = emptyList()
    }

    // delete media
    fun deleteMediaold(id: String) {
        _mediaList.value = _mediaList.value.filter { it.id != id }
        if (_coverPhotoId.value == id) _coverPhotoId.value = null
    }

    fun deleteMedia(id: String) {
        val currentList = _mediaList.value
        val deletedIndex = currentList.indexOfFirst { it.id == id }
        val deletedItem = currentList.getOrNull(deletedIndex)

        // Remove the deleted item
        val updatedList = currentList.filter { it.id != id }

        // If deleted item was cover → select next image as cover
        if (deletedItem?.isCover == true) {

            val nextCover = updatedList
                .drop(deletedIndex)
                .firstOrNull { !it.isVideo }
                ?: updatedList.firstOrNull { !it.isVideo }

            _mediaList.value = updatedList.map { media ->
                media.copy(isCover = media.id == nextCover?.id)
            }

            _coverPhotoId.value = nextCover?.id
        } else {
            _mediaList.value = updatedList
        }
    }


    // update heading for a specific media item (invoked from sheet)
    fun updateHeading(id: String, newHeading: String) {
        _mediaList.value = _mediaList.value.map { media ->
            if (media.id == id) media.copy(heading = newHeading) else media
        }
    }


    fun setCoverPhoto(mediaId: String) {
        _mediaList.value = _mediaList.value.map { media ->
            media.copy(isCover = media.id == mediaId && !media.isVideo)
        }
        _coverPhotoId.value = mediaId
    }



    suspend fun resolveThumbnail(): String {
        val coverThumbnail =
            _mediaList.value
                .firstOrNull { it.isCover && !it.isVideo && it.uploadedUrl != null }
                ?.uploadedUrl

        val imageThumbnail =
            _mediaList.value
                .firstOrNull { !it.isVideo && it.uploadedUrl != null }
                ?.uploadedUrl

        val videoItem =
            _mediaList.value
                .firstOrNull { it.isVideo && it.uploadedUrl != null }

        return coverThumbnail
            ?: imageThumbnail
            ?: if (videoItem != null) {
                generateAndUploadVideoThumbnail(
                    context = constants.activity,
                    videoUrl = videoItem.uploadedUrl!!
                )
            } else {
                ""
            }
    }
    // Build API models: map uploaded items to ApiMediaItem; only include uploadedUrl ones

    suspend fun buildUploadRequestBody(): UploadPostRequest {
        val images = _mediaList.value
            .filter { !it.isVideo && it.uploadedUrl != null }
            .map {
                ApiMediaItem(it.uploadedUrl!!, it.heading)
            }

        val videos = _mediaList.value
            .filter { it.isVideo && it.uploadedUrl != null }
            .map {
                ApiMediaItem(it.uploadedUrl!!, it.heading)
            }

        val thumbnail = resolveThumbnail()   // ✅ SAFE

        return UploadPostRequest(
            video_urls = videos,
            image_urls = images,
            thumbnail = thumbnail
        )
    }

//    fun buildUploadRequestBody(): UploadPostRequest {
//        val images = _mediaList.value.filter { !it.isVideo && it.uploadedUrl != null }.map {
//            ApiMediaItem(url = it.uploadedUrl!!, heading = it.heading)
//        }
//        val videos = _mediaList.value.filter { it.isVideo && it.uploadedUrl != null }.map {
//            ApiMediaItem(url = it.uploadedUrl!!, heading = it.heading)
//        }
//
//        // thumbnail priority: cover image -> first image -> empty
////        val thumbnail = _mediaList.value.firstOrNull { it.isCover && it.uploadedUrl != null }?.uploadedUrl
////            ?: _mediaList.value.firstOrNull { !it.isVideo && it.uploadedUrl != null }?.uploadedUrl
////            ?: ""
//
//
//
//        return UploadPostRequest(
//            video_urls = videos,
//            image_urls = images,
//            thumbnail = thumbnail
//        )
//    }

//
//    private val _mediaList = MutableStateFlow<List<UploadPropertyMedia>>(emptyList())
//    var mediaList = _mediaList.asStateFlow()
//
//    private val _coverPhotoId = MutableStateFlow<String?>(null)
//    val coverPhotoId = _coverPhotoId.asStateFlow()
//
////    fun addImages22(list: List<UploadPropertyMedia>) {
////        _mediaList.value = _mediaList.value + list
////    }
//
//    fun addImages(newImages: List<UploadPropertyMedia>) {
//        val current = _mediaList.value.toMutableList()
//
//        newImages.forEach { img ->
//            val autoHeading = defaultHeadings[headingIndex % defaultHeadings.size]
//            headingIndex++
//
//            current.add(img.copy(heading = autoHeading))
//        }
//
//        _mediaList.value = current
//    }
//
//
//
//
//
//
//    fun addVideo(video: UploadPropertyMedia) {
//        val autoHeading = defaultHeadings[headingIndex % defaultHeadings.size]
//        headingIndex++
//
//        _mediaList.value = _mediaList.value + video.copy(
//            heading = autoHeading
//        )
//    }
//
//    fun deleteMedia(id: String) {
//        _mediaList.value = _mediaList.value.filter { it.id != id }
//
//        if (_coverPhotoId.value == id) {
//            _coverPhotoId.value = null
//        }
//    }
//
//    fun updateHeading(id: String, newHeading: String) {
//        _mediaList.value = _mediaList.value.map { media ->
//            if (media.id == id) media.copy(heading = newHeading)
//            else media
//        }
//    }
//
//
//
//    fun setCoverPhoto(mediaId: String) {
//        val updated = mediaList.value.map { item ->
//            item.copy(isCover = item.id == mediaId)
//        }
//        _mediaList.value = updated
//    }
//
//
//
//    fun get_Media(): List<UploadPropertyMedia>{
//        return _mediaList.value
//    }
//
//    fun clear_Media(){
//        _mediaList.value = emptyList()
//    }
//
//
//


    /// post property forms swifter var

    private val _postPropertyFormPage = MutableStateFlow(0)
    val postPropertyFormPage = _postPropertyFormPage.asStateFlow()

    fun onNextPPForm() {
        _postPropertyFormPage.update { current ->
            if (current < 6){
                current + 1
            }
            else{
                current
            }
        }
    }

    fun onPreviousPPForm() {
        _postPropertyFormPage.update { current ->
            (current - 1).coerceAtLeast(0) // Prevent negative page
        }
    }

    fun goToPPFormPage(page: Int, maxPages: Int) {
        _postPropertyFormPage.value = page.coerceIn(0, maxPages - 1)
    }


    var emptyMediaUploadBtm =  mutableStateOf(false)

    fun set_True_emptyMediaBtm(){
        emptyMediaUploadBtm.value = true
    }

    fun set_False_emptyMediaBtm(){
        emptyMediaUploadBtm.value = false
    }

    var proceedWithoutMedia = MutableStateFlow(false)
        private set

    fun confirmProceedWithoutMedia() {
        proceedWithoutMedia.value = true
    }



    //// post property 1st form selected var
    private var _fisrt_Form_selected_PP = MutableStateFlow<Int>(-1)
    var first_Form_selected_PP : StateFlow<Int> = _fisrt_Form_selected_PP.asStateFlow()

    fun first_Form_selected_PP(Id : Int){
        _fisrt_Form_selected_PP.update { Id }
    }

    fun get_FirstForm_Selected_PP() : Int {
        return _fisrt_Form_selected_PP.value
    }

    /// post property save to drafts pop up
    private var _save_Draft_PP_State = MutableStateFlow<Boolean>(false)
    var save_Draft_PP_State : StateFlow<Boolean> = _save_Draft_PP_State.asStateFlow()

    fun enable_Save_Draft(){
        _save_Draft_PP_State.value = true
    }

    fun disable_Save_Draft(){
        _save_Draft_PP_State.value = false
    }

    fun get_Save_Draft_State(): Boolean{
        return _save_Draft_PP_State.value
    }


    //// post forms loader states

    private var _status_PFs = MutableStateFlow<Boolean>(false)
    var status_PFs : StateFlow<Boolean> = _status_PFs.asStateFlow()

    fun change_Status_PFs(change : Boolean){
        _status_PFs.update { change }
    }


    /// land types data loader state var

    private var _status_Land_Types = MutableStateFlow<Boolean>(false)
    var status_Land_Types : StateFlow<Boolean> = _status_Land_Types.asStateFlow()

    fun change_Status_Land_Types(change: Boolean){
        _status_Land_Types.update { change }
    }

    //// post property 1st form usertype var
    private var _selected_User_Type_1PF = MutableStateFlow<Int>(-1)
    var selected_User_Type_1PF : StateFlow<Int> = _selected_User_Type_1PF.asStateFlow()

    fun select_User_Type_1PF(id : Int) {
        _selected_User_Type_1PF.update { id }
    }

    /// post property form 2 get land types user clicked type of land

    private var _selected_Land_Type_PF2 = MutableStateFlow<Int>(1)
    var selected_Land_Type_PF2 : StateFlow<Int> = _selected_Land_Type_PF2.asStateFlow()

    fun select_Land_Type(type : Int){
        _selected_Land_Type_PF2.value = type
    }

    fun get_Land_Type(): Int {
        return _selected_Land_Type_PF2.value
    }

    /// post form 2 var  land sub cats

    private var _selected_Land_Cat_Id = MutableStateFlow<Int>(-1)
    var selected_Land_Cat_Id : StateFlow<Int> = _selected_Land_Cat_Id.asStateFlow()

    fun select_Land_Cat_Id(cat_id : Int){
        _selected_Land_Cat_Id.update { cat_id }
    }

    fun get_Selected_Land_Cat_Id() : Int {
        return _selected_Land_Cat_Id.value
    }




    //// selected loacality

    private var _selected_Locality = MutableStateFlow<String>("")
    var selected_Locality : StateFlow<String> = _selected_Locality.asStateFlow()

    fun add_Selected_Locality(local : String){
        _selected_Locality.update { local }
    }

    //// for lat long storing

    private var _pinned_Lat_Long = MutableStateFlow<LatLng?>(null)
    var pinned_Lat_Long : StateFlow<LatLng?> = _pinned_Lat_Long.asStateFlow()

    fun add_Pinned_Lat_Long(latLng: LatLng){
        _pinned_Lat_Long.update { latLng }
    }

    //// pp 3 api data

    private var _pp_3_API_Data = MutableStateFlow<PP3_API_DC?>(null)
    var pp_3_API_Data : StateFlow<PP3_API_DC?> = _pp_3_API_Data.asStateFlow()

    fun add_pp3_Data(data: PP3_API_DC){
        _pp_3_API_Data.update { data }
    }

    fun get_pp3_Data() :PP3_API_DC? {
        return _pp_3_API_Data.value
    }

    fun clear_pp3_Data(){
        _pp_3_API_Data.value = null
    }

    /// post form 5 var pricee
    private var _budget_Price_PF5 = MutableStateFlow<String>("")
    var budget_Price_PF5 : StateFlow<String> = _budget_Price_PF5.asStateFlow()

    fun put_budget_Price_PF5(budget : String){
        _budget_Price_PF5.update { budget }
    }
    fun get_budget_Price_PF5() : String{
        return _budget_Price_PF5.value
    }
    fun clear_Budget_PF5(){
         _budget_Price_PF5.value = ""
    }



    /// post form 5 price negotiation checkbox
    private var _price_Negotiation_PF5 = MutableStateFlow<Boolean>(false)
    var price_Negotiation_PF5 : StateFlow<Boolean> = _price_Negotiation_PF5.asStateFlow()

    fun check_Price_Negotiation(nego : Boolean){
        _price_Negotiation_PF5.update { nego }
    }

    fun get_price_negotiation(): String {
        return if (_price_Negotiation_PF5.value) "1" else "0"
    }

    //// draft new flow save changes or not
    var save_Changes_Draft = mutableStateOf(0)

    /// post form residential step 4 fields data var
    private var _pp_form_Residential_Fields = MutableStateFlow<PostProperty_Stepfour_Residential_Data?> (null)
    var pp_form_Residential_Fields : StateFlow<PostProperty_Stepfour_Residential_Data?> = _pp_form_Residential_Fields.asStateFlow()

    fun add_PP_Fields_Data_Res(data : PostProperty_Stepfour_Residential_Data ){
        _pp_form_Residential_Fields.update { data }
    }

    fun clear_PP_Fields_Data_Res(){
        _pp_form_Residential_Fields.value = null
    }

    /// post form commercial step 4 fields data var
    private var _pp_form_Commercial_Fields = MutableStateFlow<Post_Property_Stepfour_Commercial_Data?> (null)
    var pp_form_Commercial_Fields : StateFlow<Post_Property_Stepfour_Commercial_Data?> = _pp_form_Commercial_Fields.asStateFlow()

    fun add_PP_Fields_Data_Com(data : Post_Property_Stepfour_Commercial_Data ){
        _pp_form_Commercial_Fields.update { data }
    }

    fun clear_PP_Fields_Data_Com(){
        _pp_form_Commercial_Fields.value = null
    }


    /// post form agriculture step 4 fields data var
    private var _pp_form_Agriculture_Fields = MutableStateFlow<Post_Property_Stepfour_Agriculture_Data?> (null)
    var pp_form_Agriculture_Fields : StateFlow<Post_Property_Stepfour_Agriculture_Data?> = _pp_form_Agriculture_Fields.asStateFlow()

    fun add_PP_Fields_Data_Agri(data : Post_Property_Stepfour_Agriculture_Data ){
        _pp_form_Agriculture_Fields.update { data }
    }

    fun clear_PP_Fields_Data_Agri(){
        _pp_form_Agriculture_Fields.value = null
    }


    /// post form commercial step 4 fields data var
    private var _postFormCommon = MutableStateFlow<PostFormCommonPropertyData?> (null)
    var postFormCommon : StateFlow<PostFormCommonPropertyData?> = _postFormCommon.asStateFlow()


    fun add_postFormCommon(data : PostFormCommonPropertyData ){
        _postFormCommon.update { data }
    }

    fun clear_postFormCommon(){
        _postFormCommon.value = null
    }

    private var _selected_Options_Form4 = MutableStateFlow(Selected_Options_Form4_DC())
    var selected_Options_Form4 : StateFlow<Selected_Options_Form4_DC?> = _selected_Options_Form4.asStateFlow()


    fun update_Selected_Field_Form4(update: (Selected_Options_Form4_DC) -> Selected_Options_Form4_DC) {
        _selected_Options_Form4.update { current -> update(current) }
    }

    fun get_Selected_Fields_Form() : Selected_Options_Form4_DC {
        return _selected_Options_Form4.value
    }

    fun clear_Selected_Fields_Form4() {
        _selected_Options_Form4.value = Selected_Options_Form4_DC()

    }



    /// post property 3rd form location vars


    //var pincode3 by mutableStateOf("")

    private var _pincode3 = MutableStateFlow<String>("")
    var pincode3 : StateFlow<String> = _pincode3.asStateFlow()

    fun set_pincode3(pincode : String){
        _pincode3.update { pincode }
    }

    fun get_pincode3(): String{
        return _pincode3.value
    }

   // var country3 by  mutableStateOf("")

    private var _country3 = MutableStateFlow<String>("")
    var country3 : StateFlow<String> = _country3.asStateFlow()

    fun set_country3(country : String){
        _country3.update { country }
    }

    fun get_country3() : String{
       return _country3.value
    }

    //var state3 by  mutableStateOf("")

    private var _state3 = MutableStateFlow<String>("")
    var state3 : StateFlow<String> = _state3.asStateFlow()

    fun set_state3(state3 : String){
        _state3.update { state3 }
    }

    fun get_state3(): String{
       return _state3.value
    }


    //var city3 by  mutableStateOf("")

    private var _city3 = MutableStateFlow<String>("")
    var city3 : StateFlow<String> = _city3.asStateFlow()

    fun set_city3(city : String){
        _city3.update { city }
    }

    fun get_city3(): String{
       return _city3.value
    }

    //var latLng3 by  mutableStateOf<LatLng?>(null)

    private var _latLng3 = MutableStateFlow<LatLng?>(null)
    var latLng3 : StateFlow<LatLng?> = _latLng3.asStateFlow()

    fun set_latLng3(latLng3 : LatLng?){
        _latLng3.update { latLng3 }
    }

    fun get_latLng3() : LatLng?{
       return _latLng3.value
    }

    //var selectedLocality by remember { mutableStateOf("") }


    private var _form5Rento = MutableStateFlow<RentalForm5?>(null)
    var form5Rento : StateFlow<RentalForm5?> = _form5Rento.asStateFlow()


//    fun add_form5Rento(data : RentalForm5 ){
//        _form5Rento.update { data }
//    }

    fun add_form5Rento(update: (RentalForm5?) -> RentalForm5) {
        _form5Rento.update { current -> update(current) }
    }

    fun updateForm5(update: (RentalForm5) -> RentalForm5) {
        _form5Rento.value = update(_form5Rento.value ?: RentalForm5())
    }


    fun clear_form5Rento(){
        _form5Rento.value = null
    }



    private var _selectedLocality3 = MutableStateFlow("")
    var  selectedLocality3 : StateFlow<String> = _selectedLocality3.asStateFlow()

    fun set__selectedLocality3(selectedLocality3 : String){
        _selectedLocality3.update { selectedLocality3 }
    }

    fun get_selectedLocality3() : String{
        return _selectedLocality3.value
    }

    ///// post property 2nd form land type var
    //var onSelected_ProType by  mutableStateOf(-1)

    private var _onSelected_ProType = MutableStateFlow<Int>(1)
    var onSelected_ProType : StateFlow<Int> = _onSelected_ProType.asStateFlow()

    fun set_onSelected_ProType(no : Int){
        _onSelected_ProType.update { no }
    }

    fun get_onSelected_ProType() : Int{
       return _onSelected_ProType.value
    }


    fun clear_Form2MainLandCat(){
        _onSelected_ProType.value = 1
    }




    /////// new draft data var

    private var _new_Draft_Data = MutableStateFlow<New_Draft_Flow_Data?>(null)
    var new_Draft_Data : StateFlow<New_Draft_Flow_Data?> = _new_Draft_Data.asStateFlow()

    fun set_new_Draft_Data(data : New_Draft_Flow_Data){
        _new_Draft_Data.update { data }
    }

    fun get_new_Draft_Data(): New_Draft_Flow_Data? {
       return _new_Draft_Data.value
    }


    //// error state handling



    //// fun clear form details

    fun clear_Forms(){
        first_Form_selected_PP(-1)
        select_Land_Cat_Id(-1)

        add_pp3_Data(
            PP3_API_DC(
                pincode = "",
                country = "",
                state = "",
                city = "",
                locality = ""
            )
        )

        clear_Selected_Fields_Form4()

        check_Price_Negotiation(false)
        put_budget_Price_PF5("")

        //clear_Media()
    }



    // form 4 check

    // ✅ Residential
//    fun getActiveFieldsResidential(data: PostProperty_Stepfour_Residential_Data?): List<Int> {
    fun getActiveFieldsResidential4(data: PostFormCommonPropertyData?): List<Int> {
        val activeFields = mutableListOf<Int>()
        data?.let {
            if (!it.Property_Name.isNullOrEmpty()) activeFields.add(0)
//            if (!it.property_area.isNullOrEmpty()) activeFields.add(1)
            if (!it.Select_Floor_Plane.isNullOrEmpty() && it.Select_Floor_Plane.any { str -> str.isNotBlank() }) activeFields.add(1)
            if (!it.Carpet_Area.isNullOrEmpty()) activeFields.add(2)
//            if (!it.Area_Dimensions.isNullOrEmpty()) activeFields.add(3)
            if (!it.Property_Facing.isNullOrEmpty()) activeFields.add(3)
            if (!it.Floor_Details.isNullOrEmpty()) activeFields.add(4)
//            if (!it.Preferred_Tenants.isNullOrEmpty()) activeFields.add(5)
            if (!it.Available_From.isNullOrEmpty()) activeFields.add(5)
//            if (!it.property_ownership.isNullOrEmpty()) activeFields.add(7)
//            if (!it.availability_status.isNullOrEmpty() && it.availability_status.any { str -> str.isNotBlank() }) activeFields.add(8)
        }

        return activeFields
    }

    // ✅ Commercial
//    fun getActiveFieldsCommercial(data: Post_Property_Stepfour_Commercial_Data?): List<Int> {
    fun getActiveFieldsCommercial4(data: PostFormCommonPropertyData?): List<Int> {
        val activeFields = mutableListOf<Int>()

        data?.let {
            if (!it.Property_Name.isNullOrEmpty()) activeFields.add(0)
            if (!it.Carpet_Area.isNullOrEmpty()) activeFields.add(2)
//            if (!it.Shop_Facade.isNullOrEmpty()) activeFields.add(9)
            if (!it.Property_Facing.isNullOrEmpty()) activeFields.add(3)
            if (!it.Floor_Details.isNullOrEmpty()) activeFields.add(4)
            if (!it.Shop_Facade?.firstOrNull()?.Facade_Height.isNullOrEmpty() && !it.Shop_Facade?.firstOrNull()?.Facade_Width.isNullOrEmpty())activeFields.add(6)
//            if (!it.Preferred_Tenants.isNullOrEmpty()) activeFields.add(7)
            if (!it.Available_From.isNullOrEmpty()) activeFields.add(5)
        }

        return activeFields
    }

    // ✅ Agriculture
//    fun getActiveFieldsAgriculture(data: Post_Property_Stepfour_Agriculture_Data?): List<Int> {
    fun getActiveFieldsAgriculture4(data: PostFormCommonPropertyData?): List<Int> {
        val activeFields = mutableListOf<Int>()

        data?.let {
            if (!it.Property_Name.isNullOrEmpty()) activeFields.add(0)
            if (!it.Property_Area.isNullOrEmpty()) activeFields.add(7)
//            if (!it.Select_Floor_Plane.isNullOrEmpty() && it.Select_Floor_Plane.any { str -> str.isNotBlank() }) activeFields.add(1)
            if (!it.Carpet_Area.isNullOrEmpty()) activeFields.add(2)
//            if (!it.Area_Dimensions.isNullOrEmpty()) activeFields.add(4)
            if (!it.Property_Facing.isNullOrEmpty()) activeFields.add(3)
            if (!it.Floor_Details.isNullOrEmpty()) activeFields.add(4)
//            if (!it.Preferred_Tenants.isNullOrEmpty()) activeFields.add(7)
            if (!it.Available_From.isNullOrEmpty()) activeFields.add(5)
        }

        return activeFields
    }

    private var _errors4 = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val errors4: StateFlow<Map<Int, Boolean>> = _errors4.asStateFlow()



    fun check_Errors4(activeFields: List<Int>): Boolean {
        println("🔍 DEBUG - State values:")
        println("property_Name: '${_selected_Options_Form4.value.property_Name}'")
        println("property_Land_Area: '${_selected_Options_Form4.value.property_Land_Area}'")
        println("property_Area_Dimension_Length: '${_selected_Options_Form4.value.property_Area_Dimension_Length}'")
        println("property_Area_Dimension_breadth: '${_selected_Options_Form4.value.property_Area_Dimension_Width}'")
        println("property_Facing: '${_selected_Options_Form4.value.property_Facing}'")
        println("property_Floor_Det_Total: '${_selected_Options_Form4.value.property_Floor_Det_Total}'")
        println("property_Floor_Det_Which: '${_selected_Options_Form4.value.property_Floor_Det_Which}'")
//        println("property_Ownership: '${_selected_Options_Form4.value.property_Ownership}'")

        println("ACTIVE FIELDS -- ${activeFields}")

        val allValidations = mapOf(
            0 to _selected_Options_Form4.value.property_Name.isEmpty(),
//            1 to _selected_Options_Form4.value
            //1 to _selected_Options_Form4.value.property_Land_Area.isEmpty(),
            1 to _selected_Options_Form4.value.property_Floor_Plan_Bhk.isEmpty(),
            2 to _selected_Options_Form4.value.property_Carpet_Area.isEmpty(),
//            4 to (_selected_Options_Form4.value.property_Area_Dimension_Length.isEmpty() ||
//                    _selected_Options_Form4.value.property_Area_Dimension_Width.isEmpty()),
            3 to _selected_Options_Form4.value.property_Facing.isEmpty(),
            4 to (_selected_Options_Form4.value.property_Floor_Det_Total.isEmpty() ||
                    _selected_Options_Form4.value.property_Floor_Det_Which.isEmpty()),
            //7 to _selected_Options_Form4.value.property_Ownership.isEmpty(),
            5 to _selected_Options_Form4.value.property_availability_from.isEmpty(),
            6 to (_selected_Options_Form4.value.property_Facade_Height.isEmpty() ||
                    _selected_Options_Form4.value.property_Facade_Width.isEmpty()),
            7 to _selected_Options_Form4.value.property_Land_Area.isEmpty(),
        )

        // Only check validations that belong to activeFields
        val filteredValidations = allValidations.filterKeys { it in activeFields }

        _errors4.value = filteredValidations

        // 🔎 Print only the errors for active fields
        filteredValidations.filterValues { it }.forEach { (index, _) ->
            println("❌ Error at field index: $index")
        }

        return filteredValidations.values.none { it } // ✅ true if no errors
    }


    fun resetErrors4() {
        _errors4.value = emptyMap()
    }


    /// form 5 checkk

    //    fun getActiveFieldsResidential(data: PostProperty_Stepfour_Residential_Data?): List<Int> {
    fun getActiveFieldsResidential5(data: Step5?): List<Int> {
        val activeFields = mutableListOf<Int>()
        data?.let {
            if (!it.Agreement_Type.isNullOrEmpty()) activeFields.add(0)
//            if (!it.property_area.isNullOrEmpty()) activeFields.add(1)
            if (!it.No_of_Bathrooms.isNullOrEmpty()) activeFields.add(1)
            if (!it.No_of_Balconies.isNullOrEmpty()) activeFields.add(2)
//            if (!it.Area_Dimensions.isNullOrEmpty()) activeFields.add(3)
            if (!it.Food_Preferences.isNullOrEmpty()) activeFields.add(3)
            if (!it.Pets_Allowed.isNullOrEmpty()) activeFields.add(4)
//            if (!it.Preferred_Tenants.isNullOrEmpty()) activeFields.add(5)
            if (!it.Furnishing_Status.isNullOrEmpty()) activeFields.add(5)
            if (!it.Parking_Available.isNullOrEmpty()) activeFields.add(6)
            if (!it.Amenities.isNullOrEmpty()) activeFields.add(7)
//            if (!it.availability_status.isNullOrEmpty() && it.availability_status.any { str -> str.isNotBlank() }) activeFields.add(8)
        }

        return activeFields
    }

    // ✅ Commercial
//    fun getActiveFieldsCommercial(data: Post_Property_Stepfour_Commercial_Data?): List<Int> {
    fun getActiveFieldsCommercial5(data: Step5?): List<Int> {
        val activeFields = mutableListOf<Int>()

        data?.let {
            if (!it.No_of_Bathrooms.isNullOrEmpty()) activeFields.add(1)
            if (!it.Furnishing_Status.isNullOrEmpty()) activeFields.add(5)
//            if (!it.Shop_Facade.isNullOrEmpty()) activeFields.add(9)
            if (!it.Parking_Available.isNullOrEmpty()) activeFields.add(6)
            if (!it.Amenities.isNullOrEmpty()) activeFields.add(7)
//            if (!it.Shop_Facade?.firstOrNull()?.Facade_Height.isNullOrEmpty() && !it.Shop_Facade?.firstOrNull()?.Facade_Width.isNullOrEmpty())activeFields.add(6)
//            if (!it.Preferred_Tenants.isNullOrEmpty()) activeFields.add(7)
//            if (!it.Available_From.isNullOrEmpty()) activeFields.add(5)
        }

        return activeFields
    }

    // ✅ Agriculture
//    fun getActiveFieldsAgriculture(data: Post_Property_Stepfour_Agriculture_Data?): List<Int> {
    fun getActiveFieldsAgriculture5(data: Step5?): List<Int> {
        val activeFields = mutableListOf<Int>()

        data?.let {
            if (!it.Agreement_Type.isNullOrEmpty()) activeFields.add(0)
//            if (!it.property_area.isNullOrEmpty()) activeFields.add(1)
            if (!it.No_of_Bathrooms.isNullOrEmpty()) activeFields.add(1)
            if (!it.No_of_Balconies.isNullOrEmpty()) activeFields.add(2)
//            if (!it.Area_Dimensions.isNullOrEmpty()) activeFields.add(3)
//            if (!it.Food_Preferences.isNullOrEmpty()) activeFields.add(3)
            if (!it.Pets_Allowed.isNullOrEmpty()) activeFields.add(4)
//            if (!it.Preferred_Tenants.isNullOrEmpty()) activeFields.add(5)
            if (!it.Furnishing_Status.isNullOrEmpty()) activeFields.add(5)
            if (!it.Parking_Available.isNullOrEmpty()) activeFields.add(6)
            if (!it.Amenities.isNullOrEmpty()) activeFields.add(7)
        }

        return activeFields
    }

    private var _errors5 = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val errors5: StateFlow<Map<Int, Boolean>> = _errors5.asStateFlow()



    fun check_Errors5(activeFields: List<Int>): Boolean {
        println("🔍 DEBUG - State values:-- ${activeFields}")
        println("property_agreement_type: '${_selected_Options_Form4.value.property_agreement_type}'")
        println("property_No_of_Baths: '${_selected_Options_Form4.value.property_No_of_Baths}'")
        println("property_Name: '${_selected_Options_Form4.value.property_No_of_Balconies}'")
        println("property_food_preferences: '${_selected_Options_Form4.value.property_food_preferences}'")
        println("property_Parking: '${_selected_Options_Form4.value.property_Parking}'")
        println("property_Furnished: '${_selected_Options_Form4.value.property_Furnished}'")
        println("property_Parking: '${_selected_Options_Form4.value.property_Parking}'")
        println("property_Amenities: '${_selected_Options_Form4.value.property_Amenities}'")


        val allValidations = mapOf(
            0 to _selected_Options_Form4.value.property_agreement_type.isEmpty(),
//            1 to _selected_Options_Form4.value
            //1 to _selected_Options_Form4.value.property_Land_Area.isEmpty(),
            1 to _selected_Options_Form4.value.property_No_of_Baths.isEmpty(),
            2 to _selected_Options_Form4.value.property_No_of_Balconies.isEmpty(),
//            4 to (_selected_Options_Form4.value.property_Area_Dimension_Length.isEmpty() ||
//                    _selected_Options_Form4.value.property_Area_Dimension_Width.isEmpty()),
            3 to _selected_Options_Form4.value.property_food_preferences.isEmpty(),
            4 to (_selected_Options_Form4.value.property_pets_allowed.isEmpty()),
            //7 to _selected_Options_Form4.value.property_Ownership.isEmpty(),
            5 to _selected_Options_Form4.value.property_Furnished.isEmpty(),
            6 to (_selected_Options_Form4.value.property_Parking.isEmpty() ),
            7 to _selected_Options_Form4.value.property_Amenities.isEmpty(),
        )

        // Only check validations that belong to activeFields
        val filteredValidations = allValidations.filterKeys { it in activeFields }

        _errors5.value = filteredValidations

        // 🔎 Print only the errors for active fields
        filteredValidations.filterValues { it }.forEach { (index, _) ->
            println("❌ Error at field index: $index")
        }

        return filteredValidations.values.none { it } // ✅ true if no errors
    }


    fun resetErrors5() {
        _errors5.value = emptyMap()
    }


    //// form 6 checkkk

    //    fun getActiveFieldsResidential(data: PostProperty_Stepfour_Residential_Data?): List<Int> {
    fun getActiveFieldsResidential6(data: Step6?): List<Int> {
        val activeFields = mutableListOf<Int>()
        data?.let {
            //if (it.Is_this_property_for_rent_or_Lease.isEmpty() || it.Is_this_property_for_rent_or_Lease == "Rent")
            if (!it.Rent.isNullOrEmpty()) activeFields.add(0)
            if (!it.Lease.isNullOrEmpty()) activeFields.add(1)
            if (!it.Lease?.firstOrNull()?.Lease_Duration_in_Years.isNullOrEmpty()) activeFields.add(2)
        }

        return activeFields
    }

    // ✅ Commercial
//    fun getActiveFieldsCommercial(data: Post_Property_Stepfour_Commercial_Data?): List<Int> {
    fun getActiveFieldsCommercial6(data: Step6?): List<Int> {
        val activeFields = mutableListOf<Int>()

        data?.let {
            if (!it.Rent.isNullOrEmpty()) activeFields.add(0)
            if (!it.Lease.isNullOrEmpty()) activeFields.add(1)
            if (!it.Lease?.firstOrNull()?.Lease_Duration_in_Years.isNullOrEmpty()) activeFields.add(2)
        }

        return activeFields
    }

    // ✅ Agriculture
//    fun getActiveFieldsAgriculture(data: Post_Property_Stepfour_Agriculture_Data?): List<Int> {
    fun getActiveFieldsAgriculture6(data: Step6?): List<Int> {
        val activeFields = mutableListOf<Int>()

        data?.let {
            if (!it.Rent.isNullOrEmpty()) activeFields.add(0)
            if (!it.Lease.isNullOrEmpty()) activeFields.add(1)
            if (!it.Lease?.firstOrNull()?.Lease_Duration_in_Years.isNullOrEmpty()) activeFields.add(2)
        }

        return activeFields
    }


    private var _errors6 = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val errors6: StateFlow<Map<Int, Boolean>> = _errors6.asStateFlow()



    fun check_Errors6(activeFields: List<Int>): Boolean {
        println("🔍 DEBUG - State values ERROR 6:")
        println("property_Name: '${_selected_Options_Form4.value.property_Name}'")
        println("property_Land_Area: '${_selected_Options_Form4.value.property_Land_Area}'")
        println("property_Area_Dimension_Length: '${_selected_Options_Form4.value.property_Area_Dimension_Length}'")
        println("property_Area_Dimension_breadth: '${_selected_Options_Form4.value.property_Area_Dimension_Width}'")
        println("property_Facing: '${_selected_Options_Form4.value.property_Facing}'")
        println("property_Floor_Det_Total: '${_selected_Options_Form4.value.property_Floor_Det_Total}'")
        println("property_Floor_Det_Which: '${_selected_Options_Form4.value.lease_duration_in_years_type}'")
//        println("property_Ownership: '${_selected_Options_Form4.value.property_Ownership}'")


        val allValidations = mapOf(
            if (_selected_Options_Form4.value.property_for_rent_or_lease.isEmpty() ||_selected_Options_Form4.value.property_for_rent_or_lease == "Rent" ){
                0 to _selected_Options_Form4.value.rent.isEmpty()
            }
            else {
                1 to _selected_Options_Form4.value.lease_amount.isEmpty()
                2 to _selected_Options_Form4.value.lease_duration_in_years_type.isEmpty()
            }



        )

        // Only check validations that belong to activeFields
        val filteredValidations = allValidations.filterKeys { it in activeFields }

        _errors6.value = filteredValidations

        // 🔎 Print only the errors for active fields
        filteredValidations.filterValues { it }.forEach { (index, _) ->
            println("❌ Error at field index: $index")
        }

        return filteredValidations.values.none { it } // ✅ true if no errors
    }


    fun resetErrors6() {
        _errors6.value = emptyMap()
    }





    // phot headings

    private var _form7PhotoHeadings = MutableStateFlow<RentoPhotoHeadings?> (null)
    var form7PhotoHeadings : StateFlow<RentoPhotoHeadings?> = _form7PhotoHeadings.asStateFlow()

    fun add_form7PhotoHeadings(List : RentoPhotoHeadings)
    {
        _form7PhotoHeadings.update { List }
    }

//    private val _headingItems = MutableStateFlow<List<PhotoHeadingItem>>(emptyList())
//    val headingItems = _headingItems.asStateFlow()
//
//    fun updateHeadings(list: List<String>) {
//        _headingItems.value = list.map { PhotoHeadingItem(it) }
//    }
//
//    fun selectSingleHeading(title: String) {
//        _headingItems.value.forEach { item ->
//            item.isSelected.value = item.title == title
//        }
//    }
//
//    fun getSelectedHeading(): String? {
//        return _headingItems.value.firstOrNull { it.isSelected.value }?.title
//    }





    fun onPhotoHeadingApiSuccess(response: RentoPhotoHeadings, selectedList: List<String> = emptyList()) {
        _headingItems.value = response.data.map { name ->
            PhotoHeadingItem(
                title = name,
                isSelected = mutableStateOf(name in selectedList)
            )
        }
    }




//    fun check_Errors() {
//        val validations = listOf(
//            0 to _selected_Options_Form4.value.property_Name.isEmpty(),
//            1 to _selected_Options_Form4.value.property_Land_Area.isEmpty(),
//            2 to _selected_Options_Form4.value.property_Floor_Plan.isEmpty(),
//            3 to _selected_Options_Form4.value.property_Carpet_Area.isEmpty(),
//            4 to (_selected_Options_Form4.value.property_Area_Dimension_Length.isEmpty() ||
//                    _selected_Options_Form4.value.property_Area_Dimension_breadth.isEmpty()),
//            5 to _selected_Options_Form4.value.property_Facing.isEmpty(),
//            6 to (_selected_Options_Form4.value.property_Floor_Det_Total.isEmpty() ||
//                    _selected_Options_Form4.value.property_Floor_Det_Which.isEmpty()),
//            7 to _selected_Options_Form4.value.property_Ownership.isEmpty(),
//            8 to _selected_Options_Form4.value.property_Availability.isEmpty(),
//            9 to (_selected_Options_Form4.value.property_Facade_Height.isEmpty() ||
//                    _selected_Options_Form4.value.property_Facade_Width.isEmpty())
//        )
//
//        _errors.value = validations.toMap()
//    }


    private var _preview_Data_PP = MutableStateFlow<Post_Form_7_Data?>(null)
    var preview_Data_PP : StateFlow<Post_Form_7_Data?> = _preview_Data_PP.asStateFlow()

    fun add_Preview_Data(data : Post_Form_7_Data){
        _preview_Data_PP.update { data }
    }

    fun get_Preview_Data() : Post_Form_7_Data? {
        return _preview_Data_PP.value
    }


    ////// clear old data of post fomr while new clicked

    fun clear_Old_FormData(){
        clear_Selected_Fields_Form4()
        AppPreferences.save_Post_Id( 0)

        constants.PostProperty_ViewModel.first_Form_selected_PP( -1)
        constants.PostProperty_ViewModel.set_pincode3( "")
        constants.PostProperty_ViewModel.set_country3( "")
        constants.PostProperty_ViewModel.set_state3( "")
        constants.PostProperty_ViewModel.set_city3( "")
        constants.PostProperty_ViewModel.set__selectedLocality3( "")

        val latLng = LatLng(
            0.0,
            0.0
        )

        constants.PostProperty_ViewModel.set_latLng3(latLng)


        constants.PostProperty_ViewModel.select_User_Type_1PF(0)

        constants.PostProperty_ViewModel.select_Land_Cat_Id( 0)


    }


    fun clearAllPostFields(){
        _selected_Options_Form4.value = Selected_Options_Form4_DC()
        clear_PP_Fields_Data_Agri()
        clear_PP_Fields_Data_Com()
        clear_Media()
        clear_CopyMedia()
        clear_LandType_Data()
        clear_Old_FormData()
        clear_previewFormData()
        clear_Old_FormData()
        clear_Form2MainLandCat()
        clear_Form2LandCat()
        set__selectedLocality3("")
        set_latLng3(LatLng(0.0, 0.0))
        set_city3("")
        set_state3("")
        set_country3("")


     add_pp3_Data(
            PP3_API_DC(
                pincode = "",
                country = "",
                state = "",
                city = "",
                locality = ""
            )
        )

        first_Form_selected_PP(-1)
    }




    //////////// all forms handler

    // ViewModel or Repository
    private var _all_Form_Handler = MutableStateFlow(All_Form_Handler())
    val all_Form_Handler: StateFlow<All_Form_Handler> = _all_Form_Handler.asStateFlow()

    // Update specific fields using a lambda
    fun update_Selected_Draft_New_Flow(update: (All_Form_Handler) -> All_Form_Handler) {
        _all_Form_Handler.update { current -> update(current) }
    }

    // Get current value
    fun get_Selected_Draft_New_Flow(): All_Form_Handler {
        return _all_Form_Handler.value
    }

    // Reset all fields to default
    fun clear_Selected_Draft_New_Flow() {
        _all_Form_Handler.value = All_Form_Handler()
    }





}


fun Post_Form_7_Data.toSelectedOptionsForm4(): Selected_Options_Form4_DC {
    return Selected_Options_Form4_DC(
        property_Name = property_name,
        property_Floor_Plan_Bhk = bhk_type,
        property_Facing = property_facing,
        property_Carpet_Area = if (carpet_area.isNotBlank())
            listOf(carpet_area, carpet_area_unit).filter { it.isNotBlank() }.joinToString(" ")
        else "",
        property_Builtup_Area = if (built_up_area.isNotBlank())
            listOf(built_up_area, built_up_area_unit).filter { it.isNotBlank() }.joinToString(" ")
        else "",
        property_Super_Builtup_Area = if (super_built_up_area.isNotBlank())
            listOf(super_built_up_area, super_built_up_area_unit).filter { it.isNotBlank() }.joinToString(" ")
        else "",
        property_Area_Dimension_Length = if (area_length.isNotBlank())
            listOf(area_length, area_length_unit).filter { it.isNotBlank() }.joinToString(" ")
        else "",
        property_Area_Dimension_Width = if (area_width.isNotBlank())
            listOf(area_width, area_width_unit).filter { it.isNotBlank() }.joinToString(" ")
        else "",
        property_Floor_Det_Total = total_floor,
        property_Floor_Det_Which = rent_floor_no , //property_floor_no,
//        property_Ownership = property_ownership,
//        property_Availability = availability_status,
        property_No_of_Beds = no_of_bedrooms,
        property_No_of_Baths = no_of_bathrooms,
        property_No_of_Balconies = no_of_balconies,
        property_Other_Rooms = if (other_rooms.isNotBlank())
            other_rooms.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        else emptyList(),
        property_Furnished = furnishing_status,
        property_Parking = parking_available,
        property_Amenities = if (amenities.isNotBlank())
            amenities.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        else emptyList(),
        property_Highlights = if (property_highlights.isNotBlank())
            property_highlights.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        else emptyList(),
        property_Land_Area = if (property_area.isNotBlank())
            listOf(property_area, property_area_unit).filter { it.isNotBlank() }.joinToString(" ")
        else "",
        property_No_Of_OpenSides = no_of_open_sides,
        property_Boundary_Wall = boundary_wall,
        property_No_Of_Cabins = no_of_cabins,
        property_No_Of_Meeting_Rooms = no_of_meeting_rooms,
        property_Min_No_Of_Seats = min_of_seats,
        property_Max_No_Of_Seats = max_of_seats,
        property_Conference_Room = conference_room,
        property_No_Of_Stairs = no_of_staircases,
        property_Reception = reception_area,
        property_Pantry = pantry,
        property_Pantry_Size = if (pantry_size.isNotBlank())
            listOf(pantry_size, pantry_size_unit).filter { it.isNotBlank() }.joinToString(" ")
        else "",
        property_Central_AC = central_ac,
        property_Oxygen_Duct = oxygen_duct,
        property_UPS = ups,
        property_Fire_Safety = if (fire_safety_measures.isNotBlank())
            fire_safety_measures.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        else emptyList(),
        property_Lifts = lifts,
//        property_Leased_Rented = is_it_pre_leased_pre_rented,
        property_NOC_Certified = noc_certified,
        property_Occupancy = occupancy_certificate,
//        property_Previously_Used_For = office_previously_used_for,
        property_WashRoom = washroom_details
            .takeIf { it.isNotBlank() }    // handle empty string
            ?.split(",")                   // split by comma
            ?.map { it.trim() }            // remove extra spaces
            ?: emptyList()   ,
        property_Facade_Height = if (facade_height.isNotBlank())
            listOf(facade_height, facade_height_unit).filter { it.isNotBlank() }.joinToString(" ")
        else "",
        property_Facade_Width = if (facade_width.isNotBlank())
            listOf(facade_width, facade_width_unit).filter { it.isNotBlank() }.joinToString(" ")
        else "",
        property_Authority_Approved = does_local_authority,
        property_Suitable_Business_Type = if (suitable_business_type.isNotBlank())
            suitable_business_type.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        else emptyList()
    )
}

fun FormPreviewRento.toSelectedOptionsForm4(): Selected_Options_Form4_DC {

    println("==== FormPreviewRento Received Data ====")
    println("property_name = $property_name")
    println("property_area = $property_area")
    println("property_area_unit = $property_area_unit")
    println("carpet_area = $carpet_area")
    println("carpet_area_unit = $carpet_area_unit")
    println("built_up_area = $built_up_area")
    println("built_up_area_unit = $built_up_area_unit")
    println("super_built_up_area = $super_built_up_area")
    println("super_built_up_area_unit = $super_built_up_area_unit")
    println("area_length = $area_length")
    println("area_length_unit = $area_length_unit")
    println("area_width = $area_width")
    println("area_width_unit = $area_width_unit")
    println("property_facing = $property_facing")
    println("total_floor = $total_floor")
    println("rent_floor_no = $rent_floor_no")
    println("preferred_tenants = $preferred_tenants")
    println("availability_from = $availability_from")
    println("agreement_type = $agreement_type")
    println("food_preferences = $food_preferences")
    println("pets_allowed = $pets_allowed")
    println("furnishing_status = $furnishing_status")
    println("boundary_wall = $boundary_wall")
    println("parking_available = $parking_available")
    println("amenities = $amenities")
    println("property_highlights = $property_highlights")
    println("bhk_type = $bhk_type")
    println("no_of_bedrooms = $no_of_bedrooms")
    println("no_of_bathrooms = $no_of_bathrooms")
    println("no_of_balconies = $no_of_balconies")
    println("no_of_open_sides = $no_of_open_sides")
    println("other_rooms = $other_rooms")
    println("facade_height = $facade_height")
    println("facade_height_unit = $facade_height_unit")
    println("facade_width = $facade_width")
    println("facade_width_unit = $facade_width_unit")
    println("property_condition = $property_condition")
    println("no_of_cabins = $no_of_cabins")
    println("no_of_meeting_rooms = $no_of_meeting_rooms")
    println("min_of_seats = $min_of_seats")
    println("max_of_seats = $max_of_seats")
    println("conference_room = $conference_room")
    println("no_of_staircases = $no_of_staircases")
    println("reception_area = $reception_area")
    println("pantry = $pantry")
    println("pantry_size = $pantry_size")
    println("pantry_size_unit = $pantry_size_unit")
    println("central_ac = $central_ac")
    println("oxygen_duct = $oxygen_duct")
    println("ups = $ups")
    println("fire_safety_measures = $fire_safety_measures")
    println("lifts = $lifts")
    println("noc_certified = $noc_certified")
    println("occupancy_certificate = $occupancy_certificate")
    println("washroom_details = $washroom_details")
    println("does_local_authority = $does_local_authority")
    println("suitable_business_type = $suitable_business_type")
    println("is_this_property_for_rent_or_lease = $is_this_property_for_rent_or_lease")
    println("rent = $rent")
    println("rent_negotiable = $rent_negotiable")
    println("deposit_amount_month_of_rents = $deposit_amount_month_of_rents")
    println("deposit_amount_month_of_rents_type = $deposit_amount_month_of_rents_type")
    println("total_deposit = $total_deposit")
    println("duration_of_agreement = $duration_of_agreement")
    println("duration_of_agreement_type = $duration_of_agreement_type")
    println("lock_in_period = $lock_in_period")
    println("lock_in_period_type = $lock_in_period_type")
    println("notice_period = $notice_period")
    println("lease_duration_in_years = $lease_duration_in_years")
    println("lease_amount = $lease_amount")
    println("lease_negotiable = $lease_negotiable")
    println("======================================")

    return Selected_Options_Form4_DC(

        property_Name = property_name,

        // Land Area
        property_Land_Area = property_area.ifBlank { "" },
        property_area_unit = property_area_unit,

        // Carpet / Builtup / Super builtup
        property_Carpet_Area = carpet_area.ifBlank { "" },
        carpet_area_unit = carpet_area_unit,

        property_Builtup_Area = built_up_area.ifBlank { "" },
        built_up_area_unit = built_up_area_unit,

        property_Super_Builtup_Area = super_built_up_area.ifBlank { "" },
        super_built_up_area_unit = super_built_up_area_unit,

        // Length / Width
        property_Area_Dimension_Length = area_length.ifBlank { "" },
        property_Area_Dimension_Length_Unit = area_length_unit,

        property_Area_Dimension_Width = area_width.ifBlank { "" },
        property_Area_Dimension_Width_Unit = area_width_unit,

        // Facing / Floors
        property_Facing = property_facing,
        property_Floor_Det_Total = total_floor,
        property_Floor_Det_Which = rent_floor_no,

        // Preferences
        property_preferred_tenants = preferred_tenants.split(",").map { it.trim() }.filter { it.isNotEmpty() },
        property_availability_from = availability_from,
        property_agreement_type = agreement_type,
        property_food_preferences = food_preferences,
        property_pets_allowed = pets_allowed,

        // Furnishing / Parking / Amenities
        property_Furnished = furnishing_status,
        property_Boundary_Wall = boundary_wall,
        property_Parking = parking_available,
        property_Amenities = amenities.split(",").map { it.trim() }.filter { it.isNotEmpty() },
        property_Highlights = property_highlights.split(",").map { it.trim() }.filter { it.isNotEmpty() },

        property_Floor_Plan_Bhk = bhk_type,

        // Count fields
        property_No_of_Beds = no_of_bedrooms,
        property_No_of_Baths = no_of_bathrooms,
        property_No_of_Balconies = no_of_balconies,
        property_No_Of_OpenSides = no_of_open_sides,
        property_Other_Rooms = other_rooms.split(",").map { it.trim() }.filter { it.isNotEmpty() },

        // Facade
        property_Facade_Height = facade_height,
        facade_height_unit = facade_height_unit,
        property_Facade_Width = facade_width,
        facade_width_unit = facade_width_unit,

        // Additional structure info
        property_condition = property_condition,
        property_No_Of_Cabins = no_of_cabins,
        property_No_Of_Meeting_Rooms = no_of_meeting_rooms,
        property_Min_No_Of_Seats = min_of_seats,
        property_Max_No_Of_Seats = max_of_seats,
        property_Conference_Room = conference_room,
        property_No_Of_Stairs = no_of_staircases,
        property_Reception = reception_area,

        // Pantry
        property_Pantry = pantry,
        property_Pantry_Size = pantry_size,
        pantry_size_unit = pantry_size_unit,

        // Utilities
        property_Central_AC = central_ac,
        property_Oxygen_Duct = oxygen_duct,
        property_UPS = ups,
        property_Fire_Safety = fire_safety_measures.split(",").map { it.trim() }.filter { it.isNotEmpty() },
        property_Lifts = lifts,

        property_NOC_Certified = noc_certified,
        property_Occupancy = occupancy_certificate,
        property_WashRoom = washroom_details.split(",").map { it.trim() }.filter { it.isNotEmpty() },

        property_Authority_Approved = does_local_authority,
        property_Suitable_Business_Type = suitable_business_type.split(",").map { it.trim() }.filter { it.isNotEmpty() },

        // Rent - Lease setup
        property_for_rent_or_lease = is_this_property_for_rent_or_lease,
        rent = rent,
        rent_negotiable = rent_negotiable == "1",
        deposit_amount_month_of_rents = deposit_amount_month_of_rents,
        deposit_amount_month_of_rents_type = deposit_amount_month_of_rents_type,
        total_deposit = total_deposit,
        duration_of_agreement = duration_of_agreement,
        duration_of_agreement_type = duration_of_agreement_type,
        lock_in_period = lock_in_period,
        lock_in_period_type = lock_in_period_type,
        notice_period = notice_period,
//        notice_period_type = notice_period_type,
        lease_duration_in_years = lease_duration_in_years,
        lease_duration_in_years_type = duration_of_agreement_type,
        lease_amount = lease_amount,
        lease_negotiable = lease_negotiable == "1"
    )
}



fun PostProperty.toSelectedOptionsForm4_D(): Selected_Options_Form4_DC {
    return Selected_Options_Form4_DC(
        property_Name = property_name,
        property_Floor_Plan_Bhk = bhk_type,
        property_Facing = property_facing,
        property_Carpet_Area = if (carpet_area.isNotBlank())
            listOf(carpet_area).filter { it.isNotBlank() }.joinToString(" ")
        else "",
        carpet_area_unit = if (carpet_area.isNotBlank())
            listOf( carpet_area_unit).filter { it.isNotBlank() }.joinToString(" ")
        else "",
        property_Builtup_Area = if (built_up_area.isNotBlank())
            listOf(built_up_area).filter { it.isNotBlank() }.joinToString(" ")
        else "",
        built_up_area_unit = if (built_up_area.isNotBlank())
            listOf(built_up_area_unit).filter { it.isNotBlank() }.joinToString(" ")
        else "",
        property_Super_Builtup_Area = if (super_built_up_area.isNotBlank())
            listOf(super_built_up_area).filter { it.isNotBlank() }.joinToString(" ")
        else "",
        super_built_up_area_unit = if (super_built_up_area.isNotBlank())
            listOf( super_built_up_area_unit).filter { it.isNotBlank() }.joinToString(" ")
        else "",
        property_Area_Dimension_Length = if (area_length.isNotBlank())
            listOf(area_length).filter { it.isNotBlank() }.joinToString(" ")
        else "",
        property_Area_Dimension_Width = if (area_width.isNotBlank())
            listOf(area_width).filter { it.isNotBlank() }.joinToString(" ")
        else "",
        property_Area_Dimension_Length_Unit =  if (area_length.isNotBlank())
            listOf( area_length_unit).filter { it.isNotBlank() }.joinToString(" ")
        else "",
        property_Area_Dimension_Width_Unit  = if (area_width.isNotBlank())
            listOf( area_width_unit).filter { it.isNotBlank() }.joinToString(" ")
        else "",
        property_Floor_Det_Total = total_floor,
        property_Floor_Det_Which = rent_floor_no,
//        property_Ownership = property_ownership,
//        property_Availability = availability_status,
        property_No_of_Beds = no_of_bedrooms,
        property_No_of_Baths = no_of_bathrooms,
        property_No_of_Balconies = no_of_balconies,
        property_Other_Rooms = if (other_rooms.isNotBlank())
            other_rooms.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        else emptyList(),
        property_Furnished = furnishing_status,
        property_Parking = parking_available,
        property_Amenities = if (amenities.isNotBlank())
            amenities.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        else emptyList(),
        property_Highlights = if (property_highlights.isNotBlank())
            property_highlights.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        else emptyList(),
        property_Land_Area = if (property_area.isNotBlank())
            listOf(property_area).filter { it.isNotBlank() }.joinToString(" ")
        else "",
        property_area_unit = if (property_area.isNotBlank())
            listOf( property_area_unit).filter { it.isNotBlank() }.joinToString(" ")
        else "",
        property_No_Of_OpenSides = no_of_open_sides,
        property_Boundary_Wall = boundary_wall,
        property_No_Of_Cabins = no_of_cabins,
        property_No_Of_Meeting_Rooms = no_of_meeting_rooms,
        property_Min_No_Of_Seats = min_of_seats,
        property_Max_No_Of_Seats = max_of_seats,
        property_Conference_Room = conference_room,
        property_No_Of_Stairs = no_of_staircases,
        property_Reception = reception_area,
        property_Pantry = pantry,
        property_Pantry_Size = if (pantry_size.isNotBlank())
            listOf(pantry_size).filter { it.isNotBlank() }.joinToString(" ")
        else "",
        pantry_size_unit = if (pantry_size.isNotBlank())
            listOf( pantry_size_unit).filter { it.isNotBlank() }.joinToString(" ")
        else "",
        property_Central_AC = central_ac,
        property_Oxygen_Duct = oxygen_duct,
        property_UPS = ups,
        property_Fire_Safety = if (fire_safety_measures.isNotBlank())
            fire_safety_measures.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        else emptyList(),
        property_Lifts = lifts,
//        property_Leased_Rented = is_it_pre_leased_pre_rented,
        property_NOC_Certified = noc_certified,
        property_Occupancy = occupancy_certificate,
//        property_Previously_Used_For = office_previously_used_for,
        property_WashRoom = washroom_details
            .takeIf { it.isNotBlank() }    // handle empty string
            ?.split(",")                   // split by comma
            ?.map { it.trim() }            // remove extra spaces
            ?: emptyList()   ,
        property_Facade_Height = if (facade_height.isNotBlank())
            listOf(facade_height).filter { it.isNotBlank() }.joinToString(" ")
        else "",
        property_Facade_Width = if (facade_width.isNotBlank())
            listOf(facade_width).filter { it.isNotBlank() }.joinToString(" ")
        else "",
        property_Authority_Approved = does_local_authority,
        property_Suitable_Business_Type = if (suitable_business_type.isNotBlank())
            suitable_business_type.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        else emptyList()
        ,facade_width_unit = if (facade_width.isNotBlank())
            listOf( facade_width_unit).filter { it.isNotBlank() }.joinToString(" ")
        else "",
        facade_height_unit = if (facade_height.isNotBlank())
            listOf( facade_height_unit).filter { it.isNotBlank() }.joinToString(" ")
        else "",
    )
}






