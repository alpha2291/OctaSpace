package com.toletspot.houseforrent.Home_Screen.PostProperty_Module

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.android.gms.maps.model.LatLng
import com.toletspot.houseforrent.API.StartUp_API.get_Form_Preview_API_CALL
import com.toletspot.houseforrent.API.StartUp_API.post_Form_1_API_Call
import com.toletspot.houseforrent.API.StartUp_API.post_Form_5_API_Call
import com.toletspot.houseforrent.API.StartUp_API.put_post_Form4_API_CALL
import com.toletspot.houseforrent.AppPreferences
import com.toletspot.houseforrent.Custom_Assets.API_Fail_UI
import com.toletspot.houseforrent.Custom_Assets.Backer
import com.toletspot.houseforrent.Custom_Assets.Static_Bottom
import com.toletspot.houseforrent.R
import com.toletspot.houseforrent.Custom_Assets.rememberNotchHeightDp
import com.toletspot.houseforrent.Custom_Assets.toast
import com.toletspot.houseforrent.GlobalSnackbar
import com.toletspot.houseforrent.Home_Screen.Common_H_ViewModel
import com.toletspot.houseforrent.Navigation.ProfileScreenFlow
import com.toletspot.houseforrent.NetworkStatus
import com.toletspot.houseforrent.constants
import com.toletspot.houseforrent.forTab
import com.toletspot.houseforrent.noRippleClickable
import com.toletspot.houseforrent.rememberNetworkStatus
import com.toletspot.houseforrent.ui.theme.newBlack
import com.toletspot.houseforrent.ui.theme.newGray
import com.toletspot.houseforrent.ui.theme.newPurpleGradient
import com.toletspot.houseforrent.ui.theme.newWhite

fun logger(identifier : String, value : Any){
    Log.d("RENTAL APP ${identifier}" , "${value}" )
}

@Composable
fun Edit_Property_Option(navController: NavHostController, viewModel: Common_H_ViewModel) {

    val postFlow by constants.PostProperty_ViewModel.postFlow.collectAsState()

    val mediaList = constants.PostProperty_ViewModel.mediaList.collectAsState()

    var network = rememberNetworkStatus()

    val dataList = listOf(
        "Tells us who you are",
        "What type of property you are posting?",
        "Where your property Located?",
        "Provide property details",
        "Additional Details",
        "Price Details",
        "Photos / Videos of Property"
    )

    val state = remember { mutableStateOf(0) }
    var retry by remember { mutableStateOf(0) }

    DisposableEffect(Unit, retry) {
        if (network.value == NetworkStatus.Online) {
            get_Form_Preview_API_CALL { result ->
                state.value = result
            }
        }
        else {
            GlobalSnackbar.show("Check your Internet Connectivity")
        }
        onDispose { }
    }

    Box(modifier = Modifier.fillMaxSize().noRippleClickable{}) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .padding(top = if (forTab()) 16.dp else  rememberNotchHeightDp().value, start = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Backer(
                    modifier = Modifier,
                    onBackClick = {
                        constants.PostProperty_ViewModel.setPostFlow(PostFlow.NONE)
                        constants.PostProperty_ViewModel.clear_Media()
                        constants.PostProperty_ViewModel.clear_CopyMedia()

                        constants.PostProperty_ViewModel.indexClicked  = -1

                        constants.PostProperty_ViewModel.loadCopy.value = false
                        navController.navigateUp()
                    }
                )

                Text(
                    "Edit Property",
                    color = newBlack,
                    fontSize = constants.textUnit(24),
                    fontFamily = constants.fontFamily(0)
                )
            }

            if (network.value == NetworkStatus.Offline){
                Column(
                    modifier = Modifier

                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp)
                    , verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painterResource(R.drawable.nointernetrento), "")
                    Text(constants.activity.getString(R.string.no_Internet) , color = newBlack,fontSize = constants.textUnit(16)
                        , fontFamily = constants.fontFamily(0)
                        , textAlign = TextAlign.Center)

                }
            }
            else {
                when (state.value) {
                    0 -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    1 -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            API_Fail_UI(onReTryClick = {
                                retry = retry + 213435
                            })
                        }
                    }

                    2 -> {

                    }

                    3 -> {

                        LaunchedEffect(Unit) {

                            val server_Data = constants.PostProperty_ViewModel.get_previewFormData()

                            constants.PostProperty_ViewModel.clear_Media()

                            AppPreferences.save_Post_Id(server_Data?.user_post_id ?: 0)

                            server_Data?.let { data ->
                                constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                    data.toSelectedOptionsForm4()
                                }

                            }

                            AppPreferences.save_Post_Id(server_Data?.user_post_id ?: 0)

                            constants.PostProperty_ViewModel.first_Form_selected_PP(
                                server_Data?.land_type_id ?: -1
                            )
                            constants.PostProperty_ViewModel.set_pincode3(server_Data?.pincode ?: "")
                            constants.PostProperty_ViewModel.set_country3(server_Data?.country ?: "")
                            constants.PostProperty_ViewModel.set_state3(server_Data?.state ?: "")
                            constants.PostProperty_ViewModel.set_city3(server_Data?.city ?: "")
                            constants.PostProperty_ViewModel.set__selectedLocality3(
                                server_Data?.address ?: ""
                            )


                            val latLng = LatLng(
                                server_Data?.latitude?.toDouble() ?: 0.0,
                                server_Data?.longitude?.toDouble() ?: 0.0
                            )

                            constants.PostProperty_ViewModel.set_latLng3(latLng)
                            constants.PostProperty_ViewModel.select_User_Type_1PF(
                                server_Data?.land_type_id ?: 0
                            )
                            constants.PostProperty_ViewModel.select_Land_Cat_Id(
                                server_Data?.land_categorie_id ?: 0
                            )

                            constants.PostProperty_ViewModel.set_onSelected_ProType(
                                (server_Data?.land_type_id ?: 0)
                            )
                            constants.PostProperty_ViewModel.pp_SecondForm_Residential_Select_Option(
                                server_Data?.land_categorie_id ?: 0
                            )

                            constants.PostProperty_ViewModel.select_Land_Cat_Id(
                                server_Data?.land_categorie_id ?: 0
                            )

                            constants.PostProperty_ViewModel.LandSubType_Selected_Click(
                                server_Data?.land_categorie_id ?: 0
                            )

                            constants.PostProperty_ViewModel.LandSubType_Selected_Click((server_Data?.land_type_id ?: 0))

                            constants.PostProperty_ViewModel.loadDraftFromServer(
                                imageUrls = server_Data?.images ?: emptyList(),
                                videoUrls = server_Data?.video ?: emptyList(),
                                coverUrl = server_Data?.thumbnail ?: ""
                            )

                            logger("Request media " , "${postFlow}")
                            if (postFlow == PostFlow.REQUESTMEDIA){
                                logger("Request media " , "Inside Nav Laun -> 6")
                                constants.PostProperty_ViewModel.indexClicked = 6
                            }

                        }

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(newWhite)
                                .padding(top = if (forTab()) 16.dp else  rememberNotchHeightDp().value)
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            dataList.forEachIndexed { index, string ->
                                ListItem(
                                    overlineContent = {
                                        if (index == 1 || index == 2){
                                            Row(
                                                modifier = Modifier
                                                , verticalAlignment = Alignment.CenterVertically
                                                , horizontalArrangement = Arrangement.Center
                                            ) {
                                                Text(
                                                    "Step ${index + 1}",
                                                    color = Color(0xff666666),
                                                    fontSize = constants.textUnit(12),
                                                    fontFamily = constants.fontFamily(1)
                                                )

                                                constants.spacer(2)

                                                Image(painter = painterResource(R.drawable.uneditableformsicon) , "",
                                                    modifier = Modifier.size(12.dp))
                                            }
                                        }
                                        else {
                                            Text(
                                                "Step ${index + 1}",
                                                color = Color(0xff666666),
                                                fontSize = constants.textUnit(12),
                                                fontFamily = constants.fontFamily(1)
                                            )
                                        }
                                    },
                                    headlineContent = {
                                        Text(
                                            string,
                                            color =  newBlack,
                                            fontSize = constants.textUnit(14),
                                            fontFamily = constants.fontFamily(2)
                                        )
                                    },
                                    trailingContent = {
                                        Image(
                                            painter = painterResource(R.drawable.right_arrow), "",
                                            colorFilter = ColorFilter.tint(if (index == 1) newGray.copy(.5f) else newBlack)
                                        )
                                    },
                                    colors = ListItemColors(
                                        containerColor = Color.White,
                                        headlineColor = Color.Black,
                                        leadingIconColor = Color.DarkGray,
                                        overlineColor = Color.Gray,
                                        supportingTextColor = Color.Gray,
                                        trailingIconColor = Color.LightGray,
                                        disabledHeadlineColor = Color.Gray.copy(alpha = 0.5f),
                                        disabledLeadingIconColor = Color.Gray.copy(alpha = 0.5f),
                                        disabledTrailingIconColor = Color.Gray.copy(alpha = 0.5f)
                                    ),
                                    modifier = Modifier
                                        .border(1.dp, newGray, RoundedCornerShape(8.dp))
                                        .noRippleClickable{

                                            constants.PostProperty_ViewModel.indexClicked  =

                                                index

                                            if (index == 6) {
                                                constants.PostProperty_ViewModel.loadCopy.value = false

                                                constants.PostProperty_ViewModel.loadCopyToOriginal()
                                            }

                                        }
                                )
                            }
                        }
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = constants.PostProperty_ViewModel.indexClicked != -1 && state.value == 3,
            enter = slideInHorizontally(tween(600)) { it },
            exit = slideOutHorizontally(tween(600)) { it },
            modifier = Modifier.fillMaxSize()
        ) {
            Edit_Property_Screens_Navigator(
                index = constants.PostProperty_ViewModel.indexClicked ,
                onBack = { constants.PostProperty_ViewModel.indexClicked  = -1 }
                ,navController
            )
        }
    }
}

@Composable
fun Edit_Property_Screens_Navigator(
    index: Int,
    onBack: () -> Unit,
    navController: NavHostController
) {

    val isLoading = constants.PostProperty_ViewModel.status_PFs.collectAsState()
    val show_Map_view = remember { mutableStateOf(false) }
    val network = rememberNetworkStatus()
    var apiError = remember { mutableStateOf(false) }

    var onHover = remember { mutableStateOf(false) }

    var scope = rememberCoroutineScope()

    Box() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(newWhite)
                .padding(top = if (forTab()) 16.dp else rememberNotchHeightDp().value),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(.5f)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Backer(
                    modifier = Modifier,
                    onBackClick = {
                        constants.PostProperty_ViewModel.loadCopy.value = false
                        onBack()
                    }
                )
            }

            Box(
                modifier = Modifier
                    .weight(8f)
                    .padding(horizontal = 16.dp)
            ) {
                when (index) {
                    0 -> PP_First_Form(isLoading)
                    1 -> {
                        PP_Second_Form(isLoading)
                    }

                    2 -> PP_Third_Form(isLoading, show_Map_view)
                    3 -> PP_Fourth_Form(apiError)
                    4 -> PP_Fifth_Form()
                    5 -> Form6_Common()
                    6 -> {

                        PP_Seventh_Form(onHover)
                    }

                }
            }

            if (index == 1 || index == 2) {
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.5f),
                    contentAlignment = Alignment.Center
                ) {
                    Static_Bottom(
                        modifier = Modifier.fillMaxSize(),
                        content = {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(.9f)
                                    .fillMaxHeight(.5f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Brush.verticalGradient(newPurpleGradient))
                                    .noRippleClickable {
                                        when (index) {
                                            0 -> {
                                                if (network.value == NetworkStatus.Online) {
                                                    post_Form_1_API_Call { result ->
                                                        when (result) {
                                                            0 -> println("FAILURE")
                                                            1 -> {
                                                                toast("Successfully Updated")

                                                            }
                                                        }
                                                    }
                                                } else {
                                                    GlobalSnackbar.show("It seems you are offline !! Refresh Again")
                                                }
                                            }

                                            1 -> {}

                                            2 -> {}

                                            3 -> {
                                                val error_State =
                                                    constants.PostProperty_ViewModel.check_Errors4(
                                                        next_Active_Fields
                                                    )

                                                if (network.value == NetworkStatus.Online) {
                                                    if (error_State) {
                                                        put_post_Form4_API_CALL { result ->
                                                            when (result) {
                                                                0 -> println("FAILURE")
                                                                1 -> {
                                                                    toast("Successfully Updated")

                                                                }
                                                            }
                                                        }
                                                    } else {
                                                    }
                                                } else {
                                                    GlobalSnackbar.show("It seems you are offline !! Refresh Again")
                                                }
                                            }

                                            4 -> {
                                                if (network.value == NetworkStatus.Online) {
                                                    put_post_Form4_API_CALL { result ->
                                                        when (result) {
                                                            0 -> println("FAILURE")
                                                            1 -> {
                                                                toast("Successfully Updated")

                                                            }
                                                        }
                                                    }
                                                } else {
                                                    GlobalSnackbar.show("It seems you are offline !! Refresh Again")
                                                }
                                            }

                                            5 -> {
                                                if (network.value == NetworkStatus.Online) {
                                                    post_Form_5_API_Call { result ->
                                                        when (result) {
                                                            0 -> println("FAILURE")
                                                            1 -> {
                                                                toast("Successfully Updated")

                                                            }
                                                        }
                                                    }
                                                } else {
                                                    GlobalSnackbar.show("It seems you are offline !! Refresh Again")
                                                }
                                            }

                                            6 -> {
                                                handleForm6Upload(
                                                    navController = navController,
                                                    scope = scope,
                                                    onComplete = {

                                                    },
                                                    isDraft = false
                                                )
                                            }
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "Save Changes",
                                    color = newWhite,
                                    fontSize = constants.textUnit(14),
                                    fontFamily = constants.fontFamily(2)
                                )
                            }
                        }
                    )
                }
            }
        }

        if (onHover.value) {
            val mediaItems by constants.PostProperty_ViewModel.mediaList.collectAsState()

            UserMediaPreview(mediaList = mediaItems, onClose = {
                onHover.value = false
            })
        }
    }
}

@Composable
fun Repost_Property(navController: NavHostController, viewModel: Common_H_ViewModel){

    val state = remember { mutableStateOf(0) }

    DisposableEffect(Unit) {

        get_Form_Preview_API_CALL { result ->
            state.value = result
        }

        onDispose {  }
    }
    when(state.value) {
        0 -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        1 -> {

        }

        2 -> {

        }

        3 -> {

            LaunchedEffect(Unit) {
                val server_Data = constants.PostProperty_ViewModel.get_Preview_Data()

                server_Data?.let { data ->
                    constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                        data.toSelectedOptionsForm4()
                    }
                }

                AppPreferences.save_Post_Id(server_Data?.user_post_id ?: 0)

                constants.PostProperty_ViewModel.first_Form_selected_PP(
                    server_Data?.land_type_id ?: -1
                )
                constants.PostProperty_ViewModel.set_country3(server_Data?.country ?: "")
                constants.PostProperty_ViewModel.set_state3(server_Data?.state ?: "")
                constants.PostProperty_ViewModel.set_city3(server_Data?.city ?: "")
                constants.PostProperty_ViewModel.set__selectedLocality3(server_Data?.locality ?: "")


                constants.PostProperty_ViewModel.set_onSelected_ProType((server_Data?.land_type_id ?: 0) - 1 )
                constants.PostProperty_ViewModel.pp_SecondForm_Residential_Select_Option(
                    server_Data?.land_categorie_id ?: 0
                )

                val latLng = LatLng(
                    server_Data?.latitude?.toDouble() ?: 0.0,
                    server_Data?.longitude?.toDouble() ?: 0.0
                )
                constants.PostProperty_ViewModel.set_latLng3(latLng)

                constants.PostProperty_ViewModel.select_User_Type_1PF(
                    server_Data?.land_type_id ?: 0
                )
                constants.PostProperty_ViewModel.select_Land_Cat_Id(
                    server_Data?.land_categorie_id ?: 0
                )

                constants.Profile_ViewModel.set_From_Repost(1)

                navController.navigate(ProfileScreenFlow.Post_Property_Forms.route)
            }

        }
    }
}
