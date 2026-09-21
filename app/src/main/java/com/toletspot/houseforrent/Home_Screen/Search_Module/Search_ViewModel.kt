package com.toletspot.houseforrent.Home_Screen.Search_Module

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Reels_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.PopularSellersSearch.Popular_Sellers_Search_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Popular_Cities_Search_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Put_Profile_search_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.SearchFilterSort.Search_Filter_Sort_Fields_Data
import com.toletspot.houseforrent.Sort_Filter_Field_DC
import com.toletspot.houseforrent.UI_DataClass.Search_Main_Options
import com.toletspot.houseforrent.UI_DataClass.Search_Popular_Cities_DC
import com.toletspot.houseforrent.UI_DataClass.Search_Result_Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlin.collections.map

class Search_ViewModel : ViewModel() {

    fun clearAllData_SVM() {

        _search_Popular_Cities.value = _search_Popular_Cities.value.map {
            it.copy(isSelected = false)
        }

        _show_Search_Results.value = false
        _search_Results_Dropdown.value = false

        _search_Results.value = emptyList()
        _profile_Search_Results.value = emptyList()

        _selected_SearchResult_Reela_Flow.value = 0

        search_State.value = 1
        search_Area.value = ""
        search_Profile_Name.value = ""
        search_Land_Type.value = 1
        selectedOption_SEARCHTYPE = "Residential"

        _search_Sort_Filter_Fields_Data.value = null
        _selected_Sort_Filter_Fields.value = Sort_Filter_Field_DC()

        minRangeRefs.value = 2000
        maxRangeRefs.value = 10000000
        minPropertAreaRefs.value = "1"
        maxPropertAreaRefs.value = "1000"

        _search_M_Options.value = listOf(
            Search_Main_Options(id = 0, title = "Sort By", onSelected = true),
            Search_Main_Options(id = 1, title = "Property Type"),
            Search_Main_Options(id = 2, title = "Property Area"),
            Search_Main_Options(id = 3, title = "Budget"),
            Search_Main_Options(id = 4, title = "Posted By"),
            Search_Main_Options(id = 5, title = "Floor Plan"),
            Search_Main_Options(id = 6, title = "Availability Status"),
            Search_Main_Options(id = 7, title = "Furnishing Status"),

            Search_Main_Options(id = 9, title = "Parking"),
            Search_Main_Options(id = 10, title = "Open Sides"),
            Search_Main_Options(id = 11, title = "Floor Preferences"),

            Search_Main_Options(id = 13, title = "Approved Authority"),
            Search_Main_Options(id = 14, title = "Property Facing"),
            Search_Main_Options(id = 15, title = "Amenities"),
            Search_Main_Options(id = 16, title = "Property Highlights"),
            Search_Main_Options(id = 17, title = "Looking for"),
            Search_Main_Options(id = 18, title = "Posted Date"),
            Search_Main_Options(id = 19, title = "Available From"),
            Search_Main_Options(id = 20, title = "Available For"),
            Search_Main_Options(id = 21, title = "Bedrooms"),
            Search_Main_Options(id = 22, title = "Food Preference"),
            Search_Main_Options(id = 23, title = "Pets Allowed"),
            Search_Main_Options(id = 24, title = "With Photos"),
            Search_Main_Options(id = 25, title = "Agreement"),
        )

        _selectedIndex.value = 0
        _selectedIndex_Id.value = 0

        _popular_Cities.value = emptyList()
        _popular_Sellers.value = emptyList()

        _is_Search_FS_Applied.value = false

        trigger_Search_Again.value = 0

        get_Post_Id_Search_Cmt_Clicked.value = 0

        total_SearchResults_Counts.value = 0

    }

    private var _search_Popular_Cities =  MutableStateFlow(
        listOf(
            Search_Popular_Cities_DC(
                id = 0,
                title = "Banglore",
                isSelected = false
            ),
            Search_Popular_Cities_DC(
                id = 1,
                title = "Coimbatore",
                isSelected = false
            ),
            Search_Popular_Cities_DC(
                id = 2,
                title = "Chennai",
                isSelected = false
            ),
            Search_Popular_Cities_DC(
                id = 3,
                title = "Ahmedabad",
                isSelected = false
            ),
            Search_Popular_Cities_DC(
                id = 4,
                title = "Pune",
                isSelected = false
            ),
            Search_Popular_Cities_DC(
                id = 5,
                title = "Noida",
                isSelected = false
            ),
            Search_Popular_Cities_DC(
                id = 6,
                title =  "Kolkata",
                isSelected = false
            )
        )
    )

    var search_Popular_Cities : StateFlow<List<Search_Popular_Cities_DC>> = _search_Popular_Cities.asStateFlow()

    fun select_Popular_Cities(selectedId: Int) {
        _search_Popular_Cities.update { currentList ->
            currentList.map { city ->
                city.copy(isSelected = city.id == selectedId)
            }
        }
    }

    private var _show_Search_Results = MutableStateFlow<Boolean>(false)
    var show_Search_Results : StateFlow<Boolean> = _show_Search_Results.asStateFlow()

    fun enable_Search_Results(){
        _show_Search_Results.value = true
    }

    fun dismiss_Search_Results(){
        _show_Search_Results.value = false
    }

    private var _search_Results_Dropdown = MutableStateFlow<Boolean>(false)
    var search_Results_Dropdown : StateFlow<Boolean> = _search_Results_Dropdown.asStateFlow()

    fun enable_Search_Results_Dropdown(){
        _show_Search_Results.value = true
    }

    fun dismiss_Search_Results_Dropdown(){
        _show_Search_Results.value = false
    }

    private var _search_Result_Content = MutableStateFlow(
        listOf(
        Search_Result_Item(
            id = 0,
            user_Name = "user1",
            profile_Image = "",
            posted_Time = "1 hour ago",
            video_Uri = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
            type_of_Property = "Agriculture Land",
            property_Name = "Cinnamon Valleys",
            property_Location = "Canada ,Antartica",
            property_Price = "123456789",
            reviews = "12",
            rating = "5.0",
            likes_Count = "1000",
            comments_Count = "10000",
            isSaved = false,
            isLiked = false
        ),
        Search_Result_Item(
            id = 1,
            user_Name = "user2",
            profile_Image = "",
            posted_Time = "1 hour ago",
            video_Uri = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4",
            type_of_Property = "Agriculture Land",
            property_Name = "Cinnamon Valleys",
            property_Location = "Canada ,Antartica",
            property_Price = "123456789",
            reviews = "12",
            rating = "5.0",
            likes_Count = "1000",
            comments_Count = "10000",
            isSaved = false,
            isLiked = false
        ),
        Search_Result_Item(
            id = 2,
            user_Name = "user3",
            profile_Image = "",
            posted_Time = "1 hour ago",
            video_Uri = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            type_of_Property = "Agriculture Land",
            property_Name = "Cinnamon Valleys",
            property_Location = "Canada ,Antartica",
            property_Price = "123456789",
            reviews = "12",
            rating = "5.0",
            likes_Count = "1000",
            comments_Count = "10000",
            isSaved = false,
            isLiked = false
        ),
        Search_Result_Item(
            id = 3,
            user_Name = "user4",
            profile_Image = "",
            posted_Time = "1 hour ago",
            video_Uri = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4",
            type_of_Property = "Agriculture Land",
            property_Name = "Cinnamon Valleys",
            property_Location = "Canada ,Antartica",
            property_Price = "123456789",
            reviews = "12",
            rating = "5.0",
            likes_Count = "1000",
            comments_Count = "10000",
            isSaved = false,
            isLiked = false
        ),
        Search_Result_Item(
            id = 4,
            user_Name = "user5",
            profile_Image = "",
            posted_Time = "1 hour ago",
            video_Uri = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4",
            type_of_Property = "Agriculture Land",
            property_Name = "Cinnamon Valleys",
            property_Location = "Canada ,Antartica",
            property_Price = "123456789",
            reviews = "12",
            rating = "5.0",
            likes_Count = "1000",
            comments_Count = "10000",
            isSaved = false,
            isLiked = false
        ),
        Search_Result_Item(
            id = 5,
            user_Name = "user6",
            profile_Image = "",
            posted_Time = "1 hour ago",
            video_Uri = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/VolkswagenGTIReview.mp4",
            type_of_Property = "Agriculture Land",
            property_Name = "Cinnamon Valleys",
            property_Location = "Canada ,Antartica",
            property_Price = "123456789",
            reviews = "12",
            rating = "5.0",
            likes_Count = "1000",
            comments_Count = "10000",
            isSaved = false,
            isLiked = false
        ),
        Search_Result_Item(
            id = 6,
            user_Name = "user7",
            profile_Image = "",
            posted_Time = "1 hour ago",
            video_Uri = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
            type_of_Property = "Agriculture Land",
            property_Name = "Cinnamon Valleys",
            property_Location = "Canada ,Antartica",
            property_Price = "123456789",
            reviews = "12",
            rating = "5.0",
            likes_Count = "1000",
            comments_Count = "10000",
            isSaved = false,
            isLiked = false
        ),
        Search_Result_Item(
            id = 7,
            user_Name = "user8",
            profile_Image = "",
            posted_Time = "1 hour ago",
            video_Uri ="https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
            type_of_Property = "Agriculture Land",
            property_Name = "Cinnamon Valleys",
            property_Location = "Canada ,Antartica",
            property_Price = "123456789",
            reviews = "12",
            rating = "5.0",
            likes_Count = "1000",
            comments_Count = "10000",
            isSaved = false,
            isLiked = false
        ),
        Search_Result_Item(
            id = 8,
            user_Name = "user9",
            profile_Image = "",
            posted_Time = "1 hour ago",
            video_Uri = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4",
            type_of_Property = "Agriculture Land",
            property_Name = "Cinnamon Valleys",
            property_Location = "Canada ,Antartica",
            property_Price = "123456789",
            reviews = "12",
            rating = "5.0",
            likes_Count = "1000",
            comments_Count = "10000",
            isSaved = false,
            isLiked = false
        ),
        Search_Result_Item(
            id = 9,
            user_Name = "user10",
            profile_Image = "",
            posted_Time = "1 hour ago",
            video_Uri = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
            type_of_Property = "Agriculture Land",
            property_Name = "Cinnamon Valleys",
            property_Location = "Canada ,Antartica",
            property_Price = "123456789",
            reviews = "12",
            rating = "5.0",
            likes_Count = "1000",
            comments_Count = "10000",
            isSaved = false,
            isLiked = false
        ),
        )
    )

    var search_Result_Content : StateFlow<List<Search_Result_Item>> = _search_Result_Content.asStateFlow()

    fun toggle_isLiked_SR(id: Int){
        _search_Result_Content.update { currentList ->
            currentList.map { item ->
                if (item.id == id) {
                    item.copy(isLiked = !item.isLiked)
                }else{
                    item
                }

            }
        }
    }

    fun toggle_isSaved_SR(id: Int){
        _search_Result_Content.update { currentList ->
            currentList.map { item ->
                if (item.id == id) {
                    item.copy(isSaved = !item.isSaved)
                }else{
                    item
                }

            }
        }
    }

    private var _selected_SearchResult_Reela_Flow = MutableStateFlow<Int>(0)
    var selected_RS_Reels_Flow : StateFlow<Int> = _selected_SearchResult_Reela_Flow.asStateFlow()

    fun select_RS_Reels_Flow(index :Int){
        _selected_SearchResult_Reela_Flow.update { index }
    }

    private val _search_Results = MutableStateFlow<List<Get_Reels_Data>>(emptyList())
    val search_Results: StateFlow<List<Get_Reels_Data>> = _search_Results.asStateFlow()

    var selectedOption_SEARCHTYPE by  mutableStateOf("Residential")

    fun setSearchResultContent(newReels: List<Get_Reels_Data>) {
        newReels.forEachIndexed { i, item ->

        }
        _search_Results.value = newReels

    }

    fun clear_Search_Results(){
        _search_Results.value = emptyList()
    }

    fun get_Search_Results() : List<Get_Reels_Data>{
        return _search_Results.value
    }
    fun toggle_is_Report(reelId: Int) {
        _search_Results.update { currentList ->
            currentList.map { reel ->
                if (reel.user_post_id == reelId) {
                    val newReportStatus = if (reel.post_property.is_report == 1) 0 else 1
                    reel.copy(
                        post_property = reel.post_property.copy(is_report = newReportStatus)
                    )
                } else reel
            }
        }
    }

    private val _backupFilterState = MutableStateFlow<Sort_Filter_Field_DC?>(null)

    fun backupCurrentFilterState() {
        _backupFilterState.value = _selected_Sort_Filter_Fields.value?.copy()
    }

    fun restoreBackupFilterState() {
        _backupFilterState.value?.let {
            _selected_Sort_Filter_Fields.value = it.copy()
        }
    }

    fun clearBackup() {
        _backupFilterState.value = null
    }

    fun toggleLike_Reels_Search(reelId: Int) {
        _search_Results.update { currentList ->
            currentList.map { reel ->
                if (reel.user_post_id == reelId) {

                    val newLikeStatus = if (reel.is_liked == 1) 0 else 1
                    reel.copy(is_liked = newLikeStatus)
                } else reel
            }
        }
    }

    fun toggleSave_Reels_Search(reelId: Int) {
        _search_Results.update { currentList ->
            currentList.map { reel ->
                if (reel.user_post_id == reelId) {

                    val newLikeStatus = if (reel.is_saved == 1) 0 else 1
                    reel.copy(is_saved = newLikeStatus)
                } else reel
            }
        }
    }

    fun increaseLikeCount_Reels_Search(reelId: Int) {
        _search_Results.update { currentList ->
            currentList.map { reel ->
                if (reel.user_post_id == reelId) {
                    reel.copy(
                        total_likes = reel.total_likes + 1,

                    )
                } else reel
            }
        }
    }

    fun increaseCommentCount_Reels_Search(reelId: Int) {
        _search_Results.update { list ->
            list.map { reel ->
                if (reel.user_post_id == reelId) {
                    reel.copy(total_comments = reel.total_comments + 1)
                } else reel
            }
        }
    }

    fun decreaseCommentCount_Reels_Search(reelId: Int) {
        _search_Results.update { list ->
            list.map { reel ->
                if (reel.user_post_id == reelId) {
                    reel.copy(total_comments = (reel.total_comments) - 1)
                } else reel
            }
        }
    }

    fun decreaseCommentCount_Reels_Search_More(reelId: Int , count : Int) {
        _search_Results.update { list ->
            list.map { reel ->
                if (reel.user_post_id == reelId) {
                    reel.copy(total_comments = (reel.total_comments) - count)
                } else reel
            }
        }
    }

    fun decreaseLikeCount_Reels_Search(reelId: Int) {
        _search_Results.update { currentList ->
            currentList.map { reel ->
                if (reel.user_post_id == reelId) {
                    reel.copy(
                        total_likes = reel.total_likes - 1,

                    )
                } else reel
            }
        }
    }

    var get_Post_Id_Search_Cmt_Clicked = mutableStateOf(0)

    val total_SearchResults_Counts = mutableStateOf(0)

    private val _profile_Search_Results = MutableStateFlow<List<Put_Profile_search_Data>>(emptyList())
    val profile_Search_Results: StateFlow<List<Put_Profile_search_Data>> = _profile_Search_Results.asStateFlow()

    fun setProfileSearchResultContent(newReels: List<Put_Profile_search_Data>) {
        newReels.forEachIndexed { i, item ->

        }
        _profile_Search_Results.value = newReels

    }

    fun updateFollowState(userId: Int, newState: Int) {
        val currentList = profile_Search_Results.value.toMutableList()
        val index = currentList.indexOfFirst { it.user_id == userId }
        if (index != -1) {
            val updatedItem = currentList[index].copy(
                im_followed = newState
            )
            currentList[index] = updatedItem
            _profile_Search_Results.value = currentList
        }
    }

    fun updateBlockState(userId: Int){
        val currentList = profile_Search_Results.value.toMutableList()
        val index = currentList.indexOfFirst { it.user_id == userId }
        if (index != -1) {
            val updatedItem = currentList[index].copy(
                isBlocked = 0
            )
            currentList[index] = updatedItem
            _profile_Search_Results.value = currentList
        }
    }

    fun clear_profile_SearchData(){
        _profile_Search_Results.value = emptyList()
    }

    var search_State =  mutableStateOf(1)
    var search_Area =  mutableStateOf("")

    var search_Profile_Name =  mutableStateOf("")

    var search_Land_Type = mutableStateOf(1)

    private var _search_Sort_Filter_Fields_Data = MutableStateFlow<Search_Filter_Sort_Fields_Data?> (null)
    var search_Sort_Filter_Fields_Data : StateFlow<Search_Filter_Sort_Fields_Data?> = _search_Sort_Filter_Fields_Data.asStateFlow()

    fun add_sort_filter_data(data : Search_Filter_Sort_Fields_Data){
        _search_Sort_Filter_Fields_Data.update { data }
    }

    fun get_sort_filter_data():Search_Filter_Sort_Fields_Data? {
        return _search_Sort_Filter_Fields_Data.value
    }

    fun clear_sort_filter_Data(){
        _search_Sort_Filter_Fields_Data.value = null
    }

    fun activate_Search_Options(): List<Search_Main_Options> {
        val data = _search_Sort_Filter_Fields_Data.value ?: return _search_M_Options.value

        return _search_M_Options.value.map { option ->
            when (option.id) {
                0 -> option.copy(onSelected = data.sort_by.isNotEmpty())
                1 -> option.copy(onSelected = data.property_type.isNotEmpty())
                2 -> option.copy(onSelected = true)
                3 -> option.copy(onSelected = true)
                4 -> option.copy(onSelected = true)

                5 -> option.copy(onSelected = data.floor_plan.isNotEmpty())

                7 -> option.copy(onSelected = data.furnishing_status.isNotEmpty())

                9 -> option.copy(onSelected = data.parking_available.isNotEmpty())
                10 -> option.copy(onSelected = data.open_sides.isNotEmpty())
                11 -> option.copy(onSelected = data.floor_preferences.isNotEmpty())

                13 -> {
                    option.copy(onSelected = data.approved.isNotEmpty())
                }
                14 -> option.copy(onSelected = data.property_facing.isNotEmpty())
                15 -> option.copy(onSelected = data.amenities.isNotEmpty())
                16 -> option.copy(onSelected = data.property_highlights.isNotEmpty())

                17 -> option.copy(onSelected = data.rent_type.isNotEmpty())
                18 -> option.copy(onSelected = data.posted_date.isNotEmpty())
                19 -> option.copy(onSelected = data.available_from.isNotEmpty())
                20 -> option.copy(onSelected = data.available_for.isNotEmpty())
                21 -> option.copy(onSelected = data.bedrooms.isNotEmpty())
                22 -> option.copy(onSelected = data.food_preferences.isNotEmpty())
                23 -> option.copy(onSelected = data.pets_allowed.isNotEmpty())
                24 -> option.copy(onSelected = data.with_photos?.isNotEmpty() == true)
                25 -> option.copy(onSelected = data.agreement.isNotEmpty())
                else -> option
            }
        }
    }

    fun refresh_Search_Options() {
        val options = activate_Search_Options()
        val selected = options.filter { it.onSelected == true }
        _search_M_Options.value = selected
    }

    fun update_Search_Main_Options(updatedList: List<Search_Main_Options>) {
        _search_M_Options.value = updatedList
    }

    private val _search_M_Options = MutableStateFlow(
        listOf(
            Search_Main_Options(id = 0, title = "Sort By" , onSelected = true),
            Search_Main_Options(id = 1, title = "Property Type"),
            Search_Main_Options(id = 2, title = "Property Area"),
            Search_Main_Options(id = 3, title = "Budget"),
            Search_Main_Options(id = 4, title = "Posted By"),
            Search_Main_Options(id = 5, title = "Floor Plan"),
            Search_Main_Options(id = 6, title = "Availability Status"),
            Search_Main_Options(id = 7, title = "Furnishing Status"),

            Search_Main_Options(id = 9, title = "Parking"),
            Search_Main_Options(id = 10, title = "Open Sides"),
            Search_Main_Options(id = 11, title = "Floor Preferences"),

            Search_Main_Options(id = 13, title = "Approved Authority"),
            Search_Main_Options(id = 14, title = "Property Facing"),
            Search_Main_Options(id = 15, title = "Amenities"),
            Search_Main_Options(id = 16, title = "Property Highlights"),
            Search_Main_Options(id = 17, title = "Looking for"),
            Search_Main_Options(id = 18, title = "Posted Date"),
            Search_Main_Options(id = 19, title = "Available From"),
            Search_Main_Options(id = 20, title = "Available For"),
            Search_Main_Options(id = 21, title = "Bedrooms"),
            Search_Main_Options(id = 22, title = "Food Preference"),
            Search_Main_Options(id = 23, title = "Pets Allowed"),
            Search_Main_Options(id = 24, title = "With Photos"),
            Search_Main_Options(id = 25, title = "Agreement"),

            )
    )

    val search_Main_options_List: StateFlow<List<Search_Main_Options>> = _search_M_Options.asStateFlow()

    fun add_SearchSort_Main_Option(data:List<Search_Main_Options> ){
        _search_M_Options.update { data }
    }

    private val _selectedIndex = MutableStateFlow<Int>(0)
    val selectedIndex: StateFlow<Int> = _selectedIndex.asStateFlow()

    fun set_selected_SortOption_Index(index: Int){
        _selectedIndex.update { index }
    }

    private val _selectedIndex_Id = MutableStateFlow<Int>(0)
    val selectedIndex_Id: StateFlow<Int> = _selectedIndex_Id.asStateFlow()

    fun set_selected_SortOption_Index_Id(index: Int){
        _selectedIndex_Id.update { index }
    }

    var trigger_Search_Again = mutableStateOf(0)

    private var _popular_Cities = MutableStateFlow<List<Popular_Cities_Search_Data?>> (emptyList())
    var popular_Cities : StateFlow<List<Popular_Cities_Search_Data?>> = _popular_Cities.asStateFlow()

    fun add_Popular_Cities(List : List<Popular_Cities_Search_Data>)
    {
        _popular_Cities.update { List }
    }

    private var _popular_Sellers = MutableStateFlow<List<Popular_Sellers_Search_Data?>> (emptyList())
    var popular_Sellers : StateFlow<List<Popular_Sellers_Search_Data?>> = _popular_Sellers.asStateFlow()

    fun add_Popular_Sellers(List : List<Popular_Sellers_Search_Data>)
    {
        _popular_Sellers.update { List }
    }

    private var _is_Search_FS_Applied = MutableStateFlow<Boolean>(false)
    var is_Search_FS_Applied : StateFlow<Boolean> = _is_Search_FS_Applied.asStateFlow()

    fun apply_FS(){
        _is_Search_FS_Applied.value = true
    }

    fun not_Apply_FS(){
        _is_Search_FS_Applied.value =false
    }

    private var _selected_Sort_Filter_Fields = MutableStateFlow(Sort_Filter_Field_DC())
    var selected_Sort_Filter_Fields : StateFlow<Sort_Filter_Field_DC?> = _selected_Sort_Filter_Fields.asStateFlow()

    var minRangeRefs = mutableStateOf(2000L)
    var maxRangeRefs = mutableStateOf(10_000_000L)

    var minPropertAreaRefs = mutableStateOf("1")
    var maxPropertAreaRefs = mutableStateOf("1000")

    fun update_Search_SF(update: (Sort_Filter_Field_DC) -> Sort_Filter_Field_DC) {
        _selected_Sort_Filter_Fields.update { current -> update(current) }
    }

    fun get_Selected_Fields_Form() : Sort_Filter_Field_DC {
        return _selected_Sort_Filter_Fields.value
    }

    fun clear_Selected_Fields_Form4() {
        _selected_Sort_Filter_Fields.value = Sort_Filter_Field_DC()

    }

    fun toggle_Int_Field(fieldName: String, value: Int) {
        _selected_Sort_Filter_Fields.update { current ->
            val currentList = when (fieldName) {
                "land_categorie_id" -> current.land_categorie_id
                "posted_by" -> current.posted_by
                "floor_preferences" -> current.floor_preferences
                "posted_date" -> current.posted_date
                "available_from" -> current.available_from
                else -> emptyList()
            }

            val updatedList = if (currentList.contains(value)) {
                currentList - value
            } else {
                currentList + value
            }

            when (fieldName) {
                "land_categorie_id" -> current.copy(land_categorie_id = updatedList)
                "posted_by" -> current.copy(posted_by = updatedList)
                "floor_preferences" -> current.copy(floor_preferences = updatedList)
                "posted_date" -> current.copy(posted_date = updatedList)
                "available_from" -> current.copy(available_from = updatedList)
                else -> current
            }
        }
    }

    fun toggle_String_Field(fieldName: String, value: String) {
        _selected_Sort_Filter_Fields.update { current ->
            val currentList = when (fieldName) {
                "ownership" -> current.ownership

                "floor_plan" -> current.floor_plan
                "furnishing_status" -> current.furnishing_status
                "parking_available" -> current.parking_available
                "no_of_open_sides" -> current.no_of_open_sides
                "property_facing" -> current.property_facing
                "amenities" -> current.amenities
                "property_highlights" -> current.property_highlights
                "business_type" -> current.business_type
                "approved" -> current.approved

                "rent_type" -> current.rent_type

                "available_for" -> current.available_for
                "bedrooms" -> current.bedrooms
                "food_preference" -> current.food_preference
                "pets_allowed" -> current.pets_allowed

                "agreement_type" -> current.agreement_type
                else -> emptyList()
            }

            val updatedList = if (currentList.contains(value)) {
                currentList - value
            } else {
                currentList + value
            }

            when (fieldName) {
                "ownership" -> current.copy(ownership = updatedList)

                "floor_plan" -> current.copy(floor_plan = updatedList)
                "furnishing_status" -> current.copy(furnishing_status = updatedList)
                "parking_available" -> current.copy(parking_available = updatedList)
                "no_of_open_sides" -> current.copy(no_of_open_sides = updatedList)
                "property_facing" -> current.copy(property_facing = updatedList)
                "amenities" -> current.copy(amenities = updatedList)
                "property_highlights" -> current.copy(property_highlights = updatedList)
                "business_type" -> current.copy(business_type = updatedList)
                "approved" -> current.copy(approved = updatedList)

                "rent_type" -> current.copy(rent_type = updatedList)

                "available_for" -> current.copy(available_for = updatedList)
                "bedrooms" -> current.copy(bedrooms = updatedList)
                "food_preference" -> current.copy(food_preference = updatedList)
                "pets_allowed" -> current.copy(pets_allowed = updatedList)

                "agreement_type" -> current.copy(agreement_type = updatedList)
                else -> current
            }
        }
    }

    val selected_Categories_Count: StateFlow<Int> = _selected_Sort_Filter_Fields.map { it ->
        var count = 0

        if (it.short_by != null && it.short_by != 0) count++
        if (!it.recently_posted_date.isNullOrEmpty()) count++
        if (it.land_type_id != null && it.land_type_id != 0) count++
        if (!it.property_area_unit.isNullOrEmpty()) count++
        if (!it.property_area_from.isNullOrEmpty() || !it.property_area_to.isNullOrEmpty()) count++
        if (!it.budget_from.isNullOrEmpty() || !it.budget_to.isNullOrEmpty()) count++
        if (!it.search_Text.isNullOrEmpty()) count++
        if (it.land_categorie_id.isNotEmpty()) count++
        if (it.posted_by.isNotEmpty()) count++
        if (it.floor_preferences.isNotEmpty()) count++
        if (it.ownership.isNotEmpty()) count++

        if (it.floor_plan.isNotEmpty()) count++
        if (it.furnishing_status.isNotEmpty()) count++
        if (it.parking_available.isNotEmpty()) count++
        if (it.no_of_open_sides.isNotEmpty()) count++
        if (it.property_facing.isNotEmpty()) count++
        if (it.amenities.isNotEmpty()) count++
        if (it.property_highlights.isNotEmpty()) count++
        if (it.business_type.isNotEmpty()) count++
        if (it.approved.isNotEmpty()) count++

        if (it.rent_type.isNotEmpty()) count++
        if (it.posted_date.isNotEmpty()) count++
        if (it.available_from?.isNotEmpty() == true) count++
        if (it.available_for.isNotEmpty()) count++
        if (it.bedrooms.isNotEmpty()) count++
        if (it.food_preference.isNotEmpty()) count++
        if (it.pets_allowed.isNotEmpty()) count++
        if (it.with_photos == "1") count++
        if (it.agreement_type.isNotEmpty()) count++

        count
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    fun is_String_Selected(fieldName: String, value: String): Boolean {
        val current = _selected_Sort_Filter_Fields.value
        return when (fieldName) {
            "with_photos" -> current.with_photos?.contains(value)
            else -> false
        } == true
    }

    fun set_String_Field(fieldName: String, value: String) {
        _selected_Sort_Filter_Fields.update { current ->
            when (fieldName) {
                "property_area_unit" -> current.copy(property_area_unit = value)
                "with_photos" -> current.copy(with_photos = value)
                else -> current
            }
        }
    }

    fun get_Selected_Categories_Count(): Int {
        val current = _selected_Sort_Filter_Fields.value
        var count = 0

        if (current.short_by != null && current.short_by != 0) count++
        if (!current.recently_posted_date.isNullOrEmpty()) count++
        if (current.land_type_id != null && current.land_type_id != 0) count++
        if (!current.property_area_unit.isNullOrEmpty()) count++
        if (!current.property_area_from.isNullOrEmpty()) count++
        if (!current.property_area_to.isNullOrEmpty()) count++
        if (!current.budget_from.isNullOrEmpty()) count++
        if (!current.budget_to.isNullOrEmpty()) count++
        if (!current.search_Text.isNullOrEmpty()) count++

        if (current.land_categorie_id.isNotEmpty()) count++
        if (current.posted_by.isNotEmpty()) count++
        if (current.floor_preferences.isNotEmpty()) count++

        if (current.ownership.isNotEmpty()) count++

        if (current.floor_plan.isNotEmpty()) count++
        if (current.furnishing_status.isNotEmpty()) count++
        if (current.parking_available.isNotEmpty()) count++
        if (current.no_of_open_sides.isNotEmpty()) count++
        if (current.property_facing.isNotEmpty()) count++
        if (current.amenities.isNotEmpty()) count++
        if (current.property_highlights.isNotEmpty()) count++
        if (current.business_type.isNotEmpty()) count++
        if (current.approved.isNotEmpty()) count++

        return count
    }

    fun updateMinInViewModel(value: Long) {
        update_Search_SF {
            it.copy(budget_from = value.toString())
        }
        minRangeRefs.value = value
    }

    fun updateMaxInViewModel(value: Long) {
       update_Search_SF {
            it.copy(budget_to = value.toString())
        }
        maxRangeRefs.value = value
    }

}
