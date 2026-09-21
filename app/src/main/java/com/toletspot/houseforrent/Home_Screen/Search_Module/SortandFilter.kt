package com.toletspot.houseforrent.Home_Screen.Search_Module

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.SearchFilterSort.FloorPreference
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.SearchFilterSort.PostedBy
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.SearchFilterSort.PropertyType
import com.toletspot.houseforrent.API.API_Result_Handling
import com.toletspot.houseforrent.CommonText
import com.toletspot.houseforrent.Custom_Assets.API_Fail_UI
import com.toletspot.houseforrent.Custom_Assets.ClickHelper
import com.toletspot.houseforrent.Custom_Assets.LottiAnimation
import com.toletspot.houseforrent.Custom_Assets.Static_Bottom
import com.toletspot.houseforrent.Custom_Assets.rememberNotchHeightDp
import com.toletspot.houseforrent.Home_Screen.ProfileModule.ClickGuard
import com.toletspot.houseforrent.Home_Screen.Search_Module.RentoSortFilterDC.AvailableFrom
import com.toletspot.houseforrent.Home_Screen.Search_Module.RentoSortFilterDC.PostedDate
import com.toletspot.houseforrent.R
import com.toletspot.houseforrent.Sort_Filter_Field_DC
import com.toletspot.houseforrent.constants
import com.toletspot.houseforrent.forTab
import com.toletspot.houseforrent.noRippleClickable
import com.toletspot.houseforrent.ui.theme.newBlack
import com.toletspot.houseforrent.ui.theme.newBlue
import com.toletspot.houseforrent.ui.theme.newGray
import com.toletspot.houseforrent.ui.theme.newLightBlue
import com.toletspot.houseforrent.ui.theme.newLightGray
import com.toletspot.houseforrent.ui.theme.newWhite

fun selectedModifier(): Modifier {
   return Modifier
        .background(newLightBlue)
       .border(1.dp , newBlue , RoundedCornerShape(4.dp))

}

fun unSelectedModifier(): Modifier{
    return Modifier
        .background(Color.White)
        .border(1.dp , Color(0xff575757) , RoundedCornerShape(4.dp))
}

@Composable
fun Search_Filter_Sort(navController: NavHostController) {

    val notchpadding = rememberNotchHeightDp()

    val filter_Options_List = constants.Search_ViewModel.search_Main_options_List.collectAsState()

    val selected_Filter = constants.Search_ViewModel.selectedIndex.collectAsState()
    val selected_Filter_Id = constants.Search_ViewModel.selectedIndex_Id.collectAsState()

    var api_State by remember { mutableStateOf(0) }

    val option_List = constants.Search_ViewModel.search_Sort_Filter_Fields_Data.collectAsState()

    var retry by remember { mutableStateOf(0) }

    DisposableEffect(Unit ,retry) {
        constants.Search_ViewModel.clear_sort_filter_Data()
        constants.API_Vm.get_Filter_Sort_Search_Fields(
            land_type_id = constants.Search_ViewModel.search_State.value,
        )
        {
            aPI_Result_Handling ->
            when (aPI_Result_Handling){
                is API_Result_Handling.Error -> {
                    api_State = 1
                }
                is API_Result_Handling.Deactivated -> {

                }
                is API_Result_Handling.NoData -> {
                    api_State = 3
                }
                is API_Result_Handling.Loading -> {
                    api_State = 0
                }
                is API_Result_Handling.Success -> {
                    api_State = 2
                    val options = constants.Search_ViewModel.activate_Search_Options()

                    constants.Search_ViewModel.update_Search_Main_Options(options)

                }
            }
        }

        onDispose {  }
    }

    val selected_SF = constants.Search_ViewModel.selected_Sort_Filter_Fields.collectAsState()

    val filterCount by constants.Search_ViewModel.selected_Categories_Count.collectAsState()

    DisposableEffect(Unit) {
        constants.Search_ViewModel.backupCurrentFilterState()

        onDispose {
            constants.Search_ViewModel.clearBackup()
        }
    }

    Column (
        modifier = Modifier
            .background(newWhite)
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(9f)
        ){
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = if (forTab()) 16.dp else  notchpadding.value )
                , horizontalAlignment = Alignment.CenterHorizontally
            ){

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding( start = 16.dp , end = 16.dp)
                    , verticalAlignment = Alignment.CenterVertically
                    , horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Text("Filter & Sort" , color = newBlack , fontSize = constants.textUnit(24) , fontFamily = constants.fontFamily(0))

                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .border(1.dp , Color(0xffE8E8E8) , RoundedCornerShape(2.dp))
                            .background(Color.White)
                            .noRippleClickable{
                                ClickHelper.getInstance().clickOnce {
                                    val activeOptions = filter_Options_List.value.filter { it.onSelected == true }

                                        constants.API_Vm.isLoading_PS_FF = true
                                        constants.API_Vm.totalPages_PS_FF = 1

                                    constants.Search_ViewModel.restoreBackupFilterState()

                                    navController.navigateUp()

                                }
                            }
                        , contentAlignment = Alignment.Center
                    ){
                        Icon(painter = painterResource(R.drawable.close) , contentDescription = "Cancel Reply", modifier = Modifier.size(12.dp))
                    }
                }

                when (api_State){
                    0 -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                            , contentAlignment = Alignment.Center
                        ){
                            LottiAnimation(2)
                        }

                    }
                    1 -> {
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
                        Spacer(modifier = Modifier.padding(16.dp))

                        Row (
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        {
                            Box(
                                modifier = Modifier
                                    .weight(4f)
                                    .fillMaxHeight()
                            )
                            {

                                Column(
                                    modifier = Modifier

                                        .fillMaxHeight()

                                        .background(Color.White)
                                )
                                {
                                    LazyColumn {
                                        val activeOptions = filter_Options_List.value.filter { it.onSelected == true }
                                        items(activeOptions.size) { i ->
                                            val option = activeOptions[i]
                                            val isSelected = selected_Filter_Id.value == option.id
                                            val hasSelections = when (option.id) {
                                                0 -> selected_SF.value?.short_by != 0
                                                1 -> selected_SF.value?.land_type_id != 0 || selected_SF.value?.land_categorie_id?.isNotEmpty() == true
                                                2 -> selected_SF.value?.property_area_from?.isNotEmpty() == true || selected_SF.value?.property_area_to?.isNotEmpty() == true
                                                3 -> selected_SF.value?.budget_from?.isNotEmpty() == true || selected_SF.value?.budget_to?.isNotEmpty() == true
                                                4 -> selected_SF.value?.posted_by?.isNotEmpty()
                                                5 -> selected_SF.value?.floor_plan?.isNotEmpty()

                                                7 -> selected_SF.value?.furnishing_status?.isNotEmpty()
                                                8 -> selected_SF.value?.ownership?.isNotEmpty()
                                                9 -> selected_SF.value?.parking_available?.isNotEmpty()
                                                10 -> selected_SF.value?.no_of_open_sides?.isNotEmpty()
                                                11 -> selected_SF.value?.floor_preferences?.isNotEmpty()
                                                12 -> selected_SF.value?.business_type?.isNotEmpty()
                                                13 -> selected_SF.value?.available_for?.isNotEmpty()
                                                14 -> selected_SF.value?.property_facing?.isNotEmpty()
                                                15 -> selected_SF.value?.amenities?.isNotEmpty()
                                                16 -> selected_SF.value?.property_highlights?.isNotEmpty()

                                                17 -> selected_SF.value?.rent_type?.isNotEmpty()
                                                18 -> selected_SF.value?.posted_date?.isNotEmpty()
                                                19 -> selected_SF.value?.available_from?.isNotEmpty()
                                                20 -> selected_SF.value?.available_for?.isNotEmpty()
                                                21 -> selected_SF.value?.bedrooms?.isNotEmpty()
                                                22 -> selected_SF.value?.food_preference?.isNotEmpty()
                                                23 -> selected_SF.value?.pets_allowed?.isNotEmpty()
                                                24 -> selected_SF.value?.with_photos == "1"
                                                25 -> selected_SF.value?.agreement_type?.isNotEmpty()
                                                else -> false
                                            }

                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(56.dp)
                                                    .background(if(selected_Filter_Id.value == option.id) Color(0xffF7F0DC) else Color.White)
                                                    .padding(start = 16.dp)
                                                    .noRippleClickable{
                                                        ClickHelper.getInstance().clickOnce {
                                                            constants.Search_ViewModel.set_selected_SortOption_Index(i)
                                                            constants.Search_ViewModel.set_selected_SortOption_Index_Id(option.id)
                                                        }
                                                    }
                                                , contentAlignment = Alignment.CenterStart
                                            ){

                                                if (hasSelections == true) {
                                                    Box(
                                                        modifier = Modifier
                                                            .padding(top = 6.dp , end = 6.dp)
                                                            .size(10.dp)
                                                            .clip(CircleShape)
                                                            .background(newBlue)
                                                            .align(Alignment.TopEnd)
                                                    )
                                                }

                                                Text(
                                                    option.title,
                                                    color = if(selected_Filter_Id.value == option.id) newBlack else newGray,
                                                    fontSize = constants.textUnit(14),
                                                    fontFamily = constants.fontFamily(1)
                                                )
                                            }

                                        }
                                    }
                                }

                                Box(
                                    modifier = Modifier

                                        .height(1.dp)
                                        .fillMaxWidth()
                                        .background(newBlack)
                                )

                                Box(
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                        .fillMaxHeight()
                                        .width(1.dp)
                                        .background(newBlack)
                                )

                            }

                            Column(
                                modifier = Modifier
                                    .weight(6f)
                                    .padding(horizontal = 14.dp)

                            )
                            {
                                AnimatedContent (
                                    targetState = selected_Filter_Id.value
                                    , transitionSpec = {
                                        scaleIn() togetherWith scaleOut()

                                    }
                                )
                                {
                                        targetState ->

                                    when(targetState) {
                                        0 -> F_SortBy_SortOptions(option_List.value?.sort_by ?: emptyList() ,selected_SF)
                                        1 -> F_PropertyType_SortOptions(option_List.value?.property_type ?: emptyList(),selected_SF)
                                        2 -> F_Property_Area_SortOptions(selected_SF)
                                        3 -> F_Budget_SortOptions(selected_SF)
                                        4 -> F_PostedBy_SortOptions(option_List.value?.posted_by ?: emptyList(),selected_SF)
                                        5 -> F_FloorPlan_SortOptions(option_List.value?.floor_plan ?: emptyList(),selected_SF)

                                        7 -> F_FurnishingStatus_SortOptions(option_List.value?.furnishing_status ?: emptyList(),selected_SF)

                                        9 -> F_Parking_SortOptions(option_List.value?.parking_available ?: emptyList(),selected_SF)
                                        10 -> F_OpenSides_SortOptions(option_List.value?.open_sides ?: emptyList(),selected_SF)
                                        11 -> F_Floor_Preferences_SortOptions(option_List.value?.floor_preferences ?: emptyList(),selected_SF)

                                        13 -> F_Authority_Approved_SortOptions(option_List.value?.approved ?: emptyList(),selected_SF)
                                        14 -> F_Property_Facing_SortOptions(option_List.value?.property_facing ?: emptyList(),selected_SF)
                                        15 -> F_Amenities_SortOptions(option_List.value?.amenities ?: emptyList(),selected_SF)
                                        16 -> F_Property_Highights_SortOptions(option_List.value?.property_highlights ?: emptyList(),selected_SF)

                                        17 -> F_Rent_Type_SortOptions(option_List.value?.rent_type ?: emptyList(), selected_SF)
                                        18 -> F_Posted_Date_SortOptions(option_List.value?.posted_date ?: emptyList(), selected_SF)
                                        19 -> F_Available_From_SortOptions(option_List.value?.available_from ?: emptyList() , selected_SF)
                                        20 -> F_Available_For_SortOptions(option_List.value?.available_for ?: emptyList() , selected_SF)
                                        21 -> F_BedRooms_SortOptions(option_List.value?.bedrooms ?: emptyList() , selected_SF)
                                        22 -> F_FoodPreference_SortOptions(option_List.value?.food_preferences ?: emptyList() , selected_SF)
                                        23 -> F_Pet_Allowed_SortOptions(option_List.value?.pets_allowed ?: emptyList() , selected_SF)
                                        24 -> F_With_Photos_SortOptions( selected_SF)
                                        25 -> F_Agreement_Type_SortOptions(option_List.value?.agreement ?: emptyList() , selected_SF)

                                    }

                                }
                            }
                        }
                    }
                    3 -> {
                        Column(
                            modifier = Modifier

                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.padding(8.dp))
                            Image(painterResource(R.drawable.empty_drafts), "")
                            Spacer(modifier = Modifier.padding(8.dp))
                            Text("We couldn't find anything sort or filter!")
                            Spacer(modifier = Modifier.padding(8.dp))
                            Text(
                                "Try again after sometime else.",
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
        if (api_State == 2) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            {
                Static_Bottom(
                    modifier = Modifier
                        .fillMaxSize(), content = {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(newWhite),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(46.dp)
                                    .width(168.dp)
                                    .noRippleClickable{
                                        ClickHelper.getInstance().clickOnce {

                                            constants.Search_ViewModel.not_Apply_FS()
                                            constants.Search_ViewModel.clear_Selected_Fields_Form4()
                                            constants.Search_ViewModel.clearBackup()
                                            constants.API_Vm.isLoading_PS_FF = true
                                            constants.API_Vm.totalPages_PS_FF = 1
                                        }
                                    }
                                    .background(Color(0xffE8E8E8)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "Clear All",
                                    color = Color(0xff666666),
                                    fontSize = constants.textUnit(14)
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .height(46.dp)
                                    .width(168.dp)
                                    .background(newBlue)
                                    .noRippleClickable{
                                        ClickHelper.getInstance().clickOnce {
                                            if (filterCount != 0) {
                                                constants.Search_ViewModel.clear_Search_Results()

                                                constants.API_Vm.isLoading_PS_FF = true
                                                constants.API_Vm.totalPages_PS_FF = 1
                                                constants.Search_ViewModel.total_SearchResults_Counts.value = 0
                                                constants.Search_ViewModel.apply_FS()

                                                constants.Search_ViewModel.clearBackup()
                                                navController.navigateUp()
                                            } else {
                                                constants.Search_ViewModel.clear_Search_Results()

                                                constants.API_Vm.isLoading_PS_FF = true
                                                constants.API_Vm.totalPages_PS_FF = 1
                                                constants.Search_ViewModel.total_SearchResults_Counts.value = 0
                                                constants.Search_ViewModel.not_Apply_FS()
                                                navController.navigateUp()
                                            }

                                        }
                                    }, contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "Apply Filters ${ if (filterCount != 0 ) "(${filterCount})" else "" }",
                                    color = Color.White,
                                    fontSize = constants.textUnit(14)
                                )
                            }
                        }
                    }
                )
            }
        }
    }

    BackHandler {
        ClickHelper.getInstance().clickOnce {
            if (ClickGuard.canClick()) {
                constants.Search_ViewModel.restoreBackupFilterState()
                navController.navigateUp()
            }
        }
    }
}

@Composable
fun F_SortBy_SortOptions(
    options_List: List<String>,
    selected_SF: State<Sort_Filter_Field_DC?>
){

    Column {
        Text("Sort By"
            , color = newBlack
            , fontSize = constants.textUnit(18)
            , fontFamily = constants.fontFamily(0))

        Spacer(modifier = Modifier.padding(12.dp))

        options_List.forEachIndexed { index, s ->
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .then(if ((selected_SF.value?.short_by?:0) - 1 == index) selectedModifier() else unSelectedModifier())

                    .noRippleClickable{
                        ClickHelper.getInstance().clickOnce {
                            constants.Search_ViewModel.update_Search_SF {
                                it.copy(short_by = index + 1)
                            }
                        }
                    }

                    .padding(horizontal = 16.dp , vertical = 6.dp)
                , contentAlignment = Alignment.Center
            ){
                Text(s ?:"" , fontSize = constants.textUnit(14)
                    , color = newBlack)
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun F_FloorPlan_SortOptions(
    options_List: List<String>,
    selected_SF: State<Sort_Filter_Field_DC?>
){
    Column {
        Text("Floor Plan"
            , color = newBlack
            , fontSize = constants.textUnit(18)
            , fontFamily = constants.fontFamily(0))

        Spacer(modifier = Modifier.padding(12.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        )
        {
            options_List.forEach { option ->

                val isSelected = selected_SF.value?.floor_plan?.contains(option) == true

                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .then(if (isSelected) selectedModifier() else unSelectedModifier())

                        .noRippleClickable{
                            ClickHelper.getInstance().clickOnce {
                                constants.Search_ViewModel.toggle_String_Field("floor_plan", option)
                            }
                        }

                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        option,
                        fontSize = constants.textUnit(14),
                        color =  newBlack
                    )
                }
            }
        }
    }
}

@Composable
fun F_PropertyType_SortOptions(
    options_List: List<PropertyType>,
    selected_SF: State<Sort_Filter_Field_DC?>
){
    Column {
        Text("What type of property you are looking for?"
            , color = newBlack
            , fontSize = constants.textUnit(18)
            , fontFamily = constants.fontFamily(0))

        Spacer(modifier = Modifier.padding(12.dp))

        options_List.forEach { propertyType ->
            val isSelected = selected_SF.value?.land_categorie_id?.contains(propertyType.id) == true

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .then(if (isSelected) selectedModifier() else unSelectedModifier())

                    .noRippleClickable{
                        ClickHelper.getInstance().clickOnce {
                            propertyType.id?.let {
                                constants.Search_ViewModel.toggle_Int_Field("land_categorie_id", it)
                            }
                        }
                    }

                    .padding(horizontal = 16.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    propertyType.name ?: "",
                    fontSize = constants.textUnit(14),
                    color = newBlack
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun F_PostedBy_SortOptions(
    options_List: List<PostedBy>,
    selected_SF: State<Sort_Filter_Field_DC?>
){
    Column {
        Text("Posted by"
            , color = newBlack
            , fontSize = constants.textUnit(18)
            , fontFamily = constants.fontFamily(0))

        Spacer(modifier = Modifier.padding(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            options_List.forEach { postedBy ->
                val isSelected = selected_SF.value?.posted_by?.contains(postedBy.id) == true

                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .then(if (isSelected) selectedModifier() else unSelectedModifier())

                        .noRippleClickable{
                            ClickHelper.getInstance().clickOnce {
                                postedBy.id?.let {
                                    constants.Search_ViewModel.toggle_Int_Field("posted_by", it)
                                }
                            }
                        }

                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        postedBy.posted_by ?: "",
                        fontSize = constants.textUnit(14),
                        color = newBlack
                    )
                }
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetInputField(
    label: String,
    value: String,
    expanded : MutableState<Boolean>,
    onValueChange: (String) -> Unit,
    selected_SF: State<Sort_Filter_Field_DC?>
) {

    val unitsList = listOf("sq.ft" , "m" , "feet" , "hectar" )

    val selectedUnit = selected_SF.value?.property_area_unit

    Column(
        modifier = Modifier
            .wrapContentSize()
    ) {
        Text(
            text = label,
            modifier = Modifier,
            fontSize = constants.textUnit(12)
        )

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, newGray, RoundedCornerShape(8.dp))
        ) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(fontSize = constants.textUnit(12)),
                readOnly = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor =Color.Transparent ,
                    unfocusedIndicatorColor = Color.Transparent,
                    unfocusedTextColor = newBlack,
                    focusedTextColor = newBlack
                ),
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(.6f)

            )

            ExposedDropdownMenuBox(
                expanded = expanded.value,
                onExpandedChange = { expanded.value = !expanded.value }
                , modifier = Modifier
                    .fillMaxHeight()
                    .weight(.4f)
            )
            {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .background(Color(0xffEBEBEB))
                        .menuAnchor()
                        .clip(RoundedCornerShape(4.dp))
                        .border(1.dp, newGray, RoundedCornerShape(4.dp))
                        .padding(horizontal = 4.dp, vertical = 6.dp)
                    , contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        selectedUnit?.ifEmpty { "sq.ft" }?.let {
                            Text(
                                it,
                                modifier = Modifier.padding(end = 8.dp),  fontSize = constants.textUnit(14),
                                fontFamily = constants.fontFamily(1),
                                color = Color.Black
                            )
                        }
                        Icon(painter = painterResource(R.drawable.arrowdown), contentDescription = "")
                    }
                }

                ExposedDropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false }
                    , containerColor = newWhite
                )
                {
                    unitsList.forEachIndexed { index , option ->
                        DropdownMenuItem(
                            text = { Text(option ,  fontSize = constants.textUnit(14),
                                fontFamily = constants.fontFamily(1),
                                color = Color.Black)
                            },
                            onClick = {
                                ClickHelper.getInstance().clickOnce {
                                    constants.Search_ViewModel.set_String_Field("property_area_unit", option)
                                    expanded.value = false
                                }

                            }
                        )
                    }
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetSelectorSF(
    minBudget: MutableState<Long>,
    maxBudget: MutableState<Long>
) {

    val MIN_LIMIT = 2000L
    val MAX_LIMIT = 1_000_0000L

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
        var cur = 1000L
        while (cur <= MAX_LIMIT) {
            list.add(cur)
            cur += when (cur) {
                in 1000..10_000 -> 2000
                in 10_000..50_000 -> 5000
                in 50_000..100_000 -> 10_000
                in 100_000..5_000_000 -> 50_000
                else -> 100_000
            }
        }
        list
    }

    Column {

            ExposedDropdownMenuBox(
                expanded = minExpanded.value,
                onExpandedChange = { }
            )
            {
                Column {
                    Text("Min", color = Color.Black, fontSize = 14.sp)

                    constants.spacer(4)

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

        constants.spacer(8)

            ExposedDropdownMenuBox(
                expanded = maxExpanded.value,
                onExpandedChange = { }
            )
            {
                Column {
                    Text("Max", color = Color.Black, fontSize = 14.sp)

                    constants.spacer(4)

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

        AnimatedVisibility(visible = isError) {
            Text("Max should be >= Min", color = Color.Red)
        }
    }
}

@Composable
fun F_Budget_SortOptions(selected_SF: State<Sort_Filter_Field_DC?>) {
    val staticValueRange = 1_000f..10_000_000f

    val minRangeFromVM by constants.Search_ViewModel.minRangeRefs
    val maxRangeFromVM by constants.Search_ViewModel.maxRangeRefs

    var minInput by remember { mutableStateOf(minRangeFromVM) }
    var maxInput by remember { mutableStateOf(maxRangeFromVM) }

    val minValue = minInput.toFloat() ?: staticValueRange.start
    val maxValue = maxInput.toFloat() ?: staticValueRange.endInclusive

    var selectedRange by remember(minValue, maxValue) {
        mutableStateOf(minValue..maxValue)
    }

    var expanded1 = remember { mutableStateOf(false) }
    var expanded2 = remember { mutableStateOf(false) }

    Column {
        Text(
            "Budget (₹)",
            color = newBlack,
            fontSize = constants.textUnit(18),
            fontFamily = constants.fontFamily(0)
        )

        BudgetSelectorSF(
            minBudget = remember { mutableStateOf(2000L) },
            maxBudget = remember { mutableStateOf(1_000_0000L) }
        )

        constants.spacer(4)

        Spacer(Modifier.height(16.dp))

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun F_Property_Area_SortOptions(selected_SF: State<Sort_Filter_Field_DC?>) {

    val staticValueRange = 1f..10000f

    var selectedRange by remember(selected_SF.value) {
        val start = selected_SF.value?.property_area_from?.toFloatOrNull() ?: staticValueRange.start
        val end = selected_SF.value?.property_area_to?.toFloatOrNull() ?: staticValueRange.endInclusive
        mutableStateOf(start..end)
    }

    var minInput by remember { mutableStateOf(selectedRange.start.toInt().toString()) }
    var maxInput by remember { mutableStateOf(selectedRange.endInclusive.toInt().toString()) }

    val units_of_Measurements = remember { listOf("sq", "m", "hectar", "cm") }
    var expanded by remember { mutableStateOf(false) }
    var selectedUnit = remember { mutableStateOf(selected_SF.value?.property_area_unit?.ifEmpty { "sq" }) }

    var expanded1 = remember { mutableStateOf(false) }
    var expanded2 = remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Property Area",
                color = newBlack,
                fontSize = constants.textUnit(18),
                fontFamily = constants.fontFamily(0),
                modifier = Modifier.weight(1f)
            )

        }

            BudgetInputField(
                label = "Min",
                value = minInput,
                onValueChange = {
                    minInput = it
                    val newMin = it.toFloatOrNull()
                    if (newMin != null && newMin <= selectedRange.endInclusive) {
                        selectedRange = newMin..selectedRange.endInclusive
                        constants.Search_ViewModel.update_Search_SF {
                            it.copy(property_area_from = minInput)
                        }
                    }
                },
                expanded = expanded1,
                selected_SF = selected_SF
            )

        constants.spacer(4)

            BudgetInputField(
                label = "Max",
                value = maxInput,
                onValueChange = {
                    maxInput = it
                    val newMax = it.toFloatOrNull()
                    if (newMax != null && newMax >= selectedRange.start) {
                        selectedRange = selectedRange.start..newMax
                        constants.Search_ViewModel.update_Search_SF {
                            it.copy(property_area_to = maxInput)
                        }
                    }
                },
                expanded = expanded1,
                selected_SF = selected_SF
            )

        Spacer(Modifier.height(16.dp))

        val minGap = 5f

        RangeSlider(
            value = selectedRange,
            onValueChange = { newRange ->
                val start = newRange.start.coerceAtLeast(1000f)
                val end = newRange.endInclusive.coerceAtMost(100000f)

                if (end - start >= minGap) {
                    selectedRange = start..end
                    minInput = start.toInt().toString()
                    maxInput = end.toInt().toString()

                    constants.Search_ViewModel.update_Search_SF {
                        it.copy(
                            property_area_from = minInput,
                            property_area_to = maxInput
                        )
                    }
                }
            },
            valueRange = 1000f..100000f,
            steps = 98,
            modifier = Modifier
                .height(260.dp)
                .rotate(-90f),
            colors = SliderDefaults.colors(
                thumbColor = Color.Black,
                activeTrackColor = Color.Black,
                inactiveTrackColor = newLightGray,
                activeTickColor = Color.White,
                inactiveTickColor = Color.Black
            )
        )

    }
}

@Composable
fun F_FurnishingStatus_SortOptions(
    options_List: List<String>,
    selected_SF: State<Sort_Filter_Field_DC?>
){
    Column {
        Text("Furnishing Status"
            , color = newBlack
            , fontSize = constants.textUnit(18)
            , fontFamily = constants.fontFamily(0))
        Spacer(modifier = Modifier.padding(12.dp))

        options_List.forEach { option ->
            val isSelected = selected_SF.value?.furnishing_status?.contains(option) == true

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .then(if (isSelected) selectedModifier() else unSelectedModifier())

                    .noRippleClickable{
                        ClickHelper.getInstance().clickOnce {
                            constants.Search_ViewModel.toggle_String_Field(
                                "furnishing_status",
                                option
                            )
                        }
                    }

                    .padding(horizontal = 16.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    option,
                    fontSize = constants.textUnit(14),
                    color = newBlack
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun F_Ownership_SortOptions(
    options_List: List<String>,
    selected_SF: State<Sort_Filter_Field_DC?>
){
    Column {
        Text("Ownership", color = newBlack , fontSize = constants.textUnit(18) , fontFamily = constants.fontFamily(2))
        Spacer(modifier = Modifier.padding(12.dp))

        options_List.forEach { option ->
            val isSelected = selected_SF.value?.ownership?.contains(option) == true

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .then(if (isSelected) selectedModifier() else unSelectedModifier())

                    .noRippleClickable{
                        ClickHelper.getInstance().clickOnce {
                            constants.Search_ViewModel.toggle_String_Field("ownership", option)
                        }
                    }

                    .padding(horizontal = 16.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    option,
                    fontSize = constants.textUnit(14),
                    color = newBlack
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun F_Parking_SortOptions(
    options_List: List<String>,
    selected_SF: State<Sort_Filter_Field_DC?>
){
    Column {
        Text("Parking Available"
            , color = newBlack
            , fontSize = constants.textUnit(18)
            , fontFamily = constants.fontFamily(0))
        Spacer(modifier = Modifier.padding(12.dp))

        options_List.forEach { option ->

            val isSelected = selected_SF.value?.parking_available?.contains(option) == true

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .then(if (isSelected) selectedModifier() else unSelectedModifier())

                    .noRippleClickable{
                        ClickHelper.getInstance().clickOnce {
                            constants.Search_ViewModel.toggle_String_Field(
                                "parking_available",
                                option
                            )
                        }
                    }

                    .padding(horizontal = 16.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    "$option",
                    fontSize = constants.textUnit(14),
                    color =  newBlack
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))

        }
    }
}

@Composable
fun F_OpenSides_SortOptions(
    options_List: List<String>,
    selected_SF: State<Sort_Filter_Field_DC?>
){
    Column {
        Text("No. of Open Sides"
            , color = newBlack
            , fontSize = constants.textUnit(18)
            , fontFamily = constants.fontFamily(0))
        Spacer(modifier = Modifier.padding(12.dp))

        options_List.forEach { value ->
            val valueStr = value.toString()
            val isSelected = selected_SF.value?.no_of_open_sides?.contains(valueStr) == true

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .then(if (isSelected) selectedModifier() else unSelectedModifier())

                    .noRippleClickable{
                        ClickHelper.getInstance().clickOnce {
                            constants.Search_ViewModel.toggle_String_Field(
                                "no_of_open_sides",
                                valueStr
                            )
                        }
                    }

                    .padding(horizontal = 16.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    "$value",
                    fontSize = constants.textUnit(14),
                    color =  newBlack
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun F_Floor_Preferences_SortOptions(
    options_List: List<FloorPreference>,
    selected_SF: State<Sort_Filter_Field_DC?>
){
    Column {
        Text("Floor Preferences"
            , color = newBlack
            , fontSize = constants.textUnit(18)
            , fontFamily = constants.fontFamily(0))
        Spacer(modifier = Modifier.padding(12.dp))

        options_List.forEach { floorPref ->
            val isSelected = selected_SF.value?.floor_preferences?.contains(floorPref.id) == true

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .then(if (isSelected) selectedModifier() else unSelectedModifier())

                    .noRippleClickable{
                        ClickHelper.getInstance().clickOnce {
                            floorPref.id?.let {
                                constants.Search_ViewModel.toggle_Int_Field("floor_preferences", it)
                            }
                        }
                    }

                    .padding(horizontal = 16.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    floorPref.floor_preferences ?: "",
                    fontSize = constants.textUnit(14),
                    color =  newBlack
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun F_Business_Types_SortOptions(
    options_List: List<String>,
    selected_SF: State<Sort_Filter_Field_DC?>
){
    Column {
        Text("Business Type", color = newBlack , fontSize = constants.textUnit(18) , fontFamily = constants.fontFamily(2))
        Spacer(modifier = Modifier.padding(12.dp))

        options_List.forEach { option ->
            val isSelected = selected_SF.value?.business_type?.contains(option) == true

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .then(if (isSelected) selectedModifier() else unSelectedModifier())

                    .noRippleClickable{
                        ClickHelper.getInstance().clickOnce {
                            constants.Search_ViewModel.toggle_String_Field("business_type", option)
                        }
                    }

                    .padding(horizontal = 16.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    option,
                    fontSize = constants.textUnit(14),
                    color = newBlack
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun F_Authority_Approved_SortOptions(
    options_List: List<String>,
    selected_SF: State<Sort_Filter_Field_DC?>
){
    Column {
        Text("Authority Approved"
            , color = newBlack
            , fontSize = constants.textUnit(18)
            , fontFamily = constants.fontFamily(0))
        Spacer(modifier = Modifier.padding(12.dp))

        options_List.forEach { option ->
            val isSelected = selected_SF.value?.approved?.contains(option) == true

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .then(if (isSelected) selectedModifier() else unSelectedModifier())

                    .noRippleClickable{
                        ClickHelper.getInstance().clickOnce {
                            constants.Search_ViewModel.toggle_String_Field(
                                "authority_approved",
                                option
                            )
                        }
                    }

                    .padding(horizontal = 16.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    option,
                    fontSize = constants.textUnit(14),
                    color =  newBlack
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun F_Property_Facing_SortOptions(
    options_List: List<String>,
    selected_SF: State<Sort_Filter_Field_DC?>
){
    Column {
        Text("Property Facing"
            , color = newBlack
            , fontSize = constants.textUnit(18)
            , fontFamily = constants.fontFamily(0))
        Spacer(modifier = Modifier.padding(12.dp))

        options_List.forEach { option ->
            val isSelected = selected_SF.value?.property_facing?.contains(option) == true

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .then(if (isSelected) selectedModifier() else unSelectedModifier())

                    .noRippleClickable{
                        ClickHelper.getInstance().clickOnce {
                            constants.Search_ViewModel.toggle_String_Field(
                                "property_facing",
                                option
                            )
                        }
                    }

                    .padding(horizontal = 16.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    option,
                    fontSize = constants.textUnit(14),
                    color =  newBlack
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun F_Amenities_SortOptions(
    options_List: List<String>,
    selected_SF: State<Sort_Filter_Field_DC?>
){
    Column {
        Text("Amenities"
            , color = newBlack
            , fontSize = constants.textUnit(18)
            , fontFamily = constants.fontFamily(0))
        Spacer(modifier = Modifier.padding(12.dp))

        LazyColumn {
            item {
                options_List.forEach { option ->
                    val isSelected = selected_SF.value?.amenities?.contains(option) == true

                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .then(if (isSelected) selectedModifier() else unSelectedModifier())

                            .noRippleClickable{
                                ClickHelper.getInstance().clickOnce {
                                    constants.Search_ViewModel.toggle_String_Field(
                                        "amenities",
                                        option
                                    )
                                }
                            }

                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            option,
                            fontSize = constants.textUnit(14),
                            color = newBlack
                        )
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}

@Composable
fun F_Property_Highights_SortOptions(
    options_List: List<String>,
    selected_SF: State<Sort_Filter_Field_DC?>
){
    Column {
        Text("Property Highlights"
            , color = newBlack
            , fontSize = constants.textUnit(18)
            , fontFamily = constants.fontFamily(0))
        Spacer(modifier = Modifier.padding(12.dp))

        options_List.forEach { option ->
            val isSelected = selected_SF.value?.property_highlights?.contains(option) == true

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .then(if (isSelected) selectedModifier() else unSelectedModifier())

                    .noRippleClickable{
                        constants.Search_ViewModel.toggle_String_Field("property_highlights", option)
                    }

                    .padding(horizontal = 16.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    option,
                    fontSize = constants.textUnit(14),
                    color = newBlack
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun F_Rent_Type_SortOptions(
    options_List: List<String>,
    selected_SF: State<Sort_Filter_Field_DC?>
){
    Column {
        Text("Rent Type"
            , color = newBlack
            , fontSize = constants.textUnit(18)
            , fontFamily = constants.fontFamily(0))
        Spacer(modifier = Modifier.padding(12.dp))

        options_List.forEach { option ->
            val isSelected = selected_SF.value?.rent_type?.contains(option) == true

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .then(if (isSelected) selectedModifier() else unSelectedModifier())

                    .noRippleClickable{
                        constants.Search_ViewModel.toggle_String_Field("rent_type", option)
                    }

                    .padding(horizontal = 16.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    option,
                    fontSize = constants.textUnit(14),
                    color = newBlack
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun F_Posted_Date_SortOptions(
    options_List: List<PostedDate>,
    selected_SF: State<Sort_Filter_Field_DC?>
){
    Column {
        Text("Posted Date"
            , color = newBlack
            , fontSize = constants.textUnit(18)
            , fontFamily = constants.fontFamily(0))
        Spacer(modifier = Modifier.padding(12.dp))

        options_List.forEach { option ->
            val isSelected = selected_SF.value?.posted_date?.contains(option.id) == true

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .then(if (isSelected) selectedModifier() else unSelectedModifier())

                    .noRippleClickable{
                        constants.Search_ViewModel.toggle_Int_Field("posted_date", option.id)
                    }

                    .padding(horizontal = 16.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    option.floor_preferences,
                    fontSize = constants.textUnit(14),
                    color = newBlack
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun F_Available_From_SortOptions(
    options_List: List<AvailableFrom>,
    selected_SF: State<Sort_Filter_Field_DC?>
){
    Column {
        Text("Available From"
            , color = newBlack
            , fontSize = constants.textUnit(18)
            , fontFamily = constants.fontFamily(0))
        Spacer(modifier = Modifier.padding(12.dp))

        options_List.forEach { option ->
            val isSelected = selected_SF.value?.available_from?.contains(option.id) == true

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .then(if (isSelected) selectedModifier() else unSelectedModifier())

                    .noRippleClickable{
                        constants.Search_ViewModel.toggle_Int_Field("available_from", option.id)
                    }

                    .padding(horizontal = 16.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    option?.available_from ?:"",
                    fontSize = constants.textUnit(14),
                    color = newBlack
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun F_Available_For_SortOptions(
    options_List: List<String>,
    selected_SF: State<Sort_Filter_Field_DC?>
){
    Column {
        Text("Available For"
            , color = newBlack
            , fontSize = constants.textUnit(18)
            , fontFamily = constants.fontFamily(0))
        Spacer(modifier = Modifier.padding(12.dp))

        options_List.forEach { option ->
            val isSelected = selected_SF.value?.available_for?.contains(option) == true

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .then(if (isSelected) selectedModifier() else unSelectedModifier())

                    .noRippleClickable{
                        constants.Search_ViewModel.toggle_String_Field("available_for", option)
                    }

                    .padding(horizontal = 16.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    option,
                    fontSize = constants.textUnit(14),
                    color = newBlack
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun F_BedRooms_SortOptions(
    options_List: List<String>,
    selected_SF: State<Sort_Filter_Field_DC?>
){
    Column {
        Text("BedRooms"
            , color = newBlack
            , fontSize = constants.textUnit(18)
            , fontFamily = constants.fontFamily(0))
        Spacer(modifier = Modifier.padding(12.dp))

        options_List.forEach { option ->
            val isSelected = selected_SF.value?.bedrooms?.contains(option) == true

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .then(if (isSelected) selectedModifier() else unSelectedModifier())

                    .noRippleClickable{
                        constants.Search_ViewModel.toggle_String_Field("bedrooms", option)
                    }

                    .padding(horizontal = 16.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    option,
                    fontSize = constants.textUnit(14),
                    color = newBlack
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun F_FoodPreference_SortOptions(
    options_List: List<String>,
    selected_SF: State<Sort_Filter_Field_DC?>
){
    Column {
        Text("Food Preference"
            , color = newBlack
            , fontSize = constants.textUnit(18)
            , fontFamily = constants.fontFamily(0))
        Spacer(modifier = Modifier.padding(12.dp))

        options_List.forEach { option ->
            val isSelected = selected_SF.value?.food_preference?.contains(option) == true

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .then(if (isSelected) selectedModifier() else unSelectedModifier())

                    .noRippleClickable{
                        constants.Search_ViewModel.toggle_String_Field("food_preference", option)
                    }

                    .padding(horizontal = 16.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    option,
                    fontSize = constants.textUnit(14),
                    color = newBlack
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun F_Pet_Allowed_SortOptions(
    options_List: List<String>,
    selected_SF: State<Sort_Filter_Field_DC?>
){
    Column {
        Text("Pet Allowed"
            , color = newBlack
            , fontSize = constants.textUnit(18)
            , fontFamily = constants.fontFamily(0))
        Spacer(modifier = Modifier.padding(12.dp))

        options_List.forEach { option ->
            val isSelected = selected_SF.value?.pets_allowed?.contains(option) == true

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .then(if (isSelected) selectedModifier() else unSelectedModifier())

                    .noRippleClickable{
                        constants.Search_ViewModel.toggle_String_Field("pets_allowed", option)
                    }

                    .padding(horizontal = 16.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    option,
                    fontSize = constants.textUnit(14),
                    color = newBlack
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun F_With_Photos_SortOptions(
    selected_SF: State<Sort_Filter_Field_DC?>
) {
    Column {
        Text(
            "With Photos",
            color = newBlack,
            fontSize = constants.textUnit(18),
            fontFamily = constants.fontFamily(0)
        )
        Spacer(modifier = Modifier.height(12.dp))

        val isSelected = selected_SF.value?.with_photos == "1"

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            CommonText(
                "Show Properties with Photo",
                newBlack,
                16,
                0,
                modifier = Modifier.weight(0.5f)
            )

            Switch(
                checked = isSelected,
                onCheckedChange = { checked ->

                    val newValue = if (checked) "1" else "0"

                    constants.Search_ViewModel.set_String_Field("with_photos", newValue)

                },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = Color(0xffE8E9E9),
                    uncheckedTrackColor = Color(0xffE8E9E9),
                    checkedThumbColor = newBlue,
                    uncheckedThumbColor = Color.White,
                    uncheckedBorderColor = Color.Transparent
                ),
                modifier = Modifier.weight(0.5f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun F_Agreement_Type_SortOptions(
    options_List: List<String>,
    selected_SF: State<Sort_Filter_Field_DC?>
){
    Column {
        Text("Agreement Type"
            , color = newBlack
            , fontSize = constants.textUnit(18)
            , fontFamily = constants.fontFamily(0))
        Spacer(modifier = Modifier.padding(12.dp))

        options_List.forEach { option ->
            val isSelected = selected_SF.value?.agreement_type?.contains(option) == true

            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .then(if (isSelected) selectedModifier() else unSelectedModifier())

                    .noRippleClickable{
                        constants.Search_ViewModel.toggle_String_Field("agreement_type", option)
                    }

                    .padding(horizontal = 16.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    option,
                    fontSize = constants.textUnit(14),
                    color = newBlack
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}
