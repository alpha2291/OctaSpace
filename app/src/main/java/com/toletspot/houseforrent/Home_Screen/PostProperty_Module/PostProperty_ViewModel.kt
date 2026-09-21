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

    private val _postFlow = MutableStateFlow(PostFlow.NONE)
    val postFlow = _postFlow.asStateFlow()

    fun setPostFlow(flow: PostFlow) { _postFlow.value = flow }

    private val _viewDetailsFlow = MutableStateFlow(ViewDetailsFlow.NONE)
    val viewDetailsFlow = _viewDetailsFlow.asStateFlow()

    fun setViewDetailsFlow(flow: ViewDetailsFlow) { _viewDetailsFlow.value = flow }

    fun reset_ViewDetailsFlow(){
        _viewDetailsFlow.value = ViewDetailsFlow.NONE
    }

    private val _form1Error = MutableStateFlow(false)
    val form1Error = _form1Error.asStateFlow()

    fun set1formError(flow: Boolean) { _form1Error.value = flow }

    private val _form2Error = MutableStateFlow(false)
    val form2Error = _form2Error.asStateFlow()

    fun set2formError(flow: Boolean) { _form2Error.value = flow }

    private val _form3ShowMap = MutableStateFlow(false)
    val form3ShowMap = _form3ShowMap.asStateFlow()

    fun set3formShowMap(flow: Boolean) { _form3ShowMap.value = flow }

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

    fun clearAllData_PPVM() {

        _which_Post_Forms_Flow.value = -1

        _pp_Form2_LandSubTypes_Selected.value = -1
        _pp_secondform_options.value = null
        _repost_Land_Cat_Type_Ids.value = Pair(0, 0)
        _selected_Land_Type_PF2.value = 1
        _selected_Land_Cat_Id.value = -1

        _mediaList.value = emptyList()

        _postPropertyFormPage.value = 0

        _fisrt_Form_selected_PP.value = -1
        _selected_User_Type_1PF.value = -1
        _onSelected_ProType.value = 1

        step6_API_Loader.value = false
        _status_PFs.value = false
        _status_Land_Types.value = false

        _save_Draft_PP_State.value = false
        save_Changes_Draft.value = 0

        _selected_Locality.value = ""
        _pinned_Lat_Long.value = null
        _pincode3.value = ""
        _country3.value = ""
        _state3.value = ""
        _city3.value = ""
        _latLng3.value = null
        _selectedLocality3.value = ""

        _pp_3_API_Data.value = null

        _budget_Price_PF5.value = ""
        _price_Negotiation_PF5.value = false

        _pp_form_Residential_Fields.value = null
        _pp_form_Commercial_Fields.value = null
        _pp_form_Agriculture_Fields.value = null
        _selected_Options_Form4.value = Selected_Options_Form4_DC()

        _errors4.value = emptyMap()
        _errors5.value = emptyMap()
        _errors6.value = emptyMap()

        _preview_Data_PP.value = null

        _new_Draft_Data.value = null

        _all_Form_Handler.value = All_Form_Handler()

        AppPreferences.save_Post_Id(0)

    }

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

    var _repost_Land_Cat_Type_Ids = mutableStateOf(Pair(0 , 0))

    private var _postSuccessfulBtm = MutableStateFlow(false)
    var postSuccessfulBtm = _postSuccessfulBtm.asStateFlow()

    fun set_postSuccessful_State(state : Boolean){
        _postSuccessfulBtm.value = state
    }

    var indexClicked by  mutableIntStateOf(-1)

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

    private val _mediaList = MutableStateFlow<List<UploadPropertyMedia>>(emptyList())
    val mediaList = _mediaList.asStateFlow()

    var loadCopy = mutableStateOf(false)

    var copyMediaList : List<UploadPropertyMedia> = emptyList()

    fun add_CopyMedia(data : List<UploadPropertyMedia>) {
         copyMediaList = data

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

    private val _coverPhotoId = MutableStateFlow<String?>(null)
    val coverPhotoId = _coverPhotoId.asStateFlow()

    private val _headingItems = MutableStateFlow<List<PhotoHeadingItem>>(emptyList())
    val headingItems = _headingItems.asStateFlow()

    private val defaultHeadings = listOf("Kitchen", "Bedroom", "Hall", "Balcony", "Living Room", "Bathroom", "Other")
    private var headingIndex = 0

    private val MAX_IMAGES = 10
    private val MAX_VIDEOS = 5

    fun setHeadingItems(list: List<String>) {
        _headingItems.value = list.map { PhotoHeadingItem(it) }
    }

    private fun nextDefaultHeading(): String {
        val value = defaultHeadings[headingIndex % defaultHeadings.size]
        headingIndex++
        return value
    }

    fun addImagesAndUpload0(context: Context, uris: List<Uri>) {

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

    fun addImagesAndUpload(context: Context, uris: List<Uri>) {

        val currentImagesCount =
            _mediaList.value.count { !it.isVideo && it.uploadedUrl != null } +
                    _mediaList.value.count { !it.isVideo && it.isUploading }

        val remainingSlots = MAX_IMAGES - currentImagesCount

        if (remainingSlots <= 0) {
            toast("You can upload a maximum of $MAX_IMAGES images")
            return
        }

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
            copyMediaList = imageMedia + videoMedia
        }
        _mediaList.value = imageMedia + videoMedia
    }

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

    fun deleteMediaold(id: String) {
        _mediaList.value = _mediaList.value.filter { it.id != id }
        if (_coverPhotoId.value == id) _coverPhotoId.value = null
    }

    fun deleteMedia(id: String) {
        val currentList = _mediaList.value
        val deletedIndex = currentList.indexOfFirst { it.id == id }
        val deletedItem = currentList.getOrNull(deletedIndex)

        val updatedList = currentList.filter { it.id != id }

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

        val thumbnail = resolveThumbnail()

        return UploadPostRequest(
            video_urls = videos,
            image_urls = images,
            thumbnail = thumbnail
        )
    }

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
            (current - 1).coerceAtLeast(0)
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

    private var _fisrt_Form_selected_PP = MutableStateFlow<Int>(-1)
    var first_Form_selected_PP : StateFlow<Int> = _fisrt_Form_selected_PP.asStateFlow()

    fun first_Form_selected_PP(Id : Int){
        _fisrt_Form_selected_PP.update { Id }
    }

    fun get_FirstForm_Selected_PP() : Int {
        return _fisrt_Form_selected_PP.value
    }

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

    private var _status_PFs = MutableStateFlow<Boolean>(false)
    var status_PFs : StateFlow<Boolean> = _status_PFs.asStateFlow()

    fun change_Status_PFs(change : Boolean){
        _status_PFs.update { change }
    }

    private var _status_Land_Types = MutableStateFlow<Boolean>(false)
    var status_Land_Types : StateFlow<Boolean> = _status_Land_Types.asStateFlow()

    fun change_Status_Land_Types(change: Boolean){
        _status_Land_Types.update { change }
    }

    private var _selected_User_Type_1PF = MutableStateFlow<Int>(-1)
    var selected_User_Type_1PF : StateFlow<Int> = _selected_User_Type_1PF.asStateFlow()

    fun select_User_Type_1PF(id : Int) {
        _selected_User_Type_1PF.update { id }
    }

    private var _selected_Land_Type_PF2 = MutableStateFlow<Int>(1)
    var selected_Land_Type_PF2 : StateFlow<Int> = _selected_Land_Type_PF2.asStateFlow()

    fun select_Land_Type(type : Int){
        _selected_Land_Type_PF2.value = type
    }

    fun get_Land_Type(): Int {
        return _selected_Land_Type_PF2.value
    }

    private var _selected_Land_Cat_Id = MutableStateFlow<Int>(-1)
    var selected_Land_Cat_Id : StateFlow<Int> = _selected_Land_Cat_Id.asStateFlow()

    fun select_Land_Cat_Id(cat_id : Int){
        _selected_Land_Cat_Id.update { cat_id }
    }

    fun get_Selected_Land_Cat_Id() : Int {
        return _selected_Land_Cat_Id.value
    }

    private var _selected_Locality = MutableStateFlow<String>("")
    var selected_Locality : StateFlow<String> = _selected_Locality.asStateFlow()

    fun add_Selected_Locality(local : String){
        _selected_Locality.update { local }
    }

    private var _pinned_Lat_Long = MutableStateFlow<LatLng?>(null)
    var pinned_Lat_Long : StateFlow<LatLng?> = _pinned_Lat_Long.asStateFlow()

    fun add_Pinned_Lat_Long(latLng: LatLng){
        _pinned_Lat_Long.update { latLng }
    }

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

    private var _price_Negotiation_PF5 = MutableStateFlow<Boolean>(false)
    var price_Negotiation_PF5 : StateFlow<Boolean> = _price_Negotiation_PF5.asStateFlow()

    fun check_Price_Negotiation(nego : Boolean){
        _price_Negotiation_PF5.update { nego }
    }

    fun get_price_negotiation(): String {
        return if (_price_Negotiation_PF5.value) "1" else "0"
    }

    var save_Changes_Draft = mutableStateOf(0)

    private var _pp_form_Residential_Fields = MutableStateFlow<PostProperty_Stepfour_Residential_Data?> (null)
    var pp_form_Residential_Fields : StateFlow<PostProperty_Stepfour_Residential_Data?> = _pp_form_Residential_Fields.asStateFlow()

    fun add_PP_Fields_Data_Res(data : PostProperty_Stepfour_Residential_Data ){
        _pp_form_Residential_Fields.update { data }
    }

    fun clear_PP_Fields_Data_Res(){
        _pp_form_Residential_Fields.value = null
    }

    private var _pp_form_Commercial_Fields = MutableStateFlow<Post_Property_Stepfour_Commercial_Data?> (null)
    var pp_form_Commercial_Fields : StateFlow<Post_Property_Stepfour_Commercial_Data?> = _pp_form_Commercial_Fields.asStateFlow()

    fun add_PP_Fields_Data_Com(data : Post_Property_Stepfour_Commercial_Data ){
        _pp_form_Commercial_Fields.update { data }
    }

    fun clear_PP_Fields_Data_Com(){
        _pp_form_Commercial_Fields.value = null
    }

    private var _pp_form_Agriculture_Fields = MutableStateFlow<Post_Property_Stepfour_Agriculture_Data?> (null)
    var pp_form_Agriculture_Fields : StateFlow<Post_Property_Stepfour_Agriculture_Data?> = _pp_form_Agriculture_Fields.asStateFlow()

    fun add_PP_Fields_Data_Agri(data : Post_Property_Stepfour_Agriculture_Data ){
        _pp_form_Agriculture_Fields.update { data }
    }

    fun clear_PP_Fields_Data_Agri(){
        _pp_form_Agriculture_Fields.value = null
    }

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

    private var _pincode3 = MutableStateFlow<String>("")
    var pincode3 : StateFlow<String> = _pincode3.asStateFlow()

    fun set_pincode3(pincode : String){
        _pincode3.update { pincode }
    }

    fun get_pincode3(): String{
        return _pincode3.value
    }

    private var _country3 = MutableStateFlow<String>("")
    var country3 : StateFlow<String> = _country3.asStateFlow()

    fun set_country3(country : String){
        _country3.update { country }
    }

    fun get_country3() : String{
       return _country3.value
    }

    private var _state3 = MutableStateFlow<String>("")
    var state3 : StateFlow<String> = _state3.asStateFlow()

    fun set_state3(state3 : String){
        _state3.update { state3 }
    }

    fun get_state3(): String{
       return _state3.value
    }

    private var _city3 = MutableStateFlow<String>("")
    var city3 : StateFlow<String> = _city3.asStateFlow()

    fun set_city3(city : String){
        _city3.update { city }
    }

    fun get_city3(): String{
       return _city3.value
    }

    private var _latLng3 = MutableStateFlow<LatLng?>(null)
    var latLng3 : StateFlow<LatLng?> = _latLng3.asStateFlow()

    fun set_latLng3(latLng3 : LatLng?){
        _latLng3.update { latLng3 }
    }

    fun get_latLng3() : LatLng?{
       return _latLng3.value
    }

    private var _form5Rento = MutableStateFlow<RentalForm5?>(null)
    var form5Rento : StateFlow<RentalForm5?> = _form5Rento.asStateFlow()

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

    private var _new_Draft_Data = MutableStateFlow<New_Draft_Flow_Data?>(null)
    var new_Draft_Data : StateFlow<New_Draft_Flow_Data?> = _new_Draft_Data.asStateFlow()

    fun set_new_Draft_Data(data : New_Draft_Flow_Data){
        _new_Draft_Data.update { data }
    }

    fun get_new_Draft_Data(): New_Draft_Flow_Data? {
       return _new_Draft_Data.value
    }

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

    }

    fun getActiveFieldsResidential4(data: PostFormCommonPropertyData?): List<Int> {
        val activeFields = mutableListOf<Int>()
        data?.let {
            if (!it.Property_Name.isNullOrEmpty()) activeFields.add(0)

            if (!it.Select_Floor_Plane.isNullOrEmpty() && it.Select_Floor_Plane.any { str -> str.isNotBlank() }) activeFields.add(1)
            if (!it.Carpet_Area.isNullOrEmpty()) activeFields.add(2)

            if (!it.Property_Facing.isNullOrEmpty()) activeFields.add(3)
            if (!it.Floor_Details.isNullOrEmpty()) activeFields.add(4)

            if (!it.Available_From.isNullOrEmpty()) activeFields.add(5)

        }

        return activeFields
    }

    fun getActiveFieldsCommercial4(data: PostFormCommonPropertyData?): List<Int> {
        val activeFields = mutableListOf<Int>()

        data?.let {
            if (!it.Property_Name.isNullOrEmpty()) activeFields.add(0)
            if (!it.Carpet_Area.isNullOrEmpty()) activeFields.add(2)

            if (!it.Property_Facing.isNullOrEmpty()) activeFields.add(3)
            if (!it.Floor_Details.isNullOrEmpty()) activeFields.add(4)
            if (!it.Shop_Facade?.firstOrNull()?.Facade_Height.isNullOrEmpty() && !it.Shop_Facade?.firstOrNull()?.Facade_Width.isNullOrEmpty())activeFields.add(6)

            if (!it.Available_From.isNullOrEmpty()) activeFields.add(5)
        }

        return activeFields
    }

    fun getActiveFieldsAgriculture4(data: PostFormCommonPropertyData?): List<Int> {
        val activeFields = mutableListOf<Int>()

        data?.let {
            if (!it.Property_Name.isNullOrEmpty()) activeFields.add(0)
            if (!it.Property_Area.isNullOrEmpty()) activeFields.add(7)

            if (!it.Carpet_Area.isNullOrEmpty()) activeFields.add(2)

            if (!it.Property_Facing.isNullOrEmpty()) activeFields.add(3)
            if (!it.Floor_Details.isNullOrEmpty()) activeFields.add(4)

            if (!it.Available_From.isNullOrEmpty()) activeFields.add(5)
        }

        return activeFields
    }

    private var _errors4 = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val errors4: StateFlow<Map<Int, Boolean>> = _errors4.asStateFlow()

    fun check_Errors4(activeFields: List<Int>): Boolean {

        val allValidations = mapOf(
            0 to _selected_Options_Form4.value.property_Name.isEmpty(),

            1 to _selected_Options_Form4.value.property_Floor_Plan_Bhk.isEmpty(),
            2 to _selected_Options_Form4.value.property_Carpet_Area.isEmpty(),

            3 to _selected_Options_Form4.value.property_Facing.isEmpty(),
            4 to (_selected_Options_Form4.value.property_Floor_Det_Total.isEmpty() ||
                    _selected_Options_Form4.value.property_Floor_Det_Which.isEmpty()),

            5 to _selected_Options_Form4.value.property_availability_from.isEmpty(),
            6 to (_selected_Options_Form4.value.property_Facade_Height.isEmpty() ||
                    _selected_Options_Form4.value.property_Facade_Width.isEmpty()),
            7 to _selected_Options_Form4.value.property_Land_Area.isEmpty(),
        )

        val filteredValidations = allValidations.filterKeys { it in activeFields }

        _errors4.value = filteredValidations

        filteredValidations.filterValues { it }.forEach { (index, _) ->
        }

        return filteredValidations.values.none { it }
    }

    fun resetErrors4() {
        _errors4.value = emptyMap()
    }

    fun getActiveFieldsResidential5(data: Step5?): List<Int> {
        val activeFields = mutableListOf<Int>()
        data?.let {
            if (!it.Agreement_Type.isNullOrEmpty()) activeFields.add(0)

            if (!it.No_of_Bathrooms.isNullOrEmpty()) activeFields.add(1)
            if (!it.No_of_Balconies.isNullOrEmpty()) activeFields.add(2)

            if (!it.Food_Preferences.isNullOrEmpty()) activeFields.add(3)
            if (!it.Pets_Allowed.isNullOrEmpty()) activeFields.add(4)

            if (!it.Furnishing_Status.isNullOrEmpty()) activeFields.add(5)
            if (!it.Parking_Available.isNullOrEmpty()) activeFields.add(6)
            if (!it.Amenities.isNullOrEmpty()) activeFields.add(7)

        }

        return activeFields
    }

    fun getActiveFieldsCommercial5(data: Step5?): List<Int> {
        val activeFields = mutableListOf<Int>()

        data?.let {
            if (!it.No_of_Bathrooms.isNullOrEmpty()) activeFields.add(1)
            if (!it.Furnishing_Status.isNullOrEmpty()) activeFields.add(5)

            if (!it.Parking_Available.isNullOrEmpty()) activeFields.add(6)
            if (!it.Amenities.isNullOrEmpty()) activeFields.add(7)

        }

        return activeFields
    }

    fun getActiveFieldsAgriculture5(data: Step5?): List<Int> {
        val activeFields = mutableListOf<Int>()

        data?.let {
            if (!it.Agreement_Type.isNullOrEmpty()) activeFields.add(0)

            if (!it.No_of_Bathrooms.isNullOrEmpty()) activeFields.add(1)
            if (!it.No_of_Balconies.isNullOrEmpty()) activeFields.add(2)

            if (!it.Pets_Allowed.isNullOrEmpty()) activeFields.add(4)

            if (!it.Furnishing_Status.isNullOrEmpty()) activeFields.add(5)
            if (!it.Parking_Available.isNullOrEmpty()) activeFields.add(6)
            if (!it.Amenities.isNullOrEmpty()) activeFields.add(7)
        }

        return activeFields
    }

    private var _errors5 = MutableStateFlow<Map<Int, Boolean>>(emptyMap())
    val errors5: StateFlow<Map<Int, Boolean>> = _errors5.asStateFlow()

    fun check_Errors5(activeFields: List<Int>): Boolean {

        val allValidations = mapOf(
            0 to _selected_Options_Form4.value.property_agreement_type.isEmpty(),

            1 to _selected_Options_Form4.value.property_No_of_Baths.isEmpty(),
            2 to _selected_Options_Form4.value.property_No_of_Balconies.isEmpty(),

            3 to _selected_Options_Form4.value.property_food_preferences.isEmpty(),
            4 to (_selected_Options_Form4.value.property_pets_allowed.isEmpty()),

            5 to _selected_Options_Form4.value.property_Furnished.isEmpty(),
            6 to (_selected_Options_Form4.value.property_Parking.isEmpty() ),
            7 to _selected_Options_Form4.value.property_Amenities.isEmpty(),
        )

        val filteredValidations = allValidations.filterKeys { it in activeFields }

        _errors5.value = filteredValidations

        filteredValidations.filterValues { it }.forEach { (index, _) ->
        }

        return filteredValidations.values.none { it }
    }

    fun resetErrors5() {
        _errors5.value = emptyMap()
    }

    fun getActiveFieldsResidential6(data: Step6?): List<Int> {
        val activeFields = mutableListOf<Int>()
        data?.let {

            if (!it.Rent.isNullOrEmpty()) activeFields.add(0)
            if (!it.Lease.isNullOrEmpty()) activeFields.add(1)
            if (!it.Lease?.firstOrNull()?.Lease_Duration_in_Years.isNullOrEmpty()) activeFields.add(2)
        }

        return activeFields
    }

    fun getActiveFieldsCommercial6(data: Step6?): List<Int> {
        val activeFields = mutableListOf<Int>()

        data?.let {
            if (!it.Rent.isNullOrEmpty()) activeFields.add(0)
            if (!it.Lease.isNullOrEmpty()) activeFields.add(1)
            if (!it.Lease?.firstOrNull()?.Lease_Duration_in_Years.isNullOrEmpty()) activeFields.add(2)
        }

        return activeFields
    }

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

        val allValidations = mapOf(
            if (_selected_Options_Form4.value.property_for_rent_or_lease.isEmpty() ||_selected_Options_Form4.value.property_for_rent_or_lease == "Rent" ){
                0 to _selected_Options_Form4.value.rent.isEmpty()
            }
            else {
                1 to _selected_Options_Form4.value.lease_amount.isEmpty()
                2 to _selected_Options_Form4.value.lease_duration_in_years_type.isEmpty()
            }

        )

        val filteredValidations = allValidations.filterKeys { it in activeFields }

        _errors6.value = filteredValidations

        filteredValidations.filterValues { it }.forEach { (index, _) ->
        }

        return filteredValidations.values.none { it }
    }

    fun resetErrors6() {
        _errors6.value = emptyMap()
    }

    private var _form7PhotoHeadings = MutableStateFlow<RentoPhotoHeadings?> (null)
    var form7PhotoHeadings : StateFlow<RentoPhotoHeadings?> = _form7PhotoHeadings.asStateFlow()

    fun add_form7PhotoHeadings(List : RentoPhotoHeadings)
    {
        _form7PhotoHeadings.update { List }
    }

    fun onPhotoHeadingApiSuccess(response: RentoPhotoHeadings, selectedList: List<String> = emptyList()) {
        _headingItems.value = response.data.map { name ->
            PhotoHeadingItem(
                title = name,
                isSelected = mutableStateOf(name in selectedList)
            )
        }
    }

    private var _preview_Data_PP = MutableStateFlow<Post_Form_7_Data?>(null)
    var preview_Data_PP : StateFlow<Post_Form_7_Data?> = _preview_Data_PP.asStateFlow()

    fun add_Preview_Data(data : Post_Form_7_Data){
        _preview_Data_PP.update { data }
    }

    fun get_Preview_Data() : Post_Form_7_Data? {
        return _preview_Data_PP.value
    }

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

    private var _all_Form_Handler = MutableStateFlow(All_Form_Handler())
    val all_Form_Handler: StateFlow<All_Form_Handler> = _all_Form_Handler.asStateFlow()

    fun update_Selected_Draft_New_Flow(update: (All_Form_Handler) -> All_Form_Handler) {
        _all_Form_Handler.update { current -> update(current) }
    }

    fun get_Selected_Draft_New_Flow(): All_Form_Handler {
        return _all_Form_Handler.value
    }

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
        property_Floor_Det_Which = rent_floor_no ,

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

        property_NOC_Certified = noc_certified,
        property_Occupancy = occupancy_certificate,

        property_WashRoom = washroom_details
            .takeIf { it.isNotBlank() }
            ?.split(",")
            ?.map { it.trim() }
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

    return Selected_Options_Form4_DC(

        property_Name = property_name,

        property_Land_Area = property_area.ifBlank { "" },
        property_area_unit = property_area_unit,

        property_Carpet_Area = carpet_area.ifBlank { "" },
        carpet_area_unit = carpet_area_unit,

        property_Builtup_Area = built_up_area.ifBlank { "" },
        built_up_area_unit = built_up_area_unit,

        property_Super_Builtup_Area = super_built_up_area.ifBlank { "" },
        super_built_up_area_unit = super_built_up_area_unit,

        property_Area_Dimension_Length = area_length.ifBlank { "" },
        property_Area_Dimension_Length_Unit = area_length_unit,

        property_Area_Dimension_Width = area_width.ifBlank { "" },
        property_Area_Dimension_Width_Unit = area_width_unit,

        property_Facing = property_facing,
        property_Floor_Det_Total = total_floor,
        property_Floor_Det_Which = rent_floor_no,

        property_preferred_tenants = preferred_tenants.split(",").map { it.trim() }.filter { it.isNotEmpty() },
        property_availability_from = availability_from,
        property_agreement_type = agreement_type,
        property_food_preferences = food_preferences,
        property_pets_allowed = pets_allowed,

        property_Furnished = furnishing_status,
        property_Boundary_Wall = boundary_wall,
        property_Parking = parking_available,
        property_Amenities = amenities.split(",").map { it.trim() }.filter { it.isNotEmpty() },
        property_Highlights = property_highlights.split(",").map { it.trim() }.filter { it.isNotEmpty() },

        property_Floor_Plan_Bhk = bhk_type,

        property_No_of_Beds = no_of_bedrooms,
        property_No_of_Baths = no_of_bathrooms,
        property_No_of_Balconies = no_of_balconies,
        property_No_Of_OpenSides = no_of_open_sides,
        property_Other_Rooms = other_rooms.split(",").map { it.trim() }.filter { it.isNotEmpty() },

        property_Facade_Height = facade_height,
        facade_height_unit = facade_height_unit,
        property_Facade_Width = facade_width,
        facade_width_unit = facade_width_unit,

        property_condition = property_condition,
        property_No_Of_Cabins = no_of_cabins,
        property_No_Of_Meeting_Rooms = no_of_meeting_rooms,
        property_Min_No_Of_Seats = min_of_seats,
        property_Max_No_Of_Seats = max_of_seats,
        property_Conference_Room = conference_room,
        property_No_Of_Stairs = no_of_staircases,
        property_Reception = reception_area,

        property_Pantry = pantry,
        property_Pantry_Size = pantry_size,
        pantry_size_unit = pantry_size_unit,

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

        property_NOC_Certified = noc_certified,
        property_Occupancy = occupancy_certificate,

        property_WashRoom = washroom_details
            .takeIf { it.isNotBlank() }
            ?.split(",")
            ?.map { it.trim() }
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
