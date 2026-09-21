package com.toletspot.houseforrent.Navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.toletspot.houseforrent.AppPreferences
import com.toletspot.houseforrent.Chat.Msg_ChatScreen
import com.toletspot.houseforrent.Chat.Msg_UserList
import com.toletspot.houseforrent.Custom_Assets.View_Property_Structure
import com.toletspot.houseforrent.Custom_Assets.rememberNotchHeightDp
import com.toletspot.houseforrent.Home_Screen.Common_H_ViewModel
import com.toletspot.houseforrent.Home_Screen.Common_Screen
import com.toletspot.houseforrent.Home_Screen.Enquiry_Module.EnquiryFlow
import com.toletspot.houseforrent.Home_Screen.Enquiry_Module.Enquiry_Home_Screen

import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.Edit_Property_Option
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.PP_Fourth_Form
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.Post_Property_Forms
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.PreviewScreen
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
import com.toletspot.houseforrent.Justify
import com.toletspot.houseforrent.Justify_Post
import com.toletspot.houseforrent.Notifications.In_App_Notification
import com.toletspot.houseforrent.Start_Up.Onboarding
import com.toletspot.houseforrent.Start_Up.Start_Up_ViewModel
import com.toletspot.houseforrent.Start_Up.UserInterests
import com.toletspot.houseforrent.Start_Up.User_Credentials
import com.toletspot.houseforrent.WebView.AboutUsScreen
import com.toletspot.houseforrent.WebView.FeedBackScreen
import com.toletspot.houseforrent.WebView.TermsAndPrivacyScreen
import com.toletspot.houseforrent.constants
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


fun NavGraphBuilder.User_Credential_Graph(
    navController: NavHostController,
    placesClient: PlacesClient,
    viewModel: Start_Up_ViewModel,
     onLogout: () -> Unit
) {
    navigation(
        startDestination = UserCredentialsScreenFlow.UserCredentials.route,
        route = "user_credentials_graph"
    ) {

        composable(UserCredentialsScreenFlow.UserCredentials.route) {
            User_Credentials(navController, viewModel)
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
        composable(UserCredentialsScreenFlow.In_App_Notification.route) {
            val commonVM: Common_H_ViewModel = viewModel()
            In_App_Notification(navController,commonVM)
        }

//        composable(UserCredentialsScreenFlow.Other_Profile_Structure.route) {
//            val commonVM: Common_H_ViewModel = viewModel()
//            Other_Profile_Structure(navController,commonVM)
//        }

        composable(
            route = VideosScreenFlow.Other_Profile_Structure.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it }, // from right
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it }, // to left
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it }, // from left
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it }, // to right
                    animationSpec = tween(300)
                )
            }
        ) {

            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)
            commonVM.toggleshowTABars(false)
            Other_Profile_Structure(navController, commonVM)
        }

        composable(
            route = UserCredentialsScreenFlow.Justify_Post.route + "/{postId}",
            arguments = listOf(navArgument("postId") { type = NavType.StringType })
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId") ?: ""
            Justify_Post(navController, postId)
        }
        composable(
            route = UserCredentialsScreenFlow.Justify_Post.route + "/{startIndex}",
            arguments = listOf(navArgument("startIndex") { type = NavType.IntType })
        ) { backStackEntry ->
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)

            val index = backStackEntry.arguments?.getInt("startIndex") ?: 0
            ReelsView_Search_Flow(navController, startIndex = index , commonVM)
        }
        composable(UserCredentialsScreenFlow.ReelsView.route
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            constants.PostProperty_ViewModel.set_Post_Form_Flow(-1)
            ReelsView( navController , commonVM  , onLogout = onLogout)
        }
        composable(UserCredentialsScreenFlow.ViewPropertyStructure.route
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            View_Property_Structure(navController ,commonVM)
        }
        composable(UserCredentialsScreenFlow.Profile_FF_Structure.route
            , enterTransition = { slideInHorizontally (
                tween(900)
            ){ it } }
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            Profile_FF_Structure(navController ,commonVM)
        }
        composable(
            route = UserCredentialsScreenFlow.Repost_Property.route,
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            constants.Common_H_ViewModel.toggleshowBABars(false)
            constants.Common_H_ViewModel.toggleshowTABars(false)
            Repost_Property(navController ,commonVM)
        }
        composable(
            route = UserCredentialsScreenFlow.Profile_FF_Structure_Followings.route,
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            constants.Common_H_ViewModel.toggleshowBABars(false)
            constants.Common_H_ViewModel.toggleshowTABars(false)
            Profile_FF_Structure_Followings(navController, commonVM)
        }
        composable(
            route = UserCredentialsScreenFlow.Edit_Property_Option.route,
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)
            commonVM.toggleshowTABars(false)
            Edit_Property_Option(navController ,commonVM)
        }
//        composable(
//            route = "${UserCredentialsScreenFlow.SingleVideoPlayerEnquiry.route}/{video}",
//            arguments = listOf(navArgument("video") { type = NavType.StringType })
//        )
//        { backStackEntry ->
//            val commonVM: Common_H_ViewModel = viewModel()
//            val videoJson = backStackEntry.arguments?.getString("video") ?: return@composable
//            val json = Json {
//                ignoreUnknownKeys = true
//            }
//            val video = json.decodeFromString<PostUser>(Uri.decode(videoJson))
////            val video =
////                navController.previousBackStackEntry
////                    ?.savedStateHandle
////                    ?.get<PostUser>("post_user")
//
//            commonVM.toggleshowBABars(false)
//            SingleVideoPlayerEnquiry(
//                video =  video,
//                navController = navController,
//                reels_Show = remember { mutableStateOf(true) },
//                viewModel = commonVM
//            )
//        }
        composable(UserCredentialsScreenFlow.SingleVideoPlayerEnquiry.route) {

            val postId = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Int>("post_id") ?: return@composable

            val myLeads = constants.Enquiry_ViewModel.my_Leads.collectAsState()
            val selfEnquiry = constants.Enquiry_ViewModel.self_Enquiry.collectAsState()
            val messagechatlist = constants.Enquiry_ViewModel.selectedChatData.collectAsState()

            var enquiryFlow = constants.Enquiry_ViewModel.enquiryFlows.collectAsState()

            println("SIZE OF DATAS --_${enquiryFlow.value}")
            val currentVideo = remember(myLeads, selfEnquiry, postId ,messagechatlist ) {
                when {
                    enquiryFlow.value == EnquiryFlow.LEADS -> {
                        myLeads.value.firstOrNull { it.post_user.user_post_id == postId }?.post_user
                        //println("SIZE OF DATAS --leadss")
                    }

                    enquiryFlow.value == EnquiryFlow.SELF -> {
                        selfEnquiry.value.firstOrNull { it?.post_user?.user_post_id == postId }?.post_user
                            ?: messagechatlist.value?.video_model
                        //println("SIZE OF DATAS --sekf")
                    }

                    else -> {
                        //println("SIZE OF DATAS -chatt-")
                        messagechatlist.value?.video_model
                    }

                }
            }

            if (currentVideo == null) {
                // Optional: loading / fallback UI
                return@composable
            }

//            viewModel.toggleshowBABars(false)
//            viewModel.toggleshowTABars(false)
            val commonVM: Common_H_ViewModel = viewModel()

            SingleVideoPlayerEnquiry(
                video = currentVideo,
                navController = navController,
                reels_Show = remember { mutableStateOf(true) },
                viewModel = commonVM
            )
        }

        composable(UserCredentialsScreenFlow.SingleVideoPlayerEnquiry2.route) {
            val commonVM: Common_H_ViewModel = viewModel()
            val video = constants.Profile_ViewModel.selectedVideo.value ?: return@composable

//            val video =
//                navController.previousBackStackEntry
//                    ?.savedStateHandle
//                    ?.get<PostUser>("post_user")


            commonVM.toggleshowBABars(false)
            SingleVideoPlayerEnquiry(
                video = video,
                navController = navController,
                reels_Show = remember { mutableStateOf(true) }
                , viewModel = commonVM
            )
        }
        composable(
            route = UserCredentialsScreenFlow.Sold_Outs.route,
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)
            Sold_Outs(rememberNotchHeightDp() ,  navController)
        }
        composable(
            route = UserCredentialsScreenFlow.Post_Property_Forms.route,
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)
            commonVM.toggleshowTABars(false)
            Post_Property_Forms(navController , commonVM)
        }
        composable(
            route = UserCredentialsScreenFlow.ReelsView_Search_Flow.route + "/{startIndex}",
            arguments = listOf(navArgument("startIndex") { type = NavType.IntType })
        ) { backStackEntry ->
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)

            val index = backStackEntry.arguments?.getInt("startIndex") ?: 0
            ReelsView_Search_Flow(navController, startIndex = index , commonVM)
        }
        composable(
            route = UserCredentialsScreenFlow.TermsAndPrivacyScreen.route,
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)
            commonVM.toggleshowTABars(false)
            TermsAndPrivacyScreen(navController)
        }
        composable(
            route = UserCredentialsScreenFlow.FeedBackScreen.route,
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)
            commonVM.toggleshowTABars(false)
            FeedBackScreen(navController)
        }
        composable(
            route = UserCredentialsScreenFlow.AboutUsScreen.route,
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)
            commonVM.toggleshowTABars(false)
            AboutUsScreen(navController)
        }
        composable(UserCredentialsScreenFlow.Search_Main_Screen.route) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowTABars(false)
            Search_Main_Screen(navController ,commonVM)
        }
        composable(
            route = UserCredentialsScreenFlow.Search_Filter_Sort.route,
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)
            commonVM.toggleshowTABars(false)
            Search_Filter_Sort(navController)
        }
        composable(UserCredentialsScreenFlow.PP_Fourth_Form.route) {
            var apiError = remember {  mutableStateOf(false) }
            PP_Fourth_Form(apiError)
        }
        composable(UserCredentialsScreenFlow.Enquiry_Home_Screen.route) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)
            commonVM.toggleshowTABars(false)
            Enquiry_Home_Screen(navController ,commonVM)
        }
    }
}

fun NavGraphBuilder.Videos_Graph(navController: NavHostController,
                                 viewModel: Common_H_ViewModel , onLogout: () -> Unit) {
    navigation(
        startDestination = VideosScreenFlow.ReelsView.route,
        route = BottomBarScreen.Videos.route
    ) {
        val viewModel2 = ViewModelProvider(constants.activity)[Start_Up_ViewModel :: class.java]
        composable(VideosScreenFlow.UserCredentials.route) {
            User_Credentials(navController, viewModel2)
        }
        val placesClient = Places.createClient(constants.activity)
        composable(VideosScreenFlow.UserInterests.route) {
            UserInterests(navController, placesClient)
        }
        composable(VideosScreenFlow.Onboarding.route) {
            Onboarding(navController)
        }

        composable(VideosScreenFlow.Justify.route) {
            Justify(navController)
        }

        composable(VideosScreenFlow.Common_Screen.route
            , enterTransition = { slideInHorizontally (
                tween(900)
            ){ it } }){
            Common_Screen(viewModel ,navController)
        }

        composable(
            route = VideosScreenFlow.ReelsView_Search_Flow.route + "/{startIndex}",
            arguments = listOf(navArgument("startIndex") { type = NavType.IntType })
        ) { backStackEntry ->
            viewModel.toggleshowBABars(false)

            val index = backStackEntry.arguments?.getInt("startIndex") ?: 0
            ReelsView_Search_Flow(navController, startIndex = index , viewModel)
        }

        composable(VideosScreenFlow.ReelsView.route
        ) {
//            val showTABars = viewModel.showTABars.collectAsState()
//            val showBABars = viewModel.showBABars.collectAsState()

            constants.PostProperty_ViewModel.set_Post_Form_Flow(-1)
            ReelsView( navController , viewModel  , onLogout = onLogout)
        }

        composable(VideosScreenFlow.ViewPropertyStructure.route
        ) {
            View_Property_Structure(navController ,viewModel)
        }
        composable(VideosScreenFlow.In_App_Notification.route
        ) {
            In_App_Notification(navController ,viewModel)
        }

        composable(
            VideosScreenFlow.Profile_Structure.route
        ) {

            Profile_Structure(navController, viewModel , onLogout)
        }

//        composable(
//            VideosScreenFlow.Other_Profile_Structure.route
//        ) {
//
//            Other_Profile_Structure(navController ,viewModel)
//        }

        composable(
            route = VideosScreenFlow.Other_Profile_Structure.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it }, // from right
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it }, // to left
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it }, // from left
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it }, // to right
                    animationSpec = tween(300)
                )
            }
        )
        {
            Other_Profile_Structure(navController, viewModel)
        }

        composable(VideosScreenFlow.Profile_FF_Structure.route
            , enterTransition = { slideInHorizontally (
            tween(900)
        ){ it } }
        ) {
            Profile_FF_Structure(navController ,viewModel)
        }



        composable(
            route = VideosScreenFlow.Repost_Property.route,
        ) {
            constants.Common_H_ViewModel.toggleshowBABars(false)
            constants.Common_H_ViewModel.toggleshowTABars(false)
            Repost_Property(navController ,viewModel)
        }

        composable(
            route = VideosScreenFlow.Profile_FF_Structure_Followings.route,
        ) {
            constants.Common_H_ViewModel.toggleshowBABars(false)
            constants.Common_H_ViewModel.toggleshowTABars(false)
            Profile_FF_Structure_Followings(navController, viewModel)
        }

        composable(
            route = VideosScreenFlow.Justify_Post.route + "/{postId}",
            arguments = listOf(navArgument("postId") { type = NavType.StringType })
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId") ?: ""
            Justify_Post(navController, postId)
        }

        composable(
            route = VideosScreenFlow.Edit_Property_Option.route,
        ) {
            viewModel.toggleshowBABars(false)
            viewModel.toggleshowTABars(false)
            Edit_Property_Option(navController ,viewModel)
        }
      /*  composable(
            route = "${VideosScreenFlow.SingleVideoPlayerEnquiry.route}/{video}",
            arguments = listOf(navArgument("video") { type = NavType.StringType })
        )
        { backStackEntry ->
            val commonVM: Common_H_ViewModel = viewModel()
            val videoJson = backStackEntry.arguments?.getString("video") ?: return@composable
            val json = Json {
                ignoreUnknownKeys = true
            }
            val video = json.decodeFromString<PostUser>(Uri.decode(videoJson))
//            val video =
//                navController.previousBackStackEntry
//                    ?.savedStateHandle
//                    ?.get<PostUser>("post_user")


            commonVM.toggleshowBABars(false)
            SingleVideoPlayerEnquiry(
                video = video,
                navController = navController,
                reels_Show = remember { mutableStateOf(true) },
                viewModel = commonVM
            )
        }*/

        composable(VideosScreenFlow.SingleVideoPlayerEnquiry.route) {

            val postId = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Int>("post_id") ?: return@composable

            val myLeads = constants.Enquiry_ViewModel.my_Leads.collectAsState()
            val selfEnquiry = constants.Enquiry_ViewModel.self_Enquiry.collectAsState()
            val messagechatlist = constants.Enquiry_ViewModel.selectedChatData.collectAsState()

            var enquiryFlow = constants.Enquiry_ViewModel.enquiryFlows.collectAsState()

            println("SIZE OF DATAS --_${enquiryFlow.value}")
            val currentVideo = remember(myLeads, selfEnquiry, postId ,messagechatlist ) {
                when {
                    enquiryFlow.value == EnquiryFlow.LEADS -> {
                        myLeads.value.firstOrNull { it.post_user.user_post_id == postId }?.post_user
                        //println("SIZE OF DATAS --leadss")
                    }

                    enquiryFlow.value == EnquiryFlow.SELF -> {
                        selfEnquiry.value.firstOrNull { it?.post_user?.user_post_id == postId }?.post_user
                            ?: messagechatlist.value?.video_model
                        //println("SIZE OF DATAS --sekf")
                    }

                    else -> {
                        //println("SIZE OF DATAS -chatt-")
                        messagechatlist.value?.video_model
                    }

                }
            }

            if (currentVideo == null) {
                // Optional: loading / fallback UI
                return@composable
            }

//            viewModel.toggleshowBABars(false)
//            viewModel.toggleshowTABars(false)
            val commonVM: Common_H_ViewModel = viewModel()

            SingleVideoPlayerEnquiry(
                video = currentVideo,
                navController = navController,
                reels_Show = remember { mutableStateOf(true) },
                viewModel = commonVM
            )
        }


        composable(VideosScreenFlow.SingleVideoPlayerEnquiry2.route) {
            val commonVM: Common_H_ViewModel = viewModel()
            val video = constants.Profile_ViewModel.selectedVideo.value ?: return@composable

//            val video =
//                navController.previousBackStackEntry
//                    ?.savedStateHandle
//                    ?.get<PostUser>("post_user")
//

            commonVM.toggleshowBABars(false)
            SingleVideoPlayerEnquiry(
                video = video,
                navController = navController,
                reels_Show = remember { mutableStateOf(true) }
                , viewModel = commonVM
            )
        }
        composable(
            route = VideosScreenFlow.Sold_Outs.route,
        ) {
            viewModel.toggleshowBABars(false)
            Sold_Outs(rememberNotchHeightDp() ,  navController)
        }
        composable(
            route = VideosScreenFlow.Post_Property_Forms.route,
        ) {
            viewModel.toggleshowBABars(false)
            viewModel.toggleshowTABars(false)
            Post_Property_Forms(navController , viewModel)
        }
        composable(
            route = VideosScreenFlow.TermsAndPrivacyScreen.route,
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)
            commonVM.toggleshowTABars(false)
            TermsAndPrivacyScreen(navController)
        }
        composable(
            route = VideosScreenFlow.FeedBackScreen.route,
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)
            commonVM.toggleshowTABars(false)
            FeedBackScreen(navController)
        }
        composable(
            route = VideosScreenFlow.AboutUsScreen.route,
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)
            commonVM.toggleshowTABars(false)
            AboutUsScreen(navController)
        }
        composable(VideosScreenFlow.Search_Main_Screen.route) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowTABars(false)
            Search_Main_Screen(navController ,commonVM)
        }
        composable(
            route = VideosScreenFlow.Search_Filter_Sort.route,
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)
            commonVM.toggleshowTABars(false)
            Search_Filter_Sort(navController)
        }
        composable(VideosScreenFlow.PP_Fourth_Form.route) {
            var apiError = remember {  mutableStateOf(false) }
            PP_Fourth_Form(apiError)
        }
        composable(VideosScreenFlow.Enquiry_Home_Screen.route) {
            viewModel.toggleshowBABars(true)
            viewModel.toggleshowTABars(false)

            Enquiry_Home_Screen(navController ,viewModel)
        }
    }
}

fun NavGraphBuilder.Profile_Graph(navController: NavHostController,
                                  viewModel: Common_H_ViewModel , onLogout: () -> Unit ) {
    navigation(
        startDestination = ProfileScreenFlow.Profile_Structure.route,
        route = BottomBarScreen.Profile.route
    ) {
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
            ReelsView( navController , commonVM  , onLogout = onLogout)
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
            Profile_Structure(navController, viewModel , onLogout = onLogout )
        }

        composable(
            ProfileScreenFlow.Profile_FF_Structure.route,
        ) {
            viewModel.toggleshowBABars(false)
            viewModel.toggleshowTABars(false)
            Profile_FF_Structure(navController, viewModel)
        }

//        composable(
//            route = ProfileScreenFlow.Other_Profile_Structure.route,
//        ) {
//            viewModel.toggleshowBABars(false)
//            viewModel.toggleshowTABars(false)
//            Other_Profile_Structure(navController, viewModel)
//        }

        composable(
            route = VideosScreenFlow.Other_Profile_Structure.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it }, // from right
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it }, // to left
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it }, // from left
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it }, // to right
                    animationSpec = tween(300)
                )
            }
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

//        composable(
//            route = "${ProfileScreenFlow.SingleVideoPlayerEnquiry.route}/{video}",
//            arguments = listOf(navArgument("video") { type = NavType.StringType })
//        )
//        { backStackEntry ->
//            val videoJson = backStackEntry.arguments?.getString("video") ?: return@composable
//            val json = Json {
//                ignoreUnknownKeys = true
//            }
//            val video = json.decodeFromString<PostUser>(Uri.decode(videoJson))
////
////            val video =
////                navController.previousBackStackEntry
////                    ?.savedStateHandle
////                    ?.get<PostUser>("post_user")
//
//
//            viewModel.toggleshowBABars(false)
//            SingleVideoPlayerEnquiry(
//                video = video!!,
//                navController = navController,
//                reels_Show = remember { mutableStateOf(true) },
//                viewModel = viewModel
//            )
//        }

        composable(ProfileScreenFlow.SingleVideoPlayerEnquiry.route) {

            val postId = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Int>("post_id") ?: return@composable

            val myLeads = constants.Enquiry_ViewModel.my_Leads.collectAsState()
            val selfEnquiry = constants.Enquiry_ViewModel.self_Enquiry.collectAsState()
            val messagechatlist = constants.Enquiry_ViewModel.selectedChatData.collectAsState()

            var enquiryFlow = constants.Enquiry_ViewModel.enquiryFlows.collectAsState()

            println("SIZE OF DATAS --_${enquiryFlow.value}")
            val currentVideo = remember(myLeads, selfEnquiry, postId ,messagechatlist ) {
                when {
                    enquiryFlow.value == EnquiryFlow.LEADS -> {
                        myLeads.value.firstOrNull { it.post_user.user_post_id == postId }?.post_user
                        //println("SIZE OF DATAS --leadss")
                    }

                    enquiryFlow.value == EnquiryFlow.SELF -> {
                        selfEnquiry.value.firstOrNull { it?.post_user?.user_post_id == postId }?.post_user
                            ?: messagechatlist.value?.video_model
                        //println("SIZE OF DATAS --sekf")
                    }

                    else -> {
                        //println("SIZE OF DATAS -chatt-")
                        messagechatlist.value?.video_model
                    }

                }
            }

            if (currentVideo == null) {
                // Optional: loading / fallback UI
                return@composable
            }

//            viewModel.toggleshowBABars(false)
//            viewModel.toggleshowTABars(false)
            val commonVM: Common_H_ViewModel = viewModel()

            SingleVideoPlayerEnquiry(
                video = currentVideo,
                navController = navController,
                reels_Show = remember { mutableStateOf(true) },
                viewModel = commonVM
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
            route = ProfileScreenFlow.TermsAndPrivacyScreen.route,
        ) {
            viewModel.toggleshowBABars(false)
            viewModel.toggleshowTABars(false)
            TermsAndPrivacyScreen(navController)
        }
        composable(
            route = ProfileScreenFlow.FeedBackScreen.route,
        ) {
            viewModel.toggleshowBABars(false)
            viewModel.toggleshowTABars(false)
            FeedBackScreen(navController)
        }
        composable(
            route = ProfileScreenFlow.AboutUsScreen.route,
        ) {
            viewModel.toggleshowBABars(false)
            viewModel.toggleshowTABars(false)
            AboutUsScreen(navController)
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

        composable(ProfileScreenFlow.PreviewScreen.route) {
            PreviewScreen(navController)
        }

    }
}


fun NavGraphBuilder.SearchGraph(navController: NavHostController,
                                viewModel: Common_H_ViewModel , onLogout: () -> Unit) {
    navigation(startDestination = SearchScreenFlow.Search_Main_Screen.route, route = BottomBarScreen.Search.route) {

        val viewModel2 = ViewModelProvider(constants.activity)[Start_Up_ViewModel :: class.java]
        composable(SearchScreenFlow.UserCredentials.route) {
            User_Credentials(navController, viewModel2)
        }
        val placesClient = Places.createClient(constants.activity)
        composable(SearchScreenFlow.UserInterests.route) {
            UserInterests(navController, placesClient)
        }
        composable(SearchScreenFlow.Onboarding.route) {
            Onboarding(navController)
        }

        composable(SearchScreenFlow.Justify.route) {
            Justify(navController)
        }
        composable(SearchScreenFlow.In_App_Notification.route) {
            val commonVM: Common_H_ViewModel = viewModel()
            In_App_Notification(navController,commonVM)
        }
        composable(SearchScreenFlow.ReelsView.route
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            constants.PostProperty_ViewModel.set_Post_Form_Flow(-1)
            ReelsView( navController , commonVM  , onLogout = onLogout)
        }
        composable(SearchScreenFlow.Common_Screen.route
            , enterTransition = { slideInHorizontally (
                tween(900)
            ){ it } }){
            Common_Screen(viewModel ,navController)
        }
        composable(
            route = SearchScreenFlow.Justify_Post.route + "/{postId}",
            arguments = listOf(navArgument("postId") { type = NavType.StringType })
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId") ?: ""
            Justify_Post(navController, postId)
        }
        composable(SearchScreenFlow.Search_Main_Screen.route) {
            viewModel.toggleshowTABars(false)
            Search_Main_Screen(navController ,viewModel)
        }

        composable(
            route = SearchScreenFlow.ReelsView_Search_Flow.route + "/{startIndex}",
            arguments = listOf(navArgument("startIndex") { type = NavType.IntType })
        ) { backStackEntry ->
            viewModel.toggleshowBABars(false)

            val index = backStackEntry.arguments?.getInt("startIndex") ?: 0
            ReelsView_Search_Flow(navController, startIndex = index , viewModel)
        }

        composable(SearchScreenFlow.ViewPropertyStructure.route) {
            View_Property_Structure(navController, viewModel)
        }

        composable(SearchScreenFlow.Profile_Structure.route) {
            Profile_Structure(navController, viewModel , onLogout)
        }

//        composable(SearchScreenFlow.Other_Profile_Structure.route) {
//            Other_Profile_Structure(navController, viewModel)
//        }

        composable(
            route = VideosScreenFlow.Other_Profile_Structure.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it }, // from right
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it }, // to left
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it }, // from left
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it }, // to right
                    animationSpec = tween(300)
                )
            }
        ) {
            viewModel.toggleshowBABars(false)
            viewModel.toggleshowTABars(false)
            Other_Profile_Structure(navController, viewModel)
        }

        composable(SearchScreenFlow.Profile_FF_Structure.route) {
            Profile_FF_Structure(navController, viewModel)
        }

        composable(
            route = SearchScreenFlow.Repost_Property.route,
        ) {
            viewModel.toggleshowBABars(false)
            viewModel.toggleshowTABars(false)
            Repost_Property(navController, viewModel)
        }
        composable(
            route = SearchScreenFlow.Search_Filter_Sort.route,
        ) {
            viewModel.toggleshowBABars(false)
            viewModel.toggleshowTABars(false)
            Search_Filter_Sort(navController)
        }

        composable(
            route = SearchScreenFlow.Profile_FF_Structure_Followings.route,
        ) {
            viewModel.toggleshowBABars(false)
            viewModel.toggleshowTABars(false)
            Profile_FF_Structure_Followings(navController, viewModel)
        }
        composable(
            route = SearchScreenFlow.Edit_Property_Option.route,
        ) {
            viewModel.toggleshowBABars(false)
            viewModel.toggleshowTABars(false)
            Edit_Property_Option(navController ,viewModel)
        }
//        composable(
//            route = "${SearchScreenFlow.SingleVideoPlayerEnquiry.route}/{video}",
//            arguments = listOf(navArgument("video") { type = NavType.StringType })
//        )
//        { backStackEntry ->
//            val commonVM: Common_H_ViewModel = viewModel()
//            val videoJson = backStackEntry.arguments?.getString("video") ?: return@composable
//            val json = Json {
//                ignoreUnknownKeys = true
//            }
//            val video = json.decodeFromString<PostUser>(Uri.decode(videoJson))
////
////            val video =
////                navController.previousBackStackEntry
////                    ?.savedStateHandle
////                    ?.get<PostUser>("post_user")
////
//
//            commonVM.toggleshowBABars(false)
//            SingleVideoPlayerEnquiry(
//                video = video!!,
//                navController = navController,
//                reels_Show = remember { mutableStateOf(true) },
//                viewModel = commonVM
//            )
//        }

        composable(SearchScreenFlow.SingleVideoPlayerEnquiry.route) {

            val postId = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Int>("post_id") ?: return@composable

            val myLeads = constants.Enquiry_ViewModel.my_Leads.collectAsState()
            val selfEnquiry = constants.Enquiry_ViewModel.self_Enquiry.collectAsState()
            val messagechatlist = constants.Enquiry_ViewModel.selectedChatData.collectAsState()

            var enquiryFlow = constants.Enquiry_ViewModel.enquiryFlows.collectAsState()

            println("SIZE OF DATAS --_${enquiryFlow.value}")
            val currentVideo = remember(myLeads, selfEnquiry, postId ,messagechatlist ) {
                when {
                    enquiryFlow.value == EnquiryFlow.LEADS -> {
                        myLeads.value.firstOrNull { it.post_user.user_post_id == postId }?.post_user
                        //println("SIZE OF DATAS --leadss")
                    }

                    enquiryFlow.value == EnquiryFlow.SELF -> {
                        selfEnquiry.value.firstOrNull { it?.post_user?.user_post_id == postId }?.post_user
                            ?: messagechatlist.value?.video_model
                        //println("SIZE OF DATAS --sekf")
                    }

                    else -> {
                        //println("SIZE OF DATAS -chatt-")
                        messagechatlist.value?.video_model
                    }

                }
            }

            if (currentVideo == null) {
                // Optional: loading / fallback UI
                return@composable
            }

//            viewModel.toggleshowBABars(false)
//            viewModel.toggleshowTABars(false)
            val commonVM: Common_H_ViewModel = viewModel()

            SingleVideoPlayerEnquiry(
                video = currentVideo,
                navController = navController,
                reels_Show = remember { mutableStateOf(true) },
                viewModel = commonVM
            )
        }



        composable(SearchScreenFlow.SingleVideoPlayerEnquiry2.route) {
            val commonVM: Common_H_ViewModel = viewModel()
            val video = constants.Profile_ViewModel.selectedVideo.value ?: return@composable

            commonVM.toggleshowBABars(false)
            SingleVideoPlayerEnquiry(
                video = video,
                navController = navController,
                reels_Show = remember { mutableStateOf(true) }
                , viewModel = commonVM
            )
        }
        composable(
            route = SearchScreenFlow.Sold_Outs.route,
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)
            Sold_Outs(rememberNotchHeightDp() ,  navController)
        }
        composable(
            route = SearchScreenFlow.Post_Property_Forms.route,
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)
            commonVM.toggleshowTABars(false)
            Post_Property_Forms(navController , commonVM)
        }
        composable(
            route = SearchScreenFlow.TermsAndPrivacyScreen.route,
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)
            commonVM.toggleshowTABars(false)
            TermsAndPrivacyScreen(navController)
        }
        composable(
            route = SearchScreenFlow.FeedBackScreen.route,
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)
            commonVM.toggleshowTABars(false)
            FeedBackScreen(navController)
        }
        composable(
            route = SearchScreenFlow.AboutUsScreen.route,
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)
            commonVM.toggleshowTABars(false)
            AboutUsScreen(navController)
        }
        composable(SearchScreenFlow.PP_Fourth_Form.route) {
            var apiError = remember {  mutableStateOf(false) }
            PP_Fourth_Form(apiError)
        }
        composable(SearchScreenFlow.Enquiry_Home_Screen.route) {
            viewModel.toggleshowBABars(true)
            viewModel.toggleshowTABars(false)

            Enquiry_Home_Screen(navController ,viewModel)
        }
    }
}

fun NavGraphBuilder.PostPropertyGraph(navController: NavHostController,
                                      viewModel: Common_H_ViewModel , onLogout: () -> Unit) {
    navigation(startDestination = PostPropertyFlow.Post_Property_Forms.route, route = BottomBarScreen.Post.route) {

        val viewModel2 = ViewModelProvider(constants.activity)[Start_Up_ViewModel :: class.java]
        composable(PostPropertyFlow.UserCredentials.route) {
            User_Credentials(navController, viewModel2)
        }
        composable(PostPropertyFlow.Onboarding.route) {
            Onboarding(navController)
        }

        val placesClient = Places.createClient(constants.activity)
        composable(PostPropertyFlow.UserInterests.route) {
            UserInterests(navController, placesClient)
        }

        composable(PostPropertyFlow.Justify.route) {
            Justify(navController)
        }

        composable(PostPropertyFlow.PreviewScreen.route) {
            PreviewScreen(navController)
        }
        composable(PostPropertyFlow.In_App_Notification.route) {
            val commonVM: Common_H_ViewModel = viewModel()
            In_App_Notification(navController,commonVM)
        }
        composable(PostPropertyFlow.ReelsView.route
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            constants.PostProperty_ViewModel.set_Post_Form_Flow(-1)
            ReelsView( navController , commonVM  , onLogout = onLogout)
        }
        composable(
            route = PostPropertyFlow.Justify_Post.route + "/{postId}",
            arguments = listOf(navArgument("postId") { type = NavType.StringType })
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId") ?: ""
            Justify_Post(navController, postId)
        }
        composable(PostPropertyFlow.Post_Property_Forms.route) {
            //constants.PostProperty_ViewModel.set_Post_Form_Flow()
            constants.PostProperty_ViewModel.change_Status_PFs(false)
            Post_Property_Forms(
            navController,
            viewModel
        ) }
        composable(PostPropertyFlow.Common_Screen.route) { Common_Screen(viewModel ,navController) }
        composable(PostPropertyFlow.Profile_Structure.route) { Profile_Structure(navController , viewModel, onLogout) }
        composable(PostPropertyFlow.PP_Fourth_Form.route) {
            var apiError = remember {  mutableStateOf(false) }
            PP_Fourth_Form(apiError)
        }
        composable(PostPropertyFlow.ViewPropertyStructure.route) {
            View_Property_Structure(navController, viewModel)
        }

        composable(
            route = PostPropertyFlow.Edit_Property_Option.route,
        ) {
            viewModel.toggleshowBABars(false)
            viewModel.toggleshowTABars(false)
            Edit_Property_Option(navController ,viewModel)
        }

        composable(
            route = PostPropertyFlow.ReelsView_Search_Flow.route + "/{startIndex}",
            arguments = listOf(navArgument("startIndex") { type = NavType.IntType })
        ) { backStackEntry ->
            viewModel.toggleshowBABars(false)

            val index = backStackEntry.arguments?.getInt("startIndex") ?: 0
            ReelsView_Search_Flow(navController, startIndex = index , viewModel)
        }

        composable(PostPropertyFlow.Profile_FF_Structure.route
            , enterTransition = { slideInHorizontally (
                tween(900)
            ){ it } }
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            Profile_FF_Structure(navController ,commonVM)
        }
        composable(
            route = PostPropertyFlow.Repost_Property.route,
        ) {
            constants.Common_H_ViewModel.toggleshowBABars(false)
            constants.Common_H_ViewModel.toggleshowTABars(false)
            Repost_Property(navController ,viewModel)
        }
        composable(
            route = PostPropertyFlow.Profile_FF_Structure_Followings.route,
        ) {
            constants.Common_H_ViewModel.toggleshowBABars(false)
            constants.Common_H_ViewModel.toggleshowTABars(false)
            Profile_FF_Structure_Followings(navController, viewModel)
        }
//        composable(
//            route = "${PostPropertyFlow.SingleVideoPlayerEnquiry.route}/{video}",
//            arguments = listOf(navArgument("video") { type = NavType.StringType })
//        )
//        { backStackEntry ->
//            val commonVM: Common_H_ViewModel = viewModel()
//            val videoJson = backStackEntry.arguments?.getString("video") ?: return@composable
//            val json = Json {
//                ignoreUnknownKeys = true
//            }
//            val video = json.decodeFromString<PostUser>(Uri.decode(videoJson))
////
////            val video =
////                navController.previousBackStackEntry
////                    ?.savedStateHandle
////                    ?.get<PostUser>("post_user")
//
//
//            commonVM.toggleshowBABars(false)
//            SingleVideoPlayerEnquiry(
//                video = video!!,
//                navController = navController,
//                reels_Show = remember { mutableStateOf(true) },
//                viewModel = commonVM
//            )
//        }

        composable(PostPropertyFlow.SingleVideoPlayerEnquiry.route) {

            val postId = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Int>("post_id") ?: return@composable

            val myLeads = constants.Enquiry_ViewModel.my_Leads.collectAsState()
            val selfEnquiry = constants.Enquiry_ViewModel.self_Enquiry.collectAsState()
            val messagechatlist = constants.Enquiry_ViewModel.selectedChatData.collectAsState()

            var enquiryFlow = constants.Enquiry_ViewModel.enquiryFlows.collectAsState()

            println("SIZE OF DATAS --_${enquiryFlow.value}")
            val currentVideo = remember(myLeads, selfEnquiry, postId ,messagechatlist ) {
                when {
                    enquiryFlow.value == EnquiryFlow.LEADS -> {
                        myLeads.value.firstOrNull { it.post_user.user_post_id == postId }?.post_user
                        //println("SIZE OF DATAS --leadss")
                    }

                    enquiryFlow.value == EnquiryFlow.SELF -> {
                        selfEnquiry.value.firstOrNull { it?.post_user?.user_post_id == postId }?.post_user
                            ?: messagechatlist.value?.video_model
                        //println("SIZE OF DATAS --sekf")
                    }

                    else -> {
                        //println("SIZE OF DATAS -chatt-")
                        messagechatlist.value?.video_model
                    }

                }
            }

            if (currentVideo == null) {
                // Optional: loading / fallback UI
                return@composable
            }

//            viewModel.toggleshowBABars(false)
//            viewModel.toggleshowTABars(false)
            val commonVM: Common_H_ViewModel = viewModel()

            SingleVideoPlayerEnquiry(
                video = currentVideo,
                navController = navController,
                reels_Show = remember { mutableStateOf(true) },
                viewModel = commonVM
            )
        }



        composable(PostPropertyFlow.SingleVideoPlayerEnquiry2.route) {
            val commonVM: Common_H_ViewModel = viewModel()
            val video = constants.Profile_ViewModel.selectedVideo.value ?: return@composable

            commonVM.toggleshowBABars(false)
            SingleVideoPlayerEnquiry(
                video = video,
                navController = navController,
                reels_Show = remember { mutableStateOf(true) }
                , viewModel = commonVM
            )
        }
        composable(
            route = PostPropertyFlow.Sold_Outs.route,
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)
            Sold_Outs(rememberNotchHeightDp() ,  navController)
        }
        composable(
            route = PostPropertyFlow.TermsAndPrivacyScreen.route,
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)
            commonVM.toggleshowTABars(false)
            TermsAndPrivacyScreen(navController)
        }
        composable(
            route = PostPropertyFlow.FeedBackScreen.route,
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)
            commonVM.toggleshowTABars(false)
            FeedBackScreen(navController)
        }
        composable(
            route = PostPropertyFlow.AboutUsScreen.route,
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)
            commonVM.toggleshowTABars(false)
            AboutUsScreen(navController)
        }
        composable(PostPropertyFlow.Search_Main_Screen.route) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowTABars(false)
            Search_Main_Screen(navController ,commonVM)
        }
        composable(
            route = PostPropertyFlow.Search_Filter_Sort.route,
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)
            commonVM.toggleshowTABars(false)
            Search_Filter_Sort(navController)
        }
        composable(PostPropertyFlow.Enquiry_Home_Screen.route) {
            viewModel.toggleshowBABars(true)
            viewModel.toggleshowTABars(false)

            Enquiry_Home_Screen(navController ,viewModel)
        }
//        composable(
//            route = PostPropertyFlow.Other_Profile_Structure.route,
//        ) {
//            constants.Common_H_ViewModel.toggleshowBABars(false)
//            constants.Common_H_ViewModel.toggleshowTABars(false)
//            Other_Profile_Structure(navController, viewModel)
//        }

        composable(
            route = VideosScreenFlow.Other_Profile_Structure.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it }, // from right
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it }, // to left
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it }, // from left
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it }, // to right
                    animationSpec = tween(300)
                )
            }
        ) {
            viewModel.toggleshowBABars(false)
            viewModel.toggleshowTABars(false)
            Other_Profile_Structure(navController, viewModel)
        }


    }
}

fun NavGraphBuilder.EnquiryGraph(navController: NavHostController,
                                 viewModel: Common_H_ViewModel , onLogout: () -> Unit) {
    navigation(startDestination = EnquiriesFlow.Enquiry_Home_Screen.route, route = BottomBarScreen.Enquiry.route) {

        val viewModel2 = ViewModelProvider(constants.activity)[Start_Up_ViewModel::class.java]
        composable(EnquiriesFlow.UserCredentials.route) {
            User_Credentials(navController, viewModel2)
        }
        val placesClient = Places.createClient(constants.activity)
        composable(EnquiriesFlow.UserInterests.route) {
            UserInterests(navController, placesClient)
        }
        composable(EnquiriesFlow.Onboarding.route) {
            Onboarding(navController)
        }

        composable(EnquiriesFlow.Justify.route) {
            Justify(navController)
        }
        composable(EnquiriesFlow.In_App_Notification.route) {
            val commonVM: Common_H_ViewModel = viewModel()
            In_App_Notification(navController, commonVM)
        }
        composable(
            EnquiriesFlow.ReelsView.route
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            constants.PostProperty_ViewModel.set_Post_Form_Flow(-1)
            ReelsView(navController, commonVM, onLogout = onLogout)
        }
        composable(
            route = EnquiriesFlow.Justify_Post.route + "/{postId}",
            arguments = listOf(navArgument("postId") { type = NavType.StringType })
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId") ?: ""
            Justify_Post(navController, postId)
        }

        composable(EnquiriesFlow.Common_Screen.route, enterTransition = {
            slideInHorizontally(
                tween(900)
            ) { it }
        }) {
            Common_Screen(viewModel, navController)
        }

        composable(
            route = EnquiriesFlow.Edit_Property_Option.route,
        ) {
            viewModel.toggleshowBABars(false)
            viewModel.toggleshowTABars(false)
            Edit_Property_Option(navController, viewModel)
        }

        composable(EnquiriesFlow.Enquiry_Home_Screen.route) {
            viewModel.toggleshowBABars(true)
            viewModel.toggleshowTABars(false)

            Enquiry_Home_Screen(navController, viewModel)
        }

        composable(EnquiriesFlow.ViewPropertyStructure.route) {
            View_Property_Structure(
                navController,
                viewModel
            )
        }
//        composable(
//            route = EnquiriesFlow.SingleVideoPlayerEnquiry.route
//        ) { backStackEntry ->
//
//            val videoJson = backStackEntry.arguments?.getString("video") ?: return@composable
//            val json = Json {
//                ignoreUnknownKeys = true
//            }
//            val video = json.decodeFromString<PostUser>(Uri.decode(videoJson))
//
//            viewModel.toggleshowBABars(false)
//
//            SingleVideoPlayerEnquiry(
//                video = video,
//                navController = navController,
//                reels_Show = remember { mutableStateOf(true) },
//                viewModel = viewModel
//            )
//        }
//
//    }

        /*composable(
            route = EnquiriesFlow.SingleVideoPlayerEnquiry.route
        )
        { backStackEntry ->
            val video = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<PostUser>("post_user") ?: return@composable

            viewModel.toggleshowBABars(false)
            println("DATA ON NAVIGATION --- ${video}")

            SingleVideoPlayerEnquiry(
                video = video,
                navController = navController,
                reels_Show = remember { mutableStateOf(true) },
                viewModel = viewModel
            )
        }*/

        composable(EnquiriesFlow.SingleVideoPlayerEnquiry.route) {

            val postId = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Int>("post_id") ?: return@composable

            val myLeads = constants.Enquiry_ViewModel.my_Leads.collectAsState()
            val selfEnquiry = constants.Enquiry_ViewModel.self_Enquiry.collectAsState()
            val messagechatlist = constants.Enquiry_ViewModel.selectedChatData.collectAsState()

            var enquiryFlow = constants.Enquiry_ViewModel.enquiryFlows.collectAsState()

            println("SIZE OF DATAS --_${enquiryFlow.value}")
            val currentVideo = remember(myLeads, selfEnquiry, postId ,messagechatlist ) {
                when {
                    enquiryFlow.value == EnquiryFlow.LEADS -> {
                        myLeads.value.firstOrNull { it.post_user.user_post_id == postId }?.post_user
                        //println("SIZE OF DATAS --leadss")
                    }

                    enquiryFlow.value == EnquiryFlow.SELF -> {
                        selfEnquiry.value.firstOrNull { it?.post_user?.user_post_id == postId }?.post_user
                            ?: messagechatlist.value?.video_model
                        //println("SIZE OF DATAS --sekf")
                    }

                    else -> {
                        //println("SIZE OF DATAS -chatt-")
                        messagechatlist.value?.video_model
                    }

                }
            }

            if (currentVideo == null) {
                // Optional: loading / fallback UI
                return@composable
            }
            
            viewModel.toggleshowBABars(false)
            viewModel.toggleshowTABars(false)

            SingleVideoPlayerEnquiry(
                video = currentVideo,
                navController = navController,
                reels_Show = remember { mutableStateOf(true) },
                viewModel = viewModel
            )
        }



        composable(
            route = EnquiriesFlow.ReelsView_Search_Flow.route + "/{startIndex}",
            arguments = listOf(navArgument("startIndex") { type = NavType.IntType })
        ) { backStackEntry ->
            viewModel.toggleshowBABars(false)

            val index = backStackEntry.arguments?.getInt("startIndex") ?: 0
            ReelsView_Search_Flow(navController, startIndex = index, viewModel)
        }


        composable(
            EnquiriesFlow.Profile_Structure.route
        ) {
            Profile_Structure(navController, viewModel, onLogout)
        }

        composable(
            EnquiriesFlow.Profile_FF_Structure.route,
        ) {
            Profile_FF_Structure(navController, viewModel)
        }

//    composable(
//        route = EnquiriesFlow.Other_Profile_Structure.route,
//    ) {
//        viewModel.toggleshowBABars(false)
//        Other_Profile_Structure(navController, viewModel)
//    }

        composable(
            route = VideosScreenFlow.Other_Profile_Structure.route,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it }, // from right
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it }, // to left
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it }, // from left
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it }, // to right
                    animationSpec = tween(300)
                )
            }
        ) {
            viewModel.toggleshowBABars(false)
            viewModel.toggleshowTABars(false)
            Other_Profile_Structure(navController, viewModel)
        }

        composable(
            route = EnquiriesFlow.Profile_FF_Structure_Followings.route,
        ) {
            viewModel.toggleshowBABars(false)
            viewModel.toggleshowTABars(false)
            Profile_FF_Structure_Followings(navController, viewModel)
        }
        composable(
            route = EnquiriesFlow.Repost_Property.route,
        ) {
            constants.Common_H_ViewModel.toggleshowBABars(false)
            constants.Common_H_ViewModel.toggleshowTABars(false)
            Repost_Property(navController, viewModel)
        }
        composable(EnquiriesFlow.SingleVideoPlayerEnquiry2.route) {
            val commonVM: Common_H_ViewModel = viewModel()
            val video = constants.Profile_ViewModel.selectedVideo.value ?: return@composable

            commonVM.toggleshowBABars(false)
            SingleVideoPlayerEnquiry(
                video = video,
                navController = navController,
                reels_Show = remember { mutableStateOf(true) }, viewModel = commonVM
            )
        }

        composable(
            route = EnquiriesFlow.Sold_Outs.route,
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)
            Sold_Outs(rememberNotchHeightDp(), navController)
        }
        composable(
            route = EnquiriesFlow.Post_Property_Forms.route,
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)
            commonVM.toggleshowTABars(false)
            Post_Property_Forms(navController, commonVM)
        }
        composable(
            route = EnquiriesFlow.TermsAndPrivacyScreen.route,
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)
            commonVM.toggleshowTABars(false)
            TermsAndPrivacyScreen(navController)
        }
        composable(
            route = EnquiriesFlow.FeedBackScreen.route,
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)
            commonVM.toggleshowTABars(false)
            FeedBackScreen(navController)
        }
        composable(
            route = EnquiriesFlow.AboutUsScreen.route,
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)
            commonVM.toggleshowTABars(false)
            AboutUsScreen(navController)
        }
        composable(EnquiriesFlow.Search_Main_Screen.route) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowTABars(false)
            Search_Main_Screen(navController, commonVM)
        }
        composable(
            route = EnquiriesFlow.Search_Filter_Sort.route,
        ) {
            val commonVM: Common_H_ViewModel = viewModel()
            commonVM.toggleshowBABars(false)
            commonVM.toggleshowTABars(false)
            Search_Filter_Sort(navController)
        }
        composable(EnquiriesFlow.PP_Fourth_Form.route) {
            var apiError = remember { mutableStateOf(false) }
            PP_Fourth_Form(apiError)
        }

        // User list
        composable(
            route = EnquiriesFlow.Msg_UserList.route + "/{propertyId}/{sellerId}",
            arguments = listOf(
                navArgument("propertyId") { type = NavType.StringType },
                navArgument("sellerId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            viewModel.toggleshowBABars(false)

            val propertyId = backStackEntry.arguments?.getString("propertyId") ?: ""
            val sellerId = backStackEntry.arguments?.getString("sellerId") ?: ""



            Msg_UserList(
                propertyId = propertyId,
                sellerId = sellerId,
                loggedInUserId = AppPreferences.getUserId().toString(),
                onBuyerClick = { user ->
                    val encodedUserName =
                        URLEncoder.encode(user.userName, StandardCharsets.UTF_8.toString())
                    val encodedLocation = URLEncoder.encode(
                        "${user.city},${user.state}",
                        StandardCharsets.UTF_8.toString()
                    )
                    val encodedProfile = URLEncoder.encode(
                        user.profileImage ?: "",
                        StandardCharsets.UTF_8.toString()
                    )

                    navController.navigate(
                        EnquiriesFlow.Msg_ChatScreen.route +
                                "/${user.userId}/${propertyId}/${sellerId}/${encodedUserName}/${"${user.city},${user.state}"}/${encodedProfile}"
                    )

                },
                onLogout = {
                    viewModel.toggleshowBABars(true)
                    navController.popBackStack(
                        EnquiriesFlow.Enquiry_Home_Screen.route,
                        inclusive = false
                    )
                },
                onBack = {
                    viewModel.toggleshowBABars(true)
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = EnquiriesFlow.Msg_ChatScreen.route + "/{buyerId}/{propertyId}/{sellerId}/{otherUserName}/{otherUserLocation}/{otherUserProfile}",
            arguments = listOf(
                navArgument("buyerId") { type = NavType.StringType },
                navArgument("propertyId") { type = NavType.StringType },
                navArgument("sellerId") { type = NavType.StringType },
                navArgument("otherUserName") { type = NavType.StringType },
                navArgument("otherUserLocation") { type = NavType.StringType },
                navArgument("otherUserProfile") { type = NavType.StringType },
            )
        )
        { backStackEntry ->
            viewModel.toggleshowBABars(false)

            val buyerId = backStackEntry.arguments?.getString("buyerId") ?: ""
            val propertyId = backStackEntry.arguments?.getString("propertyId") ?: ""
            val sellerId = backStackEntry.arguments?.getString("sellerId") ?: ""
            val otherUserName = backStackEntry.arguments?.getString("otherUserName") ?: ""
            val otherUserLocation = backStackEntry.arguments?.getString("otherUserLocation") ?: ""
            val otherUserProfile = backStackEntry.arguments?.getString("otherUserProfile") ?: ""

            Msg_ChatScreen(

                currentUserId = AppPreferences.getUserId().toString(),
                otherUserId = buyerId,
                otherUserLocation = otherUserLocation, // optional, if you want to pass name too
                otherUserName = otherUserName,
                otherUserProfile = otherUserProfile,
                propertyId = propertyId,
                sellerId = sellerId,
                onBack = {
                    viewModel.toggleshowBABars(false)
                    navController.popBackStack()
                },
                navController = navController
            )
        }
    }

}

