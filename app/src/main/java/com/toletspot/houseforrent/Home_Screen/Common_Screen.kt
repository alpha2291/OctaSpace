package com.toletspot.houseforrent.Home_Screen

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import com.google.android.libraries.places.api.Places
import com.toletspot.houseforrent.AppPreferences
import com.toletspot.houseforrent.AppUpdateDilaog
import com.toletspot.houseforrent.BuildConfig
import com.toletspot.houseforrent.Custom_Assets.BottomBar
import com.toletspot.houseforrent.Custom_Assets.Reels_TopBar
import com.toletspot.houseforrent.Custom_Assets.scaledSp
import com.toletspot.houseforrent.GlobalSnackbar
import com.toletspot.houseforrent.GlobalSnackbarHost
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.PostFlow
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.ViewDetailsFlow
import com.toletspot.houseforrent.Navigation.BottomBarScreen
import com.toletspot.houseforrent.Navigation.EnquiryGraph
import com.toletspot.houseforrent.Navigation.PostPropertyGraph
import com.toletspot.houseforrent.Navigation.Profile_Graph
import com.toletspot.houseforrent.Navigation.SearchGraph
import com.toletspot.houseforrent.Navigation.UserCredentialsScreenFlow
import com.toletspot.houseforrent.Navigation.User_Credential_Graph
import com.toletspot.houseforrent.Navigation.Videos_Graph
import com.toletspot.houseforrent.NetworkStatus
import com.toletspot.houseforrent.R
import com.toletspot.houseforrent.RentoNewScreens.AnimatedMapView
import com.toletspot.houseforrent.Start_Up.Start_Up_ViewModel
import com.toletspot.houseforrent.constants
import com.toletspot.houseforrent.noRippleClickable
import com.toletspot.houseforrent.openDialogCustom
import com.toletspot.houseforrent.rememberNetworkStatus
import com.toletspot.houseforrent.ui.theme.newBlue
import java.util.Calendar

/*
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun Common_Screen12345678   (){

    println("RECOMPOSED SCREEN")
    val navHost = rememberNavController()


    val showTABars = constants.Common_H_ViewModel.showTABars.collectAsState()
    val showBABars = constants.Common_H_ViewModel.showBABars.collectAsState()



    val selectedTab = remember {  constants.Common_H_ViewModel.tab_View }

    LaunchedEffect(constants.Common_H_ViewModel.tab_View , isConnected.value, selectedTab.value) { }



    Box (
        modifier = Modifier
            .fillMaxSize()
        , contentAlignment = Alignment.Center
    ){
        Box(modifier = Modifier.fillMaxSize()){

            NavHost(
                navController = navHost,
                startDestination =  when (selectedTab.value) {
                    0 -> {
                     BottomBarScreen.Videos.route
//

                    }
                    1 -> {
                        //LottiAnimation(0)
                        BottomBarScreen.Search.route
                    }
                    2 -> {
                        // LottiAnimation(1)

                        BottomBarScreen.Post.route

                    }
                    3 -> {
                       BottomBarScreen.Enquiry.route

                        //Enquiry_Home_Screen(navHost)
                    }
                    else ->  BottomBarScreen.Profile.route
                }

            ) {
                when (selectedTab.value) {
                    0 -> {
                        if (showTABars.value) {
                            constants.Common_H_ViewModel.toggleshowTABars(true)
                        }

//                        Box(modifier = Modifier.fillMaxSize()){

//                            NavHost(
//                                navController = navHost,
//                                startDestination = BottomBarScreen.Videos.route
////                    , enterTransition = {
////                        fadeIn(tween(300))
////                    }
////                    , exitTransition = {
////                        fadeOut(tween(300))
////                    }
//                            ) {
                                Videos_Graph(navHost, viewModel)
//                            }
//
//                            GlobalSnackbarHost()
//                        }

                    }
                    1 -> {
                        //LottiAnimation(0)

                        constants.Common_H_ViewModel.toggleshowTABars(false)
                        if (showBABars.value) {
                            constants.Common_H_ViewModel.toggleshowBABars(true)
                        }

//                        NavHost(
//                            navController = navHost,
//                            startDestination = BottomBarScreen.Search.route
//                        ) {
                            SearchGraph(navHost)
//                        }
                    }
                    2 -> {
                        // LottiAnimation(1)

                        constants.Common_H_ViewModel.toggleshowTABars(false)
                        constants.Common_H_ViewModel.toggleshowBABars(false)

                        constants.PostProperty_ViewModel.clear_Old_FormData()

//                        NavHost(
//                            navController = navHost,
//                            startDestination = BottomBarScreen.Post.route
//                        ) {
                            PostPropertyGraph(navHost)
//                        }
                    }
                    3 -> {
                        constants.Common_H_ViewModel.toggleshowTABars(false)
//                        NavHost(
//                            navController = navHost,
//                            startDestination = BottomBarScreen.Enquiry.route
//                        ) {
                            EnquiryGraph(navHost)
//                        }
                        //Enquiry_Home_Screen(navHost)
                    }
                    4 -> {
                        constants.Common_H_ViewModel.toggleshowTABars(false)
                        if (showBABars.value) {
                            constants.Common_H_ViewModel.toggleshowBABars(true)
                        }
                        if (!showTABars.value) {
                            constants.Common_H_ViewModel.toggleshowTABars(false)
                        }

//                constants.Profile_ViewModel.set_Profile_Mode(0)
//
//                constants.Profile_ViewModel.setSelectedUser(
//                    Users_Profiles_List_DC(
//                        id = 0,
//                        user_Name = "mee",
//                        real_Name = "real me",
//                        profile_Image = "",
//                        type = -1,
//                        about_User = "Two lines",
//                        num_of_Fwers = "300",
//                        num_of_Fwing = "400",
//                        isBlocked = false,
//                    )
//                )
//
//                constants.Profile_ViewModel.addProfile( Users_Profiles_List_DC(
//                    id = 0,
//                    user_Name = "mee",
//                    real_Name = "real me",
//                    profile_Image = "",
//                    type = -1,
//                    about_User = "Two lines",
//                    num_of_Fwers = "300",
//                    num_of_Fwing = "400",
//                    isBlocked = false,
//                ))

                        constants.Profile_ViewModel.put_Other_User_Id(0)
//                        NavHost(
//                            navController = navHost,
//                            startDestination = BottomBarScreen.Profile.route
//                        ) {
                            Profile_Graph(navHost)
//                        }
                    }
                }
                //Videos_Graph(navHost)
            }

            GlobalSnackbarHost()
        }
        println("TABBBB -- ${selectedTab.value}")
       */
/* when (selectedTab.value) {
            0 -> {
                if (showTABars.value) {
                    constants.Common_H_ViewModel.toggleshowTABars(true)
                }

                Box(modifier = Modifier.fillMaxSize()){

                    NavHost(
                        navController = navHost,
                        startDestination = BottomBarScreen.Videos.route
//                    , enterTransition = {
//                        fadeIn(tween(300))
//                    }
//                    , exitTransition = {
//                        fadeOut(tween(300))
//                    }
                    ) {
                        Videos_Graph(navHost)
                    }

                    GlobalSnackbarHost()
                }

            }
            1 -> {
                //LottiAnimation(0)

                constants.Common_H_ViewModel.toggleshowTABars(false)
                if (showBABars.value) {
                    constants.Common_H_ViewModel.toggleshowBABars(true)
                }

                NavHost(
                    navController = navHost,
                    startDestination = BottomBarScreen.Search.route
                ) {
                    SearchGraph(navHost)
                }
            }
            2 -> {
               // LottiAnimation(1)

                constants.Common_H_ViewModel.toggleshowTABars(false)
                constants.Common_H_ViewModel.toggleshowBABars(false)

                constants.PostProperty_ViewModel.clear_Old_FormData()

                NavHost(
                    navController = navHost,
                    startDestination = BottomBarScreen.Post.route
                ) {
                    PostPropertyGraph(navHost)
                }
            }
            3 -> {
                constants.Common_H_ViewModel.toggleshowTABars(false)
                NavHost(
                    navController = navHost,
                    startDestination = BottomBarScreen.Enquiry.route
                ) {
                    EnquiryGraph(navHost)
                }
                //Enquiry_Home_Screen(navHost)
            }
            4 -> {
                constants.Common_H_ViewModel.toggleshowTABars(false)
                if (showBABars.value) {
                    constants.Common_H_ViewModel.toggleshowBABars(true)
                }

//                constants.Profile_ViewModel.set_Profile_Mode(0)
//
//                constants.Profile_ViewModel.setSelectedUser(
//                    Users_Profiles_List_DC(
//                        id = 0,
//                        user_Name = "mee",
//                        real_Name = "real me",
//                        profile_Image = "",
//                        type = -1,
//                        about_User = "Two lines",
//                        num_of_Fwers = "300",
//                        num_of_Fwing = "400",
//                        isBlocked = false,
//                    )
//                )
//
//                constants.Profile_ViewModel.addProfile( Users_Profiles_List_DC(
//                    id = 0,
//                    user_Name = "mee",
//                    real_Name = "real me",
//                    profile_Image = "",
//                    type = -1,
//                    about_User = "Two lines",
//                    num_of_Fwers = "300",
//                    num_of_Fwing = "400",
//                    isBlocked = false,
//                ))

                constants.Profile_ViewModel.put_Other_User_Id(0)
                NavHost(
                    navController = navHost,
                    startDestination = BottomBarScreen.Profile.route
                ) {
                    Profile_Graph(navHost)
                }
            }
        }*/
/*


        // 🎛️ Top bar
        AnimatedVisibility(
            visible = showTABars.value,
            enter = fadeIn() +  slideInVertically(initialOffsetY = { -it }, animationSpec = tween(300)),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { -it }, animationSpec = tween(300)),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Reels_TopBar(modifier = Modifier)
        }

        // 🎛️ Bottom bar
        AnimatedVisibility(
            visible = showBABars.value,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it }, animationSpec = tween(300)),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it }, animationSpec = tween(300)),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
           BottomBar(
                modifier = Modifier,
                selectedIndex = selectedTab.value,
                onTabTapped = { index ->
                   // if (isConnected.value){
                    println("INDE XCHANGEEJHGBSHVB -- ${index}")
                    constants.Common_H_ViewModel.selectedBABTab(index)
                    constants.Common_H_ViewModel.selectedBABTab_new(index)
                      //  }
                }
            )
        }


    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun Common_Screen2() {

    val navController = rememberNavController()

    val showTABars = constants.Common_H_ViewModel.showTABars.collectAsStateWithLifecycle()
    val showBABars = constants.Common_H_ViewModel.showBABars.collectAsStateWithLifecycle()


    val selectedTab = constants.Common_H_ViewModel.Viewing_Screen_BB.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // 🔹 Single NavHost for all tabs
        NavHost(
            navController = navController,
            startDestination = BottomBarScreen.Videos.route
        ) {
            Videos_Graph(navController, viewModel)
            SearchGraph(navController)
            PostPropertyGraph(navController)
            EnquiryGraph(navController)
            Profile_Graph(navController)
        }

        // 🎛️ Top bar
        AnimatedVisibility(
            visible = showTABars.value,
            enter = fadeIn() + slideInVertically(initialOffsetY = { -it }, animationSpec = tween(300)),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { -it }, animationSpec = tween(300)),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Reels_TopBar(modifier = Modifier)
        }

        // 🎛️ Bottom bar
        AnimatedVisibility(
            visible = showBABars.value,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it }, animationSpec = tween(300)),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it }, animationSpec = tween(300)),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            BottomBar(
                modifier = Modifier,
                selectedIndex = selectedTab.value,
                onTabTapped = { index ->
                    constants.Common_H_ViewModel.selectedBABTab(index)

                    // 🔹 Navigate to the right route
                    val route = constants.Common_H_ViewModel.BB_Items[index].route
                    navController.navigate(route) {
                        launchSingleTop = true
                        popUpTo(BottomBarScreen.Videos.route) { inclusive = false }
                    }

                    println("index --- ${index}")
                    // 🔹 Control top/bottom bar visibility
                    when (index) {
                        0 -> {
                            constants.Common_H_ViewModel.toggleshowTABars(true)
                            constants.Common_H_ViewModel.toggleshowBABars(true)
                        }
                        1, 4 -> {
                            constants.Common_H_ViewModel.toggleshowTABars(false)
                            constants.Common_H_ViewModel.toggleshowBABars(true)
                        }
                        2 -> {
                            constants.Common_H_ViewModel.toggleshowTABars(false)
                            constants.Common_H_ViewModel.toggleshowBABars(false)
                        }
                        3 -> {
                            constants.Common_H_ViewModel.toggleshowTABars(false)
                            constants.Common_H_ViewModel.toggleshowBABars(true)
                        }
                    }
                },
               // network = rememberNetworkStatus()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun Common_Screen3() {
    val navController = rememberNavController()

    // Track current route from navController
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // 🔹 Single NavHost
        NavHost(
            navController = navController,
            startDestination = BottomBarScreen.Videos.route
        ) {
            Videos_Graph(navController, viewModel)
            SearchGraph(navController)
            PostPropertyGraph(navController)
            EnquiryGraph(navController)
            Profile_Graph(navController)
        }

        // 🎛️ Top bar — only for Videos screen
        AnimatedVisibility(
            visible = currentRoute == BottomBarScreen.Videos.route,
            enter = fadeIn() + slideInVertically(initialOffsetY = { -it }, animationSpec = tween(300)),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { -it }, animationSpec = tween(300)),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Reels_TopBar(modifier = Modifier)
        }

        // 🎛️ Bottom bar — hidden only on Post screen
        AnimatedVisibility(
            visible = currentRoute != BottomBarScreen.Post.route,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it }, animationSpec = tween(300)),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it }, animationSpec = tween(300)),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            BottomBar(
                modifier = Modifier,
                selectedIndex = constants.Common_H_ViewModel.Viewing_Screen_BB.collectAsStateWithLifecycle().value,
                onTabTapped = { index ->
                    constants.Common_H_ViewModel.selectedBABTab(index)
                    val route = constants.Common_H_ViewModel.BB_Items[index].route
                    navController.navigate(route) {
                        launchSingleTop = true
                        popUpTo(BottomBarScreen.Videos.route) { inclusive = false }
                    }
                },
                //network = rememberNetworkStatus()
            )
        }
    }
}

*/

@Composable
fun MainContent(
    selectedTab: Int,
    viewModel: Common_H_ViewModel,
    onLogout: () -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        key(selectedTab) {
            when (selectedTab) {
                0 -> {

                    VideosModule(viewModel, onLogout)
                }
                1 -> {
                    constants.Search_ViewModel.minRangeRefs.value = 2000
                    constants.Search_ViewModel.maxRangeRefs.value = 10000000

                    SearchModule(viewModel, onLogout)
                }
                2 -> PostModule(viewModel, onLogout)
                3 -> EnquiryModule(viewModel, onLogout)
                4 -> ProfileModule(viewModel, onLogout)
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun Common_Screen(
    viewModel: Common_H_ViewModel = viewModel() // or hiltViewModel()
    ,navHostController: NavHostController,
    tabIndex: Int = 0 // default to Videos tab

) {
    AppUpdateDilaog()
    println("🏠 RECOMPOSED SCREEN — VM hash: ${viewModel.hashCode()}")

    val context = LocalContext.current  // ✅ ADD THIS - Get context

    var showUpdatePopup by remember { mutableStateOf(false) }
    var latestVersionAvailable by remember { mutableStateOf("") }
    var needsUpdate by remember { mutableStateOf(false) }

//    // ✅ Check for updates on app launch
//    LaunchedEffect(Unit) {
//        openDialogCustom.value = true
//    }



    fun logout() {
       // AppPreferences.clearAll()
        navHostController.navigate(UserCredentialsScreenFlow.UserCredentials.route) {
            popUpTo(navHostController.graph.startDestinationId) {
                inclusive = true
            }
        }

        constants.Profile_ViewModel.dismiss_Logout_PP()
    }

    // lifecycle-aware collection (use collectAsStateWithLifecycle if you have lifecycle-compose)
    val showTABars = viewModel.showTABars.collectAsState()
    val showBABars = viewModel.showBABars.collectAsState()
    val selectedTab by viewModel.Viewing_Screen_BB.collectAsState()

    println("   selectedTab: $selectedTab, showTABars: $showTABars, showBABars: $showBABars")

    LaunchedEffect(selectedTab) {
        println("   📱 LaunchedEffect triggered for tab: $selectedTab")
    }


    var network = rememberNetworkStatus()
    // Set the correct tab initially
    LaunchedEffect(tabIndex) {
        viewModel.selectedBABTab(tabIndex)
    }

    // ✅ Use rememberSaveable instead of remember
    // ✅ Use rememberSaveable for location_Change
    var location_Change = remember { mutableStateOf(false) }

    // ✅ Get dialog states from ViewModel
    val showGPS = constants.Start_Up_ViewModel.showGpsDialog.collectAsState()
    val location_Settings = constants.Start_Up_ViewModel.showLocationSettings.collectAsState()
    var app_Exit by remember { mutableStateOf(false) }


    Box(Modifier.fillMaxSize()) {
//        when (selectedTab) {
//            0 -> VideosModule(viewModel ,::logout)
//            1 -> SearchModule(viewModel ,::logout)
//            2 -> PostModule(viewModel ,::logout )
//            3 -> EnquiryModule(viewModel ,::logout)
//            4 -> ProfileModule(viewModel, ::logout)
//        }

        MainContent(
            selectedTab = selectedTab,
            viewModel = viewModel,
            onLogout = {
                logout()
            }
        )


        AnimatedVisibility(
            visible = showTABars.value,
            enter = fadeIn() + slideInVertically(initialOffsetY = { -it }, animationSpec = tween(300)),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { -it }, animationSpec = tween(300)),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Reels_TopBar(modifier = Modifier , onlocation_Changr_Click = {
                location_Change.value = true
                constants.Start_Up_ViewModel.setShowLocationView(true)
            }
                ,navHostController
            )
        }

        AnimatedVisibility(
            visible = showBABars.value,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it }, animationSpec = tween(300)),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it }, animationSpec = tween(300)),
            modifier = Modifier.align(Alignment.BottomCenter)
        )
        {
            BottomBar(
                modifier = Modifier,
                selectedIndex = selectedTab,
                onTabTapped = { index ->
                    println("🎯 onTabTapped: $index  (Common_Screen VM hash: ${viewModel.hashCode()})")

                    if (network.value == NetworkStatus.Offline){
                        if (index == 0){
                            GlobalSnackbar.show("Check your Internet Connection")
                        }
                    }

                    viewModel.selectedBABTab(index)
                    constants.Enquiry_ViewModel.enquiries_View(0)
                    viewModel.selectedBABTab(index)

//                    constants.Profile_ViewModel.clearPosts()
//                    constants.Profile_ViewModel.clearownProfileContent()

//                    else {
//                        //if (index != 2 ){
//                            viewModel.selectedBABTab(index)
//                            constants.Enquiry_ViewModel.enquiries_View(0)
//                            viewModel.selectedBABTab(index)
//                            toast(constants.activity.getString(R.string.no_Internet))
                       // }
                        //else {
                            //toast(constants.activity.getString(R.string.no_Internet))
                       // }
                   // }

                    constants.Search_ViewModel.search_Profile_Name.value = ""


                    constants.Profile_ViewModel.clear_All_BF_Handler()
                    constants.Profile_ViewModel.clearAllFFData()

                    // search sort clear
                    constants.Search_ViewModel.not_Apply_FS()
                    constants.Search_ViewModel.clear_Selected_Fields_Form4()

                    constants.PostProperty_ViewModel.goToPPFormPage(0, 7)
                    constants.PostProperty_ViewModel.clearAllPostFields()

                    constants.Reels_ViewModel.resetReelsBTMSOptions()

                    constants.Search_ViewModel.search_State.value = 0
                    constants.Search_ViewModel.search_Area.value = ""

                  constants.Search_ViewModel.search_State.value = 1
                    constants.Search_ViewModel.selectedOption_SEARCHTYPE = "Residential"

                    constants.Enquiry_ViewModel.clear_EnquiryFlow()

                    constants.API_Vm.isLoading_Reels = true
                    constants.API_Vm.totalPages_Reels = 1
                    constants.PostProperty_ViewModel.resetErrors4()
                    constants.PostProperty_ViewModel.resetErrors5()
                    constants.PostProperty_ViewModel.resetErrors6()

                   constants.API_Vm.isLoading_Leads = true
                   constants.API_Vm.totalPages_Leads = 1

                    constants.Enquiry_ViewModel.clear_Selected_DateRange()

                    constants.PostProperty_ViewModel.setPostFlow(PostFlow.NONE)
                    constants.PostProperty_ViewModel.setViewDetailsFlow(ViewDetailsFlow.NONE)
                    constants.Profile_ViewModel.onSet_Settings_Click(-1)

                    constants.Enquiry_ViewModel.leads_selected_Filter = 4
                  constants.Enquiry_ViewModel.leads_selected_Sort = 1

                      //constants.Profile_ViewModel.clearPosts()

                    constants.Reels_ViewModel.clear_All_Reels()

                    AppPreferences.save_Noti_Post_Id("")
                    set_FDLP_State(false)



                }
            )
        }

        val context = LocalContext.current

        val placesClient = Places.createClient(context)

        val network = rememberNetworkStatus()
        println("LATITUDE LONGITUE -- ${AppPreferences.get_Lat_Long()}")

        AnimatedMapView( location_Change , 1, latlong = AppPreferences.get_Lat_Long(),navHostController = navHostController)
    }





    // Deactivated account dialog

    if (openDialogCustom.value) {
        AlertDialog(
            onDismissRequest = { /*TODO*/ }
            , modifier = Modifier
                .noRippleClickable{

                }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                colors = CardDefaults.cardColors(Color.Transparent),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            //.height(400.dp)
                    ) {
                        SubcomposeAsyncImage(
                            model = R.drawable.updatepopup,
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.fillMaxSize()
                        )

                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxSize()
                                .padding(bottom = 36.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom
                        )
                        {
                            Text(
                                "It's Time for an Update",
                                color = Color.Black,
                                fontSize = constants.textUnit(20),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .padding(start = 10.dp, end = 10.dp)
                                , textAlign = TextAlign.Center
                            )
                            Text(
                                "Enjoy a smoother and faster experience.",
                                color = Color.Black,
                                fontSize = constants.textUnit(14),
                                modifier = Modifier
                                    .padding(10.dp)
                                    .padding(start = 10.dp, end = 10.dp),
                                lineHeight = 25.scaledSp
                                , textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(
                                    modifier = Modifier.width(120.dp),
                                    colors = ButtonDefaults.buttonColors(newBlue),
                                    onClick = {
                                        AppPreferences.save_timestamp(Calendar.getInstance().timeInMillis)
//                                    Utils.sharedhelper.putLong(
//                                        context, Utils.UpdateTimeStamp,
//                                        Calendar.getInstance().timeInMillis)

                                        try {
                                            constants.activity.startActivity(
                                                Intent(
                                                    Intent.ACTION_VIEW,
                                                    Uri.parse("market://details?id=${BuildConfig.APPLICATION_ID}")
                                                )
                                            )
                                        } catch (e: ActivityNotFoundException) {
                                            constants.activity.startActivity(
                                                Intent(
                                                    Intent.ACTION_VIEW,
                                                    Uri.parse("https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")
                                                )
                                            )
                                        }
                                        openDialogCustom.value = false
                                    }
                                ) {
                                    Text(
                                        text = "Update",
                                        color = Color.White,
                                        fontSize = 20.scaledSp
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Button(
                                    modifier = Modifier.width(120.dp),
                                    colors = ButtonDefaults.buttonColors(Color.White),
                                    onClick = {
                                        AppPreferences.save_timestamp(Calendar.getInstance().timeInMillis)
//                                    Utils.sharedhelper.putLong(
//                                        context, Utils.UpdateTimeStamp,
//                                        Calendar.getInstance().timeInMillis
//                                    )

                                        val skip = AppPreferences.get_skipcount()

                                        val temp = skip + 1
                                        AppPreferences.save_skipcount(temp)
                                        openDialogCustom.value = false
                                    },
                                    border = BorderStroke(1.dp, newBlue)
                                ) {
                                    Text(
                                        text = "Later",
                                        color = Color.Gray,
                                        fontSize = 20.scaledSp
                                    )
                                }
                            }
                        }
                    }


                }
            }
        }
    }


    BackHandler {
        println("90876543245678908765432345678908765432345678")
        if (location_Change.value){
            location_Change.value = false
        }
        else {

        }
    }


}


// ==================== MODULE 1: VIDEOS ====================
@Composable
fun VideosModule(viewModel: Common_H_ViewModel,onLogout: () -> Unit ) {
    val navHost = rememberNavController()

    LaunchedEffect(Unit) {
        viewModel.toggleshowTABars(true)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navHost,
            startDestination = BottomBarScreen.Videos.route
        ) {
            Videos_Graph(navHost ,viewModel,onLogout = onLogout)
        }

        GlobalSnackbarHost()
    }
}

// ==================== MODULE 2: SEARCH ====================
@Composable
fun SearchModule(viewModel: Common_H_ViewModel,onLogout: () -> Unit ) {
    val navHost = rememberNavController()

    LaunchedEffect(Unit) {
        viewModel.toggleshowTABars(false)
        viewModel.toggleshowBABars(true)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navHost,
            startDestination = BottomBarScreen.Search.route
        ) {

            SearchGraph(navHost ,viewModel,onLogout = onLogout)
        }

        GlobalSnackbarHost()
    }
}

// ==================== MODULE 3: POST ====================
@Composable
fun PostModule(viewModel: Common_H_ViewModel,onLogout: () -> Unit ) {
    val navHost = rememberNavController()

    LaunchedEffect(Unit) {
        viewModel.toggleshowTABars(false)
        viewModel.toggleshowBABars(false)
        constants.PostProperty_ViewModel.clear_Old_FormData()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navHost,
            startDestination = BottomBarScreen.Post.route
        ) {

            PostPropertyGraph(navHost , viewModel,onLogout = onLogout)
        }

        GlobalSnackbarHost()
    }
}

// ==================== MODULE 4: ENQUIRY ====================
@Composable
fun EnquiryModule(viewModel: Common_H_ViewModel,onLogout: () -> Unit ) {
    val navHost = rememberNavController()

    LaunchedEffect(Unit) {
        viewModel.toggleshowTABars(false)
        viewModel.toggleshowBABars(true)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navHost,
            startDestination = BottomBarScreen.Enquiry.route
        ) {
            EnquiryGraph(navHost ,viewModel,onLogout = onLogout)
        }

        GlobalSnackbarHost()
    }
}

// ==================== MODULE 5: PROFILE ====================
@Composable
fun ProfileModule(viewModel: Common_H_ViewModel ,onLogout: () -> Unit  // ← Receive logout function
 ) {

//    val network = rememberNetworkStatus()
    val navHost = rememberNavController()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.toggleshowTABars(false)
        viewModel.toggleshowBABars(true)
        constants.Profile_ViewModel.put_Other_User_Id(0)
    }

    val placesClient = Places.createClient(context)


    Box(modifier = Modifier.fillMaxSize()
    ) {
        NavHost(
            navController = navHost,
            startDestination = BottomBarScreen.Profile.route
        ) {
            Profile_Graph(navHost , viewModel ,onLogout = onLogout )
            User_Credential_Graph(navHost , placesClient , viewModel = Start_Up_ViewModel(), onLogout)
        }

        GlobalSnackbarHost()
    }
}

// ==================== UPDATED BOTTOM BAR ====================
