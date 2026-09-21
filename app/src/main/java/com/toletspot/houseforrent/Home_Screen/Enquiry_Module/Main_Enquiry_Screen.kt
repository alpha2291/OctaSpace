package com.toletspot.houseforrent.Home_Screen.Enquiry_Module

import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.google.android.libraries.places.api.Places
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.ChatMainList.Chat_Main_List_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_My_Leads_Data
import com.toletspot.houseforrent.API.API_Result_Handling
import com.toletspot.houseforrent.AppPreferences
import com.toletspot.houseforrent.Chat.FirebaseRepository
import com.toletspot.houseforrent.Custom_Assets.API_Fail_UI
import com.toletspot.houseforrent.Custom_Assets.CalendarExample
import com.toletspot.houseforrent.Custom_Assets.ClickHelper
import com.toletspot.houseforrent.Custom_Assets.Common_BTM_Sheet
import com.toletspot.houseforrent.Custom_Assets.Common_Popup
import com.toletspot.houseforrent.Custom_Assets.Enquiry_Message_Structure
import com.toletspot.houseforrent.Custom_Assets.Enquiry_Message_Structure2
import com.toletspot.houseforrent.Custom_Assets.LottiAnimation
import com.toletspot.houseforrent.Custom_Assets.Static_Bottom
import com.toletspot.houseforrent.Custom_Assets.convertDateToTimestamp
import com.toletspot.houseforrent.Custom_Assets.rememberNotchHeightDp
import com.toletspot.houseforrent.Home_Screen.Common_H_ViewModel
import com.toletspot.houseforrent.Home_Screen.ProfileModule.ClickGuard
import com.toletspot.houseforrent.Navigation.EnquiriesFlow
import com.toletspot.houseforrent.NetworkStatus
import com.toletspot.houseforrent.R
import com.toletspot.houseforrent.Start_Up.CommonText
import com.toletspot.houseforrent.UI_DataClass.Custom_BottomSheetState
import com.toletspot.houseforrent.UI_DataClass.Custom_PopUpState
import com.toletspot.houseforrent.UI_DataClass.Selected_Dates_Calender
import com.toletspot.houseforrent.constants
import com.toletspot.houseforrent.forTab
import com.toletspot.houseforrent.noRippleClickable
import com.toletspot.houseforrent.rememberNetworkStatus
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
import java.time.LocalDate
import kotlin.text.ifEmpty

enum class EnquiryFlow {
    LEADS, SELF, CHAT
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun Enquiry_Home_Screen(navController: NavHostController, viewModel: Common_H_ViewModel) {

    val context = LocalContext.current
    val notchPadding = rememberNotchHeightDp()

    var selected_Etype = constants.Enquiry_ViewModel.selected_Etype.collectAsStateWithLifecycle()

    val reels_Show = remember { mutableStateOf(false) }

    val disable_Click_ONPOPUP = if ( constants.open_Popup.type == "self_enquiry_delete" && constants.open_Popup.isVisible) true else false

    var reload_api_onSORT = remember { mutableStateOf(0) }
    var reload_ONSort_Filter = remember { mutableStateOf(0) }

    val selected_Date_Range = constants.Enquiry_ViewModel.selected_Date_Range.collectAsStateWithLifecycle()

    val selected_Date_Range_List = constants.Enquiry_ViewModel.Selected_Dates_List

    var selfEnquiriesSort = remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    var myleadsSearchtext = remember { mutableStateOf("") }
    var selfenquirySearchtext = remember { mutableStateOf("") }

    Column (
        modifier = Modifier
            .fillMaxSize()
            . background(newWhite)
            .padding(horizontal = 16.dp)
        , verticalArrangement = Arrangement.Top
        , horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.padding(top = if (forTab()) 16.dp else notchPadding.value))

        Text(context.getString(R.string.enquiry_Heading)
            , color = Color.Black
            , fontSize = constants.textUnit(24)
        )

        Spacer(modifier = Modifier.padding(top = 16.dp))

        Row (modifier = Modifier
            .fillMaxWidth()

            .background(newWhite)
            .clickable(disable_Click_ONPOPUP){}

        )
        {
            repeat(constants.Enquiry_ViewModel.Enquiry_Types_List.size){
                index ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .height(if (forTab())42.dp else 33.dp)

                        .weight(1f)
                        .clip(RoundedCornerShape(4.dp))
                        .noRippleClickable{
                            ClickHelper.getInstance().clickOnce {
                                constants.Enquiry_ViewModel.Selected_Dates_List.clear()
                                constants.Enquiry_ViewModel.enquiries_View(index)

                                when(index) {
                                    0 -> {
                                        constants.Enquiry_ViewModel.clear_MyleadsEnquiry()
                                        constants.Enquiry_ViewModel.clearChatMainList()
                                        constants.Enquiry_ViewModel.clear_Selected_DateRange()

                                        constants.Enquiry_ViewModel.clear_SelfEnquiry()

                                        constants.Enquiry_ViewModel.leads_selected_Filter = 4
                                        constants.Enquiry_ViewModel.leads_selected_Sort = 1
                                        constants.Enquiry_ViewModel.set_EnquiryFlow(EnquiryFlow.LEADS)

                                        constants.API_Vm.isLoading_Leads = true
                                        constants.API_Vm.totalPages_Leads = 1
                                    }
                                    1 -> {
                                        constants.Enquiry_ViewModel.clear_MyleadsEnquiry()
                                        constants.Enquiry_ViewModel.clear_Selected_DateRange()
                                        constants.Enquiry_ViewModel.clearChatMainList()

                                        constants.Enquiry_ViewModel.Self_Enquiry_selected_Sort = 1
                                        constants.Enquiry_ViewModel.set_EnquiryFlow(EnquiryFlow.SELF)

                                        constants.Enquiry_ViewModel.clear_SelfEnquiry()
                                        constants.API_Vm.isLoading_Self_Enquiry = true
                                        constants.API_Vm.totalPages_Self_Enquiry = 1
                                    }
                                    2 -> {
                                        constants.Enquiry_ViewModel.clearAllNotification()

                                        constants.Enquiry_ViewModel.clear_Selected_DateRange()

                                        constants.Enquiry_ViewModel.clearChatMainList()

                                        constants.Enquiry_ViewModel.set_EnquiryFlow(EnquiryFlow.CHAT)

                                        constants.Enquiry_ViewModel.clear_MyleadsEnquiry()
                                        constants.Enquiry_ViewModel.clear_SelfEnquiry()

                                        constants.API_Vm.isLoading_CML = true
                                        constants.API_Vm.totalPages_CML = 1
                                        constants.API_Vm.errorMessage_CML = null
                                    }
                                }

                            }
                        }
                        .background(if (selected_Etype.value == index) newBlack else newWhite)
                    , contentAlignment = Alignment.Center
                ){
                    Text(constants.Enquiry_ViewModel.Enquiry_Types_List[index]
                        , color = if (selected_Etype.value == index) newWhite else newDarkGray
                        , fontSize = constants.textUnit(14)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(top = 16.dp))

        when (selected_Etype.value){
            0 -> {

                    Column {
                        CommonText(
                            "You’re viewing all the leads interested in your \n listings"
                            ,Color(0xff575757)
                            ,14
                            ,3
                        )

                        constants.spacer(4)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        )
                        {
                            Box(
                                modifier = Modifier
                                .weight(8.5f)
                                .height(56.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White)
                                .border(1.dp , newGray ,RoundedCornerShape(8.dp))
                            ) {
                                TextField(
                                    value = myleadsSearchtext.value,
                                    onValueChange = {
                                        myleadsSearchtext.value = it
                                        if (it.length >= 3){
                                            constants.API_Vm.isLoading_Leads = true
                                            constants.API_Vm.totalPages_Leads = 1
                                            reload_ONSort_Filter.value = reload_ONSort_Filter.value + 2345321

                                        }
                                        if (it.length == 0){
                                            constants.API_Vm.isLoading_Leads = true
                                            constants.API_Vm.totalPages_Leads = 1
                                            reload_ONSort_Filter.value = reload_ONSort_Filter.value + 12345

                                        }
                                    },
                                    leadingIcon = {

                                        Icon(
                                            painter = painterResource(R.drawable.searchnotrento),
                                            contentDescription = "Clear",
                                            modifier = Modifier
                                                .size(18.dp)
                                        )

                                    },
                                    placeholder = {
                                        Text("search by name, location.."
                                            ,  color = newBlack,
                                            fontSize = constants.textUnit(14),
                                            fontFamily = constants.fontFamily(2),
                                        )
                                    },
                                    trailingIcon = {
                                        if (myleadsSearchtext.value.isNotEmpty()) {
                                            Image(
                                                painter = painterResource(R.drawable.search_text_clear),
                                                contentDescription = "Clear",
                                                modifier = Modifier
                                                    .size(18.dp)
                                                    .noRippleClickable {
                                                        if (myleadsSearchtext.value.isNotEmpty()) {
                                                            myleadsSearchtext.value = ""
                                                            constants.API_Vm.isLoading_Leads = true
                                                            constants.API_Vm.totalPages_Leads = 1
                                                            reload_ONSort_Filter.value =
                                                                reload_ONSort_Filter.value + 453874378

                                                        }
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

                                            focusManager.clearFocus()
                                            keyboardController?.hide()
                                        }
                                    ),
                                    modifier = Modifier

                                        .fillMaxWidth()
                                )
                            }

                            constants.spacer(6)

                            var iconstate =
                                if (constants.Enquiry_ViewModel.leads_selected_Filter != 4 ||
                                    constants.Enquiry_ViewModel.leads_selected_Sort != 1
                                ) {
                                    R.drawable.filterappliedrento
                                } else {
                                    R.drawable.filter
                                }

                            SubcomposeAsyncImage(
                                model = iconstate,
                                "",
                                modifier = Modifier
                                    .height(56.dp)
                                    .weight(2f)
                                    .noRippleClickable {
                                        ClickHelper.getInstance().clickOnce {

                                            constants.open_Btm_Sheet =
                                                Custom_BottomSheetState(
                                                    type = "leads_filter",
                                                    isVisible = true
                                                )

                                        }
                                    }
                            )
                        }
                    }
            }

            1 -> {

                Column {
                    CommonText(
                        "These are the properties you’ve enquired about."
                        ,Color(0xff575757)
                        ,14
                        ,3
                    )

                    constants.spacer(4)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        Box(
                            modifier = Modifier
                                .weight(8.5f)
                                .height(56.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White)
                                .border(1.dp , newGray ,RoundedCornerShape(8.dp))
                        ) {
                            TextField(
                                value = selfenquirySearchtext.value,
                                onValueChange = {
                                    selfenquirySearchtext.value = it
                                    if (it.length >= 3){
                                        constants.API_Vm.isLoading_Self_Enquiry = true
                                        constants.API_Vm.totalPages_Self_Enquiry = 1
                                        reload_api_onSORT.value = reload_api_onSORT.value + 12345
                                    }
                                    if (it.length == 0){
                                        constants.API_Vm.isLoading_Self_Enquiry = true
                                        constants.API_Vm.totalPages_Self_Enquiry = 1
                                        reload_api_onSORT.value = reload_api_onSORT.value + 98765
                                    }
                                },
                                leadingIcon = {

                                    Icon(
                                        painter = painterResource(R.drawable.searchnotrento),
                                        contentDescription = "Clear",
                                        modifier = Modifier
                                            .size(18.dp)
                                    )

                                },
                                placeholder = {
                                    Text("search by name, location.."
                                        ,  color = newBlack,
                                        fontSize = constants.textUnit(14),
                                        fontFamily = constants.fontFamily(2),
                                    )
                                },
                                trailingIcon = {
                                     if (selfenquirySearchtext.value.isNotEmpty()) {
                                    Image(
                                        painter = painterResource(R.drawable.search_text_clear),
                                        contentDescription = "Clear",
                                        modifier = Modifier
                                            .size(18.dp)
                                            .noRippleClickable {
                                                if (selfenquirySearchtext.value.isNotEmpty()) {
                                                    selfenquirySearchtext.value = ""
                                                    constants.API_Vm.isLoading_Self_Enquiry = true
                                                    constants.API_Vm.totalPages_Self_Enquiry = 1
                                                    reload_api_onSORT.value =
                                                        reload_api_onSORT.value + 98765
                                                }
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

                                        focusManager.clearFocus()
                                        keyboardController?.hide()
                                    }
                                ),
                                modifier = Modifier

                                    .fillMaxWidth()
                            )
                        }

                        constants.spacer(6)

                        var iconstate =
                            if (

                                 constants.Enquiry_ViewModel.Self_Enquiry_selected_Sort != 1
                            ) {
                                R.drawable.rentoselfenqiuryfilterapplied

                            } else {
                                R.drawable.sortrento
                            }

                        SubcomposeAsyncImage(
                            model = iconstate,
                            "",
                            modifier = Modifier
                                .height(56.dp)
                                .weight(2f)
                                .noRippleClickable {
                                    ClickHelper.getInstance().clickOnce {

                                        selfEnquiriesSort.value = true

                                    }
                                }
                        )
                    }
                }
            }

            2 -> {

            }

        }

        Spacer(modifier = Modifier.padding(top = 16.dp))

        AnimatedContent(
            targetState = selected_Etype.value
        )
        {
            targetState ->

            when(targetState){
                0 -> {
                    MyLeads_Content(reels_Show , navController , viewModel , myleadsSearchtext , reload_ONSort_Filter , keyboardController , focusManager)
                }
                1 -> {
                    SelfEnquiry_Content(reels_Show , navController , reload_api_onSORT ,viewModel , selfEnquiriesSort , selfenquirySearchtext , keyboardController , focusManager)
                }
                2 -> {

                    Message_Content(
                        onOpenBuyersList = { propertyId, sellerId ->
                            viewModel.toggleshowBABars(false)
                            navController.navigate(
                                EnquiriesFlow.Msg_UserList.route + "/$propertyId/$sellerId"
                            )
                        }
                    )

                }
            }

        }

    }

    Common_Popup(
        visible = constants.open_Popup.type == "self_enquiry_delete" && constants.open_Popup.isVisible,
        modifier = Modifier.background(Color(0xffFCEDEC)),
        content = {

            constants.spacer(2)
            constants.spacer(4)
            Image(painter = painterResource(R.drawable.deletepopupicon) , "",
                modifier = Modifier.size(64.dp))

            constants.spacer(4)
            constants.spacer(2)

            Text("Are you sure you want to delete this enquiry?"
                , color = newBlack
                , fontSize = constants.textUnit(16),
                fontFamily = constants.fontFamily(1),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
               , lineHeight = 24.sp

            )

            constants.spacer(8)

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                , verticalAlignment = Alignment.CenterVertically
                , horizontalArrangement = Arrangement.SpaceEvenly
            )
            {
                Box(
                    modifier = Modifier
                        .width(121.dp)
                        .height(if (forTab()) 46.dp else 36.dp )
                        .clip(RoundedCornerShape(4.dp))
                        .background(newLightGray)
                        .noRippleClickable{
                            ClickHelper.getInstance().clickOnce {
                                constants.open_Popup = Custom_PopUpState(type = "", false)
                                viewModel.toggleshowBABars(true)
                            }
                        }
                    , contentAlignment = Alignment.Center
                ){
                    Text("Cancel" , color = newBlack , fontSize = constants.textUnit(14))
                }

                Box(
                    modifier = Modifier
                        .width(121.dp)
                        .height(if (forTab()) 46.dp else 36.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .noRippleClickable{
                            ClickHelper.getInstance().clickOnce {
                                if (ClickGuard.canClick()) {
                                    constants.Common_H_ViewModel.toggleshowBABars(true)
                                    val enquiry = constants.Enquiry_ViewModel.selectedEnquiry

                                    if (enquiry != null) {
                                        constants.Common_H_ViewModel.toggleshowBABars(true)
                                        constants.API_Vm.put_Enquiry_Decline_Undodecline(
                                            enquire_id = enquiry?.enquiry_details?.enquire_id ?: 0,
                                            status = "1",
                                        )
                                        { aPI_Result_Handling ->
                                            when (aPI_Result_Handling) {
                                                is API_Result_Handling.Loading -> {

                                                }

                                                is API_Result_Handling.Deactivated -> {

                                                }

                                                is API_Result_Handling.Error -> {

                                                }

                                                is API_Result_Handling.Success -> {

                                                    viewModel.toggleshowBABars(true)
                                                    constants.Enquiry_ViewModel.remove_Enquiry(
                                                        enquiry?.enquiry_details?.enquire_id ?: 0
                                                    )
                                                    constants.Enquiry_ViewModel.deleteSelfEnquiryById(
                                                        enquiry?.enquiry_details?.enquire_id ?: 0
                                                    )
                                                    constants.open_Popup =
                                                        Custom_PopUpState(type = "", false)

                                                }

                                                is API_Result_Handling.NoData -> {

                                                }
                                            }
                                        }
                                    }
                                }
                                }
                        }
                        .background(Color(0xffE54C3C)),
                    contentAlignment = Alignment.Center
                ){
                    Text("Delete" , color = newWhite , fontSize = constants.textUnit(14))
                }
            }

            constants.spacer(2)
            Spacer(modifier = Modifier.padding(8.dp))
        },
        image = constants.Enquiry_ViewModel.selectedEnquiry?.post_user?.profile_image ?: "".ifEmpty { constants.Enquiry_ViewModel.selectedEnquiry?.enquiry_details?.enquiry_by_username.takeIf { it?.isNotEmpty() == true }?.take(1)?.uppercase() ?: "" },
        userName = constants.Enquiry_ViewModel.selectedEnquiry?.post_user?.username ?: ""
        , icon = 0
    )

    val deleteMyLeadsEnquiryPopup = constants.Enquiry_ViewModel.deleteMyLeadsEnquiryPopup.collectAsState()

    Common_Popup(
        visible =  deleteMyLeadsEnquiryPopup.value.second,
        modifier = Modifier
            .background(Color(0xffF7F0DC))
    )
    {
        Column (
            modifier = Modifier
                .padding(horizontal = 24.dp)
            , verticalArrangement = Arrangement.spacedBy(12.dp)
            , horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            constants.spacer(2)

            Image(painter = painterResource(R.drawable.deletepopupicon), "",
                modifier = Modifier.size(56.dp))

            constants.spacer(2)

            Text(
                text = "Are you sure you want to delete enquiry?",
                color = newBlack,
                fontSize = constants.textUnit(18),
                fontFamily = constants.fontFamily(1)
                , textAlign = TextAlign.Center
                , modifier = Modifier.padding(horizontal = if (forTab()) 46.dp else 36.dp)

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
                        .height(if (forTab()) 56.dp else 46.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xffE8E8E8))
                        .noRippleClickable {
                            constants.Enquiry_ViewModel.deleteLeads(0 , false)
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
                        .clip(RoundedCornerShape(8.dp))
                        .background(Brush.verticalGradient(newRedGradienBg))
                        .border(1.dp , Brush.linearGradient(newRedGradienBorder) , RoundedCornerShape(8.dp))
                        .noRippleClickable {
                            constants.API_Vm.put_Enquiry_Decline_Undodecline(
                                enquire_id = deleteMyLeadsEnquiryPopup.value.first
                                    ?: 0,
                                status = "1",
                            )
                            {
                                    aPI_Result_Handling ->
                                when (aPI_Result_Handling) {
                                    is API_Result_Handling.Loading -> {

                                    }

                                    is API_Result_Handling.Deactivated -> {

                                    }

                                    is API_Result_Handling.Error -> {

                                    }

                                    is API_Result_Handling.Success -> {

                                        constants.Enquiry_ViewModel.remove_Enquiry(
                                            deleteMyLeadsEnquiryPopup.value.first
                                                ?: 0
                                        )
                                        constants.Enquiry_ViewModel.deleteLeads(0 , false)

                                    }

                                    is API_Result_Handling.NoData -> {

                                    }
                                }
                            }
                        }
                    , contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Delete",
                        color = Color.White,
                        fontSize = constants.textUnit(16),
                        fontFamily = constants.fontFamily(0)
                    )
                }
            }

            constants.spacer(2)
        }
    }

    BackHandler {
        ClickHelper.getInstance().clickOnce {
            if (ClickGuard.canClick()) {
                if(constants.open_Popup.type == "self_enquiry_delete" && constants.open_Popup.isVisible){
                    constants.open_Popup = Custom_PopUpState(type = "", false)
                    constants.Common_H_ViewModel.toggleshowBABars(true)
                }
                else {
                        viewModel.selectedBABTab(0)
                        viewModel.toggleshowBABars(true)
                        viewModel.toggleshowTABars(true)
                }
            }
        }

    }
}

@Composable
fun MyLeads_Content(
    reels_Show: MutableState<Boolean>,
    navController: NavHostController,
    viewModel: Common_H_ViewModel,
    myleadsSearchtext: MutableState<String>,
    reload_ONSort_Filter: MutableState<Int>,
    keyboardController: SoftwareKeyboardController?,
    focusManager1: FocusManager
) {

    val isLoading = constants.API_Vm.isLoading_Leads
    val totalPages = constants.API_Vm.totalPages_Leads

    val errorMessage = constants.API_Vm.errorMessage_FF
    val currentPage = constants.API_Vm.currentPage_Leads
    val listState = rememberLazyListState()

    val network = rememberNetworkStatus()

    val context = LocalContext.current

    val placesClient = Places.createClient(context)

    val scope = rememberCoroutineScope()

    val lead_Data = constants.Enquiry_ViewModel.my_Leads.collectAsStateWithLifecycle()

    val selected_Date_Range = constants.Enquiry_ViewModel.selected_Date_Range.collectAsStateWithLifecycle()

    var retry by remember { mutableStateOf(0) }
    listState.isScrollInProgress

    LaunchedEffect(reload_ONSort_Filter.value, retry) {

        constants.API_Vm.load_My_Leads(
            user_id = AppPreferences.getUserId(),
            page = 1,
            search_type = constants.Enquiry_ViewModel.leads_selected_Filter,
            filter_type = constants.Enquiry_ViewModel.leads_selected_Sort,
            customer_dates = convertDateToTimestamp(LocalDate.now().toString()),
            customer_dates_start = selected_Date_Range.value?.timestamp_Start ?: "",
            customer_dates_end = selected_Date_Range.value?.timeStamp_End ?: "",
            search_text = myleadsSearchtext.value,
        )

        delay(100)
        listState.animateScrollToItem(0)
    }

    LaunchedEffect(listState, currentPage, isLoading, totalPages) {
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

                    constants.API_Vm.load_My_Leads(
                        user_id = AppPreferences.getUserId(),

                        page = currentPage + 1,
                        search_type = constants.Enquiry_ViewModel.leads_selected_Filter,

                        filter_type =  constants.Enquiry_ViewModel.leads_selected_Sort,
                        customer_dates =  convertDateToTimestamp(LocalDate.now().toString()) ,
                        customer_dates_start = selected_Date_Range.value?.timestamp_Start ?: "" ,
                        customer_dates_end = selected_Date_Range.value?.timeStamp_End ?: "",
                        search_text = myleadsSearchtext.value
                    )

                }
            }

    }

    var selectedEnquiry by remember { mutableStateOf<Get_My_Leads_Data?>(null) }

    when {

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
                Text(constants.activity.getString(R.string.no_Internet) , color = newBlack,fontSize = constants.textUnit(16)
                    , fontFamily = constants.fontFamily(0)
                    , textAlign = TextAlign.Center)
            }
        }

        isLoading && currentPage == 1

            -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                , verticalArrangement = Arrangement.Center
                , horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottiAnimation(2)
            }
        }

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

        !isLoading && lead_Data.value.isEmpty() -> {

            if (myleadsSearchtext.value.isNotEmpty()){
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
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                    , verticalArrangement = Arrangement.Center
                    , horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Image(painterResource(R.drawable.enptyleadsrento) , "")
                    Text("No lead yet!" , color = newBlack , fontSize = constants.textUnit(14))
                    Text("We’ll let you know as soon as new leads come in." , textAlign = TextAlign.Center , color = newBlack , fontSize = constants.textUnit(14))
                }
            }

        }

        lead_Data.value.isNotEmpty() -> {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .padding(bottom = 108.dp)
            )
            {
                itemsIndexed(lead_Data.value ) { index, item ->
                    AnimatedVisibility(
                        visible = true
                        , enter = expandIn(tween(500)) { it }
                        , exit = shrinkOut { it }
                    ) {
                        Column {
                            if (item.isWhich < 4) {

                                Enquiry_Message_Structure(
                                    content = lead_Data.value,
                                    index = index,
                                    reels_Show = reels_Show,
                                    navController = navController,
                                    onDeleteClick = { enquiry ->
                                        selectedEnquiry = enquiry
                                        constants.Enquiry_ViewModel.report_Option_Selection = "Already Sold"
                                        constants.open_Btm_Sheet = Custom_BottomSheetState(
                                            type = "review_delete",
                                            isVisible = true
                                        )
                                    }
                                    , from_where = 0
                                    ,keyboardController
                                    ,focusManager1
                                )
                            } else {
                                Enquiry_Message_Structure2(item, navController , from_where = 0 ,onDeleteClick = { enquiry ->
                                    constants.Enquiry_ViewModel.selectedEnquiry = enquiry
                                    viewModel.toggleshowBABars(false)
                                    constants.open_Popup = Custom_PopUpState(
                                        type = "self_enquiry_delete",
                                        true
                                    )
                                },keyboardController
                                    ,focusManager1)
                            }
                            Spacer(modifier = Modifier.padding(8.dp))
                        }
                    }
                }
            }
        }
    }

    if (constants.open_Btm_Sheet.type == "review_delete" && constants.open_Btm_Sheet.isVisible && selectedEnquiry != null)
    {
        LaunchedEffect(Unit) {
            constants.Enquiry_ViewModel.report_Custom_Reason = ""
        }
        Common_BTM_Sheet(
            content = {
                Text("Why are you declining Enquiry?" , color = newBlack , fontSize = constants.textUnit(20))

                Spacer(modifier = Modifier.padding(8.dp))

                Text("let the lead know why you cannot provide service to them.", color = newGray , fontSize = constants.textUnit(12))

                Spacer(modifier = Modifier.padding(8.dp))

                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                    , horizontalArrangement = Arrangement.spacedBy(8.dp)
                    , verticalArrangement = Arrangement.spacedBy(8.dp)
                )
                {
                    constants.Enquiry_ViewModel.report_Types_List.forEach { reportType ->
                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (constants.Enquiry_ViewModel.report_Option_Selection == reportType) newBlue else newLightGray)
                                .noRippleClickable{
                                    ClickHelper.getInstance().clickOnce {
                                        constants.Enquiry_ViewModel.report_Option_Selection =
                                            reportType
                                    }
                                }
                                .border(1.dp, newBlack, RoundedCornerShape(4.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = reportType,
                                color = if (constants.Enquiry_ViewModel.report_Option_Selection == reportType) newWhite else newBlack,
                                fontSize = constants.textUnit(12)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                        .background(newWhite)
                        .border(1.dp, newBlack, RoundedCornerShape(4.dp))
                ) {
                    TextField(
                        value = constants.Enquiry_ViewModel.report_Custom_Reason,
                        onValueChange = {
                            constants.Enquiry_ViewModel.report_Custom_Reason = it
                        },
                        placeholder = {
                            Text("Start typing here ( optional )" , color = newGray , fontSize = constants.textUnit(12))
                        },
                        modifier = Modifier
                            .wrapContentSize()
                        , colors = TextFieldDefaults.colors(
                            focusedContainerColor = newWhite,
                            unfocusedContainerColor = newWhite,
                            unfocusedIndicatorColor = Color.Transparent
                            , focusedIndicatorColor = Color.Transparent,
                            unfocusedTextColor = newBlack,
                          focusedTextColor = newBlack
                        )
                    )
                }

            }
            , bottom_Content = {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                    , verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    Box(
                        modifier = Modifier
                            .width(168.dp)
                            .height(44.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xffE8E8E8))
                            .noRippleClickable{
                                constants.Enquiry_ViewModel.report_Option_Selection =
                                    "Already Sold"
                                constants.open_Btm_Sheet =
                                    Custom_BottomSheetState(type = "", false)

                            }
                        , contentAlignment = Alignment.Center
                    ){
                        Text("Cancel" , color = newDarkGray , fontSize = constants.textUnit(14))

                    }

                    var state = remember { mutableStateOf(-1) }
                    Box(
                        modifier = Modifier
                            .width(168.dp)
                            .height(46.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .noRippleClickable{
                                if ( constants.Enquiry_ViewModel.report_Custom_Reason.isNotEmpty() || constants.Enquiry_ViewModel.report_Option_Selection.isNotEmpty()) {
                                    ClickHelper.getInstance().clickOnce {
                                        constants.Common_H_ViewModel.toggleshowBABars(true)
                                        val enquiry = selectedEnquiry
                                        if (enquiry != null) {
                                            constants.API_Vm.put_Enquiry_Decline(
                                                enquire_id = enquiry.enquiry_details.enquire_id
                                                    ?: 0,
                                                user_posts_id = enquiry.post_user.user_post_id ?: 0,
                                                sentence = constants.Enquiry_ViewModel.report_Option_Selection,
                                                custom_para = constants.Enquiry_ViewModel.report_Custom_Reason
                                            )
                                            { aPI_Result_Handling ->
                                                when (aPI_Result_Handling) {
                                                    is API_Result_Handling.Loading -> {

                                                        state.value = 0

                                                    }

                                                    is API_Result_Handling.Deactivated -> {

                                                    }

                                                    is API_Result_Handling.Error -> {

                                                        state.value = 1

                                                    }

                                                    is API_Result_Handling.Success -> {

                                                        state.value = 2

                                                        constants.Enquiry_ViewModel.decline_Update_Enquiry(
                                                            enquiry.enquiry_details?.enquire_id ?: 0
                                                        )

                                                        constants.Enquiry_ViewModel.report_Option_Selection =
                                                            "Already Sold"

                                                        constants.open_Btm_Sheet =
                                                            Custom_BottomSheetState(
                                                                type = "",
                                                                false
                                                            )

                                                    }

                                                    is API_Result_Handling.NoData -> {

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            .background(Color(0xffE54C3C)),
                        contentAlignment = Alignment.Center
                    ){
                        if (state.value == 0){
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                        }
                        else {
                            Text("Decline" , color = newWhite , fontSize = constants.textUnit(14))
                        }
                    }
                }
            }
        )
    }

    if (constants.open_Btm_Sheet.type == "leads_filter" && constants.open_Btm_Sheet.isVisible) {

        var bring_Calender by rememberSaveable { mutableStateOf(false) }
        var tempSelectedFilter by rememberSaveable { mutableStateOf(constants.Enquiry_ViewModel.leads_selected_Filter) }
        var tempSelectedSort by rememberSaveable { mutableStateOf(constants.Enquiry_ViewModel.leads_selected_Sort) }

        Common_BTM_Sheet(
            content = {
                AnimatedContent(
                    targetState = bring_Calender,
                    transitionSpec = {
                        slideInHorizontally(animationSpec = tween(500)) togetherWith ExitTransition.None
                    },
                    label = "",
                    modifier = Modifier.fillMaxWidth()
                ) { showCalendar ->

                    if (showCalendar) {

                        CalendarExample(
                            modifier = Modifier
                                .background(newWhite)
                                .fillMaxWidth()
                                .wrapContentSize(Alignment.Center),
                            initialStartDate = constants.Enquiry_ViewModel.Selected_Dates_List.firstOrNull()?.start_Date,
                            initialEndDate = constants.Enquiry_ViewModel.Selected_Dates_List.firstOrNull()?.end_Date,
                            onDateRangeSelected = { start, end ->
                                if (start != null && end != null) {
                                    constants.Enquiry_ViewModel.Selected_Dates_List.clear()
                                    constants.Enquiry_ViewModel.Selected_Dates_List.add(
                                        Selected_Dates_Calender(
                                            start,
                                            end,
                                            convertDateToTimestamp(start.toString()).toString(),
                                            convertDateToTimestamp(end.toString()).toString()
                                        )
                                    )
                                    constants.Enquiry_ViewModel.add_Selected_Date_Range(
                                        Selected_Dates_Calender(
                                            start,
                                            end,
                                            convertDateToTimestamp(start.toString()).toString(),
                                            convertDateToTimestamp(end.toString()).toString()
                                        )
                                    )
                                }
                            },
                            onDateSelected = { start ->
                                if (start != null) {
                                    constants.Enquiry_ViewModel.Selected_Dates_List.clear()
                                    constants.Enquiry_ViewModel.Selected_Dates_List.add(
                                        Selected_Dates_Calender(
                                            start,
                                            null,
                                            convertDateToTimestamp(start.toString()).toString(),
                                            ""
                                        )
                                    )
                                }
                            }
                        )
                    } else {

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                "Filter & Sort",
                                color = newBlack,
                                fontSize = constants.textUnit(20),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )

                            Spacer(modifier = Modifier.padding(8.dp))

                            Text("Lead Type", color = newBlack, fontSize = constants.textUnit(16))
                            Spacer(modifier = Modifier.padding(8.dp))

                            FlowRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                constants.Enquiry_ViewModel.leadType_Filter_List.forEachIndexed { index, filterType ->
                                    Box(
                                        modifier = Modifier
                                            .wrapContentSize()
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(
                                                if (tempSelectedFilter == index + 1)
                                                    newLightBlue else newLightGray
                                            )
                                            .noRippleClickable {
                                                ClickHelper.getInstance().clickOnce {
                                                    tempSelectedFilter = index + 1
                                                }
                                            }
                                            .border(1.dp, if (tempSelectedFilter == index + 1)
                                                newBlue else newGray, RoundedCornerShape(4.dp))
                                            .padding(horizontal = 8.dp, vertical = 4.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = filterType,
                                            color = if (tempSelectedFilter == index + 1)
                                                newBlack else newBlack,
                                            fontSize = constants.textUnit(12)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.padding(8.dp))

                            Text("Sort By", color = newBlack, fontSize = constants.textUnit(16))
                            Spacer(modifier = Modifier.padding(8.dp))

                            FlowRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                constants.Enquiry_ViewModel.lead_Sort_List.forEachIndexed { index, sortType ->
                                    Box(
                                        modifier = Modifier
                                            .wrapContentSize()
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(
                                                if (tempSelectedSort == index + 1)
                                                    newLightBlue else newLightGray
                                            )
                                            .noRippleClickable {
                                                ClickHelper.getInstance().clickOnce {
                                                    tempSelectedSort = index + 1
                                                    if (tempSelectedSort == 5) {
                                                        bring_Calender = true
                                                    } else {
                                                        constants.Enquiry_ViewModel.Selected_Dates_List.clear()
                                                    }
                                                }
                                            }
                                            .border(1.dp, if (tempSelectedSort == index + 1)
                                                newBlue else newGray, RoundedCornerShape(4.dp))
                                            .padding(horizontal = 8.dp, vertical = 4.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = sortType,
                                            color = if (tempSelectedSort == index + 1)
                                                newBlack else newBlack,
                                            fontSize = constants.textUnit(12)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.padding(36.dp))
                        }
                    }
                }
            },
            bottom_Content = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    Box(
                        modifier = Modifier
                            .width(168.dp)
                            .height(44.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xffE8E8E8))
                            .noRippleClickable {
                                ClickHelper.getInstance().clickOnce {
                                    if (bring_Calender) {
                                        tempSelectedFilter = 4
                                        tempSelectedSort = 1

                                        constants.Enquiry_ViewModel.leads_selected_Filter = 4
                                        constants.Enquiry_ViewModel.leads_selected_Sort = 1
                                        bring_Calender = false
                                        constants.Enquiry_ViewModel.Selected_Dates_List.clear()
                                    } else {

                                        tempSelectedFilter = 4
                                        tempSelectedSort = 1

                                        constants.Enquiry_ViewModel.leads_selected_Filter = 4
                                        constants.Enquiry_ViewModel.leads_selected_Sort = 1
                                        constants.open_Btm_Sheet =
                                            Custom_BottomSheetState(type = "", isVisible = false)
                                    }

                                    constants.API_Vm.isLoading_Leads = true
                                    constants.API_Vm.totalPages_Leads = 1
                                    reload_ONSort_Filter.value = reload_ONSort_Filter.value + 4574657
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (bring_Calender) "Cancel" else "Reset",
                            color = newDarkGray,
                            fontSize = constants.textUnit(14)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .width(168.dp)
                            .height(44.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                Brush.verticalGradient(if (bring_Calender) {
                                    if (constants.Enquiry_ViewModel.Selected_Dates_List.isNotEmpty())
                                        newPurpleGradient
                                    else newPurpleGradient
                                } else newPurpleGradient )
                            )
                            .noRippleClickable {
                                ClickHelper.getInstance().clickOnce {
                                    if (bring_Calender) {
                                        if (constants.Enquiry_ViewModel.Selected_Dates_List.isNotEmpty()) {
                                            constants.Enquiry_ViewModel.leads_selected_Filter = tempSelectedFilter
                                            constants.Enquiry_ViewModel.leads_selected_Sort = tempSelectedSort
                                            constants.open_Btm_Sheet =
                                                Custom_BottomSheetState(type = "", isVisible = false)
                                        }
                                    } else {

                                        constants.Enquiry_ViewModel.leads_selected_Filter = tempSelectedFilter
                                        constants.Enquiry_ViewModel.leads_selected_Sort = tempSelectedSort
                                        constants.open_Btm_Sheet =
                                            Custom_BottomSheetState(type = "", isVisible = false)
                                    }

                                    constants.API_Vm.isLoading_Leads = true
                                    constants.API_Vm.totalPages_Leads = 1
                                    reload_ONSort_Filter.value = reload_ONSort_Filter.value + 4574657
                                }
                            }
                            .border(1.dp  , Brush.verticalGradient(if (bring_Calender) {
                                if (constants.Enquiry_ViewModel.Selected_Dates_List.isNotEmpty())
                                    newPurpleGradientBorder
                                else newPurpleGradientBorder
                            } else newPurpleGradientBorder
                            )
                                ,RoundedCornerShape(4.dp)
                            )
                        ,
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (bring_Calender) "Update" else "Apply",
                            color = newWhite,
                            fontSize = constants.textUnit(14),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        )
    }

    BackHandler {
        ClickHelper.getInstance().clickOnce {
            if (ClickGuard.canClick()) {
                when {
                    constants.open_Btm_Sheet.type == "review_delete" && constants.open_Btm_Sheet.isVisible && selectedEnquiry != null -> {

                        constants.open_Btm_Sheet =
                            Custom_BottomSheetState(type = "", false)
                    }

                    constants.open_Btm_Sheet.type == "leads_filter" && constants.open_Btm_Sheet.isVisible -> {

                            constants.Enquiry_ViewModel.leads_selected_Filter = 4
                            constants.Enquiry_ViewModel.leads_selected_Sort = 1
                        reload_ONSort_Filter.value = reload_ONSort_Filter.value + 1

                        constants.open_Btm_Sheet =
                                Custom_BottomSheetState(type = "", isVisible = false)

                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelfEnquiry_Content(
    reels_Show: MutableState<Boolean>,
    navController: NavHostController,
    reload_api_onSORT: MutableState<Int>,
    viewModel: Common_H_ViewModel,
    selfEnquiriesSort: MutableState<Boolean>,
    selfenquirySearchtext: MutableState<String>,
    keyboardController: SoftwareKeyboardController?,
    focusManager1: FocusManager
)
{

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val network = rememberNetworkStatus()

    val isLoading = constants.API_Vm.isLoading_Self_Enquiry
    val errorMessage = constants.API_Vm.errorMessage_Self_Enquiry
    val currentPage = constants.API_Vm.currentPage_Self_Enquiry
    val totalPages = constants.API_Vm.totalPages_Self_Enquiry
    val listState = rememberLazyListState()

    val context = LocalContext.current
    val placesClient = Places.createClient(context)

    val selfe_Data = constants.Enquiry_ViewModel.self_Enquiry.collectAsStateWithLifecycle()
    var retry by remember { mutableStateOf(0) }

    val selected_Date_Range = constants.Enquiry_ViewModel.selected_Date_Range.collectAsStateWithLifecycle()
    val selected_Date_Range_List = constants.Enquiry_ViewModel.Selected_Dates_List

    val reversedData = remember(selfe_Data.value) { selfe_Data.value}

    LaunchedEffect(Unit, reload_api_onSORT.value, retry) {

        val selectedRange = selected_Date_Range_List.firstOrNull()

        val start = selectedRange?.timestamp_Start.orEmpty()
        val end = selectedRange?.timeStamp_End.orEmpty()

        val isRange = start.isNotEmpty() && end.isNotEmpty() && start != end
        val mainDate = if (!isRange) {

            if (end.isNotEmpty()) end else start
        } else {
            ""
        }

        val customer_dates = if (isRange) "" else mainDate
        val customer_dates_start = if (isRange) start else ""
        val customer_dates_end = if (isRange) end else ""

        constants.API_Vm.load_Self_Enquiry(
            user_id = AppPreferences.getUserId(),
            page = 1,
            filter_type = if (constants.Enquiry_ViewModel.Self_Enquiry_selected_Sort != 0)
                constants.Enquiry_ViewModel.Self_Enquiry_selected_Sort + 1 else 1,
            customer_dates = customer_dates,
            customer_dates_start = customer_dates_start,
            customer_dates_end = customer_dates_end,
            search_text = selfenquirySearchtext.value
        )

    }

    LaunchedEffect(listState, currentPage, isLoading, totalPages) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItemIndex ->
                val totalItems = listState.layoutInfo.totalItemsCount
                val loadMoreThreshold = 4

                if (
                    lastVisibleItemIndex != null &&
                    totalItems > 0 &&
                    lastVisibleItemIndex >= totalItems - loadMoreThreshold &&
                    !isLoading &&
                    currentPage < totalPages
                ) {
                    constants.API_Vm.load_Self_Enquiry(
                        user_id = AppPreferences.getUserId(),
                        page = currentPage + 1,
                        filter_type = 1,
                        customer_dates = selected_Date_Range_List.firstOrNull()?.timeStamp_End
                            ?.ifEmpty { selected_Date_Range_List.firstOrNull()?.timestamp_Start ?: "" }
                            ?: selected_Date_Range_List.firstOrNull()?.timestamp_Start ?: "",
                        customer_dates_start = selected_Date_Range_List.firstOrNull()?.timeStamp_End ?: "",
                        customer_dates_end = selected_Date_Range_List.firstOrNull()?.timeStamp_End ?: "",
                        search_text = selfenquirySearchtext.value
                    )
                }
            }
    }

    when {

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
                Text(constants.activity.getString(R.string.no_Internet) , color = newBlack,fontSize = constants.textUnit(16)
                    , fontFamily = constants.fontFamily(0)
                    , textAlign = TextAlign.Center)

            }
        }

        isLoading && selfe_Data.value.isEmpty() -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottiAnimation(2)
            }
        }

        !isLoading && selfe_Data.value.isEmpty() -> {
            if (selfenquirySearchtext.value.isNotEmpty()){
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
                        .fillMaxSize()
                        .padding(bottom = 48.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painterResource(R.drawable.emptyselfenquiryrento), "")
                    Text("You haven't made any self enquiries.", color = newBlack , fontSize = constants.textUnit(14))
                    Text("We'll list them here once you do.", textAlign = TextAlign.Center, color = newBlack , fontSize = constants.textUnit(14))
                }
            }

        }

        !isLoading && errorMessage?.isNotEmpty() == true -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                API_Fail_UI(onReTryClick = {
                    retry = retry + 213435
                    constants.API_Vm.errorMessage_Self_Enquiry = ""
                })
            }
        }

        selfe_Data.value.isNotEmpty() -> {
            LazyColumn(
                modifier = Modifier.padding(bottom = 108.dp),
                state = listState
            ) {

                itemsIndexed(reversedData) { index, item ->
                    AnimatedVisibility(
                        visible = true,
                        enter = expandIn(tween(500)) { it },
                        exit = shrinkOut { it }
                    ) {
                        Column {

                            if ((item?.isWhich ?: 1) < 5) {
                                Enquiry_Message_Structure(
                                    content = reversedData,
                                    index = index,
                                    reels_Show = reels_Show,
                                    navController = navController,
                                    onDeleteClick = { enquiry ->
                                        constants.Enquiry_ViewModel.selectedEnquiry = enquiry
                                        viewModel.toggleshowBABars(false)
                                        constants.open_Popup = Custom_PopUpState(
                                            type = "self_enquiry_delete",
                                            true
                                        )
                                    },
                                    from_where = 1,
                                    keyboardController = keyboardController,
                                    focusManager1 = focusManager1
                                )
                            } else {
                                Enquiry_Message_Structure2(
                                    item,
                                    navController,
                                    from_where = 1,
                                    onDeleteClick = { enquiry ->
                                        constants.Enquiry_ViewModel.selectedEnquiry = enquiry
                                        viewModel.toggleshowBABars(false)
                                        constants.open_Popup = Custom_PopUpState(
                                            type = "self_enquiry_delete",
                                            true
                                        )
                                    },
                                    keyboardController,
                                    focusManager1
                                )
                            }

                            Spacer(modifier = Modifier.padding(8.dp))
                        }
                    }
                }

                if (isLoading && currentPage > 1) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }

    var selectedOption by remember { mutableStateOf("Newest first") }

    if (selfEnquiriesSort.value) {

        var bring_Calender by rememberSaveable { mutableStateOf(false) }

        var tempSelectedSort by rememberSaveable { mutableStateOf(constants.Enquiry_ViewModel.Self_Enquiry_selected_Sort) }

        Common_BTM_Sheet(
            content = {
                AnimatedContent(
                    targetState = bring_Calender,
                    transitionSpec = {
                        slideInHorizontally(animationSpec = tween(500)) togetherWith ExitTransition.None
                    },
                    label = "",
                    modifier = Modifier.fillMaxWidth()
                ) { showCalendar ->

                    if (showCalendar) {

                        CalendarExample(
                            modifier = Modifier
                                .background(newWhite)
                                .fillMaxWidth()
                                .wrapContentSize(Alignment.Center),
                            initialStartDate = constants.Enquiry_ViewModel.Selected_Dates_List.firstOrNull()?.start_Date,
                            initialEndDate = constants.Enquiry_ViewModel.Selected_Dates_List.firstOrNull()?.end_Date,
                            onDateRangeSelected = { start, end ->
                                if (start != null && end != null) {
                                    constants.Enquiry_ViewModel.Selected_Dates_List.clear()
                                    constants.Enquiry_ViewModel.Selected_Dates_List.add(
                                        Selected_Dates_Calender(
                                            start,
                                            end,
                                            convertDateToTimestamp(start.toString()).toString(),
                                            convertDateToTimestamp(end.toString()).toString()
                                        )
                                    )
                                    constants.Enquiry_ViewModel.add_Selected_Date_Range(
                                        Selected_Dates_Calender(
                                            start,
                                            end,
                                            convertDateToTimestamp(start.toString()).toString(),
                                            convertDateToTimestamp(end.toString()).toString()
                                        )
                                    )
                                }
                            },
                            onDateSelected = { start ->
                                if (start != null) {
                                    constants.Enquiry_ViewModel.Selected_Dates_List.clear()
                                    constants.Enquiry_ViewModel.Selected_Dates_List.add(
                                        Selected_Dates_Calender(
                                            start,
                                            null,
                                            convertDateToTimestamp(start.toString()).toString(),
                                            ""
                                        )
                                    )
                                }
                            }
                        )
                    } else {

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text("Sort By", color = newBlack, fontSize = constants.textUnit(16))
                            Spacer(modifier = Modifier.padding(8.dp))

                            FlowRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            )
                            {
                                constants.Enquiry_ViewModel.lead_Sort_List.forEachIndexed { index, sortType ->
                                    Box(
                                        modifier = Modifier
                                            .wrapContentSize()
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(
                                                if (tempSelectedSort == index + 1)
                                                    newLightBlue else newLightGray
                                            )
                                            .noRippleClickable {
                                                ClickHelper.getInstance().clickOnce {

                                                    selectedOption = sortType
                                                    tempSelectedSort = index + 1

                                                    constants.Enquiry_ViewModel.Self_Enquiry_selected_Sort = index + 1

                                                    if (index == 4) {
                                                        bring_Calender = true

                                                    } else {
                                                        constants.Enquiry_ViewModel.Selected_Dates_List.clear()
                                                    }

                                                }
                                            }
                                            .border(
                                                1.dp, if (tempSelectedSort == index + 1)
                                                    newBlue else newGray, RoundedCornerShape(4.dp)
                                            )
                                            .padding(horizontal = 8.dp, vertical = 4.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = sortType,
                                            color = if (tempSelectedSort == index + 1)
                                                newBlack else newBlack,
                                            fontSize = constants.textUnit(12)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.padding(36.dp))
                        }
                    }
                }
            },
            bottom_Content = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    Box(
                        modifier = Modifier
                            .width(168.dp)
                            .height(44.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xffE8E8E8))
                            .noRippleClickable {
                                ClickHelper.getInstance().clickOnce {
                                    if (bring_Calender) {

                                        tempSelectedSort = 1
                                        constants.Enquiry_ViewModel.Self_Enquiry_selected_Sort = 1

                                        bring_Calender = false
                                        constants.Enquiry_ViewModel.Selected_Dates_List.clear()
                                        selfEnquiriesSort.value = false
                                    } else {

                                        tempSelectedSort = 1

                                        constants.Enquiry_ViewModel.Self_Enquiry_selected_Sort = 1

                                        constants.Enquiry_ViewModel.Selected_Dates_List.clear()
                                        constants.API_Vm.isLoading_Self_Enquiry = true
                                        constants.API_Vm.totalPages_Self_Enquiry = 1
                                        reload_api_onSORT.value = reload_api_onSORT.value + 4560

                                        selfEnquiriesSort.value = false

                                    }

                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (bring_Calender) "Cancel" else "Reset",
                            color = newDarkGray,
                            fontSize = constants.textUnit(14)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .width(168.dp)
                            .height(44.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                Brush.verticalGradient(
                                    if (bring_Calender) {
                                        if (constants.Enquiry_ViewModel.Selected_Dates_List.isNotEmpty())
                                            newPurpleGradient
                                        else newPurpleGradient
                                    } else newPurpleGradient
                                )
                            )
                            .noRippleClickable {
                                ClickHelper.getInstance().clickOnce {
                                    if (bring_Calender) {
                                        if (constants.Enquiry_ViewModel.Selected_Dates_List.isNotEmpty()) {

                                            constants.Enquiry_ViewModel.leads_selected_Sort =
                                                tempSelectedSort
                                            constants.open_Btm_Sheet =
                                                Custom_BottomSheetState(
                                                    type = "",
                                                    isVisible = false
                                                )
                                            constants.API_Vm.isLoading_Self_Enquiry = true
                                            constants.API_Vm.totalPages_Self_Enquiry = 1
                                            reload_api_onSORT.value = reload_api_onSORT.value + 12345

                                            selfEnquiriesSort.value = false
                                        }
                                    } else {

                                        constants.Enquiry_ViewModel.leads_selected_Sort =
                                            tempSelectedSort
                                        constants.open_Btm_Sheet =
                                            Custom_BottomSheetState(type = "", isVisible = false)
                                    }

                                    constants.API_Vm.isLoading_Self_Enquiry = true
                                    constants.API_Vm.totalPages_Self_Enquiry = 1
                                    reload_api_onSORT.value = reload_api_onSORT.value + 12345
                                    selfEnquiriesSort.value = false

                                }
                            }
                            .border(
                                1.dp, Brush.verticalGradient(
                                    if (bring_Calender) {
                                        if (constants.Enquiry_ViewModel.Selected_Dates_List.isNotEmpty())
                                            newPurpleGradientBorder
                                        else newPurpleGradientBorder
                                    } else newPurpleGradientBorder
                                ), RoundedCornerShape(4.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (bring_Calender) "Update" else "Apply",
                            color = newWhite,
                            fontSize = constants.textUnit(14),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            , onDismiss = selfEnquiriesSort
        )
    }

    BackHandler {
        ClickHelper.getInstance().clickOnce {
            if (ClickGuard.canClick()) {
                if (constants.Enquiry_ViewModel.Self_Enquiry_selected_Sort == 4) {
                    constants.Enquiry_ViewModel.Self_Enquiry_selected_Sort = 0
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Message_Content(
    onOpenBuyersList: (propertyId: String, sellerId: String) -> Unit
)
{
    var network = rememberNetworkStatus()
    var listState = rememberLazyListState()

    var isLoading = constants.API_Vm.isLoading_CML
    var totalPages = constants.API_Vm.totalPages_CML
    var currentPage = constants.API_Vm.currentPage_CML
    var errorMessage = constants.API_Vm.errorMessage_CML

    val chatList = constants.Enquiry_ViewModel.chatMainList.collectAsStateWithLifecycle()

    var reLoadchatListApi = remember { mutableStateOf(0) }

    val focusManager = LocalFocusManager.current

    var chatsearchtext = remember { mutableStateOf("") }

    DisposableEffect(Unit , reLoadchatListApi.value) {
        Log.d("MESSAGE API HITTING", "HITTING DISPOSAL -- ${constants.Enquiry_ViewModel.selected_Msg_Filter.value}")
        constants.API_Vm.isLoading_CML = true
        constants.API_Vm.totalPages_CML = 1

        constants.API_Vm.load_ChatMainList(
            user_id =  AppPreferences.getUserId(),
            filter_type = when {
                constants.Enquiry_ViewModel.selected_Msg_Filter.value == 1 -> "1,2"
                constants.Enquiry_ViewModel.selected_Msg_Filter.value == 2 -> "1"
                else -> "2"
            },

            search_text = chatsearchtext.value,
            page = 1
        )
        onDispose {
            Log.d("MESSAGE API HITTING", "DISPOSAL OF SCREEN")
        }
    }

    LaunchedEffect(listState, currentPage, isLoading, totalPages) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItemIndex ->
                val totalItems = listState.layoutInfo.totalItemsCount
                val loadMoreThreshold = 4

                if (
                    lastVisibleItemIndex != null &&
                    totalItems > 0 &&
                    lastVisibleItemIndex >= totalItems - loadMoreThreshold &&
                    !isLoading &&
                    currentPage < totalPages
                ) {
                    constants.API_Vm.load_ChatMainList(
                        user_id = AppPreferences.getUserId(),
                        filter_type = when {
                            constants.Enquiry_ViewModel.selected_Msg_Filter.value == 1 -> "1,2"
                            constants.Enquiry_ViewModel.selected_Msg_Filter.value == 2  -> "1"
                            else -> "2"
                        },
                        search_text = chatsearchtext.value,
                        page = currentPage + 1
                    )

                }
            }

    }

    var chatlistFilterOptions = listOf("All" , "Enquired" , "Self Enquired")

    var chatFilterOpen = remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .padding(bottom = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Box(
                    modifier = Modifier
                        .weight(8.5f)
                        .height(56.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .border(1.dp, newGray, RoundedCornerShape(8.dp))
                ) {
                    TextField(
                        value = chatsearchtext.value,
                        onValueChange = {
                            chatsearchtext.value = it
                            if (it.length >= 3) {
                                constants.API_Vm.isLoading_CML = true
                                constants.API_Vm.totalPages_CML = 1

                                reLoadchatListApi.value = reLoadchatListApi.value + 2345321

                            }
                            if (it.length == 0) {
                                constants.API_Vm.isLoading_CML = true
                                constants.API_Vm.totalPages_CML = 1
                                reLoadchatListApi.value = reLoadchatListApi.value + 12345
                            }
                        },
                        leadingIcon = {

                            Icon(
                                painter = painterResource(R.drawable.searchnotrento),
                                contentDescription = "Clear",
                                modifier = Modifier
                                    .size(18.dp)
                            )

                        },
                        placeholder = {
                            Text(
                                "search by name, location..",
                                color = newBlack,
                                fontSize = constants.textUnit(14),
                                fontFamily = constants.fontFamily(2),
                            )
                        },
                        trailingIcon = {

                            Image(
                                painter = painterResource(R.drawable.search_text_clear),
                                contentDescription = "Clear",
                                modifier = Modifier
                                    .size(18.dp)
                                    .noRippleClickable {
                                        if (chatsearchtext.value.isNotEmpty()) {
                                            chatsearchtext.value = ""
                                            constants.API_Vm.isLoading_CML = true
                                            constants.API_Vm.totalPages_CML = 1
                                            reLoadchatListApi.value =
                                                reLoadchatListApi.value + 12345
                                        }
                                    }

                            )

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

                                focusManager.clearFocus()
                            }
                        ),
                        modifier = Modifier

                            .fillMaxWidth()
                    )
                }

                constants.spacer(6)

                        var iconstate =
                            if (

                                constants.Enquiry_ViewModel.selected_Msg_Filter.value == 1
                            ) {
                                R.drawable.filter
                            } else {
                                R.drawable.filterappliedrento
                            }

                SubcomposeAsyncImage(
                    model = iconstate,
                    "",
                    modifier = Modifier
                        .height(56.dp)
                        .weight(2f)
                        .noRippleClickable {
                            ClickHelper.getInstance().clickOnce {

                                chatFilterOpen.value = true

                            }
                        }
                )
            }

            constants.spacer(8)

            when {

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
                        Text(
                            constants.activity.getString(R.string.no_Internet),
                            color = newBlack,
                            fontSize = constants.textUnit(16),
                            fontFamily = constants.fontFamily(0),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                isLoading && currentPage == 1 -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LottiAnimation(2)
                    }
                }

                !errorMessage.isNullOrEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(), contentAlignment = Alignment.Center
                    ) {

                        API_Fail_UI(onReTryClick = {
                            constants.API_Vm.reload_CML_API(
                                user_id = AppPreferences.getUserId(),
                                filter_type =  when {
                                    constants.Enquiry_ViewModel.selected_Msg_Filter.value == 1 -> "1,2"
                                    constants.Enquiry_ViewModel.selected_Msg_Filter.value == 2  -> "1"
                                    else -> "2"
                                },
                                search_text = chatsearchtext.value,
                                page = 1
                            )
                        })
                    }
                }

                chatList.value.isEmpty() && !isLoading -> {

                    Column(
                        modifier = Modifier
                            .padding(bottom = 76.dp)
                            .fillMaxSize()

                        , verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        Spacer(modifier = Modifier.padding(8.dp))
                        Image(
                            painterResource(R.drawable.emptychatrento),
                            ""
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text("No Chats Found!")
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(
                            "Collab with others and  Check back later .",
                            textAlign = TextAlign.Center
                        )
                    }

                }

                chatList.value.isNotEmpty() && !isLoading -> {

                    FirebaseRepository.getTotalUnreadChatsForUser(AppPreferences.getUserId().toString() , onResult = {
                            count ->
                        Log.d("COUNT TOTAL UNREAD BY USER --- " , "${count}")
                    })

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 100.dp)
                        , listState
                    )
                    {

                        itemsIndexed(chatList.value) {

                                index, item ->

                            Chat_User_Structure(
                                content = item,
                                index = index,
                                onClick = { clickedItem ->
                                    onOpenBuyersList(
                                        clickedItem.video_model.user_post_id.toString(),
                                        clickedItem.video_model.user_id.toString(),
                                    )
                                }
                            )

                            constants.spacer(8)

                        }
                    }

                }
            }
        }

    if (chatFilterOpen.value){

        val tempSelectedFilter = remember {
            mutableStateOf(constants.Enquiry_ViewModel.selected_Msg_Filter.value)
        }

        ModalBottomSheet(
            onDismissRequest = {
                chatFilterOpen.value = false
            }
                , containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()

            ) {

                constants.spacer(2)

                CommonText("Filter",
                    newBlack,
                    20,
                    0,
                    modifier = Modifier .padding(horizontal = 16.dp)
                )
                constants.spacer(8)

                CommonText("Message Type",
                    Color(0xff1A1A1A),
                    16,
                    1
                    , modifier = Modifier .padding(horizontal = 16.dp)
                )
                constants.spacer(6)

                FlowRow(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                    , horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                )
                {
                    chatlistFilterOptions.forEachIndexed { index, sortType ->
                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .clip(RoundedCornerShape(4.dp))
                                .background(
                                    if (tempSelectedFilter.value == (index + 1))
                                        newLightBlue else Color.White
                                )
                                .noRippleClickable {
                                    ClickHelper.getInstance().clickOnce {

                                        tempSelectedFilter.value = index + 1

                                    }
                                }
                                .border(
                                    1.dp,
                                    if (tempSelectedFilter.value == (index + 1))
                                        newBlue else newGray,
                                    RoundedCornerShape(4.dp)
                                )

                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = sortType,
                                color = if (constants.Enquiry_ViewModel.selected_Msg_Filter.value == (index + 1))
                                    newBlack else newBlack,
                                fontSize = constants.textUnit(12)
                            )
                        }
                    }
                }

                constants.spacer(8)

                Static_Bottom(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                    , content = {

                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp)
                                .fillMaxWidth()

                            , verticalAlignment = Alignment.CenterVertically
                            , horizontalArrangement = Arrangement.SpaceEvenly
                        )
                        {
                            Box(
                                modifier = Modifier
                                    .weight(4f)
                                    .height(56.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .noRippleClickable {
                                        tempSelectedFilter.value = 1
                                        constants.Enquiry_ViewModel.selected_Msg_Filter.value = 1
                                        chatFilterOpen.value = false
                                        reLoadchatListApi.value += 1234
                                    }
                                    .background(Color(0xffEBEBEB))
                                , contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "Reset",
                                    color = newDarkGray,
                                    fontSize = constants.textUnit(14),
                                    fontFamily = constants.fontFamily(0)
                                )
                            }

                            constants.spacer(4)

                            Box(
                                modifier = Modifier
                                    .weight(4f)
                                    .height(56.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .noRippleClickable {
                                        chatFilterOpen.value = false
                                        constants.Enquiry_ViewModel.selected_Msg_Filter.value =
                                            tempSelectedFilter.value
                                        reLoadchatListApi.value += 1234
                                    }
                                    .background(Brush.verticalGradient(newPurpleGradient))
                                , contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "Apply",
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

@Composable
fun Chat_User_Structure(
    content: Chat_Main_List_Data
    , index: Int
    , onClick: (Chat_Main_List_Data) -> Unit
){

    var countUnread = remember { mutableStateOf(0) }

    LaunchedEffect(content.video_model.user_post_id) {
        FirebaseRepository.getTotalUnreadCountForProperty(
            propertyId = content.video_model.user_post_id.toString(),
            currentUserId = AppPreferences.getUserId().toString()
        ) { count ->
            countUnread.value = count
            Log.d("COUNT PER PROPERTY", "$count")
        }
    }

    Card(
        modifier = Modifier

            .fillMaxWidth()
            .clickable {
                onClick(content)
                constants.Enquiry_ViewModel.selectMainListData(content)
                       }
        , shape = RoundedCornerShape(8.dp)
        , border = BorderStroke(1.dp , Color(0xffCECECE))
        , colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    )
    {
        Column (
            modifier = Modifier

                .fillMaxSize()
        ){
            Row(
                modifier = Modifier
                    .background(if (content.chat_type == 2) Color(0xffEAFAE0) else Color(0xffF7F0DC))
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (content.chat_type == 2){

                    Text("Self Enquired"
                        , color = Color(0xff269D3D)
                        , textAlign = TextAlign.Start
                        , modifier = Modifier

                            .padding(vertical = 6.dp, horizontal = 6.dp)
                    )

                    Row {

                        if (countUnread.value != 0 ) {
                            Text("New Message")

                            constants.spacer(2)

                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(newBlue), contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "${countUnread.value}",
                                    color = Color.White,
                                    lineHeight = 8.sp,
                                    modifier = Modifier
                                        .padding(2.dp)
                                )
                            }
                        }

                        constants.spacer(2)

                        Icon(painterResource(R.drawable.right_arrow), "")
                    }
                }
                else {

                    Text("Enquired"
                        , color = newBlue
                        , textAlign = TextAlign.Start
                        , modifier = Modifier

                            .padding(vertical = 6.dp, horizontal = 6.dp)
                    )

                    Box(
                        modifier = Modifier
                    )
                    {
                        Row(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                        )
                        {
                            if (countUnread.value != 0) {
                                Text("New Message")

                                constants.spacer(2)

                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clip(CircleShape)
                                        .background(newBlue), contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        "${countUnread.value}",
                                        color = Color.White,
                                        lineHeight = 8.sp,
                                        modifier = Modifier
                                            .padding(2.dp)
                                    )
                                }

                                constants.spacer(2)

                            }

                        }
                    }
                }

            }

            Box(
                modifier = Modifier
                    .padding(all = 8.dp)
                    .background(Color(0xffFBFBFB))
                    .border(1.dp, Color(0xffE8E8E8) , RoundedCornerShape(12.dp))
            )
            {
                ListItem(
                    headlineContent = {
                        Text(content.video_model.post_property.landCategoryText ?: "")
                    }
                    , supportingContent = {
                        Row(
                            horizontalArrangement = Arrangement.Center
                            , verticalAlignment = Alignment.Top
                        ) {
                            SubcomposeAsyncImage(
                                model = R.drawable.locationpinenquiry,
                                contentDescription = "",
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier
                                    .size(14.dp)
                                    .align(Alignment.Top)
                            )

                            constants.spacer(2)

                            Text(content.video_model.post_property.locality ?: "")
                        }
                    },
                    trailingContent = {
                        SubcomposeAsyncImage(
                            model = content.video_model.post_property.thumbnail ?: "",
                            modifier = Modifier
                                .width(90.dp)
                                .heightIn(min = 50.dp , max = 80.dp)

                            , contentDescription = "",
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
                                    Image(painter = painterResource(R.drawable.emptypostsrento) , "")
                                }
                            } else {
                                SubcomposeAsyncImageContent()
                            }
                        }

                        SubcomposeAsyncImage(
                            model = content.video_model.post_property.thumbnail ?: "",
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.width(90.dp).height(50.dp)
                        )
                    },
                    colors = ListItemDefaults.colors(
                        containerColor = Color(0xffFBFBFB)
                    )
                )
            }

            Row(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .height(30.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text("Chats")

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    content.user_details.take(3).forEachIndexed { index, detail ->
                        Box(
                            modifier = Modifier
                                .offset(x = (-12 * index).dp)
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(newLightBlue)
                                .border(1.dp, Color.White, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            SubcomposeAsyncImage(
                                model = detail.profile_image.takeIf { it.isNotEmpty() },
                                contentDescription = detail.username,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                when (painter.state) {
                                    is AsyncImagePainter.State.Loading,
                                    is AsyncImagePainter.State.Error -> {
                                        Box(
                                            modifier = Modifier.fillMaxSize()
                                            , contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = detail.username
                                                    .takeIf { it.isNotEmpty() }
                                                    ?.take(1)?.uppercase() ?: "?",
                                                fontSize = constants.textUnit(14),
                                                fontFamily = constants.fontFamily(1)
                                            )
                                        }
                                    }
                                    else -> SubcomposeAsyncImageContent()
                                }
                            }
                        }
                    }

                    if (content.user_details.size > 3) {
                        Text(
                            "+${content.user_details.size - 3}",
                            modifier = Modifier
                                .offset(x = (-12 * 3).dp)
                                .background(Color.Gray, CircleShape)
                                .padding(horizontal = 6.dp, vertical = 2.dp),
                            color = Color.White,
                            fontSize = constants.textUnit(12)
                        )
                    }
                }
            }

        }
    }
}
