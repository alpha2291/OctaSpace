package com.toletspot.houseforrent.Notifications

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.AppNotifcation.App_Notification_Data
import com.toletspot.houseforrent.API.API_Result_Handling
import com.toletspot.houseforrent.API.StartUp_API.follow_Unfollow_Delete_API_Call
import com.toletspot.houseforrent.AppPreferences
import com.toletspot.houseforrent.CommonText
import com.toletspot.houseforrent.Custom_Assets.API_Fail_UI
import com.toletspot.houseforrent.Custom_Assets.Backer
import com.toletspot.houseforrent.Custom_Assets.ClickHelper
import com.toletspot.houseforrent.Custom_Assets.Common_Popup
import com.toletspot.houseforrent.Custom_Assets.LottiAnimation
import com.toletspot.houseforrent.Custom_Assets.Static_Bottom
import com.toletspot.houseforrent.Custom_Assets.getTimeAgo
import com.toletspot.houseforrent.Custom_Assets.logD
import com.toletspot.houseforrent.Custom_Assets.rememberNotchHeightDp
import com.toletspot.houseforrent.Custom_Assets.toast
import com.toletspot.houseforrent.GlobalSnackbar
import com.toletspot.houseforrent.Home_Screen.Common_H_ViewModel
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.ViewDetailsFlow
import com.toletspot.houseforrent.Home_Screen.ProfileModule.ClickGuard
import com.toletspot.houseforrent.Home_Screen.Video_Module.isWithinLast5DaysOfValidity
import com.toletspot.houseforrent.Navigation.UserCredentialsScreenFlow
import com.toletspot.houseforrent.Navigation.VideosScreenFlow
import com.toletspot.houseforrent.NetworkStatus
import com.toletspot.houseforrent.R
import com.toletspot.houseforrent.UI_DataClass.Profile_Handle_Back
import com.toletspot.houseforrent.constants
import com.toletspot.houseforrent.forTab
import com.toletspot.houseforrent.noRippleClickable
import com.toletspot.houseforrent.rememberNetworkStatus
import com.toletspot.houseforrent.ui.theme.newBlack
import com.toletspot.houseforrent.ui.theme.newBlue
import com.toletspot.houseforrent.ui.theme.newDarkGray
import com.toletspot.houseforrent.ui.theme.newGray
import com.toletspot.houseforrent.ui.theme.newLightBlue
import com.toletspot.houseforrent.ui.theme.newLightGray
import com.toletspot.houseforrent.ui.theme.newPurpleGradient
import com.toletspot.houseforrent.ui.theme.newPurpleGradientBorder
import com.toletspot.houseforrent.ui.theme.newWhite
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Collections.emptyList

fun getNotificationCategory(createdAt: String): String {
    return try {
        val formatter = try {
            DateTimeFormatter.ISO_OFFSET_DATE_TIME
        } catch (_: Exception) {
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        }

        val createdDate = try {
            OffsetDateTime.parse(createdAt, formatter).toLocalDate()
        } catch (_: Exception) {
            LocalDateTime.parse(createdAt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toLocalDate()
        }

        val today = LocalDate.now()
        val yesterday = today.minusDays(1)

        when (createdDate) {
            today -> "Today"
            yesterday -> "Yesterday"
            else -> "Older"
        }
    } catch (e: Exception) {
        "Older"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun In_App_Notification(navController: NavHostController, viewModel: Common_H_ViewModel) {

    val isLoading = constants.API_Vm.isLoading_AN
    val nxtPage = constants.API_Vm.nextPage_AN
    val currentPage = constants.API_Vm.currentPage_AN
    val totalPages = constants.API_Vm.totalPages_AN
    val error = constants.API_Vm.errorMessage_AN

    var retry by remember { mutableStateOf(0) }
    val listState = rememberLazyListState()

    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("All") }
    var activateRentedout = remember { mutableStateOf(false) }
    var activateid = remember { mutableStateOf(0) }

    var selectedOptionIndex = remember { mutableListOf("1", "2", "3" , "4" ,"5" ,"6", "7", "8") }

    val network = rememberNetworkStatus()

    if (network.value == NetworkStatus.Online) {

        LaunchedEffect(Unit, selectedOption, retry) {
            constants.API_Vm.totalPages_AN = 1

            constants.Common_H_ViewModel.clearAN_data()
            constants.API_Vm.load_AN(
                AppPreferences.getUserId(),
                selectedOptionIndex.joinToString(","),
                1
            )
        }

        LaunchedEffect(currentPage, isLoading, totalPages, nxtPage) {
            snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                .collect { lastVisibleItemIndex ->
                    val totalItems = listState.layoutInfo.totalItemsCount
                    val loadMoreThreshold = 4
                    if (
                        lastVisibleItemIndex != null &&
                        totalItems > 0 &&
                        lastVisibleItemIndex >= totalItems - loadMoreThreshold &&
                        !isLoading &&
                        currentPage < totalPages
                    ) {
                        constants.API_Vm.load_AN(
                            AppPreferences.getUserId(),
                            selectedOptionIndex.toString(),
                            nxtPage
                        )
                    }
                }
        }
    }

    val notifications = constants.Common_H_ViewModel.appNotification.collectAsStateWithLifecycle()

    val optionsList = remember {

        listOf("Follows", "Likes", "Comments", "Enquiries", "Declines", "Photo Request" , "All")
    }

    Column(
        modifier = Modifier
            .padding(top = if (forTab()) 16.dp else rememberNotchHeightDp().value)
            .fillMaxSize()
            .background(newWhite)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Backer(
                modifier = Modifier
                ,onBackClick = {
                selectedOptionIndex = emptyList()
                selectedOption = "All"

                    constants.Profile_ViewModel.put_follow_unfollow_Status(0)
                    constants.Profile_ViewModel.put_Following_Id(0)
                    notification_Comment_Clicked.value = false
                    AppPreferences.save_Noti_Post_Id("")

                    constants.Profile_ViewModel.set_Open_False()
                    constants.Profile_ViewModel.onSet_Settings_Click(-1)
                    viewModel.selectedBABTab(0)
                    viewModel.toggleshowBABars(true)
                    viewModel.toggleshowTABars(true)
                    AppPreferences.save_Post_Id(0)
                    constants.Reels_ViewModel.clear_All_Reels()
                    navController.navigate(UserCredentialsScreenFlow.Common_Screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
            })
            Text(
                "Notification",
                fontSize = constants.textUnit(24),
                fontFamily = constants.fontFamily(0),
                color = newBlack
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Text(
                "Showing",
                fontSize = constants.textUnit(16),
                fontFamily = constants.fontFamily(2),
                color = Color(0xff666666)
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                Box(
                    modifier = Modifier
                        .menuAnchor()
                        .width(if (forTab()) 220.dp else 180.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .border(1.dp, newGray, RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            selectedOption,
                            modifier = Modifier.padding(end = 8.dp),
                            fontSize = constants.textUnit(16),
                            fontFamily = constants.fontFamily(1),
                            color = newBlack
                        )
                        Icon(
                            painter = painterResource(R.drawable.arrowdown),
                            contentDescription = ""
                        )
                    }
                }

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    containerColor = newWhite

                ) {
                    optionsList.forEachIndexed { index, option ->
                        DropdownMenuItem(
                            text = {
                                Text(option
                                    , color = newBlack,
                                    fontSize = constants.textUnit(14),
                                    fontFamily = constants.fontFamily(2)
                                    , modifier = Modifier.width(110.dp))
                                   },
                            onClick = {
                                selectedOption = option
                                selectedOptionIndex.clear()
                                selectedOptionIndex.add((index + 1).toString())
                                expanded = false
                                if (selectedOption == "All"){
                                    selectedOptionIndex.add("1")
                                    selectedOptionIndex.add("2")
                                    selectedOptionIndex.add("3")
                                    selectedOptionIndex.add("4")
                                    selectedOptionIndex.add("5")
                                    selectedOptionIndex.add("6")
                                    selectedOptionIndex.add("7")
                                    selectedOptionIndex.add("8")
                                }
                            }

                        )
                    }
                }
            }
        }

        when {

            network.value == NetworkStatus.Offline -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painterResource(R.drawable.nointernerdesign), "")
                    Text(constants.activity.getString(R.string.no_Internet) , color = newBlack,fontSize = constants.textUnit(16)
                        , fontFamily = constants.fontFamily(0)
                        , textAlign = TextAlign.Center)

                }
            }

            isLoading && currentPage == 1 -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    LottiAnimation(2)
                }
            }

            !error.isNullOrEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    API_Fail_UI(onReTryClick = {
                        retry = retry + 76543
                    })
                }
            }

            notifications.value.isEmpty() && !isLoading -> {
                Column(
                    modifier = Modifier
                        .padding(bottom = 48.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painterResource(R.drawable.emptynotificationrento), "")
                    Text("No notifications!" , color = newBlack, fontSize = constants.textUnit(16),
                        fontFamily = constants.fontFamily(0)
                        ,textAlign = TextAlign.Center
                    )
                    Text(
                        "We’ll notify you when something comes up.",
                        textAlign = TextAlign.Center
                        , color = newBlack, fontSize = constants.textUnit(16),
                        fontFamily = constants.fontFamily(0)
                    )
                }
            }

            notifications.value.isNotEmpty() -> {
                val groupedNotifications = notifications.value
                    .sortedByDescending { it.created_at }
                    .groupBy { getNotificationCategory(it.created_at) }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(bottom = 16.dp),
                    state = listState
                ) {
                    groupedNotifications.forEach { (group, items) ->
                        item {
                            Text(
                                text = group,
                                fontSize = constants.textUnit(18),
                                fontFamily = constants.fontFamily(1),
                                color = newBlack,
                                modifier = Modifier.padding(vertical = 6.dp)
                            )
                        }

                        items(items) { item ->
                            when (item.notification_type) {
                                1 -> About_Users(item, navController, viewModel)
                                2 -> About_Likes(item, navController, viewModel)
                                3 -> About_Comment(item, navController, viewModel)
                                4, 5 -> About_Property(item.notification_type, item, navController, viewModel)
                                6 -> About_Likes(item, navController, viewModel)
                                7 -> MediaAssistance(item.notification_type , item, navController, viewModel)
                                8 -> RenewPost(item.notification_type , item, navController, viewModel ,
                                    { userid ->
                                        activateid.value = userid
                                        activateRentedout.value = true

                                    },
                                    onDeleteAfterRenew = { id ->
                                        constants.Common_H_ViewModel.removeActivatedNotification(id)
                                    }
                                )

                                9 -> {
                                    PostExpired(
                                        item.notification_type, item , navController , viewModel = viewModel
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    val unfollow_PUP = constants.Profile_ViewModel.unFollowClick.collectAsState()

    val content = constants.Profile_ViewModel.get_Unflw_Flw_Content_Pup()

    Common_Popup(
        visible = unfollow_PUP.value,
        modifier = Modifier.background(Color(0xffF7F0DC)),
        content = {
            Column(
                modifier = Modifier.padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.padding(2.dp))

                Text(
                    text = "Unfollow ${content.user_Name} ?",
                    color = newBlack,
                    fontSize = constants.textUnit(16),
                    fontFamily = constants.fontFamily(0)
                )

                Text(
                    text = "By unfollowing, you cannot able to view their property posts.",
                    color = newBlack,
                    fontSize = constants.textUnit(12),
                    fontFamily = constants.fontFamily(3),
                    textAlign = TextAlign.Center
                   , lineHeight = 24.sp
                    , modifier = Modifier.padding(horizontal =if (forTab()) 36.dp else 0.dp)
                )

                Spacer(modifier = Modifier.padding(2.dp))
                constants.spacer(2)

                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(4f)
                            .height(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xffE8E8E8))
                            .noRippleClickable {
                                constants.Profile_ViewModel.setunfollowClicker()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Cancel",
                            color = newBlack,
                            fontSize = constants.textUnit(14),
                            fontFamily = constants.fontFamily(0)
                        )
                    }

                    Spacer(modifier = Modifier.weight(.5f))

                    Box(
                        modifier = Modifier
                            .weight(4f)
                            .height(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(newBlue)
                            .noRippleClickable {
                                ClickHelper.getInstance().clickOnce {
                                    if (ClickGuard.canClick()) {
                                        if (network.value == NetworkStatus.Online) {
                                            constants.Profile_ViewModel.put_follow_unfollow_Status(2)
                                            follow_Unfollow_Delete_API_Call() { result ->
                                                when (result) {
                                                    0 -> {

                                                        Log.d(
                                                            "FollowAPI",
                                                            "API Success - About to update"
                                                        )
                                                        Log.d(
                                                            "FollowAPI",
                                                            "content.noti_Id: ${content.noti_Id}"
                                                        )
                                                        Log.d(
                                                            "FollowAPI",
                                                            "Setting im_followed to: 0"
                                                        )

                                                        constants.Common_H_ViewModel.updateImFollowedState_Notification(
                                                            content.noti_Id,
                                                            0
                                                        )
                                                        constants.Profile_ViewModel.setunfollowClicker()
                                                    }

                                                    1 -> {
                                                        toast("Try Again later")
                                                    }

                                                    2 -> {}
                                                }
                                            }
                                        } else {
                                            GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
                                        }
                                    }
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Unfollow",
                            color = Color.White,
                            fontSize = constants.textUnit(16),
                            fontFamily = constants.fontFamily(0)
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(8.dp))
                constants.spacer(2)
            }
        },
        image = content.user_Image,
        userName = content.user_Name
    )

    if (activateRentedout.value) {
        ModalBottomSheet(
            onDismissRequest = {
                activateRentedout.value = false

                AppPreferences.save_Post_Id(0)
            },
            containerColor = Color.White
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                , verticalArrangement = Arrangement.spacedBy(8.dp)
                , horizontalAlignment = Alignment.CenterHorizontally
            ){

                CommonText(
                    "Renew Property Listing",
                    Color(0xff575757),
                    18,
                    1
                    , modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 16.dp)
                )

                CommonText("Renew keeps your property listing active for more days. Your post is about to expire, and renewing will refresh its validity so more people can view it without creating a new listing.",
                    Color(0xff575757),
                    14,
                    3
                    , modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 16.dp)
                )

                var isLoading by remember { mutableStateOf(false) }

                Static_Bottom(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .fillMaxHeight(.6f)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Brush.verticalGradient(newPurpleGradient))
                            .border(
                                1.dp,
                                Brush.linearGradient(newPurpleGradientBorder),
                                RoundedCornerShape(6.dp)
                            )
                            .noRippleClickable {
                                constants.API_Vm.activate_RentedOut(
                                    user_id = AppPreferences.getUserId(),
                                    user_post_id = activateid.value,
                                )
                                { aPI_Result_Handling ->
                                    when (aPI_Result_Handling) {
                                        is API_Result_Handling.NoData -> {}
                                        is API_Result_Handling.Error -> {

                                            GlobalSnackbar.show("Something went wrong")

                                            activateRentedout.value = false
                                        }

                                        is API_Result_Handling.Deactivated -> {

                                        }

                                        is API_Result_Handling.Loading -> {

                                        }

                                        is API_Result_Handling.Success -> {

                                            GlobalSnackbar.show("Property Activated Successfully ")

                                            activateRentedout.value = false

                                        }
                                    }
                                }
                            }
                        , contentAlignment = Alignment.Center
                    ){
                        if (isLoading){
                            CircularProgressIndicator(modifier = Modifier.size(24.dp) , color = newWhite)
                        }
                        else {
                            Text("Renew Listing" , color = newWhite)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun About_Property(
    type: Int,
    item: App_Notification_Data,
    navController: NavHostController,
    viewModel: Common_H_ViewModel
) {
        if (type == 4) {
            ListItem(
                headlineContent = {
                    Text(item.message, color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2),)
                },
                supportingContent = {
                    Text(getTimeAgo(item.created_at), color = newDarkGray,
                        fontSize = constants.textUnit(12),
                        fontFamily = constants.fontFamily(2),)
                },
                leadingContent = {
                    Box(
                        modifier = Modifier
                            .size(if (forTab()) 36.dp else 32.dp)
                            .background(newLightBlue, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        SubcomposeAsyncImage(
                            model = item.profile_image ?: "",
                            modifier = Modifier
                                .fillMaxSize()
                                .noRippleClickable {
                                    ClickHelper.getInstance().clickOnce {
                                        if (item.reported == 1 || item.id_deleted == 1) {
                                            GlobalSnackbar.show("User Not found")
                                        } else {

                                            ClickHelper.getInstance().clickOnce {
                                                if (ClickGuard.canClick()) {
                                                    logD("clicks reels profile")
                                                    viewModel.toggleshowTABars(false)
                                                    constants.Profile_ViewModel.add_Selected_User_Name(
                                                        item.user_name ?: "Unknown"
                                                    )

                                                    constants.Profile_ViewModel.add_BF_Handler(
                                                        Profile_Handle_Back(
                                                            current_UsedId = AppPreferences.getUserId(),
                                                            other_UserId = item.user_id ?: 0,
                                                            ff_User_Name = item.user_name ?: "",
                                                            ff_Fw_Count = 0,
                                                            ff_Fg_Count = 0,

                                                        )
                                                    )

                                                    constants.Profile_ViewModel.addProfile(
                                                        item.user_id ?: 0
                                                    )
                                                    constants.Profile_ViewModel.add_Selected_Profile_Id(
                                                        id = item.user_id ?: 0
                                                    )

                                                    viewModel.toggleshowBABars(false)
                                                    navController.navigate(VideosScreenFlow.Other_Profile_Structure.route)
                                                }
                                            }
                                        }
                                    }

                                }
                                .clip(CircleShape)
                            , contentDescription = "",
                            contentScale = ContentScale.FillBounds
                        )
                        {
                            val state = painter.state
                            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(newLightBlue, CircleShape)

                                    , contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = item.user_name.takeIf { it?.isNotEmpty() == true }
                                            ?.take(1)?.uppercase() ?: ""
                                    )
                                }
                            } else {
                                SubcomposeAsyncImageContent()
                            }
                        }
                    }
                },
                trailingContent = {
                    if (item.thumbnail.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(newLightGray, RoundedCornerShape(4.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            SubcomposeAsyncImage(
                                model = item.thumbnail ?: "",
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentDescription = "",
                                contentScale = ContentScale.FillBounds
                            )
                            {
                                val state = painter.state
                                if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(newLightBlue)

                                        , contentAlignment = Alignment.Center
                                    ) {
                                        Image(painterResource(R.drawable.emptypostsrento), "")
                                    }
                                } else {
                                    SubcomposeAsyncImageContent()
                                }
                            }
                        }
                    } else {
                        null
                    }
                },
                modifier = Modifier
                    .border(1.dp, newDarkGray, RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 12.dp)
                    .noRippleClickable {
                        ClickHelper.getInstance().clickOnce {
                            when {
                                item.is_sold == 1 -> {
                                    GlobalSnackbar.show("Property Not found")
                                }

                                item.reported == 1 -> {
                                    GlobalSnackbar.show("Property Not found")
                                }

                                item.post_deleted == 1 -> {
                                    GlobalSnackbar.show("Property Not found")
                                }

                                item.id_deleted == 1 -> {
                                    GlobalSnackbar.show("Property Not found")
                                }

                                else -> {

                                    navController.navigate(
                                        UserCredentialsScreenFlow.Common_Screen.route + "/3"
                                    ) {
                                        popUpTo(UserCredentialsScreenFlow.Common_Screen.route) {
                                            inclusive = true
                                        }
                                        launchSingleTop = true
                                    }

                                }
                            }

                        }
                    }

                , colors = ListItemColors(
                    containerColor = newWhite,
                    headlineColor = Color.Black,
                    leadingIconColor = Color.DarkGray,
                    overlineColor = Color.Gray,
                    supportingTextColor = Color.Gray,
                    trailingIconColor = Color.LightGray,
                    disabledHeadlineColor = Color.Gray.copy(alpha = 0.5f),
                    disabledLeadingIconColor = Color.Gray.copy(alpha = 0.5f),
                    disabledTrailingIconColor = Color.Gray.copy(alpha = 0.5f)
                ),
            )
        }
        else {

            ListItem(
                headlineContent = {
                    Text(item.message)
                },
                leadingContent = {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color(0xffFCEDEC), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        SubcomposeAsyncImage(
                            model = R.drawable.closeenquiry,
                            modifier = Modifier
                                .fillMaxSize(),
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds
                        )
                        {
                            val state = painter.state
                            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(newLightBlue)

                                    , contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = item.user_name.takeIf { it?.isNotEmpty() == true }
                                            ?.take(1)?.uppercase() ?: ""
                                    )
                                }
                            } else {
                                SubcomposeAsyncImageContent()
                            }
                        }
                    }
                },
                modifier = Modifier
                    .border(1.dp, newDarkGray, RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 12.dp)
                    .noRippleClickable {

                        ClickHelper.getInstance().clickOnce {

                            when {
                                item.is_sold == 1 -> {
                                    GlobalSnackbar.show("Property Not found")
                                }

                                item.reported == 1 -> {
                                    GlobalSnackbar.show("Property Not found")
                                }

                                item.post_deleted == 1 -> {
                                    GlobalSnackbar.show("Property Not found")
                                }

                                item.id_deleted == 1 -> {
                                    GlobalSnackbar.show("Property Not found")
                                }

                                else -> {

                                    navController.navigate(
                                        UserCredentialsScreenFlow.Common_Screen.route + "/3"
                                    ) {
                                        popUpTo(UserCredentialsScreenFlow.Common_Screen.route) {
                                            inclusive = true
                                        }
                                        launchSingleTop = true
                                    }
                                }
                            }
                        }

                    }
                , colors = ListItemColors(
                    containerColor = newWhite,
                    headlineColor = Color.Black,
                    leadingIconColor = Color.DarkGray,
                    overlineColor = Color.Gray,
                    supportingTextColor = Color.Gray,
                    trailingIconColor = Color.LightGray,
                    disabledHeadlineColor = Color.Gray.copy(alpha = 0.5f),
                    disabledLeadingIconColor = Color.Gray.copy(alpha = 0.5f),
                    disabledTrailingIconColor = Color.Gray.copy(alpha = 0.5f)
                ),
            )
        }
}

@Composable
fun About_Comment(
    item: App_Notification_Data,
    navController: NavHostController,
    viewModel: Common_H_ViewModel
) {
    ListItem(
        headlineContent = {
            Text(item.message, color = newBlack,
                fontSize = constants.textUnit(14),
                fontFamily = constants.fontFamily(2),)
        },
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(if (forTab()) 38.dp else 32.dp)
                    .background(newLightBlue, CircleShape), contentAlignment = Alignment.Center
            ) {
                SubcomposeAsyncImage(
                    model = item.profile_image ?: "",
                    modifier = Modifier
                        .fillMaxSize()
                        .noRippleClickable {
                            ClickHelper.getInstance().clickOnce {
                                if (item.reported == 1 || item.id_deleted == 1) {
                                    GlobalSnackbar.show("User Not found")
                                } else {

                                    ClickHelper.getInstance().clickOnce {
                                        if (ClickGuard.canClick()) {
                                            logD("clicks reels profile")
                                            viewModel.toggleshowTABars(false)
                                            constants.Profile_ViewModel.add_Selected_User_Name(
                                                item.user_name ?: "Unknown"
                                            )

                                            constants.Profile_ViewModel.add_BF_Handler(
                                                Profile_Handle_Back(
                                                    current_UsedId = AppPreferences.getUserId(),
                                                    other_UserId = item.user_id ?: 0,
                                                    ff_User_Name = item.user_name ?: "",
                                                    ff_Fw_Count = 0,
                                                    ff_Fg_Count = 0,

                                                )
                                            )

                                            constants.Profile_ViewModel.addProfile(
                                                item.user_id ?: 0
                                            )
                                            constants.Profile_ViewModel.add_Selected_Profile_Id(
                                                id = item.user_id ?: 0
                                            )

                                            viewModel.toggleshowBABars(false)
                                            navController.navigate(VideosScreenFlow.Other_Profile_Structure.route)
                                        }
                                    }
                                }
                            }

                        }
                        .clip(CircleShape)
                    , contentDescription = ""
                    , contentScale = ContentScale.FillBounds
                )
                {
                    val state = painter.state
                    if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(newLightBlue)

                            , contentAlignment = Alignment.Center
                        ){
                            Text(
                                text = item.user_name.takeIf { it?.isNotEmpty() == true }?.take(1)?.uppercase() ?: ""
                            )
                        }
                    } else {
                        SubcomposeAsyncImageContent()
                    }
                }
            }
        },
        trailingContent = {
            if(item.thumbnail.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(newLightGray, RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    SubcomposeAsyncImage(
                        model = item.thumbnail ?: "",
                        modifier = Modifier
                            .fillMaxSize(),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds
                    )
                    {
                        val state = painter.state
                        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(newLightBlue)

                                , contentAlignment = Alignment.Center
                            ) {
                                Image(painterResource(R.drawable.emptypostsrento), "")
                            }
                        } else {
                            SubcomposeAsyncImageContent()
                        }
                    }
                }
            }
            else {
                null
            }
        },
        modifier = Modifier
            .border(1.dp, newDarkGray, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .noRippleClickable {
                ClickHelper.getInstance().clickOnce {

                    when {
                        item.is_sold == 1 -> {
                            GlobalSnackbar.show("Property Not found")
                        }

                        item.reported == 1 -> {
                            GlobalSnackbar.show("Property Not found")
                        }

                        item.post_deleted == 1 -> {
                            GlobalSnackbar.show("Property Not found")
                        }

                        item.id_deleted == 1 -> {
                            GlobalSnackbar.show("Property Not found")
                        }

                        else -> {
                            AppPreferences.save_Noti_Post_Id(item.user_post_id.toString())
                            viewModel.notification_PostId = item.user_post_id.toString()
                            viewModel.selectedBABTab(0)
                            viewModel.toggleshowTABars(false)
                            viewModel.toggleshowBABars(false)
                            constants.Common_H_ViewModel.toggleshowBABars(false)
                            constants.Common_H_ViewModel.toggleshowTABars(false)

                            constants.Reels_ViewModel.clear_All_Reels()
                            constants.API_Vm.isLoading_Reels = true
                            constants.API_Vm.totalPages_Reels = 1

                            navController.navigate(UserCredentialsScreenFlow.Common_Screen.route) {
                                popUpTo(UserCredentialsScreenFlow.Common_Screen.route) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }

                            notification_Comment_Clicked.value = true

                        }
                    }
                }
            }
        , colors = ListItemColors(
            containerColor = newWhite,
            headlineColor = Color.Black,
            leadingIconColor = Color.DarkGray,
            overlineColor = Color.Gray,
            supportingTextColor = Color.Gray,
            trailingIconColor = Color.LightGray,
            disabledHeadlineColor = Color.Gray.copy(alpha = 0.5f),
            disabledLeadingIconColor = Color.Gray.copy(alpha = 0.5f),
            disabledTrailingIconColor = Color.Gray.copy(alpha = 0.5f)
        ),
    )
}

var notification_Comment_Clicked = mutableStateOf(false)

@Composable
fun About_Likes(
    item: App_Notification_Data,
    navController: NavHostController,
    viewModel: Common_H_ViewModel
) {
    if(item.notification_type == 2){
        ListItem(
            headlineContent = {
                Text(item.message, color = newBlack,
                    fontSize = constants.textUnit(14),
                    fontFamily = constants.fontFamily(2),)
            },
            leadingContent = {
                Box(
                    modifier = Modifier
                        .size(if (forTab()) 38.dp else 32.dp)
                        .background(newLightBlue, CircleShape), contentAlignment = Alignment.Center
                ) {
                    SubcomposeAsyncImage(
                        model = item.profile_image ?: "",
                        modifier = Modifier
                            .fillMaxSize()
                            .noRippleClickable {
                                ClickHelper.getInstance().clickOnce {
                                    if (item.reported == 1 || item.id_deleted == 1) {
                                        GlobalSnackbar.show("User Not found")
                                    } else {

                                        ClickHelper.getInstance().clickOnce {
                                            if (ClickGuard.canClick()) {
                                                logD("clicks reels profile")
                                                viewModel.toggleshowTABars(false)
                                                constants.Profile_ViewModel.add_Selected_User_Name(
                                                    item.user_name ?: "Unknown"
                                                )

                                                constants.Profile_ViewModel.add_BF_Handler(
                                                    Profile_Handle_Back(
                                                        current_UsedId = AppPreferences.getUserId(),
                                                        other_UserId = item.user_id ?: 0,
                                                        ff_User_Name = item.user_name ?: "",
                                                        ff_Fw_Count = 0,
                                                        ff_Fg_Count = 0,

                                                    )
                                                )

                                                constants.Profile_ViewModel.addProfile(
                                                    item.user_id ?: 0
                                                )
                                                constants.Profile_ViewModel.add_Selected_Profile_Id(
                                                    id = item.user_id ?: 0
                                                )

                                                viewModel.toggleshowBABars(false)
                                                navController.navigate(VideosScreenFlow.Other_Profile_Structure.route)
                                            }
                                        }
                                    }
                                }

                            }
                            .clip(CircleShape)
                        , contentDescription = ""
                        , contentScale = ContentScale.FillBounds
                    )
                    {
                        val state = painter.state
                        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(newLightBlue)

                                , contentAlignment = Alignment.Center
                            ){
                                Text(
                                    text = item.user_name.takeIf { it?.isNotEmpty() == true }?.take(1)?.uppercase() ?: ""
                                )
                            }
                        } else {
                            SubcomposeAsyncImageContent()
                        }
                    }
                }
            },
            trailingContent = {
                if(item.thumbnail.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(newLightGray, RoundedCornerShape(4.dp)),
                        contentAlignment = Alignment.Center
                    )
                    {
                        SubcomposeAsyncImage(
                            model = item.thumbnail ?: "",
                            modifier = Modifier
                                .fillMaxSize(),
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds
                        )
                        {
                            val state = painter.state
                            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(newLightBlue)

                                    , contentAlignment = Alignment.Center
                                ) {
                                    Image(painterResource(R.drawable.emptypostsrento), "")
                                }
                            } else {
                                SubcomposeAsyncImageContent()
                            }
                        }
                    }
                }
                else {
                    null
                }
            },
            modifier = Modifier
                .border(1.dp, newDarkGray, RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 12.dp)
                .noRippleClickable {
                    ClickHelper.getInstance().clickOnce {
                        when {
                            item.is_sold == 1 -> {
                                GlobalSnackbar.show("Property Not found")
                            }

                            item.reported == 1 -> {
                                GlobalSnackbar.show("Property Not found")
                            }

                            item.post_deleted == 1 -> {
                                GlobalSnackbar.show("Property Not found")
                            }

                            item.id_deleted == 1 -> {
                                GlobalSnackbar.show("Property Not found")
                            }

                            else -> {
                                AppPreferences.save_Noti_Post_Id(item.user_post_id.toString())
                                viewModel.notification_PostId = item.user_post_id.toString()
                                viewModel.toggleshowTABars(false)
                                viewModel.toggleshowBABars(false)

                                constants.Reels_ViewModel.clear_All_Reels()
                                constants.API_Vm.isLoading_Reels = true
                                constants.API_Vm.totalPages_Reels = 1

                                viewModel.selectedBABTab(0)
                                navController.navigate(UserCredentialsScreenFlow.Common_Screen.route) {
                                    popUpTo(UserCredentialsScreenFlow.Common_Screen.route) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            }
                        }
                    }

                }

            , colors = ListItemColors(
                containerColor = newWhite,
                headlineColor = Color.Black,
                leadingIconColor = Color.DarkGray,
                overlineColor = Color.Gray,
                supportingTextColor = Color.Gray,
                trailingIconColor = Color.LightGray,
                disabledHeadlineColor = Color.Gray.copy(alpha = 0.5f),
                disabledLeadingIconColor = Color.Gray.copy(alpha = 0.5f),
                disabledTrailingIconColor = Color.Gray.copy(alpha = 0.5f)
            ),
        )
    }
    else {
        ListItem(
            headlineContent = {
                Text(item.message, color = newBlack,
                    fontSize = constants.textUnit(14),
                    fontFamily = constants.fontFamily(2),)
            },
            leadingContent = {
                Box(
                    modifier = Modifier
                        .size(if (forTab()) 38.dp else 32.dp)
                        .background(newLightBlue, CircleShape), contentAlignment = Alignment.Center
                ) {
                    SubcomposeAsyncImage(
                        model = item.profile_image ?: "",
                        modifier = Modifier
                            .fillMaxSize()
                            .noRippleClickable {
                                ClickHelper.getInstance().clickOnce {
                                    if (item.reported == 1 || item.id_deleted == 1) {
                                        GlobalSnackbar.show("User Not found")
                                    } else {

                                        ClickHelper.getInstance().clickOnce {
                                            if (ClickGuard.canClick()) {
                                                logD("clicks reels profile")
                                                viewModel.toggleshowTABars(false)
                                                constants.Profile_ViewModel.add_Selected_User_Name(
                                                    item.user_name ?: "Unknown"
                                                )

                                                constants.Profile_ViewModel.add_BF_Handler(
                                                    Profile_Handle_Back(
                                                        current_UsedId = AppPreferences.getUserId(),
                                                        other_UserId = item.user_id ?: 0,
                                                        ff_User_Name = item.user_name ?: "",
                                                        ff_Fw_Count = 0,
                                                        ff_Fg_Count = 0,

                                                    )
                                                )

                                                constants.Profile_ViewModel.addProfile(
                                                    item.user_id ?: 0
                                                )
                                                constants.Profile_ViewModel.add_Selected_Profile_Id(
                                                    id = item.user_id ?: 0
                                                )

                                                viewModel.toggleshowBABars(false)
                                                navController.navigate(VideosScreenFlow.Other_Profile_Structure.route)
                                            }
                                        }
                                    }
                                }

                            }
                            .clip(CircleShape)
                        , contentDescription = ""
                        , contentScale = ContentScale.FillBounds
                    )
                    {
                        val state = painter.state
                        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(newLightBlue)

                                , contentAlignment = Alignment.Center
                            ){
                                Text(
                                    text = item.user_name.takeIf { it?.isNotEmpty() == true }?.take(1)?.uppercase() ?: ""
                                )
                            }
                        } else {
                            SubcomposeAsyncImageContent()
                        }
                    }
                }
            },
            trailingContent = {

                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .background(newBlue)
                        .noRippleClickable {
                            navController.navigate(VideosScreenFlow.Justify_Post.route + "/${item.user_post_id}")
                        }
                        .padding(horizontal = 8.dp, vertical = 12.dp)
                    , contentAlignment = Alignment.Center
                ){
                    Text("Appeal",
                        color = newWhite
                        , fontSize = constants.textUnit(12)
                        , fontFamily = constants.fontFamily(0)
                    )
                }
            },
            modifier = Modifier
                .border(1.dp, newDarkGray, RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 12.dp)
                .noRippleClickable {

                }

            , colors = ListItemColors(
                containerColor = newWhite,
                headlineColor = Color.Black,
                leadingIconColor = Color.DarkGray,
                overlineColor = Color.Gray,
                supportingTextColor = Color.Gray,
                trailingIconColor = Color.LightGray,
                disabledHeadlineColor = Color.Gray.copy(alpha = 0.5f),
                disabledLeadingIconColor = Color.Gray.copy(alpha = 0.5f),
                disabledTrailingIconColor = Color.Gray.copy(alpha = 0.5f)
            ),
        )
    }
}

@Composable
fun About_Users(
    item: App_Notification_Data,
    navController: NavHostController,
    viewModel: Common_H_ViewModel
) {
    Log.d("AboutUsers", "Rendering notification_id: ${item.notification_id}, im_followed: ${item.im_followed}, is_followed: ${item.is_followed}")

    var followLoader by remember { mutableStateOf(false) }

    val followType = remember(item.im_followed, item.is_followed) {
        val type = if(item.im_followed == 1 && item.is_followed == 1 || item.im_followed == 1 && item.is_followed == 0) 1 else 0
        Log.d("AboutUsers", "Calculated followType: $type")
        type
    }

    ListItem(
        headlineContent = {
            Text(item.message ,color =  newBlack,
                fontSize = constants.textUnit(14),
                fontFamily = constants.fontFamily(2),)
        },
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(if (forTab()) 38.dp else 32.dp)
                    .background(newLightBlue, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                SubcomposeAsyncImage(
                    model = item.profile_image ?: "",
                    modifier = Modifier
                        .fillMaxSize()
                        .noRippleClickable {
                            ClickHelper.getInstance().clickOnce {
                                if (item.reported == 1 || item.id_deleted == 1) {
                                    GlobalSnackbar.show("User Not found")
                                } else {
                                    ClickHelper.getInstance().clickOnce {
                                        if (ClickGuard.canClick()) {
                                            logD("clicks reels profile")
                                            viewModel.toggleshowTABars(false)
                                            constants.Profile_ViewModel.add_Selected_User_Name(
                                                item.user_name ?: "Unknown"
                                            )

                                            constants.Profile_ViewModel.add_BF_Handler(
                                                Profile_Handle_Back(
                                                    current_UsedId = AppPreferences.getUserId(),
                                                    other_UserId = item.user_id ?: 0,
                                                    ff_User_Name = item.user_name ?: "",
                                                    ff_Fw_Count = 0,
                                                    ff_Fg_Count = 0,
                                                )
                                            )

                                            constants.Profile_ViewModel.addProfile(
                                                item.user_id ?: 0
                                            )
                                            constants.Profile_ViewModel.add_Selected_Profile_Id(
                                                id = item.user_id ?: 0
                                            )

                                            viewModel.toggleshowBABars(false)
                                            navController.navigate(VideosScreenFlow.Other_Profile_Structure.route)
                                        }
                                    }
                                }
                            }
                        }
                        .clip(CircleShape),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds
                ) {
                    val state = painter.state
                    if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(newLightBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = item.user_name.takeIf { it?.isNotEmpty() == true }?.take(1)?.uppercase() ?: ""
                            )
                        }
                    } else {
                        SubcomposeAsyncImageContent()
                    }
                }
            }
        },
        trailingContent = {

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .background(if (followType == 1) Color(0xffE8E8E8) else newBlue)
                    .noRippleClickable {
                        ClickHelper.getInstance().clickOnce {
                            if (followType == 1) {

                                constants.Profile_ViewModel.add_Unflw_Flw_Content_Pup(
                                    id = item.user_id ?: 0,
                                    username = item.user_name ?: "username",
                                    profilePic = item.profile_image ?: "",
                                    noti_Id = item.notification_id
                                )

                                constants.Profile_ViewModel.put_follow_unfollow_Status(2)
                                constants.Profile_ViewModel.put_Following_Id(item.user_id)
                                constants.Profile_ViewModel.setunfollowClicker()
                            } else {

                                followLoader = true
                                constants.Profile_ViewModel.put_follow_unfollow_Status(1)
                                constants.Profile_ViewModel.put_Following_Id(item.user_id)

                                follow_Unfollow_Delete_API_Call() { result ->
                                    when (result) {
                                        0 -> {
                                            Log.d("FollowAPI", "API Success - Follow Back")
                                            Log.d(
                                                "FollowAPI",
                                                "notification_id: ${item.notification_id}"
                                            )
                                            Log.d("FollowAPI", "Setting im_followed to: 1")

                                            constants.Common_H_ViewModel.updateImFollowedState_Notification(
                                                item.notification_id,
                                                1
                                            )
                                            followLoader = false
                                        }

                                        1 -> {
                                            followLoader = false
                                            toast("Try Again later")
                                        }

                                        2 -> {
                                            followLoader = true
                                        }
                                    }
                                }
                            }
                        }
                    }
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {

                    Text(
                        if (followType == 1) "Following" else "Follow Back",
                        color = if (followType == 1) newDarkGray else newWhite,
                        fontSize = constants.textUnit(12),
                        fontFamily = constants.fontFamily(0)
                    )

            }
        },
        modifier = Modifier
            .border(1.dp, newDarkGray, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .noRippleClickable { },
        colors = ListItemColors(
            containerColor = newWhite,
            headlineColor = Color.Black,
            leadingIconColor = Color.DarkGray,
            overlineColor = Color.Gray,
            supportingTextColor = Color.Gray,
            trailingIconColor = Color.LightGray,
            disabledHeadlineColor = Color.Gray.copy(alpha = 0.5f),
            disabledLeadingIconColor = Color.Gray.copy(alpha = 0.5f),
            disabledTrailingIconColor = Color.Gray.copy(alpha = 0.5f)
        ),
    )
}

@Composable
fun MediaAssistance(
    type: Int,
    item: App_Notification_Data,
    navController: NavHostController,
    viewModel: Common_H_ViewModel
){
    ListItem(
        headlineContent = {

            Text("Hey, 1 person requested photos of this property. Add Photos to boost visibility!", color = newBlack,
                fontSize = constants.textUnit(14),
                fontFamily = constants.fontFamily(2),)
        },
        supportingContent = {
            Text(getTimeAgo(item.created_at), color = newDarkGray,
                fontSize = constants.textUnit(12),
                fontFamily = constants.fontFamily(2),)
        },
        leadingContent = {
            Column(
                modifier = Modifier
                , verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(if (forTab()) 36.dp else 32.dp)
                        .background(newLightBlue, CircleShape),
                    contentAlignment = Alignment.Center
                )
                {
                    SubcomposeAsyncImage(
                        model = item.profile_image ?: "",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape), contentDescription = "",
                        contentScale = ContentScale.FillBounds
                    )
                    {
                        val state = painter.state
                        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(newLightBlue, CircleShape)

                                , contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.requestmediaprofilenotificationicon),
                                    ""
                                )
                            }
                        } else {
                            SubcomposeAsyncImageContent()
                        }
                    }
                }
            }
        },
        trailingContent = {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xffF7F0DC))
                , contentAlignment = Alignment.Center
            ) {
                Image(painter = painterResource(R.drawable.requestmedianotifiationicon) , "")
            }
        },
        modifier = Modifier
            .border(1.dp, newDarkGray, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .noRippleClickable {
                ClickHelper.getInstance().clickOnce {
                    when {
                        item.is_sold == 1 -> {
                            GlobalSnackbar.show("Property Not found")
                        }

                        item.reported == 1 -> {
                            GlobalSnackbar.show("Property Not found")
                        }

                        item.post_deleted == 1 -> {
                            GlobalSnackbar.show("Property Not found")
                        }

                        item.id_deleted == 1 -> {
                            GlobalSnackbar.show("Property Not found")
                        }

                        else -> {

                            navController.navigate(
                                UserCredentialsScreenFlow.Common_Screen.route + "/3"
                            ) {
                                popUpTo(UserCredentialsScreenFlow.Common_Screen.route) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }

                        }
                    }
                }
            }

        , colors = ListItemColors(
            containerColor = newWhite,
            headlineColor = Color.Black,
            leadingIconColor = Color.DarkGray,
            overlineColor = Color.Gray,
            supportingTextColor = Color.Gray,
            trailingIconColor = Color.LightGray,
            disabledHeadlineColor = Color.Gray.copy(alpha = 0.5f),
            disabledLeadingIconColor = Color.Gray.copy(alpha = 0.5f),
            disabledTrailingIconColor = Color.Gray.copy(alpha = 0.5f)
        ),
    )
}

@Composable
fun RenewPost(
    type: Int,
    item: App_Notification_Data,
    navController: NavHostController,
    viewModel: Common_H_ViewModel,
    onRenewClick :(Int) -> Unit,
    onDeleteAfterRenew :(Int) -> Unit
){
    ListItem(
        headlineContent = {

            Column() {
                Text(
                    item.message, color = newBlack,
                    fontSize = constants.textUnit(14),
                    fontFamily = constants.fontFamily(2),
                )

                Text(
                    "Renew now", color = newBlue,
                    fontSize = constants.textUnit(14),
                    fontFamily = constants.fontFamily(0)
                    , modifier = Modifier
                        .noRippleClickable {

                        }
                )
            }
        },
        supportingContent = {
            Text(getTimeAgo(item.created_at), color = newDarkGray,
                fontSize = constants.textUnit(12),
                fontFamily = constants.fontFamily(2),)
        },
        leadingContent = {
            Column(
                modifier = Modifier
                , verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(if (forTab()) 36.dp else 32.dp)
                        .background(newLightBlue, CircleShape),
                    contentAlignment = Alignment.Center
                )
                {
                    SubcomposeAsyncImage(
                        model = item.profile_image ?: "",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape), contentDescription = "",
                        contentScale = ContentScale.FillBounds
                    )
                    {
                        val state = painter.state
                        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(newLightBlue, CircleShape)

                                , contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.requestmediaprofilenotificationicon),
                                    ""
                                )
                            }
                        } else {
                            SubcomposeAsyncImageContent()
                        }
                    }
                }
            }
        },
        trailingContent = {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xffF7F0DC))
                , contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(newLightGray, RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                )
                {
                    SubcomposeAsyncImage(
                        model = item.thumbnail ?: "",
                        modifier = Modifier
                            .fillMaxSize(),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds
                    )
                    {
                        val state = painter.state
                        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(newLightBlue)

                                , contentAlignment = Alignment.Center
                            ) {
                                Image(painterResource(R.drawable.emptypostsrento), "")
                            }
                        } else {
                            SubcomposeAsyncImageContent()
                        }
                    }
                }
            }
        },
        modifier = Modifier
            .border(1.dp, newDarkGray, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .noRippleClickable {
                ClickHelper.getInstance().clickOnce {
                    when {
                        item.is_sold == 1 -> {
                            GlobalSnackbar.show("Property Not found")
                        }

                        item.reported == 1 -> {
                            GlobalSnackbar.show("Property Not found")
                        }

                        item.post_deleted == 1 -> {
                            GlobalSnackbar.show("Property Not found")
                        }

                        item.id_deleted == 1 -> {
                            GlobalSnackbar.show("Property Not found")
                        }

                        !isWithinLast5DaysOfValidity(item.created_at) -> {
                            toast("Post Already Renewed")
                        }

                        else -> {

                            constants.PostProperty_ViewModel.setViewDetailsFlow(ViewDetailsFlow.RENEW)
                            AppPreferences.save_Noti_Post_Id(item.user_post_id.toString())
                            viewModel.notification_PostId = item.user_post_id.toString()
                            viewModel.toggleshowTABars(false)
                            viewModel.toggleshowBABars(false)

                            constants.Reels_ViewModel.clear_All_Reels()
                            constants.API_Vm.isLoading_Reels = true
                            constants.API_Vm.totalPages_Reels = 1

                            constants.PostProperty_ViewModel.setViewDetailsFlow(ViewDetailsFlow.RENEW)

                            viewModel.selectedBABTab(0)
                            navController.navigate(UserCredentialsScreenFlow.Common_Screen.route) {
                                popUpTo(UserCredentialsScreenFlow.Common_Screen.route) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }

                        }
                    }
                }
            }

        , colors = ListItemColors(
            containerColor = newWhite,
            headlineColor = Color.Black,
            leadingIconColor = Color.DarkGray,
            overlineColor = Color.Gray,
            supportingTextColor = Color.Gray,
            trailingIconColor = Color.LightGray,
            disabledHeadlineColor = Color.Gray.copy(alpha = 0.5f),
            disabledLeadingIconColor = Color.Gray.copy(alpha = 0.5f),
            disabledTrailingIconColor = Color.Gray.copy(alpha = 0.5f)
        ),
    )
}

@Composable
fun PostExpired(
    type: Int,
    item: App_Notification_Data,
    navController: NavHostController,
    viewModel: Common_H_ViewModel,
){
    ListItem(
        headlineContent = {

            Column() {
                Text(
                    item.message, color = newBlack,
                    fontSize = constants.textUnit(14),
                    fontFamily = constants.fontFamily(2),
                )

            }
        },
        supportingContent = {
            Text(getTimeAgo(item.created_at), color = newDarkGray,
                fontSize = constants.textUnit(12),
                fontFamily = constants.fontFamily(2),)
        },
        leadingContent = {
            Column(
                modifier = Modifier
                , verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(if (forTab()) 36.dp else 32.dp)
                        .background(newLightBlue, CircleShape),
                    contentAlignment = Alignment.Center
                )
                {
                    SubcomposeAsyncImage(
                        model = item.profile_image ?: "",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape), contentDescription = "",
                        contentScale = ContentScale.FillBounds
                    )
                    {
                        val state = painter.state
                        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(newLightBlue, CircleShape)

                                , contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.requestmediaprofilenotificationicon),
                                    ""
                                )
                            }
                        } else {
                            SubcomposeAsyncImageContent()
                        }
                    }
                }
            }
        },
        trailingContent = {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xffF7F0DC))
                , contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(newLightGray, RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                )
                {
                    SubcomposeAsyncImage(
                        model = item.thumbnail ?: "",
                        modifier = Modifier
                            .fillMaxSize(),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds
                    )
                    {
                        val state = painter.state
                        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(newLightBlue)

                                , contentAlignment = Alignment.Center
                            ) {
                                Image(painterResource(R.drawable.emptypostsrento), "")
                            }
                        } else {
                            SubcomposeAsyncImageContent()
                        }
                    }
                }
            }
        },
        modifier = Modifier
            .border(1.dp, newDarkGray, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .noRippleClickable {
                ClickHelper.getInstance().clickOnce {
                    GlobalSnackbar.show("Property Expired")

                }
            }

        , colors = ListItemColors(
            containerColor = newWhite,
            headlineColor = Color.Black,
            leadingIconColor = Color.DarkGray,
            overlineColor = Color.Gray,
            supportingTextColor = Color.Gray,
            trailingIconColor = Color.LightGray,
            disabledHeadlineColor = Color.Gray.copy(alpha = 0.5f),
            disabledLeadingIconColor = Color.Gray.copy(alpha = 0.5f),
            disabledTrailingIconColor = Color.Gray.copy(alpha = 0.5f)
        ),
    )
}
