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
            Get_User_Profile_API_Call { result ->
                apiResult = result
            }

        constants.Profile_ViewModel.clear_All_BF_Handler()

        onDispose {
        }
    }
    }
    else {
        GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
    }

    val profile_Content_Original = constants.Profile_ViewModel.own_Profile_Content.collectAsStateWithLifecycle()

    val profile_Content = constants.Profile_ViewModel.get_Content_own_Profile()

    val posts = constants.Profile_ViewModel.profile_Posts.collectAsStateWithLifecycle()

    val tabs = listOf("Published" , "Expired")
    var selectedTab by remember { mutableStateOf(0) }
    var selectedTabTitle by remember { mutableStateOf("") }

    LaunchedEffect(AppPreferences.get_ProfileImage().isNotEmpty() , Unit) {
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

        !constants.Profile_ViewModel.get_Content_Own_Profile_Check() -> {

            FirebaseRepository.setAccountDeleted(
                userId = AppPreferences.getUserId().toString(),
                isDeleted = false,
                onComplete = {
                }
            )

            if (!profile_Content?.profile_image.isNullOrEmpty()) {
                AppPreferences.save_ProfileImage(profile_Content?.profile_image ?: "")
            }

            constants.Start_Up_ViewModel.set_Country(profile_Content?.country ?: constants.Start_Up_ViewModel.country.value)
            constants.Start_Up_ViewModel.set_State(profile_Content?.state ?: constants.Start_Up_ViewModel.state.value)
            constants.Start_Up_ViewModel.set_City(profile_Content?.city ?: constants.Start_Up_ViewModel.city.value)
            constants.Start_Up_ViewModel.set_Pincode(profile_Content?.pincode ?: constants.Start_Up_ViewModel.pincode.value)
            AppPreferences.save_Lat_Long(lat = profile_Content?.latitude ?: constants.Start_Up_ViewModel.latitude.value , profile_Content?.longitude ?: constants.Start_Up_ViewModel.longitude.value )

            AppPreferences.save_ph_number(profile_Content?.phone_num ?: "")

            val new = constants.Profile_ViewModel.profile_BF_Handler.collectAsState()

                   var retry_posts by remember { mutableStateOf(0) }

                   var failure_posts = remember { mutableStateOf(false) }

            if (network.value == NetworkStatus.Online) {

                LaunchedEffect(Unit,selectedTab, retry_posts) {

                    constants.Profile_ViewModel.clearPosts()
                    constants.API_Vm.currentPage_Profile_Posts = 0
                    constants.API_Vm.totalPages_Profile_Posts = 1
                    constants.API_Vm.nextPage_Profile_Posts = 1
                    constants.API_Vm.errorMessage_Profile_Posts = null

                    constants.API_Vm.load_Profile_Posts(
                        user_id = AppPreferences.getUserId(),
                        others_id = 0,
                        status = if (selectedTab == 0) constants.active else constants.expired,
                        page = 1
                    )
                }

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

                        if (lastVisibleItemIndex != null &&
                            totalItems > 0 &&
                            lastVisibleItemIndex >= totalItems - loadMoreThreshold &&
                            !isLoading &&
                            currentPage < totalPages &&
                            nextPage > 0
                        ) {

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

                constants.Profile_ViewModel.add_Selected_User_Name(
                    profile_Content?.username ?: "Profile"
                )

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

                                    )
                                    {

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

                                        .background(Color.White)
                                        .padding(horizontal = 16.dp)
                                        .padding(top = 40.dp, bottom = 16.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                )
                                {
                                    constants.spacer(4)

                                    Text(
                                        text = profile_Content?.name ?: "",
                                        color = newGray,
                                        fontSize = constants.textUnit(14),
                                        fontFamily = constants.fontFamily(2)
                                    )
                                    constants.spacer(2)

                                    if (!profile_Content?.bio.isNullOrEmpty()) {
                                        ExpandableText(
                                            fullText = profile_Content?.bio ?: "",
                                            maxCharacters = 80,
                                            modifier = Modifier.fillMaxWidth(.9f)
                                        )

                                        constants.spacer(2)
                                    }

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

                                                                            constants.Common_H_ViewModel.toggleshowBABars(
                                                                                false
                                                                            )

                                                                            if (new.value.isNotEmpty()) {
                                                                                constants.Profile_ViewModel.add_BF_Handler(
                                                                                    Profile_Handle_Back(
                                                                                        current_UsedId = AppPreferences.getUserId(),
                                                                                        other_UserId = 0,
                                                                                        selected_Tab = 0,
                                                                                        ff_User_Name = profile_Content?.username ?: "Username",
                                                                                        ff_Fw_Count = profile_Content?.followers ?: 0,
                                                                                        ff_Fg_Count = profile_Content?.following ?: 0,
                                                                                        screenType = ScreenType.FF_LIST
                                                                                    )
                                                                                )
                                                                            }

                                                                            clicker =
                                                                                0

                                                                            navController.navigate(
                                                                                ProfileScreenFlow.Profile_FF_Structure.route
                                                                            )
                                                                        }

                                                                        2 -> {

                                                                            constants.Profile_ViewModel.clear_FF_Lists()

                                                                            constants.Common_H_ViewModel.toggleshowBABars(
                                                                                false
                                                                            )

                                                                            if (new.value.isNotEmpty()) {
                                                                                constants.Profile_ViewModel.add_BF_Handler(
                                                                                    Profile_Handle_Back(
                                                                                        current_UsedId = AppPreferences.getUserId(),
                                                                                        other_UserId = 0,
                                                                                        selected_Tab = 1,
                                                                                        ff_User_Name = profile_Content?.username ?: "Username",
                                                                                        ff_Fw_Count = profile_Content?.followers ?: 0,
                                                                                        ff_Fg_Count = profile_Content?.following ?: 0,
                                                                                        screenType = ScreenType.FF_LIST
                                                                                    )
                                                                                )
                                                                            }


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

                                    constants.spacer(2)

                                }
                            }
                        }

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

                                if (selectedTab == 0){
                                    Column(
                                        modifier = Modifier
                                            .padding(top = if (forTab()) 154.dp else 64.dp)
                                            .fillMaxSize()

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

                            customGridItems(
                                count = posts.value.size,
                                nColumns = tabUnit

                            )
                            { itemIndex ->

                                val item = posts.value[itemIndex]
                                Box(
                                    modifier = Modifier
                                        .padding(vertical = 8.dp, horizontal = 8.dp)

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
                                                posts.value.map { it.toReelsData() }
                                            )

                                            constants.PostProperty_ViewModel.set_Post_Form_Flow(
                                                -1
                                            )

                                            if (selectedTabTitle == "Expired") {

                                                constants.PostProperty_ViewModel.setViewDetailsFlow(
                                                    ViewDetailsFlow.EXPIRY
                                                )
                                            }
                                            else {

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

                                        , modifier = thumbnailData.modifier

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

            Text(
                text = "Block Akash Kishore ?",
                color = newBlack,
                fontSize = constants.textUnit(16),
                fontFamily = constants.fontFamily(0)
            )

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

                constants.spacer(8)
            }
        }
    )

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

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Other_Profile_Structure(navController: NavHostController, viewModel: Common_H_ViewModel)
{

    BackHandler {
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

    var apiResult by remember { mutableStateOf<Int?>(null) }

    val context = LocalContext.current

    val getter = constants.Profile_ViewModel.show_Current_BF_Handler()

    var test = constants.Profile_ViewModel.profile_BF_Handler.collectAsState()

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

        constants.Profile_ViewModel.put_Other_User_Id(getter?.other_UserId ?: 0)

        Get_User_Profile_API_Call { result ->
            apiResult = result
        }

        onDispose {
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

                        constants.Profile_ViewModel.remove_last_BF_Handler()

                        navController.navigateUp()

                    }
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()

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

            Column(
                modifier = Modifier
                    .padding(top = 64.dp)
                    .fillMaxSize()

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

        !constants.Profile_ViewModel.get_Content_Others_Profile_Check() -> {

            val userBFHandler = constants.Profile_ViewModel.show_Current_BF_Handler()

            DisposableEffect(profile_Content.value?.user_id) {
                if (profile_Content.value != null) {
                    val profileId = profile_Content.value?.user_id ?: 0
                    constants.Profile_ViewModel.add_Current_Profile_UserId(profileId)

                }

                onDispose {
                }
            }

            if (from_DLP_State.value){
                constants.Profile_ViewModel.add_Selected_User_Name(
                    profile_Content.value?.username ?: "UserName"
                )

                constants.Profile_ViewModel.addProfile(
                    profile_Content.value?.user_id ?: 0
                )
                constants.Profile_ViewModel.add_Selected_Profile_Id(
                    id = profile_Content.value?.user_id ?: 0
                )
            }

            LaunchedEffect(getter?.other_UserId) {
                if (getter?.other_UserId != null && getter.other_UserId != 0) {

                    isLoadingChange.value = true

                }
            }

            LaunchedEffect(profile_Content.value?.user_id) {
                if (profile_Content.value != null) {
                    delay(100)
                    isLoadingChange.value = false
                }
            }

            var retry_posts by remember { mutableStateOf(0) }

            if (network.value == NetworkStatus.Online) {

                LaunchedEffect(Unit, retry_posts) {

                    constants.Profile_ViewModel.clearPosts()
                    constants.API_Vm.currentPage_Profile_Posts = 0
                    constants.API_Vm.totalPages_Profile_Posts = 1
                    constants.API_Vm.nextPage_Profile_Posts = 1
                    constants.API_Vm.errorMessage_Profile_Posts = null

                    constants.API_Vm.load_Profile_Posts(
                        user_id = AppPreferences.getUserId(),
                        others_id = constants.Profile_ViewModel.get_Other_User_Id(),
                        status =  constants.active ,
                        page = 1
                    )
                }

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

                        if (lastVisibleItemIndex != null &&
                            totalItems > 0 &&
                            lastVisibleItemIndex >= totalItems - loadMoreThreshold &&
                            !isLoading &&
                            currentPage < totalPages &&
                            nextPage > 0
                        ) {

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

                constants.Profile_ViewModel.add_Current_Profile_UserId(profile_Content.value?.user_id ?: 999)

                onDispose {
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

                                            .padding(horizontal = 16.dp)
                                            .padding(top = if (forTab()) 16.dp else (notchPadding.value + 4.dp))
                                        , contentAlignment = Alignment.Center
                                    )
                                    {

                                        Backer(
                                            modifier = Modifier.align(Alignment.CenterStart),
                                            onBackClick = {
                                                ClickHelper.getInstance().clickOnce {
                                                    if (ClickGuard.canClick()) {
                                                        constants.API_Vm.isLoading_ProfileS = true

                                                        if (from_DLP_State.value) {

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

                                                                constants.Profile_ViewModel.remove_last_BF_Handler()

                                                                when (previousHandler.screenType) {
                                                                    ScreenType.PROFILE -> {

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

                                                                    }
                                                                }

                                                                navController.navigateUp()
                                                            } else {

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

                                        if (!from_DLP_State.value && profile_Content.value?.user_id != AppPreferences.getUserId()) {
                                            Box(
                                                modifier = Modifier
                                                    .align(Alignment.CenterEnd)
                                                    .size(24.dp)
                                                    .clip(RoundedCornerShape(4.dp))

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

                                                ExpandableText(
                                                    fullText = profile_Content.value?.bio ?: "",
                                                    maxCharacters = 80,
                                                    modifier = Modifier.fillMaxWidth(.9f)
                                                )

                                                Spacer(modifier = Modifier.padding(4.dp))

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

                                                                                    constants.Profile_ViewModel.add_BF_Handler(
                                                                                        Profile_Handle_Back(
                                                                                            current_UsedId = AppPreferences.getUserId(),
                                                                                            other_UserId = profile_Content.value?.user_id ?: 0,
                                                                                            selected_Tab = 0,
                                                                                            ff_User_Name = profile_Content.value?.username ?: "Profile",
                                                                                            ff_Fw_Count = profile_Content.value?.followers ?: 0,
                                                                                            ff_Fg_Count = profile_Content.value?.following ?: 0,
                                                                                            screenType = ScreenType.FF_LIST
                                                                                        )
                                                                                    )

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

                                                                                    constants.Profile_ViewModel.add_BF_Handler(
                                                                                        Profile_Handle_Back(
                                                                                            current_UsedId = AppPreferences.getUserId(),
                                                                                            other_UserId = profile_Content.value?.user_id ?: 0,
                                                                                            selected_Tab = 1,
                                                                                            ff_User_Name = profile_Content.value?.username ?: "Profile",
                                                                                            ff_Fw_Count = profile_Content.value?.followers ?: 0,
                                                                                            ff_Fg_Count = profile_Content.value?.following ?: 0,
                                                                                            screenType = ScreenType.FF_LIST
                                                                                        )
                                                                                    )
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
                                                            var type = when {
                                                                profile_Content.value?.im_followed == "0" && profile_Content.value?.is_followed == "0" -> 0
                                                                profile_Content.value?.im_followed == "0" && profile_Content.value?.is_followed == "1" -> 1
                                                                else -> 2

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

                                                                , contentAlignment = Alignment.Center
                                                            )
                                                            {
                                                                Text(
                                                                    text = when(type) {
                                                                        0 -> "Follow"
                                                                        1 -> "Follow Back"
                                                                        else -> "Following"
                                                                    }

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

                                                constants.spacer(4)

                                            }
                                        }
                                    }

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

                                    item {
                                        if ((profile_Content.value?.is_blocked ?: 0) == 1){

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

                                                    Spacer(modifier = Modifier.height(16.dp))

                                                    Text(
                                                        text =  "Unblock to view their property listings",
                                                        color = newBlack,
                                                        textAlign = TextAlign.Center,
                                                        fontSize = constants.textUnit(12),
                                                        fontFamily = constants.fontFamily(2)
                                                    )

                                                }

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

                                            customGridItems(
                                                count = posts.value.size,
                                                nColumns = grditype

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
                                                                    posts.value.map { it.toReelsData() }
                                                                )
                                                                constants.PostProperty_ViewModel.set_Post_Form_Flow(
                                                                    -1
                                                                )

                                                                constants.PostProperty_ViewModel.setViewDetailsFlow(
                                                                    ViewDetailsFlow.OTHERS
                                                                )

                                                                constants.Common_H_ViewModel.dismiss_Cmt_btm_Sheet()

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

    Common_Popup(
        block_PopUp,
        modifier = Modifier
            .background(Color(0xffF7F0DC))
        , image = "",
        userName = "",
        icon = 0
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

            Text(
                text = "Are you sure you want to ${if (profile_Content.value?.is_blocked == 0)  "Block" else "Unblock" }  this profile?",
                color = newBlack,
                fontSize = constants.textUnit(18),
                fontFamily = constants.fontFamily(1)
                , textAlign = TextAlign.Center

                , modifier = Modifier.padding(horizontal = if (forTab()) 46.dp else 36.dp)
            )

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
                                        constants.API_Vm.put_Block_User(
                                            user_id = AppPreferences.getUserId(),
                                            blocker_id = profile_Content.value?.user_id ?: 0,

                                            status = blockStatus.value

                                        )
                                        { apiResultHandling ->
                                            when (apiResultHandling) {
                                                is API_Result_Handling.Error -> {

                                                }

                                                is API_Result_Handling.Deactivated -> {

                                                }

                                                is API_Result_Handling.NoData -> {

                                                }

                                                is API_Result_Handling.Loading -> {

                                                }

                                                is API_Result_Handling.Success -> {

                                                    Get_User_Profile_API_Call() { result2 ->
                                                        when (result2) {
                                                            0 -> {}
                                                            1 -> {

                                                                constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
                                                                    userId = profile_Content.value?.user_id
                                                                        ?: 0,
                                                                    newFollowers = constants.Profile_ViewModel.get_Followers_Count_BGAPIC(),
                                                                    newFollowing = constants.Profile_ViewModel.get_Following_Count_BGAPIC()
                                                                )

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

                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {

                constants.spacer(2)

                Image(painter = painterResource(R.drawable.profilepopicon) , "",
                    modifier = Modifier.size(56.dp))

                constants.spacer(2)

                Text(
                    text = "Are you sure you want to Unfollow this profile?",
                    color = newBlack,
                    fontSize = constants.textUnit(18),
                    fontFamily = constants.fontFamily(1),
                    textAlign = TextAlign.Center

                    , modifier = Modifier.padding(horizontal = if (forTab()) 46.dp else 36.dp)
                )

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

                                                                                    constants.Profile_ViewModel.closeUnFollowClick()

                                                                                }

                                                                                2 -> {
                                                                                    GlobalSnackbar.show(
                                                                                        "Something went wrong"
                                                                                    )
                                                                                }
                                                                            }
                                                                        }

                                                                        val currentUserId =
                                                                            AppPreferences.getUserId()
                                                                        val targetUserId =
                                                                            profile_Content.value?.user_id
                                                                                ?: 0

                                                                        if (constants.Profile_ViewModel.get_follow_unfollow_Status() == 2) {

                                                                            constants.Profile_ViewModel.deleteUserById_Profile_FF_Map(
                                                                                mapUserId = currentUserId,
                                                                                mapTab = 1,
                                                                                targetUserId = targetUserId
                                                                            )

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

                                                    }

                                                    is API_Result_Handling.Error -> {

                                                    }

                                                    is API_Result_Handling.Success -> {

                                                        constants.Profile_ViewModel.updateReported_Selected_Profile(
                                                            1
                                                        )
                                                        constants.Profile_ViewModel.toggleReportSubmissionSuccess()

                                                    }

                                                    is API_Result_Handling.NoData -> {

                                                    }

                                                    is API_Result_Handling.Deactivated -> {

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

    LaunchedEffect(new_Stack?.id, new_Stack?.ff_Fw_Count, new_Stack?.ff_Fg_Count) {
        if (new_Stack != null) {

            if (network.value == NetworkStatus.Online) {

                val savedOtherUserId = constants.Profile_ViewModel.get_Other_User_Id()
                constants.Profile_ViewModel.put_Other_User_Id(0)

                Get_User_Profile_API_Call() { result ->
                    when (result) {
                        1 -> {
                            val userId = AppPreferences.getUserId()

                            constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
                                userId = userId,
                                newFollowers = constants.Profile_ViewModel.get_Followers_Count_BGAPIC(),
                                newFollowing = constants.Profile_ViewModel.get_Following_Count_BGAPIC()
                            )

                        }
                    }
                }

                constants.Profile_ViewModel.put_Other_User_Id(savedOtherUserId)
            }
        }
    }

    val currentUserId = if (new_Stack?.other_UserId == 0 || new_Stack?.other_UserId == null) {
        AppPreferences.getUserId()
    } else {
        new_Stack?.other_UserId ?: 0

    }
    val currentTab = new_Stack?.selected_Tab ?: 0
    val currentKey = constants.Profile_ViewModel.createFFKey(currentUserId, currentTab)

    val ffListMap by constants.Profile_ViewModel.users_FF_List_Map.collectAsStateWithLifecycle()
    val ffSearchMap by constants.Profile_ViewModel.users_FF_Search_Map.collectAsStateWithLifecycle()

    val users_Profiles_List = ffListMap[currentKey] ?: emptyList()
    val users_Profiles_Search_List = ffSearchMap[currentKey] ?: emptyList()

    val tapped_Profiles_List = constants.Profile_ViewModel.tapped_Profile_List.collectAsState()

    var isLoading by remember { mutableStateOf(constants.API_Vm.isLoading_FF) }
    var errorMessage by remember { mutableStateOf(constants.API_Vm.errorMessage_FF) }
    var currentPage by remember { mutableStateOf(constants.API_Vm.currentPage_FF) }
    var totalPages by remember { mutableStateOf(constants.API_Vm.totalPages_FF) }
    val listState = rememberLazyListState()

    LaunchedEffect(new_Stack?.id) {
    }

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
    }

    var retry by remember { mutableStateOf(0) }

    if (network.value == NetworkStatus.Online) {
        LaunchedEffect(currentUserId, currentTab, is_Search_Enabled.value, search_Text.value, retry) {
            if (is_Search_Enabled.value) {
                constants.API_Vm.totalPages_FF = 1
                constants.API_Vm.load_Search_FF(
                    user_id = AppPreferences.getUserId(),
                    others_id = if (currentUserId == AppPreferences.getUserId()) "" else currentUserId.toString(),
                    status = currentTab + 1,
                    search = search_Text.value,
                    page = 1
                )
            } else {

                    constants.API_Vm.load_Profile_FF_List(
                        user_id = AppPreferences.getUserId(),
                        others_id = if (currentUserId == AppPreferences.getUserId()) "" else currentUserId.toString(),
                        status = currentTab + 1,
                        page = 1
                    )

            }
        }

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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

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

                                constants.Profile_ViewModel.remove_last_BF_Handler()

                                when (previousHandler.screenType) {
                                    ScreenType.PROFILE -> {

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

                                        navController.navigateUp()
                                    }

                                    ScreenType.FF_LIST -> {

                                        navController.navigateUp()
                                    }
                                }
                            } else {

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

                constants.spacer(2)

                Image(painter = painterResource(R.drawable.profilepopicon) , "",
                    modifier = Modifier.size(56.dp))

                constants.spacer(2)

                Text(
                    text = "Are you sure you want to Unfollow this profile?",
                    color = newBlack,
                    fontSize = constants.textUnit(18),
                    fontFamily = constants.fontFamily(1),
                    textAlign = TextAlign.Center

                    ,modifier = Modifier.padding(horizontal = if(forTab())46.dp else 36.dp)
                )

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

                                                        if (currentTab == 1) {
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
                                                                constants.Profile_ViewModel.updateImFollowedByUserId_FF_Map(
                                                                    mapUserId = currentUserId,
                                                                    mapTab = currentTab,
                                                                    targetUserId = content.user_Id
                                                                        ?: 0
                                                                )
                                                            }
                                                        } else if (currentTab == 0) {
                                                            constants.Profile_ViewModel.updateImFollowedByUserId_FF_Map(
                                                                mapUserId = currentUserId,
                                                                mapTab = currentTab,
                                                                targetUserId = content.user_Id ?: 0
                                                            )
                                                        }

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

                Image(painter = painterResource(R.drawable.profileremoveicon) , "",
                    modifier = Modifier.size(56.dp))

                constants.spacer(2)

                Text(
                    text = "Are you sure you want to Remove this profile?",
                    color = newBlack,
                    fontSize = constants.textUnit(18),
                    fontFamily = constants.fontFamily(1),
                    textAlign = TextAlign.Center

                    , modifier = Modifier.padding(horizontal = if (forTab()) 46.dp else 36.dp)
                )

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

                } else {
                    val isViewingOwnProfile = (new_Stack?.other_UserId == 0
                            || new_Stack?.other_UserId == null
                            || new_Stack?.other_UserId == AppPreferences.getUserId())

                    if (isViewingOwnProfile) {

                        val followers_State = if (item.is_followed == 1 && item.im_followed == 0) {
                            0
                        } else {
                            1
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

                        val followers_State = when {
                            item.im_followed == 0 && item.is_followed == 1 -> 1
                            item.im_followed == 1 && item.is_followed == 1 -> 2
                            item.im_followed == 0 && item.is_followed == 0 -> 0
                            else -> 2
                        }

                        val following_State = when {
                            item.is_followed == 1 && item.im_followed == 0 -> 1
                            item.im_followed == 1 || item.im_followed == 1 && item.is_followed == 0 -> 2
                            else -> 0
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

            modifier = Modifier.noRippleClickable {
                ClickHelper.getInstance().clickOnce {
                    if (ClickGuard.canClick()) {
                        keyboardController1?.hide()
                        focusManager1.clearFocus()

                        val currentProfileId = new_Stack?.other_UserId ?: 0
                        val targetProfileId = item.user_id

                        if (targetProfileId == AppPreferences.getUserId() && currentProfileId == 0) {
                            return@clickOnce
                        }

                        if (targetProfileId == currentProfileId) {
                            return@clickOnce
                        }

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
                                screenType = ScreenType.PROFILE
                            )
                        )

                        constants.Profile_ViewModel.clearSelectedUserProfile()
                        constants.Profile_ViewModel.add_Selected_User_Name(item.username ?: "Profile")
                        constants.Profile_ViewModel.addProfile(item.user_id)
                        constants.Profile_ViewModel.add_Selected_Profile_Id(id = item.user_id)
                        constants.Profile_ViewModel.put_Other_User_Id(item.user_id)
                        constants.Profile_ViewModel.clear_SearchList_FF()
                        constants.Profile_ViewModel.clear_Search_Text_FF()
                        constants.Profile_ViewModel.disable_Search()
                        constants.API_Vm.totalPages_Profile_Posts = 1

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

                }

                PROFILE_IMAGE_URL.value = result.url

            } catch (e: Exception) {
                GlobalSnackbar.show("Image upload failed")
                e.printStackTrace()
            }
        }
    }

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

        )
        {
            item {
                Column  {

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

                                                        focusManager.clearFocus()
                                                        keyboardController?.hide()
                                                        constants.Profile_ViewModel.save_new_name_edit_profile(
                                                            AppPreferences.get_User_Name()
                                                        )
                                                        GlobalSnackbar.show(
                                                            profileChangeErrorMessage.value
                                                        )

                                                    }

                                                    is API_Result_Handling.NoData -> {

                                                    }

                                                    is API_Result_Handling.Loading -> {

                                                    }

                                                    is API_Result_Handling.Success -> {
                                                        constants.Profile_ViewModel.enable_Edit_Profile()

                                                        focusManager.clearFocus()
                                                        keyboardController?.hide()
                                                        AppPreferences.save_User_Name(constants.Profile_ViewModel.get_new_username())
                                                        GlobalSnackbar.show("UserName Successfully Updated")

                                                    }

                                                    is API_Result_Handling.Deactivated -> {

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

                            )

                            Text(
                                "You can change username only 30 days once",
                                color = newBlack,
                                fontSize = constants.textUnit(12),
                                fontFamily = constants.fontFamily(2),
                                modifier = Modifier

                            )
                        }
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

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
    if (isWhich_FF == 1){

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

            Text(
                text = "Unfollow",
                color = newBlack,
                fontSize = constants.textUnit(12),
                fontFamily = constants.fontFamily(2)
            )
        }

    }
    else {

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

                if (item.user_id == AppPreferences.getUserId()) {

                } else {

                    val isViewingOwnProfile = (new_Stack?.other_UserId == 0
                            || new_Stack?.other_UserId == null
                            || new_Stack?.other_UserId == AppPreferences.getUserId())

                    if (isViewingOwnProfile) {

                        val followers_State = if (item.is_followed == 1 && item.im_followed == 0) {
                            0
                        } else {
                            1
                        }

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

                        val followers_State = when {
                            item.im_followed == 0 && item.is_followed == 1 -> 1
                            item.im_followed == 1 && item.is_followed == 1 -> 2
                            item.im_followed == 0 && item.is_followed == 0 -> 0
                            else -> 2
                        }

                        val following_State = when {
                            item.im_followed == 0 && item.is_followed == 1 -> 1
                            item.im_followed == 1 && item.is_followed == 1 -> 2
                            else -> 0
                        }

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

                    constants.Profile_ViewModel.add_Selected_User_Name(
                        item.username ?: "Profile"
                    )

                    constants.Profile_ViewModel.add_BF_Handler(Profile_Handle_Back(
                        current_UsedId = AppPreferences.getUserId(),
                        other_UserId = item.user_id,
                        ff_User_Name = item.username ,
                        ff_Fw_Count = item.followers_count,
                        ff_Fg_Count = item.following_count,

                    ))

                    constants.Profile_ViewModel.addProfile(item.user_id)
                    constants.Profile_ViewModel.add_Selected_Profile_Id(id = item.user_id)

                    constants.Profile_ViewModel.clear_SearchList_FF()

                    constants.API_Vm.totalPages_Profile_Posts = 1

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

    BackHandler {  }

    val profile_selected_Index = constants.Profile_ViewModel.show_Tapped_FFs.collectAsState()

    val unfollow_PUP = constants.Profile_ViewModel.unFollowClick.collectAsState()

    val followRequest_Delete = constants.Profile_ViewModel.followRequestDelete.collectAsState()

    val users_Profiles_List = constants.Profile_ViewModel.get_User_FF_List.collectAsStateWithLifecycle()
    val users_Profiles_Search_List = constants.Profile_ViewModel.get_User_FF_Search_List.collectAsStateWithLifecycle()

    val tapped_Profiles_List = constants.Profile_ViewModel.tapped_Profile_List.collectAsState()

    val isLoading = constants.API_Vm.isLoading_FF

    val currentPage = constants.API_Vm.currentPage_FF
    val totalPages = constants.API_Vm.totalPages_FF
    val listState = rememberLazyListState()

    val new_Stack by constants.Profile_ViewModel.currentBFHandler.collectAsStateWithLifecycle()

    var search_Text = constants.Profile_ViewModel.search_Text_FF.collectAsStateWithLifecycle()
    var search_Text_Handler = constants.Profile_ViewModel.search_Text_FF_Handler.collectAsStateWithLifecycle()
    var is_Search_Enabled = constants.Profile_ViewModel.is_Search_Enabled.collectAsStateWithLifecycle()
    var is_Search_Enabled_Handler = constants.Profile_ViewModel.is_Search_Enabled_Handler.collectAsStateWithLifecycle()

    DisposableEffect(Unit) {
        constants.Profile_ViewModel.add_Search_Text_FF(search_Text_Handler.value)
        constants.Profile_ViewModel.add_Search_State(is_Search_Enabled_Handler.value)
        onDispose {  }
    }

    LaunchedEffect(users_Profiles_List.value) {
    }

    LaunchedEffect(new_Stack) {
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
        }
    }

    LaunchedEffect(listState, currentPage, isLoading, totalPages) {
        if (!is_Search_Enabled.value) {
            snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                .collect { lastVisibleItemIndex ->
                    val totalItems = listState.layoutInfo.totalItemsCount
                    val loadMoreThreshold = 2

                    if (
                        lastVisibleItemIndex != null &&
                        totalItems > 0 &&
                        lastVisibleItemIndex >= totalItems - loadMoreThreshold &&
                        !isLoading &&
                        currentPage < totalPages
                    ) {

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

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(8.7f)
                    .background(newWhite),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CircularProgressIndicator()
            }
        }
        else {

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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .border(1.dp, newBlack, RoundedCornerShape(8.dp))

                , verticalAlignment = Alignment.CenterVertically,

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

            )
            { targetState ->

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
                                        && AppPreferences.getUserId() == 0
                                    ) {
                                        1
                                    } else {
                                        0
                                    }

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

                                                                constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
                                                                    userId = new_Stack?.current_UsedId
                                                                        ?: 0,
                                                                    newFollowers = constants.Profile_ViewModel.get_Followers_Count_BGAPIC(),
                                                                    newFollowing = constants.Profile_ViewModel.get_Following_Count_BGAPIC()
                                                                )

                                                            }

                                                            1 -> {

                                                            }

                                                            2 -> {

                                                            }
                                                        }
                                                    }
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

                Text(
                    text = "Remove ${content.user_Name} ?",
                    color = newBlack,
                    fontSize = constants.textUnit(16),
                    fontFamily = constants.fontFamily(0)
                )

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

                                                                constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
                                                                    userId = new_Stack?.current_UsedId
                                                                        ?: 0,
                                                                    newFollowers = constants.Profile_ViewModel.get_Followers_Count_BGAPIC(),
                                                                    newFollowing = constants.Profile_ViewModel.get_Following_Count_BGAPIC()
                                                                )

                                                            }

                                                            1 -> {

                                                            }

                                                            2 -> {

                                                            }
                                                        }
                                                    }
                                                    if (!is_Search_Enabled.value) {
                                                        constants.Profile_ViewModel.deleteUserById_Profile_FF(
                                                            content.user_Id ?: 0
                                                        )
                                                    } else {
                                                        constants.Profile_ViewModel.deleteUserById_Profile_Search_FF(
                                                            content.user_Id ?: 0
                                                        )
                                                    }
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

        when(Following_State){
            0 -> {

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

                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xffE8E8E8))
                        .noRippleClickable {
                            onFollowing()
                        }

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

        when(Followers_State){
            0 -> {

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

                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xffE8E8E8))
                        .noRippleClickable {
                            onFollowing()
                        }

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
