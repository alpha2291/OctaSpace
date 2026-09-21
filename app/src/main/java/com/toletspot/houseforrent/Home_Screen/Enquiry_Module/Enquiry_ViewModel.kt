package com.toletspot.houseforrent.Home_Screen.Enquiry_Module

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.ChatMainList.Chat_Main_List_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_My_Leads_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Reels_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Reels_Property_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.PostPropertyXXX
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.PostUser
import com.toletspot.houseforrent.UI_DataClass.Chat_Property_Structure_DC
import com.toletspot.houseforrent.UI_DataClass.Chat_User_Structure_Content_DC
import com.toletspot.houseforrent.UI_DataClass.Enquiry_Content
import com.toletspot.houseforrent.UI_DataClass.Selected_Dates_Calender
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class Enquiry_ViewModel : ViewModel(){

    fun clearAllData_EVM() {

        _selected_Etype.value = 0
        selected_Etype = _selected_Etype.asStateFlow()

        leads_selected_Filter = 4
        leads_selected_Sort = 1
        Self_Enquiry_selected_Sort = 0
        Self_Enquiry_selected_Sort_Bool = false

        report_Custom_Reason = ""
        report_Option_Selection = "Already Sold"

        selectedContent = null
        selectedEnquiry = null

        Selected_Dates_List.clear()
        _selected_Date_Range.value = null
        selected_Date_Range = _selected_Date_Range.asStateFlow()

        _my_Leads.value = emptyList()

        _self_Enquiry.value = emptyList()

        _chat_Property_Structure.value = emptyList()
        _chat_User_Structure_Content.value = emptyList()

    }

    private var _enquiryFlows = MutableStateFlow(EnquiryFlow.LEADS)
    var enquiryFlows = _enquiryFlows.asStateFlow()

    fun set_EnquiryFlow(flow : EnquiryFlow){
        _enquiryFlows.value = flow
    }

    fun clear_EnquiryFlow(){
        _enquiryFlows.value = EnquiryFlow.LEADS
    }

    var Enquiry_Types_List = mutableStateListOf(
        "My Leads",
        "Self Enquiry",
        "Message"
    )

    var report_Types_List = mutableStateListOf(
        "Already Sold",
        "Low Budget",
        "Fake Enquiry",
        "Already Booked",
        "Wrong Location Preference"
    )

    var report_Custom_Reason by mutableStateOf("")
    var report_Option_Selection by mutableStateOf("Already Sold")

    var leads_selected_Filter by mutableStateOf(4)
    var leads_selected_Sort by mutableStateOf(1)
    var Self_Enquiry_selected_Sort by mutableStateOf(0)
    var Self_Enquiry_selected_Sort_Bool by mutableStateOf(false)

    val E_MyLeadsList = listOf(
        Enquiry_Content(
            isWhich = 1,
            isWhich_Content = "Enquired",
            profile_Pic = 0,
            user_Name = "John Doe",
            time = "1 hr ago",
            cont_No = "1234567890",
            mail_Id = "john@example.com",
            location = "Peelamedu, Coimbatore",
            message = "Looking for a plot near airport.",
            type_Of_Land = 1,
            name_Of_Land = "Green Acres",
            land_Location = "Avinashi Road"
        ),
        Enquiry_Content(
            isWhich = 2,
            isWhich_Content = "Shown Interest",
            profile_Pic = 1,
            user_Name = "Jane Smith",
            time = "2 hrs ago",
            cont_No = "9876543210",
            mail_Id = "jane@example.com",
            location = "RS Puram, Coimbatore",
            message = "Need DTCP land with road access.",
            type_Of_Land = 2,
            name_Of_Land = "Sunrise Gardens",
            land_Location = "Thudiyalur",
            SI_property_Type = "Residential",
            SI_price_Range = "₹5L - ₹20L"
        ),
        Enquiry_Content(
            isWhich = 2,
            isWhich_Content = "Shown Interest",
            profile_Pic = 1,
            user_Name = "Jane Smith",
            time = "2 hrs ago",
            cont_No = "9876543210",
            mail_Id = "jane@example.com",
            location = "RS Puram, Coimbatore",
            message = "Need DTCP land with road access.",
            type_Of_Land = 2,
            name_Of_Land = "Sunrise Gardens",
            land_Location = "Thudiyalur"
        ),
        Enquiry_Content(
            isWhich = 4,
            isWhich_Content = "Declined",
            profile_Pic = 1,
            user_Name = "Jane Smith",
            time = "2 hrs ago",
            cont_No = "9876543210",
            mail_Id = "jane@example.com",
            location = "RS Puram, Coimbatore",
            message = "Need DTCP land with road access.",
            type_Of_Land = 2,
            name_Of_Land = "Sunrise Gardens",
            land_Location = "Thudiyalur"
        )
    )

    val E_SelfEnquiry_List = listOf(
        Enquiry_Content(
            isWhich = 3,
            profile_Pic = 0,
            user_Name = "John Doe",
            time = "1 hr ago",
            cont_No = "1234567890",
            mail_Id = "john@example.com",
            location = "Peelamedu, Coimbatore",
            message = "Looking for a plot near airport.",
            type_Of_Land = 1,
            name_Of_Land = "Green Acres",
            land_Location = "Avinashi Road"
        ),
        Enquiry_Content(
            isWhich = 3,
            profile_Pic = 1,
            user_Name = "Jane Smith",
            time = "2 hrs ago",
            cont_No = "9876543210",
            mail_Id = "jane@example.com",
            location = "RS Puram, Coimbatore",
            message = "Need DTCP land with road access.",
            type_Of_Land = 2,
            name_Of_Land = "Sunrise Gardens",
            land_Location = "Thudiyalur"
        ),
        Enquiry_Content(
            isWhich = 5,
            profile_Pic = 1,
            isWhich_Content = "Declined",
            user_Name = "Jane Smith",
            time = "2 hrs ago",
            cont_No = "9876543210",
            mail_Id = "jane@example.com",
            location = "RS Puram, Coimbatore",
            message = "Need DTCP land with road access.",
            type_Of_Land = 2,
            name_Of_Land = "Sunrise Gardens",
            land_Location = "Thudiyalur"
        )
    )

    var leadType_Filter_List = mutableStateListOf(
        "Enquired",
        "Shown Interest",
        "Declined",
        "Both",
    )

    var lead_Sort_List = mutableStateListOf(
        "Newest First",
        "Oldest First",
        "Last 7 days",
        "Last 30 days",
        "Custom Date"
    )

    var message_Filter_List = mutableStateListOf(
        "All Enquiries",
        "Received Enquiry",
        "Self Enquiry"
    )

    var selected_Msg_Filter = mutableStateOf(1)

    private var _selected_Etype = MutableStateFlow(0)
    var selected_Etype : StateFlow<Int> = _selected_Etype.asStateFlow()

    fun enquiries_View(view : Int){
        _selected_Etype.update { view }
    }

    var Selected_Dates_List = mutableStateListOf<Selected_Dates_Calender>()

   private var _selected_Date_Range = MutableStateFlow<Selected_Dates_Calender?> ( null)
    var selected_Date_Range : StateFlow<Selected_Dates_Calender?> = _selected_Date_Range.asStateFlow()

    fun add_Selected_Date_Range(data : Selected_Dates_Calender){
        _selected_Date_Range.update { data }
    }

    fun clear_Selected_DateRange(){
        _selected_Date_Range.value = null
    }

    var selectedContent by mutableStateOf<Enquiry_Content?>(null)

    var _chat_Property_Structure = MutableStateFlow(
        listOf(
            Chat_Property_Structure_DC(
                id = 0,
                enquiry_Type = 0,
                property_Location = "Location1",
                property_Name = "Valleys1"
            ),
            Chat_Property_Structure_DC(
                id = 1,
                enquiry_Type = 1,
                property_Location = "Location2",
                property_Name = "Valleys2"
            ),
            Chat_Property_Structure_DC(
                id = 2,
                enquiry_Type = 2,
                property_Location = "Location3",
                property_Name = "Valleys3"
            ),
        )
    )

    var chat_Property_Structure_Content : StateFlow<List<Chat_Property_Structure_DC>> = _chat_Property_Structure.asStateFlow()

    var _chat_User_Structure_Content = MutableStateFlow(
        listOf(
            Chat_User_Structure_Content_DC(
                id = 0,
                user_Name = "user0",
                profile_Pic = 0,
                last_Done_Message = "hello0",
                timing = "Just now",
                count_of_Message = 3,
            ),
            Chat_User_Structure_Content_DC(
                id = 1,
                user_Name = "user1",
                profile_Pic = 0,
                last_Done_Message = "hello1",
                timing = "Just now",
                count_of_Message = 4,
            ),
            Chat_User_Structure_Content_DC(
                id = 2,
                user_Name = "user2",
                profile_Pic = 0,
                last_Done_Message = "hello2",
                timing = "Just now",
                count_of_Message = 5,
            ),
            Chat_User_Structure_Content_DC(
                id = 3,
                user_Name = "user3",
                profile_Pic = 0,
                last_Done_Message = "hello3",
                timing = "Just now",
                count_of_Message = 6,
            ),
            Chat_User_Structure_Content_DC(
                id = 4,
                user_Name = "user4",
                profile_Pic = 0,
                last_Done_Message = "hello4",
                timing = "Just now",
                count_of_Message = 7,
            ),
        )
    )

    var chat_User_Content : StateFlow<List<Chat_User_Structure_Content_DC>> = _chat_User_Structure_Content.asStateFlow()

    var selectedEnquiry by  mutableStateOf<Get_My_Leads_Data?>(null)

    private val _my_Leads = MutableStateFlow<List<Get_My_Leads_Data>>(emptyList())
    val my_Leads: StateFlow<List<Get_My_Leads_Data>> = _my_Leads.asStateFlow()

    fun setMyLeads(newReels: List<Get_My_Leads_Data>) {

        val updatedList = newReels.mapIndexed { index, item ->

            val displayType = if (item.search_type == 3) 4 else item.search_type

            println("  [$index] enquire_id=${item.enquiry_details?.enquire_id}, " +
                    "search_type=${item.search_type} → isWhich=$displayType")

            item.copy(isWhich = displayType)
        }

        _my_Leads.value = updatedList

    }

    fun toggleLike_Reels_Enquiry(reelId: Int) {
        _my_Leads.update { currentList ->
            currentList.map { reel ->
                if (reel.post_user.user_post_id == reelId) {

                    val newLikeStatus = if (reel.post_user.is_liked == 1) 0 else 1
                    reel.copy(
                        post_user = reel.post_user.copy(is_liked = newLikeStatus),

                    )
                } else reel
            }
        }
    }

    fun toggle__video_Report_Enquiry(reelId: Int) {
        _my_Leads.update { currentList ->
            currentList.map { reel ->
                if (reel.post_user.user_post_id == reelId) {

                    val currentStatus = reel.post_user.post_property?.is_report
                    val newReportStatus = if (currentStatus == 1) 0 else 1

                    reel.copy(
                        post_user = reel.post_user.copy(
                            post_property = reel.post_user.post_property.copy(is_report = newReportStatus)
                        ),
                    )

                } else reel
            }
        }
    }

    fun toggleSave_Reels_Enquiry(reelId: Int) {
        _my_Leads.update { currentList ->
            currentList.map { reel ->
                if (reel.post_user.user_post_id == reelId) {
                    val newSaveStatus = if (reel.post_user.is_saved == 1) 0 else 1
                    reel.copy(
                        post_user = reel.post_user.copy(is_saved  = newSaveStatus)
                    )
                } else reel
            }
        }
    }

    fun increaseLikeCount_Reels_Enquiry(reelId: Int) {
        _my_Leads.update { currentList ->
            currentList.map { reel ->
                if (reel.post_user.user_post_id == reelId) {
                    reel.copy(
                        post_user = reel.post_user.copy(total_likes = reel.post_user.total_likes.plus(1)),

                    )
                } else reel
            }
        }
    }

    fun decreaseLikeCount_Reels_Enquiry(reelId: Int) {
        _my_Leads.update { currentList ->
            currentList.map { reel ->
                if (reel.post_user.user_post_id == reelId) {
                    val newLikes = (reel.post_user.total_likes.minus(1)).coerceAtLeast(0)
                    reel.copy(
                        post_user = reel.post_user.copy(total_likes = newLikes),
                    )
                } else reel
            }
        }
    }

    fun clear_MyleadsEnquiry (){
        _self_Enquiry.value = emptyList()
    }
    fun decline_Update_Enquiry(reelId: Int) {
        _my_Leads.update { currentList ->
            currentList.map { reel ->
                if (reel.enquiry_details?.enquire_id == reelId) {
                    val newDeclineStatus = if (reel.enquiry_details?.is_declain == 1) 0 else 1
                    val newSearchType = if (reel.search_type == 1) 3 else 1
                    val newIsWhich = if (reel.isWhich == 1) 4 else 1
                    reel.copy(
                        enquiry_details = reel.enquiry_details.copy(
                            is_declain = newDeclineStatus
                        ),
                        search_type = newSearchType,
                        isWhich = newIsWhich
                    )
                } else reel
            }
        }
    }

    fun remove_Enquiry(reelId: Int) {
        _my_Leads.update { currentList ->
            currentList.filterNot { reel ->
                reel.enquiry_details?.enquire_id == reelId
            }
        }
    }

    fun increaseCommentCount_Enquiry(reelId: Int) {
        _my_Leads.update { list ->
            list.map { reel ->
                if (reel.post_user.user_post_id == reelId) {
                    reel.copy(
                        post_user = reel.post_user.copy(
                            total_comments = reel.post_user.total_comments + 1

                        )
                    )
                } else reel
            }
        }
    }

    fun decreaseCommentCount_Enquiry(reelId: Int) {
        _my_Leads.update { list ->
            list.map { reel ->
                if (reel.post_user.user_post_id == reelId) {
                    reel.copy(
                        post_user = reel.post_user.copy(
                            total_comments = reel.post_user.total_comments - 1

                        )
                    )
                } else reel
            }
        }
    }

    fun decline_Undo_Update_Enquiry(reelId: Int) {
        _my_Leads.update { currentList ->
            currentList.map { reel ->
                if (reel.enquiry_details?.enquire_id == reelId) {
                    val newDeclineStatus = if (reel.enquiry_details.is_declain == 1) 0 else 1
                    val newSearchType = if (reel.search_type == 3) 1 else 4
                    val newIsWhich = if (reel.isWhich == 3) 1 else 4
                    reel.copy(
                        enquiry_details = reel.enquiry_details.copy(
                            is_declain = newDeclineStatus
                        ),
                        search_type = newSearchType,
                        isWhich = newIsWhich
                    )
                } else reel
            }
        }
    }

    private val _chatMainList = MutableStateFlow<List<Chat_Main_List_Data>>(emptyList())
    val chatMainList: StateFlow<List<Chat_Main_List_Data>> = _chatMainList.asStateFlow()

    private val _selectedChatData = MutableStateFlow<Chat_Main_List_Data?>(null)
    val selectedChatData: StateFlow<Chat_Main_List_Data?> = _selectedChatData.asStateFlow()

    fun selectMainListData(chatData: Chat_Main_List_Data) {
        _selectedChatData.value = chatData
    }

    fun  getSelectedMainListData(): Chat_Main_List_Data? {
        return _selectedChatData.value
    }

    fun clearChatMainList(){
        _selectedChatData.value = null
    }

    fun get_CML_Data(): Boolean {
        return if (_chatMainList.value.isEmpty()) false else true
    }

    fun set_ChatMainList_Content(newReels: List<Chat_Main_List_Data>) {
        newReels.forEachIndexed { i, item ->
        }
        _chatMainList.value = newReels

    }

    fun clearAllNotification(){
        _chatMainList.update { emptyList() }
    }

    private val _self_Enquiry = MutableStateFlow<List<Get_My_Leads_Data?>>(emptyList())
    val self_Enquiry: StateFlow<List<Get_My_Leads_Data?>> = _self_Enquiry.asStateFlow()

    fun setSelfLeads(newLeads: List<Get_My_Leads_Data>) {

        val updatedList = newLeads.map { item ->
            val mappedType = when (item.search_type) {
                3 -> 4
                4 -> 3
                else -> item.search_type
            }
            item.copy(isWhich = mappedType)
        }

            _self_Enquiry.value = updatedList

    }

    fun toggleLike_Reels_SelfEnquiry(reelId: Int) {
        _self_Enquiry.update { currentList ->
            currentList.map { reel ->
                if (reel?.post_user?.user_post_id == reelId) {

                    val newLikeStatus = if (reel.post_user.is_liked == 1) 0 else 1
                    reel.copy(
                        post_user = reel.post_user.copy(is_liked = newLikeStatus)
                    )
                } else reel
            }
        }
    }

    fun atomicupdateLikeleadsSelf(postId: Int, isLiked: Boolean) {
        _my_Leads.update { list ->
            list.map {
                if (it.post_user.user_post_id == postId) {
                    val updatedPost = it.post_user.copy(
                        is_liked = if (isLiked) 0 else 1,
                        total_likes = if (isLiked)
                            it.post_user.total_likes - 1
                        else
                            it.post_user.total_likes + 1
                    )
                    it.copy(post_user = updatedPost)
                } else it
            }
        }

        _self_Enquiry.update { list ->
            list.map {
                if (it?.post_user?.user_post_id == postId) {
                    it.copy(
                        post_user = it.post_user.copy(
                            is_liked = if (isLiked) 0 else 1,
                            total_likes = if (isLiked)
                                it.post_user.total_likes - 1
                            else
                                it.post_user.total_likes + 1
                        )
                    )
                } else it
            }
        }
    }

    fun atomicupdateSaveleadsSelf(postId: Int, isSaved: Boolean) {
        _my_Leads.update { list ->
            list.map {
                if (it.post_user.user_post_id == postId) {
                    it.copy(
                        post_user = it.post_user.copy(
                            is_saved = if (isSaved) 0 else 1
                        )
                    )
                } else it
            }
        }

        _self_Enquiry.update { list ->
            list.map {
                if (it?.post_user?.user_post_id == postId) {
                    it.copy(
                        post_user = it.post_user.copy(
                            is_saved = if (isSaved) 0 else 1
                        )
                    )
                } else it
            }
        }
    }

    fun toggle__video_Report_SelfEnquiry(reelId: Int) {
        _self_Enquiry.update { currentList ->
            currentList.map { reel ->
                if (reel?.post_user?.user_post_id == reelId) {

                    val currentStatus = reel.post_user.post_property.is_report
                    val newReportStatus = if (currentStatus == 1) 0 else 1

                    reel.copy(
                            post_user = reel.post_user.copy(
                                post_property = reel.post_user.post_property.copy(
                                    is_report = newReportStatus
                                )
                            )
                    )

                } else reel
            }
        }
    }

    fun toggleSave_Reels_SelfEnquiry(reelId: Int) {
        _self_Enquiry.update { currentList ->
            currentList.map { reel ->
                if (reel?.post_user?.user_post_id == reelId) {
                    val newSaveStatus = if (reel.post_user.is_saved == 1) 0 else 1
                    reel.copy(
                        post_user = reel.post_user.copy(is_saved = newSaveStatus),
                    )
                } else reel
            }
        }
    }

    fun increaseLikeCount_SelfReels_Enquiry(reelId: Int) {
        _self_Enquiry.update { currentList ->
            currentList.map { reel ->
                if (reel?.post_user?.user_post_id == reelId) {
                    reel.copy(
                        post_user = reel.post_user.copy(total_likes = reel.post_user.total_likes.plus(1)),

                    )
                } else reel
            }
        }
    }

    fun decreaseLikeCount_Reels_SelfEnquiry(reelId: Int) {
        _self_Enquiry.update { currentList ->
            currentList.map { reel ->
                if (reel?.post_user?.user_post_id == reelId) {
                    val newLikes = (reel.post_user.total_likes.minus(1)).coerceAtLeast(0)
                    reel.copy(
                        post_user = reel.post_user.copy(total_likes = newLikes),

                    )
                } else reel
            }
        }
    }

    fun clear_SelfEnquiry (){
        _self_Enquiry.value = emptyList()
    }

    fun decline_Update_SelfEnquiry(reelId: Int) {
        _self_Enquiry.update { currentList ->
            currentList.map { reel ->
                if (reel?.enquiry_details?.enquire_id == reelId) {
                    val newDeclineStatus = if (reel.enquiry_details.is_declain == 1) 0 else 1
                    val newSearchType = if (reel.search_type == 1) 3 else 1
                    val newIsWhich = if (reel.isWhich == 1) 4 else 1
                    reel.copy(
                        enquiry_details = reel.enquiry_details.copy(
                            is_declain = newDeclineStatus
                        ),
                        search_type = newSearchType,
                        isWhich = newIsWhich
                    )
                } else reel
            }
        }
    }

    fun increaseCommentCount_SelfEnquiry(reelId: Int) {
        _self_Enquiry.update { list ->
            list.map { reel ->
                if (reel?.post_user?.user_post_id == reelId) {
                    reel.copy(
                        post_user = reel.post_user.copy(total_comments = reel.post_user.total_comments + 1),

                    )
                } else reel
            }
        }
    }

    fun decreaseCommentCount_SelfEnquiry(reelId: Int) {
        _self_Enquiry.update { list ->
            list.map { reel ->
                if (reel?.post_user?.user_post_id == reelId) {
                    reel.copy(
                        post_user = reel.post_user.copy( total_comments = reel.post_user.total_comments - 1),

                    )
                } else reel
            }
        }
    }

    fun deleteSelfEnquiryById(enquiryId: Int) {
        val updatedList = _self_Enquiry.value.filterNot { it?.enquiry_details?.enquire_id == enquiryId }
        _self_Enquiry.value = updatedList
    }

    private var _deleteMyLeadsEnquiryPopup = MutableStateFlow(Pair(0 , false))
    var deleteMyLeadsEnquiryPopup = _deleteMyLeadsEnquiryPopup.asStateFlow()

    fun deleteLeads(id : Int , state : Boolean) {

        _deleteMyLeadsEnquiryPopup.update { pair -> Pair(id , state) }
    }

    private var _deleteViewDetailsPopup = MutableStateFlow(Pair(0 , false))
    var deleteViewDetailsPopup = _deleteViewDetailsPopup.asStateFlow()

    fun deleteViewDetailsProperty(id : Int , state : Boolean) {

        _deleteViewDetailsPopup.update { pair -> Pair(id , state) }
    }

}

fun PostUser.toGetReelsData(): Get_Reels_Data {
    return Get_Reels_Data(
        cities = cities,
        country = country,
        name = name,
        phone_num = phone_num,
        phone_num_cc = phone_num_cc,
        whatsapp_num_cc = whatsapp_num_cc,
        whatsapp_num = whatsapp_num,
        email = email,
        post_property = post_property.toGetReelsPropertyData(),
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

fun PostPropertyXXX.toGetReelsPropertyData(): Get_Reels_Property_Data {
    return Get_Reels_Property_Data(
        address = address ?: "",
        amenities = amenities ?: "",
        area_length = area_length ?: "",
        area_width = area_width ?: "",
        bhk_type = bhk_type ?: "",
        boundary_wall = boundary_wall ?: "",
        built_up_area = built_up_area ?: "",
        carpet_area = carpet_area ?: "",
        central_ac = central_ac ?: "",
        city = city ?: "",
        conference_room = conference_room ?: "",
        country = country ?: "",
        created_at = created_at ?: "",
        does_local_authority = does_local_authority ?: "",
        facade_height = facade_height ?: "",
        facade_width = facade_width ?: "",
        fire_safety_measures = fire_safety_measures ?: "",
        furnishing_status = furnishing_status ?: "",
        is_report = is_report ?: 0,
        land_categorie_id = land_categorie_id ?: 0,
        land_type_id = land_type_id ?: 0,
        latitude = latitude ?: "",
        lifts = lifts ?: "",
        locality = locality ?: "",
        longitude = longitude ?: "",
        max_of_seats = max_of_seats ?: "",
        min_of_seats = min_of_seats ?: "",
        no_of_Balconies = no_of_balconies ?: "",
        no_of_Bathrooms = no_of_bathrooms ?: "",
        no_of_bedrooms = no_of_bedrooms ?: "",
        no_of_cabins = no_of_cabins ?: "",
        no_of_meeting_rooms = no_of_meeting_rooms ?: "",
        no_of_open_sides = no_of_open_sides ?: "",
        no_of_Staircases = no_of_staircases ?: "",
        noc_certified = noc_certified ?: "",
        occupancy_certificate = occupancy_certificate ?: "",
        other_rooms = other_rooms ?: "",
        oxygen_duct = oxygen_duct ?: "",
        pantry = pantry ?: "",
        pantry_size = pantry_size ?: "",
        property_area = property_area ?: "",
        property_facing = property_facing ?: "",
        property_highlights = property_highlights ?: "",
        property_name = property_name ?: "",
        reception_area = reception_area ?: "",
        state = state ?: "",
        suitable_business_type = suitable_business_type ?: "",
        super_built_up_area = super_built_up_area ?: "",
        thumbnail = thumbnail ?: "",
        total_floor = total_floor ?: "",
        ups = ups ?: "",
        user_post_id = user_post_id ?: 0,
        user_type = user_type ?: "",
        video = video ?: emptyList(),
        washroom_details = washroom_details ?: "",
        parking_available = parking_available ?: "",
        landTypeText = landTypeText ?: "",
        landCategoryText = landCategoryText ?: "",
        is_sold = is_sold ?: 0,
        pincode = pincode ?: "",
        draft = draft ?: "",
        agreement_type = agreement_type ?: "",
        area_length_unit = area_length_unit ?: "",
        area_width_unit = area_width_unit ?: "",
        availability_from = availability_from ?: "",
        built_up_area_unit = built_up_area_unit ?: "",
        carpet_area_unit = carpet_area_unit ?: "",
        deposit_amount_month_of_rents = deposit_amount_month_of_rents ?: "",
        deposit_amount_month_of_rents_type = deposit_amount_month_of_rents_type ?: "",
        duration_of_agreement = duration_of_agreement ?: "",
        duration_of_agreement_type = duration_of_agreement_type ?: "",
        facade_width_unit = facade_width_unit ?: "",
        food_preferences = food_preferences ?: "",
        is_this_property_for_rent_or_lease = is_this_property_for_rent_or_lease ?: "",
        lease_amount = lease_amount ?: "",
        lease_duration_in_years = lease_duration_in_years ?: "",
        lease_negotiable = lease_negotiable ?: "",
        lock_in_period = lock_in_period ?: "",
        lock_in_period_type = lock_in_period_type ?: "",
        map_config = map_config ?: "",
        notice_period = notice_period ?: "",
        pantry_size_unit = pantry_size_unit ?: "",
        pets_allowed = pets_allowed ?: "",
        post_type = post_type ?: "",
        preferred_tenants = preferred_tenants ?: "",
        property_area_unit = property_area_unit ?: "",
        rent = rent ?: "",
        rent_floor_no = rent_floor_no ?: "",
        rent_negotiable = rent_negotiable ?: "",
        super_built_up_area_unit = super_built_up_area_unit ?: "",
        total_deposit = total_deposit ?: "",
        images = images ?: emptyList(),
        facade_height_unit = facade_height_unit ?: "",
        U_ID = U_ID ?: 0,
        status = status ?: ""
    )
}
