package com.toletspot.houseforrent.Home_Screen.ProfileModule

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Patterns
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
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
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.google.android.gms.maps.model.LatLng
import com.toletspot.houseforrent.API.API_Result_Handling
import com.toletspot.houseforrent.API.StartUp_API.Put_User_Interests_API_Call
import com.toletspot.houseforrent.API.StartUp_API.get_User_Interest_Particular_API_Call
import com.toletspot.houseforrent.API.StartUp_API.register_API_Call
import com.toletspot.houseforrent.AppPreferences
import com.toletspot.houseforrent.BuildConfig
import com.toletspot.houseforrent.Custom_Assets.API_Fail_UI
import com.toletspot.houseforrent.Custom_Assets.Backer
import com.toletspot.houseforrent.Custom_Assets.ClickHelper
import com.toletspot.houseforrent.Custom_Assets.Common_Popup
import com.toletspot.houseforrent.Custom_Assets.LottiAnimation
import com.toletspot.houseforrent.Custom_Assets.OTP_TF
import com.toletspot.houseforrent.Custom_Assets.OTP_TF_6
import com.toletspot.houseforrent.Custom_Assets.PhoneNumberInput_Settings
import com.toletspot.houseforrent.Custom_Assets.Static_Bottom
import com.toletspot.houseforrent.Custom_Assets.StepCircularProgress
import com.toletspot.houseforrent.Custom_Assets.customGridItems
import com.toletspot.houseforrent.Custom_Assets.getDeviceId
import com.toletspot.houseforrent.Custom_Assets.getDeviceType
import com.toletspot.houseforrent.Custom_Assets.rememberCountdownTimer
import com.toletspot.houseforrent.Custom_Assets.rememberNotchHeightDp
import com.toletspot.houseforrent.Custom_Assets.toast
import com.toletspot.houseforrent.Custom_Assets.verifyOtp
import com.toletspot.houseforrent.GlobalSnackbar
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.PostFlow
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.ViewDetailsFlow
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.toSelectedOptionsForm4_D
import com.toletspot.houseforrent.Navigation.ProfileScreenFlow
import com.toletspot.houseforrent.NetworkStatus
import com.toletspot.houseforrent.R
import com.toletspot.houseforrent.Start_Up.CommonText
import com.toletspot.houseforrent.Start_Up.CountryPickerBottomSheet
import com.toletspot.houseforrent.Start_Up.Start_Up_ViewModel
import com.toletspot.houseforrent.UI_DataClass.PP3_API_DC
import com.toletspot.houseforrent.UI_DataClass.Profile_Handle_Back
import com.toletspot.houseforrent.constants
import com.toletspot.houseforrent.constants.Companion.REQUEST_CODE
import com.toletspot.houseforrent.constants.Companion.notificationEnabled
import com.toletspot.houseforrent.constants.Companion.spacer
import com.toletspot.houseforrent.deviceToken
import com.toletspot.houseforrent.forTab
import com.toletspot.houseforrent.grayscaleMatrix
import com.toletspot.houseforrent.isConnected
import com.toletspot.houseforrent.noRippleClickable
import com.toletspot.houseforrent.rememberNetworkStatus
import com.toletspot.houseforrent.ui.theme.newBlack
import com.toletspot.houseforrent.ui.theme.newBlue
import com.toletspot.houseforrent.ui.theme.newGray
import com.toletspot.houseforrent.ui.theme.newLightBlue
import com.toletspot.houseforrent.ui.theme.newLightGray
import com.toletspot.houseforrent.ui.theme.newLightPurpleGradient
import com.toletspot.houseforrent.ui.theme.newPurpleGradient
import com.toletspot.houseforrent.ui.theme.newPurpleGradientBorder
import com.toletspot.houseforrent.ui.theme.newRedGradienBg
import com.toletspot.houseforrent.ui.theme.newWhite
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged



@Composable
fun Settings_Main(navController: NavHostController ,onLogout: () -> Unit ) {

    val notchPadding = rememberNotchHeightDp()
    val settings_List = constants.Profile_ViewModel.settings_List.collectAsState()

    val selected_Settings = constants.Profile_ViewModel.onSettings_Click.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(newBlack.copy(.5f))
            .clickable (enabled = false) {  }
        , contentAlignment = Alignment.CenterEnd
    )
    {
        AnimatedContent(
            targetState = selected_Settings
            , transitionSpec = {
                slideInHorizontally(
                    animationSpec = tween(
                        durationMillis = 600,
                        easing = FastOutSlowInEasing
                    )
                ) { fullWidth -> fullWidth } togetherWith
                        ExitTransition.None
             }
        )
        {
                targetState ->
            when(targetState.value){
                -1 -> {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(.7f)
                            .background(newWhite)
                            .padding(top = if (forTab()) 16.dp else notchPadding.value, start = 16.dp)
                        , verticalArrangement = Arrangement.spacedBy(32.dp)
                    )
                    {

                        // top bar
                        Row (
                            modifier = Modifier
                                //.fillMaxWidth(.5f)
                                .wrapContentHeight()
                            , horizontalArrangement = Arrangement.spacedBy(8.dp)
                        )
                        {
                            Backer(
                                modifier = Modifier
                                , onBackClick = {
                                    constants.Profile_ViewModel.set_open_settings()
                                    constants.Common_H_ViewModel.toggleshowBABars(true)
                                }
                            )

                            Text("Settings" ,
                                color = newBlack,
                                fontSize = constants.textUnit(24),
                                fontFamily = constants.fontFamily(0)
                            )
                        }
                        /// top bar end



                        Column {
                            LazyColumn(
                                modifier = Modifier.weight(9.5f),
                                verticalArrangement = Arrangement.spacedBy(32.dp)
                            )
                            {
                                items(settings_List.value) { item ->

                                    Row(
                                        modifier = Modifier
                                            .noRippleClickable {
                                                ClickHelper.getInstance().clickOnce {
                                                    constants.Profile_ViewModel.onSet_Settings_Click(
                                                        item.id
                                                    )
                                                    println("ITEMIDDDD === ${item.id}")
                                                }
                                            }
                                        //.fillMaxWidth()
                                        ,
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        AsyncImage(
                                            model = item.icon, "", modifier = Modifier
                                                .size(24.dp)
                                        )

                                        Text(
                                            item.title,
                                            fontSize = constants.textUnit(16),
                                            fontFamily = constants.fontFamily(2)
                                        )

                                    }
                                }


                            }

                            Box(
                                modifier = Modifier
                                    .padding(bottom = 12.dp)
                                    .fillMaxWidth()
                                    .weight(.5f)
                                , contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Version ${BuildConfig.VERSION_NAME}",
                                    color = Color.Black,
                                    fontSize = constants.textUnit(12),
                                    //  lineHeight = 36.sp,
                                    modifier = Modifier.align(Alignment.BottomCenter)
                                    //.padding(vertical = 8.dp)
                                    // .align(Alignment.Start) // Align title to the start
                                )
                            }
                        }

                    }
                }
                0 -> {
                    Account_Settings(notchPadding , navController ,onLogout = onLogout)
                }
                1 -> {
                    //Draftsdrth
                   constants.API_Vm.isLoading_Profile_Drafts = true
                    constants.API_Vm.totalPages_Profile_Drafts = 1
                    Drafts(notchPadding ,navController)
                }
                2 -> {
                    //SoldOuts
                    // Sold_Outs(notchPadding , navController)
                    constants.API_Vm.isLoading_Profile_SoldOuts = true
                    constants.API_Vm.totalPages_Profile_SoldOuts = 1
                    navController.navigate(ProfileScreenFlow.Sold_Outs.route)
                }
                3 -> {
                    //My Interests
                    My_Interest(notchPadding)
                }
                4 -> {
                    constants.API_Vm.totalPages_Profile_SavedP = 1
                    //Saved Properties
                    Saved_Properties(notchPadding ,navController)
                }
                5 -> {
                    //Share the App
                    constants.Profile_ViewModel.set_open_settings()
                    constants.Profile_ViewModel.onSet_Settings_Click(-1)
                    constants.DefaultShare("${constants.APP_URL}${BuildConfig.APPLICATION_ID}" ,3)
                }
                6 -> {
                    //Rate us
                    constants.API_Vm.FeedbackScreen = false
                    constants.Profile_ViewModel.selectAllStars()
                    Rate_Us(notchPadding,navController)
                }
                7 -> {
                    constants.Common_H_ViewModel.webView_Type = 2
                    //FAQ
                    navController.navigate(ProfileScreenFlow.TermsAndPrivacyScreen.route)
                }
                8 -> {
                    constants.API_Vm.FeedbackScreen = true
                    navController.navigate(ProfileScreenFlow.FeedBackScreen.route)
                    //Feedback
                }
                9 -> {
                    navController.navigate(ProfileScreenFlow.AboutUsScreen.route)
                    //About us
                }
                10 -> {
                    constants.Common_H_ViewModel.webView_Type = 0
                    navController.navigate(ProfileScreenFlow.TermsAndPrivacyScreen.route)

                    //Terms of Use
                }
                11 -> {
                    constants.Common_H_ViewModel.webView_Type = 1
                    navController.navigate(ProfileScreenFlow.TermsAndPrivacyScreen.route)
                    //Privacy Policy
                }
                12 -> {
                    //Disclaimer
                    constants.Common_H_ViewModel.webView_Type = 3
                    navController.navigate(ProfileScreenFlow.TermsAndPrivacyScreen.route)
                }
            }
        }
    }
}


@Composable
fun Account_Settings(notchPadding: State<Dp>, navController: NavHostController ,onLogout: () -> Unit ) {


    var network = rememberNetworkStatus()

    var settings_List = constants.Profile_ViewModel.account_settings_List.collectAsState()

    val selected_AS_Settings = constants.Profile_ViewModel.selected_AS_Settings.collectAsState()

    val toggle_unblock_PP = constants.Profile_ViewModel.toggle_Unblock_PP.collectAsState()

    val logout_PP_State = constants.Profile_ViewModel.logout_PP.collectAsState()

    // for unblock
    val unblock_Loading = remember { mutableStateOf(false) }
    val unBlock_PUP = constants.Profile_ViewModel.UnBlock_User.collectAsState()
    var content_PP_Unblock = remember { mutableStateOf(Triple(0, "", "")) }

    var deactivated = remember { mutableStateOf(false) }



    val triggerEdited_Profile_details = remember { mutableStateOf(0) }
    val edit_profile_Listener = constants.Profile_ViewModel.edit_profile_onTap.collectAsState()



    DisposableEffect(Unit) {
        onDispose {
            println("asdfghjkl098765432q1")
            constants.Profile_ViewModel.dismiss_Logout_PP()
        }
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(newWhite)
            .padding(top = if (forTab()) 16.dp else notchPadding.value)
        , verticalArrangement = Arrangement.spacedBy(16.dp)
    )
    {
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
            , contentAlignment = Alignment.Center
        )
        {
            Backer(
                modifier = Modifier.align(Alignment.CenterStart)
                    , onBackClick = {
                        if (selected_AS_Settings.value == "Account Settings") {
                            constants.Profile_ViewModel.onSet_Settings_Click(-1)

                        }
                        else {
                            constants.Profile_ViewModel.setSelected_AS_Settings("Account Settings")
                            constants.Profile_ViewModel.onSet_Settings_Click(0)
                            if (edit_profile_Listener.value){
                                constants.Profile_ViewModel.dismiss_Edit_Profile()
                            }
                        }
                    constants.Profile_ViewModel.toggle_ProfileReport_Options(
                        -1
                    )
                    constants.Profile_ViewModel.user_Manual_report_Index_delete.value = 0
                }
            )

            Text(
                selected_AS_Settings.value ,
                fontSize = constants.textUnit(20),
                fontFamily = constants.fontFamily(0)
                , modifier = Modifier.align(Alignment.Center)
            )
        }

        AnimatedContent(
            targetState = selected_AS_Settings
            , transitionSpec = {
                slideInHorizontally (tween(900)){ it } togetherWith slideOutHorizontally(tween(900)) { it }
            }
        )
        {
            targetState ->

            when(targetState.value){
                "Edit Profile" -> {
                    if (edit_profile_Listener.value) {
                        constants.Common_H_ViewModel.toggleshowBABars(false)
                    }
                    else {
                        triggerEdited_Profile_details.value = triggerEdited_Profile_details.value + 9876
                    }
                    Edit_Profile(notchPadding ,navController)
//                    constants.Profile_ViewModel.enable_Edit_Profile()
                }
                "Contact" -> { AS_Contact() }
                "Share Profile" -> {
                    constants.DefaultShare("https://toletspot.com/profile/${AppPreferences.getUserId()}" , 0)
                }
                "Notification" -> { AS_Notification() }
                "My Blocklist" -> {

                    //constants.API_Vm.isLoading_BUL = true

                    constants.API_Vm.currentPage_BUL = 1
                   constants.API_Vm.totalPages_BUL = 1

                    AS_My_BlockList(content_PP_Unblock , navController)
                }
                "Delete Account" -> { AS_Delete_Account(onLogout ,deactivated) }
                "Logout" -> {
                    constants.Profile_ViewModel.enable_Logout_PP()
                }
            }
        }

        LazyColumn (
            modifier = Modifier
                .padding(horizontal = 16.dp)
            , verticalArrangement = Arrangement.spacedBy(32.dp)
        )
        {
            items(settings_List.value){
                item ->

                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, newGray, RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                            .noRippleClickable{
                                ClickHelper.getInstance().clickOnce {
                                    constants.Profile_ViewModel.setSelected_AS_Settings(item)
                                }
                            }
                        , horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        Text(item,
                            fontSize = constants.textUnit(16),
                            fontFamily = constants.fontFamily(2)
                            , color = if (item != "Logout") newBlack else Color(0xffE54C3C)
                        )

                        if (item != "Logout") {
                            AsyncImage(
                                model = R.drawable.right_arrow, "", modifier = Modifier
                                    .size(18.dp)
                            )
                        }
                    }
            }
        }
    }




    // unbloack popup
    Common_Popup(
        visible = unBlock_PUP.value,
        modifier = Modifier
            .background(Color(0xffF7F0DC)), content = {


            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {

                Spacer(modifier = Modifier.padding(2.dp))

                Image(painter = painterResource(R.drawable.profilepopicon) , "",
                    modifier = Modifier.size(64.dp))

                spacer(2)

//                Text(
//                    text = "Unblock ${content_PP_Unblock.value.second} ?",
//                    color = newBlack,
//                    fontSize = constants.textUnit(16),
//                    fontFamily = constants.fontFamily(0)
//                )
                //constants.spacer(2)

                Text(
                    text = "Are you sure you want to Unblock this profile?",
                    color = newBlack,
                    fontSize = constants.textUnit(18),
                    fontFamily = constants.fontFamily(1),
                    textAlign = TextAlign.Center
//                   , lineHeight = 24.sp
                    , modifier = Modifier.padding(horizontal = if (forTab()) 46.dp else 36.dp)
                )

//                Spacer(modifier = Modifier.padding(2.dp))
                spacer(4)

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
                            .noRippleClickable{
                                ClickHelper.getInstance().clickOnce {
                                    constants.Profile_ViewModel.dismiss_Unblock_pp()
                                }
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

                    Row (
                        modifier = Modifier
                            .weight(4f)
                            .height(if (forTab()) 56.dp else 46.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Brush.verticalGradient(newPurpleGradient))
                            .noRippleClickable{
                                    ClickHelper.getInstance().clickOnce {
                                        if (ClickGuard.canClick()) {

                                            if (network.value == NetworkStatus.Online) {
                                                println("SETTINGS UNBLOCK USEER --${AppPreferences.getUserId()} -OTHERID- ${content_PP_Unblock.value.first}")
                                                constants.API_Vm.put_Block_User(
                                                    user_id = AppPreferences.getUserId(),
                                                    blocker_id = content_PP_Unblock.value.first,
                                                    //profile_Content.value?.user_id ?: 0,
                                                    status = 2
                                                    //if (profile_Content.value?.is_blocked == 0) "1" else "0"
                                                )
                                                { apiResultHandling ->
                                                    when (apiResultHandling) {
                                                        is API_Result_Handling.Deactivated -> {
                                                            // resultCallback(5)
                                                        }

                                                        is API_Result_Handling.Error -> {
                                                            toast("Unable to Unblock ,try again later!")
                                                            unblock_Loading.value = false
                                                            //errror
                                                            //constants.Profile_ViewModel.change_Update_profile(false)
                                                        }

                                                        is API_Result_Handling.NoData -> {
                                                            // no data
                                                        }

                                                        is API_Result_Handling.Loading -> {
                                                            unblock_Loading.value = true
                                                            //loading
                                                            // constants.Profile_ViewModel.change_Update_profile(true)
                                                        }

                                                        is API_Result_Handling.Success -> {


                                                            constants.Profile_ViewModel.remove_UnBlocked_User(
                                                                content_PP_Unblock.value.first
                                                            )
                                                            constants.Profile_ViewModel.updateBlockedStatus_Selected_Profile(
                                                                0
                                                            )
                                                            constants.Profile_ViewModel.dismiss_Unblock_pp()

                                                            unblock_Loading.value = false
                                                        }
                                                    }
                                                }
                                            }
                                            else {
                                                toast("Check your Internet Connection")
                                            }
                                        }
                                    }
                            }
                        , verticalAlignment = Alignment.CenterVertically
                        , horizontalArrangement = Arrangement.Absolute.Center
                    ) {
                        if (unblock_Loading.value){
                            CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Text(
                            text = "Unblock",
                            color = Color.White,
                            fontSize = constants.textUnit(16),
                            fontFamily = constants.fontFamily(0)
                        )
                    }
                }
                spacer(2)
            }
        }, image =""
            //content_PP_Unblock.value.third
    )

    // logout popup
    Common_Popup(
        visible = logout_PP_State.value
        , modifier = Modifier
            .background(Color(0xffFCEDEC))
        , content = {

            //val user_item = constants.Profile_ViewModel.selected_User_Profile.collectAsState()

            Column (
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(horizontal = 24.dp)
                , verticalArrangement = Arrangement.spacedBy(12.dp)
                , horizontalAlignment = Alignment.CenterHorizontally
            )
            {

                spacer(2)

                Image(painter = painterResource(R.drawable.logoutrento), "",
                    modifier = Modifier.size(64.dp))

                spacer(2)
               // constants.spacer(2)

//                Text(
//                    text = "Are you sure you want to Logout ?",
//                    color = newBlack,
//                    fontSize = constants.textUnit(16),
//                    fontFamily = constants.fontFamily(0)
//                )

               // constants.spacer(2)

                Text(
                    text = "Are you sure you want to logout Account?",
                    color = newBlack,
                    fontSize = constants.textUnit(18),
                    fontFamily = constants.fontFamily(1)
                    , textAlign = TextAlign.Center
//                   , lineHeight = 24.sp
                    , modifier = Modifier.padding(horizontal = if (forTab()) 46.dp else 36.dp)
                )

                Spacer(modifier = Modifier.padding(2.dp))
                spacer(2)

                Row (
                    modifier = Modifier
                    , horizontalArrangement = Arrangement.Center
                    , verticalAlignment = Alignment.CenterVertically
                )
                {
                    Box(
                        modifier = Modifier
                            .weight(4f)
                            .height(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xffE8E8E8))
                            .noRippleClickable{

                                ClickHelper.getInstance().clickOnce {

                                    if (selected_AS_Settings.value == "Account Settings") {
                                        constants.Profile_ViewModel.onSet_Settings_Click(-1)
                                    } else {
                                        constants.Profile_ViewModel.dismiss_Logout_PP()
                                        constants.Profile_ViewModel.setSelected_AS_Settings("Account Settings")
                                        constants.Profile_ViewModel.onSet_Settings_Click(0)
                                    }
                                }
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
                            .height(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Brush.verticalGradient(newPurpleGradient))
                            .noRippleClickable{
                                ClickHelper.getInstance().clickOnce {
                                    if (ClickGuard.canClick()) {

                                        if (network.value == NetworkStatus.Online) {
                                            constants.API_Vm.logout_Api(
                                                AppPreferences.getUserId(),
                                                device_id = getDeviceId(constants.activity),
                                                device_type = "Android",
                                                device_token = deviceToken
                                            ) { aPI_Result_Handling ->
                                                when (aPI_Result_Handling) {
                                                    is API_Result_Handling.NoData -> {}
                                                    is API_Result_Handling.Error -> {
                                                        toast("Something went wrong")
                                                    }

                                                    is API_Result_Handling.Deactivated -> {
                                                        //resultCallback(5)
                                                    }

                                                    is API_Result_Handling.Loading -> {

                                                    }

                                                    is API_Result_Handling.Success -> {
                                                        AppPreferences.clearAll()

                                                        constants.Profile_ViewModel.set_open_settings()
                                                        constants.Profile_ViewModel.onSet_Settings_Click(
                                                            0
                                                        )

                                                        constants.Common_H_ViewModel.changeStatus(
                                                            false
                                                        )

                                                        constants.Common_H_ViewModel.toggleshowBABars(
                                                            true
                                                        )
                                                        constants.Start_Up_ViewModel.updateLoginState(
                                                            0
                                                        )
                                                        constants.Start_Up_ViewModel.phoneNumber =
                                                            ""
                                                        constants.Start_Up_ViewModel.countryCode =
                                                            "+91"
                                                        constants.Start_Up_ViewModel.userName = ""
                                                        constants.Start_Up_ViewModel.otp = ""

                                                        constants.Profile_ViewModel.clear_All_BF_Handler()
                                                        constants.Profile_ViewModel.clearAllFFData()


                                                        //constants.Profile_ViewModel.dismiss_Logout_PP()
                                                        constants.Profile_ViewModel.onSet_Settings_Click(
                                                            -1
                                                        )
                                                        constants.Profile_ViewModel.setSelected_AS_Settings(
                                                            ""
                                                        )

                                                        constants.Profile_ViewModel.clearAllData_PRVM()
                                                        constants.PostProperty_ViewModel.clearAllData_PPVM()
                                                        constants.Reels_ViewModel.clearAllData_RVM()
                                                        constants.Start_Up_ViewModel.clearAllData_STVM()
                                                        constants.Common_H_ViewModel.clearAllData_CVM()
                                                        constants.Search_ViewModel.clearAllData_SVM()
                                                        constants.Enquiry_ViewModel.clearAllData_EVM()


                                                        onLogout()

                                                        constants.Profile_ViewModel.dismiss_Logout_PP()

                                                        // Example logout from Common_Screen
//                                    navController.navigate(UserCredentialsScreenFlow.UserCredentials.route) {
//                                        popUpTo(navController.graph.startDestinationId) {
//                                            inclusive = true
//                                        }
//                                    }
                                                    }
                                                }
                                            }
                                        }
                                        else {
                                            toast("Check your Internet Connection")
                                        }


                                    }
                                }

                            }
                        , contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "Logout",
                            color = Color.White,
                            fontSize = constants.textUnit(16),
                            fontFamily = constants.fontFamily(0)
                        )
                    }

                    Spacer(modifier = Modifier.weight(.1f))
                }

                Spacer(modifier = Modifier.padding(12.dp))
                spacer(2)
            }
        }
        , image = AppPreferences.get_ProfileImage()
    )

    /// delete account
    Common_Popup(
        visible = deactivated.value,
        modifier = Modifier.background(Color(0xffFCEDEC)),
        image = "",
            //AppPreferences.get_ProfileImage(),
        icon = 0,
        userName = AppPreferences.get_User_Name().ifEmpty { "Username" },
        content = {
            Column (
                modifier = Modifier
                , verticalArrangement = Arrangement.spacedBy(if (forTab()) 16.dp else 8.dp)
                , horizontalAlignment = Alignment.CenterHorizontally
            ){


                spacer(2)
                Image(painter = painterResource(R.drawable.deletepopupicon), "",
                    modifier = Modifier.size(64.dp))
                spacer(2)

                Text("Are you sure you want to Delete Account?"
                    , color = Color.Black
                    , fontSize = constants.textUnit(16)
                    , fontFamily = constants.fontFamily(0)
                )

                spacer(2)

                Text("Your account will be disabled and permanently deleted after 30 days. You can reactivate it anytime by logging in before that."
                    , color = Color(0xff484848)
                    , fontSize = constants.textUnit(12)
                    , fontFamily = constants.fontFamily(3)
                    , textAlign = TextAlign.Center
                    , modifier = Modifier.padding(horizontal = 16.dp)
                   , lineHeight = 24.sp
                )

                spacer(6)

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                    , verticalAlignment = Alignment.CenterVertically
                    , horizontalArrangement = Arrangement.SpaceEvenly
                )
                {
                    Box(
                        modifier = Modifier
                            .height(46.dp)
                            .width(if (forTab()) 156.dp else 136.dp)
                            .noRippleClickable{
                                deactivated.value = false
                                //isChecked.value = false
                            }
                            .background(Color(0xffE8E8E8))
                            .padding(horizontal = 16.dp)
                        , contentAlignment = Alignment.Center
                    ){
                        Text("Cancel"
                            , color = newBlack
                            , fontSize = constants.textUnit(14)
                            , fontFamily = constants.fontFamily(0)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .height(46.dp)
                            .width(if (forTab()) 156.dp else 136.dp)
                            .noRippleClickable{
                                if ( network.value == NetworkStatus.Online) {
                                    ClickHelper.getInstance().clickOnce {
                                        if (ClickGuard.canClick()) {
                                            constants.API_Vm.Put_account_Activate_Deactivate(
                                                user_id = AppPreferences.getUserId(),
                                                account_delete_type = constants.Profile_ViewModel.user_Manual_report_Index_delete.value.toString(),
                                                account_delete_sentence = constants.Profile_ViewModel.user_Manual_report_String_delete.value,
                                                status = 1,
                                            )
                                            { aPI_Result_Handling ->
                                                when (aPI_Result_Handling) {
                                                    is API_Result_Handling.Error -> {
                                                        println("failure")
                                                        toast("Something Went Wrong")
                                                    }

                                                    is API_Result_Handling.Deactivated -> {
                                                        // resultCallback(5)
                                                    }

                                                    is API_Result_Handling.Loading -> {}
                                                    is API_Result_Handling.Success -> {
                                                        println("Success")

                                                        constants.Profile_ViewModel.set_open_settings()
                                                        constants.Profile_ViewModel.onSet_Settings_Click(
                                                            0
                                                        )

                                                        constants.Common_H_ViewModel.changeStatus(
                                                            false
                                                        )

                                                        constants.Common_H_ViewModel.toggleshowBABars(
                                                            true
                                                        )
                                                        constants.Start_Up_ViewModel.updateLoginState(
                                                            0
                                                        )
                                                        constants.Start_Up_ViewModel.phoneNumber =
                                                            ""
                                                        constants.Start_Up_ViewModel.countryCode =
                                                            "+91"
                                                        constants.Start_Up_ViewModel.userName = ""
                                                        constants.Start_Up_ViewModel.otp = ""


                                                        //constants.Profile_ViewModel.dismiss_Logout_PP()
                                                        constants.Profile_ViewModel.onSet_Settings_Click(
                                                            -1
                                                        )
                                                        constants.Profile_ViewModel.setSelected_AS_Settings(
                                                            ""
                                                        )

                                                        AppPreferences.clearAllDeactivate()

                                                        constants.Profile_ViewModel.set_open_settings()
                                                        constants.Profile_ViewModel.onSet_Settings_Click(
                                                            0
                                                        )

                                                        constants.Common_H_ViewModel.changeStatus(false)

                                                        constants.Common_H_ViewModel.toggleshowBABars(
                                                            true
                                                        )
                                                        constants.Start_Up_ViewModel.updateLoginState(0)
                                                        constants.Start_Up_ViewModel.phoneNumber = ""
                                                        constants.Start_Up_ViewModel.countryCode = "+91"
                                                        constants.Start_Up_ViewModel.userName = ""
                                                        constants.Start_Up_ViewModel.otp = ""

                                                        constants.Profile_ViewModel.clear_All_BF_Handler()
                                                        constants.Profile_ViewModel.clearAllFFData()


                                                        //constants.Profile_ViewModel.dismiss_Logout_PP()
                                                        constants.Profile_ViewModel.onSet_Settings_Click(
                                                            -1
                                                        )
                                                        constants.Profile_ViewModel.setSelected_AS_Settings(
                                                            ""
                                                        )

                                                        constants.Profile_ViewModel.clearAllData_PRVM()
                                                        constants.PostProperty_ViewModel.clearAllData_PPVM()
                                                        constants.Reels_ViewModel.clearAllData_RVM()
                                                        constants.Start_Up_ViewModel.clearAllData_STVM()
                                                        constants.Common_H_ViewModel.clearAllData_CVM()
                                                        constants.Search_ViewModel.clearAllData_SVM()
                                                        constants.Enquiry_ViewModel.clearAllData_EVM()


                                                        onLogout()


                                                        constants.Profile_ViewModel.dismiss_Logout_PP()

                                                        deactivated.value = false
                                                    }

                                                    is API_Result_Handling.NoData -> {

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                else {
                                    toast(constants.activity.getString(R.string.no_Internet))
                                }

                            }
                            .background(Brush.verticalGradient(newRedGradienBg))
                            .padding(horizontal = 8.dp)
                        , contentAlignment = Alignment.Center
                    ){
                        Text("Delete Account"
                            , color = Color.White
                            , fontSize = constants.textUnit(14)
                            , fontFamily = constants.fontFamily(0)
                        )
                    }
                }


                Spacer(modifier = Modifier.padding(8.dp))
                spacer(2)
            }
        }
    )

    ///edit profile view



    BackHandler {
        ClickHelper.getInstance().clickOnce {
            if (selected_AS_Settings.value == "Account Settings") {
                constants.Profile_ViewModel.onSet_Settings_Click(-1)
            }
            else {
                constants.Profile_ViewModel.setSelected_AS_Settings("Account Settings")
                constants.Profile_ViewModel.onSet_Settings_Click(0)
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AS_Contactold(){

    var isChecked by remember { mutableStateOf(false) }
    val isError = constants.Start_Up_ViewModel.error_OTP.collectAsState()


    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    val verify_status = constants.Profile_ViewModel.verify_pp.collectAsState()

    val email = remember {  mutableStateOf("") }

    val is_Resend_Loading = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
       constants.Start_Up_ViewModel.WaNumber =  AppPreferences.get_User_WaNumber()
        email.value = AppPreferences.get_Email()
        println("CHEKINGG -- ${constants.Start_Up_ViewModel.WaNumber} --- ${AppPreferences.get_User_WaNumber()}")
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(newWhite)
            //.padding(top = notchPadding.value)
            //.padding(horizontal = 16.dp)
            .padding(horizontal = 16.dp)
        , verticalArrangement = Arrangement.spacedBy(16.dp)
    )
    {

        Text(
            "Mobile Number",
            fontSize = constants.textUnit(14),
            fontFamily = constants.fontFamily(1)
        )

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .border(1.dp, newGray, RoundedCornerShape(8.dp))
        )
        {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1.5f)
                    .background(newWhite)
                    .padding(horizontal = 8.dp)
                , contentAlignment = Alignment.Center
            )
            {
                Text(
                    "+91",
                    fontSize = constants.textUnit(12),
                    fontFamily = constants.fontFamily(2)
                )
            }

            VerticalDivider()

            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(8.5f)
                    .background(Color.White)
                    .padding(horizontal = 16.dp)
                , verticalAlignment = Alignment.CenterVertically
                , horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Text(
                    AppPreferences.get_ph_number(),
                    fontSize = constants.textUnit(12),
                    fontFamily = constants.fontFamily(2)
                )

                AsyncImage(
                    model = R.drawable.phone_verified
                    ,"",
                    modifier = Modifier
                        .size(16.dp)
                )
            }
        }


        Text(
            "WhatsApp Number",
            fontSize = constants.textUnit(14),
            fontFamily = constants.fontFamily(1)
        )

        PhoneNumberInput_Settings (
            phoneNumber = constants.Start_Up_ViewModel.WaNumber,
            onPhoneNumberChange = { constants.Start_Up_ViewModel.WaNumber = it },
            countryCode = constants.Start_Up_ViewModel.countryCode,
            onCountryCodeChange = {
                constants.Start_Up_ViewModel.countryCode = it
                constants.Start_Up_ViewModel.WaNumber = ""
                                  }
            , onClick = {

                //if (AppPreferences.get_User_WaNumber().isEmpty()){

                if (AppPreferences.get_User_WaNumber() == constants.Start_Up_ViewModel.WaNumber){
                    GlobalSnackbar.show("Change Number to verify and update")
                }
                else {
                    constants.API_Vm.put_Contact_Details(
                        user_id = AppPreferences.getUserId(),
                        whatsapp_num_cc = constants.Start_Up_ViewModel.countryCode,
                        whatsapp_num = constants.Start_Up_ViewModel.WaNumber,
                        email = email.value,
                    ){
                            apiResultHandling ->
                        when(apiResultHandling) {
                            is API_Result_Handling.Deactivated -> {
                                //resultCallback(5)
                            }
                            is API_Result_Handling.Loading -> {}
                            is API_Result_Handling.Success -> {
                                constants.Profile_ViewModel.set_verify_PP()
                                constants.Start_Up_ViewModel.otp = ""
                            }
                            is API_Result_Handling.NoData -> {}
                            is API_Result_Handling.Error -> {}
                        }
                    }
                }


//                }
//                else {
//                    constants.API_Vm.put_Contact_Details(
//                        user_id = AppPreferences.getUserId(),
//                        whatsapp_num_cc = constants.Start_Up_ViewModel.countryCode,
//                        whatsapp_num = constants.Start_Up_ViewModel.WaNumber,
//                        email = "",
//                    )
//                    {
//                            apiResultHandling ->
//                        when(apiResultHandling) {
//                            is API_Result_Handling.Loading -> {}
//                            is API_Result_Handling.Success -> {
//                                GlobalSnackbar.show("")
//                            }
//                            is API_Result_Handling.NoData -> {}
//                            is API_Result_Handling.Error -> {}
//                        }
//                    }
//                }

            }
        )


        // same as mobile
        Row (
            modifier = Modifier
                .fillMaxWidth()
            , verticalAlignment = Alignment.CenterVertically
//            , horizontalArrangement = Arrangement.
        )
        {
            Checkbox(
                checked = isChecked
                , onCheckedChange = {
                    isChecked = it
                }
            )

            Text(
                "same as mobile number",
                fontSize = constants.textUnit(12),
                fontFamily = constants.fontFamily(2)
            )
        }

        Text(
            "Email ID",
            fontSize = constants.textUnit(14),
            fontFamily = constants.fontFamily(1)
        )


        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(1.dp, newGray, RoundedCornerShape(8.dp))
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(8f)
                    .background(Color.White)
                    .padding(horizontal = 16.dp)
                , verticalAlignment = Alignment.CenterVertically
                , horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                TextField(
                    value = email.value,
                    onValueChange = {
                        email.value = it
                    },
                    modifier = Modifier
                    , colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedTextColor = newBlack,
                        unfocusedTextColor = newBlack,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = TextStyle(
                        color = newBlack,
                        fontSize = constants.textUnit(12),
                        fontFamily = constants.fontFamily(2)
                    )
//                        .fillMaxWidth()
                )

                AsyncImage(
                    model = R.drawable.phone_verified
                    ,"",
                    modifier = Modifier
                        .size(16.dp)
                )
            }


            VerticalDivider()

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(2f)
                    .clip( RoundedCornerShape(topEnd = 8.dp , bottomEnd = 8.dp))
                    .background(if (email.value.isEmpty()) Color(0xffF5F3F3) else newBlue)
                    .padding(horizontal = 8.dp)
                    .noRippleClickable{
                        ClickHelper.getInstance().clickOnce {

                            constants.API_Vm.put_Contact_Details(
                                user_id = AppPreferences.getUserId(),
                                whatsapp_num_cc = "",
                                whatsapp_num = "",
                                email = email.value,
                            ) { apiResultHandling ->
                                when (apiResultHandling) {
                                    is API_Result_Handling.Deactivated -> {
                                        //resultCallback(5)
                                    }
                                    is API_Result_Handling.Loading -> {}
                                    is API_Result_Handling.Success -> {
                                        //constants.Profile_ViewModel.set_verify_PP()
                                    }

                                    is API_Result_Handling.NoData -> {}
                                    is API_Result_Handling.Error -> {}
                                }
                            }
                            //constants.Profile_ViewModel.set_verify_PP()
                        }
                    }
                , contentAlignment = Alignment.Center
            )
            {
                Text(
                    if (email.value.isEmpty())"Change" else "Verify",
                    color = if (email.value.isEmpty()) newBlack else Color.White,
                    fontSize = constants.textUnit(12),
                    fontFamily = constants.fontFamily(2)
                )
            }

        }


        if (verify_status.value){

            val time = rememberCountdownTimer(180)

            ModalBottomSheet(
                onDismissRequest = {
                    constants.Profile_ViewModel.dismiss_verify_PP()
                }
                , sheetState = bottomSheetState
                , containerColor = newWhite

            ) {
                Column (
                    modifier = Modifier
                        .background(newWhite)
                        .padding(horizontal = 16.dp)
                    , verticalArrangement = Arrangement.spacedBy(16.dp)
                ){
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            , verticalAlignment = Alignment.CenterVertically
                        , horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        Text(
                            "Verification",
                            fontSize = constants.textUnit(24),
                            fontFamily = constants.fontFamily(0)
                        )

                        AsyncImage(
                            model = R.drawable.close_common
                            ,""
                            , modifier = Modifier
                                .size(24.dp)
                                .noRippleClickable{
                                    ClickHelper.getInstance().clickOnce {
                                        constants.Profile_ViewModel.dismiss_verify_PP()
                                    }
                                }
                        )
                    }



                    Text(
                        "Please enter the OTP sent to your entered mobile number to verify.",
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                    )

//                    OtpTextField(
//                        otp = constants.Start_Up_ViewModel.otp,
//                        onOtpChange = {
//                            constants.Start_Up_ViewModel.otp = it
//                        },
//                        modifier = Modifier
//                            .align(Alignment.CenterHorizontally),
//                        isError = false
//                    )

                    OTP_TF(
                        otp = constants.Start_Up_ViewModel.otp,
                        onOtpChange = {
                            constants.Start_Up_ViewModel.otp = it
                            //  constants.Start_Up_ViewModel.put_OTP_Response(it)
                            println("OTP GETTER - ${constants.Start_Up_ViewModel.get_OTP_Response()}")
                        },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        isError = isError.value
                    )

                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .wrapContentSize()
                            .clip(RoundedCornerShape(4.dp))
                            .border(1.dp, newGray, RoundedCornerShape(4.dp))
                            .padding(vertical = 4.dp, horizontal = 4.dp)
                        , verticalAlignment = Alignment.CenterVertically
                        , horizontalArrangement = Arrangement.spacedBy(8.dp)
                    )
                    {


                        SubcomposeAsyncImage(
                            model = R.drawable.otptimer,
                            modifier = Modifier
                                .size(16.dp)
                            , contentDescription = ""
                        )
                        {
                            val state = painter.state
                            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(8.dp)
                                ){
                                    Image(painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                        contentDescription = "",modifier = Modifier
                                            .matchParentSize())
                                }
                            } else {
                                SubcomposeAsyncImageContent()
                            }
                        }

                        Text(time.time.value , fontSize = constants.textUnit(14) , color = newBlue)


                    }

                    verifyOtp(
                        resendOTP = {
                            println("TIMER RESEND")
                            time.restart()
                            if (isConnected.value) {
                                constants.Start_Up_ViewModel.phoneNumber = constants.Start_Up_ViewModel.WaNumber
                                register_API_Call(
                                    resultCallback = { result ->
                                        when (result) {
                                            0 -> {
                                                //isResend_Loading.value = false
                                                println("TIMER RESEND")
                                                constants.Start_Up_ViewModel.is_Error_OTP_Reset()
                                                constants.Start_Up_ViewModel.otp = ""
                                                time.restart()
                                            }

                                            1 -> {
                                                toast("Resend OTP Failed to Initiate !")
                                            }

                                            2 -> {
                                               // isResend_Loading.value = true
                                            }
                                        }
                                    }
                                )
                                /*register_API_Call(
                                    resultCallback = { result ->
                                        when (result) {
                                            0 -> {
                                                //isResend_Loading.value = false
                                                println("TIMER RESEND")
                                                constants.Start_Up_ViewModel.is_Error_OTP_Reset()
                                                constants.Start_Up_ViewModel.otp = ""
                                                time.restart()
                                            }

                                            1 -> {
                                                toast("Resend OTP Failed to Initiate !")
                                            }

                                            2 -> {
                                               // isResend_Loading.value = true
                                            }
                                        }
                                    }
                                )*/
                            }
                            else {
                                toast("It Seems your are offline !!.Refresh again")
                            }
                        },
                        isTimerFinished = time.isFinished.value
                        , modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                        , isResend_Loading = is_Resend_Loading
                    )

                    Spacer(modifier = Modifier.padding(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .noRippleClickable{
                                ClickHelper.getInstance().clickOnce {
                                    constants.API_Vm.verify_OTP(
                                        user_id = AppPreferences.getUserId(),
                                        phone_num_cc = "",
                                        phone_num = "",
                                        whatsapp_num_cc = constants.Start_Up_ViewModel.countryCode,
                                        whatsapp_num = constants.Start_Up_ViewModel.WaNumber,
                                        email = email.value,
                                        otp = constants.Start_Up_ViewModel.otp,
                                        device_id = getDeviceId(constants.activity),
                                        device_type = "Android",
                                        device_token = deviceToken,
                                    ) { apiResultHandling ->
                                        when (apiResultHandling) {
                                            is API_Result_Handling.Deactivated -> {
                                                //resultCallback(5)
                                            }
                                            is API_Result_Handling.Error -> {}
                                            is API_Result_Handling.Deactivated -> {
                                                //resultCallback(5)
                                            }
                                            is API_Result_Handling.NoData -> {}
                                            is API_Result_Handling.Success -> {
                                                constants.API_Vm.put_Contact_Details(
                                                    user_id = AppPreferences.getUserId(),
                                                    whatsapp_num_cc = constants.Start_Up_ViewModel.countryCode,
                                                    whatsapp_num = constants.Start_Up_ViewModel.WaNumber,
                                                    email = email.value,
                                                ) { apiResultHandling ->
                                                    when (apiResultHandling) {
                                                        is API_Result_Handling.Loading -> {}
                                                        is API_Result_Handling.Deactivated -> {
                                                            //resultCallback(5)
                                                        }
                                                        is API_Result_Handling.Success -> {
                                                            AppPreferences.save_User_WaNumber(
                                                                constants.Start_Up_ViewModel.WaNumber
                                                            )
                                                            constants.Profile_ViewModel.dismiss_verify_PP()
                                                        }

                                                        is API_Result_Handling.NoData -> {}
                                                        is API_Result_Handling.Error -> {}
                                                    }
                                                }
                                            }

                                            is API_Result_Handling.Loading -> {}
                                        }
                                    }
                                }
                            }
                            .align(Alignment.CenterHorizontally)
                            .background(newBlue, RoundedCornerShape(8.dp))
                        , contentAlignment = Alignment.Center
                    ) {
                        Text("Verify", color = newWhite , fontSize = constants.textUnit(14),)
                    }

                }
            }
    }

    }

    BackHandler {
        ClickHelper.getInstance().clickOnce {
                constants.Profile_ViewModel.setSelected_AS_Settings("Account Settings")
                constants.Profile_ViewModel.onSet_Settings_Click(0)

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AS_Contact(){

    var isChecked by remember { mutableStateOf(false) }

    val isError = constants.Start_Up_ViewModel.error_OTP.collectAsState()

    var is_Error = remember { mutableStateOf(isError.value) }

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val verify_status = constants.Profile_ViewModel.verify_pp.collectAsState()

    val email = remember {  mutableStateOf("") }

    val is_Loading = remember { mutableStateOf(false) }

    var emailOrNumber = remember { mutableStateOf(0) }

    val focusManager = LocalFocusManager.current

    var network = rememberNetworkStatus()

    LaunchedEffect(Unit) {
        constants.Start_Up_ViewModel.WaNumber =  AppPreferences.get_User_WaNumber()
        email.value = AppPreferences.get_Email_Address()
        println("CHEKINGG -- ${constants.Start_Up_ViewModel.WaNumber} --- ${AppPreferences.get_User_WaNumber()}")
    }

    LaunchedEffect(constants.Start_Up_ViewModel.WaNumber) {
        if(constants.Start_Up_ViewModel.WaNumber != AppPreferences.get_ph_number())
            isChecked = false
        else
            isChecked = true
    }


    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(newWhite)
            //.padding(top = notchPadding.value)
            //.padding(horizontal = 16.dp)
            .padding(horizontal = 16.dp)
        , verticalArrangement = Arrangement.spacedBy(16.dp)
    )
    {

        Text(
            "Mobile Number",
            fontSize = constants.textUnit(14),
            fontFamily = constants.fontFamily(1)
        )

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .border(1.dp, newGray, RoundedCornerShape(8.dp))
        )
        {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1.5f)
                    .background(newWhite)
                    .padding(horizontal = 8.dp)
                , contentAlignment = Alignment.Center
            )
            {
                Text(
                    AppPreferences.getCountry().emoji ?:"",
                    fontSize = constants.textUnit(12),
                    fontFamily = constants.fontFamily(2)
                )
            }

            VerticalDivider()

            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(8.5f)
                    .background(Color.White)
                    .padding(horizontal = 16.dp)
                , verticalAlignment = Alignment.CenterVertically
                , horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Text(
                    AppPreferences.get_ph_number(),
                    fontSize = constants.textUnit(12),
                    fontFamily = constants.fontFamily(2)
                )

                AsyncImage(
                    model = R.drawable.phone_verified
                    ,"",
                    modifier = Modifier
                        .size(16.dp)
                )
            }
        }


        Text(
            "WhatsApp Number",
            fontSize = constants.textUnit(14),
            fontFamily = constants.fontFamily(1)
        )

        PhoneNumberInput_Settings_WithBottomSheet(
            phoneNumber = constants.Start_Up_ViewModel.WaNumber,
            onPhoneNumberChange = { constants.Start_Up_ViewModel.WaNumber = it },
            countryCode = constants.Start_Up_ViewModel.countryCode,
            onCountryCodeChange = { constants.Start_Up_ViewModel.countryCode = it },
            onClick = {
                focusManager.clearFocus()
                //if (AppPreferences.get_User_WaNumber().isEmpty()){

                constants.Start_Up_ViewModel.is_Error_OTP_Reset()

                if (network.value == NetworkStatus.Online)
                {
                    if (AppPreferences.get_User_WaNumber() == constants.Start_Up_ViewModel.WaNumber && constants.Start_Up_ViewModel.WaNumber.isNotEmpty()) {
                        GlobalSnackbar.show("Number already exist")
                    }
                    else if (constants.Start_Up_ViewModel.WaNumber.isNullOrBlank())
                        GlobalSnackbar.show("Please enter WhatsApp number")
                    else {
                        constants.API_Vm.put_Contact_Details(
                            user_id = AppPreferences.getUserId(),
                            whatsapp_num_cc = constants.Start_Up_ViewModel.countryCode,
                            whatsapp_num = constants.Start_Up_ViewModel.WaNumber,
                            email = email.value,
                        )
                        { apiResultHandling ->
                            when (apiResultHandling) {
                                is API_Result_Handling.Deactivated -> {
                                    //resultCallback(5)
                                    is_Loading.value = false
                                }

                                is API_Result_Handling.Loading -> {
                                    is_Loading.value = true
                                }
                                is API_Result_Handling.Success -> {
                                    is_Loading.value = false
                                    emailOrNumber.value = 1
                                    constants.Profile_ViewModel.set_verify_PP()
                                    constants.Start_Up_ViewModel.otp = ""
                                }

                                is API_Result_Handling.NoData -> {}
                                is API_Result_Handling.Error -> {
                                    is_Loading.value = false
                                }
                            }
                        }
                    }
                }
                else {
                    toast(constants.activity.getString(R.string.no_Internet))
                }
            },
            viewModel = constants.Start_Up_ViewModel
        )
//        PhoneNumberInput_Settings (
//            phoneNumber = constants.Start_Up_ViewModel.WaNumber,
//            onPhoneNumberChange = { constants.Start_Up_ViewModel.WaNumber = it },
//            countryCode = constants.Start_Up_ViewModel.countryCode,
//            onCountryCodeChange = { constants.Start_Up_ViewModel.countryCode = it },
//
//        )


        // same as mobile
        Row (
            modifier = Modifier
                .fillMaxWidth()
            , verticalAlignment = Alignment.CenterVertically
//            , horizontalArrangement = Arrangement.
        )
        {
            Checkbox(
                checked = isChecked ,
                onCheckedChange = {
                    isChecked = it
                    if(isChecked)
                        constants.Start_Up_ViewModel.WaNumber = AppPreferences.get_ph_number()
                    else
                        constants.Start_Up_ViewModel.WaNumber = ""
                }
            )

            Text(
                "same as mobile number",
                fontSize = constants.textUnit(12),
                fontFamily = constants.fontFamily(2)
            )
        }

        Text(
            "Email ID",
            fontSize = constants.textUnit(14),
            fontFamily = constants.fontFamily(1)
        )


        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(1.dp, newGray, RoundedCornerShape(8.dp))
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(8f)
                    .background(Color.White)
                    .padding(horizontal = 16.dp)
                , verticalAlignment = Alignment.CenterVertically
                , horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                TextField(
                    value = email.value,
                    onValueChange = {
                        email.value = it
                    },
                    placeholder = {
                        Text("Enter email address", fontSize = constants.textUnit(12))
                    },
                    singleLine = true,
                    modifier = Modifier,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedTextColor = newBlack,
                        unfocusedTextColor = newBlack,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = TextStyle(
                        color = newBlack,
                        fontSize = constants.textUnit(12),
                        fontFamily = constants.fontFamily(2)
                    )
//                        .fillMaxWidth()
                )

                AsyncImage(
                    model = R.drawable.phone_verified
                    ,"",
                    modifier = Modifier
                        .size(16.dp)
                )
            }


            VerticalDivider()

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(2f)
                    .clip( RoundedCornerShape(topEnd = 8.dp , bottomEnd = 8.dp))
                    .background(if (email.value.isNullOrBlank()) Color(0xffF4F4F4)
                    else if(Patterns.EMAIL_ADDRESS.matcher(email.value).matches() && email.value != AppPreferences.get_Email_Address()) newBlue
                    else if(email.value == AppPreferences.get_Email_Address()) Color(0xffF5F3F3)
                    else Color(0xffF4F4F4) )
                    .padding(horizontal = 8.dp)
                    .clickable {
                        ClickHelper.getInstance().clickOnce {
                            constants.Start_Up_ViewModel.is_Error_OTP_Reset()
                            focusManager.clearFocus()
                            if (network.value == NetworkStatus.Online) {
                                if (AppPreferences.get_Email_Address() == email.value && email.value.isNotEmpty()) {
                                    GlobalSnackbar.show("Email Address already exist")
                                } else if (email.value.isNullOrBlank())
                                    GlobalSnackbar.show("Please enter email address")
                                else {
                                    constants.API_Vm.put_Contact_Details(
                                        user_id = AppPreferences.getUserId(),
                                        whatsapp_num_cc = "",
                                        whatsapp_num = "",
                                        email = email.value,
                                    )
                                    { apiResultHandling ->
                                        when (apiResultHandling) {
                                            is API_Result_Handling.Deactivated -> {
                                                //resultCallback(5)
                                                is_Loading.value = false
                                            }

                                            is API_Result_Handling.Loading -> {
                                                is_Loading.value = true
                                            }
                                            is API_Result_Handling.Success -> {
                                                emailOrNumber.value = 2
                                                constants.Profile_ViewModel.set_verify_PP()
                                                constants.Start_Up_ViewModel.otp = ""
                                                is_Loading.value = false
                                            }

                                            is API_Result_Handling.NoData -> {}
                                            is API_Result_Handling.Error -> {
                                                is_Loading.value = false
                                            }
                                        }
                                    }
                                }
                            }
                            else {
                                toast(constants.activity.getString(R.string.no_Internet))
                            }
                        }
                    }
                , contentAlignment = Alignment.Center
            )
            {
                Text(
                    if(email.value == AppPreferences.get_Email_Address() && !email.value.isNullOrBlank()) "Change" else "Verify",
                    color = if(Patterns.EMAIL_ADDRESS.matcher(email.value).matches() && email.value != AppPreferences.get_Email_Address()) Color.White else if(email.value == AppPreferences.get_Email_Address() && email.value.isNotEmpty()) newBlue else Color(0xFF666666),
                    fontSize = constants.textUnit(12),
                    fontFamily = constants.fontFamily(2)
                )
            }

        }


        if (verify_status.value)
        {
            val time = rememberCountdownTimer(180)


            ModalBottomSheet(
                onDismissRequest = {
                    constants.Profile_ViewModel.dismiss_verify_PP()
                }
                , sheetState = bottomSheetState
                , containerColor = newWhite

            ) {

                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                    , verticalArrangement = Arrangement.spacedBy(8.dp)
//        , horizontalAlignment = Align
                )
                {

                    spacer(4)

//                    Image(painter = painterResource(R.drawable.left_arrow) , ""
//                        , modifier = Modifier.noRippleClickable {
//                            index.value = 0
//                        }
//                    )

                    Image(painter = painterResource(R.drawable.verificationheader) , ""
                        , modifier = Modifier.align(Alignment.CenterHorizontally))

                    spacer(4)

                    CommonText("Verification"
                        , newBlack
                        , 20
                        , 0
                        , modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    CommonText("We sent a code to your entered \n ${if (emailOrNumber.value == 1) "whatsapp number" else "email Id" } ${if (emailOrNumber.value == 1) constants.Start_Up_ViewModel.WaNumber else email.value } "
                        , newBlack
                        , 12
                        , 3
                        , modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    spacer(4)

                    OTP_TF_6(
                        otp = constants.Start_Up_ViewModel.otp,
                        onOtpChange = {
                            constants.Start_Up_ViewModel.otp = it
                        },
                        isError = is_Error,
                        modifier = Modifier
                    )

                    if (isError.value){
                        Row() {
                            Image(
                                painter = painterResource(R.drawable.error),
                                "",
                                colorFilter = ColorFilter.tint(Color.Red)
                            )

                            CommonText("Verification code is expired or incorrect. Try again." , Color.Red,12 ,3)

                        }
                    }

                    spacer(4)

                    if (!time.isFinished.value){
                        Text("Resend Code in ${time.time.value }"
                            , fontSize = constants.textUnit(14)
                            , color = newBlack
                            , modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                    else {
                        Text("Resend Code"
                            , fontSize = constants.textUnit(14)
                            , color = newBlue
                            , modifier = Modifier.align(Alignment.CenterHorizontally)
                                .noRippleClickable {
                                    AppPreferences.save_User_Verify_Otp("")
                                    if (network.value == NetworkStatus.Online) {
//                           register_API_Call(resultCallback = {})
                                        constants.Start_Up_ViewModel.phoneNumber = constants.Start_Up_ViewModel.WaNumber
                                        constants.API_Vm.user_Login(
                                            phone_num = constants.Start_Up_ViewModel.phoneNumber,
                                            phone_num_cc =  constants.Start_Up_ViewModel.get_Country_Code(),
                                            device_id = getDeviceId(constants.activity),
                                            device_type = getDeviceType(),
                                            device_token = deviceToken,
                                        )
                                        { apiResultHandling ->
                                            when (apiResultHandling) {
                                                is API_Result_Handling.Loading -> {
                                                    //state = true
                                                    // resultCallback(2)
                                                    // isResend_Loading.value = true
                                                }

                                                is API_Result_Handling.Deactivated -> {
                                                    //resultCallback(5)
                                                }

                                                is API_Result_Handling.NoData -> {
                                                    //state = false
                                                    constants.Common_H_ViewModel.changeStatus(false)
                                                    toast("Something went wrong , No Records found")
                                                }

                                                is API_Result_Handling.Error -> {
                                                    //state = false
                                                    //resultCallback(1)
                                                    toast("Resend OTP Failed to Initiate!")
                                                }

                                                is API_Result_Handling.Success -> {
                                                    //state = false
                                                    //  resultCallback(0)
                                                    // isResend_Loading.value = false
                                                    println("TIMER RESEND")
                                                    constants.Start_Up_ViewModel.is_Error_OTP_Reset()
                                                    emailOrNumber.value = 0
                                                    is_Error.value = false
                                                    constants.Start_Up_ViewModel.otp = ""
                                                    time.restart()
                                                }
                                            }
                                        }
                                    } else {
                                        toast("It Seems your are offline !!.Refresh again")
                                    }

                                }
                        )
                    }


                    spacer(4)

                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .height(56.dp)
                            .noRippleClickable {
                                ClickHelper.getInstance().clickOnce {
                                    if (network.value == NetworkStatus.Online) {
                                        constants.API_Vm.verify_OTP(
                                            user_id = AppPreferences.getUserId(),
                                            phone_num_cc = "",
                                            phone_num = "",
                                            whatsapp_num_cc = constants.Start_Up_ViewModel.countryCode,
                                            whatsapp_num = constants.Start_Up_ViewModel.WaNumber,
                                            email = email.value,
                                            otp = constants.Start_Up_ViewModel.otp,
                                            device_id = getDeviceId(constants.activity),
                                            device_type = "Android",
                                            device_token = deviceToken,
                                        )
                                        { apiResultHandling ->
                                            when (apiResultHandling) {
                                                is API_Result_Handling.Deactivated -> {
                                                    //resultCallback(5)
                                                }

                                                is API_Result_Handling.Error -> {}
                                                is API_Result_Handling.Deactivated -> {
                                                    //resultCallback(5)
                                                }

                                                is API_Result_Handling.NoData -> {}
                                                is API_Result_Handling.Success -> {

                                                    AppPreferences.save_User_WaNumber(constants.Start_Up_ViewModel.WaNumber)
                                                    AppPreferences.save_Email_Address(email.value)
                                                    constants.Start_Up_ViewModel.otp = ""
                                                    constants.Profile_ViewModel.dismiss_verify_PP()
                                                    is_Error.value = false
                                                    GlobalSnackbar.show("Details Updated Successfullly")


                                                    /// store value to datstore
                                                    /*constants.API_Vm.put_Contact_Details(
                                                    user_id = AppPreferences.getUserId(),
                                                    whatsapp_num_cc = constants.Start_Up_ViewModel.countryCode,
                                                    whatsapp_num = constants.Start_Up_ViewModel.WaNumber,
                                                    email = email.value,
                                                )
                                                { apiResultHandling ->
                                                    when (apiResultHandling) {
                                                        is API_Result_Handling.Loading -> {}
                                                        is API_Result_Handling.Deactivated -> {
                                                            //resultCallback(5)
                                                        }
                                                        is API_Result_Handling.Success -> {
                                                            AppPreferences.save_User_WaNumber(
                                                                constants.Start_Up_ViewModel.WaNumber
                                                            )
                                                            constants.Profile_ViewModel.dismiss_verify_PP()
                                                        }

                                                        is API_Result_Handling.NoData -> {}
                                                        is API_Result_Handling.Error -> {}
                                                    }
                                                }*/
                                                }

                                                is API_Result_Handling.Loading -> {}
                                            }
                                        }
                                    }
                                    else {
                                        toast(constants.activity.getString(R.string.no_Internet))
                                    }
                                }
                            }
                            .background(Brush.verticalGradient(newPurpleGradient), RoundedCornerShape(8.dp))
                            .border(
                                1.dp,
                                Brush.verticalGradient(newPurpleGradientBorder),
                                RoundedCornerShape(8.dp)
                            )
                        , contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Verify",
                            color = newWhite,
                            fontSize = constants.textUnit(15),
                            fontFamily = constants.fontFamily(0)
                        )

                    }

                    spacer(16)

                }

            }
        }

    }

    if (is_Loading.value) {
        AlertDialog(onDismissRequest = {
            is_Loading.value = false
        }, content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .noRippleClickable{}
                    .background(Color.Transparent), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = newBlue)
            }
        })
    }

    BackHandler {
        ClickHelper.getInstance().clickOnce {
            constants.Profile_ViewModel.setSelected_AS_Settings("Account Settings")
            constants.Profile_ViewModel.onSet_Settings_Click(0)

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneNumberInput_Settings_WithBottomSheet(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    countryCode: String,
    onCountryCodeChange: (String) -> Unit,
    onClick: () -> Unit,
    viewModel: Start_Up_ViewModel
) {

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.add_selectedCountry(AppPreferences.getCountry())
    }

    val selectedCountry = viewModel.selectedCountryVm.collectAsState()

    var isLimit by remember { mutableStateOf(10) }
    var showSheet by remember { mutableStateOf(false) }

    // Update limit when country changes
    LaunchedEffect(selectedCountry.value) {
        isLimit = selectedCountry.value?.limit ?: 10
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(1.dp, newGray, RoundedCornerShape(8.dp))
    ) {

        /* Country Picker Button */
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .noRippleClickable { showSheet = true }
                .padding(start = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
//                Text(
//                    text = selectedCountry.value?.dial_code ?: countryCode,
//                    fontSize = constants.textUnit(12),
//                    fontFamily = constants.fontFamily(0),
//                    color = newBlack
//                )
//
//                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = selectedCountry.value?.emoji ?: "",
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.width(4.dp))

                Image(
                    painter = painterResource(R.drawable.arrowdown),
                    contentDescription = "",
                    modifier = Modifier.size(14.dp)
                )
            }
        }

        VerticalDivider(
            modifier = Modifier.width(1.dp),
            color = newGray
        )

        /* Phone Number Input */
        TextField(
            value = phoneNumber,
            onValueChange = {
                if (it.length <= isLimit && it.all { c -> c.isDigit() }) {
                    onPhoneNumberChange(it)
                }
            },
            placeholder = {
                Text("Mobile number", fontSize = constants.textUnit(12))
            },
            trailingIcon = {
                if (
                    AppPreferences.get_User_WaNumber().isNotEmpty() &&
                    constants.Start_Up_ViewModel.WaNumber == AppPreferences.get_User_WaNumber()
                ) {
                    Image(
                        painter = painterResource(R.drawable.phone_verified),
                        contentDescription = ""
                    )
                }
            },
            modifier = Modifier
                .weight(3f)
                .padding(horizontal = 4.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = newWhite,
                unfocusedContainerColor = newWhite,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedPlaceholderColor = newGray,
                unfocusedPlaceholderColor = newGray,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )
        )

        VerticalDivider()

        /* Verify / Change Button */
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .clip(RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
                .background(
                    when {
                        phoneNumber.length != isLimit ->
                            Color(0xffF5F3F3)

                        AppPreferences.get_User_WaNumber() ==
                                constants.Start_Up_ViewModel.WaNumber ->
                            Color(0xffF5F3F3)

                        else -> newBlue
                    }
                )
                .padding(horizontal = 10.dp)
                .noRippleClickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (AppPreferences.get_User_WaNumber().isEmpty())
                    "Verify" else "Change",
                fontSize = constants.textUnit(12),
                fontFamily = constants.fontFamily(2),
                color = when {
                    phoneNumber.length != isLimit -> newBlack
                    AppPreferences.get_User_WaNumber() ==
                            constants.Start_Up_ViewModel.WaNumber -> newBlack
                    else -> Color.White
                }
            )
        }
    }

    /* Country Picker Bottom Sheet */
    CountryPickerBottomSheet(
        context = context,
        showSheet = showSheet,
        onDismiss = { showSheet = false },
        onSelect = {
            viewModel.add_selectedCountry(it)
            onCountryCodeChange(it.dial_code ?: "")
            showSheet = false
        },
        viewModel = viewModel
    )
}




var notification = mutableStateOf(false)



@Composable
fun AS_Notification(){

    var customNotificationAlert = remember { mutableStateOf(false) }
    var getState by remember { mutableStateOf(0) }
    var isChecked = remember { mutableStateOf(false) }
    // val notification_Subs = constants.Profile_ViewModel.notification_Subs.collectAsState()
    val notification_Subs = constants.Profile_ViewModel.notification_Subs.collectAsState()


    var network = rememberNetworkStatus()




// Replace your LaunchedEffect with this:

    LaunchedEffect(Unit) {
        constants.API_Vm.get_Notification_Settings(
            user_id = AppPreferences.getUserId()
        ) { aPI_Result_Handling ->
            when(aPI_Result_Handling) {
                is API_Result_Handling.Loading -> {
                    println("ASD 000-- ")
                    getState = 0
                }
                is API_Result_Handling.Deactivated -> {

                }
                is API_Result_Handling.Error -> {
                    println("ASD 222-- ")
                    getState = 2
                }
                is API_Result_Handling.NoData -> {
                    println("ASD 3333-- ")
                    //getState = 3
                }
                is API_Result_Handling.Success -> {
                    println("ASD 111-- ")
                    isChecked.value = constants.Profile_ViewModel.api_NS_Ids_StringList.value.first

                    // IMPORTANT: Clear existing selections first
                    // This assumes you have a method to clear selections in your ViewModel
                    // If not, you may need to add one

                    // Set the selected items from API
                    constants.Profile_ViewModel.api_NS_Ids_StringList.value.second.forEach { s ->
                        constants.Profile_ViewModel.set_selected_Notification_Sub(s.toInt())
                    }

                    println("ASD 111-- ${notification_Subs.value}")

                    // Add a small delay to ensure state propagation
                    // OR better: move getState update to after state collection
                    ///delay(100)
                    getState = 1
                }
            }
        }
    }

// BETTER SOLUTION: Use derived state
// Replace the notification_Subs collection with:



    Box{

        Column(
            modifier = Modifier
                .fillMaxSize()
        )
        {

            if (network.value == NetworkStatus.Offline) {

                /// constants.activity.getString(R.string.no_Internet)
                //GlobalSnackbar.show("It Seems your are offline !!.Refresh again")
                Column(
                    modifier = Modifier
                        .height(800.dp)
                        .fillMaxWidth(), verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painterResource(R.drawable.nointernerdesign), "")

                    Spacer(modifier = Modifier.padding(16.dp))

                    Text(
                        constants.activity.getString(R.string.no_Internet),
                        modifier = Modifier
                            .padding(horizontal = 56.dp)
                    )
                }
            }
            else {
                when (getState){
                    0 -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                            , verticalArrangement = Arrangement.Center
                            , horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            LottiAnimation(2)
                        }
                    }
                    1 -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(9f)
                                .background(newWhite)
                                .padding(horizontal = 16.dp)
                            //.padding(horizontal = 16.dp),
                            ,verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        )
                        {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            )
                            {
                                Text(
                                    "Allow Notification",
                                    fontSize = constants.textUnit(16),
                                    fontFamily = constants.fontFamily(1)
                                )
                                val context = LocalContext.current


                                Switch(
                                    checked = notification.value,
                                    onCheckedChange = {

                                        if(isPostNotificationpermissionEnabledHome()){
                                            notification.value = !notification.value

                                        }else
                                        {
                                            notification.value = false


                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)

                                            {

                                                if (ActivityCompat.checkSelfPermission(

                                                        constants.activity,

                                                        Manifest.permission.POST_NOTIFICATIONS

                                                    ) == PackageManager.PERMISSION_DENIED

                                                )

                                                {

                                                    println("NOTI----1")


                                                    val showRationale =

                                                        ActivityCompat.shouldShowRequestPermissionRationale(

                                                            constants.activity,

                                                            Manifest.permission.POST_NOTIFICATIONS

                                                        )

                                                    // customised Permission alert

                                                    if (showRationale) {
                                                        println("NOTI----2")
                                                        customNotificationAlert.value = true

                                                    }

                                                    // default alert

                                                    else

                                                    {

                                                        if (ActivityCompat.checkSelfPermission(

                                                                constants.activity,

                                                                Manifest.permission.POST_NOTIFICATIONS

                                                            ) == PackageManager.PERMISSION_GRANTED

                                                        )

                                                        {
                                                            println("NOTI----3")


                                                        }

                                                        else {

                                                            println("NOTI----4")
                                                            ActivityCompat.requestPermissions(

                                                                constants.activity,

                                                                arrayOf(Manifest.permission.POST_NOTIFICATIONS),

                                                                REQUEST_CODE
                                                            )
                                                        }
                                                    }
                                                }

                                                else

                                                {

                                                    if (ActivityCompat.checkSelfPermission(

                                                            constants.activity,

                                                            Manifest.permission.POST_NOTIFICATIONS

                                                        ) == PackageManager.PERMISSION_GRANTED

                                                    )

                                                    {

                                                        println("NOTI----5")



                                                    }

                                                    else {
                                                        println("NOTI----6")
                                                        ActivityCompat.requestPermissions(

                                                            constants.activity,

                                                            arrayOf(Manifest.permission.POST_NOTIFICATIONS),

                                                            REQUEST_CODE

                                                        )
                                                    }

                                                }

                                            }

                                            else

                                            {

                                                println("NOTI----7")


                                                notification.value = !notification.value

                                                println("InsideNotification")

                                            }
                                        }


//                                jklkl
//
//
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                                    if(ContextCompat.checkSelfPermission(
//                                            constants.activity,
//                                            android.Manifest.permission.POST_NOTIFICATIONS
//                                        ) == PackageManager.PERMISSION_GRANTED){
//                                        constants.sharedHelper.getBoolean(constants.activity,constants.notificationEnabled)
//                                    }else {
//                                        false
//                                    }
//                                }else constants.sharedHelper.getBoolean(constants.activity,constants.notificationEnabled)
//
//






                                    },
                                    colors = SwitchDefaults.colors(
                                        checkedTrackColor = newBlue,
                                        uncheckedTrackColor = Color(0xffE8E9E9),
                                        checkedThumbColor = Color.White,
                                        uncheckedThumbColor = Color.White,
                                        checkedBorderColor = Color.Transparent,
                                        uncheckedBorderColor = Color.Transparent
                                    )
                                )
                            }

                            AnimatedVisibility(
                                visible = notification.value ,enter = slideInVertically { -it }
                            )
                            {
                                Column {

                                    notification_Subs.value.forEachIndexed { index, item ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                item.title,
                                                fontSize = constants.textUnit(16),
                                                fontFamily = constants.fontFamily(1)
                                            )

                                            Checkbox(
                                                checked = item.isSelected,
                                                onCheckedChange = {
                                                    constants.Profile_ViewModel.set_selected_Notification_Sub_(item.id)
                                                }, colors = CheckboxDefaults.colors(
                                                    checkedColor = newBlue, uncheckedColor = Color(0xffE8E9E9)
                                                )
                                            )
                                        }
                                    }

                                }
                            }

                        }
                        val status by rememberNetworkStatus()

                        Static_Bottom(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                            , content = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(.9f)
                                        .fillMaxHeight(.7f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .noRippleClickable {

                                            if(status == NetworkStatus.Online) {
                                                val idss = notification_Subs.value
                                                    .filter { it.isSelected }
                                                    .map { it.id }
                                                    .joinToString(",")

                                                println(
                                                    "SELECTEDIDD  IDD ${idss}-- ${
                                                        notification_Subs.value.filter { it.isSelected }
                                                            .joinToString()
                                                    }"
                                                )

                                                constants.sharedHelper.putBoolean(
                                                    constants.activity,
                                                    notificationEnabled,
                                                    notification.value
                                                )

                                                constants.API_Vm.put_Notification_Settings(
                                                    user_id = AppPreferences.getUserId(),
                                                    allow_notification = if (isChecked.value) 1 else 0,
                                                    notification_ids = idss,
                                                )
                                                { aPI_Result_Handling ->
                                                    when (aPI_Result_Handling) {
                                                        is API_Result_Handling.Loading -> {

                                                        }

                                                        is API_Result_Handling.Deactivated -> {
                                                            // resultCallback(5)
                                                        }

                                                        is API_Result_Handling.Error -> {

                                                        }

                                                        is API_Result_Handling.NoData -> {

                                                        }

                                                        is API_Result_Handling.Success<*> -> {

                                                            GlobalSnackbar.show("Notification settings updated.")


                                                        }
                                                    }
                                                }
                                            }else{
                                                GlobalSnackbar.show(constants.activity.resources.getString(R.string.no_Internet))
                                            }


                                        }
                                        .background(newBlue), contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        "Submit",
                                        color = Color.White,
                                        fontSize = constants.textUnit(14),
                                        fontFamily = constants.fontFamily(0)
                                    )
                                }
                            }
                        )
                    }

                    2 ->  {
                        GlobalSnackbar.show("Failed to fetch your Notification Settings")
                    }

                }
            }


        }

        if(customNotificationAlert.value){

            PermissionDilaog(customNotificationAlert)

        }
    }



    BackHandler {
        ClickHelper.getInstance().clickOnce {
            constants.Profile_ViewModel.setSelected_AS_Settings("Account Settings")
            constants.Profile_ViewModel.onSet_Settings_Click(0)

        }
    }
}


fun isPostNotificationpermissionEnabledHome(): Boolean{
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if(ContextCompat.checkSelfPermission(
                constants.activity,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED){
            true
        }else {
            false
        }
    } else{
        true
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionDilaog(notificationValue: MutableState<Boolean>) {


    Column(modifier = Modifier
        .fillMaxSize()
        .padding(7.dp)
        .background(Color.Transparent)
        .noRippleClickable {}
        , verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {

        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(7.dp), colors =  CardDefaults.cardColors(
            containerColor = Color.White,
        ), shape = RoundedCornerShape(6.dp), elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )) {


            Column(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.padding(4.dp))
                Text(text = "Allow Notifications to get latest updates" ,textAlign = TextAlign.Center,modifier = Modifier
                    .fillMaxWidth()
                    , fontSize = 15.sp,
                    color =  Color.Black)

                Spacer(modifier = Modifier.padding(7.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                )
                {
                    OutlinedButton(
                        shape = RoundedCornerShape(5.dp),
                        onClick = {
                            notificationValue.value = false
                        },
                        border = BorderStroke(
                            1.dp,
                            Color(android.graphics.Color.parseColor("#186bf5"))
                        ),
                        modifier = Modifier
                            .width( 150.dp)

                    ) {
                        Text("Don`t Allow", color = Color(android.graphics.Color.parseColor("#186bf5")))
                    }

                    Button(
                        shape = RoundedCornerShape(5.dp),
                        onClick = {
                            notificationValue.value = false
                            if(ContextCompat.checkSelfPermission(
                                    constants.activity,
                                    Manifest.permission.POST_NOTIFICATIONS
                                ) == PackageManager.PERMISSION_DENIED) {
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                val uri: Uri = Uri.fromParts("package", constants.activity.packageName, null)
                                intent.data = uri
                                constants.activity.startActivity(intent)
                            }

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(android.graphics.Color.parseColor("#186bf5"))
                        ), modifier = Modifier
                            .width( 150.dp)
                    ) {
                        Text("Allow", color = Color.White, modifier = Modifier)
                    }


                }

                Spacer(modifier = Modifier.padding(7.dp))

            }



        }

    }

}


@Composable
fun AS_My_BlockList(
    content_PP_Unblock: MutableState<Triple<Int, String, String>>,
    navController: NavHostController
) {


    val network = rememberNetworkStatus()

    val isLoading = constants.API_Vm.isLoading_BUL
    val errorMessage = constants.API_Vm.errorMessage_BUL
    val currentPage = constants.API_Vm.currentPage_BUL
    val totalPages = constants.API_Vm.totalPages_BUL


    val listState = rememberLazyListState()

    var retry by remember { mutableStateOf(0) }

    println("LOADING -- ${isLoading} -- ${constants.API_Vm.isLoading_BUL} --- ${currentPage}")



    LaunchedEffect (Unit ,retry) {
        println("PRINTLN 2 BUL =  ")

        constants.API_Vm.isLoading_BUL = true
        // ✅ Only fetch if online
        constants.API_Vm.load_Blocked_Users_List(
            user_id = AppPreferences.getUserId(),
            page = 1
        )

    }


    // Detect when near end of list
    LaunchedEffect(listState, currentPage, isLoading, totalPages) {
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
                    constants.API_Vm.isLoading_BUL = true
                    //constants.API_Vm.loadCategories(currentPage + 1)
                    constants.API_Vm.load_Blocked_Users_List(
                        user_id = AppPreferences.getUserId(),
                        page = currentPage + 1
                    )

                }
            }
    }


    val blocked_Users_List = constants.Profile_ViewModel.get_Blocked_Users_List.collectAsStateWithLifecycle()



    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(newWhite)
            .padding(horizontal = 16.dp)
        , verticalArrangement = Arrangement.spacedBy(16.dp)
    ){

        when {

            network.value == NetworkStatus.Offline  -> {
                /// constants.activity.getString(R.string.no_Internet)
                //GlobalSnackbar.show("It Seems your are offline !!.Refresh again")
                Column(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxSize()
                    , verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painterResource(R.drawable.nointernerdesign), "")

                    Spacer(modifier = Modifier.padding(16.dp))

                    Text(
                        constants.activity.getString(R.string.no_Internet),
                        modifier = Modifier
                            .padding(horizontal = 56.dp)
                    )
                }
            }

            isLoading && currentPage == 1  -> {
                Box(
                    modifier = Modifier
                        .weight(9.5f)
                        .fillMaxWidth()
                    , contentAlignment = Alignment.Center
                ){
                    //CircularProgressIndicator()
                    LottiAnimation(2)
                }
            }


            !errorMessage.isNullOrEmpty() -> {
                Box(
                    modifier = Modifier
                        .weight(9.5f), contentAlignment = Alignment.Center
                ) {

                    API_Fail_UI(onReTryClick = {
                        retry = retry + 213435
                    })
                }
            }


            blocked_Users_List.value.isEmpty() && !isLoading -> {
                Column(
                    modifier = Modifier

                        //.background(Color.Red)
                        .fillMaxWidth()
                        .weight(9.5f)
                    , verticalArrangement = Arrangement.Center
                    , horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Image(painterResource(R.drawable.rentootherprofileblock) , "")

                    Text("No Accounts blocked!"
                        , color = newBlack
                        , fontSize = constants.textUnit(16)
                        , fontFamily = constants.fontFamily(1)
                    )

                    Spacer(modifier = Modifier.padding(4.dp))

                    Text("You haven't blocked any accounts yet. Block accounts to stop seeing their properties."
                        , color = Color(0xff666666)
                        , fontSize = constants.textUnit(12)
                        , fontFamily = constants.fontFamily(2)
                        , textAlign = TextAlign.Center
                        , modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
            }

            blocked_Users_List.value.isNotEmpty() -> {
                LazyColumn(
                    state = listState
                    , modifier = Modifier.padding(top = 16.dp)
                ) {

                    itemsIndexed(
                        items = blocked_Users_List.value,
                        key = { index, item -> "WHAT${item?.user_id}_${index}" }
                    )
                    { _, item ->

                        Column {
                            ListItem(
                                leadingContent = {
                                    Box(
                                        modifier = Modifier
                                            .size(46.dp)
                                            .clip(CircleShape)
                                            .background(newLightBlue)
                                    ) {
                                        SubcomposeAsyncImage(
                                            model = item?.profile_image ?: "",
                                            modifier = Modifier
                                                .clip(CircleShape)
                                                .fillMaxSize()
                                            , contentScale = ContentScale.FillBounds
                                            , contentDescription = ""
                                        )
                                        {
                                            val state = painter.state
                                            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .padding(8.dp)
                                                    , contentAlignment = Alignment.Center
                                                ){
                                                    Text(
                                                        text = item?.username.takeIf { it?.isNotEmpty() == true }?.take(1)?.uppercase() ?: "",
                                                        fontSize = constants.textUnit(14),
                                                        fontFamily = constants.fontFamily(1),
                                                        color = Color.Black
                                                    )
//                                Image(painter = painterResource(id = R.drawable.ic_launcher_foreground),
//                                    contentDescription = "",modifier = Modifier
//                                        .matchParentSize())
                                                }
                                            } else {
                                                SubcomposeAsyncImageContent()
                                            }
                                        }

                                    }
                                },
                                headlineContent = {
                                    Text(
                                        item?.username ?: "user_Name",
                                        fontSize = constants.textUnit(16),
                                        fontFamily = constants.fontFamily(1)
                                    )
                                },
                                supportingContent = {
                                    Text(
                                        item?.name ?: "real_name",
                                        fontSize = constants.textUnit(12),
                                        fontFamily = constants.fontFamily(2)
                                    )
                                },
                                trailingContent = {
                                    Row(
                                        modifier = Modifier
                                            .wrapContentSize()
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(Color.White)
                                            .border(1.dp, newBlack, RoundedCornerShape(4.dp))
                                            .padding(horizontal = 8.dp, vertical = 8.dp)
                                            .noRippleClickable{

                                                ClickHelper.getInstance().clickOnce {
                                                    constants.API_Vm.isLoading_BUL = false

//                                        content_PP_Unblock.copy(first =  , second = , third = )
                                                    constants.Profile_ViewModel.enable_Unblock_pp()
                                                    content_PP_Unblock.value = Triple(
                                                        item?.user_id ?: 0,
                                                        item?.username ?: "",
                                                        item?.profile_image ?: ""
                                                    )



                                                    // println("CONTENT POPUP __ ${content_PP_Unblock.first} === ${content_PP_Unblock.second} ==== ${content_PP_Unblock.third}")


                                                }
                                            }
//                                    .noRippleClickable{
//
//                                        //constants.Profile_ViewModel.setSelectedUser(item)
//                                       // constants.Profile_ViewModel.set_enabler_Unblock_PP()
//                                    }
                                        , horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        , verticalAlignment = Alignment.CenterVertically
                                    ) {
//                                        AsyncImage(
//                                            model = R.drawable.blocked_profiles, "", modifier = Modifier
//                                                .size(14.dp)
//                                        )

                                        Text(
                                            "Unblock",
                                            color = newBlack,
                                            fontSize = constants.textUnit(12),
                                            fontFamily = constants.fontFamily(1)
                                        )

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
                                modifier = Modifier.noRippleClickable{
                                    constants.Profile_ViewModel.add_Selected_User_Name(
                                        item?.username ?: "Username"
                                    )

                                    //new flowwewwwwwww
                                    constants.Profile_ViewModel.add_BF_Handler(
                                        Profile_Handle_Back(
                                            current_UsedId = AppPreferences.getUserId(),
                                            other_UserId = item?.user_id ?:0,
                                            ff_User_Name = item?.username ?:"user_Name" ,
                                            ff_Fw_Count = item?.followers ?: 999,
                                            ff_Fg_Count = item?.following ?: 999,
                                        )
                                    )

                                    constants.Profile_ViewModel.put_Other_User_Id(item?.user_id ?:0)
                                    println("GIVEN OTHER USER ID -- ${constants.Profile_ViewModel.get_Other_User_Id()}")


                                    constants.Profile_ViewModel.addProfile(item?.user_id ?: 0)
                                    constants.Profile_ViewModel.add_Selected_Profile_Id(id = item?.user_id ?:0)

                                    navController.navigate(ProfileScreenFlow.Other_Profile_Structure.route)
                                }
                            )

                            HorizontalDivider(color = Color(0xffCECECE))

                        }
                    }

//                    if (isLoading && currentPage >= 1) {
//                        item {
//                            Row (
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                , verticalAlignment = Alignment.CenterVertically
//                                , horizontalArrangement = Arrangement.Center
//                            ){
//                                CircularProgressIndicator()
//                            }
//                        }
//                    }
                }
            }

        }

    }

    BackHandler {
        ClickHelper.getInstance().clickOnce {
            constants.Profile_ViewModel.setSelected_AS_Settings("Account Settings")
            constants.Profile_ViewModel.onSet_Settings_Click(0)

        }
    }
}


@Composable
fun AS_Delete_Account(onLogout: () -> Unit, deactivated: MutableState<Boolean>) {



    var report_Options = constants.Profile_ViewModel.profile_Report_Options.collectAsState()

    var user_Manual_report = constants.Profile_ViewModel.user_Manual_report_delete
    var user_Manual_report_String = constants.Profile_ViewModel.user_Manual_report_String_delete
    constants.Profile_ViewModel.user_Manual_report_Index_delete

    var isChecked = remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .fillMaxSize()

    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(9.5f)
                .padding(horizontal = 16.dp)
            , verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            item {

                Column(
                    modifier = Modifier
                        .fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)
                )
                {
                    Column(
                        modifier = Modifier
                            .border(1.dp , Color(0xffE54C3C) , RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp , vertical = 8.dp)
                    ) {
                        Text(
                            text = "Account Deletion is a Permanent Action",
                            color = Color(0xffE54C3C),
                            fontSize = constants.textUnit(18),
                            fontFamily = constants.fontFamily(0),
                            modifier = Modifier.align(Alignment.Start)
                        )

                        spacer(2)

                        Text(
                            text = "This action is permanent and will remove all your data, including your saved searches, listings, and preferences.",
                            color = newBlack,
                            fontSize = constants.textUnit(14),
                            fontFamily = constants.fontFamily(2),
                            modifier = Modifier.align(Alignment.Start)
                        )

                        Text(
                            text = "Deleting your account will remove all your information from our system. If you ever want to return, you’ll need to create a new account.",
                            color = newBlack,
                            fontSize = constants.textUnit(14),
                            fontFamily = constants.fontFamily(2),
                            modifier = Modifier.align(Alignment.Start)
                        )

                        Text(
                            text = "This action is permanent and will remove all your data, including your saved searches, listings, and preferences.",
                            color = newBlack,
                            fontSize = constants.textUnit(14),
                            fontFamily = constants.fontFamily(2),
                            modifier = Modifier.align(Alignment.Start)
                        )
                        spacer(2)
                    }



                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                        //.padding(horizontal = 16.dp)
                        , verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        Text(
                            text = "Please select the main reason to deleting your account",
                            color = newBlack,
                            fontSize = constants.textUnit(18),
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
                                            constants.Profile_ViewModel.user_Manual_report_Index_delete.value =
                                                index + 1
                                            constants.Profile_ViewModel.user_Manual_report_String_delete.value =
                                                profileReportOptionsDc.option_title
                                        } else {
                                            user_Manual_report.value = true

                                            constants.Profile_ViewModel.toggle_ProfileReport_Options(
                                                profileReportOptionsDc.id
                                            )

                                            constants.Profile_ViewModel.user_Manual_report_Index_delete.value =
                                                index + 1

                                            constants.Profile_ViewModel.user_Manual_report_String_delete.value = ""

                                        }
                                    }
                                )
                            }
                        }

                        AnimatedVisibility(
                            user_Manual_report.value,
                            enter = slideInHorizontally(tween(900)) { it }
                        )
                        {
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
                                    onValueChange = {
                                        user_Manual_report_String.value = it
                                        //constants.Profile_ViewModel.user_Manual_report_String_delete.value = it

                                    },
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

                    spacer(2)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    )
                    {
                        Checkbox(
                            checked = isChecked.value, onCheckedChange = {
                                isChecked.value = it
                                //  deactivated = true
                            }, colors = CheckboxDefaults.colors(
                                checkedColor = newBlue
                            )
                        )

                        Text(
                            text = "Yes, I want to permanently delete my account",
                            color = newBlack,
                            fontSize = constants.textUnit(12),
                            fontFamily = constants.fontFamily(3),
                            modifier = Modifier
                        )

                    }
                    spacer(2)
                }


            }

        }

        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
              //  .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .weight(.5f)
                .clip(RoundedCornerShape(4.dp))
                .background(
                    Brush.verticalGradient(newRedGradienBg)
//                    if (constants.Profile_ViewModel.getSelectedProfileReportOptionDescription()
//                            ?.isNotEmpty() == true && isChecked.value
//                    )
//                        Color(0xffE54C3C)
//                    else
//                        Color(0xffE54C3C).copy(.5f)
                )
                .noRippleClickable {

                    if (constants.Profile_ViewModel.user_Manual_report_String_delete.value.isNotEmpty()) {
                        when {
                            (constants.Profile_ViewModel.getSelectedProfileReportOptionDescription()
                                ?.isNotEmpty() == true && isChecked.value
                                    ) -> {
                                deactivated.value = true
                            }

                            constants.Profile_ViewModel.getSelectedProfileReportOptionDescription()
                                ?.isNotEmpty() == false -> toast("Select Options to Delete Account")

                            !isChecked.value -> toast("Select Confirm to Delete Account")
                        }
                    }
                    else {
                        toast("Select the reason or enter the reson for deleting account")
                    }


                }
            , contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Delete Account",
                color = Color.White,
                fontSize = constants.textUnit(14),
                fontFamily = constants.fontFamily(0),
                modifier = Modifier
            )
        }

        Spacer(modifier = Modifier.padding(16.dp))
    }



    BackHandler {
        ClickHelper.getInstance().clickOnce {

            if (!deactivated.value) {
                constants.Profile_ViewModel.setSelected_AS_Settings("Account Settings")
                constants.Profile_ViewModel.onSet_Settings_Click(0)
            }
            else {
                deactivated.value = false
            }

        }
    }
}


@Composable
fun Saved_Properties(notchPadding: State<Dp> , navController: NavHostController) {

    val network = rememberNetworkStatus()

    val isLoading = constants.API_Vm.isLoading_Profile_SavedP
    val errorMessage = constants.API_Vm.errorMessage_Profile_SavedP
    val currentPage = constants.API_Vm.currentPage_Profile_SavedP
    val totalPages = constants.API_Vm.totalPages_Profile_SavedP
    val listState = rememberLazyListState()

    var retry by remember { mutableStateOf(0) }

    if (network.value == NetworkStatus.Online) {
        DisposableEffect(Unit , retry) {


            constants.API_Vm.load_Profile_Saved_Properties(
                user_id = AppPreferences.getUserId(),
                page = 1
            )


            onDispose {

            }
        }


        // Detect when near end of list
        LaunchedEffect(listState, currentPage, isLoading, totalPages , retry) {
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
                        constants.API_Vm.load_Profile_Saved_Properties(
                            user_id = AppPreferences.getUserId(),
                            page = currentPage + 1
                        )

                    }
                }
        }
    }
    else {
        GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
    }


    val saved_property = constants.Profile_ViewModel.profile_SavedP.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(newWhite)
            .padding(top = if (forTab()) 16.dp else  notchPadding.value)
        , verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .weight(.5f)
                .padding(horizontal = 16.dp)
            , contentAlignment = Alignment.Center
//            , verticalAlignment = Alignment.CenterVertically
//            , horizontalArrangement = Arrangement.Start
        )
        {
            // Header
            Backer(
                modifier = Modifier.align(Alignment.CenterStart)
                    //.align(Alignment.CenterHorizontally)
                    //.background(Color.Cyan)
                , onBackClick = {
                    constants.Profile_ViewModel.onSet_Settings_Click(-1)
                    //navController.navigateUp()
                    //navController.navigate(UserCredentialsScreenFlow.UserCredentials.route)
                }
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = "Saved Properties",
                color = Color.Black,
                fontSize = constants.textUnit(20),
                fontFamily = constants.fontFamily(0),
                //  lineHeight = 36.sp,
                modifier = Modifier.align(Alignment.Center)
                //.padding(vertical = 8.dp)
                // .align(Alignment.Start) // Align title to the start
            )
        }

        when {

            network.value == NetworkStatus.Offline  -> {
                /// constants.activity.getString(R.string.no_Internet)
                //GlobalSnackbar.show("It Seems your are offline !!.Refresh again")
                    Column(
                        modifier = Modifier
                            .weight(9.5f)
                            .fillMaxWidth()
                        , verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(painterResource(R.drawable.nointernerdesign), "")

                        Spacer(modifier = Modifier.padding(16.dp))

                        Text(
                            constants.activity.getString(R.string.no_Internet),
                            modifier = Modifier
                                .padding(horizontal = 56.dp)
                        )
                    }
            }

            isLoading && currentPage == 1 && saved_property.value.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .weight(9.5f)
                    , contentAlignment = Alignment.Center
                ){
                   LottiAnimation(2)
                }
            }


            !errorMessage.isNullOrEmpty() -> {
                Box(
                    modifier = Modifier
                        .weight(9.5f), contentAlignment = Alignment.Center
                ) {

                    API_Fail_UI(onReTryClick = {
                        retry = retry + 213435
                    })
                }
            }


            saved_property.value.isEmpty() && !isLoading -> {
                Column(
                    modifier = Modifier
                        //.background(Color.Red)
                        .fillMaxWidth()
                        .weight(9.5f)
                    , verticalArrangement = Arrangement.Center
                    , horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Image(painterResource(R.drawable.emptysavedprosrento) , "")

                    Text("No Properties Saved!"
                        , color = newBlack
                        , fontSize = constants.textUnit(16)
                        , fontFamily = constants.fontFamily(1)
                    )

                }
            }


            saved_property.value.isNotEmpty() -> {
                var gridtype = if (forTab()) 3 else 2
                // Show Interest Selection UI
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(9.5f)
                        .padding(horizontal = 16.dp , vertical = 16.dp),
                    // verticalArrangement = Arrangement
                        //.spacedBy(16.dp) // spacing between items
                )
                {
                    customGridItems(
                        count = saved_property.value.size,
                        nColumns = gridtype,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    )
                    { index ->

                        Box(
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                                .height(172.dp)
                                .clip(RoundedCornerShape(6.dp))
//                                .aspectRatio(0.94f) // Keeps consistent card size (approx 162/172)
                                .border(1.dp, color = newGray, RoundedCornerShape(6.dp))
                        )
                        {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .noRippleClickable{
                                        //constants.Start_Up_ViewModel.toggleInterestSelection(index)
//                                          val videoJson = Uri.encode(Json.encodeToString(soldOuts[index]))
//                                          navController.navigate("${ProfileScreenFlow.SingleVideoPlayerEnquiry.route}/$videoJson")

                                        // When clicking on an item
                                        // constants.Profile_ViewModel.selectVideo(soldOuts[index].toPostUser())
                                        //  constants.Profile_ViewModel.set_From_SoldOuts(true)
                                        // navController.navigate(ProfileScreenFlow.SingleVideoPlayerEnquiry2.route)
                                        constants.Reels_ViewModel.clear_view_pro_Details()
                                        AppPreferences.save_Post_Id(saved_property.value[index].user_post_id)
                                        constants.Search_ViewModel.select_RS_Reels_Flow(index)
                                        constants.Reels_ViewModel.setReelsContent(
                                            saved_property.value
                                            //.map { it.toReelsData() }  // map each element to Get_Reels_Data
                                        )
//                                        constants.Reels_ViewModel.add_View_Property_Details(posts.value.toReelsData())
//                                        val data = constants.Search_ViewModel.get_Search_Results()
//                                        println("SIZE OF DATA MAPPED TO SEARCH REELS FLOW DATA HOLDER -- ${data.size}  *** ${data.map { it.video }}")
                                        if (constants.Reels_ViewModel.get_Reels_Data()) {
                                            constants.Profile_ViewModel.set_From_SoldOuts(true)
                                            navController.navigate(ProfileScreenFlow.ReelsView_Search_Flow.route + "/$index")
                                        }

                                    }
                                    .background(newBlue ,
                                        RoundedCornerShape(4.dp)
                                    ),
                                verticalArrangement = Arrangement.SpaceEvenly,
                                horizontalAlignment = Alignment.CenterHorizontally
                            )
                            {

                                SubcomposeAsyncImage(
                                    model = saved_property.value[index].thumbnail,
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
                                                .background(newLightGray)
                                            //.padding(8.dp)
                                            , contentAlignment = Alignment.Center
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.emptypostsrento),
                                                contentDescription = "", modifier = Modifier
                                                    .matchParentSize()
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

    BackHandler {
        ClickHelper.getInstance().clickOnce {
                constants.Profile_ViewModel.onSet_Settings_Click(-1)

        }
    }
}


@Composable
fun My_Interest(notchPadding: State<Dp>) {
    val notchPadding = rememberNotchHeightDp()
    val context = LocalContext.current
    val network = rememberNetworkStatus()
    var retry by remember { mutableStateOf(0) }

    LaunchedEffect(AppPreferences.get_Location_Received()) {
        if (AppPreferences.get_Interest_Completed() == 1 && AppPreferences.get_Location_Received() == 0) {
            constants.Start_Up_ViewModel.setLocationDenied(false)
        }
    }

    val categories = constants.Start_Up_ViewModel.user_Interests.collectAsState()
    val isLoading = constants.API_Vm.isLoading
    val errorMessage = constants.API_Vm.errorMessage
    val currentPage = constants.API_Vm.currentPage
    val totalPages = constants.API_Vm.totalPages
    val location_Api_Loading = constants.Common_H_ViewModel.status.collectAsState()
    val user_Inters_Already = constants.Profile_ViewModel.user_Interest_Partcular

    val listState = rememberLazyListState()

    var initialSelectedIds by remember { mutableStateOf<Set<Int>>(emptySet()) }

    // Track if we've done initial load for this screen instance
    var hasLoadedOnce by remember { mutableStateOf(false) }



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


    if (network.value == NetworkStatus.Online) {
        // Load categories and user interests ONLY if not already loaded
        LaunchedEffect(Unit) {
            if (!hasLoadedOnce) {
                println("Initial load starting...")

                // Load categories first
                constants.API_Vm.loadCategories(1)

                // Get user's selected interests
                get_User_Interest_Particular_API_Call { result ->
                    when (result) {
                        0 -> {
                            println("User interests fetched successfully")
                        }
                        1 -> {
                            println("API call failed")
                        }
                        2 -> {
                            println("No data")
                        }
                    }
                }

                hasLoadedOnce = true
            }
        }

        // Handle retry separately
        LaunchedEffect(retry) {
            if (retry > 0) {
                println("Retrying...")
                constants.API_Vm.loadCategories(1)

                get_User_Interest_Particular_API_Call { result ->
                    when (result) {
                        0 -> println("User interests fetched successfully")
                        1 -> println("API call failed")
                        2 -> println("No data")
                    }
                }
            }
        }

        // Restore interests when both categories and user data are ready
        LaunchedEffect(categories.value, user_Inters_Already.value) {
            if (
                initialSelectedIds.isEmpty() &&
                categories.value.isNotEmpty() &&
                !user_Inters_Already.value.isNullOrEmpty()
            ) {
                val interests = user_Inters_Already.value
                    ?.firstOrNull()
                    ?.user_interest
                    ?.split(",")
                    ?.mapNotNull { it.toIntOrNull() }
                    ?.distinct()
                    ?.toSet()
                    ?: emptySet()

                if (interests.isNotEmpty()) {
                    constants.Start_Up_ViewModel.restoreUserInterests(interests.toList())
                    initialSelectedIds = interests
                }
            }
        }


        // Detect when near end of list for pagination
        LaunchedEffect(listState, currentPage, isLoading, totalPages) {
            snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                .collect { lastVisibleItemIndex ->
                    val totalItems = listState.layoutInfo.totalItemsCount
                    val loadMoreThreshold = 2

                    if (lastVisibleItemIndex != null &&
                        totalItems > 0 &&
                        lastVisibleItemIndex >= totalItems - loadMoreThreshold &&
                        !isLoading &&
                        currentPage < totalPages
                    ) {
                        println("Loading page: ${currentPage + 1}")
                        constants.API_Vm.loadCategories(currentPage + 1)
                    }
                }
        }
    } else {
        GlobalSnackbar.show("It Seems you are offline !! Refresh again")
    }

    // Calculate current selection count from actual state
    val currentSelectedIds = remember(categories.value) {
        categories.value
            .filter { it.is_Selected }
            .map { it.land_categorie_id }
            .toSet()
    }


    // Determine if Save button should be enabled
    val hasChanges = remember(currentSelectedIds, initialSelectedIds) {
        currentSelectedIds.size >= 2 &&
                currentSelectedIds != initialSelectedIds
    }



    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(newWhite)
                .padding(top = if (forTab()) 16.dp else  notchPadding.value)
            , verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(.5f)
                    .padding(horizontal = 16.dp)
                , contentAlignment = Alignment.Center
            )
            {
//                Box(
//                    modifier = Modifier
//                    ,contentAlignment = Alignment.CenterStart
//                ) {
                    Backer(
                        modifier = Modifier.align(Alignment.CenterStart),
                        onBackClick = {
                            constants.Start_Up_ViewModel.clear_Interests()
                            constants.Profile_ViewModel.onSet_Settings_Click(-1)
                        }
                    )
//                }

//                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = "My Interests",
                    color = Color.Black,
                    fontSize = constants.textUnit(20),
                    fontFamily = constants.fontFamily(0),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            var gridtype = if (forTab())3 else 2

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(9.5f)
                    .padding(horizontal = 16.dp ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                when {
                    network.value == NetworkStatus.Offline && categories.value.isEmpty() -> {
                        item {
                            Column(
                                modifier = Modifier.height(800.dp).fillMaxWidth(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(painterResource(R.drawable.nointernerdesign), "")
                                Spacer(modifier = Modifier.padding(16.dp))
                                Text(
                                    "Looks like you're not connected to the internet. Just reconnect and refresh.",
                                    modifier = Modifier.padding(horizontal = 56.dp)
                                )
                            }
                        }
                    }

                    !errorMessage.isNullOrEmpty() -> {
                        item {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                API_Fail_UI(onReTryClick = {
                                    retry = retry + 213435
                                })
                            }
                        }
                    }

                    isLoading && currentPage == 1 && categories.value.isEmpty() -> {
                        item {
                            Box(
                                modifier = Modifier.fillParentMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    !isLoading && categories.value.isEmpty() -> {
                        item {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(painterResource(R.drawable.emptysavedproperties), "")
                                Spacer(modifier = Modifier.padding(16.dp))
                                Text(
                                    "Looks like there is no interests found at now !!",
                                    modifier = Modifier.padding(horizontal = 56.dp)
                                )
                            }
                        }
                    }

                    categories.value.isNotEmpty() -> {

                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {

                                spacer(4)


                                CommonText("Tell us what kind of rental you’re looking for?",
                                    newBlack,
                                    18,
                                    0
                                )

                                spacer(4)


                                CommonText("We’ll suggest rentals based on your preferences.",
                                    Color(0xff575757),
                                    14,
                                    1
                                )
                            }
                        }

                        customGridItems(
                            count = categories.value.size,
                            nColumns = gridtype,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        )
                        { index ->

                            Box(
                                modifier = Modifier
                                    .height(172.dp)
                                    .clip(RoundedCornerShape(4.dp))
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
                                            Modifier.border(1.dp, newBlue, RoundedCornerShape(4.dp))
                                        }
                                        else Modifier .border(1.dp, Color(0xffCECECE), RoundedCornerShape(4.dp))
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

                                    spacer(8)

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

                        }


                        if (isLoading && currentPage > 1) {
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

            // Bottom Button
            Static_Bottom(
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(56.dp)
                        .noRippleClickable(enabled = hasChanges) {
                            if (hasChanges) {
                                Put_User_Interests_API_Call { result ->
                                    when (result) {
                                        0 -> {
                                            AppPreferences.save_Location_Received(1)
                                            GlobalSnackbar.show("Interests Updated Successfully")
                                            // Update initial count after successful save
                                            initialSelectedIds = currentSelectedIds
                                        }
                                        1 -> {
                                            GlobalSnackbar.show("Something went wrong while storing location")
                                        }
                                        2 -> {
                                            constants.Common_H_ViewModel.changeStatus(true)
                                        }
                                    }
                                }
                            }
                        }
                        .background(
                            if (hasChanges) newBlue else newBlue.copy(.4f),
                            RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Save Changes",
                        color = newWhite,
                        fontSize = constants.textUnit(14),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }

    BackHandler {
        ClickHelper.getInstance().clickOnce {
            constants.Profile_ViewModel.onSet_Settings_Click(-1)
        }
    }
}



@Composable
fun Sold_Outs(notchPadding: State<Dp>, navController: NavHostController) {

    val isLoading = constants.API_Vm.isLoading_Profile_SoldOuts
    val totalPages = constants.API_Vm.totalPages_Profile_SoldOuts
    val errorMessage = constants.API_Vm.errorMessage_FF
    val currentPage = constants.API_Vm.currentPage_Profile_SoldOuts

    val listState = rememberLazyListState()

    val soldOuts by constants.Profile_ViewModel.profile_SoldOuts.collectAsState()


    val network = rememberNetworkStatus()
    var retry by remember { mutableStateOf(0) }



    DisposableEffect(Unit ) {

       // if (soldOuts.isEmpty()) {
            println("qwasdzxfgchvjbknm -- ${AppPreferences.getUserId()}")
            constants.API_Vm.load_Profile_SoldOuts(
                user_id = AppPreferences.getUserId()
                    //AppPreferences.getUserId(),
                ,page = 1,
            )
       // }


        onDispose {
            println("Profile_FF_Structure disposed")
        }
    }


    // Detect when near end of list
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .distinctUntilChanged()
            .debounce(300)
            .collect { lastVisibleItemIndex ->
                val totalItems = listState.layoutInfo.totalItemsCount
                val loadMoreThreshold = 3

                if (
                    lastVisibleItemIndex != null &&
                    totalItems > 0 &&
                    lastVisibleItemIndex >= totalItems - loadMoreThreshold
                ) {
                    // Check loading state and page info at this moment
                    if (!constants.API_Vm.isLoading_Profile_SoldOuts &&
                        constants.API_Vm.currentPage_Profile_SoldOuts < constants.API_Vm.totalPages_Profile_SoldOuts
                    ) {
                        constants.API_Vm.load_Profile_SoldOuts(
                            user_id = AppPreferences.getUserId()
                                //AppPreferences.getUserId(),
                            ,page = constants.API_Vm.currentPage_Profile_SoldOuts + 1,
                        )
                    }
                }
            }
    }


      Column(
          modifier = Modifier
              .fillMaxSize()
              .background(newWhite)
              .padding(top = if (forTab()) 16.dp else  notchPadding.value)
          , verticalArrangement = Arrangement.SpaceBetween,
          horizontalAlignment = Alignment.CenterHorizontally
      )
      {
          Box(
              modifier = Modifier
                  .fillMaxWidth()
                  .weight(.5f)
                  //.background(Color.Cyan)
                  .padding(horizontal = 16.dp)
              ,contentAlignment = Alignment.Center
          ) {
              Backer(
                  modifier = Modifier.align(Alignment.CenterStart)
                  //.align(Alignment.CenterHorizontally)
                  //.background(Color.Cyan)
                  , onBackClick = {
                      navController.navigateUp()
                      constants.Profile_ViewModel.onSet_Settings_Click(-1)
                      constants.Profile_ViewModel.from_SoldOuts.value = false
                      //navController.navigate(UserCredentialsScreenFlow.UserCredentials.route)
                  }
              )

              Spacer(modifier = Modifier.padding(8.dp))

              Text(
                  text = "RentedOut",
                  color = Color.Black,
                  fontSize = constants.textUnit(20),
                  fontFamily = constants.fontFamily(0),
                  //  lineHeight = 36.sp,
                  modifier = Modifier.align(Alignment.Center)
                  //.padding(vertical = 8.dp)
                  // .align(Alignment.Start) // Align title to the start
              )
          }
         /* Row (
              modifier = Modifier
                  .fillMaxWidth()
                  .weight(.5f)
                  //.background(Color.Cyan)
                  .padding(horizontal = 16.dp)
              , verticalAlignment = Alignment.CenterVertically
              , horizontalArrangement = Arrangement.Start
              // .background(Color.Cyan)
          )
          {
              // Header
                  Backer(
                      modifier = Modifier
                          //.align(Alignment.CenterHorizontally)
                          //.background(Color.Cyan)
                      , onBackClick = {
                          navController.navigateUp()
                          constants.Profile_ViewModel.onSet_Settings_Click(-1)
                          constants.Profile_ViewModel.from_SoldOuts.value = false
                          //navController.navigate(UserCredentialsScreenFlow.UserCredentials.route)
                      }
                  )

              Spacer(modifier = Modifier.padding(8.dp))

              Text(
                  text = "RentedOut",
                  color = Color.Black,
                  fontSize = constants.textUnit(20),
                  fontFamily = constants.fontFamily(0),
                  //  lineHeight = 36.sp,
                  modifier = Modifier.align(Alignment.CenterVertically)
                  //.padding(vertical = 8.dp)
                  // .align(Alignment.Start) // Align title to the start
              )
          }*/



//          if (isLoading && currentPage == 1){
//              Box(
//                  modifier = Modifier
//                      .fillMaxSize()
//                  , contentAlignment = Alignment.Center
//              ){
//                  CircularProgressIndicator()
//              }
//          }
//          else {
//              if (soldOuts.isEmpty()){
//
//              }
//              else {

              // Show Interest Selection UI
          
          var gridtype = if (forTab()) 3 else 2
              LazyColumn(
                  state = listState,
                  modifier = Modifier
                      .fillMaxWidth()
                      .weight(9.5f)
                      .padding(horizontal = 16.dp , vertical = 16.dp),
                  verticalArrangement = Arrangement.spacedBy(16.dp) // spacing between items
              )
              {

                  when {

                      network.value == NetworkStatus.Offline && soldOuts.isEmpty() -> {
                          /// constants.activity.getString(R.string.no_Internet) 
                          //GlobalSnackbar.show("It Seems your are offline !!.Refresh again")
                          item {
                              Column(
                                  modifier = Modifier
                                      .height(800.dp)
                                      .fillMaxWidth()
                                  , verticalArrangement = Arrangement.Center,
                                  horizontalAlignment = Alignment.CenterHorizontally
                              ) {
                                  Image(painterResource(R.drawable.nointernerdesign), "")

                                  Spacer(modifier = Modifier.padding(16.dp))

                                  Text(
                                      constants.activity.getString(R.string.no_Internet),
                                      modifier = Modifier
                                          .padding(horizontal = 56.dp)
                                  )
                              }
                          }
                      }

                      !errorMessage.isNullOrEmpty() -> {
                          item {
                              Box(
                                  modifier = Modifier
                                      .height(800.dp)
                                      .fillMaxWidth()
//                                      .fillMaxSize()
                                  , contentAlignment = Alignment.Center
                              ) {

                                  API_Fail_UI(onReTryClick = {
                                      retry = retry + 213435
                                      constants.API_Vm.errorMessage_FF = ""

                                  })
                              }
                          }
                      }


                      isLoading && currentPage == 1 && soldOuts.isEmpty() -> {
                          // ✅ show fullscreen loader if first page is loading
                          item {
                              Box(
                                  modifier = Modifier.fillParentMaxSize(),
                                  contentAlignment = Alignment.Center
                              ) {
                                  CircularProgressIndicator()
                              }
                          }
                      }


                      !isLoading && soldOuts.isEmpty() -> {
                          // no data found
                          item {
                              Column(
                                  modifier = Modifier
                                      //.background(Color.Red)
                                      .fillMaxWidth()
                                      .height(800.dp)
                                  , verticalArrangement = Arrangement.Center
                                  , horizontalAlignment = Alignment.CenterHorizontally
                              )
                              {
                                  Image(painterResource(R.drawable.emptyrentoutrento) , "")

                                  Text("No land marked as RentedOut yet!"
                                      , color = newBlack
                                      , fontSize = constants.textUnit(16)
                                      , fontFamily = constants.fontFamily(1)
                                  )

                                  Text("Once you mark a post as RentedOut, it will appear here."
                                      , color = Color(0xff666666)
                                      , fontSize = constants.textUnit(12)
                                      , fontFamily = constants.fontFamily(2)
                                  )
                              }
                          }
                      }


                      soldOuts.isNotEmpty() -> {
                          customGridItems(
                              count = soldOuts.size,
                              nColumns = gridtype,
                              horizontalArrangement = Arrangement.spacedBy(16.dp)
                          )
                          { index ->

                              Box(
                                  modifier = Modifier
                                      .clip(RoundedCornerShape(6.dp))
                                      .padding(bottom = 16.dp)
                                      .aspectRatio(0.94f) // Keeps consistent card size (approx 162/172)
                                      .border(1.dp, color = newGray, RoundedCornerShape(4.dp))
                              ) {
                                  Column(
                                      modifier = Modifier
                                          .fillMaxSize()
                                          .noRippleClickable {
                                              ClickHelper.getInstance().clickOnce {

                                                  constants.Reels_ViewModel.clear_view_pro_Details()
                                                  //constants.Start_Up_ViewModel.toggleInterestSelection(index)
//                                          val videoJson = Uri.encode(Json.encodeToString(soldOuts[index]))
//                                          navController.navigate("${ProfileScreenFlow.SingleVideoPlayerEnquiry.route}/$videoJson")

                                                  // When clicking on an item
                                                  // constants.Profile_ViewModel.selectVideo(soldOuts[index].toPostUser())
                                                  //  constants.Profile_ViewModel.set_From_SoldOuts(true)
                                                  // navController.navigate(ProfileScreenFlow.SingleVideoPlayerEnquiry2.route)

                                                  AppPreferences.save_Post_Id(soldOuts[index].user_post_id)
                                                  constants.Search_ViewModel.select_RS_Reels_Flow(
                                                      index
                                                  )
                                                  constants.Reels_ViewModel.setReelsContent(
                                                      soldOuts
                                                      //.map { it.toReelsData() }  // map each element to Get_Reels_Data
                                                  )
//                                        constants.Reels_ViewModel.add_View_Property_Details(posts.value.toReelsData())
//                                        val data = constants.Search_ViewModel.get_Search_Results()
//                                        println("SIZE OF DATA MAPPED TO SEARCH REELS FLOW DATA HOLDER -- ${data.size}  *** ${data.map { it.video }}")
                                                  if (constants.Reels_ViewModel.get_Reels_Data()) {
                                                      constants.Profile_ViewModel.set_From_SoldOuts(
                                                          true
                                                      )

                                                      constants.PostProperty_ViewModel.set_Post_Form_Flow(
                                                          -1
                                                      )
                                                      constants.Profile_ViewModel.set_From_Repost(
                                                          0
                                                      )
                                                      constants.PostProperty_ViewModel.goToPPFormPage(
                                                          0,
                                                          7
                                                      )
                                                      constants.Profile_ViewModel.from_SoldOuts.value =
                                                          true

                                                      constants.PostProperty_ViewModel.setViewDetailsFlow(
                                                          ViewDetailsFlow.RENTOUT
                                                      )

                                                      navController.navigate(ProfileScreenFlow.ReelsView_Search_Flow.route + "/$index")
                                                  }

                                              }
                                          }
                                          .background(newWhite ,
                                              RoundedCornerShape(6.dp)
                                          ),
                                      verticalArrangement = Arrangement.SpaceEvenly,
                                      horizontalAlignment = Alignment.CenterHorizontally
                                  ) {

                                      SubcomposeAsyncImage(
                                          model = soldOuts[index].thumbnail,
                                          modifier = Modifier
                                              .fillMaxSize()
                                          , contentDescription = ""
                                          , contentScale = ContentScale.FillBounds
                                          , colorFilter = ColorFilter.colorMatrix(grayscaleMatrix)
                                      )
                                      {
                                          val state = painter.state
                                          if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                                              Box(
                                                  modifier = Modifier
                                                      .fillMaxSize()
                                                      .background(newLightGray)
                                                  //.padding(8.dp)
                                                  , contentAlignment = Alignment.Center
                                              ) {
                                                  Image(
                                                      painter = painterResource(id = R.drawable.emptypostsrento),
                                                      contentDescription = ""
                                                      , modifier = Modifier
                                                          .matchParentSize()
                                                      , colorFilter = ColorFilter.colorMatrix(grayscaleMatrix)
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

                      isLoading && currentPage != 1 && soldOuts.isNotEmpty() ->{

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
//              }
//          }
      }



    BackHandler {
        ClickHelper.getInstance().clickOnce {
            constants.Profile_ViewModel.onSet_Settings_Click(-1)

        }
    }
}



@Composable
fun FAQ(notchPadding: State<Dp>) {

    val faq_Details = constants.Profile_ViewModel.faq_Details.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(newWhite)
            .padding(top = if (forTab()) 16.dp else  notchPadding.value),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
            , verticalAlignment = Alignment.CenterVertically
            , horizontalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            Backer(
                modifier = Modifier
                , onBackClick = {
                   // if (selected_AS_Settings.value == "Account Settings") {
                        constants.Profile_ViewModel.onSet_Settings_Click(-1)
//                    }
//                    else {
//                        constants.Profile_ViewModel.setSelected_AS_Settings("Account Settings")
//                        constants.Profile_ViewModel.onSet_Settings_Click(0)
//                    }
                }
            )

            Text(
                "FAQ" ,
                fontSize = constants.textUnit(20),
                fontFamily = constants.fontFamily(0)
            )
        }


        faq_Details.value.forEachIndexed { index, item ->
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .border(1.dp, newGray, RoundedCornerShape(8.dp))
                    .noRippleClickable{
                        constants.Profile_ViewModel.enable_faq(item.id) // Or pass index
                    }
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Box(
                        modifier = Modifier
                            .weight(9f)
                        , contentAlignment = Alignment.Center
                    ){
                        Text(
                            item.title,
                            fontSize = constants.textUnit(16),
                            fontFamily = constants.fontFamily(2)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                        , contentAlignment = Alignment.Center
                    ){
                        AsyncImage(
                            model = R.drawable.arrowdown,
                            contentDescription = "",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                AnimatedVisibility(
                    visible = item.isSelected,
                    enter = slideInVertically(initialOffsetY = { -it }),
                    //exit = slideOutVertically(targetOffsetY = { -it }),
                ) {
                    Text(
                        text = item.desc,
                        fontSize = constants.textUnit(12),
                        fontFamily = constants.fontFamily(3),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }

    BackHandler {
        ClickHelper.getInstance().clickOnce {
            constants.Profile_ViewModel.onSet_Settings_Click(-1)

        }
    }
}



@OptIn(FlowPreview::class)
@Composable
fun Drafts(notchPadding: State<Dp>, navController: NavHostController) {

    val isLoading = constants.API_Vm.isLoading_Profile_Drafts
    val totalPages = constants.API_Vm.totalPages_Profile_Drafts

    val currentPage = constants.API_Vm.currentPage_Profile_Drafts
    val listState = rememberLazyListState()

    val drafts by constants.Profile_ViewModel.profile_Drafts.collectAsState()

    var onDelete_Tap by remember { mutableStateOf(false) }
    var onSelectAll_Tap by remember { mutableStateOf(false) }
    var delete_Confirmation by remember { mutableStateOf(false) }

    // Track selected items by their post IDs
    var selectedPostIds by remember { mutableStateOf<Set<Int>>(emptySet()) }

    var network = rememberNetworkStatus()

    // Update select all state based on individual selections
    LaunchedEffect(selectedPostIds, drafts.size) {
        if (drafts.isNotEmpty()) {
            onSelectAll_Tap = selectedPostIds.size == drafts.size
        }
    }

    // Function to get the selection data for API
    fun getSelectionData(): Pair<String, Int> {
        return if (onSelectAll_Tap) {
            // All selected: return all post IDs and 1
            val allPostIds = drafts.mapNotNull { it.post_property?.user_post_id }.joinToString(",")
            Pair(allPostIds, 1)
        } else {
            // Individual selection: return comma-separated IDs and 0
            val postIds = selectedPostIds.joinToString(",")
            Pair(postIds, 0)
        }
    }

    DisposableEffect(Unit) {
       // if (drafts.isEmpty()) {
            println("qwasdzxfgchvjbknm -- ${AppPreferences.getUserId()}")
            constants.API_Vm.load_Profile_Drafts(
                user_id = AppPreferences.getUserId(),
                page = 1,
            )
       // }

        onDispose {
            println("Profile_FF_Structure disposed")
        }
    }

    // Detect when near end of list
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .distinctUntilChanged()
            .debounce(300)
            .collect { lastVisibleItemIndex ->
                val totalItems = listState.layoutInfo.totalItemsCount
                val loadMoreThreshold = 3

                if (
                    lastVisibleItemIndex != null &&
                    totalItems > 0 &&
                    lastVisibleItemIndex >= totalItems - loadMoreThreshold
                ) {
                    if (!constants.API_Vm.isLoading_Profile_Drafts &&
                        constants.API_Vm.currentPage_Profile_Drafts < constants.API_Vm.totalPages_Profile_Drafts
                    ) {
                        constants.API_Vm.load_Profile_Drafts(
                            user_id = AppPreferences.getUserId(),
                            page = constants.API_Vm.currentPage_Profile_Drafts + 1,
                        )
                    }
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(newWhite)
            .padding(top = if (forTab()) 16.dp else  notchPadding.value)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Row(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .align(Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Backer(
                    modifier = Modifier.align(Alignment.CenterStart)
                       // .background(Color.Cyan)
                    , onBackClick = {
                        constants.Profile_ViewModel.onSet_Settings_Click(-1)
                    }
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = "Drafts",
                    color = Color.Black,
                    fontSize = constants.textUnit(20),
                    fontFamily = constants.fontFamily(0),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            if (drafts.isNotEmpty()) {
                AnimatedContent(targetState = onDelete_Tap) { state ->
                    if (!state) {
                        Image(
                            painter = painterResource(R.drawable.reelsdelete), "",
                            colorFilter = ColorFilter.tint(newBlack),
                            modifier = Modifier.noRippleClickable{
                                onDelete_Tap = true
                            }
                        )
                    } else {
                        Row(
                            modifier = Modifier
                                .wrapContentWidth()
                                .background(Color.White, RoundedCornerShape(4.dp))
                               // .border(1.dp, Color(0xffE8E8E8), RoundedCornerShape(4.dp)),
                            ,horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = onSelectAll_Tap,
                                onCheckedChange = { isChecked ->
                                    onSelectAll_Tap = isChecked
                                    if (isChecked) {
                                        // Select all items
                                        selectedPostIds = drafts.mapNotNull {
                                            it.post_property?.user_post_id
                                        }.toSet()
                                    } else {
                                        // Deselect all items
                                        selectedPostIds = emptySet()
                                    }
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = newBlue,
                                    checkmarkColor = Color.White,
                                    uncheckedColor = Color(0xffB8B8B8),
                                    disabledUncheckedColor = Color(0xffB8B8B8)
                                )
                            )

                            Text("Select All")

                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                }
            }
        }

        if (network.value == NetworkStatus.Online) {
            if (isLoading && currentPage == 1) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                if (drafts.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(painterResource(R.drawable.emptydraftsrento), "",
                            modifier = Modifier.size(100.dp))

                        Text(
                            "No drafts saved!",
                            color = newBlack,
                            fontSize = constants.textUnit(16),
                            fontFamily = constants.fontFamily(1)
                        )

                    }
                } else {
                    Column {
                        LazyColumn(
                            modifier = Modifier
                                .weight(9f)
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            state = listState
                        ) {
                            itemsIndexed(drafts) { index, item ->
                                val postId = item.post_property?.user_post_id ?: 0
                                val isSelected = selectedPostIds.contains(postId)



                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(1.dp, Color(0xffB8B8B8), RoundedCornerShape(8.dp))
                                        .noRippleClickable {
                                            if (!onDelete_Tap) {
                                                constants.PostProperty_ViewModel.clear_Selected_Fields_Form4()

                                                constants.PostProperty_ViewModel.save_Changes_Draft.value =
                                                    -1
                                                constants.PostProperty_ViewModel.put_budget_Price_PF5(
                                                    ""
                                                )
                                                constants.PostProperty_ViewModel.check_Price_Negotiation(
                                                    false
                                                )
                                                constants.PostProperty_ViewModel.add_pp3_Data(
                                                    PP3_API_DC(
                                                        pincode = "",
                                                        country = "",
                                                        state = "",
                                                        city = "",
                                                        locality = ""
                                                    )
                                                )
                                                constants.PostProperty_ViewModel.add_Pinned_Lat_Long(
                                                    LatLng(
                                                        0.0,
                                                        0.0
                                                    )
                                                )



                                                constants.PostProperty_ViewModel.clear_Budget_PF5()
                                                constants.PostProperty_ViewModel.clear_Media()
//                                    constants.PostProperty_ViewModel.pinned_Lat_Long.value?.longitude = 0.0


                                                constants.PostProperty_ViewModel.select_Land_Type(1)
                                                constants.PostProperty_ViewModel.select_Land_Cat_Id(
                                                    -1
                                                )
                                                constants.PostProperty_ViewModel.select_User_Type_1PF(
                                                    -1
                                                )



                                                constants.URL_COMPLETED.clear()


                                                val server_Data = drafts[index].post_property

                                                server_Data?.let { data ->

                                                    // Update Selected Fields for Form4 if available
                                                    constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                                        data.toSelectedOptionsForm4_D()
                                                    }

                                                    // Log data
                                                    println("FORM $ DATA -- ${data.carpet_area} $$ ${data.area_width} $$ ${data.area_length} -- ${data.total_floor}")

                                                    // Save Post ID
                                                    if (!data.user_post_id.toString()
                                                            .isNullOrEmpty()
                                                    ) {
                                                        AppPreferences.save_Post_Id(
                                                            data.user_post_id ?: 0
                                                        )
                                                        println("POST ID -- ${AppPreferences.get_Post_Id()} -- ${data.user_post_id}")
                                                    }

                                                    println("LOCARTION DERAIKS sfjngv adsfbvkhjd bjekjdnm ${data.pincode} -__${data.longitude}--${data.latitude}--${data.address}--${data.city}- ${data.country} -- ${data.state}")

                                                    // Safely set values if they’re not empty
                                                    if (!data.pincode.isNullOrEmpty()) {
                                                        constants.PostProperty_ViewModel.set_pincode3(
                                                            data.pincode
                                                        )
                                                    }
                                                    if (!data.country.isNullOrEmpty()) {
                                                        constants.PostProperty_ViewModel.set_country3(
                                                            data.country
                                                        )
                                                    }
                                                    if (!data.state.isNullOrEmpty()) {
                                                        constants.PostProperty_ViewModel.set_state3(
                                                            data.state
                                                        )
                                                    }
                                                    if (!data.city.isNullOrEmpty()) {
                                                        constants.PostProperty_ViewModel.set_city3(
                                                            data.city
                                                        )
                                                    }
                                                    if (!data.address.isNullOrEmpty()) {
                                                        constants.PostProperty_ViewModel.set__selectedLocality3(
                                                            data.address
                                                        )
                                                    }
//
                                                    if (data.latitude.isNotEmpty() && data.longitude.isNotEmpty()) {
                                                        constants.PostProperty_ViewModel.add_Pinned_Lat_Long(
                                                            LatLng(
                                                                data.latitude.toDouble(),
                                                                data.longitude.toDouble()
                                                            )
                                                        )
                                                    }
                                                    constants.PostProperty_ViewModel.add_pp3_Data(
                                                        PP3_API_DC(
                                                            pincode = data.pincode ?:"",
                                                            country = data.country,
                                                            state = data.state,
                                                            city = data.city,
                                                            locality = data.locality
                                                        )
                                                    )

                                                    // Set LatLng if valid
                                                    if (!data.latitude.isNullOrEmpty() && !data.longitude.isNullOrEmpty()) {
                                                        val latLng = LatLng(
                                                            data.latitude.toDoubleOrNull() ?: 0.0,
                                                            data.longitude.toDoubleOrNull() ?: 0.0
                                                        )
                                                        constants.PostProperty_ViewModel.set_latLng3(
                                                            latLng
                                                        )
                                                    }

                                                    println(" WHOOOOOOOOO-- ${data.land_type_id} --- ${data.land_categorie_id}")
                                                    // Land type & category
                                                    if (data.land_type_id > 0) {
                                                        constants.PostProperty_ViewModel.first_Form_selected_PP(
                                                            data.land_type_id
                                                        )
                                                        constants.PostProperty_ViewModel.select_User_Type_1PF(
                                                            data.land_type_id
                                                        )
                                                        constants.PostProperty_ViewModel.set_onSelected_ProType(
                                                            data.land_type_id
                                                        )
                                                        constants.PostProperty_ViewModel.select_Land_Type(
                                                            data.land_type_id
                                                        )
                                                        constants.PostProperty_ViewModel.pp_SecondForm_Residential_Select_Option(
                                                            data.land_categorie_id
                                                        )
                                                        constants.PostProperty_ViewModel.select_Land_Cat_Id(
                                                            data.land_categorie_id
                                                        )
                                                    }
                                                    println("LAND CAT ID -- ${data.land_categorie_id}")
                                                    if (data.land_categorie_id > 0) {
                                                        constants.PostProperty_ViewModel.pp_SecondForm_Residential_Select_Option(
                                                            data.land_categorie_id
                                                        )
                                                        constants.PostProperty_ViewModel.LandSubType_Selected_Click(
                                                            data.land_categorie_id
                                                        )
                                                        constants.PostProperty_ViewModel.select_Land_Cat_Id(
                                                            data.land_categorie_id
                                                        )

                                                    }

                                                    // Budget / Price
//                                                    if (!data.price.isNullOrEmpty()) {
//                                                        constants.PostProperty_ViewModel.put_budget_Price_PF5(
//                                                            data.price
//                                                        )
//                                                    }


//                                                    constants.PostProperty_ViewModel.check_Price_Negotiation(
//                                                        data.price_negotiable == "1"
//                                                    )

                                                    println("SERVER DATA -DRAFT NO ${data}__-  -${ constants.PostProperty_ViewModel.budget_Price_PF5.value}--- cons.")

                                                    // ✅ Navigate only at the end, after applying values
                                                    constants.PostProperty_ViewModel.goToPPFormPage(
                                                        page = (item.draft),
                                                        maxPages = 7,
                                                    )
                                                    // ✅ Navigate only at the end, after applying values
//

                                                    if (data.images.isNotEmpty()) {
                                                        println("SERVERIMges__  -${ constants.PostProperty_ViewModel.budget_Price_PF5.value}--- cons.")

//                                                        val imageMediaList =
//                                                            data.images.map { imageUri ->
//                                                                UploadPropertyMedia(
//                                                                    uri = Uri.parse(imageUri),
//                                                                    isVideo = false
//                                                                )
//                                                            }
//                                                        constants.PostProperty_ViewModel.addImages(
//                                                            imageMediaList
//                                                        )
                                                    } else if (data.video.isNotEmpty()) {
                                                        println("SERVER Video__-  -${ constants.PostProperty_ViewModel.budget_Price_PF5.value}--- cons.")

//                                                        val videoMedia = UploadPropertyMedia(
//                                                            uri = Uri.parse(data.video),
//                                                            isVideo = true
//                                                        )
//                                                        constants.PostProperty_ViewModel.addVideo(
//                                                            videoMedia
//                                                        )
                                                    }


                                                    println("DRAFT IPOST IDD -- ${item.post_property.user_post_id}")

                                                    constants.PostProperty_ViewModel.set_Post_Form_Flow(
                                                        1
                                                    )

                                                    constants.PostProperty_ViewModel.select_Land_Cat_Id(data.land_categorie_id)

                                                    constants.PostProperty_ViewModel.setPostFlow(PostFlow.DRAFT)

                                                    AppPreferences.save_Post_Id(data.user_post_id)

                                                    navController.navigate(ProfileScreenFlow.Post_Property_Forms.route)
                                                }
                                            }

                                        }
                                    , verticalAlignment = Alignment.CenterVertically
                                    , horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxHeight().weight(2f)
                                        , contentAlignment = Alignment.Center
                                    ) {
                                        StepCircularProgress(
                                            totalSteps = 7,
                                            currentStep = item.draft,
                                            modifier = Modifier.align(Alignment.Center)
                                                .size(50.dp),
                                            strokeWidth = 6.dp,

//                                            completedColor = newBlue,
//                                            remainingGradient = listOf(newLightBlue  ,newLightBlue)
                                        )
                                    }
                                    constants.spacer(4)

                                    Column(
                                        modifier = Modifier.fillMaxHeight().weight(8f)
                                        , horizontalAlignment = Alignment.Start
                                        , verticalArrangement = Arrangement.SpaceEvenly
                                    )
                                    {
                                        Text(
                                            "Draft ${item.draft}",
                                            color = Color(0xff7E7E7E),
                                            fontSize = constants.textUnit(12),
                                            fontFamily = constants.fontFamily(1),
                                            modifier = Modifier
                                            //.padding(vertical = 8.dp)
                                        )

                                        constants.spacer(4)

                                        if (item.draft < 3) {
                                            Text(
                                                "Keep going to post your property.",
                                                color = Color(0xff666666),
                                                fontSize = constants.textUnit(12),
                                                fontFamily = constants.fontFamily(3),
                                                modifier = Modifier
                                                //.padding(vertical = 16.dp)
                                            )
                                        }
                                        else if (item.draft >= 3 && item.draft < 7) {
                                            Text(
                                                "You're nearly done! ${7 - item.draft} more steps to post.",
                                                color = Color(0xff666666),
                                                fontSize = constants.textUnit(12),
                                                fontFamily = constants.fontFamily(3),
                                                modifier = Modifier//.padding(vertical = 16.dp)
                                            )
                                        }
                                        else {
                                            Text(
                                                "Everything's set! Just hit 'Post' to publish property. ",
                                                color = Color(0xff666666),
                                                fontSize = constants.textUnit(12),
                                                fontFamily = constants.fontFamily(3),
                                                modifier = Modifier//.padding(vertical = 16.dp)
                                            )
                                        }
                                    }

                                    Box(
                                        modifier = Modifier.fillMaxHeight().weight(1f)
                                        , contentAlignment = Alignment.Center
                                    ) {
                                        AnimatedContent(targetState = onDelete_Tap) { state ->
                                            if (!state) {
                                                Image(
                                                    painter = painterResource(R.drawable.right_arrow),
                                                    "",
                                                    modifier = Modifier.noRippleClickable {
                                                        // Navigate to edit draft
                                                    }
                                                )
                                            } else {
                                                Checkbox(
                                                    checked = isSelected,
                                                    onCheckedChange = { isChecked ->
                                                        selectedPostIds = if (isChecked) {
                                                            selectedPostIds + postId
                                                        } else {
                                                            selectedPostIds - postId
                                                        }
                                                    },
                                                    colors = CheckboxDefaults.colors(
                                                        checkedColor = newBlue,
                                                        checkmarkColor = Color.White,
                                                        uncheckedColor = Color(0xffB8B8B8),
                                                        disabledUncheckedColor = Color(0xffB8B8B8)
                                                    )
                                                )
                                            }
                                        }
                                    }
                                }

                                /*ListItem(
                                    supportingContent = {
                                        if (item.draft < 3) {
                                            Text(
                                                "Keep going to post your property.",
                                                color = Color(0xff666666),
                                                fontSize = constants.textUnit(12),
                                                fontFamily = constants.fontFamily(3),
                                                modifier = Modifier
                                                    //.padding(vertical = 16.dp)
                                            )
                                        }
                                        else if (item.draft >= 3 && item.draft < 7) {
                                            Text(
                                                "You're nearly done! ${7 - item.draft} more steps to post.",
                                                color = Color(0xff666666),
                                                fontSize = constants.textUnit(12),
                                                fontFamily = constants.fontFamily(3),
                                                modifier = Modifier//.padding(vertical = 16.dp)
                                            )
                                        }
                                        else {
                                            Text(
                                                "Everything's set! Just hit 'Post' to publish property. ",
                                                color = Color(0xff666666),
                                                fontSize = constants.textUnit(12),
                                                fontFamily = constants.fontFamily(3),
                                                modifier = Modifier//.padding(vertical = 16.dp)
                                            )
                                        }
                                    },
                                    headlineContent = {
                                        Text(
                                            "Draft ${item.draft}",
                                            color = Color(0xff7E7E7E),
                                            fontSize = constants.textUnit(12),
                                            fontFamily = constants.fontFamily(1),
                                            modifier = Modifier
                                                //.padding(vertical = 8.dp)
                                        )
                                    },
                                    leadingContent = {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                , contentAlignment = Alignment.Center
                                        ) {
                                            StepCircularProgress(
                                                totalSteps = 7,
                                                currentStep = item.draft,
                                                modifier = Modifier.align(Alignment.Center)
                                                    .size(50.dp),
                                                strokeWidth = 6.dp,

//                                            completedColor = newBlue,
//                                            remainingGradient = listOf(newLightBlue  ,newLightBlue)
                                            )
                                        }
                                    },
                                    trailingContent = {

                                        AnimatedContent(targetState = onDelete_Tap) { state ->
                                            if (!state) {
                                                Image(
                                                    painter = painterResource(R.drawable.right_arrow),
                                                    "",
                                                    modifier = Modifier.noRippleClickable {
                                                        // Navigate to edit draft
                                                    }
                                                )
                                            } else {
                                                Checkbox(
                                                    checked = isSelected,
                                                    onCheckedChange = { isChecked ->
                                                        selectedPostIds = if (isChecked) {
                                                            selectedPostIds + postId
                                                        } else {
                                                            selectedPostIds - postId
                                                        }
                                                    },
                                                    colors = CheckboxDefaults.colors(
                                                        checkedColor = newBlue,
                                                        checkmarkColor = Color.White,
                                                        uncheckedColor = Color(0xffB8B8B8),
                                                        disabledUncheckedColor = Color(0xffB8B8B8)
                                                    )
                                                )
                                            }
                                        }
                                    },
                                    colors = ListItemDefaults.colors(
                                        containerColor = Color.White,
                                    ),
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(1.dp, Color(0xffB8B8B8), RoundedCornerShape(8.dp))
                                        .noRippleClickable {
                                            if (!onDelete_Tap) {
                                                constants.PostProperty_ViewModel.clear_Selected_Fields_Form4()

                                                constants.PostProperty_ViewModel.save_Changes_Draft.value =
                                                    -1
                                                constants.PostProperty_ViewModel.put_budget_Price_PF5(
                                                    ""
                                                )
                                                constants.PostProperty_ViewModel.check_Price_Negotiation(
                                                    false
                                                )
                                                constants.PostProperty_ViewModel.add_pp3_Data(
                                                    PP3_API_DC(
                                                        pincode = "",
                                                        country = "",
                                                        state = "",
                                                        city = "",
                                                        locality = ""
                                                    )
                                                )
                                                constants.PostProperty_ViewModel.add_Pinned_Lat_Long(
                                                    LatLng(
                                                        0.0,
                                                        0.0
                                                    )
                                                )



                                     constants.PostProperty_ViewModel.clear_Budget_PF5()
                                     constants.PostProperty_ViewModel.clear_Media()
//                                    constants.PostProperty_ViewModel.pinned_Lat_Long.value?.longitude = 0.0


                                                constants.PostProperty_ViewModel.select_Land_Type(1)
                                                constants.PostProperty_ViewModel.select_Land_Cat_Id(
                                                    -1
                                                )
                                                constants.PostProperty_ViewModel.select_User_Type_1PF(
                                                    -1
                                                )



                                                constants.URL_COMPLETED.clear()


                                                val server_Data = drafts[index].post_property

                                                server_Data?.let { data ->

                                                    // Update Selected Fields for Form4 if available
                                                    constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                                        data.toSelectedOptionsForm4_D()
                                                    }

                                                    // Log data
                                                    println("FORM $ DATA -- ${data.carpet_area} $$ ${data.area_width} $$ ${data.area_length} -- ${data.total_floor}")

                                                    // Save Post ID
                                                    if (!data.user_post_id.toString()
                                                            .isNullOrEmpty()
                                                    ) {
                                                        AppPreferences.save_Post_Id(
                                                            data.user_post_id ?: 0
                                                        )
                                                        println("POST ID -- ${AppPreferences.get_Post_Id()} -- ${data.user_post_id}")
                                                    }

                                                    println("LOCARTION DERAIKS sfjngv adsfbvkhjd bjekjdnm ${data.pincode} -__${data.longitude}--${data.latitude}--${data.address}--${data.city}- ${data.country} -- ${data.state}")

                                                    // Safely set values if they’re not empty
                                                    if (!data.pincode.isNullOrEmpty()) {
                                                        constants.PostProperty_ViewModel.set_pincode3(
                                                            data.pincode
                                                        )
                                                    }
                                                    if (!data.country.isNullOrEmpty()) {
                                                        constants.PostProperty_ViewModel.set_country3(
                                                            data.country
                                                        )
                                                    }
                                                    if (!data.state.isNullOrEmpty()) {
                                                        constants.PostProperty_ViewModel.set_state3(
                                                            data.state
                                                        )
                                                    }
                                                    if (!data.city.isNullOrEmpty()) {
                                                        constants.PostProperty_ViewModel.set_city3(
                                                            data.city
                                                        )
                                                    }
                                                    if (!data.address.isNullOrEmpty()) {
                                                        constants.PostProperty_ViewModel.set__selectedLocality3(
                                                            data.address
                                                        )
                                                    }
//
                                                    if (data.latitude.isNotEmpty() && data.longitude.isNotEmpty()) {
                                                        constants.PostProperty_ViewModel.add_Pinned_Lat_Long(
                                                            LatLng(
                                                                data.latitude.toDouble(),
                                                                data.longitude.toDouble()
                                                            )
                                                        )
                                                    }
                                                    constants.PostProperty_ViewModel.add_pp3_Data(
                                                        PP3_API_DC(
                                                            pincode = data.pincode ?:"",
                                                            country = data.country,
                                                            state = data.state,
                                                            city = data.city,
                                                            locality = data.locality
                                                        )
                                                    )

                                                    // Set LatLng if valid
                                                    if (!data.latitude.isNullOrEmpty() && !data.longitude.isNullOrEmpty()) {
                                                        val latLng = LatLng(
                                                            data.latitude.toDoubleOrNull() ?: 0.0,
                                                            data.longitude.toDoubleOrNull() ?: 0.0
                                                        )
                                                        constants.PostProperty_ViewModel.set_latLng3(
                                                            latLng
                                                        )
                                                    }

                                                    println(" WHOOOOOOOOO-- ${data.land_type_id} --- ${data.land_categorie_id}")
                                                    // Land type & category
                                                    if (data.land_type_id > 0) {
                                                        constants.PostProperty_ViewModel.first_Form_selected_PP(
                                                            data.land_type_id
                                                        )
                                                        constants.PostProperty_ViewModel.select_User_Type_1PF(
                                                            data.land_type_id
                                                        )
                                                        constants.PostProperty_ViewModel.set_onSelected_ProType(
                                                            data.land_type_id
                                                        )
                                                        constants.PostProperty_ViewModel.select_Land_Type(
                                                            data.land_type_id
                                                        )
                                                        constants.PostProperty_ViewModel.pp_SecondForm_Residential_Select_Option(
                                                            data.land_categorie_id
                                                        )
                                                        constants.PostProperty_ViewModel.select_Land_Cat_Id(
                                                            data.land_categorie_id
                                                        )
                                                    }
                                                    println("LAND CAT ID -- ${data.land_categorie_id}")
                                                    if (data.land_categorie_id > 0) {
                                                        constants.PostProperty_ViewModel.pp_SecondForm_Residential_Select_Option(
                                                            data.land_categorie_id
                                                        )
                                                        constants.PostProperty_ViewModel.LandSubType_Selected_Click(
                                                            data.land_categorie_id
                                                        )
                                                        constants.PostProperty_ViewModel.select_Land_Cat_Id(
                                                            data.land_categorie_id
                                                        )

                                                    }

                                                    // Budget / Price
//                                                    if (!data.price.isNullOrEmpty()) {
//                                                        constants.PostProperty_ViewModel.put_budget_Price_PF5(
//                                                            data.price
//                                                        )
//                                                    }


//                                                    constants.PostProperty_ViewModel.check_Price_Negotiation(
//                                                        data.price_negotiable == "1"
//                                                    )

                                                    println("SERVER DATA -DRAFT NO ${data}__-  -${ constants.PostProperty_ViewModel.budget_Price_PF5.value}--- cons.")

                                                    // ✅ Navigate only at the end, after applying values
                                                    constants.PostProperty_ViewModel.goToPPFormPage(
                                                        page = (item.draft - 1),
                                                        maxPages = 7,
                                                    )
                                                    // ✅ Navigate only at the end, after applying values
//

                                                    if (data.images.isNotEmpty()) {
                                                        println("SERVERIMges__  -${ constants.PostProperty_ViewModel.budget_Price_PF5.value}--- cons.")

//                                                        val imageMediaList =
//                                                            data.images.map { imageUri ->
//                                                                UploadPropertyMedia(
//                                                                    uri = Uri.parse(imageUri),
//                                                                    isVideo = false
//                                                                )
//                                                            }
//                                                        constants.PostProperty_ViewModel.addImages(
//                                                            imageMediaList
//                                                        )
                                                    } else if (data.video.isNotEmpty()) {
                                                        println("SERVER Video__-  -${ constants.PostProperty_ViewModel.budget_Price_PF5.value}--- cons.")

//                                                        val videoMedia = UploadPropertyMedia(
//                                                            uri = Uri.parse(data.video),
//                                                            isVideo = true
//                                                        )
//                                                        constants.PostProperty_ViewModel.addVideo(
//                                                            videoMedia
//                                                        )
                                                    }


                                                    println("DRAFT IPOST IDD -- ${item.post_property.user_post_id}")

                                                    constants.PostProperty_ViewModel.set_Post_Form_Flow(
                                                        1
                                                    )

                                                    constants.PostProperty_ViewModel.select_Land_Cat_Id(data.land_categorie_id)

                                                    constants.PostProperty_ViewModel.setPostFlow(PostFlow.DRAFT)

                                                    AppPreferences.save_Post_Id(data.user_post_id)

                                                    navController.navigate(ProfileScreenFlow.Post_Property_Forms.route)
                                                }
                                            }

                                        }
                                )*/

                                Spacer(modifier = Modifier.padding(16.dp))
                            }

                            if (isLoading && currentPage != 1) {
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

                        AnimatedVisibility(visible = onDelete_Tap) {
                            Static_Bottom(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(80.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxHeight(.7f)
                                            .width(132.dp)
                                            .background(Color(0xffE8E8E8), RoundedCornerShape(8.dp))
                                            .noRippleClickable {
                                                onDelete_Tap = false
                                                selectedPostIds = emptySet()
                                                onSelectAll_Tap = false
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("Cancel", color = Color(0xff666666))
                                    }

                                    Box(
                                        modifier = Modifier
                                            .width(132.dp)
                                            .fillMaxHeight(.7f)
                                            .background(
                                                if (selectedPostIds.isEmpty()) Color(0xffCCCCCC) else Color(
                                                    0xffE54C3C
                                                ),
                                                RoundedCornerShape(8.dp)
                                            )
                                            .clickable(enabled = selectedPostIds.isNotEmpty()) {
                                                if (selectedPostIds.isNotEmpty()) {
                                                    delete_Confirmation = true
                                                }
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("Delete", color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painterResource(R.drawable.nointernerdesign), "")
                Spacer(modifier = Modifier.padding(16.dp))
                Text(constants.activity.getString(R.string.no_Internet),
                    modifier = Modifier.padding(horizontal = 56.dp)
                )
            }
        }
    }

    Common_Popup(
        visible = delete_Confirmation,
        modifier = Modifier.background(Color(0xffF7F0DC)),
        content = {
            Column(
                modifier = Modifier.padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                spacer(2)

                Image(painter = painterResource(R.drawable.deletepopupicon) , "",
                    modifier = Modifier.size(64.dp))

//                constants.spacer(2)
                //constants.spacer(2)

//                Text(
//                    text = if (onSelectAll_Tap) "Delete all draft items" else "Delete selected draft items",
//                    color = newBlack,
//                    fontSize = constants.textUnit(16),
//                    fontFamily = constants.fontFamily(0)
//                )

                spacer(2)

                Text(
                    text = "This action cannot be undone. Are you sure you want to delete ${
                        if (onSelectAll_Tap) "all drafts" else "${selectedPostIds.size} draft(s)"
                    } permanently?",
                    color = newBlack,
                    fontSize = constants.textUnit(18),
                    fontFamily = constants.fontFamily(1),
                    textAlign = TextAlign.Center
//                   , lineHeight = 24.sp
                )

//                Spacer(modifier = Modifier.padding(2.dp))
                spacer(4)

                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(4f)
                            .height(46.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xffE8E8E8))
                            .noRippleClickable{
                                delete_Confirmation = false
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
                            .height(46.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Brush.verticalGradient(newRedGradienBg))
                            .noRippleClickable{
                                ClickHelper.getInstance().clickOnce {
                                    if (ClickGuard.canClick()) {
                                        if (network.value == NetworkStatus.Online) {
                                            val (postIds, selectAllFlag) = getSelectionData()

                                            println("Deleting drafts - PostIDs: $postIds, SelectAll: $selectAllFlag")

                                            constants.API_Vm.delete_Post_SM_Drafts(
                                                user_id = AppPreferences.getUserId(),
                                                select_all = selectAllFlag,
                                                user_post_id = postIds,
                                            ) { aPI_Result_Handling ->
                                                when (aPI_Result_Handling) {
                                                    is API_Result_Handling.Loading -> {}
                                                    is API_Result_Handling.NoData -> {}
                                                    is API_Result_Handling.Deactivated -> {}
                                                    is API_Result_Handling.Error -> {
                                                        GlobalSnackbar.show("Failed to delete drafts")
                                                    }

                                                    is API_Result_Handling.Success -> {
                                                        // Delete from UI
                                                        if (selectAllFlag == 1) {
                                                            // Delete all drafts from UI
                                                            constants.Profile_ViewModel.deleteAllProfileDrafts()
                                                        } else {
                                                            // Delete selected items from UI
                                                            selectedPostIds.forEach { postId ->
                                                                constants.Profile_ViewModel.deleteProfileDraftByUserPostId(
                                                                    postId
                                                                )
                                                            }
                                                        }

                                                        delete_Confirmation = false
                                                        onDelete_Tap = false
                                                        selectedPostIds = emptySet()
                                                        onSelectAll_Tap = false

                                                        GlobalSnackbar.show("Drafts deleted successfully")
                                                    }
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
                            text = "Delete",
                            color = Color.White,
                            fontSize = constants.textUnit(16),
                            fontFamily = constants.fontFamily(0)
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(8.dp))
                spacer(2)
            }
        },
        image = "",
        icon = 0 //R.drawable.closeenquiry
    )

    BackHandler {
        ClickHelper.getInstance().clickOnce {
            if (onDelete_Tap) {
                onDelete_Tap = false
                selectedPostIds = emptySet()
                onSelectAll_Tap = false
            } else {
                constants.Profile_ViewModel.onSet_Settings_Click(-1)
            }
        }
    }
}



@Composable
fun Rate_Us(notchPadding: State<Dp>, navController: NavHostController) {
    val rateUsContent by constants.Profile_ViewModel.rate_Us_content.collectAsState()

    var selectedItem  = rateUsContent.lastOrNull { it.isSelected }

    val rateus_Reason = constants.Profile_ViewModel.rate_us_Reason.collectAsState()

    var network = rememberNetworkStatus()

    println("selectedItem ${selectedItem}")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(newWhite)
            .padding(top = if (forTab()) 16.dp else  notchPadding.value)
    )
    {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .background(newWhite)
                .padding(horizontal = 16.dp),
            // verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            Box (
                modifier = Modifier
                    .fillMaxWidth()
                //.padding(horizontal = 16.dp)
                , contentAlignment = Alignment.Center
//                , verticalAlignment = Alignment.CenterVertically
//                , horizontalArrangement = Arrangement.spacedBy(8.dp)
            )
            {
                Backer(
                    modifier = Modifier.align(Alignment.CenterStart)
                    , onBackClick = {
                        //if (selected_AS_Settings.value == "Account Settings") {
                        constants.Profile_ViewModel.onSet_Settings_Click(-1)

                        rateUsContent.forEach {
                            it.isSelected = true
                        }


                        selectedItem?.isSelected = true
                        // }
                        // else {
                        //   constants.Profile_ViewModel.setSelected_AS_Settings("Account Settings")
                        //   constants.Profile_ViewModel.onSet_Settings_Click(0)
                        ///}
                    }
                )

                Text(
                    "Rate us" ,
                    fontSize = constants.textUnit(20),
                    fontFamily = constants.fontFamily(0)
                    , modifier = Modifier.align(Alignment.Center)
                )
            }



        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterStart)
            ,  horizontalAlignment = Alignment.CenterHorizontally) {

            // Description with animation
            AnimatedContent(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                targetState = selectedItem?.desc,
                label = "Rating Description", transitionSpec = {
                    //expandIn(tween(300)) togetherWith shrinkOut(tween(300))
                    scaleIn(tween(600)) togetherWith ExitTransition.None
                }
            )
            {
                    desc ->

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    AsyncImage(
                        model = selectedItem?.image, "", modifier = Modifier
                            .size(200.dp)
                    )

                    spacer(16)

                    Text(
                        text = desc ?: "Tap a star to rate!",
                        fontSize = constants.textUnit(20),
                        fontFamily = constants.fontFamily(0),
                        //style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }

            spacer(16)

            // Stars Row
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                rateUsContent.forEach { item ->
                    Image(
                        painter = painterResource(
                            id = if (item.isSelected) item.selected_Image else item.unselected_Image
                        ),
                        contentDescription = "Rating Star ${item.id + 1}",
                        modifier = Modifier
                            .size(32.dp)
                            .noRippleClickable{
                                constants.Profile_ViewModel.update_Star_Selection(item.id)
                            }
                    )
                }
            }

            spacer(16)

            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(56.dp)
                    .noRippleClickable{
                        ClickHelper.getInstance().clickOnce {
                            if(selectedItem!= null && selectedItem.id >2 ){
                                openPlayStore()
                            }else{
                                if (network.value == NetworkStatus.Online) {
                                    navController.navigate(ProfileScreenFlow.FeedBackScreen.route)
                                }
                                else {
                                    toast(constants.activity.getString(R.string.no_Internet))
                                }
                            }
                        }
                    }
                    .clip(RoundedCornerShape(8.dp))
                    .background(Brush.verticalGradient(newPurpleGradient))
                    .padding(horizontal = 24.dp)
                , contentAlignment = Alignment.Center
            ){
                Text(
                    if(selectedItem!= null && selectedItem.id >2 ) "RateUs" else "Submit Feedback",
                    color = Color.White,
                    fontSize = constants.textUnit(16),
                    fontFamily = constants.fontFamily(1),
                    modifier = Modifier
                )
            }

        }

        Image(
            painter = painterResource(R.drawable.credientialsbottom),
            ""
            , contentScale = ContentScale.FillWidth
            , modifier = Modifier.align(Alignment.BottomCenter)
        )


       /* Static_Bottom(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(80.dp)
            , content = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .fillMaxHeight(.7f).noRippleClickable{
                            ClickHelper.getInstance().clickOnce {
                                if(selectedItem!= null && selectedItem.id >2 ){
                                    openPlayStore()
                                }else{
                                    if (network.value == NetworkStatus.Online) {
                                        navController.navigate(ProfileScreenFlow.FeedBackScreen.route)
                                    }
                                    else {
                                        toast(constants.activity.getString(R.string.no_Internet))
                                    }
                                }
                            }
                        }
                        .clip(RoundedCornerShape(8.dp))
                        .background(newBlue)
                    , contentAlignment = Alignment.Center
                ){
                    Text(
                        if(selectedItem!= null && selectedItem.id >2 ) "RateUs" else "Submit Feedback",
                        color = Color.White,
                        fontSize = constants.textUnit(16),
                        fontFamily = constants.fontFamily(1),
                        modifier = Modifier
                    )
                }
            }
        )*/
    }

    BackHandler {
        ClickHelper.getInstance().clickOnce {
            constants.Profile_ViewModel.onSet_Settings_Click(-1)
            rateUsContent.forEach {
                it.isSelected = true
            }


            selectedItem?.isSelected = true

        }
    }
}




fun openPlayStore() {
    val playIntent: Intent = Intent().apply {
        action = Intent.ACTION_VIEW

        data = "http://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}".toUri()

    }
    try {
        constants.activity.startActivity(playIntent)
    } catch (e: Exception) {
        // handle the exception
    }
}


