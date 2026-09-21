package com.toletspot.houseforrent.Home_Screen.PostProperty_Module


import com.toletspot.houseforrent.R
import android.graphics.Bitmap
import android.location.Geocoder
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.compose.PlayerSurface
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.toletspot.houseforrent.API.API_Result_Handling
import com.toletspot.houseforrent.API.StartUp_API.get_Form_Publish_API_CALL
import com.toletspot.houseforrent.API.StartUp_API.get_Land_Categories_PF2_API_Call
import com.toletspot.houseforrent.API.StartUp_API.post_Form_2_API_Call
import com.toletspot.houseforrent.AppPreferences
import com.toletspot.houseforrent.CommonText
import com.toletspot.houseforrent.Custom_Assets.API_Fail_UI
import com.toletspot.houseforrent.Custom_Assets.Backer
import com.toletspot.houseforrent.Custom_Assets.ClickHelper
import com.toletspot.houseforrent.Custom_Assets.Common_Popup
import com.toletspot.houseforrent.Custom_Assets.Section
import com.toletspot.houseforrent.Custom_Assets.Sell_Gradient_Box
import com.toletspot.houseforrent.Custom_Assets.Static_Bottom
import com.toletspot.houseforrent.Custom_Assets.dashedBorder
import com.toletspot.houseforrent.Custom_Assets.formatLabel
import com.toletspot.houseforrent.Custom_Assets.getVideoThumbnail
import com.toletspot.houseforrent.Custom_Assets.getVideoThumbnailString
import com.toletspot.houseforrent.Custom_Assets.toast
import com.toletspot.houseforrent.GlobalSnackbar
import com.toletspot.houseforrent.Home_Screen.Common_H_ViewModel
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.CommonFormDataClass.PostFormCommonPropertyData
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentoFormPreview.FormPreviewRento
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentoMediaDC.Image
import com.toletspot.houseforrent.Home_Screen.ProfileModule.ClickGuard
import com.toletspot.houseforrent.MapSearchScreen
import com.toletspot.houseforrent.Navigation.PostPropertyFlow
import com.toletspot.houseforrent.Navigation.UserCredentialsScreenFlow
import com.toletspot.houseforrent.NetworkStatus
import com.toletspot.houseforrent.PincodePlaceSearch
import com.toletspot.houseforrent.UI_DataClass.PP3_API_DC
import com.toletspot.houseforrent.UI_DataClass.PropertyType_Form2
import com.toletspot.houseforrent.UI_DataClass.UploadPropertyMedia
import com.toletspot.houseforrent.constants
import com.toletspot.houseforrent.forTab
import com.toletspot.houseforrent.noRippleClickable
import com.toletspot.houseforrent.rememberNetworkStatus
import com.toletspot.houseforrent.ui.theme.newBlack
import com.toletspot.houseforrent.ui.theme.newBlue
import com.toletspot.houseforrent.ui.theme.newGray
import com.toletspot.houseforrent.ui.theme.newLightBlue
import com.toletspot.houseforrent.ui.theme.newLightGray
import com.toletspot.houseforrent.ui.theme.newPurpleGradient
import com.toletspot.houseforrent.ui.theme.newPurpleGradientBorder
import com.toletspot.houseforrent.ui.theme.newRedGradienBorder
import com.toletspot.houseforrent.ui.theme.newWhite
import com.toletspot.houseforrent.ui.theme.rentoDarkGray
import com.toletspot.houseforrent.ui.theme.rentoLightGray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.collections.forEach
import kotlin.reflect.full.memberProperties


var limitGoingIn = mutableStateOf(false)

var onSomething = mutableStateOf(0)
var next_Active_Fields = mutableStateListOf<Int>()
var next_Active_Fields5 = mutableStateListOf<Int>()
var next_Active_Fields6 = mutableStateListOf<Int>()






@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Post_Property_Forms(navController: NavHostController, viewModel: Common_H_ViewModel) {



    val apiError = remember { mutableStateOf(false) }

    val network = rememberNetworkStatus()

    var onHover = remember { mutableStateOf(false) }
    val hoveredMedia = remember { mutableStateOf<UploadPropertyMedia?>(null) }

    val current_Form = constants.PostProperty_ViewModel.postPropertyFormPage.collectAsState()


    val isLoading = constants.PostProperty_ViewModel.status_PFs.collectAsState()

    val draft_State = constants.PostProperty_ViewModel.save_Draft_PP_State.collectAsStateWithLifecycle()

    /// post flow form draft
    var draft_from_Draft by remember { mutableStateOf(false) }
    var repost_Draft by remember { mutableStateOf(false) }
    val Flow_From_Which by constants.PostProperty_ViewModel.which_Post_Forms_Flow.collectAsState()

    var show_Map_view = remember { mutableStateOf(false) }

    val pin_Lat_Long  = constants.PostProperty_ViewModel.pinned_Lat_Long.collectAsStateWithLifecycle()

    val context = LocalContext.current


    val form1_Selected = constants.PostProperty_ViewModel.first_Form_selected_PP.collectAsState()

    var scope = rememberCoroutineScope()

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(9f)
                    .padding(horizontal = 16.dp)
            )
            {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                    , contentAlignment = Alignment.Center
                ) {

                    Backer(
                        modifier = Modifier.align(Alignment.CenterStart),
                        onBackClick = {
                            if (!isLoading.value) {
                                if (current_Form.value == 0) {
                                    if (form1_Selected.value == -1) {
                                        viewModel.selectedBABTab(0)
                                        viewModel.toggleshowBABars(true)
                                        viewModel.toggleshowTABars(true)
                                        AppPreferences.save_Post_Id(0)
                                        //navController.navigateUp()
                                    } else {
                                        println("FLOW FROM WHICH ___ ${Flow_From_Which}")
                                        if (Flow_From_Which == 1) {
                                            draft_from_Draft = true
                                        } else {
                                            if (constants.PostProperty_ViewModel.get_Post_Form_Flow() == 2) {
                                                repost_Draft = true
                                            } else {
                                                constants.PostProperty_ViewModel.enable_Save_Draft()
                                            }

                                        }

                                    }

                                    //navController.navigate(PostPropertyFlow.Common_Screen.route)
                                } else {
                                    // constants.PostProperty_ViewModel.onPreviousPPForm()
                                    println("FLOW FROM WHICH ___ ${Flow_From_Which}")
                                    if (Flow_From_Which == 1) {
                                        draft_from_Draft = true
                                    } else {
                                        if (constants.PostProperty_ViewModel.get_Post_Form_Flow() == 2) {
                                            repost_Draft = true
                                        } else {
                                            constants.PostProperty_ViewModel.enable_Save_Draft()
                                        }
                                    }
                                }
                            }
                        }
                    )

                    Sell_Gradient_Box(modifier = Modifier.align(Alignment.CenterEnd))
                }
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(9f),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                        var previousPage by remember { mutableStateOf(current_Form.value) }



                    AnimatedContent(
                        targetState = current_Form.value,
                        transitionSpec = {
                            if (targetState > previousPage) {
                                // Moving forward
                                slideInHorizontally(animationSpec = tween(600)) { it } togetherWith
                                        ExitTransition.None
                            } else {
                                // Moving backward
                                slideInHorizontally(animationSpec = tween(600)) { -it } togetherWith
                                        ExitTransition.None
                            }
                        }
                    ) { targetState ->
                        previousPage = targetState // update previous page

                        when (targetState) {
                            0 -> {
                                println("FIRST FORM TIMESS INN")
                                key("form_0") {
                                    PP_First_Form(isLoading)
                                }
                            }
                            1 -> {
                                println("SECOND FORM TIMES INNN")
                                key("form_1") {
                                    PP_Second_Form(isLoading)
                                }
                            }
                            2 -> {
                                key("form_2") {
                                    PP_Third_Form(isLoading, show_Map_view)
                                }
                            }
                            3 -> {
                                key("form_3") {
                                    println("MULTIPLE TIMES INNNNN OUT ")
                                    PP_Fourth_Form(apiError)
                                    println("MULTIPLE TIMES INNNNN INSIDE ")
                                }
                            }
                            4 -> {
                                key("form_4") {
                                    PP_Fifth_Form()
                                }
                            }
                            5 -> {
                                key("form_6") {
                                    Form6_Common()
                                }
                            }
                            6 -> {
                                key("form_5") {
                                    PP_Seventh_Form(onHover)
                                    //PP_Sixth_Form(isLoading)
                                }
                            }
//                            7 -> {
//                                key("form_6") {
//                                    //PreviewScreen(navController)
//                                }
//                            }
                        }
                    }
                }

            }

            if (!apiError.value) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), contentAlignment = Alignment.Center
                ) {
                    println("CURRENT FORM NUMBER--- ${current_Form.value}")
                    PP_Forms_Next_Clicker(current_Form, isLoading, navController, apiError)
                }
            }
        }

    }

    if (onHover.value) {
        val mediaItems by constants.PostProperty_ViewModel.mediaList.collectAsState()

        UserMediaPreview(mediaList = mediaItems , modifier = Modifier , onClose = {
            onHover.value = false
        })
    }






    if (constants.PostProperty_ViewModel.emptyMediaUploadBtm.value) {
        ModalBottomSheet(
            onDismissRequest = {
                constants.PostProperty_ViewModel.set_False_emptyMediaBtm()
            }
            , containerColor = Color.White
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                , verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                constants.spacer(4)
                CommonText(
                    "You didn’t upload any Photo/Video",
                    newBlack,
                    18,
                    1
                    , modifier = Modifier
                        .padding(horizontal = 16.dp)
                )

                constants.spacer(4)

                CommonText(
                    "Since you’re skipping photos or videos for now, users exploring your property listing may request to view photos. You can always upload visuals later helps renters get a better idea of the space and attracts more interest.",
                    Color(0xff575757),
                    14,
                    3
                    , modifier = Modifier
                        .padding(horizontal = 16.dp)

                )

                constants.spacer(4)

                HorizontalDivider()

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(.9f)
                        .height(56.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Brush.verticalGradient(newPurpleGradient))
                        .border(
                            1.dp,
                            Brush.linearGradient(newPurpleGradientBorder),
                            RoundedCornerShape(4.dp)
                        )
                        .noRippleClickable {
                            constants.PostProperty_ViewModel.set_False_emptyMediaBtm()
                            constants.PostProperty_ViewModel.confirmProceedWithoutMedia()
                        }
                    , contentAlignment = Alignment.Center
                ) {
                    CommonText(
                        "Okay, got it",
                        Color.White,
                        14,
                        0
                    )
                }
            }
        }
    }

    Common_Popup(
        visible = draft_State.value,
        modifier = Modifier.background(Color(0xffF7F0DC))
        , image = ""
        , icon = R.drawable.save_draft
    )
    {
        Column (
            modifier = Modifier
                .wrapContentHeight()
                //.padding(top = 16.dp)
            , horizontalAlignment = Alignment.CenterHorizontally
            , verticalArrangement = Arrangement.Center
        )
        {

            constants.spacer(6)

            Image(painter = painterResource(R.drawable.posting_draft) , "",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(62.dp))

            constants.spacer(6)
//
//            Text(
//                "Save this in Draft?",
//                color = newBlack,
//                fontSize = constants.textUnit(16),
//                fontFamily = constants.fontFamily(0)
//            )

            //constants.spacer(2)
            Text(
                "Save your progress as a draft before exiting?",
                color = newBlack,
                fontSize = constants.textUnit(18),
                fontFamily = constants.fontFamily(1)
                , textAlign = TextAlign.Center
                , modifier = Modifier.padding(horizontal = if (forTab())48.dp else 36.dp)
               , lineHeight = 24.sp
            )

            constants.spacer(8)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                , verticalArrangement = Arrangement.Center
                , horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                    , verticalAlignment = Alignment.CenterVertically
                    , horizontalArrangement = Arrangement.SpaceEvenly
                )
                {
                    Box(
                        modifier = Modifier
                            .height(if (forTab()) 46.dp else 36.dp)
                            .width(if (forTab()) 142.dp else 121.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color.White)
                            .border(1.dp, newBlack, RoundedCornerShape(6.dp))
                            .noRippleClickable {
                                constants.PostProperty_ViewModel.disable_Save_Draft()
                            }
                        , contentAlignment = Alignment.Center
                    ){
                        Text(
                            "Keep Editing",
                            color = newBlack,
                            fontSize = constants.textUnit(14),
                            fontFamily = constants.fontFamily(0)
                        )
                    }
                    val loadState = remember { mutableStateOf(false) }
                    Box(
                        modifier = Modifier
                            .height(if (forTab()) 46.dp else 36.dp)
                            .width(if (forTab()) 132.dp else 121.dp)
                            .background(Brush.verticalGradient(newPurpleGradient))
                            .noRippleClickable {
                                ClickHelper.getInstance().clickOnce {
                                    if (ClickGuard.canClick()) {
                                        if (constants.Profile_ViewModel.get_From_Repost() != 1) {
                                            viewModel.selectedBABTab(0)
                                            viewModel.toggleshowBABars(true)
                                            viewModel.toggleshowTABars(true)
                                            AppPreferences.save_Post_Id(0)
                                            constants.PostProperty_ViewModel.disable_Save_Draft()
                                        } else {
                                            constants.Profile_ViewModel.set_From_Repost(0)

                                            navController.navigateUp()
                                            constants.PostProperty_ViewModel.disable_Save_Draft()

                                        }
                                    }
                                }
                            }
                        , contentAlignment = Alignment.Center
                    ){
                        if (loadState.value){
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                        }
                        else {
                            Text(
                                "Save Draft",
                                color = Color.White,
                                fontSize = constants.textUnit(14),
                                fontFamily = constants.fontFamily(0)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(8.dp))
                constants.spacer(2)

                Text("Exit Form"
                    , color = Color(0xff666666)
                    , fontSize = constants.textUnit(14)
                    , fontFamily = constants.fontFamily(0)
                    , modifier = Modifier
                        .padding(vertical = 8.dp)
                        .noRippleClickable {

                            viewModel.selectedBABTab(0)
                            viewModel.toggleshowBABars(true)
                            viewModel.toggleshowTABars(true)
                            AppPreferences.save_Post_Id(0)
                            constants.Profile_ViewModel.from_SoldOuts.value = false
                            constants.PostProperty_ViewModel.set_Post_Form_Flow(-1)
                            constants.PostProperty_ViewModel.disable_Save_Draft()

                        }
                )

                Spacer(modifier = Modifier.padding(4.dp))
            }

            constants.spacer(2)
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }


    Common_Popup(
        visible = draft_from_Draft,
        modifier = Modifier.background(Color(0xffF7F0DC))
        , image = ""
        , icon = R.drawable.save_draft
    )
    {
        Column (
            modifier = Modifier
                .wrapContentHeight()
               // .background(Color.Red)
                //.padding(top = 16.dp)
            , horizontalAlignment = Alignment.CenterHorizontally
            , verticalArrangement = Arrangement.spacedBy(12.dp)
        )
        {

            constants.spacer(2)
//            Text(
//                "Save changes to draft?",
//                color = newBlack,
//                fontSize = constants.textUnit(16),
//                fontFamily = constants.fontFamily(0)
//            )

            Image(painter = painterResource(R.drawable.posting_draft) , "",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(62.dp))


            constants.spacer(2)
            Text(
                "You’ve made edits to this draft. Would you like to update it with the latest changes?",
                color = newBlack,
                fontSize = constants.textUnit(12),
                fontFamily = constants.fontFamily(3)
                , textAlign = TextAlign.Center
                , modifier = Modifier.padding(horizontal = if (forTab()) 46.dp else 36.dp)
               , lineHeight = 24.sp
            )

//            Spacer(modifier = Modifier.padding(8.dp))
            constants.spacer(4)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                , verticalArrangement = Arrangement.Center
                , horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                    , verticalAlignment = Alignment.CenterVertically
                    , horizontalArrangement = Arrangement.SpaceEvenly
                )
                {
                    Box(
                        modifier = Modifier
                            .height(if (forTab()) 46.dp else 36.dp)
                            .width(if (forTab()) 142.dp else 121.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color.White)
                            .border(1.dp, newBlack, RoundedCornerShape(6.dp))
                            .noRippleClickable {
                                draft_from_Draft = false
                            }
                        , contentAlignment = Alignment.Center
                    ){
                        Text(
                            "keep Editing",
                            color = newBlack,
                            fontSize = constants.textUnit(14),
                            fontFamily = constants.fontFamily(0)
                        )
                    }
                    val loadState = remember { mutableStateOf(false) }
                    Box(
                        modifier = Modifier
                            .height(if (forTab()) 46.dp else 36.dp)
                            .width(if (forTab()) 132.dp else 121.dp)
                            .background(Brush.verticalGradient(newPurpleGradient))
                            .noRippleClickable {
                                ClickHelper.getInstance().clickOnce {
                                    if (ClickGuard.canClick()) {

                                        if (network.value == NetworkStatus.Offline) {
                                            GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
                                        } else {

                                            scope.launch(Dispatchers.Main) {
                                                constants.PostProperty_ViewModel.save_Changes_Draft.value =
                                                    1
                                                constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                                    it.copy(draft = current_Form.value + 1)
                                                }

                                                println("APP POST BID  -- ${AppPreferences.get_Post_Id()}")



                                                val requestBody =
                                                    constants.PostProperty_ViewModel.buildUploadRequestBody()

                                                handleDraftSubmission(
                                                    requestBody = requestBody,
                                                    navController = navController,
                                                    "1",
                                                    onComplete = {
                                                        println("On COmplete Draft model preview 1")
                                                        /* get_Form_Publish_API_CALL { result ->
                                                            when (result) {
                                                                0 -> {}
                                                                1 -> {}
                                                                2 -> {}
                                                                3 -> {
                                                                    constants.Profile_ViewModel.set_From_Repost(
                                                                        0
                                                                    )
                                                                    constants.PostProperty_ViewModel.set_Post_Form_Flow(
                                                                        -1
                                                                    )
                                                                    constants.Common_H_ViewModel.selectedBABTab(0)

                                                                    constants.PostProperty_ViewModel.setPostFlow(PostFlow.NONE)

                                                                    constants.PostProperty_ViewModel.clear_Selected_Fields_Form4()


                                                                    constants.URL_COMPLETED.clear()
                                                                    constants.PostProperty_ViewModel.clear_Media()

                                                                    navController.navigate(
                                                                        PostPropertyFlow.Common_Screen.route
                                                                    )
                                                                }
                                                            }
                                                        }*/

                                                        loadState.value = false
                                                        constants.Profile_ViewModel.set_From_Repost(
                                                            1
                                                        )
                                                        constants.PostProperty_ViewModel.set_Post_Form_Flow(
                                                            1
                                                        )

                                                        constants.PostProperty_ViewModel.change_Status_PFs(
                                                            false
                                                        )

                                                        constants.PostProperty_ViewModel.clear_Selected_Fields_Form4()
                                                        constants.PostProperty_ViewModel.clearAllPostFields()

                                                        constants.PostProperty_ViewModel.resetErrors4()
                                                        constants.PostProperty_ViewModel.resetErrors5()
                                                        constants.PostProperty_ViewModel.resetErrors6()

                                                        scope.launch(Dispatchers.Main) {
                                                            navController.navigateUp()
                                                        }
                                                    }
                                                )

                                                /* put_Draft_New_Flow_API_CALL { result ->
                                                    when (result) {
                                                        0 -> {
                                                            // fail
                                                            loadState.value = false
                                                            println("failllll")
                                                        }

                                                        1 -> {
                                                            scope.launch(Dispatchers.Main) {
                                                                // sucvcess
                                                                println("sucesssssss")
                                                                loadState.value = false
                                                                constants.Profile_ViewModel.set_From_Repost(
                                                                    1
                                                                )
                                                                constants.PostProperty_ViewModel.set_Post_Form_Flow(
                                                                    1
                                                                )

                                                                constants.PostProperty_ViewModel.change_Status_PFs(
                                                                    false
                                                                )



                                                                navController.navigateUp()
                                                            }
                                                            //navController.navigate(PostPropertyFlow.ViewPropertyStructure.route)

                                                        }

                                                        2 -> {}
                                                        3 -> {
                                                            loadState.value = true
                                                        }
                                                    }
                                                }*/
                                            }
                                        }
                                    }
                                }

                            }
                        , contentAlignment = Alignment.Center
                    ){
                        if (loadState.value){
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
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


                Spacer(modifier = Modifier.padding(8.dp))

                Text("Back to Drafts"
                    , color = Color(0xff666666)
                    , fontSize = constants.textUnit(14)
                    , fontFamily = constants.fontFamily(0)
                    , modifier = Modifier
                        .padding(vertical = 8.dp)
                        .noRippleClickable {
                            constants.PostProperty_ViewModel.set_Post_Form_Flow(-1)
                            constants.PostProperty_ViewModel.clearAllPostFields()

                            constants.PostProperty_ViewModel.resetErrors4()
                            constants.PostProperty_ViewModel.resetErrors5()
                            constants.PostProperty_ViewModel.resetErrors6()
                            navController.navigateUp()
                        }
                )
                Spacer(modifier = Modifier.padding(4.dp))
                constants.spacer(2)
            }
        }
    }

    Common_Popup(
        visible = repost_Draft,
        modifier = Modifier.background(Color(0xffF7F0DC))
        , image = ""
        , icon = R.drawable.save_draft
    )
    {
        Column (
            modifier = Modifier
                .wrapContentHeight()
            // .background(Color.Red)
            //.padding(top = 16.dp)
            , horizontalAlignment = Alignment.CenterHorizontally
            , verticalArrangement = Arrangement.spacedBy(12.dp)
        )
        {
           //constants.spacer(2)
//            Text(
//                "Save this in Draft?",
//                color = newBlack,
//                fontSize = constants.textUnit(16),
//                fontFamily = constants.fontFamily(0)
//            )

            constants.spacer(2)
//            Text(
//                "Save changes to draft?",
//                color = newBlack,
//                fontSize = constants.textUnit(16),
//                fontFamily = constants.fontFamily(0)
//            )

            Image(painter = painterResource(R.drawable.posting_draft) , "",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(62.dp))


            constants.spacer(2)
           // constants.spacer(2)
            Text(
                "You have not completed this. Would you like to save it as a draft?",
                color = newBlack,
                fontSize = constants.textUnit(18),
                fontFamily = constants.fontFamily(1)
                , textAlign = TextAlign.Center
                , modifier = Modifier.padding(horizontal =if (forTab()) 46.dp else 36.dp)
               , lineHeight = 24.sp
            )


            constants.spacer(2)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                , verticalArrangement = Arrangement.Center
                , horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                    , verticalAlignment = Alignment.CenterVertically
                    , horizontalArrangement = Arrangement.SpaceEvenly
                )
                {
                    Box(
                        modifier = Modifier
                            .height(36.dp)
                            .width(if (forTab()) 142.dp else 121.dp)
                            .background(Color(0xffE8E8E8))
                            .noRippleClickable {
                                println("#$%^&*()*&^%$%^&*")
                                repost_Draft = false
                                println("#wdcjwdcjkvjk -- ${repost_Draft}")
                            }
                        , contentAlignment = Alignment.Center
                    ){
                        Text(
                            "Keep Editing",
                            color = newBlack,
                            fontSize = constants.textUnit(14),
                            fontFamily = constants.fontFamily(0)
                        )
                    }
                    val loadState = remember { mutableStateOf(false) }
                    Box(
                        modifier = Modifier
                            .height(36.dp)
                            .width(if (forTab()) 142.dp else 121.dp)
                            .background(Brush.verticalGradient(newPurpleGradient))
                            .noRippleClickable {
                                ClickHelper.getInstance().clickOnce {
                                    if (ClickGuard.canClick()) {

                                        if (network.value == NetworkStatus.Offline) {
                                            GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
                                        } else {

                                            scope.launch(Dispatchers.Main) {
                                                constants.PostProperty_ViewModel.save_Changes_Draft.value =
                                                    1
                                                constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                                    it.copy(draft = current_Form.value)
                                                }

                                                /*put_Draft_New_Flow_API_CALL { result ->
                                                    when (result) {
                                                        0 -> {
                                                            // fail
                                                            loadState.value = false
                                                            println("failllll")
                                                        }

                                                        1 -> {
                                                            scope.launch(Dispatchers.Main) {
                                                                // sucvcess
                                                                println("sucesssssss")
                                                                loadState.value = false

                                                                constants.Profile_ViewModel.set_From_Repost(
                                                                    0
                                                                )
                                                                if(constants.Profile_ViewModel.from_SoldOuts.value == true){
                                                                    constants.PostProperty_ViewModel.set_Post_Form_Flow(
                                                                        -1
                                                                    )
                                                                }else {
                                                                    constants.PostProperty_ViewModel.set_Post_Form_Flow(
                                                                        1
                                                                    )
                                                                }


                                                                constants.PostProperty_ViewModel.change_Status_PFs(
                                                                    false
                                                                )


                                                                AppPreferences.save_Post_Id(0)



                                                                navController.navigateUp()
                                                            }
                                                            //navController.navigate(PostPropertyFlow.ViewPropertyStructure.route)

                                                        }

                                                        2 -> {}
                                                        3 -> {
                                                            loadState.value = true
                                                        }
                                                    }
                                                }*/
                                            }
                                        }
                                    }
                                }

                            }
                        , contentAlignment = Alignment.Center
                    ){
                        if (loadState.value){
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                        }
                        else {
                            Text(
                                "Save Draft",
                                color = Color.White,
                                fontSize = constants.textUnit(14),
                                fontFamily = constants.fontFamily(0)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Text("Back to Home"
                    , color = Color(0xff666666)
                    , fontSize = constants.textUnit(14)
                    , fontFamily = constants.fontFamily(0)
                    , modifier = Modifier
                        .padding(vertical = 8.dp)
                        .noRippleClickable {


                            constants.Profile_ViewModel.set_Open_False()
                            constants.Profile_ViewModel.onSet_Settings_Click(-1)
                            viewModel.selectedBABTab(0)
                            viewModel.toggleshowBABars(true)
                            viewModel.toggleshowTABars(true)
                            AppPreferences.save_Post_Id(0)
                            constants.PostProperty_ViewModel.set_Post_Form_Flow(-1)
                            repost_Draft = false
                            constants.Profile_ViewModel.from_SoldOuts.value = false

                            constants.PostProperty_ViewModel.clear_Selected_Fields_Form4()
                            constants.PostProperty_ViewModel.clearAllPostFields()

                            navController.navigate(UserCredentialsScreenFlow.Common_Screen.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                            }

//                            constants.API_Vm.delete_Post_SM_Drafts(
//                                user_id = AppPreferences.getUserId(),
//                                select_all = 0,
//                                user_post_id = AppPreferences.get_Post_Id().toString(),
//                            ) { aPI_Result_Handling ->
//                                when (aPI_Result_Handling) {
//                                    is API_Result_Handling.Loading -> {}
//                                    is API_Result_Handling.NoData -> {}
//                                    is API_Result_Handling.Error -> {}
//                                    is API_Result_Handling.Deactivated -> {
//                                        //resultCallback(5)
//                                    }
//                                    is API_Result_Handling.Success -> {
//
//                                        AppPreferences.save_Post_Id(0)
//                                        navController.navigateUp()
//
//                                    }
//                                }
//                            }
                        }
                )
            }
            constants.spacer(2)
        }
    }


    if (show_Map_view.value){
        val placesClient = Places.createClient(context)

        MapSearchScreen(placesClient , LocationServices.getFusedLocationProviderClient(context) ,
            constants.activity.getString(R.string.maps_api_key) , show_Map_view, initialLatLng = if (pin_Lat_Long.value != null) pin_Lat_Long.value else null)
    }

    BackHandler {
        if (current_Form.value == 0) {
            if (form1_Selected.value == -1) {
                viewModel.selectedBABTab(0)
                viewModel.toggleshowBABars(true)
                viewModel.toggleshowTABars(true)
                AppPreferences.save_Post_Id(0)
                //navController.navigateUp()
            }
            else
            {
                println("FLOW FROM WHICH ___ ${Flow_From_Which}")
                if (Flow_From_Which == 1){
                    draft_from_Draft = true
                }
                else {
                    if (constants.PostProperty_ViewModel.get_Post_Form_Flow() == 2) {
                        repost_Draft = true
                    }
                    else {
                        constants.PostProperty_ViewModel.enable_Save_Draft()
                    }

                }

            }

            //navController.navigate(PostPropertyFlow.Common_Screen.route)
        } else
        {
            // constants.PostProperty_ViewModel.onPreviousPPForm()
            println("FLOW FROM WHICH ___ ${Flow_From_Which}")
            if (Flow_From_Which == 1){
                draft_from_Draft = true
            }
            else {
                if (constants.PostProperty_ViewModel.get_Post_Form_Flow() == 2) {
                    repost_Draft = true
                }
                else {
                    constants.PostProperty_ViewModel.enable_Save_Draft()
                }
            }
        }
    }
}



@Composable
fun PP_First_Form(isLoading: State<Boolean>) {

    val selected_Option = constants.PostProperty_ViewModel.first_Form_selected_PP.collectAsState()
    val errorState by constants.PostProperty_ViewModel.form1Error.collectAsState()

    Column {
        Row(
            modifier = Modifier
                .align(Alignment.Start)
                .wrapContentSize()
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0XFFF4F4F4))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        )
        {
            Text(
                "Step 1",
                color = newBlue,
                fontSize = constants.textUnit(12),
                fontFamily = constants.fontFamily(0)
            )
            Text(
                "/7",
                color = Color(0xff666666),
                fontSize = constants.textUnit(12),
                fontFamily = constants.fontFamily(0)
            )
        }

        Spacer(modifier = Modifier.padding(4.dp))

        Text(
            "Tells us who you are",
            color = newBlack,
            fontSize = constants.textUnit(24),
            fontFamily = constants.fontFamily(0),
            modifier = Modifier
                .align(Alignment.Start)
        )

        Spacer(modifier = Modifier.padding(8.dp))

        repeat(2){
            index ->

            var textContent = if (index == 0) "I am the owner of the property" else "I am a broker"

            var imageContent_unselected = if (index == 0) R.drawable.ownerrento else R.drawable.brokerrento

            var imageContent_selected = if (index == 0) R.drawable.ownerselectedrento else R.drawable.brokerselectedrento


            println("ISLOADING __ ${isLoading}")

            ListItem(
                headlineContent = {
                    Text(
                        textContent,
                        color = newBlack,
                        fontSize = constants.textUnit(16),
                        fontFamily = constants.fontFamily(2),
                        modifier = Modifier

                    )
                },
                leadingContent = {
                    if (selected_Option.value == index){
                        AsyncImage(
                            model = imageContent_selected
                            ,""
                            , modifier = Modifier
                                .size(24.dp)
                            //, colorFilter = ColorFilter.tint(if (selected_Option.value == index) newBlue else newGray)
                        )
                    }
                    else {
                        AsyncImage(
                            model = imageContent_unselected
                            ,""
                            , modifier = Modifier
                                .size(24.dp)
                          //  , colorFilter = ColorFilter.tint(if (selected_Option.value == index) newBlue else newGray)
                        )
                    }
                },
                modifier = Modifier
                    .wrapContentSize()
                    .clip(RoundedCornerShape(8.dp))
                    .then(
                        if (errorState) {
                            Modifier
                                //.background(Color.White)
                                .border(
                                    1.dp,
                                    Brush.verticalGradient(newRedGradienBorder),
                                    RoundedCornerShape(8.dp)
                                )
                        } else {
                            Modifier
                                //.background(if (selected_Option.value == index) Color(0xffF7F0DC) else Color.White)
                                .border(
                                    1.dp,
                                    if (selected_Option.value == index) newBlue else newGray,
                                    RoundedCornerShape(8.dp)
                                )
                        }
                    )
                    //.padding(vertical = 8.dp)
                    .noRippleClickable {
                        println("ISLOADING __ONLICK ${isLoading}")
                        if (!isLoading.value) {
                            constants.PostProperty_ViewModel.set1formError(false)
                            constants.PostProperty_ViewModel.first_Form_selected_PP(index)
                            constants.PostProperty_ViewModel.select_User_Type_1PF(index)
                            println("USERTYPE PF1 -- ${constants.PostProperty_ViewModel.selected_User_Type_1PF.value}")
                        }
                    }
                , colors = ListItemColors(
                    containerColor = if (selected_Option.value == index) Color(0xffF7F0DC) else Color.White,
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

            Spacer(modifier = Modifier.padding(12.dp))

        }

        if (errorState){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                , verticalAlignment = Alignment.CenterVertically
                , horizontalArrangement = Arrangement.Start
            ) {
                Image(painter = painterResource(R.drawable.errorinforento) , "",
                    modifier = Modifier.size(12.dp))

                constants.spacer(8)

                CommonText("please select who you are",
                    Color.Red,
                    12,
                    2
                    )
            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PP_Second_Form(isLoading: State<Boolean>) {



    println("ON REPOST COMING INTO -- ${constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value}")
    var network = rememberNetworkStatus()




    var onSelected_ProType = constants.PostProperty_ViewModel.onSelected_ProType.collectAsStateWithLifecycle()


    println("ON COMPODABLE IDD-- ${onSelected_ProType.value}")
    val errorState by constants.PostProperty_ViewModel.form2Error.collectAsState()


    //val selected_Land_Main = constants.PostProperty_ViewModel.selected_Land_Cat_Id.collectAsStateWithLifecycle()
    val selected_LandSubType = constants.PostProperty_ViewModel.pp_Form2_LandSubTypes_Selected.collectAsStateWithLifecycle()


    val changes_Not_Allowed = remember { mutableStateOf(false) }


    LaunchedEffect(Unit , onSelected_ProType.value ) {
        //if (isConnected.value) {
            get_Land_Categories_PF2_API_Call { result ->
                when (result) {
                    0 -> {
                        println("Failure")
                    }

                    1 -> {
                        println("Success 12345678 form 22")
                    }
                }
            }
    }

    val isLoadingSub = constants.PostProperty_ViewModel.status_Land_Types.collectAsState()


    val residential_Options_List = constants.PostProperty_ViewModel.pp_SecondForm_OptionsList.collectAsState()

    val postFlow = constants.PostProperty_ViewModel.postFlow.collectAsState()

    println("Step 2 SSCREEN-- ${ onSelected_ProType}@@@${constants.PostProperty_ViewModel.selected_Land_Cat_Id} ###${constants.PostProperty_ViewModel.selected_Land_Type_PF2.value}--- ${residential_Options_List.value}")



    if (constants.PostProperty_ViewModel.get_Post_Form_Flow() == 2) {
        LaunchedEffect(onSelected_ProType, selected_LandSubType.value) {
            println("REFRESHING 2###${onSelected_ProType}  *** ${selected_LandSubType.value}  ${constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value.first} --- ${constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value.second}")
            if (onSelected_ProType.value  != constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value.first || selected_LandSubType.value != constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value.second) {
                changes_Not_Allowed.value = true
            }
        }
    }



    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(newWhite)
    )
    {
        Row(
            modifier = Modifier
                .align(Alignment.Start)
                .wrapContentSize()
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0XFFF4F4F4))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        )
        {
            Text(
                "Step 2",
                color = newBlue,
                fontSize = constants.textUnit(12),
                fontFamily = constants.fontFamily(0)
            )
            Text(
                "/7",
                color = Color(0xff666666),
                fontSize = constants.textUnit(12),
                fontFamily = constants.fontFamily(0)
            )
        }

        Spacer(modifier = Modifier.padding(4.dp))

        Text(
            "What type of property you are posting?",
            color = newBlack,
            fontSize = constants.textUnit(24),
            fontFamily = constants.fontFamily(0),
            modifier = Modifier
                .align(Alignment.Start)
        )

        Spacer(modifier = Modifier.padding(8.dp))

        val propertyTypes = listOf(
            PropertyType_Form2(
                id = 1,
                title = "Residential",
                imageRes = R.drawable.residentialrento,
                selectedImageRes = R.drawable.residentialselectedrento
            ),
            PropertyType_Form2(
                id = 2,
                title = "Commercial",
                imageRes = R.drawable.commercialrento,
                selectedImageRes = R.drawable.comericialselectedrento
            ),
            PropertyType_Form2(
                id = 3,
                title = "Agriculture",
                imageRes = R.drawable.agrirento,
                selectedImageRes = R.drawable.agriselectedrento
            )
        )


        propertyTypes.forEachIndexed {  index , item ->
        //repeat(propertyTypes.size){




            ListItem(
                headlineContent = {
                    Text(
                        propertyTypes[index].title,
                        color = newBlack,
                        fontSize = constants.textUnit(16),
                        fontFamily = constants.fontFamily(2),
                        modifier = Modifier

                    )
                },
                leadingContent = {
                    SubcomposeAsyncImage(
                        model = if (onSelected_ProType.value  == item.id) {
                            if (postFlow.value == PostFlow.EDIT || postFlow.value == PostFlow.REQUESTMEDIA)
                                propertyTypes[index].imageRes
                            else
                                propertyTypes[index].selectedImageRes
                        }

                        else {
                            propertyTypes[index].imageRes
                        }
                        ,""
                        , modifier = Modifier.size(24.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(74.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .noRippleClickable {
                        if (postFlow.value == PostFlow.EDIT || postFlow.value == PostFlow.REQUESTMEDIA){
                            /// click not allowed
                        }
                        else {
                            if (!isLoading.value) {


                                constants.PostProperty_ViewModel.set_onSelected_ProType(item.id)
                                constants.PostProperty_ViewModel.set2formError(false)
                                constants.PostProperty_ViewModel.select_Land_Type(item.id)

                                constants.PostProperty_ViewModel.LandSubType_Selected_Click(-1)
                                println("ISLOADING ONLCICKwewew__${propertyTypes[index].id} ${item.id}****** --- $onSelected_ProType")
                            }
                            println("ISLOADING ONLCICK_${propertyTypes[index].id}_ ${isLoading}--- $onSelected_ProType")
                        }

                    }
                    then (
                        if (errorState) {
                            Modifier.border(
                                    1.dp,
                            Brush.verticalGradient(newRedGradienBorder),
                            RoundedCornerShape(8.dp)
                            )
                        }
                        else {
                            Modifier .border(
                                1.dp,
                                if (onSelected_ProType.value  == item.id) {
                                    if (postFlow.value == PostFlow.EDIT || postFlow.value == PostFlow.REQUESTMEDIA)
                                        rentoDarkGray
                                    else
                                    newBlue
                                }
                                else {
                                    Color(0xffE8E8E8)
                                     },
                                RoundedCornerShape(8.dp)
                            )
                        }

                    )
                , colors = ListItemColors(
                    containerColor = if (errorState) Color.White
                    else if (onSelected_ProType.value == item.id) {
                        if (postFlow.value == PostFlow.EDIT || postFlow.value == PostFlow.REQUESTMEDIA)
                            rentoLightGray
                        else
                            Color(0xffF7F0DC)
                    }
                    else {
                        Color.White
                    },
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

            Spacer(modifier = Modifier.padding(8.dp))

            AnimatedVisibility(
                visible = onSelected_ProType.value == item.id ,
                enter = slideInVertically(
                    initialOffsetY = { it },
                   // animationSpec = tween(durationMillis = 100, easing = LinearOutSlowInEasing)
                )
                , exit = ExitTransition.None
                , modifier = Modifier.padding(bottom = 16.dp)
            )
            {
                FlowRow(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    when (onSelected_ProType.value) {
                        1 -> {
                            if (isLoadingSub.value){
                                Box(modifier = Modifier
                                    .height(200.dp)
                                    .fillMaxWidth()
                                    , contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                            else {
                                residential_Options_List.value?.forEachIndexed { index, option ->
                                    Box(
                                        modifier = Modifier
                                            .wrapContentSize()
                                            .noRippleClickable(enabled = !isLoadingSub.value && !isLoading.value) {
                                                if (postFlow.value == PostFlow.EDIT || postFlow.value == PostFlow.REQUESTMEDIA){
                                                    /// click not allowed
                                                }
                                                else {

                                                    constants.PostProperty_ViewModel.set2formError(
                                                        false
                                                    )
                                                    constants.PostProperty_ViewModel.pp_SecondForm_Residential_Select_Option(
                                                        option.land_categorie_id
                                                    )
                                                    constants.PostProperty_ViewModel.select_Land_Cat_Id(
                                                        option.land_categorie_id
                                                    )

                                                    /// new one using id
                                                    constants.PostProperty_ViewModel.LandSubType_Selected_Click(
                                                        option.land_categorie_id
                                                    )
                                                    println("QWERTY _-Resi ${constants.PostProperty_ViewModel.get_Selected_Land_Cat_Id()}")
                                                }
                                            }
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(
                                                if (
                                                    selected_LandSubType.value == option.land_categorie_id
                                                //option.on_Selected
                                                )
                                                {
                                                    if (postFlow.value == PostFlow.EDIT || postFlow.value == PostFlow.REQUESTMEDIA)
                                                        rentoLightGray
                                                    else
                                                        Color(0xffF7F0DC)
                                                }
                                                else {
                                                    Color.White
                                                }
                                            )
                                            .border(
                                                1.dp, if (
                                                    selected_LandSubType.value == option.land_categorie_id
                                                //option.on_Selected
                                                ) {
                                                    if (postFlow.value == PostFlow.EDIT || postFlow.value == PostFlow.REQUESTMEDIA)
                                                        rentoDarkGray
                                                    else
                                                        newBlue
                                                    // newBlue
                                                } else {
                                                    newGray
                                                }, RoundedCornerShape(4.dp)
                                            )
                                            .padding(horizontal = 8.dp, vertical = 4.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            option.name,
                                            color =
//                                                if (
//                                                selected_LandSubType.value == option.land_categorie_id
//                                                //option.on_Selected
//                                                ) {
//                                                if (postFlow.value == PostFlow.EDIT || postFlow.value == PostFlow.REQUESTMEDIA)
//                                                    rentoDarkGray
//                                                else
//                                                    newBlue
//                                                //newBlue
//                                            }
//                                            else
//                                            {
                                                    newBlack
//                                                              }
                                            , fontSize = constants.textUnit(14),
                                            fontFamily = constants.fontFamily(2),
                                            modifier = Modifier
                                        )
                                    }
                                }
                            }
                        }

                        2 -> {
                            if (isLoadingSub.value){
                                Box(modifier = Modifier
                                    .height(200.dp)
                                    .fillMaxWidth()
                                    , contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                            else {
                                residential_Options_List.value?.forEachIndexed { index, option ->
                                    Box(
                                        modifier = Modifier
                                            .wrapContentSize()
                                            .noRippleClickable(enabled = !isLoadingSub.value && !isLoading.value) {
                                                if (postFlow.value == PostFlow.EDIT || postFlow.value == PostFlow.REQUESTMEDIA){
                                                    /// click not allowed
                                                }
                                                else {
                                                    constants.PostProperty_ViewModel.set2formError(
                                                        false
                                                    )
                                                    constants.PostProperty_ViewModel.pp_SecondForm_Residential_Select_Option(
                                                        option.land_categorie_id
                                                    )
                                                    constants.PostProperty_ViewModel.select_Land_Cat_Id(
                                                        option.land_categorie_id
                                                    )

                                                    /// new one using id
                                                    constants.PostProperty_ViewModel.LandSubType_Selected_Click(
                                                        option.land_categorie_id
                                                    )

                                                    println("QWERTY _-Commercial ${constants.PostProperty_ViewModel.get_Selected_Land_Cat_Id()}")
                                                }
                                            }
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(
                                                if (
                                                    selected_LandSubType.value == option.land_categorie_id
                                                //option.on_Selected
                                                ) {
                                                    if (postFlow.value == PostFlow.EDIT || postFlow.value == PostFlow.REQUESTMEDIA)
                                                        rentoLightGray
                                                    else
                                                        Color(0xffF7F0DC)
                                                }
                                                else {
                                                    Color.White
                                                }
                                            )
                                            .border(
                                                1.dp, if (
                                                    selected_LandSubType.value == option.land_categorie_id
                                                //option.on_Selected
                                                ) {
                                                    if (postFlow.value == PostFlow.EDIT || postFlow.value == PostFlow.REQUESTMEDIA)
                                                        rentoDarkGray
                                                    else
                                                        newBlue
                                                    // newBlue
                                                } else {
                                                    newGray
                                                }, RoundedCornerShape(4.dp)
                                            )
                                            .padding(horizontal = 8.dp, vertical = 4.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            option.name,
                                            color =
//                                                if (
//                                                selected_LandSubType.value == option.land_categorie_id
//                                            //option.on_Selected
//                                            ) {
//                                                if (postFlow.value == PostFlow.EDIT || postFlow.value == PostFlow.REQUESTMEDIA)
//                                                    rentoDarkGray
//                                                else
//                                                    newBlue
////                                                newBlue
//                                            }
//                                            else {
                                                newBlack
//                                                           }
                                            , fontSize = constants.textUnit(14),
                                            fontFamily = constants.fontFamily(2),
                                            modifier = Modifier
                                        )
                                    }
                                }
                            }

                        }

                        3 -> {
                            if (isLoadingSub.value){
                                Box(modifier = Modifier
                                    .height(200.dp)
                                    .fillMaxWidth()
                                    , contentAlignment = Alignment.Center
                                ){
                                    CircularProgressIndicator()
                                }
                            }
                            else {
                                residential_Options_List.value?.forEachIndexed { index, option ->
                                    Box(
                                        modifier = Modifier
                                            .wrapContentSize()
                                            .noRippleClickable(enabled = !isLoadingSub.value && !isLoading.value) {
                                                if (postFlow.value == PostFlow.EDIT || postFlow.value == PostFlow.REQUESTMEDIA){
                                                    /// click not allowed
                                                }
                                                else {
                                                    constants.PostProperty_ViewModel.set2formError(
                                                        false
                                                    )
                                                    constants.PostProperty_ViewModel.pp_SecondForm_Residential_Select_Option(
                                                        option.land_categorie_id
                                                    )
                                                    constants.PostProperty_ViewModel.select_Land_Cat_Id(
                                                        option.land_categorie_id
                                                    )

                                                    /// new one using id
                                                    constants.PostProperty_ViewModel.LandSubType_Selected_Click(
                                                        option.land_categorie_id
                                                    )
                                                    println("QWERTY _-Agri ${constants.PostProperty_ViewModel.get_Selected_Land_Cat_Id()}")
                                                }
                                            }
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(
                                                if (
                                                    selected_LandSubType.value == option.land_categorie_id
                                                //option.on_Selected
                                                ) {
                                                    if (postFlow.value == PostFlow.EDIT || postFlow.value == PostFlow.REQUESTMEDIA)
                                                        rentoLightGray
                                                    else
                                                        Color(0xffF7F0DC)
                                                }
                                                else {
                                                    Color.White
                                                }
                                            )
                                            .border(
                                                1.dp, if (
                                                    selected_LandSubType.value == option.land_categorie_id
                                                //option.on_Selected
                                                ) {
                                                    if (postFlow.value == PostFlow.EDIT || postFlow.value == PostFlow.REQUESTMEDIA)
                                                        rentoDarkGray
                                                    else
                                                        newBlue
//                                                    newBlue
                                                }
                                                else {
                                                    newGray
                                                               }, RoundedCornerShape(4.dp)
                                            )
                                            .padding(horizontal = 8.dp, vertical = 4.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            option.name,
                                            color =
//                                                if (
//                                                selected_LandSubType.value == option.land_categorie_id
//                                            //option.on_Selected
//                                            ) {
//                                                if (postFlow.value == PostFlow.EDIT || postFlow.value == PostFlow.REQUESTMEDIA)
//                                                    rentoDarkGray
//                                                else
//                                                    newBlue
////                                                newBlue
//                                            }
//                                            else {
                                                newBlack
//                                                           }
                                            , fontSize = constants.textUnit(14),
                                            fontFamily = constants.fontFamily(2),
                                            modifier = Modifier
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        constants.spacer(4)

        if (errorState){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                , verticalAlignment = Alignment.CenterVertically
                , horizontalArrangement = Arrangement.Start
            ) {
                Image(painter = painterResource(R.drawable.errorinforento) , "",
                    modifier = Modifier.size(12.dp))

                constants.spacer(8)

                CommonText("please select type of property you want to post.",
                    Color.Red,
                    12,
                    2
                )
            }
        }

    }

    if (changes_Not_Allowed.value) {
        ModalBottomSheet(
            onDismissRequest = {
                changes_Not_Allowed.value = false
            },
            containerColor = Color.White
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                , verticalArrangement = Arrangement.spacedBy(8.dp)
                , horizontalAlignment = Alignment.Start
            ){
//                Image(painter = painterResource(R.drawable.chnages_not_allowed) , "")

                CommonText("Trying to Change Property Type?",
                    newBlack,
                    18,
                    1
                    , modifier = Modifier
                        .padding(horizontal = 8.dp)
                )

                constants.spacer(4)

                Text("Changing the property type will clear all the details you’ve entered. The form will reset like a new one."
                    , modifier = Modifier
                        .padding(horizontal = 8.dp)
                    , textAlign = TextAlign.Start
                )
                constants.spacer(4)

                val state = remember { mutableStateOf(-1) }

                Static_Bottom(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    ) {

                        Box(
                            modifier = Modifier
                                //.fillMaxWidth(.9f)
                                .weight(4f)
                                .fillMaxHeight(.6f)
                                .background(Color(0xffE8E8E8))
                                .noRippleClickable {
                                    println(
                                        "TYPE CHANGE -ONE- ${constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value.first} -- ${constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value.second} -##$$#$-" +
                                                "--- ${constants.PostProperty_ViewModel.get_Land_Type()} --- ${constants.PostProperty_ViewModel.get_Selected_Land_Cat_Id()} --- ${constants.PostProperty_ViewModel.get_LandSubType_Selected_Click()}"
                                    )
                                    constants.PostProperty_ViewModel.select_Land_Type(constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value.first)
                                    constants.PostProperty_ViewModel.select_Land_Cat_Id(constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value.second)
                                    constants.PostProperty_ViewModel.LandSubType_Selected_Click(
                                        constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value.second
                                    )
                                    println(
                                        "TYPE CHANGE -Two- ${constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value.first} -- ${constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value.second} -##$$#$-" +
                                                "--- ${constants.PostProperty_ViewModel.get_Land_Type()} --- ${constants.PostProperty_ViewModel.get_Selected_Land_Cat_Id()} --- ${constants.PostProperty_ViewModel.get_LandSubType_Selected_Click()}"
                                    )

                                    changes_Not_Allowed.value = false

                                }, contentAlignment = Alignment.Center
                        ) {
                                Text("Cancel", color = newBlack)
                        }

                        Spacer(modifier = Modifier.weight(1.8f))
                        Box(
                            modifier = Modifier
                                //.fillMaxWidth(.9f)
                                .weight(4f)
                                .fillMaxHeight(.6f)
                                .clip(RoundedCornerShape(4.dp))
                                .border(1.dp , Brush.linearGradient(newPurpleGradientBorder), RoundedCornerShape(4.dp))
                                .background(Brush.verticalGradient(newPurpleGradient))
                                .noRippleClickable {

                                    if (network.value == NetworkStatus.Online) {
                                        if (constants.PostProperty_ViewModel.get_Selected_Land_Cat_Id() != -1) {
                                            println("2nd FORM _ CLICKER _ ${constants.PostProperty_ViewModel.selected_Land_Cat_Id.value}")
                                            post_Form_2_API_Call { result ->
                                                when (result) {
                                                    0 -> {
                                                        println("FAILURE")
                                                    }

                                                    1 -> {
                                                        println("SUCCESS")

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
                                                            LatLng(0.0, 0.0)
                                                        )
                                                        constants.PostProperty_ViewModel.onNextPPForm()
                                                    }
                                                }
                                            }
                                        } else {
                                            toast("Select type of property")
                                        }
                                    } else {
                                        toast(constants.activity.getString(R.string.no_Internet))
                                    }
                                }, contentAlignment = Alignment.Center
                        ) {
                            if (state.value == 0) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = newWhite
                                )
                            } else {
                                Text("Change Anyway", color = newWhite)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun PP_Third_Form(isLoading: State<Boolean>, show_Map_view: MutableState<Boolean>) {


    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }



    //val pincode = constants.Start_Up_ViewModel.pincode.collectAsState()
    val context = LocalContext.current
    val placesClient = Places.createClient(context)



    Column {
        Row(
            modifier = Modifier
                .align(Alignment.Start)
                .wrapContentSize()
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0XFFF4F4F4))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        )
        {
            Text(
                "Step 3",
                color = newBlue,
                fontSize = constants.textUnit(12),
                fontFamily = constants.fontFamily(0)
            )
            Text(
                "/7",
                color = Color(0xff666666),
                fontSize = constants.textUnit(12),
                fontFamily = constants.fontFamily(0)
            )
        }

        Spacer(modifier = Modifier.padding(4.dp))

        Text(
            "Where your property Located?",
            color = newBlack,
            fontSize = constants.textUnit(24),
            fontFamily = constants.fontFamily(0),
            modifier = Modifier
                .align(Alignment.Start)
        )

        Spacer(modifier = Modifier.padding(8.dp))

         PincodePlaceSearch(placesClient, Geocoder(context, Locale.ENGLISH), show_Map_view)

    }

}


@Composable
fun PP_Sixth_Form(isLoading: State<Boolean>) {


    val budget_Price = constants.PostProperty_ViewModel.budget_Price_PF5.collectAsState()
    val price_Negotiation = constants.PostProperty_ViewModel.price_Negotiation_PF5.collectAsState()

    Column {
        Row(
            modifier = Modifier
                .align(Alignment.Start)
                .wrapContentSize()
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0XFFF4F4F4))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        )
        {
            Text(
                "Step 6",
                color = newBlue,
                fontSize = constants.textUnit(12),
                fontFamily = constants.fontFamily(0)
            )
            Text(
                "/7",
                color = Color(0xff666666),
                fontSize = constants.textUnit(12),
                fontFamily = constants.fontFamily(0)
            )
        }

        Spacer(modifier = Modifier.padding(4.dp))

        Text(
            "Give your property a price",
            color = newBlack,
            fontSize = constants.textUnit(24),
            fontFamily = constants.fontFamily(0),
            modifier = Modifier
                .align(Alignment.Start)
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                "Price",
                color = newBlack,
                fontSize = constants.textUnit(16),
                fontFamily = constants.fontFamily(1),
                modifier = Modifier
            )
            Text("*" , color = Color.Red)
        }

        Spacer(modifier = Modifier.padding(8.dp))


        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(1.dp, newGray, RoundedCornerShape(8.dp))
            , verticalAlignment = Alignment.CenterVertically
        )
        {
            Box(
                modifier = Modifier
                    .weight(1.5f)
                    .fillMaxHeight()
                    .background(newLightGray)
                , contentAlignment = Alignment.Center
            ){
                Text("\u20B9")
            }

            VerticalDivider()

            Box(
                modifier = Modifier
                    .weight(9f)
                    .fillMaxHeight()
                    .background(newLightBlue)
                , contentAlignment = Alignment.Center
            ){
                TextField(
                    value = budget_Price.value,
                    onValueChange = {
                        if(it.length < 500000000) {
                            constants.PostProperty_ViewModel.put_budget_Price_PF5(it)
                        }
                    },
                    placeholder = {
                        Text("Enter property price"
                            , color = Color(0xff969696)
                            , fontSize = constants.textUnit(12)
                        , fontFamily = constants.fontFamily(2)
                        )
                    }
                    ,keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    singleLine = true
                    ,colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedTextColor = newBlack,
                      focusedTextColor = newBlack
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Row (
            modifier = Modifier
                .align(Alignment.Start)
            , verticalAlignment = Alignment.CenterVertically
        ){
            Checkbox(
                checked = price_Negotiation.value,
                onCheckedChange = {
                    constants.PostProperty_ViewModel.check_Price_Negotiation(it)
                }
            )

            Text("Price Negotiable" , color = newBlack , fontSize = constants.textUnit(12) , fontFamily = constants.fontFamily(2))
        }
    }
}



@Composable
fun StepHeader() {
    Row(
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(4.dp))
            .background(Color(0XFFF4F4F4))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Step 7",
            color = newBlue,
            fontSize = constants.textUnit(12),
            fontFamily = constants.fontFamily(0)
        )
        Text(
            "/7",
            color = Color(0xff666666),
            fontSize = constants.textUnit(12),
            fontFamily = constants.fontFamily(0)
        )
    }

    Spacer(modifier = Modifier.padding(4.dp))

    Text(
        "Add photos / videos of property",
        color = newBlack,
        fontSize = constants.textUnit(24),
        fontFamily = constants.fontFamily(0)
    )

    Spacer(modifier = Modifier.padding(4.dp))

    Text(
        "(Optional)",
        color = Color(0xff7E7E7E),
        fontSize = constants.textUnit(14),
        fontFamily = constants.fontFamily(1)
    )

//    Spacer(modifier = Modifier.padding(4.dp))
//
//    Text(
//        buildAnnotatedString {
//            withStyle(SpanStyle(color = newBlack)) { append("Upload Video / Photos ") }
//            withStyle(SpanStyle(color = Color.Red)) { append("* ") }
//            withStyle(SpanStyle(color = Color(0xff666666))) { append("(minimum 3 photos)") }
//        },
//        fontSize = constants.textUnit(16),
//        fontFamily = constants.fontFamily(1)
//    )

    Spacer(modifier = Modifier.padding(8.dp))

    Text(
        "Upload video max of 25mb and upload photo at least 3 and up to 10 photos. Each photo should be under 1mb. Supported formats mp4, png, jpeg.",
        color = Color(0xff666666),
        fontSize = constants.textUnit(12),
        fontFamily = constants.fontFamily(2)
        , textAlign = TextAlign.Start
    )
}


@Composable
fun UploadBox(
    mediaItems: List<UploadPropertyMedia>,
    canAddPhotos: Boolean,
    canAddVideos: Boolean,
    onPickImage: () -> Unit,
    onPickVideo: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
            .dashedBorder( // you have your helper; otherwise implement custom
                strokeWidth = 2.dp,
                dashLength = 10.dp,
                gapLength = 5.dp,
                color = newBlue,
                cornerRadius = 12.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // upload graphic
            //Text("Upload Photo / Video", fontSize = 16.sp)

            Image(painter = painterResource(R.drawable.mediauploadicon) , ""
                , modifier = Modifier.size(46.dp)
            )

            constants.spacer(8)

            Row {

                Box(
                    modifier = Modifier
                        .width(132.dp)
                        .height(46.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White)
                        .border(1.dp, newBlue, RoundedCornerShape(4.dp))
                        .noRippleClickable {
                            onPickImage()
                        }
                        , contentAlignment = Alignment.Center
                ){
                    CommonText("Upload Photo",
                        newBlue,
                        14,
                        0)
                }
//                Button(
//                    onClick = onPickImage,
//                    enabled = canAddPhotos
//                ) { Text("Upload Photo") }

                constants.spacer(8)

                Box(
                    modifier = Modifier
                        .width(132.dp)
                        .height(46.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Brush.verticalGradient(newPurpleGradient))
                        .border(
                            1.dp,
                            Brush.verticalGradient(newPurpleGradientBorder),
                            RoundedCornerShape(4.dp)
                        )
                        .noRippleClickable {
                            onPickVideo()
                        }
                    , contentAlignment = Alignment.Center
                ){
                    CommonText("Upload Video",
                        Color.White,
                        14,
                        0)
                }

//                Button(
//                    onClick = onPickVideo,
//                    enabled = canAddVideos
//                ) { Text("Upload Video") }
            }

            Spacer(Modifier.height(8.dp))
//            Text("Images: ${mediaItems.count { !it.isVideo }} / 10 — Videos: ${mediaItems.count { it.isVideo }} / 5", fontSize = 12.sp)
        }
    }
}







data class PhotoHeadingItem(
    val title: String,
    val isSelected: MutableState<Boolean> = mutableStateOf(false)
)




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PP_Seventh_Form(
    onHover: MutableState<Boolean>,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val mediaItems by constants.PostProperty_ViewModel.mediaList.collectAsState()
    val headingItems by constants.PostProperty_ViewModel.headingItems.collectAsState()
    val coverId by constants.PostProperty_ViewModel.coverPhotoId.collectAsState()

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val headingDropDown = remember { mutableStateOf(false) }

    // image/video pickers
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments()
    ) { uris ->
        if (uris.isNotEmpty()) {
            constants.PostProperty_ViewModel.addImagesAndUpload(context, uris)
        }
    }
    val videoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let { constants.PostProperty_ViewModel.addVideoAndUpload(context, it) }
    }

    var selectedMediaId by remember { mutableStateOf<String?>(null) }

    var apiState = remember { mutableStateOf(0) }


    Column(modifier = Modifier.fillMaxSize()) {
        // Header
        StepHeader()

        Spacer(Modifier.height(16.dp))

//        UploadBox(
//            mediaItems = mediaItems,
//            canAddPhotos = mediaItems.count { !it.isVideo } < 10,
//            canAddVideos = mediaItems.count { it.isVideo } < 5,
//            onPickImage = { imagePickerLauncher.launch(arrayOf("image/*")) },
//            onPickVideo = { videoPickerLauncher.launch(arrayOf("video/*")) }
//        )
        UploadBox(
            mediaItems = mediaItems,
            canAddPhotos = mediaItems.count { !it.isVideo } < 10,
            canAddVideos = mediaItems.count { it.isVideo } < 5,

            onPickImage = {
                if (mediaItems.count { !it.isVideo } >= 10) {
                    toast("You can upload a maximum of 10 images")
                } else {
                    imagePickerLauncher.launch(arrayOf("image/*"))
                }
            },

            onPickVideo = {
                if (mediaItems.count { it.isVideo } >= 5) {
                    toast("You can upload a maximum of 5 videos")
                } else {
                    videoPickerLauncher.launch(arrayOf("video/*"))
                }
            }
        )


        Spacer(Modifier.height(16.dp))

        println("Heer crash find -- ${mediaItems}")

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxHeight()
        ) {
            itemsIndexed(
                items = mediaItems,
                key = { _, item -> item.id }
            ) { index, media ->
                if (!media.isVideo) {
                    PhotoItem(
                        index = index,
                        media = media,
                        onClickHeading = {
                            selectedMediaId = media.id
                            headingDropDown.value = true
                        },
                        onCoverSelected = { id -> constants.PostProperty_ViewModel.setCoverPhoto(id) },
                        onDelete = { constants.PostProperty_ViewModel.deleteMedia(media.id) },
                        onHoverClicked = onHover
                    )
                } else {
                    val thumbnailBitmap = remember(media.localUri) {
                        if (media.localUri != null) {
                            getVideoThumbnail(context, media.localUri!!)
                        }
                        else {
                            getVideoThumbnailString(context , media.uploadedUrl?:"")
                        }
                        // if you have a blocking thumbnail function, better to call in LaunchedEffect and store elsewhere
                    }
                    VideoItem(
                        index = index,
                        media = media,
                        thumbnail = thumbnailBitmap,
                        onClickHeading = {
                            selectedMediaId = media.id
                            headingDropDown.value = true
                        },
                        onDelete = { constants.PostProperty_ViewModel.deleteMedia(media.id) },
                        onHoverClicked = onHover
                    )
                }
            }
        }

        Spacer(Modifier.weight(1f))

        SnackbarHost(hostState = snackbarHostState, modifier = Modifier.align(Alignment.CenterHorizontally))
    }

    // bottom sheet heading selection (api driven)
    if (headingDropDown.value) {

        if (headingItems.isEmpty()) {
            LaunchedEffect(Unit) {
                constants.API_Vm.getPhotoHeadings()
                { apiResultHandling ->
                    when (apiResultHandling) {
                        is API_Result_Handling.Loading -> {
                            //constants.Common_H_ViewModel.changeStatus(true)

                            //resultCallback(2)
                            apiState.value = 0
                        }

                        is API_Result_Handling.NoData -> {
                            //constants.Common_H_ViewModel.changeStatus(false)
                            apiState.value = 3
                        }

                        is API_Result_Handling.Error -> {
                            // constants.Common_H_ViewModel.changeStatus(false)
                            apiState.value = 2
                            // resultCallback(1)
                        }

                        is API_Result_Handling.Success -> {
                            //constants.Common_H_ViewModel.changeStatus(false)
                            apiState.value = 1
                            // resultCallback(0)
                        }

                        is API_Result_Handling.Deactivated -> {
                            // resultCallback(5)
                            apiState.value = 4
                        }
                    }
                }
            }
        }
        ModalBottomSheet(
            sheetState = bottomSheetState,
            onDismissRequest = { headingDropDown.value = false }
        ) {
            Text("What is this photo about?", fontSize = 18.sp, modifier = Modifier.padding(16.dp))

            constants.spacer(8)

            when (apiState.value) {
                0 -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(600.dp)
                        , contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                1 -> {
                  /*  Column(
                        modifier = Modifier
                            .fillMaxWidth()
                        , horizontalAlignment = Alignment.Start
                        , verticalArrangement = Arrangement.spacedBy(8.dp)
                    )
                    {

                        headingItems.forEach { item ->

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                Text(item.title, fontSize = constants.textUnit(16))

                                RadioButton(
                                    selected = selectedMediaId?.let { id ->
                                        constants.PostProperty_ViewModel.mediaList.value
                                            .find { it.id == id }?.heading == item.title
                                    } ?: false,
                                    onClick = {
                                        selectedMediaId?.let { id ->
                                            constants.PostProperty_ViewModel.updateHeading(id, item.title)
                                        }
                                        headingDropDown.value = false
                                    }
                                )
                            }


                            constants.spacer(8)
                        }

                    }*/

                    val headings = if (headingItems.isEmpty()) {
                        // fallback to local defaults if api not loaded yet
                        listOf("Kitchen", "Bedroom", "Floor Plan", "Balcony", "Building", "Bathroom", " Entrance" , "Property Layout" )
                    } else headingItems.map { it.title }

                    Column(modifier = Modifier.padding(horizontal = 8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        headings.forEach { title ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(title, fontSize = 16.sp)
                                RadioButton(
                                    selected = selectedMediaId?.let { id ->
                                        constants.PostProperty_ViewModel.mediaList.value.find { it.id == id }?.heading == title
                                    } ?: false,
                                    onClick = {
                                        selectedMediaId?.let { id ->
                                            constants.PostProperty_ViewModel.updateHeading(id, title)
                                        }
                                        headingDropDown.value = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(32.dp))

                    constants.spacer(8)
                }
                2 -> {
                    /// error
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(600.dp), contentAlignment = Alignment.Center
                    ) {
                        Text("Something went wrong")
                    }
                }
                3 -> {
                    // empty
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(600.dp), contentAlignment = Alignment.Center
                    ) {
                        Text("No Data Available")
                    }
                }
            }
        }
    }

    if (onHover.value) {
       // val mediaItems by constants.PostProperty_ViewModel.mediaList.collectAsState()

        UserMediaPreview(mediaList = mediaItems, onClose = {
            onHover.value = false
        })
    }
}



@Composable
fun VideoItem(
    index: Int,
    media: UploadPropertyMedia,
    thumbnail: Bitmap?,
    onClickHeading: (Int) -> Unit,
    onDelete: (Int) -> Unit,
    onHoverClicked : MutableState<Boolean>
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .border(1.dp, Color(0xFFDDDDDD), RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .size(172.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray)) {
           // val imageModel = media.uploadedUrl ?: media.localUri
            AsyncImage(
                model = thumbnail,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .noRippleClickable {
                        onHoverClicked.value = true
                    },
                contentScale = ContentScale.Crop
            )

            // Show video icon overlay
            Icon(painter = painterResource(id = R.drawable.play_arrow), contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(36.dp), tint = Color.White)

            if (media.isUploading) {
                Box(
                    Modifier
                        .matchParentSize()
                        .background(Color(0x99000000))
                ) {
                    Column(Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(progress = media.uploadProgress / 100f)
                        Spacer(Modifier.height(8.dp))
                        Text("${media.uploadProgress}%", color = Color.White)
                    }
                }
            }
        }

        Spacer(Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .height(28.dp)
                .width(172.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xffEBEBEB))
                .clickable { onClickHeading(index) }
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(media.heading.ifEmpty { "Select Heading" }, fontSize = 14.sp)
            Icon(painter = painterResource(id = R.drawable.arrowdown), contentDescription = null, modifier = Modifier.size(16.dp))
        }

        Row(modifier = Modifier
            .height(28.dp)
            .width(172.dp)
            , verticalAlignment = Alignment.CenterVertically
            , horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
                , modifier = Modifier
                    .alpha(0.6f)
            ) {
                Checkbox(checked = false, onCheckedChange = null, enabled = false)
                Spacer(Modifier.width(6.dp))
                Text("Cover", fontSize = 12.sp, color = Color.Gray)
            }

            Icon(painter = painterResource(R.drawable.reelsdelete), contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clickable { onDelete(index) })
        }
    }
}


@Composable
fun PhotoItem(
    index: Int,
    media: UploadPropertyMedia,
    onClickHeading: (Int) -> Unit,
    onCoverSelected: (String) -> Unit,
    onDelete: (Int) -> Unit,
    onHoverClicked : MutableState<Boolean>
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .border(1.dp, Color(0xFFDDDDDD), RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .size(172.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray)) {
            val imageModel = media.uploadedUrl ?: media.localUri
            AsyncImage(
                model = imageModel,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .noRippleClickable {
                        onHoverClicked.value = true
                    },
                contentScale = ContentScale.Crop
            )

            if (media.isUploading) {
                // overlay progress
                Box(
                    Modifier
                        .matchParentSize()
                        .background(Color(0x99000000))
                ) {
                    Column(Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(progress = media.uploadProgress / 100f)
                        Spacer(Modifier.height(8.dp))
                        Text("${media.uploadProgress}%", color = Color.White)
                    }
                }
            }
        }

        Spacer(Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .height(28.dp)
                .width(172.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xffEBEBEB))
                .clickable { onClickHeading(index) }
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(media.heading.ifEmpty { "Select Heading" }, fontSize = 14.sp)
            Icon(painter = painterResource(id = R.drawable.arrowdown), contentDescription = null, modifier = Modifier.size(16.dp))
        }

        Row(
            modifier = Modifier
                .height(28.dp)
                .width(172.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
                , horizontalArrangement = Arrangement.Start
            ) {
                Checkbox(
                    checked = media.isCover,
                    onCheckedChange = { onCoverSelected(media.id) },
                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF1E88E5))
                )
                Spacer(Modifier.width(6.dp))

                Text("Cover", fontSize = 12.sp)
            }

            Icon(painter = painterResource(R.drawable.reelsdelete), contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clickable { onDelete(index) }
            )
        }
    }
}



@Composable
fun UserMediaPreview(
    mediaList: List<UploadPropertyMedia>,
    modifier: Modifier = Modifier,
    onClose : () -> Unit
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val pagerState = rememberPagerState(pageCount = { mediaList.size })
    val listState = rememberLazyListState()

    // Map of ExoPlayers
    val playerMap = remember(mediaList) {
        mediaList.mapIndexedNotNull { index, media ->
            if (media.isVideo) {
                index to ExoPlayer.Builder(context).build().apply {
                    val mediaItem = MediaItem.fromUri(media.uploadedUrl ?: "")
                    setMediaItem(mediaItem)
                    prepare()
                    playWhenReady = false
                }
            } else null
        }.toMap()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
        , verticalArrangement = Arrangement.Center
        , horizontalAlignment = Alignment.CenterHorizontally
    )
    {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.Black)
                .padding(horizontal = 16.dp)
        ){
            Image(painter = painterResource(R.drawable.close) , "",
                colorFilter = ColorFilter.tint(
                    Color.White
                )
                , modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .noRippleClickable {
                        onClose()
                    }
            )
        }

        // Main HorizontalPager
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(8.5f)
                .padding(horizontal = 16.dp)
        )
        { page ->
            val media = mediaList[page]

            Box(modifier = Modifier.fillMaxSize()
                , contentAlignment = Alignment.Center) {
                if (media.isVideo) {
                    val exoPlayer = playerMap[page]!!
                    PlayerSurface(player = exoPlayer, modifier = Modifier.fillMaxSize())

                    // Play/pause overlay
                    var isPlaying by remember { mutableStateOf(false) }
                    LaunchedEffect(pagerState.currentPage) {
                        playerMap.forEach { (idx, player) ->
                            player.playWhenReady = idx == pagerState.currentPage
                        }
                        isPlaying = pagerState.currentPage == page && playerMap[page]?.isPlaying == true
                    }

                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable {
                                val player = playerMap[page]!!
                                player.playWhenReady = !player.isPlaying
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (!isPlaying) {
                            Icon(
                                painter = painterResource(R.drawable.play_arrow),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }

                } else {
                    AsyncImage(
                        model = media.uploadedUrl ?: media.localUri,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

//        Spacer(modifier = Modifier.height(8.dp))

        constants.spacer(16)

        // Indicator
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            repeat(mediaList.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(if (pagerState.currentPage == index) Color.White else Color.Gray)
                )
            }
        }

//        Spacer(modifier = Modifier.height(8.dp))

        constants.spacer(16)

        // Thumbnails LazyRow
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .padding(horizontal = 8.dp)
            ,state = listState,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        )
        {
            itemsIndexed(mediaList) { index, media ->
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(
                            width = if (pagerState.currentPage == index) 2.dp else 0.dp,
                            color = if (pagerState.currentPage == index) Color.White else Color.Transparent,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            scope.launch { pagerState.animateScrollToPage(index) }
                        }
                ) {
                    AsyncImage(
                        model = media.uploadedUrl ?: media.localUri,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    if (media.isVideo) {
                        Icon(
                            painter = painterResource(R.drawable.play_arrow),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }

    // Scroll thumbnail LazyRow to center current page
    LaunchedEffect(pagerState.currentPage) {
        playerMap.forEach { (idx, player) ->
            if (idx == pagerState.currentPage) {
                player.seekTo(0)
                player.playWhenReady = true
            } else {
                player.playWhenReady = false
                player.pause()
                player.seekTo(0) // reset to start
            }
        }

        // Scroll thumbnail LazyRow
        listState.animateScrollToItem(pagerState.currentPage)
    }


    // Release players
    DisposableEffect(Unit) {
        onDispose {
            playerMap.values.forEach { it.release() }
        }
    }

    BackHandler() {
        onClose()
    }
}






@Composable
fun PP_Fourth_Form(apiError: MutableState<Boolean>) {

    println("TYPE CHANGE -Threee- ${constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value.first} -- ${constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value.second} -##$$#$-" +
            "--- ${constants.PostProperty_ViewModel.get_Land_Type()} --- ${constants.PostProperty_ViewModel.get_Selected_Land_Cat_Id()} --- ${constants.PostProperty_ViewModel.get_LandSubType_Selected_Click()}")


    val field_Data = constants.PostProperty_ViewModel.postFormCommon.collectAsStateWithLifecycle()
   // val field_Data_Com = constants.PostProperty_ViewModel.pp_form_Commercial_Fields.collectAsStateWithLifecycle()
   // val field_Data_Agri = constants.PostProperty_ViewModel.pp_form_Agriculture_Fields.collectAsStateWithLifecycle()
    val api_Type = constants.PostProperty_ViewModel.selected_Land_Type_PF2.collectAsStateWithLifecycle()
    val errors = constants.PostProperty_ViewModel.errors4.collectAsState()
    val selectedLandCatId = constants.PostProperty_ViewModel.get_Selected_Land_Cat_Id()

    val state = remember(selectedLandCatId) { mutableStateOf(0) }
    val apiCallMade = remember(selectedLandCatId) { mutableStateOf(false) }

    var retry by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(selectedLandCatId) {
        println("FOURTH FORM--- $selectedLandCatId, API Type: ${api_Type.value} -${apiError}- ${state}")
    }

    // Main API call effect
    LaunchedEffect(api_Type.value, selectedLandCatId, retry) {
        if (selectedLandCatId != -1 && !apiCallMade.value) {
            apiCallMade.value = true
            state.value = 0
            println("Starting API call for type: ${api_Type.value}, landCatId: $selectedLandCatId")

            when (api_Type.value) {
                1 -> {
                    constants.API_Vm.get_post_Form4_Residential(
                        land_categorie_id = selectedLandCatId
                    ) { apiResultHandling ->
                        when (apiResultHandling) {
                            is API_Result_Handling.Loading -> {
                                state.value = 0
                                println("Residential API: Loading")
                            }
                            is API_Result_Handling.Deactivated -> {
                                println("Residential API: Deactivated")
                            }
                            is API_Result_Handling.Error -> {
                                state.value = 1
                                apiCallMade.value = false
                                println("Residential API: Error")
                            }
                            is API_Result_Handling.Success -> {
                                state.value = 2
                                println("Residential API: Success, Fields: ${field_Data.value}")
                            }
                            is API_Result_Handling.NoData -> {
                                state.value = 1
                                apiCallMade.value = false
                                println("Residential API: No Data")
                            }
                        }
                    }
                }
                2 -> {
                    constants.API_Vm.get_post_Form4_Commercial(
                        land_categorie_id = selectedLandCatId
                    ) { apiResultHandling ->
                        when (apiResultHandling) {
                            is API_Result_Handling.Loading -> {
                                state.value = 0
                                println("Commercial API: Loading")
                            }
                            is API_Result_Handling.Deactivated -> {
                                println("Commercial API: Deactivated")
                            }
                            is API_Result_Handling.Error -> {
                                state.value = 1
                                apiCallMade.value = false
                                println("Commercial API: Error")
                            }
                            is API_Result_Handling.Success -> {
                                state.value = 2
                                println("Commercial API: Success, Fields: ${field_Data.value}")
                            }
                            is API_Result_Handling.NoData -> {
                                state.value = 1
                                apiCallMade.value = false
                                println("Commercial API: No Data")
                            }
                        }
                    }
                }
                3 -> {
                    constants.API_Vm.get_post_Form4_Agriculture(
                        land_categorie_id = selectedLandCatId
                    ) { apiResultHandling ->
                        when (apiResultHandling) {
                            is API_Result_Handling.Loading -> {
                                state.value = 0
                                println("Agriculture API: Loading")
                            }
                            is API_Result_Handling.Deactivated -> {
                                println("Agriculture API: Deactivated")
                            }
                            is API_Result_Handling.Error -> {
                                state.value = 1
                                apiCallMade.value = false
                                println("Agriculture API: Error")
                            }
                            is API_Result_Handling.Success -> {
                                state.value = 2
                                println("Agriculture API: Success, Fields: ${field_Data.value}")
                            }
                            is API_Result_Handling.NoData -> {
                                state.value = 1
                                apiCallMade.value = false
                                println("Agriculture API: No Data")
                            }
                        }
                    }
                }
            }
        }
    }

    // Update active fields when data changes
    LaunchedEffect(field_Data.value) {
        next_Active_Fields.clear()
        next_Active_Fields5.clear()
        next_Active_Fields6.clear()

        when (api_Type.value) {
            1 -> {
                next_Active_Fields.addAll(constants.PostProperty_ViewModel.getActiveFieldsResidential4(field_Data.value))
                next_Active_Fields5.addAll(constants.PostProperty_ViewModel.getActiveFieldsResidential5(field_Data.value?.step_5?.firstOrNull()))
                next_Active_Fields6.addAll(constants.PostProperty_ViewModel.getActiveFieldsResidential6(field_Data.value?.step_6?.firstOrNull()))
                println("Residential Fields Active: ${next_Active_Fields.size}")
            }
            2 -> {

                next_Active_Fields.addAll(constants.PostProperty_ViewModel.getActiveFieldsCommercial4(field_Data.value))
                next_Active_Fields5.addAll(constants.PostProperty_ViewModel.getActiveFieldsCommercial5(field_Data.value?.step_5?.firstOrNull()))
                next_Active_Fields6.addAll(constants.PostProperty_ViewModel.getActiveFieldsCommercial6(field_Data.value?.step_6?.firstOrNull()))

                println("Commercial Fields Active: ${next_Active_Fields.size} -- ${next_Active_Fields5}")
            }
            3 -> {
                next_Active_Fields.addAll(constants.PostProperty_ViewModel.getActiveFieldsAgriculture4(field_Data.value))
                next_Active_Fields5.addAll(constants.PostProperty_ViewModel.getActiveFieldsAgriculture5(field_Data.value?.step_5?.firstOrNull()))
                next_Active_Fields6.addAll(constants.PostProperty_ViewModel.getActiveFieldsAgriculture6(field_Data.value?.step_6?.firstOrNull()))

//                next_Active_Fields.addAll(constants.PostProperty_ViewModel.getActiveFieldsAgriculture4(field_Data.value))
                println("Agriculture Fields Active: ${next_Active_Fields.size}")
            }
        }
    }

    when(state.value){
        0 -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        1 -> {
            apiError.value = true
            Box (
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                API_Fail_UI(onReTryClick = {
                    apiCallMade.value = false
                    retry = retry + 1
                })
            }
        }
        2 -> {
            limitGoingIn.value = false
            apiError.value = false

            LazyColumn (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            )
            {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            modifier = Modifier
                                .wrapContentSize()
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0XFFF4F4F4))
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                "Step 4",
                                color = newBlue,
                                fontSize = constants.textUnit(12),
                                fontFamily = constants.fontFamily(0)
                            )
                            Text(
                                "/7",
                                color = Color(0xff666666),
                                fontSize = constants.textUnit(12),
                                fontFamily = constants.fontFamily(0)
                            )
                        }

                        Spacer(modifier = Modifier.padding(4.dp))

                        Text(
                            "Fill Property Details",
                            color = newBlack,
                            fontSize = constants.textUnit(24),
                            fontFamily = constants.fontFamily(0),
                            modifier = Modifier
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }

                item {
                    when (api_Type.value) {
                        1 -> {
//                            next_Active_Fields.addAll(constants.PostProperty_ViewModel.getActiveFieldsResidential(field_Data.value))
                            println("Residential Fields Active: ${next_Active_Fields.size}")
                            Form4_Residential_Content(field_Data, errors)
                        }
                        2 -> {
//                            next_Active_Fields.addAll(constants.PostProperty_ViewModel.getActiveFieldsCommercial(field_Data.value))
                            println("Commercial Fields Active: ${next_Active_Fields.size}")
                            Form4_Commercial_Content(field_Data, errors)
                        }
                        3 -> {
//                            next_Active_Fields.addAll(constants.PostProperty_ViewModel.getActiveFieldsAgriculture(field_Data.value))
                            println("Agriculture Fields Active: ${next_Active_Fields.size}")
                            Form4_Agriculture_Content(field_Data, errors)
                        }
                    }
                }

//                item {
//                    Form4_Residential_Content(field_Data, errors)
//                }
//
//                item {
//                    Form4_Commercial_Content(field_Data, errors)
//                }
//
//                item {
//                    Form4_Agriculture_Content(field_Data, errors)
//                }
            }
        }
    }
}



@Composable
fun Form4_Residential_Content(
//    field_Data: State<PostProperty_Stepfour_Residential_Data?>,
    field_Data: State<PostFormCommonPropertyData?>,
    errors: State<Map<Int, Boolean>>
) {

    println("FORM $4 --- ${field_Data.value}")
    Column() {
        if(field_Data.value?.Property_Name?.isNotEmpty() == true){
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_Property_Name(is_Error = errors.value[0] == true)
            Spacer(modifier = Modifier.padding(8.dp))
        }


        if (field_Data.value?.Select_Floor_Plane?.first()?.isNotEmpty() == true){
            val floorPlans: List<Chips_Items_DC> = field_Data.value?.Select_Floor_Plane
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()

            PP_Select_Floor_Plan(data = floorPlans, isError = errors.value[1] == true)
            Spacer(modifier = Modifier.padding(8.dp))
        }



        if(field_Data.value?.Carpet_Area?.isNotEmpty() == true){
            PP_Carpet_Built_SuperBuilt_Area(isError = errors.value[2] == true)
            Spacer(modifier = Modifier.padding(8.dp))
        }



        if(field_Data.value?.Area_Dimensions?.isNotEmpty() == true){
            PP_Area_Dimensions()
            Spacer(modifier = Modifier.padding(8.dp))
        }



        if(field_Data.value?.Property_Facing?.first()?.isNotEmpty() == true){

            val propertyFacing: List<Chips_Items_DC> = field_Data.value?.Property_Facing
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()

            PP_Property_Facing(data = propertyFacing, isError = errors.value[3] == true)
            Spacer(modifier = Modifier.padding(8.dp))
        }



        if(field_Data.value?.Floor_Details?.isNotEmpty() == true){
            PP_Floor_Details(isError = errors.value[4] == true)
            Spacer(modifier = Modifier.padding(8.dp))
        }


        if(field_Data.value?.Preferred_Tenants?.isNotEmpty() == true){

            val preferredTenants: List<Chips_Items_DC> = field_Data.value?.Preferred_Tenants
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()

            PP_Preferred_Tenants(preferredTenants)
            Spacer(modifier = Modifier.padding(8.dp))
        }


        if(field_Data.value?.Available_From?.isNotEmpty() == true){
            PP_Available_From( isError = errors.value[5] == true)
            Spacer(modifier = Modifier.padding(8.dp))
        }

    }
}



@Composable
fun Form4_Commercial_Content(
//    field_Data_Com: State<Post_Property_Stepfour_Commercial_Data?>
    field_Data_Com: State<PostFormCommonPropertyData?>
    , errors: State<Map<Int, Boolean>>)
{





    if(field_Data_Com.value?.Property_Name?.isNotEmpty() == true){
//        val errors = constants.PostProperty_ViewModel.errors.collectAsState()

        PP_Property_Name(is_Error = errors.value[0] == true)
        Spacer(modifier = Modifier.padding(8.dp))
    }



    if(field_Data_Com.value?.Carpet_Area?.isNotEmpty() == true){

        PP_Carpet_Built_SuperBuilt_Area( isError = errors.value[2] == true)
        Spacer(modifier = Modifier.padding(8.dp))
    }


    if(field_Data_Com.value?.Shop_Facade?.firstOrNull()?.Facade_Width?.isNotEmpty() == true && field_Data_Com.value?.Shop_Facade?.firstOrNull()?.Facade_Height?.isNotEmpty() == true){

        PP_Shop_facade(isError = errors.value[6] == true)
        Spacer(modifier = Modifier.padding(8.dp))
    }



    if(field_Data_Com.value?.Property_Facing?.first()?.isNotEmpty() == true){

        val propertyFacing: List<Chips_Items_DC> = field_Data_Com.value?.Property_Facing
            ?.map { Chips_Items_DC(title = it) }
            ?: emptyList()

        PP_Property_Facing(data = propertyFacing, isError = errors.value[3] == true)
        Spacer(modifier = Modifier.padding(8.dp))
    }



    if(field_Data_Com.value?.Floor_Details?.isNotEmpty() == true){
        PP_Floor_Details(isError = errors.value[4] == true)
        Spacer(modifier = Modifier.padding(8.dp))
    }



    if(field_Data_Com.value?.Available_From?.isNotEmpty() == true){
        PP_Available_From(isError =  errors.value[5] == true)
        Spacer(modifier = Modifier.padding(8.dp))
    }



//    if(field_Data_Com.value?.property_ownership?.first()?.isNotEmpty() == true){
//
//        val propertyOwnership: List<Chips_Items_DC> = field_Data_Com.value?.property_ownership
//            ?.map { Chips_Items_DC(title = it) }
//            ?: emptyList()
//
//        PP_Property_Ownership(data = propertyOwnership, isError = errors.value[7] == true)
//        Spacer(modifier = Modifier.padding(8.dp))
//    }



//    if(field_Data_Com.value?.availability_status?.first()?.isNotEmpty() == true){
//
//        val availabilityStatus: List<Chips_Items_DC> = field_Data_Com.value?.availability_status
//            ?.map { Chips_Items_DC(title = it) }
//            ?: emptyList()
//
//        PP_Availability_Status(data = availabilityStatus, isError = errors.value[8] == true)
//        Spacer(modifier = Modifier.padding(8.dp))
//    }


}



@Composable
fun Form4_Agriculture_Content(
//    field_Data_Agri: State<Post_Property_Stepfour_Agriculture_Data?>
    field_Data_Agri: State<PostFormCommonPropertyData?>
    ,errors: State<Map<Int, Boolean>>)  {


    if(field_Data_Agri.value?.Property_Name?.isNotEmpty() == true){


        PP_Property_Name(is_Error = errors.value[0] == true)
        Spacer(modifier = Modifier.padding(8.dp))
    }



    if(field_Data_Agri.value?.Property_Area?.isNotEmpty() == true){

        PP_Land_Area(isError =  errors.value[7] == true)
        Spacer(modifier = Modifier.padding(8.dp))
    }



//    if(field_Data_Agri.value?.Select_Floor_Plane?.first()?.isNotEmpty() == true){
//
//        val select_floor_plane: List<Chips_Items_DC> = field_Data_Agri.value?.Select_Floor_Plane
//            ?.map { Chips_Items_DC(title = it) }
//            ?: emptyList()
//
//
//        PP_Select_Floor_Plan(data = select_floor_plane, isError = errors.value[2] == true)
//        Spacer(modifier = Modifier.padding(8.dp))
//    }


    if(field_Data_Agri.value?.Carpet_Area?.isNotEmpty() == true){

        PP_Carpet_Built_SuperBuilt_Area(isError =  errors.value[2] == true)
        Spacer(modifier = Modifier.padding(8.dp))
    }



    if(field_Data_Agri.value?.Area_Dimensions?.isNotEmpty() == true){

        PP_Area_Dimensions()
        Spacer(modifier = Modifier.padding(8.dp))
    }



    if(field_Data_Agri.value?.Property_Facing?.first()?.isNotEmpty() == true){

        val property_facing: List<Chips_Items_DC> = field_Data_Agri.value?.Property_Facing
            ?.map { Chips_Items_DC(title = it) }
            ?: emptyList()

        PP_Property_Facing(data = property_facing, isError = errors.value[3] == true)
        Spacer(modifier = Modifier.padding(8.dp))
    }



    if(field_Data_Agri.value?.Floor_Details?.isNotEmpty() == true){

        PP_Floor_Details(isError = errors.value[4] == true)
        Spacer(modifier = Modifier.padding(8.dp))
    }

    if(field_Data_Agri.value?.Available_From?.isNotEmpty() == true){
        PP_Available_From( isError = errors.value[5] == true)
        Spacer(modifier = Modifier.padding(8.dp))
    }


//    if(field_Data_Agri.value?.property_ownership?.first()?.isNotEmpty() == true){
//
//        val property_ownership: List<Chips_Items_DC> = field_Data_Agri.value?.property_ownership
//            ?.map { Chips_Items_DC(title = it) }
//            ?: emptyList()
//
//        PP_Property_Ownership(data = property_ownership, isError = errors.value[7] == true)
//        Spacer(modifier = Modifier.padding(8.dp))
//    }


//    if(field_Data_Agri.value?.availability_status?.first()?.isNotEmpty() == true){
//
//        val availability_status: List<Chips_Items_DC> = field_Data_Agri.value?.availability_status
//            ?.map { Chips_Items_DC(title = it) }
//            ?: emptyList()
//
//        PP_Availability_Status(data = availability_status, isError = errors.value[8] == true)
//        Spacer(modifier = Modifier.padding(8.dp))
//    }


}



@Composable
fun PP_Fifth_Form() {




    //// FORM flow
    val postFlow = constants.PostProperty_ViewModel.postFlow.collectAsState()

    val field_Data = constants.PostProperty_ViewModel.postFormCommon.collectAsStateWithLifecycle()
    // val field_Data_Com = constants.PostProperty_ViewModel.pp_form_Commercial_Fields.collectAsStateWithLifecycle()
    // val field_Data_Agri = constants.PostProperty_ViewModel.pp_form_Agriculture_Fields.collectAsStateWithLifecycle()
    val api_Type = constants.PostProperty_ViewModel.selected_Land_Type_PF2.collectAsStateWithLifecycle()
    val errors = constants.PostProperty_ViewModel.errors4.collectAsState()
    val selectedLandCatId = constants.PostProperty_ViewModel.get_Selected_Land_Cat_Id()

    val state = remember(selectedLandCatId) { mutableStateOf(if(postFlow.value == PostFlow.DRAFT || postFlow.value == PostFlow.EDIT) 0 else 2)}
    val apiCallMade = remember(selectedLandCatId) { mutableStateOf(false) }

    var retry by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()


    if (postFlow.value == PostFlow.EDIT || postFlow.value == PostFlow.DRAFT || postFlow.value == PostFlow.REQUESTMEDIA) {
        LaunchedEffect(selectedLandCatId) {
            println("FOURTH FORM--- $selectedLandCatId, API Type: ${api_Type.value} -$- ${state}")
        }

        // Main API call effect
        LaunchedEffect(api_Type.value, selectedLandCatId, retry) {
            if (selectedLandCatId != -1 && !apiCallMade.value) {
                apiCallMade.value = true
                state.value = 0
                println("Starting API call for type: ${api_Type.value}, landCatId: $selectedLandCatId")

                when (api_Type.value) {
                    1 -> {
                        constants.API_Vm.get_post_Form4_Residential(
                            land_categorie_id = selectedLandCatId
                        ) { apiResultHandling ->
                            when (apiResultHandling) {
                                is API_Result_Handling.Loading -> {
                                    state.value = 0
                                    println("Residential API: Loading")
                                }

                                is API_Result_Handling.Deactivated -> {
                                    println("Residential API: Deactivated")
                                }

                                is API_Result_Handling.Error -> {
                                    state.value = 1
                                    apiCallMade.value = false
                                    println("Residential API: Error")
                                }

                                is API_Result_Handling.Success -> {
                                    state.value = 2
                                    println("Residential API: Success, Fields: ${field_Data.value}")
                                }

                                is API_Result_Handling.NoData -> {
                                    state.value = 1
                                    apiCallMade.value = false
                                    println("Residential API: No Data")
                                }
                            }
                        }
                    }

                    2 -> {
                        constants.API_Vm.get_post_Form4_Commercial(
                            land_categorie_id = selectedLandCatId
                        ) { apiResultHandling ->
                            when (apiResultHandling) {
                                is API_Result_Handling.Loading -> {
                                    state.value = 0
                                    println("Commercial API: Loading")
                                }

                                is API_Result_Handling.Deactivated -> {
                                    println("Commercial API: Deactivated")
                                }

                                is API_Result_Handling.Error -> {
                                    state.value = 1
                                    apiCallMade.value = false
                                    println("Commercial API: Error")
                                }

                                is API_Result_Handling.Success -> {
                                    state.value = 2
                                    println("Commercial API: Success, Fields: ${field_Data.value}")
                                }

                                is API_Result_Handling.NoData -> {
                                    state.value = 1
                                    apiCallMade.value = false
                                    println("Commercial API: No Data")
                                }
                            }
                        }
                    }

                    3 -> {
                        constants.API_Vm.get_post_Form4_Agriculture(
                            land_categorie_id = selectedLandCatId
                        ) { apiResultHandling ->
                            when (apiResultHandling) {
                                is API_Result_Handling.Loading -> {
                                    state.value = 0
                                    println("Agriculture API: Loading")
                                }

                                is API_Result_Handling.Deactivated -> {
                                    println("Agriculture API: Deactivated")
                                }

                                is API_Result_Handling.Error -> {
                                    state.value = 1
                                    apiCallMade.value = false
                                    println("Agriculture API: Error")
                                }

                                is API_Result_Handling.Success -> {
                                    state.value = 2
                                    println("Agriculture API: Success, Fields: ${field_Data.value}")
                                }

                                is API_Result_Handling.NoData -> {
                                    state.value = 1
                                    apiCallMade.value = false
                                    println("Agriculture API: No Data")
                                }
                            }
                        }
                    }
                }
            }
        }

        // Update active fields when data changes
        LaunchedEffect(field_Data.value) {
            next_Active_Fields.clear()
            next_Active_Fields5.clear()
            next_Active_Fields6.clear()

            when (api_Type.value) {
                1 -> {
                    next_Active_Fields.addAll(
                        constants.PostProperty_ViewModel.getActiveFieldsResidential4(
                            field_Data.value
                        )
                    )
                    next_Active_Fields5.addAll(
                        constants.PostProperty_ViewModel.getActiveFieldsResidential5(
                            field_Data.value?.step_5?.firstOrNull()
                        )
                    )
                    next_Active_Fields6.addAll(
                        constants.PostProperty_ViewModel.getActiveFieldsResidential6(
                            field_Data.value?.step_6?.firstOrNull()
                        )
                    )
                    println("Residential Fields Active: ${next_Active_Fields.size}")
                }

                2 -> {

                    next_Active_Fields.addAll(
                        constants.PostProperty_ViewModel.getActiveFieldsCommercial4(
                            field_Data.value
                        )
                    )
                    next_Active_Fields5.addAll(
                        constants.PostProperty_ViewModel.getActiveFieldsCommercial5(
                            field_Data.value?.step_5?.firstOrNull()
                        )
                    )
                    next_Active_Fields6.addAll(
                        constants.PostProperty_ViewModel.getActiveFieldsCommercial6(
                            field_Data.value?.step_6?.firstOrNull()
                        )
                    )

                    println("Commercial Fields Active: ${next_Active_Fields.size} -- ${next_Active_Fields5}")
                }

                3 -> {
                    next_Active_Fields.addAll(
                        constants.PostProperty_ViewModel.getActiveFieldsAgriculture4(
                            field_Data.value
                        )
                    )
                    next_Active_Fields5.addAll(
                        constants.PostProperty_ViewModel.getActiveFieldsAgriculture5(
                            field_Data.value?.step_5?.firstOrNull()
                        )
                    )
                    next_Active_Fields6.addAll(
                        constants.PostProperty_ViewModel.getActiveFieldsAgriculture6(
                            field_Data.value?.step_6?.firstOrNull()
                        )
                    )

//                next_Active_Fields.addAll(constants.PostProperty_ViewModel.getActiveFieldsAgriculture4(field_Data.value))
                    println("Agriculture Fields Active: ${next_Active_Fields.size}")
                }
            }
        }

    }





//
//    val api_Type = constants.PostProperty_ViewModel.selected_Land_Type_PF2.collectAsStateWithLifecycle()
//
//
//    val field_Data = constants.PostProperty_ViewModel.postFormCommon.collectAsStateWithLifecycle()
//
//    val errors = constants.PostProperty_ViewModel.errors5.collectAsState()

    println("FIELD DATA COMING -- ${field_Data.value}")

    when {
        state.value == 0 -> CircularProgressIndicator()

        state.value == 1 -> {
          //  apiError.value = true
            Box (
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                API_Fail_UI(onReTryClick = {
                    apiCallMade.value = false
                    retry = retry + 1
                })
            }
        }


        state.value == 2 -> {
            LazyColumn()
            {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                        // .background(newLightGray)
                        , horizontalAlignment = Alignment.Start
                    )
                    {
                        Row(
                            modifier = Modifier
                                // .align(Alignment.Start)
                                .wrapContentSize()
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0XFFF4F4F4))
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        )
                        {
                            Text(
                                "Step 5",
                                color = newBlue,
                                fontSize = constants.textUnit(12),
                                fontFamily = constants.fontFamily(0)
                            )
                            Text(
                                "/7",
                                color = Color(0xff666666),
                                fontSize = constants.textUnit(12),
                                fontFamily = constants.fontFamily(0)
                            )
                        }

                        Spacer(modifier = Modifier.padding(4.dp))

                        Text(
                            "Add Property Features",
                            color = newBlack,
                            fontSize = constants.textUnit(24),
                            fontFamily = constants.fontFamily(0),
                            modifier = Modifier
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))


                    when(api_Type.value){
                        1 -> {
                            Form5_Residential_Content(field_Data ,errors )
                        }
                        2 -> {
                            Form5_Commercial_Content(field_Data , errors)
                        }
                        3 -> {
                            Form5_Agriculture_Content(field_Data , errors)
                        }
                    }

                    //residential
//                    if (field_Data.value?.step5?.first()?.no_of_Bathrooms?.first()
//                            ?.isNotEmpty() == true
//                    ) {
//                        val no_of_bedrooms: List<Chips_Items_DC> =
//                            field_Data.value?.optional?.first()?.no_of_bedrooms
//                                ?.map { Chips_Items_DC(title = it) }
//                                ?: emptyList()
//
//                        PP_No_Of_Bedrooms(no_of_bedrooms)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }

//
//
////                    if (field_Data.value?.step5?.first()?.no?.first()
////                            ?.isNotEmpty() == true
////                    ) {
////                        val no_of_open_sides: List<Chips_Items_DC> =
////                            field_Data.value?.optional?.first()?.no_of_open_sides
////                                ?.map { Chips_Items_DC(title = it) }
////                                ?: emptyList()
////
////                        PP_Land_Open_Sides(no_of_open_sides)
////                        Spacer(modifier = Modifier.padding(8.dp))
////                    }
//
//
////                    if (field_Data.value?.optional?.first()?.is?.isNotEmpty() == true) {
////
////                        PP_Has_Boundary_Wall()
////                        Spacer(modifier = Modifier.padding(8.dp))
////                    }
//
//
//                    //// Commercial
//
//
//                    if (field_Data_Com.value?.optional?.first()?.no_of_cabins?.first()
//                            ?.isNotEmpty() == true
//                    ) {
//                        val no_of_cabins: List<Chips_Items_DC> =
//                            field_Data_Com.value?.optional?.first()?.no_of_cabins
//                                ?.map { Chips_Items_DC(title = it) }
//                                ?: emptyList()
//
//                        PP_No_of_Cabins(no_of_cabins)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//
//                    if (field_Data_Com.value?.optional?.first()?.no_of_meeting_rooms?.first()
//                            ?.isNotEmpty() == true
//                    ) {
//                        val no_of_meeting_rooms: List<Chips_Items_DC> =
//                            field_Data_Com.value?.optional?.first()?.no_of_meeting_rooms
//                                ?.map { Chips_Items_DC(title = it) }
//                                ?: emptyList()
//
//                        PP_No_of_Meeting_rooms(no_of_meeting_rooms)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//                    if (field_Data_Com.value?.optional?.first()?.min_no_of_seats?.first()
//                            ?.isNotEmpty() == true
//                    ) {
//                        val min_no_of_seats: List<Chips_Items_DC> =
//                            field_Data_Com.value?.optional?.first()?.min_no_of_seats
//                                ?.map { Chips_Items_DC(title = it) }
//                                ?: emptyList()
//
//                        PP_Min_No_of_Seats(min_no_of_seats)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//                    if (field_Data_Com.value?.optional?.first()?.max_no_of_seats?.first()
//                            ?.isNotEmpty() == true
//                    ) {
//                        val max_no_of_seats: List<Chips_Items_DC> =
//                            field_Data_Com.value?.optional?.first()?.max_no_of_seats
//                                ?.map { Chips_Items_DC(title = it) }
//                                ?: emptyList()
//
//                        PP_Max_No_of_Seats(max_no_of_seats)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//                    if (field_Data_Com.value?.optional?.first()?.conference_room?.first()
//                            ?.isNotEmpty() == true
//                    ) {
//                        val conference_room: List<Chips_Items_DC> =
//                            field_Data_Com.value?.optional?.first()?.conference_room
//                                ?.map { Chips_Items_DC(title = it) }
//                                ?: emptyList()
//
//                        PP_Conference_Room(conference_room)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//
//                    if (field_Data_Com.value?.optional?.first()?.no_of_staircases?.first()
//                            ?.isNotEmpty() == true
//                    ) {
//                        val no_of_staircases: List<Chips_Items_DC> =
//                            field_Data_Com.value?.optional?.first()?.no_of_staircases
//                                ?.map { Chips_Items_DC(title = it) }
//                                ?: emptyList()
//
//                        PP_No_of_Staircases(no_of_staircases)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//                    if (field_Data_Com.value?.optional?.first()?.reception_area?.first()
//                            ?.isNullOrEmpty() == false
//                    ) {
//                        val reception_area: List<Chips_Items_DC> =
//                            field_Data_Com.value?.optional?.first()?.reception_area
//                                ?.map { Chips_Items_DC(title = it) }
//                                ?: emptyList()
//
//                        PP_Reception_Area(reception_area)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//
//                    if (field_Data_Com.value?.optional?.first()?.pantry?.first()
//                            ?.isNotEmpty() == true
//                    ) {
//                        val pantry: List<Chips_Items_DC> =
//                            field_Data_Com.value?.optional?.first()?.pantry
//                                ?.map { Chips_Items_DC(title = it) }
//                                ?: emptyList()
//
//                        PP_Pantry(pantry)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//                    if (field_Data_Com.value?.optional?.first()?.pantry_size?.isNotEmpty() == true) {
//
//                        PP_Pantry_Size()
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//
//                    if (field_Data_Com.value?.optional?.first()?.central_ac?.first()
//                            ?.isNotEmpty() == true
//                    ) {
//                        val central_ac: List<Chips_Items_DC> =
//                            field_Data_Com.value?.optional?.first()?.central_ac
//                                ?.map { Chips_Items_DC(title = it) }
//                                ?: emptyList()
//
//                        PP_Central_AC(central_ac)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//
//                    if (field_Data_Com.value?.optional?.first()?.oxygen_duct?.first()
//                            ?.isNotEmpty() == true
//                    ) {
//                        val oxygen_duct: List<Chips_Items_DC> =
//                            field_Data_Com.value?.optional?.first()?.oxygen_duct
//                                ?.map { Chips_Items_DC(title = it) }
//                                ?: emptyList()
//
//                        PP_Oxygen_Duct(oxygen_duct)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//                    if (field_Data_Com.value?.optional?.first()?.ups?.first()
//                            ?.isNotEmpty() == true
//                    ) {
//                        val ups: List<Chips_Items_DC> = field_Data_Com.value?.optional?.first()?.ups
//                            ?.map { Chips_Items_DC(title = it) }
//                            ?: emptyList()
//
//                        PP_UPS(ups)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//
//
//                    if (field_Data_Com.value?.optional?.first()?.furnishing_status?.first()
//                            ?.isNotEmpty() == true
//                    ) {
//                        val furnishing_status: List<Chips_Items_DC> =
//                            field_Data_Com.value?.optional?.first()?.furnishing_status
//                                ?.map { Chips_Items_DC(title = it) }
//                                ?: emptyList()
//
//                        PP_Furnishing_Status(furnishing_status)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//
//                    if (field_Data_Com.value?.optional?.first()?.fire_safety_measures?.first()
//                            ?.isNotEmpty() == true
//                    ) {
//                        val fire_safety_measures: List<Chips_Items_DC> =
//                            field_Data_Com.value?.optional?.first()?.fire_safety_measures
//                                ?.map { Chips_Items_DC(title = it) }
//                                ?: emptyList()
//
//                        PP_Fire_Safety_Measures(fire_safety_measures)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//
//
//                    if (field_Data_Com.value?.optional?.first()?.lifts?.first()
//                            ?.isNotEmpty() == true
//                    ) {
//                        val lifts: List<Chips_Items_DC> =
//                            field_Data_Com.value?.optional?.first()?.lifts
//                                ?.map { Chips_Items_DC(title = it) }
//                                ?: emptyList()
//
//                        PP_Lifts(lifts)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//
//                    if (field_Data_Com.value?.optional?.first()?.parking_available?.isNotEmpty() == true) {
//
//                        PP_Parking_Available()
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
////                    if (field_Data_Com.value?.optional?.first()?.is_it_Pre_leased_pre_rented?.isNotEmpty() == true) {
////
////                        PP_Is_Pre_leased_Pre_Rented()
////                        Spacer(modifier = Modifier.padding(8.dp))
////                    }
//
//                    if (field_Data_Com.value?.optional?.first()?.is_your_office_fire_noc_certified?.isNotEmpty() == true) {
//
//                        PP_Is_your_office_fire_NOC_Certified()
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//                    if (field_Data_Com.value?.optional?.first()?.occupancy_certificate?.isNotEmpty() == true) {
//
//                        PP_Occupancy_Certificate()
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//
////                    if (field_Data_Com.value?.optional?.first()?.office_previously_used_for?.first()
////                            ?.isNotEmpty() == true
////                    ) {
////                        val office_previously_used_for: List<Chips_Items_DC> =
////                            field_Data_Com.value?.optional?.first()?.office_previously_used_for
////                                ?.map { Chips_Items_DC(title = it) }
////                                ?: emptyList()
////
////                        PP_Office_Previously_Used_for(office_previously_used_for)
////                        Spacer(modifier = Modifier.padding(8.dp))
////                    }
//
//                    if (field_Data_Com.value?.optional?.first()?.amenities?.first()
//                            ?.isNotEmpty() == true
//                    ) {
//                        val amenities: List<Chips_Items_DC> =
//                            field_Data_Com.value?.optional?.first()?.amenities
//                                ?.map { Chips_Items_DC(title = it) }
//                                ?: emptyList()
//
//                        PP_Amenities(amenities)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//                    if (field_Data_Com.value?.optional?.first()?.property_highlights?.first()
//                            ?.isNotEmpty() == true
//                    ) {
//                        val property_highlights: List<Chips_Items_DC> =
//                            field_Data_Com.value?.optional?.first()?.property_highlights
//                                ?.map { Chips_Items_DC(title = it) }
//                                ?: emptyList()
//
//                        PP_Property_Highlights(property_highlights)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//                    if (field_Data_Com.value?.optional?.first()?.washroom_details?.first()
//                            ?.isNotEmpty() == true
//                    ) {
//                        val washroom_details: List<Chips_Items_DC> =
//                            field_Data_Com.value?.optional?.first()?.washroom_details
//                                ?.map { Chips_Items_DC(title = it) }
//                                ?: emptyList()
//
//                        PP_Washroom_Details(washroom_details)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//
//                    if (field_Data_Com.value?.optional?.first()?.suitable_business_type?.first()
//                            ?.isNotEmpty() == true
//                    ) {
//                        val suitable_business_type: List<Chips_Items_DC> =
//                            field_Data_Com.value?.optional?.first()?.suitable_business_type
//                                ?.map { Chips_Items_DC(title = it) }
//                                ?: emptyList()
//
//                        PP_Suitable_Business_Type(suitable_business_type)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//
//                    if (field_Data_Com.value?.optional?.first()?.which_authority_the_property_is_approved_by?.first()
//                            ?.isNotEmpty() == true
//                    ) {
//                        val which_authority_the_property_is_approved_by: List<Chips_Items_DC> =
//                            field_Data_Com.value?.optional?.first()?.which_authority_the_property_is_approved_by
//                                ?.map { Chips_Items_DC(title = it) }
//                                ?: emptyList()
//
//                        PP_authority_property_Approved(which_authority_the_property_is_approved_by)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//
//                    ///// agriculture
//
//
//                    if (field_Data_Agri.value?.optional?.first()?.no_of_bedrooms?.first()
//                            ?.isNotEmpty() == true
//                    ) {
//                        val no_of_bedrooms: List<Chips_Items_DC> =
//                            field_Data_Agri.value?.optional?.first()?.no_of_bedrooms
//                                ?.map { Chips_Items_DC(title = it) }
//                                ?: emptyList()
//
//                        PP_No_Of_Bedrooms(no_of_bedrooms)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//
//
//                    if (field_Data_Agri.value?.optional?.first()?.no_of_bathrooms?.first()
//                            ?.isNotEmpty() == true
//                    ) {
//                        val no_of_bathrooms: List<Chips_Items_DC> =
//                            field_Data_Agri.value?.optional?.first()?.no_of_bathrooms
//                                ?.map { Chips_Items_DC(title = it) }
//                                ?: emptyList()
//
//                        PP_No_Of_Bathrooms(no_of_bathrooms)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//
//
//                    if (field_Data_Agri.value?.optional?.first()?.no_of_balconies?.first()
//                            ?.isNotEmpty() == true
//                    ) {
//                        val no_of_balconies: List<Chips_Items_DC> =
//                            field_Data_Agri.value?.optional?.first()?.no_of_balconies
//                                ?.map { Chips_Items_DC(title = it) }
//                                ?: emptyList()
//
//                        PP_No_Of_Balconies(no_of_balconies)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//
//
//                    if (field_Data_Agri.value?.optional?.first()?.other_rooms?.first()
//                            ?.isNotEmpty() == true
//                    ) {
//                        val other_rooms: List<Chips_Items_DC> =
//                            field_Data_Agri.value?.optional?.first()?.other_rooms
//                                ?.map { Chips_Items_DC(title = it) }
//                                ?: emptyList()
//
//                        PP_Other_Rooms(other_rooms)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//
//                    if (field_Data_Agri.value?.optional?.first()?.furnishing_status?.first()
//                            ?.isNotEmpty() == true
//                    ) {
//                        val furnishing_status: List<Chips_Items_DC> =
//                            field_Data_Agri.value?.optional?.first()?.furnishing_status
//                                ?.map { Chips_Items_DC(title = it) }
//                                ?: emptyList()
//
//                        PP_Furnishing_Status(furnishing_status)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//
//                    if (field_Data_Agri.value?.optional?.first()?.parking_available?.isNotEmpty() == true) {
//
//
//                        PP_Parking_Available()
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//                    if (field_Data_Agri.value?.optional?.first()?.amenities?.first()
//                            ?.isNotEmpty() == true
//                    ) {
//                        val amenities: List<Chips_Items_DC> =
//                            field_Data_Agri.value?.optional?.first()?.amenities
//                                ?.map { Chips_Items_DC(title = it) }
//                                ?: emptyList()
//
//                        PP_Amenities(amenities)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//                    if (field_Data_Agri.value?.optional?.first()?.property_highlights?.first()
//                            ?.isNotEmpty() == true
//                    ) {
//                        val property_highlights: List<Chips_Items_DC> =
//                            field_Data_Agri.value?.optional?.first()?.property_highlights
//                                ?.map { Chips_Items_DC(title = it) }
//                                ?: emptyList()
//
//                        PP_Property_Highlights(property_highlights)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//
//                    if (field_Data_Agri.value?.optional?.first()?.does_your_property_authority_approved?.first()
//                            ?.isNotEmpty() == true
//                    ) {
//                        val does_your_property_authority_approved: List<Chips_Items_DC> =
//                            field_Data_Agri.value?.optional?.first()?.does_your_property_authority_approved
//                                ?.map { Chips_Items_DC(title = it) }
//                                ?: emptyList()
//
//                        PP_authority_property_Approved(does_your_property_authority_approved)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//
//                    if (field_Data_Agri.value?.optional?.first()?.no_of_open_sides?.first()
//                            ?.isNotEmpty() == true
//                    ) {
//                        val no_of_open_sides: List<Chips_Items_DC> =
//                            field_Data_Agri.value?.optional?.first()?.no_of_open_sides
//                                ?.map { Chips_Items_DC(title = it) }
//                                ?: emptyList()
//
//                        PP_Land_Open_Sides(no_of_open_sides)
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//
//                    if (field_Data_Agri.value?.optional?.first()?.is_boundary_wall_around_property?.isNotEmpty() == true) {
//
//
//                        PP_Has_Boundary_Wall()
//                        Spacer(modifier = Modifier.padding(8.dp))
//                    }
//

                }
            }
        }
    }
}


@Composable
fun Form5_Residential_Content(
//    field_Data: State<PostProperty_Stepfour_Residential_Data?>,
    field_Data: State<PostFormCommonPropertyData?>,
    errors: State<Map<Int, Boolean>>
) {
    Column() {

        /// Agreement Type
        if(field_Data.value?.step_5?.firstOrNull()?.Agreement_Type?.isNotEmpty() == true){

            val agreementType: List<Chips_Items_DC> = field_Data.value?.step_5?.firstOrNull()?.Agreement_Type
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_AgreementType(agreementType , isError = errors.value[0] == true)
            Spacer(modifier = Modifier.padding(8.dp))
        }


        /// no of bathrooms
        if(field_Data.value?.step_5?.firstOrNull()?.No_of_Bathrooms?.isNotEmpty() == true){

            val data: List<Chips_Items_DC> = field_Data.value?.step_5?.firstOrNull()?.No_of_Bathrooms
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_No_Of_Bathrooms(data , isError = errors.value[1] == true )
            Spacer(modifier = Modifier.padding(8.dp))
        }


        /// no of balconies
        if(field_Data.value?.step_5?.firstOrNull()?.No_of_Balconies?.isNotEmpty() == true){

            val data: List<Chips_Items_DC> = field_Data.value?.step_5?.firstOrNull()?.No_of_Balconies
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_No_Of_Balconies(data, isError = errors.value[2] == true)
            Spacer(modifier = Modifier.padding(8.dp))
        }

        /// food prefrence

        if(field_Data.value?.step_5?.firstOrNull()?.Food_Preferences?.isNotEmpty() == true){

            val data: List<Chips_Items_DC> = field_Data.value?.step_5?.firstOrNull()?.Food_Preferences
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_FoodPreference(data, isError = errors.value[3] == true)
            Spacer(modifier = Modifier.padding(8.dp))
        }

        /// pets allowed

        if(field_Data.value?.step_5?.firstOrNull()?.Pets_Allowed?.isNotEmpty() == true){

            PP_PetsAllowed( isError = errors.value[4] == true)
            Spacer(modifier = Modifier.padding(8.dp))
        }


        /// parking available

        println("fieldata form 5 --- ${field_Data.value?.step_5}")
        if(field_Data.value?.step_5?.firstOrNull()?.Parking_Available?.isNotEmpty() == true){

            PP_Parking_available( isError = errors.value[6] == true)
            Spacer(modifier = Modifier.padding(8.dp))
        }


        /// other rooms

        if(field_Data.value?.step_5?.firstOrNull()?.Other_Rooms?.isNotEmpty() == true){

            val data: List<Chips_Items_DC> = field_Data.value?.step_5?.firstOrNull()?.Other_Rooms
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_Other_Rooms(data)
            Spacer(modifier = Modifier.padding(8.dp))
        }

        /// furnishing status

        if(field_Data.value?.step_5?.firstOrNull()?.Furnishing_Status?.isNotEmpty() == true){

            val data: List<Chips_Items_DC> = field_Data.value?.step_5?.firstOrNull()?.Furnishing_Status
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_Furnishing_Status(data, isError = errors.value[5] == true)
            Spacer(modifier = Modifier.padding(8.dp))
        }

        /// amenities

        if(field_Data.value?.step_5?.firstOrNull()?.Amenities?.isNotEmpty() == true){

            val data: List<Chips_Items_DC> = field_Data.value?.step_5?.firstOrNull()?.Amenities
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_Amenities(data, isError = errors.value[7] == true)
            Spacer(modifier = Modifier.padding(8.dp))
        }

        /// property highlishts

        if(field_Data.value?.step_5?.firstOrNull()?.Property_Highlights?.isNotEmpty() == true){

            val data: List<Chips_Items_DC> = field_Data.value?.step_5?.firstOrNull()?.Property_Highlights
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_Property_Highlights(data)
            Spacer(modifier = Modifier.padding(8.dp))
        }

        /// reception area

        if(field_Data.value?.step_5?.firstOrNull()?.Reception_Area?.firstOrNull()?.isNotEmpty() == true){

            val data: List<Chips_Items_DC> = field_Data.value?.step_5?.firstOrNull()?.Reception_Area
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_Reception_Area(data)
            Spacer(modifier = Modifier.padding(8.dp))
        }

    }
}


@Composable
fun Form5_Commercial_Content123(
    field_Data_Com: State<PostFormCommonPropertyData?>
    ,errors: State<Map<Int, Boolean>>)
{


    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsStateWithLifecycle()



    Column {

//         property condition

        if(field_Data_Com.value?.step_5?.firstOrNull()?.Property_Condition ?.isNotEmpty() == true){

            val agreementType: List<Chips_Items_DC> = field_Data_Com.value?.step_5?.firstOrNull()?.Property_Condition
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_Property_Condition(agreementType)
            Spacer(modifier = Modifier.padding(8.dp))
        }


//         no of baths

        if(field_Data_Com.value?.step_5?.firstOrNull()?.No_of_Bathrooms?.isNotEmpty() == true){

            val agreementType: List<Chips_Items_DC> = field_Data_Com.value?.step_5?.firstOrNull()?.No_of_Bathrooms
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_No_Of_Bathrooms(agreementType , isError = errors.value[1] == true)
            Spacer(modifier = Modifier.padding(8.dp))
        }

//         no of staircases

        if(field_Data_Com.value?.step_5?.firstOrNull()?.No_of_Staircases?.isNotEmpty() == true){

            val agreementType: List<Chips_Items_DC> = field_Data_Com.value?.step_5?.firstOrNull()?.No_of_Staircases
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_No_of_Staircases(agreementType)
            Spacer(modifier = Modifier.padding(8.dp))
        }

        if ( flowData.value?.property_condition == "Ready to Use"){

//         conference room

            if(field_Data_Com.value?.step_5?.firstOrNull()?.Conference_Room?.isNotEmpty() == true){

                val agreementType: List<Chips_Items_DC> = field_Data_Com.value?.step_5?.firstOrNull()?.Conference_Room
                    ?.map { Chips_Items_DC(title = it) }
                    ?: emptyList()
                //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
                PP_Conference_Room(agreementType)
                Spacer(modifier = Modifier.padding(8.dp))
            }


//         max no of seats

            if(field_Data_Com.value?.step_5?.firstOrNull()?.Max_No_of_Seats?.isNotEmpty() == true){

                val agreementType: List<Chips_Items_DC> = field_Data_Com.value?.step_5?.firstOrNull()?.Max_No_of_Seats
                    ?.map { Chips_Items_DC(title = it) }
                    ?: emptyList()
                //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
                PP_Max_No_of_Seats(agreementType)
                Spacer(modifier = Modifier.padding(8.dp))
            }


//         min no of seats

            if(field_Data_Com.value?.step_5?.firstOrNull()?.Min_No_of_Seats?.isNotEmpty() == true){

                val agreementType: List<Chips_Items_DC> = field_Data_Com.value?.step_5?.firstOrNull()?.Min_No_of_Seats
                    ?.map { Chips_Items_DC(title = it) }
                    ?: emptyList()
                //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
                PP_Min_No_of_Seats(agreementType)
                Spacer(modifier = Modifier.padding(8.dp))
            }

//         no of meeting rooms

            if(field_Data_Com.value?.step_5?.firstOrNull()?.No_of_Meeting_Rooms?.isNotEmpty() == true){

                val agreementType: List<Chips_Items_DC> = field_Data_Com.value?.step_5?.firstOrNull()?.No_of_Meeting_Rooms
                    ?.map { Chips_Items_DC(title = it) }
                    ?: emptyList()
                //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
                PP_No_of_Meeting_rooms(agreementType)
                Spacer(modifier = Modifier.padding(8.dp))
            }

//         no of cabins

            if(field_Data_Com.value?.step_5?.firstOrNull()?.No_of_Cabins?.isNotEmpty() == true){

                val agreementType: List<Chips_Items_DC> = field_Data_Com.value?.step_5?.firstOrNull()?.No_of_Cabins
                    ?.map { Chips_Items_DC(title = it) }
                    ?: emptyList()
                //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
                PP_No_of_Cabins(agreementType)
                Spacer(modifier = Modifier.padding(8.dp))
            }

        }


//         reception area

        if(field_Data_Com.value?.step_5?.firstOrNull()?.Reception_Area?.isNotEmpty() == true){

            val agreementType: List<Chips_Items_DC> = field_Data_Com.value?.step_5?.firstOrNull()?.Reception_Area
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_Reception_Area(agreementType)
            Spacer(modifier = Modifier.padding(8.dp))
        }

//         pantry

        if(field_Data_Com.value?.step_5?.firstOrNull()?.Pantry?.isNotEmpty() == true){

            val agreementType: List<Chips_Items_DC> = field_Data_Com.value?.step_5?.firstOrNull()?.Pantry
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_Pantry(agreementType)
            Spacer(modifier = Modifier.padding(8.dp))
        }


//         pantry size

        if(field_Data_Com.value?.step_5?.firstOrNull()?.Pantry_Size?.isNotEmpty() == true){


            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_Pantry_Size()
            Spacer(modifier = Modifier.padding(8.dp))
        }


//        central ac

        if(field_Data_Com.value?.step_5?.firstOrNull()?.Central_AC?.isNotEmpty() == true){

            val agreementType: List<Chips_Items_DC> = field_Data_Com.value?.step_5?.firstOrNull()?.Central_AC
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_Central_AC(agreementType)
            Spacer(modifier = Modifier.padding(8.dp))
        }

//        oxygen duct

        if(field_Data_Com.value?.step_5?.firstOrNull()?.Oxygen_Duct?.isNotEmpty() == true){

            val agreementType: List<Chips_Items_DC> = field_Data_Com.value?.step_5?.firstOrNull()?.Oxygen_Duct
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_Oxygen_Duct(agreementType)
            Spacer(modifier = Modifier.padding(8.dp))
        }

//        ups

        if(field_Data_Com.value?.step_5?.firstOrNull()?.UPS?.isNotEmpty() == true){

            val agreementType: List<Chips_Items_DC> = field_Data_Com.value?.step_5?.firstOrNull()?.UPS
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_UPS(agreementType)
            Spacer(modifier = Modifier.padding(8.dp))
        }


//        lifts

        if(field_Data_Com.value?.step_5?.firstOrNull()?.Lifts?.isNotEmpty() == true){

            val agreementType: List<Chips_Items_DC> = field_Data_Com.value?.step_5?.firstOrNull()?.Lifts
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_Lifts(agreementType)
            Spacer(modifier = Modifier.padding(8.dp))
        }


//        fire safety measures

        if(field_Data_Com.value?.step_5?.firstOrNull()?.Fire_Safety_Measures?.isNotEmpty() == true){

            val agreementType: List<Chips_Items_DC> = field_Data_Com.value?.step_5?.firstOrNull()?.Fire_Safety_Measures
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_Fire_Safety_Measures(agreementType)
            Spacer(modifier = Modifier.padding(8.dp))
        }


//        furnishing status

        if(field_Data_Com.value?.step_5?.firstOrNull()?.Furnishing_Status?.isNotEmpty() == true){

            val agreementType: List<Chips_Items_DC> = field_Data_Com.value?.step_5?.firstOrNull()?.Furnishing_Status
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_Furnishing_Status(agreementType , isError = errors.value[5] == true)
            Spacer(modifier = Modifier.padding(8.dp))
        }


//        noc certified

        if(field_Data_Com.value?.step_5?.firstOrNull()?.Is_your_office_fire_NOC_Certified?.isNotEmpty() == true){


            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_Is_your_office_fire_NOC_Certified()
            Spacer(modifier = Modifier.padding(8.dp))
        }

//        occupancy certified
        if(field_Data_Com.value?.step_5?.firstOrNull()?.Occupancy_Certificate?.isNotEmpty() == true){

            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_Occupancy_Certificate()
            Spacer(modifier = Modifier.padding(8.dp))
        }

//        amentities

        if(field_Data_Com.value?.step_5?.firstOrNull()?.Amenities?.isNotEmpty() == true){

            val agreementType: List<Chips_Items_DC> = field_Data_Com.value?.step_5?.firstOrNull()?.Amenities
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_Amenities(agreementType ,isError = errors.value[7] == true)
            Spacer(modifier = Modifier.padding(8.dp))
        }

//        property highlights

        if(field_Data_Com.value?.step_5?.firstOrNull()?.Property_Highlights?.isNotEmpty() == true){

            val agreementType: List<Chips_Items_DC> = field_Data_Com.value?.step_5?.firstOrNull()?.Property_Highlights
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_Property_Highlights(agreementType)
            Spacer(modifier = Modifier.padding(8.dp))
        }

//        suitable bisuness type

        if(field_Data_Com.value?.step_5?.firstOrNull()?.Suitable_Business_Type?.isNotEmpty() == true){

            val agreementType: List<Chips_Items_DC> = field_Data_Com.value?.step_5?.firstOrNull()?.Suitable_Business_Type
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_Suitable_Business_Type(agreementType)
            Spacer(modifier = Modifier.padding(8.dp))
        }



    }
}

@Composable
fun Form5_Commercial_Content(
    field_Data_Com: State<PostFormCommonPropertyData?>,
    errors: State<Map<Int, Boolean>>
) {

    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsStateWithLifecycle()

    // ✅ KEY FIX: Remember each list separately to prevent shared state
    val propertyConditionList = remember(field_Data_Com.value?.step_5?.firstOrNull()?.Property_Condition) {
        field_Data_Com.value?.step_5?.firstOrNull()?.Property_Condition
            ?.map { Chips_Items_DC(title = it) }
            ?: emptyList()
    }

    val bathroomsList = remember(field_Data_Com.value?.step_5?.firstOrNull()?.No_of_Bathrooms) {
        field_Data_Com.value?.step_5?.firstOrNull()?.No_of_Bathrooms
            ?.map { Chips_Items_DC(title = it) }
            ?: emptyList()
    }


    val staircasesList = remember(field_Data_Com.value?.step_5?.firstOrNull()?.No_of_Staircases) {
        field_Data_Com.value?.step_5?.firstOrNull()?.No_of_Staircases
            ?.map { Chips_Items_DC(title = it) }
            ?: emptyList()
    }

    val conferenceRoomList = remember(field_Data_Com.value?.step_5?.firstOrNull()?.Conference_Room) {
        field_Data_Com.value?.step_5?.firstOrNull()?.Conference_Room
            ?.map { Chips_Items_DC(title = it) }
            ?: emptyList()
    }

    val maxSeatsList = remember(field_Data_Com.value?.step_5?.firstOrNull()?.Max_No_of_Seats) {
        field_Data_Com.value?.step_5?.firstOrNull()?.Max_No_of_Seats
            ?.map { Chips_Items_DC(title = it) }
            ?: emptyList()
    }

    val minSeatsList = remember(field_Data_Com.value?.step_5?.firstOrNull()?.Min_No_of_Seats) {
        field_Data_Com.value?.step_5?.firstOrNull()?.Min_No_of_Seats
            ?.map { Chips_Items_DC(title = it) }
            ?: emptyList()
    }

    val meetingRoomsList = remember(field_Data_Com.value?.step_5?.firstOrNull()?.No_of_Meeting_Rooms) {
        field_Data_Com.value?.step_5?.firstOrNull()?.No_of_Meeting_Rooms
            ?.map { Chips_Items_DC(title = it) }
            ?: emptyList()
    }

    val cabinsList = remember(field_Data_Com.value?.step_5?.firstOrNull()?.No_of_Cabins) {
        field_Data_Com.value?.step_5?.firstOrNull()?.No_of_Cabins
            ?.map { Chips_Items_DC(title = it) }
            ?: emptyList()
    }

    val receptionAreaList = remember(field_Data_Com.value?.step_5?.firstOrNull()?.Reception_Area) {
        field_Data_Com.value?.step_5?.firstOrNull()?.Reception_Area
            ?.map { Chips_Items_DC(title = it) }
            ?: emptyList()
    }

    val pantryList = remember(field_Data_Com.value?.step_5?.firstOrNull()?.Pantry) {
        field_Data_Com.value?.step_5?.firstOrNull()?.Pantry
            ?.map { Chips_Items_DC(title = it) }
            ?: emptyList()
    }

    val centralACList = remember(field_Data_Com.value?.step_5?.firstOrNull()?.Central_AC) {
        field_Data_Com.value?.step_5?.firstOrNull()?.Central_AC
            ?.map { Chips_Items_DC(title = it) }
            ?: emptyList()
    }

    val oxygenDuctList = remember(field_Data_Com.value?.step_5?.firstOrNull()?.Oxygen_Duct) {
        field_Data_Com.value?.step_5?.firstOrNull()?.Oxygen_Duct
            ?.map { Chips_Items_DC(title = it) }
            ?: emptyList()
    }

    val upsList = remember(field_Data_Com.value?.step_5?.firstOrNull()?.UPS) {
        field_Data_Com.value?.step_5?.firstOrNull()?.UPS
            ?.map { Chips_Items_DC(title = it) }
            ?: emptyList()
    }

    val liftsList = remember(field_Data_Com.value?.step_5?.firstOrNull()?.Lifts) {
        field_Data_Com.value?.step_5?.firstOrNull()?.Lifts
            ?.map { Chips_Items_DC(title = it) }
            ?: emptyList()
    }

    val fireSafetyList = remember(field_Data_Com.value?.step_5?.firstOrNull()?.Fire_Safety_Measures) {
        field_Data_Com.value?.step_5?.firstOrNull()?.Fire_Safety_Measures
            ?.map { Chips_Items_DC(title = it) }
            ?: emptyList()
    }

    val furnishingStatusList = remember(field_Data_Com.value?.step_5?.firstOrNull()?.Furnishing_Status) {
        field_Data_Com.value?.step_5?.firstOrNull()?.Furnishing_Status
            ?.map { Chips_Items_DC(title = it) }
            ?: emptyList()
    }

    val amenitiesList = remember(field_Data_Com.value?.step_5?.firstOrNull()?.Amenities) {
        field_Data_Com.value?.step_5?.firstOrNull()?.Amenities
            ?.map { Chips_Items_DC(title = it) }
            ?: emptyList()
    }

    val propertyHighlightsList = remember(field_Data_Com.value?.step_5?.firstOrNull()?.Property_Highlights) {
        field_Data_Com.value?.step_5?.firstOrNull()?.Property_Highlights
            ?.map { Chips_Items_DC(title = it) }
            ?: emptyList()
    }

    val suitableBusinessList = remember(field_Data_Com.value?.step_5?.firstOrNull()?.Suitable_Business_Type) {
        field_Data_Com.value?.step_5?.firstOrNull()?.Suitable_Business_Type
            ?.map { Chips_Items_DC(title = it) }
            ?: emptyList()
    }

    Column {

        // property condition
        if (field_Data_Com.value?.step_5?.firstOrNull()?.Property_Condition?.isNotEmpty() == true) {
            PP_Property_Condition(propertyConditionList)
            Spacer(modifier = Modifier.padding(8.dp))
        }

        // no of baths
        if (field_Data_Com.value?.step_5?.firstOrNull()?.No_of_Bathrooms?.isNotEmpty() == true) {
            PP_No_Of_Bathrooms(bathroomsList, isError = errors.value[1] == true)
            Spacer(modifier = Modifier.padding(8.dp))
        }

        // no of staircases
        if (field_Data_Com.value?.step_5?.firstOrNull()?.No_of_Staircases?.isNotEmpty() == true) {
            PP_No_of_Staircases(staircasesList)
            Spacer(modifier = Modifier.padding(8.dp))
        }

        if (flowData.value?.property_condition == "Ready to Use") {

            // conference room
            if (field_Data_Com.value?.step_5?.firstOrNull()?.Conference_Room?.isNotEmpty() == true) {
                PP_Conference_Room(conferenceRoomList)
                Spacer(modifier = Modifier.padding(8.dp))
            }

            // max no of seats
            if (field_Data_Com.value?.step_5?.firstOrNull()?.Max_No_of_Seats?.isNotEmpty() == true) {
                PP_Max_No_of_Seats(maxSeatsList)
                Spacer(modifier = Modifier.padding(8.dp))
            }

            // min no of seats
            if (field_Data_Com.value?.step_5?.firstOrNull()?.Min_No_of_Seats?.isNotEmpty() == true) {
                PP_Min_No_of_Seats(minSeatsList)
                Spacer(modifier = Modifier.padding(8.dp))
            }

            // no of meeting rooms
            if (field_Data_Com.value?.step_5?.firstOrNull()?.No_of_Meeting_Rooms?.isNotEmpty() == true) {
                PP_No_of_Meeting_rooms(meetingRoomsList)
                Spacer(modifier = Modifier.padding(8.dp))
            }

            // no of cabins
            if (field_Data_Com.value?.step_5?.firstOrNull()?.No_of_Cabins?.isNotEmpty() == true) {
                PP_No_of_Cabins(cabinsList)
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }

        // reception area
        if (field_Data_Com.value?.step_5?.firstOrNull()?.Reception_Area?.isNotEmpty() == true) {
            PP_Reception_Area(receptionAreaList)
            Spacer(modifier = Modifier.padding(8.dp))
        }

        // pantry
        if (field_Data_Com.value?.step_5?.firstOrNull()?.Pantry?.isNotEmpty() == true) {
            PP_Pantry(pantryList)
            Spacer(modifier = Modifier.padding(8.dp))
        }

        // pantry size
        if (field_Data_Com.value?.step_5?.firstOrNull()?.Pantry_Size?.isNotEmpty() == true) {
            PP_Pantry_Size()
            Spacer(modifier = Modifier.padding(8.dp))
        }

        // central ac
        if (field_Data_Com.value?.step_5?.firstOrNull()?.Central_AC?.isNotEmpty() == true) {
            PP_Central_AC(centralACList)
            Spacer(modifier = Modifier.padding(8.dp))
        }

        // oxygen duct
        if (field_Data_Com.value?.step_5?.firstOrNull()?.Oxygen_Duct?.isNotEmpty() == true) {
            PP_Oxygen_Duct(oxygenDuctList)
            Spacer(modifier = Modifier.padding(8.dp))
        }

        // ups
        if (field_Data_Com.value?.step_5?.firstOrNull()?.UPS?.isNotEmpty() == true) {
            PP_UPS(upsList)
            Spacer(modifier = Modifier.padding(8.dp))
        }

        // lifts
        if (field_Data_Com.value?.step_5?.firstOrNull()?.Lifts?.isNotEmpty() == true) {
            PP_Lifts(liftsList)
            Spacer(modifier = Modifier.padding(8.dp))
        }

        // fire safety measures
        if (field_Data_Com.value?.step_5?.firstOrNull()?.Fire_Safety_Measures?.isNotEmpty() == true) {
            PP_Fire_Safety_Measures(fireSafetyList)
            Spacer(modifier = Modifier.padding(8.dp))
        }

        // furnishing status
        if (field_Data_Com.value?.step_5?.firstOrNull()?.Furnishing_Status?.isNotEmpty() == true) {
            PP_Furnishing_Status(furnishingStatusList, isError = errors.value[5] == true)
            Spacer(modifier = Modifier.padding(8.dp))
        }

        // parking available status
        if (field_Data_Com.value?.step_5?.firstOrNull()?.Parking_Available?.isNotEmpty() == true) {
            PP_Parking_available( isError = errors.value[5] == true)
            Spacer(modifier = Modifier.padding(8.dp))
        }

        // noc certified
        if (field_Data_Com.value?.step_5?.firstOrNull()?.Is_your_office_fire_NOC_Certified?.isNotEmpty() == true) {
            PP_Is_your_office_fire_NOC_Certified()
            Spacer(modifier = Modifier.padding(8.dp))
        }

        // occupancy certified
        if (field_Data_Com.value?.step_5?.firstOrNull()?.Occupancy_Certificate?.isNotEmpty() == true) {
            PP_Occupancy_Certificate()
            Spacer(modifier = Modifier.padding(8.dp))
        }

        // amenities
        if (field_Data_Com.value?.step_5?.firstOrNull()?.Amenities?.isNotEmpty() == true) {
            PP_Amenities(amenitiesList, isError = errors.value[7] == true)
            Spacer(modifier = Modifier.padding(8.dp))
        }

        // property highlights
        if (field_Data_Com.value?.step_5?.firstOrNull()?.Property_Highlights?.isNotEmpty() == true) {
            PP_Property_Highlights(propertyHighlightsList)
            Spacer(modifier = Modifier.padding(8.dp))
        }

        // suitable business type
        if (field_Data_Com.value?.step_5?.firstOrNull()?.Suitable_Business_Type?.firstOrNull()?.isNotEmpty() == true) {
            PP_Suitable_Business_Type(suitableBusinessList)
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}


@Composable
fun Form5_Agriculture_Content(
    field_Data_Com: State<PostFormCommonPropertyData?>
    ,errors: State<Map<Int, Boolean>>
){
    Column {

        // property condition

        if(field_Data_Com.value?.step_5?.firstOrNull()?.Agreement_Type?.isNotEmpty() == true){

            val agreementType: List<Chips_Items_DC> = field_Data_Com.value?.step_5?.firstOrNull()?.Agreement_Type
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_AgreementType(agreementType , isError = errors.value[0] == true)
            Spacer(modifier = Modifier.padding(8.dp))
        }



        // no of baths

        if(field_Data_Com.value?.step_5?.firstOrNull()?.No_of_Bathrooms?.isNotEmpty() == true){

            val agreementType: List<Chips_Items_DC> = field_Data_Com.value?.step_5?.firstOrNull()?.No_of_Bathrooms
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_No_Of_Bathrooms(agreementType , errors.value[1] == true)
            Spacer(modifier = Modifier.padding(8.dp))
        }


        // no of bedroom

        if(field_Data_Com.value?.step_5?.firstOrNull()?.No_of_Bedrooms?.isNotEmpty() == true){

            val agreementType: List<Chips_Items_DC> = field_Data_Com.value?.step_5?.firstOrNull()?.No_of_Bedrooms
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_No_Of_Bedrooms(agreementType)
            Spacer(modifier = Modifier.padding(8.dp))
        }

        // no of balconies

        if(field_Data_Com.value?.step_5?.firstOrNull()?.No_of_Balconies?.isNotEmpty() == true){

            val agreementType: List<Chips_Items_DC> = field_Data_Com.value?.step_5?.firstOrNull()?.No_of_Balconies
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_No_Of_Balconies(agreementType , isError = errors.value[2] == true)
            Spacer(modifier = Modifier.padding(8.dp))
        }

        // pets allowed

        if(field_Data_Com.value?.step_5?.firstOrNull()?.Pets_Allowed?.isNotEmpty() == true){


            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_PetsAllowed(isError = errors.value[4] == true)
            Spacer(modifier = Modifier.padding(8.dp))
        }


        // other rooms

        if(field_Data_Com.value?.step_5?.firstOrNull()?.Agreement_Type?.isNotEmpty() == true){

            val agreementType: List<Chips_Items_DC> = field_Data_Com.value?.step_5?.firstOrNull()?.Agreement_Type
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_Other_Rooms(agreementType)
            Spacer(modifier = Modifier.padding(8.dp))
        }

        // furnishing status

        if(field_Data_Com.value?.step_5?.firstOrNull()?.Furnishing_Status?.isNotEmpty() == true){

            val agreementType: List<Chips_Items_DC> = field_Data_Com.value?.step_5?.firstOrNull()?.Furnishing_Status
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_Furnishing_Status(agreementType , isError = errors.value[5] == true)
            Spacer(modifier = Modifier.padding(8.dp))
        }

        // amenities

        if(field_Data_Com.value?.step_5?.firstOrNull()?.Amenities?.isNotEmpty() == true){

            val agreementType: List<Chips_Items_DC> = field_Data_Com.value?.step_5?.firstOrNull()?.Amenities
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_Amenities(agreementType , isError = errors.value[7] == true)
            Spacer(modifier = Modifier.padding(8.dp))
        }

        // property highloights

        if(field_Data_Com.value?.step_5?.firstOrNull()?.Property_Highlights?.isNotEmpty() == true){

            val agreementType: List<Chips_Items_DC> = field_Data_Com.value?.step_5?.firstOrNull()?.Property_Highlights
                ?.map { Chips_Items_DC(title = it) }
                ?: emptyList()
            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
            PP_Property_Highlights(agreementType)
            Spacer(modifier = Modifier.padding(8.dp))
        }

    }
}


@Composable
fun Form6_Common(
) {

    //// FORM flow
    val postFlow = constants.PostProperty_ViewModel.postFlow.collectAsState()

    val field_Data = constants.PostProperty_ViewModel.postFormCommon.collectAsStateWithLifecycle()
    // val field_Data_Com = constants.PostProperty_ViewModel.pp_form_Commercial_Fields.collectAsStateWithLifecycle()
    // val field_Data_Agri = constants.PostProperty_ViewModel.pp_form_Agriculture_Fields.collectAsStateWithLifecycle()
    val api_Type = constants.PostProperty_ViewModel.selected_Land_Type_PF2.collectAsStateWithLifecycle()
    val errors = constants.PostProperty_ViewModel.errors6.collectAsState()
    val selectedLandCatId = constants.PostProperty_ViewModel.get_Selected_Land_Cat_Id()

    val state = remember(selectedLandCatId) { mutableStateOf(if(postFlow.value == PostFlow.DRAFT || postFlow.value == PostFlow.EDIT) 0 else 2)}
    val apiCallMade = remember(selectedLandCatId) { mutableStateOf(false) }

    var retry by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()


    if (postFlow.value == PostFlow.EDIT || postFlow.value == PostFlow.DRAFT) {
        LaunchedEffect(selectedLandCatId) {
            println("FOURTH FORM--- $selectedLandCatId, API Type: ${api_Type.value} -$- ${state}")
        }

        // Main API call effect
        LaunchedEffect(api_Type.value, selectedLandCatId, retry) {
            if (selectedLandCatId != -1 && !apiCallMade.value) {
                apiCallMade.value = true
                state.value = 0
                println("Starting API call for type: ${api_Type.value}, landCatId: $selectedLandCatId")

                when (api_Type.value) {
                    1 -> {
                        constants.API_Vm.get_post_Form4_Residential(
                            land_categorie_id = selectedLandCatId
                        ) { apiResultHandling ->
                            when (apiResultHandling) {
                                is API_Result_Handling.Loading -> {
                                    state.value = 0
                                    println("Residential API: Loading")
                                }

                                is API_Result_Handling.Deactivated -> {
                                    println("Residential API: Deactivated")
                                }

                                is API_Result_Handling.Error -> {
                                    state.value = 1
                                    apiCallMade.value = false
                                    println("Residential API: Error")
                                }

                                is API_Result_Handling.Success -> {
                                    state.value = 2
                                    println("Residential API: Success, Fields: ${field_Data.value}")
                                }

                                is API_Result_Handling.NoData -> {
                                    state.value = 1
                                    apiCallMade.value = false
                                    println("Residential API: No Data")
                                }
                            }
                        }
                    }

                    2 -> {
                        constants.API_Vm.get_post_Form4_Commercial(
                            land_categorie_id = selectedLandCatId
                        ) { apiResultHandling ->
                            when (apiResultHandling) {
                                is API_Result_Handling.Loading -> {
                                    state.value = 0
                                    println("Commercial API: Loading")
                                }

                                is API_Result_Handling.Deactivated -> {
                                    println("Commercial API: Deactivated")
                                }

                                is API_Result_Handling.Error -> {
                                    state.value = 1
                                    apiCallMade.value = false
                                    println("Commercial API: Error")
                                }

                                is API_Result_Handling.Success -> {
                                    state.value = 2
                                    println("Commercial API: Success, Fields: ${field_Data.value}")
                                }

                                is API_Result_Handling.NoData -> {
                                    state.value = 1
                                    apiCallMade.value = false
                                    println("Commercial API: No Data")
                                }
                            }
                        }
                    }

                    3 -> {
                        constants.API_Vm.get_post_Form4_Agriculture(
                            land_categorie_id = selectedLandCatId
                        ) { apiResultHandling ->
                            when (apiResultHandling) {
                                is API_Result_Handling.Loading -> {
                                    state.value = 0
                                    println("Agriculture API: Loading")
                                }

                                is API_Result_Handling.Deactivated -> {
                                    println("Agriculture API: Deactivated")
                                }

                                is API_Result_Handling.Error -> {
                                    state.value = 1
                                    apiCallMade.value = false
                                    println("Agriculture API: Error")
                                }

                                is API_Result_Handling.Success -> {
                                    state.value = 2
                                    println("Agriculture API: Success, Fields: ${field_Data.value}")
                                }

                                is API_Result_Handling.NoData -> {
                                    state.value = 1
                                    apiCallMade.value = false
                                    println("Agriculture API: No Data")
                                }
                            }
                        }
                    }
                }
            }
        }

        // Update active fields when data changes
        LaunchedEffect(field_Data.value) {
            next_Active_Fields.clear()
            next_Active_Fields5.clear()
            next_Active_Fields6.clear()

            when (api_Type.value) {
                1 -> {
                    next_Active_Fields.addAll(
                        constants.PostProperty_ViewModel.getActiveFieldsResidential4(
                            field_Data.value
                        )
                    )
                    next_Active_Fields5.addAll(
                        constants.PostProperty_ViewModel.getActiveFieldsResidential5(
                            field_Data.value?.step_5?.firstOrNull()
                        )
                    )
                    next_Active_Fields6.addAll(
                        constants.PostProperty_ViewModel.getActiveFieldsResidential6(
                            field_Data.value?.step_6?.firstOrNull()
                        )
                    )
                    println("Residential Fields Active: ${next_Active_Fields.size}")
                }

                2 -> {

                    next_Active_Fields.addAll(
                        constants.PostProperty_ViewModel.getActiveFieldsCommercial4(
                            field_Data.value
                        )
                    )
                    next_Active_Fields5.addAll(
                        constants.PostProperty_ViewModel.getActiveFieldsCommercial5(
                            field_Data.value?.step_5?.firstOrNull()
                        )
                    )
                    next_Active_Fields6.addAll(
                        constants.PostProperty_ViewModel.getActiveFieldsCommercial6(
                            field_Data.value?.step_6?.firstOrNull()
                        )
                    )

                    println("Commercial Fields Active: ${next_Active_Fields.size} -- ${next_Active_Fields5}")
                }

                3 -> {
                    next_Active_Fields.addAll(
                        constants.PostProperty_ViewModel.getActiveFieldsAgriculture4(
                            field_Data.value
                        )
                    )
                    next_Active_Fields5.addAll(
                        constants.PostProperty_ViewModel.getActiveFieldsAgriculture5(
                            field_Data.value?.step_5?.firstOrNull()
                        )
                    )
                    next_Active_Fields6.addAll(
                        constants.PostProperty_ViewModel.getActiveFieldsAgriculture6(
                            field_Data.value?.step_6?.firstOrNull()
                        )
                    )

//                next_Active_Fields.addAll(constants.PostProperty_ViewModel.getActiveFieldsAgriculture4(field_Data.value))
                    println("Agriculture Fields Active: ${next_Active_Fields.size}")
                }
            }
        }

    }


//    val field_Data = constants.PostProperty_ViewModel.postFormCommon.collectAsStateWithLifecycle()
//
//    val errors = constants.PostProperty_ViewModel.errors6.collectAsState()


//    val field_Data = when (api_Type.value) {
//        1 -> constants.PostProperty_ViewModel.pp_form_Residential_Fields.collectAsStateWithLifecycle().value
//        2 -> constants.PostProperty_ViewModel.pp_form_Commercial_Fields.collectAsStateWithLifecycle().value
//        else -> constants.PostProperty_ViewModel.pp_form_Agriculture_Fields.collectAsStateWithLifecycle().value
//    }




    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsStateWithLifecycle()



    when {
        state.value == 0 -> CircularProgressIndicator()

        state.value == 1 -> {
            //  apiError.value = true
            Box (
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                API_Fail_UI(onReTryClick = {
                    apiCallMade.value = false
                    retry = retry + 1
                })
            }
        }


        state.value == 2 -> {
            LazyColumn (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            )
            {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            modifier = Modifier
                                .wrapContentSize()
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0XFFF4F4F4))
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                "Step 6",
                                color = newBlue,
                                fontSize = constants.textUnit(12),
                                fontFamily = constants.fontFamily(0)
                            )
                            Text(
                                "/7",
                                color = Color(0xff666666),
                                fontSize = constants.textUnit(12),
                                fontFamily = constants.fontFamily(0)
                            )
                        }

                        Spacer(modifier = Modifier.padding(4.dp))

                        Text(
                            "Price Details",
                            color = newBlack,
                            fontSize = constants.textUnit(24),
                            fontFamily = constants.fontFamily(0),
                            modifier = Modifier
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }

                item {
                    Column() {


                        /// is rent or lease
                        if (field_Data.value?.step_6?.firstOrNull()?.Is_this_property_for_rent_or_Lease?.isNotEmpty() == true) {

                            val agreementType: List<Chips_Items_DC> = field_Data.value?.step_6?.firstOrNull()?.Is_this_property_for_rent_or_Lease
                                ?.map { Chips_Items_DC(title = it) }
                                ?: emptyList()
                            //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
                            PP_Is_this_property_for_Rent_or_Lease(agreementType)
                            Spacer(modifier = Modifier.padding(8.dp))
                        }

                        println("is RENt or Lease -- ${flowData.value?.property_for_rent_or_lease}")

                        if (flowData.value?.property_for_rent_or_lease?.isEmpty() == true || flowData.value?.property_for_rent_or_lease == "Rent")
                        {
                            /// if rent



                            //// rent

                            if (field_Data.value?.step_6?.firstOrNull()?.Rent?.firstOrNull()?.Rent?.isNotEmpty() == true) {

                                PP_Rent(isError = errors.value[0] == true)
                                Spacer(modifier = Modifier.padding(8.dp))
                            }


                            //// DepositAmount

                            if (field_Data.value?.step_6?.firstOrNull()?.Rent?.firstOrNull()?.Deposit_Amount_month_of_rents?.isNotEmpty() == true) {

                                val data: List<Chips_Items_DC> =
                                    field_Data.value?.step_6?.firstOrNull()?.Rent?.firstOrNull()?.Deposit_Amount_month_of_rents
                                        ?.map { Chips_Items_DC(title = it) }
                                        ?: emptyList()
                                //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
                                PP_DepositAmount(data)
                                Spacer(modifier = Modifier.padding(8.dp))
                            }

                            //// duration of agreement

                            if (field_Data.value?.step_6?.firstOrNull()?.Rent?.firstOrNull()?.Duration_of_Agreement?.isNotEmpty() == true) {

                                val data: List<Chips_Items_DC> =
                                    field_Data.value?.step_6?.firstOrNull()?.Rent?.firstOrNull()?.Duration_of_Agreement
                                        ?.map { Chips_Items_DC(title = it) }
                                        ?: emptyList()
                                //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
                                PP_Duration_of_Agreement(data)
                                Spacer(modifier = Modifier.padding(8.dp))
                            }


                            //// lock in period

                            if (field_Data.value?.step_6?.firstOrNull()?.Rent?.firstOrNull()?.Lock_in_Period?.isNotEmpty() == true) {

                                val data: List<Chips_Items_DC> =
                                    field_Data.value?.step_6?.firstOrNull()?.Rent?.firstOrNull()?.Lock_in_Period
                                        ?.map { Chips_Items_DC(title = it) }
                                        ?: emptyList()
                                //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
                                PP_Lock_in_Period(data)
                                Spacer(modifier = Modifier.padding(8.dp))
                            }


                            //// notice period

                            if (field_Data.value?.step_6?.firstOrNull()?.Rent?.firstOrNull()?.Notice_Period?.firstOrNull()?.isNotEmpty() == true) {

                                val data: List<Chips_Items_DC> =
                                    field_Data.value?.step_6?.firstOrNull()?.Rent?.firstOrNull()?.Notice_Period
                                        ?.map { Chips_Items_DC(title = it) }
                                        ?: emptyList()
                                //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
                                PP_Notice_Period(data)
                                Spacer(modifier = Modifier.padding(8.dp))
                            }

                        }
                        else {


                            ////  if lease

                            // lease duratoin

                            if (field_Data.value?.step_6?.firstOrNull()?.Lease?.firstOrNull()?.Lease_Duration_in_Years?.isNotEmpty() == true) {

                                val data: List<Chips_Items_DC> =
                                    field_Data.value?.step_6?.firstOrNull()?.Lease?.firstOrNull()?.Lease_Duration_in_Years
                                        ?.dropLast(1)
                                        ?.map { Chips_Items_DC(title = it) }
                                        ?: emptyList()
                                //val errors = constants.PostProperty_ViewModel.errors.collectAsState()
                                PP_Lease_Duration(data , isError = errors.value[2] == true)

                                Spacer(modifier = Modifier.padding(8.dp))
                            }

                            // lease amount

                            if (field_Data.value?.step_6?.firstOrNull()?.Lease?.firstOrNull()?.Lease_Duration_in_Years?.isNotEmpty() == true) {

                                PP_LeaseAmount(isError = errors.value[1] == true)

                                Spacer(modifier = Modifier.padding(8.dp))
                            }

                        }

                    }
                }
            }
        }
    }
}



@Composable
fun PreviewScreen(navController: NavHostController) {


    val previewdata by constants.PostProperty_ViewModel.previewForm.collectAsState()

   var postFlow = constants.PostProperty_ViewModel.postFlow.collectAsState()

    val context = LocalContext.current


    var scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White)
           , contentAlignment = Alignment.Center
        ) {
            Image(painter = painterResource(R.drawable.left_arrow)
                , ""
                , modifier = Modifier
                    .size(25.dp)
                    .align(Alignment.CenterStart)
                    .noRippleClickable {
                        navController.navigateUp()
                    }
            )

            CommonText(
                "Preview"
                ,newBlack
                ,24
                ,0
                , modifier = Modifier
                    .align(Alignment.Center)
            )

        }


        constants.spacer(8)

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(6f)
                .background(Color.White)
            , verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                /// video , images animate part
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(450.dp)
                        .background(Color.Black)
                    , contentAlignment = Alignment.Center
                )
                {
                    if (previewdata?.images?.isNotEmpty() == true){
                        AnimatedImageSlider(imageList = previewdata!!.images , modifier = Modifier.fillMaxSize())
                    }
                    else if (previewdata?.images?.isEmpty() == true && previewdata?.video?.isNotEmpty() == true) {
                        var player = remember { ExoPlayer.Builder(context).build().apply {
                            previewdata?.video?.firstOrNull()?.url?.let { setMediaItem(MediaItem.fromUri(it)) }
                            prepare()
                            playWhenReady = true
                        } }



                        PlayerSurface(
                            player = player,
                            modifier = Modifier
                                .fillMaxSize()
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onTap = {
                                            if (player.isPlaying) {
                                                player.pause()
                                            } else {
                                                player.play()
                                            }
                                        }
                                    )
                                }
                        )
                    }
                    else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0xffF7F0DC))
                            , verticalArrangement = Arrangement.Center
                            , horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(painter = painterResource(R.drawable.emptymediaplaceholder) , "",
                                modifier = Modifier.size(180.dp))

                            CommonText("Hello, I’m your assistant. You haven’t\n added photos yet, but I’ll inform you when\n others request them.",
                                newBlack,
                                14,
                                1)
                        }
                }
                }


            }

            item {
                /// main details part

                Box(
                    modifier = Modifier

                        .fillMaxWidth()
                        .weight(3f)
                        .background(Color.White)
                    , contentAlignment = Alignment.Center
                )
                {
                    ListItem(
                        overlineContent = {
                            Box(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .background(Color(0xffF7F0DC), RoundedCornerShape(4.dp))
                                , contentAlignment = Alignment.Center
                            ){
                                CommonText(
                                    previewdata?.landCategoryText ?:""
                                    ,newBlue
                                    ,12
                                    ,0
                                    , modifier = Modifier.padding(horizontal = 6.dp , vertical = 6.dp)
                                )
                            }
                        }
                        , headlineContent = {
                            Column() {
                                constants.spacer(4)
                                CommonText(
                                    previewdata?.property_name ?:"" , newBlack, 20, 0
                                )
                                constants.spacer(4)

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                    , verticalAlignment = Alignment.CenterVertically
                                    , horizontalArrangement = Arrangement.Start
                                ) {
                                    Image(painter = painterResource(R.drawable.locationpinrento) , "",
                                        modifier = Modifier.size(12.dp)
                                    )

                                    constants.spacer(4)

                                    CommonText(
                                        previewdata?.locality ?:"",
                                        newBlack,
                                        12,
                                        3
                                    )
                                }

                                constants.spacer(8)

//                                CommonText(
//                                    "posted Need ago", Color(0xff969696), 12, 3
//                                )
                            }
                        }
                        , supportingContent = {
                           Row() {
                               if (previewdata?.rent_negotiable?: "" == "1") {
                                   Row(
                                       modifier = Modifier
                                           .wrapContentWidth()
                                           .clip(RoundedCornerShape(2.dp))
                                           .border(
                                               1.dp,
                                               Color(0xffBCE1C3),
                                               RoundedCornerShape(2.dp)
                                           )
                                           .background(Color(0xffE9F5EC))
                                           .padding(horizontal = 6.dp, vertical = 6.dp),
                                       verticalAlignment = Alignment.CenterVertically,
                                       horizontalArrangement = Arrangement.Start
                                   )
                                   {
                                       Image(
                                           painter = painterResource(R.drawable.rentonegotiablerento),
                                           "",
                                           modifier = Modifier.size(12.dp)
                                       )

                                       constants.spacer(4)

                                       CommonText(
                                           "Rent Negotiable", Color(0xff238F38), 12, 1
                                       )
                                   }
                               }

                               if (previewdata?.pets_allowed?: "" == "1") {
                                   Row(
                                       modifier = Modifier
                                           .wrapContentWidth()
                                           .clip(RoundedCornerShape(2.dp))
                                           .border(
                                               1.dp,
                                               Color(0xffBCE1C3),
                                               RoundedCornerShape(2.dp)
                                           )
                                           .background(Color(0xffE9F5EC))
                                           .padding(horizontal = 6.dp, vertical = 6.dp),
                                       verticalAlignment = Alignment.CenterVertically,
                                       horizontalArrangement = Arrangement.Start
                                   )
                                   {
                                       Image(
                                           painter = painterResource(R.drawable.petallowedrento),
                                           "",
                                           modifier = Modifier.size(12.dp)
                                       )

                                       constants.spacer(4)

                                       CommonText(
                                           "Pets Allowed", Color(0xff238F38), 12, 1
                                       )
                                   }
                               }

                               if (previewdata?.food_preferences?: "" == "Vegetarian only") {
                                   Row(
                                       modifier = Modifier
                                           .wrapContentWidth()
                                           .clip(RoundedCornerShape(2.dp))
                                           .border(
                                               1.dp,
                                               Color(0xffBCE1C3),
                                               RoundedCornerShape(2.dp)
                                           )
                                           .background(Color(0xffE9F5EC))
                                           .padding(horizontal = 6.dp, vertical = 6.dp),
                                       verticalAlignment = Alignment.CenterVertically,
                                       horizontalArrangement = Arrangement.Start
                                   )
                                   {
                                       Image(
                                           painter = painterResource(R.drawable.vegonlyrento), "",
                                           modifier = Modifier.size(12.dp)
                                       )

                                       constants.spacer(4)

                                       CommonText(
                                           "Vegetarians only", Color(0xff238F38), 12, 1
                                       )
                                   }
                               }
                           }
                        }
                        , colors = ListItemDefaults.colors(
                            containerColor = Color.White
                        )
                    )
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    ///sub details part

//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .weight(5f)
//                            .background(Color.Blue)
//                        , contentAlignment = Alignment.Center
//                    ){

                    previewdata?.let {
                        Property_Preview_Table(it)
                    }

                    constants.spacer(8)

                    previewdata?.let {
                        Property_Preview_FlowRow_Rento(it)
                    }

                    constants.spacer(16)


//                    }
                }
            }
        }


        Column() {
            HorizontalDivider()
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(64.dp)
                , verticalAlignment = Alignment.CenterVertically
                , horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                var textshow = if(previewdata?.rent?.isEmpty() == true) "Lease Amount" else "Rent"
                CommonText(
                    textshow,
                    newBlack,
                    20,
                    1
                )

                val formattedPrice = previewdata?.rent?.ifEmpty { previewdata?.lease_amount }.let { price ->
                    try {
                        val number = price.toString().toDouble()
                        NumberFormat.getNumberInstance(Locale("en", "IN")).format(number)
                    } catch (e: Exception) {
                        "Unavailable"
                    }
                } ?: "Unavailable"

                CommonText(
                    "\u20B9 $formattedPrice",
                    newBlack,
                    20,
                    1
                )
            }

            constants.spacer(4)

            HorizontalDivider()

            constants.spacer(4)

            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(.9f)
                    .height(56.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Brush.verticalGradient(newPurpleGradient))
                    .border(
                        1.dp,
                        Brush.linearGradient(newPurpleGradientBorder),
                        RoundedCornerShape(4.dp)
                    )
                    .noRippleClickable {
                        println("POST FLOW -- ${postFlow.value}")
                        scope.launch {
                        if (postFlow.value == PostFlow.DRAFT) {

                                val requestBody =
                                    constants.PostProperty_ViewModel.buildUploadRequestBody()

                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(draft = 6)
                            }

                                handleDraftSubmission(
                                    requestBody = requestBody,
                                    navController = navController,
                                    "1",
                                    onComplete = {
                                        println("On COmplete Draft model preview 1")
                                        get_Form_Publish_API_CALL { result ->
                                            when (result) {
                                                0 -> {}
                                                1 -> {}
                                                2 -> {}
                                                3 -> {
                                                    constants.Profile_ViewModel.set_From_Repost(
                                                        0
                                                    )
                                                    constants.PostProperty_ViewModel.set_Post_Form_Flow(
                                                        -1
                                                    )
                                                    constants.Common_H_ViewModel.selectedBABTab(0)

                                                    constants.PostProperty_ViewModel.setPostFlow(
                                                        PostFlow.NONE
                                                    )

                                                    constants.PostProperty_ViewModel.clear_Selected_Fields_Form4()


                                                    constants.URL_COMPLETED.clear()
                                                    constants.PostProperty_ViewModel.clear_Media()

                                                    constants.PostProperty_ViewModel.set_postSuccessful_State(
                                                        true
                                                    )

                                                    navController.navigate(
                                                        PostPropertyFlow.Common_Screen.route
                                                    )
                                                }
                                            }
                                        }
                                    }
                                )
                            }
                            else {
                                get_Form_Publish_API_CALL { result ->
                                    when (result) {
                                        0 -> {}
                                        1 -> {}
                                        2 -> {}
                                        3 -> {
                                            constants.Profile_ViewModel.set_From_Repost(
                                                0
                                            )
                                            constants.PostProperty_ViewModel.set_Post_Form_Flow(
                                                -1
                                            )
                                            constants.Common_H_ViewModel.selectedBABTab(0)

                                            constants.URL_COMPLETED.clear()
                                            constants.PostProperty_ViewModel.clear_Media()

                                            constants.PostProperty_ViewModel.set_postSuccessful_State(
                                                true
                                            )

                                            navController.navigate(
                                                PostPropertyFlow.Common_Screen.route
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                , contentAlignment = Alignment.Center
            ){
                CommonText(
                    "Post Property",
                    Color.White,
                    14,
                    0
                )
            }

            constants.spacer(4)

        }



    }
}


@Composable
fun AnimatedImageSlider(
    imageList: List<Image>,
    modifier: Modifier = Modifier,
    durationMillis: Int = 3000,      // animation speed
    delayMillis: Long = 2000L        // time before next slide
) {
    var currentIndex by remember { mutableStateOf(0) }

    // Infinite auto-slide
    LaunchedEffect(currentIndex) {
        delay(delayMillis)
        currentIndex = (currentIndex + 1) % imageList.size
    }

    // Animated transition
    val transition = updateTransition(targetState = currentIndex, label = "imageTransition")

    val alpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis) }, label = "alpha"
    ) { 1f }

    val scale by transition.animateFloat(
        transitionSpec = { tween(durationMillis) }, label = "scale"
    ) { 1.05f } // small zoom

    val rotation by transition.animateFloat(
        transitionSpec = { tween(durationMillis) }, label = "rotation"
    ) { 1.5f } // slight rotation

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(240.dp),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = imageList[currentIndex].url,
            contentDescription = null,
            modifier = Modifier
                .graphicsLayer {
                    this.alpha = alpha
                    this.scaleX = scale
                    this.scaleY = scale
                    this.rotationZ = rotation
                }
                .clip(RoundedCornerShape(14.dp))
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}


fun mapPropertyFieldsRento2(
    property: FormPreviewRento,
    ignoreFields: Set<String> = emptySet()
): List<Pair<String, String>> {
    return FormPreviewRento::class.memberProperties
        .filterNot { it.name in ignoreFields } // skip ignored fields
        .mapNotNull { prop ->
            val value = prop.get(property)
            when (value) {
                null -> null
                is String -> if (value.isBlank()) null else formatLabel(prop.name) to value
                is Int -> formatLabel(prop.name) to value.toString()
                is List<*> -> if (value.isEmpty()) null else formatLabel(prop.name) to value.joinToString(", ")
                else -> formatLabel(prop.name) to value.toString()
            }
        }

}

fun mapPropertyFieldsRento(
    property: FormPreviewRento,
    ignoreFields: Set<String> = emptySet()
): List<Pair<String, String>> {

    val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val result = mutableListOf<Pair<String, String>>()

    fun add(label: String, value: String?) {
        if (!value.isNullOrBlank()) {
            result.add(label to value)
        }
    }

    // ---------- AREA DIMENSION ----------
    if (
        !ignoreFields.contains("area_length") &&
        !ignoreFields.contains("area_width")
    ) {
        val length = property.area_length
        val lengthUnit = property.area_length_unit
        val width = property.area_width
        val widthUnit = property.area_width_unit

        if (!length.isNullOrBlank() && !width.isNullOrBlank()) {
            add(
                "Area Dimension",
                "$length $lengthUnit x $width $widthUnit"
            )
        }
    }

    // ---------- FACADE DIMENSION ----------
    if (
        !ignoreFields.contains("facade_height") &&
        !ignoreFields.contains("facade_width")
    ) {
        val height = property.facade_height
        val heightUnit = property.facade_height_unit
        val width = property.facade_width
        val widthUnit = property.facade_width_unit

        if (!height.isNullOrBlank() && !width.isNullOrBlank()) {
            add(
                "Facade Dimension",
                "$height $heightUnit x $width $widthUnit"
            )
        }
    }

    // ---------- FLOOR PLAN ----------
    add("Floor Plan", property.bhk_type)

    // ---------- PROPERTY TYPE ----------
    add("Property Type", property.is_this_property_for_rent_or_lease)

    // ---------- AREA FIELDS WITH UNITS ----------
    property.carpet_area?.takeIf { it.isNotBlank() }?.let {
        add("Carpet Area", "$it ${property.carpet_area_unit}")
    }

    property.built_up_area?.takeIf { it.isNotBlank() }?.let {
        add("Built-up Area", "$it ${property.built_up_area_unit}")
    }

    property.super_built_up_area?.takeIf { it.isNotBlank() }?.let {
        add("Super Built-up Area", "$it ${property.super_built_up_area_unit}")
    }

    // ---------- GENERIC FALLBACK (REFLECTION) ----------
    FormPreviewRento::class.memberProperties
        .filterNot {
            it.name in ignoreFields ||
                    it.name in listOf(
                "area_length",
                "area_width",
                "area_length_unit",
                "area_width_unit",
                "facade_height",
                "facade_height_unit",
                "facade_width",
                "facade_width_unit",
                "bhk_type",
                "is_this_property_for_rent_or_lease",
                "carpet_area",
                "carpet_area_unit",
                "built_up_area",
                "built_up_area_unit",
                "super_built_up_area",
                "super_built_up_area_unit"
            )
        }
        .forEach { prop ->
            val value = prop.get(property)

            when {
                value == null -> Unit

                prop.name == "availability_from" && value is String -> {
                    value.toLongOrNull()?.let {
                        add(
                            formatLabel(prop.name),
                            dateFormatter.format(Date(it))
                        )
                    }
                }

                value is String && value.isNotBlank() -> {
                    add(formatLabel(prop.name), value)
                }

                value is Int || value is Long -> {
                    add(formatLabel(prop.name), value.toString())
                }

                value is List<*> && value.isNotEmpty() -> {
                    add(formatLabel(prop.name), value.joinToString(", "))
                }
            }
        }

    return result
}


@Composable
fun Property_Preview_Table(property: FormPreviewRento) {

    if (property == null) return

    val ignoreFields2 =
        setOf("city" ,"state" , "country", "land_categorie_id" , "land_type_id" , "user_type" , "user_post_id", "locality"
            ,"latitude" , "longitude" , "created_at" , "property_name" , "price" , "video" , "image_urls" , "thumbnail" , "is_report")

    val ignoreFields = setOf(
        "city", "state", "country", "land_categorie_id", "land_type_id", "user_type",
        "user_post_id", "locality", "latitude", "longitude", "created_at",
        "property_name", "price", "video", "image_urls", "thumbnail", "is_report",
        "washroom_details", "other_rooms", "amenities", "suitable_business_type",
        "property_highlights", "fire_safety_measures",
        "pincode","draft","facade_height_unit", "landTypeText","landCategoryText",
        "is_sold"
        //"area_length_unit" ,"area_width_unit"
        ,"built_up_area_unit"
        ,"carpet_area_unit"
        ,"deposit_amount_month_of_rents_type",
        "duration_of_agreement_type","facade_width_unit"
        ,"lease_negotiable","lock_in_period_type","map_config","pantry_size_unit",
        "post_type","property_area_unit","rent_negotiable"
        ,"super_built_up_area_unit" ,"images","video","status","U_ID", "address" ,

        //"agreement_type",
        //   "area_length" ,
        //  "area_width",
        // "bhk_type",
        "food_preferences"
        // "is_this_property_for_rent_or_lease"
        ,"notice_period",
        "pets_allowed",
        "preferred_tenants",
        "rent",
        "lease_amount" ,
        "is_expired",
        "account_status",
        "is_sold"

        // "lease_duration_in_years"

    )
    val fields = mapPropertyFieldsRento(property, ignoreFields)


    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            // .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xffE8E8E8), RoundedCornerShape(8.dp))
            .background(newWhite)
    )
    {
        for ( i in 0 until fields.size){
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(50.dp)
                        .background(if (i % 2 == 0) Color(0xffFCFCFC) else Color.White)
                    , horizontalArrangement = Arrangement.Start
                    , verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(4.9f)
                            .padding(horizontal = 16.dp)
                        , contentAlignment = Alignment.CenterStart
                    ){
                        Text(fields[i].first, color = newBlack , fontSize = constants.textUnit(14) , fontFamily = constants.fontFamily(1))
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(.2f)
                        , contentAlignment = Alignment.Center
                    ){
                        Text(":")
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(4.9f)
                            .padding(horizontal = 8.dp)
                        , contentAlignment = Alignment.CenterStart
                    ){
                        Text(fields[i].second , color = newBlack , fontSize = constants.textUnit(14) , fontFamily = constants.fontFamily(1))
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color(0xffE8E8E8))
                )
            }
        }
    }
}



@Composable
fun Property_Preview_FlowRow_Rento(property: FormPreviewRento) {
    println("DATA COMING -- ${property}")

    // Define sections with their data
    val sections = listOf(
        Section(
            title = "Washroom Details",
            items = property.washroom_details?.split(",")?.map { it.trim() }?.filter { it.isNotEmpty() } ?: emptyList()
        ),
        Section(
            title = "Business Type",
            items = property.suitable_business_type?.split(",")?.map { it.trim() }?.filter { it.isNotEmpty() } ?: emptyList()
        ),
        Section(
            title = "Other Rooms",
            items = property.other_rooms?.split(",")?.map { it.trim() }?.filter { it.isNotEmpty() } ?: emptyList()
        ),
//        Section(
//            title = "Office Previously Used for",
//            items = property.office_previously_used_for?.split(",")?.map { it.trim() }?.filter { it.isNotEmpty() } ?: emptyList()
//        ),
        Section(
            title = "Fire Safety Measures",
            items = property.fire_safety_measures?.split(",")?.map { it.trim() }?.filter { it.isNotEmpty() } ?: emptyList()
        ),
        Section(
            title = "Amenities",
            items = property.amenities?.split(",")?.map { it.trim() }?.filter { it.isNotEmpty() } ?: emptyList()
        ),
        Section(
            title = "Property Highlights",
            items = property.property_highlights?.split(",")?.map { it.trim() }?.filter { it.isNotEmpty() } ?: emptyList()
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
        //.padding(16.dp)
        ,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        sections.forEach { section ->
            if (section.items.isNotEmpty()) {
                PropertySection_Rento(
                    title = section.title,
                    items = section.items
                )
            }
        }
    }
}


@Composable
fun PropertySection_Rento(
    title: String,
    items: List<String>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Section Title
        Text(
            text = title,
            color = Color.Black,
            fontSize = constants.textUnit(16),
            fontFamily = constants.fontFamily(1)
        )

        // Tags in FlowRow
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items.forEach { item ->
                Box(
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item,
                        color = Color.Black,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(1)
                    )
                }
            }
        }
    }
}









//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun PP_Seventh_Formold(onHover: MutableState<Boolean> , hoveredMedia: MutableState<UploadPropertyMedia?>)  {
//
//    val context = LocalContext.current
//
//    val snackbarHostState = remember { SnackbarHostState() }
//    val coroutineScope = rememberCoroutineScope()
//
//    val mediaItems by constants.PostProperty_ViewModel.mediaList.collectAsState()
//
//    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
//
//
//    println("MEDIAITEMSS lIST DAATA CHERCK -- ${mediaItems}")
//
//    val imagePickerLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.OpenMultipleDocuments()
//    ) { uris: List<Uri> ->
//        URL_COMPLETED.clear()
//        handleSelectedImages(context, uris , snackbarHostState , coroutineScope)
//    }
//
//    val videoPickerLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.OpenDocument()
//    ) { uri: Uri? ->
//        uri?.let {
//            URL_COMPLETED.clear()
//            handleSelectedVideo(context, it , snackbarHostState , coroutineScope)
//        }
//    }
//
//
//
//    var headingDropDown = remember { mutableStateOf(false) }
//
//
//    var apiState = remember { mutableStateOf(0) }
//
//
//    val headingItems = constants.PostProperty_ViewModel.headingItems.collectAsState()
//
//    val selectedHeading = constants.PostProperty_ViewModel.getSelectedHeading()
//
//
//    var selectedTab by remember { mutableStateOf(0) }
//
//    val imageItems = mediaItems.filter { !it.isVideo }
//
//    val videoItems = mediaItems.filter { it.isVideo }
//
//
//
//
//
//
//    Box {
//        Column {
//            Row(
//                modifier = Modifier
//                    .align(Alignment.Start)
//                    .wrapContentSize()
//                    .clip(RoundedCornerShape(4.dp))
//                    .background(Color(0XFFF4F4F4))
//                    .padding(horizontal = 8.dp, vertical = 4.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Start
//            )
//            {
//                Text(
//                    "Step 7",
//                    color = newBlue,
//                    fontSize = constants.textUnit(12),
//                    fontFamily = constants.fontFamily(0)
//                )
//                Text(
//                    "/7",
//                    color = Color(0xff666666),
//                    fontSize = constants.textUnit(12),
//                    fontFamily = constants.fontFamily(0)
//                )
//            }
//
//            Spacer(modifier = Modifier.padding(4.dp))
//
//            Text(
//                "Add photos / videos of property",
//                color = newBlack,
//                fontSize = constants.textUnit(24),
//                fontFamily = constants.fontFamily(0),
//                modifier = Modifier
//                    .align(Alignment.Start)
//            )
//
//
//            Text(
//                buildAnnotatedString {
//                    withStyle(style = SpanStyle(color = newBlack)) {
//                        append("Upload Video / Photos ")
//                    }
//                    withStyle(style = SpanStyle(color = Color.Red)) {
//                        append("* ")
//                    }
//                    withStyle(style = SpanStyle(color = Color(0xff666666))) {
//                        append("(minimum 5 photos)")
//                    }
//                },
//                fontSize = constants.textUnit(16),
//                fontFamily = constants.fontFamily(1),
//            )
//
//            constants.spacer(2)
//
//
//            Text(
//                "upload video max of 10mb and photo max of 2mb. Supported formats mp4, png, jpeg .",
//                color = Color(0xff666666),
//                fontSize = constants.textUnit(12),
//                fontFamily = constants.fontFamily(2),
//                modifier = Modifier
//                    .align(Alignment.Start)
//            )
//
//            constants.spacer(2)
//
//
//            val hasVideo = mediaItems.any { it.isVideo }
//            val hasPhotos = mediaItems.any { !it.isVideo }
//            val maxImagesReached = mediaItems.count { !it.isVideo } >= 10
//
//
//            Spacer(modifier = Modifier.padding(8.dp))
//
//            constants.spacer(2)
//
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .fillMaxHeight(.4f)
//                    .dashedBorder(
//                        strokeWidth = 2.dp,
//                        dashLength = 10.dp,
//                        gapLength = 5.dp,
//                        color = newBlue,
//                        cornerRadius = 16.dp
//                    ), contentAlignment = Alignment.Center
//            )
//            {
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally
//                )
//                {
//                    SubcomposeAsyncImage(
//                        model = R.drawable.upload_button, "",
//                        modifier = Modifier
//                            .size(if (forTab())48.dp else 38.dp)
//                    )
//                    Spacer(modifier = Modifier.padding(8.dp))
//                    Row {
//                        // Upload Photo Button
//                        Box(
//                            modifier = Modifier
//                                .height(if (forTab())48.dp else 32.dp)
//                                .width(if (forTab()) 128.dp else 104.dp)
//                                .clip(RoundedCornerShape(4.dp))
//                                .background(Color.White)
//                                .noRippleClickable{
//
//                                    if (!hasVideo && !maxImagesReached)
//                                        imagePickerLauncher.launch(arrayOf("image/*"))
//                                    else if (hasVideo)
//                                        coroutineScope.launch {
//                                            snackbarHostState.showSnackbar("Please remove the uploaded video to add photos")
//                                        }
//                                    else
//                                        coroutineScope.launch {
//                                            snackbarHostState.showSnackbar("Maximum 10 images can be uploaded")
//                                        }
//                                }
//                                .border(
//                                    1.dp,
//                                    if (hasVideo || maxImagesReached) newGray else newBlue,
//                                    RoundedCornerShape(4.dp)
//                                ),
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Text(
//                                "Upload Photo",
//                                color = if (hasVideo || maxImagesReached) Color.Gray else newBlue,
//                                fontSize = constants.textUnit(14),
//                                fontFamily = constants.fontFamily(2)
//                            )
//                        }
//
//                        Spacer(modifier = Modifier.padding(8.dp))
//
//                        // Upload Video Button
//                        Box(
//                            modifier = Modifier
//                                .height(if (forTab())48.dp else 32.dp)
//                                .width(if (forTab()) 128.dp else 104.dp)
//                                .clip(RoundedCornerShape(4.dp))
//                                .background(if (hasPhotos || hasVideo) Color(0xFFE0E0E0) else newBlue)
//                                .noRippleClickable{
//
//                                    if (!hasPhotos && !hasVideo)
//                                        videoPickerLauncher.launch(arrayOf("video/*"))
//                                    else if (hasPhotos)
//                                        coroutineScope.launch {
//                                            snackbarHostState.showSnackbar("Please remove the uploaded photos to add video")
//                                        }
//                                    else
//                                        coroutineScope.launch {
//                                            snackbarHostState.showSnackbar("Maximum 1 video can be uploaded")
//                                        }
//
//                                },
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Text(
//                                "Upload Video",
//                                color = if (hasPhotos) Color.Gray else Color.White,
//                                fontSize = constants.textUnit(14),
//                                fontFamily = constants.fontFamily(2)
//                            )
//                        }
//                    }
//
//                }
//            }
//
//
//            Spacer(modifier = Modifier.padding(16.dp))
//
//
//            TabRow(
//                selectedTabIndex = selectedTab,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Tab(
//                    selected = selectedTab == 0,
//                    onClick = { selectedTab = 0 },
//                    text = { Text("Images (${imageItems.size})") }
//                )
//                Tab(
//                    selected = selectedTab == 1,
//                    onClick = { selectedTab = 1 },
//                    text = { Text("Videos (${videoItems.size})") }
//                )
//            }
//
//            val defaultHeadings = listOf(
//                "Exterior",
//                "Living Room",
//                "Bedroom",
//                "Kitchen",
//                "Bathroom",
//                "Balcony",
//                "Floor Plan"
//            )
//
//
//            val itemsToShow = if (selectedTab == 0) imageItems else videoItems
//
//            val coverId by constants.PostProperty_ViewModel.coverPhotoId.collectAsState()
//
//
//            LazyVerticalGrid(
//                columns = GridCells.Fixed(3),
//                verticalArrangement = Arrangement.spacedBy(8.dp),
//                horizontalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                items(itemsToShow.size) { index ->
//                    val media = itemsToShow[index]
//                    val heading = defaultHeadings[index % defaultHeadings.size]
//
//                    if (selectedTab == 0) {
//                        PhotoItem(
//                            media = media,
//                            isCover = coverId == media.uri.toString(),
//                            onCoverSelect = {
//                                constants.PostProperty_ViewModel.setCoverPhoto(media.uri.toString())
//                            },
//                            onClick = {}
//                        )
//                    } else {
//                        VideoItem(
//                            media = media,
//                            heading = heading,
//                            headingDropDown = headingDropDown,
//                            onClick = {
//                                hoveredMedia.value = media
//                                onHover.value = true
//                            }
//                        )
//                    }
//                }
//            }
//
//
//
//
//            /*  LazyRow(
//                  modifier = Modifier.fillMaxWidth(),
//                  horizontalArrangement = Arrangement.spacedBy(8.dp)
//              )
//              {
//                  items(mediaItems.size) { index ->
//                      val media = mediaItems[index]
//
//                      var thumbnailBitmap by remember { mutableStateOf<Bitmap?>(null) }
//
//                      LaunchedEffect(media.uri) {
//                          thumbnailBitmap = getVideoThumbnail(context, media.uri)
//                      }
//
//                      Box(
//                          modifier = Modifier
//                              .size(108.dp)
//                              .clip(RoundedCornerShape(8.dp))
//                              .background(Color.LightGray)
//                      )
//                      {
//
//                          if (media.isVideo) {
//                              // Show a video icon or thumbnail (simplified)
//                              val thumbnailBitmap by remember(media.uri) {
//                                  mutableStateOf(getVideoThumbnail(context, media.uri))
//                              }
//
//                              if (thumbnailBitmap != null) {
//                                  Image(
//                                      bitmap = thumbnailBitmap!!.asImageBitmap(),
//                                      contentDescription = "Video thumbnail",
//                                      contentScale = ContentScale.Crop,
//                                      modifier = Modifier
//                                          .fillMaxSize()
//                                          .noRippleClickable{
//                                              constants.toggleOnHoverEnable(true)
//                                              hoveredMedia.value = media
//                                              onHover.value = true
//                                          }
//                                  )
//                              } else {
//                                  Box(
//                                      modifier = Modifier.fillMaxSize(),
//                                      contentAlignment = Alignment.Center
//                                  ) {
//                                      Icon(
//                                          painter = painterResource(R.drawable.play_arrow),
//                                          contentDescription = "Video",
//                                          tint = Color.White
//                                      )
//                                  }
//                              }
//
//                          } else {
//                              //Column {
//                              SubcomposeAsyncImage(
//                                  model = media.uri,
//                                  contentDescription = "Image",
//                                  contentScale = ContentScale.FillBounds,
//                                  modifier = Modifier
//                                      .fillMaxSize()
//                                      .noRippleClickable{
//                                          hoveredMedia.value = media
//                                          onHover.value = true
//                                      }
//                              )
//  //                            SubcomposeAsyncImage(
//  //                                model = constants.URL_COMPLETED.value,
//  //                                contentDescription = "Image",
//  //                                contentScale = ContentScale.FillBounds,
//  //                                modifier = Modifier
//  //                                    .fillMaxSize()
//  //                                    .noRippleClickable{
//  //                                        hoveredMedia.value = media
//  //                                        onHover.value = true
//  //                                    }
//  //                            )
//                             // }
//                          }
//
//                          // Delete button
//                          Box(
//                              modifier = Modifier
//                                  .padding(4.dp)
//                                  .align(Alignment.TopEnd)
//                                  .size(24.dp)
//                                  .clip(CircleShape)
//                                  .background(Color.White)
//                                  .noRippleClickable{
//                                      constants.PostProperty_ViewModel.removeMediaAt(index)
//                                  },
//                              contentAlignment = Alignment.Center
//                          ) {
//                              SubcomposeAsyncImage(
//                                  model = R.drawable.reelsdelete,
//                                  "",
//                                  colorFilter = ColorFilter.tint(Color.Red),
//                                  modifier = Modifier.size(12.dp)
//                              )
//                          }
//                      }
//
//                      photoItem(media ,)
//                  }
//              }*/
//
//            Spacer(modifier = Modifier.weight(1f))
//
//            SnackbarHost(
//                hostState = snackbarHostState,
//                modifier = Modifier
//                    .align(Alignment.CenterHorizontally),
//                snackbar = { data ->
//                    Snackbar(
//                        containerColor = newWhite, // 👈 set background color
//                        contentColor = Color.Black, // 👈 set text/icon
//                        modifier = Modifier
//                            .padding(8.dp)
//                            .border(1.dp, Color.Red, shape = RoundedCornerShape(4.dp)),
//                        shape = RoundedCornerShape(4.dp)
//                    ) {
//                        Text(text = data.visuals.message)
//                    }
//                }
//            )
//        }
//    }
//
//
//
//    if (headingDropDown.value) {
//
//
//        LaunchedEffect(Unit) {
//            constants.API_Vm.getPhotoHeadings()
//            { apiResultHandling ->
//                when (apiResultHandling) {
//                    is API_Result_Handling.Loading -> {
//                        //constants.Common_H_ViewModel.changeStatus(true)
//
//                        //resultCallback(2)
//                        apiState.value = 0
//                    }
//
//                    is API_Result_Handling.NoData -> {
//                        //constants.Common_H_ViewModel.changeStatus(false)
//                        apiState.value = 3
//                    }
//
//                    is API_Result_Handling.Error -> {
//                        // constants.Common_H_ViewModel.changeStatus(false)
//                        apiState.value = 2
//                        // resultCallback(1)
//                    }
//
//                    is API_Result_Handling.Success -> {
//                        //constants.Common_H_ViewModel.changeStatus(false)
//                        apiState.value = 1
//                        // resultCallback(0)
//                    }
//
//                    is API_Result_Handling.Deactivated -> {
//                        // resultCallback(5)
//                        apiState.value = 4
//                    }
//                }
//            }
//        }
//
//
//        ModalBottomSheet(
//            sheetState = bottomSheetState
//            , onDismissRequest = {
//                headingDropDown.value = false
//            }
//            , containerColor = Color.White
//        ) {
//            LazyColumn (
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 8.dp)
//            ) {
//                item {
//                    when (apiState.value) {
//                        0 -> {
//                            Box(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .height(600.dp)
//                                , contentAlignment = Alignment.Center
//                            ) {
//                                CircularProgressIndicator()
//                            }
//                        }
//
//                        1 -> {
//                            Column(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                , horizontalAlignment = Alignment.Start
//                            ) {
//                                CommonText(
//                                    "What is this photo about?"
//                                    , newBlack
//                                    ,18
//                                    ,1
//                                )
//
//
//                                headingItems.value.forEach { item ->
//                                    Row(
//                                        modifier = Modifier.fillMaxWidth(),
//                                        verticalAlignment = Alignment.CenterVertically
//                                        , horizontalArrangement = Arrangement.SpaceBetween
//                                    ) {
//
//                                        Text(item.title, fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))
//
//                                        RadioButton(
//                                            selected = item.isSelected.value,
//                                            onClick = {
//                                                constants.PostProperty_ViewModel.updateMediaHeading(selectedMediaId, heading)
//                                                headingDropDown.value = false
//                                                constants.PostProperty_ViewModel.selectSingleHeading(item.title)
//                                            }
//                                        )
//
//                                    }
//                                }
//
//
//                            }
//                        }
//                        2 -> {
//                            /// error
//                            Box(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .height(600.dp), contentAlignment = Alignment.Center
//                            ) {
//                                Text("Something went wrong")
//                            }
//                        }
//                        3 -> {
//                            // empty
//                            Box(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .height(600.dp), contentAlignment = Alignment.Center
//                            ) {
//                                Text("No Data Available")
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//

//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun PP_Seventh_Form333(
//    onHover: MutableState<Boolean>,
//    hoveredMedia: MutableState<UploadPropertyMedia?>
//) {
//
//    val context = LocalContext.current
//    val scope = rememberCoroutineScope()
//    val snackbarHostState = remember { SnackbarHostState() }
//
//    val mediaItems by constants.PostProperty_ViewModel.mediaList.collectAsState()
//    val headingItems by constants.PostProperty_ViewModel.headingItems.collectAsState()
//    val coverId by constants.PostProperty_ViewModel.coverPhotoId.collectAsState()
//
//    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
//    val headingDropDown = remember { mutableStateOf(false) }
//
//    // Upload Launchers
//    val imagePickerLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.OpenMultipleDocuments()
//    ) { uris ->
//        URL_COMPLETED.clear()
//        handleSelectedImages(context, uris, snackbarHostState, scope)
//    }
//
//    val videoPickerLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.OpenDocument()
//    ) { uri ->
//        uri?.let {
//            URL_COMPLETED.clear()
//            handleSelectedVideo(context, it, snackbarHostState, scope)
//        }
//    }
//
//    var selectedMediaId by remember { mutableStateOf<String?>(null) }
//
//
//
//    Column(modifier = Modifier.fillMaxSize()) {
//
//        StepHeader()
//
//        Spacer(Modifier.padding(16.dp))
//
//        UploadBox(
//            mediaItems = mediaItems,
//            onPickImage = { imagePickerLauncher.launch(arrayOf("image/*")) },
//            onPickVideo = { videoPickerLauncher.launch(arrayOf("video/*")) }
//        )
//
//
//        Spacer(Modifier.padding(16.dp))
//
//
//        val itemsToShow = mediaItems
//
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(2),
//            verticalArrangement = Arrangement.spacedBy(8.dp),
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
//            , modifier = Modifier
//                .padding(horizontal = 8.dp)
//        ) {
//            items(itemsToShow.size) { index ->
//                val media = itemsToShow[index]
//
//
//                if (!media.isVideo) {
//                    PhotoItem(
//                        index = index,
//                        media = media,
//                        // onClickHeading = { selectedMediaId = media.id; headingDropDown.value = true },
//                        onClickHeading = { selectedMediaId = media.id; headingDropDown.value = true },
//
//                        onCoverSelected = { constants.PostProperty_ViewModel.setCoverPhoto(media.id) },
//                        onDelete = { constants.PostProperty_ViewModel.deleteMedia(media.id) }
//                    )
//
//                } else {
//                    VideoItem(
//                        index = index,
//                        media = media,
//                        thumbnail = getVideoThumbnail(context, media.uri),
//                        onClickHeading = { selectedMediaId = media.id; headingDropDown.value = true },
//                        onDelete = { constants.PostProperty_ViewModel.deleteMedia(media.id) }
//                    )
//
//                }
//
//
//            }
//
//        }
//
//
//
//        Spacer(Modifier.weight(1f))
//
//        SnackbarHost(
//            hostState = snackbarHostState,
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        )
//    }
//
//
//
//
//
//    var apiState = remember { mutableStateOf(0) }
//
//
//
//    // PHOTO HEADING SELECTION BOTTOM SHEET
//    if (headingDropDown.value) {
//
//        if (headingItems.isEmpty()) {
//            LaunchedEffect(Unit) {
//                constants.API_Vm.getPhotoHeadings()
//                { apiResultHandling ->
//                    when (apiResultHandling) {
//                        is API_Result_Handling.Loading -> {
//                            //constants.Common_H_ViewModel.changeStatus(true)
//
//                            //resultCallback(2)
//                            apiState.value = 0
//                        }
//
//                        is API_Result_Handling.NoData -> {
//                            //constants.Common_H_ViewModel.changeStatus(false)
//                            apiState.value = 3
//                        }
//
//                        is API_Result_Handling.Error -> {
//                            // constants.Common_H_ViewModel.changeStatus(false)
//                            apiState.value = 2
//                            // resultCallback(1)
//                        }
//
//                        is API_Result_Handling.Success -> {
//                            //constants.Common_H_ViewModel.changeStatus(false)
//                            apiState.value = 1
//                            // resultCallback(0)
//                        }
//
//                        is API_Result_Handling.Deactivated -> {
//                            // resultCallback(5)
//                            apiState.value = 4
//                        }
//                    }
//                }
//            }
//        }
//
//        ModalBottomSheet(
//            sheetState = bottomSheetState,
//            onDismissRequest = {
//                headingDropDown.value = false
//            }
//        ) {
//            Text(
//                "What is this photo about?",
//                fontSize = constants.textUnit(18),
//                fontFamily = constants.fontFamily(1),
//                modifier = Modifier.padding(16.dp)
//            )
//
//            constants.spacer(8)
//
//            when (apiState.value) {
//                0 -> {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(600.dp)
//                        , contentAlignment = Alignment.Center
//                    ) {
//                        CircularProgressIndicator()
//                    }
//                }
//
//                1 -> {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                        , horizontalAlignment = Alignment.Start
//                        , verticalArrangement = Arrangement.spacedBy(8.dp)
//                    ) {
//
//                        headingItems.forEach { item ->
//
//                            Row(
//                                modifier = Modifier
//                                    .fillMaxWidth(),
//                                verticalAlignment = Alignment.CenterVertically,
//                                horizontalArrangement = Arrangement.SpaceBetween
//                            ) {
//
//                                Text(item.title, fontSize = constants.textUnit(16))
//
//                                RadioButton(
//                                    selected = selectedMediaId?.let { id ->
//                                        constants.PostProperty_ViewModel.mediaList.value
//                                            .find { it.id == id }?.heading == item.title
//                                    } ?: false,
//                                    onClick = {
//                                        selectedMediaId?.let { id ->
//                                            constants.PostProperty_ViewModel.updateHeading(id, item.title)
//                                        }
//                                        headingDropDown.value = false
//                                    }
//                                )
//                            }
//
//
//                            constants.spacer(8)
//                        }
//
//                    }
//                }
//                2 -> {
//                    /// error
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(600.dp), contentAlignment = Alignment.Center
//                    ) {
//                        Text("Something went wrong")
//                    }
//                }
//                3 -> {
//                    // empty
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(600.dp), contentAlignment = Alignment.Center
//                    ) {
//                        Text("No Data Available")
//                    }
//                }
//            }
//
//
//
//            Spacer(Modifier.height(32.dp))
//        }
//    }
//}


