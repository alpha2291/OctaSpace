package com.toletspot.houseforrent.Home_Screen.ProfileModule

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Profile_FF_List_Data
import com.toletspot.houseforrent.API.API_Result_Handling
import com.toletspot.houseforrent.API.StartUp_API.Get_User_Profile_API_Call
import com.toletspot.houseforrent.API.StartUp_API.follow_Unfollow_Delete_API_Call
import com.toletspot.houseforrent.API.StartUp_API.update_User_Profile_API_Call
import com.toletspot.houseforrent.AppPreferences
import com.toletspot.houseforrent.Chat.FirebaseHelper
import com.toletspot.houseforrent.Chat.FirebaseRepository
import com.toletspot.houseforrent.Chat.User
import com.toletspot.houseforrent.Custom_Assets.API_Fail_UI
import com.toletspot.houseforrent.Custom_Assets.Backer
import com.toletspot.houseforrent.Custom_Assets.ClickHelper
import com.toletspot.houseforrent.Custom_Assets.Common_DropDown2Options
import com.toletspot.houseforrent.Custom_Assets.Common_Popup
import com.toletspot.houseforrent.Custom_Assets.ExpandableText
import com.toletspot.houseforrent.Custom_Assets.LoadingShimmerEffect
import com.toletspot.houseforrent.Custom_Assets.LottiAnimation
import com.toletspot.houseforrent.Custom_Assets.Static_Bottom
import com.toletspot.houseforrent.Custom_Assets.customGridItems
import com.toletspot.houseforrent.Custom_Assets.getVideoThumbnailFP
import com.toletspot.houseforrent.Custom_Assets.logD
import com.toletspot.houseforrent.Custom_Assets.openDialer
import com.toletspot.houseforrent.Custom_Assets.rememberNotchHeightDp
import com.toletspot.houseforrent.Custom_Assets.toast
import com.toletspot.houseforrent.GlobalSnackbar
import com.toletspot.houseforrent.Home_Screen.Common_H_ViewModel
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.ViewDetailsFlow
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.logger
import com.toletspot.houseforrent.Home_Screen.from_DeepLink_Property
import com.toletspot.houseforrent.Home_Screen.set_FDLP_State
import com.toletspot.houseforrent.Navigation.ProfileScreenFlow
import com.toletspot.houseforrent.Navigation.UserCredentialsScreenFlow
import com.toletspot.houseforrent.NetworkStatus
import com.toletspot.houseforrent.R
import com.toletspot.houseforrent.S3Uploader
import com.toletspot.houseforrent.UI_DataClass.Common_DropDown2Options_DC
import com.toletspot.houseforrent.UI_DataClass.Profile_Handle_Back
import com.toletspot.houseforrent.UI_DataClass.ScreenType
import com.toletspot.houseforrent.constants
import com.toletspot.houseforrent.constants.Companion.PROFILE_IMAGE_URL
import com.toletspot.houseforrent.deviceToken
import com.toletspot.houseforrent.forTab
import com.toletspot.houseforrent.grayscaleMatrix
import com.toletspot.houseforrent.noRippleClickable
import com.toletspot.houseforrent.profileChangeErrorMessage
import com.toletspot.houseforrent.rememberNetworkStatus
import com.toletspot.houseforrent.ui.theme.new5757
import com.toletspot.houseforrent.ui.theme.newBlack
import com.toletspot.houseforrent.ui.theme.newBlue
import com.toletspot.houseforrent.ui.theme.newDarkGray
import com.toletspot.houseforrent.ui.theme.newGray
import com.toletspot.houseforrent.ui.theme.newLightBlue
import com.toletspot.houseforrent.ui.theme.newLightGray
import com.toletspot.houseforrent.ui.theme.newPurpleGradient
import com.toletspot.houseforrent.ui.theme.newPurpleGradientBorder
import com.toletspot.houseforrent.ui.theme.newRedGradienBg
import com.toletspot.houseforrent.ui.theme.newRedGradienBorder
import com.toletspot.houseforrent.ui.theme.newWhite
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.collections.isNotEmpty
import kotlin.text.isNotEmpty


enum class OwnProfileTab {
    ACTIVE,
    EXPIRED
}

data class ThumbnailData(
    val model: Any,
    val modifier: Modifier
)







var apiOnce = mutableStateOf(false)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile_Structure(navController: NavHostController, viewModel: Common_H_ViewModel ,onLogout: () -> Unit ) {




    BackHandler {
        println("Backhandler Restricted")
        viewModel.selectedBABTab(0)
        viewModel.toggleshowBABars(true)
        viewModel.toggleshowTABars(true)
    }

    val network = rememberNetworkStatus()


    val notchPadding = rememberNotchHeightDp()

    viewModel.toggleshowBABars(true)

    val BA_Bar_Listener = viewModel.showBABars.collectAsState()

    LaunchedEffect (BA_Bar_Listener.value){
        viewModel.toggleshowBABars(BA_Bar_Listener.value)
    }

    var deactivated by remember { mutableStateOf(false) }



    var clicker by remember { mutableStateOf(0) }

    var block_PopUp by remember { mutableStateOf(false) }

    var report_BS by remember { mutableStateOf(false) }

    val report_Options = constants.Profile_ViewModel.profile_Report_Options.collectAsState()

    val report_success = constants.Profile_ViewModel.report_Submit_Success.collectAsState()

    val open_Settings = constants.Profile_ViewModel.settings_Open.collectAsState()



    var retry by remember { mutableStateOf(0) }

    var failure = remember { mutableStateOf(false) }


    var apiResult by remember { mutableStateOf<Int?>(null) }


    val isLoading = constants.API_Vm.isLoading_Profile_Posts
    val errorMessage = constants.API_Vm.errorMessage_Profile_Posts
    val currentPage = constants.API_Vm.currentPage_Profile_Posts
    val totalPages = constants.API_Vm.totalPages_Profile_Posts
    val nxtpage = constants.API_Vm.nextPage_Profile_Posts
    val listState = rememberLazyListState()



    if (network.value == NetworkStatus.Online){
    DisposableEffect (true) {

        constants.Profile_ViewModel.put_Other_User_Id(0)
        println("HOW MANY TIMES")
            Get_User_Profile_API_Call { result ->
                apiResult = result
                println("RESULT OWN PROFILE -- ${result}")
            }


        constants.Profile_ViewModel.clear_All_BF_Handler()

        onDispose {
            println("OWN PROFILE DISPOSED")
        }
    }
    }
    else {
        GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
    }


    val profile_Content_Original = constants.Profile_ViewModel.own_Profile_Content.collectAsStateWithLifecycle()

    val profile_Content = constants.Profile_ViewModel.get_Content_own_Profile()


    val posts = constants.Profile_ViewModel.profile_Posts.collectAsStateWithLifecycle()


    println("API RESULT __ ${apiResult} DATA _4444${profile_Content_Original.value}__ ${ constants.Profile_ViewModel.get_Content_Own_Profile_Check()}  DHUVD __ ${ constants.Profile_ViewModel.get_Content_own_Profile()}")




    val tabs = listOf("Published" , "Expired")
    var selectedTab by remember { mutableStateOf(0) }
    var selectedTabTitle by remember { mutableStateOf("") }

    LaunchedEffect(AppPreferences.get_ProfileImage().isNotEmpty() , Unit) {
        println("PROFILE IMAGE SHARE PFRS -- ${AppPreferences.get_ProfileImage()} -- ${profile_Content?.profile_image ?: ""}")
        selectedTab = constants.Profile_ViewModel.onclickedProfileTab.value
    }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    when {

        apiResult == 5 -> {
            viewModel.toggleshowBABars(false)
            viewModel.toggleshowTABars(false)
            constants.Profile_ViewModel.set_Open_False()
            deactivated = true

            FirebaseRepository.setAccountDeleted(
                userId = AppPreferences.getUserId().toString(),
                isDeleted = true,
                onComplete = {
                    println("FIREBASE ACCOUNT DELECTED UPDATED")
                }
            )

        }

        network.value == NetworkStatus.Offline -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painterResource(R.drawable.nointernerdesign), "")
                Text(constants.activity.getString(R.string.no_Internet)
                    , color = newBlack,fontSize = constants.textUnit(16)
                    , fontFamily = constants.fontFamily(0)
                    , textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
        }

        apiResult == 0 && constants.Profile_ViewModel.get_Content_Own_Profile_Check() -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                , verticalArrangement = Arrangement.Center
                , horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottiAnimation(2)
            }
        }

        apiResult == 2 && constants.Profile_ViewModel.get_Content_Own_Profile_Check()-> {
            failure.value = true
            Box(modifier = Modifier
                .fillMaxSize()
                , contentAlignment = Alignment.Center
            ){
                API_Fail_UI (
                    onReTryClick = {
                        retry = retry + 1234
                        constants.API_Vm.errorMessage_Profile_Posts = ""
                    }
                )
            }
        }


        apiResult == 3 && constants.Profile_ViewModel.get_Content_Own_Profile_Check() -> {
            // no data
            failure.value = true
            Box(modifier = Modifier
                .fillMaxSize()
                , contentAlignment = Alignment.Center
            ){
                Image(painterResource(R.drawable.profile_empty_all) , "")
                Text("No Profile yet!")
                Text("try again sometime later." , textAlign = TextAlign.Center)
                }
        }

       //apiResult == 1 &&
        !constants.Profile_ViewModel.get_Content_Own_Profile_Check() -> {

            FirebaseRepository.setAccountDeleted(
                userId = AppPreferences.getUserId().toString(),
                isDeleted = false,
                onComplete = {
                    println("FIREBASE ACCOUNT DELECTED UPDATED")
                }
            )

            if (!profile_Content?.profile_image.isNullOrEmpty()) {
                AppPreferences.save_ProfileImage(profile_Content?.profile_image ?: "")
            }

//            AppPreferences.save_Email(profile_Content?)
            constants.Start_Up_ViewModel.set_Country(profile_Content?.country ?: constants.Start_Up_ViewModel.country.value)
            constants.Start_Up_ViewModel.set_State(profile_Content?.state ?: constants.Start_Up_ViewModel.state.value)
            constants.Start_Up_ViewModel.set_City(profile_Content?.city ?: constants.Start_Up_ViewModel.city.value)
            constants.Start_Up_ViewModel.set_Pincode(profile_Content?.pincode ?: constants.Start_Up_ViewModel.pincode.value)
            AppPreferences.save_Lat_Long(lat = profile_Content?.latitude ?: constants.Start_Up_ViewModel.latitude.value , profile_Content?.longitude ?: constants.Start_Up_ViewModel.longitude.value )

            AppPreferences.save_ph_number(profile_Content?.phone_num ?: "")

            val new = constants.Profile_ViewModel.profile_BF_Handler.collectAsState()

                   var retry_posts by remember { mutableStateOf(0) }

                   var failure_posts = remember { mutableStateOf(false) }


            // Initial load
            if (network.value == NetworkStatus.Online) {

                // ✅ INITIAL LOAD - Only load page 1 once
                LaunchedEffect(Unit,selectedTab, retry_posts) {
                    println("📍 Initial load triggered - retry_posts=$retry_posts")

                    // Reset state ONLY - Don't set isLoading here!
                    constants.Profile_ViewModel.clearPosts()
                    constants.API_Vm.currentPage_Profile_Posts = 0
                    constants.API_Vm.totalPages_Profile_Posts = 1
                    constants.API_Vm.nextPage_Profile_Posts = 1
                    constants.API_Vm.errorMessage_Profile_Posts = null

                    // Now call API (it will set isLoading internally)
                    constants.API_Vm.load_Profile_Posts(
                        user_id = AppPreferences.getUserId(),
                        others_id = 0,
                        status = if (selectedTab == 0) constants.active else constants.expired,
                        page = 1
                    )
                }

                // ✅ PAGINATION - Load more when scrolling
                LaunchedEffect(listState) {
                    snapshotFlow {
                        listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                    }.collect { lastVisibleItemIndex ->

                        val totalItems = listState.layoutInfo.totalItemsCount
                        val loadMoreThreshold = 3
                        val currentPage = constants.API_Vm.currentPage_Profile_Posts
                        val totalPages = constants.API_Vm.totalPages_Profile_Posts
                        val isLoading = constants.API_Vm.isLoading_Profile_Posts
                        val nextPage = constants.API_Vm.nextPage_Profile_Posts

                        println("📊 Scroll → last=$lastVisibleItemIndex total=$totalItems isLoading=$isLoading current=$currentPage/$totalPages")

                        // ✅ Load next page when near bottom
                        if (lastVisibleItemIndex != null &&
                            totalItems > 0 &&
                            lastVisibleItemIndex >= totalItems - loadMoreThreshold &&
                            !isLoading &&
                            currentPage < totalPages &&
                            nextPage > 0
                        ) {
                            println("🔄 Triggering next page load → $nextPage")

                            constants.API_Vm.load_Profile_Posts(
                                user_id = AppPreferences.getUserId(),
                                others_id = 0,
                                status = if (selectedTab == 0) constants.active else constants.expired,
                                page = nextPage
                            )
                        }
                    }
                }

            }
            else {
                GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
            }

            DisposableEffect  (!constants.Profile_ViewModel.get_Content_Own_Profile_Check()) {

                constants.Profile_ViewModel.add_BF_Handler(
                    Profile_Handle_Back(
                        current_UsedId = AppPreferences.getUserId(),
                        other_UserId = 0,
                        ff_User_Name = profile_Content?.username ?: "Username",
                        ff_Fw_Count = profile_Content?.followers ?: 999,
                        ff_Fg_Count = profile_Content?.following ?: 999,
                    )
                )



                constants.Profile_ViewModel.add_Current_Profile_UserId(
                    profile_Content?.user_id ?: 999
                )


                println("NEW FLOW OP  = ${new.value.map { it }}")


                constants.Profile_ViewModel.add_Selected_User_Name(
                    profile_Content?.username ?: "Profile"
                )

                println("STACK ADDING OWN PROFILE-- ${constants.Profile_ViewModel.currentBFHandler.value}")

                onDispose {
                    constants.Profile_ViewModel.add_BF_Handler(
                        Profile_Handle_Back(
                            current_UsedId = AppPreferences.getUserId(),
                            other_UserId = 0,
                            ff_User_Name = profile_Content?.username ?: "Username",
                            ff_Fw_Count = profile_Content?.followers ?: 999,
                            ff_Fg_Count = profile_Content?.following ?: 999,
                            selected_Tab = clicker
                        )
                    )
                }
            }

            FirebaseHelper.addOrUpdateUser(
                User(
                    userId = profile_Content?.user_id.toString(),
                    userName = profile_Content?.username ?: "",
                    name = profile_Content?.name ?:"",
                    mobileNumber = profile_Content?.phone_num ?:"",
                    mobileNumberCC = profile_Content?.phone_num_cc ?:"",
                    profileImage = profile_Content?.profile_image ?: "",
                    deviceToken = deviceToken,
                    isOnline = true,
                    isAccountDeleted = false,
                    lastSeen = System.currentTimeMillis(),
                ),
                onComplete = {
                    println("USER FIREBASE UPDATED FROM PROFILE")
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(newWhite)
                ,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {


                var tabUnit = if (forTab()) 3 else 2





                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 100.dp)
                        .padding(horizontal = 4.dp)
                    , state = listState
                )
                {
                    stickyHeader {


                        constants.spacer(2)
                        Box(
                            modifier = Modifier
                                .background(Color(0xffF7F0DC))
                                .fillMaxWidth()
                                .padding(top = rememberNotchHeightDp().value)
                                .padding(horizontal = 16.dp)
                            , contentAlignment = Alignment.Center
                        )
                        {

                            Text(
                                text = profile_Content?.username ?: "",
                                color = newBlack,
                                fontSize = constants.textUnit(24),
                                fontFamily = constants.fontFamily(0),
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )

                            Box(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .size(if (forTab()) 28.dp else 24.dp)
//                                    .clip(RoundedCornerShape(4.dp))
//                                    .border(
//                                        1.dp,
//                                        newGray,
//                                        RoundedCornerShape(4.dp)
//                                    )
                                    .background(Color.Transparent)
                                    .noRippleClickable {
                                        constants.Profile_ViewModel.set_open_settings()
                                    }, contentAlignment = Alignment.Center
                            ) {
                                AsyncImage(
                                    model = R.drawable.own_profile_settings_icon,
                                    "",
                                    modifier = Modifier
                                        .size(if (forTab()) 24.dp else 18.dp)
                                )
                            }
                        }
                    }

                    item {
                        // new top app bar
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            Color(0xffF7F0DC),
                                            Color(0xffE6C96A)
                                        )
                                    )
                                )
                            //.padding(top = if (forTab()) 16.dp else notchPadding.value)
                        )
                        {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(
                                                Color(0xffF7F0DC),
                                                Color(0xffE6C96A)
                                            )
                                        )
                                    )
                            )


                            Box(
                                modifier = Modifier
                                    .padding(top = 20.dp)
                            ) {

                                Box(
                                    modifier = Modifier
                                        .zIndex(2f)
                                        .align(Alignment.TopCenter)
                                        .clip(CircleShape)
                                        .wrapContentSize()
                                        .background(Color.White)
                                        .padding(6.dp)


                                )
                                {
                                    Box(
                                        modifier = Modifier

                                            .align(Alignment.TopCenter)
                                            .size(if (forTab()) 92.dp else 80.dp)
                                            .clip(CircleShape)
                                            .background(newWhite)
                                        //.zIndex(2f)
                                        //.padding(4.dp)

                                    )
                                    {
                                        println("OWN PROFILE IMAGE -- ${profile_Content?.profile_image}")

                                        SubcomposeAsyncImage(
                                            model = profile_Content?.profile_image
                                                ?: "".ifEmpty { AppPreferences.get_ProfileImage() },
                                            modifier = Modifier
                                                .clip(CircleShape)
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
                                                    //.padding(8.dp)
                                                    ,
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Text(
                                                        text = profile_Content?.username.takeIf { it?.isNotEmpty() == true }
                                                            ?.take(1)?.uppercase()
                                                            ?: ""
                                                    )
                                                }
                                            } else {
                                                SubcomposeAsyncImageContent()
                                            }
                                        }

                                    }
                                }

                                Column(
                                    modifier = Modifier
                                        .padding(top = if (forTab()) 46.dp else 40.dp)
                                        .zIndex(0f)
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        //clip(RoundedCornerShape(8.dp))
                                        .background(Color.White)
                                        .padding(horizontal = 16.dp)
                                        .padding(top = 40.dp, bottom = 16.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                )
                                {
                                    constants.spacer(4)
//                                                   Text(
//                                                       text = profile_Content?.username ?: "",
//                                                       color = newBlack,
//                                                       fontSize = constants.textUnit(18),
//                                                       fontFamily = constants.fontFamily(0)
//                                                   )
//                                                   constants.spacer(2)

                                    Text(
                                        text = profile_Content?.name ?: "",
                                        color = newGray,
                                        fontSize = constants.textUnit(14),
                                        fontFamily = constants.fontFamily(2)
                                    )
                                    constants.spacer(2)

//"This is about me in two hello lines. If the content gets longer, it will end with an ellipsis and a clickable 'see more' to view the full text."

                                    if (!profile_Content?.bio.isNullOrEmpty()) {
                                        ExpandableText(
                                            fullText = profile_Content?.bio ?: "",
                                            maxCharacters = 80,
                                            modifier = Modifier.fillMaxWidth(.9f)
                                        )
                                        //Spacer(modifier = Modifier.padding(4.dp))

                                        constants.spacer(2)
                                    }

                                    // follow follwing , post show box
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(.9f)
                                            .height(if (forTab()) 64.dp else 56.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .border(
                                                1.dp,
                                                newGray,
                                                RoundedCornerShape(8.dp)
                                            ),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceEvenly
                                    )
                                    {
                                        repeat(3) { itemIndex ->
                                            Column(
                                                modifier = Modifier
                                                    .noRippleClickable {
                                                        ClickHelper.getInstance()
                                                            .clickOnce {
                                                                if (ClickGuard.canClick()) {
                                                                    when (itemIndex) {
                                                                        1 -> {

                                                                            constants.Profile_ViewModel.clear_FF_Lists()


                                                                            /// following

                                                                            constants.Common_H_ViewModel.toggleshowBABars(
                                                                                false
                                                                            )
//
                                                                            // backtrack variable
//                                                                            constants.Profile_ViewModel.add_Tapped_FFs(
//                                                                                Profile_ViewModel.Tap_Flw_Flg_DC(
//                                                                                    id = profile_Content?.user_id
//                                                                                        ?: 0,
//                                                                                    tap_data = 0,
//                                                                                    flg_Count = profile_Content?.following
//                                                                                        ?: 0,
//                                                                                    flw_Count = profile_Content?.followers
//                                                                                        ?: 0
//                                                                                )
//                                                                            )


                                                                            /// new flowww

                                                                            // ✅ Add handler for FF screen
                                                                            if (new.value.isNotEmpty()) {
                                                                                constants.Profile_ViewModel.add_BF_Handler(
                                                                                    Profile_Handle_Back(
                                                                                        current_UsedId = AppPreferences.getUserId(),
                                                                                        other_UserId = 0,
                                                                                        selected_Tab = 0, // Followers
                                                                                        ff_User_Name = profile_Content?.username ?: "Username",
                                                                                        ff_Fw_Count = profile_Content?.followers ?: 0,
                                                                                        ff_Fg_Count = profile_Content?.following ?: 0,
                                                                                        screenType = ScreenType.FF_LIST // ✅ Set to FF_LIST
                                                                                    )
                                                                                )
                                                                            }


//                                                                            if (new.value.isNotEmpty()) {
//                                                                                constants.Profile_ViewModel.updateSelectedTab_BF_Handler(
//                                                                                    new.value.first().id,
//                                                                                    0
//                                                                                )
//                                                                            }

                                                                            clicker =
                                                                                0


                                                                            navController.navigate(
                                                                                ProfileScreenFlow.Profile_FF_Structure.route
                                                                            )
                                                                        }

                                                                        2 -> {

                                                                            constants.Profile_ViewModel.clear_FF_Lists()

                                                                            // followers

                                                                            constants.Common_H_ViewModel.toggleshowBABars(
                                                                                false
                                                                            )
//

                                                                            // backtrack var
//                                                                            constants.Profile_ViewModel.add_Tapped_FFs(
//                                                                                Profile_ViewModel.Tap_Flw_Flg_DC(
//                                                                                    id = profile_Content?.user_id
//                                                                                        ?: 1,
//                                                                                    tap_data = 1,
//                                                                                    flg_Count = profile_Content?.following
//                                                                                        ?: 1,
//                                                                                    flw_Count = profile_Content?.followers
//                                                                                        ?: 1
//                                                                                )
//                                                                            )

                                                                            // ✅ Add handler for FF screen
                                                                            if (new.value.isNotEmpty()) {
                                                                                constants.Profile_ViewModel.add_BF_Handler(
                                                                                    Profile_Handle_Back(
                                                                                        current_UsedId = AppPreferences.getUserId(),
                                                                                        other_UserId = 0,
                                                                                        selected_Tab = 1, // Following
                                                                                        ff_User_Name = profile_Content?.username ?: "Username",
                                                                                        ff_Fw_Count = profile_Content?.followers ?: 0,
                                                                                        ff_Fg_Count = profile_Content?.following ?: 0,
                                                                                        screenType = ScreenType.FF_LIST // ✅ Set to FF_LIST
                                                                                    )
                                                                                )
                                                                            }

                                                                            println(
                                                                                "COUNTS NOT ADIDING -222-${new.value} ${profile_Content?.following} -- ${profile_Content?.followers}"
                                                                            )

                                                                            /// new flowww

//                                                                            if (new.value.isNotEmpty()) {
//                                                                                constants.Profile_ViewModel.updateSelectedTab_BF_Handler(
//                                                                                    new.value.first().id,
//                                                                                    1
//                                                                                )
//                                                                            }

                                                                            clicker =
                                                                                1



                                                                            navController.navigate(
                                                                                ProfileScreenFlow.Profile_FF_Structure.route
                                                                            )
                                                                        }

                                                                        else -> {}
                                                                    }
                                                                }
                                                            }
                                                    },
                                                verticalArrangement = Arrangement.Center,
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Text(
                                                    text = when (itemIndex) {
                                                        0 -> " ${profile_Content?.posts ?: 0}"
                                                        1 -> "${profile_Content?.followers ?: 999}"
                                                        2 -> "${profile_Content?.following.toString() ?: 999}"
                                                        else -> "48"
                                                    },
                                                    color = newBlack,
                                                    fontSize = constants.textUnit(
                                                        14
                                                    ),
                                                    fontFamily = constants.fontFamily(
                                                        0
                                                    )
                                                )

                                                Text(
                                                    text = when (itemIndex) {
                                                        0 -> "Properties"
                                                        1 -> "Followers"
                                                        2 -> "Following"
                                                        else -> "Extra"
                                                    },
                                                    color = newBlack,
                                                    fontSize = constants.textUnit(
                                                        12
                                                    ),
                                                    fontFamily = constants.fontFamily(
                                                        3
                                                    )
                                                )
                                            }

                                            if (itemIndex != 2) {
                                                VerticalDivider(
                                                    modifier = Modifier.padding(
                                                        vertical = 8.dp
                                                    )
                                                )
                                            }
                                        }
                                    }

                                    //Spacer(modifier = Modifier.padding(4.dp))

//                                                   constants.spacer(2)
//
//                                                   Row(
//                                                       modifier = Modifier
//                                                           .fillMaxWidth(.9f)
//                                                           .height(if (forTab()) 44.dp else 32.dp)
//                                                           .clip(RoundedCornerShape(8.dp))
//                                                           .background(Color(0xffF7F0DC))
//                                                           .border(
//                                                               1.dp,
//                                                               newBlue,
//                                                               RoundedCornerShape(8.dp)
//                                                           )
//                                                           .noRippleClickable {
//                                                               constants.Profile_ViewModel.enable_Edit_Profile()
//                                                           },
//                                                       verticalAlignment = Alignment.CenterVertically,
//                                                       horizontalArrangement = Arrangement.Center
//                                                   ) {
//                                                       Text(
//                                                           text = "Edit profile",
//                                                           color = newBlack,
//                                                           fontSize = constants.textUnit(14),
//                                                           fontFamily = constants.fontFamily(0)
//                                                       )
//                                                   }

                                    constants.spacer(2)

                                }
                            }
                        }
                        // top bar end
                    }

                    item {

                        Column {
                            TabRow(
                                selectedTabIndex = selectedTab,
                                containerColor = Color.White,
                                indicator = { tabPositions ->
                                    TabRowDefaults.Indicator(
                                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                                        color = Color.Black
                                    )
                                },
                                divider = {}
                            ) {
                                tabs.forEachIndexed { index, title ->
                                    Tab(
                                        selected = selectedTab == index,
                                        onClick = {
                                            selectedTab = index
                                            constants.Profile_ViewModel.onclickedProfileTab.value = index
                                            selectedTabTitle = title
                                                  },
                                        selectedContentColor = Color.Black,
                                        unselectedContentColor = Color.Gray,
                                        text = { Text(title) }
                                    )
                                }
                            }

                            constants.spacer(4)
                        }






//                        Column() {
//                            SecondaryTabRow(
//                                selectedTabIndex = selectedTab,
//                                divider = {},
//                                containerColor = Color.White) {
//                                tabs.forEachIndexed { index, title ->
//                                    Tab(
//                                        text = { Text(title) },
//                                        selected = selectedTab == index,
//                                        onClick = { selectedTab = index }
//                                    )
//                                }
//                            }
//
//                            constants.spacer(4)
//                        }
                    }



                    when {
                        isLoading && posts.value.isEmpty() -> {
                            item {
                                Column(
                                    modifier = Modifier
                                        .padding(top = if (forTab()) 154.dp else 64.dp)
                                        .fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    LottiAnimation(2)
                                }
                            }
                        }

                        !errorMessage.isNullOrEmpty() -> {
                            item {
                                Box (
                                    modifier = Modifier
                                        .padding(top = if (forTab()) 154.dp else 64.dp)
                                        .fillMaxSize()
                                    ,contentAlignment = Alignment.Center
                                ) {

                                    API_Fail_UI(onReTryClick = {
                                        retry = retry + 213435
                                    })
                                }
                            }
                        }

                        !isLoading && posts.value.isEmpty() -> {
                            item {
                                // no data
                                if (selectedTab == 0){
                                    Column(
                                        modifier = Modifier
                                            .padding(top = if (forTab()) 154.dp else 64.dp)
                                            .fillMaxSize()
                                        // .background(newBlue)
                                        , verticalArrangement = Arrangement.Bottom,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    )
                                    {
                                        Spacer(modifier = Modifier.padding(8.dp))
                                        Image(
                                            painterResource(R.drawable.emptysavedprosrento),
                                            ""
                                        )
                                        Spacer(modifier = Modifier.padding(8.dp))
                                        Text("No Properties Listed!"
                                            ,textAlign = TextAlign.Center
                                            , color = newBlack
                                            , fontSize = constants.textUnit(14)
                                            , fontFamily = constants.fontFamily(2))
                                        Spacer(modifier = Modifier.padding(8.dp))
                                        Text(
                                            "Create your first land post today.",
                                            textAlign = TextAlign.Center
                                            , color = newBlack
                                            , fontSize = constants.textUnit(14)
                                            , fontFamily = constants.fontFamily(2)
                                        )
                                    }
                                }
                                else {
                                    Column(
                                        modifier = Modifier
                                            .padding(top = if (forTab()) 154.dp else 64.dp)
                                            .fillMaxSize()
                                        // .background(newBlue)
                                        , verticalArrangement = Arrangement.Bottom,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    )
                                    {
                                        Spacer(modifier = Modifier.padding(8.dp))
                                        Image(
                                            painterResource(R.drawable.expiredemptyplaceholder),
                                            ""
                                        )
                                        Spacer(modifier = Modifier.padding(8.dp))
                                        Text("No properties expired right now."
                                            ,textAlign = TextAlign.Center
                                            , color = newBlack
                                            , fontSize = constants.textUnit(14)
                                            , fontFamily = constants.fontFamily(2))

                                    }
                                }

                            }
                        }

                        posts.value.isNotEmpty() -> {
                            println("POSTS DATA PROFILE -- ${posts.value.map { it.user_post_id }}")


                            customGridItems(
                                count = posts.value.size,
                                nColumns = tabUnit
                                //, horizontalArrangement = Arrangement.spacedBy(16.dp)
                            )
                            { itemIndex ->

                                val item = posts.value[itemIndex]
                                Box(
                                    modifier = Modifier
                                        .padding(vertical = 8.dp, horizontal = 8.dp)
                                        //.padding(top = 16.dp)
                                        .height(216.dp)
                                        .width(162.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(Color(0xffEBEBEB))
                                        .noRippleClickable {
                                            constants.Profile_ViewModel.change_OwnProfileTab(
                                                selectedTab
                                            )
                                            constants.Reels_ViewModel.clear_view_pro_Details()
                                            AppPreferences.save_Post_Id(item.user_post_id)
                                            constants.Search_ViewModel.select_RS_Reels_Flow(
                                                itemIndex
                                            )
                                            constants.Reels_ViewModel.setReelsContent(
                                                posts.value.map { it.toReelsData() }  // map each element to Get_Reels_Data
                                            )

                                            constants.PostProperty_ViewModel.set_Post_Form_Flow(
                                                -1
                                            )
//                                            constants.PostProperty_ViewModel.setViewDetailsFlow(
//                                                ViewDetailsFlow.OWN
//                                            )

                                            println("SELECTED TABBBB -- ${selectedTabTitle}")
                                            if (selectedTabTitle == "Expired") {
                                                println("SELECTED TABBBB11111 -- ${selectedTabTitle}")

                                                constants.PostProperty_ViewModel.setViewDetailsFlow(
                                                    ViewDetailsFlow.EXPIRY
                                                )
                                            }
                                            else {
                                                println("SELECTED TABBBB2222 -- ${selectedTabTitle}")

                                                constants.PostProperty_ViewModel.setViewDetailsFlow(
                                                    ViewDetailsFlow.OWN
                                                )
                                            }

                                            constants.Common_H_ViewModel.dismiss_Cmt_btm_Sheet()

                                            if (constants.Reels_ViewModel.get_Reels_Data()) {
                                                navController.navigate(
                                                    ProfileScreenFlow.ReelsView_Search_Flow.route + "/$itemIndex"
                                                )
                                            }
                                        }
                                    , contentAlignment = Alignment.Center
                                ) {

                                    var thumbnailData by remember {
                                        mutableStateOf(ThumbnailData(R.drawable.emptypostsrento, modifier = Modifier.size(70.dp)))
                                    }

                                    LaunchedEffect(item.post_property.video.firstOrNull()?.url) {
                                        thumbnailData =
                                            when {
                                                item.thumbnail.isNotEmpty() -> {
                                                    ThumbnailData(
                                                        model = item.post_property.thumbnail ?: "",
                                                        modifier = Modifier.fillMaxSize()
                                                    )
                                                }

                                                item.post_property.images.isEmpty() && item.post_property.video.isNotEmpty() -> {
                                                    ThumbnailData(
                                                        model = getVideoThumbnailFP(context, item.post_property.video.firstOrNull()?.url ?: "")
                                                            ?: R.drawable.emptypostsrento,
                                                        modifier = Modifier.fillMaxSize()
                                                    )
                                                }

                                                item.post_property.images.isEmpty() && item.post_property.video.isEmpty() -> {
//                                                                   ThumbnailData(
//                                                                       model = R.drawable.photorequestimage,
//                                                                       modifier = Modifier.size(80.dp)
//                                                                   )
                                                    ThumbnailData(
                                                        model = R.drawable.emptypostsrento,
                                                        modifier = Modifier.size(100.dp)
                                                    )
                                                }

                                                else -> {
                                                    ThumbnailData(
                                                        model = R.drawable.emptypostsrento,
                                                        modifier = Modifier.size(100.dp)
                                                    )
                                                }
                                            }
                                    }

                                    logger("CREATE" , "$thumbnailData ** ${item.post_property.video.firstOrNull()?.url ?: ""}")
                                    SubcomposeAsyncImage(
                                        model = thumbnailData.model
                                        // item?.thumbnail ?: ""
                                        , modifier = thumbnailData.modifier
                                        //.size(40.dp)

                                        , contentDescription = ""
                                        , contentScale = ContentScale.FillBounds
                                        , colorFilter = if (selectedTab == 1)ColorFilter.colorMatrix(grayscaleMatrix) else null
                                    )
                                    {
                                        val state = painter.state
                                        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .background(Color(0xffEBEBEB))
                                                , contentAlignment = Alignment.Center
                                            ) {
                                                Image(painterResource(R.drawable.emptypostsrento) , ""
                                                    , colorFilter = if (selectedTab == 1)ColorFilter.colorMatrix(grayscaleMatrix) else null
                                                    , modifier = Modifier.size( 100.dp)
                                                )
                                            }
                                        } else {
                                            SubcomposeAsyncImageContent()
                                        }
                                    }

                                }
                            }

                        }

                    }
                }
            }


        }
    }




    /// settings
    AnimatedVisibility(
        visible = open_Settings.value
        , enter = slideInHorizontally (tween(400)){ it }
        , modifier = Modifier
            .fillMaxSize()
    )
    {

        if (open_Settings.value) {
            viewModel.toggleshowBABars(false)
            Settings_Main(navController , onLogout = onLogout)
        }
    }


    // block popup
    Common_Popup(
       visible =  block_PopUp,
        modifier = Modifier
            .background(Color(0xffF7F0DC))
    )
    {
        Column (
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 24.dp)
            , verticalArrangement = Arrangement.spacedBy(12.dp)
            , horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            Spacer(modifier = Modifier.padding(2.dp))
//            constants.spacer(2)

            Text(
                text = "Block Akash Kishore ?",
                color = newBlack,
                fontSize = constants.textUnit(16),
                fontFamily = constants.fontFamily(0)
            )

//            constants.spacer(2)

            Text(
                text = "After blocking, your posts won’t visible to them. Are you sure you want to block?",
                color = newBlack,
                fontSize = constants.textUnit(12),
                fontFamily = constants.fontFamily(3)
                , textAlign = TextAlign.Center
                , lineHeight = 12.sp
                    , modifier = Modifier.padding(if (forTab()) 36.dp else 0.dp)

            )

            Spacer(modifier = Modifier.padding(2.dp))
            constants.spacer(2)

            Row (
                modifier = Modifier
                , horizontalArrangement = Arrangement.Center
                , verticalAlignment = Alignment.CenterVertically
            )
            {
                Box(
                    modifier = Modifier
                        .weight(4f)
                        .height(if (forTab()) 46.dp else 36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xffE8E8E8))
                        .noRippleClickable {
                            block_PopUp = false
                        }
                    , contentAlignment = Alignment.Center
                ){
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
                        .height(if (forTab()) 46.dp else 36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(newBlue)
                    , contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Block",
                        color = Color.White,
                        fontSize = constants.textUnit(16),
                        fontFamily = constants.fontFamily(0)
                    )
                }
            }

            constants.spacer(2)
        }
    }

    Common_Popup(
        visible = deactivated,
        modifier = Modifier.background(Color(0xffFCEDEC)),
        image = "",
        icon = 0,
            //R.drawable.deactivated,
        userName = "",
        content = {
            Column (
                modifier = Modifier
                , verticalArrangement = Arrangement.spacedBy(8.dp)
                , horizontalAlignment = Alignment.CenterHorizontally
            ){
                constants.spacer(2)

                Image(painter = painterResource(R.drawable.rentoprofileblock) , "",
                    modifier = Modifier.size(64.
                    dp))
                constants.spacer(2)


                Text("Account Restricted"
                    , color = Color.Black
                    , fontSize = constants.textUnit(16)
                    , fontFamily = constants.fontFamily(0)
                )

                constants.spacer(2)

                Text("Your account has been reported multiple times for violating our community standards. Your account has been temporarily restricted. You can appeal this decision upon login, if you believe it was a mistake."
                    , color = Color(0xff484848)
                    , fontSize = constants.textUnit(12)
                    , fontFamily = constants.fontFamily(3)
                    , modifier = Modifier.padding(horizontal = 16.dp)
                   , lineHeight = 24.sp
                    , textAlign = TextAlign.Center
                )

                constants.spacer(8)

                Row() {
                    /*Box(
                        modifier = Modifier
                            .height(32.dp)
                            .fillMaxWidth(.9f)
                            .noRippleClickable {

                                ClickHelper.getInstance().clickOnce {
                                    if (ClickGuard.canClick()) {
                                        AppPreferences.clearAll()

                                        constants.Profile_ViewModel.set_open_settings()
                                        constants.Profile_ViewModel.onSet_Settings_Click(0)

                                        constants.Common_H_ViewModel.changeStatus(false)

                                        constants.Common_H_ViewModel.toggleshowBABars(true)
                                        constants.Start_Up_ViewModel.updateLoginState(0)
                                        constants.Start_Up_ViewModel.phoneNumber = ""
                                        constants.Start_Up_ViewModel.countryCode = "+91"
                                        constants.Start_Up_ViewModel.userName = ""
                                        constants.Start_Up_ViewModel.otp = ""


                                        //constants.Profile_ViewModel.dismiss_Logout_PP()

                                        constants.Profile_ViewModel.onSet_Settings_Click(-1)
                                        constants.Profile_ViewModel.setSelected_AS_Settings("")
                                        onLogout()
                                        deactivated = false
                                        constants.Profile_ViewModel.dismiss_Logout_PP()
                                    }
                                }
                            }, contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Disagree with Decision",
                            color = Color.White,
                            fontSize = constants.textUnit(14),
                            fontFamily = constants.fontFamily(0)
                        )
                    }*/

                    Text(
                        "Okay",
                        color = Color(0xff484848),
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(0),
                        modifier = Modifier
                            .noRippleClickable {
                                ClickHelper.getInstance().clickOnce {
                                    if (ClickGuard.canClick()) {
                                        constants.Profile_ViewModel.set_open_settings()
                                        constants.Profile_ViewModel.onSet_Settings_Click(0)

                                        constants.Common_H_ViewModel.changeStatus(false)

                                        constants.Common_H_ViewModel.toggleshowBABars(true)
                                        constants.Start_Up_ViewModel.updateLoginState(0)
                                        constants.Profile_ViewModel.onSet_Settings_Click(-1)
                                        constants.Start_Up_ViewModel.phoneNumber = ""
                                        constants.Start_Up_ViewModel.countryCode = "+91"
                                        constants.Start_Up_ViewModel.userName = ""
                                        constants.Start_Up_ViewModel.otp = ""


                                        constants.Profile_ViewModel.dismiss_Logout_PP()
                                        onLogout()
                                    }
                                }
                            }
                    )
                }

                //Spacer(modifier = Modifier.padding(8.dp))
                constants.spacer(8)
            }
        }
    )



    /// report bottom sheet
    if (report_BS){

        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )

        ModalBottomSheet(
            onDismissRequest = {
                constants.Profile_ViewModel.toggle_ReportSucces_False()

                report_BS = false
            },
            sheetState = sheetState
            , containerColor = newWhite
        )
        {
            Column (
                modifier = Modifier
                    , verticalArrangement = Arrangement.Top
            ){
                val user_Manual_report = remember { mutableStateOf(false) }
                val user_Manual_report_String = remember { mutableStateOf("") }



                AnimatedContent (
                    targetState = report_success
                )
                {
                        targetState ->

                    if (targetState.value) {
                        Column(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(horizontal = 16.dp)
                            , verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        )
                        {
                            Text(
                                text = "Why are you reporting ?",
                                color = newBlack,
                                fontSize = constants.textUnit(24),
                                fontFamily = constants.fontFamily(0),
                                modifier = Modifier.align(Alignment.Start)
                            )

                            report_Options.value.forEachIndexed { index, profileReportOptionsDc ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = profileReportOptionsDc.option_title,
                                        color = newBlack,
                                        fontSize = constants.textUnit(14),
                                        fontFamily = constants.fontFamily(3)
                                    )

                                    RadioButton(
                                        selected = profileReportOptionsDc.isSelected,
                                        onClick = {
                                            user_Manual_report.value = false
                                            if (index < report_Options.value.size - 1) {
                                                constants.Profile_ViewModel.toggle_ProfileReport_Options(
                                                    profileReportOptionsDc.id
                                                )
                                            } else {
                                                user_Manual_report.value = true
                                            }
                                        }
                                    )
                                }
                            }

                            AnimatedVisibility(
                                user_Manual_report.value,
                                enter = slideInHorizontally(tween(900)) { it }
                            ) {
                                /* Box(
                                 modifier = Modifier
                                     .fillMaxWidth()
                                     .heightIn(min = 50.dp , max = 80.dp)
                                     .clip(RoundedCornerShape(8.dp))
                                     .background(Color.White)
                                     .border(1.dp , newGray , RoundedCornerShape(8.dp))
                             )
                             {
                                 TextField(
                                     value = user_Manual_report_String.value,
                                     onValueChange = {
                                         user_Manual_report_String.value = it
                                     },
                                     placeholder = {
                                         Text(
                                             text = "What else we need to know...",
                                             color = newBlack,
                                             fontSize = constants.textUnit(12),
                                             fontFamily = constants.fontFamily(3)
                                         )
                                     },
                                     colors = TextFieldDefaults.colors(
                                         focusedContainerColor = Color.White
                                         ,unfocusedContainerColor = Color.White
                                         , focusedIndicatorColor = Color.Transparent
                                         , unfocusedIndicatorColor = Color.Transparent
                                         , focusedTextColor = newBlack
                                         , unfocusedTextColor = newGray
                                     )
                                 )
                             }*/
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color.White)
                                        .border(1.dp, newGray, RoundedCornerShape(8.dp))
                                )
                                {
                                    BasicTextField(
                                        value = user_Manual_report_String.value,
                                        onValueChange = { user_Manual_report_String.value = it },
                                        modifier = Modifier
                                            .padding(12.dp)
                                            .fillMaxWidth(),
                                        textStyle = TextStyle(
                                            color = newBlack,
                                            fontSize = constants.textUnit(14),
                                            fontFamily = constants.fontFamily(2)
                                        ),
                                        decorationBox = { innerTextField ->
                                            if (user_Manual_report_String.value.isEmpty()) {
                                                Text(
                                                    text = "Enter reason...",
                                                    color = newGray,
                                                    fontSize = constants.textUnit(14),
                                                    fontFamily = constants.fontFamily(2)
                                                )
                                            }
                                            innerTextField()
                                        }
                                    )
                                }

                            }
                        }
                    }
                    else {
                        Column(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                            , verticalArrangement = Arrangement.SpaceEvenly
                            , horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            SubcomposeAsyncImage(
                                model = R.drawable.profile_report_submit_success
                                ,""
                                , modifier = Modifier
                                    .size(150.dp)
                            )


                            Text(
                                text = "Submitted Successfully",
                                color = newBlack,
                                fontSize = constants.textUnit(18),
                                fontFamily = constants.fontFamily(0)
                            )


                            Text(
                                text = "Thank you for bringing this to our attention.",
                                color = newBlack,
                                fontSize = constants.textUnit(12),
                                fontFamily = constants.fontFamily(3)
                            )

                        }
                    }
                }

                Spacer(modifier = Modifier.padding(8.dp))

                if (report_success.value){
                    Static_Bottom(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(92.dp)
                        , content = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                , contentAlignment = Alignment.Center
                            )
                            {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(.9f)
                                        .fillMaxHeight(.7f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(newBlue)
                                        .noRippleClickable {
                                            constants.Profile_ViewModel.toggleReportSubmissionSuccess()
                                        }
                                    , contentAlignment = Alignment.Center
                                ){
                                    Text(
                                        text = "Submit Report",
                                        color = Color.White,
                                        fontSize = constants.textUnit(14),
                                        fontFamily = constants.fontFamily(0)
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
    }


    /*///edit profile view
    AnimatedVisibility(
        visible = edit_profile_Listener.value
        , enter = slideInHorizontally (tween(600)){ it }
        , exit = slideOutHorizontally (tween(600)) { it }
        , modifier = Modifier
            .fillMaxSize()
    )
    {
        if (edit_profile_Listener.value) {
           viewModel.toggleshowBABars(false)
        }
        else {
            triggerEdited_Profile_details.value = triggerEdited_Profile_details.value + 9876
        }
            Edit_Profile(notchPadding ,navController)
    }*/

}


//var isLoadingChange =  mutableStateOf(false)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Other_Profile_Structure(navController: NavHostController, viewModel: Common_H_ViewModel)
{

    BackHandler {
        println("Backhandler Restricted other profile")
    }

    var isLoadingChange = remember { mutableStateOf(false) }


    val network = rememberNetworkStatus()


    val notchPadding = rememberNotchHeightDp()


    val BA_Bar_Listener = viewModel.showBABars.collectAsState()


    LaunchedEffect (BA_Bar_Listener.value , Unit){
        viewModel.toggleshowBABars(BA_Bar_Listener.value)
        viewModel.toggleshowTABars(false)
        viewModel.toggleshowTABars(false)
    }

    val profile_Content = constants.Profile_ViewModel.selected_User_Profile.collectAsState()

    var block_PopUp by remember { mutableStateOf(false) }
    var report_BS by remember { mutableStateOf(false) }
    val expanded = remember { mutableStateOf(false) }

    val report_Options = constants.Profile_ViewModel.profile_Report_Options.collectAsState()

    val report_success = constants.Profile_ViewModel.report_Submit_Success.collectAsState()

    val unfollow_PUP = constants.Profile_ViewModel.unFollowClick.collectAsState()

    var from_DLP_State = from_DeepLink_Property.collectAsStateWithLifecycle()

    //val profile_Mode = constants.Profile_ViewModel.switch_Profile_Mode.collectAsState()

    var apiResult by remember { mutableStateOf<Int?>(null) }

    val context = LocalContext.current


    val getter = constants.Profile_ViewModel.show_Current_BF_Handler()

    var test = constants.Profile_ViewModel.profile_BF_Handler.collectAsState()

    println("CHECKING NOWWWWW --- ${getter} %%% -${test}- ")



    val is_Search_State_FF = constants.Profile_ViewModel.is_Search_Enabled.collectAsStateWithLifecycle()

    val isLoading = constants.API_Vm.isLoading_Profile_Posts
    val errorMessage = constants.API_Vm.errorMessage_FF
    val currentPage = constants.API_Vm.currentPage_Profile_Posts
    val totalPages = constants.API_Vm.totalPages_Profile_Posts
    val listState = rememberLazyListState()


    var retry by remember { mutableStateOf(0) }

    var failure = remember { mutableStateOf(false) }


    val posts = constants.Profile_ViewModel.profile_Posts.collectAsStateWithLifecycle()


    if (network.value == NetworkStatus.Online){
    DisposableEffect (Unit ,retry) {

        println("PRINTLN 1 = ${constants.Profile_ViewModel.get_Other_User_Id()}")
        constants.Profile_ViewModel.put_Other_User_Id(getter?.other_UserId ?: 0)
        println("PRINTLN 2 = ${constants.Profile_ViewModel.get_Other_User_Id()}")

        Get_User_Profile_API_Call { result ->
            apiResult = result
            println("APIRESULT -- ${apiResult}")
        }


        onDispose {
            println("OTHER PROFILES DISPOSED")
        }
    }
    }
    else {
        GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
    }



    when {



        apiResult == 5 -> {
            viewModel.toggleshowBABars(false)
            viewModel.toggleshowTABars(false)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = if (forTab()) 16.dp else rememberNotchHeightDp().value)
                , contentAlignment = Alignment.Center
            ) {
                Backer(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .align(Alignment.TopStart)
                    , onBackClick = {
                        // constants.Profile_ViewModel.remove_Selected_User_Name_last()
                        //constants.Profile_ViewModel.remove_Tapped_FFs_last()
                        //constants.Profile_ViewModel.setProfileSelectedTab(profile_Content.value!!.type)

                        constants.Profile_ViewModel.remove_last_BF_Handler()

                        navController.navigateUp()

                    }
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                    // .background(newBlue)
                    , verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Spacer(modifier = Modifier.padding(8.dp))
                    Image(
                        painterResource(R.drawable.profile_empty_all),
                        ""
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text("Profile Unavailable")
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(
                        "This profile has been Deleted or Reported",
                        textAlign = TextAlign.Center
                    )
                }
            }

        }

        network.value == NetworkStatus.Offline -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {


                Row (
                    modifier = Modifier
                        .padding(top = if (forTab()) 16.dp else rememberNotchHeightDp().value)
                        .align(Alignment.Start)
                        .weight(1f)
                    , verticalAlignment = Alignment.CenterVertically
                    , horizontalArrangement = Arrangement.spacedBy(8.dp)
                )
                {
                    Backer(
                        modifier = Modifier
                        , onBackClick = {
                            ClickHelper.getInstance().clickOnce {
                                if (ClickGuard.canClick()) {
                                    if (from_DLP_State.value) {
                                        println("waesdgrjhkl")
                                        viewModel.toggleshowBABars(true)
                                        viewModel.toggleshowTABars(true)


                                        constants.Profile_ViewModel.put_Other_User_Id(
                                            0
                                        )

                                        constants.Profile_ViewModel.add_Selected_Profile_Id(
                                            0
                                        )

                                        AppPreferences.save_Post_Id(0)

                                        navController.navigate(
                                            UserCredentialsScreenFlow.Common_Screen.route
                                        ) {
                                            popUpTo(navController.graph.startDestinationId) {
                                                inclusive = true
                                            }
                                        }
                                        set_FDLP_State(false)

                                    } else {
                                        // constants.Profile_ViewModel.remove_Selected_User_Name_last()
                                        //constants.Profile_ViewModel.remove_Tapped_FFs_last()
                                        //constants.Profile_ViewModel.setProfileSelectedTab(profile_Content.value!!.type)

                                        constants.Profile_ViewModel.remove_last_BF_Handler()


                                        navController.navigateUp()
                                    }
                                }
                            }



                        }
                    )

                    Text(
                        text = "Profile",
                        color = newBlack,
                        fontSize = constants.textUnit(24),
                        fontFamily = constants.fontFamily(0)
                    )
                }



                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(9f)
                        , verticalArrangement = Arrangement.Center
                        , horizontalAlignment = Alignment.CenterHorizontally
                    ){
                    Image(
                        painterResource(R.drawable.nointernerdesign), "",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                        Spacer(modifier = Modifier.padding(8.dp))

                    Text(
                        constants.activity.getString(R.string.no_Internet),
                        color = newBlack,
                        fontSize = constants.textUnit(16),
                        fontFamily = constants.fontFamily(0),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                        }

            }
        }

        apiResult == 0 && constants.Profile_ViewModel.get_Content_Others_Profile_Check() -> {
            Column(
                modifier = Modifier
                    .padding(top = 64.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottiAnimation(2)
            }
        }


        apiResult == 2 -> {
            Box (
                modifier = Modifier
                    .padding(top = 64.dp)
                    .fillMaxSize()
                ,contentAlignment = Alignment.Center
            ) {

                API_Fail_UI(onReTryClick = {
                    retry = retry + 213435
                })
            }
        }


        apiResult == 3 && constants.Profile_ViewModel.get_Content_Others_Profile_Check() -> {
            // no data
            Column(
                modifier = Modifier
                    .padding(top = 64.dp)
                    .fillMaxSize()
                // .background(newBlue)
                , verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Spacer(modifier = Modifier.padding(8.dp))
                Image(
                    painterResource(R.drawable.empty_posts),
                    ""
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text("No Properties Listed!")
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    "This profile hasn’t added any land listings. Check back later .",
                    textAlign = TextAlign.Center
                )
            }
        }

        //apiResult == 1 &&
        !constants.Profile_ViewModel.get_Content_Others_Profile_Check() -> {

            val userBFHandler = constants.Profile_ViewModel.show_Current_BF_Handler()

            // ✅ ADD HANDLER FOR BOTH DEEP LINK AND NORMAL NAVIGATION
           /* DisposableEffect(Unit) {
                // Only add if not already added (check if this profile is already in the stack)
                val currentStack = constants.Profile_ViewModel.profile_BF_Handler.value
                val alreadyInStack = currentStack.any {
                    it.other_UserId == profile_Content.value?.user_id
                }

                if (!alreadyInStack) {
                    println("➕ Adding handler for other profile: ${profile_Content.value?.username}")

                    constants.Profile_ViewModel.add_BF_Handler(
                        Profile_Handle_Back(
                            current_UsedId = AppPreferences.getUserId(),
                            other_UserId = profile_Content.value?.user_id ?: 0,
                            ff_User_Name = profile_Content.value?.username ?: "",
                            ff_Fw_Count = profile_Content.value?.followers ?: 0,
                            ff_Fg_Count = profile_Content.value?.following ?: 0,
                        )
                    )
                }

                onDispose {
                    println("🗑️ Other_Profile_Structure disposed")
                }
            }*/

            // ✅ Only observe the profile data, don't add handler here
            DisposableEffect(profile_Content.value?.user_id) {
                if (profile_Content.value != null) {
                    val profileId = profile_Content.value?.user_id ?: 0
                    constants.Profile_ViewModel.add_Current_Profile_UserId(profileId)

                    println("👁️ Observing profile: ${profile_Content.value?.username} (ID: $profileId)")
                }

                onDispose {
                    println("🗑️ Other_Profile_Structure disposed")
                }
            }

            if (from_DLP_State.value){
                constants.Profile_ViewModel.add_Selected_User_Name(
                    profile_Content.value?.username ?: "UserName"
                )



//                new flowwewwwwwww
//                constants.Profile_ViewModel.add_BF_Handler(
//                    Profile_Handle_Back(
//                        current_UsedId = AppPreferences.getUserId(),
//                        other_UserId = profile_Content.value?.user_id ?: 0,
//                        ff_User_Name = profile_Content.value?.username ?: "",
//                        ff_Fw_Count = profile_Content.value?.followers ?: 0,
//                        ff_Fg_Count = profile_Content.value?.following ?: 0,
//                         is_Search_Enabled = is_Search_Enabled.value,
//                         search_Text = search_Text.value
//                    )
//                )

                /// println("ITEM PROFILE STRUCTURE __ ${is_Search_Enabled.value} -- ${constants.Profile_ViewModel.profile_BF_Handler.value}")

                println("GIVEN OTHER USER ID -- ${constants.Profile_ViewModel.get_Other_User_Id()}")

                constants.Profile_ViewModel.addProfile(
                    profile_Content.value?.user_id ?: 0
                )
                constants.Profile_ViewModel.add_Selected_Profile_Id(
                    id = profile_Content.value?.user_id ?: 0
                )
            }


            // ✅ FIX: Detect when navigating to a different profile
            LaunchedEffect(getter?.other_UserId) {
                if (getter?.other_UserId != null && getter.other_UserId != 0) {
                    // Show shimmer immediately when profile ID changes
                    isLoadingChange.value = true

                    // Keep shimmer visible until new data arrives
                    // The shimmer will be hidden after profile_Content updates (see next LaunchedEffect)
                }
            }

// Keep your existing LaunchedEffect for hiding shimmer after data loads
            LaunchedEffect(profile_Content.value?.user_id) {
                if (profile_Content.value != null) {
                    delay(100) // Small delay for smooth transition
                    isLoadingChange.value = false
                }
            }

            var retry_posts by remember { mutableStateOf(0) }

            if (network.value == NetworkStatus.Online) {

                // ✅ INITIAL LOAD - Only load page 1 once
                LaunchedEffect(Unit, retry_posts) {
                    println("📍 Initial load triggered - retry_posts=$retry_posts")

                    // Reset state ONLY - Don't set isLoading here!
                    constants.Profile_ViewModel.clearPosts()
                    constants.API_Vm.currentPage_Profile_Posts = 0
                    constants.API_Vm.totalPages_Profile_Posts = 1
                    constants.API_Vm.nextPage_Profile_Posts = 1
                    constants.API_Vm.errorMessage_Profile_Posts = null

                    // Now call API (it will set isLoading internally)
                    constants.API_Vm.load_Profile_Posts(
                        user_id = AppPreferences.getUserId(),
                        others_id = constants.Profile_ViewModel.get_Other_User_Id(),
                        status =  constants.active ,
                        page = 1
                    )
                }

                // ✅ PAGINATION - Load more when scrolling
                LaunchedEffect(listState) {
                    snapshotFlow {
                        listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                    }.collect { lastVisibleItemIndex ->

                        val totalItems = listState.layoutInfo.totalItemsCount
                        val loadMoreThreshold = 3
                        val currentPage = constants.API_Vm.currentPage_Profile_Posts
                        val totalPages = constants.API_Vm.totalPages_Profile_Posts
                        val isLoading = constants.API_Vm.isLoading_Profile_Posts
                        val nextPage = constants.API_Vm.nextPage_Profile_Posts

                        println("📊 Scroll → last=$lastVisibleItemIndex total=$totalItems isLoading=$isLoading current=$currentPage/$totalPages")

                        // ✅ Load next page when near bottom
                        if (lastVisibleItemIndex != null &&
                            totalItems > 0 &&
                            lastVisibleItemIndex >= totalItems - loadMoreThreshold &&
                            !isLoading &&
                            currentPage < totalPages &&
                            nextPage > 0
                        ) {
                            println("🔄 Triggering next page load → $nextPage")

                            constants.API_Vm.load_Profile_Posts(
                                user_id = AppPreferences.getUserId(),
                                others_id = constants.Profile_ViewModel.get_Other_User_Id(),
                                status =  constants.active,
                                page = currentPage + 1
                            )
                        }
                    }
                }

            }
            else {
                GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
            }


            DisposableEffect (Unit){

                constants.Profile_ViewModel.addProfile(profile_Content.value?.user_id ?: 0)


                println("OTHER USER CURRENT PRPFILE USERID ___ ${profile_Content.value?.user_id}")

                constants.Profile_ViewModel.add_Current_Profile_UserId(profile_Content.value?.user_id ?: 999)



                println("BLOCK STATUS VALUE GIVEN __ ${constants.Profile_ViewModel.get_Block_Status()}")

                onDispose {
                    println("VALUESS ADDDING DISPOSED")
                }
            }

            val collect = constants.Profile_ViewModel.tapped_Profile_List.collectAsState()


                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(newWhite)
                        , verticalArrangement = Arrangement.spacedBy(16.dp)
                        , horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {

                      /*  // top bar
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                            , contentAlignment = Alignment.Center
                        )
                        {
                            Row (
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                , verticalAlignment = Alignment.CenterVertically
                                , horizontalArrangement = Arrangement.spacedBy(8.dp)
                            )
                            {
                                Backer(
                                    modifier = Modifier
                                    , onBackClick = {
                                        ClickHelper.getInstance().clickOnce {
                                            if (ClickGuard.canClick()) {
                                                if (from_DLP_State.value) {
                                                    println("waesdgrjhkl")
                                                    viewModel.toggleshowBABars(true)
                                                    viewModel.toggleshowTABars(true)


                                                    constants.Profile_ViewModel.put_Other_User_Id(
                                                        0
                                                    )

                                                    constants.Profile_ViewModel.add_Selected_Profile_Id(
                                                        0
                                                    )

                                                    AppPreferences.save_Post_Id(0)

                                                    navController.navigate(
                                                        UserCredentialsScreenFlow.Common_Screen.route
                                                    ) {
                                                        popUpTo(navController.graph.startDestinationId) {
                                                            inclusive = true
                                                        }
                                                    }
                                                    set_FDLP_State(false)

                                                } else {
                                                    // constants.Profile_ViewModel.remove_Selected_User_Name_last()
                                                    //constants.Profile_ViewModel.remove_Tapped_FFs_last()
                                                    //constants.Profile_ViewModel.setProfileSelectedTab(profile_Content.value!!.type)

                                                    constants.Profile_ViewModel.remove_last_BF_Handler()


                                                    navController.navigateUp()
                                                }
                                            }
                                        }
                                    }
                                )

                                Text(
                                    text = userBFHandler?.ff_User_Name ?: "Profile",
                                    color = newBlack,
                                    fontSize = constants.textUnit(24),
                                    fontFamily = constants.fontFamily(0)
                                )
                            }


                            if (!from_DLP_State.value) {
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                        .size(24.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .border(1.dp, newGray, RoundedCornerShape(4.dp))
                                        .background(Color.White)
                                        .noRippleClickable {
                                            expanded.value = true
                                        }, contentAlignment = Alignment.Center
                                )
                                {
                                    Common_DropDown2Options(
                                        expanded = expanded,
                                        mainIcon = R.drawable.more_vert,
                                        content = listOf(
                                            Common_DropDown2Options_DC(
                                                icon = R.drawable.block_profile,
                                                if ((profile_Content.value?.is_blocked
                                                        ?: 0) == 0
                                                ) "Block" else "Unblock"
                                            ),
                                            Common_DropDown2Options_DC(
                                                icon = R.drawable.reelsreport,
                                                "Report"
                                            ),
                                        ),
                                        onClick1 = {
                                            if (profile_Content.value?.is_blocked == 0) {
                                                constants.Profile_ViewModel.put_Block_Status(1)
                                            } else {
                                                constants.Profile_ViewModel.put_Block_Status(2)
                                            }
                                            block_PopUp = true
                                            expanded.value = false
                                        },
                                        onClick2 = {
                                            if (profile_Content.value?.is_report != 1) {
                                                constants.Profile_ViewModel.toggle_ReportSucces_True()
                                                report_BS = true
                                            } else {
                                                GlobalSnackbar.show("Profile Already Reported")
                                            }
                                            expanded.value = false
                                        },
                                        modifier = Modifier
                                            .size(18.dp)
                                    )
                                }
                            }
                        }

                        //top bar end*/

                        var grditype = if (forTab()) 3 else 2

                        LazyColumn (
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .padding(bottom = 16.dp)

                            , state = listState
                        )
                        {
                            if(!isLoadingChange.value) {

                                stickyHeader {
                                    Box(
                                        modifier = Modifier
                                            .background(Color(0xffF7F0DC))
                                            .fillMaxWidth()
                                            //.padding(top = rememberNotchHeightDp().value)
                                            .padding(horizontal = 16.dp)
                                            .padding(top = if (forTab()) 16.dp else (notchPadding.value + 4.dp))
                                        , contentAlignment = Alignment.Center
                                    )
                                    {
//                                        Row (
//                                            modifier = Modifier
//                                                .align(Alignment.CenterStart)
//                                            , verticalAlignment = Alignment.CenterVertically
//                                            , horizontalArrangement = Arrangement.spacedBy(8.dp)
//                                        )
//                                        {
                                           /* Backer(
                                                modifier = Modifier.align(Alignment.CenterStart)
                                                , onBackClick = {
                                                    ClickHelper.getInstance().clickOnce {
                                                        if (ClickGuard.canClick()) {
                                                            constants.API_Vm.isLoading_ProfileS = true
                                                            if (from_DLP_State.value) {
                                                                println("waesdgrjhkl")
                                                                viewModel.toggleshowBABars(true)
                                                                viewModel.toggleshowTABars(true)


                                                                constants.Profile_ViewModel.put_Other_User_Id(
                                                                    0
                                                                )

                                                                constants.Profile_ViewModel.add_Selected_Profile_Id(
                                                                    0
                                                                )

                                                                AppPreferences.save_Post_Id(0)

                                                                navController.navigate(
                                                                    UserCredentialsScreenFlow.Common_Screen.route
                                                                ) {
                                                                    popUpTo(navController.graph.startDestinationId) {
                                                                        inclusive = true
                                                                    }
                                                                }
                                                                set_FDLP_State(false)

                                                            } else {
                                                                // constants.Profile_ViewModel.remove_Selected_User_Name_last()
                                                                //constants.Profile_ViewModel.remove_Tapped_FFs_last()
                                                                //constants.Profile_ViewModel.setProfileSelectedTab(profile_Content.value!!.type)

                                                                constants.Profile_ViewModel.remove_last_BF_Handler()


                                                                navController.navigateUp()
                                                            }
                                                        }
                                                    }
                                                }
                                            )*/

                                        Backer(
                                            modifier = Modifier.align(Alignment.CenterStart),
                                            onBackClick = {
                                                ClickHelper.getInstance().clickOnce {
                                                    if (ClickGuard.canClick()) {
                                                        constants.API_Vm.isLoading_ProfileS = true

                                                        if (from_DLP_State.value) {
                                                            // Deep link case
                                                            viewModel.toggleshowBABars(true)
                                                            viewModel.toggleshowTABars(true)
                                                            constants.Profile_ViewModel.put_Other_User_Id(0)
                                                            constants.Profile_ViewModel.add_Selected_Profile_Id(0)
                                                            AppPreferences.save_Post_Id(0)

                                                            navController.navigate(
                                                                UserCredentialsScreenFlow.Common_Screen.route
                                                            ) {
                                                                popUpTo(navController.graph.startDestinationId) {
                                                                    inclusive = true
                                                                }
                                                            }
                                                            set_FDLP_State(false)
                                                        } else {
                                                            val currentStack = constants.Profile_ViewModel.profile_BF_Handler.value

                                                            if (currentStack.size > 1) {
                                                                val currentHandler = currentStack.last()
                                                                val previousHandler = currentStack[currentStack.size - 2]

                                                                println("🔙 Back from Profile - current: ${currentHandler.screenType}, previous: ${previousHandler.screenType}")

                                                                // Remove current handler (Profile screen)
                                                                constants.Profile_ViewModel.remove_last_BF_Handler()

                                                                when (previousHandler.screenType) {
                                                                    ScreenType.PROFILE -> {
                                                                        // ✅ Going back to another Profile
                                                                        val profileId = if (previousHandler.other_UserId == 0) {
                                                                            previousHandler.current_UsedId
                                                                        } else {
                                                                            previousHandler.other_UserId
                                                                        }

                                                                        constants.Profile_ViewModel.put_Other_User_Id(previousHandler.other_UserId)
                                                                        constants.Profile_ViewModel.add_Selected_Profile_Id(profileId)

                                                                        if (network.value == NetworkStatus.Online) {
                                                                            Get_User_Profile_API_Call() { result ->
                                                                                when (result) {
                                                                                    1 -> {
                                                                                        constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
                                                                                            userId = profileId,
                                                                                            newFollowers = constants.Profile_ViewModel.get_Followers_Count_BGAPIC(),
                                                                                            newFollowing = constants.Profile_ViewModel.get_Following_Count_BGAPIC()
                                                                                        )
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }

                                                                    ScreenType.FF_LIST -> {
                                                                        // ✅ Going back to FF screen
                                                                        // Just restore the state, navigateUp will go to FF screen
                                                                        println("✅ Going back to FF screen")
                                                                    }
                                                                }

                                                                navController.navigateUp()
                                                            } else {
                                                                // Last profile - go to own profile
                                                                constants.Profile_ViewModel.put_Other_User_Id(0)
                                                                constants.Profile_ViewModel.add_Selected_Profile_Id(0)
                                                                viewModel.toggleshowBABars(true)
                                                                viewModel.toggleshowTABars(true)
                                                                navController.navigateUp()
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        )


                                        Text(
                                                text = userBFHandler?.ff_User_Name ?: "Profile",
                                                color = newBlack,
                                                fontSize = constants.textUnit(24),
                                                fontFamily = constants.fontFamily(0)
                                                , modifier = Modifier.align(Alignment.Center)
                                            )
//                                        }

                                        if (!from_DLP_State.value && profile_Content.value?.user_id != AppPreferences.getUserId()) {
                                            Box(
                                                modifier = Modifier
                                                    .align(Alignment.CenterEnd)
                                                    .size(24.dp)
                                                    .clip(RoundedCornerShape(4.dp))
//                                                    .border(1.dp, newGray, RoundedCornerShape(4.dp))
                                                    .background(Color.Transparent)
                                                    .noRippleClickable {
                                                        expanded.value = true
                                                    }
                                                , contentAlignment = Alignment.Center
                                            )
                                            {

                                                Common_DropDown2Options(
                                                    expanded = expanded,
                                                    mainIcon = R.drawable.more_vert,
                                                    content = listOf(
                                                        Common_DropDown2Options_DC(
                                                            icon = R.drawable.block_profile,
                                                            if ((profile_Content.value?.is_blocked
                                                                    ?: 0) == 0
                                                            ) "Block" else "Unblock"
                                                        )
                                                        ,
                                                        Common_DropDown2Options_DC(
                                                            icon = R.drawable.reelsreport,
                                                            "Report"
                                                        ),
                                                    ),
                                                    onClick1 = {
                                                        if (profile_Content.value?.is_blocked == 0) {
                                                            constants.Profile_ViewModel.put_Block_Status(1)
                                                        } else {
                                                            constants.Profile_ViewModel.put_Block_Status(2)
                                                        }
                                                        block_PopUp = true
                                                        expanded.value = false
                                                    },
                                                    onClick2 = {
                                                        if (profile_Content.value?.is_report != 1) {
                                                            constants.Profile_ViewModel.toggle_ReportSucces_True()
                                                            report_BS = true
                                                        } else {
                                                            GlobalSnackbar.show("Profile Already Reported")
                                                        }
                                                        expanded.value = false
                                                    },
                                                    modifier = Modifier
                                                        .size(18.dp)
                                                )
                                            }
                                        }
                                    }
                                }

                                item {
                                    // new top app bar
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight()
                                            .background(
                                                Brush.verticalGradient(
                                                    listOf(
                                                        Color(0xffF7F0DC),
                                                        Color(0xffE6C96A)
                                                    )
                                                )
                                            )
                                        //.padding(top = if (forTab()) 16.dp else notchPadding.value)
                                    )
                                    {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(100.dp)
                                                .background(
                                                    Brush.verticalGradient(
                                                        listOf(
                                                            Color(0xffF7F0DC),
                                                            Color(0xffE6C96A)
                                                        )
                                                    )
                                                )
                                        )
                                        Box(
                                            modifier = Modifier
                                                .padding(top = 20.dp)
                                        ) {

                                            Box(
                                                modifier = Modifier
                                                    .zIndex(2f)
                                                    .align(Alignment.TopCenter)
                                                    .clip(CircleShape)
                                                    .wrapContentSize()
                                                    .background(Color.White)
                                                    .padding(6.dp)
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .align(Alignment.TopCenter)
                                                        .size(if (forTab()) 92.dp else 80.dp)
                                                        .clip(CircleShape)
                                                        .background(newWhite)
                                                )
                                                {
                                                    /// profile pic

                                                    println("PROFILE IMAGE __ ${profile_Content.value?.profile_image ?: ""}")
//                                AsyncImage (
//                                    model = profile_Content.value?.profile_image ?: "",
//                                    "",
//                                    contentScale = ContentScale.FillBounds
//                                )
                                                    SubcomposeAsyncImage(
                                                        model = profile_Content.value?.profile_image ?: "",
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
                                                                //.padding(8.dp)
                                                                , contentAlignment = Alignment.Center
                                                            ) {
                                                                Text(
                                                                    text = profile_Content.value?.username.takeIf { it?.isNotEmpty() == true }
                                                                        ?.take(1)?.uppercase() ?: ""
                                                                )
                                                            }
                                                        } else {
                                                            SubcomposeAsyncImageContent()
                                                        }
                                                    }
                                                }
                                            }

                                            Column(
                                                modifier = Modifier
                                                    .padding(top = if (forTab()) 46.dp else 40.dp)
                                                    .zIndex(0f)
                                                    .fillMaxWidth()
                                                    .wrapContentHeight()
                                                    //clip(RoundedCornerShape(8.dp))
                                                    .background(Color.White)
                                                    .padding(horizontal = 16.dp)
                                                    .padding(top = 40.dp, bottom = 16.dp),
                                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            )
                                            {


                                                constants.spacer(2)

                                                Text(
                                                    text = profile_Content.value?.name ?: "",
                                                    color = new5757,
                                                    fontSize = constants.textUnit(14),
                                                    fontFamily = constants.fontFamily(1)
                                                )

//"This is about me in two hello lines. If the content gets longer, it will end with an ellipsis and a clickable 'see more' to view the full text."

                                                ExpandableText(
                                                    fullText = profile_Content.value?.bio ?: "",
                                                    maxCharacters = 80,
                                                    modifier = Modifier.fillMaxWidth(.9f)
                                                )

                                                Spacer(modifier = Modifier.padding(4.dp))

                                                // follow follwing , post show box
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth(.9f)
                                                        .height(if (forTab()) 64.dp else 56.dp)
                                                        .clip(RoundedCornerShape(8.dp))
                                                        .border(
                                                            1.dp,
                                                            newGray,
                                                            RoundedCornerShape(8.dp)
                                                        ),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.SpaceEvenly
                                                )
                                                {
                                                    repeat(3) { itemIndex ->
                                                        Column(
                                                            modifier = Modifier
                                                                .noRippleClickable(
                                                                    enabled = (profile_Content.value?.is_blocked
                                                                        ?: 0) == 0
                                                                )
                                                                {
                                                                    ClickHelper.getInstance().clickOnce {
                                                                        if (ClickGuard.canClick()) {
                                                                            logD("clicks other profile ffp")
                                                                            when (itemIndex) {
                                                                                1 -> {

                                                                                    constants.Profile_ViewModel.clear_Search_Text_FF()
                                                                                    constants.Profile_ViewModel.disable_Search()
                                                                                    viewModel.toggleshowBABars(
                                                                                        false
                                                                                    )
//
                                                                                    println("PROFILE CONTENT --- ${profile_Content.value?.followers} -- ${profile_Content.value?.following}")

//                                                                                    constants.Profile_ViewModel.updateSelectedTab_BF_Handler(
//                                                                                        userBFHandler?.id
//                                                                                            ?: 0,
//                                                                                        0,
//                                                                                        flw_count = profile_Content.value?.followers
//                                                                                            ?: 0,
//                                                                                        fing_Count = profile_Content.value?.following
//                                                                                            ?: 0
//                                                                                    )

                                                                                    // ✅ Add handler with FF_LIST type
                                                                                    constants.Profile_ViewModel.add_BF_Handler(
                                                                                        Profile_Handle_Back(
                                                                                            current_UsedId = AppPreferences.getUserId(),
                                                                                            other_UserId = profile_Content.value?.user_id ?: 0,
                                                                                            selected_Tab = 0, // Followers
                                                                                            ff_User_Name = profile_Content.value?.username ?: "Profile",
                                                                                            ff_Fw_Count = profile_Content.value?.followers ?: 0,
                                                                                            ff_Fg_Count = profile_Content.value?.following ?: 0,
                                                                                            screenType = ScreenType.FF_LIST // ✅ Set to FF_LIST
                                                                                        )
                                                                                    )

                                                                                    println("TESTING 111 --${userBFHandler}-${profile_Content.value?.following ?: 0}- ${profile_Content.value?.followers ?: 0}")

                                                                                    constants.API_Vm.totalPages_FF =
                                                                                        1

                                                                                    constants.Profile_ViewModel.clear_FF_Lists()

                                                                                    navController.navigate(
                                                                                        ProfileScreenFlow.Profile_FF_Structure.route
                                                                                    )
                                                                                }

                                                                                2 -> {
                                                                                    constants.Profile_ViewModel.clear_Search_Text_FF()
                                                                                    constants.Profile_ViewModel.disable_Search()

                                                                                    viewModel.toggleshowBABars(
                                                                                        false
                                                                                    )
//
//                                                                                    constants.Profile_ViewModel.updateSelectedTab_BF_Handler(
//                                                                                        userBFHandler?.id
//                                                                                            ?: 0,
//                                                                                        1,
//                                                                                        flw_count = profile_Content.value?.followers
//                                                                                            ?: 0,
//                                                                                        fing_Count = profile_Content.value?.following
//                                                                                            ?: 0
//                                                                                    )

                                                                                    // ✅ Add handler with FF_LIST type
                                                                                    constants.Profile_ViewModel.add_BF_Handler(
                                                                                        Profile_Handle_Back(
                                                                                            current_UsedId = AppPreferences.getUserId(),
                                                                                            other_UserId = profile_Content.value?.user_id ?: 0,
                                                                                            selected_Tab = 1, // Following
                                                                                            ff_User_Name = profile_Content.value?.username ?: "Profile",
                                                                                            ff_Fw_Count = profile_Content.value?.followers ?: 0,
                                                                                            ff_Fg_Count = profile_Content.value?.following ?: 0,
                                                                                            screenType = ScreenType.FF_LIST // ✅ Set to FF_LIST
                                                                                        )
                                                                                    )
                                                                                    println("TESTING 11 2222 --${userBFHandler}-${profile_Content.value?.following ?: 0}- ${profile_Content.value?.followers ?: 0}")
                                                                                    constants.API_Vm.totalPages_FF =
                                                                                        1

                                                                                    constants.Profile_ViewModel.clear_FF_Lists()

                                                                                    navController.navigate(
                                                                                        ProfileScreenFlow.Profile_FF_Structure.route
                                                                                    )
                                                                                }

                                                                                else -> {}
                                                                            }
                                                                        }
                                                                    }
                                                                },
                                                            verticalArrangement = Arrangement.Center,
                                                            horizontalAlignment = Alignment.CenterHorizontally
                                                        ) {
                                                            Text(
                                                                text = when (itemIndex) {
                                                                    0 -> "${profile_Content.value?.posts ?: 0}"
                                                                    1 -> "${profile_Content.value?.followers ?: 0}"
                                                                    2 -> "${profile_Content.value?.following ?: 0}"
                                                                    else -> "48"
                                                                },
                                                                color = newBlack,
                                                                fontSize = constants.textUnit(14),
                                                                fontFamily = constants.fontFamily(0)
                                                            )

                                                            Text(
                                                                text = when (itemIndex) {
                                                                    0 -> "Properties"
                                                                    1 -> "Followers"
                                                                    2 -> "Following"
                                                                    else -> "Extra"
                                                                },
                                                                color = newBlack,
                                                                fontSize = constants.textUnit(12),
                                                                fontFamily = constants.fontFamily(3)
                                                            )
                                                        }

                                                        if (itemIndex != 2) {
                                                            VerticalDivider(
                                                                modifier = Modifier.padding(
                                                                    vertical = 8.dp
                                                                )
                                                            )
                                                        }
                                                    }
                                                }

                                                //Spacer(modifier = Modifier.padding(4.dp))

                                                constants.spacer(2)
                                                constants.spacer(2)


                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth(.9f),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                )
                                                {
                                                    if ((profile_Content.value?.is_blocked
                                                            ?: 0) == 1
                                                    )
                                                    {
                                                        Box(
                                                            modifier = Modifier
                                                                .weight(8f)
                                                                .height(if (forTab()) 44.dp else 34.dp)
                                                                .clip(RoundedCornerShape(4.dp))
                                                                .border(
                                                                    1.dp,
                                                                    Brush.linearGradient(newPurpleGradientBorder),
                                                                    RoundedCornerShape(4.dp)
                                                                )
                                                                .noRippleClickable {

                                                                    if (network.value == NetworkStatus.Online) {
                                                                        constants.Profile_ViewModel.put_Block_Status(
                                                                            2
                                                                        )
                                                                        block_PopUp = true

                                                                        /*constants.API_Vm.put_Block_User(
                                                                        user_id = AppPreferences.getUserId(),
                                                                        blocker_id = profile_Content.value?.user_id
                                                                            ?: 0,
                                                                        //profile_Content.value?.user_id ?: 0,
                                                                        status = 2
                                                                        //if (profile_Content.value?.is_blocked == 0) "1" else "0"
                                                                    )
                                                                    {
                                                                        apiResultHandling ->
                                                                        when (apiResultHandling) {
                                                                            is API_Result_Handling.Error -> {
                                                                                //errror
                                                                                //constants.Profile_ViewModel.change_Update_profile(false)
                                                                            }

                                                                            is API_Result_Handling.NoData -> {
                                                                                // no data
                                                                            }

                                                                            is API_Result_Handling.Loading -> {
                                                                                //loading
                                                                                // constants.Profile_ViewModel.change_Update_profile(true)
                                                                            }

                                                                            is API_Result_Handling.Success -> {
                                                                                constants.Profile_ViewModel.updateBlockedStatus_Selected_Profile(
                                                                                    0
                                                                                )
                                                                                //block_PopUp = false
                                                                                //constants.Profile_ViewModel.enable_Edit_Profile()
                                                                                //constants.Profile_ViewModel.change_Update_profile(false)
                                                                                //success
                                                                            }
                                                                        }
                                                                    }*/
                                                                        GlobalSnackbar.show(
                                                                            constants.activity.getString(
                                                                                R.string.no_Internet
                                                                            )
                                                                        )
                                                                    }
                                                                }
                                                                .background(Brush.verticalGradient(newPurpleGradient)),
                                                            contentAlignment = Alignment.Center
                                                        )
                                                        {
                                                            Text(
                                                                text = "Unblock",
                                                                color = Color.White,
                                                                fontSize = constants.textUnit(14),
                                                                fontFamily = constants.fontFamily(0)
                                                            )
                                                        }
                                                    }
                                                    else {

                                                        if (profile_Content.value?.user_id == AppPreferences.getUserId()) {

                                                        } else {
                                                            Box(
                                                                modifier = Modifier
                                                                    .weight(4f)
                                                                    .height(if (forTab()) 44.dp else 34.dp)
                                                                    .clip(RoundedCornerShape(4.dp))
                                                                    .border(
                                                                        1.dp,
                                                                        newBlack,
                                                                        RoundedCornerShape(4.dp)
                                                                    )
                                                                    .noRippleClickable {
                                                                        openDialer(
                                                                            context,
                                                                            profile_Content.value?.phone_num
                                                                                ?: ""
                                                                        )
                                                                    }
                                                                    .background(Color.White),
                                                                contentAlignment = Alignment.Center
                                                            )
                                                            {
                                                                Row(
                                                                    modifier = Modifier
                                                                    ,
                                                                    horizontalArrangement = Arrangement.spacedBy(
                                                                        4.dp
                                                                    ),
                                                                    verticalAlignment = Alignment.CenterVertically
                                                                )
                                                                {
                                                                    AsyncImage(
                                                                        model = R.drawable.rentocallenquiryicon,
                                                                        "",
                                                                        colorFilter = ColorFilter.tint(
                                                                            newBlack
                                                                        ),
                                                                        modifier = Modifier
                                                                            .size(12.dp)
                                                                    )

                                                                    Text(
                                                                        text = "Call",
                                                                        color = newBlack,
                                                                        fontSize = constants.textUnit(
                                                                            14
                                                                        ),
                                                                        fontFamily = constants.fontFamily(
                                                                            0
                                                                        )
                                                                    )
                                                                }
                                                            }

                                                            Spacer(modifier = Modifier.weight(.5f))
                                                            println("USER ID IM FOLLOWED __ ${profile_Content.value?.im_followed}")
                                                            var type = when {
                                                                profile_Content.value?.im_followed == "0" && profile_Content.value?.is_followed == "0" -> 0
                                                                profile_Content.value?.im_followed == "0" && profile_Content.value?.is_followed == "1" -> 1
                                                                else -> 2
                                                                //profile_Content.value?.im_followed == "1" && profile_Content.value?.is_followed == "0" -> 2
                                                            }

                                                            if (profile_Content.value?.im_followed == "1" && profile_Content.value?.is_followed == "1"){
                                                                0
                                                            }

                                                            Box(
                                                                modifier = Modifier
                                                                    .weight(4f)
                                                                    .height(if (forTab()) 44.dp else 34.dp)
                                                                    .noRippleClickable {
                                                                        constants.Profile_ViewModel.put_Following_Id(
                                                                            profile_Content.value?.user_id
                                                                                ?: 0
                                                                        )
                                                                        constants.Profile_ViewModel.add_Unflw_Flw_Content_Pup(
                                                                            id = profile_Content.value?.user_id
                                                                                ?: 0,
                                                                            username = profile_Content.value?.username
                                                                                ?: "username",
                                                                            profilePic = profile_Content.value?.profile_image
                                                                                ?: "",
                                                                        )
                                                                        if ((profile_Content.value?.im_followed
                                                                                ?: "") == "0"
                                                                        ) {
                                                                            constants.Profile_ViewModel.put_follow_unfollow_Status(
                                                                                1
                                                                            )

                                                                            if (network.value == NetworkStatus.Online) {
                                                                                follow_Unfollow_Delete_API_Call() { result ->
                                                                                    when (result) {
                                                                                        0 -> {

                                                                                            Get_User_Profile_API_Call() { result2 ->
                                                                                                when (result2) {
                                                                                                    0 -> {


                                                                                                    }

                                                                                                    1 -> {
                                                                                                        // toast("Try Again later")
//                                                                                                        constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
//                                                                                                            userId = profile_Content.value?.user_id
//                                                                                                                ?: 0,
//                                                                                                            newFollowers = constants.Profile_ViewModel.get_Followers_Count_BGAPIC(),
//                                                                                                            newFollowing = constants.Profile_ViewModel.get_Following_Count_BGAPIC()
//                                                                                                        )

                                                                                                        constants.Profile_ViewModel.update_Previous_BF_Handler_FF_Counts(
                                                                                                            constants.Profile_ViewModel.get_Followers_Count_BGAPIC(),
                                                                                                            newFollowing = constants.Profile_ViewModel.get_Following_Count_BGAPIC() - 1
                                                                                                        )

                                                                                                    }

                                                                                                    2 -> {
                                                                                                        GlobalSnackbar.show(
                                                                                                            "Something went wrong"
                                                                                                        )
                                                                                                    }
                                                                                                }
                                                                                            }

                                                                                            println(
                                                                                                "Success"
                                                                                            )

                                                                                            if (!is_Search_State_FF.value) {
                                                                                                constants.Profile_ViewModel.updateImFollowedByUserId_FF(
                                                                                                    profile_Content.value?.user_id
                                                                                                        ?: 0
                                                                                                )
                                                                                            } else {
                                                                                                constants.Profile_ViewModel.updateImFollowedByUserId_Search_FF(
                                                                                                    profile_Content.value?.user_id
                                                                                                        ?: 0
                                                                                                )
                                                                                            }
                                                                                            // constants.Profile_ViewModel.setunfollowClicker()
                                                                                        }

                                                                                        1 -> {
                                                                                            toast("Try Again later")
                                                                                        }

                                                                                        2 -> {

                                                                                        }
                                                                                    }
                                                                                }
                                                                            } else {
                                                                                GlobalSnackbar.show(
                                                                                    constants.activity.getString(
                                                                                        R.string.no_Internet
                                                                                    )
                                                                                )
                                                                            }
                                                                        } else {
                                                                            constants.Profile_ViewModel.put_follow_unfollow_Status(
                                                                                2
                                                                            )
                                                                            constants.Profile_ViewModel.setunfollowClicker()

                                                                        }
//                                                                    constants.Profile_ViewModel.put_follow_unfollow_Status(
//                                                                        1
//                                                                    )


                                                                    }
                                                                    .clip(RoundedCornerShape(4.dp))
                                                                    .then(
                                                                        if ((profile_Content.value?.im_followed ?: "") == "0")
                                                                            Modifier.background(Brush.verticalGradient(newPurpleGradient))
                                                                        else
                                                                            Modifier.background( Color(
                                                                                0xffF4F4F4
                                                                            ))
                                                                    )
//                                                                    .background(
//                                                                        if ((profile_Content.value?.im_followed
//                                                                                ?: "") == "0"
//                                                                        ) newBlue
//                                                                        else Color(
//                                                                            0xffF4F4F4
//                                                                        )
//                                                                    )
                                                                , contentAlignment = Alignment.Center
                                                            )
                                                            {
                                                                Text(
                                                                    text = when(type) {
                                                                        0 -> "Follow"
                                                                        1 -> "Follow Back"
                                                                        else -> "Following"
                                                                    }
//                                                                        if ((profile_Content.value?.im_followed
//                                                                            ?: "") == "0"
//                                                                    ) "Follow" else "Following",
//
                                                                    ,color = if ((profile_Content.value?.im_followed
                                                                            ?: "") == "0"
                                                                    ) Color.White else newBlack,
                                                                    fontSize = constants.textUnit(14),
                                                                    fontFamily = constants.fontFamily(
                                                                        0
                                                                    )
                                                                )
                                                            }
                                                        }
                                                    }
                                                }

//                                                constants.spacer(2)
                                                constants.spacer(4)
                                               // Spacer(modifier = Modifier.padding(8.dp))
                                            }
                                        }
                                    }
                                    // top bar end
                                }
                            }
                            else{
                                item {
                                    LoadingShimmerEffect(1)
                                }
                            }

                            when {
                                isLoading && posts.value.isEmpty() -> {
                                    item {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(if (forTab()) 600.dp else 400.dp)
                                            ,
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            LottiAnimation(2)
                                        }
                                    }
                                }

                                !errorMessage.isNullOrEmpty() -> {
                                    item {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                            , contentAlignment = Alignment.Center
                                        ) {

                                            API_Fail_UI(onReTryClick = {
                                                retry_posts = retry_posts + 213435
                                            })
                                        }
                                    }
                                }

                                !isLoading && posts.value.isEmpty() -> {
                                    // no data
                                    item {
                                        if ((profile_Content.value?.is_blocked ?: 0) == 1){
//                                            item {
                                                Column(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(400.dp)
                                                        .background(newLightGray)
                                                        .padding(horizontal = 16.dp)
                                                    , verticalArrangement = Arrangement.Center
                                                    , horizontalAlignment = Alignment.CenterHorizontally
                                                )
                                                {
                                                    AsyncImage(
                                                        model = R.drawable.rentootherprofileblock,
                                                        "",
                                                        modifier = Modifier
                                                            .size(70.dp)
                                                    )

//                                                    Spacer(modifier = Modifier.height(16.dp))

//                                                    Text(
//                                                        text =  "You blocked this user",
//                                                        color = newBlack,
//                                                        fontSize = constants.textUnit(16),
//                                                        fontFamily = constants.fontFamily(1)
//                                                    )

                                                    Spacer(modifier = Modifier.height(16.dp))

                                                    Text(
                                                        text =  "Unblock to view their property listings",
                                                        color = newBlack,
                                                        textAlign = TextAlign.Center,
                                                        fontSize = constants.textUnit(12),
                                                        fontFamily = constants.fontFamily(2)
                                                    )


                                                }
//                                            }
                                        }
                                        else {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(if (forTab()) 600.dp else 400.dp),
                                                verticalArrangement = Arrangement.Center,
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Image(
                                                    painterResource(R.drawable.emptysavedprosrento),
                                                    ""
                                                )
                                                Text(
                                                    "No Properties Listed!",
                                                    color = newBlack,
                                                    fontSize = constants.textUnit(14),
                                                    fontFamily = constants.fontFamily(2)
                                                )
                                                Text(
                                                    "This profile hasn’t added any land listings. Check back later .",
                                                    textAlign = TextAlign.Center,
                                                    color = newBlack,
                                                    fontSize = constants.textUnit(14),
                                                    fontFamily = constants.fontFamily(2),
                                                    modifier = Modifier.padding(horizontal = 24.dp)
                                                )
                                            }
                                        }
                                    }
                                }

                                posts.value.isNotEmpty() -> {
                                    if ((profile_Content.value?.is_blocked ?: 0) == 1){
                                        item {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(400.dp)
                                                    .background(newLightGray)
                                                    .padding(horizontal = 16.dp)
                                                , verticalArrangement = Arrangement.Center
                                                , horizontalAlignment = Alignment.CenterHorizontally
                                            )
                                            {
                                                AsyncImage(
                                                    model = R.drawable.blocked_user,
                                                    "",
                                                    modifier = Modifier
                                                        .size(70.dp)
                                                )

                                                Spacer(modifier = Modifier.height(16.dp))

                                                Text(
                                                    text =  "You blocked this user",
                                                    color = newBlack,
                                                    fontSize = constants.textUnit(16),
                                                    fontFamily = constants.fontFamily(1)
                                                )

                                                Spacer(modifier = Modifier.height(16.dp))

                                                Text(
                                                    text =  "Unblock to see their property posts and to contact them.",
                                                    color = newBlack,
                                                    textAlign = TextAlign.Center,
                                                    fontSize = constants.textUnit(12),
                                                    fontFamily = constants.fontFamily(2)
                                                )


                                            }
                                        }
                                    }
                                    else {
//                                        if (posts.value.isEmpty()){
//                                            item {
//                                                Column (
//                                                    modifier = Modifier
//                                                        .fillMaxWidth()
//                                                        .height(400.dp)
//                                                    , verticalArrangement = Arrangement.Center
//                                                    , horizontalAlignment = Alignment.CenterHorizontally
//                                                ){
//                                                    Image(painter = painterResource(R.drawable.empty_posts), "")
//                                                    Text("No land listed!")
//                                                    Text("Create your first land post today.")
//                                                }
//                                            }
//                                        }else {


                                            println("POSTS DATA OTHER PROFILE -- ${posts.value.map { it.user_post_id }}")
                                            customGridItems(
                                                count = posts.value.size,
                                                nColumns = grditype
                                                //, horizontalArrangement = Arrangement.spacedBy(16.dp)
                                            )
                                            { itemIndex ->

                                                val item = posts.value[itemIndex]

                                                var thumbnailData by remember {
                                                    mutableStateOf(ThumbnailData(R.drawable.emptypostsrento, modifier = Modifier.size(70.dp)))
                                                }

                                                LaunchedEffect(item.post_property.video.firstOrNull()?.url) {
                                                    thumbnailData =
                                                        when {
                                                            item.thumbnail.isNotEmpty() -> {
                                                                ThumbnailData(
                                                                    model = item.post_property.thumbnail ?: "",
                                                                    modifier = Modifier.fillMaxSize()
                                                                )
                                                            }

                                                            item.post_property.images.isEmpty() && item.post_property.video.isNotEmpty() -> {
                                                                ThumbnailData(
                                                                    model = getVideoThumbnailFP(context, item.post_property.video.firstOrNull()?.url ?: "")
                                                                        ?: R.drawable.emptypostsrento,
                                                                    modifier = Modifier.fillMaxSize()
                                                                )
                                                            }

                                                            item.post_property.images.isEmpty() && item.post_property.video.isEmpty() -> {
//                                                                   ThumbnailData(
//                                                                       model = R.drawable.photorequestimage,
//                                                                       modifier = Modifier.size(80.dp)
//                                                                   )
                                                                ThumbnailData(
                                                                    model = R.drawable.emptypostsrento,
                                                                    modifier = Modifier.size(100.dp)
                                                                )
                                                            }

                                                            else -> {
                                                                ThumbnailData(
                                                                    model = R.drawable.emptypostsrento,
                                                                    modifier = Modifier.size(100.dp)
                                                                )
                                                            }
                                                        }
                                                }

                                                logger("CREATE" , "$thumbnailData ** ${item.post_property.video.firstOrNull()?.url ?: ""}")
                                                Box(
                                                    modifier = Modifier
                                                        .padding(vertical = 8.dp, horizontal = 8.dp)
//                                                        .padding(horizontal = 16.dp)
//                                                        .padding(top = 16.dp)
                                                        .height(216.dp)
                                                        .width(162.dp)
                                                        .clip(RoundedCornerShape(4.dp))
                                                        .noRippleClickable {
                                                            ClickHelper.getInstance().clickOnce {
                                                                constants.Reels_ViewModel.clear_view_pro_Details()
                                                                logD("clicks other profile posts")
                                                                AppPreferences.save_Post_Id(item.user_post_id)
                                                                constants.Search_ViewModel.select_RS_Reels_Flow(
                                                                    itemIndex
                                                                )
                                                                constants.Reels_ViewModel.setReelsContent(
                                                                    posts.value.map { it.toReelsData() }  // map each element to Get_Reels_Data
                                                                )
                                                                constants.PostProperty_ViewModel.set_Post_Form_Flow(
                                                                    -1
                                                                )

                                                                constants.PostProperty_ViewModel.setViewDetailsFlow(
                                                                    ViewDetailsFlow.OTHERS
                                                                )

                                                                constants.Common_H_ViewModel.dismiss_Cmt_btm_Sheet()
//                                        constants.Reels_ViewModel.add_View_Property_Details(posts.value.toReelsData())
//                                        val data = constants.Search_ViewModel.get_Search_Results()
//                                        println("SIZE OF DATA MAPPED TO SEARCH REELS FLOW DATA HOLDER -- ${data.size}  *** ${data.map { it.video }}")
                                                                if (constants.Reels_ViewModel.get_Reels_Data()) {
                                                                    navController.navigate(
                                                                        ProfileScreenFlow.ReelsView_Search_Flow.route + "/$itemIndex"
                                                                    )
                                                                }
                                                            }
                                                        }
                                                        .background(newLightBlue)
                                                    , contentAlignment = Alignment.Center
                                                ) {

                                                    SubcomposeAsyncImage(
                                                        model = thumbnailData,
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                        , contentDescription = ""
                                                        , contentScale = ContentScale.FillBounds
                                                    )
                                                    {
                                                        val state = painter.state
                                                        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                                                            Box(
                                                                modifier = Modifier
                                                                    .fillMaxSize()
                                                                    .background(Color(0xffE8E8E8))
                                                                , contentAlignment = Alignment.Center
                                                            ) {
                                                                Image(painterResource(R.drawable.emptypostsrento) , "",
                                                                    modifier = Modifier.size(100.dp))
                                                            }
                                                        } else {
                                                            SubcomposeAsyncImageContent()
                                                        }
                                                    }
                                                }
                                            }
                                    }
                                }
                            }
                        }



                    }
        }

    }


    val blockStatus = constants.Profile_ViewModel.block_Status.collectAsState()

    // block popup
    Common_Popup(
        block_PopUp,
        modifier = Modifier
            .background(Color(0xffF7F0DC))
        , image = "",
        userName = "",
        icon = 0 /// or R.drawable
    )
    {
        Column (
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 24.dp)
            , verticalArrangement = Arrangement.spacedBy(12.dp)
            , horizontalAlignment = Alignment.CenterHorizontally
        )
        {



            constants.spacer(2)

            Image(painter = painterResource(
                if (profile_Content.value?.is_blocked == 0)
                R.drawable.rentoprofileblock
                else
                R.drawable.profilepopicon
            ) , "",
                modifier = Modifier.size(64.dp))


            constants.spacer(2)

//            Text(
//                text = "${if (profile_Content.value?.is_blocked == 0)"Block" else "Unblock"} ${getter?.ff_User_Name ?: "Other_User"} ?",
//                color = newBlack,
//                fontSize = constants.textUnit(16),
//                fontFamily = constants.fontFamily(0)
//            )

            //constants.spacer(2)

            Text(
                text = "Are you sure you want to ${if (profile_Content.value?.is_blocked == 0)  "Block" else "Unblock" }  this profile?",
                color = newBlack,
                fontSize = constants.textUnit(18),
                fontFamily = constants.fontFamily(1)
                , textAlign = TextAlign.Center
//               , lineHeight = 24.sp
                , modifier = Modifier.padding(horizontal = if (forTab()) 46.dp else 36.dp)
            )

//            Spacer(modifier = Modifier.padding(2.dp))
            constants.spacer(4)

            Row (
                modifier = Modifier
                , horizontalArrangement = Arrangement.Center
                , verticalAlignment = Alignment.CenterVertically
            )
            {
                Box(
                    modifier = Modifier
                        .weight(4f)
                        .height(if (forTab()) 56.dp else 46.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xffE8E8E8))
                        .noRippleClickable {
                            block_PopUp = false
                        }
                    , contentAlignment = Alignment.Center
                ){
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
                        .height(if (forTab()) 56.dp else 46.dp)
                        .noRippleClickable {
                            ClickHelper.getInstance().clickOnce {
                                if (ClickGuard.canClick()) {
                                    if (network.value == NetworkStatus.Online) {
                                        println("BLOCKED API CALL HIT STATUS __ ${constants.Profile_ViewModel.get_Block_Status()} --- ${blockStatus.value}")
                                        constants.API_Vm.put_Block_User(
                                            user_id = AppPreferences.getUserId(),
                                            blocker_id = profile_Content.value?.user_id ?: 0,
                                            //profile_Content.value?.user_id ?: 0,
                                            status = blockStatus.value
                                            //if (profile_Content.value?.is_blocked == 0) "1" else "0"
                                        )
                                        { apiResultHandling ->
                                            when (apiResultHandling) {
                                                is API_Result_Handling.Error -> {
                                                    //errror
                                                    //constants.Profile_ViewModel.change_Update_profile(false)
                                                }

                                                is API_Result_Handling.Deactivated -> {
                                                    // resultCallback(5)
                                                }

                                                is API_Result_Handling.NoData -> {
                                                    // no data
                                                }

                                                is API_Result_Handling.Loading -> {
                                                    //loading
                                                    // constants.Profile_ViewModel.change_Update_profile(true)
                                                }

                                                is API_Result_Handling.Success -> {

                                                    Get_User_Profile_API_Call() { result2 ->
                                                        when (result2) {
                                                            0 -> {}
                                                            1 -> {
                                                                // ✅ FIX: Update counts for the OTHER user's profile
                                                                constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
                                                                    userId = profile_Content.value?.user_id
                                                                        ?: 0,
                                                                    newFollowers = constants.Profile_ViewModel.get_Followers_Count_BGAPIC(),
                                                                    newFollowing = constants.Profile_ViewModel.get_Following_Count_BGAPIC()
                                                                )

                                                                // ✅ FIX: Also update YOUR OWN profile's following count
                                                                // Since you unfollowed someone, YOUR following count decreases
                                                                val myUserId =
                                                                    AppPreferences.getUserId()
                                                                val myCurrentHandler =
                                                                    constants.Profile_ViewModel.profile_BF_Handler.value
                                                                        .find { it.current_UsedId == myUserId && it.other_UserId == 0 }

                                                                if (myCurrentHandler != null) {
                                                                    constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
                                                                        userId = myUserId,
                                                                        newFollowers = myCurrentHandler.ff_Fw_Count,
                                                                        newFollowing = (myCurrentHandler.ff_Fg_Count - 1).coerceAtLeast(
                                                                            0
                                                                        )
                                                                    )
                                                                }

                                                                if (profile_Content.value?.is_blocked == 0) {
                                                                    constants.Profile_ViewModel.updateBlockedStatus_Selected_Profile(
                                                                        1
                                                                    )
                                                                    constants.Profile_ViewModel.update_OnUnBlocked_Follow()
                                                                } else {
                                                                    constants.Profile_ViewModel.updateBlockedStatus_Selected_Profile(
                                                                        0
                                                                    )
                                                                    constants.Profile_ViewModel.update_OnUnBlocked_Follow()
                                                                }
                                                                block_PopUp = false

                                                            }

                                                            2 -> {
                                                                GlobalSnackbar.show(
                                                                    "Something went wrong"
                                                                )
                                                            }
                                                        }
                                                    }

                                                    //constants.Profile_ViewModel.enable_Edit_Profile()
                                                    //constants.Profile_ViewModel.change_Update_profile(false)
                                                    //success
                                                }
                                            }
                                        }
                                    } else {
                                        GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
                                    }
                                }
                            }
                        }
                        .clip(RoundedCornerShape(8.dp))
                        .background( if (profile_Content.value?.is_blocked == 0)
                            Brush.verticalGradient(newRedGradienBg)
                            else
                            Brush.verticalGradient(newPurpleGradient)
                        )
                    , contentAlignment = Alignment.Center
                ){
                    Text(
                        text = if (profile_Content.value?.is_blocked == 0)"Block" else "Unblock",
                        color = Color.White,
                        fontSize = constants.textUnit(16),
                        fontFamily = constants.fontFamily(0)
                    )
                }
            }

            Spacer(modifier = Modifier.padding(8.dp))
            constants.spacer(2)
        }
    }

    val content = constants.Profile_ViewModel.get_Unflw_Flw_Content_Pup()

    Common_Popup(
        visible = unfollow_PUP.value,
        modifier = Modifier
            .background(Color(0xffF7F0DC))
        , content = {


            Column(
                modifier = Modifier
                    //.fillMaxHeight()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {

                constants.spacer(2)

                //constants.spacer(2)

//                Text(
//                    text = "Unfollow ${content.user_Name} ?",
//                    color = newBlack,
//                    fontSize = constants.textUnit(16),
//                    fontFamily = constants.fontFamily(0)
//                )
                Image(painter = painterResource(R.drawable.profilepopicon) , "",
                    modifier = Modifier.size(56.dp))

                constants.spacer(2)

                Text(
                    text = "Are you sure you want to Unfollow this profile?",
                    color = newBlack,
                    fontSize = constants.textUnit(18),
                    fontFamily = constants.fontFamily(1),
                    textAlign = TextAlign.Center
//                   , lineHeight = 24.sp
                    , modifier = Modifier.padding(horizontal = if (forTab()) 46.dp else 36.dp)
                )

//                Spacer(modifier = Modifier.padding(2.dp))
                constants.spacer(4)

                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Box(
                        modifier = Modifier
                            .weight(4f)
                            .height(if (forTab()) 56.dp else 46.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xffE8E8E8))
                            .noRippleClickable {
//                                constants.Profile_ViewModel.setunfollowClicker()
                                constants.Profile_ViewModel.closeUnFollowClick()
                            }, contentAlignment = Alignment.Center
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
                            .height(if (forTab()) 56.dp else 46.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Brush.verticalGradient(newPurpleGradient))
                            .border(
                                1.dp,
                                Brush.linearGradient(newPurpleGradientBorder),
                                RoundedCornerShape(8.dp)
                            )
                            .noRippleClickable {
                                ClickHelper.getInstance().clickOnce {
                                    if (ClickGuard.canClick()) {

                                        if ((profile_Content.value?.im_followed
                                                ?: "0") == "0"
                                        ) {
                                            constants.Profile_ViewModel.put_follow_unfollow_Status(
                                                1
                                            )
                                        } else {
                                            constants.Profile_ViewModel.put_follow_unfollow_Status(
                                                2
                                            )
                                        }

                                        constants.Profile_ViewModel.put_Following_Id(
                                            profile_Content.value?.user_id ?: 0
                                        )
                                        constants.Profile_ViewModel.put_Other_User_Id(
                                            profile_Content.value?.user_id ?: 0
                                        )


                                        constants.Profile_ViewModel.add_Unflw_Flw_Content_Pup(
                                            id = profile_Content.value?.user_id
                                                ?: 0,
                                            username = profile_Content.value?.username
                                                ?: "",
                                            profilePic = profile_Content.value?.profile_image
                                                ?: "",
                                        )

                                        if (network.value == NetworkStatus.Online) {
                                            follow_Unfollow_Delete_API_Call() { result ->
                                                when (result) {
                                                    0 -> {

                                                        if (network.value == NetworkStatus.Online) {
                                                            follow_Unfollow_Delete_API_Call() { result ->
                                                                when (result) {
                                                                    0 -> {
                                                                        Get_User_Profile_API_Call() { result2 ->
                                                                            when (result2) {
                                                                                0 -> {}
                                                                                1 -> {
                                                                                    // ✅ FIX: Update counts for the OTHER user's profile
//                                                                                    constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
//                                                                                        userId = profile_Content.value?.user_id
//                                                                                            ?: 0,
//                                                                                        newFollowers = constants.Profile_ViewModel.get_Followers_Count_BGAPIC(),
//                                                                                        newFollowing = constants.Profile_ViewModel.get_Following_Count_BGAPIC()
//                                                                                    )
                                                                                    // ✅ FIX: Update counts for the OTHER user's profile
//                                                                                    constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
//                                                                                        userId = profile_Content.value?.user_id ?: 0,  // The profile you're viewing
//                                                                                        newFollowers = constants.Profile_ViewModel.get_Followers_Count_BGAPIC(),
//                                                                                        newFollowing = constants.Profile_ViewModel.get_Following_Count_BGAPIC()
//                                                                                    )

                                                                                    // ✅ FIX: Also update YOUR OWN profile's following count
                                                                                    // Since you unfollowed someone, YOUR following count decreases
//                                                                                    val myUserId =
//                                                                                        AppPreferences.getUserId()
//                                                                                    val myCurrentHandler =
//                                                                                        constants.Profile_ViewModel.profile_BF_Handler.value
//                                                                                            .find { it.current_UsedId == myUserId && it.other_UserId == 0 }
//
//                                                                                    if (myCurrentHandler != null) {
//                                                                                        constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
//                                                                                            userId = myUserId,
//                                                                                            newFollowers = myCurrentHandler.ff_Fw_Count,
//                                                                                            newFollowing = (myCurrentHandler.ff_Fg_Count - 1).coerceAtLeast(
//                                                                                                0
//                                                                                            )
//                                                                                        )
//                                                                                    }

//                                                                                    constants.Profile_ViewModel.setunfollowClicker()
                                                                                    constants.Profile_ViewModel.closeUnFollowClick()

                                                                                }

                                                                                2 -> {
                                                                                    GlobalSnackbar.show(
                                                                                        "Something went wrong"
                                                                                    )
                                                                                }
                                                                            }
                                                                        }

                                                                        println("Success - Unfollow Status: ${constants.Profile_ViewModel.get_follow_unfollow_Status()}")

                                                                        // ✅ ADD THIS: Update the FF map when unfollowing
                                                                        val currentUserId =
                                                                            AppPreferences.getUserId()
                                                                        val targetUserId =
                                                                            profile_Content.value?.user_id
                                                                                ?: 0

                                                                        if (constants.Profile_ViewModel.get_follow_unfollow_Status() == 2) {
                                                                            // Unfollowing - remove from Following tab (tab = 1)
                                                                            constants.Profile_ViewModel.deleteUserById_Profile_FF_Map(
                                                                                mapUserId = currentUserId,
                                                                                mapTab = 1, // Following tab
                                                                                targetUserId = targetUserId
                                                                            )

                                                                            // Also update search map if search is active
                                                                            if (is_Search_State_FF.value) {
                                                                                constants.Profile_ViewModel.deleteUserById_Profile_Search_FF_NEW(
                                                                                    targetUserId = targetUserId
                                                                                )
                                                                            }
                                                                        }
                                                                    }

                                                                    1 -> {
                                                                        toast("Try Again later")
                                                                    }

                                                                    2 -> {}
                                                                }
                                                            }
                                                        }

                                                        println("Success")

                                                        if (!is_Search_State_FF.value) {
                                                            constants.Profile_ViewModel.updateImFollowedByUserId_FF(
                                                                profile_Content.value?.user_id
                                                                    ?: 0
                                                            )
                                                        } else {
                                                            constants.Profile_ViewModel.updateImFollowedByUserId_Search_FF(
                                                                profile_Content.value?.user_id
                                                                    ?: 0
                                                            )
                                                        }
                                                        // constants.Profile_ViewModel.setunfollowClicker()
                                                    }

                                                    1 -> {
                                                        toast("Try Again later")
                                                    }

                                                    2 -> {

                                                    }
                                                }
                                            }
                                        } else {
                                            GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
                                        }
                                    }
                                }
                            }
                        , contentAlignment = Alignment.Center
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
        }
        , image = content.user_Image
        , userName = content.user_Name
    )

    /// report bottom sheet
    if (report_BS){

        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )

        ModalBottomSheet(
            onDismissRequest = {
                report_BS = false
                constants.Profile_ViewModel.toggle_ProfileReport_Options(
                    -1
                )
            },
            sheetState = sheetState
            , containerColor = newWhite
        )
        {
            Column (
                modifier = Modifier
                , verticalArrangement = Arrangement.Top
            ){
                val user_Manual_report = remember { mutableStateOf(false) }
                val user_Manual_report_String = remember { mutableStateOf("") }



                AnimatedContent (
                    targetState = report_success
                )
                {
                        targetState ->

                    if (targetState.value) {
                        Column(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(horizontal = 16.dp)
                            , verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        )
                        {
                            Text(
                                text = "Why are you reporting ?",
                                color = newBlack,
                                fontSize = constants.textUnit(24),
                                fontFamily = constants.fontFamily(0),
                                modifier = Modifier.align(Alignment.Start)
                            )

                            report_Options.value.forEachIndexed { index, profileReportOptionsDc ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = profileReportOptionsDc.option_title,
                                        color = newBlack,
                                        fontSize = constants.textUnit(14),
                                        fontFamily = constants.fontFamily(3)
                                    )

                                    RadioButton(
                                        selected = profileReportOptionsDc.isSelected,
                                        onClick = {
                                            user_Manual_report.value = false
                                            if (index < report_Options.value.size - 1) {
                                                constants.Profile_ViewModel.toggle_ProfileReport_Options(
                                                    profileReportOptionsDc.id
                                                )
                                            } else {
                                                user_Manual_report.value = true
                                            }
                                        }
                                    )
                                }
                            }

                            AnimatedVisibility(
                                user_Manual_report.value,
                                enter = slideInHorizontally(tween(900)) { it }
                            ) {
                                /* Box(
                                 modifier = Modifier
                                     .fillMaxWidth()
                                     .heightIn(min = 50.dp , max = 80.dp)
                                     .clip(RoundedCornerShape(8.dp))
                                     .background(Color.White)
                                     .border(1.dp , newGray , RoundedCornerShape(8.dp))
                             )
                             {
                                 TextField(
                                     value = user_Manual_report_String.value,
                                     onValueChange = {
                                         user_Manual_report_String.value = it
                                     },
                                     placeholder = {
                                         Text(
                                             text = "What else we need to know...",
                                             color = newBlack,
                                             fontSize = constants.textUnit(12),
                                             fontFamily = constants.fontFamily(3)
                                         )
                                     },
                                     colors = TextFieldDefaults.colors(
                                         focusedContainerColor = Color.White
                                         ,unfocusedContainerColor = Color.White
                                         , focusedIndicatorColor = Color.Transparent
                                         , unfocusedIndicatorColor = Color.Transparent
                                         , focusedTextColor = newBlack
                                         , unfocusedTextColor = newGray
                                     )
                                 )
                             }*/
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color.White)
                                        .border(1.dp, newGray, RoundedCornerShape(8.dp))
                                )
                                {
                                    BasicTextField(
                                        value = user_Manual_report_String.value,
                                        onValueChange = { user_Manual_report_String.value = it },
                                        modifier = Modifier
                                            .padding(12.dp)
                                            .fillMaxWidth(),
                                        textStyle = TextStyle(
                                            color = newBlack,
                                            fontSize = constants.textUnit(14),
                                            fontFamily = constants.fontFamily(2)
                                        ),
                                        decorationBox = { innerTextField ->
                                            if (user_Manual_report_String.value.isEmpty()) {
                                                Text(
                                                    text = "Enter reason...",
                                                    color = newGray,
                                                    fontSize = constants.textUnit(14),
                                                    fontFamily = constants.fontFamily(2)
                                                )
                                            }
                                            innerTextField()
                                        }
                                    )
                                }

                            }
                        }
                    }
                    else {
                        Column(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                            , verticalArrangement = Arrangement.SpaceEvenly
                            , horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            SubcomposeAsyncImage(
                                model = R.drawable.profile_report_submit_success
                                ,""
                                , modifier = Modifier
                                    .size(150.dp)
                            )


                            Text(
                                text = "Submitted Successfully",
                                color = newBlack,
                                fontSize = constants.textUnit(18),
                                fontFamily = constants.fontFamily(0)
                            )


                            Text(
                                text = "Thank you for bringing this to our attention.",
                                color = newBlack,
                                fontSize = constants.textUnit(12),
                                fontFamily = constants.fontFamily(3)
                            )

                        }
                    }
                }

                Spacer(modifier = Modifier.padding(8.dp))

                if (report_success.value){
                    Static_Bottom(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(92.dp)
                        , content = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                , contentAlignment = Alignment.Center
                            )
                            {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(.9f)
                                        .fillMaxHeight(.7f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(newBlue)
                                        .noRippleClickable {
                                            if (user_Manual_report_String.value.isEmpty()) {
                                                user_Manual_report_String.value =
                                                    constants.Profile_ViewModel.getSelectedProfileReportOptionDescription()
                                                        ?: ""
                                            }
                                            constants.API_Vm.put_Report_All(
                                                user_id = AppPreferences.getUserId(),
                                                user_post_id = "",
                                                //AppPreferences.get_Post_Id(),
                                                receiver_id = (profile_Content.value?.user_id
                                                    ?: 0).toString(),
                                                comment_id = "",
                                                report_sentence_id = (constants.Profile_ViewModel.getSelectedProfileReportOptionId()
                                                    ?.plus(1)) ?: 0,
                                                report_sentence = user_Manual_report_String.value,
                                                status = 1,
                                            )
                                            { apiResultHandling ->

                                                when (apiResultHandling) {
                                                    is API_Result_Handling.Loading -> {
                                                        // loading
                                                        //constants.PostProperty_ViewModel.change_Status_PFs(true)
                                                    }

                                                    is API_Result_Handling.Error -> {
                                                        // fail
                                                        //constants.PostProperty_ViewModel.change_Status_PFs(false)
                                                    }

                                                    is API_Result_Handling.Success -> {


                                                        constants.Profile_ViewModel.updateReported_Selected_Profile(
                                                            1
                                                        )
                                                        constants.Profile_ViewModel.toggleReportSubmissionSuccess()
                                                        //constants.Reels_ViewModel.deleteVideoById_Profile_Post_Reels(videos[pagerState.currentPage].user_id)
                                                        // success
                                                        //constants.PostProperty_ViewModel.change_Status_PFs(false)
                                                    }

                                                    is API_Result_Handling.NoData -> {
                                                        // no data
                                                        //constants.PostProperty_ViewModel.change_Status_PFs(false)
                                                    }

                                                    is API_Result_Handling.Deactivated -> {
                                                        /// resultCallback(5)
                                                    }
                                                }
                                            }
                                        }
                                    , contentAlignment = Alignment.Center
                                ){
                                    Text(
                                        text = "Submit Report",
                                        color = Color.White,
                                        fontSize = constants.textUnit(14),
                                        fontFamily = constants.fontFamily(0)
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
    }


}




@Composable
fun Profile_FF_Structure(navController: NavHostController, viewModel: Common_H_ViewModel) {


    BackHandler {
        println("Backhandler Restricted ff view")
    }

    val network = rememberNetworkStatus()
    val notchPadding = rememberNotchHeightDp()

    viewModel.toggleshowBABars(false)
    val BA_Bar_Listener = viewModel.showBABars.collectAsState()
    LaunchedEffect(BA_Bar_Listener.value) {
        viewModel.toggleshowBABars(BA_Bar_Listener.value)
    }



    val context = LocalContext.current

    val profile_selected_Index = constants.Profile_ViewModel.show_Tapped_FFs.collectAsState()
    val unfollow_PUP = constants.Profile_ViewModel.unFollowClick.collectAsState()
    val followRequest_Delete = constants.Profile_ViewModel.followRequestDelete.collectAsState()

    val new_Stack by constants.Profile_ViewModel.currentBFHandler.collectAsStateWithLifecycle()


    // ✅ This will refresh YOUR counts when you return
    LaunchedEffect(new_Stack?.id, new_Stack?.ff_Fw_Count, new_Stack?.ff_Fg_Count) {
        if (new_Stack != null) {
            println("🔄 Profile_FF_Structure detected stack change -- ${new_Stack}")

            if (network.value == NetworkStatus.Online) {
                // ✅ Make sure to fetch YOUR profile, not the other user's
                val savedOtherUserId = constants.Profile_ViewModel.get_Other_User_Id()
                constants.Profile_ViewModel.put_Other_User_Id(0)  // ✅ Set to 0 to get YOUR profile

                Get_User_Profile_API_Call() { result ->
                    when (result) {
                        1 -> {
                            val userId = AppPreferences.getUserId()  // ✅ Always YOUR user ID

                            println("call going inside ###0 -- $$$$${userId}")

                            constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
                                userId = userId,
                                newFollowers = constants.Profile_ViewModel.get_Followers_Count_BGAPIC(),
                                newFollowing = constants.Profile_ViewModel.get_Following_Count_BGAPIC()
                            )

                            println("✅ Updated counts - Followers: ${constants.Profile_ViewModel.get_Followers_Count_BGAPIC()}, Following: ${constants.Profile_ViewModel.get_Following_Count_BGAPIC()}")
                        }
                    }
                }

                // ✅ Restore the saved other_UserId
                constants.Profile_ViewModel.put_Other_User_Id(savedOtherUserId)
            }
        }
    }


    println("CHECKING NOWWWWW --- ${new_Stack}")

    // ✅ Get current user and tab
    val currentUserId = if (new_Stack?.other_UserId == 0 || new_Stack?.other_UserId == null) {
        AppPreferences.getUserId()
    } else {
        new_Stack?.other_UserId ?: 0
        //AppPreferences.getUserId()
    }
    val currentTab = new_Stack?.selected_Tab ?: 0
    val currentKey = constants.Profile_ViewModel.createFFKey(currentUserId, currentTab)

    println("📍 Profile_FF_Structure - UserId: $currentUserId, Tab: $currentTab, Key: $currentKey")

    // ✅ Collect the entire map and extract the specific list
    val ffListMap by constants.Profile_ViewModel.users_FF_List_Map.collectAsStateWithLifecycle()
    val ffSearchMap by constants.Profile_ViewModel.users_FF_Search_Map.collectAsStateWithLifecycle()

    val users_Profiles_List = ffListMap[currentKey] ?: emptyList()
    val users_Profiles_Search_List = ffSearchMap[currentKey] ?: emptyList()

    val tapped_Profiles_List = constants.Profile_ViewModel.tapped_Profile_List.collectAsState()

    // ✅ These need to be State objects to trigger recomposition
    var isLoading by remember { mutableStateOf(constants.API_Vm.isLoading_FF) }
    var errorMessage by remember { mutableStateOf(constants.API_Vm.errorMessage_FF) }
    var currentPage by remember { mutableStateOf(constants.API_Vm.currentPage_FF) }
    var totalPages by remember { mutableStateOf(constants.API_Vm.totalPages_FF) }
    val listState = rememberLazyListState()


    // 1. In Profile_FF_Structure LaunchedEffect:
    LaunchedEffect(new_Stack?.id) {
        println("📍 Profile_FF_Structure state:")
        println("   Stack size: ${constants.Profile_ViewModel.profile_BF_Handler.value.size}")
        println("   Current handler: ${new_Stack}")
        println("   Current user: ${currentUserId}, Tab: ${currentTab}")
    }
    // ✅ Sync with API_Vm values
    LaunchedEffect(Unit) {
        snapshotFlow {
            Triple(
                constants.API_Vm.isLoading_FF,
                constants.API_Vm.currentPage_FF,
                constants.API_Vm.totalPages_FF
            )
        }.collect { (loading, page, total) ->
            isLoading = loading
            currentPage = page
            totalPages = total
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { constants.API_Vm.errorMessage_FF }
            .collect { errorMessage = it }
    }

    var search_Text = constants.Profile_ViewModel.search_Text_FF.collectAsStateWithLifecycle()
    var search_Text_Handler = constants.Profile_ViewModel.search_Text_FF_Handler.collectAsStateWithLifecycle()
    var is_Search_Enabled = constants.Profile_ViewModel.is_Search_Enabled.collectAsStateWithLifecycle()
    var is_Search_Enabled_Handler = constants.Profile_ViewModel.is_Search_Enabled_Handler.collectAsStateWithLifecycle()
    var BPAIC__FW_HANDER = constants.Profile_ViewModel.following_Count_BG_API_Call.collectAsStateWithLifecycle()
    var BPAIC__FR_HANDER = constants.Profile_ViewModel.followers_Count_BG_API_Call.collectAsStateWithLifecycle()

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val firstLoad = isLoading && users_Profiles_List.isEmpty() && currentPage == 1

    DisposableEffect(Unit) {
        constants.Profile_ViewModel.add_Search_Text_FF(search_Text_Handler.value)
        constants.Profile_ViewModel.add_Search_State(is_Search_Enabled_Handler.value)
        onDispose { }
    }

    LaunchedEffect(new_Stack) {
        println("🔥 New stack updated = $new_Stack")
    }

    var retry by remember { mutableStateOf(0) }

    // ✅ Load data if not already cached
    if (network.value == NetworkStatus.Online) {
        println("DISPOSAL SEARCH ENABLED -- ${is_Search_Enabled.value}")
        LaunchedEffect(currentUserId, currentTab, is_Search_Enabled.value, search_Text.value, retry) {
            if (is_Search_Enabled.value) {
                println("COMES GIVE SEARCH")
                constants.API_Vm.totalPages_FF = 1
                constants.API_Vm.load_Search_FF(
                    user_id = AppPreferences.getUserId(),
                    others_id = if (currentUserId == AppPreferences.getUserId()) "" else currentUserId.toString(),
                    status = currentTab + 1,
                    search = search_Text.value,
                    page = 1
                )
            } else {
                // ✅ Only load if list is empty (not cached)
                //if (users_Profiles_List.isEmpty()) {
                    println("📥 Loading FF list for user: $currentUserId, tab: $currentTab")
                    constants.API_Vm.load_Profile_FF_List(
                        user_id = AppPreferences.getUserId(),
                        others_id = if (currentUserId == AppPreferences.getUserId()) "" else currentUserId.toString(),
                        status = currentTab + 1,
                        page = 1
                    )
               // } else {
                    println("✅ Using cached data for user: $currentUserId, tab: $currentTab, size: ${users_Profiles_List.size}")
                //}
            }
        }

        // ✅ Pagination - Observe list state changes
        LaunchedEffect(users_Profiles_List.size) {
            if (!is_Search_Enabled.value) {
                snapshotFlow {
                    val lastIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                    val totalItems = listState.layoutInfo.totalItemsCount
                    Triple(lastIndex, totalItems, isLoading)
                }
                    .collect { (lastVisibleItemIndex, totalItems, loading) ->
                        val loadMoreThreshold = 2

                        if (lastVisibleItemIndex != null &&
                            totalItems > 0 &&
                            lastVisibleItemIndex >= totalItems - loadMoreThreshold &&
                            !loading &&
                            currentPage < totalPages &&
                            network.value == NetworkStatus.Online
                        ) {
                            println("📄 Loading page ${currentPage + 1} - Current: $currentPage, Total: $totalPages")
                            constants.API_Vm.load_Profile_FF_List(
                                user_id = AppPreferences.getUserId(),
                                others_id = if (currentUserId == AppPreferences.getUserId()) "" else currentUserId.toString(),
                                status = currentTab + 1,
                                page = currentPage + 1
                            )
                        }
                    }
            }
        }
    }
    else {
        GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = if (forTab()) 16.dp else notchPadding.value)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {

        // Top Row: Back Button + Username
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            /*Backer(
                modifier = Modifier,
                onBackClick = {
                    ClickHelper.getInstance().clickOnce {
                        if (ClickGuard.canClick()) {
                            if (tapped_Profiles_List.value.profiles.isNotEmpty()) {
                                constants.Profile_ViewModel.removeLastProfile()
                                constants.Profile_ViewModel.remove_Tapped_FFs_last()
                                constants.Profile_ViewModel.unfollow_Dismiss()
                                constants.Profile_ViewModel.delete_Follow_Dismiss()
                                // ✅ ADD THIS: Refresh the counts when going back
                                Get_User_Profile_API_Call() { result ->
                                    when (result) {
                                        1 -> {
                                            val previousUserId = constants.Profile_ViewModel.show_Current_BF_Handler()?.let {
                                                if (it.other_UserId == 0) it.current_UsedId else it.other_UserId
                                            } ?: 0

                                            constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
                                                userId = previousUserId,
                                                newFollowers = constants.Profile_ViewModel.get_Followers_Count_BGAPIC(),
                                                newFollowing = constants.Profile_ViewModel.get_Following_Count_BGAPIC()
                                            )
                                        }
                                    }
                                }
                            } else {
                                constants.Profile_ViewModel.add_Selected_Profile_Id(0)
                                constants.Profile_ViewModel.put_Other_User_Id(0)
                                viewModel.toggleshowBABars(true)
                            }
                            constants.API_Vm.totalPages_Profile_Posts = 1
                            navController.navigateUp()
                        }
                    }
                }
            )*/

            Backer(
                modifier = Modifier,
                onBackClick = {
                    ClickHelper.getInstance().clickOnce {
                        if (ClickGuard.canClick()) {
                            keyboardController?.hide()
                            focusManager.clearFocus()

                            val currentStack = constants.Profile_ViewModel.profile_BF_Handler.value

                            if (currentStack.size > 1) {
                                val currentHandler = currentStack.last()
                                val previousHandler = currentStack[currentStack.size - 2]

                                println("🔙 Back pressed from ${currentHandler.screenType}")
                                println("   Previous screen: ${previousHandler.screenType}")

                                // Remove current handler (FF screen)
                                constants.Profile_ViewModel.remove_last_BF_Handler()

                                // Determine navigation based on previous screen type
                                when (previousHandler.screenType) {
                                    ScreenType.PROFILE -> {
                                        // ✅ Going back to a Profile screen
                                        val profileId = if (previousHandler.other_UserId == 0) {
                                            previousHandler.current_UsedId
                                        } else {
                                            previousHandler.other_UserId
                                        }

                                        constants.Profile_ViewModel.put_Other_User_Id(previousHandler.other_UserId)
                                        constants.Profile_ViewModel.add_Selected_Profile_Id(profileId)

                                        // Refresh profile data
                                        if (network.value == NetworkStatus.Online) {
                                            Get_User_Profile_API_Call() { result ->
                                                when (result) {
                                                    1 -> {
                                                        constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
                                                            userId = profileId,
                                                            newFollowers = constants.Profile_ViewModel.get_Followers_Count_BGAPIC(),
                                                            newFollowing = constants.Profile_ViewModel.get_Following_Count_BGAPIC()
                                                        )
                                                    }
                                                }
                                            }
                                        }

                                        navController.navigateUp()
                                    }

                                    ScreenType.FF_LIST -> {
                                        // ✅ Going back to another FF screen
                                        // This shouldn't happen in normal flow, but handle it
                                        println("⚠️ Unexpected: Going from FF to FF")
                                        navController.navigateUp()
                                    }
                                }
                            } else {
                                // Last item in stack - go back to own profile
                                constants.Profile_ViewModel.put_Other_User_Id(0)
                                constants.Profile_ViewModel.add_Selected_Profile_Id(0)
                                viewModel.toggleshowBABars(true)
                                navController.navigateUp()
                            }

                            constants.Profile_ViewModel.unfollow_Dismiss()
                            constants.Profile_ViewModel.delete_Follow_Dismiss()
                            constants.API_Vm.totalPages_Profile_Posts = 1
                        }
                    }
                }
            )

            Text(
                text = new_Stack?.ff_User_Name ?: "Profile",
                color = newBlack,
                fontSize = constants.textUnit(24),
                fontFamily = constants.fontFamily(0)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Tab Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(.8f)
                .background(newWhite)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        )
        {
            repeat(2) { index ->
                Box(
                    modifier = Modifier
                        .fillMaxHeight(0.60f)
                        .weight(4f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (currentTab == index) newBlack else newWhite)
                        .noRippleClickable {
                            ClickHelper
                                .getInstance()
                                .clickOnce {
                                    if (ClickGuard.canClick()) {
                                       // if (network.value == NetworkStatus.Online) {

                                            constants.Profile_ViewModel.clear_Search_Text_FF()
                                            constants.Profile_ViewModel.disable_Search()

                                            if (index == 0) {
                                                constants.Profile_ViewModel.updateSelectedTab_BF_Handler(
                                                    new_Stack?.id ?: 999,
                                                    0,
                                                    flw_count = new_Stack?.ff_Fw_Count ?: 0,
                                                    new_Stack?.ff_Fg_Count ?: 0,
                                                )
                                            } else {
                                                constants.Profile_ViewModel.updateSelectedTab_BF_Handler(
                                                    new_Stack?.id ?: 999,
                                                    1,
                                                    flw_count = new_Stack?.ff_Fw_Count ?: 0,
                                                    new_Stack?.ff_Fg_Count ?: 0,
                                                )
                                            }

                                            users_Profiles_List.getOrNull(index)?.let { user ->
                                                constants.Profile_ViewModel.replace_Last_Tapped_FF(
                                                    Profile_ViewModel.Tap_Flw_Flg_DC(
                                                        id = user.user_id ?: 0,
                                                        tap_data = index,
                                                        flg_Count = user.following_count ?: 0,
                                                        flw_Count = user.followers_count ?: 0
                                                    )
                                                )
                                            }

                                            constants.API_Vm.isLoading_FF = true
                                            constants.API_Vm.totalPages_FF = 1
                                            constants.Profile_ViewModel.clear_Search_Text_FF()
                                            constants.Profile_ViewModel.disable_Search()

                                            keyboardController?.hide()
                                            focusManager.clearFocus()
                                        }
                                    //} else {
                                       // GlobalSnackbar.show("It Seems your are offline !!.Refresh again")
                                   // }
                                }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(horizontal = 8.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = if (index == 0) (if(new_Stack?.ff_Fw_Count == null) "" else new_Stack?.ff_Fw_Count.toString() ) else (if(new_Stack?.ff_Fg_Count == null) "" else new_Stack?.ff_Fg_Count.toString()),
                            color = if (currentTab == index) Color.White else newBlack,
                            fontSize = constants.textUnit(14),
                            fontFamily = constants.fontFamily(0)
                        )

                        Text(
                            text = if (index == 0) "Followers" else "Following",
                            color = if (currentTab == index) Color.White else newBlack,
                            fontSize = constants.textUnit(14),
                            fontFamily = constants.fontFamily(0)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Search Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(if (forTab()) 0.5f else 0.7f)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .border(1.dp, newBlack, RoundedCornerShape(8.dp)),
            verticalAlignment = Alignment.CenterVertically,
        )
        {
            TextField(
                value = search_Text.value,
                onValueChange = {

                    if (network.value == NetworkStatus.Online) {
                        constants.Profile_ViewModel.add_Search_Text_FF(it)
                        if (it.length >= 3 && constants.Profile_ViewModel.get_Search_Text_FF().length >= 3) {
                            constants.Profile_ViewModel.update_FF_BF_Search_State_Text_ByUserId(
                                userId = currentUserId,
                                state = true,
                                text = it
                            )
                            constants.Profile_ViewModel.enable_Search()
                        } else if (constants.Profile_ViewModel.get_Search_Text_FF()
                                .isEmpty() || it.isEmpty()
                        ) {
                            constants.Profile_ViewModel.update_FF_BF_Search_State_Text_ByUserId(
                                userId = currentUserId,
                                state = false,
                                text = search_Text.value
                            )
                            constants.API_Vm.isLoading_FF = false
                            constants.API_Vm.totalPages_FF = 1
                            constants.Profile_ViewModel.clear_Search_Text_FF()
                            constants.Profile_ViewModel.disable_Search()
                        }
                    }
                    else {
                        toast(constants.activity.getString(R.string.no_Internet))
                    }
                },
                placeholder = {
                    Text(
                        text = "search users by name..",
                        color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                    )
                },
                leadingIcon = {
                    AsyncImage(
                        model = R.drawable.search_icon,
                        contentDescription = "",
                        modifier = Modifier.size(14.dp)
                    )
                },
                trailingIcon = {

                    if (search_Text.value.isNotEmpty()) {
                        AsyncImage(
                            model = R.drawable.close,
                            contentDescription = "",
                            modifier = Modifier
                                .size(16.dp)
                                .noRippleClickable {
                                    constants.Profile_ViewModel.update_FF_BF_Search_State_Text_ByUserId(
                                        userId = currentUserId,
                                        state = false,
                                        text = ""
                                    )
                                    constants.API_Vm.isLoading_FF = false
                                    constants.API_Vm.totalPages_FF = 1
                                    constants.Profile_ViewModel.clear_Search_Text_FF()
                                    constants.Profile_ViewModel.disable_Search()
                                    constants.Profile_ViewModel.add_Search_Text_FF("")
                                    keyboardController?.hide()
                                    focusManager.clearFocus()
                                }
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedTextColor = newBlack,
                    unfocusedTextColor = newBlack,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {

                        if (search_Text.value.length >= 3 && constants.Profile_ViewModel.get_Search_Text_FF().length >= 3) {
                            constants.Profile_ViewModel.update_FF_BF_Search_State_Text_ByUserId(
                                userId = currentUserId,
                                state = true,
                                text = search_Text.value
                            )
                            constants.Profile_ViewModel.enable_Search()
                        } else if (constants.Profile_ViewModel.get_Search_Text_FF().isEmpty()) {
                            constants.Profile_ViewModel.update_FF_BF_Search_State_Text_ByUserId(
                                userId = currentUserId,
                                state = false,
                                text = search_Text.value
                            )
                            constants.Profile_ViewModel.disable_Search()
                        }

                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                ),
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LaunchedEffect(users_Profiles_List) {
            println("List updated: size=${users_Profiles_List.size}")
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(8.3f)
        )
        {
            when {
                network.value == NetworkStatus.Offline -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(8.3f)
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

                firstLoad -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(8.3f)
                            .background(newWhite),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }

                users_Profiles_List.isEmpty() && !isLoading -> {
                    if (users_Profiles_Search_List.isEmpty() && is_Search_Enabled.value ){
                        Column(
                            modifier = Modifier
                                .padding(bottom = 48.dp)
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        )
                        {
                            Spacer(modifier = Modifier.padding(8.dp))
                            Image(painterResource(R.drawable.emptyusearchrento), "")
                            Spacer(modifier = Modifier.padding(8.dp))
                            Text("We couldn't find anything!"
                                ,textAlign = TextAlign.Center
                                , color = newBlack
                                , fontSize = constants.textUnit(14)
                                , fontFamily = constants.fontFamily(2))
                            Spacer(modifier = Modifier.padding(8.dp))
                            Text(
                                "Try searching for something else.",
                                textAlign = TextAlign.Center
                                , color = newBlack
                                , fontSize = constants.textUnit(14)
                                , fontFamily = constants.fontFamily(2)
                            )
                        }
                    }
                    else {
                        Column(
                            modifier = Modifier
                                .padding(bottom = 48.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        )
                        {
                            if (currentTab == 0) {
                                Spacer(modifier = Modifier.padding(8.dp))
                                Image(painterResource(R.drawable.emptyfollowersrento), "")
                                Spacer(modifier = Modifier.padding(8.dp))
                                Text("No Followers!"
                                    ,textAlign = TextAlign.Center
                                    , color = newBlack
                                    , fontSize = constants.textUnit(14)
                                    , fontFamily = constants.fontFamily(2))
                                Spacer(modifier = Modifier.padding(8.dp))
                                Text("Looks like nobody has followed yet.",
                                    textAlign = TextAlign.Center
                                    , color = newBlack
                                    , fontSize = constants.textUnit(14)
                                    , fontFamily = constants.fontFamily(2)
                                )
                            } else {
                                Spacer(modifier = Modifier.padding(8.dp))
                                Image(painterResource(R.drawable.emptyfollowingrentro), "")
                                Spacer(modifier = Modifier.padding(8.dp))
                                Text("No Followings!"
                                    ,textAlign = TextAlign.Center
                                    , color = newBlack
                                    , fontSize = constants.textUnit(14)
                                    , fontFamily = constants.fontFamily(2))
                                Spacer(modifier = Modifier.padding(8.dp))
                                Text("Looks like not following anyone yet.",
                                    textAlign = TextAlign.Center
                                    , color = newBlack
                                    , fontSize = constants.textUnit(14)
                                    , fontFamily = constants.fontFamily(2)
                                )
                            }
                        }
                    }
                }

                !isLoading && users_Profiles_Search_List.isEmpty() && is_Search_Enabled.value -> {
                    Column(
                        modifier = Modifier
                            .padding(bottom = 48.dp)
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        Spacer(modifier = Modifier.padding(8.dp))
                        Image(painterResource(R.drawable.emptyusearchrento), "")
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text("We couldn't find anything!"
                        ,textAlign = TextAlign.Center
                            , color = newBlack
                            , fontSize = constants.textUnit(14)
                            , fontFamily = constants.fontFamily(2))
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(
                            "Try searching for something else.",
                            textAlign = TextAlign.Center
                            , color = newBlack
                            , fontSize = constants.textUnit(14)
                            , fontFamily = constants.fontFamily(2)
                        )
                    }
                }

                !errorMessage.isNullOrEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        API_Fail_UI(onReTryClick = {
                            constants.Profile_ViewModel.clearUserFFData(currentUserId, currentTab)
                            constants.API_Vm.errorMessage_FF = null
                            retry = retry + 213435
                        })
                    }
                }

                users_Profiles_List.isNotEmpty() || users_Profiles_Search_List.isNotEmpty() -> {

                    // Add this after your DisposableEffect in Profile_FF_Structure
//                    LaunchedEffect(new_Stack?.id) {
//                        // When the stack changes (navigating back), refresh the current profile's counts
//                        if (network.value == NetworkStatus.Online) {
//                            Get_User_Profile_API_Call() { result ->
//                                when (result) {
//                                    1 -> {
//                                        // Update the current handler with fresh counts
//                                        val currentHandler = constants.Profile_ViewModel.show_Current_BF_Handler()
//                                        if (currentHandler != null) {
//                                            val targetUserId = if (currentHandler.other_UserId == 0) {
//                                                currentHandler.current_UsedId
//                                            } else {
//                                                currentHandler.other_UserId
//                                            }
//
//                                            constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
//                                                userId = targetUserId,
//                                                newFollowers = constants.Profile_ViewModel.get_Followers_Count_BGAPIC(),
//                                                newFollowing = constants.Profile_ViewModel.get_Following_Count_BGAPIC()
//                                            )
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
                    AnimatedContent(
                        targetState = currentTab,
                        transitionSpec = {
                            when (targetState) {
                                0 -> slideInHorizontally(tween(400)) { -it } togetherWith slideOutHorizontally { it }
                                else -> slideInHorizontally(tween(400)) { it } togetherWith slideOutHorizontally { -it }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(8f)
                    ) { targetState ->
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            state = listState
                        ) {
                            itemsIndexed(
                                items = if (!is_Search_Enabled.value) users_Profiles_List else users_Profiles_Search_List,
                                key = { index, item -> "WHAT${item?.user_id}_${index}" }
                            ) { _, item ->
                                if (item != null) {
                                    val type =
                                        if ((new_Stack?.other_UserId == 0 || new_Stack?.other_UserId == null) &&
                                            AppPreferences.getUserId() == 0
                                        ) {
                                            0
                                        } else {
                                            1
                                        }

                                    Profile_Following_Item_Structure(
                                        isWhich = targetState,
                                        profile_Type = type,
                                        item = item,
                                        navController = navController,
                                        new_Stack = new_Stack,
                                        is_Search_Enabled = is_Search_Enabled,
                                        search_Text = search_Text,
                                        currentUserId = currentUserId,
                                        currentTab = currentTab,
                                        keyboardController,
                                        focusManager
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    val content = constants.Profile_ViewModel.get_Unflw_Flw_Content_Pup()
    Common_Popup(
        visible = unfollow_PUP.value,
        modifier = Modifier.background(Color(0xffF7F0DC)),
        content = {
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                Spacer(modifier = Modifier.padding(2.dp))
                constants.spacer(2)

                Image(painter = painterResource(R.drawable.profilepopicon) , "",
                    modifier = Modifier.size(56.dp))

//                Text(
//                    text = "Unfollow ${content.user_Name} ?",
//                    color = newBlack,
//                    fontSize = constants.textUnit(16),
//                    fontFamily = constants.fontFamily(0)
//                )

                constants.spacer(2)

                Text(
                    text = "Are you sure you want to Unfollow this profile?",
                    color = newBlack,
                    fontSize = constants.textUnit(18),
                    fontFamily = constants.fontFamily(1),
                    textAlign = TextAlign.Center
                   //, lineHeight = 24.sp
                    ,modifier = Modifier.padding(horizontal = if(forTab())46.dp else 36.dp)
                )

                //Spacer(modifier = Modifier.padding(2.dp))
                constants.spacer(4)

                Row(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(4f)
                            .height(if (forTab()) 56.dp else 46.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xffE8E8E8))
                            .noRippleClickable {
//                                constants.Profile_ViewModel.setunfollowClicker()
                                constants.Profile_ViewModel.closeUnFollowClick()

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
                            .height(if (forTab()) 56.dp else 46.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Brush.verticalGradient(newPurpleGradient))
                            .border(
                                1.dp,
                                Brush.linearGradient(newPurpleGradientBorder),
                                RoundedCornerShape(8.dp)
                            )
                            .noRippleClickable {
                                ClickHelper.getInstance().clickOnce {
                                    if (ClickGuard.canClick()) {
                                        if (network.value == NetworkStatus.Online) {
                                            follow_Unfollow_Delete_API_Call() { result ->
                                                when (result) {
                                                    0 -> {
                                                        Get_User_Profile_API_Call() { result2 ->
                                                            when (result2) {
                                                                0 -> {

                                                                }

                                                                1 -> {
                                                                    println("CHECKING FF-${BPAIC__FR_HANDER.value} --${BPAIC__FW_HANDER.value}****- ${new_Stack?.current_UsedId} -${constants.Profile_ViewModel.get_Following_Count_BGAPIC()}- ${constants.Profile_ViewModel.get_Followers_Count_BGAPIC()}")
                                                                    constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
                                                                        userId = new_Stack?.current_UsedId
                                                                            ?: 0,
                                                                        newFollowers = constants.Profile_ViewModel.get_Followers_Count_BGAPIC(),
                                                                        newFollowing = constants.Profile_ViewModel.get_Following_Count_BGAPIC()
                                                                    )
                                                                }

                                                                2 -> {}
                                                            }
                                                        }

                                                        println("HELLO  NOW --- ${currentUserId}")


                                                        if (currentTab == 1) {
                                                            println("HELLO  NOW 2222--- ${currentUserId}")
                                                            if (AppPreferences.getUserId() == currentUserId) {
                                                                if (!is_Search_Enabled.value) {
                                                                    constants.Profile_ViewModel.deleteUserById_Profile_FF_Map(
                                                                        mapUserId = currentUserId,
                                                                        mapTab = currentTab,
                                                                        targetUserId = content.user_Id
                                                                            ?: 0
                                                                    )
                                                                } else {
                                                                    constants.Profile_ViewModel.deleteUserById_Profile_Search_FF_NEW(
                                                                        content.user_Id ?: 0
                                                                    )
                                                                }
                                                            } else {
                                                                println("HELLO  NOW -3333-- ${currentUserId}")
                                                                constants.Profile_ViewModel.updateImFollowedByUserId_FF_Map(
                                                                    mapUserId = currentUserId,
                                                                    mapTab = currentTab,
                                                                    targetUserId = content.user_Id
                                                                        ?: 0
                                                                )
                                                            }
                                                        } else if (currentTab == 0) {
                                                            println("HELLO  NOW 4444--- ${currentUserId}")
                                                            constants.Profile_ViewModel.updateImFollowedByUserId_FF_Map(
                                                                mapUserId = currentUserId,
                                                                mapTab = currentTab,
                                                                targetUserId = content.user_Id ?: 0
                                                            )
                                                        }
                                                        // }
//                                                        constants.Profile_ViewModel.setunfollowClicker()
                                                        constants.Profile_ViewModel.closeUnFollowClick()

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

    Common_Popup(
        visible = followRequest_Delete.value,
        modifier = Modifier.background(Color(0xffFCEDEC)),
        content = {
            Column(
                modifier = Modifier.padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.padding(2.dp))
               // constants.spacer(2)

//                Text(
//                    text = "Remove ${content.user_Name} ?",
//                    color = newBlack,
//                    fontSize = constants.textUnit(16),
//                    fontFamily = constants.fontFamily(0)
//                )
                Image(painter = painterResource(R.drawable.profileremoveicon) , "",
                    modifier = Modifier.size(56.dp))

                constants.spacer(2)

                Text(
                    text = "Are you sure you want to Remove this profile?",
                    color = newBlack,
                    fontSize = constants.textUnit(18),
                    fontFamily = constants.fontFamily(1),
                    textAlign = TextAlign.Center
//                   , lineHeight = 24.sp
                    , modifier = Modifier.padding(horizontal = if (forTab()) 46.dp else 36.dp)
                )

//                Spacer(modifier = Modifier.padding(2.dp))
                constants.spacer(4)

                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(4f)
                            .height(if (forTab()) 56.dp else 46.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xffE8E8E8))
                            .noRippleClickable {
                                constants.Profile_ViewModel.delete_Follow_Dismiss()
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
                            .height(if (forTab()) 56.dp else 46.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Brush.verticalGradient(newRedGradienBg))
                            .border(
                                1.dp,
                                Brush.linearGradient(newRedGradienBorder),
                                RoundedCornerShape(8.dp)
                            )
                            .noRippleClickable {
                                ClickHelper.getInstance().clickOnce {
                                    if (ClickGuard.canClick()) {
                                        if (network.value == NetworkStatus.Online) {
                                            follow_Unfollow_Delete_API_Call() { result ->
                                                when (result) {
                                                    0 -> {
                                                        Get_User_Profile_API_Call() { result2 ->
                                                            when (result2) {
                                                                0 -> {


                                                                }

                                                                1 -> {
                                                                    constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
                                                                        userId = new_Stack?.current_UsedId
                                                                            ?: 0,
                                                                        newFollowers = constants.Profile_ViewModel.get_Followers_Count_BGAPIC(),
                                                                        newFollowing = constants.Profile_ViewModel.get_Following_Count_BGAPIC()
                                                                    )
                                                                }

                                                                2 -> {}
                                                            }
                                                        }

                                                        if (!is_Search_Enabled.value) {
                                                            constants.Profile_ViewModel.deleteUserById_Profile_FF_Map(
                                                                mapUserId = currentUserId,
                                                                mapTab = currentTab,
                                                                targetUserId = content.user_Id ?: 0
                                                            )
                                                        } else {
                                                            constants.Profile_ViewModel.deleteUserById_Profile_Search_FF_NEW(
                                                                content.user_Id ?: 0
                                                            )
                                                        }
                                                        constants.Profile_ViewModel.delete_Follow_Dismiss()
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
                            text = "Remove",
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
        image = content.user_Image
        , userName = content.user_Name
    )

}




@Composable
fun Profile_Following_Item_Structure(
    isWhich: Int,
    profile_Type: Int,
    item: Get_Profile_FF_List_Data,
    navController: NavHostController,
    new_Stack: Profile_Handle_Back?,
    is_Search_Enabled: State<Boolean>,
    search_Text: State<String>,
    currentUserId: Int,
    currentTab: Int,
    keyboardController1: SoftwareKeyboardController?,
    focusManager1: FocusManager
) {
    val network = rememberNetworkStatus()
    val new = constants.Profile_ViewModel.profile_BF_Handler.collectAsState()

    Column {
        ListItem(
            leadingContent = {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(newGray),
                    contentAlignment = Alignment.Center
                ) {
                    SubcomposeAsyncImage(
                        model = item?.profile_image ?: "",
                        modifier = Modifier.fillMaxSize(),
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
                                    text = item?.username.takeIf { it?.isNotEmpty() == true }
                                        ?.take(1)?.uppercase() ?: ""
                                )
                            }
                        } else {
                            SubcomposeAsyncImageContent()
                        }
                    }
                }
            },
            headlineContent = {
                Text(
                    text = item.username,
                    color = newBlack,
                    fontSize = constants.textUnit(16),
                    fontFamily = constants.fontFamily(1)
                )
            },
            supportingContent = {
                Text(
                    text = item.name,
                    color = newBlack,
                    fontSize = constants.textUnit(12),
                    fontFamily = constants.fontFamily(2)
                )
            },
            trailingContent = {
                if (item.user_id == AppPreferences.getUserId()) {
                    // Don't show buttons for yourself
                } else {
                    val isViewingOwnProfile = (new_Stack?.other_UserId == 0
                            || new_Stack?.other_UserId == null
                            || new_Stack?.other_UserId == AppPreferences.getUserId())

                    if (isViewingOwnProfile) {
                        // Viewing your own profile
                        val followers_State = if (item.is_followed == 1 && item.im_followed == 0) {
                            0  // "Follow back"
                        } else {
                            1  // "Following"
                        }

                        Own_Profile_FF_Buttons(
                            isWhich_FF = isWhich,
                            Followers_State = followers_State,
                            onUnfollow = {
                                keyboardController1?.hide()
                                focusManager1.clearFocus()
                                constants.Profile_ViewModel.put_follow_unfollow_Status(2)
                                constants.Profile_ViewModel.put_Following_Id(item.user_id)
                                constants.Profile_ViewModel.add_Unflw_Flw_Content_Pup(
                                    id = item.user_id,
                                    username = item.username,
                                    profilePic = item.profile_image,
                                )
                                constants.Profile_ViewModel.setunfollowClicker()
                            },
                            onFollowBack = {
                                keyboardController1?.hide()
                                focusManager1.clearFocus()
                                constants.Profile_ViewModel.put_follow_unfollow_Status(1)
                                constants.Profile_ViewModel.put_Following_Id(item.user_id)
                                constants.Profile_ViewModel.add_Unflw_Flw_Content_Pup(
                                    id = item.user_id,
                                    username = item.username,
                                    profilePic = item.profile_image,
                                )
                                if (network.value == NetworkStatus.Online) {
                                    follow_Unfollow_Delete_API_Call() { result ->
                                        when (result) {
                                            0 -> {
                                                Get_User_Profile_API_Call() { result2 ->
                                                    when (result2) {
                                                        1 -> {
                                                            constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
                                                                userId = new_Stack?.current_UsedId ?: 0,
                                                                newFollowers = constants.Profile_ViewModel.get_Followers_Count_BGAPIC(),
                                                                newFollowing = constants.Profile_ViewModel.get_Following_Count_BGAPIC()
                                                            )
                                                        }
                                                    }
                                                }

                                                if (currentTab == 1) {
                                                    if (AppPreferences.getUserId() == currentUserId) {
                                                        if (!is_Search_Enabled.value) {
                                                            constants.Profile_ViewModel.deleteUserById_Profile_FF_Map(
                                                                mapUserId = currentUserId,
                                                                mapTab = currentTab,
                                                                targetUserId = item.user_id
                                                            )
                                                        } else {
                                                            constants.Profile_ViewModel.deleteUserById_Profile_Search_FF_NEW(
                                                                item.user_id
                                                            )
                                                        }
                                                    } else {
                                                        constants.Profile_ViewModel.updateImFollowedByUserId_FF_Map(
                                                            mapUserId = currentUserId,
                                                            mapTab = currentTab,
                                                            targetUserId = item.user_id
                                                        )
                                                    }
                                                } else if (currentTab == 0) {
                                                    constants.Profile_ViewModel.updateImFollowedByUserId_FF_Map(
                                                        mapUserId = currentUserId,
                                                        mapTab = currentTab,
                                                        targetUserId = item.user_id
                                                    )
                                                }
                                            }
                                            1 -> { toast("Try Again later") }
                                        }
                                    }
                                } else {
                                    GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
                                }
                            },
                            onFollowing = {
                                keyboardController1?.hide()
                                focusManager1.clearFocus()
                                constants.Profile_ViewModel.put_follow_unfollow_Status(2)
                                constants.Profile_ViewModel.put_Following_Id(item.user_id)
                                constants.Profile_ViewModel.add_Unflw_Flw_Content_Pup(
                                    id = item.user_id,
                                    username = item.username,
                                    profilePic = item.profile_image,
                                )
                                constants.Profile_ViewModel.setunfollowClicker()
                            },
                            onDelete = {
                                keyboardController1?.hide()
                                focusManager1.clearFocus()
                                constants.Profile_ViewModel.put_follow_unfollow_Status(3)
                                constants.Profile_ViewModel.put_Following_Id(item.user_id)
                                constants.Profile_ViewModel.add_Unflw_Flw_Content_Pup(
                                    id = item.user_id,
                                    username = item.username,
                                    profilePic = item.profile_image,
                                )
                                constants.Profile_ViewModel.delete_Follow_Request()
                            }
                        )
                    } else {
                        // Viewing someone else's profile
                        val followers_State = when {
                            item.im_followed == 0 && item.is_followed == 1 -> 1  // Follow back
                            item.im_followed == 1 && item.is_followed == 1 -> 2  // Following
                            item.im_followed == 0 && item.is_followed == 0 -> 0  // Follow
                            else -> 2
                        }

                        val following_State = when {
                            item.is_followed == 1 && item.im_followed == 0 -> 1  // "Follow back"
                            item.im_followed == 1 || item.im_followed == 1 && item.is_followed == 0 -> 2 // "Following"
                            else -> 0  // "Follow"
                        }

                        Others_Profile_FF_Buttons(
                            isWhich_FF = isWhich,
                            Following_State = following_State,
                            Followers_State = followers_State,
                            onFollow = {
                                keyboardController1?.hide()
                                focusManager1.clearFocus()
                                constants.Profile_ViewModel.put_follow_unfollow_Status(1)
                                constants.Profile_ViewModel.put_Following_Id(item.user_id)
                                constants.Profile_ViewModel.add_Unflw_Flw_Content_Pup(
                                    id = item.user_id,
                                    username = item.username,
                                    profilePic = item.profile_image,
                                )

                                if (network.value == NetworkStatus.Online) {
                                    follow_Unfollow_Delete_API_Call() { result ->
                                        when (result) {
                                            0 -> {
                                                Get_User_Profile_API_Call() { result2 ->
                                                    when (result2) {
                                                        1 -> {
                                                            constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
                                                                userId = new_Stack?.current_UsedId ?: 0,
                                                                newFollowers = constants.Profile_ViewModel.get_Followers_Count_BGAPIC(),
                                                                newFollowing = constants.Profile_ViewModel.get_Following_Count_BGAPIC()
                                                            )

                                                            if (!is_Search_Enabled.value) {
                                                                constants.Profile_ViewModel.updateImFollowedByUserId_FF_Map(
                                                                    mapUserId = new_Stack?.other_UserId ?: 0,
                                                                    mapTab = currentTab,
                                                                    targetUserId = item.user_id
                                                                )
                                                            } else {
                                                                constants.Profile_ViewModel.updateImFollowedByUserId_Search_FF_NEW(item.user_id)
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            1 -> { toast("Try Again later") }
                                        }
                                    }
                                } else {
                                    GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
                                }
                            },
                            onFollowBack = {
                                keyboardController1?.hide()
                                focusManager1.clearFocus()
                                constants.Profile_ViewModel.put_follow_unfollow_Status(1)
                                constants.Profile_ViewModel.put_Following_Id(item.user_id)
                                constants.Profile_ViewModel.add_Unflw_Flw_Content_Pup(
                                    id = item.user_id,
                                    username = item.username,
                                    profilePic = item.profile_image,
                                )

                                if (network.value == NetworkStatus.Online) {
                                    follow_Unfollow_Delete_API_Call() { result ->
                                        when (result) {
                                            0 -> {
                                                Get_User_Profile_API_Call() { result2 ->
                                                    when (result2) {
                                                        1 -> {
                                                            constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
                                                                userId = new_Stack?.current_UsedId ?: 0,
                                                                newFollowers = constants.Profile_ViewModel.get_Followers_Count_BGAPIC(),
                                                                newFollowing = constants.Profile_ViewModel.get_Following_Count_BGAPIC()
                                                            )
                                                        }
                                                    }
                                                }

                                                if (!is_Search_Enabled.value) {
                                                    constants.Profile_ViewModel.updateImFollowedByUserId_FF_Map(
                                                        mapUserId = currentUserId,
                                                        mapTab = currentTab,
                                                        targetUserId = item.user_id
                                                    )
                                                } else {
                                                    constants.Profile_ViewModel.updateImFollowedByUserId_Search_FF_NEW(item.user_id)
                                                }
                                            }
                                            1 -> { toast("Try Again later") }
                                        }
                                    }
                                } else {
                                    GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
                                }
                            },
                            onFollowing = {
                                keyboardController1?.hide()
                                focusManager1.clearFocus()
                                constants.Profile_ViewModel.put_follow_unfollow_Status(2)
                                constants.Profile_ViewModel.put_Following_Id(item.user_id)
                                constants.Profile_ViewModel.add_Unflw_Flw_Content_Pup(
                                    id = item.user_id,
                                    username = item.username,
                                    profilePic = item.profile_image,
                                )
                                constants.Profile_ViewModel.setunfollowClicker()
                            }
                        )
                    }
                }
            },
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
            /*modifier = Modifier.noRippleClickable {
                ClickHelper.getInstance().clickOnce {
                    if (ClickGuard.canClick()) {
                        // ✅ FIX: Check if you're already viewing this profile
                        val currentProfileId = constants.Profile_ViewModel.selected_Profile_Id.value

                        // Don't navigate if clicking on the same profile you're already viewing
//                        if (item.user_id == currentProfileId) {
//                            println("⚠️ Already viewing this profile, skipping navigation")
//                            return@clickOnce
//                        }

                        // ✅ FIX: Don't navigate if clicking on your own profile when you're on your own profile
//                        if (item.user_id == AppPreferences.getUserId() &&
//                            (new_Stack?.other_UserId == 0 || new_Stack?.other_UserId == null)) {
//                            println("⚠️ Can't navigate to your own profile from your own profile")
//                            return@clickOnce
//                        }
                        keyboardController1?.hide()
                        focusManager1.clearFocus()

                        constants.Profile_ViewModel.add_BF_Handler(
                            item = Profile_Handle_Back(
                                //id = ,
                                current_UsedId = AppPreferences.getUserId(),
                                other_UserId = item.user_id,
                                selected_Tab = new_Stack?.selected_Tab ?: 0,
                                ff_User_Name = item.username,
                                ff_Fw_Count = item.followers_count,
                                ff_Fg_Count = item.following_count,
                                is_Search_Enabled = is_Search_Enabled.value,
                                search_Text = search_Text.value
                            )
                        )

                        constants.Profile_ViewModel.clearSelectedUserProfile()
                        constants.Profile_ViewModel.add_Selected_User_Name(item.username ?: "Profile")

                        // Set the profile ID and navigate
                        constants.Profile_ViewModel.addProfile(item.user_id)
                        constants.Profile_ViewModel.add_Selected_Profile_Id(id = item.user_id)
                        constants.Profile_ViewModel.put_Other_User_Id(item.user_id)
                        constants.Profile_ViewModel.clear_SearchList_FF()
                        constants.API_Vm.totalPages_Profile_Posts = 1

                        println("✅ Navigating to profile: ${item.username} (ID: ${item.user_id})")
                        navController.navigate(ProfileScreenFlow.Other_Profile_Structure.route)
                    }
                }
            }*/
            modifier = Modifier.noRippleClickable {
                ClickHelper.getInstance().clickOnce {
                    if (ClickGuard.canClick()) {
                        keyboardController1?.hide()
                        focusManager1.clearFocus()

                        val currentProfileId = new_Stack?.other_UserId ?: 0
                        val targetProfileId = item.user_id

                        if (targetProfileId == AppPreferences.getUserId() && currentProfileId == 0) {
                            println("⚠️ Can't navigate to your own profile from your own FF list")
                            return@clickOnce
                        }

                        if (targetProfileId == currentProfileId) {
                            println("⚠️ Already viewing this profile, skipping navigation")
                            return@clickOnce
                        }

                        // ✅ Add handler for PROFILE screen
                        constants.Profile_ViewModel.add_BF_Handler(
                            item = Profile_Handle_Back(
                                current_UsedId = AppPreferences.getUserId(),
                                other_UserId = item.user_id,
                                selected_Tab = 0,
                                ff_User_Name = item.username,
                                ff_Fw_Count = item.followers_count ?: 0,
                                ff_Fg_Count = item.following_count ?: 0,
                                is_Search_Enabled = false,
                                search_Text = "",
                                screenType = ScreenType.PROFILE // ✅ Set to PROFILE
                            )
                        )

                        // Clear and set new profile data
                        constants.Profile_ViewModel.clearSelectedUserProfile()
                        constants.Profile_ViewModel.add_Selected_User_Name(item.username ?: "Profile")
                        constants.Profile_ViewModel.addProfile(item.user_id)
                        constants.Profile_ViewModel.add_Selected_Profile_Id(id = item.user_id)
                        constants.Profile_ViewModel.put_Other_User_Id(item.user_id)
                        constants.Profile_ViewModel.clear_SearchList_FF()
                        constants.Profile_ViewModel.clear_Search_Text_FF()
                        constants.Profile_ViewModel.disable_Search()
                        constants.API_Vm.totalPages_Profile_Posts = 1

                        println("✅ Navigating to profile: ${item.username} (ID: ${item.user_id})")
                        navController.navigate(ProfileScreenFlow.Other_Profile_Structure.route)
                    }
                }
            }

        )

        HorizontalDivider()
    }
}


@Composable
fun Edit_Profile(notchPadding: State<Dp>, navController: NavHostController) {


    val network = rememberNetworkStatus()

    LaunchedEffect(Unit) {
        println("USERNAME----${AppPreferences.get_User_Name()}")
        constants.Profile_ViewModel.save_new_name_edit_profile(AppPreferences.get_User_Name())
        constants.Profile_ViewModel.save_new_Bio_Content(AppPreferences.get_ProfileBio())
        constants.Profile_ViewModel.save_New_Realname(AppPreferences.get_Real_Name())
        PROFILE_IMAGE_URL.value = ""
    }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val user_name = constants.Profile_ViewModel.edit_profile_name.collectAsState()

    val real_Name = constants.Profile_ViewModel.edit_Profile_Realname.collectAsState()

    val bio_Content = constants.Profile_ViewModel.change_Bio_Content.collectAsState()

    val isLoading = constants.Profile_ViewModel.status_Update_Profile.collectAsState()

    val context = LocalContext.current

    val scope = rememberCoroutineScope()


    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    )
    { uri: Uri? ->

        uri ?: return@rememberLauncherForActivityResult

        constants.Profile_ViewModel.set_From_Profile_Pic_Update(true)

        scope.launch {
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
                    uri = uri,
                    thumbail = false,
                    profile = "profileview"
                ) { progress ->
                    // optional: update progress state
                }

                // ✅ update state after upload
                PROFILE_IMAGE_URL.value = result.url

            } catch (e: Exception) {
                GlobalSnackbar.show("Image upload failed")
                e.printStackTrace()
            }
        }
    }

//    val imagePickerLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.OpenDocument()
//    ) { uri: Uri? ->
//        // Only proceed if the user selected an image
//        constants.Profile_ViewModel.set_From_Profile_Pic_Update(true)
//        uri?.let {
//            val s3Uploader = S3Uploader(
//                bucket = constants.BUCKET_NAME,
//                cloudFront = constants.CLOUD_FRONT_URL,
//                accessId = constants.ACCESS_ID,
//                secretKey = constants.SECRET_KEY
//            )
//
//                val result = s3Uploader.uploadSingle(
//                    context = context,
//                    userId = AppPreferences.getUserId().toString(),
//                    uri = uri,
//                    false,
//                    ""
//                ) { progress ->
//
//                }
//
//            constants.PROFILE_IMAGE_URL.value = result.url
//
//            //GenertateLink(constants.activity, it, navController , onComplete = {})
//        }
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(newWhite)
        , verticalArrangement = Arrangement.Center
        , horizontalAlignment = Alignment.CenterHorizontally
    ){
        LazyColumn (
            modifier = Modifier
                .fillMaxWidth()
                .weight(9f)
                .background(newWhite)
                .padding(top = if (forTab()) 16.dp else notchPadding.value)
                .padding(horizontal = 16.dp)
            , verticalArrangement = Arrangement.spacedBy(8.dp)
            //, horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            item {
                Column  {
                    /*  Row(
                          modifier = Modifier
                              .fillMaxWidth(),
                          verticalAlignment = Alignment.CenterVertically,
                          horizontalArrangement = Arrangement.spacedBy(8.dp)
                      )
                      {
                          Backer(
                              modifier = Modifier, onBackClick = {
                                  constants.Profile_ViewModel.dismiss_Edit_Profile()
  //                                constants.Common_H_ViewModel.toggleshowBABars(true)
                                  constants.Profile_ViewModel.clear_All_BF_Handler()
  //                                constants.Profile_ViewModel.save_new_name_edit_profile("")
  //                                constants.Profile_ViewModel.save_new_Bio_Content("")
  //                                constants.Profile_ViewModel.save_New_Realname("")

                              }
                          )

                          Text(
                              "Edit Profile",
                              fontSize = constants.textUnit(24),
                              fontFamily = constants.fontFamily(0)
                              , color = newBlack
                          )
                      }*/

                    // profile imge
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = 16.dp)
                            .size(108.dp)
                        , contentAlignment = Alignment.Center
                    )
                    {

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(newLightBlue)
                        ) {
                            if (constants.URL_COMPLETED.isNotEmpty()) {
                                println("Profile image url --- ${constants.URL_COMPLETED} -- ${PROFILE_IMAGE_URL.value}")
                                SubcomposeAsyncImage(
                                    model = PROFILE_IMAGE_URL.value.ifEmpty { AppPreferences.get_ProfileImage() },
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    contentDescription = "",
                                    contentScale = ContentScale.FillBounds
                                )
                                {
                                    val state = painter.state
                                    if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {

                                        Box(modifier = Modifier.fillMaxSize() ,
                                            contentAlignment = Alignment.Center)
                                        {

                                            Text(
                                                text =  user_name.value.takeIf { it.isNotEmpty() }?.take(1)?.uppercase() ?: ""
                                                , fontSize = constants.textUnit(16)
                                            )
                                           // CircularProgressIndicator(modifier = Modifier.size(10.dp) ,)
                                        }
                                    } else {
                                        SubcomposeAsyncImageContent()
                                    }
                                }
                            }
                        }

                        AsyncImage(
                            model = R.drawable.changeprofilerento, "", modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .size(28.dp)
                                .noRippleClickable {
                                    imagePickerLauncher.launch(arrayOf("image/*"))
                                }
                        )
                    }



                    Spacer(modifier = Modifier.padding(8.dp))

                    // user name change

                    Text(
                        "User Name",
                        color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(0),
                        modifier = Modifier
                            .align(Alignment.Start)
                    )

                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, newGray, RoundedCornerShape(8.dp))
                        )
                        {
                            TextField(
                                value = user_name.value,
                                onValueChange = {
                                    if (it.length < 20) {
                                        constants.Profile_ViewModel.save_new_name_edit_profile(it)
                                    }
                                }, modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(8f)
                                , placeholder = {
                                    Text(
                                        "Enter your user name",
                                        color = newBlack,
                                        fontSize = constants.textUnit(14),
                                        fontFamily = constants.fontFamily(1),
                                        modifier = Modifier
                                        //.align(Alignment.Start)
                                    )
                                }, singleLine = true,
                                enabled = !isLoading.value,
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    unfocusedTextColor = newBlack,
                                    focusedTextColor = newBlack,
                                    disabledContainerColor = Color.White
                                )
                            )

                            VerticalDivider(color = newGray)

                            println("NAMESS -- ${AppPreferences.get_Real_Name()} --- ${user_name.value}")
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(2f)
                                    .background(
                                        if (AppPreferences.get_User_Name() != user_name.value) {
                                            if (!user_name.value.isNullOrEmpty()) {
                                                newBlue
                                            } else
                                                Color(0xffF4F4F4)
                                        } else Color(0xffF4F4F4)
                                    )
                                    .noRippleClickable {
                                        if (AppPreferences.get_User_Name() != user_name.value) {
                                            constants.API_Vm.username_update(
                                                user_id = AppPreferences.getUserId(),
                                                username = user_name.value,
                                            ) { apiResultHandling ->
                                                when (apiResultHandling) {
                                                    is API_Result_Handling.Error -> {
                                                        // AppPreferences.save_User_Name(constants.Profile_ViewModel.get_new_username())
                                                        focusManager.clearFocus()
                                                        keyboardController?.hide()
                                                        constants.Profile_ViewModel.save_new_name_edit_profile(
                                                            AppPreferences.get_User_Name()
                                                        )
                                                        GlobalSnackbar.show(
                                                            profileChangeErrorMessage.value
                                                        )
                                                        //errror
                                                        //constants.Profile_ViewModel.change_Update_profile(false)
                                                    }

                                                    is API_Result_Handling.NoData -> {
                                                        // no data
                                                    }

                                                    is API_Result_Handling.Loading -> {
                                                        //loading
                                                        //  constants.Profile_ViewModel.change_Update_profile(true)
                                                    }

                                                    is API_Result_Handling.Success -> {
                                                        constants.Profile_ViewModel.enable_Edit_Profile()
//                                                    constants.Profile_ViewModel.change_Update_profile(false)
//                                                    constants.Profile_ViewModel.set_From_Profile_Pic_Update(false)

                                                        // AppPreferences.save_ProfileImage(constants.PROFILE_IMAGE_URL.value)
                                                        focusManager.clearFocus()
                                                        keyboardController?.hide()
                                                        AppPreferences.save_User_Name(constants.Profile_ViewModel.get_new_username())
                                                        GlobalSnackbar.show("UserName Successfully Updated")

                                                        //AppPreferences.save_Real_Name(constants.Profile_ViewModel.get_New_Realname())

                                                        println("API STORING PROFILE IMAGE -- ${AppPreferences.get_ProfileImage()} -- ${AppPreferences.get_Real_Name()}")

                                                        //success
                                                    }

                                                    is API_Result_Handling.Deactivated -> {
                                                        //resultCallback(5)
                                                    }
                                                }
                                            }
                                        }
                                        else {
                                            GlobalSnackbar.show("Changes have to be made for updating")
                                        }
                                    }
                                , contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "Change",
                                    color = if (AppPreferences.get_User_Name() != user_name.value)  Color.White else newDarkGray,
                                    fontSize = constants.textUnit(12),
                                    fontFamily = constants.fontFamily(2)
                                )
                            }
                        }

                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        )
                        {
                            Text(
                                "\u2736",
                                color = Color.Red,
                                fontSize = constants.textUnit(12),
                                fontFamily = constants.fontFamily(2),
                                modifier = Modifier
                                //.align(Alignment.Start)
                            )

                            Text(
                                "You can change username only 30 days once",
                                color = newBlack,
                                fontSize = constants.textUnit(12),
                                fontFamily = constants.fontFamily(2),
                                modifier = Modifier
                                //.align(Alignment.Start)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                    // real name

                    Text(
                        "Name",
                        color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(0),
                        modifier = Modifier
                            .align(Alignment.Start)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .border(1.dp, newGray, RoundedCornerShape(8.dp)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    )
                    {

                        TextField(
                            value = real_Name.value,
                            onValueChange = {
                                if (it.length < 20) {
                                    constants.Profile_ViewModel.save_New_Realname(it)
                                }
                            },
                            placeholder = {
                                Text(
                                    "Enter your name",
                                    color = newBlack,
                                    fontSize = constants.textUnit(14),
                                    fontFamily = constants.fontFamily(1),
                                    modifier = Modifier
                                    //.align(Alignment.Start)
                                )
                            },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(8f),
                            enabled = !isLoading.value,
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                unfocusedTextColor = newBlack,
                                focusedTextColor = newBlack,
                                disabledContainerColor = Color.White
                            )
                        )
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                    // bio changes

                    Text(
                        "Bio",
                        color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(0),
                        modifier = Modifier
                            .align(Alignment.Start)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .border(1.dp, newGray, RoundedCornerShape(8.dp))
                        //.padding(horizontal = 8.dp , vertical = 8.dp)
                        , contentAlignment = Alignment.TopStart
                    )
                    {
                        TextField(
                            value = bio_Content.value,
                            onValueChange = {
                                if (it.length <= 150) {
                                    constants.Profile_ViewModel.save_new_Bio_Content(it)
                                }
                            },
                            placeholder = {
                                Text(
                                    "Type your bio...",
                                    color = newBlack,
                                    fontSize = constants.textUnit(12),
                                    fontFamily = constants.fontFamily(2),
                                    modifier = Modifier
                                )
                            },
                            textStyle = TextStyle(
                                color = newBlack,
                                fontSize = constants.textUnit(12),
                                fontFamily = constants.fontFamily(2)
                            ),
                            enabled = !isLoading.value,
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 80.dp, max = 150.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                unfocusedTextColor = newBlack,
                                focusedTextColor = newBlack,
                                disabledContainerColor = Color.White
                            )
                        )
                    }
                }
            }

        }

        Static_Bottom(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .fillMaxHeight(.7f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(newBlue)
                    .clickable(enabled = !isLoading.value) {
                        if (network.value == NetworkStatus.Online) {
                            if (!user_name.value.isNullOrEmpty()) {
                                if (AppPreferences.get_User_Name() == user_name.value) {
                                    if (!real_Name.value.isNullOrEmpty()) {
                                        if (!bio_Content.value.isNullOrEmpty()) {
                                            update_User_Profile_API_Call()
                                        } else
                                            GlobalSnackbar.show("Enter your bio")
                                    } else
                                        GlobalSnackbar.show("Enter your name")
                                } else
                                    GlobalSnackbar.show("Save your username to continue")
                            } else
                                GlobalSnackbar.show("Enter your user name")

                        } else {
                            GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
                        }
                    }
                , contentAlignment = Alignment.Center
            ){
                if (isLoading.value){
                    CircularProgressIndicator(color = Color.White , modifier = Modifier.size(20.dp))
                }
                else {
                    Text(
                        "Save Changes",
                        color = Color.White,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(0)
                    )
                }
            }
        }
    }
}




//////// iswhich - Main State Following or Followers tab
/////// followers State -  followback 0, following 1

@Composable
fun Own_Profile_FF_Buttons(
    isWhich_FF : Int
    , Followers_State : Int
    , onUnfollow :() -> Unit
    , onFollowBack : () -> Unit,
    onFollowing :() -> Unit
    , onDelete : () -> Unit
)
{
    println("iswhich -- ${isWhich_FF}")
    if (isWhich_FF == 1){
        /// for Following
        Row(
            modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(4.dp))
                .background(Color.White)
                .border(1.dp, newBlack, RoundedCornerShape(4.dp))
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .noRippleClickable {
                    onUnfollow()
                }
            , verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        )
        {
//            SubcomposeAsyncImage(
//                model = R.drawable.profile_following_users_icon, "", modifier = Modifier
//                    .size(14.dp)
//            )
//
//            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "Unfollow",
                color = newBlack,
                fontSize = constants.textUnit(12),
                fontFamily = constants.fontFamily(2)
            )
        }

    }
    else {
        /// for followers

        Row (
            modifier = Modifier
            , verticalAlignment = Alignment.CenterVertically
            , horizontalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(RoundedCornerShape(4.dp))
                    .background(if (Followers_State == 0) Color.White else Color(0xffE8E8E8))
                    .then(
                        if (Followers_State == 0) {
                            Modifier.border(
                                1.dp,
                                Brush.linearGradient(newPurpleGradientBorder),
                                RoundedCornerShape(4.dp)
                            )
                        } else {
                            Modifier
                        }
                    )
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .noRippleClickable {
                        if (Followers_State == 0) {
                            onFollowBack()
                        } else {
                            onFollowing()
                        }

                    }
                , verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            )
            {
//                SubcomposeAsyncImage(
//                    model = R.drawable.profile_following_users_icon,
//                    "",
//                    modifier = Modifier
//                        .size(14.dp)
//                    , colorFilter = ColorFilter.tint(if (Followers_State == 0) newBlue else newBlack)
//                )
//
//                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = if (Followers_State == 0)"Follow back" else "Following",
                    color = if (Followers_State == 0) newBlue else newBlack,
                    fontSize = constants.textUnit(12),
                    fontFamily = constants.fontFamily(2)
                )
            }

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White)
                    .border(1.dp, newGray, RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .noRippleClickable {
                        onDelete()
                    }
                , contentAlignment = Alignment.Center
            ){
                AsyncImage(
                    model = R.drawable.deletefllwersrento
                    ,""
                    //, colorFilter = ColorFilter.tint(Color.Red)
                    , modifier = Modifier
                        .size(16.dp)
                )
            }
        }

    }
}




object ClickGuard {
    private var lastClickTime = 0L
    private const val DEBOUNCE_TIME = 500L

    fun canClick(): Boolean {
        val now = System.currentTimeMillis()
        return if (now - lastClickTime > DEBOUNCE_TIME) {
            lastClickTime = now
            true
        } else false
    }
}




@Composable
fun Profile_Following_Item_Structure_Old(
    isWhich: Int,
    profile_Type: Int,
    item: Get_Profile_FF_List_Data,
    navController: NavHostController,
    new_Stack: Profile_Handle_Back?,
    is_Search_Enabled: State<Boolean>,
    search_Text: State<String>
) {

    val network = rememberNetworkStatus()

    val new = constants.Profile_ViewModel.profile_BF_Handler.collectAsState()

    Column {
        ListItem(
            leadingContent = {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(newGray), contentAlignment = Alignment.Center
                ) {

                    SubcomposeAsyncImage(
                        model = item?.profile_image ?: "",
                        modifier = Modifier
                            .fillMaxSize()
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
                                //.padding(8.dp)
                                , contentAlignment = Alignment.Center
                            ){
                                Text(
                                    text = item?.username.takeIf { it?.isNotEmpty() == true }?.take(1)?.uppercase() ?: ""
                                )
                            }
                        } else {
                            SubcomposeAsyncImageContent()
                        }
                    }
                }
            },
            headlineContent = {
                Text(
                    text = item.username,
                    color = newBlack,
                    fontSize = constants.textUnit(16),
                    fontFamily = constants.fontFamily(1)
                )
            },
            supportingContent = {
                Text(
                    text = item.name,
                    color = newBlack,
                    fontSize = constants.textUnit(12),
                    fontFamily = constants.fontFamily(2)
                )
            },
            trailingContent = {
                // Skip if viewing own user in the list
                if (item.user_id == AppPreferences.getUserId()) {
                    // Don't show any buttons for yourself
                } else {
                    // ✅ FIXED: Determine if viewing own profile or other's profile
                    val isViewingOwnProfile = (new_Stack?.other_UserId == 0
                            || new_Stack?.other_UserId == null
                            || new_Stack?.other_UserId == AppPreferences.getUserId())

                    println("👤 Viewing profile: ${if (isViewingOwnProfile) "OWN" else "OTHER"}")
                    println("📊 Item: user_id=${item.user_id}, im_followed=${item.im_followed}, is_followed=${item.is_followed}")

                    if (isViewingOwnProfile) {
                        // ========================================
                        // VIEWING YOUR OWN PROFILE
                        // ========================================

                        // For FOLLOWERS tab: Determine button state
                        // - If they follow you but you don't follow them: Show "Follow back"
                        // - If you already follow them: Show "Following"
                        val followers_State = if (item.is_followed == 1 && item.im_followed == 0) {
                            0  // They follow you, you don't follow them → "Follow back"
                        } else {
                            1  // You follow them → "Following"
                        }

                        println("🔵 Own Profile - Followers State: $followers_State")

                        Own_Profile_FF_Buttons(
                            isWhich_FF = isWhich,
                            Followers_State = followers_State,
                            onUnfollow = {
                                constants.Profile_ViewModel.put_follow_unfollow_Status(2)
                                constants.Profile_ViewModel.put_Following_Id(item.user_id)
                                constants.Profile_ViewModel.add_Unflw_Flw_Content_Pup(
                                    id = item.user_id,
                                    username = item.username,
                                    profilePic = item.profile_image,
                                )
                                constants.Profile_ViewModel.setunfollowClicker()
                            },
                            onFollowBack = {
                                constants.Profile_ViewModel.put_follow_unfollow_Status(1)
                                constants.Profile_ViewModel.put_Following_Id(item.user_id)
                                constants.Profile_ViewModel.add_Unflw_Flw_Content_Pup(
                                    id = item.user_id,
                                    username = item.username,
                                    profilePic = item.profile_image,
                                )
                                if (network.value == NetworkStatus.Online) {
                                    follow_Unfollow_Delete_API_Call() { result ->
                                        when (result) {
                                            0 -> {
                                                Get_User_Profile_API_Call() { result2 ->
                                                    when (result2) {
                                                        0 -> {
                                                            constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
                                                                userId = new_Stack?.current_UsedId ?: 0,
                                                                newFollowers = constants.Profile_ViewModel.get_Followers_Count_BGAPIC(),
                                                                newFollowing = constants.Profile_ViewModel.get_Following_Count_BGAPIC()
                                                            )
                                                        }
                                                        1 -> { }
                                                        2 -> { }
                                                    }
                                                }

                                                if (!is_Search_Enabled.value) {
                                                    constants.Profile_ViewModel.updateImFollowedByUserId_FF(item.user_id)
                                                } else {
                                                    constants.Profile_ViewModel.updateImFollowedByUserId_Search_FF(item.user_id)
                                                }
                                            }
                                            1 -> { toast("Try Again later") }
                                            2 -> { }
                                        }
                                    }
                                } else {
                                    GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
                                }
                            },
                            onFollowing = {
                                constants.Profile_ViewModel.put_follow_unfollow_Status(2)
                                constants.Profile_ViewModel.put_Following_Id(item.user_id)
                                constants.Profile_ViewModel.add_Unflw_Flw_Content_Pup(
                                    id = item.user_id,
                                    username = item.username,
                                    profilePic = item.profile_image,
                                )
                                constants.Profile_ViewModel.setunfollowClicker()
                            },
                            onDelete = {
                                constants.Profile_ViewModel.put_follow_unfollow_Status(3)
                                constants.Profile_ViewModel.put_Following_Id(item.user_id)
                                constants.Profile_ViewModel.add_Unflw_Flw_Content_Pup(
                                    id = item.user_id,
                                    username = item.username,
                                    profilePic = item.profile_image,
                                )
                                constants.Profile_ViewModel.delete_Follow_Request()
                            }
                        )
                    } else {
                        // ========================================
                        // VIEWING SOMEONE ELSE'S PROFILE
                        // ========================================

                        // For FOLLOWERS tab:
                        // 0 = You don't follow them
                        // 1 = They follow you but you don't follow them → "Follow back"
                        // 2 = You follow them → "Following"
                        val followers_State = when {
                            item.im_followed == 0 && item.is_followed == 1 -> 1  // Follow back
                            item.im_followed == 1 && item.is_followed == 1 -> 2  // Following
                            item.im_followed == 0 && item.is_followed == 0 -> 0  // Follow
                            else -> 2
                        }

                        // For FOLLOWING tab:
                        // 0 = You don't follow them → "Follow"
                        // 1 = They follow you but you don't follow them → "Follow back"
                        // 2 = You follow them → "Following"
                        val following_State = when {
                            item.im_followed == 0 && item.is_followed == 1 -> 1  // Follow back
                            item.im_followed == 1 && item.is_followed == 1 -> 2  // Following
                            else -> 0  // Follow
                        }

                        println("🟢 Other Profile - Following State: $following_State, Followers State: $followers_State")

                        Others_Profile_FF_Buttons(
                            isWhich_FF = isWhich,
                            Following_State = following_State,
                            Followers_State = followers_State,
                            onFollow = {
                                constants.Profile_ViewModel.put_follow_unfollow_Status(1)
                                constants.Profile_ViewModel.put_Following_Id(item.user_id)
                                constants.Profile_ViewModel.add_Unflw_Flw_Content_Pup(
                                    id = item.user_id,
                                    username = item.username,
                                    profilePic = item.profile_image,
                                )

                                if (network.value == NetworkStatus.Online) {
                                    follow_Unfollow_Delete_API_Call() { result ->
                                        when (result) {
                                            0 -> {
                                                Get_User_Profile_API_Call() { result2 ->
                                                    when (result2) {
                                                        0 -> {
                                                            constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
                                                                userId = new_Stack?.current_UsedId ?: 0,
                                                                newFollowers = constants.Profile_ViewModel.get_Followers_Count_BGAPIC(),
                                                                newFollowing = constants.Profile_ViewModel.get_Following_Count_BGAPIC()
                                                            )
                                                        }
                                                        1 -> { }
                                                        2 -> { }
                                                    }
                                                }

                                                if (!is_Search_Enabled.value) {
                                                    constants.Profile_ViewModel.updateImFollowedByUserId_FF(item.user_id)
                                                } else {
                                                    constants.Profile_ViewModel.updateImFollowedByUserId_Search_FF(item.user_id)
                                                }
                                            }
                                            1 -> { toast("Try Again later") }
                                            2 -> { }
                                        }
                                    }
                                } else {
                                    GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
                                }
                            },
                            onFollowBack = {
                                // Same as onFollow logic
                                constants.Profile_ViewModel.put_follow_unfollow_Status(1)
                                constants.Profile_ViewModel.put_Following_Id(item.user_id)
                                constants.Profile_ViewModel.add_Unflw_Flw_Content_Pup(
                                    id = item.user_id,
                                    username = item.username,
                                    profilePic = item.profile_image,
                                )

                                if (network.value == NetworkStatus.Online) {
                                    follow_Unfollow_Delete_API_Call() { result ->
                                        when (result) {
                                            0 -> {
                                                Get_User_Profile_API_Call() { result2 ->
                                                    when (result2) {
                                                        0 -> {
                                                            constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
                                                                userId = new_Stack?.current_UsedId ?: 0,
                                                                newFollowers = constants.Profile_ViewModel.get_Followers_Count_BGAPIC(),
                                                                newFollowing = constants.Profile_ViewModel.get_Following_Count_BGAPIC()
                                                            )
                                                        }
                                                        1 -> { }
                                                        2 -> { }
                                                    }
                                                }

                                                if (!is_Search_Enabled.value) {
                                                    constants.Profile_ViewModel.updateImFollowedByUserId_FF(item.user_id)
                                                } else {
                                                    constants.Profile_ViewModel.updateImFollowedByUserId_Search_FF(item.user_id)
                                                }
                                            }
                                            1 -> { toast("Try Again later") }
                                            2 -> { }
                                        }
                                    }
                                } else {
                                    GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
                                }
                            },
                            onFollowing = {
                                constants.Profile_ViewModel.put_follow_unfollow_Status(2)
                                constants.Profile_ViewModel.put_Following_Id(item.user_id)
                                constants.Profile_ViewModel.add_Unflw_Flw_Content_Pup(
                                    id = item.user_id,
                                    username = item.username,
                                    profilePic = item.profile_image,
                                )
                                constants.Profile_ViewModel.setunfollowClicker()
                            }
                        )
                    }
                }
            }
            /*  trailingContent = {
                  if (item.user_id == AppPreferences.getUserId()) {


                  }
                  else {
                      if (profile_Type == 0) {
                          println("THIS ONE HERE3333 --- ${item.im_followed} -${isWhich}- ${item.is_followed}")
  //                        val followers_State = remember(item.user_id, item.im_followed, item.is_followed, isWhich) {
  //                            if (item.im_followed == 0 && item.is_followed == 1) 0 else 1
  //                        }

                          val followers_State =
  //                            when {
  //                            // If on FOLLOWING tab (isWhich = 1)
  //                            isWhich == 1 -> {
  //                                // Always show "Following" state (value = 1)
  //                                1
  //                            }
  //                            // If on FOLLOWERS tab (isWhich = 0)
  //                            else -> {
                                  // Show "Follow back" (0) if they follow you but you don't follow them
                                  // Show "Following" (1) if you already follow them
                                  if (item.is_followed == 1 && item.im_followed == 0) 0 else 1
  //                            }
  //                        }

                          println("THIS ONE HERE --${followers_State}- ${item.im_followed} -- ${item.is_followed}")
                          Own_Profile_FF_Buttons(
                              isWhich_FF = isWhich,
                              Followers_State = followers_State,
                              onUnfollow = {
                                  constants.Profile_ViewModel.put_follow_unfollow_Status(2)
                                  constants.Profile_ViewModel.put_Following_Id(item.user_id)
                                  constants.Profile_ViewModel.add_Unflw_Flw_Content_Pup(
                                      id = item.user_id,
                                      username = item.username,
                                      profilePic = item.profile_image,
                                  )
                                  constants.Profile_ViewModel.setunfollowClicker()
                              },
                              onFollowBack = {
                                  constants.Profile_ViewModel.put_follow_unfollow_Status(1)
                                  constants.Profile_ViewModel.put_Following_Id(item.user_id)
                                  constants.Profile_ViewModel.add_Unflw_Flw_Content_Pup(
                                      id = item.user_id,
                                      username = item.username,
                                      profilePic = item.profile_image,
                                  )
                                  if (network.value == NetworkStatus.Online){
                                      follow_Unfollow_Delete_API_Call() { result ->
                                          when (result) {
                                              0 -> {

                                                  Get_User_Profile_API_Call() { result2 ->
                                                      when (result2) {
                                                          0 -> {

                                                              constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
                                                                  userId = new_Stack?.current_UsedId ?: 0,
                                                                  newFollowers = constants.Profile_ViewModel.get_Followers_Count_BGAPIC(),
                                                                  newFollowing = constants.Profile_ViewModel.get_Following_Count_BGAPIC()
                                                              )

                                                          }

                                                          1 -> {
                                                              // toast("Try Again later")
                                                          }

                                                          2 -> {

                                                          }
                                                      }
                                                  }
                                                  println("Success")

                                                  if (!is_Search_Enabled.value){
                                                      constants.Profile_ViewModel.updateImFollowedByUserId_FF(
                                                          item.user_id
                                                      )
                                                  }
                                                  else {
                                                      constants.Profile_ViewModel.updateImFollowedByUserId_Search_FF(
                                                          item.user_id
                                                      )
                                                  }

                                                  // constants.Profile_ViewModel.setunfollowClicker()
                                              }

                                              1 -> {
                                                  toast("Try Again later")
                                              }

                                              2 -> {

                                              }
                                          }
                                      }
                                  }
                                  else {
                                      GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
                                  }
                              },
                              onFollowing = {
                                  constants.Profile_ViewModel.put_follow_unfollow_Status(2)
                                  constants.Profile_ViewModel.put_Following_Id(item.user_id)
                                  constants.Profile_ViewModel.add_Unflw_Flw_Content_Pup(
                                      id = item.user_id,
                                      username = item.username,
                                      profilePic = item.profile_image,
                                  )
                                  constants.Profile_ViewModel.setunfollowClicker()
                              },
                              onDelete = {
                                  constants.Profile_ViewModel.put_follow_unfollow_Status(3)
                                  constants.Profile_ViewModel.put_Following_Id(item.user_id)
                                  constants.Profile_ViewModel.add_Unflw_Flw_Content_Pup(
                                      id = item.user_id,
                                      username = item.username,
                                      profilePic = item.profile_image,
                                  )
                                  constants.Profile_ViewModel.delete_Follow_Request()
                              }
                          )

                      }
                      else {
                          println("THIS TWO HERE")

                          val followers_State = if (item.im_followed == 0 && item.is_followed == 1) 1
                          else if (item.im_followed == 1 && item.is_followed == 1) 2 else if (item.im_followed == 0 && item.is_followed == 0) 0 else 2

                          val following_State = if (item.im_followed == 0 && item.is_followed == 1) 1
                          else if (item.im_followed == 1 && item.is_followed == 1) 2 else 0

                          println("THIS TWO HERE  ${following_State} ${following_State}")

                          Others_Profile_FF_Buttons(
                              isWhich_FF = isWhich,
                              Following_State = following_State,
                              Followers_State = followers_State,
                              onFollow = {
                                  constants.Profile_ViewModel.put_follow_unfollow_Status(1)
                                  constants.Profile_ViewModel.put_Following_Id(item.user_id)
                                  constants.Profile_ViewModel.add_Unflw_Flw_Content_Pup(
                                      id = item.user_id,
                                      username = item.username,
                                      profilePic = item.profile_image,
                                  )

                                  if (network.value == NetworkStatus.Online){
                                      follow_Unfollow_Delete_API_Call() { result ->
                                          when (result) {
                                              0 -> {

                                                  Get_User_Profile_API_Call() { result2 ->
                                                      when (result2) {
                                                          0 -> {

                                                              constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
                                                                  userId = new_Stack?.current_UsedId ?: 0,
                                                                  newFollowers = constants.Profile_ViewModel.get_Followers_Count_BGAPIC(),
                                                                  newFollowing = constants.Profile_ViewModel.get_Following_Count_BGAPIC()
                                                              )

                                                          }

                                                          1 -> {
                                                              // toast("Try Again later")
                                                          }

                                                          2 -> {

                                                          }
                                                      }
                                                  }
                                                  println("Success")

                                                  if (!is_Search_Enabled.value){
                                                      constants.Profile_ViewModel.updateImFollowedByUserId_FF(
                                                          item.user_id
                                                      )
                                                  }
                                                  else {
                                                      constants.Profile_ViewModel.updateImFollowedByUserId_Search_FF(
                                                          item.user_id
                                                      )
                                                  }

                                                  // constants.Profile_ViewModel.setunfollowClicker()
                                              }

                                              1 -> {
                                                  toast("Try Again later")
                                              }

                                              2 -> {

                                              }
                                          }
                                      }
                                  }
                                  else {
                                      GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
                                  }
                              },
                              onFollowBack = {
                                  constants.Profile_ViewModel.put_follow_unfollow_Status(1)
                                  constants.Profile_ViewModel.put_Following_Id(item.user_id)
                                  constants.Profile_ViewModel.add_Unflw_Flw_Content_Pup(
                                      id = item.user_id,
                                      username = item.username,
                                      profilePic = item.profile_image,
                                  )

                                  if (network.value == NetworkStatus.Online){
                                      follow_Unfollow_Delete_API_Call() { result ->
                                          when (result) {
                                              0 -> {

                                                  Get_User_Profile_API_Call() { result2 ->
                                                      when (result2) {
                                                          0 -> {

                                                              constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
                                                                  userId = new_Stack?.current_UsedId ?: 0,
                                                                  newFollowers = constants.Profile_ViewModel.get_Followers_Count_BGAPIC(),
                                                                  newFollowing = constants.Profile_ViewModel.get_Following_Count_BGAPIC()
                                                              )

                                                          }

                                                          1 -> {
                                                              // toast("Try Again later")
                                                          }

                                                          2 -> {

                                                          }
                                                      }
                                                  }
                                                  println("Success")

                                                  if (!is_Search_Enabled.value) {
                                                      constants.Profile_ViewModel.updateImFollowedByUserId_FF(
                                                          item.user_id
                                                      )
                                                  }
                                                  else {
                                                      constants.Profile_ViewModel.updateImFollowedByUserId_Search_FF(
                                                          item.user_id
                                                      )
                                                  }
                                                  // constants.Profile_ViewModel.setunfollowClicker()
                                              }

                                              1 -> {
                                                  toast("Try Again later")
                                              }

                                              2 -> {

                                              }
                                          }
                                      }
                                  }
                                  else {
                                      GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
                                  }
                              },
                              onFollowing = {
                                  constants.Profile_ViewModel.put_follow_unfollow_Status(2)
                                  constants.Profile_ViewModel.put_Following_Id(item.user_id)
                                  constants.Profile_ViewModel.add_Unflw_Flw_Content_Pup(
                                      id = item.user_id,
                                      username = item.username,
                                      profilePic = item.profile_image,
                                  )
                                  constants.Profile_ViewModel.setunfollowClicker()
                              }
                          )
                      }

                  }

              }*/
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
            )
            , modifier = Modifier
                .noRippleClickable{
//                    is_Search_Enabled.value = false
//                    constants.API_Vm.isLoading_FF = false
//                    constants.API_Vm.totalPages_FF = 1
//                    search_Text.value = ""
                    //constants.Profile_ViewModel.setSelectedUser(item)]
                    constants.Profile_ViewModel.add_Selected_User_Name(
                        item.username ?: "Profile"
                    )

                    //new flowwewwwwwww
                    constants.Profile_ViewModel.add_BF_Handler(Profile_Handle_Back(
                        current_UsedId = AppPreferences.getUserId(),
                        other_UserId = item.user_id,
                        ff_User_Name = item.username ,
                        ff_Fw_Count = item.followers_count,
                        ff_Fg_Count = item.following_count,
                        // is_Search_Enabled = is_Search_Enabled.value,
                        // search_Text = search_Text.value
                    ))

                    println("ITEM PROFILE STRUCTURE __ ${is_Search_Enabled.value} -- ${constants.Profile_ViewModel.profile_BF_Handler.value}")

                    println("GIVEN OTHER USER ID -- ${constants.Profile_ViewModel.get_Other_User_Id()}")

                    constants.Profile_ViewModel.addProfile(item.user_id)
                    constants.Profile_ViewModel.add_Selected_Profile_Id(id = item.user_id)

                    constants.Profile_ViewModel.clear_SearchList_FF()

                    constants.API_Vm.totalPages_Profile_Posts = 1
//                    constants.Profile_ViewModel.clear_Search_Text_FF()


                    navController.navigate(ProfileScreenFlow.Other_Profile_Structure.route)
                }
        )

        HorizontalDivider()
    }
}



@Composable
fun Profile_FF_Structure_Followings(navController: NavHostController, viewModel: Common_H_ViewModel) {

    val notchPadding = rememberNotchHeightDp()

    constants.Common_H_ViewModel.toggleshowBABars(false)
    val BA_Bar_Listener = constants.Common_H_ViewModel.showBABars.collectAsState()
    LaunchedEffect (BA_Bar_Listener.value){

        constants.Common_H_ViewModel.toggleshowBABars(BA_Bar_Listener.value)

    }


    // val network = remember { mutableStateOf(isConnected.value) }

    BackHandler {  }


    val profile_selected_Index = constants.Profile_ViewModel.show_Tapped_FFs.collectAsState()

    val unfollow_PUP = constants.Profile_ViewModel.unFollowClick.collectAsState()

    val followRequest_Delete = constants.Profile_ViewModel.followRequestDelete.collectAsState()


    val users_Profiles_List = constants.Profile_ViewModel.get_User_FF_List.collectAsStateWithLifecycle()
    val users_Profiles_Search_List = constants.Profile_ViewModel.get_User_FF_Search_List.collectAsStateWithLifecycle()



    val tapped_Profiles_List = constants.Profile_ViewModel.tapped_Profile_List.collectAsState()

    val isLoading = constants.API_Vm.isLoading_FF
    //val errorMessage = constants.API_Vm.errorMessage_FF
    val currentPage = constants.API_Vm.currentPage_FF
    val totalPages = constants.API_Vm.totalPages_FF
    val listState = rememberLazyListState()



    println("WHY 2222 FFFFFFFFFFFFF -- ${constants.Profile_ViewModel.currentBFHandler.value} --- list full ${constants.Profile_ViewModel.profile_BF_Handler.value}" )


    /// showing user name count of followers/ followeing
    val new_Stack by constants.Profile_ViewModel.currentBFHandler.collectAsStateWithLifecycle()
    // constants.Profile_ViewModel.show_Current_BF_Handler()

    println("NEW STACK __ ${new_Stack}")


    var search_Text = constants.Profile_ViewModel.search_Text_FF.collectAsStateWithLifecycle()
    var search_Text_Handler = constants.Profile_ViewModel.search_Text_FF_Handler.collectAsStateWithLifecycle()
    var is_Search_Enabled = constants.Profile_ViewModel.is_Search_Enabled.collectAsStateWithLifecycle()
    var is_Search_Enabled_Handler = constants.Profile_ViewModel.is_Search_Enabled_Handler.collectAsStateWithLifecycle()


    DisposableEffect(Unit) {
        constants.Profile_ViewModel.add_Search_Text_FF(search_Text_Handler.value)
        constants.Profile_ViewModel.add_Search_State(is_Search_Enabled_Handler.value)
        onDispose {  }
    }




    println("IS ENABLE OR NOT OUTSIDE DISPOSE __ ${is_Search_Enabled.value}  -- ${search_Text.value}")

    LaunchedEffect(users_Profiles_List.value) {
        println("List updated: size=${users_Profiles_List.value.size}")
    }


    LaunchedEffect(new_Stack) {
        println("🔥 New stack updated = $new_Stack")
    }



    DisposableEffect(new_Stack?.selected_Tab, is_Search_Enabled, search_Text.value) {

        if (is_Search_Enabled.value ) {
            constants.API_Vm.load_Search_FF(
                user_id = AppPreferences.getUserId(),
                others_id = if (new_Stack?.other_UserId == 0 || new_Stack?.other_UserId == null) "" else new_Stack?.other_UserId.toString(),
                status = if (new_Stack?.selected_Tab == null) 2 else (new_Stack?.selected_Tab
                    ?: 0) + 1,
                search = search_Text.value,
                page = 0
            )

        }
        else {
            if (search_Text.value.isEmpty()) {
                constants.API_Vm.load_Profile_FF_List(
                    user_id = AppPreferences.getUserId(),
                    others_id = if (new_Stack?.other_UserId == 0 || new_Stack?.other_UserId == null) "" else new_Stack?.other_UserId.toString(),
                    status = if (new_Stack?.selected_Tab == null) 2 else (new_Stack?.selected_Tab
                        ?: 0) + 1,
                    page = 1
                )
            }
        }

        onDispose {
            println("Profile_FF_Structure disposed")
        }
    }


    // Detect when near end of list
    LaunchedEffect(listState, currentPage, isLoading, totalPages) {
        if (!is_Search_Enabled.value) {
            println("WHEN SEARCHED HITTING")
            snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                .collect { lastVisibleItemIndex ->
                    val totalItems = listState.layoutInfo.totalItemsCount
                    val loadMoreThreshold = 2// 👈 trigger when 4 items from the end

                    if (
                        lastVisibleItemIndex != null &&
                        totalItems > 0 &&
                        lastVisibleItemIndex >= totalItems - loadMoreThreshold &&
                        !isLoading &&
                        currentPage < totalPages
                    ) {
                        println("CURRENT PAGE - ${currentPage}")
                        //constants.API_Vm.loadCategories(currentPage + 1)
                        constants.API_Vm.load_Profile_FF_List(
                            user_id = AppPreferences.getUserId(),
                            others_id = if (new_Stack?.other_UserId == 0 || new_Stack?.other_UserId == null) "" else new_Stack?.other_UserId.toString()
                                ?: "",
                            status = (new_Stack?.selected_Tab ?: 0) + 1,
                            page = currentPage + 1
                        )

                    }
                }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = if (forTab()) 16.dp else notchPadding.value)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {

        //if (isConnected.value) {
        // Top Row: Back Button + Username
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
            , verticalAlignment = Alignment.CenterVertically
        )
        {
            Backer(
                modifier = Modifier,
                onBackClick = {

                    if (tapped_Profiles_List.value.profiles.isNotEmpty()) {
                        constants.Profile_ViewModel.removeLastProfile()
                        constants.Profile_ViewModel.remove_Tapped_FFs_last()
                        constants.Profile_ViewModel.unfollow_Dismiss()
                        constants.Profile_ViewModel.delete_Follow_Dismiss()
                    } else {
                        constants.Profile_ViewModel.add_Selected_Profile_Id(0)
                        constants.Profile_ViewModel.put_Other_User_Id(0)
                        constants.Common_H_ViewModel.toggleshowBABars(true)
                    }
                    constants.API_Vm.totalPages_Profile_Posts = 1
                    navController.navigateUp()
                }
            )


            Text(
                text = new_Stack?.ff_User_Name ?: "Unknown",
                color = newBlack,
                fontSize = constants.textUnit(24),
                fontFamily = constants.fontFamily(0)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (isLoading && currentPage == 1) {

            // ⏳ Loading UI Composable
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(8.7f)
                    .background(newWhite),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // LottiAnimation(2)
                CircularProgressIndicator()
            }
        }
        else {
            // Tabs Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color(0xffE8E8E8))
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            )
            {

                repeat(2) { index ->
                    Box(
                        modifier = Modifier
                            .fillMaxHeight(0.65f)
                            .weight(4f)
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (new_Stack?.selected_Tab ?: 0 == index) newBlack else Color.White)
                            .noRippleClickable {


                                constants.Profile_ViewModel.clear_Search_Text_FF()
                                constants.Profile_ViewModel.disable_Search()

//                                    search_Text.value = ""
//                                    is_Search_Enabled.value = false
                                println("1111111111111111111111111111111111111111111111111111111")

                                // if (is_Search_Enabled.value == false) {
                                if (index == 0) {
                                    constants.Profile_ViewModel.updateSelectedTab_BF_Handler(
                                        new_Stack?.id ?: 999,
                                        0,
                                        flw_count = users_Profiles_List.value[index]?.followers_count
                                            ?: 0,
                                        users_Profiles_List.value[index]?.following_count
                                            ?: 0,


                                        )
                                } else {
                                    constants.Profile_ViewModel.updateSelectedTab_BF_Handler(
                                        new_Stack?.id ?: 999,
                                        1,
                                        flw_count = users_Profiles_List.value[index]?.followers_count
                                            ?: 0,
                                        users_Profiles_List.value[index]?.following_count
                                            ?: 0,
                                    )
                                }
                                // if(!isLoading) {
                                constants.Profile_ViewModel.replace_Last_Tapped_FF(
                                    Profile_ViewModel.Tap_Flw_Flg_DC(
                                        id = users_Profiles_List.value[index]?.user_id ?: 0,
                                        tap_data = index,
                                        flg_Count = users_Profiles_List.value[index]?.following_count
                                            ?: 0,
                                        flw_Count = users_Profiles_List.value[index]?.followers_count
                                            ?: 0
                                    )
                                )


                                //}
                                //}


                            },
                        contentAlignment = Alignment.Center
                    )
                    {

                        Row(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(horizontal = 8.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = if (index == 0) new_Stack?.ff_Fw_Count.toString() else new_Stack?.ff_Fg_Count.toString(),
                                color = if (new_Stack?.selected_Tab ?: 0 == index) Color.White else newBlack,
                                fontSize = constants.textUnit(14),
                                fontFamily = constants.fontFamily(0)
                            )

                            Text(
                                text = if (index == 0) "Followers" else "Following",
                                color = if (new_Stack?.selected_Tab ?: 0 == index) Color.White else newBlack,
                                fontSize = constants.textUnit(14),
                                fontFamily = constants.fontFamily(0)
                            )
                        }
                    }
                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            // Search Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .border(1.dp, newBlack, RoundedCornerShape(8.dp))
                // .padding(horizontal = 8.dp)
                , verticalAlignment = Alignment.CenterVertically,
                //horizontalArrangement = Arrangement.spacedBy(8.dp)
            )
            {
                TextField(
                    value = search_Text.value,
                    onValueChange = {
                        constants.Profile_ViewModel.add_Search_Text_FF(it)
                        if (it.length >= 3 && constants.Profile_ViewModel.get_Search_Text_FF().length  >= 3){
                            constants.Profile_ViewModel.update_FF_BF_Search_State_Text_ByUserId(
                                userId = if (new_Stack?.other_UserId == 0 || new_Stack?.other_UserId == null) AppPreferences.getUserId() else new_Stack?.other_UserId ?: 0,
                                state = true,
                                text = it
                            )
                            constants.Profile_ViewModel.enable_Search()
                        }
                        else if(constants.Profile_ViewModel.get_Search_Text_FF().isEmpty()){
                            constants.Profile_ViewModel.update_FF_BF_Search_State_Text_ByUserId(
                                userId = if (new_Stack?.other_UserId == 0 || new_Stack?.other_UserId == null) AppPreferences.getUserId() else new_Stack?.other_UserId ?: 0,
                                state = false,
                                text = search_Text.value
                            )
                            constants.Profile_ViewModel.disable_Search()
                        }
                    },
                    placeholder = {
                        Text(
                            text = "search users by name..",
                            color = newBlack,
                            fontSize = constants.textUnit(12),
                            fontFamily = constants.fontFamily(2)
                        )
                    },
                    leadingIcon = {
                        AsyncImage(
                            model = R.drawable.search_icon,
                            contentDescription = "",
                            modifier = Modifier.size(12.dp)
                        )
                    },
                    trailingIcon = {
                        AsyncImage(
                            model = R.drawable.close,
                            contentDescription = "",
                            modifier = Modifier
                                .size(12.dp)
                                .noRippleClickable {
                                    constants.API_Vm.isLoading_FF = false
                                    constants.API_Vm.totalPages_FF = 1
                                    constants.Profile_ViewModel.clear_Search_Text_FF()
                                    constants.Profile_ViewModel.disable_Search()

                                }
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedTextColor = newBlack,
                        unfocusedTextColor = newBlack,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),

                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main content area

            AnimatedContent(
                targetState = profile_selected_Index.value?.tap_data ?: 0,
                transitionSpec = {
                    when (targetState) {
                        0 -> slideInHorizontally { -it } togetherWith slideOutHorizontally { it }
                        else -> slideInHorizontally { it } togetherWith slideOutHorizontally { -it }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(8f)
                //.background(newBlue)
            )
            { targetState ->

                println("TARGET STATE __ ${targetState}")
                if (!isLoading) {

                    val emptycheck = if (!is_Search_Enabled.value) users_Profiles_List.value else users_Profiles_Search_List.value

                    if (emptycheck.isEmpty()){
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                            , contentAlignment = Alignment.Center
                        ){
                            Text("NO DATA FOUND")
                        }
                    }
                    else {


                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            state = listState
                        )
                        {
                            itemsIndexed(
                                items = if (!is_Search_Enabled.value) {
                                    // if ()
                                    users_Profiles_List.value
                                }
                                else {
                                    users_Profiles_Search_List.value
                                },
                                key = { index, item -> "WHAT${item?.user_id}_${index}" }
                            )
                            { _, item ->
                                if (item != null) {
                                    val type = if ((new_Stack?.other_UserId == 0 || new_Stack?.other_UserId == null)
                                        && AppPreferences.getUserId() == 0 // <-- adjust if needed
                                    ) {
                                        1
                                    } else {
                                        0
                                    }

//                                    Profile_Following_Item_Structure(
//                                        isWhich = targetState,
//                                        profile_Type = type,
//                                        item = item,
//                                        navController = navController,
//                                        new_Stack,
//                                        is_Search_Enabled,
//                                        search_Text
//                                    )
                                }
                            }

                            if (isLoading && currentPage > 1) {
                                item {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(24.dp),
                                            color = newBlue,
                                            strokeWidth = 2.dp
                                        )
                                    }
                                }
                            }

                        }
                    }
                }
                else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                        , contentAlignment = Alignment.Center
                    ){
                        CircularProgressIndicator()
                    }
                }

            }
        }
    }


    val content = constants.Profile_ViewModel.get_Unflw_Flw_Content_Pup()

    Common_Popup(
        visible = unfollow_PUP.value,
        modifier = Modifier
            .background(Color(0xffF7F0DC)), content = {


            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {

                Spacer(modifier = Modifier.padding(2.dp))
               // constants.spacer(2)

                Text(
                    text = "Unfollow ${content.user_Name} ?",
                    color = newBlack,
                    fontSize = constants.textUnit(16),
                    fontFamily = constants.fontFamily(0)
                )

                //constants.spacer(2)

                Text(
                    text = "By unfollowing, you cannot able to view their property posts.",
                    color = newBlack,
                    fontSize = constants.textUnit(12),
                    fontFamily = constants.fontFamily(3),
                    textAlign = TextAlign.Center
                   , lineHeight = 24.sp
                    , modifier = Modifier.padding(horizontal = if (forTab()) 36.dp else 0.dp)
                )

                Spacer(modifier = Modifier.padding(2.dp))
                constants.spacer(2)

                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Box(
                        modifier = Modifier
                            .weight(4f)
                            .height(if (forTab()) 46.dp else 36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xffE8E8E8))
                            .noRippleClickable {
//                                constants.Profile_ViewModel.setunfollowClicker()
                                constants.Profile_ViewModel.closeUnFollowClick()

                            }, contentAlignment = Alignment.Center
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
                            .height(if (forTab()) 46.dp else 36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(newBlue)
                            .noRippleClickable {
                                ClickHelper.getInstance().clickOnce {
                                    if (ClickGuard.canClick()) {
                                        follow_Unfollow_Delete_API_Call() { result ->
                                            when (result) {
                                                0 -> {


                                                    Get_User_Profile_API_Call() { result2 ->
                                                        when (result2) {
                                                            0 -> {

                                                                println("BACKGROUNF API __ ${new_Stack?.current_UsedId} --${constants.Profile_ViewModel.get_Following_Count_BGAPIC()} ")
                                                                constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
                                                                    userId = new_Stack?.current_UsedId
                                                                        ?: 0,
                                                                    newFollowers = constants.Profile_ViewModel.get_Followers_Count_BGAPIC(),
                                                                    newFollowing = constants.Profile_ViewModel.get_Following_Count_BGAPIC()
                                                                )

                                                            }

                                                            1 -> {
                                                                // toast("Try Again later")
                                                            }

                                                            2 -> {

                                                            }
                                                        }
                                                    }
                                                    println("Success")
                                                    if (constants.Profile_ViewModel.get_follow_unfollow_Status() == 3) {
                                                        if (!is_Search_Enabled.value) {
                                                            constants.Profile_ViewModel.deleteUserById_Profile_FF(
                                                                content.user_Id ?: 0
                                                            )
                                                        } else {
                                                            constants.Profile_ViewModel.deleteUserById_Profile_Search_FF(
                                                                content.user_Id ?: 0
                                                            )
                                                        }
                                                    } else if (constants.Profile_ViewModel.get_follow_unfollow_Status() == 2) {
                                                        constants.Profile_ViewModel.updateImFollowed_toZero_ByUserId_FF(
                                                            content.user_Id
                                                        )
                                                        constants.Profile_ViewModel.updateIsFollowedByUserId_FF(
                                                            content.user_Id
                                                        )
                                                    }
                                                    println("DELETION ID __ ${content.user_Id}")
//                                                    constants.Profile_ViewModel.setunfollowClicker()
                                                    constants.Profile_ViewModel.closeUnFollowClick()

                                                }

                                                1 -> {
                                                    toast("Try Again later")
                                                }

                                                2 -> {

                                                }
                                            }
                                        }
                                    }
                                }
                            }, contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Unfollow",
                            color = Color.White,
                            fontSize = constants.textUnit(16),
                            fontFamily = constants.fontFamily(0)
                        )
                    }
                }
                constants.spacer(2)
            }
        }, image = content.user_Image
    )


    Common_Popup(
        visible = followRequest_Delete.value,
        modifier = Modifier.background(Color(0xffFCEDEC)), content = {


            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Spacer(modifier = Modifier.padding(2.dp))
               // constants.spacer(2)

                Text(
                    text = "Remove ${content.user_Name} ?",
                    color = newBlack,
                    fontSize = constants.textUnit(16),
                    fontFamily = constants.fontFamily(0)
                )

               // constants.spacer(2)

                Text(
                    text = "By removing, this person will be removed from your follower list.",
                    color = newBlack,
                    fontSize = constants.textUnit(12),
                    fontFamily = constants.fontFamily(3),
                    textAlign = TextAlign.Center
                   , lineHeight = 24.sp
                    , modifier = Modifier.padding(if (forTab()) 36.dp else 0.dp)
                )

                Spacer(modifier = Modifier.padding(2.dp))
                constants.spacer(2)

                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Box(
                        modifier = Modifier
                            .weight(4f)
                            .height(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xffE8E8E8))
                            .noRippleClickable {
                                constants.Profile_ViewModel.delete_Follow_Dismiss()
                            }, contentAlignment = Alignment.Center
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
                            .noRippleClickable {
                                ClickHelper.getInstance().clickOnce {
                                    if (ClickGuard.canClick()) {
                                        follow_Unfollow_Delete_API_Call() { result ->
                                            when (result) {
                                                0 -> {
                                                    Get_User_Profile_API_Call() { result2 ->
                                                        when (result2) {
                                                            0 -> {

                                                                println("BACKGROUNF API __ ${new_Stack?.current_UsedId} --${constants.Profile_ViewModel.get_Following_Count_BGAPIC()} ")
                                                                constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
                                                                    userId = new_Stack?.current_UsedId
                                                                        ?: 0,
                                                                    newFollowers = constants.Profile_ViewModel.get_Followers_Count_BGAPIC(),
                                                                    newFollowing = constants.Profile_ViewModel.get_Following_Count_BGAPIC()
                                                                )

                                                            }

                                                            1 -> {
                                                                // toast("Try Again later")
                                                            }

                                                            2 -> {

                                                            }
                                                        }
                                                    }
                                                    println("Success")
                                                    if (!is_Search_Enabled.value) {
                                                        constants.Profile_ViewModel.deleteUserById_Profile_FF(
                                                            content.user_Id ?: 0
                                                        )
                                                    } else {
                                                        constants.Profile_ViewModel.deleteUserById_Profile_Search_FF(
                                                            content.user_Id ?: 0
                                                        )
                                                    }
                                                    println("DELETION ID __ ${content.user_Id}")
                                                    constants.Profile_ViewModel.delete_Follow_Dismiss()

                                                }

                                                1 -> {
                                                    toast("Try Again later")
                                                }

                                                2 -> {

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            .background(Color(0xffE54C3C)), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Remove",
                            color = Color.White,
                            fontSize = constants.textUnit(16),
                            fontFamily = constants.fontFamily(0)
                        )
                    }
                }
                constants.spacer(2)
            }
        }, image = content.user_Image
    )


}




//////// iswhich - Main State Following or Followers tab
/////// Following State - follow 0 , followback 1, following 2
/////// followers State - follow 0 , followback 1, following 2


@Composable
fun Others_Profile_FF_Buttons(
    isWhich_FF : Int,
    Following_State : Int,
    Followers_State : Int ,
    onFollow :() -> Unit ,
    onFollowBack : () -> Unit,
    onFollowing :() -> Unit
){
    if (isWhich_FF == 1){
        /// for Following

        when(Following_State){
            0 -> {
                /// follow button
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White)
                        .border(1.dp, newGray, RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .noRippleClickable {
                            onFollow()
                        }
                    , verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                )
                {
                    SubcomposeAsyncImage(
                        model = R.drawable.profilenotrento, "", modifier = Modifier
                            .size(14.dp),
                        colorFilter = ColorFilter.tint(newBlue)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "Follow",
                        color = newBlue,
                        fontSize = constants.textUnit(12),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }
            1 -> {

                // follow back button

                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White)
                        .border(1.dp, newGray, RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .noRippleClickable {
                            onFollowBack()
                        }
                    , verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                )
                {
                    SubcomposeAsyncImage(
                        model = R.drawable.profilenotrento, "", modifier = Modifier
                            .size(14.dp),
                        colorFilter = ColorFilter.tint(newBlue)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "Follow back",
                        color = newBlue,
                        fontSize = constants.textUnit(12),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }
            2 -> {

                // following button

                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xffE8E8E8))
                        .noRippleClickable {
                            onFollowing()
                        }
                        //.border(1.dp, newGray, RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                    , verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                )
                {
                    SubcomposeAsyncImage(
                        model = R.drawable.profilenotrento, "", modifier = Modifier
                            .size(14.dp),
                        colorFilter = ColorFilter.tint(newBlack)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "Following",
                        color = newBlack,
                        fontSize = constants.textUnit(12),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }
        }
    }
    else
    {
        /// for followers

        when(Followers_State){
            0 -> {
                /// follow button
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White)
                        .border(1.dp, newGray, RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .noRippleClickable {
                            onFollow()
                        }
                    , verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                )
                {
                    SubcomposeAsyncImage(
                        model = R.drawable.profilenotrento, "", modifier = Modifier
                            .size(14.dp),
                        colorFilter = ColorFilter.tint(newBlue)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "Follow",
                        color = newBlue,
                        fontSize = constants.textUnit(12),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }
            1 -> {

                // follow back button

                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White)
                        .border(1.dp, newGray, RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .noRippleClickable {
                            onFollowBack()
                        }
                    , verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                )
                {
                    SubcomposeAsyncImage(
                        model = R.drawable.profilenotrento, "", modifier = Modifier
                            .size(14.dp),
                        colorFilter = ColorFilter.tint(newBlue)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "Follow back",
                        color = newBlue,
                        fontSize = constants.textUnit(12),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }
            2 -> {

                // following button

                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xffE8E8E8))
                        .noRippleClickable {
                            onFollowing()
                        }
                        //.border(1.dp, newGray, RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                    , verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                )
                {
                    SubcomposeAsyncImage(
                        model = R.drawable.profilenotrento, "", modifier = Modifier
                            .size(14.dp),
                        colorFilter = ColorFilter.tint(newBlack)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "Following",
                        color = newBlack,
                        fontSize = constants.textUnit(12),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }
        }

    }
}



