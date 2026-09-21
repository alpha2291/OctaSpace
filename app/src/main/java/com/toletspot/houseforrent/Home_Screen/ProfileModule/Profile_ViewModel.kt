package com.toletspot.houseforrent.Home_Screen.ProfileModule

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Drafts.Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Blocked_Users_List_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Profile_FF_List_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Reels_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Reels_Property_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_User_Posts_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_User_Profile_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Particular_User_Interest.User_Interests_Particular_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.PostPropertyX
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.PostPropertyXXX
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.PostUser
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.SoldOuts.PostProperty
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.SoldOuts.Sold_Outs_Data
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentoMediaDC.Image
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentoMediaDC.Video
import com.toletspot.houseforrent.R
import com.toletspot.houseforrent.UI_DataClass.Flw_UnFlw_Content_DC
import com.toletspot.houseforrent.UI_DataClass.NotInterested_Options_DC
import com.toletspot.houseforrent.UI_DataClass.Notication_Subs_DC
import com.toletspot.houseforrent.UI_DataClass.Profile_FF_DC
import com.toletspot.houseforrent.UI_DataClass.Profile_Handle_Back
import com.toletspot.houseforrent.UI_DataClass.Profile_List_Back_Handler
import com.toletspot.houseforrent.UI_DataClass.Profile_Report_Options_DC
import com.toletspot.houseforrent.UI_DataClass.Settings_DC
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlin.collections.map

class Profile_ViewModel: ViewModel() {

    fun clearAllData_PRVM() {

        user_Manual_report_delete.value = false
        user_Manual_report_String_delete.value = ""
        user_Manual_report_Index_delete.value = 0
        _selectedProfileReport_Option.value = null
        _report_Submission_Success.value = true
        comment_Id_Report.value = 0
        user_Id_Report.value = 0

        _profileReport.value = _profileReport.value.map { it.copy(isSelected = false) }

        _profile_FF.value = _profile_FF.value.map {
            when (it.id) {
                0 -> it.copy(isSelected = true, count = "0", no_following = 0)
                1 -> it.copy(isSelected = false, count = "0", no_follwers = 0)
                else -> it
            }
        }

        clearAllFFData()

        _get_User_FF_List.value = emptyList()
        _get_User_FF_List_Flws.value = emptyList()
        _get_User_FF_Search_List.value = emptyList()

        _get_Blocked_Users_List.value = emptyList()

        _UnfollowClick.value = false
        _UnBlock_User.value = false
        _followRequestDelete.value = false
        _toggle_Unblock_PP.value = false

        _selected_User_profile.value = null
        _own_Profile_Content.value = null
        _block_Status.value = 0

        _switch_Profile_Mode.value = 0

        _logout_PP.value = false

        clear_Tapped_List()

        _followers_Count_BG_API_Call.value = 0
        _following_Count_BG_API_Call.value = 0

        _from_Repost.value = 0

        _selected_Profile_Id.value = 0
        _selected_User_Name.value = emptyList()
        clear_Tapped_FF_List()

        _status_Update_Profile.value = false

        _other_User_Id.value = 0
        _following_Id.value = 0
        _status_Follow_Unfollow_Delete.value = 0
        _flw_Unflw_Content_Pup.value = Flw_UnFlw_Content_DC(0, "", "", 0)

        clear_All_BF_Handler()

        _is_Search_Enabled.value = false
        _search_Text_FF.value = ""

        _current_Profile_UserId.value = 0

        _setting_Open.value = false
        _onSettingsClick.value = -1
        _verify_PP.value = false
        _selected_AS_Settings.value = "Account Settings"

        _notification_Subs.value = _notification_Subs.value.map { it.copy(isSelected = false) }
        api_NS_Ids_StringList.value = Pair(false, emptyList())

        _edit_profile_onTap.value = false
        _edit_profile_name.value = ""
        _edit_Profile_Realname.value = ""
        _change_Bio_Content.value = ""

        _profile_Posts.value = emptyList()
        _profile_Drafts.value = emptyList()
        _profile_SoldOuts.value = emptyList()
        _profile_SavedP.value = emptyList()
        user_Interest_Partcular.value = emptyList()

        selectedVideo.value = null
        from_SoldOuts.value = null
        from_Profile_Pic_Update.value = null

    }

    var onclickedProfileTab = mutableStateOf(0)

    var user_Manual_report_delete =  mutableStateOf(false)
    var user_Manual_report_String_delete = mutableStateOf("")
    var user_Manual_report_Index_delete= mutableStateOf(0)

    var selectedOwnProfileTab = mutableStateOf<OwnProfileTab>(OwnProfileTab.ACTIVE)

    fun change_OwnProfileTab(id: Int){
        if (id == 0)
            selectedOwnProfileTab.value = OwnProfileTab.ACTIVE
        else
            selectedOwnProfileTab.value = OwnProfileTab.EXPIRED
    }

    private val _selectedProfileReport_Option = MutableStateFlow<Int?>(null)
    val selected_profileReport_Option: StateFlow<Int?> = _selectedProfileReport_Option.asStateFlow()

    private val _report_Submission_Success = MutableStateFlow<Boolean>(true)
    val report_Submit_Success : StateFlow<Boolean> = _report_Submission_Success.asStateFlow()

    fun toggleReportSubmissionSuccess() {
        _report_Submission_Success.value = !_report_Submission_Success.value
    }

    fun toggle_ReportSucces_True(){
        _report_Submission_Success.value = true
    }
    fun toggle_ReportSucces_False(){
        _report_Submission_Success.value = false
    }

    private var _notInterestOptions = MutableStateFlow(
        listOf(
            NotInterested_Options_DC(
                id = 0,
                option_title = "Irrelevant to me",
                isSelected = false
            ),
            NotInterested_Options_DC(
                id = 1,
                option_title = "I’ve seen this before",
                isSelected = false
            ),
            NotInterested_Options_DC(
                id = 2,
                option_title = "Too many similar posts",
                isSelected = false
            ),
            NotInterested_Options_DC(
                id = 3,
                option_title = "Not my preference",
                isSelected = false
            ),
            NotInterested_Options_DC(
                id = 4,
                option_title = "Not useful right now",
                isSelected = false
            ),
            NotInterested_Options_DC(
                id = 5,
                option_title = "I don’t want to see this category",
                isSelected = false
            ),
            NotInterested_Options_DC(
                id = 6,
                option_title = "Other",
                isSelected = false
            ),
        )
    )

    var notInterestedOptions : StateFlow<List<NotInterested_Options_DC>> = _notInterestOptions.asStateFlow()

    private val _selectedNotInterest_Option = MutableStateFlow<Int?>(null)
    val selected_NotInterest_Option: StateFlow<Int?> = _selectedNotInterest_Option.asStateFlow()

    fun toggle_NotInterested_Options(optionId: Int) {
        _notInterestOptions.update { currentList ->
            currentList.map { option ->
                option.copy(isSelected = option.id == optionId)
            }
        }
        _selectedNotInterest_Option.value = optionId
    }
    fun getSelected_NotInterested_OptionId(): Int? {
        return _notInterestOptions.value.firstOrNull { it.isSelected }?.id
    }

    fun getSelected_NotInterested_OptionDescription(): String? {
        return _notInterestOptions.value.firstOrNull { it.isSelected }?.option_title
    }

    private var _profileReport = MutableStateFlow(
        listOf(
            Profile_Report_Options_DC(
                id = 0,
                option_title = "Inappropriate Content",
                isSelected = false
            ),
            Profile_Report_Options_DC(
                id = 1,
                option_title = "Spam or Irrelevant Content",
                isSelected = false
            ),
            Profile_Report_Options_DC(
                id = 2,
                option_title = "Copyright Infringement",
                isSelected = false
            ),
            Profile_Report_Options_DC(
                id = 3,
                option_title = "Technical Issues or Bugs",
                isSelected = false
            ),
            Profile_Report_Options_DC(
                id = 4,
                option_title = "Privacy Violations",
                isSelected = false
            ),
            Profile_Report_Options_DC(
                id = 5,
                option_title = "Cultural Insensitivity",
                isSelected = false
            ),
            Profile_Report_Options_DC(
                id = 6,
                option_title = "Something Else",
                isSelected = false
            ),
        )
    )

    val profile_Report_Options: StateFlow<List<Profile_Report_Options_DC>> = _profileReport.asStateFlow()

    fun toggle_ProfileReport_Options(optionId: Int) {
        _profileReport.update { currentList ->
            currentList.map { option ->
                option.copy(isSelected = option.id == optionId)
            }
        }
        _selectedProfileReport_Option.value = optionId
    }
    fun getSelectedProfileReportOptionId(): Int? {
        return _profileReport.value.firstOrNull { it.isSelected }?.id
    }

    fun getSelectedProfileReportOptionDescription(): String? {
        return _profileReport.value.firstOrNull { it.isSelected }?.option_title
    }

    fun clearProfileReportSelections() {
        _profileReport.update { currentList ->
            currentList.map { option ->
                option.copy(isSelected = false)
            }
        }
        _selectedProfileReport_Option.value = null
    }

    var comment_Id_Report = mutableStateOf(0)
    var user_Id_Report = mutableStateOf(0)

    private var _profile_FF = MutableStateFlow(
        listOf(
            Profile_FF_DC(
                id = 0,
                title = "Following",
                count = "200",
                isSelected = true
            ),
            Profile_FF_DC(
                id = 1,
                title = "Followers",
                count = "330",
                isSelected = false
            ),
        )
    )

    val profile_FF : StateFlow<List<Profile_FF_DC>> = _profile_FF.asStateFlow()

    fun update_Follow_Counts(noFollowers: Int, noFollowing: Int) {
        _profile_FF.value = _profile_FF.value.map { item ->
            when (item.id) {
                0 -> item.copy(no_following = noFollowing, count = noFollowing.toString())
                1 -> item.copy(no_follwers = noFollowers, count = noFollowers.toString())
                else -> item
            }
        }
    }

    private val _users_FF_List_Map = MutableStateFlow<Map<String, List<Get_Profile_FF_List_Data>>>(emptyMap())
    var users_FF_List_Map : StateFlow<Map<String, List<Get_Profile_FF_List_Data>>> = _users_FF_List_Map.asStateFlow()

    private val _users_FF_Search_Map = MutableStateFlow<Map<String, List<Get_Profile_FF_List_Data>>>(emptyMap())

    var users_FF_Search_Map : StateFlow<Map<String, List<Get_Profile_FF_List_Data>>> = _users_FF_Search_Map.asStateFlow()

    private val ffListFlowCache = mutableMapOf<String, StateFlow<List<Get_Profile_FF_List_Data>>>()
    private val ffSearchFlowCache = mutableMapOf<String, StateFlow<List<Get_Profile_FF_List_Data>>>()

    fun createFFKey(userId: Int, tab: Int): String = "${userId}_${tab}"

    fun getUserFFList(userId: Int, tab: Int): StateFlow<List<Get_Profile_FF_List_Data>> {
        val key = createFFKey(userId, tab)

        return ffListFlowCache.getOrPut(key) {
            _users_FF_List_Map.map { map ->
                val list = map[key] ?: emptyList()
                list
            }.stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                _users_FF_List_Map.value[key] ?: emptyList()
            )
        }
    }

    fun getUserFFSearchList(userId: Int, tab: Int): StateFlow<List<Get_Profile_FF_List_Data>> {
        val key = createFFKey(userId, tab)

        return ffSearchFlowCache.getOrPut(key) {
            _users_FF_Search_Map.map { map ->
                val list = map[key] ?: emptyList()
                list
            }.stateIn(
                viewModelScope,
                SharingStarted.Eagerly,
                _users_FF_Search_Map.value[key] ?: emptyList()
            )
        }
    }

    fun setUserFFList(userId: Int, tab: Int, data: List<Get_Profile_FF_List_Data>) {
        val key = createFFKey(userId, tab)
        _users_FF_List_Map.value = _users_FF_List_Map.value.toMutableMap().apply {
            put(key, data)
        }
    }

    fun appendUserFFList(userId: Int, tab: Int, newData: List<Get_Profile_FF_List_Data>) {
        val key = createFFKey(userId, tab)
        val currentData = _users_FF_List_Map.value[key] ?: emptyList()
        val updatedData = currentData + newData

        _users_FF_List_Map.value = _users_FF_List_Map.value.toMutableMap().apply {
            put(key, updatedData)
        }
    }

    fun setUserFFSearchList(userId: Int, tab: Int, data: List<Get_Profile_FF_List_Data>) {
        val key = createFFKey(userId, tab)
        _users_FF_Search_Map.value = _users_FF_Search_Map.value.toMutableMap().apply {
            put(key, data)
        }
    }

    fun clearUserFFData(userId: Int, tab: Int) {
        val key = createFFKey(userId, tab)
        _users_FF_List_Map.value = _users_FF_List_Map.value.toMutableMap().apply {
            remove(key)
        }
        _users_FF_Search_Map.value = _users_FF_Search_Map.value.toMutableMap().apply {
            remove(key)
        }

        ffListFlowCache.remove(key)
        ffSearchFlowCache.remove(key)
    }

    fun clearAllFFData() {
        _users_FF_List_Map.value = emptyMap()
        _users_FF_Search_Map.value = emptyMap()
        ffListFlowCache.clear()
        ffSearchFlowCache.clear()
    }

    fun updateImFollowedByUserId_FF_Map(
        mapUserId: Int,
        mapTab: Int,
        targetUserId: Int
    ) {
        val key = createFFKey(mapUserId, mapTab)
        val currentList = _users_FF_List_Map.value[key] ?: return

        val updatedList = currentList.map { user ->
            if (user.user_id == targetUserId) {
                user.copy(im_followed = if (user.im_followed == 0) 1 else 0)
            } else {
                user
            }
        }

        _users_FF_List_Map.value = _users_FF_List_Map.value.toMutableMap().apply {
            put(key, updatedList)
        }
    }

    fun deleteUserById_Profile_FF_Map(
        mapUserId: Int,
        mapTab: Int,
        targetUserId: Int
    )
    {
        val key = createFFKey(mapUserId, mapTab)
        val currentList = _users_FF_List_Map.value[key] ?: return

        val updatedList = currentList.filter { it.user_id != targetUserId }

        _users_FF_List_Map.value = _users_FF_List_Map.value.toMutableMap().apply {
            put(key, updatedList)
        }
    }

    fun updateImFollowedByUserId_Search_FF_NEW(targetUserId: Int) {
        val updatedSearchMap = _users_FF_Search_Map.value.mapValues { (_, list) ->
            list.map { user ->
                if (user.user_id == targetUserId) {
                    user.copy(im_followed = if (user.im_followed == 0) 1 else 0)
                } else {
                    user
                }
            }
        }
        _users_FF_Search_Map.value = updatedSearchMap
    }

    fun deleteUserById_Profile_Search_FF_NEW(targetUserId: Int) {
        val updatedSearchMap = _users_FF_Search_Map.value.mapValues { (_, list) ->
            list.filter { it.user_id != targetUserId }
        }
        _users_FF_Search_Map.value = updatedSearchMap
    }

    private var _get_User_FF_List = MutableStateFlow<List<Get_Profile_FF_List_Data?>>(emptyList())
    var get_User_FF_List : StateFlow<List<Get_Profile_FF_List_Data?>> = _get_User_FF_List.asStateFlow()

    fun set_Profile_FF(FF_Lists: List<Get_Profile_FF_List_Data?>) {
        FF_Lists.forEachIndexed { i, item ->

        }
        _get_User_FF_List.value = FF_Lists

    }

    fun deleteUserById_Profile_FF(userId: Int) {

        val before = _get_User_FF_List.value.size
        _get_User_FF_List.update { list ->
            list.filterNot { it?.user_id == userId }
        }
        val after = _get_User_FF_List.value.size
    }

    fun updateImFollowedByUserId_FF(userId: Int) {
        _get_User_FF_List.update { list ->
            list.map { item ->
                if (item?.user_id == userId) {
                    item.copy(im_followed = 1)
                } else item
            }
        }
    }

    fun updateImFollowed_toZero_ByUserId_FF(userId: Int) {
        _get_User_FF_List.update { list ->
            list.map { item ->
                if (item?.user_id == userId) {
                    item.copy(im_followed = 0)
                } else item
            }
        }
    }

    fun updateIsFollowedByUserId_FF(userId: Int) {
        _get_User_FF_List.update { list ->
            list.map { item ->
                if (item?.user_id == userId) {
                    item.copy(is_followed = 1)
                } else item
            }
        }
    }

    fun clear_FF_Lists(){
        _get_User_FF_List.value = emptyList<Get_Profile_FF_List_Data>()
    }

    private var _get_User_FF_List_Flws = MutableStateFlow<List<Get_Profile_FF_List_Data?>>(emptyList())
    var get_User_FF_List_Flws : StateFlow<List<Get_Profile_FF_List_Data?>> = _get_User_FF_List_Flws.asStateFlow()

    fun set_Profile_FF_Flws(FF_Lists: List<Get_Profile_FF_List_Data?>) {
        FF_Lists.forEachIndexed { i, item ->

        }
        _get_User_FF_List.value = FF_Lists

    }

    fun deleteUserById_Profile_FF_Flws(userId: Int) {

        val before = _get_User_FF_List.value.size
        _get_User_FF_List.update { list ->
            list.filterNot { it?.user_id == userId }
        }
        val after = _get_User_FF_List.value.size
    }

    fun updateImFollowedByUserId_FF_Flws(userId: Int) {
        _get_User_FF_List.update { list ->
            list.map { item ->
                if (item?.user_id == userId) {
                    item.copy(im_followed = 1)
                } else item
            }
        }
    }

    fun updateImFollowed_toZero_ByUserId_FF_Flws(userId: Int) {
        _get_User_FF_List.update { list ->
            list.map { item ->
                if (item?.user_id == userId) {
                    item.copy(im_followed = 0)
                } else item
            }
        }
    }

    fun updateIsFollowedByUserId_FF_Flws(userId: Int) {
        _get_User_FF_List.update { list ->
            list.map { item ->
                if (item?.user_id == userId) {
                    item.copy(is_followed = 1)
                } else item
            }
        }
    }

    private var _get_User_FF_Search_List = MutableStateFlow<List<Get_Profile_FF_List_Data?>>(emptyList())
    var get_User_FF_Search_List : StateFlow<List<Get_Profile_FF_List_Data?>> = _get_User_FF_Search_List.asStateFlow()

    fun set_Profile_Search_FF(FF_Lists: List<Get_Profile_FF_List_Data?>) {
        FF_Lists.forEachIndexed { i, item ->

        }
        _get_User_FF_Search_List.value = FF_Lists

    }

    fun deleteUserById_Profile_Search_FF(userId: Int) {

        val before = _get_User_FF_Search_List.value.size
        _get_User_FF_Search_List.update { list ->
            list.filterNot { it?.user_id == userId }
        }
        val after = _get_User_FF_Search_List.value.size

        if (_get_User_FF_List.value.any { it?.user_id == userId }) {
             _get_User_FF_List.update { list ->
                list.filterNot { it?.user_id == userId }
            }
        }
    }

    fun updateImFollowedByUserId_Search_FF(userId: Int) {
        _get_User_FF_Search_List.update { list ->
            list.map { item ->
                if (item?.user_id == userId) {
                    item.copy(im_followed = 1)
                } else item
            }
        }

        if (_get_User_FF_List.value.any { it?.user_id == userId }) {
            _get_User_FF_List.update { list ->
                list.map { item ->
                    if (item?.user_id == userId) item.copy(im_followed = 1) else item
                }
            }
        }
    }

    fun clear_SearchList_FF(){
        _get_User_FF_Search_List.value = emptyList<Get_Profile_FF_List_Data>()
    }

    private var _get_Blocked_Users_List = MutableStateFlow<List<Get_Blocked_Users_List_Data?>>(emptyList())
    var get_Blocked_Users_List : StateFlow<List<Get_Blocked_Users_List_Data?>> = _get_Blocked_Users_List.asStateFlow()

    fun set_Blocked_Users_List(BUL_Lists: List<Get_Blocked_Users_List_Data?>) {
        BUL_Lists.forEachIndexed { i, item ->

        }
        _get_Blocked_Users_List.value = BUL_Lists

    }

        fun remove_UnBlocked_User(userId: Int) {

            val before = _get_Blocked_Users_List.value.size
            _get_Blocked_Users_List.update { list ->
                list.filterNot { it?.user_id == userId }
            }
            val after = _get_Blocked_Users_List.value.size
        }

    private val _UnfollowClick = MutableStateFlow<Boolean>(false)
    var unFollowClick : StateFlow<Boolean> = _UnfollowClick.asStateFlow()

    fun setunfollowClicker(){
        _UnfollowClick.update { !it }
    }

    fun closeUnFollowClick(){
        _UnfollowClick.value = false
    }

    fun unfollow_Dismiss(){
        _UnfollowClick.value = false
    }

    private val _UnBlock_User = MutableStateFlow<Boolean>(false)
    var UnBlock_User : StateFlow<Boolean> = _UnBlock_User.asStateFlow()

    fun enable_Unblock_pp(){
        _UnBlock_User.update { !it }
    }

    fun dismiss_Unblock_pp(){
        _UnBlock_User.value = false
    }

    private val _followRequestDelete= MutableStateFlow<Boolean>(false)
    var followRequestDelete : StateFlow<Boolean> = _followRequestDelete.asStateFlow()

    fun delete_Follow_Request(){
        _followRequestDelete.update { !it }
    }

    fun delete_Follow_Dismiss(){
        _followRequestDelete.value = false
    }

    private var _toggle_Unblock_PP = MutableStateFlow<Boolean>(false)
    var toggle_Unblock_PP : StateFlow<Boolean> = _toggle_Unblock_PP.asStateFlow()

    fun set_enabler_Unblock_PP(){
        _toggle_Unblock_PP.value = true
    }
    fun set_dismisser_Unblock_PP(){
        _toggle_Unblock_PP.value = false
    }

    private val _selected_User_profile = MutableStateFlow<Get_User_Profile_Data?>(null)
    val selected_User_Profile: StateFlow<Get_User_Profile_Data?> = _selected_User_profile.asStateFlow()

    fun setSelectedUser(user: Get_User_Profile_Data) {
        _selected_User_profile.value = user
    }

    fun updateBlockedStatus_Selected_Profile(isBlocked: Int) {
        _selected_User_profile.value = _selected_User_profile.value?.copy(
            is_blocked = isBlocked
        )
    }

    fun update_OnUnBlocked_Follow(){
        _selected_User_profile.value = _selected_User_profile.value?.copy(
            im_followed = "0"
        )
    }

    fun updateReported_Selected_Profile(isBlocked: Int) {
        _selected_User_profile.value = _selected_User_profile.value?.copy(
            is_report = isBlocked
        )
    }

    fun get_Content_Others_Profile_Check() : Boolean{
        return  if (_selected_User_profile.value == null) true else false
    }

    private var _block_Status = MutableStateFlow<Int>(0)
    var block_Status : StateFlow<Int> = _block_Status.asStateFlow()

    fun get_Block_Status(): Int{
        return _block_Status.value
    }

    fun put_Block_Status( status : Int){
        _block_Status.update { status }
    }

    private var _own_Profile_Content = MutableStateFlow<Get_User_Profile_Data?>(null)

    var own_Profile_Content : StateFlow<Get_User_Profile_Data?> = _own_Profile_Content.asStateFlow()

    fun set_Content_Own_Profile(item : Get_User_Profile_Data){
        _own_Profile_Content.update { item }
    }

    fun get_Content_own_Profile() :Get_User_Profile_Data? {
        return _own_Profile_Content.value
    }

    fun get_Content_Own_Profile_Check() : Boolean{
        return  if (_own_Profile_Content.value == null) true else false
    }

    fun clearownProfileContent(){
        _own_Profile_Content.value = null
    }

    fun updateOwnProfile(
        username: String? = null,
        name: String? = null,
        bio: String? = null,
        profileImage: String? = null
    ) {
        _own_Profile_Content.update { currentProfile ->
            currentProfile?.copy(
                username = username ?: currentProfile.username,
                name = name ?: currentProfile.name,
                bio = bio ?: currentProfile.bio,
                profile_image = profileImage ?: currentProfile.profile_image
            )
        }
    }

    private val _switch_Profile_Mode = MutableStateFlow<Int?>(0)
    var switch_Profile_Mode: StateFlow<Int?> = _switch_Profile_Mode.asStateFlow()

    fun set_Profile_Mode(index: Int) {
        _switch_Profile_Mode.value = index
    }

    private var _logout_PP = MutableStateFlow<Boolean>(false)
    var logout_PP : StateFlow<Boolean> = _logout_PP.asStateFlow()

    fun enable_Logout_PP(){
        _logout_PP.value = true
    }

    fun dismiss_Logout_PP(){
        _logout_PP.value = false
    }

    private var _tapped_Profile_List = MutableStateFlow(Profile_List_Back_Handler())
    val tapped_Profile_List: StateFlow<Profile_List_Back_Handler> = _tapped_Profile_List.asStateFlow()

    fun addProfile(newProfile: Int) {
        val currentList = _tapped_Profile_List.value.profiles
        val updatedList = currentList + newProfile
        _tapped_Profile_List.value = Profile_List_Back_Handler(updatedList)
    }

    fun removeLastProfile() {
        val currentList = _tapped_Profile_List.value.profiles
        if (currentList.isNotEmpty() && currentList.size > 1) {
            val lastProfile = currentList.dropLast(1).last()
            _selected_Profile_Id.value = lastProfile

            val updatedList = currentList.dropLast(1)
            _tapped_Profile_List.value = Profile_List_Back_Handler(updatedList)

        }
    }

    fun clear_Tapped_List() {
        _tapped_Profile_List.value = Profile_List_Back_Handler(emptyList())
    }

    private var _followers_Count_BG_API_Call = MutableStateFlow<Int>(0)
    var followers_Count_BG_API_Call : StateFlow<Int> = _followers_Count_BG_API_Call.asStateFlow()

    fun get_Followers_Count_BGAPIC(): Int{
       return _followers_Count_BG_API_Call.value
    }

    fun put_Followers_Count_BGAPIC(count : Int){
        _followers_Count_BG_API_Call.update {  count }
    }

    private var _following_Count_BG_API_Call = MutableStateFlow<Int>(0)
    var following_Count_BG_API_Call : StateFlow<Int> = _following_Count_BG_API_Call.asStateFlow()

    fun get_Following_Count_BGAPIC(): Int{
        return _following_Count_BG_API_Call.value
    }

    fun put_Following_Count_BGAPIC(count : Int){
        _following_Count_BG_API_Call.update {  count }
    }

    private var _from_Repost = MutableStateFlow(0)
    var from_Repost : StateFlow<Int> = _from_Repost.asStateFlow()

    fun set_From_Repost(value : Int){
        _from_Repost.update { value }
    }

    fun get_From_Repost() : Int {
       return _from_Repost.value
    }

    private var _selected_Profile_Id = MutableStateFlow<Int>(0)
    var selected_Profile_Id : StateFlow<Int> = _selected_Profile_Id.asStateFlow()

    fun add_Selected_Profile_Id(id : Int){
        _selected_Profile_Id.update { id }
    }

    fun clear_Selected_Profile_Id(){
        _selected_Profile_Id.update { 0 }
    }

    fun clearSelectedUserProfile() {
        _selected_User_profile.value = null
    }

    private val _selected_User_Name = MutableStateFlow<List<String>>(emptyList())
    val selected_User_Name: StateFlow<List<String>> = _selected_User_Name.asStateFlow()

    val show_Selected_UserName: StateFlow<String> =
        selected_User_Name
            .map { names -> names.lastOrNull() ?: "" }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = ""
            )

    fun add_Selected_User_Name(name: String) {
        _selected_User_Name.value = _selected_User_Name.value + name
    }

    fun remove_Selected_User_Name_last() {
        if (_selected_User_Name.value.isNotEmpty()) {
            _selected_User_Name.value = _selected_User_Name.value.dropLast(1)
        }
    }

    fun clear_Selected_User_Names() {
        _selected_User_Name.value = emptyList()
    }

    data class Tap_Flw_Flg_DC(
        val id: Int,
        val tap_data: Int,
        val flw_Count: Int,
        val flg_Count: Int
    )

    private val _tapped_flw_fing_List = MutableStateFlow<List<Tap_Flw_Flg_DC>>(emptyList())
    val tapped_flw_fing_List: StateFlow<List<Tap_Flw_Flg_DC>> = _tapped_flw_fing_List.asStateFlow()

    val show_Tapped_FFs: StateFlow<Tap_Flw_Flg_DC?> =
        tapped_flw_fing_List
            .map { taps -> taps.lastOrNull() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = null
            )

    fun add_Tapped_FFs(tap: Tap_Flw_Flg_DC) {
        _tapped_flw_fing_List.value = _tapped_flw_fing_List.value + tap
    }

    fun remove_Tapped_FFs_last() {
        if (_tapped_flw_fing_List.value.isNotEmpty()) {
            _tapped_flw_fing_List.value = _tapped_flw_fing_List.value.dropLast(1)
        }
    }

    fun replace_Last_Tapped_FF(newTap: Tap_Flw_Flg_DC) {
        val currentList = _tapped_flw_fing_List.value
        if (currentList.isNotEmpty()) {
            val updatedList = currentList.dropLast(1) + newTap
            _tapped_flw_fing_List.value = updatedList
        } else {

            _tapped_flw_fing_List.value = listOf(newTap)
        }
    }

    fun clear_Tapped_FF_List(){
        _tapped_flw_fing_List.value = emptyList()
    }

    private var _status_Update_Profile = MutableStateFlow<Boolean>(false)
    var status_Update_Profile : StateFlow<Boolean> = _status_Update_Profile.asStateFlow()

    fun change_Update_profile(change: Boolean){
        _status_Update_Profile.update { change }
    }

    private var _other_User_Id = MutableStateFlow<Int>(0)
    var other_User_Id : StateFlow<Int> = _other_User_Id.asStateFlow()

    fun put_Other_User_Id(id: Int){
        _other_User_Id.update { id }
    }

    fun get_Other_User_Id() : Int{
        return _other_User_Id.value
    }

    private var _following_Id = MutableStateFlow<Int>(0)
    var following_Id : StateFlow<Int> = _following_Id.asStateFlow()

    fun put_Following_Id(id : Int){
        _following_Id.update { id }
    }

    fun get_Following_Id() : Int{
        return _following_Id.value
    }

    private var _status_Follow_Unfollow_Delete = MutableStateFlow<Int>(0)
    var status_Follow_Unfollow_Delete : StateFlow<Int> = _status_Follow_Unfollow_Delete.asStateFlow()

    fun put_follow_unfollow_Status(id : Int){
        _status_Follow_Unfollow_Delete.update { id }
    }

    fun get_follow_unfollow_Status() : Int{
        return _status_Follow_Unfollow_Delete.value
    }

    private val _flw_Unflw_Content_Pup = MutableStateFlow(
        Flw_UnFlw_Content_DC(0, "", "", 0)
    )
    val flw_Uflw_Content_Pup: StateFlow<Flw_UnFlw_Content_DC> = _flw_Unflw_Content_Pup.asStateFlow()

    fun add_Unflw_Flw_Content_Pup(id: Int, username: String, profilePic: String, status: Int = 0 ,noti_Id : Int = 0) {
        _flw_Unflw_Content_Pup.value = Flw_UnFlw_Content_DC(id, username, profilePic, status , noti_Id)
    }

    fun get_Unflw_Flw_Content_Pup(): Flw_UnFlw_Content_DC {
        return _flw_Unflw_Content_Pup.value
    }

    private val _profile_BF_Handler = MutableStateFlow<List<Profile_Handle_Back>>(emptyList())
    val profile_BF_Handler: StateFlow<List<Profile_Handle_Back>> = _profile_BF_Handler.asStateFlow()

    private var nextId = 1

    fun add_BF_Handler(item: Profile_Handle_Back) {
        val newItem = Profile_Handle_Back(
            id = nextId++,
            current_UsedId = item.current_UsedId,
            other_UserId = item.other_UserId,
            selected_Tab = item.selected_Tab,
            ff_User_Name = item.ff_User_Name,
            ff_Fw_Count = item.ff_Fw_Count,
            ff_Fg_Count = item.ff_Fg_Count,
            is_Search_Enabled = item.is_Search_Enabled,
            search_Text = item.search_Text,
            screenType = item.screenType
        )

        _profile_BF_Handler.value = _profile_BF_Handler.value + newItem

    }

    fun remove_last_BF_Handler() {
        if (_profile_BF_Handler.value.isNotEmpty()) {
            val removed = _profile_BF_Handler.value.last()
            _profile_BF_Handler.value = _profile_BF_Handler.value.dropLast(1)

        }
    }

    val currentBFHandler: StateFlow<Profile_Handle_Back?> =
        profile_BF_Handler
            .map { it.lastOrNull() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = null
            )

    fun show_Current_BF_Handler(): Profile_Handle_Back? {
        return _profile_BF_Handler.value.lastOrNull()
    }

    fun updateSelectedTab_BF_Handler(
        id: Int,
        newTab: Int,
        flw_count: Int = 0,
        fing_Count: Int = 0
    ) {
        _profile_BF_Handler.value = _profile_BF_Handler.value.map {
            if (it.id == id) {
                it.copy(
                    selected_Tab = newTab,
                    ff_Fg_Count = fing_Count,
                    ff_Fw_Count = flw_count
                )
            } else it
        }

    }

    fun getLastSelectedTab_BF_Handler(): Int? {
        return _profile_BF_Handler.value.lastOrNull()?.selected_Tab
    }

    fun update_FF_BF_CountsByUserId11(
        userId: Int,
        newFollowers: Int,
        newFollowing: Int
    ) {

        _profile_BF_Handler.value = _profile_BF_Handler.value.map { handler ->
            if (handler.current_UsedId == userId || handler.other_UserId == userId) {
                handler.copy(
                    ff_Fw_Count = newFollowers,
                    ff_Fg_Count = newFollowing
                )
            } else handler
        }

    }

    fun update_FF_BF_CountsByUserId00(
        userId: Int,
        newFollowers: Int,
        newFollowing: Int
    ) {
        _profile_BF_Handler.value = _profile_BF_Handler.value.map { handler ->

            if (handler.other_UserId == userId ||
                (handler.other_UserId == 0 && handler.current_UsedId == userId)) {
                handler.copy(
                    ff_Fw_Count = newFollowers,
                    ff_Fg_Count = newFollowing
                )
            } else {
                handler
            }
        }

    }

    fun update_FF_BF_CountsByUserId(
        userId: Int,
        newFollowers: Int,
        newFollowing: Int
    ) {
        _profile_BF_Handler.value = _profile_BF_Handler.value.map { handler ->
            val shouldUpdate = when {

                handler.other_UserId == 0 && handler.current_UsedId == userId -> true

                handler.other_UserId == userId -> true

                else -> false
            }

            if (shouldUpdate) {
                handler.copy(
                    ff_Fw_Count = newFollowers,
                    ff_Fg_Count = newFollowing
                )
            } else {
                handler
            }
        }

        _profile_BF_Handler.value.forEach { handler ->
        }
    }

    fun update_FF_BF_Search_State_Text_ByUserId(
        userId: Int,
        state: Boolean,
        text: String
    ) {
        _profile_BF_Handler.value = _profile_BF_Handler.value.map { handler ->
            if (handler.current_UsedId == userId || handler.other_UserId == userId) {
                handler.copy(
                    is_Search_Enabled = state,
                    search_Text = text
                )
            } else handler
        }
    }

    fun clear_All_BF_Handler() {
        _profile_BF_Handler.value = emptyList()
        nextId = 1
    }

    fun update_Previous_BF_Handler_FF_Counts(
        newFollowers: Int,
        newFollowing: Int
    ): Profile_Handle_Back? {
        val currentList = _profile_BF_Handler.value

        if (currentList.size >= 2) {
            val updatedList = currentList.toMutableList()
            val indexToUpdate = currentList.size - 2
            val updatedItem = currentList[indexToUpdate].copy(
                ff_Fw_Count = newFollowers,
                ff_Fg_Count = newFollowing
            )
            updatedList[indexToUpdate] = updatedItem
            _profile_BF_Handler.value = updatedList

            return updatedItem
        }

        return null
    }

    private var _is_Search_Enabled = MutableStateFlow<Boolean>(false)
    var is_Search_Enabled : StateFlow<Boolean> = _is_Search_Enabled.asStateFlow()

    fun enable_Search(){
        _is_Search_Enabled.value = true
    }

    fun disable_Search(){
        _is_Search_Enabled.value = false
    }

    fun add_Search_State(value :Boolean){
        _is_Search_Enabled.update { value }
    }

    val is_Search_Enabled_Handler: StateFlow<Boolean> =
        currentBFHandler
            .map { handler -> handler?.is_Search_Enabled == true }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = false
            )

    private var _search_Text_FF = MutableStateFlow<String>("")
    var search_Text_FF : StateFlow<String> = _search_Text_FF.asStateFlow()

    fun add_Search_Text_FF(text : String){
        _search_Text_FF.update { text }
    }

    fun clear_Search_Text_FF(){
        _search_Text_FF.value = ""
    }

    fun get_Search_Text_FF() :String {
        return _search_Text_FF.value
    }

    val search_Text_FF_Handler: StateFlow<String> =
        currentBFHandler
            .map { handler -> handler?.search_Text ?: "" }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = ""
            )

    private var _current_Profile_UserId = MutableStateFlow<Int>(0)
    var current_Profile_UserId : StateFlow<Int> = _current_Profile_UserId.asStateFlow()

    fun add_Current_Profile_UserId(id : Int){
        _current_Profile_UserId.update { id }
    }

    fun get_Current_Profile_UserId(): Int{
        return _current_Profile_UserId.value
    }

    private var _settings_List = MutableStateFlow(
        listOf(
            Settings_DC(
                id = 0,
                icon = R.drawable.accountsettingrento,
                title = "Account Settings"
            ),
            Settings_DC(
                id = 1,
                icon = R.drawable.draftsrento,
                title = "Drafts"
            ),
            Settings_DC(
                id = 2,
                icon = R.drawable.rentedoutrento,
                title = "Rented Out"
            ),
            Settings_DC(
                id = 3,
                icon = R.drawable.myinterestrento,
                title = "My Interests"
            ),
            Settings_DC(
                id = 4,
                icon = R.drawable.saveedpropsrento,
                title = "Saved Properties"
            ),
            Settings_DC(
                id = 5,
                icon = R.drawable.shareapprento,
                title = "Share the App"
            ),
        )
    )

    var settings_List : StateFlow<List<Settings_DC>> = _settings_List.asStateFlow()

    private val _setting_Open = MutableStateFlow<Boolean>(false)
    var settings_Open: StateFlow<Boolean> = _setting_Open.asStateFlow()

    fun set_open_settings(){
        _setting_Open.update { !it }
    }

    fun set_Open_False(){
        _setting_Open.update { false }
    }

    private var _onSettingsClick = MutableStateFlow<Int>(-1)
    var onSettings_Click : StateFlow<Int> = _onSettingsClick.asStateFlow()

    fun onSet_Settings_Click(id :Int){
        _onSettingsClick.update { id }
    }

    private var _acccount_settings_List = MutableStateFlow(
        listOf(
            "Edit Profile",
            "Contact",
            "Notification",
            "My Blocklist",
            "Delete Account",
            "Logout"
        )
    )

    var account_settings_List : StateFlow<List<String>> = _acccount_settings_List.asStateFlow()

    private var _verify_PP = MutableStateFlow<Boolean>(false)
    var verify_pp : StateFlow<Boolean> = _verify_PP.asStateFlow()

    fun set_verify_PP(){
        _verify_PP.value = true
    }

    fun dismiss_verify_PP() {
        _verify_PP.value = false
    }

    private var _selected_AS_Settings = MutableStateFlow<String>("Account Settings")
    var selected_AS_Settings : StateFlow<String> = _selected_AS_Settings.asStateFlow()

    fun setSelected_AS_Settings(settings : String){
        _selected_AS_Settings.update { settings }
    }

    private var _notification_Subs = MutableStateFlow(
        listOf(
            Notication_Subs_DC(
                id = 1,
                title = "New Followers",
                isSelected = false
            ),
            Notication_Subs_DC(
                id = 2,
                title = "New Likes",
                isSelected = false
            ),
            Notication_Subs_DC(
                id = 3,
                title =  "New Comments",
                isSelected = false
            ),
            Notication_Subs_DC(
                id = 4,
                title = "New Property Posts",
                isSelected = false
            ),
            Notication_Subs_DC(
                id = 5,
                title = "Profile Visit",
                isSelected = false
            )
        )
    )

    var api_NS_Ids_StringList = mutableStateOf(Pair(false, emptyList<String>()))

    var notification_Subs : StateFlow<List<Notication_Subs_DC>> = _notification_Subs.asStateFlow()

    fun set_selected_Notification_Subold(id: Int) {
        _notification_Subs.update { currentList ->
            currentList.map { item ->
                if (item.id == id) {
                    item.copy(isSelected = !item.isSelected)
                } else {
                    item
                }
            }
        }
    }

    fun set_selected_Notification_Sub_Old(id: Int) {
        val currentList = _notification_Subs.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == id }
        if (index != -1) {
            currentList[index] = currentList[index].copy(
                isSelected = !currentList[index].isSelected
            )
            _notification_Subs.value = currentList
        }
    }
    fun set_selected_Notification_Sub(id: Int) {
        val currentList = _notification_Subs.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == id }
        if (index != -1) {
            currentList[index] = currentList[index].copy(
                isSelected = true
            )
            _notification_Subs.value = currentList
        }
    }

    fun set_selected_Notification_Sub_(id: Int) {
        val currentList = _notification_Subs.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == id }
        if (index != -1) {
            currentList[index] = currentList[index].copy(
                isSelected = !currentList[index].isSelected
            )
            _notification_Subs.value = currentList
        }
    }

    private var _edit_profile_onTap = MutableStateFlow<Boolean>(false)
    var edit_profile_onTap : StateFlow<Boolean> = _edit_profile_onTap.asStateFlow()

    fun enable_Edit_Profile(){
        _edit_profile_onTap.value =  true
    }

    fun dismiss_Edit_Profile(){
        _edit_profile_onTap.value  = false
    }

    private var _edit_profile_name = MutableStateFlow("")
    var edit_profile_name : StateFlow<String> = _edit_profile_name.asStateFlow()

    fun save_new_name_edit_profile(name : String){
        _edit_profile_name.update { name  }
    }

    fun get_new_username(): String {
       return _edit_profile_name.value
    }

    private var _edit_Profile_Realname = MutableStateFlow("")
    var edit_Profile_Realname : StateFlow<String> = _edit_Profile_Realname.asStateFlow()

    fun save_New_Realname(realName : String){
        _edit_Profile_Realname.update { realName }
    }

    fun get_New_Realname() : String {
       return _edit_Profile_Realname.value
    }

    private var _change_Bio_Content = MutableStateFlow<String>("")
    var change_Bio_Content : StateFlow<String> = _change_Bio_Content.asStateFlow()

    fun save_new_Bio_Content(content : String){
        _change_Bio_Content.update { content }
    }

    fun get_new_Bio_Content(): String {
        return _change_Bio_Content.value
    }

    private val _profile_Posts = MutableStateFlow<List<Get_User_Posts_Data>>(emptyList())
    val profile_Posts: StateFlow<List<Get_User_Posts_Data>> = _profile_Posts.asStateFlow()

    fun deleteVideoById_Profile_Posts(postId: Int) {
        _profile_Posts.value = _profile_Posts.value.filter { it.user_post_id != postId }
    }

    fun updateMediaByPostId231123(
        postId: Int,
        newImages: List<Image>,
        newVideos: List<Video>
    ) {
        _profile_Posts.value = _profile_Posts.value.map { post ->
            if (post.user_post_id == postId) {
                post.copy(
                    post_property = post.post_property.copy(
                        images = newImages,
                        video = newVideos
                    )
                )
            } else post
        }
    }

    fun updateMediaProfilePostByPostId(
        postId: Int,
        newImages: List<Image>?,
        newVideos: List<Video>?
    ) {
        _profile_Posts.value = _profile_Posts.value.map { post ->
            if (post.user_post_id == postId) {
                post.copy(
                    post_property = post.post_property.copy(
                        images = newImages ?: emptyList(),
                        video = newVideos ?: emptyList()
                    )
                )
            } else post
        }
    }

    fun set_profile_Posts_data(newReels: List<Get_User_Posts_Data>) {

        _profile_Posts.value = newReels

    }

    fun clearPosts(){
        _profile_Posts.value = emptyList()
    }

    private val _profile_Drafts = MutableStateFlow<List<Data>>(emptyList())
    val profile_Drafts: StateFlow<List<Data>> = _profile_Drafts.asStateFlow()

    var dataaa: List<Data>? = null

    fun set_profile_Drafts_data(newdrafts: List<Data>) {
        newdrafts.forEachIndexed { i, item ->
        }
        _profile_Drafts.value = newdrafts

    }

    fun deleteProfileDraftByUserPostId(userPostId: Int) {
        _profile_Drafts.value = _profile_Drafts.value.filter {
            it.post_property?.user_post_id != userPostId
        }
    }

    fun deleteAllProfileDrafts() {
        _profile_Drafts.value = emptyList()
    }

    fun deleteProfileDraftsByUserPostIds(userPostIds: Set<Int>) {
        _profile_Drafts.value = _profile_Drafts.value.filter {
            it.post_property?.user_post_id !in userPostIds
        }
    }

    private val _profile_SoldOuts = MutableStateFlow<List<Get_Reels_Data>>(emptyList())
    val profile_SoldOuts: StateFlow<List<Get_Reels_Data>> = _profile_SoldOuts.asStateFlow()

    fun set_profile_SoldOuts_data(newdrafts: List<Get_Reels_Data>) {
        newdrafts.forEachIndexed { i, item ->
        }
        _profile_SoldOuts.value = newdrafts

    }

    fun deleteByPostId_Profile_SoldOuts(postId: Int) {
        _profile_SoldOuts.update { list ->
            list.filterNot { it.post_property.user_post_id == postId }
        }

    }

    private val _profile_SavedP = MutableStateFlow<List<Get_Reels_Data>>(emptyList())
    val profile_SavedP: StateFlow<List<Get_Reels_Data>> = _profile_SavedP.asStateFlow()

    fun set_profile_SavedP_data(newdrafts: List<Get_Reels_Data>) {
        newdrafts.forEachIndexed { i, item ->
        }
        _profile_SavedP.value = newdrafts

    }

    fun deleteByPostId_Profile_SavedP(postId: Int) {
        _profile_SavedP.update { list ->
            list.filterNot { it.post_property.user_post_id == postId }
        }
    }

    val user_Interest_Partcular = mutableStateOf<List<User_Interests_Particular_Data>?>(emptyList())

    fun add_user_Interests_Particular(video: List<User_Interests_Particular_Data>) {
        user_Interest_Partcular.value = video
    }

    val selectedVideo = mutableStateOf<PostUser?>(null)

    fun selectVideo(video: PostUser) {
        selectedVideo.value = video
    }

    val from_SoldOuts = mutableStateOf<Boolean?>(null)

    fun set_From_SoldOuts(video: Boolean) {
        from_SoldOuts.value = video
    }

    val from_Profile_Pic_Update = mutableStateOf<Boolean?>(null)

    fun set_From_Profile_Pic_Update(video: Boolean) {
        from_Profile_Pic_Update.value = video
    }

}

fun Get_User_Posts_Data.toGetReelsData_PR(): Get_Reels_Data {
    return Get_Reels_Data(
        cities = cities,
        country = country,
        name = name,
        phone_num = phone_num,
        phone_num_cc = phone_num_cc,
        whatsapp_num_cc = whatsapp_num_cc,
        whatsapp_num = whatsapp_num,
        email = email,
        post_property = post_property.toGetReelsPropertyData_pr(),
        profile_image = profile_image,
        state = state,
        user_id = user_id,
        user_post_id = user_post_id,
        username = username,
        is_liked = is_liked,
        is_saved = is_saved,
        thumbnail = thumbnail,
        total_likes = total_likes,
        total_comments = total_comments,
        enquiry = enquiry,
        post_interest = post_interest

    )
}

fun PostPropertyX.toGetReelsPropertyData_pr(): Get_Reels_Property_Data {
    return Get_Reels_Property_Data(
        address = address,
        amenities = amenities,
        area_length = area_length,
        area_width = area_width,

        bhk_type = bhk_type,
        boundary_wall = boundary_wall,
        built_up_area = built_up_area,
        carpet_area = carpet_area,
        central_ac = central_ac,
        city = city,
        conference_room = conference_room,
        country = country,
        created_at = created_at,
        facade_height = facade_height,
        facade_width = facade_width,
        fire_safety_measures = fire_safety_measures,
        furnishing_status = furnishing_status,
        land_categorie_id = land_categorie_id,
        land_type_id = land_type_id,
        latitude = latitude,
        lifts = lifts,

        does_local_authority = does_local_authority,
        locality = locality,
        longitude = longitude,
        max_of_seats = max_of_seats,
        min_of_seats = min_of_seats,
        no_of_Balconies = no_of_balconies,
        no_of_Bathrooms = no_of_bathrooms,
        no_of_bedrooms = no_of_bedrooms,
        no_of_cabins = no_of_cabins,
        no_of_meeting_rooms = no_of_meeting_rooms,
        no_of_open_sides = no_of_open_sides,
        no_of_Staircases = no_of_staircases,
        noc_certified = noc_certified,
        occupancy_certificate = occupancy_certificate,

        other_rooms = other_rooms,
        oxygen_duct = oxygen_duct,
        pantry = pantry,
        pantry_size = pantry_size,
        parking_available = parking_available,

        property_area = property_area,
        property_facing = property_facing,

        property_highlights = property_highlights,
        property_name = property_name,

        reception_area = reception_area,
        state = state,
        suitable_business_type = suitable_business_type,
        super_built_up_area = super_built_up_area,
        thumbnail = thumbnail,
        total_floor = total_floor,
        ups = ups,
        user_post_id = 0,
        is_report = is_report,
        user_type = post_type,
        video = video,
        washroom_details = washroom_details,
        landCategoryText = landCategoryText ?: "",
        landTypeText = landTypeText ?: "",
        is_sold = 0,

        pincode = pincode ?: "",
        draft = draft ?: "",
        agreement_type = agreement_type,
        area_length_unit = area_length_unit,
        area_width_unit = area_width_unit,
        availability_from = availability_from,
        built_up_area_unit = built_up_area,
        carpet_area_unit = carpet_area,
        deposit_amount_month_of_rents = deposit_amount_month_of_rents,
        deposit_amount_month_of_rents_type = deposit_amount_month_of_rents_type,
        duration_of_agreement = duration_of_agreement,
        duration_of_agreement_type = duration_of_agreement_type,
        facade_width_unit = facade_width_unit,
        food_preferences = food_preferences,
        is_this_property_for_rent_or_lease = is_this_property_for_rent_or_lease,
        lease_amount = lease_amount,
        lease_duration_in_years = lease_duration_in_years,
        lease_negotiable = lease_negotiable,
        lock_in_period = lock_in_period,
        lock_in_period_type = lock_in_period_type,
        map_config = map_config,
        notice_period = notice_period,
        pantry_size_unit = pantry_size_unit,
        pets_allowed = pets_allowed,
        post_type = post_type,
        preferred_tenants = preferred_tenants,
        property_area_unit = property_area_unit,
        rent = rent,
        rent_floor_no = rent_floor_no,
        rent_negotiable = rent_negotiable,
        super_built_up_area_unit = super_built_up_area_unit,
        total_deposit = total_deposit,
        images = images,
        facade_height_unit = facade_height_unit,
        U_ID = U_ID,
        status = status
    )

}

fun Get_User_Posts_Data.toReelsData(): Get_Reels_Data {
    return Get_Reels_Data(
        cities = cities,
        country = country,

        name = name,
        phone_num = phone_num,
        phone_num_cc = phone_num_cc,
        whatsapp_num_cc = whatsapp_num_cc,
        whatsapp_num = whatsapp_num,
        email = email,
        post_property = post_property.toReelsPropertyData(),
        profile_image = profile_image,
        state = state,
        user_id = user_id,
        user_post_id = user_post_id,

        username = username,
        is_liked = is_liked,
        is_saved = is_saved,

        thumbnail = thumbnail,
        total_likes = total_likes,
        total_comments = total_comments,
        enquiry = enquiry,
        post_interest = post_interest

    )
}

fun PostPropertyX.toReelsPropertyData(): Get_Reels_Property_Data {
    return Get_Reels_Property_Data(
        address = address,
        amenities = amenities,
        area_length = area_length,
        area_width = area_width,

        bhk_type = bhk_type,
        boundary_wall = boundary_wall,
        built_up_area = built_up_area,
        carpet_area = carpet_area,
        central_ac = central_ac,
        city = city,
        conference_room = conference_room,
        country = country,
        created_at = created_at,
        facade_height = facade_height,
        facade_width = facade_width,
        fire_safety_measures = fire_safety_measures,
        furnishing_status = furnishing_status,

        land_categorie_id = land_categorie_id,
        land_type_id = land_type_id,
        latitude = latitude,
        lifts = lifts,

        does_local_authority = does_local_authority,
        locality = locality,
        longitude = longitude,
        max_of_seats = max_of_seats,
        min_of_seats = min_of_seats,
        no_of_Balconies = no_of_balconies,
        no_of_Bathrooms = no_of_bathrooms,
        no_of_bedrooms = no_of_bedrooms,
        no_of_cabins = no_of_cabins,
        no_of_meeting_rooms = no_of_meeting_rooms,
        no_of_open_sides = no_of_open_sides,
        no_of_Staircases = no_of_staircases,
        noc_certified = noc_certified,
        occupancy_certificate = occupancy_certificate,

        other_rooms = other_rooms,
        oxygen_duct = oxygen_duct,
        pantry = pantry,
        pantry_size = pantry_size,
        parking_available = parking_available,

        property_area = property_area,
        property_facing = property_facing,

        property_highlights = property_highlights,
        property_name = property_name,

        reception_area = reception_area,
        state = state,
        suitable_business_type = suitable_business_type,
        super_built_up_area = super_built_up_area,
        thumbnail = "",
        total_floor = total_floor,
        ups = ups,
        user_post_id = 0,
        is_report = is_report,
        user_type = "",
        video = video,
        washroom_details = washroom_details,
        landCategoryText = landCategoryText ?: "",
        landTypeText = landTypeText ?: "",
        is_sold = 0,

        pincode = pincode ?: "",
        draft = draft ?: "",
        agreement_type = agreement_type,
        area_length_unit = area_length_unit,
        area_width_unit = area_width_unit,
        availability_from = availability_from,
        built_up_area_unit = built_up_area,
        carpet_area_unit = carpet_area,
        deposit_amount_month_of_rents = deposit_amount_month_of_rents,
        deposit_amount_month_of_rents_type = deposit_amount_month_of_rents_type,
        duration_of_agreement = duration_of_agreement,
        duration_of_agreement_type = duration_of_agreement_type,
        facade_width_unit = facade_width_unit,
        food_preferences = food_preferences,
        is_this_property_for_rent_or_lease = is_this_property_for_rent_or_lease,
        lease_amount = lease_amount,
        lease_duration_in_years = lease_duration_in_years,
        lease_negotiable = lease_negotiable,
        lock_in_period = lock_in_period,
        lock_in_period_type = lock_in_period_type,
        map_config = map_config,
        notice_period = notice_period,
        pantry_size_unit = pantry_size_unit,
        pets_allowed = pets_allowed,
        post_type = post_type,
        preferred_tenants = preferred_tenants,
        property_area_unit = property_area_unit,
        rent = rent,
        rent_floor_no = rent_floor_no,
        rent_negotiable = rent_negotiable,
        super_built_up_area_unit = super_built_up_area_unit,
        total_deposit = total_deposit,
        images = images,
        facade_height_unit = facade_height_unit,
        U_ID = U_ID,
        status = status
    )
}

fun Sold_Outs_Data.toPostUser(): PostUser {
    return PostUser(

        cities = this.cities,
        country = this.country,
        email = this.email,
        enquiry = this.enquiry,
        is_liked = this.is_liked,

        is_saved = this.is_saved,
        name = this.name,
        phone_num = this.phone_num,
        phone_num_cc = this.phone_num_cc,
        post_property = this.post_property.toPostPropertyXXX(),
        profile_image = this.profile_image,
        state = this.state,
        thumbnail = this.thumbnail,
        total_comments = this.total_comments,
        total_likes = this.total_likes,
        user_id = this.user_id,
        user_post_id = this.user_post_id,
        username = this.username,
        whatsapp_num = this.whatsapp_num,
        whatsapp_num_cc = this.whatsapp_num_cc,
        post_interest = this.post_interest
    )
}

fun PostProperty.toPostPropertyXXX(): PostPropertyXXX {
    return PostPropertyXXX(
        address = this.address,
        pincode = this.pincode,
        is_sold = 0,
        parking_available = this.parking_available,
        amenities = this.amenities,
        area_length = this.area_length,
        area_width = this.area_width,

        bhk_type = this.bhk_type,
        area_length_unit = this.area_length_unit,
        area_width_unit = this.area_width_unit,
        facade_width_unit = this.facade_width_unit,
        facade_height_unit = this.facade_height,
        carpet_area_unit = this.carpet_area_unit,
        built_up_area_unit = this.built_up_area_unit,
        super_built_up_area_unit = this.super_built_up_area_unit,
        pantry_size_unit = this.pantry_size_unit,
        boundary_wall = this.boundary_wall,
        built_up_area = this.built_up_area,
        carpet_area = this.carpet_area,
        central_ac = this.central_ac,
        city = this.city,
        conference_room = this.conference_room,
        country = this.country,
        created_at = this.created_at,
        does_local_authority = this.does_local_authority,
        facade_height = this.facade_height,
        facade_width = this.facade_width,
        fire_safety_measures = this.fire_safety_measures,
        furnishing_status = this.furnishing_status,

        is_report = this.is_report,
        land_categorie_id = this.land_categorie_id,
        landCategoryText = this.landCategoryText,
        land_type_id = this.land_type_id,
        landTypeText = this.landTypeText,
        latitude = this.latitude,
        lifts = this.lifts,
        locality = this.locality,
        longitude = this.longitude,
        max_of_seats = this.max_of_seats,
        min_of_seats = this.min_of_seats,
        no_of_balconies = this.no_of_balconies,
        no_of_bathrooms = this.no_of_bathrooms,
        no_of_bedrooms = this.no_of_bedrooms,
        no_of_cabins = this.no_of_cabins,
        no_of_meeting_rooms = this.no_of_meeting_rooms,
        no_of_open_sides = this.no_of_open_sides,
        no_of_staircases = this.no_of_staircases,
        noc_certified = this.noc_certified,
        occupancy_certificate = this.occupancy_certificate,

        other_rooms = this.other_rooms,
        oxygen_duct = this.oxygen_duct,
        pantry = this.pantry,
        pantry_size = this.pantry_size,

        property_area = this.property_area,
        property_facing = this.property_facing,

        property_highlights = this.property_highlights,
        property_name = this.property_name,

        reception_area = this.reception_area,
        state = this.state,
        suitable_business_type = this.suitable_business_type,
        super_built_up_area = this.super_built_up_area,
        thumbnail = this.thumbnail,
        total_floor = this.total_floor,
        ups = this.ups,
        user_post_id = this.user_post_id,
        user_type = this.user_type,
        video = this.video,
        washroom_details = this.washroom_details,

        draft = this.draft,
        agreement_type = this.agreement_type,
        availability_from = this.availability_from,
        deposit_amount_month_of_rents = this.deposit_amount_month_of_rents,
        deposit_amount_month_of_rents_type = this.deposit_amount_month_of_rents_type,
        duration_of_agreement = this.duration_of_agreement,
        duration_of_agreement_type = this.duration_of_agreement_type,
        food_preferences = this.food_preferences,
        is_this_property_for_rent_or_lease = this.is_this_property_for_rent_or_lease,
        lease_amount = this.lease_amount,
        lease_duration_in_years = this.lease_duration_in_years,
        lease_negotiable = this.lease_negotiable,
        lock_in_period = this.lock_in_period,
        lock_in_period_type = this.lock_in_period_type,
        map_config = this.map_config,
        notice_period = this.notice_period,
        pets_allowed = this.pets_allowed,
        post_type = this.post_type,
        preferred_tenants = this.preferred_tenants,
        property_area_unit = this.property_area_unit,
        rent = this.rent,
        rent_floor_no = this.rent_floor_no,
        rent_negotiable = this.rent_negotiable,
        total_deposit = this.total_deposit,
        images = this.images,
        U_ID = this.U_ID,
        status = this.status,
    )
}
