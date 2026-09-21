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

/*

@Composable
fun Edit_Property_Option(navController: NavHostController, viewModel: Common_H_ViewModel) {


    var index_Clicked = remember { mutableStateOf(-1) }

    val dataList = listOf(
        "Tells us who you are",
        "What type of property you are posting?",
        "Where your property Located?",
        "Fill Property Details",
        "Add Property Features",
        "Property Price",
        "Add photos / videos of property"
    )

    val state = remember { mutableStateOf(0) }

    var retry by remember { mutableStateOf(0) }


    DisposableEffect(Unit , retry) {

        get_Form_Preview_API_CALL { result ->
            state.value = result
            println("STATE ${state.value}")
        }

        onDispose {  }
    }



    Column {
        Row(
            modifier = Modifier
                .padding(top = rememberNotchHeightDp().value , start = 16.dp)
                .fillMaxWidth()
            , verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Backer(
                modifier = Modifier,
                onBackClick = {
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

        when (state.value) {
            0 -> {
                println("qqq000")
                Box(
                    modifier = Modifier
                        .fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            1 -> {
                println("qqq111")
                // println failure

                Box (
                    modifier = Modifier
                        .fillMaxSize()
                    ,contentAlignment = Alignment.Center
                ) {

                    API_Fail_UI(onReTryClick = {
                        retry = retry + 213435
                    })
                }
            }

            2 -> {
                println("qqq222")
                // no data
            }

            3 -> {
                println("qqq333")

                LaunchedEffect(Unit) {
                    val server_Data = constants.PostProperty_ViewModel.get_Preview_Data()

                    server_Data?.let { data ->
                        constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                            data.toSelectedOptionsForm4()
                        }
                    }

                    println("FORM $ DATA -- ${server_Data?.carpet_area} $$ ${server_Data?.area_width} $$ ${server_Data?.area_length} -- ${server_Data?.total_floor}")
                    AppPreferences.save_Post_Id(server_Data?.user_post_id ?: 0)
                    println("POST ID -- ${AppPreferences.get_Post_Id()}")

//               // constants.PostProperty_ViewModel.pincode3 = server_Data
//                constants.PostProperty_ViewModel.country3 =
//                constants.PostProperty_ViewModel.state3 = server_Data?.state ?: ""
//                constants.PostProperty_ViewModel.city3 = server_Data?.city ?: ""


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

                    println(
                        "GIVEN ADDRESS DATA FIELD -- " +
                                "${constants.PostProperty_ViewModel.get_selectedLocality3()} --- " +
                                "${constants.PostProperty_ViewModel.get_latLng3()}---" +
                                "-${constants.PostProperty_ViewModel.get_city3()}--" +
                                "-${constants.PostProperty_ViewModel.get_state3()} ----" +
                                " ${constants.PostProperty_ViewModel.get_country3()}" +
                                " ${constants.PostProperty_ViewModel.get_pincode3()}"
                    )

//                constants.PostProperty_ViewModel.set_pincode3(data_Copy_Map.value?.pincode ?: "")
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

                    val budget_Price = constants.PostProperty_ViewModel.put_budget_Price_PF5(
                        server_Data?.price ?: ""
                    )


                    // val price_Negotiation = constants.PostProperty_ViewModel.price_Negotiation_PF5.collectAsState()


                    println("SERVER DATA -- ${server_Data} ---- cons.${constants.PostProperty_ViewModel.get_Selected_Fields_Form()}")

                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(newWhite)
                        .padding(top = rememberNotchHeightDp().value)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                )
                {


                    dataList.forEachIndexed { index, string ->
                        ListItem(
                            overlineContent = {
                                Text(
                                    "Step ${index + 1}",
                                    color = if (index == 1) newGray.copy(.5f) else Color(0xff666666),
                                    fontSize = constants.textUnit(12),
                                    fontFamily = constants.fontFamily(1)
                                )
                            },
                            headlineContent = {
                                Text(
                                    string,
                                    color = if (index == 1) newGray.copy(.5f) else newBlack,
                                    fontSize = constants.textUnit(14),
                                    fontFamily = constants.fontFamily(2)
                                )
                            },
                            trailingContent = {
                                Image(
                                    painter = painterResource(R.drawable.right_arrow), "",
                                    colorFilter = ColorFilter.tint(if (index == 1) newGray.copy(.5f) else newBlack)
                                )
                            }, colors = ListItemColors(
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
                                    println("INDEX CLICKED ON CLICK - ${index_Clicked.value} -- ${ index}")
                                    if (index != 1) {
                                        index_Clicked.value = index
                                    } else {
                                        index_Clicked.value = -1
                                    }
                                }
                        )

                    }
                }
                println("WHAT I HAVE CLOCKED -- ${index_Clicked.value}")

                AnimatedVisibility(
                    visible = index_Clicked.value != -1,
                    enter = slideInHorizontally(tween(600)) { it },
                    exit = slideOutHorizontally(tween(600)) { it }
                ) {
                    Edit_Property_Screens_Navigator(index_Clicked)
                }
            }
        }

    }
}

@Composable
fun Edit_Property_Screens_Navigator(index: MutableState<Int>) {

    val isLoading = constants.PostProperty_ViewModel.status_PFs.collectAsState()
    val show_Map_view = remember { mutableStateOf(false) }

    val onHover = remember { mutableStateOf(false) }
    val hoveredMedia = remember { mutableStateOf<UploadPropertyMedia?>(null) }


    val network = rememberNetworkStatus()

    var apiError = remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(newWhite)
            .padding(top = rememberNotchHeightDp().value)
        , verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .weight(.5f)
                .padding(horizontal = 16.dp)
            , verticalAlignment = Alignment.CenterVertically
            , horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Backer(
                modifier = Modifier
                , onBackClick = {
                    index.value = -1
                }
            )
        }

        Box (modifier = Modifier
            .weight(8f)
            .padding(horizontal = 16.dp)
        ){
            when (index.value) {
                0 -> PP_First_Form(isLoading)
                1 -> {}
                2 -> PP_Third_Form(isLoading, show_Map_view)
                3 -> PP_Fourth_Form(apiError)
                4 -> PP_Fifth_Form(apiError)
                5 -> PP_Sixth_Form(isLoading)
                6 -> {}
                    //PP_Seventh_Form(onHover, hoveredMedia)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                //.height(100.dp)
                .weight(1.5f)
            , contentAlignment = Alignment.Center
        )
        {
            Static_Bottom(
                modifier = Modifier.fillMaxSize(),
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .fillMaxHeight(.5f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(newBlue)
                            .noRippleClickable{

                                when (index.value) {
                                    0 -> {
                                        //if (constants.PostProperty_ViewModel.get_FirstForm_Selected_PP() != -1) {
                                        if (network.value == NetworkStatus.Online) {
                                            post_Form_1_API_Call { result ->
                                                when (result) {
                                                    0 -> {
                                                        println("FAILURE")
                                                    }

                                                    1 -> {
                                                        println("SUCCESS")
                                                        constants.PostProperty_ViewModel.onNextPPForm()
                                                    }
                                                }
                                            }
                                        } else {
                                            GlobalSnackbar.show("It seems you are offline !! Refresh Again")
                                        }
//                                        } else {
//                                            toast("Selected Options to continue")
//                                        }
                                    }
                                    1 -> {
//
                                    }

                                    2 -> {
                                        val pp3Data =
                                            constants.PostProperty_ViewModel.get_pp3_Data()

                                        val locationMissing = listOf(
                                            pp3Data?.locality,
                                            pp3Data?.pincode,
                                            pp3Data?.city,
                                            pp3Data?.state,
                                            pp3Data?.country
                                        ).any { it.isNullOrEmpty() }

                                        println(
                                            "locationMissing == $locationMissing ---- ${pp3Data?.locality} --${pp3Data?.pincode},\n" +
                                                    "                                                ${pp3Data?.city},\n" +
                                                    "                                                ${pp3Data?.state},\n" +
                                                    "                                                ${pp3Data?.country}"
                                        )

                                        if (network.value == NetworkStatus.Online){
                                        if (!locationMissing) {
                                            post_Form_3_API_Call { result ->
                                                when (result) {
                                                    0 -> {
                                                        println("FAILURE")
                                                    }

                                                    1 -> {
                                                        println("SUCCESS")
                                                        constants.PostProperty_ViewModel.onNextPPForm()
                                                    }
                                                }
                                            }
                                        } else {
                                            toast("Enter the location or pin in on map")
                                        }
                                        } else {
                                            GlobalSnackbar.show("It seems you are offline !! Refresh Again")
                                        }

                                    }

                                    3 -> {
//                                            val formData = constants.PostProperty_ViewModel.selected_Options_Form4.value
//                                            if (formData?.property_Name.isNullOrBlank()) {
//                                                is_Error.value = true
//                                            } else {
//                                                // proceed with submission
//                                            }

                                        val error_State =
                                            constants.PostProperty_ViewModel.check_Errors(
                                                next_Active_Fields
                                            )

                                        if (network.value == NetworkStatus.Online){
                                        if (error_State) {
                                            put_post_Form4_API_CALL { result ->
                                                when (result) {
                                                    0 -> {
                                                        println("FAILURE")
                                                    }

                                                    1 -> {
                                                        println("SUCCESS")
                                                        constants.PostProperty_ViewModel.onNextPPForm()
                                                    }
                                                }
                                            }
                                        } else {
                                            println("ERROR FIELDSSSS")
                                        }
                                        } else {
                                            GlobalSnackbar.show("It seems you are offline !! Refresh Again")
                                        }
                                        /// needed 4th form
                                        //constants.PostProperty_ViewModel.onNextPPForm()
                                    }

                                    4 -> {

                                        if (network.value == NetworkStatus.Online){
                                        put_post_Form4_API_CALL { result ->
                                            when (result) {
                                                0 -> {
                                                    println("FAILURE")
                                                }

                                                1 -> {
                                                    println("SUCCESS")
                                                    constants.PostProperty_ViewModel.onNextPPForm()
                                                }
                                            }
                                        }
                                        } else {
                                            GlobalSnackbar.show("It seems you are offline !! Refresh Again")
                                        }

                                    }

                                    5 -> {
                                        if (network.value == NetworkStatus.Online){
                                        post_Form_5_API_Call { result ->
                                            when (result) {
                                                0 -> {
                                                    println("FAILURE")
                                                }

                                                1 -> {
                                                    println("SUCCESS")
                                                    constants.PostProperty_ViewModel.onNextPPForm()
                                                }
                                            }

                                        }
                                        } else {
                                            GlobalSnackbar.show("It seems you are offline !! Refresh Again")
                                        }
                                    }
                                    6 -> {

                                    }
                                }
                            }
                        , contentAlignment = Alignment.Center
                    ){
                        Text("Save", color = newWhite, fontSize = constants.textUnit(14) , fontFamily = constants.fontFamily(2))
                    }
                }
            )
        }

    }
}

*/

fun logger(identifier : String, value : Any){
    Log.d("RENTAL APP ${identifier}" , "${value}" )
}

@Composable
fun Edit_Property_Option(navController: NavHostController, viewModel: Common_H_ViewModel) {

    // FIX 1: Use remember with mutableIntStateOf for better state management

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
                println("STATE ${state.value}")
            }
        }
        else {
            GlobalSnackbar.show("Check your Internet Connectivity")
        }
        onDispose { }
    }

    // FIX 2: Move AnimatedVisibility outside to prevent layout issues
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
                        //constants.PostProperty_ViewModel.set_Post_Form_Flow(-1)
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
                        //.padding(bottom = 48.dp)
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
                        println("qqq000")
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    1 -> {
                        println("qqq111")
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
                        println("qqq222")
                        // no data
                    }

                    3 -> {
                        println("qqq333")

                        LaunchedEffect(Unit) {
                            //val server_Data2 = constants.PostProperty_ViewModel.get_Preview_Data()
                            val server_Data = constants.PostProperty_ViewModel.get_previewFormData()

                            constants.PostProperty_ViewModel.clear_Media()

                            AppPreferences.save_Post_Id(server_Data?.user_post_id ?: 0)

                            server_Data?.let { data ->
                                constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                    data.toSelectedOptionsForm4()
                                }

                                println("==== FormPreviewRento Received Data ====")

                                println("property_name = ${data.property_name}")
                                println("property_area = ${data.property_area}")
                                println("property_area_unit = ${data.property_area_unit}")
                                println("carpet_area = ${data.carpet_area}")
                                println("carpet_area_unit = ${data.carpet_area_unit}")
                                println("built_up_area = ${data.built_up_area}")
                                println("built_up_area_unit = ${data.built_up_area_unit}")
                                println("super_built_up_area = ${data.super_built_up_area}")
                                println("super_built_up_area_unit = ${data.super_built_up_area_unit}")
                                println("area_length = ${data.area_length}")
                                println("area_length_unit = ${data.area_length_unit}")
                                println("area_width = ${data.area_width}")
                                println("area_width_unit = ${data.area_width_unit}")
                                println("property_facing = ${data.property_facing}")
                                println("total_floor = ${data.total_floor}")
                                println("rent_floor_no = ${data.rent_floor_no}")
                                println("preferred_tenants = ${data.preferred_tenants}")
                                println("availability_from = ${data.availability_from}")
                                println("agreement_type = ${data.agreement_type}")
                                println("food_preferences = ${data.food_preferences}")
                                println("pets_allowed = ${data.pets_allowed}")
                                println("furnishing_status = ${data.furnishing_status}")
                                println("boundary_wall = ${data.boundary_wall}")
                                println("parking_available = ${data.parking_available}")
                                println("amenities = ${data.amenities}")
                                println("property_highlights = ${data.property_highlights}")
                                println("bhk_type = ${data.bhk_type}")
                                println("no_of_bedrooms = ${data.no_of_bedrooms}")
                                println("no_of_bathrooms = ${data.no_of_bathrooms}")
                                println("no_of_balconies = ${data.no_of_balconies}")
                                println("no_of_open_sides = ${data.no_of_open_sides}")
                                println("other_rooms = ${data.other_rooms}")
                                println("facade_height = ${data.facade_height}")
                                println("facade_height_unit = ${data.facade_height_unit}")
                                println("facade_width = ${data.facade_width}")
                                println("facade_width_unit = ${data.facade_width_unit}")
                                println("property_condition = ${data.property_condition}")
                                println("no_of_cabins = ${data.no_of_cabins}")
                                println("no_of_meeting_rooms = ${data.no_of_meeting_rooms}")
                                println("min_of_seats = ${data.min_of_seats}")
                                println("max_of_seats = ${data.max_of_seats}")
                                println("conference_room = ${data.conference_room}")
                                println("no_of_staircases = ${data.no_of_staircases}")
                                println("reception_area = ${data.reception_area}")
                                println("pantry = ${data.pantry}")
                                println("pantry_size = ${data.pantry_size}")
                                println("pantry_size_unit = ${data.pantry_size_unit}")
                                println("central_ac = ${data.central_ac}")
                                println("oxygen_duct = ${data.oxygen_duct}")
                                println("ups = ${data.ups}")
                                println("fire_safety_measures = ${data.fire_safety_measures}")
                                println("lifts = ${data.lifts}")
                                println("noc_certified = ${data.noc_certified}")
                                println("occupancy_certificate = ${data.occupancy_certificate}")
                                println("washroom_details = ${data.washroom_details}")
                                println("does_local_authority = ${data.does_local_authority}")
                                println("suitable_business_type = ${data.suitable_business_type}")
                                println("is_this_property_for_rent_or_lease = ${data.is_this_property_for_rent_or_lease}")
                                println("rent = ${data.rent}")
                                println("rent_negotiable = ${data.rent_negotiable}")
                                println("deposit_amount_month_of_rents = ${data.deposit_amount_month_of_rents}")
                                println("deposit_amount_month_of_rents_type = ${data.deposit_amount_month_of_rents_type}")
                                println("total_deposit = ${data.total_deposit}")
                                println("duration_of_agreement = ${data.duration_of_agreement}")
                                println("duration_of_agreement_type = ${data.duration_of_agreement_type}")
                                println("lock_in_period = ${data.lock_in_period}")
                                println("lock_in_period_type = ${data.lock_in_period_type}")
                                println("notice_period = ${data.notice_period}")
                                println("lease_duration_in_years = ${data.lease_duration_in_years}")
                                println("lease_amount = ${data.lease_amount}")
                                println("lease_negotiable = ${data.lease_negotiable}")

                                println("======================================")

                            }



                            println("FORM $ DATA -- ${server_Data?.user_post_id ?: 0} $$ ${server_Data?.area_width} $$ ${server_Data?.area_length} -- ${server_Data?.total_floor}")
                            AppPreferences.save_Post_Id(server_Data?.user_post_id ?: 0)
                            println("POST ID -- ${AppPreferences.get_Post_Id()}")

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

                            println(
                                "GIVEN ADDRESS DATA FIELD -- " +
                                        "${constants.PostProperty_ViewModel.get_selectedLocality3()} --- " +
                                        "${constants.PostProperty_ViewModel.get_latLng3()}---" +
                                        "-${constants.PostProperty_ViewModel.get_city3()}--" +
                                        "-${constants.PostProperty_ViewModel.get_state3()} ----" +
                                        " ${constants.PostProperty_ViewModel.get_country3()}" +
                                        " ${constants.PostProperty_ViewModel.get_pincode3()}"
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


//                        if (mediaList.value.isNotEmpty()){
//                            println("Media list is Not empty while edit data adding")
//                            constants.PostProperty_ViewModel.add_CopyMedia(mediaList.value)
//                        }
//                        else {
//                            println("Media list is empty while edit data adding")
//                        }


//                        val budget_Price = constants.PostProperty_ViewModel.put_budget_Price_PF5(
//                            server_Data?.price ?: ""
//                        )
                            logger("Request media " , "${postFlow}")
                            if (postFlow == PostFlow.REQUESTMEDIA){
                                logger("Request media " , "Inside Nav Laun -> 6")
                                constants.PostProperty_ViewModel.indexClicked = 6
                            }

                            println("SERVER DATA -- ${server_Data} ---- cons.${constants.PostProperty_ViewModel.get_Selected_Fields_Form()}")
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
                                            println("INDEX CLICKED ON CLICK - $constants.PostProperty_ViewModel.indexClicked = 6 -- $index")
                                            // FIX 3: Proper state update
                                            constants.PostProperty_ViewModel.indexClicked  =
                                                    //if (index != 1)
                                                index

                                            if (index == 6) {
                                                constants.PostProperty_ViewModel.loadCopy.value = false
                                                println("COPY MEDIA DATA -- ${constants.PostProperty_ViewModel.copyMediaList}")
                                                // constants.PostProperty_ViewModel.clear_Media()
                                                constants.PostProperty_ViewModel.loadCopyToOriginal()
                                            }
                                            //else -1
                                        }
                                )
                            }
                        }
                        println("WHAT I HAVE CLICKED -- $constants.PostProperty_ViewModel.indexClicked = 6")
                    }
                }
            }
        }

        // FIX 4: AnimatedVisibility at the top level of Box
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
    index: Int,  // FIX 5: Changed from MutableState to Int
    onBack: () -> Unit,  // FIX 6: Added callback for back navigation
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
                        onBack()  // FIX 7: Use callback instead of direct state mutation
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
                    //7 -> {}
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
                                                                println("SUCCESS")
                                                                toast("Successfully Updated")

                                                                //constants.PostProperty_ViewModel.onNextPPForm()
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
                                                                    println("SUCCESS")
                                                                    toast("Successfully Updated")
                                                                    // constants.PostProperty_ViewModel.onNextPPForm()
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        println("ERROR FIELDSSSS")
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
                                                                println("SUCCESS")
                                                                toast("Successfully Updated")
                                                                //constants.PostProperty_ViewModel.onNextPPForm()
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
                                                                println("SUCCESS")
                                                                toast("Successfully Updated")
                                                                //constants.PostProperty_ViewModel.onNextPPForm()
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
                                                        println("On Complete Edit ")

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
            println("STATE ${state.value}")
        }

        onDispose {  }
    }
    when(state.value) {
        0 -> {
            println("qqq000")
            Box(
                modifier = Modifier
                    .fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        1 -> {
            println("qqq111")
            // println failure
        }

        2 -> {
            println("qqq222")
            // no data
        }

        3 -> {
            println("qqq333")

            LaunchedEffect(Unit) {
                val server_Data = constants.PostProperty_ViewModel.get_Preview_Data()

                server_Data?.let { data ->
                    constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                        data.toSelectedOptionsForm4()
                    }
                }

                AppPreferences.save_Post_Id(server_Data?.user_post_id ?: 0)
                println("POST ID -- ${AppPreferences.get_Post_Id()}")

                constants.PostProperty_ViewModel.first_Form_selected_PP(
                    server_Data?.land_type_id ?: -1
                )
                constants.PostProperty_ViewModel.set_country3(server_Data?.country ?: "")
                constants.PostProperty_ViewModel.set_state3(server_Data?.state ?: "")
                constants.PostProperty_ViewModel.set_city3(server_Data?.city ?: "")
                constants.PostProperty_ViewModel.set__selectedLocality3(server_Data?.locality ?: "")

                println(
                    "GIVEN ADDRESS DATA FIELD -- " +
                            "${constants.PostProperty_ViewModel.get_selectedLocality3()} --- " +
                            "${constants.PostProperty_ViewModel.get_latLng3()}---" +
                            "-${constants.PostProperty_ViewModel.get_city3()}--" +
                            "-${constants.PostProperty_ViewModel.get_state3()} ----" +
                            " ${constants.PostProperty_ViewModel.get_country3()}"
                )

                constants.PostProperty_ViewModel.set_onSelected_ProType((server_Data?.land_type_id ?: 0) - 1 )
                constants.PostProperty_ViewModel.pp_SecondForm_Residential_Select_Option(
                    server_Data?.land_categorie_id ?: 0
                )
                println("Step 2 --- ${(server_Data?.land_type_id ?: 0) - 1 } --- ${server_Data?.land_categorie_id ?: 0}")

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

//                val budget_Price =
//                    constants.PostProperty_ViewModel.put_budget_Price_PF5(server_Data?.price ?: "")

                println("SERVER DATA -- ${server_Data} ---- cons.${constants.PostProperty_ViewModel.get_Selected_Fields_Form()}")

                constants.Profile_ViewModel.set_From_Repost(1)
                // ✅ Navigate after everything is done
                navController.navigate(ProfileScreenFlow.Post_Property_Forms.route)
            }

        }
    }
}