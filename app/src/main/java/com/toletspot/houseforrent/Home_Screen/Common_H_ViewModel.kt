package com.toletspot.houseforrent.Home_Screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.AppNotifcation.App_Notification_Data
import com.toletspot.houseforrent.AppPreferences
import com.toletspot.houseforrent.Navigation.BottomBarScreen
import com.toletspot.houseforrent.R
import com.toletspot.houseforrent.UI_DataClass.Bottom_Bar_Items
import com.toletspot.houseforrent.UI_DataClass.Comment_Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class Common_H_ViewModel : ViewModel(){

    fun clearAllData_CVM() {

        notification_PostId = ""

        BB_Items.clear()
        BB_Items.addAll(
            listOf(
                Bottom_Bar_Items(
                    title = "Videos",
                    unSelectedIcon = R.drawable.homenotrento,
                    selectedIcon = R.drawable.homerento,
                    onSelected = false,
                    route = BottomBarScreen.Videos.route
                ),
                Bottom_Bar_Items(
                    title = "Search",
                    unSelectedIcon = R.drawable.searchnotrento,
                    selectedIcon = R.drawable.searchrento,
                    onSelected = false,
                    route = BottomBarScreen.Search.route
                ),
                Bottom_Bar_Items(
                    title = "Post",
                    unSelectedIcon = R.drawable.postaddnotrento,
                    selectedIcon = R.drawable.postaddrento,
                    onSelected = false,
                    route = BottomBarScreen.Post.route
                ),
                Bottom_Bar_Items(
                    title = "Enquiry",
                    unSelectedIcon = R.drawable.enquirynotrento,
                    selectedIcon = R.drawable.enquiryrento,
                    onSelected = false,
                    route = BottomBarScreen.Enquiry.route
                ),
                Bottom_Bar_Items(
                    title = "Profile",
                    unSelectedIcon = R.drawable.profilenotrento,
                    selectedIcon = R.drawable.profilerento,
                    onSelected = false,
                    route = BottomBarScreen.Profile.route
                ),
            )
        )

        cmt_List.clear()

        _showTABars.value = true
        _showBABars.value = true

        tab_View.value = 0
        _Viewing_Screen_BB.value = 0

        _reelsBtm_sheet.value = false
        _comment_btm_Sheet.value = false

        _status.value = false
        _verify_Status.value = false
        _like_Status.value = false

        view_Property_Details_Mode.value = 0

        _appNotification.value = emptyList()

        AppPreferences.save_UserToken("")
        AppPreferences.save_Interest_Completed(0)
        AppPreferences.save_Location_Received(0)
        AppPreferences.save_Post_Id(0)

    }
    var notification_PostId by mutableStateOf("")

    var BB_Items = mutableStateListOf(
        Bottom_Bar_Items(
            title = "Videos",
            unSelectedIcon = R.drawable.homenotrento,
            selectedIcon = R.drawable.homerento,
            onSelected = false,
            route = BottomBarScreen.Videos.route
        ),
        Bottom_Bar_Items(
            title = "Search",
            unSelectedIcon = R.drawable.searchnotrento,
            selectedIcon = R.drawable.searchrento,
            onSelected = false,
            route = BottomBarScreen.Search.route
        ),
        Bottom_Bar_Items(
            title = "Post",
            unSelectedIcon = R.drawable.postaddnotrento,
            selectedIcon = R.drawable.postaddrento,
            onSelected = false,
            route = BottomBarScreen.Post.route
        ),
        Bottom_Bar_Items(
            title = "Enquiry",
            unSelectedIcon = R.drawable.enquirynotrento,
            selectedIcon = R.drawable.enquiryrento,
            onSelected = false,
            route = BottomBarScreen.Enquiry.route
        ),
        Bottom_Bar_Items(
            title = "Profile",
            unSelectedIcon = R.drawable.profilenotrento,
            selectedIcon = R.drawable.profilerento,
            onSelected = false,
            route = BottomBarScreen.Profile.route
        ),
    )

    fun toggleBottomBar(index: Int) {
        for (i in BB_Items.indices) {
            val current = BB_Items[i]
            BB_Items[i] = current.copy(onSelected = i == index)
        }
    }

    var cmt_List = mutableStateListOf(
        Comment_Item(
            profile = 0,
            name = "@one",
            own_Comment = "I want Details",
            time = "10:10",
            like = true,
            like_Count = 1000,
            other_Commments = listOf(
                Comment_Item(
                    profile = 1,
                    name = "@one_one",
                    own_Comment = "I want Details 2",
                    time = "10:10",
                    like = true,
                    like_Count = 1000,
                ),
                Comment_Item(
                    profile = 1,
                    name = "@one_one",
                    own_Comment = "I want Details 2",
                    time = "10:10",
                    like = true,
                    like_Count = 1000,
                ),
                Comment_Item(
                    profile = 1,
                    name = "@one_one",
                    own_Comment = "I want Details 2",
                    time = "10:10",
                    like = true,
                    like_Count = 1000,
                ),
            ),
        )
    )

    private val _showTABars = MutableStateFlow(true)
    var showTABars: StateFlow<Boolean> = _showTABars

    fun toggleshowTABars(value: Boolean) {
        _showTABars.value = value
    }

    private val _showBABars = MutableStateFlow(true)
    var showBABars: StateFlow<Boolean> = _showBABars

    fun toggleshowBABars(value: Boolean) {
        _showBABars.value = value
    }

    var tab_View = mutableStateOf(0)

    fun selectedBABTab_new(index: Int) {
        tab_View.value = index
        toggleBottomBar(index)
    }

    private var _Viewing_Screen_BB = MutableStateFlow(0)

    val Viewing_Screen_BB: StateFlow<Int> = _Viewing_Screen_BB.asStateFlow()

    fun selectedBABTab(index: Int) {

        _Viewing_Screen_BB.value = index

        toggleBottomBar(index)
    }

    private val _reelsBtm_sheet = MutableStateFlow(false)
    var reelsBtm_sheet: StateFlow<Boolean> = _reelsBtm_sheet

    fun toggleReelsBTMSheet(value: Boolean) {
        _reelsBtm_sheet.value = value
    }

    private val _comment_btm_Sheet = MutableStateFlow<Boolean>(false)
    var comment_btm_Sheet : StateFlow<Boolean> = _comment_btm_Sheet.asStateFlow()

    fun enable_Cmt_btm_Sheet(){
        _comment_btm_Sheet.value = true
    }

    fun dismiss_Cmt_btm_Sheet(){
        _comment_btm_Sheet.value = false
    }

    private var _status = MutableStateFlow<Boolean>(false)
    var status : StateFlow<Boolean> = _status.asStateFlow()

    fun changeStatus(change : Boolean){
        _status.update { change }
    }

    private var _verify_Status = MutableStateFlow<Boolean>(false)
    var verify_Status : StateFlow<Boolean> = _verify_Status.asStateFlow()

    fun change_Verify_Status(change : Boolean){
        _verify_Status.update { change }
    }

    private var _like_Status = MutableStateFlow<Boolean>(false)
    var like_Status : StateFlow<Boolean> = _like_Status.asStateFlow()

    fun change_Like_Status(change: Boolean){
        _like_Status.update { change }
    }

    fun logOut(){

        AppPreferences.save_UserToken("")
        AppPreferences.save_Interest_Completed(0)
        AppPreferences.save_Location_Received(0)
        AppPreferences.save_Post_Id(0)
    }

    var view_Property_Details_Mode = mutableStateOf(0)

    private val _appNotification = MutableStateFlow<List<App_Notification_Data>>(emptyList())
    val appNotification: StateFlow<List<App_Notification_Data>> = _appNotification.asStateFlow()

    fun set_App_Notification_Content(newReels: List<App_Notification_Data>) {
        newReels.forEachIndexed { i, item ->

        }
        _appNotification.value = newReels

    }

    fun removeActivatedNotification(id: Int) {
        _appNotification.update { list ->
            list.filterNot { it.notification_id == id }
        }
    }

    fun clearAN_data(){
        _appNotification.value = emptyList()
    }

    fun updateImFollowedState_Notificationold(notificationId: Int, isFollowed: Int) {
        val currentList = _appNotification.value.toList()

        val updatedList = currentList.map { item ->
            if (item.notification_id == notificationId) {
                item.copy(im_followed = isFollowed)
            } else {
                item.copy(im_followed = 0)
            }
        }

        _appNotification.value = updatedList
    }

    fun updateImFollowedState_Notification(notificationId: Int, isFollowed: Int) {
        val currentList = _appNotification.value.toList()

        Log.d("NotificationUpdate", "Updating notification_id: $notificationId to isFollowed: $isFollowed")
        Log.d("NotificationUpdate", "Current list size: ${currentList.size}")

        val updatedList = currentList.map { item ->
            Log.d("NotificationUpdate", "Checking item: notification_id=${item.notification_id}, current im_followed=${item.im_followed}")
            if (item.notification_id == notificationId) {
                Log.d("NotificationUpdate", "MATCH FOUND! Updating to $isFollowed")
                item.copy(im_followed = isFollowed)
            } else {
                item
            }
        }

        _appNotification.value = updatedList
        Log.d("NotificationUpdate", "Update complete. New list emitted.")
    }

}

private var _from_DeepLink_Property = MutableStateFlow(false)
var from_DeepLink_Property : StateFlow<Boolean> = _from_DeepLink_Property.asStateFlow()

fun set_FDLP_State(state : Boolean){
    _from_DeepLink_Property.value = state
}

fun get_FDLP_State() : Boolean {
    return _from_DeepLink_Property.value
}
