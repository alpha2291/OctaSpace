package com.toletspot.houseforrent


//import androidx.compose.ui.Alignment


import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.webkit.MimeTypeMap
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.DeleteObjectsRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.messaging.FirebaseMessaging
import com.toletspot.houseforrent.API.StartUp_API.API_ViewModel
import com.toletspot.houseforrent.Chat.FirebasePresence
import com.toletspot.houseforrent.Custom_Assets.ExpandableText
import com.toletspot.houseforrent.Custom_Assets.getDeviceId
import com.toletspot.houseforrent.Custom_Assets.rememberNotchHeightDp
import com.toletspot.houseforrent.Custom_Assets.toast
import com.toletspot.houseforrent.Home_Screen.Common_H_ViewModel
import com.toletspot.houseforrent.Home_Screen.Enquiry_Module.Enquiry_ViewModel
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.PostProperty_ViewModel
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.onSomething
import com.toletspot.houseforrent.Home_Screen.ProfileModule.Profile_ViewModel
import com.toletspot.houseforrent.Home_Screen.Search_Module.Search_ViewModel
import com.toletspot.houseforrent.Home_Screen.Video_Module.Reels_ViewModel
import com.toletspot.houseforrent.Home_Screen.set_FDLP_State
import com.toletspot.houseforrent.Navigation.UserCredentialsScreenFlow
import com.toletspot.houseforrent.Start_Up.Start_Up_ViewModel
import com.toletspot.houseforrent.constants.Companion.DeepLinkImageUrl
import com.toletspot.houseforrent.constants.Companion.PROFILE_IMAGE_URL
import com.toletspot.houseforrent.constants.Companion.URL_COMPLETED
import com.toletspot.houseforrent.constants.Companion.activity
import com.toletspot.houseforrent.constants.Companion.file
import com.toletspot.houseforrent.constants.Companion.filePath
import com.toletspot.houseforrent.ui.theme.LandSalesTheme
import com.toletspot.houseforrent.ui.theme.newBlack
import com.toletspot.houseforrent.ui.theme.newGray
import com.toletspot.houseforrent.ui.theme.newLightBlue
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import java.io.File
import java.io.FileOutputStream
import java.util.Calendar

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.PostUser
import com.toletspot.houseforrent.Custom_Assets.View_Property_Structure
import com.toletspot.houseforrent.Home_Screen.Common_Screen
import com.toletspot.houseforrent.Home_Screen.Enquiry_Module.Enquiry_Home_Screen
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.Edit_Property_Option
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.PP_Fourth_Form
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.Post_Property_Forms
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.Repost_Property
import com.toletspot.houseforrent.Home_Screen.ProfileModule.Other_Profile_Structure
import com.toletspot.houseforrent.Home_Screen.ProfileModule.Profile_FF_Structure
import com.toletspot.houseforrent.Home_Screen.ProfileModule.Profile_FF_Structure_Followings
import com.toletspot.houseforrent.Home_Screen.ProfileModule.Profile_Structure
import com.toletspot.houseforrent.Home_Screen.ProfileModule.Sold_Outs
import com.toletspot.houseforrent.Home_Screen.Search_Module.Search_Filter_Sort
import com.toletspot.houseforrent.Home_Screen.Search_Module.Search_Main_Screen
import com.toletspot.houseforrent.Home_Screen.Video_Module.ReelsView
import com.toletspot.houseforrent.Home_Screen.Video_Module.ReelsView_Search_Flow
import com.toletspot.houseforrent.Home_Screen.Video_Module.SingleVideoPlayerEnquiry
import com.toletspot.houseforrent.Navigation.ProfileScreenFlow
import com.toletspot.houseforrent.Notifications.In_App_Notification
import com.toletspot.houseforrent.Start_Up.Onboarding
import com.toletspot.houseforrent.Start_Up.UserInterests
import com.toletspot.houseforrent.Start_Up.User_Credentials
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json


var isConnected = mutableStateOf(false)
var dataEnforce = mutableStateOf(false)

class MainActivity : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")

    val notiRequestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            isGranted: Boolean ->
        if (isGranted) {
            dataEnforce.value = true // notification on
        }
        else
        {

            dataEnforce.value = true


        }
    }


    companion object {
      init {
         System.loadLibrary("propreelz")
      }

        external fun getLiveUrl() : String
        external fun getDemoUrl() : String
        external fun getprofileReportUrl() : String
        external fun getPostReportUrl() : String
        external fun getSecretKey() : String
        external fun getAccessId() : String
        external fun getBucketName() : String
        external fun getBaseimageUrl() : String

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        // No need for enableEdgeToEdge() if you are manually handling system UI visibility
         enableEdgeToEdge()




        if (!Places.isInitialized()) {
            Places.initialize(applicationContext,  getString(R.string.maps_api_key))
        }

        val placesClient = Places.createClient(this)





        setContent {
            LandSalesTheme {





//                constants.activity = this
//                constants.Start_Up_ViewModel = Start_Up_ViewModel()
//                constants.Common_H_ViewModel = Common_H_ViewModel()
//                constants.Reels_ViewModel = Reels_ViewModel()
//                constants.Enquiry_ViewModel = Enquiry_ViewModel()
//                constants.Search_ViewModel = Search_ViewModel()
//                constants.PostProperty_ViewModel = PostProperty_ViewModel()
//                constants.Profile_ViewModel = Profile_ViewModel()
//                constants.API_Vm = API_ViewModel()

                activity = this

                constants.Start_Up_ViewModel = ViewModelProvider(this)[Start_Up_ViewModel::class.java]
                constants.Common_H_ViewModel = ViewModelProvider(this)[Common_H_ViewModel::class.java]
                constants.Reels_ViewModel = ViewModelProvider(this)[Reels_ViewModel::class.java]
                constants.Enquiry_ViewModel = ViewModelProvider(this)[Enquiry_ViewModel::class.java]
                constants.Search_ViewModel = ViewModelProvider(this)[Search_ViewModel::class.java]
                constants.PostProperty_ViewModel = ViewModelProvider(this)[PostProperty_ViewModel::class.java]
                constants.Profile_ViewModel = ViewModelProvider(this)[Profile_ViewModel::class.java]
                constants.API_Vm = API_ViewModel()



                isConnected.value = OnClick()

                //var loggedInUserId by remember { mutableStateOf(AppPreferences.getUserId().toString()) }

                // Observe process lifecycle

                DisposableEffect(Unit) {
                    val observer = LifecycleEventObserver { _, event ->
                        when (event) {
                            Lifecycle.Event.ON_START -> {
                                if (AppPreferences.getUserId() != -1 || AppPreferences.getUserId() != 0) {
                                    FirebasePresence.startListening(AppPreferences.getUserId().toString())
                                }
                            }

                            Lifecycle.Event.ON_STOP -> {
                                println("ON BACKGROUND APP GOES")
                                if (AppPreferences.getUserId() != -1 || AppPreferences.getUserId() != 0) {
                                    FirebasePresence.setOfflineNow(AppPreferences.getUserId().toString())
                                }
                            }

                            else -> {}
                        }
                    }
                    ProcessLifecycleOwner.get().lifecycle.addObserver(observer)
                    onDispose {
                        ProcessLifecycleOwner.get().lifecycle.removeObserver(observer)
                    }

                }

                FirebasePresence.startListening(AppPreferences.getUserId().toString())



                //DashboardScreen()

                setSystemUIVisibility(true , this)
//                val token = getDeviceToken(onTokenReceived = { token ->
//                    println("token --- $token")
//                })

//              deviceToken =  getDeviceToken()

                getDeviceToken { token ->
                    if (token?.isNotEmpty() == true) {
                        deviceToken = token
                        println("✅ Device token received: $token")
                    } else {
                        println("❌ Failed to get token")
                    }
                }

                var id = getDeviceId(this)
                println("DEVICE ID __ ${id} ")

                fun extractDeepLinkParams(uri: Uri?): Map<String, String>? {
                    uri ?: return null
                    if (!uri.toString().contains("toletspot.com")) return null

                    val segments = uri.pathSegments
                    DeepLinkImageUrl.value = uri.toString()

                    // Expecting something like: [ "profile", "1234" ] or [ "property", "1234" ]
                    if (segments.isNotEmpty()) {
                        val type = segments.getOrNull(0) ?: ""
                        val id = segments.getOrNull(1) ?: ""
                        if (type != "profile") {
                            AppPreferences.save_Noti_Post_Id(id)
                        }
                        return mapOf("type" to type, "id" to id)

                    }


                    return null
                }

                val deepliknUrl = extractDeepLinkParams(intent?.data)


                if(ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_DENIED)
                {

                }

                if (!dataEnforce.value)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                    {
                        when {
//                        utils.sharedHelper.getBoolean(this,utils.noti_ON_OFF
                            ContextCompat.checkSelfPermission(
                                this,
                                Manifest.permission.POST_NOTIFICATIONS
                            ) == PackageManager.PERMISSION_GRANTED -> {
                                println("NOTI-CHECK----1")
                                MyApp(placesClient,deepliknUrl)
//                                MainActivityContents(extras, this)
                            }

                            shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                                println("NOTI-CHECK----2")
                                MyApp(placesClient,deepliknUrl)


//                            notification.value = false
//                            utils.sharedHelper.putBoolean(this,utils.noti_ON_OFF, false)
//                            utils.sharedHelper.putBoolean(this,utils.notificationPermissionCheck,false)

                            }

                            else -> {
                                //Permission  fesDeGrand
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    notiRequestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                }
                            }
                        }
                    }
                    else
                    {
                        println("NOTI-CHECK----3")
                        MyApp(placesClient,deepliknUrl)











//                    notification.value = true
//                        utils.sharedHelper.putBoolean(this, utils.notificationEnabled, true)
//                        utils.sharedHelper.putBoolean(this, utils.noti_ON_OFF, true)
//                        notification.value = false

//                    utils.sharedHelper.putBoolean(this,utils.notificationPermissionCheck,true)

                    }
                }
                else
                {
                    println("NOTI-CHECK----4")
                    MyApp(placesClient,deepliknUrl)
                }

            }
        }
    }
}

//To hide status bar and navigation bar
fun setSystemUIVisibility(hide: Boolean, mainActivity: MainActivity) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val window = mainActivity.window.insetsController!!
        val windows = WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars()
        if (hide) window.hide(windows) else window.show(windows)
        // needed for hide, doesn't do anything in show
        window.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    } else {
        val view = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        mainActivity.window.decorView.systemUiVisibility = if (hide) view else view.inv()
    }
}


var directHome = mutableStateOf(false)



var gooooo = mutableStateOf(false)



@Composable
fun IsNavigationBarVisible(): Boolean {
    val insets = androidx.compose.foundation.layout.WindowInsets.navigationBars
    val bottomInset = insets.getBottom(LocalDensity.current)
    return bottomInset > 0
}



@Composable
fun MyApp(placesClient: PlacesClient,deepLinkUrl: Map<String, String>? = null) {
    val navController = rememberNavController()
    val startUpViewModel :  Start_Up_ViewModel = viewModel()
    val viewModel: Common_H_ViewModel = viewModel()

    val context = LocalContext.current

    fun logout() {
        // AppPreferences.clearAll()
        navController.navigate(UserCredentialsScreenFlow.UserCredentials.route) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }

        constants.Profile_ViewModel.dismiss_Logout_PP()
    }

    var Animated by remember { mutableStateOf(false) }

    val alphaAnim = animateFloatAsState(targetValue = if (Animated) 1f else 0f,
        animationSpec = tween(durationMillis = 5000),
        label = "")








    LaunchedEffect(key1 = true) {
        Animated = true
        delay(3000)
        gooooo.value = true
    }

    if (!gooooo.value) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "",
                modifier = Modifier
                    .size(250.dp)
                    .alpha(alphaAnim.value)
            )


        }
    }


    if (gooooo.value) {

        // Check login / onboarding status
        val onAuthenticationComplete =
            AppPreferences.getUserId() != -1 && AppPreferences.get_Verify_Complete() != -1
        //|| AppPreferences.get_UserToken().isEmpty()
        val onBoardingCompleted = AppPreferences.get_Onboarding_Completed()
        val userCompletedCredentials = AppPreferences.get_Interest_Completed() == 1 // true/false
        val locationReceived = AppPreferences.get_Location_Received() == 1 // true/false

        val startDestination = when {
            !onBoardingCompleted -> UserCredentialsScreenFlow.Onboarding.route
            !onAuthenticationComplete -> UserCredentialsScreenFlow.UserCredentials.route
            !userCompletedCredentials -> UserCredentialsScreenFlow.UserInterests.route
            !locationReceived -> UserCredentialsScreenFlow.UserInterests.route
            else ->
                if (deepLinkUrl.isNullOrEmpty()) {
                    println("kwdjvbjkwnvjk132jk31233232---1111")
                    UserCredentialsScreenFlow.Common_Screen.route
                } else {
                    if (deepLinkUrl?.get("type") == "profile") {
                        set_FDLP_State(true)
                        AppPreferences.save_Post_Id(0)
                        println("kwdjvbjkwnvjk132jk31233232---22221")
                        constants.Profile_ViewModel.put_Other_User_Id(
                            deepLinkUrl?.get("id")?.toInt() ?: 0
                        )
                        constants.Profile_ViewModel.add_Selected_Profile_Id(
                            deepLinkUrl?.get("id")?.toInt() ?: 0
                        )
                        UserCredentialsScreenFlow.Other_Profile_Structure.route
                    } else {
                        println("kwdjvbjkwnvjk132jk31233232---3333")
                        AppPreferences.save_Post_Id(deepLinkUrl?.get("id")?.toInt() ?: 0)
                        set_FDLP_State(true)
                        UserCredentialsScreenFlow.ReelsView.route
                    }
                }
        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            BottomCenterRotateBox()
           //Credentials_Rento()
          // ProfileHeader()
            //CalendarPickerWithBox()
           // trao()
           // PP_Fifth_Form()

//            PP_Seventh_Form(
//                onHover = mutableStateOf(false),
//                //hoveredMedia = mutableStateOf(null)
//            )

            //PreviewScreen()
            //CalendarPickerWithBox(false)

          NavHost(
                navController = navController,
                startDestination = startDestination
            )
            {
                composable  (UserCredentialsScreenFlow.UserCredentials.route) {
                    User_Credentials(navController, startUpViewModel)
                }
                composable(UserCredentialsScreenFlow.UserInterests.route) {
                    UserInterests(navController, placesClient)
                }
                composable(UserCredentialsScreenFlow.Onboarding.route) {
                    Onboarding(navController)
                }
                composable(UserCredentialsScreenFlow.Justify.route) {
                    Justify(navController)
                }
                composable(UserCredentialsScreenFlow.Common_Screen.route) {
                    val commonVM: Common_H_ViewModel = viewModel()
                    Common_Screen(commonVM, navController)
                }

                composable(
                    route = UserCredentialsScreenFlow.Common_Screen.route + "/{tabIndex}",
                    arguments = listOf(navArgument("tabIndex") { type = NavType.IntType })
                ) { backStackEntry ->
                    val tabIndex = backStackEntry.arguments?.getInt("tabIndex") ?: 0
                    val commonVM: Common_H_ViewModel = viewModel()
                    Common_Screen(commonVM, navController, tabIndex)
                }

                // ✅ ADD THIS NEW DESTINATION
                composable(
                    route = UserCredentialsScreenFlow.Justify_Post.route + "/{postId}",
                    arguments = listOf(navArgument("postId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val postId = backStackEntry.arguments?.getString("postId") ?: ""
                    Justify_Post(navController, postId)
                }

                composable(UserCredentialsScreenFlow.ReelsView.route
                ) {
                    val commonVM: Common_H_ViewModel = viewModel()
                    constants.PostProperty_ViewModel.set_Post_Form_Flow(-1)
                    ReelsView( navController , commonVM  , onLogout = {
                        logout()
                    })
                }

                composable(UserCredentialsScreenFlow.In_App_Notification.route
                ) {
                    val commonVM: Common_H_ViewModel = viewModel()
                    In_App_Notification(navController ,commonVM)
                }


                composable(UserCredentialsScreenFlow.Other_Profile_Structure.route) {
                    val commonVM: Common_H_ViewModel = viewModel()
                    Other_Profile_Structure(navController, commonVM)
                }

                composable(UserCredentialsScreenFlow.In_App_Notification.route) {
                    val commonVM: Common_H_ViewModel = viewModel()
                    In_App_Notification(navController, commonVM)
                }

                val placesClient = Places.createClient(constants.activity)
                composable(ProfileScreenFlow.UserInterests.route) {
                    UserInterests(navController, placesClient)
                }
                composable(ProfileScreenFlow.Onboarding.route) {
                    Onboarding(navController)
                }

                composable(ProfileScreenFlow.Justify.route) {
                    Justify(navController)
                }
                composable(ProfileScreenFlow.In_App_Notification.route) {
                    val commonVM: Common_H_ViewModel = viewModel()
                    In_App_Notification(navController,commonVM)
                }
                composable(ProfileScreenFlow.ReelsView.route
                ) {
                    val commonVM: Common_H_ViewModel = viewModel()
                    constants.PostProperty_ViewModel.set_Post_Form_Flow(-1)
                    ReelsView ( navController , commonVM  , onLogout = {
                        logout()
                    })
                }
                composable(ProfileScreenFlow.Common_Screen.route
                    , enterTransition = { slideInHorizontally (
                        tween(900)
                    ){ it } }){
                    Common_Screen(viewModel , navController)
                }
                composable(
                    route = ProfileScreenFlow.Justify_Post.route + "/{postId}",
                    arguments = listOf(navArgument("postId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val postId = backStackEntry.arguments?.getString("postId") ?: ""
                    Justify_Post(navController, postId)
                }

                composable(
                    ProfileScreenFlow.Profile_Structure.route
                ) {
                    constants.Common_H_ViewModel.toggleshowTABars(false)
                    Profile_Structure(navController, viewModel , onLogout = {
                        logout()
                    } )
                }

                composable(
                    ProfileScreenFlow.Profile_FF_Structure.route,
                ) {
                    viewModel.toggleshowBABars(false)
                    viewModel.toggleshowTABars(false)
                    Profile_FF_Structure(navController, viewModel)
                }

                composable(
                    route = ProfileScreenFlow.Other_Profile_Structure.route,
                ) {
                    viewModel.toggleshowBABars(false)
                    viewModel.toggleshowTABars(false)
                    Other_Profile_Structure(navController, viewModel)
                }
                composable(
                    route = ProfileScreenFlow.Edit_Property_Option.route,
                ) {
                    viewModel.toggleshowBABars(false)
                    viewModel.toggleshowTABars(false)
                    Edit_Property_Option(navController ,viewModel)
                }

                composable(
                    route = "${ProfileScreenFlow.SingleVideoPlayerEnquiry.route}/{video}",
                    arguments = listOf(navArgument("video") { type = NavType.StringType })
                )
                { backStackEntry ->
                    val videoJson = backStackEntry.arguments?.getString("video") ?: return@composable
                    val json = Json {
                        ignoreUnknownKeys = true
                    }
//                    val video = json.decodeFromString<PostUser>(Uri.decode(videoJson))
                    val video =
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.get<PostUser>("post_user")

                    viewModel.toggleshowBABars(false)
                    SingleVideoPlayerEnquiry(
                        video = video!!,
                        navController = navController,
                        reels_Show = remember { mutableStateOf(true) },
                        viewModel = viewModel
                    )
                }

                composable(ProfileScreenFlow.SingleVideoPlayerEnquiry2.route) {
                    val video = constants.Profile_ViewModel.selectedVideo.value ?: return@composable

                    viewModel.toggleshowBABars(false)
                    SingleVideoPlayerEnquiry(
                        video = video,
                        navController = navController,
                        reels_Show = remember { mutableStateOf(true) }
                        , viewModel = viewModel
                    )
                }

                composable(ProfileScreenFlow.UserCredentials.route
                    , enterTransition = { slideInHorizontally(
                        tween(900)
                    ) { it } }) {
                    val viewModel : Start_Up_ViewModel = viewModel()
                    User_Credentials(navController , viewModel)
                }




                composable(
                    route = ProfileScreenFlow.ViewPropertyStructure.route,
                ) {
                    viewModel.toggleshowBABars(false)
                    View_Property_Structure(navController, viewModel)
                }

                composable(
                    route = ProfileScreenFlow.Sold_Outs.route,
                ) {
                    viewModel.toggleshowBABars(false)
                    Sold_Outs(rememberNotchHeightDp() ,  navController)
                }

                composable(
                    route = ProfileScreenFlow.Repost_Property.route,
                ) {
                    viewModel.toggleshowBABars(false)
                    viewModel.toggleshowTABars(false)
                    Repost_Property(navController, viewModel)
                }

                composable(
                    route = ProfileScreenFlow.Post_Property_Forms.route,
                ) {
                    viewModel.toggleshowBABars(false)
                    viewModel.toggleshowTABars(false)
                    Post_Property_Forms(navController , viewModel)
                }

                composable(
                    route = ProfileScreenFlow.ReelsView_Search_Flow.route + "/{startIndex}",
                    arguments = listOf(navArgument("startIndex") { type = NavType.IntType })
                ) { backStackEntry ->
                    viewModel.toggleshowBABars(false)

                    val index = backStackEntry.arguments?.getInt("startIndex") ?: 0
                    ReelsView_Search_Flow(navController, startIndex = index , viewModel)
                }

                composable(
                    route = ProfileScreenFlow.Profile_FF_Structure_Followings.route,
                ) {
                    viewModel.toggleshowBABars(false)
                    viewModel.toggleshowTABars(false)
                    Profile_FF_Structure_Followings(navController , viewModel)
                }
                composable(ProfileScreenFlow.Search_Main_Screen.route) {
                    val commonVM: Common_H_ViewModel = viewModel()
                    commonVM.toggleshowTABars(false)
                    Search_Main_Screen(navController ,commonVM)
                }
                composable(
                    route = ProfileScreenFlow.Search_Filter_Sort.route,
                ) {
                    val commonVM: Common_H_ViewModel = viewModel()
                    commonVM.toggleshowBABars(false)
                    commonVM.toggleshowTABars(false)
                    Search_Filter_Sort(navController)
                }
                composable(ProfileScreenFlow.PP_Fourth_Form.route) {
                    var apiError = remember {  mutableStateOf(false) }
                    PP_Fourth_Form(apiError)
                }
                composable(ProfileScreenFlow.Enquiry_Home_Screen.route) {
                    viewModel.toggleshowBABars(true)
                    viewModel.toggleshowTABars(false)

                    Enquiry_Home_Screen(navController ,viewModel)
                }
            }



            //CircleToCylinderAnimation()


            //Onboarding(navController)


           // Search_Profile_Result_Screen(viewModel, navHostController)
            GlobalSnackbarHost()
        }
    }
}








@Composable
fun CircleToCylinderAnimation() {
    var expanded by remember { mutableStateOf(false) }

    val transition = updateTransition(targetState = expanded, label = "shapeTransition")

    val width: Dp by transition.animateDp(
        transitionSpec = { tween(durationMillis = 600) }, label = "width"
    ) { isExpanded ->
        if (isExpanded) 300.dp else 70.dp
    }

    val height: Dp by transition.animateDp(
        transitionSpec = { tween(durationMillis = 600) }, label = "height"
    ) { isExpanded ->
        if (isExpanded) 70.dp else 70.dp // same height
    }

    val cornerRadius: Dp by transition.animateDp(
        transitionSpec = { tween(durationMillis = 600) }, label = "corner"
    ) { isExpanded ->
        if (isExpanded) 50.dp else 50.dp // 50% of height for circle
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(width = width, height = height)
                .background(Color.Blue, shape = RoundedCornerShape(cornerRadius))
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { expanded = !expanded }) {
            Text("Animate")
        }
    }
}







@Composable
fun BottomCenterRotateBox() {
    val screenHeightDp = LocalConfiguration.current.screenHeightDp.dp
    val boxHeightDp = 150.dp
    val density = LocalDensity.current

    val screenHeightPx = with(density) { screenHeightDp.toPx() }
    val boxHeightPx = with(density) { boxHeightDp.toPx() }

    val offsetY = remember { Animatable(screenHeightPx - boxHeightPx) } // Start at bottom
    val rotation = remember { Animatable(0f) }

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        scope.launch {
                            // Step 1: Move to center
                            offsetY.animateTo(
                                (screenHeightPx - boxHeightPx) / 2,
                                animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
                            )

                            // Step 2: Rotate
                            rotation.animateTo(
                                360f,
                                animationSpec = tween(durationMillis = 800, easing = LinearEasing)
                            )

                            // Step 3: Return to bottom
                            rotation.snapTo(0f) // reset rotation
                            offsetY.animateTo(
                                screenHeightPx - boxHeightPx,
                                animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
                            )
                        }
                    }
                )
            }
    ) {
        Box(
            modifier = Modifier
                .size(200.dp, boxHeightDp)
                .background(Color.Blue)
                .offset { IntOffset(x = 0, y = offsetY.value.toInt()) }
                .rotate(rotation.value)
        )
    }
}

@Composable
fun ProfileHeader(){

    LazyColumn() {

        stickyHeader {
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
                    text = "My Profile",
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
                        .clip(RoundedCornerShape(4.dp))
                        .border(
                            1.dp,
                            newGray,
                            RoundedCornerShape(4.dp)
                        )
                        .background(Color.White)
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
                    ///.background(Color.Red)
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
            {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Brush.verticalGradient(
                            listOf(Color(0xffF7F0DC) , Color(0xffE6C96A))
                        ))
                )


                Box (
                    modifier = Modifier
                        .padding(top = 20.dp)
                ){
                    Box(
                        modifier = Modifier  .zIndex(2f)
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
                                .background(Color(0xffF7F0DC))
                        )
                        {

                            SubcomposeAsyncImage(
                                model = R.drawable.signupheader
                                    ?: "".ifEmpty { AppPreferences.get_ProfileImage() },
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
                                            text = "HIIII"
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
                            //.clip(RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .padding(horizontal = 16.dp)
                            .padding(top = 40.dp, bottom = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        constants.spacer(2)
                        Text(
                            text = "profile_Content?.username ?: ",
                            color = newBlack,
                            fontSize = constants.textUnit(18),
                            fontFamily = constants.fontFamily(0)
                        )
                        constants.spacer(2)

                        Text(
                            text = "profile_Content?.name ?:",
                            color = newGray,
                            fontSize = constants.textUnit(14),
                            fontFamily = constants.fontFamily(2)
                        )
                        constants.spacer(2)

//"This is about me in two hello lines. If the content gets longer, it will end with an ellipsis and a clickable 'see more' to view the full text."

                        ExpandableText(
                            fullText = "profile_Content?.bio ?: ",
                            maxCharacters = 80,
                            modifier = Modifier.fillMaxWidth(.9f)
                        )

                        Spacer(modifier = Modifier.padding(4.dp))

                        constants.spacer(2)

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
                                    modifier = Modifier,
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = when (itemIndex) {
                                            0 -> " ${0}"
                                            1 -> "${999}"
                                            2 -> "${999}"
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
        items(40){
            index ->

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color.Black)
            )

            Spacer(modifier = Modifier.padding(12.dp))

        }
    }

/*
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ){
        Box(
            modifier = Modifier
                .background(Color.Red)
                .fillMaxWidth()
                .wrapContentHeight()
        )
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Brush.verticalGradient(
                        listOf(Color(0xffF7F0DC) , Color(0xffE6C96A))
                    ))
            )
            Box(
                modifier = Modifier
                    .padding(bottom = 40.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            )
            {
                Text(
                    text = "My Profile",
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
                        .clip(RoundedCornerShape(4.dp))
                        .border(
                            1.dp,
                            newGray,
                            RoundedCornerShape(4.dp)
                        )
                        .background(Color.White)
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


            Box (
                modifier = Modifier
                    .padding(top = 60.dp)
            ){
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .size(if (forTab()) 92.dp else 80.dp)
                        .clip(CircleShape)
                        .background(newWhite)
                        .zIndex(2f)
                )
                {

                    SubcomposeAsyncImage(
                        model = R.drawable.signupheader
                            ?: "".ifEmpty { AppPreferences.get_ProfileImage() },
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
                                    text = "HIIII"
                                )
                            }
                        } else {
                            SubcomposeAsyncImageContent()
                        }
                    }

                }

                Column(
                    modifier = Modifier
                        .padding(top = if (forTab()) 46.dp else 40.dp)
                        .zIndex(0f)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Green)
                        .padding(horizontal = 16.dp)
                        .padding(top = 40.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    constants.spacer(2)
                    Text(
                        text = "profile_Content?.username ?: ",
                        color = newBlack,
                        fontSize = constants.textUnit(18),
                        fontFamily = constants.fontFamily(0)
                    )
                    constants.spacer(2)

                    Text(
                        text = "profile_Content?.name ?:",
                        color = newGray,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                    )
                    constants.spacer(2)

//"This is about me in two hello lines. If the content gets longer, it will end with an ellipsis and a clickable 'see more' to view the full text."

                    ExpandableText(
                        fullText = "profile_Content?.bio ?: ",
                        maxCharacters = 80,
                        modifier = Modifier.fillMaxWidth(.9f)
                    )

                    Spacer(modifier = Modifier.padding(4.dp))

                    constants.spacer(2)

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
                                modifier = Modifier,
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = when (itemIndex) {
                                        0 -> " ${0}"
                                        1 -> "${999}"
                                        2 -> "${999}"
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
    }*/
}


fun getDeviceToken(onTokenReceived: (String?) -> Unit) {
    FirebaseMessaging.getInstance().token
        .addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                onTokenReceived(null)
                return@addOnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            Log.d("FCM", "Device token: $token")
            onTokenReceived(token)
        }
}


fun isLocationEnabled(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}

@SuppressLint("MissingPermission")
fun getCurrentLocation(
    fusedLocationClient: FusedLocationProviderClient,
    onLocationRetrieved: (Location) -> Unit
) {
    fusedLocationClient.getCurrentLocation(
        Priority.PRIORITY_HIGH_ACCURACY,
        null
    ).addOnSuccessListener { location ->
        location?.let { onLocationRetrieved(it) }
    }
}

fun showEnableGPSDialog(context: Context, onPositiveClick: () -> Unit) {
    AlertDialog.Builder(context)
        .setTitle("Enable Location")
        .setMessage("Your device location is turned off. Please enable it to continue.")
        .setPositiveButton("Enable") { _, _ -> onPositiveClick() }
        .setNegativeButton("Cancel", null)
        .show()
}





// Make file nullable instead of lateinit
var file: File? = null

// Get file extension from URI
fun getFileExtension(context: Context, uri: Uri): String? {
    val contentResolver: ContentResolver = context.contentResolver

    return if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
        contentResolver.getType(uri)?.let { MimeTypeMap.getSingleton().getExtensionFromMimeType(it) }
    } else {
        uri.path?.let { MimeTypeMap.getFileExtensionFromUrl(it) }
    }
}

// AWS File Upload - Safe Version
//lateinit var file: File
var filePath: Uri? = null

fun GenertateLink(
    mainActivity: MainActivity,
    uri: Uri,
    navController: NavHostController,
    onComplete: () -> Unit
) {
    val fileExt = getFileExtension(mainActivity, uri) ?: run {
        onComplete()
        return
    }

    try {
        filePath = uri
        file = File.createTempFile("media", ".$fileExt", mainActivity.cacheDir)

        mainActivity.contentResolver.openInputStream(filePath!!)?.use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }

        val credentials = BasicAWSCredentials(constants.ACCESS_ID, constants.SECRET_KEY)
        val s3Client = AmazonS3Client(credentials).apply {
            setRegion(Region.getRegion(Regions.AP_SOUTH_1))
        }
        constants.s3Client = s3Client

        UploadS3000(mainActivity, fileExt, 1005, navController) {
            onComplete()
        }

    } catch (e: Exception) {
        e.printStackTrace()
        onComplete() // continue even if error
    }
}

// Upload function
fun UploadS3000(
    mainActivity: MainActivity,
    fileExt: String,
    requestCode: Int,
    navController: NavHostController,
    onComplete: () -> Unit
) {
    val userId = AppPreferences.getUserId() ?: run {
        onComplete()
        return
    }

    val fileToUpload = file ?: run {
        toast("❌ No file selected")
        onComplete()
        return
    }



    val metadata = ObjectMetadata().apply {
        contentType = when (fileExt.lowercase()) {
            "mp3" -> "audio/mpeg"
            "m4a" -> "audio/mp4"
            "mp4" -> "video/mp4"
            "wav" -> "audio/wav"
            "jpg", "jpeg" -> "image/jpeg"
            "png" -> "image/png"
            else -> "application/octet-stream"
        }
        contentDisposition = "inline"
    }

    val folder = if (metadata.contentType == "jpg" || metadata.contentType == "png") "postImages" else "postVideos"
    val timestamp = System.currentTimeMillis()
    val fileName = "${folder.substringAfterLast("/")}_${timestamp}.$fileExt"
    val newKey = "$userId/post/$folder/${folder.dropLast(1)}_$timestamp.$fileExt"



    val s3Client = constants.s3Client ?: AmazonS3Client(
        BasicAWSCredentials(constants.ACCESS_ID, constants.SECRET_KEY)
    ).apply {
        setRegion(Region.getRegion(Regions.AP_SOUTH_1))
        constants.s3Client = this
    }

    Thread {
        try {
            // Delete old files
            val existingFiles = s3Client.listObjects(constants.BUCKET_NAME, "$userId/$folder/")
                .objectSummaries
                .filter { !it.key.endsWith("/") }

            if (existingFiles.isNotEmpty()) {
                val deleteReq = DeleteObjectsRequest(constants.BUCKET_NAME).withKeys(
                    existingFiles.map { DeleteObjectsRequest.KeyVersion(it.key) }
                )
                s3Client.deleteObjects(deleteReq)
            }

            // Upload new file
            val putRequest = PutObjectRequest(constants.BUCKET_NAME, newKey, fileToUpload).apply {
                this.metadata = metadata
            }
            s3Client.putObject(putRequest)

            val finalUrl = constants.CLOUD_FRONT_URL + "/$newKey"

            if (constants.Profile_ViewModel.from_Profile_Pic_Update.value == true) {

                PROFILE_IMAGE_URL.value = finalUrl
            }
            val type = if (fileExt.lowercase() == "mp4") 1 else 0

            URL_COMPLETED.add(Pair(type, finalUrl))

            mainActivity.runOnUiThread {
//                println("🌐 Uploaded URL: $finalUrl __ ${constants.PostProperty_ViewModel.get_Media().size} -- ${URL_COMPLETED.size}")

                onSomething.value = 2134 + URL_COMPLETED.size

               /* if (constants.PostProperty_ViewModel.get_Media().size == URL_COMPLETED.size){
                    val videoUrl = URL_COMPLETED.find { it.first == 1 }?.second ?: ""
                    val imageUrls = URL_COMPLETED.filter { it.first == 0 }.map { it.second }
                    val type = if (videoUrl.isEmpty()) "2" else "1"

//                   var imageUrls2 = imageUrls.map { it.replace("\\", "") }
                    var imageUrls2 = imageUrls.map { it.replace("\\", "") }.distinct()

                    val result = imageUrls2

                    println("dschjvcdsbjcdsvjhcdvwj -- ${videoUrl} --${type} ${imageUrls2} -- ${result}")
                    println("WHERE TO FOND 111111")
                    constants.API_Vm.put_post_Form6(
                        user_id = AppPreferences.getUserId(),
                        user_post_id = AppPreferences.get_Post_Id(),
                        post_type = type,
                        video_url = videoUrl,
                        image_urls = result
                    )
                    {
                        aPI_Result_Handling ->
                        when (aPI_Result_Handling) {
                            is API_Result_Handling.NoData -> {
                                constants.PostProperty_ViewModel.change_Status_PFs(false)
                            }
                            is API_Result_Handling.Deactivated -> {
                                //resultCallback(5)
                            }
                            is API_Result_Handling.Loading -> {
                                println("✅ Post submitted loading")
                            }

                            is API_Result_Handling.Error -> {
                                constants.PostProperty_ViewModel.change_Status_PFs(false)
                                println("✅ Post submitted failurre")
                                //toast("Something Went wrong")
                            }

                            is API_Result_Handling.Success -> {
                                constants.PostProperty_ViewModel.change_Status_PFs(false)
                                println("✅ Post submitted successfully")

                                constants.Profile_ViewModel.set_From_Repost(1)
                                constants.PostProperty_ViewModel.set_Post_Form_Flow(1)

                                constants.PostProperty_ViewModel.change_Status_PFs(false)


                                constants.PostProperty_ViewModel.set_Post_Form_Flow(
                                    999
                                )
                                navController.navigate(PostPropertyFlow.ViewPropertyStructure.route)
                            }
                        }

                    }
                }*/
            }

            onComplete()
        } catch (e: Exception) {
            e.printStackTrace()
            mainActivity.runOnUiThread {
                toast("❌ Upload failed: ${e.localizedMessage ?: "Unknown"}")
            }
            onComplete()
        }
    }.start()
}


@SuppressLint("SuspiciousModifierThen")
fun Modifier.noRippleClickable(enabled: Boolean = true, onClick: () -> Unit): Modifier = composed {

    this.then( // Use the Modifier instance

        clickable(

            enabled = enabled,

            indication = null,

            interactionSource = remember { MutableInteractionSource() }

        ) {

            onClick()

        }

    )

}







@Composable
fun SimpleSnackbar(
    message: String,
    duration: SnackbarDuration = SnackbarDuration.Short
) {
    val snackbarHostState = remember { SnackbarHostState() }

    // Launch the snackbar when message changes
    LaunchedEffect(message) {
        if (message.isNotEmpty()) {
            snackbarHostState.showSnackbar(
                message = message,
                duration = duration
            )
        }
    }

    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}


enum class ScrollDirection { UP, DOWN, IDLE }

@Composable
fun rememberLazyListScrollDirection(
    listState: LazyListState,
    reverseLayout: Boolean = false,
    onlyWhileScrolling: Boolean = false // if true, updates only while listState.isScrollInProgress == true
): State<ScrollDirection> {
    val direction = remember { mutableStateOf(ScrollDirection.IDLE) }

    LaunchedEffect(listState, reverseLayout, onlyWhileScrolling) {
        var prevIndex = listState.firstVisibleItemIndex
        var prevOffset = listState.firstVisibleItemScrollOffset

        snapshotFlow {
            // collect both scrolling flag and (index, offset)
            Triple(listState.isScrollInProgress, listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset)
        }
            // optional: only update while the user (or fling) is in progress
            .let { flow ->
                if (onlyWhileScrolling) flow.filter { it.first } else flow
            }
            .map { (_, index, offset) -> index to offset }
            .collect { (index, offset) ->
                val newDir = when {
                    index > prevIndex -> ScrollDirection.DOWN
                    index < prevIndex -> ScrollDirection.UP
                    offset > prevOffset -> ScrollDirection.DOWN
                    offset < prevOffset -> ScrollDirection.UP
                    else -> ScrollDirection.IDLE
                }

                prevIndex = index
                prevOffset = offset

                direction.value = if (reverseLayout) {
                    // if you used reverseLayout = true, invert the sense of UP/DOWN
                    when (newDir) {
                        ScrollDirection.UP -> ScrollDirection.DOWN
                        ScrollDirection.DOWN -> ScrollDirection.UP
                        else -> ScrollDirection.IDLE
                    }
                } else newDir
            }
    }

    return direction
}



// ============================================================
// 2️⃣ UPDATE POPUP COMPOSABLE
// ============================================================


// Compare version strings

fun isVersionOlder(currentVersion: String, newVersion: String): Boolean {
    val current = currentVersion.split(".").map { it.toIntOrNull() ?: 0 }
    val new = newVersion.split(".").map { it.toIntOrNull() ?: 0 }

    for (i in 0 until maxOf(current.size, new.size)) {
        val c = current.getOrNull(i) ?: 0
        val n = new.getOrNull(i) ?: 0

        if (c < n) return true
        if (c > n) return false
    }

    return false
}



var openDialogCustom = mutableStateOf(false)
inline fun <reified T> getDuration(current: Long, old: Long): T {
    val milliseconds = current - old
    val days: Int = ((milliseconds / (1000 * 60 * 60 * 24)).toInt())
    val hours: Int = (((milliseconds - 1000 * 60 * 60  * 24 * days) / (1000  *60 * 60)).toInt())
    val min: Int =
        ((milliseconds - 1000 * 60 * 60 * 24 * days - 1000 * 60  *60  *hours) / (1000 * 60)).toInt()


    return when (T::class) {
        Int::class -> days as T
        else -> min.toLong() as T
    }
}

fun AppUpdateDilaog() {
    if (checkForInternet(activity)) {
        val appUpdateManager = AppUpdateManagerFactory.create(activity)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo


        if(AppPreferences.get_version() == 0) {
            appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->

                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
                ) {
                    //sharedhelper.putInt(activity,Utils.UpdateVersionCode,appUpdateInfo.availableVersionCode())
                    AppPreferences.save_version( appUpdateInfo.availableVersionCode())
                    openDialogCustom.value = true

                }
            }
        }
        else if(AppPreferences.get_version()   != BuildConfig.VERSION_CODE && getDuration<Int>(
                Calendar.getInstance().timeInMillis, AppPreferences.get_timestamp()
            ) >= 5 && AppPreferences.get_skipcount() < 3)
        {
            appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->

                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
                ) {
                    //sharedhelper.putInt(activity,Utils.UpdateVersionCode,appUpdateInfo.availableVersionCode())
                    AppPreferences.save_version(appUpdateInfo.availableVersionCode())
                    openDialogCustom.value = true

                }
            }
        }
        else if(AppPreferences.get_skipcount() >= 2)
        {
            appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->

                if(AppPreferences.get_version() != appUpdateInfo.availableVersionCode()){
                    if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
                    ) {
                       // sharedhelper.putInt(activity,Utils.UpdateVersionCode,appUpdateInfo.availableVersionCode())
                        AppPreferences.save_version(appUpdateInfo.availableVersionCode())
//                        sharedhelper.putInt(activity,Utils.skipCount,)
                        AppPreferences.save_skipcount(0)
                        openDialogCustom.value = true
                    }
                }
            }

        }

    }
}




@Composable
fun UpdatePopup(
    onUpdateClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.7f))
            .pointerInput(Unit) { detectTapGestures { } },
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .wrapContentHeight()
                .clip(RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // ✅ Icon
                Image(
                    painter = painterResource(id = R.drawable.app_logo),
                    contentDescription = "Update Available",
                    modifier = Modifier.size(64.dp)
                )

                // ✅ Title (FIXED: Larger font)
                Text(
                    text = "Update Available",
                    color = Color.Black,
                    fontSize = constants.textUnit(20),  // ✅ CHANGED from 12 to 20
                    fontFamily = constants.fontFamily(0),
                    fontWeight = FontWeight.Bold,  // ✅ ADDED
                    textAlign = TextAlign.Center
                )

                // ✅ Description (FIXED: Correct font size)
                Text(
                    text = "A new version of the app is available. Please update to continue using all features.",
                    color = Color.Gray,
                    fontSize = constants.textUnit(12),  // ✅ CHANGED from 14 to 12
                    fontFamily = constants.fontFamily(1),
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // ✅ Buttons Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Cancel Button
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.5.dp, Color(0xffE54C3C), RoundedCornerShape(8.dp))
                            .noRippleClickable {
                                AppPreferences.saveUpdatePopupDismissTime()
                                onCancelClick()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Cancel",
                            color = Color(0xffE54C3C),
                            fontSize = constants.textUnit(14),
                            fontFamily = constants.fontFamily(0)
                        )
                    }

                    // Update Button
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xff007AFF))
                            .noRippleClickable {
                                AppPreferences.clearUpdatePopupDismissTime()
                                onUpdateClick()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Update Now",
                            color = Color.White,
                            fontSize = constants.textUnit(14),
                            fontFamily = constants.fontFamily(1)
                        )
                    }
                }
            }
        }
    }
}


