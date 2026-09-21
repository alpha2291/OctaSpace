package com.toletspot.houseforrent.Start_Up

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
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
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.android.libraries.places.api.net.PlacesClient
import com.toletspot.houseforrent.API.StartUp_API.put_User_Location_API_Call
import com.toletspot.houseforrent.AppPreferences
import com.toletspot.houseforrent.Custom_Assets.CheckLocationPermissionOnResume
import com.toletspot.houseforrent.Custom_Assets.ClickHelper
import com.toletspot.houseforrent.Custom_Assets.Common_Popup
import com.toletspot.houseforrent.Custom_Assets.GetCurrentLocationButton
import com.toletspot.houseforrent.Custom_Assets.LocationButton
import com.toletspot.houseforrent.Custom_Assets.LottiAnimation
import com.toletspot.houseforrent.Custom_Assets.Static_Bottom
import com.toletspot.houseforrent.Custom_Assets.customGridItems
import com.toletspot.houseforrent.Custom_Assets.rememberNotchHeightDp
import com.toletspot.houseforrent.Custom_Assets.toast
import com.toletspot.houseforrent.GlobalSnackbar
import com.toletspot.houseforrent.Navigation.UserCredentialsScreenFlow
import com.toletspot.houseforrent.NetworkStatus
import com.toletspot.houseforrent.R
import com.toletspot.houseforrent.RentoNewScreens.AnimatedMapView
import com.toletspot.houseforrent.constants
import com.toletspot.houseforrent.fetchCityStateFromPincode
import com.toletspot.houseforrent.forTab
import com.toletspot.houseforrent.noRippleClickable
import com.toletspot.houseforrent.rememberNetworkStatus
import com.toletspot.houseforrent.ui.theme.newBlack
import com.toletspot.houseforrent.ui.theme.newBlue
import com.toletspot.houseforrent.ui.theme.newGray
import com.toletspot.houseforrent.ui.theme.newLightBlue
import com.toletspot.houseforrent.ui.theme.newLightPurpleGradient
import com.toletspot.houseforrent.ui.theme.newPurpleGradientBorder
import com.toletspot.houseforrent.ui.theme.newWhite



@OptIn(ExperimentalAnimationApi::class)
@Composable
fun UserInterests(navController: NavHostController, placesClient: PlacesClient) {


    val network = rememberNetworkStatus()

    val notchPadding = rememberNotchHeightDp()
    val context = LocalContext.current



    val locationDenied = constants.Start_Up_ViewModel.locationDenied.collectAsState()

    LaunchedEffect(AppPreferences.get_Location_Received()) {
        if (AppPreferences.get_Interest_Completed() == 1 && AppPreferences.get_Location_Received() == 0) {
            constants.Start_Up_ViewModel.setLocationDenied(false)
        }
    }

    println("ONCOMPLETE __ ${AppPreferences.get_Interest_Completed()} -- ${AppPreferences.get_Location_Received()}")

    val categories = constants.Start_Up_ViewModel.user_Interests.collectAsState()
    val isLoading = constants.API_Vm.isLoading
    val errorMessage = constants.API_Vm.errorMessage
    val currentPage = constants.API_Vm.currentPage
    val totalPages = constants.API_Vm.totalPages


    val location_Api_Loading = constants.Common_H_ViewModel.status.collectAsState()


    val selected_Ids =  constants.Start_Up_ViewModel.selectedCategoryIds.collectAsState()


    val listState = rememberLazyListState()

    val pincode = constants.Start_Up_ViewModel.pincode.collectAsState()

    var location_Settings = remember { mutableStateOf(false) }
    var showGpsDialog = remember { mutableStateOf(false) }

    var staticIconList = listOf(
        R.drawable.flat,
        R.drawable.housevilla,
        R.drawable.builderfloor,
        R.drawable.studioapartment,
        R.drawable.residentialother,
        R.drawable.commercialofficespace,
        R.drawable.officeitpark,
        R.drawable.commercialshop,
        R.drawable.commercialshowroomg,
        R.drawable.commercialland,
        R.drawable.warehousegodown,
        R.drawable.industrialland,
        R.drawable.industrialbuilding,
        R.drawable.industrialshed,
        R.drawable.commercialother,
        R.drawable.farmhouse,
        R.drawable.agricultureland,

        //// duplicate

        R.drawable.flat,
        R.drawable.housevilla,
        R.drawable.builderfloor,
        R.drawable.studioapartment,
        R.drawable.residentialother,
        R.drawable.commercialofficespace,
        R.drawable.officeitpark,
        R.drawable.commercialshop,
        R.drawable.commercialshowroomg,
        R.drawable.commercialland,
        R.drawable.warehousegodown,
        R.drawable.industrialland,
        R.drawable.industrialbuilding,
        R.drawable.industrialshed,
        R.drawable.commercialother,
        R.drawable.farmhouse,
        R.drawable.agricultureland,

    )


    //LaunchedEffect (Unit){
        CheckLocationPermissionOnResume(context, location_Settings)

  //  }

//    if (categories.value.isEmpty()) {
        if (network.value == NetworkStatus.Online) {
            // Load first page when screen launches
            LaunchedEffect(Unit) {
                constants.API_Vm.loadCategories(1)
                println("COMING HERE 1")
            }

            // Detect when near end of list
            LaunchedEffect(listState, currentPage, isLoading, totalPages) {
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
                            constants.API_Vm.loadCategories(currentPage + 1)
                        }
                    }
            }


        } else {
            GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
        }
//    }

    var gridtype = if (forTab())3 else 2


    /// rental

    var rentoShowMap = remember { mutableStateOf(0) }


    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(newWhite),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            AnimatedContent(
                targetState = locationDenied.value,
                transitionSpec = {
                    slideInHorizontally(animationSpec = tween(800)) { it } with ExitTransition.None
                },
                label = "UserInterestsSwitcher", modifier = Modifier
                    .fillMaxWidth()
                    .weight(8.5f)
            )
            { denied ->
                if (denied) {
                    // Show Interest Selection UI
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxSize()
                            .padding(horizontal = 16.dp , vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp) // spacing between items
                    )
                    {
                        item {
                            Column(
                                modifier = Modifier
                                // .background(Color.Cyan)
                            ) {
                                // Header
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = if (forTab()) 16.dp else  notchPadding.value),
                                    contentAlignment = Alignment.Center
                                )
                                {
//                                Backer(
//                                    modifier = Modifier
//                                        .align(Alignment.TopStart)
//                                        .background(Color.Cyan)
//                                    , onBackClick = {
//                                        constants.Start_Up_ViewModel.updateLoginState(0)
//                                        navController.navigate(UserCredentialsScreenFlow.UserCredentials.route)
//                                    }
//                                )

//                                LocationText(context, modifier = Modifier.align(Alignment.TopEnd))
                                }


                                Text(
                                    text = "Tell us what kind of rental \n you’re looking for?",
                                    color = Color.Black,
                                    fontSize = constants.textUnit(24),
                                    fontFamily = constants.fontFamily(0),
                                    lineHeight = 36.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .align(Alignment.CenterHorizontally) // Align title to the start
                                )

                                Text(
                                    text = "We’ll suggest rentals based on your preferences.",
                                    color = Color.Black,
                                    fontSize = constants.textUnit(16),
                                    fontFamily = constants.fontFamily(1),
                                    textAlign = TextAlign.Center,
                                   // lineHeight = 36.sp,
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .align(Alignment.CenterHorizontally) // Align title to the start
                                )
                            }
                        }

                        println("INterests Data -- ${isLoading} --- ${currentPage} -- ${categories.value.isEmpty()}")
                        when {

                            network.value == NetworkStatus.Offline && categories.value.isEmpty() -> {
                                /// constants.activity.getString(R.string.no_Internet) 
                                println("INterests Data - 33333- ${isLoading} --- ${currentPage} -- ${categories.value.isEmpty()}")

                                GlobalSnackbar.show("It Seems your are offline !!.Refresh again")
                                item {
                                    Column(
                                        modifier = Modifier
                                            .height(600.dp)
                                           // .fillMaxSize()
                                        , verticalArrangement = Arrangement.Center
                                        , horizontalAlignment = Alignment.CenterHorizontally
                                    ){
                                        Image(painterResource(R.drawable.nointernerdesign), "")

                                        Spacer(modifier = Modifier.padding(16.dp))

                                        Text(
                                            constants.activity.getString(R.string.no_Internet)
                                            , modifier = Modifier
                                                .padding(horizontal = 24.dp)
                                        )
                                    }
                                }
                            }


                            isLoading && currentPage == 1 && categories.value.isEmpty() ->{
                                println("INterests Data 1111-- ${isLoading} --- ${currentPage} -- ${categories.value.isEmpty()}")

                                item {
                                    Box(
                                        modifier = Modifier  .height(600.dp)
                                            .fillMaxWidth()
                                            //.fillMaxSize()
                                        ,contentAlignment = Alignment.Center
                                    ) {
                                        LottiAnimation(2)
                                    }
                                }
                            }




                            !isLoading && categories.value.isEmpty() -> {
                                // no data found
                                println("INterests Data -222- ${isLoading} --- ${currentPage} -- ${categories.value.isEmpty()}")

                                item {
                                   Column(
                                       modifier = Modifier
                                           .height(600.dp)
                                           .fillMaxWidth()
                                           //.fillMaxSize()
                                       , verticalArrangement = Arrangement.Center
                                       , horizontalAlignment = Alignment.CenterHorizontally
                                   ){
                                       Image(painterResource(R.drawable.emptysavedproperties), "")

                                       Spacer(modifier = Modifier.padding(16.dp))

                                       Text(
                                           "Looks like there is no interests found at now !!"
                                           , modifier = Modifier
                                               .padding(horizontal = 56.dp)
                                       )
                                   }
                                }
                            }


                            categories.value.isNotEmpty() -> {
                                println("INterests Data 4444-- ${isLoading} --- ${currentPage} -- ${categories.value.isEmpty()}")

                                customGridItems(
                                    count = categories.value.size,
                                    nColumns = gridtype,
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                )
                                { index ->

                                    Box(
                                        modifier = Modifier
                                            .height(172.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .then(
                                                if (categories.value[index].is_Selected){
                                                    Modifier.background(Brush.verticalGradient(
                                                        newLightPurpleGradient
                                                    ))
                                                }
                                                else Modifier.background(newWhite)
                                            )
                                            .noRippleClickable(enabled = if (location_Api_Loading.value) false else true) {
                                                ClickHelper.getInstance().clickOnce {
                                                    constants.Start_Up_ViewModel.toggleInterestSelection(
                                                        categories.value[index].land_categorie_id
                                                    )
                                                }
                                            }
                                            .then(
                                                if (categories.value[index].is_Selected){
                                                    Modifier.border(1.dp, newBlue, RoundedCornerShape(10.dp))
                                                }
                                                else Modifier .border(1.dp, Color(0xffCECECE), RoundedCornerShape(10.dp))
                                            )

                                        , contentAlignment = Alignment.Center
                                    ){
                                        Column(
                                            verticalArrangement = Arrangement.Center
                                            , horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            if (categories.value[index].is_Selected) {
                                                Box(
                                                    modifier = Modifier
                                                        .clip(CircleShape)
                                                        .wrapContentSize()
                                                        .background(Color.White)
                                                        .border(
                                                            1.dp, Brush.verticalGradient(
                                                                newPurpleGradientBorder
                                                            ), CircleShape
                                                        ), contentAlignment = Alignment.Center

                                                ) {
                                                    Image(painter = painterResource(staticIconList[index]) , "",
                                                        modifier = Modifier.padding(6.dp).size(30.dp), colorFilter = ColorFilter.tint(newBlue) )
                                                }
                                            }
                                            else {
                                                Image(painter = painterResource(staticIconList[index]) , "",
                                                    modifier = Modifier.size(30.dp)
                                                )
                                            }

                                            constants.spacer(8)

                                            Text(
                                                text = categories.value[index].name,
                                                color =  Color.Black, // if (categories.value[index].is_Selected) Color.White else
                                                fontSize = constants.textUnit(14),
                                                fontFamily = constants.fontFamily(0),
                                                lineHeight = constants.textUnit(16),
                                                textAlign = TextAlign.Center ,
                                                modifier = Modifier.padding(horizontal = 16.dp)
                                            )
                                        }
                                    }

                                    /*Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .padding(bottom = 10.dp)
                                            // .aspectRatio(1f)
                                            .border(1.dp, color = newGray, RoundedCornerShape(4.dp))
                                    )
                                    {
                                        Column(
                                            modifier = Modifier
                                                .height(200.dp)
                                                .fillMaxWidth()
                                                .clickable(enabled = if (location_Api_Loading.value) false else true) {
                                                    ClickHelper.getInstance().clickOnce {
                                                        // constants.Start_Up_ViewModel.updateSelectedIds()
                                                        // constants.Start_Up_ViewModel.toggle_User_Selections(index)
                                                        //if (network.value == NetworkStatus.Online) {
                                                        constants.Start_Up_ViewModel.toggleInterestSelection(
                                                            categories.value[index].land_categorie_id
                                                        )
                                                        //  } else {
                                                        //GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
                                                        //  }

                                                    }
                                                }
                                                .background(
                                                    if (categories.value[index].is_Selected) newBlue else newWhite,
                                                    RoundedCornerShape(4.dp)
                                                ),
                                            verticalArrangement = Arrangement.SpaceEvenly,
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        )
                                        {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
//                                            .height(130.dp)
                                                    .fillMaxHeight(.8f)
                                                    .padding(start = 12.dp, end = 12.dp, top = 8.dp)
                                                    .clip(RoundedCornerShape(10.dp))
                                                    .background(Color.LightGray)
                                            ){
                                                SubcomposeAsyncImage(
                                                    model = categories.value[index]?.image ?: "",
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
                                                        ) {
                                                            Image(painterResource(R.drawable.emptypostsrento) , "")
                                                        }
                                                    } else {
                                                        SubcomposeAsyncImageContent()
                                                    }
                                                }
                                            }

                                            Text(
                                                text = categories.value[index].name,
                                                color = if (categories.value[index].is_Selected) Color.White else Color.Black,
                                                fontSize = constants.textUnit(13),
                                                fontFamily = constants.fontFamily(2),
                                                lineHeight = constants.textUnit(16),
                                                modifier = Modifier
                                                    .padding(horizontal = 12.dp)
                                                    .align(Alignment.Start) // Align title to the start
                                            )

                                        }
                                    }*/
                                }
                            }

                            isLoading && currentPage != 1 && categories.value.isNotEmpty() ->{
                                println("INterests Data 1111-- ${isLoading} --- ${currentPage} -- ${categories.value.isEmpty()}")

                                item {
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                       CircularProgressIndicator()
                                    }
                                }
                            }



                        }

                    }

                }
                else {

                    rentoShowMap.value = 1
                    // Show Manual Location Entry UI
                   // Location_Manual(modifier = Modifier.fillMaxSize(), context, navController , placesClient)
                }
            }

            // Bottom Button
            Static_Bottom(
                modifier = Modifier
                    .weight(1f)

            )
            {
                AnimatedContent(
                    targetState = locationDenied.value,
                    transitionSpec = {
                        slideInHorizontally(animationSpec = tween(800)) { it } with ExitTransition.None
                    },
                    label = "UserInterestsSwitcher"
                ) { denied ->
                    if (denied) {


                            LocationButton(
                                context,
                                modifier = Modifier.then(
                                    if (selected_Ids.value.size >= 2) {
                                        Modifier.background(newBlue, RoundedCornerShape(8.dp))
                                    } else {
                                        Modifier
                                            .background(newBlue.copy(.5f), RoundedCornerShape(8.dp))
                                            .noRippleClickable (if (selected_Ids.value.size >= 2) true else false){
                                                ClickHelper.getInstance().clickOnce {
                                                    toast("Select at least 2 Interests")
                                                }
                                            }
                                    }
                                ),
                                enabled =  if (selected_Ids.value.size >= 2) true else false,
                                navController

                            )
                    }
                    else
                    {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .height(56.dp)
                                .noRippleClickable{
                                    ClickHelper.getInstance().clickOnce {

                                        if (constants.Start_Up_ViewModel.country.value.isNotEmpty()
                                            && constants.Start_Up_ViewModel.state.value.isNotEmpty()
                                            && constants.Start_Up_ViewModel.city.value.isNotEmpty()
                                            && constants.Start_Up_ViewModel.pincode.value.isNotEmpty()
                                            && constants.Start_Up_ViewModel.latitude.value.isNotEmpty()
                                            && constants.Start_Up_ViewModel.longitude.value.isNotEmpty()
                                        )
                                        {
                                            AppPreferences.save_User_Lcation(constants.Start_Up_ViewModel.city.value)
                                            put_User_Location_API_Call(
                                                resultCallback = { result ->
                                                    when (result) {
                                                        0 -> {
                                                            //sucess
                                                            AppPreferences.save_Location_Received(1)
                                                            toast("LOCATION STORED SUCCESSFULLY")
// Example: after completing onboarding or login
                                                            navController.navigate(
                                                                UserCredentialsScreenFlow.Common_Screen.route
                                                            ) {
                                                                popUpTo(navController.graph.startDestinationId) {
                                                                    inclusive = true
                                                                }
                                                            }
                                                        }

                                                        1 -> {
                                                            //fail
                                                            toast("Something went wrong while storing location")
                                                        }

                                                        2 -> {
                                                            //loading
                                                            constants.Common_H_ViewModel.changeStatus(
                                                                true
                                                            )
                                                        }
                                                    }
                                                }
                                            )
                                        } else {
                                            GlobalSnackbar.show("All fields are Mandatory")
                                        }
                                    }
                                }
                                .background(newBlue, RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        )
                        {
                            Text(
                                text = constants.activity.getString(R.string.continue_click),
                                color = newWhite,
                                fontSize = constants.textUnit(14),
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }

    }



    if (location_Settings.value){
        Common_Popup(
            location_Settings.value,
            modifier = Modifier.background(newLightBlue),
            image = "",
            icon = R.drawable.location,
            userName = "",
        )
        {
            Column (
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                , verticalArrangement = Arrangement.spacedBy(8.dp)
                , horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text("Enable Location")
                Text("Your location services are turned off. Please enable GPS to use current location." ,
                    textAlign = TextAlign.Center
                    , color = newBlack,
                    fontSize = constants.textUnit(12),
                    fontFamily = constants.fontFamily(3)
                   , lineHeight = 24.sp
                )

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                    , verticalAlignment = Alignment.CenterVertically
                    , horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    Box(
                        modifier = Modifier
                            .height(48.dp)
                            .width(164.dp)
                            .background(Color(0xffB8B8B8))
                            .noRippleClickable{
                                ClickHelper.getInstance().clickOnce {
                                    println("sdcjhchjdchjbdwc")
                                    location_Settings.value = false
                                }
                            }
                        , contentAlignment = Alignment.Center
                    ){
                        Text("Cancel")
                    }
                    Box(
                        modifier = Modifier
                            .height(48.dp)
                            .width(164.dp)
                            .background(newBlue)
                            .noRippleClickable{
                                ClickHelper.getInstance().clickOnce {
                                    location_Settings.value = false
                                    val intent =
                                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                            data =
                                                Uri.fromParts("package", context.packageName, null)
                                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // ← this flag is IMPORTANT
                                        }
                                    context.startActivity(intent)

                                }
                            }
                        , contentAlignment = Alignment.Center
                    ){
                        Text("Go to Settings" , color = newWhite)
                    }
                }
                Spacer(modifier = Modifier.padding(4.dp))
            }
        }
    }

    var enable = mutableStateOf(if (rentoShowMap.value == 1) true else false)
    AnimatedMapView(enable , 0, latlong = Pair("",""),navController)

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Location_Manual(
    modifier: Modifier,
    context: Context,
    navController: NavHostController,
    placesClient: PlacesClient,
) {


    var network = rememberNetworkStatus()


    LaunchedEffect(Unit)
    {
        constants.Start_Up_ViewModel.set_Pincode(AppPreferences.get_Pincode())
        constants.Start_Up_ViewModel.set_Country(AppPreferences.get_Country())
        constants.Start_Up_ViewModel.set_State(AppPreferences.get_State())
        constants.Start_Up_ViewModel.set_City(AppPreferences.get_User_Location())
    }


    val notchPadding = rememberNotchHeightDp()

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    val pincode = constants.Start_Up_ViewModel.pincode.collectAsState()

    val locationDenied = constants.Start_Up_ViewModel.locationDenied.collectAsState()


    var city = constants.Start_Up_ViewModel.city.collectAsState()
    var state = constants.Start_Up_ViewModel.state.collectAsState()
    var country = constants.Start_Up_ViewModel.country.collectAsState()
    var errorMessage by remember { mutableStateOf<String?>(null) }


    // ✅ Get states from ViewModel
    val location_Settings = constants.Start_Up_ViewModel.showLocationSettings.collectAsState()
    val showGpsDialog = constants.Start_Up_ViewModel.showGpsDialog.collectAsState()


    val no_Click_SaveLocation = remember { mutableStateOf(false) }


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(newWhite)
            .padding(top = if (forTab()) 16.dp else  notchPadding.value)
            .padding(horizontal = 16.dp)
    )
    {

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = constants.activity.getString(R.string.choose_Location),
            color = Color.Black,
            fontSize = constants.textUnit(24),
            lineHeight = 36.sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Text(
            text = constants.activity.getString(R.string.choose_Location_Desc),
            color = Color.Black,
            fontSize = constants.textUnit(12),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxSize())
        {
            item {
                Text(
                    text = "Pincode",
                    color = Color.Black,
                    fontSize = constants.textUnit(14),
                    lineHeight = 36.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Column(
                    modifier = Modifier
                    // .fillMaxHeight()
                    //.weight(7.5f)
                )
                {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(newWhite)
                            .border(
                                1.dp,
                                if (errorMessage != null) Color.Red else newGray,
                                RoundedCornerShape(4.dp)
                            )
                    )
                    {
                        TextField(
                            value = pincode.value,
                            onValueChange = {
                                if (it.length <= 10) {
                                    constants.Start_Up_ViewModel.set_Pincode(it)
                                    constants.Start_Up_ViewModel.updateSelectedState("")
                                    constants.Start_Up_ViewModel.updateSelectedCity("")
                                    constants.Start_Up_ViewModel.set_Country("")
                                    constants.Start_Up_ViewModel.set_State("")
                                    constants.Start_Up_ViewModel.set_City("")
                                }
                            },
                            textStyle = TextStyle(
                                 color = newBlack,
                                fontSize = constants.textUnit(14),
                                fontFamily = constants.fontFamily(2),
                            ),
                            placeholder = {
                                Text(
                                    "Enter Pincode",
                                    color = newGray,
                                    fontSize = constants.textUnit(14)
                                )
                            }, colors = TextFieldDefaults.colors(
                                focusedContainerColor = newWhite,
                                unfocusedContainerColor = newWhite,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                unfocusedTextColor = newBlack,
                                focusedTextColor = newBlack
                            ),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(7.5f)
                        )

                        VerticalDivider()

                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(2.5f)
                                .noRippleClickable{
                                    ClickHelper.getInstance().clickOnce {

                                        if (network.value == NetworkStatus.Online) {

                                            fetchCityStateFromPincode(
                                                pincode.value,
                                                placesClient
                                            ) { resultCity, resultState, resultCountry, error, lat, long ->
                                                if (error != null) {
                                                    errorMessage = error

                                                    if (resultCountry != null) {
                                                        constants.Start_Up_ViewModel.set_Country("")
                                                    }
                                                    if (resultState != null) {
                                                        constants.Start_Up_ViewModel.set_State("")
                                                    }
                                                    if (resultCity != null) {
                                                        constants.Start_Up_ViewModel.set_City("")
                                                    }

                                                    if (long != null) {
                                                        constants.Start_Up_ViewModel.set_Longitude(
                                                            long
                                                        )

                                                    }

                                                    if (lat != null) {
                                                        constants.Start_Up_ViewModel.set_Latitude(
                                                            lat
                                                        )
                                                    }
                                                } else {

                                                    if (resultCountry != null) {
                                                        constants.Start_Up_ViewModel.set_Country(
                                                            resultCountry
                                                        )
                                                    }
                                                    if (resultState != null) {
                                                        constants.Start_Up_ViewModel.set_State(
                                                            resultState
                                                        )
                                                    }
                                                    if (resultCity != null) {
                                                        constants.Start_Up_ViewModel.set_City(
                                                            resultCity
                                                        )
                                                    }
                                                    errorMessage = null

                                                }
                                            }
                                        }
                                        else {
                                            toast(constants.activity.getString(R.string.no_Internet))
                                        }
                                    }
                                }
                                .background(Color(0xffF7F0DC)), contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Get Details",
                                color = newBlue,
                                fontSize = constants.textUnit(12),
                                lineHeight = 36.sp,
                                fontFamily = constants.fontFamily(2),
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }

                    if (errorMessage != null) {

                        constants.Start_Up_ViewModel.set_Country("")
                        constants.Start_Up_ViewModel.set_State("")
                        constants.Start_Up_ViewModel.set_City("")

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.error),
                                contentDescription = "",
                                colorFilter = ColorFilter.tint(Color.Red),
                                modifier = Modifier.size(12.dp)
                            )

                            Spacer(Modifier.width(4.dp))

                            Text(
                                text = errorMessage ?: "",
                                color = Color.Red,
                                fontSize = constants.textUnit(12),
                                fontFamily = constants.fontFamily(2)
                            )
                        }
                    }
                }



                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Country",
                    color = if (country.value.isEmpty()) newGray else newBlack,
                    fontSize = constants.textUnit(14),
                    lineHeight = 36.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(newWhite)
                        .border(
                            1.dp,
                            if (country.value.isEmpty()) newGray else newBlack,
                            RoundedCornerShape(4.dp)
                        )
                )
                {
                    TextField(
                        value = country.value,
                        onValueChange = {
//                    if (it.length <= 6) {
//                        constants.Start_Up_ViewModel.set_Pincode(it)
//                    }
                        },
                        placeholder = {
                            Text("Country", color = newGray, fontSize = constants.textUnit(14))
                        }, readOnly = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = newWhite,
                            unfocusedContainerColor = newWhite,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            unfocusedTextColor = if (country.value.isEmpty()) newGray else newBlack,
                            focusedTextColor = Color.Black
                        ),
                        textStyle = TextStyle(
                             color = newBlack,
                            fontSize = constants.textUnit(14),
                            fontFamily = constants.fontFamily(2),
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "State",
                    color = if (state.value.isEmpty()) newGray else newBlack,
                    fontSize = constants.textUnit(14),
                    lineHeight = 36.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(newWhite)
                        .border(
                            1.dp,
                            if (state.value.isEmpty()) newGray else newBlack,
                            RoundedCornerShape(4.dp)
                        )
                )
                {
                    TextField(
                        value = state.value,
                        onValueChange = {
//                    if (it.length <= 6) {
//                        constants.Start_Up_ViewModel.set_Pincode(it)
//                    }
                        }, readOnly = true,
                        placeholder = {
                            Text("State", color = newGray, fontSize = constants.textUnit(14))
                        },
                        textStyle = TextStyle(
                             color = newBlack,
                            fontSize = constants.textUnit(14),
                            fontFamily = constants.fontFamily(2),
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = newWhite,
                            unfocusedContainerColor = newWhite,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            unfocusedTextColor = if (state.value.isEmpty()) newGray else newBlack,
                            focusedTextColor = Color.Black
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.fillMaxSize()
                    )
                }


                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "City",
                    color = if (city.value.isEmpty()) newGray else newBlack,
                    fontSize = constants.textUnit(14),
                    lineHeight = 36.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(newWhite)
                        .border(
                            1.dp,
                            if (city.value.isEmpty()) newGray else newBlack,
                            RoundedCornerShape(4.dp)
                        )
                )
                {
                    TextField(
                        value = city.value,
                        onValueChange = {
//                    if (it.length <= 6) {
//                        constants.Start_Up_ViewModel.set_Pincode(it)
//                    }
                        }, readOnly = true,
                        placeholder = {
                            Text("City", color = newGray, fontSize = constants.textUnit(14))
                        },
                        textStyle = TextStyle(
                             color = newBlack,
                            fontSize = constants.textUnit(14),
                            fontFamily = constants.fontFamily(2),
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = newWhite,
                            unfocusedContainerColor = newWhite,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            unfocusedTextColor = if (city.value.isEmpty()) newGray else newBlack,
                            focusedTextColor = Color.Black
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .noRippleClickable{
                            if (network.value == NetworkStatus.Online) {
                                no_Click_SaveLocation.value = true
                            }
                            else {
                                toast(constants.activity.getString(R.string.no_Internet))
                            }
                        }, contentAlignment = Alignment.BottomCenter
                ) {
                    GetCurrentLocationButton(context, navController, no_Click_SaveLocation)
                }
            }
        }

    }


    if (location_Settings.value) {
        Common_Popup(
            location_Settings.value,
            modifier = Modifier.background(newLightBlue),
            image = "",
            icon = R.drawable.location,
            userName = "",
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Enable Location")
                Text(
                    "Your location services are turned off. Please enable GPS to use current location.",
                    textAlign = TextAlign.Center
                    , color = newBlack,
                    fontSize = constants.textUnit(12),
                    fontFamily = constants.fontFamily(3)
                   , lineHeight = 24.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Box(
                        modifier = Modifier
                            .height(48.dp)
                            .width(164.dp)
                            .background(Color(0xffB8B8B8))
                            .noRippleClickable{
                                ClickHelper.getInstance().clickOnce {
                                    constants.Start_Up_ViewModel.setShowLocationSettings(false)
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Cancel")
                    }
                    Box(
                        modifier = Modifier
                            .height(48.dp)
                            .width(164.dp)
                            .background(newBlue)
                            .noRippleClickable{
                                ClickHelper.getInstance().clickOnce {
                                    constants.Start_Up_ViewModel.setShowLocationSettings(false)
                                    val intent =
                                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                            data =
                                                Uri.fromParts("package", context.packageName, null)
                                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        }
                                    context.startActivity(intent)
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Go to Settings", color = newWhite)
                    }
                }
                Spacer(modifier = Modifier.padding(4.dp))
            }
        }
    }

    if (showGpsDialog.value) {
        Common_Popup(
            showGpsDialog.value,
            modifier = Modifier,
            image = "",
            icon = R.drawable.location,
            userName = "",
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Enable Location")
                Text(
                    "Your location services are turned off. Please enable GPS to use current location.",
                    textAlign = TextAlign.Center
                    , color = newBlack,
                    fontSize = constants.textUnit(12),
                    fontFamily = constants.fontFamily(3)
                   , lineHeight = 24.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Box(
                        modifier = Modifier
                            .height(48.dp)
                            .width(164.dp)
                            .background(Color(0xffB8B8B8))
                            .noRippleClickable{
                                constants.Start_Up_ViewModel.setShowGpsDialog(false)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Cancel")
                    }
                    Box(
                        modifier = Modifier
                            .height(48.dp)
                            .width(164.dp)
                            .background(newBlue)
                            .noRippleClickable{
                                constants.Start_Up_ViewModel.setShowGpsDialog(false)
                                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                                context.startActivity(intent)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Go to Settings", color = newWhite)
                    }
                }
                Spacer(modifier = Modifier.padding(4.dp))
            }
        }
    }


}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchableCountryDropdown(
    CountryList: List<String>,
    focusManager: FocusManager = LocalFocusManager.current,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    focusRequester: FocusRequester
) {
    val expandedCountry by constants.Start_Up_ViewModel.expandedCountry.collectAsState()
    val searchQuery by constants.Start_Up_ViewModel.searchQuery.collectAsState()
    val showFullList by constants.Start_Up_ViewModel.showFullList.collectAsState()

    var isReadOnly by remember { mutableStateOf(false) }

    val displayList = remember(searchQuery, showFullList) {
        when {
            showFullList -> CountryList
            searchQuery.isNotBlank() -> CountryList.filter {
                it.contains(searchQuery, ignoreCase = true)
            }
            else -> emptyList()
        }
    }

    ExposedDropdownMenuBox(
        expanded = expandedCountry && displayList.isNotEmpty(),
        onExpandedChange = {
            constants.Start_Up_ViewModel.setExpandedCountry(!expandedCountry)
        }
    ) {
        TextField(
            value = searchQuery,
            onValueChange = {
                constants.Start_Up_ViewModel.setSearchQuery(it)
                constants.Start_Up_ViewModel.setShowFullList(false)

                if (it.isNotBlank() && CountryList.any { country -> country.contains(it, ignoreCase = true) }) {
                    constants.Start_Up_ViewModel.setExpandedCountry(true)
                } else {
                    constants.Start_Up_ViewModel.setExpandedCountry(false)
                }
            },
            readOnly = isReadOnly,
            textStyle = TextStyle(
                color = newGray,
                fontSize = constants.textUnit(14),
            ),
            placeholder = {
                Text(
                    text = "Select Country",
                    color = newGray,
                    fontSize = constants.textUnit(14),
                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.arrowdown),
                    contentDescription = "Expand",
                    modifier = Modifier
                        .pointerInput(expandedCountry) {
                            detectTapGestures(
                                onTap = {
                                    val shouldExpand = !expandedCountry
                                    constants.Start_Up_ViewModel.setExpandedCountry(shouldExpand)
                                    constants.Start_Up_ViewModel.setShowFullList(shouldExpand && CountryList.isNotEmpty())

                                    isReadOnly = true
                                    keyboardController?.hide()
                                    focusManager.clearFocus()
                                }
                            )
                        }
                )
            },
            modifier = Modifier
                .focusRequester(focusRequester)
                .menuAnchor()
                .fillMaxWidth()
                .border(1.dp, newGray)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        isReadOnly = false
                        focusRequester.requestFocus()
                    })
                }
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused) {
                        isReadOnly = false
                        constants.Start_Up_ViewModel.setExpandedCountry(false)
                        constants.Start_Up_ViewModel.setShowFullList(false)
                    }
                },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = newWhite,
                unfocusedContainerColor = newWhite,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedTextColor = newBlack,
              focusedTextColor = newBlack
            )
        )

        ExposedDropdownMenu(
            expanded = expandedCountry && displayList.isNotEmpty(),
            onDismissRequest = {
                constants.Start_Up_ViewModel.setExpandedCountry(false)
                constants.Start_Up_ViewModel.setShowFullList(false)
                isReadOnly = false
            },
            containerColor = newWhite
        ) {
            displayList.forEach { country ->
                DropdownMenuItem(
                    text = { Text(country) },
                    onClick = {
                        constants.Start_Up_ViewModel.updateSelectedCountry(country)
                        constants.Start_Up_ViewModel.setSearchQuery(country)
                        constants.Start_Up_ViewModel.setExpandedCountry(false)
                        constants.Start_Up_ViewModel.setShowFullList(false)
                        isReadOnly = false
                        keyboardController?.hide()
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchableStateDropdown(
    stateList: List<String>,
    focusManager: FocusManager = LocalFocusManager.current,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    focusRequester: FocusRequester
) {
    val expandedState by constants.Start_Up_ViewModel.expandedState.collectAsState()
    val searchQuery by constants.Start_Up_ViewModel.searchQuery.collectAsState()
    val showFullList by constants.Start_Up_ViewModel.showFullList.collectAsState()

    var isReadOnly by remember { mutableStateOf(false) }

    val displayList = remember(searchQuery, showFullList) {
        when {
            showFullList -> stateList
            searchQuery.isNotBlank() -> stateList.filter {
                it.contains(searchQuery, ignoreCase = true)
            }
            else -> emptyList()
        }
    }

    ExposedDropdownMenuBox(
        expanded = expandedState && displayList.isNotEmpty(),
        onExpandedChange = {
            constants.Start_Up_ViewModel.setExpandedState(!expandedState)
        }
    ) {
        TextField(
            value = searchQuery,
            onValueChange = {
                constants.Start_Up_ViewModel.setSearchQuery(it)
                constants.Start_Up_ViewModel.setShowFullList(false)

                if (it.isNotBlank() && stateList.any { state -> state.contains(it, ignoreCase = true) }) {
                    constants.Start_Up_ViewModel.setExpandedState(true)
                } else {
                    constants.Start_Up_ViewModel.setExpandedState(false)
                }
            },
            readOnly = isReadOnly,
            textStyle = TextStyle(
                color = newGray,
                fontSize = constants.textUnit(14),
            ),
            placeholder = {
                Text(
                    text = "Select State",
                    color = newGray,
                    fontSize = constants.textUnit(14),
                )
            },
            trailingIcon = {
                Icon(
                   painter = painterResource(R.drawable.arrowdown),
                    contentDescription = "Expand",
                    modifier = Modifier
                        .pointerInput(expandedState) {
                            detectTapGestures(
                                onTap = {
                                    val shouldExpand = !expandedState
                                    constants.Start_Up_ViewModel.setExpandedState(shouldExpand)
                                    constants.Start_Up_ViewModel.setShowFullList(shouldExpand && stateList.isNotEmpty())

                                    isReadOnly = true
                                    keyboardController?.hide()
                                    focusManager.clearFocus()
                                }
                            )
                        }
                )
            },
            modifier = Modifier
                .focusRequester(focusRequester)
                .menuAnchor()
                .fillMaxWidth()
                .border(1.dp, newGray)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        isReadOnly = false
                        focusRequester.requestFocus()
                    })
                }
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused) {
                        isReadOnly = false
                        constants.Start_Up_ViewModel.setExpandedState(false)
                        constants.Start_Up_ViewModel.setShowFullList(false)
                    }
                },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = newWhite,
                unfocusedContainerColor = newWhite,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedTextColor = newBlack,
              focusedTextColor = newBlack
            )
        )

        ExposedDropdownMenu(
            expanded = expandedState && displayList.isNotEmpty(),
            onDismissRequest = {
                constants.Start_Up_ViewModel.setExpandedState(false)
                constants.Start_Up_ViewModel.setShowFullList(false)
                isReadOnly = false
            },
            containerColor = newWhite
        ) {
            displayList.forEach { state ->
                DropdownMenuItem(
                    text = { Text(state) },
                    onClick = {
                        constants.Start_Up_ViewModel.updateSelectedState(state)
                        constants.Start_Up_ViewModel.setSearchQuery(state)
                        constants.Start_Up_ViewModel.setExpandedState(false)
                        constants.Start_Up_ViewModel.setShowFullList(false)
                        isReadOnly = false
                        keyboardController?.hide()
                    }
                )
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchableCityDropdown(
    cityList: List<String>,
    focusManager: FocusManager = LocalFocusManager.current,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    focusRequester: FocusRequester
) {
    val expandedCity by constants.Start_Up_ViewModel.expandedCity.collectAsState()
    val searchCity by constants.Start_Up_ViewModel.searchCity.collectAsState()
    val showFullCityList by constants.Start_Up_ViewModel.showFullCityList.collectAsState()

    var isReadOnly by remember { mutableStateOf(false) }

    val displayList = remember(searchCity, showFullCityList) {
        when {
            showFullCityList -> cityList
            searchCity.isNotBlank() -> cityList.filter {
                it.contains(searchCity, ignoreCase = true)
            }
            else -> emptyList()
        }
    }

    ExposedDropdownMenuBox(
        expanded = expandedCity && displayList.isNotEmpty(),
        onExpandedChange = {
            constants.Start_Up_ViewModel.setExpandedCity(!expandedCity)
        }
    ) {
        TextField(
            value = searchCity,
            onValueChange = {
                constants.Start_Up_ViewModel.setSearchCity(it)
                constants.Start_Up_ViewModel.setShowFullCityList(false)

                if (it.isNotBlank() && cityList.any { city -> city.contains(it, ignoreCase = true) }) {
                    constants.Start_Up_ViewModel.setExpandedCity(true)
                } else {
                    constants.Start_Up_ViewModel.setExpandedCity(false)
                }
            },
            readOnly = isReadOnly,
            textStyle = TextStyle(
                color = newGray,
                fontSize = constants.textUnit(14),
            ),
            placeholder = {
                Text(
                    text = "Select City",
                    color = newGray,
                    fontSize = constants.textUnit(14),
                )
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.arrowdown),
                    contentDescription = "Expand",
                    modifier = Modifier
                        .pointerInput(expandedCity) {
                            detectTapGestures(
                                onTap = {
                                    val shouldExpand = !expandedCity
                                    constants.Start_Up_ViewModel.setExpandedCity(shouldExpand)
                                    constants.Start_Up_ViewModel.setShowFullCityList(shouldExpand && cityList.isNotEmpty())

                                    isReadOnly = true
                                    keyboardController?.hide()
                                    focusManager.clearFocus()
                                }
                            )
                        }
                )

            },
            modifier = Modifier
                .focusRequester(focusRequester)
                .menuAnchor()
                .fillMaxWidth()
                .border(1.dp, newGray)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        isReadOnly = false
                        focusRequester.requestFocus()
                    })
                }
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused) {
                        isReadOnly = false
                        constants.Start_Up_ViewModel.setExpandedCity(false)
                        constants.Start_Up_ViewModel.setShowFullCityList(false)
                    }
                },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = newWhite,
                unfocusedContainerColor = newWhite,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedTextColor = newBlack,
              focusedTextColor = newBlack
            )
        )

        ExposedDropdownMenu(
            expanded = expandedCity && displayList.isNotEmpty(),
            onDismissRequest = {
                constants.Start_Up_ViewModel.setExpandedCity(false)
                constants.Start_Up_ViewModel.setShowFullCityList(false)
                isReadOnly = false
            },
            containerColor = newWhite
        ) {
            displayList.forEach { city ->
                DropdownMenuItem(
                    text = { Text(city) },
                    onClick = {
                        constants.Start_Up_ViewModel.updateSelectedCity(city)
                        constants.Start_Up_ViewModel.setSearchCity(city)
                        constants.Start_Up_ViewModel.setExpandedCity(false)
                        constants.Start_Up_ViewModel.setShowFullCityList(false)
                        isReadOnly = false
                        keyboardController?.hide()
                    }
                )
            }
        }
    }
}




