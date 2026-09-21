package com.toletspot.houseforrent.Home_Screen.Search_Module


import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Reels_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Put_Profile_search_Data
import com.toletspot.houseforrent.API.API_Result_Handling
import com.toletspot.houseforrent.API.StartUp_API.Get_User_Profile_API_Call
import com.toletspot.houseforrent.API.StartUp_API.follow_Unfollow_Delete_API_Call
import com.toletspot.houseforrent.AppPreferences
import com.toletspot.houseforrent.Custom_Assets.API_Fail_UI
import com.toletspot.houseforrent.Custom_Assets.Backer
import com.toletspot.houseforrent.Custom_Assets.ClickHelper
import com.toletspot.houseforrent.Custom_Assets.Comment_Structure
import com.toletspot.houseforrent.Custom_Assets.Common_API_Fail
import com.toletspot.houseforrent.Custom_Assets.Common_DropDown2Options
import com.toletspot.houseforrent.Custom_Assets.Enquiry_Form_Btm_Sheet_Structure
import com.toletspot.houseforrent.Custom_Assets.LottiAnimation
import com.toletspot.houseforrent.Custom_Assets.Static_Bottom
import com.toletspot.houseforrent.Custom_Assets.getTimeAgo
import com.toletspot.houseforrent.Custom_Assets.getVideoThumbnailBase64
import com.toletspot.houseforrent.Custom_Assets.logD
import com.toletspot.houseforrent.Custom_Assets.rememberNotchHeightDp
import com.toletspot.houseforrent.Custom_Assets.toast
import com.toletspot.houseforrent.GlobalSnackbar
import com.toletspot.houseforrent.Home_Screen.Common_H_ViewModel
import com.toletspot.houseforrent.Home_Screen.ProfileModule.ClickGuard
import com.toletspot.houseforrent.Navigation.SearchScreenFlow
import com.toletspot.houseforrent.Navigation.VideosScreenFlow
import com.toletspot.houseforrent.NetworkStatus
import com.toletspot.houseforrent.R
import com.toletspot.houseforrent.ScrollDirection
import com.toletspot.houseforrent.Start_Up.CommonText
import com.toletspot.houseforrent.UI_DataClass.Common_DropDown2Options_DC
import com.toletspot.houseforrent.UI_DataClass.Profile_Handle_Back
import com.toletspot.houseforrent.constants
import com.toletspot.houseforrent.forTab
import com.toletspot.houseforrent.noRippleClickable
import com.toletspot.houseforrent.rememberLazyListScrollDirection
import com.toletspot.houseforrent.rememberNetworkStatus
import com.toletspot.houseforrent.ui.theme.newBlack
import com.toletspot.houseforrent.ui.theme.newBlue
import com.toletspot.houseforrent.ui.theme.newGray
import com.toletspot.houseforrent.ui.theme.newLightBlue
import com.toletspot.houseforrent.ui.theme.newPurpleGradient
import com.toletspot.houseforrent.ui.theme.newPurpleGradientBorder
import com.toletspot.houseforrent.ui.theme.newWhite
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Search_Main_Screen(navHostController: NavHostController, viewModel: Common_H_ViewModel)
{




    LaunchedEffect(Unit) {
        viewModel.toggleshowTABars(false)
        viewModel.toggleshowBABars(true)
        constants.Common_H_ViewModel.toggleshowBABars(true)
    }

//    DisposableEffect(Unit) {
//        viewModel.toggleshowBABars(true)
//        onDispose {  }
//    }

    val go_to_Search_Results = constants.Search_ViewModel.show_Search_Results.collectAsStateWithLifecycle()

    val network = rememberNetworkStatus()

    val list = listOf("Residential" , "Commercial" , "Agricultural" , "Sellers")




    var search_State = constants.Search_ViewModel.search_State

    var search_Area = constants.Search_ViewModel.search_Area
    val notchPadding = rememberNotchHeightDp()

    var selectedRange by remember { mutableStateOf(1_000f..10_000_000f) }


    var api_State = remember { mutableStateOf(-1) }
    var api_State2 = remember { mutableStateOf(-1) }


    val popular_Cities = constants.Search_ViewModel.popular_Cities.collectAsState()
    val popular_Sellers = constants.Search_ViewModel.popular_Sellers.collectAsState()


    if (network.value == NetworkStatus.Online)
    {
        DisposableEffect(Unit) {
            if (popular_Cities.value.isEmpty()) {
                constants.API_Vm.get_Popular_Cities_Saerch(
                    user_id = AppPreferences.getUserId()
                )
                { aPI_Result_Handling ->
                    when (aPI_Result_Handling) {
                        is API_Result_Handling.Loading -> {
                            api_State.value = 0
                        }

                        is API_Result_Handling.Deactivated -> {
                            //resultCallback(5)
                        }

                        is API_Result_Handling.Error -> {
                            api_State.value = 1
                        }

                        is API_Result_Handling.Success -> {
                            api_State.value = 2
                        }

                        is API_Result_Handling.NoData -> {
                            api_State.value = 3
                        }
                    }
                }
            }

            if (popular_Sellers.value.isEmpty()) {

                constants.API_Vm.get_Popular_Users() { aPI_Result_Handling ->
                    when (aPI_Result_Handling) {
                        is API_Result_Handling.Loading -> {
                            api_State2.value = 0
                        }

                        is API_Result_Handling.Deactivated -> {
                            //resultCallback(5)
                        }

                        is API_Result_Handling.Error -> {
                            api_State2.value = 1
                        }

                        is API_Result_Handling.Success -> {
                            api_State2.value = 2
                        }

                        is API_Result_Handling.NoData -> {
                            api_State2.value = 3
                        }
                    }
                }
            }


            onDispose {
                if (search_State.value == 4) {
                    constants.Search_ViewModel.search_Land_Type.value = 4
                    constants.Search_ViewModel.search_State.value = 4
                   constants.Search_ViewModel.selectedOption_SEARCHTYPE = "Sellers"
                    search_State.value = 4
                }
            }
        }
    }
    else {
        GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
    }



    var minInput = remember { mutableStateOf(selectedRange.start.toInt().toString()) }
    var maxInput = remember { mutableStateOf(selectedRange.endInclusive.toInt().toString()) }


    var scrollstate = rememberScrollState()


    val scope = rememberCoroutineScope()

    val listState = rememberLazyListState()

    val scrollDirection by rememberLazyListScrollDirection(listState)

    LaunchedEffect(scrollDirection) {
        when (scrollDirection) {
            ScrollDirection.DOWN -> constants.Common_H_ViewModel.toggleshowBABars(false)
            ScrollDirection.UP, ScrollDirection.IDLE -> constants.Common_H_ViewModel.toggleshowBABars(true)
        }
    }


    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current



    val isLoading = constants.API_Vm.isLoading_ProfileS
    val errorMessage = constants.API_Vm.errorMessage_ProfileS
    val currentPage = constants.API_Vm.currentPage_ProfileS
    val totalPages = constants.API_Vm.totalPages_ProfileS

    val search_Profile_Content = constants.Search_ViewModel.profile_Search_Results.collectAsState()

    var search_username = constants.Search_ViewModel.search_Profile_Name

    var user_search_trigger = remember { mutableStateOf(false) }

    var failure = remember { mutableStateOf(false) }
    var retry by remember { mutableStateOf(0) }


    if (network.value == NetworkStatus.Online) {
        LaunchedEffect(search_username.value , retry) {
            if (search_username.value.length >= 3) {
                println("WHENPROFILE SEARCHED HITTING NORMAL")
                constants.API_Vm.load_Profile_Search(
                    user_id = AppPreferences.getUserId(),
                    name = search_username.value,
                    page = 1,
                )
            }
        }

        // Pagination logic stays the same
        LaunchedEffect(listState, currentPage, isLoading, totalPages) {
            println("WHENPROFILE SEARCHED HITTING Pagination")
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
                        println("CURRENT PAGE - ${currentPage}")
                        constants.API_Vm.load_Profile_Search(
                            user_id = AppPreferences.getUserId(),
                            name = search_username.value,
                            page = currentPage + 1,
                        )
                    }
                }
        }
    }
    else {
        GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
    }

    var density = LocalDensity.current

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .background(newWhite)
            .padding(top =if (forTab()) 16.dp else  notchPadding.value)
            .padding(horizontal = 16.dp)
        , horizontalAlignment = Alignment.CenterHorizontally
        , state = listState
    )
    {
        item {
           // Spacer(modifier = Modifier.padding(top = if (forTab()) 16.dp else notchPadding.value))

            Text(
                "Search",
                fontSize = constants.textUnit(24),
                fontFamily = constants.fontFamily(0),
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(newWhite)
                    .padding(8.dp)
            )
            {


                val itemWidth = 140.dp // approximate width of each item including padding/margin
                val itemSpacing = 8.dp // spacing between items

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(scrollstate)
                ) {
                    list.forEachIndexed { index, item ->
                        Box(
                            modifier = Modifier
                                .width(itemWidth) // make width fixed
                                .clip(RoundedCornerShape(4.dp))
                                .background(
                                    if (constants.Search_ViewModel.selectedOption_SEARCHTYPE == item) newBlack else newWhite
                                )
                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                .noRippleClickable {
                                    constants.Search_ViewModel.selectedOption_SEARCHTYPE = item
                                    constants.Search_ViewModel.search_Profile_Name.value = ""
                                    constants.Search_ViewModel.clear_profile_SearchData()
                                    scope.launch {
                                        // calculate offset in pixels
                                        val offset = with(density) {
                                            (itemWidth + itemSpacing).toPx() * index
                                        }
                                        scrollstate.animateScrollTo(offset.toInt())
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            CommonText(
                                item,
                                if (constants.Search_ViewModel.selectedOption_SEARCHTYPE == item) newWhite else newGray,
                                16,
                                0
                            )
                        }

                        Spacer(modifier = Modifier.width(itemSpacing))
                    }
                }



                Spacer(modifier = Modifier.height(15.dp))


                Spacer(modifier = Modifier.height(15.dp))

//                if (search_State.value != "Sellers"){
                if (constants.Search_ViewModel.selectedOption_SEARCHTYPE != "Sellers"){
                    Search_Main_Property(focusManager , viewModel, search_State , selectedRange)
                }
                else {
                    Search_Main_Profile(viewModel , navHostController ,viewModel , search_State)
                }

            }

                // popular cities flow row

            Column {
                if (constants.Search_ViewModel.selectedOption_SEARCHTYPE == "Sellers") {
                    if (constants.Search_ViewModel.search_Profile_Name.value.isEmpty()) {
                        Spacer(modifier = Modifier.padding(16.dp))

                        if (popular_Sellers.value.isNotEmpty()) {
                            Text(
                                "Popular Sellers",
                                fontSize = constants.textUnit(16),
                                fontFamily = constants.fontFamily(0),
                                color = Color.Black,
                                modifier = Modifier.align(Alignment.Start).padding(start = 8.dp)
                                //.align(Alignment.Start)
                            )

                            constants.spacer(2)

                            FlowRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(if (forTab())16.dp else 8.dp),
                                verticalArrangement = Arrangement.spacedBy(if (forTab())16.dp else 8.dp)
                            )
                            {
                                /// content from api
                                popular_Sellers.value.filterNot { it?.user_id == AppPreferences.getUserId() }.forEach { item ->

                                    Row(
                                        modifier = Modifier
                                            .wrapContentWidth()
                                            .background(Color.White)
                                            .border(1.dp, newGray, RoundedCornerShape(4.dp))
                                            .padding(8.dp)
                                            .noRippleClickable{
                                                ClickHelper.getInstance().clickOnce {
                                                    viewModel.toggleshowTABars(false)
//                                                    constants.Profile_ViewModel.add_Selected_User_Name(
//                                                        item?.username ?: "Unknown"
//                                                    )

                                                    //new flowwewwwwwww
//                                                    constants.Profile_ViewModel.add_BF_Handler(
//                                                        Profile_Handle_Back(
//                                                            current_UsedId = AppPreferences.getUserId(),
//                                                            other_UserId = item?.user_id ?: 0,
//                                                            ff_User_Name = item?.username ?: "",
//                                                            ff_Fw_Count = item?.followers ?: 0,
//                                                            ff_Fg_Count = item?.following ?: 0,
//                                                            // is_Search_Enabled = is_Search_Enabled.value,
//                                                            // search_Text = search_Text.value
//                                                        )
//                                                    )
//
//                                                    /// println("ITEM PROFILE STRUCTURE __ ${is_Search_Enabled.value} -- ${constants.Profile_ViewModel.profile_BF_Handler.value}")
//
//                                                    println("GIVEN OTHER USER ID -- ${constants.Profile_ViewModel.get_Other_User_Id()}")
//
//                                                    constants.Profile_ViewModel.addProfile(
//                                                        item?.user_id ?: 0
//                                                    )
//                                                    constants.Profile_ViewModel.add_Selected_Profile_Id(
//                                                        id = item?.user_id ?: 0
//                                                    )

//                                                    keyboardController1?.hide()
//                                                    focusManager1.clearFocus()

                                                    constants.Profile_ViewModel.add_BF_Handler(
                                                        item = Profile_Handle_Back(
                                                            //id = ,
                                                            current_UsedId = AppPreferences.getUserId(),
                                                            other_UserId = item?.user_id ?: 0,
                                                            //selected_Tab = item?.selected_Tab ?: 0,
                                                            ff_User_Name = item?.username?: "username",
                                                            ff_Fw_Count = item?.followers ?: 0,
                                                            ff_Fg_Count = item?.following ?: 0,
//                                                            is_Search_Enabled = is_Search_Enabled.value,
//                                                            search_Text = search_Text.value
                                                        )
                                                    )

                                                    constants.Profile_ViewModel.clearSelectedUserProfile()
                                                    constants.Profile_ViewModel.add_Selected_User_Name(item?.username ?: "username")

                                                    // Set the profile ID and navigate
                                                    constants.Profile_ViewModel.addProfile(item?.user_id ?: 0)
                                                    constants.Profile_ViewModel.add_Selected_Profile_Id(id = item?.user_id ?: 0)
                                                    constants.Profile_ViewModel.put_Other_User_Id(item?.user_id ?: 0)
                                                    constants.Profile_ViewModel.clear_SearchList_FF()
                                                    constants.API_Vm.totalPages_Profile_Posts = 1

                                                    // if (view_Details_Data.value?.user_id == AppPreferences.)
                                                    viewModel.toggleshowBABars(false)
                                                    navHostController.navigate(VideosScreenFlow.Other_Profile_Structure.route)
                                                }
                                            },
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start
                                    )
                                    {
                                        Box(
                                            modifier = Modifier
                                                .size(20.dp)
                                                .background(newWhite, CircleShape),
                                            contentAlignment = Alignment.Center
                                        )
                                        {
                                            SubcomposeAsyncImage(
                                                model = item?.profile_image ?: "",
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .clip(CircleShape),
                                                contentDescription = "",
                                                contentScale = ContentScale.FillBounds
                                            )
                                            {
                                                val state = painter.state
                                                if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                                                    Box(
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                            .background(newLightBlue, CircleShape)
                                                        //.padding(8.dp)
                                                        , contentAlignment = Alignment.Center
                                                    ) {
                                                        Text(
                                                            text = item?.username.takeIf { it?.isNotEmpty() == true }
                                                                ?.take(1)?.uppercase() ?: "",
                                                            autoSize = TextAutoSize.StepBased(
                                                                minFontSize = 10.sp,
                                                                constants.textUnit(16),
                                                                stepSize = 2.sp
                                                            )

                                                        )
                                                    }
                                                } else {
                                                    SubcomposeAsyncImageContent()
                                                }
                                            }
                                        }



                                        constants.spacer(4)

                                        Text(
                                            item?.username ?: "",
                                            fontFamily = constants.fontFamily(1),
                                            color = newBlack,
                                            fontSize = constants.textUnit(16)
                                        )

                                    }

                                }
                            }
                        }
                    }
                    else {

                        when {
                            network.value == NetworkStatus.Offline  &&  search_Profile_Content.value.isEmpty() -> {
                                Column(
                                    modifier = Modifier
                                        //.padding(bottom = 48.dp)
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .padding(horizontal = 16.dp)
                                    , verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Image(painterResource(R.drawable.nointernerdesign), "")
                                    Text(constants.activity.getString(R.string.no_Internet) , color = newBlack,fontSize = constants.textUnit(16)
                                        , fontFamily = constants.fontFamily(0)
                                        , textAlign = TextAlign.Center)

                                }
                            }

                            isLoading && search_Profile_Content.value.isEmpty() -> {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(400.dp)
                                    , verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    LottiAnimation(2)
                                }
                            }

                            errorMessage?.isNotEmpty() == true -> {


                                Common_API_Fail(
                                    failure
                                    , onReTryClick = {
                                        retry = retry + 5678
                                    }
                                )
                            }

                            search_username.value.isNotEmpty() && !isLoading && search_Profile_Content.value.isEmpty() -> {
                                // no data
                                Column(
                                    modifier = Modifier
                                        //.padding(bottom = 48.dp)
                                        .fillMaxWidth()
                                        .height(400.dp)
                                        .padding(horizontal = 16.dp , vertical = 16.dp)
                                    , verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Image(painterResource(R.drawable.emptyusearchrento), "")
                                    Text("We couldn’t find anything!")
                                    Text(
                                        "Try searching for something else.",
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }

                            search_Profile_Content.value.isNotEmpty() -> {
                                LazyColumn (
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(min = 80.dp, max = 400.dp)
                                        .border(1.dp , Color(0xffCECECE) , RoundedCornerShape(6.dp))
                                        .padding(horizontal = 16.dp , vertical = 16.dp)
                                ) {
                                    item {
                                        search_Profile_Content.value.forEachIndexed { index, option ->

                                            ListItem(
                                                headlineContent = {
                                                    Text(
                                                        option.username,
                                                        fontSize = constants.textUnit(14),
                                                        fontFamily = constants.fontFamily(1),
                                                        color = Color(0xff575757),
                                                    )
                                                },
                                                supportingContent = {
                                                    Text(
                                                        option.name,
                                                        fontSize = constants.textUnit(12),
                                                        fontFamily = constants.fontFamily(1),
                                                        color = Color(0XFF7E7E7E),
                                                    )
                                                },
                                                leadingContent = {
                                                    Box(
                                                        modifier = Modifier
                                                            .size(36.dp)
                                                            .clip(CircleShape)
                                                            .background(newLightBlue),
                                                        contentAlignment = Alignment.Center
                                                    ) {

                                                        SubcomposeAsyncImage(
                                                            model = option?.profile_image ?: "",
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
                                                                    ,
                                                                    contentAlignment = Alignment.Center
                                                                ) {
                                                                    Text(
                                                                        text = option?.username.takeIf { it?.isNotEmpty() == true }
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
                                                , colors = ListItemColors(
                                                    containerColor = newWhite,
                                                    headlineColor = Color.Black,
                                                    leadingIconColor = Color.DarkGray,
                                                    overlineColor = Color.Gray,
                                                    supportingTextColor = Color.Gray,
                                                    trailingIconColor = Color.LightGray,
                                                    disabledHeadlineColor = Color.Gray.copy(alpha = 0.5f),
                                                    disabledLeadingIconColor = Color.Gray.copy(alpha = 0.5f),
                                                    disabledTrailingIconColor = Color.Gray.copy(
                                                        alpha = 0.5f
                                                    )
                                                ), modifier = Modifier
                                                    .noRippleClickable {
                                                        viewModel.toggleshowTABars(false)
                                                        constants.Profile_ViewModel.add_Selected_User_Name(
                                                            option.username ?: "Unknown"
                                                        )

                                                        //new flowwewwwwwww
                                                        constants.Profile_ViewModel.add_BF_Handler(
                                                            Profile_Handle_Back(
                                                                current_UsedId = AppPreferences.getUserId(),
                                                                other_UserId = option?.user_id ?: 0,
                                                                ff_User_Name = option?.username
                                                                    ?: "",
                                                                ff_Fw_Count = option.followers,
                                                                ff_Fg_Count = option.following,
                                                                // is_Search_Enabled = is_Search_Enabled.value,
                                                                // search_Text = search_Text.value
                                                            )
                                                        )

                                                        /// println("ITEM PROFILE STRUCTURE __ ${is_Search_Enabled.value} -- ${constants.Profile_ViewModel.profile_BF_Handler.value}")

                                                        println("GIVEN OTHER USER ID -- ${constants.Profile_ViewModel.get_Other_User_Id()}")

                                                        constants.Profile_ViewModel.addProfile(
                                                            option?.user_id ?: 0
                                                        )
                                                        constants.Profile_ViewModel.add_Selected_Profile_Id(
                                                            id = option.user_id ?: 0
                                                        )

                                                        // if (view_Details_Data.value?.user_id == AppPreferences.)
                                                        viewModel.toggleshowBABars(false)
                                                        navHostController.navigate(VideosScreenFlow.Other_Profile_Structure.route)
                                                    }
                                            )
                                            HorizontalDivider()
                                        }
                                    }
                                }
                            }
                        }
                    }

                    constants.spacer(8)

                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Brush.verticalGradient(newPurpleGradient))
                            .noRippleClickable {
                                if (network.value == NetworkStatus.Online) {
                                    ClickHelper.getInstance().clickOnce {
                                        when {
                                            search_username.value.isNotEmpty() -> {
                                                focusManager.clearFocus()
                                              //  constants.Search_ViewModel.clear_profile_SearchData()
                                                constants.API_Vm.isLoading_ProfileS = false
                                                constants.API_Vm.totalPages_ProfileS = 1
                                                constants.API_Vm.errorMessage_ProfileS = null

                                                viewModel.toggleshowTABars(false)
                                                viewModel.toggleshowBABars(false)

                                                constants.Search_ViewModel.enable_Search_Results()
                                            }

                                            else -> GlobalSnackbar.show("Enter Location or Property Name")
                                        }
                                    }
                                } else {
                                    toast(constants.activity.getString(R.string.no_Internet))
                                }
                            }
                        , contentAlignment = Alignment.Center
                    ){
                        CommonText("Search Sellers",
                            Color.White,
                            14,
                            0)
                    }
                }
            }

        }
    }

    LaunchedEffect(!go_to_Search_Results.value) {

        viewModel.toggleshowTABars(false)
        viewModel.toggleshowBABars(true)
    }

    AnimatedVisibility(
        visible = go_to_Search_Results.value
        , enter = slideInHorizontally(tween(600)) { it }
        , exit = slideOutHorizontally(tween(600)) { it }
    ) {
        //Search_Result_Structure()

        LaunchedEffect(go_to_Search_Results.value , key2 = go_to_Search_Results.value) {
            if (go_to_Search_Results.value){
                println("Search result page truue")
                viewModel.toggleshowTABars(false)
                viewModel.toggleshowBABars(false)
            }
            else {
                println("Search result page false")
                viewModel.toggleshowTABars(false)
                viewModel.toggleshowBABars(true)
            }
        }

        if (constants.Search_ViewModel.selectedOption_SEARCHTYPE != "Sellers"){
            Search_Result_Screen(notchPadding , navHostController , search_State, search_Area, minInput, maxInput ,viewModel)
        }
        else {
            //constants.API_Vm.isLoading_ProfileS = false
            Search_Profile_Result_Screen(viewModel , navHostController)
        }


    }

    BackHandler {
        when {
            go_to_Search_Results.value -> !go_to_Search_Results.value

            else -> {
                viewModel.selectedBABTab(0)
                viewModel.toggleshowBABars(true)
                viewModel.toggleshowTABars(true)
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetSelector(
    minBudget: MutableState<Long>,
    maxBudget: MutableState<Long>
) {

    val MIN_LIMIT = 2000L
    val MAX_LIMIT = 10_000_000L

    val minExpanded = remember { mutableStateOf(false) }
    val maxExpanded = remember { mutableStateOf(false) }

    var isError by remember { mutableStateOf(false) }
    var minError by remember { mutableStateOf(false) }
    var maxError by remember { mutableStateOf(false) }

    LaunchedEffect(minBudget.value, maxBudget.value) {
        isError = minBudget.value > maxBudget.value
        minError = minBudget.value < MIN_LIMIT
        maxError = maxBudget.value < MIN_LIMIT
    }

    val focusManager = LocalFocusManager.current

    var minReadOnly by remember { mutableStateOf(false) }
    var maxReadOnly by remember { mutableStateOf(false) }

    val rangeSteps = remember {
        val list = mutableListOf<Long>()
        var cur = 2000L
        while (cur <= MAX_LIMIT) {
            list.add(cur)
            cur += when (cur) {
                in 2000..10_000 -> 2000
                in 10_000..50_000 -> 5000
                in 50_000..100_000 -> 10_000
                in 100_000..5_000_000 -> 50_000
                else -> 100_000
            }
        }
        list
    }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // ======================== MIN FIELD ============================
            ExposedDropdownMenuBox(
                expanded = minExpanded.value,
                onExpandedChange = { }
            )
            {
                Column {
                    Text("Min", color = Color.Black, fontSize = 14.sp)

                    Row(
                        modifier = Modifier
                            .width(172.dp)
                            .border(
                                1.dp,
                                if (isError || minError) Color.Red else Color.Gray,
                                RoundedCornerShape(6.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = if (minBudget.value == 0L) "" else "₹" + "%,d".format(minBudget.value),
                            onValueChange = { input ->
                                val filtered = input.filter { it.isDigit() }

                                if (filtered.isEmpty()) {
                                    minBudget.value = 0L
                                    constants.Search_ViewModel.updateMinInViewModel(0L)
                                } else {
                                    val value =
                                        (filtered.toLongOrNull() ?: 0L).coerceAtMost(MAX_LIMIT)
                                    minBudget.value = value
                                    constants.Search_ViewModel.updateMinInViewModel(value)
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.NumberPassword,
                            ),
                            readOnly = minReadOnly,
                            singleLine = true,
                            placeholder = { Text("₹2,000") },
                            modifier = Modifier
                                .weight(8f)
                                .menuAnchor()
                                .onFocusChanged { f ->
                                    if (!f.isFocused && minBudget.value == 0L) {
                                        minBudget.value = MIN_LIMIT
                                        constants.Search_ViewModel.updateMinInViewModel(MIN_LIMIT)
                                    }
                                },
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            )
                        )

                        Icon(
                            painterResource(R.drawable.arrowdown),
                            contentDescription = "",
                            modifier = Modifier
                                .weight(2f)
                                .noRippleClickable {
                                    focusManager.clearFocus()
                                    minReadOnly = true
                                    minExpanded.value = true
                                }
                        )
                    }
                }

                ExposedDropdownMenu(
                    expanded = minExpanded.value,
                    onDismissRequest = {
                        focusManager.clearFocus()
                        minReadOnly = false
                        minExpanded.value = false
                    }
                ) {
                    rangeSteps.forEach { value ->
                        DropdownMenuItem(
                            text = { Text("₹" + "%,d".format(value)) },
                            onClick = {
                                minReadOnly = false
                                minBudget.value = value
                                constants.Search_ViewModel.updateMinInViewModel(value)

                                if (value > maxBudget.value) {
                                    maxBudget.value = value
                                    constants.Search_ViewModel.updateMaxInViewModel(value)
                                }

                                focusManager.clearFocus()
                                minExpanded.value = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.width(10.dp))

            // ======================== MAX FIELD ============================
            ExposedDropdownMenuBox(
                expanded = maxExpanded.value,
                onExpandedChange = { }
            )
            {
                Column {
                    Text("Max", color = Color.Black, fontSize = 14.sp)

                    Row(
                        modifier = Modifier
                            .width(172.dp)
                            .border(
                                1.dp,
                                if (isError || maxError) Color.Red else Color.Gray,
                                RoundedCornerShape(6.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = if (maxBudget.value == 0L) "" else "₹" + "%,d".format(maxBudget.value),
                            onValueChange = { input ->
                                val filtered = input.filter { it.isDigit() }

                                if (filtered.isEmpty()) {
                                    maxBudget.value = 0L
                                    constants.Search_ViewModel.updateMaxInViewModel(0L)
                                } else {
                                    val value =
                                        (filtered.toLongOrNull() ?: 0L).coerceAtMost(MAX_LIMIT)
                                    maxBudget.value = value
                                    constants.Search_ViewModel.updateMaxInViewModel(value)
                                }
                            },
                            readOnly = maxReadOnly,
                            singleLine = true,
                            placeholder = { Text("₹2,000") },
                            modifier = Modifier
                                .weight(8f)
                                .menuAnchor()
                                .onFocusChanged { f ->
                                    if (!f.isFocused && maxBudget.value == 0L) {
                                        maxBudget.value = MIN_LIMIT
                                        constants.Search_ViewModel.updateMaxInViewModel(MIN_LIMIT)
                                    }
                                },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.NumberPassword,
                            ),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            )
                        )

                        Icon(
                            painterResource(R.drawable.arrowdown),
                            contentDescription = "",
                            modifier = Modifier
                                .weight(2f)
                                .noRippleClickable {
                                    focusManager.clearFocus()
                                    maxReadOnly = true
                                    maxExpanded.value = true
                                }
                        )
                    }
                }

                ExposedDropdownMenu(
                    expanded = maxExpanded.value,
                    onDismissRequest = {
                        focusManager.clearFocus()
                        maxReadOnly = false
                        maxExpanded.value = false
                    }
                ) {
                    rangeSteps.forEach { value ->
                        DropdownMenuItem(
                            text = { Text("₹" + "%,d".format(value)) },
                            onClick = {
                                maxReadOnly = false
                                maxBudget.value = value
                                constants.Search_ViewModel.updateMaxInViewModel(value)

                                if (value < minBudget.value) {
                                    minBudget.value = value
                                    constants.Search_ViewModel.updateMinInViewModel(value)
                                }

                                focusManager.clearFocus()

                                maxExpanded.value = false
                            }
                        )
                    }
                }
            }
        }

        // ERROR TEXTS
        AnimatedVisibility(visible = isError) {
            Text("Max should be greater than Min", color = Color.Red)
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search_Main_Property(
    focusManager1: FocusManager,
    viewModel: Common_H_ViewModel,
    search_State: MutableState<Int>,
    selectedRange: ClosedFloatingPointRange<Float>
) {

    val context = LocalContext.current
    val network = rememberNetworkStatus()
    val placesClient = Places.createClient(context)


    var api_State = remember { mutableStateOf(-1) }
    var api_State2 = remember { mutableStateOf(-1) }

//    var minexpanded = remember { mutableStateOf(false) }
//    var maxexpanded = remember { mutableStateOf(false) }

    val MIN_LIMIT = 2000L
    val MAX_LIMIT = 10_000_000L

    var minBudget by remember { mutableStateOf("2000") }
    var maxBudget by remember { mutableStateOf("10000000") }

    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(minBudget, maxBudget) {
        val minVal = minBudget.toLongOrNull() ?: MIN_LIMIT
        val maxVal = maxBudget.toLongOrNull() ?: MAX_LIMIT

        isError = minVal > maxVal
    }
    val popular_Cities = constants.Search_ViewModel.popular_Cities.collectAsState()




    if (network.value == NetworkStatus.Online) {
        DisposableEffect(Unit) {
            if (popular_Cities.value.isEmpty()) {
                constants.API_Vm.get_Popular_Cities_Saerch(
                    user_id = AppPreferences.getUserId()
                )
                { aPI_Result_Handling ->
                    when (aPI_Result_Handling) {
                        is API_Result_Handling.Loading -> {
                            api_State.value = 0
                        }

                        is API_Result_Handling.Deactivated -> {
                            //resultCallback(5)
                        }

                        is API_Result_Handling.Error -> {
                            api_State.value = 1
                        }

                        is API_Result_Handling.Success -> {
                            api_State.value = 2
                        }

                        is API_Result_Handling.NoData -> {
                            api_State.value = 3
                        }
                    }
                }
            }


            onDispose {
                if (search_State.value == 4) {
                    constants.Search_ViewModel.search_Land_Type.value = 4
                    constants.Search_ViewModel.search_State.value = 4
                    constants.Search_ViewModel.selectedOption_SEARCHTYPE = "Sellers"
                    search_State.value = 4
                }
            }
        }
    }
    else {
        api_State.value = -1
        GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
    }



    val search_Area = constants.Search_ViewModel.search_Area

    // Observe ViewModel values as State
    val minRangeFromVM by constants.Search_ViewModel.minRangeRefs
    val maxRangeFromVM by constants.Search_ViewModel.maxRangeRefs

    println("MIN MAX VALUE -- ${constants.Search_ViewModel.minRangeRefs} -- ${constants.Search_ViewModel.maxRangeRefs}")

    // Convert ViewModel strings to Float values
    val minValue = minRangeFromVM.toFloat() ?: 2_000f
    val maxValue = maxRangeFromVM.toFloat() ?: 10_000_000f

    // Local slider range state
    var sliderRange by remember(minValue, maxValue) {
        mutableStateOf(minValue..maxValue)
    }

    // Display values for UI
    val minDisplay = remember(sliderRange) { "%,.0f".format(sliderRange.start) }
    val maxDisplay = remember(sliderRange) { "%,.0f".format(sliderRange.endInclusive) }

    // Sync when VM values change
    LaunchedEffect(minRangeFromVM, maxRangeFromVM) {
        val newMin = minRangeFromVM.toFloat() ?: 2_000f
        val newMax = maxRangeFromVM.toFloat() ?: 10_000_000f
        sliderRange = newMin..newMax
    }

    LaunchedEffect(Unit) {
        viewModel.toggleshowBABars(true)
    }

    Column(modifier = Modifier.fillMaxWidth()) {

        // City search input
        CityDropdown(
            modifier = Modifier,
            placesClient = placesClient,
            countryCode = "In",
            search_Area = search_Area
        )

        constants.spacer(8)

        /// popular cities

        when(api_State.value){
             0 -> {
                 CircularProgressIndicator()
             }

            1 -> {
                toast("Something went wrong while loading popular cities , try again later!!")
            }
            2 -> {
                if (popular_Cities.value.isNotEmpty()) {

                    Text(
                        "Popular locations",
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(0),
                        color = Color.Black, modifier = Modifier
                            //.padding(start = 8.dp)
                        //.align(Alignment.Start)
                    )

                    constants.spacer(2)



                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                        ,
                        horizontalArrangement = Arrangement.spacedBy(if (forTab())16.dp else 8.dp),
                        verticalArrangement = Arrangement.spacedBy(if (forTab())16.dp else 8.dp)
                    )
                    {

                        popular_Cities.value.filterNot { it?.city?.isEmpty() == true }.forEach { reportType ->
                            Box(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .clip(RoundedCornerShape(4.dp))
                                    .then(
                                        if (search_Area.value == (reportType?.city ?: ""))
                                            Modifier
                                                .border(1.dp , Brush.linearGradient(newPurpleGradientBorder) ,RoundedCornerShape(4.dp) )
                                                .background(Brush.verticalGradient(newPurpleGradient))
                                        else
                                            Modifier.background(newWhite).border(1.dp, newBlack, RoundedCornerShape(4.dp))
                                    )
                                    .noRippleClickable{
                                        ClickHelper.getInstance().clickOnce {
                                            //constants.Search_ViewModel.select_Popular_Cities(reportType.id)
                                            search_Area.value = reportType?.city ?: ""
                                            focusManager1.clearFocus()
                                            constants.Search_ViewModel.search_Area.value =
                                                reportType?.city ?: ""

                                            val minSaved =
                                                constants.Search_ViewModel.minRangeRefs.value.toFloat() ?: 0f
                                            val maxSaved =
                                                constants.Search_ViewModel.maxRangeRefs.value.toFloat() ?: 0f

                                            if (selectedRange.start != minSaved || selectedRange.endInclusive != maxSaved) {
                                                println("BUDGET UPDATING")
                                                constants.Search_ViewModel.update_Search_SF {
                                                    it.copy(
                                                        budget_from = selectedRange.start.toInt()
                                                            .toString()
                                                    )
                                                }
                                                constants.Search_ViewModel.update_Search_SF {
                                                    it.copy(
                                                        budget_to = selectedRange.endInclusive.toInt()
                                                            .toString()
                                                    )
                                                }
                                            }

                                            constants.Search_ViewModel.trigger_Search_Again.value = constants.Search_ViewModel.trigger_Search_Again.value + 12345
                                            constants.API_Vm.isLoading_PS_FF = true
                                            constants.API_Vm.totalPages_PS_FF = 1

                                            println("DATA ADED SUCCES -- ${constants.Search_ViewModel.selected_Sort_Filter_Fields.value}")
                                            constants.Search_ViewModel.enable_Search_Results()

                                        }
                                    }
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = reportType?.city ?: "",
                                    color = if (search_Area.value == (reportType?.city
                                            ?: "")
                                    ) newWhite else newBlack,
                                    fontSize = constants.textUnit(12)
                                )
                            }
                        }
                    }
                }
            }
        }

        Text(
            "Budget Range (₹)",
            fontSize = constants.textUnit(14),
            fontFamily = constants.fontFamily(0),
            color = Color.Black,
            modifier = Modifier.padding(top = 16.dp)
        )




        constants.spacer(8)

        // Min & Max Display Boxes

        BudgetSelector(
            minBudget = constants.Search_ViewModel.minRangeRefs ,
            maxBudget = constants.Search_ViewModel.maxRangeRefs
        )

        Spacer(modifier = Modifier.height(16.dp))


        Spacer(modifier = Modifier.height(24.dp))

        // Search Button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp , Brush.linearGradient(newPurpleGradientBorder) ,RoundedCornerShape(8.dp) )
                .background(Brush.verticalGradient(newPurpleGradient))
                .noRippleClickable {
                    if (network.value == NetworkStatus.Online) {
                        ClickHelper.getInstance().clickOnce {
                            when {
                                search_Area.value.isNotEmpty() -> {
                                    focusManager1.clearFocus()

                                    constants.Search_ViewModel.update_Search_SF {
                                        it.copy(
                                            budget_from = sliderRange.start.toInt().toString(),
                                            budget_to = sliderRange.endInclusive.toInt().toString()
                                        )
                                    }

                                    constants.Search_ViewModel.trigger_Search_Again.value += 12345
                                    constants.API_Vm.isLoading_PS_FF = true
                                    constants.API_Vm.totalPages_PS_FF = 1

                                    constants.Search_ViewModel.enable_Search_Results()
                                }

                                else -> GlobalSnackbar.show("Enter Location or Property Name")
                            }
                        }
                    } else {
                        toast(constants.activity.getString(R.string.no_Internet))
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Search",
                color = Color.White,
                fontSize = constants.textUnit(14),
                fontFamily = constants.fontFamily(0),
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
    }
}


@Composable
fun Search_Main_Profile(
    viewModel: Common_H_ViewModel,
    navHostController: NavHostController,
    viewModel1: Common_H_ViewModel,
    search_State: MutableState<Int>
) {



    val network = rememberNetworkStatus()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current



    val isLoading = constants.API_Vm.isLoading_ProfileS
    val errorMessage = constants.API_Vm.errorMessage_FF
    val currentPage = constants.API_Vm.currentPage_ProfileS
    val totalPages = constants.API_Vm.totalPages_ProfileS
    val listState = rememberLazyListState()

    val search_Profile_Content = constants.Search_ViewModel.profile_Search_Results.collectAsState()

    var search_username = constants.Search_ViewModel.search_Profile_Name

    var user_search_trigger = remember { mutableStateOf(false) }

    var failure = remember { mutableStateOf(false) }
    var retry by remember { mutableStateOf(0) }



    if (network.value == NetworkStatus.Online) {
        LaunchedEffect(search_username.value , retry) {
            if (search_username.value.length >= 3 && search_Profile_Content.value.isEmpty() &&  !constants.API_Vm.isLoading_ProfileS) {
                println("WHENPROFILE SEARCHED HITTING NORMAL 1625623636")
                //constants.API_Vm.isLoading_ProfileS = false
                constants.API_Vm.load_Profile_Search(
                    user_id = AppPreferences.getUserId(),
                    name = search_username.value,
                    page = 1,
                )
            }
        }

        // Pagination logic stays the same
        LaunchedEffect(listState, currentPage, isLoading, totalPages) {
            println("WHENPROFILE SEARCHED HITTING Pagination 3u7327327")
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
                        println("CURRENT PAGE - ${currentPage}")

                        constants.API_Vm.load_Profile_Search(
                            user_id = AppPreferences.getUserId(),
                            name = search_username.value,
                            page = currentPage + 1,
                        )
                    }
                }
        }
    } else {
        GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
    }

    Box(
        modifier = Modifier
            //.menuAnchor()
            .clip(RoundedCornerShape(4.dp))
            .background(Color.White)
            //.border(1.dp, newGray, RoundedCornerShape(4.dp))
           // .padding(horizontal = 8.dp, vertical = 6.dp)
    )
    {
        OutlinedTextField(
            value = search_username.value,
            onValueChange = {
                search_username.value = it
                println("NETWORK --  ${network.value }")
                    if (it.length >= 3) {
                        user_search_trigger.value = true
                        constants.API_Vm.isLoading_ProfileS = false
                        constants.API_Vm.totalPages_ProfileS = 1
                    }

                if (network.value == NetworkStatus.Offline){
                    GlobalSnackbar.show("It Seems your are offline !!.Refresh again")

                }
            },
            textStyle = TextStyle(
                fontSize = constants.textUnit(14),
                fontFamily = constants.fontFamily(1),
                color = Color.Black
            ),
            placeholder = {
                Text(
                    "Search by username...",
                    fontSize = constants.textUnit(14),
                    fontFamily = constants.fontFamily(2),
                    color = Color.Black
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.search_icon),
                    contentDescription = "Search",
                    tint = Color.Black, modifier = Modifier
                        .size(16.dp)
                )
            },
            trailingIcon = {
                if (search_username.value.isNotEmpty() || search_Profile_Content.value.isNotEmpty()) {
                    Image(
                        painter = painterResource(R.drawable.search_text_clear),
                        "",
                        modifier = Modifier
                            .noRippleClickable{
                                search_username.value = ""
                                println("PROFILE SEARCH DATA --search_Profile_Content.value ${search_Profile_Content.value.size}")
                                //user_search_trigger.value = false
                                constants.Search_ViewModel.clear_profile_SearchData()

                            }
                    )
                }
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                unfocusedBorderColor = Color(0xffCECECE),
                focusedBorderColor = Color(0xff575757),
                focusedContainerColor = Color.White,
                unfocusedTextColor = newBlack,
              focusedTextColor = newBlack
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(1.dp , newBlack , RoundedCornerShape(6.dp))
        )
    }




}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search_Result_Screen(
    notchPadding: State<Dp>,
    navHostController: NavHostController,
    search_State: MutableState<Int>,
    search_Area: MutableState<String>,
    minInput: MutableState<String>,
    maxInput: MutableState<String>,
    viewModel: Common_H_ViewModel,
) {



    viewModel.toggleshowBABars(false)
    val isLoading = constants.API_Vm.isLoading_PS_FF
    val errorMessage = constants.API_Vm.errorMessage_FF
    val currentPage = constants.API_Vm.currentPage_PS_FF
    val totalPages = constants.API_Vm.totalPages_PS_FF
    val listState = rememberLazyListState()

    var retry by remember { mutableStateOf(0) }
    var failure = remember { mutableStateOf(false) }

    val network = rememberNetworkStatus()

    val search_Result_Content by constants.Search_ViewModel.search_Results.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val placesClient = Places.createClient(context)



    val cmt_btm_Sheet = constants.Common_H_ViewModel.comment_btm_Sheet.collectAsState()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val apply_FS = constants.Search_ViewModel.is_Search_FS_Applied.collectAsStateWithLifecycle()
    val apply_FS_Count = constants.Search_ViewModel.selected_Categories_Count.collectAsStateWithLifecycle()

    val selected_SF = constants.Search_ViewModel.selected_Sort_Filter_Fields.collectAsStateWithLifecycle()


    if (network.value == NetworkStatus.Online) {

        LaunchedEffect (Unit, constants.Search_ViewModel.trigger_Search_Again.value, retry) {

            if (apply_FS.value) {
                println("1234567890-")
//                constants.Search_ViewModel.total_SearchResults_Counts.value = 0
                constants.API_Vm.load_Search_SF(
                    user_id = AppPreferences.getUserId(),
                    search_text = search_Area.value,
                    short_by = selected_SF.value?.short_by ?: 0,

                    page = 1,
                    limit = 10,

                    land_categorie_id = selected_SF.value?.land_categorie_id?.joinToString(","),
                    land_type_id = constants.Search_ViewModel.search_Land_Type.value.toString(),

                    property_area_unit = selected_SF.value?.property_area_unit,
                    property_area_from = selected_SF.value?.property_area_from,
                    property_area_to = selected_SF.value?.property_area_to,

                    rent_type = selected_SF.value?.rent_type?.joinToString { "," },

                    budget_from = selected_SF.value?.budget_from,
                    budget_to = selected_SF.value?.budget_to,

                    posted_by = selected_SF.value?.posted_by?.joinToString(","),

                    posted_date_time = System.currentTimeMillis().toString(),
                    posted_date_id = selected_SF.value?.posted_date?.joinToString { "," },

                    availability_for = selected_SF.value?.available_for?.joinToString(","),
                    availability_from_time = System.currentTimeMillis().toString(),
                    availability_from_id = selected_SF.value?.available_from?.joinToString { "," },

                    floor_plan = selected_SF.value?.floor_plan?.joinToString(","),
                    floor_preferences = selected_SF.value?.floor_preferences?.joinToString(","),

                    property_facing = selected_SF.value?.property_facing?.joinToString(","),

                    food_preferences = selected_SF.value?.food_preference?.joinToString(","),
                    pets_allowed = selected_SF.value?.pets_allowed?.joinToString(","),

                    furnishing_status = selected_SF.value?.furnishing_status?.joinToString(","),
                    agreement = selected_SF.value?.agreement_type?.joinToString { "," },

                    with_photo = selected_SF.value?.with_photos,

                    parking_available = selected_SF.value?.parking_available?.joinToString(","),
                    amenities = selected_SF.value?.amenities?.joinToString(","),
                    property_highlights = selected_SF.value?.property_highlights?.joinToString(","),

                    no_of_open_sides = selected_SF.value?.no_of_open_sides?.joinToString(","),
                    no_of_bathrooms = selected_SF.value?.bedrooms?.joinToString { "," },

                    authority_approved = selected_SF.value?.approved?.joinToString(",")
                )

                constants.API_Vm.load_Search_SF(
                    user_id = AppPreferences.getUserId(),
                    search_text = search_Area.value,
                    short_by = selected_SF.value?.short_by ?: 0,
                   // recently_posted_date = System.currentTimeMillis().toString(),
                    land_categorie_id = selected_SF.value?.land_categorie_id?.joinToString(","),
                    land_type_id = constants.Search_ViewModel.search_Land_Type.value.toString(),
                    property_area_unit = selected_SF.value?.property_area_unit ?: "sq",
                    property_area_from = selected_SF.value?.property_area_from,
                    property_area_to = selected_SF.value?.property_area_to,
                    budget_from = selected_SF.value?.budget_from,
                    budget_to = selected_SF.value?.budget_to,
                    posted_by = selected_SF.value?.posted_by?.joinToString(","),
                    //ownership = selected_SF.value?.ownership?.joinToString(","),
//                    availability_status = selected_SF.value?.availability_status?.joinToString(","),
                    floor_plan = selected_SF.value?.floor_plan?.joinToString(","),
                    furnishing_status = selected_SF.value?.furnishing_status?.joinToString(","),
                    parking_available = selected_SF.value?.parking_available?.joinToString(","),
                    no_of_open_sides = selected_SF.value?.no_of_open_sides?.joinToString(","),
                    floor_preferences = selected_SF.value?.floor_preferences?.joinToString(","),
                    property_facing = selected_SF.value?.property_facing?.joinToString(","),
                    amenities = selected_SF.value?.amenities?.joinToString(","),
                    property_highlights = selected_SF.value?.property_highlights?.joinToString(","),
                    //business_type = selected_SF.value?.business_type?.joinToString(","),
                    authority_approved = selected_SF.value?.approved?.joinToString(","),
                    page = 1,
                )
            }
            else {
                println("WHAT ARE THE SEARCHES $$$$${constants.Search_ViewModel.maxRangeRefs.value.toInt()}-###${constants.Search_ViewModel.minRangeRefs.value.toInt()}-n ${search_State.value} -- ${search_Area.value} -- ${minInput.value.toInt()} -${maxInput.value.toInt()}-" )
                constants.Search_ViewModel.total_SearchResults_Counts.value = 0
                constants.API_Vm.load_Property_Search(
                    user_id = AppPreferences.getUserId(),
                    search_type = search_State.value,
                    search_text = search_Area.value,
                    min_price = constants.Search_ViewModel.minRangeRefs.value.toInt(),
                    //minInput.value.toInt(),
                    max_price = constants.Search_ViewModel.maxRangeRefs.value.toInt(),
                    //maxInput.value.toInt(),
                    page = 1,
                )
            }



//            onDispose {
//                constants.Search_ViewModel.search_Area.value = search_Area.value
//                println("Profile_FF_Structure disposed")
//
//            }
        }


        // Detect when near end of list
        LaunchedEffect(listState, currentPage, isLoading, totalPages) {

            if (apply_FS.value) {
                println("WHEN SEARCHED HITTING")
                snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                    .collect { lastVisibleItemIndex ->
                        val totalItems = listState.layoutInfo.totalItemsCount
                        val loadMoreThreshold = 4// 👈 trigger when 4 items from the end

                        if (
                            lastVisibleItemIndex != null &&
                            totalItems > 0 &&
                            lastVisibleItemIndex >= totalItems - loadMoreThreshold &&
                            !isLoading &&
                            currentPage < totalPages
                        ) {
                            println("CURRENT PAGE - ${currentPage}")
                            //constants.API_Vm.loadCategories(currentPage + 1)
                            constants.API_Vm.load_Search_SF(
                                user_id = AppPreferences.getUserId(),
                                search_text = search_Area.value,
                                short_by = selected_SF.value?.short_by ?: 0,

                                page = currentPage + 1,
                                limit = 10,

                                land_categorie_id = selected_SF.value?.land_categorie_id?.joinToString(","),
                                land_type_id = constants.Search_ViewModel.search_Land_Type.value.toString(),

                                property_area_unit = selected_SF.value?.property_area_unit,
                                property_area_from = selected_SF.value?.property_area_from,
                                property_area_to = selected_SF.value?.property_area_to,

                                rent_type = selected_SF.value?.rent_type?.joinToString { "," },

                                budget_from = selected_SF.value?.budget_from,
                                budget_to = selected_SF.value?.budget_to,

                                posted_by = selected_SF.value?.posted_by?.joinToString(","),

                                posted_date_time = System.currentTimeMillis().toString(),
                                posted_date_id = selected_SF.value?.posted_date.toString(),

                                availability_for = selected_SF.value?.available_for?.joinToString(","),
                                availability_from_time = System.currentTimeMillis().toString(),
                                availability_from_id = selected_SF.value?.available_from.toString(),

                                floor_plan = selected_SF.value?.floor_plan?.joinToString(","),
                                floor_preferences = selected_SF.value?.floor_preferences?.joinToString(","),

                                property_facing = selected_SF.value?.property_facing?.joinToString(","),

                                food_preferences = selected_SF.value?.food_preference?.joinToString(","),
                                pets_allowed = selected_SF.value?.pets_allowed?.joinToString(","),

                                furnishing_status = selected_SF.value?.furnishing_status?.joinToString(","),
                                agreement = selected_SF.value?.agreement_type?.joinToString { "," },

                                with_photo = selected_SF.value?.with_photos,

                                parking_available = selected_SF.value?.parking_available?.joinToString(","),
                                amenities = selected_SF.value?.amenities?.joinToString(","),
                                property_highlights = selected_SF.value?.property_highlights?.joinToString(","),

                                no_of_open_sides = selected_SF.value?.no_of_open_sides?.joinToString(","),
                                no_of_bathrooms = selected_SF.value?.bedrooms?.joinToString { "," },

                                authority_approved = selected_SF.value?.approved?.joinToString(",")
                            )

                        }
                    }
            }
            else {
                println("WHEN SEARCHED HITTING")
                snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                    .collect { lastVisibleItemIndex ->
                        val totalItems = listState.layoutInfo.totalItemsCount
                        val loadMoreThreshold = 4// 👈 trigger when 4 items from the end

                        if (
                            lastVisibleItemIndex != null &&
                            totalItems > 0 &&
                            lastVisibleItemIndex >= totalItems - loadMoreThreshold &&
                            !isLoading &&
                            currentPage < totalPages
                        ) {
                            println("CURRENT PAGE - ${currentPage}")
                            //constants.API_Vm.loadCategories(currentPage + 1)
                            constants.API_Vm.load_Property_Search(
                                user_id = AppPreferences.getUserId(),
                                search_type = search_State.value,
                                search_text = search_Area.value,
                                min_price = constants.Search_ViewModel.minRangeRefs.value.toInt(),
                                //minInput.value.toInt(),
                                max_price = constants.Search_ViewModel.maxRangeRefs.value.toInt(),
                                page = currentPage + 1,
                            )

                        }
                    }
            }
        }
    }
    else {
        GlobalSnackbar.show("It Seems your are offline !!.Refresh again")
    }

    val cmt_Clicked_Index = remember { mutableStateOf(0) }


    var report_BS = remember { mutableStateOf(false) }

    val report_success = constants.Profile_ViewModel.report_Submit_Success.collectAsState()

    val report_Options = constants.Profile_ViewModel.profile_Report_Options.collectAsState()


    var reported_Index by remember { mutableStateOf(-1) }


    BackHandler {
        ClickHelper.getInstance().clickOnce {

            if (ClickGuard.canClick()) {
                when {
                    cmt_btm_Sheet.value -> {
                        println("909090909090909090")
                        !cmt_btm_Sheet.value
                    }
                    report_BS.value -> {
                        println("343434343434344")
                        !report_BS.value
                    }
                    !cmt_btm_Sheet.value && !report_BS.value -> {
                        viewModel.toggleshowBABars(true)
                        println("121212121212121221122112")
                        constants.Search_ViewModel.minRangeRefs.value = 2000
                        constants.Search_ViewModel.maxRangeRefs.value = 10000000

                        constants.Search_ViewModel.updateMinInViewModel(2000)
                        constants.Search_ViewModel.updateMaxInViewModel(10_000_000)
                        //search_Area.value = ""
                       // constants.Search_ViewModel.search_Area.value = ""
                        constants.Search_ViewModel.dismiss_Search_Results()
                    }
                    else -> {
                        println("787878878778788778")
                        constants.Search_ViewModel.minRangeRefs.value = 2000
                        constants.Search_ViewModel.maxRangeRefs.value = 10000000

                        constants.Search_ViewModel.updateMinInViewModel(2000)
                        constants.Search_ViewModel.updateMaxInViewModel(10_000_000)

                        viewModel.toggleshowBABars(true)
                        //search_Area.value = ""
                        //constants.Search_ViewModel.search_Area.value = ""
                        constants.Search_ViewModel.dismiss_Search_Results()
                    }

                }
            }
        }
    }


    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(newWhite)
            .padding(top = if (forTab()) 16.dp else  notchPadding.value)
            .noRippleClickable{}
        , verticalArrangement = Arrangement.spacedBy(8.dp)
    )
    {

        // top bar
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
            , contentAlignment = Alignment.Center
        )
        {
            Backer(
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                println("#Back Search")
                constants.Search_ViewModel.minRangeRefs.value = 2000
                constants.Search_ViewModel.maxRangeRefs.value = 10000000

                constants.Search_ViewModel.not_Apply_FS()
                constants.Search_ViewModel.clear_Selected_Fields_Form4()
                constants.Search_ViewModel.search_Area.value = search_Area.value

                viewModel.toggleshowBABars(true)
                constants.Search_ViewModel.dismiss_Search_Results()
                viewModel.toggleshowBABars(true)

            }

            val title = when(search_State.value){
                1 -> "Residential"
                2 -> "Commercial"
                3 -> "Agriculture"
                else -> ""
            }
            Text(title
                , fontSize = constants.textUnit(16)
                , fontFamily = constants.fontFamily(1)
                , color = Color(0xff666666)
                , modifier = Modifier
                    .align(Alignment.Center)
            )

        }

        /// search bar with filter
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
            , verticalAlignment = Alignment.CenterVertically
            , horizontalArrangement = Arrangement.SpaceBetween
        )
        {

            CityDropdown(modifier = Modifier.height(56.dp).weight(8f), placesClient, "In", search_Area)

            Spacer(modifier = Modifier.padding(4.dp))

            AsyncImage(
                model = if (apply_FS_Count.value != 0)R.drawable.filterappliedrento else R.drawable.filter
                ,""
                , modifier = Modifier
                    .height(56.dp)
                    .weight(1.5f)
                    .noRippleClickable{
                        // show_Sort_Filter.value = false
                        if (network.value == NetworkStatus.Online) {
                            navHostController.navigate(SearchScreenFlow.Search_Filter_Sort.route)
                        } else {
                            GlobalSnackbar.show("It Seems your are offline !!.Refresh again")
                        }
                    }
            )
        }

        /// text to show counts of results

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
            , horizontalArrangement = Arrangement.Start
            , verticalAlignment = Alignment.CenterVertically
        )
        {

            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(color = newGray)){
                        append("showing ${ constants.Search_ViewModel.total_SearchResults_Counts.value ?: 0} results")
                    }
//                    withStyle(style = SpanStyle(color = newBlack)){
//                        append("“${search_Area?.value ?:""}...”")
//                    }

                }
                , fontSize = constants.textUnit(14)
                , fontFamily = constants.fontFamily(2)
            )
        }

        // search results item lazy column


        println("LOADING STATES -- ${isLoading} -- ${search_Result_Content} -- ${currentPage}")

        when {
            !errorMessage.isNullOrEmpty() -> {
                Box (
                    modifier = Modifier
                        .fillMaxSize()
                    ,contentAlignment = Alignment.Center
                ) {

                    API_Fail_UI(onReTryClick = {
                        retry = retry + 213435
                        constants.API_Vm.errorMessage_FF = ""
                    })
                }
            }

            network.value == NetworkStatus.Offline && search_Result_Content.isEmpty() -> {
               //constants.activity.getString(R.string.no_Internet) 
                Column(
                    modifier = Modifier
                        //.height(600.dp)
                        .fillMaxSize()
                        .fillMaxWidth()
                    , verticalArrangement = Arrangement.Center
                    , horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Image(painterResource(R.drawable.nointernerdesign), "")

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        constants.activity.getString(R.string.no_Internet)
                        , modifier = Modifier
                           // .padding(start = 24.dp , end = 24.dp)
                            .padding(horizontal = 36.dp)
                    )
                }
            }


            isLoading
                  //  && search_Result_Content.isEmpty()
                    && currentPage == 1 -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LottiAnimation(2)
                }
            }



            !isLoading && search_Result_Content.isEmpty() -> {
                constants.Search_ViewModel.total_SearchResults_Counts.value = 0
                // no data
                Column(
                    modifier = Modifier
                        .padding(bottom = 48.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painterResource(R.drawable.emptyusearchrento), "")
                    Text("We couldn’t find anything!" ,  color = Color.Black
                        , fontSize = constants.textUnit(16)
                        , fontFamily = constants.fontFamily(0))
                    Text(
                        "Try searching for something else.",
                        textAlign = TextAlign.Center
                        , color = Color.Black
                        , fontSize = constants.textUnit(14)
                        , fontFamily = constants.fontFamily(0)
                    )
                }
            }

            search_Result_Content.isNotEmpty() -> {

                constants.Reels_ViewModel.setReelsContent(
                    search_Result_Content
                   /// posts.value.map { it.toReelsData() }  // map each element to Get_Reels_Data
                )
                LazyColumn (
                    state = listState
                ){
                    itemsIndexed(
                        items = search_Result_Content,
                        key = { index, item -> item.user_post_id } // 👈 stable key
                    ){
                            index,item ->

                        println("r  ---- ${item.total_comments}")

                        Column {
                            Search_Result_Structure(item , index, navHostController
                                , onCommentIndexClicked = {
                                    index ->
                                if (index != null){
                                    cmt_Clicked_Index.value = index?: 0
                                    constants.API_Vm.isLoading_MComments = false
                                    constants.API_Vm.totalPages_MComments = 1
                                    constants.Reels_ViewModel.clear_MCommentList()
                                    constants.Reels_ViewModel.clear_Reply_Map()
                                    constants.Common_H_ViewModel.enable_Cmt_btm_Sheet()
                                    constants.Search_ViewModel.get_Post_Id_Search_Cmt_Clicked.value = search_Result_Content[index].user_post_id
                                }
                                println("CLICKED INDEX ____ ${index} ERTYU ${cmt_Clicked_Index.value}")
                            },
                                onReportIndexClicked = {
                                    index ->
                                    if (index != null){
                                        report_BS.value = true
                                        reported_Index = index
                                    }
                                }
                                ,viewModel
                            )

                            Spacer(modifier = Modifier.padding(8.dp))
                        }
                    }
                }
            }
        }

    }

    // Comment sheet
    if (cmt_btm_Sheet.value) {

        ModalBottomSheet(
            onDismissRequest = {
                constants.Common_H_ViewModel.dismiss_Cmt_btm_Sheet()
            },
            sheetState = bottomSheetState,
            containerColor = newWhite,
            sheetGesturesEnabled = false
        ) {
            Comment_Structure(
                search_Result_Content,
                cmt_Clicked_Index.value,
                navHostController,
                viewModel
            )
        }
    }

    // Report Sheet
    if (report_BS.value){

        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )

        ModalBottomSheet(
            onDismissRequest = {
                constants.Profile_ViewModel.toggle_ReportSucces_True()

                report_BS.value = false
                constants.Profile_ViewModel.toggle_ProfileReport_Options(
                    -1
                )
            },
            sheetState = sheetState
            , containerColor = newWhite
            , sheetGesturesEnabled = false
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
                                                constants.Profile_ViewModel.toggle_ProfileReport_Options(
                                                    profileReportOptionsDc.id
                                                )
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
                                        singleLine = true,
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
                                        .noRippleClickable{
                                            if (search_Result_Content[reported_Index].post_property.is_report != 1) {

                                                if (user_Manual_report_String.value.isEmpty()) {
                                                    user_Manual_report_String.value =
                                                        constants.Profile_ViewModel.getSelectedProfileReportOptionDescription()
                                                            ?: ""
                                                }
                                                constants.API_Vm.put_Report_All(
                                                    user_id = AppPreferences.getUserId(),
                                                    user_post_id = search_Result_Content[reported_Index].user_post_id.toString(),
                                                    //AppPreferences.get_Post_Id(),
                                                    receiver_id = search_Result_Content[reported_Index].user_id.toString(),
                                                    comment_id = "",
                                                    report_sentence_id = (constants.Profile_ViewModel.getSelectedProfileReportOptionId()
                                                        ?.plus(1)) ?: 0,
                                                    report_sentence = user_Manual_report_String.value,
                                                    status = 2,
                                                )
                                                { apiResultHandling ->

                                                    when (apiResultHandling) {
                                                        is API_Result_Handling.Loading -> {
                                                            // loading
                                                            //constants.PostProperty_ViewModel.change_Status_PFs(true)
                                                        }

                                                        is API_Result_Handling.Deactivated -> {
                                                            //resultCallback(5)
                                                        }

                                                        is API_Result_Handling.Error -> {
                                                            // fail
                                                            //constants.PostProperty_ViewModel.change_Status_PFs(false)
                                                        }

                                                        is API_Result_Handling.Success -> {


                                                            constants.Profile_ViewModel.toggleReportSubmissionSuccess()
                                                            constants.Search_ViewModel.toggle_is_Report(
                                                                search_Result_Content[reported_Index].user_post_id
                                                            )
                                                            //constants.Reels_ViewModel.deleteVideoById_Profile_Post_Reels(videos[pagerState.currentPage].user_id)
                                                            // success
                                                            //constants.PostProperty_ViewModel.change_Status_PFs(false)
                                                        }

                                                        is API_Result_Handling.NoData -> {
                                                            // no data
                                                            //constants.PostProperty_ViewModel.change_Status_PFs(false)
                                                        }
                                                    }
                                                }
                                            } else {

                                                //constants.Common_H_ViewModel.toggleReelsBTMSheet(false)

                                                GlobalSnackbar.show("Post already reported")

                                                report_BS.value = false

                                                //SimpleSnackbar(" Post Already Reported")

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
fun Search_Profile_Result_Screen(
    viewModel: Common_H_ViewModel,
    navHostController: NavHostController
) {


    val network = rememberNetworkStatus()


    val isLoading = constants.API_Vm.isLoading_ProfileS
    val errorMessage = constants.API_Vm.errorMessage_ProfileS
    val currentPage = constants.API_Vm.currentPage_ProfileS
    val totalPages = constants.API_Vm.totalPages_ProfileS

    var retry by remember { mutableStateOf(0) }
    var failure = remember { mutableStateOf(false) }


    val listState = rememberLazyListState()


    val search_Profile_Content = constants.Search_ViewModel.profile_Search_Results.collectAsState()

    var search_username = constants.Search_ViewModel.search_Profile_Name

    println("DATA SIXE --- ${search_Profile_Content.value.size}")
    println("DATA SIXE --- ${search_Profile_Content.value.isEmpty()}")



    if (network.value == NetworkStatus.Online) {
        if (search_username.value.length >= 3 &&  !search_Profile_Content.value.isNotEmpty() && !constants.API_Vm.isLoading_ProfileS) {

        LaunchedEffect(search_username.value , retry) {

                println("WHENPROFILE SEARCHED HITTING NORMAL")
                constants.API_Vm.load_Profile_Search(
                    user_id = AppPreferences.getUserId(),
                    name = search_username.value,
                    page = 1,
                )
            }
        }

        // Pagination logic stays the same
        LaunchedEffect(listState, currentPage, isLoading, totalPages) {
          //  if (search_username.value.length >= 3) {
                println("WHENPROFILE SEARCHED HITTING Pagination")
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
                                println("CURRENT PAGE - ${currentPage}")
                                constants.API_Vm.load_Profile_Search(
                                    user_id = AppPreferences.getUserId(),
                                    name = search_username.value,
                                    page = currentPage + 1,
                                )
                            }

                    }
            //}
        }
    }
    else {
        GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
    }



    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ){
            Image(painter = painterResource(R.drawable.left_arrow) , "",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(20.dp)
                    .noRippleClickable{
                        viewModel.toggleshowBABars(true)
                        constants.Search_ViewModel.dismiss_Search_Results()
                       // constants.Search_ViewModel.clear_profile_SearchData()
                        viewModel.toggleshowBABars(true)
                    }
            )

            CommonText("Property Sellers",
                Color(0xff323232)
                ,18
                ,1
                , modifier = Modifier.align(Alignment.Center)
            )
        }
        when {
            network.value == NetworkStatus.Offline  &&  search_Profile_Content.value.isEmpty() -> {
                Column(
                    modifier = Modifier
                        //.padding(bottom = 48.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 16.dp)
                    , verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painterResource(R.drawable.nointernerdesign), "")
                    Text(constants.activity.getString(R.string.no_Internet) , color = newBlack,fontSize = constants.textUnit(16)
                        , fontFamily = constants.fontFamily(0)
                        , textAlign = TextAlign.Center)

                }
            }

            isLoading && search_Profile_Content.value.isEmpty() -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(9f)
                    , verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LottiAnimation(2)
                }
            }

            errorMessage?.isNotEmpty() == true -> {


                Common_API_Fail(
                    failure
                    , onReTryClick = {
                        retry = retry + 5678
                    }
                )
            }

            search_username.value.isNotEmpty() && !isLoading && search_Profile_Content.value.isEmpty() -> {
                // no data
                Column(
                    modifier = Modifier
                        //.padding(bottom = 48.dp)
                        .fillMaxWidth()
                        .weight(9f)
                        .padding(horizontal = 16.dp , vertical = 16.dp)
                    , verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painterResource(R.drawable.emptyusearchrento), "")
                    Text("We couldn’t find anything!")
                    Text(
                        "Try searching for something else.",
                        textAlign = TextAlign.Center
                    )
                }
            }

            search_Profile_Content.value.isNotEmpty() -> {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(9f)
                        , state = listState
                ) {


                    itemsIndexed(search_Profile_Content.value){
                            index ,item ->
                        ProfileSearchItem(  item , index, viewModel , navHostController)

                        constants.spacer(6)
                    }
                }

                /*search_Profile_Content.value.forEachIndexed { index, option ->

                    ListItem(
                        headlineContent = {
                            Text(option.username,  fontSize = constants.textUnit(14),
                                fontFamily = constants.fontFamily(1),
                                color = Color(0xff575757),)
                        },
                        supportingContent = {
                            Text(option.name ,  fontSize = constants.textUnit(12),
                                fontFamily = constants.fontFamily(1),
                                color = Color(0XFF7E7E7E),)
                        },
                        leadingContent = {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(newLightBlue),
                                contentAlignment = Alignment.Center
                            ) {

                                SubcomposeAsyncImage(
                                    model = option?.profile_image ?: "",
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
                                                text = option?.username.takeIf { it?.isNotEmpty() == true }
                                                    ?.take(1)?.uppercase() ?: ""
                                            )
                                        }
                                    } else {
                                        SubcomposeAsyncImageContent()
                                    }
                                }
                            }
                        }, colors = ListItemColors(
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
                                viewModel.toggleshowTABars(false)
                                constants.Profile_ViewModel.add_Selected_User_Name(
                                    option.username ?: "Username"
                                )

                                //new flowwewwwwwww
                                constants.Profile_ViewModel.add_BF_Handler(Profile_Handle_Back(
                                    current_UsedId = AppPreferences.getUserId(),
                                    other_UserId = option?.user_id ?: 0,
                                    ff_User_Name = option?.username ?: "",
                                    ff_Fw_Count = option.followers,
                                    ff_Fg_Count = option.following,
                                    // is_Search_Enabled = is_Search_Enabled.value,
                                    // search_Text = search_Text.value
                                ))

                                /// println("ITEM PROFILE STRUCTURE __ ${is_Search_Enabled.value} -- ${constants.Profile_ViewModel.profile_BF_Handler.value}")

                                println("GIVEN OTHER USER ID -- ${constants.Profile_ViewModel.get_Other_User_Id()}")

                                constants.Profile_ViewModel.addProfile(option?.user_id ?: 0)
                                constants.Profile_ViewModel.add_Selected_Profile_Id(id = option.user_id ?: 0)

                                // if (view_Details_Data.value?.user_id == AppPreferences.)
                                viewModel.toggleshowBABars(false)
                                navHostController.navigate(VideosScreenFlow.Other_Profile_Structure.route)
                            }
                    )
                    HorizontalDivider()
                }*/
            }
        }

    }
}


@Composable
fun ProfileSearchItem(
    option: Put_Profile_search_Data
    , index: Int
   , viewModel: Common_H_ViewModel,
    navHostController: NavHostController
) {


    val network = rememberNetworkStatus()

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .background(Color.White)
            .border(1.dp ,Color(0xffCECECE), RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp , vertical = 8.dp)
    ) {

        val followState =
            if (option.isBlocked == 1){
                999
            }
        else {
                when {
                    option.is_followed == 1 && option.im_followed == 1 -> 1
                    option.is_followed == 0 && option.im_followed == 1 -> 1
                    option.is_followed == 1 && option.im_followed == 0 -> 2
                    else -> 0
                }
            }


        ListItem(
            leadingContent = {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(newWhite, CircleShape),
                    contentAlignment = Alignment.Center
                )
                {
                    SubcomposeAsyncImage(
                        model = option.profile_image
                            //item?.profile_image ?: ""
                        ,modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds
                    )
                    {
                        val state = painter.state
                        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(newLightBlue, CircleShape)
                                //.padding(8.dp)
                                , contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = option.username.takeIf { it?.isNotEmpty() == true }
                                        ?.take(1)?.uppercase() ?: "",
                                    autoSize = TextAutoSize.StepBased(
                                        minFontSize = 10.sp,
                                        constants.textUnit(16),
                                        stepSize = 2.sp
                                    )

                                )
                            }
                        } else {
                            SubcomposeAsyncImageContent()
                        }
                    }
                }
            },
            headlineContent = {
                CommonText(option.username,
                    newBlack,
                    16,
                    0)
            },
            supportingContent = {
                CommonText(option.name,
                    Color(0xff7E7E7E),
                    12,
                    3)
            },
            trailingContent = {
                if (followState == 999){
                    Box(
                        modifier = Modifier
                            .wrapContentWidth()
                            .height(32.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                Color.White
                            )
                            .border(
                                1.dp, newBlack,
                                RoundedCornerShape(4.dp)
                            )
                            .noRippleClickable {
                                ClickHelper.getInstance().clickOnce {
                                    if (ClickGuard.canClick()) {
                                        if (network.value == NetworkStatus.Online) {
                                            println("BLOCKED API CALL HIT STATUS __ ${constants.Profile_ViewModel.get_Block_Status()} --- ")
                                            constants.API_Vm.put_Block_User(
                                                user_id = AppPreferences.getUserId(),
                                                blocker_id = option.user_id,
                                                //profile_Content.value?.user_id ?: 0,
                                                status = 2
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


                                                                    constants.Search_ViewModel.updateBlockState(option.user_id)


//                                                                    // ✅ FIX: Update counts for the OTHER user's profile
//                                                                    constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
//                                                                        userId = profile_Content.value?.user_id
//                                                                            ?: 0,
//                                                                        newFollowers = constants.Profile_ViewModel.get_Followers_Count_BGAPIC(),
//                                                                        newFollowing = constants.Profile_ViewModel.get_Following_Count_BGAPIC()
//                                                                    )
//
//                                                                    // ✅ FIX: Also update YOUR OWN profile's following count
//                                                                    // Since you unfollowed someone, YOUR following count decreases
//                                                                    val myUserId =
//                                                                        AppPreferences.getUserId()
//                                                                    val myCurrentHandler =
//                                                                        constants.Profile_ViewModel.profile_BF_Handler.value
//                                                                            .find { it.current_UsedId == myUserId && it.other_UserId == 0 }
//
//                                                                    if (myCurrentHandler != null) {
//                                                                        constants.Profile_ViewModel.update_FF_BF_CountsByUserId(
//                                                                            userId = myUserId,
//                                                                            newFollowers = myCurrentHandler.ff_Fw_Count,
//                                                                            newFollowing = (myCurrentHandler.ff_Fg_Count - 1).coerceAtLeast(
//                                                                                0
//                                                                            )
//                                                                        )
//                                                                    }
//
//                                                                    if (profile_Content.value?.is_blocked == 0) {
//                                                                        constants.Profile_ViewModel.updateBlockedStatus_Selected_Profile(
//                                                                            1
//                                                                        )
//                                                                        constants.Profile_ViewModel.update_OnUnBlocked_Follow()
//                                                                    } else {
//                                                                        constants.Profile_ViewModel.updateBlockedStatus_Selected_Profile(
//                                                                            0
//                                                                        )
//                                                                        constants.Profile_ViewModel.update_OnUnBlocked_Follow()
//                                                                    }
//                                                                    block_PopUp = false


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
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                        , contentAlignment = Alignment.Center
                    ){
                        CommonText("Unblock",
                            newBlack,
                            14,
                            0
                        )
                    }
                }
                else {
                    Box(
                        modifier = Modifier
                            .wrapContentWidth()
                            .height(32.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                when (followState) {
                                    1 -> Color(0xffEBEBEB)
                                    2 -> Color.White
                                    else -> newBlue
                                }
                            )
                            .border(
                                1.dp, when (followState) {
                                    1 -> Color.Transparent
                                    2 -> newBlue
                                    else -> Color.Transparent
                                },
                                RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                            .noRippleClickable {

                                constants.Profile_ViewModel.put_Following_Id(option.user_id)

                                constants.Profile_ViewModel.put_follow_unfollow_Status(
                                    when (followState) {
                                        1 -> 2
                                        2 -> 1
                                        else -> 1
                                    }
                                )

                                if (network.value == NetworkStatus.Online) {
                                    follow_Unfollow_Delete_API_Call() { result ->
                                        when (result) {
                                            0 -> {
                                                /// suxxesss
                                                when (followState) {
                                                    1 -> {
                                                        constants.Search_ViewModel.updateFollowState(
                                                            option.user_id,
                                                            0
                                                        )
                                                        GlobalSnackbar.show("Unfollowed Successful")
                                                    }

                                                    2 -> {
                                                        constants.Search_ViewModel.updateFollowState(
                                                            option.user_id,
                                                            1
                                                        )
                                                        GlobalSnackbar.show("Followed Successful")
                                                    }

                                                    else -> {
                                                        constants.Search_ViewModel.updateFollowState(
                                                            option.user_id,
                                                            1
                                                        )
                                                        GlobalSnackbar.show("Followed Successful")
                                                    }
                                                }
                                            }

                                            1 -> {
                                                toast("Something went wrong, Try Again later")
                                            }

                                            2 -> {}
                                        }
                                    }
                                } else {
                                    GlobalSnackbar.show("Check your Internet Connection")
                                }
                            }
                        , contentAlignment = Alignment.Center
                    )
                    {
                        CommonText(
                            when (followState) {
                                1 -> "Following"
                                2 -> "Follow back"
                                else -> "Follow"
                            },
                            when (followState) {
                                1 -> newBlack
                                2 -> newBlue
                                else -> Color.White
                            },
                            14,
                            0
                        )
                    }
                }
            }
            , colors = ListItemDefaults.colors(
                containerColor = Color.White
            )
            , modifier = Modifier
                .noRippleClickable{
                    viewModel.toggleshowTABars(false)
//                    constants.Profile_ViewModel.add_Selected_User_Name(
//                        option.username ?: "Username"
//                    )
//
//                    //new flowwewwwwwww
//                    constants.Profile_ViewModel.add_BF_Handler(Profile_Handle_Back(
//                        current_UsedId = AppPreferences.getUserId(),
//                        other_UserId = option?.user_id ?: 0,
//                        ff_User_Name = option?.username ?: "",
//                        ff_Fw_Count = option.followers,
//                        ff_Fg_Count = option.following,
//                        // is_Search_Enabled = is_Search_Enabled.value,
//                        // search_Text = search_Text.value
//                    ))
//
//                    /// println("ITEM PROFILE STRUCTURE __ ${is_Search_Enabled.value} -- ${constants.Profile_ViewModel.profile_BF_Handler.value}")
//
//                    println("GIVEN OTHER USER ID -- ${constants.Profile_ViewModel.get_Other_User_Id()}")
//
//                    constants.Profile_ViewModel.addProfile(option?.user_id ?: 0)
//                    constants.Profile_ViewModel.add_Selected_Profile_Id(id = option.user_id ?: 0)
//


                    constants.Profile_ViewModel.add_BF_Handler(
                        item = Profile_Handle_Back(
                            //id = ,
                            current_UsedId = AppPreferences.getUserId(),
                            other_UserId = option?.user_id ?: 0,
                            ff_User_Name = option?.username ?: "",
                            ff_Fw_Count = option.followers,
                            ff_Fg_Count = option.following,
//                                                            is_Search_Enabled = is_Search_Enabled.value,
//                                                            search_Text = search_Text.value
                        )
                    )

                    constants.Profile_ViewModel.clearSelectedUserProfile()
                    constants.Profile_ViewModel.add_Selected_User_Name(option?.username ?: "username")

                    // Set the profile ID and navigate
                    constants.Profile_ViewModel.addProfile(option?.user_id ?: 0)
                    constants.Profile_ViewModel.add_Selected_Profile_Id(id = option?.user_id ?: 0)
                    constants.Profile_ViewModel.put_Other_User_Id(option?.user_id ?: 0)
                    constants.Profile_ViewModel.clear_SearchList_FF()
                    constants.API_Vm.totalPages_Profile_Posts = 1

                    // if (view_Details_Data.value?.user_id == AppPreferences.)
                    viewModel.toggleshowBABars(false)
                    navHostController.navigate(VideosScreenFlow.Other_Profile_Structure.route)
                }
        )

        constants.spacer(4)

        val buildimageList = buildThumbnailList(context , option.thumbnails)

        //// multiple shape parts
        StaggeredProfileImages(
            images = buildimageList
        )
    }
}


fun buildThumbnailList(context: Context, urls: List<String>): List<String> {
    val result = mutableListOf<String>()

    urls.forEach { url ->
        val lowerUrl = url.lowercase()

        val thumbnailUrl =
            if (lowerUrl.endsWith(".mp4") || lowerUrl.endsWith(".mov") || lowerUrl.endsWith(".mkv")) {
                // Generate thumbnail from video URL
                getVideoThumbnailBase64(context, url)
            } else {
                // Normal image URL
                url
            }

        result.add(thumbnailUrl?:"")
    }

    return result
}



@Composable
fun StaggeredProfileImages(images: List<String>) {
    val itemCount = images.size

    when (itemCount) {
        1 -> {

            SubcomposeAsyncImage(
                model = images[0],
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp , newGray , RoundedCornerShape(12.dp))
                ,
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            {
                val state = painter.state
                if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(newLightBlue), contentAlignment = Alignment.Center
                    ) {
                        Image(painter = painterResource(R.drawable.emptypostsrento), "")
                    }
                } else {
                    SubcomposeAsyncImageContent()
                }
            }
//            SubcomposeAsyncImage(
//                model = images[0],
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(240.dp)
//                    .clip(RoundedCornerShape(12.dp))
//                    .border(1.dp , newGray , RoundedCornerShape(12.dp))
//                ,
//                contentScale = ContentScale.Crop,
//                contentDescription = null
//            )
        }

        2 -> {
            Row(Modifier.fillMaxWidth()) {
                images.forEachIndexed { _, image ->

                    SubcomposeAsyncImage(
                        model = image,
                        modifier = Modifier
                            .weight(1f)
                            .height(280.dp)
                            .padding(2.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp , newGray , RoundedCornerShape(12.dp))
                        ,
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds
                    )
                    {
                        val state = painter.state
                        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(newLightBlue), contentAlignment = Alignment.Center
                            ) {
                                Image(painter = painterResource(R.drawable.emptypostsrento), "")
                            }
                        } else {
                            SubcomposeAsyncImageContent()
                        }
                    }
//                    SubcomposeAsyncImage(
//                        model = image,
//                        modifier = Modifier
//                            .weight(1f)
//                            .height(280.dp)
//                            .padding(2.dp)
//                            .clip(RoundedCornerShape(12.dp)),
//                        contentScale = ContentScale.Crop,
//                        contentDescription = null
//                    )
                }
            }
        }

        3 -> {
            Column(Modifier.fillMaxWidth()) {


                SubcomposeAsyncImage(
                    model = images[0],
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .padding(2.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp , newGray , RoundedCornerShape(12.dp))
                    ,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
                {
                    val state = painter.state
                    if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(newLightBlue), contentAlignment = Alignment.Center
                        ) {
                            Image(painter = painterResource(R.drawable.emptypostsrento), "")
                        }
                    } else {
                        SubcomposeAsyncImageContent()
                    }
                }
//                SubcomposeAsyncImage(
//                    model = images[0],
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(180.dp)
//                        .padding(2.dp)
//                        .clip(RoundedCornerShape(12.dp)),
//                    contentScale = ContentScale.Crop,
//                    contentDescription = null
//                )

                Row(Modifier.fillMaxWidth()) {
                    images.subList(1, 3).forEach { image ->

                        SubcomposeAsyncImage(
                            model = image,
                            modifier = Modifier
                                .weight(1f)
                                .height(120.dp)
                                .padding(2.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .border(1.dp , newGray , RoundedCornerShape(12.dp))
                            ,
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds
                        )
                        {
                            val state = painter.state
                            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(newLightBlue), contentAlignment = Alignment.Center
                                ) {
                                    Image(painter = painterResource(R.drawable.emptypostsrento), "")
                                }
                            } else {
                                SubcomposeAsyncImageContent()
                            }
                        }
//                        SubcomposeAsyncImage(
//                            model = image,
//                            modifier = Modifier
//                                .weight(1f)
//                                .height(120.dp)
//                                .padding(2.dp)
//                                .clip(RoundedCornerShape(12.dp)),
//                            contentScale = ContentScale.Crop,
//                            contentDescription = null
//                        )
                    }
                }
            }
        }

        4 -> {
            Column(Modifier.fillMaxWidth()) {
                for (i in 0 until 2) {
                    Row(Modifier.fillMaxWidth()) {
                        images.subList(i * 2, i * 2 + 2).forEach { image ->

                            SubcomposeAsyncImage(
                                model = image,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(140.dp)
                                    .padding(2.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(1.dp , newGray , RoundedCornerShape(12.dp))
                                ,
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds
                            )
                            {
                                val state = painter.state
                                if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(newLightBlue), contentAlignment = Alignment.Center
                                    ) {
                                        Image(painter = painterResource(R.drawable.emptypostsrento), "")
                                    }
                                } else {
                                    SubcomposeAsyncImageContent()
                                }
                            }

//                            SubcomposeAsyncImage(
//                                model = image,
//                                modifier = Modifier
//                                    .weight(1f)
//                                    .height(140.dp)
//                                    .padding(2.dp)
//                                    .clip(RoundedCornerShape(12.dp)),
//                                contentScale = ContentScale.Crop,
//                                contentDescription = null
//                            )
                        }
                    }
                }
            }
        }

        5 -> {
            Column(Modifier.fillMaxWidth()) {
                SubcomposeAsyncImage(
                    model = images[0],
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .padding(2.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp , newGray , RoundedCornerShape(12.dp))
                    ,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
                {
                    val state = painter.state
                    if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(newLightBlue), contentAlignment = Alignment.Center
                        ) {
                            Image(painter = painterResource(R.drawable.emptypostsrento), "")
                        }
                    } else {
                        SubcomposeAsyncImageContent()
                    }
                }
//                SubcomposeAsyncImage(
//                    model = images[0],
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(180.dp)
//                        .padding(2.dp)
//                        .clip(RoundedCornerShape(12.dp)),
//                    contentScale = ContentScale.Crop,
//                    contentDescription = null
//                )

                Row(Modifier.fillMaxWidth()) {
                    images.subList(1, 5).forEach { image ->

                        SubcomposeAsyncImage(
                            model = image,
                            modifier = Modifier
                                .weight(1f)
                                .height(120.dp)
                                .padding(2.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .border(1.dp , newGray , RoundedCornerShape(12.dp))
                            ,
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds
                        )
                        {
                            val state = painter.state
                            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(newLightBlue), contentAlignment = Alignment.Center
                                ) {
                                    Image(painter = painterResource(R.drawable.emptypostsrento), "")
                                }
                            } else {
                                SubcomposeAsyncImageContent()
                            }
                        }
//                        SubcomposeAsyncImage(
//                            model = image,
//                            modifier = Modifier
//                                .weight(1f)
//                                .height(120.dp)
//                                .padding(2.dp)
//                                .clip(RoundedCornerShape(12.dp)),
//                            contentScale = ContentScale.Crop,
//                            contentDescription = null
//                        )
                    }
                }
            }
        }
    }
}


@Composable
fun  Search_Result_Structure(
    item: Get_Reels_Data?,
    index: Int,
    navHostController: NavHostController,
    onCommentIndexClicked: (Int?) -> Unit,
    onReportIndexClicked: (Int?) -> Unit,
    viewModel: Common_H_ViewModel,
)
{

    var show_DropDown = remember { mutableStateOf(false) }

    val send_Eq_State = constants.Reels_ViewModel.send_Enquiry_Btm_Sheet.collectAsState()


    Box(
        modifier = Modifier
            .fillMaxSize()
        , contentAlignment = Alignment.Center
    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth(.9f)
                .background(Color.White)
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, newGray, RoundedCornerShape(8.dp))
            , verticalArrangement = Arrangement.spacedBy(6.dp)
            , horizontalAlignment = Alignment.CenterHorizontally
        ){
            ListItem(
                headlineContent = {
                    //Text()

                    item?.name?.ifEmpty { item.username }?.let {
                        Text(
                            it, color = Color.Black
                            , fontSize = constants.textUnit(16)
                            , fontFamily = constants.fontFamily(0)
                            , modifier = Modifier
                        )
                    }
                },
                supportingContent = {
                    Text("${getTimeAgo(item?.post_property?.created_at ?: "")}"
                        , color = Color.Black
                        , fontSize = constants.textUnit(10)
                        , fontFamily = constants.fontFamily(1))
                    /// posted time should come
                },
                leadingContent = {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(newLightBlue)
                            .noRippleClickable{
                                ClickHelper.getInstance().clickOnce {
                                    if (ClickGuard.canClick()) {
                                        logD("clicks reels profile")
                                        constants.Common_H_ViewModel.toggleshowTABars(false)
                                        constants.Profile_ViewModel.add_Selected_User_Name(
                                            item?.username ?: "UserName"
                                        )

                                        //new flowwewwwwwww
                                        constants.Profile_ViewModel.add_BF_Handler(
                                            Profile_Handle_Back(
                                                current_UsedId = AppPreferences.getUserId(),
                                                other_UserId = item?.user_id ?: 0,
                                                ff_User_Name = item?.username ?: "",
                                                ff_Fw_Count = 999,
                                                ff_Fg_Count = 999,
                                                // is_Search_Enabled = is_Search_Enabled.value,
                                                // search_Text = search_Text.value
                                            )
                                        )

                                        /// println("ITEM PROFILE STRUCTURE __ ${is_Search_Enabled.value} -- ${constants.Profile_ViewModel.profile_BF_Handler.value}")

                                        println("GIVEN OTHER USER ID -- ${constants.Profile_ViewModel.get_Other_User_Id()}")

                                        constants.Profile_ViewModel.addProfile(
                                            item?.user_id ?: 0
                                        )
                                        constants.Profile_ViewModel.add_Selected_Profile_Id(
                                            id = item?.user_id ?: 0
                                        )

                                        // if (view_Details_Data.value?.user_id == AppPreferences.)
                                        viewModel.toggleshowBABars(false)
                                        navHostController.navigate(VideosScreenFlow.Other_Profile_Structure.route)
                                    }
                                }
                            }
                    ){
                        SubcomposeAsyncImage(
                            model = item?.profile_image ?: "",
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
                                        .background(newLightBlue, CircleShape)
                                    //.padding(8.dp)
                                    , contentAlignment = Alignment.Center
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
                trailingContent = {
//                    Box(
//                        modifier = Modifier
//                            .size(24.dp)
//                            .clip(RoundedCornerShape(4.dp))
//                            .background(Color.White)
//                            .border(1.dp, newGray, RoundedCornerShape(4.dp))
//
//                        , contentAlignment = Alignment.Center
//                    )
//                    {
                        Common_DropDown2Options(
                            expanded = show_DropDown,
                            mainIcon = R.drawable.moreblackrento,
                            content = listOf(
                                Common_DropDown2Options_DC(
                                    icon = R.drawable.reelsshare,
                                    title = "Share"
                                ),
                                Common_DropDown2Options_DC(
                                    icon = R.drawable.reelsreport,
                                    title = "Report"
                                ),
                            ),
                            onClick1 = {

                                constants.DefaultShare("https://toletspot.com/property/${ item?.user_post_id ?: 0}" ,0)
                                show_DropDown.value = false
                                       //constants.Search_ViewModel.enable_Search_Results_Dropdown()
                            },
                            onClick2 = {
                                //constants.Search_ViewModel.enable_Search_Results_Dropdown()
                                onReportIndexClicked(index)
                            },
                            modifier = Modifier.size(24.dp).noRippleClickable{
                                show_DropDown.value = true
                            }
                                //.align(Alignment.TopEnd)
                                //.padding(top = 48.dp, end = 16.dp)
                        )
                    //}
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

            )


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .noRippleClickable{
                        constants.Reels_ViewModel.clear_view_pro_Details()
                        constants.Search_ViewModel.select_RS_Reels_Flow(index)

                        constants.Reels_ViewModel.removeReelsBTMSOptions(constants.Reels_ViewModel.otherIdOptions)

                        if (constants.Reels_ViewModel.get_Reels_Data()) {
                            navHostController.navigate(SearchScreenFlow.ReelsView_Search_Flow.route + "/$index")
                        }
                    }
                    .background(newBlue)
            ){
                SubcomposeAsyncImage(
                    model = item?.thumbnail ?: "",
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
                                .background(Color(0xffCECECE))
                            //.padding(8.dp)
                            , contentAlignment = Alignment.Center
                        ) {
                            Image(painterResource(R.drawable.emptypostsrento) , "",
                                modifier = Modifier.size(78.dp))
                        }
                    } else {
                        SubcomposeAsyncImageContent()
                    }
                }

            }

            ListItem(
                headlineContent = {
                    Column (
                        modifier = Modifier
                        , verticalArrangement = Arrangement.SpaceBetween
                        , horizontalAlignment = Alignment.Start
                    ) {
                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .background(Color(0xffF7F0DC))
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                item?.post_property?.landCategoryText ?: "",
                                color = newBlue,
                                fontSize = constants.textUnit(12),
                                fontFamily = constants.fontFamily(0)
                            )
                        }

                        constants.spacer(4)

                        Text(
                            item?.post_property?.property_name ?: "",
                            color = Color.Black,
                            fontSize = constants.textUnit(18),
                            fontFamily = constants.fontFamily(1)
                        )

                        Spacer(modifier = Modifier.padding(2.dp))
                    }



                }
                , supportingContent = {
                    Column {
                        Spacer(modifier = Modifier.padding(2.dp))
                        Row(
                            verticalAlignment = Alignment.Top
                            , horizontalArrangement = Arrangement.Center
                        ) {
                            SubcomposeAsyncImage(
                                model = R.drawable.locationpinenquiry,
                                "",
                                modifier = Modifier.size(14.dp).align(Alignment.Top)
                            )
                            Text(
                                "${ item?.post_property?.city ?: "" }, ${item?.post_property?.state ?: ""} "
                                , color = Color.Black
                                , fontSize = constants.textUnit(12)
                                , fontFamily = constants.fontFamily(1)
                                , overflow = TextOverflow.Ellipsis)
                            /// location should come
                        }

                        Spacer(modifier = Modifier.padding(2.dp))


                        val formattedPrice = item?.post_property?.rent?.ifEmpty { item?.post_property?.lease_amount } ?.let { price ->
                            try {
                                val number = price.toString().toDouble()
                                NumberFormat.getNumberInstance(Locale("en", "IN")).format(number)
                            } catch (e: Exception) {
                                "Unavailable"
                            }
                        } ?: "Unavailable"

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                            , verticalAlignment = Alignment.CenterVertically
                            , horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "\u20B9 ${formattedPrice}",
                                color = Color.Black,
                                fontSize = constants.textUnit(16),
                                fontFamily = constants.fontFamily(0)
                            )

                            Box(
                                modifier = Modifier
                                    //.align(Alignment.CenterEnd)
                                    .wrapContentSize()
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(brush = Brush.verticalGradient(newPurpleGradient))
                                    .border(1.dp , Brush.linearGradient(newPurpleGradientBorder) , RoundedCornerShape(4.dp))
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                                    .noRippleClickable{
                                        constants.Reels_ViewModel.enable_Send_Eq_Btm_Sheet()
                                    }
                            ){
                                Text("Send Enquiry" , color = Color.White , fontSize = constants.textUnit(12))
                            }


                        }
                    }
                }
                /*, trailingContent = {
                    Column (
                        modifier = Modifier
                        , verticalArrangement = Arrangement.SpaceBetween
                        , horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .background(Color(0xffF7F0DC))
                                .padding(horizontal = 8.dp, vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                item?.post_property?.landCategoryText ?:"",
                                color = newBlue,
                                fontSize = constants.textUnit(12)
                                , fontFamily = constants.fontFamily(0)
                            )
                        }

                        Spacer(modifier = Modifier.padding(8.dp))

                        Row(
                            modifier = Modifier
                            , horizontalArrangement = Arrangement.SpaceBetween
                            , verticalAlignment = Alignment.CenterVertically
                        ) {
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
            )

            var isLike_Loading = remember { mutableStateOf(false) }
            var isSave_Loading = remember { mutableStateOf(false) }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(Color(0xffF7F0DC))
//                    .background(Color(0xffE8E8E8))
            )
            {
                Box (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                    , contentAlignment = Alignment.Center
//                    , verticalAlignment = Alignment.CenterVertically
//                    , horizontalArrangement = Arrangement.SpaceEvenly
                )
                {
                    Row (
                        modifier = Modifier.fillMaxWidth()
                            .align(Alignment.CenterStart),
                        horizontalArrangement = Arrangement.SpaceBetween
                        , verticalAlignment = Alignment.CenterVertically
                    ){
                        Row(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        )
                        {
//                            Box(
//                                modifier = Modifier
//                                    .size(24.dp)
//                                    .clip(RoundedCornerShape(4.dp))
//                                    .background(newWhite)
//
//                                    .border(1.dp, Color(0xffE8E8E8), RoundedCornerShape(4.dp)),
//                                contentAlignment = Alignment.Center
//                            )
//                            {
//                                if (isLike_Loading.value) {
//                                    CircularProgressIndicator(modifier = Modifier.size(12.dp))
//                                } else {
//                                    if (item?.is_liked == 1) {
                                        SubcomposeAsyncImage(
                                            model =if (item?.is_liked == 1) R.drawable.searchlikedrento else R.drawable.searchlikerento, "",
                                            modifier = Modifier
                                                .size(24.dp)
                                                .noRippleClickable {
                                                    // constants.Search_ViewModel.toggle_isLiked_SR(item.user_post_id)
                                                    constants.API_Vm.like_Dislike(
                                                        user_id = AppPreferences.getUserId(),
                                                        user_post_id = item?.user_post_id ?: 0,
                                                        status = if (item?.is_liked == 1) 2 else 1,
                                                    )
                                                    { apiResultHandling ->
                                                        when (apiResultHandling) {
                                                            is API_Result_Handling.Error -> {
                                                                isLike_Loading.value = false
                                                                GlobalSnackbar.show("Something went wrong")
                                                                //errror
                                                            }

                                                            is API_Result_Handling.NoData -> {
                                                                // no data
                                                            }

                                                            is API_Result_Handling.Deactivated -> {
                                                                // resultCallback(5)
                                                            }

                                                            is API_Result_Handling.Loading -> {
                                                                isLike_Loading.value = true
                                                                //loading
                                                            }

                                                            is API_Result_Handling.Success -> {
                                                                isLike_Loading.value = false

                                                                if (item?.is_liked == 1) {
                                                                    constants.Search_ViewModel.decreaseLikeCount_Reels_Search(
                                                                        item.user_post_id
                                                                    )
                                                                } else {
                                                                    constants.Search_ViewModel.increaseLikeCount_Reels_Search(
                                                                        item?.user_post_id ?: 0
                                                                    )
                                                                }
                                                                constants.Search_ViewModel.toggleLike_Reels_Search(
                                                                    item?.user_post_id ?: 0
                                                                )

                                                                //success
                                                            }
                                                        }
                                                    }
                                                }
                                        )
//                                    } else {
//                                        SubcomposeAsyncImage(
//                                            model = R.drawable.likerento,
//                                            "",
//                                            modifier = Modifier
//                                                .size(16.dp),
//                                            colorFilter = ColorFilter.tint(newBlack)
//                                        )
//                                    }
                               // }
//                            }

                            Text(
                                "${item?.total_likes ?: 0}",
                                color = newBlack,
                                fontSize = constants.textUnit(14),
                                fontFamily = constants.fontFamily(1)
                            )

                            Text(
                                "Likes",
                                color = Color(0xff7E7E7E),
                                fontSize = constants.textUnit(14),
                                fontFamily = constants.fontFamily(1)
                            )


                            Spacer(modifier = Modifier.padding(4.dp))



//                            Box(
//                                modifier = Modifier
//                                    .size(24.dp)
//                                    .clip(RoundedCornerShape(4.dp))
//                                    .background(newWhite)
//                                    .border(1.dp, Color(0xffE8E8E8), RoundedCornerShape(4.dp)),
//                                contentAlignment = Alignment.Center
//                            )
//                            {
                                SubcomposeAsyncImage(
                                    model = R.drawable.commentrento, "",
                                    colorFilter = ColorFilter.tint(newBlack),
                                    modifier = Modifier
                                        .size(24.dp)
                                        .noRippleClickable {
                                            onCommentIndexClicked(index)
                                        }
                                )
//                            }

                            Text(
                                "${item?.total_comments ?: 0}",
                                color = newBlack,
                                fontSize = constants.textUnit(14),
                                fontFamily = constants.fontFamily(1)
                            )

                            Text(
                                "Comments",
                                color = Color(0xff7E7E7E),
                                fontSize = constants.textUnit(14),
                                fontFamily = constants.fontFamily(1)
                            )



                            Spacer(modifier = Modifier.padding(4.dp))


                        }

//                        Box(
//                            modifier = Modifier
//                                .size(24.dp)
//                                .clip(RoundedCornerShape(4.dp))
//                                .background(newWhite)
//
//                                .border(1.dp, Color(0xffE8E8E8), RoundedCornerShape(4.dp)),
//                            contentAlignment = Alignment.Center
//                        )
//                        {
//                            if (isSave_Loading.value) {
//                                CircularProgressIndicator(modifier = Modifier.size(12.dp))
//                            } else {

                                    SubcomposeAsyncImage(
                                        model =  if (item?.is_saved == 1) R.drawable.saveedrento else R.drawable.saverento, "",
                                        colorFilter = ColorFilter.tint(newBlue),
                                        modifier = Modifier
                                            .size(24.dp)
                                            .noRippleClickable {
                                                //constants.Search_ViewModel.toggle_isSaved_SR(item.id)
                                                constants.API_Vm.put_save_UnSave_Property(
                                                    user_id = AppPreferences.getUserId(),
                                                    user_post_id = item?.user_post_id ?: 0,
                                                    status = if (item?.is_saved == 1) 2 else 1,
                                                )
                                                { apiResultHandling ->
                                                    when (apiResultHandling) {
                                                        is API_Result_Handling.Error -> {
                                                            isSave_Loading.value = false
                                                            GlobalSnackbar.show("Something went wrong")
                                                            //errror
                                                        }

                                                        is API_Result_Handling.NoData -> {
                                                            // no data
                                                        }

                                                        is API_Result_Handling.Loading -> {
                                                            isSave_Loading.value = true
                                                            //loading
                                                        }

                                                        is API_Result_Handling.Deactivated -> {
                                                            // resultCallback(5)
                                                        }

                                                        is API_Result_Handling.Success -> {
                                                            isSave_Loading.value = false

                                                            constants.Search_ViewModel.toggleSave_Reels_Search(
                                                                item?.user_post_id ?: 0
                                                            )
                                                            //success
                                                        }
                                                    }
                                                }
                                            }
                                    )
//                                } else {
//                                    SubcomposeAsyncImage(
//                                        model = R.drawable.saverento, "",
//                                        colorFilter = ColorFilter.tint(newBlack),
//                                        modifier = Modifier
//                                            .size(16.dp)
//                                    )
//                                }
//                            }
                        //}
                    }


                   /* Box(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .wrapContentSize()
                            .clip(RoundedCornerShape(4.dp))
                            .background(newBlue)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .noRippleClickable{
                                constants.Reels_ViewModel.enable_Send_Eq_Btm_Sheet()
                            }
                    ){
                        Text("Send Enquiry" , color = Color.White , fontSize = constants.textUnit(12))
                    }*/


                }
            }
        }
    }

    if (item != null) {
        Enquiry_Form_Btm_Sheet_Structure(send_Eq_State.value, item)
    }
}



fun fetchCities(
    query: String,
    countryCode: String, // e.g. "IN", "US"
    placesClient: PlacesClient,
    callback: (List<AutocompletePrediction>) -> Unit
) {
    val request = FindAutocompletePredictionsRequest.builder()
        .setQuery(query)
        //.setTypesFilter(listOf("(cities)")) // only cities
        //.setCountries(countryCode) // restrict to specific country
        .build()

    placesClient.findAutocompletePredictions(request)
        .addOnSuccessListener { response ->
            callback(response.autocompletePredictions)
        }
        .addOnFailureListener { exception ->
            Log.e("Places", "Error fetching cities", exception)
            callback(emptyList())
        }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityDropdown(
    modifier: Modifier = Modifier,
    placesClient: PlacesClient,
    countryCode: String,
    search_Area: MutableState<String>
) {
    var cityQuery =  constants.Search_ViewModel.search_Area
    var cities by remember { mutableStateOf<List<AutocompletePrediction>>(emptyList()) }
    var selectedCity by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    var expanded by remember { mutableStateOf(false) }

    // TextField with dropdown
    ExposedDropdownMenuBox(
        expanded = expanded && cities.isNotEmpty(),
        onExpandedChange = { expanded = it },
        modifier = modifier.fillMaxWidth()
    ) {
        TextField(
            value = selectedCity.ifEmpty { cityQuery.value },
            onValueChange = {
                selectedCity = ""
                cityQuery.value = it

                if (cityQuery.value.length > 2) {
                    fetchCities(cityQuery.value, countryCode, placesClient) { predictions ->
                        cities = predictions
                        expanded = predictions.isNotEmpty()
                    }
                } else {
                    cities = emptyList()
                    expanded = false
                }
            },
            leadingIcon = {
               // if (cityQuery.value.isEmpty()) {
                    Icon(
                        painter = painterResource(R.drawable.searchnotrento),
                        contentDescription = "Clear",
                        modifier = Modifier
                            .size(18.dp)
                    )
               // }
            },
            placeholder = {
                Text("Search by property, city.."
                ,  color = newBlack,
                fontSize = constants.textUnit(14),
                fontFamily = constants.fontFamily(2),
                )
                          },
            trailingIcon = {
                if (cityQuery.value.isNotEmpty()) {
                    Image(
                        painter = painterResource(R.drawable.search_text_clear),
                        contentDescription = "Clear",
                        modifier = Modifier
                            .size(18.dp)
                            .noRippleClickable{
                                cityQuery.value = ""
                                selectedCity = ""
                                cities = emptyList()
                                expanded = false
                            }
                    )
                }
            },
            textStyle = TextStyle(
                fontSize = constants.textUnit(14),
                fontFamily = constants.fontFamily(1),
                color = Color.Black
            ),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    val displayName = cityQuery.value.ifEmpty { selectedCity }
                    if (displayName.isNotBlank()) {
                        search_Area.value = displayName

                        constants.Search_ViewModel.search_Area.value = displayName


                        constants.Search_ViewModel.total_SearchResults_Counts.value = 0
                        constants.API_Vm.totalPages_PS_FF = 1
                        constants.API_Vm.isLoading_PS_FF = true
                        constants.Search_ViewModel.trigger_Search_Again.value = constants.Search_ViewModel.trigger_Search_Again.value + 1324
                        cities = emptyList()
                        expanded = false
                    } else {
                        toast("Enter a city name to search")
                    }
                    focusManager.clearFocus()
                }
            ),
            modifier = Modifier
                .menuAnchor() // anchor for dropdown menu
                .fillMaxWidth()
                .border(1.dp , newBlack , RoundedCornerShape(6.dp))
        )

        // Overlay dropdown that does NOT push layout
        ExposedDropdownMenu(
            expanded = expanded && cities.isNotEmpty(),
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth(.9f)
                .heightIn(max = 250.dp)
            , containerColor = newWhite
        ) {
            cities.forEach { prediction ->
                val displayName = prediction.getFullText(null).toString()
                DropdownMenuItem(
                    text = { Text(displayName,    fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2),) },
                    onClick = {
                        selectedCity = displayName
                        search_Area.value = displayName


                        constants.Search_ViewModel.search_Area.value = displayName

                        constants.Search_ViewModel.clear_Search_Results()
                        constants.API_Vm.totalPages_PS_FF = 1
                        constants.API_Vm.currentPage_PS_FF = 1
                        constants.API_Vm.isLoading_PS_FF = true
                        constants.Search_ViewModel.not_Apply_FS()
                        constants.Search_ViewModel.total_SearchResults_Counts.value = 0

                        constants.Search_ViewModel.trigger_Search_Again.value = constants.Search_ViewModel.trigger_Search_Again.value + 1324
                        cities = emptyList()
                        expanded = false
                        focusManager.clearFocus()
                    } ,colors = MenuDefaults.itemColors(textColor = newBlack)
                )
            }
        }
    }
}








