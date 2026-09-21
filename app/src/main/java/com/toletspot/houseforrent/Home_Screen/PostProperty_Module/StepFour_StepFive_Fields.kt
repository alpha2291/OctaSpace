package com.toletspot.houseforrent.Home_Screen.PostProperty_Module

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toletspot.houseforrent.CommonText
import com.toletspot.houseforrent.Custom_Assets.Static_Bottom
import com.toletspot.houseforrent.Custom_Assets.noRippleClickableWithScale
import com.toletspot.houseforrent.R
import com.toletspot.houseforrent.constants
import com.toletspot.houseforrent.noRippleClickable
import com.toletspot.houseforrent.ui.theme.newBlack
import com.toletspot.houseforrent.ui.theme.newBlue
import com.toletspot.houseforrent.ui.theme.newGray
import com.toletspot.houseforrent.ui.theme.newPurpleGradient
import com.toletspot.houseforrent.ui.theme.newPurpleGradientBorder
import com.toletspot.houseforrent.ui.theme.newWhite
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


@Composable
fun trao(){

    val floorPlans = listOf(
        Chips_Items_DC("1 BHK"),
        Chips_Items_DC("2 BHK"),
        Chips_Items_DC("3 BHK"),
        Chips_Items_DC("4 BHK"),
        Chips_Items_DC("Studio"),
    )

    val facingOptions = listOf(
        Chips_Items_DC("North"),
        Chips_Items_DC("South"),
        Chips_Items_DC("East"),
        Chips_Items_DC("West"),
        Chips_Items_DC("North-East"),
        Chips_Items_DC("North-West"),
        Chips_Items_DC("South-East"),
        Chips_Items_DC("South-West"),
    )

    val ownershipOptions = listOf(
        Chips_Items_DC("Freehold"),
        Chips_Items_DC("Leasehold"),
        Chips_Items_DC("Power of Attorney"),
    )

    val availabilityStatus = listOf(
        Chips_Items_DC("Ready to Move"),
        Chips_Items_DC("Under Construction"),
    )

    val bedroomOptions = (1..6).map { Chips_Items_DC("$it") }

    val bathroomOptions = (1..4).map { Chips_Items_DC("$it") }

    val balconyOptions = (0..3).map { Chips_Items_DC("$it") }

    val otherRooms = listOf(
        Chips_Items_DC("Study Room"),
        Chips_Items_DC("Servant Room"),
        Chips_Items_DC("Pooja Room"),
        Chips_Items_DC("Store Room"),
    )

    val furnishingStatus = listOf(
        Chips_Items_DC("Furnished"),
        Chips_Items_DC("Semi-Furnished"),
        Chips_Items_DC("Unfurnished"),
    )

    val amenities = listOf(
        Chips_Items_DC("Gym"),
        Chips_Items_DC("Swimming Pool"),
        Chips_Items_DC("Lift"),
        Chips_Items_DC("Power Backup"),
        Chips_Items_DC("Security"),
        Chips_Items_DC("Parking"),
        Chips_Items_DC("Club House"),
        Chips_Items_DC("Play Area"),
    )

    val highlights = listOf(
        Chips_Items_DC("Close to Metro"),
        Chips_Items_DC("Near Park"),
        Chips_Items_DC("Corner Property"),
        Chips_Items_DC("High Floor"),
        Chips_Items_DC("East Facing"),
        Chips_Items_DC("Gated Society"),
    )
    val cabins = (1..10).map { Chips_Items_DC("$it") }

    val meetingRooms = (1..5).map { Chips_Items_DC("$it") }

    val minSeats = listOf(
        Chips_Items_DC("5"),
        Chips_Items_DC("10"),
        Chips_Items_DC("20"),
        Chips_Items_DC("30"),
        Chips_Items_DC("50"),
    )

    val maxSeats = listOf(
        Chips_Items_DC("50"),
        Chips_Items_DC("100"),
        Chips_Items_DC("200"),
        Chips_Items_DC("300"),
        Chips_Items_DC("500+"),
    )

    val conferenceRooms = (1..3).map { Chips_Items_DC("$it") }

    val staircases = (1..4).map { Chips_Items_DC("$it ") }

    val receptionArea = listOf(
        Chips_Items_DC("Available"),
        Chips_Items_DC("Not Available"),
    )

    val pantryOptions = listOf(
        Chips_Items_DC("Dry Pantry"),
        Chips_Items_DC("Wet Pantry"),
        Chips_Items_DC("Not Available"),
    )

    val centralACOptions = listOf(
        Chips_Items_DC("Yes"),
        Chips_Items_DC("No"),
    )

    val oxygenDuctOptions = listOf(
        Chips_Items_DC("Available"),
        Chips_Items_DC("Not Available"),
    )

    val upsOptions = listOf(
        Chips_Items_DC("Available"),
        Chips_Items_DC("Not Available"),
    )

    val fireSafetyMeasures = listOf(
        Chips_Items_DC("Fire Extinguishers"),
        Chips_Items_DC("Sprinklers"),
        Chips_Items_DC("Smoke Detectors"),
        Chips_Items_DC("Fire Exit"),
    )

    val lifts = listOf(
        Chips_Items_DC("1"),
        Chips_Items_DC("2"),
        Chips_Items_DC("3"),
        Chips_Items_DC("4+"),
    )

    val previouslyUsedFor = listOf(
        Chips_Items_DC("IT Office"),
        Chips_Items_DC("Call Center"),
        Chips_Items_DC("Bank"),
        Chips_Items_DC("Co-working"),
        Chips_Items_DC("Corporate Office"),
    )

    val washroomDetails = listOf(
        Chips_Items_DC("Attached Washroom"),
        Chips_Items_DC("Shared Washroom"),
        Chips_Items_DC("Separate for Men & Women"),
    )


    val openSides = (1..4).map { Chips_Items_DC("$it") }

    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {

           // PP_AgreementType(previouslyUsedFor)
            PP_Preferred_Tenants(previouslyUsedFor)
            //PP_Available_From()
            PP_DepositAmount(previouslyUsedFor)
            //PP_Rent()
           // PP_LeaseAmount()
//            PP_Lease_Duration(previouslyUsedFor)
            PP_Lock_in_Period(previouslyUsedFor)
            PP_Notice_Period(previouslyUsedFor)
            //PP_Carpet_Built_SuperBuilt_Area()
           // PP_Property_Name()
//            PP_Select_Floor_Plan(data = floorPlans)
//            PP_Area_Dimensions()
//            PP_Property_Facing(data = facingOptions)
//            PP_Floor_Details()
//            PP_Property_Ownership(data = ownershipOptions)
//            PP_Availability_Status(data = availabilityStatus)
            PP_No_Of_Bedrooms(data = bedroomOptions)
//            PP_No_Of_Bathrooms(data = bathroomOptions)
//            PP_No_Of_Balconies(data = balconyOptions)
            PP_Other_Rooms(data = otherRooms)
//            PP_Furnishing_Status(data = furnishingStatus)
//            PP_parking_available()
//            PP_Amenities(data = amenities)
            PP_Property_Highlights(data = highlights)
//            PP_Land_Area()
            PP_Land_Open_Sides(openSides)
            PP_Has_Boundary_Wall()

            PP_No_of_Cabins(data = cabins)
            PP_No_of_Meeting_rooms(data = meetingRooms)
            PP_Min_No_of_Seats(data = minSeats)
            PP_Max_No_of_Seats(data = maxSeats)
            PP_Conference_Room(data = conferenceRooms)
            PP_No_of_Staircases(data = staircases)
            PP_Reception_Area(data = receptionArea)
            PP_Pantry(data = pantryOptions)
            PP_Central_AC(data = centralACOptions)
            PP_Oxygen_Duct(data = oxygenDuctOptions)
            PP_UPS(data = upsOptions)
            PP_Fire_Safety_Measures(data = fireSafetyMeasures)
            PP_Lifts(data = lifts)
            //PP_Office_Previously_Used_for(data = previouslyUsedFor)
            PP_Washroom_Details(data = washroomDetails)


//            PP_Shop_facade()
            PP_Pantry_Size()
            //PP_Is_Pre_leased_Pre_Rented()
            PP_Is_your_office_fire_NOC_Certified()
            PP_Occupancy_Certificate()
            PP_authority_property_Approved(data = listOf(Chips_Items_DC("Local") ))




        }
    }

}



data class Chips_Items_DC(
    var title : String,
    var isSelected: MutableState<Boolean> = mutableStateOf(false)
)

@Composable
fun PP_Property_Name(is_Error: Boolean) {
    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val propertyName = flowData.value?.property_Name ?: ""

    Column {
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = newBlack)) {
                    append("Property Name")
                }
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append("*")
                }
            },
            fontSize = constants.textUnit(16),
            fontFamily = constants.fontFamily(1)
        )

        Spacer(modifier = Modifier.padding(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, if (is_Error) Color.Red else newGray, RoundedCornerShape(8.dp))
        ) {
            TextField(
                value = propertyName,
                onValueChange = { newValue ->
                    if (newValue.length <= 20) {
                        val filteredValue = newValue.filter { it.isLetterOrDigit() }
                        constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                            it.copy(property_Name = filteredValue)
                        }
                    }
                },
                placeholder = {
                    Text("Enter Property / Project Name")
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = newBlack,
                    unfocusedTextColor = newBlack
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
        if (is_Error) {
            Text("Enter property name", color = Color.Red, fontSize = constants.textUnit(12))
        }
    }
}

fun backGroundBrush() : Brush {
    return Brush.verticalGradient(listOf(Color(0xffF7F0DC) , Color(0xffF7F0DC)))
}

fun defaultBackGroundBrush(): Brush {
    return Brush.verticalGradient(listOf(Color.White , Color.White))
}

fun errorBorderBrush() : Brush {
    return Brush.verticalGradient(listOf(Color.Red , Color.Red.copy(.5f)))
}

fun defaultBorderBrush(): Brush {
    return Brush.linearGradient(listOf(Color(0xffCECECE) , Color(0xffCECECE)))
}

fun borderBrush() : Brush {
   return Brush.linearGradient(newPurpleGradientBorder)
}

@Composable
fun PP_Select_Floor_Plan(
    data: List<Chips_Items_DC>,
    isError: Boolean
) {
    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val selectedPlan = flowData.value?.property_Floor_Plan_Bhk

    Column {
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = newBlack)) { append("Select Floor Plan") }
                withStyle(style = SpanStyle(color = Color.Red)) { append("*") }
            },
            fontSize = constants.textUnit(16),
            fontFamily = constants.fontFamily(1)
        )

        Spacer(modifier = Modifier.padding(4.dp))

        FlowRow(modifier = Modifier.fillMaxWidth()) {
            data.forEach { item ->
                val isSelected = selectedPlan == item.title

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .wrapContentSize()
                        .background(
                            if (isSelected) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                            if (isError) errorBorderBrush() else if (isSelected) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .noRippleClickable{
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_Floor_Plan_Bhk = item.title)
                            }
                        }
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(item.title, color = newBlack ,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2))
                }
            }
        }

        if (isError) {
            Text("Select a floor plan", color = Color.Red, fontSize = constants.textUnit(12))
        }
    }
}


@Composable
fun PP_Carpet_Built_SuperBuilt_Area(isError: Boolean) {

    val areaMeasurements = constants.AREA_UNITS
        //listOf("sq ft / ft²", "m²", "cm²", "sq yd")

    /// carpet are

    var expanded by remember { mutableStateOf(false) }
    var value by remember { mutableStateOf("") }



    val add_builtup = remember { mutableStateOf(false) }
    val add_Super_builtup = remember { mutableStateOf(false) }


    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    var formStateCarper = remember { mutableStateOf(flowData.value?.property_Carpet_Area) }

//    var selectedDimension by remember { mutableStateOf(areaMeasurements.first()) }
    var selectedDimension by remember { mutableStateOf(flowData.value?.carpet_area_unit) }


    var focusManager = LocalFocusManager.current
    var keyboardController = LocalSoftwareKeyboardController.current



    Column {

        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = newBlack)){
                    append( "Carpet Area")
                }
                withStyle(style = SpanStyle(color = Color.Red)){
                    append("*")
                }
            }
            , fontSize = constants.textUnit(16)
            , fontFamily = constants.fontFamily(1)
        )
//        Text("Carpet Area", fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(1.dp, if (isError) Color.Red else newGray, RoundedCornerShape(8.dp))
        )
        {
            // Numeric input
            TextField(
                value = formStateCarper.value?: "",
                onValueChange = { newValue ->
                    val filteredValue = newValue.filter { it.isDigit() }
                    val numericValue = filteredValue.toLongOrNull() ?: 0L

                    if (numericValue <= 50_00_00_000) { // 50 crores in numeric form
                        formStateCarper.value = filteredValue
                        constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                            it.copy(property_Carpet_Area = filteredValue)
                        }
                    }
                },
                placeholder = { Text("Enter Carpet Area", fontSize = constants.textUnit(12)) },
                modifier = Modifier
                    .weight(8f)
                    .fillMaxHeight()
                ,
                textStyle = TextStyle(
                    color = newBlack,
                    fontSize = constants.textUnit(14),
                    fontFamily = constants.fontFamily(2)
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                ),
                keyboardActions = KeyboardActions (
                    onDone = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
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
                    focusedIndicatorColor = Color.Transparent,
                )
            )

            VerticalDivider(
                modifier = Modifier
                    .fillMaxHeight()
                , thickness = 1.dp,
                color = newGray
            )

            // Dropdown for units
            Box(
                modifier = Modifier
                    .weight(2f)
                    .noRippleClickable{ expanded = true },
                contentAlignment = Alignment.Center
            )
            {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = selectedDimension ?: "",
                        fontSize = constants.textUnit(12),
                        fontWeight = FontWeight.Bold
                    )
                    //Spacer(modifier = Modifier.padding(2.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.arrowdown),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = newBlack
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    containerColor = newWhite
                ) {
                    areaMeasurements.forEach { unit ->
                        DropdownMenuItem(
                            text = { Text(unit ,
                                color = newBlack,
                                fontSize = constants.textUnit(12),
                                fontFamily = constants.fontFamily(2)) },
                            onClick = {
                                selectedDimension = unit
                                constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                    it.copy(carpet_area_unit = unit)
                                }
                                expanded = false
                            },
                            colors = MenuDefaults.itemColors(textColor = newBlack)
                        )
                    }
                }
            }
        }

        if (isError) {
            Text("Enter Carpet Area", color = Color.Red, fontSize = constants.textUnit(12))
        }

        Spacer(modifier = Modifier.padding(8.dp))

        // built up area field

        Box (
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            if (!add_builtup.value) {
                Text(
                    "+ add built-up area", color = newBlue, modifier = Modifier
                    .noRippleClickable{ add_builtup.value = true }
                    .align(Alignment.CenterStart)
                )
            }

            if (add_builtup.value) {
                Icon(
                    painter = painterResource(R.drawable.minus), "",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(14.dp)
                        .noRippleClickable{
                            add_builtup.value = false
                        }, tint = Color.Red
                )
            }
        }


        Spacer(modifier = Modifier.height(8.dp))

        AnimatedVisibility(
            visible = add_builtup.value
        )
        {

            PP_Builtup_Area()
        }

        Spacer(modifier = Modifier.height(8.dp))

        // super built up area field

        Box  (
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            if (!add_Super_builtup.value) {
                Text(
                    "+ add Super built-up area", color = newBlue, modifier = Modifier
                        .noRippleClickable{ add_Super_builtup.value = true }
                        .align(Alignment.CenterStart)
                )
            }

            if (add_Super_builtup.value) {
                Icon(
                    painter = painterResource(R.drawable.minus), "",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(14.dp)
                        .noRippleClickable{
                            add_Super_builtup.value = false
                        }, tint = Color.Red
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        AnimatedVisibility(
            visible = add_Super_builtup.value
        )
        {
            PP_Super_Builtup_Area()
        }

        println("MAIN COMPOSABLE CARPERT -- ${constants.PostProperty_ViewModel.selected_Options_Form4.value}")

    }
}




@Composable
fun PP_Builtup_Area(){

    val areaMeasurements = constants.AREA_UNITS
        //listOf("sq ft / ft²", "m²", "cm²", "sq yd")

    /// built up area
    var expanded2 by remember { mutableStateOf(false) }
    var value2 by remember { mutableStateOf("") }

    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    var formStateBuilt = remember { mutableStateOf(flowData.value?.property_Builtup_Area) }
    var selectedDimension2 by remember { mutableStateOf(flowData.value?.built_up_area_unit) }
//    var selectedDimension2 by remember { mutableStateOf(areaMeasurements.first()) }


    Column {

        Text("Built-up Area", fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))

        Spacer(modifier = Modifier.padding(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(1.dp, newGray, RoundedCornerShape(8.dp))
        )
        {
            // Numeric input
            TextField(
                value = formStateBuilt.value ?: "",
                onValueChange = { newValue ->
                    val filteredValue = newValue.filter { it.isDigit() }
                    val numericValue = filteredValue.toLongOrNull() ?: 0L

                    if (numericValue <= 50_00_00_000) { // 50 crores in numeric form
                        formStateBuilt.value =  filteredValue
                        constants.PostProperty_ViewModel.update_Selected_Field_Form4 { it.copy(property_Builtup_Area = filteredValue) }
                    }
                },
                placeholder = {
                    Text(
                        "Enter built-up Area",
                        fontSize = constants.textUnit(12)
                    )
                },
                modifier = Modifier
                    .weight(8f)
                    .fillMaxHeight(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                ),
                keyboardActions = KeyboardActions { ImeAction.Done },
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

            VerticalDivider(
                modifier = Modifier
                    .fillMaxHeight(), thickness = 1.dp,
                color = newGray
            )

            // Dropdown for units
            Box(
                modifier = Modifier
                    .weight(2f)
                    .noRippleClickable{ expanded2 = true },
                contentAlignment = Alignment.Center
            )
            {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = selectedDimension2 ?:"",
                        fontSize = constants.textUnit(12),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.arrowdown),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = newBlack
                    )
                }

                DropdownMenu(
                    expanded = expanded2,
                    onDismissRequest = { expanded2 = false },
                    containerColor = newWhite
                ) {
                    areaMeasurements.forEach { unit ->
                        DropdownMenuItem(
                            text = { Text(unit) },
                            onClick = {
                                selectedDimension2 = unit
                                constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                    it.copy(built_up_area_unit = unit)
                                }
                                expanded2 = false
                            },
                            colors = MenuDefaults.itemColors(textColor = newBlack)
                        )
                    }
                }
            }
        }
    }
}



@Composable
fun PP_Super_Builtup_Area(){

    val areaMeasurements = constants.AREA_UNITS
        //listOf("sq ft / ft²", "m²", "cm²", "sq yd")

    /// built up area
    // super built up area
    var expanded3 by remember { mutableStateOf(false) }
    var value3 by remember { mutableStateOf("") }
//    var selectedDimension3 by remember { mutableStateOf(areaMeasurements.first()) }



    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    var formStateSuperBu = remember { mutableStateOf(flowData.value?.property_Super_Builtup_Area) }

    var selectedDimension3 by remember { mutableStateOf(flowData.value?.super_built_up_area_unit) }


    Column {
        Text("Super Built-up Area", fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))

        Spacer(modifier = Modifier.padding(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(1.dp, newGray, RoundedCornerShape(8.dp))
        )
        {
            // Numeric input
            TextField(
                value = formStateSuperBu.value ?: "",
                onValueChange = { newValue ->
                    val filteredValue = newValue.filter { it.isDigit() }
                    val numericValue = filteredValue.toLongOrNull() ?: 0L

                    if (numericValue <= 50_00_00_000) { // 50 crores in numeric form
                        formStateSuperBu.value =  filteredValue
                        constants.PostProperty_ViewModel.update_Selected_Field_Form4 { it.copy(property_Super_Builtup_Area = filteredValue) }
                    }
                },
                placeholder = {
                    Text(
                        "Enter Super built-up Area",
                        fontSize = constants.textUnit(12)
                    )
                },
                modifier = Modifier
                    .weight(8f)
                    .fillMaxHeight(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                ),
                keyboardActions = KeyboardActions { ImeAction.Done },
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

            VerticalDivider(
                modifier = Modifier
                    .fillMaxHeight(), thickness = 1.dp,
                color = newGray
            )

            // Dropdown for units
            Box(
                modifier = Modifier
                    .weight(2f)
                    .noRippleClickable{ expanded3 = true },
                contentAlignment = Alignment.Center
            )
            {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = selectedDimension3 ?: "",
                        fontSize = constants.textUnit(12),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.arrowdown),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = newBlack
                    )
                }

                DropdownMenu(
                    expanded = expanded3,
                    onDismissRequest = { expanded3 = false },
                    containerColor = newWhite
                ) {
                    areaMeasurements.forEach { unit ->
                        DropdownMenuItem(
                            text = { Text(unit) },
                            onClick = {
                                selectedDimension3 = unit
                                constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                    it.copy(super_built_up_area_unit = unit)
                                }
                                expanded3 = false
                            },
                            colors = MenuDefaults.itemColors(textColor = newBlack)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun PP_Area_Dimensions() {
    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    var formStateLength = remember { mutableStateOf(flowData.value?.property_Area_Dimension_Length) }
    var formStateBreadth = remember { mutableStateOf(flowData.value?.property_Area_Dimension_Width) }



    val areaMeasurements = constants.AREA_UNITS
        //listOf("sq ft / ft²", "m²", "cm²", "sq yd")
    var expanded by remember { mutableStateOf(false) }
    var selectedDimension by remember { mutableStateOf(flowData.value?.property_Area_Dimension_Length_Unit) }
//    var selectedDimension by remember { mutableStateOf(areaMeasurements.first()) }



    val areaMeasurements2 = constants.AREA_UNITS
        //listOf("sq ft / ft²", "m²", "cm²", "sq yd")
    var expanded2 by remember { mutableStateOf(false) }
    var selectedDimension2 by remember { mutableStateOf(flowData.value?.property_Area_Dimension_Width_Unit) }
//    var selectedDimension2 by remember { mutableStateOf(areaMeasurements.first()) }



    var focusManager = LocalFocusManager.current
    var keyboardController = LocalSoftwareKeyboardController.current



    Column {
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = newBlack)) { append("Area Dimensions") }
                //withStyle(style = SpanStyle(color = Color.Red)) { append("*") }
            },
            fontSize = constants.textUnit(16),
            fontFamily = constants.fontFamily(1)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Length field

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(1.dp, newGray, RoundedCornerShape(8.dp))
        )
        {
            TextField(
                value = formStateLength.value ?: "",
                onValueChange = { newValue ->
                    val filteredValue = newValue.filter { it.isDigit() }
                    val numericValue = filteredValue.toLongOrNull() ?: 0L

                    if (numericValue <= 50_00_00_000) { // 50 crores in numeric form
                        formStateLength.value  = filteredValue
                        constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                            it.copy(property_Area_Dimension_Length = filteredValue)
                        }
                    }
                },

                placeholder = { Text("Enter area length", fontSize = constants.textUnit(12)) },
                modifier =  Modifier
                    .weight(8f)
                    .fillMaxHeight()
                , keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                ),
                keyboardActions = KeyboardActions (
                    onDone = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                ),
                singleLine = true
                , colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = newBlack,
                    unfocusedTextColor = newBlack
                ),
            )
            // Numeric input

            VerticalDivider(
                modifier = Modifier
                    .fillMaxHeight(), thickness = 1.dp,
                color = newGray
            )

            // Dropdown for units
            Box(
                modifier = Modifier
                    .weight(2f)
                    .noRippleClickable{ expanded = true }
                   // .padding(horizontal = 2.dp)
                , contentAlignment = Alignment.Center
            )
            {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = selectedDimension ?:"",
                        fontSize = constants.textUnit(12),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.arrowdown),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = newBlack
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    containerColor = newWhite
                ) {
                    areaMeasurements.forEach { unit ->
                        DropdownMenuItem(
                            text = { Text(unit ,   color = newBlack,
                                fontSize = constants.textUnit(18),
                                fontFamily = constants.fontFamily(0)) },
                            onClick = {
                                selectedDimension = unit
                                constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                    it.copy(property_Area_Dimension_Length_Unit = unit)
                                }
                                expanded = false
                            },
                            colors = MenuDefaults.itemColors(textColor = newBlack)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Breadth field
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(1.dp, newGray, RoundedCornerShape(8.dp))
        )
        {
            TextField(
                value = formStateBreadth.value ?: "",
                onValueChange = { newValue ->
                    val filteredValue = newValue.filter { it.isDigit() }
                    val numericValue = filteredValue.toLongOrNull() ?: 0L

                    if (numericValue <= 50_00_00_000) { // 50 crores in numeric form
                        formStateBreadth.value = filteredValue
                        constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                            it.copy(property_Area_Dimension_Width = filteredValue)
                        }
                    }
                },
                placeholder = { Text("Enter area length", fontSize = constants.textUnit(12)) },
                modifier =  Modifier
                    .weight(8f)
                    .fillMaxHeight()
                , keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                ),
                singleLine = true
                , colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = newBlack,
                    unfocusedTextColor = newBlack
                ),
            )
            // Numeric input

            VerticalDivider(
                modifier = Modifier
                    .fillMaxHeight(), thickness = 1.dp,
                color = newGray
            )

            // Dropdown for units
            Box(
                modifier = Modifier
                    .weight(2f)
                    .noRippleClickable{ expanded2 = true }
                    //.padding(horizontal = 2.dp)
                , contentAlignment = Alignment.Center
            )
            {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = selectedDimension2 ?:"",
                        fontSize = constants.textUnit(12),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.arrowdown),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = newBlack
                    )
                }

                DropdownMenu(
                    expanded = expanded2,
                    onDismissRequest = { expanded2 = false },
                    containerColor = newWhite
                ) {
                    areaMeasurements2.forEach { unit ->
                        DropdownMenuItem(
                            text = { Text(unit) },
                            onClick = {
                                selectedDimension2 = unit
                                constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                    it.copy(property_Area_Dimension_Width_Unit = unit)
                                }
                                expanded2 = false
                            },
                            colors = MenuDefaults.itemColors(textColor = newBlack)
                        )
                    }
                }
            }
        }

    }
}



@Composable
fun PP_Property_Facing(
    data: List<Chips_Items_DC>,
    isError: Boolean
) {
    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val selectedFacing = flowData.value?.property_Facing

    Column {
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = newBlack)) { append("Property Facing") }
                withStyle(style = SpanStyle(color = Color.Red)) { append("*") }
            },
            fontSize = constants.textUnit(16),
            fontFamily = constants.fontFamily(1)
        )

        FlowRow(modifier = Modifier.fillMaxWidth()) {
            data.forEach { item ->
                val isSelected = selectedFacing == item.title

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .wrapContentSize()
                        .background(
                            if (isSelected) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                            if (isError) errorBorderBrush() else if (isSelected) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .noRippleClickable{
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_Facing = item.title)
                            }
                        }
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(item.title, color =  newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2))
                }
            }
        }

        if (isError) {
            Text("Select a property facing", color = Color.Red, fontSize = constants.textUnit(12),
                fontFamily = constants.fontFamily(2))
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PP_Floor_Details(
    isError: Boolean
) {
    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    var formStateFloorDetails = remember { mutableStateOf(flowData.value?.property_Floor_Det_Total) }
    var formStateFloorDetailsWhich = remember { mutableStateOf(flowData.value?.property_Floor_Det_Which) }



    var focusManager = LocalFocusManager.current
    var keyboardController = LocalSoftwareKeyboardController.current



    Column {
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = newBlack)) { append("Floor Details") }
                withStyle(style = SpanStyle(color = Color.Red)) { append("*") }
            },
            fontSize = constants.textUnit(16),
            fontFamily = constants.fontFamily(1)
        )

        Spacer(modifier = Modifier.padding(4.dp))

        Text("Total Floors in Property")

        Spacer(modifier = Modifier.padding(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, if (isError) Color.Red else newGray, RoundedCornerShape(8.dp))
        ) {
            TextField(
                value = formStateFloorDetails.value ?: "",
                onValueChange = { newValue ->
                    if (newValue.length < 4){
                    val filteredValue = newValue.filter { it.isDigit() }

                        formStateFloorDetails.value = filteredValue
                        constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                            it.copy(property_Floor_Det_Total = formStateFloorDetails.value ?: "")
                        }
                        }
                   // }
                },
                placeholder = { Text("Enter Total Floors") },
                        keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword
                        ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                ),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = newBlack,
                    unfocusedTextColor = newBlack
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (formStateFloorDetails.value?.isNotEmpty() == true) {
            Spacer(modifier = Modifier.padding(8.dp))
            Text("Your Property Floor No.")

            constants.spacer(4)

            val floorOptions = listOf("Ground Floor") +
                    (1..(formStateFloorDetails.value?.toIntOrNull() ?: 0)).map { it.toString() }
            var expanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, if (isError) Color.Red else newGray, RoundedCornerShape(8.dp))
            )
            {
                TextField(
                    value = formStateFloorDetailsWhich.value ?: "",
                    onValueChange = { newValue ->
                        val filteredValue = newValue.filter { it.isDigit() }
                        val numericValue = filteredValue.toLongOrNull() ?: 0L

                        //if (numericValue <= 50_00_00_000) { // 50 crores in numeric form
                            formStateFloorDetailsWhich.value = filteredValue
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_Floor_Det_Which = formStateFloorDetailsWhich.value ?: "")
                            }
                       // }
                    },
                    readOnly = true,
                    placeholder = { Text("Select floor") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = newBlack,
                        unfocusedTextColor = newBlack
                    ),
                    modifier = Modifier.menuAnchor(type = ExposedDropdownMenuAnchorType.PrimaryEditable)
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }
                    , containerColor = newWhite
                ) {
                    floorOptions.forEach { floor ->
                        DropdownMenuItem(
                            text = { Text(floor) },
                            onClick = {
                                formStateFloorDetailsWhich.value = floor
                                constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                    it.copy(property_Floor_Det_Which = floor)
                                }
                                expanded = false
                            },
                            colors = MenuDefaults.itemColors(textColor = newBlack)
                        )
                    }
                }
            }
        }

        if (isError) {
            Text("Enter total floors and floor number", color = Color.Red, fontSize = constants.textUnit(12))
        }
    }
}





@Composable
fun PP_Parking_available(isError: Boolean) {



    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    var leaseamount = remember { mutableStateOf(flowData.value?.property_Parking) }



    val options = listOf("Yes", "No")
    var selectedOption by remember { mutableStateOf<String?>(leaseamount.value) }

    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()


    LaunchedEffect(Unit) {
        if (constants.PostProperty_ViewModel.get_Post_Form_Flow() != -1) {
            val selectedTitles = onDraft.property_Parking

            selectedOption = selectedTitles
        }
    }

    Column {
        Text(
                buildAnnotatedString {
            withStyle(style = SpanStyle(color = newBlack)) {
                append("Parking Available")
            }
            withStyle(style = SpanStyle(color = Color.Red)) {
                append("*")
            }
        },
             fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))

        Spacer(modifier = Modifier.height(4.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
            , verticalAlignment = Alignment.CenterVertically
            , horizontalArrangement = Arrangement.Start
        )
        {
            options.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .wrapContentWidth()
                        .noRippleClickable{ selectedOption = option }
                        .padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = selectedOption == option,
                        onClick = {
                            selectedOption = option
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_Parking = option)
                            }
                                  }
                        , colors = RadioButtonDefaults.colors(
                            selectedColor = newBlue
                        )
                    )
                    Text(
                        text = option,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
            }
        }

        constants.spacer(4)

        if (isError) {
            Text("Select parking availability option", color = Color.Red, fontSize = constants.textUnit(12))
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PP_Amenities(data: List<Chips_Items_DC>, isError: Boolean) {

    var expanded by remember { mutableStateOf(false) }
    val visibleCount = 6 // items to show initially

    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    // Restore selections whenever flowData or onDraft changes
    LaunchedEffect(flowData.value?.property_Amenities) {
        val selectedTitles = flowData.value?.property_Amenities ?: onDraft.property_Amenities ?: emptyList()

        data.forEach { chip ->
            chip.isSelected.value = chip.title in selectedTitles
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = newBlack)) {
                    append("Amenities")
                }
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append("*")
                }
            },
            fontSize = constants.textUnit(16),
            fontFamily = constants.fontFamily(1)
        )

        Spacer(modifier = Modifier.height(8.dp))

        FlowRow(modifier = Modifier.fillMaxWidth()) {
            // Decide which items to show
            val itemsToShow = if (expanded) data else data.take(visibleCount)

            itemsToShow.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (item.isSelected.value) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                            if (isError) errorBorderBrush() else if (item.isSelected.value) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .noRippleClickable {
                            item.isSelected.value = !item.isSelected.value
                            val selected = data.filter { it.isSelected.value }.map { it.title }

                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_Amenities = selected)
                            }
                        }
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        item.title,
                        color =  newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }

            // Show View More / View Less if there are more than visibleCount items
            if (data.size > visibleCount) {
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 8.dp)
                        .wrapContentSize()
                        .background(Color.White, RoundedCornerShape(4.dp))
                        .border(1.dp, newGray, RoundedCornerShape(4.dp))
                        .noRippleClickable { expanded = !expanded }
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        if (expanded) "View Less" else "View More",
                        color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }
        }

        constants.spacer(4)

        if (isError) {
            Text("Select amenities", color = Color.Red, fontSize = constants.textUnit(12))
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PP_Suitable_Business_Type(data:List<Chips_Items_DC>){


    var manual_add by remember { mutableStateOf(false) }
    var manual_add_No by remember { mutableStateOf("") }

    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()


    LaunchedEffect(Unit) {
        if (constants.PostProperty_ViewModel.get_Post_Form_Flow() != -1) {
            val selectedTitles = onDraft.property_Suitable_Business_Type ?: emptyList()

            data.forEach { chip ->
                chip.isSelected.value = chip.title in selectedTitles
            }
        }
    }


    Column {
        Text("Suitable Business Type", fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))

        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow (
            modifier = Modifier
                .fillMaxWidth()
        ){
            data.forEachIndexed { index , item ->
                Box(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (item.isSelected.value) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                             if (item.isSelected.value) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        ).noRippleClickable{
                            item.isSelected.value = !item.isSelected.value

                            val selected = data.filter { it.isSelected.value }.map { it.title }

                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_Suitable_Business_Type = selected)
                            }

                        }
                        .padding(horizontal = 16.dp , vertical = 8.dp)
                    , contentAlignment = Alignment.Center
                ){
                    Text(item.title, color = newBlack,
                        fontSize = constants.textUnit(16),
                        fontFamily = constants.fontFamily(2))
                }

                Spacer(modifier = Modifier.padding(8.dp))
            }


            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .wrapContentSize()
                    .background(Color.White , RoundedCornerShape(4.dp))
                    .border(1.dp , newGray , RoundedCornerShape(4.dp))
                    .noRippleClickable{
                        manual_add = true
                    }
                    .padding(horizontal = 16.dp , vertical = 8.dp)
                , contentAlignment = Alignment.Center
            ){
                if (!manual_add){
                    Text("View More",
                        color =  newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2) )
                }
                else {
                    Text("View Less",
                        color =  newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2) )
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(
//                            text = manual_add_No,
//                            fontSize = constants.textUnit(14),
//                            //fontWeight = FontWeight.Bold
//                        )
//                        Spacer(modifier = Modifier.width(4.dp))
//
//                        Icon(painter = painterResource(R.drawable.arrowdown) ,"",
//                            modifier = Modifier.size(14.dp))
//                    }
                }

            }
        }
    }


    if (manual_add) {
        ModalBottomSheet(
            onDismissRequest = {
                manual_add = false
               // manual_add_No = ""
            }
            , containerColor = newWhite
            , sheetGesturesEnabled = false
        ) {
            Column (

            ){
                Text("Suitable Business Type",  modifier = Modifier
                    .padding(horizontal = 16.dp))

                Spacer(modifier = Modifier.padding(8.dp))


                FlowRow (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ){
                        data.forEachIndexed { index , item ->
                            Box(
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .wrapContentSize()
                                    .background(if (item.isSelected.value)newBlue else Color.White , RoundedCornerShape(4.dp))
                                    .border(1.dp , newGray , RoundedCornerShape(4.dp))
                                    .noRippleClickable{
                                        item.isSelected.value = !item.isSelected.value
                                        val selected = data.filter { it.isSelected.value }.map { it.title }

                                        constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                            it.copy(property_Suitable_Business_Type = selected)
                                        }

                                        println("DATA ADDING -- ${constants.PostProperty_ViewModel.selected_Options_Form4.value}")
                                    }
                                    .padding(horizontal = 16.dp , vertical = 8.dp)
                                , contentAlignment = Alignment.Center
                            ){
                                Text(item.title, color = if (item.isSelected.value) Color.White else newBlack,
                                    fontSize = constants.textUnit(14),
                                    fontFamily = constants.fontFamily(2)
                                )
                            }

                            Spacer(modifier = Modifier.padding(8.dp))
                        }
                    }


                Spacer(modifier = Modifier.padding(8.dp))

                Static_Bottom(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .fillMaxHeight(.7f)
                            .background(newBlue, RoundedCornerShape(8.dp))
                            .noRippleClickable{
                                manual_add = false
                            }
                        , contentAlignment = Alignment.Center
                    ){
                        Text("Submit", color = Color.White)
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PP_Property_Highlights(data: List<Chips_Items_DC>) {

    var manual_add by remember { mutableStateOf(false) }
    var manual_add_No by remember { mutableStateOf("") }

    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    // Fix: Restore selections when flowData changes
    LaunchedEffect(flowData.value?.property_Highlights) {
        val selectedTitles = flowData.value?.property_Highlights ?: onDraft.property_Highlights ?: emptyList()

        data.forEach { chip ->
            chip.isSelected.value = chip.title in selectedTitles
        }
    }

    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text("Property Highlights", fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))

        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            data.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (item.isSelected.value) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                             if (item.isSelected.value) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        ).noRippleClickable {
                            item.isSelected.value = !item.isSelected.value

                            val selected = data.filter { it.isSelected.value }.map { it.title }

                            // Fix: Update property_Highlights, not property_Amenities
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_Highlights = selected)
                            }

                            println("DATA ADDING HIGHLIGHT-- ${constants.PostProperty_ViewModel.selected_Options_Form4.value}")
                        }
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        item.title,
                        color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }
        }
    }

    if (manual_add) {
        ModalBottomSheet(
            onDismissRequest = {
                manual_add = false
                manual_add_No = ""
            },
            containerColor = newWhite,
            sheetGesturesEnabled = false
        ) {
            Column {
                Text(
                    "Property Highlights", modifier = Modifier
                        .padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.padding(8.dp))

                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    data.forEachIndexed { index, item ->
                        Box(
                            modifier = Modifier
                                .padding(end = 8.dp, bottom = 8.dp)
                                .wrapContentSize()
                                .background(
                                    if (item.isSelected.value) newBlue else Color.White,
                                    RoundedCornerShape(4.dp)
                                )
                                .border(1.dp, newGray, RoundedCornerShape(4.dp))
                                .clickable() {
                                    item.isSelected.value = !item.isSelected.value
                                    val selected = data.filter { it.isSelected.value }.map { it.title }

                                    constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                        it.copy(property_Highlights = selected)
                                    }

                                    println("DATA ADDING -- ${constants.PostProperty_ViewModel.selected_Options_Form4.value}")
                                }
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                item.title, fontSize = constants.textUnit(14),
                                fontFamily = constants.fontFamily(2)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Static_Bottom(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .fillMaxHeight(.7f)
                            .background(newBlue, RoundedCornerShape(8.dp))
                            .noRippleClickable {
                                manual_add = false
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Submit", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun PP_Other_Rooms(data: List<Chips_Items_DC>) {

    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    // Fix: Restore selections when flowData changes
    LaunchedEffect(flowData.value?.property_Other_Rooms) {
        val selectedTitles = flowData.value?.property_Other_Rooms ?: onDraft.property_Other_Rooms ?: emptyList()

        data.forEach { chip ->
            chip.isSelected.value = chip.title in selectedTitles
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text("Other Rooms", fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))

        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            data.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (item.isSelected.value) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                             if (item.isSelected.value) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .noRippleClickable {
                            item.isSelected.value = !item.isSelected.value

                            val selected = data.filter { it.isSelected.value }.map { it.title }
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_Other_Rooms = selected)
                            }
                        }
                        .padding(8.dp)
                ) {
                    Text(
                        item.title,
                        color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }
        }
    }
}

@Composable
fun PP_Furnishing_Status(data: List<Chips_Items_DC>, isError: Boolean) {

    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    // Fix: Restore selection when flowData changes
    LaunchedEffect(flowData.value?.property_Furnished) {
        val selectedTitle = flowData.value?.property_Furnished ?: onDraft.property_Furnished

        data.forEach { chip ->
            chip.isSelected.value = chip.title == selectedTitle
        }
    }

    Column {
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = newBlack)) {
                    append("Furnishing Status")
                }
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append("*")
                }
            },
            fontSize = constants.textUnit(16),
            fontFamily = constants.fontFamily(1)
        )

        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            data.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (item.isSelected.value) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                            if (isError) errorBorderBrush() else if (item.isSelected.value) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        ).noRippleClickable {
                            data.forEach { it.isSelected.value = false } // unselect all
                            item.isSelected.value = true // select clicked one

                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_Furnished = item.title)
                            }
                        }
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        item.title,
                        color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }
        }

        constants.spacer(4)

        if (isError) {
            Text("Select a furnishing status", color = Color.Red, fontSize = constants.textUnit(12))
        }
    }
}

@Composable
fun PP_Preferred_Tenants(data: List<Chips_Items_DC>) {

    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    // Fix: Restore selections when flowData changes (now supporting multiple selections)
//    LaunchedEffect(flowData.value?.property_preferred_tenants) {
//        val selectedTitles = flowData.value?.property_preferred_tenants ?: onDraft.property_preferred_tenants ?: emptyList()
//
//        println("ONSelction -- ${data} -- ${selectedTitles} -- ${flowData.value?.property_preferred_tenants}")
//        data.forEach { chip ->
//            chip.isSelected.value = chip.title in selectedTitles
//        }
//    }

    LaunchedEffect(flowData.value?.property_preferred_tenants) {
        val selectedTitles = (flowData.value?.property_preferred_tenants ?: onDraft.property_preferred_tenants ?: emptyList())
            .flatMap {
                it.removePrefix("[").removeSuffix("]").split(",")
            }
            .map { it.trim() }


        println("ONSelction -- $data -- $selectedTitles")

        println("----- Selection Compare Debug -----")
        selectedTitles.forEach { apiValue ->
            data.forEach { chip ->
                println("Compare '${chip.title}' == '$apiValue' --> ${chip.title == apiValue}")
            }
        }

        data.forEach { chip ->
            chip.isSelected.value = chip.title in selectedTitles
        }
    }





    Column {
        Text("Preferred Tenants", fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))

        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            data.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (item.isSelected.value) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                             if (item.isSelected.value) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        ).noRippleClickable {
//                            item.isSelected.value = !item.isSelected.value
//
//                            val selected = data.filter { it.isSelected.value }.map { it.title }
//
//                            // Update ViewModel with multiple selections
//                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
//                                it.copy(property_preferred_tenants = selected)
//                            }

                            item.isSelected.value = !item.isSelected.value

                            val selected = data.filter { it.isSelected.value }.map { it.title }
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_preferred_tenants = selected)
                            }


                        }
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        item.title,
                        color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }
        }
    }
}


@Composable
fun PP_Land_Area(isError: Boolean){

    val areaMeasurements = constants.AREA_UNITS
        //listOf("m²", "sq ft", "sq yd", "acre", "hectare")

    /// area

    var expanded by remember { mutableStateOf(false) }
//    var selectedDimension by remember { mutableStateOf(areaMeasurements.first()) }
    var value by remember { mutableStateOf("") }



    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    var formStateSuperBu = remember { mutableStateOf(flowData.value?.property_Land_Area) }
    var selectedDimension by remember { mutableStateOf(flowData.value?.property_area_unit) }



    var focusManager = LocalFocusManager.current
    var keyboardController = LocalSoftwareKeyboardController.current




    Column {
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = newBlack)){
                    append("Property Area")
                }
                withStyle(style = SpanStyle(color = Color.Red)){
                    append("*")
                }
            }
            , fontSize = constants.textUnit(16)
            , fontFamily = constants.fontFamily(1)
        )
       // Text("Property Area", fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(1.dp, if (isError) Color.Red else newGray, RoundedCornerShape(8.dp))
        )
        {
            // Numeric input
            TextField(
                value = formStateSuperBu.value ?: ""
                ,onValueChange = { newValue ->
                    val filteredValue = newValue.filter { it.isDigit() }

                    // if (numericValue <= 50_00_00_000) { // 50 crores in numeric form
                    formStateSuperBu.value =  filteredValue
                    constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                        it.copy(property_Land_Area = filteredValue)
                    }

                },
                placeholder = { Text("Enter area length", fontSize = constants.textUnit(12)) },
                modifier = Modifier
                    .weight(8f)
                    .fillMaxHeight(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
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

            VerticalDivider(
                modifier = Modifier
                    .fillMaxHeight(), thickness = 1.dp,
                color = newGray
            )

            // Dropdown for units
            Box(
                modifier = Modifier
                    .weight(2f)
                    .noRippleClickable{ expanded = true },
                contentAlignment = Alignment.Center
            )
            {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = selectedDimension ?:"",
                        fontSize = constants.textUnit(12),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.arrowdown),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = newBlack
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    containerColor = newWhite
                ) {
                    areaMeasurements.forEach { unit ->
                        DropdownMenuItem(
                            text = { Text(unit) },
                            onClick = {
                                selectedDimension = unit
                               // areaMeasurements.forEach { it.isSelected.value = false } // unselect all
                               // item.isSelected.value = true // select clicked one

// 🔹 Update ViewModel with only one selected
                                constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                    it.copy(property_area_unit = unit)
                                }
                                expanded = false
                            },
                            colors = MenuDefaults.itemColors(textColor = newBlack)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.padding(8.dp))

    }
}


//val selected = data.filter { it.isSelected.value }.map { it.title }
//
//constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
//    it.copy(property_No_Of_OpenSides = selected)
//}


//data.forEach { it.isSelected.value = false } // unselect all
//item.isSelected.value = true // select clicked one
//
//// 🔹 Update ViewModel with only one selected
//constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
//    it.copy(property_No_of_Baths = item.title)
//}

@Composable
fun PP_Land_Open_Sides(data:List<Chips_Items_DC>){

    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()


    LaunchedEffect(Unit) {
        if (constants.PostProperty_ViewModel.get_Post_Form_Flow() != -1) {
            val selectedTitles = onDraft.property_No_Of_OpenSides

            data.forEach { chip ->
                chip.isSelected.value = chip.title in selectedTitles
            }
        }
    }

    Column {
        Text("No. of Open Sides", fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))

        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow (
            modifier = Modifier
                .fillMaxWidth()
        ){
            data.forEachIndexed { index , item ->
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .background(
                            if (item.isSelected.value) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                             if (item.isSelected.value) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        ).noRippleClickable{
                            item.isSelected.value = !item.isSelected.value

                            data.forEach { it.isSelected.value = false } // unselect all
                            item.isSelected.value = true // select clicked one

                            // 🔹 Update ViewModel with only one selected
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_No_Of_OpenSides = item.title)
                            }

                        }
                        .padding(vertical = 8.dp , horizontal = 16.dp)
                    , contentAlignment = Alignment.Center
                ){
                    Text(item.title,
                        color = newBlack
                        ,fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2))
                }

                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    }
}




@Composable
fun PP_Has_Boundary_Wall() {
    val options = listOf("Yes", "No")
    var selectedOption by remember { mutableStateOf<String?>(null) }

    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()


    LaunchedEffect(Unit) {
        if (constants.PostProperty_ViewModel.get_Post_Form_Flow() != -1) {
            val selectedTitles = onDraft.property_Boundary_Wall

            selectedOption = selectedTitles
        }
    }

    Column {
        Text("Is boundary wall around property?", fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))

        Spacer(modifier = Modifier.height(4.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
            , verticalAlignment = Alignment.CenterVertically
            , horizontalArrangement = Arrangement.Start
        ){
            options.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .wrapContentWidth()
                        .noRippleClickable{ selectedOption = option }
                        .padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = selectedOption == option,
                        onClick = {
                            selectedOption = option

                            // 🔹 Update ViewModel with only one selected
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_Boundary_Wall = option)
                            }
                                  }
                        , colors = RadioButtonDefaults.colors(
                            selectedColor = newBlue
                        )
                    )
                    Text(
                        text = option,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PP_No_Of_Bathrooms(data: List<Chips_Items_DC>, isError: Boolean) {

    var manual_add by remember { mutableStateOf(false) }
    var manual_add_No by remember { mutableStateOf("") }

    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    // Restore selection state when navigating back
    LaunchedEffect(flowData.value?.property_No_of_Baths) {
        val selectedValue = flowData.value?.property_No_of_Baths ?: onDraft.property_No_of_Baths

        println("ONSelction -- $data -- $selectedValue")

        println("----- Selection Compare Debug NO OF baths-----")
        selectedValue.forEach { apiValue ->
            data.forEach { chip ->
                println("Compare '${chip.title}' == '$apiValue' --> ${chip.title == apiValue.toString()}")
            }
        }

        if (selectedValue != null) {
            // Check if the value exists in the predefined chips
            val matchingChip = data.find { it.title == selectedValue }

            if (matchingChip != null) {
                // It's a predefined option, select it
                data.forEach { it.isSelected.value = false }
                matchingChip.isSelected.value = true
            } else {
                // It's a custom value, deselect all chips
                data.forEach { it.isSelected.value = false }
            }
        }
    }

    // Get the current selected value (either from chip or custom)
    val selectedFromChip = data.find { it.isSelected.value }?.title
    val customValue = flowData.value?.property_No_of_Baths
    val displayValue = if (selectedFromChip != null) null else customValue


    var focusManager = LocalFocusManager.current
    var keyboardController = LocalSoftwareKeyboardController.current



    Column {
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = newBlack)) {
                    append("No. of Bathrooms")
                }
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append("*")
                }
            },
            fontSize = constants.textUnit(16),
            fontFamily = constants.fontFamily(1)
        )

        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            data.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (item.isSelected.value) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                            if (isError) errorBorderBrush() else if (item.isSelected.value) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        ).noRippleClickable {
                            data.forEach { it.isSelected.value = false } // unselect all
                            item.isSelected.value = true // select clicked one

                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_No_of_Baths = item.title)
                            }
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        item.title,
                        color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 8.dp)
                    .wrapContentSize()
                    .background(
                        if (displayValue.isNullOrEmpty()) Color.White else newBlue,
                        RoundedCornerShape(4.dp)
                    )
                    .border(1.dp, newGray, RoundedCornerShape(4.dp))
                    .noRippleClickableWithScale(MutableInteractionSource()) {
                        manual_add = true
                        manual_add_No = displayValue ?: ""
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                if (displayValue.isNullOrEmpty()) {
                    Text("+ Add")
                } else {
                    Row(
                        modifier = Modifier.background(newBlue),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = displayValue,
                            fontSize = constants.textUnit(14),
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))

                        Icon(
                            painter = painterResource(R.drawable.arrowdown), "",
                            modifier = Modifier.size(14.dp),
                            tint = Color.White
                        )
                    }
                }
            }
        }

        constants.spacer(4)

        if (isError) {
            Text("Select a no of bathrooms", color = Color.Red, fontSize = constants.textUnit(12))
        }
    }

    if (manual_add) {
        ModalBottomSheet(
            onDismissRequest = {
                manual_add = false
                manual_add_No = ""
            },
            containerColor = newWhite,
            sheetGesturesEnabled = false
        ) {
            Column {
                Text(
                    "Enter No. of Bathrooms", modifier = Modifier
                        .padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .border(1.dp, newGray, RoundedCornerShape(8.dp))
                ) {
                    TextField(
                        value = manual_add_No,
                        onValueChange = { newValue ->
                            if (newValue.length <= 3) {
                                manual_add_No = newValue.filter { it.isDigit() }
                            }
                        },
                        singleLine = true,
                        placeholder = {
                            Text("Enter number")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword
                        ),
                        keyboardActions = KeyboardActions (
                            onDone = {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            }
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedTextColor = newBlack,
                            unfocusedTextColor = newBlack
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Static_Bottom(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .fillMaxHeight(.7f)
                            .background(newBlue, RoundedCornerShape(8.dp))
                            .noRippleClickable {
                                if (manual_add_No.isNotEmpty()) {
                                    // Deselect all chips when custom value is added
                                    data.forEach { it.isSelected.value = false }

                                    constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                        it.copy(property_No_of_Baths = manual_add_No)
                                    }
                                }
                                manual_add = false
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Submit", color = Color.White)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PP_No_Of_Balconies(data: List<Chips_Items_DC>, isError: Boolean) {

    var manual_add by remember { mutableStateOf(false) }
    var manual_add_No by remember { mutableStateOf("") }

    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    // Restore selection state when navigating back
    LaunchedEffect(flowData.value?.property_No_of_Balconies) {
        println("NOOFBALCONIES --${flowData.value?.property_No_of_Balconies} ")
        val selectedValue = flowData.value?.property_No_of_Balconies ?: onDraft.property_No_of_Balconies

        if (selectedValue != null) {
            // Check if the value exists in the predefined chips
            val matchingChip = data.find { it.title == selectedValue }

            if (matchingChip != null) {
                // It's a predefined option, select it
                data.forEach { it.isSelected.value = false }
                matchingChip.isSelected.value = true
            } else {
                // It's a custom value, deselect all chips
                data.forEach { it.isSelected.value = false }
            }
        }
    }

    // Get the current selected value (either from chip or custom)
    val selectedFromChip = data.find { it.isSelected.value }?.title
    val customValue = flowData.value?.property_No_of_Balconies
    val displayValue = if (selectedFromChip != null) null else customValue


    var focusManager = LocalFocusManager.current
    var keyboardController = LocalSoftwareKeyboardController.current



    Column {
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = newBlack)) {
                    append("No. of Balconies")
                }
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append("*")
                }
            },
            fontSize = constants.textUnit(16),
            fontFamily = constants.fontFamily(1)
        )

        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            data.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (item.isSelected.value) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                            if (isError) errorBorderBrush() else if (item.isSelected.value) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        ).noRippleClickable {
                            data.forEach { it.isSelected.value = false } // unselect all
                            item.isSelected.value = true // select clicked one

                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_No_of_Balconies = item.title)
                            }
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        item.title,
                        color =  newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 8.dp)
                    .wrapContentSize()
                    .background(
                        if (displayValue.isNullOrEmpty()) Color.White else newBlue,
                        RoundedCornerShape(4.dp)
                    )
                    .border(1.dp, newGray, RoundedCornerShape(4.dp))
                    .noRippleClickableWithScale(MutableInteractionSource()) {
                        manual_add = true
                        manual_add_No = displayValue ?: ""
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                if (displayValue.isNullOrEmpty()) {
                    Text("+ Add")
                } else {
                    Row(
                        modifier = Modifier.background(newBlue),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = displayValue,
                            fontSize = constants.textUnit(14),
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))

                        Icon(
                            painter = painterResource(R.drawable.arrowdown), "",
                            modifier = Modifier.size(14.dp),
                            tint = Color.White
                        )
                    }
                }
            }
        }

        constants.spacer(4)

        if (isError) {
            Text("Select a No of balconies", color = Color.Red, fontSize = constants.textUnit(12))
        }
    }

    if (manual_add) {
        ModalBottomSheet(
            onDismissRequest = {
                manual_add = false
                manual_add_No = ""
            },
            containerColor = newWhite,
            sheetGesturesEnabled = false
        ) {
            Column {
                Text(
                    "Enter No. of Balconies", modifier = Modifier
                        .padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .border(1.dp, newGray, RoundedCornerShape(8.dp))
                ) {
                    TextField(
                        value = manual_add_No,
                        onValueChange = { newValue ->
                            if (newValue.length <= 3) {
                                manual_add_No = newValue.filter { it.isDigit() }
                            }
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            }
                        ),
                        placeholder = {
                            Text("Enter number")
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedTextColor = newBlack,
                            unfocusedTextColor = newBlack
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Static_Bottom(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .fillMaxHeight(.7f)
                            .background(newBlue, RoundedCornerShape(8.dp))
                            .noRippleClickable {
                                if (manual_add_No.isNotEmpty()) {
                                    // Deselect all chips when custom value is added
                                    data.forEach { it.isSelected.value = false }

                                    constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                        it.copy(property_No_of_Balconies = manual_add_No)
                                    }
                                }
                                manual_add = false
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Submit", color = Color.White)
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PP_No_of_Meeting_rooms(data: List<Chips_Items_DC>) {

    var manual_add by remember { mutableStateOf(false) }
    var manual_add_No by remember { mutableStateOf("") }

    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    LaunchedEffect(flowData.value?.property_No_Of_Meeting_Rooms) {
        val selectedValue = flowData.value?.property_No_Of_Meeting_Rooms ?: onDraft.property_No_Of_Meeting_Rooms

        if (selectedValue != null) {
            val matchingChip = data.find { it.title == selectedValue }

            if (matchingChip != null) {
                data.forEach { it.isSelected.value = false }
                matchingChip.isSelected.value = true
            } else {
                data.forEach { it.isSelected.value = false }
            }
        }
    }

    val selectedFromChip = data.find { it.isSelected.value }?.title
    val customValue = flowData.value?.property_No_Of_Meeting_Rooms
    val displayValue = if (selectedFromChip != null) null else customValue


    var focusManager = LocalFocusManager.current
    var keyboardController = LocalSoftwareKeyboardController.current



    Column {
        Text("No. of Meeting rooms", fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))
        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow(modifier = Modifier.fillMaxWidth()) {
            data.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (item.isSelected.value) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                            if (item.isSelected.value) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        ).noRippleClickable {
                            data.forEach { it.isSelected.value = false }
                            item.isSelected.value = true
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_No_Of_Meeting_Rooms = item.title)
                            }
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(item.title, color = newBlack, fontSize = constants.textUnit(14), fontFamily = constants.fontFamily(2))
                }
            }

            Box(
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 8.dp)
                    .wrapContentSize()
                    .background(if (displayValue.isNullOrEmpty()) Color.White else newBlue, RoundedCornerShape(4.dp))
                    .border(1.dp, newGray, RoundedCornerShape(4.dp))
                    .noRippleClickableWithScale(MutableInteractionSource()) {
                        manual_add = true
                        manual_add_No = displayValue ?: ""
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                if (displayValue.isNullOrEmpty()) {
                    Text("+ Add")
                } else {
                    Row(modifier = Modifier.background(newBlue), verticalAlignment = Alignment.CenterVertically) {
                        Text(text = displayValue, fontSize = constants.textUnit(14), color = Color.White)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(painter = painterResource(R.drawable.arrowdown), "", modifier = Modifier.size(14.dp), tint = Color.White)
                    }
                }
            }
        }
    }

    if (manual_add) {
        ModalBottomSheet(
            onDismissRequest = { manual_add = false; manual_add_No = "" },
            containerColor = newWhite,
            sheetGesturesEnabled = false
        ) {
            Column {
                Text("Enter No. of Meeting Rooms", modifier = Modifier.padding(horizontal = 16.dp))
                Spacer(modifier = Modifier.padding(8.dp))

                Row(modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth().border(1.dp, newGray, RoundedCornerShape(8.dp))) {
                    TextField(
                        value = manual_add_No,
                        onValueChange = {
                            if (it.length <= 3) {
                                manual_add_No = it.filter { char -> char.isDigit() }
                            }
                                        },
                        placeholder = { Text("Enter number") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            }
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White, unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent,
                            focusedTextColor = newBlack, unfocusedTextColor = newBlack
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Static_Bottom(modifier = Modifier.fillMaxWidth().height(80.dp)) {
                    Box(
                        modifier = Modifier.fillMaxWidth(.9f).fillMaxHeight(.7f).background(newBlue, RoundedCornerShape(8.dp))
                            .noRippleClickable {
                                if (manual_add_No.isNotEmpty()) {
                                    data.forEach { it.isSelected.value = false }
                                    constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                        it.copy(property_No_Of_Meeting_Rooms = manual_add_No)
                                    }
                                }
                                manual_add = false
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Submit", color = Color.White)
                    }
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PP_No_Of_Bedrooms(data:List<Chips_Items_DC>){


    var manual_add by remember { mutableStateOf(false) }
    var manual_add_No by remember { mutableStateOf("") }

    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    var formStateSuperBu = remember { mutableStateOf(flowData.value?.property_No_of_Beds) }


    var focusManager = LocalFocusManager.current
    var keyboardController = LocalSoftwareKeyboardController.current




    Column {
        Text("No. of Bedrooms", fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))

        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow (
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            data.forEachIndexed { index , item ->
                Box(
                    modifier = Modifier
                        .padding( vertical = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (item.isSelected.value) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                           if (item.isSelected.value) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        ).noRippleClickable{
                            //item.isSelected.value = !item.isSelected.value
                            data.forEach { it.isSelected.value = false } // unselect all
                            item.isSelected.value = true // select clicked one

                            // 🔹 Update ViewModel with only one selected
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_No_of_Beds = item.title)
                            }
                        }
                        .padding(horizontal = 16.dp , vertical = 8.dp)
                    , contentAlignment = Alignment.Center
                ){
                    Text(item.title, color =  newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2))
                }

                Spacer(modifier = Modifier.padding(8.dp))
            }
            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .wrapContentSize()
                    .background(  if (formStateSuperBu.value?.isEmpty() == true) Color.White else newBlue , RoundedCornerShape(4.dp))
                    .border(1.dp , newGray , RoundedCornerShape(4.dp))
                    .noRippleClickableWithScale(MutableInteractionSource()){
                        manual_add = true
                    }
                    .padding(horizontal = 16.dp , vertical = 8.dp)
                , contentAlignment = Alignment.Center
            ){
                if (formStateSuperBu.value?.isEmpty() == true){
                    Text("+ Add" )
                }
                else {
                    Row(
                        modifier = Modifier.background(newBlue),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = formStateSuperBu.value ?: "",
                            fontSize = constants.textUnit(14),
                            color = Color.White
                            //fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(4.dp))

                        Icon(painter = painterResource(R.drawable.arrowdown) ,"",
                            modifier = Modifier
                                .size(14.dp)
                            , tint = Color.White
                        )
                    }
                }

            }
        }
    }


    if (manual_add) {
        ModalBottomSheet(
            onDismissRequest = {
                manual_add = false
                manual_add_No = ""
                formStateSuperBu.value = ""
            }
            , containerColor = newWhite
            , sheetGesturesEnabled = false
        ) {
            Column (

            ){
                Text("Enter No. of BedRooms",  modifier = Modifier
                    .padding(horizontal = 16.dp))

                Spacer(modifier = Modifier.padding(8.dp))

                Row (
                    modifier = Modifier.padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .border(1.dp, newGray, RoundedCornerShape(8.dp))
                ){
                    TextField(
                        value = formStateSuperBu.value ?: ""
                        ,onValueChange = { newValue ->
                            if (newValue.length <= 3) {
                                val filteredValue = newValue.filter { it.isDigit() }

                                // if (numericValue <= 50_00_00_000) { // 50 crores in numeric form
                                formStateSuperBu.value = filteredValue
                            }

                        }
                        , placeholder = {
                            Text("Enter number")
                        }
                        , singleLine = true
                               , keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.NumberPassword
                                ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            }
                        )
                        , colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedTextColor = newBlack,
                            unfocusedTextColor = newBlack
                        )
                        , modifier = Modifier
                            .fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Static_Bottom(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .fillMaxHeight(.7f)
                            .background(newBlue, RoundedCornerShape(8.dp))
                            .noRippleClickable{
                                manual_add = false
                            }
                        , contentAlignment = Alignment.Center
                    ){
                        Text("Submit", color = Color.White)
                    }
                }
            }
        }
    }
}


///////// commmercial

////// newww

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PP_No_of_Staircases(data: List<Chips_Items_DC>) {

    var manual_add by remember { mutableStateOf(false) }
    var manual_add_No by remember { mutableStateOf("") }

    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    LaunchedEffect(flowData.value?.property_No_Of_Stairs) {
        val selectedValue = flowData.value?.property_No_Of_Stairs ?: onDraft.property_No_Of_Stairs

        if (selectedValue != null) {
            val matchingChip = data.find { it.title == selectedValue }

            if (matchingChip != null) {
                data.forEach { it.isSelected.value = false }
                matchingChip.isSelected.value = true
            } else {
                data.forEach { it.isSelected.value = false }
            }
        }
    }

    val selectedFromChip = data.find { it.isSelected.value }?.title
    val customValue = flowData.value?.property_No_Of_Stairs
    val displayValue = if (selectedFromChip != null) null else customValue


    var focusManager = LocalFocusManager.current
    var keyboardController = LocalSoftwareKeyboardController.current



    Column {
        Text("No. of Staircases", fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))
        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow(modifier = Modifier.fillMaxWidth()) {
            data.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (item.isSelected.value) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                            if (item.isSelected.value) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .noRippleClickable {
                            data.forEach { it.isSelected.value = false }
                            item.isSelected.value = true
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_No_Of_Stairs = item.title)
                            }
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        item.title,
                        color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 8.dp)
                    .wrapContentSize()
                    .background(
                        if (displayValue.isNullOrEmpty()) Color.White else newBlue,
                        RoundedCornerShape(4.dp)
                    )
                    .border(1.dp, newGray, RoundedCornerShape(4.dp))
                    .noRippleClickableWithScale(MutableInteractionSource()) {
                        manual_add = true
                        manual_add_No = displayValue ?: ""
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                if (displayValue.isNullOrEmpty()) {
                    Text("+ Add")
                } else {
                    Row(
                        modifier = Modifier.background(newBlue),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = displayValue, fontSize = constants.textUnit(14), color = Color.White)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            painter = painterResource(R.drawable.arrowdown),
                            "",
                            modifier = Modifier.size(14.dp),
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }

    if (manual_add) {
        ModalBottomSheet(
            onDismissRequest = {
                manual_add = false
                manual_add_No = ""
            },
            containerColor = newWhite,
            sheetGesturesEnabled = false
        ) {
            Column {
                Text("Enter No. of Staircases", modifier = Modifier.padding(horizontal = 16.dp))
                Spacer(modifier = Modifier.padding(8.dp))

                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .border(1.dp, newGray, RoundedCornerShape(8.dp))
                ) {
                    TextField(
                        value = manual_add_No,
                        onValueChange = {
                            if (it.length <= 3) {
                                manual_add_No = it.filter { char -> char.isDigit() }
                            }
                                        },
                        placeholder = { Text("Enter number") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            }
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedTextColor = newBlack,
                            unfocusedTextColor = newBlack
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Static_Bottom(modifier = Modifier.fillMaxWidth().height(80.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .fillMaxHeight(.7f)
                            .background(newBlue, RoundedCornerShape(8.dp))
                            .noRippleClickable {
                                if (manual_add_No.isNotEmpty()) {
                                    data.forEach { it.isSelected.value = false }
                                    constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                        it.copy(property_No_Of_Stairs = manual_add_No)
                                    }
                                }
                                manual_add = false
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Submit", color = Color.White)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PP_Conference_Room(data: List<Chips_Items_DC>) {

    var manual_add by remember { mutableStateOf(false) }
    var manual_add_No by remember { mutableStateOf("") }

    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    LaunchedEffect(flowData.value?.property_Conference_Room) {
        val selectedValue = flowData.value?.property_Conference_Room ?: onDraft.property_Conference_Room

        if (selectedValue != null) {
            val matchingChip = data.find { it.title == selectedValue }

            if (matchingChip != null) {
                data.forEach { it.isSelected.value = false }
                matchingChip.isSelected.value = true
            } else {
                data.forEach { it.isSelected.value = false }
            }
        }
    }

    val selectedFromChip = data.find { it.isSelected.value }?.title
    val customValue = flowData.value?.property_Conference_Room
    val displayValue = if (selectedFromChip != null) null else customValue


    var focusManager = LocalFocusManager.current
    var keyboardController = LocalSoftwareKeyboardController.current



    Column {
        Text("Conference Room", fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))
        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow(modifier = Modifier.fillMaxWidth()) {
            data.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (item.isSelected.value) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                            if (item.isSelected.value) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .noRippleClickable {
                            data.forEach { it.isSelected.value = false }
                            item.isSelected.value = true
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_Conference_Room = item.title)
                            }
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        item.title,
                        color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 8.dp)
                    .wrapContentSize()
                    .background(
                        if (displayValue.isNullOrEmpty()) Color.White else newBlue,
                        RoundedCornerShape(4.dp)
                    )
                    .border(1.dp, newGray, RoundedCornerShape(4.dp))
                    .noRippleClickableWithScale(MutableInteractionSource()) {
                        manual_add = true
                        manual_add_No = displayValue ?: ""
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                if (displayValue.isNullOrEmpty()) {
                    Text("+ Add")
                } else {
                    Row(
                        modifier = Modifier.background(newBlue),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = displayValue, fontSize = constants.textUnit(14), color = Color.White)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            painter = painterResource(R.drawable.arrowdown),
                            "",
                            modifier = Modifier.size(14.dp),
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }

    if (manual_add) {
        ModalBottomSheet(
            onDismissRequest = {
                manual_add = false
                manual_add_No = ""
            },
            containerColor = newWhite,
            sheetGesturesEnabled = false
        ) {
            Column {
                Text("Enter No. of Conference Rooms", modifier = Modifier.padding(horizontal = 16.dp))
                Spacer(modifier = Modifier.padding(8.dp))

                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .border(1.dp, newGray, RoundedCornerShape(8.dp))
                ) {
                    TextField(
                        value = manual_add_No,
                        onValueChange = {
                            if(it.length <= 3) {
                                manual_add_No = it.filter { char -> char.isDigit() }
                            }
                                        },
                        placeholder = { Text("Enter number") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            }
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedTextColor = newBlack,
                            unfocusedTextColor = newBlack
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Static_Bottom(modifier = Modifier.fillMaxWidth().height(80.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .fillMaxHeight(.7f)
                            .background(newBlue, RoundedCornerShape(8.dp))
                            .noRippleClickable {
                                if (manual_add_No.isNotEmpty()) {
                                    data.forEach { it.isSelected.value = false }
                                    constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                        it.copy(property_Conference_Room = manual_add_No)
                                    }
                                }
                                manual_add = false
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Submit", color = Color.White)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PP_Min_No_of_Seats(data: List<Chips_Items_DC>) {

    var manual_add by remember { mutableStateOf(false) }
    var manual_add_No by remember { mutableStateOf("") }

    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    LaunchedEffect(flowData.value?.property_Min_No_Of_Seats) {
        val selectedValue = flowData.value?.property_Min_No_Of_Seats ?: onDraft.property_Min_No_Of_Seats

        if (selectedValue != null) {
            val matchingChip = data.find { it.title == selectedValue }

            if (matchingChip != null) {
                data.forEach { it.isSelected.value = false }
                matchingChip.isSelected.value = true
            } else {
                data.forEach { it.isSelected.value = false }
            }
        }
    }

    val selectedFromChip = data.find { it.isSelected.value }?.title
    val customValue = flowData.value?.property_Min_No_Of_Seats
    val displayValue = if (selectedFromChip != null) null else customValue


    var focusManager = LocalFocusManager.current
    var keyboardController = LocalSoftwareKeyboardController.current



    Column {
        Text("Min. No. of Seats", fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))
        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow(modifier = Modifier.fillMaxWidth()) {
            data.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (item.isSelected.value) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                            if (item.isSelected.value) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .noRippleClickable {
                            data.forEach { it.isSelected.value = false }
                            item.isSelected.value = true
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_Min_No_Of_Seats = item.title)
                            }
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        item.title,
                        color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 8.dp)
                    .wrapContentSize()
                    .background(
                        if (displayValue.isNullOrEmpty()) Color.White else newBlue,
                        RoundedCornerShape(4.dp)
                    )
                    .border(1.dp, newGray, RoundedCornerShape(4.dp))
                    .noRippleClickableWithScale(MutableInteractionSource()) {
                        manual_add = true
                        manual_add_No = displayValue ?: ""
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                if (displayValue.isNullOrEmpty()) {
                    Text("+ Add")
                } else {
                    Row(
                        modifier = Modifier.background(newBlue),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = displayValue, fontSize = constants.textUnit(14), color = Color.White)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            painter = painterResource(R.drawable.arrowdown),
                            "",
                            modifier = Modifier.size(14.dp),
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }

    if (manual_add) {
        ModalBottomSheet(
            onDismissRequest = {
                manual_add = false
                manual_add_No = ""
            },
            containerColor = newWhite,
            sheetGesturesEnabled = false
        ) {
            Column {
                Text("Enter Min. No. of Seats", modifier = Modifier.padding(horizontal = 16.dp))
                Spacer(modifier = Modifier.padding(8.dp))

                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .border(1.dp, newGray, RoundedCornerShape(8.dp))
                ) {
                    TextField(
                        value = manual_add_No,
                        onValueChange = {
                            if (it.length <= 3) {
                                manual_add_No = it.filter { char -> char.isDigit() }
                            }
                                        },
                        placeholder = { Text("Enter number") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            }
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedTextColor = newBlack,
                            unfocusedTextColor = newBlack
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Static_Bottom(modifier = Modifier.fillMaxWidth().height(80.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .fillMaxHeight(.7f)
                            .background(newBlue, RoundedCornerShape(8.dp))
                            .noRippleClickable {
                                if (manual_add_No.isNotEmpty()) {
                                    data.forEach { it.isSelected.value = false }
                                    constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                        it.copy(property_Min_No_Of_Seats = manual_add_No)
                                    }
                                }
                                manual_add = false
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Submit", color = Color.White)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PP_Max_No_of_Seats(data: List<Chips_Items_DC>) {

    var manual_add by remember { mutableStateOf(false) }
    var manual_add_No by remember { mutableStateOf("") }

    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    LaunchedEffect(flowData.value?.property_Max_No_Of_Seats) {
        val selectedValue = flowData.value?.property_Max_No_Of_Seats ?: onDraft.property_Max_No_Of_Seats

        if (selectedValue != null) {
            val matchingChip = data.find { it.title == selectedValue }

            if (matchingChip != null) {
                data.forEach { it.isSelected.value = false }
                matchingChip.isSelected.value = true
            } else {
                data.forEach { it.isSelected.value = false }
            }
        }
    }

    val selectedFromChip = data.find { it.isSelected.value }?.title
    val customValue = flowData.value?.property_Max_No_Of_Seats
    val displayValue = if (selectedFromChip != null) null else customValue


    var focusManager = LocalFocusManager.current
    var keyboardController = LocalSoftwareKeyboardController.current



    Column {
        Text("Max. No. of Seats", fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))
        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow(modifier = Modifier.fillMaxWidth()) {
            data.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (item.isSelected.value) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                            if (item.isSelected.value) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .noRippleClickable {
                            data.forEach { it.isSelected.value = false }
                            item.isSelected.value = true
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_Max_No_Of_Seats = item.title)
                            }
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        item.title,
                        color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 8.dp)
                    .wrapContentSize()
                    .background(
                        if (displayValue.isNullOrEmpty()) Color.White else newBlue,
                        RoundedCornerShape(4.dp)
                    )
                    .border(1.dp, newGray, RoundedCornerShape(4.dp))
                    .noRippleClickableWithScale(MutableInteractionSource()) {
                        manual_add = true
                        manual_add_No = displayValue ?: ""
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                if (displayValue.isNullOrEmpty()) {
                    Text("+ Add")
                } else {
                    Row(
                        modifier = Modifier.background(newBlue),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = displayValue, fontSize = constants.textUnit(14), color = Color.White)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            painter = painterResource(R.drawable.arrowdown),
                            "",
                            modifier = Modifier.size(14.dp),
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }

    if (manual_add) {
        ModalBottomSheet(
            onDismissRequest = {
                manual_add = false
                manual_add_No = ""
            },
            containerColor = newWhite,
            sheetGesturesEnabled = false
        ) {
            Column {
                Text("Enter Max. No. of Seats", modifier = Modifier.padding(horizontal = 16.dp))
                Spacer(modifier = Modifier.padding(8.dp))

                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .border(1.dp, newGray, RoundedCornerShape(8.dp))
                ) {
                    TextField(
                        value = manual_add_No,
                        onValueChange = {
                            if (it.length <= 3) {
                                manual_add_No = it.filter { char -> char.isDigit() }
                            }
                                        },
                        placeholder = { Text("Enter number") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            }
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedTextColor = newBlack,
                            unfocusedTextColor = newBlack
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Static_Bottom(modifier = Modifier.fillMaxWidth().height(80.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .fillMaxHeight(.7f)
                            .background(newBlue, RoundedCornerShape(8.dp))
                            .noRippleClickable {
                                if (manual_add_No.isNotEmpty()) {
                                    data.forEach { it.isSelected.value = false }
                                    constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                        it.copy(property_Max_No_Of_Seats = manual_add_No)
                                    }
                                }
                                manual_add = false
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Submit", color = Color.White)
                    }
                }
            }
        }
    }
}

// PART 1: PP_No_of_Cabins through PP_Washroom_Details

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PP_No_of_Cabins(data: List<Chips_Items_DC>) {

    var manual_add by remember { mutableStateOf(false) }
    var manual_add_No by remember { mutableStateOf("") }

    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    LaunchedEffect(flowData.value?.property_No_Of_Cabins) {
        val selectedValue = flowData.value?.property_No_Of_Cabins ?: onDraft.property_No_Of_Cabins

        if (selectedValue != null) {
            val matchingChip = data.find { it.title == selectedValue }

            if (matchingChip != null) {
                data.forEach { it.isSelected.value = false }
                matchingChip.isSelected.value = true
            } else {
                data.forEach { it.isSelected.value = false }
            }
        }
    }

    val selectedFromChip = data.find { it.isSelected.value }?.title
    val customValue = flowData.value?.property_No_Of_Cabins
    val displayValue = if (selectedFromChip != null) null else customValue


    var focusManager = LocalFocusManager.current
    var keyboardController = LocalSoftwareKeyboardController.current



    Column {
        Text("No. of Cabins", fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))
        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow(modifier = Modifier.fillMaxWidth()) {
            data.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (item.isSelected.value) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                            if (item.isSelected.value) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .noRippleClickable {
                            data.forEach { it.isSelected.value = false }
                            item.isSelected.value = true
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_No_Of_Cabins = item.title)
                            }
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        item.title,
                        color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 8.dp)
                    .wrapContentSize()
                    .background(
                        if (displayValue.isNullOrEmpty()) Color.White else newBlue,
                        RoundedCornerShape(4.dp)
                    )
                    .border(1.dp, newGray, RoundedCornerShape(4.dp))
                    .noRippleClickableWithScale(MutableInteractionSource()) {
                        manual_add = true
                        manual_add_No = displayValue ?: ""
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                if (displayValue.isNullOrEmpty()) {
                    Text("+ Add")
                } else {
                    Row(
                        modifier = Modifier.background(newBlue),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = displayValue, fontSize = constants.textUnit(14), color = Color.White)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            painter = painterResource(R.drawable.arrowdown),
                            "",
                            modifier = Modifier.size(14.dp),
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }

    if (manual_add) {
        ModalBottomSheet(
            onDismissRequest = {
                manual_add = false
                manual_add_No = ""
            },
            containerColor = newWhite,
            sheetGesturesEnabled = false
        ) {
            Column {
                Text("Enter No. of Cabins", modifier = Modifier.padding(horizontal = 16.dp))
                Spacer(modifier = Modifier.padding(8.dp))

                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .border(1.dp, newGray, RoundedCornerShape(8.dp))
                ) {
                    TextField(
                        value = manual_add_No,
                        onValueChange = {
                            if (it.length <= 3) {
                                manual_add_No = it.filter { char -> char.isDigit() }
                            }
                                        },
                        placeholder = { Text("Enter number") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword
                        ),
                        keyboardActions = KeyboardActions (
                            onDone = {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            }
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedTextColor = newBlack,
                            unfocusedTextColor = newBlack
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Static_Bottom(modifier = Modifier.fillMaxWidth().height(80.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .fillMaxHeight(.7f)
                            .background(newBlue, RoundedCornerShape(8.dp))
                            .noRippleClickable {
                                if (manual_add_No.isNotEmpty()) {
                                    data.forEach { it.isSelected.value = false }
                                    constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                        it.copy(property_No_Of_Cabins = manual_add_No)
                                    }
                                }
                                manual_add = false
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Submit", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun PP_Reception_Area(data: List<Chips_Items_DC>) {

    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    LaunchedEffect(flowData.value?.property_Reception) {
        val selectedValue = flowData.value?.property_Reception ?: onDraft.property_Reception

        if (selectedValue != null) {
            data.forEach { it.isSelected.value = false }
            val matchingChip = data.find { it.title == selectedValue }
            matchingChip?.isSelected?.value = true
        }
    }

    Column {
        Text("Reception Area", fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))
        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow(modifier = Modifier.fillMaxWidth()) {
            data.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (item.isSelected.value) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                            if (item.isSelected.value) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .noRippleClickable {
                            data.forEach { it.isSelected.value = false }
                            item.isSelected.value = true
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_Reception = item.title)
                            }
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        item.title,
                        color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }
        }
    }
}

@Composable
fun PP_Pantry(data: List<Chips_Items_DC>) {

    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    LaunchedEffect(flowData.value?.property_Pantry) {
        val selectedValue = flowData.value?.property_Pantry ?: onDraft.property_Pantry

        if (selectedValue != null) {
            data.forEach { it.isSelected.value = false }
            val matchingChip = data.find { it.title == selectedValue }
            matchingChip?.isSelected?.value = true
        }
    }

    Column {
        Text("Pantry", fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))
        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow(modifier = Modifier.fillMaxWidth()) {
            data.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (item.isSelected.value) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                            if (item.isSelected.value) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .noRippleClickable {
                            data.forEach { it.isSelected.value = false }
                            item.isSelected.value = true
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_Pantry = item.title)
                            }
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        item.title,
                        color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }
        }
    }
}

@Composable
fun PP_Central_AC(data: List<Chips_Items_DC>) {

    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    LaunchedEffect(flowData.value?.property_Central_AC) {
        val selectedValue = flowData.value?.property_Central_AC ?: onDraft.property_Central_AC

        if (selectedValue != null) {
            data.forEach { it.isSelected.value = false }
            val matchingChip = data.find { it.title == selectedValue }
            matchingChip?.isSelected?.value = true
        }
    }

    Column {
        Text("Central AC", fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))
        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow(modifier = Modifier.fillMaxWidth()) {
            data.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (item.isSelected.value) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                            if (item.isSelected.value) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .noRippleClickable {
                            data.forEach { it.isSelected.value = false }
                            item.isSelected.value = true
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_Central_AC = item.title)
                            }
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        item.title,
                        color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }
        }
    }
}

@Composable
fun PP_Oxygen_Duct(data: List<Chips_Items_DC>) {

    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    LaunchedEffect(flowData.value?.property_Oxygen_Duct) {
        val selectedValue = flowData.value?.property_Oxygen_Duct ?: onDraft.property_Oxygen_Duct

        if (selectedValue != null) {
            data.forEach { it.isSelected.value = false }
            val matchingChip = data.find { it.title == selectedValue }
            matchingChip?.isSelected?.value = true
        }
    }

    Column {
        Text("Oxygen Duct", fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))
        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow(modifier = Modifier.fillMaxWidth()) {
            data.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (item.isSelected.value) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                            if (item.isSelected.value) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .noRippleClickable {
                            data.forEach { it.isSelected.value = false }
                            item.isSelected.value = true
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_Oxygen_Duct = item.title)
                            }
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        item.title,
                        color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }
        }
    }
}

@Composable
fun PP_UPS(data: List<Chips_Items_DC>) {

    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    LaunchedEffect(flowData.value?.property_UPS) {
        val selectedValue = flowData.value?.property_UPS ?: onDraft.property_UPS

        if (selectedValue != null) {
            data.forEach { it.isSelected.value = false }
            val matchingChip = data.find { it.title == selectedValue }
            matchingChip?.isSelected?.value = true
        }
    }

    Column {
        Text("UPS", fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))
        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow(modifier = Modifier.fillMaxWidth()) {
            data.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (item.isSelected.value) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                            if (item.isSelected.value) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .noRippleClickable {
                            data.forEach { it.isSelected.value = false }
                            item.isSelected.value = true
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_UPS = item.title)
                            }
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        item.title,
                        color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }
        }
    }
}

@Composable
fun PP_Fire_Safety_Measures(data: List<Chips_Items_DC>) {

    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    LaunchedEffect(flowData.value?.property_Fire_Safety) {
        val selectedTitles = flowData.value?.property_Fire_Safety ?: onDraft.property_Fire_Safety

        if (selectedTitles.isNotEmpty()) {
            data.forEach { chip ->
                chip.isSelected.value = chip.title in selectedTitles
            }
        }
    }

    Column {
        Text("Fire Safety Measures", fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))
        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow(modifier = Modifier.fillMaxWidth()) {
            data.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (item.isSelected.value) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                            if (item.isSelected.value) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .noRippleClickable {
                            item.isSelected.value = !item.isSelected.value
                            val selected = data.filter { it.isSelected.value }.map { it.title }
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_Fire_Safety = selected)
                            }
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        item.title,
                        color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }
        }
    }
}

@Composable
fun PP_Lifts(data: List<Chips_Items_DC>) {

    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    LaunchedEffect(flowData.value?.property_Lifts) {
        val selectedValue = flowData.value?.property_Lifts ?: onDraft.property_Lifts

        if (selectedValue != null) {
            data.forEach { it.isSelected.value = false }
            val matchingChip = data.find { it.title == selectedValue }
            matchingChip?.isSelected?.value = true
        }
    }

    Column {
        Text("Lifts", fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))
        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow(modifier = Modifier.fillMaxWidth()) {
            data.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (item.isSelected.value) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                            if (item.isSelected.value) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .noRippleClickable {
                            data.forEach { it.isSelected.value = false }
                            item.isSelected.value = true
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_Lifts = item.title)
                            }
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        item.title,
                        color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }
        }
    }
}

@Composable
fun PP_Is_your_office_fire_NOC_Certified() {
    val options = listOf("Yes", "No")
    var selectedOption by remember { mutableStateOf<String?>(null) }

    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    LaunchedEffect(flowData.value?.property_NOC_Certified) {
        selectedOption = flowData.value?.property_NOC_Certified ?: onDraft.property_NOC_Certified
    }

    Column {
        Text("Is your office fire NOC Certified", fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))
        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            options.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .wrapContentWidth()
                        .noRippleClickable {
                            selectedOption = option
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_NOC_Certified = option)
                            }
                        }
                        .padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = selectedOption == option,
                        onClick = {
                            selectedOption = option
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_NOC_Certified = option)
                            }
                        },
                        colors = RadioButtonDefaults.colors(selectedColor = newBlue)
                    )
                    Text(text = option, modifier = Modifier.padding(start = 8.dp))
                }
            }
        }
    }
}

@Composable
fun PP_Occupancy_Certificate() {
    val options = listOf("Yes", "No")
    var selectedOption by remember { mutableStateOf<String?>(null) }

    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    LaunchedEffect(flowData.value?.property_Occupancy) {
        selectedOption = flowData.value?.property_Occupancy ?: onDraft.property_Occupancy
    }

    Column {
        Text("Occupancy Certificate", fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))
        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            options.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .wrapContentWidth()
                        .noRippleClickable {
                            selectedOption = option
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_Occupancy = option)
                            }
                        }
                        .padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = selectedOption == option,
                        onClick = {
                            selectedOption = option
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_Occupancy = option)
                            }
                        },
                        colors = RadioButtonDefaults.colors(selectedColor = newBlue)
                    )
                    Text(text = option, modifier = Modifier.padding(start = 8.dp))
                }
            }
        }
    }
}

@Composable
fun PP_Washroom_Details(data: List<Chips_Items_DC>) {

    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    LaunchedEffect(flowData.value?.property_WashRoom) {
        val selectedTitles = flowData.value?.property_WashRoom ?: onDraft.property_WashRoom ?: emptyList()

        data.forEach { chip ->
            chip.isSelected.value = chip.title in selectedTitles
        }
    }

    Column {
        Text("Washroom Details", fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))
        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow(modifier = Modifier.fillMaxWidth()) {
            data.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (item.isSelected.value) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                            if (item.isSelected.value) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .noRippleClickable {
                            item.isSelected.value = !item.isSelected.value
                            val selected = data.filter { it.isSelected.value }.map { it.title }
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_WashRoom = selected)
                            }
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        item.title,
                        color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }
        }
    }
}


///// neww end



@Composable
fun PP_Pantry_Size(){

    val areaMeasurements = constants.AREA_UNITS
        //listOf("m²", "sq ft", "sq yd", "acre", "hectare")

    /// area

    var expanded by remember { mutableStateOf(false) }
//    var selectedDimension by remember { mutableStateOf(areaMeasurements.first()) }
    var value by remember { mutableStateOf("") }


    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    var formStateSuperBu = remember { mutableStateOf(flowData.value?.property_Pantry_Size) }
    var selectedDimension by remember { mutableStateOf(flowData.value?.pantry_size_unit) }




    var focusManager = LocalFocusManager.current
    var keyboardController = LocalSoftwareKeyboardController.current



    Column {
        Text("Pantry Size", fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(1.dp, newGray, RoundedCornerShape(8.dp))
        )
        {
            // Numeric input
            TextField(
                value = formStateSuperBu.value ?:""
                ,onValueChange = { newValue ->
                    val filteredValue = newValue.filter { it.isDigit() }

                    // if (numericValue <= 50_00_00_000) { // 50 crores in numeric form
                    formStateSuperBu.value = filteredValue

                },
                placeholder = { Text("Enter area length", fontSize = constants.textUnit(12)) },
                modifier = Modifier
                    .weight(8f)
                    .fillMaxHeight(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
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

            VerticalDivider(
                modifier = Modifier
                    .fillMaxHeight(), thickness = 1.dp,
                color = newGray
            )

            // Dropdown for units
            Box(
                modifier = Modifier
                    .weight(2f)
                    .noRippleClickable{ expanded = true },
                contentAlignment = Alignment.Center
            )
            {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = selectedDimension ?:"",
                        fontSize = constants.textUnit(12),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.arrowdown),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = newBlack
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    containerColor = newWhite
                ) {
                    areaMeasurements.forEach { unit ->
                        DropdownMenuItem(
                            text = { Text(unit) },
                            onClick = {
                                selectedDimension = unit
                                constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                    it.copy(pantry_size_unit = unit)
                                }
                                expanded = false
                            },
                            colors = MenuDefaults.itemColors(textColor = newBlack)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.padding(8.dp))

    }
}





@Composable
fun PP_Shop_facade(isError: Boolean){

    val areaMeasurements = constants.AREA_UNITS
        //listOf("m", "cm", "ft", "in", "yd")


    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    var value = remember { mutableStateOf(flowData.value?.property_Facade_Height) }
    var value2 = remember { mutableStateOf(flowData.value?.property_Facade_Width) }


    /// area dimension length

    var expanded by remember { mutableStateOf(false) }
//    var selectedDimension by remember { mutableStateOf(areaMeasurements.first()) }
    var selectedDimension2 by remember { mutableStateOf(flowData.value?.facade_height_unit) }
    //var value by remember { mutableStateOf("") }

    // area dimension width

    var expanded2 by remember { mutableStateOf(false) }
//    var selectedDimension2 by remember { mutableStateOf(areaMeasurements.first()) }
    var selectedDimension by remember { mutableStateOf(flowData.value?.facade_width_unit) }



    var focusManager = LocalFocusManager.current
    var keyboardController = LocalSoftwareKeyboardController.current



    Column {
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = newBlack)){
                    append("Shop facade")
                }
                withStyle(style = SpanStyle(color = Color.Red)){
                    append("*")
                }
            }
            , fontSize = constants.textUnit(16)
            , fontFamily = constants.fontFamily(1)
        )
       // Text("Shop facade" , fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))

        Spacer(modifier = Modifier.height(8.dp))

        Text("Facade Width", fontWeight = FontWeight.Medium)

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(1.dp, if (isError) Color.Red else newGray, RoundedCornerShape(8.dp))
        )
        {
            // Numeric input
            TextField(
                value = value.value ?: "",
                onValueChange = {

                    value.value = it
                },
                placeholder = { Text("Enter Facade Width", fontSize = constants.textUnit(12)) },
                modifier = Modifier
                    .weight(8f)
                    .fillMaxHeight(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                ),
                keyboardActions = KeyboardActions (
                    onDone = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
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

            VerticalDivider(
                modifier = Modifier
                    .fillMaxHeight(), thickness = 1.dp,
                color = newGray
            )

            // Dropdown for units
            Box(
                modifier = Modifier
                    .weight(2f)
                    .noRippleClickable{ expanded = true },
                contentAlignment = Alignment.Center
            )
            {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = selectedDimension ?:"",
                        fontSize = constants.textUnit(12),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.arrowdown),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = newBlack
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    containerColor = newWhite
                ) {
                    areaMeasurements.forEach { unit ->
                        DropdownMenuItem(
                            text = { Text(unit) },
                            onClick = {
                                selectedDimension = unit
                                constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                    it.copy(facade_width_unit = unit)
                                }
                                expanded = false
                            },
                            colors = MenuDefaults.itemColors(textColor = newBlack)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Text("Facade Height", fontWeight = FontWeight.Medium)

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(1.dp, if (isError) Color.Red else newGray, RoundedCornerShape(8.dp))
        )
        {
            // Numeric input
            TextField(
                value = value2.value ?: "",
                onValueChange = {

                    value2.value = it
                },
                placeholder = { Text("Enter Facade Height", fontSize = constants.textUnit(12)) },
                modifier = Modifier
                    .weight(8f)
                    .fillMaxHeight(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
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

            VerticalDivider(
                modifier = Modifier
                    .fillMaxHeight(), thickness = 1.dp,
                color = newGray
            )

            // Dropdown for units
            Box(
                modifier = Modifier
                    .weight(2f)
                    .noRippleClickable{ expanded2 = true },
                contentAlignment = Alignment.Center
            )
            {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = selectedDimension2 ?:"",
                        fontSize = constants.textUnit(12),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.arrowdown),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = newBlack
                    )
                }

                DropdownMenu(
                    expanded = expanded2,
                    onDismissRequest = { expanded2 = false },
                    containerColor = newWhite
                ) {
                    areaMeasurements.forEach { unit ->
                        DropdownMenuItem(
                            text = { Text(unit) },
                            onClick = {
                                selectedDimension2 = unit
                                constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                    it.copy(facade_height_unit = unit)
                                }
                                expanded2 = false
                            },
                            colors = MenuDefaults.itemColors(textColor = newBlack)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.padding(8.dp))
    }
}




@Composable
fun PP_authority_property_Approved(data:List<Chips_Items_DC>){

//    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()


//    LaunchedEffect(Unit) {
//        if (constants.PostProperty_ViewModel.get_Post_Form_Flow() != -1) {
//            val selectedTitles = onDraft.property_Authority_Approved
//
//            data.forEach { chip ->
//                chip.isSelected.value = chip.title in selectedTitles
//            }
//        }
//    }

    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    LaunchedEffect(flowData.value?.property_Authority_Approved) {
        val selectedValue = flowData.value?.property_Authority_Approved
            ?: onDraft.property_Authority_Approved

        if (selectedValue != null) {
            val matchingChip = data.find { it.title == selectedValue }
            if (matchingChip != null) {
                data.forEach { it.isSelected.value = false }
                matchingChip.isSelected.value = true
            }
        }
    }

    val selectedValue = flowData.value?.property_Authority_Approved

    Column {
        Text("Which authority the property is approved by?" , fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))

        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow (
            modifier = Modifier
                .fillMaxWidth()
        ){
            data.forEachIndexed { index , item ->
                val isSelected = selectedValue == item.title
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .wrapContentSize()
                        .background(
                            if (isSelected) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                             if (isSelected) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        ).noRippleClickable{
                            //item.isSelected.value = !item.isSelected.value


                            data.forEach { it.isSelected.value = false } // unselect all
                            item.isSelected.value = true // select clicked one

                            // 🔹 Update ViewModel with only one selected
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_Authority_Approved = item.title)
                            }
                        }
                        .padding(8.dp)
                    , contentAlignment = Alignment.Center
                ){
                    Text(item.title, color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2))
                }

                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    }
}


/// Reental app




@Composable
fun PP_Available_From(isError: Boolean){
    Column {
        Text(buildAnnotatedString {
            withStyle(style = SpanStyle(color = newBlack)) { append("Available From") }
            withStyle(style = SpanStyle(color = Color.Red)) { append(" *") }
        }, fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))

        Spacer(modifier = Modifier.padding(8.dp))

        CalendarPickerWithBox(isError)

        constants.spacer(4)

        if (isError) {
            Text("Select Available From", color = Color.Red, fontSize = constants.textUnit(12))
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarPickerWithBox(isError: Boolean) {
    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    val today = LocalDate.now()
    val maxDate = today.plusMonths(3)

    val selectedDateFromVm = flowData.value?.property_availability_from

    // Parse the date from ViewModel
    val parsedDate = remember(selectedDateFromVm) {
        if (!selectedDateFromVm.isNullOrBlank()) {
            runCatching {
                LocalDate.parse(selectedDateFromVm, DateTimeFormatter.ofPattern("dd-MM-yy"))
            }.getOrNull()
        } else null
    }

    // Display text for the button
    val displayText = parsedDate?.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) ?: "Select Date"

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(
                    1.dp,
                    if (isError) Color.Red else Color(0xffCECECE),
                    RoundedCornerShape(6.dp)
                )
                .padding(horizontal = 12.dp)
                .noRippleClickable { expanded = true },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(displayText)
            Image(
                painter = painterResource(R.drawable.postformcalenderpick),
                contentDescription = ""
            )
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(380.dp)
                .wrapContentHeight(),  // Increased height
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Calender_View(
                    currentDate = today,
                    maxDate = maxDate,
                    selectedDate = parsedDate,
                    onCancel = { expanded = false },
                    onSet = { day, month, year ->
                        val date = LocalDate.of(year, month, day)
                        val formatted = date.format(DateTimeFormatter.ofPattern("dd-MM-yy"))

                        constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                            it.copy(property_availability_from = formatted)
                        }
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun Calender_View(
    currentDate: LocalDate,
    maxDate: LocalDate,
    selectedDate: LocalDate?,
    onCancel: () -> Unit,
    onSet: (Int, Int, Int) -> Unit
) {
    // Use selectedDate if available, otherwise use currentDate
    val initialDate = selectedDate ?: currentDate

    var selectedDay by remember { mutableIntStateOf(initialDate.dayOfMonth) }
    var selectedMonth by remember { mutableIntStateOf(initialDate.monthValue) }
    var selectedYear by remember { mutableIntStateOf(initialDate.year) }

    val minYear = currentDate.year
    val maxYear = maxDate.year
    val yearCount = maxYear - minYear + 1

    // Calculate allowed months based on selected year
    val minMonth = if (selectedYear == currentDate.year) currentDate.monthValue else 1
    val maxMonth = if (selectedYear == maxDate.year) maxDate.monthValue else 12
    val allowedMonths = (maxMonth - minMonth + 1).coerceAtLeast(1)

    // Clamp month when year changes
    LaunchedEffect(selectedYear) {
        if (selectedMonth < minMonth) selectedMonth = minMonth
        if (selectedMonth > maxMonth) selectedMonth = maxMonth
    }

    // Clamp day when month changes and validate against maxDate
    val maxDayInMonth = YearMonth.of(selectedYear, selectedMonth).lengthOfMonth()
    LaunchedEffect(selectedMonth, selectedYear) {
        // First clamp to month length
        if (selectedDay > maxDayInMonth) {
            selectedDay = maxDayInMonth
        }

        // Then check if the date exceeds maxDate
        val currentSelectedDate = LocalDate.of(selectedYear, selectedMonth, selectedDay)
        if (currentSelectedDate.isAfter(maxDate)) {
            // Set to the last valid day
            val lastValidDate = if (selectedYear == maxDate.year && selectedMonth == maxDate.monthValue) {
                maxDate.dayOfMonth
            } else {
                maxDayInMonth
            }
            selectedDay = lastValidDate.coerceAtMost(maxDayInMonth)
        }

        // Also check if date is before currentDate
        if (currentSelectedDate.isBefore(currentDate)) {
            selectedDay = currentDate.dayOfMonth
            selectedMonth = currentDate.monthValue
            selectedYear = currentDate.year
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Pager Section with explicit height
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)  // Fixed height instead of weight
                .border(2.dp, newGray, RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp),  // Match the pager height
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Calculate max day based on current selections
                val calculatedMaxDay = if (selectedYear == maxDate.year && selectedMonth == maxDate.monthValue) {
                    maxDate.dayOfMonth
                } else {
                    YearMonth.of(selectedYear, selectedMonth).lengthOfMonth()
                }

                // Force recomposition with key when dependencies change
                key(selectedMonth, selectedYear, calculatedMaxDay) {
                    Days_Pager_View(
                        initialDay = selectedDay,
                        maxDay = calculatedMaxDay,
                        onDaySelected = { selectedDay = it }
                    )
                }

                key(selectedYear, minMonth, maxMonth) {
                    Months_Pager_View(
                        startMonth = minMonth,
                        monthsCount = allowedMonths,
                        initialMonth = selectedMonth,
                        onMonthSelected = { selectedMonth = it }
                    )
                }

                key(minYear, maxYear) {
                    YearsPager_View(
                        minYear = minYear,
                        initialYear = selectedYear,
                        yearCount = yearCount,
                        onYearSelected = { selectedYear = it }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onCancel,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEEEEEE)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Cancel",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Brush.verticalGradient(newPurpleGradient))
                    .border(
                        1.dp,
                        Brush.verticalGradient(newPurpleGradientBorder),
                        RoundedCornerShape(4.dp)
                    )
                    .noRippleClickable {
                        onSet(selectedDay, selectedMonth, selectedYear)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Set",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun Days_Pager_View(
    initialDay: Int,
    maxDay: Int = 31,
    onDaySelected: (Int) -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = (initialDay - 1).coerceAtLeast(0),
        pageCount = { maxDay }
    )

    LaunchedEffect(pagerState.settledPage) {
        onDaySelected(pagerState.settledPage + 1)
    }

    Box(
        modifier = Modifier
            .height(210.dp)
            .width(70.dp)
    ) {
        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            pageSize = PageSize.Fixed(70.dp),
            contentPadding = PaddingValues(vertical = 70.dp)
        ) { page ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = String.format("%02d", page + 1),
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = if (pagerState.currentPage == page) FontWeight.Bold else FontWeight.Normal
                )
            }
        }

        // Top divider
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .offset(y = (-35).dp),
            color = Color.Gray,
            thickness = 1.dp
        )

        // Bottom divider
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .offset(y = 35.dp),
            color = Color.Gray,
            thickness = 1.dp
        )
    }
}

@Composable
fun Months_Pager_View(
    startMonth: Int,
    monthsCount: Int,
    initialMonth: Int,
    onMonthSelected: (Int) -> Unit
) {
    val months = listOf(
        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )

    val pagerState = rememberPagerState(
        initialPage = (initialMonth - startMonth).coerceAtLeast(0),
        pageCount = { monthsCount.coerceAtLeast(1) }
    )

    LaunchedEffect(pagerState.settledPage, startMonth) {
        onMonthSelected(startMonth + pagerState.settledPage)
    }

    Box(
        modifier = Modifier
            .height(210.dp)
            .width(80.dp)
    ) {
        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            pageSize = PageSize.Fixed(70.dp),
            contentPadding = PaddingValues(vertical = 70.dp)
        ) { page ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = months[startMonth + page - 1],
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = if (pagerState.currentPage == page) FontWeight.Bold else FontWeight.Normal
                )
            }
        }

        // Top divider
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .offset(y = (-35).dp),
            color = Color.Gray,
            thickness = 1.dp
        )

        // Bottom divider
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .offset(y = 35.dp),
            color = Color.Gray,
            thickness = 1.dp
        )
    }
}

@Composable
fun YearsPager_View(
    minYear: Int,
    initialYear: Int,
    yearCount: Int,
    onYearSelected: (Int) -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = (initialYear - minYear).coerceAtLeast(0),
        pageCount = { yearCount.coerceAtLeast(1) }
    )

    LaunchedEffect(pagerState.settledPage) {
        onYearSelected(minYear + pagerState.settledPage)
    }

    Box(
        modifier = Modifier
            .height(210.dp)
            .width(70.dp)
    ) {
        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            pageSize = PageSize.Fixed(70.dp),
            contentPadding = PaddingValues(vertical = 70.dp)
        ) { page ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${minYear + page}",
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = if (pagerState.currentPage == page) FontWeight.Bold else FontWeight.Normal
                )
            }
        }

        // Top divider
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .offset(y = (-35).dp),
            color = Color.Gray,
            thickness = 1.dp
        )

        // Bottom divider
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .offset(y = 35.dp),
            color = Color.Gray,
            thickness = 1.dp
        )
    }
}


/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarPickerWithBox(isError: Boolean) {


    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    var leaseamount = remember { mutableStateOf(flowData.value?.property_availability_from) }


    var expanded by remember { mutableStateOf(false) }
     // var selectedDate by remember { mutableStateOf("Select Date") }

    val today = LocalDate.now()
    val maxDate = today.plusMonths(3)

    var selectedDate by remember { mutableStateOf("Select Date") }

    val selectedDateFromVm = flowData.value?.property_availability_from

    val parsedDate = remember(selectedDateFromVm) {
        selectedDateFromVm?.let {
            LocalDate.parse(it, DateTimeFormatter.ofPattern("dd-MM-yy"))
        }
    }



    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(1.dp, if (isError) Color.Red else  Color(0xffCECECE), RoundedCornerShape(6.dp))
                .padding(horizontal = 12.dp)
                .noRippleClickable {
                    expanded = true
                }
            , verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            leaseamount.value?.ifEmpty { selectedDate }?.let { Text(it) }

            Image(
                painter = painterResource(R.drawable.postformcalenderpick),
                contentDescription = "",
                modifier = Modifier
            )
        }



        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(380.dp)
                .height(400.dp)      // IMPORTANT
            , containerColor = Color.White
        )
        {
            // Put your custom UI inside a Box with fixed size
            Box(
                modifier = Modifier
                   // .background(newBlue)
                   // .fillMaxSize()   // We already fixed size above
            ) {
                Card(
                    modifier = Modifier
                        //.fillMaxSize()
                        .width(380.dp)
                        .height(400.dp)
                    ,shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Calender_View(
                        currentDate = today,
                        maxDate = maxDate,
                        onCancel = { expanded = false },
                        selectedDate = parsedDate,
                        onSet = { day, month, year ->
                            val formatted = LocalDate.of(year, month, day)
                                .format(DateTimeFormatter.ofPattern("dd-MM-yy"))

                            selectedDate = formatted
                            expanded = false

                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_availability_from = formatted)
                            }
                        }
                    )

//                    Calender_View(
//                        onCancel = { expanded = false },
//                        onSet = { day, month, year ->
//                            val months = listOf(
//                                "January", "February", "March", "April", "May", "June",
//                                "July", "August", "September", "October", "November", "December"
//                            )
//                            selectedDate = String.format("%02d %s %d", day, months[month - 1], year)
//                            expanded = false
//
//                            // 🔹 Update ViewModel with only one selected
//                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
//                                it.copy(property_availability_from = selectedDate)
//                            }
//                        }
//                    )
                }
            }
        }
    }
}*/



/*@Composable
fun Calender_View(
    currentDate: LocalDate,
    maxDate: LocalDate,
    selectedDate: LocalDate?, // ✅ NEW
    onCancel: () -> Unit,
    onSet: (Int, Int, Int) -> Unit
)
 {
    var selectedDay by remember { mutableIntStateOf(currentDate.dayOfMonth) }
    var selectedMonth by remember { mutableIntStateOf(currentDate.monthValue) }
    var selectedYear by remember { mutableIntStateOf(currentDate.year) }

    val allowedMonths = ChronoUnit.MONTHS.between(
        YearMonth.from(currentDate),
        YearMonth.from(maxDate)
    ).toInt() + 1

    val monthPagerState = rememberPagerState(
        initialPage = currentDate.monthValue - 1,
        pageCount = { allowedMonths }
    )

    val yearPagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { 1 }   // year can't change beyond today-year
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Pager Section
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .border(2.dp,newGray, RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Days_Pager_View(
                    initialDay = selectedDay - 1,
                    onDaySelected = { selectedDay = it }
                )
                Months_Pager_View(
                    initialMonth = selectedMonth - 1,
                    onMonthSelected = { selectedMonth = it },
                    monthsLimit = 3
                )
                YearsPager_View(
                    initialYear = selectedYear,
                    onYearSelected = { selectedYear = it }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Buttons Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Button(
                onClick = onCancel,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEEEEEE)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Cancel",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Brush.verticalGradient(newPurpleGradient))
                    .border(1.dp , Brush.verticalGradient(newPurpleGradientBorder) ,RoundedCornerShape(4.dp) )
                    .noRippleClickable {
                        onSet(selectedDay, selectedMonth, selectedYear)
                    }
                , contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Set",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

        }

        constants.spacer(6)
    }
}*/


/*@Composable
fun Days_Pager_View(
    initialDay: Int = 4,
    onDaySelected: (Int) -> Unit = {}
) {
    val pagerState = rememberPagerState(
        initialPage = initialDay,
        pageCount = { 31 }
    )

    LaunchedEffect(pagerState.settledPage) {
        onDaySelected(pagerState.settledPage + 1)
    }

    Box(
        modifier = Modifier
            .width(70.dp)
            .height(210.dp)  // Total height for 3 items
    ) {
        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            //beyondBoundsPageCount = 1,
            pageSize = PageSize.Fixed(70.dp),
            contentPadding = PaddingValues(vertical = 70.dp)  // ADD THIS - centers current item
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                contentAlignment = Alignment.Center
            ) {
                val offsetFromCenter = (page - pagerState.currentPage).toFloat()
                val alpha = 1f - (abs(offsetFromCenter) * 0.7f).coerceAtMost(0.8f)

                Text(
                    text = String.format("%02d", page + 1),
                    color = Color.Black.copy(alpha = alpha),
                    fontSize = constants.textUnit(if (abs(offsetFromCenter) < 0.5f) 16 else 14),
                    fontWeight = if (abs(offsetFromCenter) < 0.5f) FontWeight.Bold else FontWeight.Normal
                )
            }
        }

        // Top border line - above center item
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .offset(y = (-35).dp),
            color = newBlack,
            thickness = 2.dp
        )

        // Bottom border line - below center item
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .offset(y = 35.dp),
            color = newBlack,
            thickness = 2.dp
        )
    }
}

@Composable
fun Months_Pager_View(
    monthsLimit: Int,
    initialMonth: Int,
    onMonthSelected: (Int) -> Unit
) {
    val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    val pagerState = rememberPagerState(
        initialPage = initialMonth - 1,
        pageCount = { monthsLimit }
    )

    LaunchedEffect(pagerState.settledPage) {
        onMonthSelected(pagerState.settledPage + 1)
    }

    Box(
        modifier = Modifier
            .width(70.dp)
            .height(210.dp)  // Total height for 3 items
    )
    {
        VerticalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize(),
            pageSize = PageSize.Fixed(70.dp),
            contentPadding = PaddingValues(vertical = 70.dp)
        ) { page ->
            val offset = (page - pagerState.currentPage).toFloat()
            val alpha = 1f - (abs(offset) * 0.7f).coerceAtMost(0.8f)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    months[page],
                    color = Color.Black.copy(alpha),
                    fontSize = constants.textUnit(if (abs(offset) < 0.5f) 16 else 14),
                    fontWeight = if (abs(offset) < 0.5f) FontWeight.Bold else FontWeight.Normal
                )
            }
        }

        // Top border line - above center item
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .offset(y = (-35).dp),
            color = newBlack,
            thickness = 2.dp
        )

        // Bottom border line - below center item
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .offset(y = 35.dp),
            color = newBlack,
            thickness = 2.dp
        )
    }


}

@Composable
fun YearsPager_View(
    initialYear: Int = 2025,
    onYearSelected: (Int) -> Unit = {}
) {
    val currentYear = 2024
    val initialPage = initialYear - currentYear

    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { 1 }
    )

    LaunchedEffect(pagerState.settledPage) {
        onYearSelected(currentYear + pagerState.settledPage)
    }

    Box(
        modifier = Modifier
            .width(70.dp)
            .height(210.dp)  // Total height for 3 items
    )
    {
        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            //beyondBoundsPageCount = 1,
            pageSize = PageSize.Fixed(70.dp),
            contentPadding = PaddingValues(vertical = 70.dp)  // ADD THIS - centers current item
        )
        { page ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                contentAlignment = Alignment.Center
            ) {
                val offsetFromCenter = (page - pagerState.currentPage).toFloat()
                val alpha = 1f - (abs(offsetFromCenter) * 0.7f).coerceAtMost(0.8f)

                Text(
                    text = "${currentYear + page}",
                    color = Color.Black.copy(alpha = alpha),
                    fontSize = constants.textUnit(if (abs(offsetFromCenter) < 0.5f) 16 else 14),
                    fontWeight = if (abs(offsetFromCenter) < 0.5f) FontWeight.Bold else FontWeight.Normal
                )
            }
        }

        // Top border line - above center item
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .offset(y = (-35).dp),
            color = newBlack,
            thickness = 2.dp
        )

        // Bottom border line - below center item
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .offset(y = 35.dp),
            color = newBlack,
            thickness = 2.dp
        )
    }
}*/



@Composable
fun PP_AgreementType(data:List<Chips_Items_DC> , isError: Boolean){




    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    var leaseamount = remember { mutableStateOf(flowData.value?.property_for_rent_or_lease) }

    println("DAta agreement type -- ${flowData.value}")

    Column {
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = newBlack)) {
                    append("Agreement Type")
                }
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append("*")
                }
            }
            , fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))

        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow (
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            data.forEachIndexed { index , item ->

                val isSelected = flowData.value?.property_agreement_type == item.title

                Box(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (isSelected) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                            if (isError) errorBorderBrush() else if (isSelected) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        ).noRippleClickable{


                            data.forEach { it.isSelected.value = false } // unselect all
                            item.isSelected.value = true // select clicked one

                            // 🔹 Update ViewModel with only one selected
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_agreement_type = item.title)
                            }

                        }
                        .padding(8.dp)
                    , contentAlignment = Alignment.Center
                ){
                    Text(item.title, color =  newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2))
                }

                Spacer(modifier = Modifier.padding(8.dp))
            }
        }

        constants.spacer(4)

        if (isError) {
            Text("Select Agreement type", color = Color.Red, fontSize = constants.textUnit(12))
        }
    }
}



@Composable
fun PP_FoodPreference(data:List<Chips_Items_DC> , isError: Boolean){



    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    var leaseamount = remember { mutableStateOf(flowData.value?.property_for_rent_or_lease) }


    Column {
        Text(


                    buildAnnotatedString {
                withStyle(style = SpanStyle(color = newBlack)) {
                    append("Food Preferences")
                }
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append("*")
                }
            }
            , fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))

        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow (
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            data.forEachIndexed { index , item ->


                val isSelected = flowData.value?.property_food_preferences == item.title

                Box(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (isSelected) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                            if (isError) errorBorderBrush() else if (isSelected) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .noRippleClickable{
                            //item.isSelected.value = !item.isSelected.value


//                            data.forEach { it.isSelected.value = false } // unselect all
//                            item.isSelected.value = true // select clicked one
//
//                            // 🔹 Update ViewModel with only one selected
//                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
//                                it.copy(property_food_preferences = item.title)
//                            }

                            //item.isSelected.value = !item.isSelected.value
                            data.forEach { it.isSelected.value = false } // unselect all
                            item.isSelected.value = true // select clicked one

                            // 🔹 Update ViewModel with only one selected
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_food_preferences = item.title)
                            }

                        }
                        .padding(8.dp)
                    , contentAlignment = Alignment.Center
                ){
                    Text(item.title, color =  newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2))
                }

                Spacer(modifier = Modifier.padding(8.dp))
            }
        }

        constants.spacer(4)

        if (isError) {
            Text("Select Food preference", color = Color.Red, fontSize = constants.textUnit(12))
        }
    }
}



@Composable
fun PP_Is_this_property_for_Rent_or_Lease(data:List<Chips_Items_DC>){


    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    var leaseamount = remember { mutableStateOf(flowData.value?.property_for_rent_or_lease) }



    Column {
        Text("Is this property for Rent or Lease?" , fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))

        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow (
            modifier = Modifier
                .fillMaxWidth()
        ){
            data.forEachIndexed { index , item ->

                val isSelected = flowData.value?.property_for_rent_or_lease == item.title

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .wrapContentSize()
                        .background(
                            if (isSelected) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                             if (isSelected) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        ).noRippleClickable{
                            //item.isSelected.value = !item.isSelected.value

                            //item.isSelected.value = !item.isSelected.value
//                            data.forEach { it.isSelected.value = false } // unselect all
//                            item.isSelected.value = true // select clicked one
//
//                            // 🔹 Update ViewModel with only one selected


                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_for_rent_or_lease = item.title)
                            }

                            println("DATA REN OR LEASE CLIKED -- ${constants.PostProperty_ViewModel.selected_Options_Form4.value}")

                        }
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                    , contentAlignment = Alignment.Center
                ){
                    Text(item.title, color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2))
                }

                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    }
}



@Composable
fun PP_PetsAllowed(isError: Boolean) {



    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    var leaseamount = remember { mutableStateOf(flowData.value?.property_pets_allowed) }


    val options = listOf("Yes", "No")
    var selectedOption by remember { mutableStateOf<String?>(leaseamount.value) }

    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()


    LaunchedEffect(Unit) {
        if (constants.PostProperty_ViewModel.get_Post_Form_Flow() != -1) {
            val selectedTitles = onDraft.property_pets_allowed

            selectedOption = selectedTitles
        }
    }

    Column {
        Text(


                    buildAnnotatedString {
                withStyle(style = SpanStyle(color = newBlack)) {
                    append("Pets Allowed")
                }
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append("*")
                }
            }
            , fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))

        Spacer(modifier = Modifier.height(4.dp))

        Row (
            modifier = Modifier
                .fillMaxWidth()
            , verticalAlignment = Alignment.CenterVertically
            , horizontalArrangement = Arrangement.Start
        )
        {
            options.forEach { option ->
                //val isSelected = flowData.value?.property_pets_allowed == option
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .wrapContentWidth()
                        .noRippleClickable{ selectedOption = option }
                        .padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = selectedOption == option,
                        onClick = {
                            selectedOption = option
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_pets_allowed = option)
                            }
                        }
                        , colors = RadioButtonDefaults.colors(
                            selectedColor = newBlue
                        )
                    )
                    Text(
                        text = option,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))
            }
        }

        constants.spacer(4)

        if (isError) {
            Text("Select pets allowance option", color = Color.Red, fontSize = constants.textUnit(12))
        }
    }
}


@Composable
fun PP_Property_Condition(data:List<Chips_Items_DC>){


    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    var leaseamount = remember { mutableStateOf(flowData.value?.property_condition) }


    Column {
        Text("Property Condition" , fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(1))

        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow (
            modifier = Modifier
                .fillMaxWidth()
        ){
            data.forEachIndexed { index , item ->

                val isSelected = flowData.value?.property_condition == item.title

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .wrapContentSize()
                        .background(
                            if (isSelected) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                             if (isSelected) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        ).noRippleClickable{
                            //item.isSelected.value = !item.isSelected.value


                            data.forEach { it.isSelected.value = false } // unselect all
                            item.isSelected.value = true // select clicked one

                            // 🔹 Update ViewModel with only one selected
                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(property_condition = item.title)
                            }


                        }
                        .padding(8.dp)
                    , contentAlignment = Alignment.Center
                ){
                    Text(item.title, color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2))
                }

                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    }
}



@Composable
fun PP_Rent(isError: Boolean) {
    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    var rent by remember { mutableStateOf("") }
    var rentNego by remember { mutableStateOf(false) }

    // Initialize from flow or draft
    LaunchedEffect(flowData.value?.rent, flowData.value?.rent_negotiable) {
        rent = flowData.value?.rent ?: onDraft.rent ?: ""
        rentNego = flowData.value?.rent_negotiable ?: onDraft.rent_negotiable ?: false
    }



    var focusManager = LocalFocusManager.current
    var keyboardController = LocalSoftwareKeyboardController.current


    Column {
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = newBlack)) {
                    append("Rent")
                }
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append("*")
                }
            },
            fontSize = constants.textUnit(16),
            fontFamily = constants.fontFamily(1)
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Row(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .background(Color(0xffFFFFFF))
                .border(
                    1.dp,
                    if (isError) Color.Red else Color(0xffCECECE),
                    RoundedCornerShape(6.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(Color(0xffCECECE))
                    .padding(horizontal = 18.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("₹")
            }

            VerticalDivider(color = Color(0xffCECECE))

            TextField(
                value = rent,
                onValueChange = {
                    rent = it.filter { char -> char.isDigit() }
                    constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                        it.copy(rent = rent)
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                            keyboardController?.hide()
                    }
                ),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.weight(8f)
            )
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Checkbox(
                checked = rentNego,
                onCheckedChange = {
                    rentNego = it
                    constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                        it.copy(rent_negotiable = rentNego)
                    }
                }
            )
            Text("Rent Negotiable")
        }

        constants.spacer(4)

        if (isError) {
            Text("Enter Rent Amount", color = Color.Red, fontSize = constants.textUnit(12))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PP_DepositAmount(data: List<Chips_Items_DC>) {
    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    var targetState by remember { mutableStateOf(0) }
    var depositInput by remember { mutableStateOf("") }
    var depositAmount by remember { mutableStateOf("") }

    // Initialize state from flow or draft
    LaunchedEffect(
        flowData.value?.deposit_amount_month_of_rents_type,
        flowData.value?.deposit_amount_month_of_rents,
        flowData.value?.total_deposit
    ) {
        val selectedType = flowData.value?.deposit_amount_month_of_rents_type
            ?: onDraft.deposit_amount_month_of_rents_type

        depositInput = flowData.value?.deposit_amount_month_of_rents
            ?: onDraft.deposit_amount_month_of_rents ?: ""
        depositAmount = flowData.value?.total_deposit
            ?: onDraft.total_deposit ?: ""

        if (selectedType != null) {
            val matchingChip = data.find { it.title == selectedType }
            if (matchingChip != null) {
                data.forEach { it.isSelected.value = false }
                matchingChip.isSelected.value = true

                val index = data.indexOf(matchingChip)
                targetState = when {
                    index == data.size - 1 -> 1
                    index == 1 -> 2
                    else -> 0
                }
            }
        }
    }

    val selectedValue = flowData.value?.deposit_amount_month_of_rents_type


    var focusManager = LocalFocusManager.current
    var keyboardController = LocalSoftwareKeyboardController.current




    Column {
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = newBlack)) {
                    append("Deposit Amount")
                }
                withStyle(style = SpanStyle(color = Color(0xff575757))) {
                    append("(month of rents)")
                }
            },
            fontSize = constants.textUnit(16),
            fontFamily = constants.fontFamily(1)
        )

        constants.spacer(4)

        Text(
            "Deposit is calculated as rent multiplied by the number of months.",
            fontSize = constants.textUnit(12),
            fontFamily = constants.fontFamily(3)
        )

        constants.spacer(4)

        FlowRow(modifier = Modifier.fillMaxWidth()) {
            data.forEachIndexed { index, item ->
                val isSelected = selectedValue == item.title
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (isSelected) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                             if (isSelected) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        ).noRippleClickable {
//                            data.forEach { it.isSelected.value = false }
//                            item.isSelected.value = true

                            targetState = when {
                                index == data.size - 1 -> 1
                                index == 1 -> 2
                                else -> 0
                            }


                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(deposit_amount_month_of_rents_type = item.title)
                            }
                        }
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        item.title,
                        color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }
        }

        AnimatedContent(targetState = targetState) { state ->
            when (state) {
                1 -> {
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xffFFFFFF))
                                .border(1.dp, Color(0xffCECECE), RoundedCornerShape(6.dp))
                        ) {
                            TextField(
                                value = depositInput,
                                onValueChange = {
                                    depositInput = it.filter { char -> char.isDigit() }
                                    constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                        it.copy(deposit_amount_month_of_rents = depositInput)
                                    }
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.NumberPassword
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        focusManager.clearFocus()
                                        keyboardController?.hide()
                                    }
                                ),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                modifier = Modifier.weight(8f)
                            )

                            VerticalDivider(color = Color(0xffCECECE))

                            Row(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .background(Color(0xffCECECE))
                                    .padding(horizontal = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text("Months")
                            }
                        }

                        if (depositInput.isNotEmpty()) {
                            val rent = flowData.value?.rent?.toIntOrNull() ?: 0
                            val months = depositInput.toIntOrNull() ?: 0
                            val total = rent * months

                            LaunchedEffect(total) {
                                depositAmount = total.toString()
                                constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                    it.copy(total_deposit = depositAmount)
                                }
                            }

                            CommonText("Total Deposit : ₹$total", Color(0xff7E7E7E), 12, 1)
                        }
                    }
                }
                2 -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color(0xffFFFFFF))
                            .border(1.dp, Color(0xffCECECE), RoundedCornerShape(6.dp))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxHeight()
                                .background(Color(0xffCECECE))
                                .padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text("₹")
                        }

                        VerticalDivider(color = Color(0xffCECECE))

                        TextField(
                            value = depositAmount,
                            onValueChange = {
                                depositAmount = it.filter { char -> char.isDigit() }
                                constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                    it.copy(total_deposit = depositAmount)
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.NumberPassword
                            ),
                            keyboardActions = KeyboardActions (
                                onDone = {
                                    focusManager.clearFocus()
                                    keyboardController?.hide()
                                }
                            ),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier.weight(8f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PP_Duration_of_Agreement(data: List<Chips_Items_DC>) {
    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    var targetState by remember { mutableStateOf(0) }
    var durationInput by remember { mutableStateOf("") }

    LaunchedEffect(
        flowData.value?.duration_of_agreement_type,
        flowData.value?.duration_of_agreement
    ) {
        val selectedType = flowData.value?.duration_of_agreement_type
            ?: onDraft.duration_of_agreement_type

        durationInput = flowData.value?.duration_of_agreement
            ?: onDraft.duration_of_agreement ?: ""

        if (selectedType != null) {
            val matchingChip = data.find { it.title == selectedType }
            if (matchingChip != null) {
                data.forEach { it.isSelected.value = false }
                matchingChip.isSelected.value = true

                val index = data.indexOf(matchingChip)
                targetState = if (index == data.size - 1) 1 else 0
            }
        }
    }

    val selectedValue = flowData.value?.duration_of_agreement_type


    var focusManager = LocalFocusManager.current
    var keyboardController = LocalSoftwareKeyboardController.current




    Column {
        Text(
            "Duration of Agreement",
            fontSize = constants.textUnit(16),
            fontFamily = constants.fontFamily(1)
        )

        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow(modifier = Modifier.fillMaxWidth()) {
            data.forEachIndexed { index, item ->
                val isSelected = selectedValue == item.title
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (isSelected) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                             if (isSelected) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        ).noRippleClickable {
//                            data.forEach { it.isSelected.value = false }
//                            item.isSelected.value = true
//
                            targetState = if (index == data.size - 1) 1 else 0

                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(duration_of_agreement_type = item.title)
                            }
                        }
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        item.title,
                        color =  newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }
        }

        AnimatedContent(targetState = targetState) { state ->
            when (state) {
                1 -> {
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xffFFFFFF))
                                .border(1.dp, Color(0xffCECECE), RoundedCornerShape(6.dp))
                        ) {
                            TextField(
                                value = durationInput,
                                onValueChange = {
                                    durationInput = it.filter { char -> char.isDigit() }
                                    constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                        it.copy(duration_of_agreement = durationInput)
                                    }
                                },
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        focusManager.clearFocus()
                                        keyboardController?.hide()
                                    }
                                ),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                modifier = Modifier.weight(8f)
                            )

                            VerticalDivider(color = Color(0xffCECECE))

                            Row(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .background(Color(0xffCECECE))
                                    .padding(horizontal = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text("Months")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PP_Lock_in_Period(data: List<Chips_Items_DC>) {
    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    var targetState by remember { mutableStateOf(0) }
    var lockInInput by remember { mutableStateOf("") }

    LaunchedEffect(
        flowData.value?.lock_in_period_type,
        flowData.value?.lock_in_period
    )
    {
        val selectedType = flowData.value?.lock_in_period_type
            ?: onDraft.lock_in_period_type

        lockInInput = flowData.value?.lock_in_period
            ?: onDraft.lock_in_period ?: ""

        if (selectedType != null) {
            val matchingChip = data.find { it.title == selectedType }
            if (matchingChip != null) {
                data.forEach { it.isSelected.value = false }
                matchingChip.isSelected.value = true

                val index = data.indexOf(matchingChip)
                targetState = if (index == data.size - 1) 1 else 0
            }
        }
    }

    val selectedValue = flowData.value?.lock_in_period_type


    var focusManager = LocalFocusManager.current
    var keyboardController = LocalSoftwareKeyboardController.current




    Column {
        Text(
            "Lock-in Period",
            fontSize = constants.textUnit(16),
            fontFamily = constants.fontFamily(1)
        )

        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow(modifier = Modifier.fillMaxWidth()) {
            data.forEachIndexed { index, item ->
                val isSelected = selectedValue == item.title
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (isSelected) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                             if (isSelected) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        ).noRippleClickable {
//                            data.forEach { it.isSelected.value = false }
//                            item.isSelected.value = true

                            targetState = if (index == data.size - 1) 1 else 0

                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(lock_in_period_type = item.title)
                            }
                        }
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        item.title,
                        color =  newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }
        }

        AnimatedContent(targetState = targetState) { state ->
            when (state) {
                1 -> {
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xffFFFFFF))
                                .border(1.dp, Color(0xffCECECE), RoundedCornerShape(6.dp))
                        ) {
                            TextField(
                                value = lockInInput,
                                onValueChange = {
                                    lockInInput = it.filter { char -> char.isDigit() }
                                    constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                        it.copy(lock_in_period = lockInInput)
                                    }
                                },
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        focusManager.clearFocus()
                                        keyboardController?.hide()
                                    }
                                ),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                modifier = Modifier.weight(8f)
                            )

                            VerticalDivider(color = Color(0xffCECECE))

                            Row(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .background(Color(0xffCECECE))
                                    .padding(horizontal = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text("Years")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PP_Notice_Period(data: List<Chips_Items_DC>) {
    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    LaunchedEffect(flowData.value?.notice_period) {
        val selectedValue = flowData.value?.notice_period
            ?: onDraft.notice_period

        if (selectedValue != null) {
            val matchingChip = data.find { it.title == selectedValue }
            if (matchingChip != null) {
                data.forEach { it.isSelected.value = false }
                matchingChip.isSelected.value = true
            }
        }
    }

    val selectedValue = flowData.value?.notice_period


    Column {
        Text(
            "Notice Period",
            fontSize = constants.textUnit(16),
            fontFamily = constants.fontFamily(1)
        )

        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow(modifier = Modifier.fillMaxWidth()) {
            data.forEachIndexed { index, item ->
                val isSelected = selectedValue == item.title
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (isSelected) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                             if (isSelected) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        ).noRippleClickable {
                            data.forEach { it.isSelected.value = false }
                            item.isSelected.value = true

                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(notice_period = item.title)
                            }
                        }
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        item.title,
                        color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }
        }
    }
}

@Composable
fun PP_LeaseAmount(isError: Boolean) {
    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    var leaseAmount by remember { mutableStateOf("") }
    var leaseNego by remember { mutableStateOf(false) }

    LaunchedEffect(flowData.value?.lease_amount, flowData.value?.lease_negotiable) {
        leaseAmount = flowData.value?.lease_amount ?: onDraft.lease_amount ?: ""
        leaseNego = flowData.value?.lease_negotiable ?: onDraft.lease_negotiable ?: false
    }


    var focusManager = LocalFocusManager.current
    var keyboardController = LocalSoftwareKeyboardController.current



    Column {
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = newBlack)) {
                    append("Lease Amount")
                }
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append("*")
                }
            },
            fontSize = constants.textUnit(16),
            fontFamily = constants.fontFamily(1)
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Row(
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .background(Color(0xffFFFFFF))
                .border(
                    1.dp,
                    if (isError) Color.Red else Color(0xffCECECE),
                    RoundedCornerShape(6.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(Color(0xffCECECE))
                    .padding(horizontal = 18.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("₹")
            }

            VerticalDivider(color = Color(0xffCECECE))

            TextField(
                value = leaseAmount,
                onValueChange = {
                    leaseAmount = it.filter { char -> char.isDigit() }
                    constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                        it.copy(lease_amount = leaseAmount)
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword
                ),
                keyboardActions = KeyboardActions (
                    onDone = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                    }
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.weight(8f)
            )
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Checkbox(
                checked = leaseNego,
                onCheckedChange = {
                    leaseNego = it
                    constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                        it.copy(lease_negotiable = leaseNego)
                    }
                }
            )
            Text("Lease Negotiable")
        }

        constants.spacer(4)

        if (isError) {
            Text("Enter Lease amount", color = Color.Red, fontSize = constants.textUnit(12))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PP_Lease_Duration(data: List<Chips_Items_DC> , isError: Boolean) {
    var manual_add by remember { mutableStateOf(false) }
    var manual_add_No by remember { mutableStateOf("") }

    val flowData = constants.PostProperty_ViewModel.selected_Options_Form4.collectAsState()
    val onDraft = constants.PostProperty_ViewModel.get_Selected_Fields_Form()

    LaunchedEffect(flowData.value?.lease_duration_in_years_type, flowData.value?.lease_duration_in_years) {
        val selectedType = flowData.value?.lease_duration_in_years_type
            ?: onDraft.lease_duration_in_years_type

        if (selectedType != null) {
            val matchingChip = data.find { it.title == selectedType }
            if (matchingChip != null) {
                data.forEach { it.isSelected.value = false }
                matchingChip.isSelected.value = true
            } else {
                data.forEach { it.isSelected.value = false }
            }
        }
    }

    val selectedFromChip = data.find { it.isSelected.value }?.title
    val customValue = flowData.value?.lease_duration_in_years
    val displayValue = if (selectedFromChip != null) null else customValue


    val selectedValue = flowData.value?.lease_duration_in_years_type


    var focusManager = LocalFocusManager.current
    var keyboardController = LocalSoftwareKeyboardController.current




    Column {
//        Text(
//            "Lease Duration in Years",
//            fontSize = constants.textUnit(16),
//            fontFamily = constants.fontFamily(1)
//        )

        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = newBlack)) {
                    append("Lease Duration in Years")
                }
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append("*")
                }
            },
            fontSize = constants.textUnit(16),
            fontFamily = constants.fontFamily(1)
        )

        Spacer(modifier = Modifier.padding(8.dp))

        FlowRow(modifier = Modifier.fillMaxWidth()) {
            data.forEachIndexed { index, item ->

                val isSelected = selectedValue == item.title
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp, bottom = 8.dp)
                        .wrapContentSize()
                        .background(
                            if (isSelected) backGroundBrush() else defaultBackGroundBrush(),
                            RoundedCornerShape(4.dp)
                        )
                        .border(
                            1.dp,
                             if (isSelected) borderBrush() else defaultBorderBrush(),
                            RoundedCornerShape(4.dp)
                        ).noRippleClickable {
//                            data.forEach { it.isSelected.value = false }
//                            item.isSelected.value = true

                            constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                it.copy(lease_duration_in_years_type = item.title)
                            }
                        }
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        item.title,
                        color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 8.dp)
                    .wrapContentSize()
                    .background(
                        if (displayValue.isNullOrEmpty()) Color.White else Color(0xffF7F0DC),
                        RoundedCornerShape(4.dp)
                    )
                    .border(
                        1.dp,
                        Brush.linearGradient(newPurpleGradientBorder),
                        RoundedCornerShape(4.dp)
                    )
                    .noRippleClickableWithScale(MutableInteractionSource()) {
                        manual_add = true
                        manual_add_No = displayValue ?: ""
                    }
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                if (displayValue.isNullOrEmpty()) {
                    Text("+ Add", color = newBlue)
                } else {
                    Row(
                        modifier = Modifier.background(newBlue),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = displayValue,
                            fontSize = constants.textUnit(14),
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            painter = painterResource(R.drawable.arrowdown),
                            "",
                            modifier = Modifier.size(14.dp),
                            tint = Color.White
                        )
                    }
                }
            }
        }


        constants.spacer(4)

        if (isError) {
            Text("Enter Lease duration", color = Color.Red, fontSize = constants.textUnit(12))
        }
    }

    if (manual_add) {
        ModalBottomSheet(
            onDismissRequest = { manual_add = false; manual_add_No = "" },
            containerColor = newWhite,
            sheetGesturesEnabled = false
        ) {
            Column {
                Text(
                    "Enter Lease Duration",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .border(1.dp, newGray, RoundedCornerShape(8.dp))
                ) {
                    TextField(
                        value = manual_add_No,
                        onValueChange = {

                            if (it.length <= 3) {
                                manual_add_No = it.filter { char -> char.isDigit() }
                            }

                        },
                        placeholder = { Text("Enter number") },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedTextColor = newBlack,
                            unfocusedTextColor = newBlack
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            }
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Static_Bottom(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .fillMaxHeight(.7f)
                            .background(newBlue, RoundedCornerShape(8.dp))
                            .noRippleClickable {
                                if (manual_add_No.isNotEmpty()) {
                                    data.forEach { it.isSelected.value = false }
                                    constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                        it.copy(lease_duration_in_years = manual_add_No)
                                    }
                                }
                                manual_add = false
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Submit", color = Color.White)
                    }
                }
            }
        }
    }
}
