package com.toletspot.houseforrent

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.toletspot.houseforrent.Custom_Assets.ClickHelper
import com.toletspot.houseforrent.Custom_Assets.Static_Bottom
import com.toletspot.houseforrent.Custom_Assets.rememberNotchHeightDp
import com.toletspot.houseforrent.Custom_Assets.toast
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.PostFlow
import com.toletspot.houseforrent.UI_DataClass.PP3_API_DC
import com.toletspot.houseforrent.ui.theme.newBlack
import com.toletspot.houseforrent.ui.theme.newBlue
import com.toletspot.houseforrent.ui.theme.newGray
import com.toletspot.houseforrent.ui.theme.newPurpleGradient
import com.toletspot.houseforrent.ui.theme.newPurpleGradientBorder
import com.toletspot.houseforrent.ui.theme.newWhite
import com.toletspot.houseforrent.ui.theme.rentoDarkGray
import com.toletspot.houseforrent.ui.theme.rentoLightGray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.Locale
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PincodePlaceSearchOLD(
    placesClient: PlacesClient,
    geocoder: Geocoder,
    show_Map_view: MutableState<Boolean>
) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    val pincode = constants.PostProperty_ViewModel.pincode3.collectAsStateWithLifecycle()
    val country = constants.PostProperty_ViewModel.country3.collectAsState()
    val state = constants.PostProperty_ViewModel.state3.collectAsStateWithLifecycle()
    val city = constants.PostProperty_ViewModel.city3.collectAsStateWithLifecycle()
    val latLng = constants.PostProperty_ViewModel.latLng3.collectAsStateWithLifecycle()
    val selectedLocality = constants.PostProperty_ViewModel.selectedLocality3.collectAsStateWithLifecycle()

    var localityQuery by remember { mutableStateOf("") }
    var localities by remember { mutableStateOf<List<AutocompletePrediction>>(emptyList()) }

    val selected_Map_Locality = constants.PostProperty_ViewModel.selected_Locality.collectAsStateWithLifecycle()
    val data_Copy_Map = constants.PostProperty_ViewModel.get_pp3_Data()

    if (selected_Map_Locality.value.isNotEmpty()) {
        constants.PostProperty_ViewModel.set__selectedLocality3(selected_Map_Locality.value)
    }

    LaunchedEffect(Unit, show_Map_view.value) {
        val dmmy = constants.PostProperty_ViewModel.get_pp3_Data()
        constants.PostProperty_ViewModel.set_country3(dmmy?.country ?: "")
        constants.PostProperty_ViewModel.set_state3(dmmy?.state ?: "")
        constants.PostProperty_ViewModel.set_city3(dmmy?.city ?: "")
        constants.PostProperty_ViewModel.set__selectedLocality3(dmmy?.locality ?: "")
        constants.PostProperty_ViewModel.set_pincode3(dmmy?.pincode ?: "")
    }

    val cant_find = remember { mutableStateOf(false) }
    val condition by remember {
        derivedStateOf {
            pincode.value.isNotEmpty() && country.value.isNotEmpty() && state.value.isNotEmpty() && city.value.isNotEmpty()
        }
    }
    var isPinCode_Invalid by remember { mutableStateOf(false) }

    LazyColumn {
        item {

            Text("Enter Pincode" ,color = newBlack,
                fontSize = constants.textUnit(18),
                fontFamily = constants.fontFamily(0))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .border(
                        1.dp,
                        if (isPinCode_Invalid) Color.Red else newGray,
                        RoundedCornerShape(8.dp)
                    )
            ) {
                TextField(
                    value = pincode.value,
                    onValueChange = {
                        constants.PostProperty_ViewModel.set_pincode3(it)
                        if (pincode.value.isEmpty()) {
                            isPinCode_Invalid = false
                            constants.PostProperty_ViewModel.set_country3("")
                            constants.PostProperty_ViewModel.set_state3("")
                            constants.PostProperty_ViewModel.set_city3("")
                            constants.PostProperty_ViewModel.set__selectedLocality3("")
                            localityQuery  = ""
                            localities = emptyList()
                        }
                    },
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(6.5f),
                    placeholder = { Text("Enter Pincode", color = newBlack,
                        fontSize = constants.textUnit(18),
                        fontFamily = constants.fontFamily(0)) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedTextColor = newBlack,
                        unfocusedTextColor = newBlack,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                    , singleLine = true
                )

                VerticalDivider(color = if (isPinCode_Invalid) Color.Red else newGray)

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
                        .background(Color(0xffF7F0DC))
                        .weight(3.5f)
                        .noRippleClickable {
                            ClickHelper.getInstance().clickOnce {
                                keyboardController?.hide()
                                if (pincode.value.isNotEmpty()) {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        val valid = isValidPincode(pincode.value, geocoder)
                                        withContext(Dispatchers.Main) {
                                            if (valid) {
                                                val (ll, c, st, cn) = getLatLngForPincode1(
                                                    pincode.value,
                                                    geocoder
                                                )
                                                constants.PostProperty_ViewModel.set_country3(
                                                    cn ?: ""
                                                )
                                                constants.PostProperty_ViewModel.set_state3(
                                                    st ?: ""
                                                )
                                                constants.PostProperty_ViewModel.set_city3(c ?: "")
                                                if (ll != null) constants.PostProperty_ViewModel.set_latLng3(
                                                    ll
                                                )

                                                cant_find.value = false
                                                isPinCode_Invalid = false
                                            } else {
                                                isPinCode_Invalid = true
                                                constants.PostProperty_ViewModel.set_country3("")
                                                constants.PostProperty_ViewModel.set_state3("")
                                                constants.PostProperty_ViewModel.set_city3("")
                                                constants.PostProperty_ViewModel.set_latLng3(null)
                                            }
                                        }
                                    }
                                } else {
                                    toast("Enter the pincode")

                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text("Get details", color = if (isPinCode_Invalid) Color.Red else newBlue,
                        fontSize = constants.textUnit(18),
                        fontFamily = constants.fontFamily(0))
                }
            }

            AnimatedVisibility(visible = condition) {
                Column {
                    Spacer(Modifier.height(12.dp))
                    ReadOnlyField("Country", country.value)
                    Spacer(Modifier.height(8.dp))
                    ReadOnlyField("State", state.value)
                    Spacer(Modifier.height(8.dp))
                    ReadOnlyField("City", city.value)
                }
            }

            Spacer(Modifier.height(12.dp))

            Text("Locality" ,
                color = newBlack,
                fontSize = constants.textUnit(18),
                fontFamily = constants.fontFamily(0)
            )

            var expanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = selectedLocality.value.ifEmpty { localityQuery },
                    onValueChange = {
                        constants.PostProperty_ViewModel.set__selectedLocality3("")
                        localityQuery = it
                        if (latLng.value != null && localityQuery.length > 2) {
                            fetchLocalities(
                                localityQuery,
                                latLng.value!!,
                                placesClient,
                                city.value
                            ) { predictions ->
                                localities = predictions
                                expanded = predictions.isNotEmpty()
                            }
                        } else {
                            localities = emptyList()
                            expanded = false
                        }
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    placeholder = { Text("Select your locality") },
                    singleLine = true,
                    readOnly = !(pincode.value.isNotEmpty()
                            && country.value.isNotEmpty()
                            && state.value.isNotEmpty()
                            && city.value.isNotEmpty()),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedTextColor = newBlack,
                        unfocusedTextColor = newBlack,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                    , containerColor = Color.White
                    , border = BorderStroke(1.dp , newGray)
                    , modifier = Modifier.heightIn(max = 400.dp)
                ) {
                    localities.forEach { prediction ->
                        val primary = prediction.getPrimaryText(null).toString()
                        val secondary = prediction.getSecondaryText(null)?.toString() ?: ""
                        val displayName =
                            if (secondary.isNotEmpty()) "$primary, $secondary" else primary

                        DropdownMenuItem(
                            text = { Text(displayName) },
                            onClick = {
                                ClickHelper.getInstance().clickOnce {
                                    constants.PostProperty_ViewModel.set__selectedLocality3(
                                        displayName
                                    )
                                    focusManager.clearFocus()
                                    localities = emptyList()
                                    expanded = false
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .height(86.dp)
                    .fillMaxWidth()
                    .background(newBlue),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(.95f)
                        .fillMaxHeight(.75f)
                        .background(Color.White)
                        .padding(horizontal = 8.dp)
                        .noRippleClickable {
                            ClickHelper.getInstance().clickOnce {
                                focusManager.clearFocus()
                                show_Map_view.value = true

                            }
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(painter = painterResource(R.drawable.locationpinenquiry), contentDescription = "")
                        Text("Pin Property on Map")
                    }
                    Image(painterResource(R.drawable.right_arrow), contentDescription = "")
                }

                if (localityQuery.isNotEmpty() && localities.isEmpty()
                    && selectedLocality.value.isEmpty() && !cant_find.value
                ) {
                    Image(
                        painter = painterResource(R.drawable.location_find_failed),
                        contentDescription = "Location not found",
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .noRippleClickable {
                                ClickHelper.getInstance().clickOnce {

                                    if (localityQuery.isNotEmpty()) {
                                        constants.PostProperty_ViewModel.set__selectedLocality3(
                                            localityQuery
                                        )
                                    }
                                    cant_find.value = true

                                }
                            }
                    )
                }

                if (country.value.isNotEmpty() &&
                    state.value.isNotEmpty() &&
                    city.value.isNotEmpty() &&
                    selectedLocality.value.isNotEmpty()
                ) {
                    constants.PostProperty_ViewModel.add_pp3_Data(
                        PP3_API_DC(
                            pincode = pincode.value,
                            country = country.value,
                            state = state.value,
                            city = city.value,
                            locality = selectedLocality.value
                        )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  PincodePlaceSearch(
    placesClient: PlacesClient,
    geocoder: Geocoder,
    show_Map_view: MutableState<Boolean>
) {

    var network = rememberNetworkStatus()

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    val pincode = constants.PostProperty_ViewModel.pincode3.collectAsStateWithLifecycle()
    val country = constants.PostProperty_ViewModel.country3.collectAsState()
    val state = constants.PostProperty_ViewModel.state3.collectAsStateWithLifecycle()
    val city = constants.PostProperty_ViewModel.city3.collectAsStateWithLifecycle()
    val latLng = constants.PostProperty_ViewModel.latLng3.collectAsStateWithLifecycle()
    val selectedLocality = constants.PostProperty_ViewModel.selectedLocality3.collectAsStateWithLifecycle()

    var form3ShowMap = constants.PostProperty_ViewModel.form3ShowMap.collectAsState()

    var localityQuery by remember { mutableStateOf("") }
    var localities by remember { mutableStateOf<List<AutocompletePrediction>>(emptyList()) }

    val selected_Map_Locality = constants.PostProperty_ViewModel.selected_Locality.collectAsStateWithLifecycle()
    val data_Copy_Map = constants.PostProperty_ViewModel.get_pp3_Data()

    LaunchedEffect(selected_Map_Locality.value) {
        if (selected_Map_Locality.value.isNotEmpty()) {
            constants.PostProperty_ViewModel.set__selectedLocality3(
                selected_Map_Locality.value
            )
        }
    }

    LaunchedEffect(Unit, show_Map_view.value) {
        val dmmy = constants.PostProperty_ViewModel.get_pp3_Data()
        constants.PostProperty_ViewModel.set_country3(dmmy?.country ?: "")
        constants.PostProperty_ViewModel.set_state3(dmmy?.state ?: "")
        constants.PostProperty_ViewModel.set_city3(dmmy?.city ?: "")
        constants.PostProperty_ViewModel.set__selectedLocality3(dmmy?.locality ?: "")
        constants.PostProperty_ViewModel.set_pincode3(dmmy?.pincode ?: "")
    }

    val cant_find = remember { mutableStateOf(false) }
    val condition by remember {
        derivedStateOf {
            pincode.value.isNotEmpty() && country.value.isNotEmpty() && state.value.isNotEmpty() && city.value.isNotEmpty()
        }
    }
    var isPinCode_Invalid by remember { mutableStateOf(false) }

    val postFlow = constants.PostProperty_ViewModel.postFlow.collectAsState()

    LaunchedEffect(selectedLocality.value) {
        val vmValue = selectedLocality.value
        if (
            vmValue.isNotBlank() &&
            vmValue != localityQuery
        ) {
            localityQuery = vmValue
        }
    }

    LazyColumn {
        item {

            Text(
                    buildAnnotatedString {
                withStyle(style = SpanStyle(color = newBlack)){
                    append("Enter Pincode")
                }
                    withStyle(style = SpanStyle(color = Color.Red)){
                        append(" *")
                    }

            },color = newBlack,
                fontSize = constants.textUnit(16),
                fontFamily = constants.fontFamily(1)
            )

            constants.spacer(2)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .border(
                        1.dp,
                        if (isPinCode_Invalid) Color.Red else newGray,
                        RoundedCornerShape(8.dp)
                    )
            )
            {
                TextField(
                    value = pincode.value,
                    onValueChange = {
                        constants.PostProperty_ViewModel.set_pincode3(it)
                        isPinCode_Invalid = false
                        if (pincode.value.isEmpty() ) {
                            isPinCode_Invalid = false
                            constants.PostProperty_ViewModel.set_country3("")
                            constants.PostProperty_ViewModel.set_state3("")
                            constants.PostProperty_ViewModel.set_city3("")
                            constants.PostProperty_ViewModel.set__selectedLocality3("")
                            localityQuery  = ""
                            localities = emptyList()
                        }
                    }
                    ,textStyle = TextStyle(
                            color = newBlack,
                    fontSize = constants.textUnit(12),
                    fontFamily = constants.fontFamily(2)
                ),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(6.5f),
                    placeholder = { Text("Enter Pincode") },
                    readOnly = if (postFlow.value == PostFlow.EDIT || postFlow.value == PostFlow.REQUESTMEDIA)  true else false,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = if (postFlow.value == PostFlow.EDIT || postFlow.value == PostFlow.REQUESTMEDIA) rentoLightGray else  Color.White,
                        focusedTextColor = newBlack,
                        unfocusedTextColor = newBlack,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                    , singleLine = true
                )

                VerticalDivider(color = if (isPinCode_Invalid) Color.Red else newGray)

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
                        .background(if (isPinCode_Invalid) Color.White else if (postFlow.value == PostFlow.EDIT || postFlow.value == PostFlow.REQUESTMEDIA) rentoLightGray else  Color(0xffF7F0DC))
                        .weight(3.5f)
                        .noRippleClickable {
                            if (postFlow.value == PostFlow.EDIT || postFlow.value == PostFlow.REQUESTMEDIA){

                            }
                            else {
                                ClickHelper.getInstance().clickOnce {
                                    keyboardController?.hide()
                                    if (network.value == NetworkStatus.Online) {
                                        if (pincode.value.isEmpty()) {
                                            toast("Enter the pincode")
                                        } else if (pincode.value.length in 6..10) {
                                            constants.PostProperty_ViewModel.set__selectedLocality3(
                                                ""
                                            )
                                            focusManager.clearFocus()
                                            localities = emptyList()
                                            localityQuery = ""
                                            CoroutineScope(Dispatchers.IO).launch {
                                                val valid = isValidPincode(pincode.value, geocoder)
                                                withContext(Dispatchers.Main) {
                                                    if (valid) {
                                                        val (ll, c, st, cn) = getLatLngForPincode1(
                                                            pincode.value,
                                                            geocoder
                                                        )
                                                        constants.PostProperty_ViewModel.set_country3(
                                                            cn ?: ""
                                                        )
                                                        constants.PostProperty_ViewModel.set_state3(
                                                            st ?: ""
                                                        )
                                                        constants.PostProperty_ViewModel.set_city3(
                                                            c ?: ""
                                                        )
                                                        if (ll != null) constants.PostProperty_ViewModel.set_latLng3(
                                                            ll
                                                        )

                                                        cant_find.value = false
                                                        isPinCode_Invalid = false
                                                    } else {
                                                        isPinCode_Invalid = true
                                                        toast("Enter Valid Pincode")
                                                        constants.PostProperty_ViewModel.set_country3(
                                                            ""
                                                        )
                                                        constants.PostProperty_ViewModel.set_state3(
                                                            ""
                                                        )
                                                        constants.PostProperty_ViewModel.set_city3("")
                                                        constants.PostProperty_ViewModel.set_latLng3(
                                                            null
                                                        )
                                                    }
                                                }
                                            }
                                        } else {
                                            toast("Please enter a valid pincode")
                                        }
                                    } else {
                                        toast(constants.activity.getString(R.string.no_Internet))
                                    }
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text("Get details", color = if (isPinCode_Invalid) Color.Red else if (postFlow.value == PostFlow.EDIT || postFlow.value == PostFlow.REQUESTMEDIA) rentoDarkGray else newBlue ,
                        fontSize = constants.textUnit(12),
                        fontFamily = constants.fontFamily(2)
                    )
                }
            }

            if (isPinCode_Invalid){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                        , verticalAlignment = Alignment.CenterVertically
                        , horizontalArrangement = Arrangement.Start
                    ) {
                        Image(painter = painterResource(R.drawable.errorinforento) , "",
                            modifier = Modifier.size(12.dp))

                        constants.spacer(8)

                        CommonText("Enter valid pincode and tap get details.",
                            Color.Red,
                            12,
                            2
                        )
                    }
            }

            AnimatedVisibility(visible = condition) {
                Column {
                    Spacer(Modifier.height(12.dp))
                    ReadOnlyField("Country", country.value)
                    Spacer(Modifier.height(8.dp))
                    ReadOnlyField("State", state.value)
                    Spacer(Modifier.height(8.dp))
                    ReadOnlyField("City", city.value)
                }
            }

            Spacer(Modifier.height(12.dp))

            Text("Locality",
                    color = newBlack,
                fontSize = constants.textUnit(16),
                fontFamily = constants.fontFamily(1)
            ,)

            var expanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {

                    if (localities.isNotEmpty()) {
                        expanded = !expanded
                    }
                }
            )
            {
                TextField(
                    value = localityQuery,
                    onValueChange = { newText ->
                        localityQuery = newText
                        constants.PostProperty_ViewModel.set__selectedLocality3(newText)

                        if (latLng.value != null && newText.length > 2) {
                            val requestQuery = newText

                            fetchLocalities(
                                requestQuery,
                                latLng.value!!,
                                placesClient,
                                city.value
                            ) { predictions ->

                                if (localityQuery == requestQuery) {
                                    localities = predictions
                                    expanded = predictions.isNotEmpty()
                                }
                            }
                        } else {
                            localities = emptyList()
                            expanded = false
                        }

                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .border(
                            1.dp,
                             newGray,
                            RoundedCornerShape(8.dp)
                        )
                    , placeholder = { Text("Select your locality") },
                    singleLine = true,
                    readOnly = if (postFlow.value == PostFlow.EDIT || postFlow.value == PostFlow.REQUESTMEDIA) true else if (!(pincode.value.isNotEmpty()
                                && country.value.isNotEmpty()
                                && state.value.isNotEmpty()
                                && city.value.isNotEmpty())) true else false,

                    textStyle = TextStyle(
                        color = newBlack,
                        fontSize = constants.textUnit(12),
                        fontFamily = constants.fontFamily(2)
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedTextColor = newBlack,
                        unfocusedTextColor = newBlack,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                    , containerColor = Color.White
                    , border = BorderStroke(1.dp , newGray)
                    , modifier = Modifier.heightIn(max = 400.dp)
                ) {
                    localities.forEach { prediction ->
                        val primary = prediction.getPrimaryText(null).toString()
                        val secondary = prediction.getSecondaryText(null)?.toString() ?: ""
                        val displayName =
                            if (secondary.isNotEmpty()) "$primary, $secondary" else primary

                        DropdownMenuItem(
                            text = { Text(displayName , color = newBlack ,
                                fontSize = constants.textUnit(12),
                                fontFamily = constants.fontFamily(2)
                            ) },
                            onClick = {
                                ClickHelper.getInstance().clickOnce {
                                    constants.PostProperty_ViewModel.set__selectedLocality3(
                                        displayName
                                    )
                                    focusManager.clearFocus()
                                    localities = emptyList()
                                    expanded = false
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                , verticalAlignment = Alignment.CenterVertically
                , horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                HorizontalDivider(modifier = Modifier.weight(4f))
                Text("or"
                    , color = Color(0xff7E7E7E)
                    , fontSize = constants.textUnit(14)
                    , fontFamily = constants.fontFamily(1)
                )
                HorizontalDivider(modifier = Modifier.weight(4f))

            }

            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .height(86.dp)
                    .fillMaxWidth()
                    .background(newBlue),
                contentAlignment = Alignment.Center
            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(.95f)
                        .fillMaxHeight(.75f)
                        .background(Color.White)
                        .padding(horizontal = 8.dp)
                        .noRippleClickable {
                            if (postFlow.value == PostFlow.EDIT || postFlow.value == PostFlow.REQUESTMEDIA){

                            }
                            else {
                                ClickHelper.getInstance().clickOnce {
                                    focusManager.clearFocus()
                                    show_Map_view.value = true
                                }
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(painter = painterResource(R.drawable.locationpinenquiry), contentDescription = "")
                        Text("Pin Property on Map")
                    }
                    Image(painterResource(R.drawable.right_arrow), contentDescription = "")
                }

                if (localityQuery.isNotEmpty() && localities.isEmpty()
                    && selectedLocality.value.isEmpty() && !cant_find.value
                ) {
                    Image(
                        painter = painterResource(R.drawable.location_find_failed),
                        contentDescription = "Location not found",
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .noRippleClickable {
                                if (postFlow.value == PostFlow.EDIT || postFlow.value == PostFlow.REQUESTMEDIA){

                                }
                                else {
                                    ClickHelper.getInstance().clickOnce {

                                        if (localityQuery.isNotEmpty()) {
                                            constants.PostProperty_ViewModel.set__selectedLocality3(
                                                localityQuery
                                            )
                                        }
                                        cant_find.value = true

                                    }
                                }
                            }
                    )
                }

                if (country.value.isNotEmpty() &&
                    state.value.isNotEmpty() &&
                    city.value.isNotEmpty() &&
                    selectedLocality.value.isNotEmpty()
                ) {
                    constants.PostProperty_ViewModel.clear_pp3_Data()
                    constants.PostProperty_ViewModel.add_pp3_Data(
                        PP3_API_DC(
                            pincode = pincode.value,
                            country = country.value,
                            state = state.value,
                            city = city.value,
                            locality = selectedLocality.value
                        )
                    )
                }
            }

            constants.spacer(12)

            ListItem(
                headlineContent = {
                    Text("Show your property on the Map?"
                        , color = newBlack
                        , fontSize = constants.textUnit(14)
                        , fontFamily = constants.fontFamily(1)
                    )
                },
                supportingContent = {
                    Text("Others will be able to view your property’s location on the map."
                        , color = Color(0xff575757)
                        , fontSize = constants.textUnit(12)
                        , fontFamily = constants.fontFamily(3)
                    )
                }
                , trailingContent = {
                    Switch(
                        checked = form3ShowMap.value,
                        onCheckedChange = {
                            if (postFlow.value == PostFlow.EDIT || postFlow.value == PostFlow.REQUESTMEDIA){

                            }
                            else {
                                constants.PostProperty_ViewModel.set3formShowMap(it)
                            }
                        },
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = Color(0xffE8E9E9),
                            uncheckedTrackColor = Color(0xffE8E9E9),
                            checkedThumbColor = newBlue,
                            uncheckedThumbColor = Color.White,
                            uncheckedBorderColor = Color.Transparent
                        )
                    )
                }
                , colors = ListItemDefaults.colors(
                    containerColor = newWhite
                )
            )
        }
    }
}

@Composable
private fun ReadOnlyField(label: String, value: String) {
    Text(label ,color = newBlack,
        fontSize = constants.textUnit(16),
        fontFamily = constants.fontFamily(1))

    constants.spacer(2)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xffEBEBEB))
            .border(1.dp, newGray, RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        TextField(
            value = value,
            onValueChange = {},
            modifier = Modifier.fillMaxSize(),
            placeholder = { Text(label) },
            readOnly = true,
            textStyle = TextStyle(
                color = newBlack,
                fontSize = constants.textUnit(12),
                fontFamily = constants.fontFamily(2)
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffEBEBEB),
                unfocusedContainerColor = Color(0xffEBEBEB),
                focusedTextColor = newBlack,
                unfocusedTextColor = newBlack,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}

suspend fun fetchDistrictFromPostalAPI(pincode: String): String? {
    return try {
        val url = URL("https://api.postalpincode.in/pincode/$pincode")
        val connection = withContext(Dispatchers.IO) { url.openConnection() as HttpURLConnection }
        connection.requestMethod = "GET"
        connection.connectTimeout = 5000
        connection.readTimeout = 5000

        val response = connection.inputStream.bufferedReader().use { it.readText() }

        val jsonArray = JSONArray(response)
        if (jsonArray.length() > 0) {
            val postOffices = jsonArray.getJSONObject(0).optJSONArray("PostOffice")
            if (postOffices != null && postOffices.length() > 0) {
                return postOffices.getJSONObject(0).optString("District")
            }
        }
        null
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

suspend fun isValidPincode(pincode: String, geocoder: Geocoder): Boolean {
    return try {
        val addresses = geocoder.getFromLocationName(pincode, 1)
        !addresses.isNullOrEmpty()
    } catch (e: Exception) {
        false
    }
}

suspend fun getLatLngForPincode1(
    pincode: String,
    geocoder: Geocoder
): Quad<LatLng?, String?, String?, String?> {
    val addresses = geocoder.getFromLocationName(pincode, 1)
    return if (!addresses.isNullOrEmpty()) {
        val address = addresses[0]

        val lat = address.latitude
        val lng = address.longitude

        var district: String? = address.subAdminArea

        if (district.isNullOrEmpty()) {
            try {
                val postalDistrict = fetchDistrictFromPostalAPI(pincode)
                if (!postalDistrict.isNullOrEmpty()) {
                    district = postalDistrict
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        if (district.isNullOrEmpty()) {
            district = address.locality ?: address.subLocality ?: ""
        }

        val state = address.adminArea ?: ""
        val country = address.countryName ?: ""

        constants.PostProperty_ViewModel.add_Pinned_Lat_Long(LatLng(lat, lng))
        Quad(LatLng(lat, lng), district, state, country)
    } else {
        Quad(null, null, null, null)
    }
}

fun fetchLocalities(
    query: String,
    latLng: LatLng,
    placesClient: PlacesClient,
    city: String,
    callback: (List<AutocompletePrediction>) -> Unit
) {
    Log.d("PlacesDebug", "Query: $query, City: $city, LatLng: $latLng")

    val bounds = RectangularBounds.newInstance(
        LatLng(latLng.latitude - 0.15, latLng.longitude - 0.15),
        LatLng(latLng.latitude + 0.15, latLng.longitude + 0.15)
    )

    val request = FindAutocompletePredictionsRequest.builder()
        .setQuery("$query $city")
        .setLocationRestriction(bounds)
        .build()

    placesClient.findAutocompletePredictions(request)
        .addOnSuccessListener { response ->

            Log.d(
                "PlacesDebug",
                "Predictions count: ${response.autocompletePredictions.size}"
            )

            response.autocompletePredictions.forEachIndexed { index, prediction ->
                Log.d(
                    "PlacesDebug",
                    "[$index] ${prediction.getFullText(null)} | " +
                            "Types=${prediction.placeTypes}"
                )
            }

            callback(response.autocompletePredictions)
        }
        .addOnFailureListener { e ->
            Log.e("PlacesDebug", "Places API error", e)
            callback(emptyList())
        }
}

@Composable
fun MapSearchScreen(
    placesClient: PlacesClient,
    fusedLocationProviderClient: FusedLocationProviderClient,
    apiKey: String,
    show_Map_view: MutableState<Boolean>,
    initialLatLng: LatLng? = null
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val network = rememberNetworkStatus()

    var query by remember { mutableStateOf("") }
    var predictions by remember { mutableStateOf<List<AutocompletePrediction>>(emptyList()) }
    var selectedPlace by remember { mutableStateOf<String?>(null) }
    var selectedLatLng by remember { mutableStateOf<LatLng?>(null) }
    var pinnedLocation by remember { mutableStateOf<LatLng?>(null) }

    var placeName by remember { mutableStateOf("") }
    var addressText by remember { mutableStateOf("") }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(20.0, 77.0), 4f)
    }

    LaunchedEffect(initialLatLng) {
        initialLatLng?.let { latLng ->
            pinnedLocation = latLng
            selectedLatLng = latLng
            addressText = getFullAddress(context, latLng)
            coroutineScope.launch(Dispatchers.IO) {
                val (name, _) = fetchPlaceDetailsForLatLng(latLng, apiKey)
                withContext(Dispatchers.Main) { placeName = name }
            }
            cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        }
    }

    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                mapType = MapType.NORMAL,
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
                    context,
                    R.raw.map_light
                )
            )
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {

        GoogleMap(
            modifier = Modifier.matchParentSize(),
            properties = mapProperties,
            cameraPositionState = cameraPositionState,
            onMapClick = { latLng ->

                if (network.value == NetworkStatus.Online) {
                    pinnedLocation = latLng

                    addressText = getFullAddress(context, latLng)

                    coroutineScope.launch(Dispatchers.IO) {
                        val (name, _) = fetchPlaceDetailsForLatLng(latLng, apiKey)
                        withContext(Dispatchers.Main) {
                            placeName = name
                        }
                    }
                }
                else {
                    toast("Check your Internet Connection")
                }
            }
        ) {
            pinnedLocation?.let {
                Marker(
                    state = MarkerState(position = it),
                    title = "Pinned Location"
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.verticalGradient(listOf(
                        Color(0xffFCFCFC),
                        Color(0xffFCFCFC),
                        Color(0xffFCFCFC)
                        ,Color.Transparent
                    )))
                    .padding(
                        horizontal = 16.dp,
                        vertical = if (forTab()) 16.dp else rememberNotchHeightDp().value
                    )

            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                        , verticalAlignment = Alignment.CenterVertically
                        , horizontalArrangement = Arrangement.Start
                    ) {
                        Image(
                            painterResource(R.drawable.left_arrow),
                            "",
                            modifier = Modifier.size(20.dp).noRippleClickable{
                                 show_Map_view.value = false
                            }
                        )

                        constants.spacer(4)

                        CommonText("Pin Property",
                            newBlack,
                            20,
                            0
                            )

                    }

                    constants.spacer(16)

                    TextField(
                        value = selectedPlace ?: query,
                        onValueChange = {
                            selectedPlace = null
                            query = it
                            if (query.length > 2) {
                                fetchGlobalPlaces(query, placesClient) { newPredictions ->
                                    predictions = newPredictions
                                }
                            } else {
                                predictions = emptyList()
                            }
                        },
                        leadingIcon = {
                            Image(
                                painterResource(R.drawable.searchnotrento),
                                "",
                                modifier = Modifier.size(16.dp).noRippleClickable{

                                }
                            )
                        },
                        textStyle = TextStyle(
                            color = newBlack,
                            fontSize = constants.textUnit(12),
                            fontFamily = constants.fontFamily(2)
                        ),
                        placeholder = { Text("Search by area, city...") },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = if (forTab()) 36.dp else 0.dp)
                            .border(1.dp, Color(0xffCECECE), RoundedCornerShape(4.dp))
                    )

                    if (predictions.isNotEmpty() && selectedPlace == null) {
                        Column(
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .fillMaxWidth()

                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White)

                        ) {
                            predictions.forEach { prediction ->
                                Text(
                                    text = prediction.getFullText(null).toString(),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .noRippleClickable {
                                            selectedPlace = prediction.getFullText(null).toString()
                                            predictions = emptyList()

                                            fetchPlaceLatLngmap(
                                                prediction.placeId,
                                                placesClient
                                            ) { latLng ->
                                                if (latLng != null) {
                                                    pinnedLocation = latLng
                                                    selectedLatLng = latLng
                                                    addressText = getFullAddress(context, latLng)

                                                    coroutineScope.launch(Dispatchers.IO) {
                                                        val (name, _) =
                                                            fetchPlaceDetailsForLatLng(
                                                                latLng,
                                                                apiKey
                                                            )
                                                        withContext(Dispatchers.Main) {
                                                            placeName = name
                                                        }
                                                    }

                                                    cameraPositionState.move(
                                                        CameraUpdateFactory.newLatLngZoom(
                                                            latLng,
                                                            15f
                                                        )
                                                    )
                                                }
                                            }
                                        }
                                        .padding(12.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
        ) {
            val context = LocalContext.current

            var showPermissionDialog by remember { mutableStateOf(false) }
            var showGpsDialog by remember { mutableStateOf(false) }

            FloatingActionButton(
                onClick = {

                    if (network.value == NetworkStatus.Online) {

                        val locationManager =
                            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                        val isGpsEnabled =
                            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

                        if (ActivityCompat.checkSelfPermission(
                                context,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            showPermissionDialog = true
                            return@FloatingActionButton
                        }

                        if (!isGpsEnabled) {
                            showGpsDialog = true
                            return@FloatingActionButton
                        }

                        val cancellationTokenSource = CancellationTokenSource()

                        fusedLocationProviderClient.getCurrentLocation(
                            Priority.PRIORITY_HIGH_ACCURACY,
                            cancellationTokenSource.token
                        ).addOnSuccessListener { location ->
                            if (location != null) {
                                val latLng = LatLng(location.latitude, location.longitude)

                                pinnedLocation = latLng
                                addressText = getFullAddress(context, latLng)

                                coroutineScope.launch(Dispatchers.IO) {
                                    val (name, _) = fetchPlaceDetailsForLatLng(latLng, apiKey)
                                    withContext(Dispatchers.Main) { placeName = name }
                                }

                                coroutineScope.launch {
                                    cameraPositionState.animate(
                                        update = CameraUpdateFactory.newLatLngZoom(latLng, 15f),
                                        durationMs = 1000
                                    )
                                }
                            } else {
                                GlobalSnackbar.show("Unable to fetch current location")
                            }
                        }
                    }
                    else {
                        toast("Check your Internet Connection")
                    }

                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(16.dp),
                shape = CircleShape,
                containerColor = Color.White
            ) {
                Image(painterResource(R.drawable.location), "",
                    colorFilter = ColorFilter.tint(newBlack))
            }

            if (showPermissionDialog) {
                AlertDialog(
                    onDismissRequest = { showPermissionDialog = false },
                    title = { Text("Permission Required") },
                    text = { Text("Location permission is required to access your current location.") },
                    confirmButton = {
                        TextButton(onClick = {

                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                data = Uri.fromParts("package", context.packageName, null)
                            }
                            context.startActivity(intent)
                            showPermissionDialog = false
                        }) {
                            Text("Go to Settings")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showPermissionDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }

            if (showGpsDialog) {
                AlertDialog(
                    onDismissRequest = { showGpsDialog = false },
                    title = { Text("Enable GPS") },
                    text = { Text("Please enable GPS to access your current location.") },
                    confirmButton = {
                        TextButton(onClick = {
                            context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                            showGpsDialog = false
                        }) {
                            Text("Enable")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showGpsDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }

            if (pinnedLocation != null) {
                Surface(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(.9f)
                        .wrapContentHeight()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = newWhite,
                    shadowElevation = 8.dp
                )
                {

                    ListItem(
                        overlineContent = {
                            Column() {
                                Text(
                                    "Selected Location",
                                    color = newBlack,
                                    fontSize = constants.textUnit(12),
                                    fontFamily = constants.fontFamily(0),
                                    modifier = Modifier
                                )
                                constants.spacer(6)
                            }
                        },
                        headlineContent = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Image(painter = painterResource(R.drawable.selectedlocation), "")

                                constants.spacer(4)

                                Text(
                                    addressText,
                                    color = Color(0xff575757),
                                    fontSize = constants.textUnit(14),
                                    fontFamily = constants.fontFamily(1),
                                    modifier = Modifier
                                )
                            }
                        }, colors = ListItemDefaults.colors(
                            containerColor = newWhite
                        ), modifier = Modifier.padding(vertical = 6.dp)
                    )
                }

                constants.spacer(12)
            }

            Static_Bottom(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .fillMaxHeight(.6f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Brush.verticalGradient(newPurpleGradient))
                        .border(1.dp , Brush.verticalGradient(newPurpleGradientBorder) ,RoundedCornerShape(8.dp))
                        .noRippleClickable {
                            if (network.value == NetworkStatus.Online) {
                                if (addressText.isNotEmpty() && pinnedLocation != null) {

                                    val components = getAddressComponents(context, pinnedLocation!!)

                                    if (!components.pincode.isNullOrEmpty()) {
                                        constants.PostProperty_ViewModel.add_pp3_Data(
                                            PP3_API_DC(
                                                pincode = components.pincode,
                                                country = components.country,
                                                state = components.state,
                                                city = components.city,
                                                locality = components.locality + if (placeName.isNotEmpty()) ", $placeName" else ""
                                            )
                                        )

                                        constants.PostProperty_ViewModel.add_Pinned_Lat_Long(
                                            pinnedLocation!!
                                        )
                                        constants.PostProperty_ViewModel.set_latLng3(pinnedLocation!!)
                                        constants.PostProperty_ViewModel.set__selectedLocality3(
                                            addressText
                                        )
                                        constants.PostProperty_ViewModel.add_Selected_Locality(
                                            addressText
                                        )

                                        constants.PostProperty_ViewModel.set_country3(components.country)
                                        constants.PostProperty_ViewModel.set_state3(components.state)
                                        constants.PostProperty_ViewModel.set_city3(components.city)
                                        constants.PostProperty_ViewModel.set__selectedLocality3(
                                            addressText
                                        )
                                        constants.PostProperty_ViewModel.set_pincode3(components.pincode)
                                        show_Map_view.value = false
                                    } else {
                                        toast("Select valid position , unable to attain pincode")
                                    }
                                } else {
                                    toast("Select Address in the map")
                                }
                            }
                            else {
                                toast("Check your Internet Connection")
                            }
                        }
                    , contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Confirm Location",
                        color = Color.White,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(0)
                    )
                }
            }
        }
    }
}

data class AddressComponents(
    val pincode: String,
    val country: String,
    val state: String,
    val city: String,
    val locality: String
)

fun getAddressComponents(context: Context, latLng: LatLng): AddressComponents {
    val geocoder = Geocoder(context, Locale.getDefault())
    val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

    if (addresses.isNullOrEmpty()) {
        GlobalSnackbar.show("Unable to fetch address")
        return AddressComponents("", "", "", "", "")
    }

    val addr = addresses[0]

    if (addr.postalCode.isNullOrEmpty()) {
        GlobalSnackbar.show("Unable to find the Pincode")
        return AddressComponents("", "", "", "", "")
    }

    if (addr.countryName.isNullOrEmpty()) {
        GlobalSnackbar.show("Unable to find the Country")
        return AddressComponents("", "", "", "", "")
    }

    if (addr.adminArea.isNullOrEmpty()) {
        GlobalSnackbar.show("Unable to find the State")
        return AddressComponents("", "", "", "", "")
    }

    if (addr.locality.isNullOrEmpty()) {
        GlobalSnackbar.show("Unable to find the City")
        return AddressComponents("", "", "", "", "")
    }

    constants.PostProperty_ViewModel.set_country3(addr.countryName)
    constants.PostProperty_ViewModel.set_state3(addr.adminArea)
    constants.PostProperty_ViewModel.set_city3(addr.locality)
    constants.PostProperty_ViewModel.set__selectedLocality3(addr.subLocality ?: addr.featureName)
    constants.PostProperty_ViewModel.set_pincode3(addr.postalCode)

    return AddressComponents(
        pincode = addr.postalCode ?: "",
        country = addr.countryName ?: "",
        state = addr.adminArea ?: "",
        city = addr.locality ?: "",
        locality = addr.subLocality ?: addr.featureName ?: ""
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PincodePlaceSearch2222222(
    placesClient: PlacesClient,
    geocoder: Geocoder,
    show_Map_view: MutableState<Boolean>
)
{
    var pincode by remember { mutableStateOf("") }

    var country by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var latLng by remember { mutableStateOf<LatLng?>(null) }

    var localityQuery by remember { mutableStateOf("") }
    var localities by remember { mutableStateOf<List<AutocompletePrediction>>(emptyList()) }

    val selected_Map_Locality = constants.PostProperty_ViewModel.selected_Locality.collectAsStateWithLifecycle()
    var selectedLocality by remember { mutableStateOf("") }

    val data_Copy_Map = constants.PostProperty_ViewModel.pp_3_API_Data.collectAsStateWithLifecycle()

    if (selected_Map_Locality.value.isNotEmpty()){
        selectedLocality = selected_Map_Locality.value
    }

    LaunchedEffect(Unit, show_Map_view.value) {

        country = data_Copy_Map.value?.country ?: ""
        state = data_Copy_Map.value?.state ?: ""
        city = data_Copy_Map.value?.city ?: ""
        selectedLocality = data_Copy_Map.value?.locality ?: ""
        pincode = data_Copy_Map.value?.pincode ?: ""
    }

    val cant_find = remember { mutableStateOf(false) }

    var expanded by remember { mutableStateOf(false) }
    var cityOptions by remember { mutableStateOf<List<String>>(emptyList()) }

    Column(Modifier

    ) {

        Text("Enter Pincode")
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(1.dp, newGray, RoundedCornerShape(8.dp))
        )
        {
            TextField(
                value = pincode,
                onValueChange = {
                    pincode = it
                },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(6.5f)
                , placeholder = {
                    Text("Enter Pincode")
                }
                , colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedTextColor = newBlack,
                    unfocusedTextColor = newBlack,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            VerticalDivider()

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
                    .background(Color(0xffF7F0DC))
                    .weight(3.5f)
                    .clickable(enabled = pincode.length == 6) {
                        if (pincode.length == 6) {
                            CoroutineScope(Dispatchers.IO).launch {

                                val (ll, c, st, cn) = getLatLngForPincode1(pincode, geocoder)
                                latLng = ll
                                state = st ?: ""
                                country = cn ?: ""

                                val areas = fetchLocalitiesFromPostalAPI(pincode)
                                withContext(Dispatchers.Main) {
                                    cityOptions = areas
                                    if (areas.isNotEmpty()) {
                                        city = areas.first()
                                    }
                                }
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text("Get details", color = newBlue)
            }

        }

        Spacer(Modifier.height(12.dp))

        Text("Country")
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.White)
                .border(1.dp, newGray, RoundedCornerShape(8.dp))
            , verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        )
        {
            TextField(
                value = country,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxSize()
                , placeholder = {
                    Text("Country")
                }
                ,readOnly = true
                , colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedTextColor = newBlack,
                    unfocusedTextColor = newBlack,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }

        Spacer(Modifier.height(8.dp))

        Text("State")
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.White)
                .border(1.dp, newGray, RoundedCornerShape(8.dp))
            , verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        )
        {
            TextField(
                value = state,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxSize()
                , placeholder = {
                    Text("State")
                }
                ,readOnly = true
                , colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedTextColor = newBlack,
                    unfocusedTextColor = newBlack,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }

        Spacer(Modifier.height(8.dp))

        Text("City")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.White)
                .border(1.dp, newGray, RoundedCornerShape(8.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = city,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("City") },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxSize(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedTextColor = newBlack,
                        unfocusedTextColor = newBlack,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    cityOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                ClickHelper.getInstance().clickOnce {
                                    city = option
                                    expanded = false
                                }
                            }
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        Text("Locality")

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color.White)
                .border(1.dp, newGray, RoundedCornerShape(8.dp))
            , verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        )
        {
            TextField(
                value = selectedLocality.ifEmpty { localityQuery },
                onValueChange = {
                    selectedLocality = ""
                    localityQuery = it
                    if (latLng != null && localityQuery.length > 2) {
                        fetchLocalities(
                            localityQuery,
                            latLng!!,
                            placesClient,
                            city
                        ) { predictions ->
                            localities = predictions
                        }
                    } else {
                        localities = emptyList()
                    }
                },
                modifier = Modifier

                , placeholder = {
                    Text("Select your locality")
                }
                ,readOnly =  if(pincode.isNotEmpty() && country.isNotEmpty() && state.isNotEmpty() && city.isNotEmpty()) false else true
                , singleLine = true, colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedTextColor = newBlack,
                    unfocusedTextColor = newBlack,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }

        if (localities.isNotEmpty() && selectedLocality.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
            ) {
                localities.forEach { prediction ->
                    Text(
                        text = prediction.getPrimaryText(null).toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .noRippleClickable {
                                selectedLocality = prediction.getPrimaryText(null).toString()
                                localities = emptyList()
                            }
                            .padding(12.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .height(86.dp)
                .fillMaxWidth()
                .background(newBlue)
            , contentAlignment = Alignment.Center
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(.95f)
                    .fillMaxHeight(.75f)
                    .background(Color.White)
                    .padding(horizontal = 8.dp)
                    .clickable() {
                        show_Map_view.value = true
                    }
                , verticalAlignment = Alignment.CenterVertically
                , horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Row (
                    modifier = Modifier
                        .fillMaxHeight()
                    , verticalAlignment = Alignment.CenterVertically
                ){
                    Image(painter = painterResource(R.drawable.locationpinenquiry) , "")
                    Text("Pin Property on Map")
                }

                Image(painterResource(R.drawable.right_arrow), "")
            }

            if (localityQuery.isNotEmpty() && localities.isEmpty() && selectedLocality.isEmpty() && !cant_find.value) {
                Image(
                    painter = painterResource(R.drawable.location_find_failed),
                    contentDescription = "Location not found",
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .noRippleClickable {

                            cant_find.value = true
                        }
                )
            }

            if (country.isNotEmpty() && state.isNotEmpty() && city.isNotEmpty() && selectedLocality.isNotEmpty()) {

                constants.PostProperty_ViewModel.add_pp3_Data(
                    PP3_API_DC(
                        pincode =  pincode,
                        country = country,
                        state = state,
                        city = city,
                        locality = selectedLocality
                    )
                )

            }

        }
    }
}

suspend fun fetchLocalitiesFromPostalAPI(pincode: String): List<String> {
    return try {
        val url = URL("https://api.postalpincode.in/pincode/$pincode")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connectTimeout = 5000
        connection.readTimeout = 5000

        val response = connection.inputStream.bufferedReader().use { it.readText() }

        val jsonArray = JSONArray(response)
        val postOffices = jsonArray
            .getJSONObject(0)
            .getJSONArray("PostOffice")

        (0 until postOffices.length()).map { i ->
            postOffices.getJSONObject(i).getString("Name")
        }
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    }
}

fun fetchLocalitiesFromPincode(
    latLng: LatLng,
    placesClient: PlacesClient,
    fallbackQuery: String,
    callback: (List<String>) -> Unit
) {
    val bounds = RectangularBounds.newInstance(
        LatLng(latLng.latitude - 0.1, latLng.longitude - 0.1),
        LatLng(latLng.latitude + 0.1, latLng.longitude + 0.1)
    )

    val request = FindAutocompletePredictionsRequest.builder()
        .setLocationRestriction(bounds)
        .setTypesFilter(listOf("locality", "sublocality"))
        .setQuery(fallbackQuery)
        .build()

    placesClient.findAutocompletePredictions(request)
        .addOnSuccessListener { response ->
            val areas = response.autocompletePredictions
                .map { it.getPrimaryText(null).toString() }
                .distinct()
            callback(areas)
        }
        .addOnFailureListener {
            callback(emptyList())
        }
}

fun getLatLngForPincodeMultiple(
    pincode: String,
    geocoder: Geocoder
): Pair<LatLng?, List<String>> {
    val addresses = geocoder.getFromLocationName(pincode, 20)
    if (addresses.isNullOrEmpty()) return Pair(null, emptyList())

    val lat = addresses[0].latitude
    val lng = addresses[0].longitude

    val cityList = addresses.mapNotNull { it.locality ?: it.subAdminArea }
        .distinct()

    constants.PostProperty_ViewModel.add_Pinned_Lat_Long(LatLng(lat, lng))

    return Pair(LatLng(lat, lng), cityList)
}

@Composable
fun LocationValidationScreen(placesClient: PlacesClient) {
    var selectedState by remember { mutableStateOf<String?>(null) }
    var selectedCity by remember { mutableStateOf<String?>(null) }
    var selectedLatLng by remember { mutableStateOf<LatLng?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Text("Step 1: Select State", style = MaterialTheme.typography.bodyLarge)
        StateSearchFieldWithSelection(
            placesClient,
            onStateSelected = { state, latLng ->
                selectedState = state
                selectedLatLng = latLng
                selectedCity = null
            }
        )

        Spacer(Modifier.height(24.dp))

        Text("Step 2: Select City", style = MaterialTheme.typography.bodyLarge)
        if (selectedState != null) {
            CitySearchFieldWithSelection(
                placesClient,
                onCitySelected = { city, latLng ->
                    selectedCity = city
                    selectedLatLng = latLng
                }
            )
        } else {
            Text("⚠️ Please select a state first")
        }

        Spacer(Modifier.height(24.dp))

        Text("Step 3: Enter Pincode", style = MaterialTheme.typography.bodyLarge)
        if (selectedCity != null && selectedState != null) {
            PincodeField(
                placesClient,
                selectedCity = selectedCity,
                selectedState = selectedState
            )
        } else {
            Text("⚠️ Please select city & state first")
        }
    }
}

@Composable
fun StateSearchFieldWithSelection(
    placesClient: PlacesClient,
    onStateSelected: (String, LatLng?) -> Unit
) {
    var query by remember { mutableStateOf("") }
    var predictions by remember { mutableStateOf<List<Pair<String, String>>>(emptyList()) }
    var expanded by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                if (query.length > 2) {
                    fetchStatePredictionsParallel(query, placesClient) { newPredictions ->
                        predictions = newPredictions
                        expanded = newPredictions.isNotEmpty()
                    }
                } else {
                    predictions = emptyList()
                    expanded = false
                }
            },
            placeholder = { Text("Search state...") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            predictions.forEach { (name, placeId) ->
                DropdownMenuItem(
                    text = { Text(name) },
                    onClick = {
                        ClickHelper.getInstance().clickOnce {
                            query = name
                            expanded = false
                            predictions = emptyList()
                            fetchPlaceLatLng(placeId, placesClient) { latLng ->
                                onStateSelected(name, latLng)
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun CitySearchFieldWithSelection(
    placesClient: PlacesClient,
    onCitySelected: (String, LatLng?) -> Unit
) {
    var query by remember { mutableStateOf("") }
    var predictions by remember { mutableStateOf<List<AutocompletePrediction>>(emptyList()) }

    Column {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                if (query.length > 2) {
                    fetchPredictions(query, placesClient) { newPredictions ->
                        predictions = newPredictions
                    }
                } else {
                    predictions = emptyList()
                }
            },
            placeholder = { Text("Search city...") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        predictions.forEach { prediction ->
            Text(
                text = prediction.getPrimaryText(null).toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .noRippleClickable {
                        query = prediction.getPrimaryText(null).toString()
                        predictions = emptyList()
                        fetchPlaceLatLng(prediction.placeId, placesClient) { latLng ->
                            onCitySelected(query, latLng)
                        }
                    }
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun CitySearchField(placesClient: PlacesClient) {
    var query by remember { mutableStateOf("") }
    var predictions by remember { mutableStateOf<List<AutocompletePrediction>>(emptyList()) }
    var selectedLatLng by remember { mutableStateOf<LatLng?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                if (query.length > 2) {
                    fetchPredictions2(query, placesClient) { newPredictions ->
                        predictions = newPredictions
                    }
                } else {
                    predictions = emptyList()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search city...") },
            singleLine = true
        )

        predictions.forEach { prediction ->
            Text(
                text = prediction.getPrimaryText(null).toString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .noRippleClickable {
                        query = prediction.getPrimaryText(null).toString()
                        predictions = emptyList()
                        fetchPlaceLatLng(prediction.placeId, placesClient) { latLng ->
                            selectedLatLng = latLng
                            Log.d("CitySearch", "City: $query, LatLng: $latLng")
                        }
                    }
                    .padding(8.dp)
            )
        }

        selectedLatLng?.let {
            Spacer(Modifier.height(12.dp))
            Text("Lat: ${it.latitude}, Lng: ${it.longitude}")
        }
    }
}

fun fetchPredictions2(
    query: String,
    placesClient: PlacesClient,
    onResult: (List<AutocompletePrediction>) -> Unit
) {
    val request = FindAutocompletePredictionsRequest.builder()
        .setQuery(query)

        .build()

    placesClient.findAutocompletePredictions(request)
        .addOnSuccessListener { response ->
            onResult(response.autocompletePredictions)
        }
        .addOnFailureListener { exception ->
            Log.e("CitySearch", "Prediction fetch error", exception)
            onResult(emptyList())
        }
}

fun fetchPredictions(
    query: String,
    placesClient: PlacesClient,
    callback: (List<AutocompletePrediction>) -> Unit
) {
    val request = FindAutocompletePredictionsRequest.builder()
        .setQuery(query)
        .setTypeFilter(TypeFilter.CITIES)
        .build()

    placesClient.findAutocompletePredictions(request)
        .addOnSuccessListener { response ->
            callback(response.autocompletePredictions)
        }
        .addOnFailureListener {
            callback(emptyList())
        }
}

fun fetchPlaceLatLng(
    placeId: String,
    placesClient: PlacesClient,
    callback: (LatLng?) -> Unit
) {
    val request = FetchPlaceRequest.builder(
        placeId,
        listOf(Place.Field.LAT_LNG, Place.Field.NAME)
    ).build()

    placesClient.fetchPlace(request)
        .addOnSuccessListener { response ->
            callback(response.place.latLng)
        }
        .addOnFailureListener {
            callback(null)
        }
}

@Composable
fun StateSearchField(placesClient: PlacesClient) {
    val context = LocalContext.current
    var query by remember { mutableStateOf("") }
    var predictions by remember { mutableStateOf<List<Pair<String, String>>>(emptyList()) }
    var selectedLatLng by remember { mutableStateOf<LatLng?>(null) }
    var selectedState by remember { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
    {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                if (query.length > 2) {
                    fetchStatePredictionsParallel(query, placesClient) { newPredictions ->
                        predictions = newPredictions
                        expanded = newPredictions.isNotEmpty()
                    }
                } else {
                    predictions = emptyList()
                    expanded = false
                }
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search state...") },
            singleLine = true
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            predictions.forEach { (name, placeId) ->
                DropdownMenuItem(
                    text = { Text(name) },
                    onClick = {
                        ClickHelper.getInstance().clickOnce {
                            query = name
                            expanded = false
                            predictions = emptyList()

                            fetchPlaceLatLng(placeId, placesClient) { latLng ->
                                selectedLatLng = latLng
                                selectedState = name
                                Log.d("StateSearch", "State: $name, LatLng: $latLng")
                            }
                        }
                    }
                )
            }
        }

        selectedState?.let {
            Spacer(Modifier.height(12.dp))
            Text("Selected State: $it")
        }

        selectedLatLng?.let {
            Text("Lat: ${it.latitude}, Lng: ${it.longitude}")
        }
    }

}

fun fetchStatePredictionsParallel(
    query: String,
    placesClient: PlacesClient,
    callback: (List<Pair<String, String>>) -> Unit
) {
    val request = FindAutocompletePredictionsRequest.builder()
        .setQuery(query)
        .setTypesFilter(listOf("administrative_area_level_1"))
        .build()

    placesClient.findAutocompletePredictions(request)
        .addOnSuccessListener { response ->

            val stateResults = response.autocompletePredictions.map { prediction ->
                prediction.getPrimaryText(null).toString() to prediction.placeId
            }
            callback(stateResults)
        }
        .addOnFailureListener {
            callback(emptyList())
        }
}

@Composable
fun PincodeField(
    placesClient: PlacesClient,
    selectedCity: String?,
    selectedState: String?
) {
    var pincode by remember { mutableStateOf("") }
    var validationResult by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = pincode,
            onValueChange = { pincode = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Enter Pincode...") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                ClickHelper.getInstance().clickOnce {
                    validatePincodeWithCityState(
                        pincode,
                        selectedCity,
                        selectedState,
                        placesClient
                    ) { result ->
                        validationResult = result
                    }
                }
            },
            enabled = pincode.length >= 4 && selectedCity != null && selectedState != null
        ) {
            Text("Validate Pincode")
        }

        validationResult?.let {
            Spacer(Modifier.height(12.dp))
            Text(it)
        }
    }
}

fun validatePincodeWithCityState(
    pincode: String,
    city: String?,
    state: String?,
    placesClient: PlacesClient,
    callback: (String) -> Unit
) {
    if (city == null || state == null) {
        callback("Please select city and state first")
        return
    }

    val request = FindAutocompletePredictionsRequest.builder()
        .setQuery(pincode)
        .setTypesFilter(listOf("postal_code"))
        .build()

    placesClient.findAutocompletePredictions(request)
        .addOnSuccessListener { response ->
            if (response.autocompletePredictions.isEmpty()) {
                callback("Invalid Pincode")
                return@addOnSuccessListener
            }

            val placeId = response.autocompletePredictions[0].placeId
            val fetchRequest = FetchPlaceRequest.builder(
                placeId,
                listOf(Place.Field.ADDRESS_COMPONENTS)
            ).build()

            placesClient.fetchPlace(fetchRequest)
                .addOnSuccessListener { placeResponse ->
                    val components = placeResponse.place.addressComponents?.asList() ?: emptyList()

                    val postalCode = components.find { it.types.contains("postal_code") }?.name
                    val stateName = components.find { it.types.contains("administrative_area_level_1") }?.name
                    val cityName = components.find { it.types.contains("locality") }
                        ?.name ?: components.find { it.types.contains("administrative_area_level_2") }?.name

                    if (postalCode == pincode && stateName == state && cityName == city) {
                        callback("✅ Pincode matches City & State")
                    } else {
                        callback("❌ Pincode does not match selected City/State")
                    }
                }
                .addOnFailureListener {
                    callback("Error validating Pincode")
                }
        }
        .addOnFailureListener {
            callback("Error fetching Pincode")
        }
}

@Composable
fun PincodeToLocationField(placesClient: PlacesClient) {
    var pincode by remember { mutableStateOf("") }
    var city by remember { mutableStateOf<String?>(null) }
    var state by remember { mutableStateOf<String?>(null) }
    var country by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = pincode,
            onValueChange = { pincode = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Enter Pincode...") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = {

            },
            enabled = pincode.length >= 4
        ) {
            Text("Get City, State & Country")
        }

        Spacer(Modifier.height(16.dp))

        when {
            errorMessage != null -> Text("❌ $errorMessage", color = Color.Red)
            city != null && state != null && country != null -> {
                Text("✅ City: $city")
                Text("✅ State: $state")
                Text("✅ Country: $country")
            }
        }
    }
}

fun fetchCityStateFromPincode(
    pincode: String,
    placesClient: PlacesClient,
    callback: (String?, String?, String?, String?, Double?, Double?) -> Unit
) {
    val request = FindAutocompletePredictionsRequest.builder()
        .setQuery(pincode)
        .setTypesFilter(listOf("postal_code"))
        .build()

    placesClient.findAutocompletePredictions(request)
        .addOnSuccessListener { response ->
            if (response.autocompletePredictions.isEmpty()) {
                callback(null, null, null, "Invalid Pincode", null, null)
                return@addOnSuccessListener
            }

            val placeId = response.autocompletePredictions[0].placeId
            val fetchRequest = FetchPlaceRequest.builder(
                placeId,
                listOf(Place.Field.ADDRESS_COMPONENTS, Place.Field.LAT_LNG)
            ).build()

            placesClient.fetchPlace(fetchRequest)
                .addOnSuccessListener { placeResponse ->
                    val components = placeResponse.place.addressComponents?.asList() ?: emptyList()

                    val city = components.find { it.types.contains("locality") }?.name
                        ?: components.find { it.types.contains("administrative_area_level_2") }?.name
                    val state = components.find { it.types.contains("administrative_area_level_1") }?.name
                    val country = components.find { it.types.contains("country") }?.name
                    val latLng = placeResponse.place.latLng

                    if (city != null && state != null && country != null && latLng != null) {

                        callback(city, state, country, null, latLng.latitude, latLng.longitude)
                        constants.Start_Up_ViewModel.set_Latitude(latLng.latitude)
                        constants.Start_Up_ViewModel.set_Longitude(latLng.longitude)

                    } else {
                        callback(null, null, null, "City/State/Country not found for this Pincode", null, null)
                    }
                }
                .addOnFailureListener {
                    callback(null, null, null, "Error fetching place details", null, null)
                }
        }
        .addOnFailureListener {
            callback(null, null, null, "Error searching pincode", null, null)
        }
}

data class Quad<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)

fun getLatLngForPincode(pincode: String, geocoder: Geocoder): Pair<LatLng?, String?> {
    val addresses = geocoder.getFromLocationName(pincode, 1)
    return if (addresses != null && addresses.isNotEmpty()) {
        val lat = addresses[0].latitude
        val lng = addresses[0].longitude
        val city = addresses[0].locality ?: addresses[0].subAdminArea
        Pair(LatLng(lat, lng), city)
    } else {
        Pair(null, null)
    }
}

fun fetchPincodeLatLng(
    pincode: String,
    placesClient: PlacesClient,
    onResult: (LatLng?) -> Unit
) {
    val request = FindAutocompletePredictionsRequest.builder()
        .setQuery(pincode)
        .setTypesFilter(listOf("postal_code"))
        .build()

    placesClient.findAutocompletePredictions(request)
        .addOnSuccessListener { response ->
            val prediction = response.autocompletePredictions.firstOrNull()
            if (prediction != null) {
                fetchPlaceLatLng(prediction.placeId, placesClient) { latLng ->
                    onResult(latLng)
                }
            } else {
                onResult(null)
            }
        }
        .addOnFailureListener {
            onResult(null)
        }
}

fun fetchPlacesForPincode(
    query: String,
    center: LatLng,
    placesClient: PlacesClient,
    onResult: (List<AutocompletePrediction>) -> Unit
) {
    val bias = RectangularBounds.newInstance(
        LatLng(center.latitude - 0.05, center.longitude - 0.05),
        LatLng(center.latitude + 0.05, center.longitude + 0.05)
    )

    val request = FindAutocompletePredictionsRequest.builder()
        .setQuery(query)
        .setLocationBias(bias)
        .setTypesFilter(listOf("locality", "sublocality"))
        .build()

    placesClient.findAutocompletePredictions(request)
        .addOnSuccessListener { response ->
            onResult(response.autocompletePredictions)
        }
        .addOnFailureListener {
            onResult(emptyList())
        }
}

@Composable
fun GlobalPlaceSearch(placesClient: PlacesClient) {
    var query by remember { mutableStateOf("") }
    var predictions by remember { mutableStateOf<List<AutocompletePrediction>>(emptyList()) }
    var selectedPlace by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = selectedPlace.ifEmpty { query },
            onValueChange = {
                selectedPlace = ""
                query = it
                if (query.length > 2) {
                    fetchGlobalPlaces(query, placesClient) { newPredictions ->
                        predictions = newPredictions
                    }
                } else {
                    predictions = emptyList()
                }
            },
            label = { Text("Search any place worldwide") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        if (predictions.isNotEmpty() && selectedPlace.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
            ) {
                predictions.forEach { prediction ->
                    Text(
                        text = prediction.getFullText(null).toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .noRippleClickable {
                                selectedPlace = prediction.getFullText(null).toString()
                                predictions = emptyList()
                            }
                            .padding(12.dp)
                    )
                }
            }
        }
    }
}

fun fetchGlobalPlaces(
    query: String,
    placesClient: PlacesClient,
    callback: (List<AutocompletePrediction>) -> Unit
) {
    val request = FindAutocompletePredictionsRequest.builder()
        .setQuery(query)
        .build()

    placesClient.findAutocompletePredictions(request)
        .addOnSuccessListener { response ->
            callback(response.autocompletePredictions)
        }
        .addOnFailureListener {
            callback(emptyList())
        }
}

@SuppressLint("MissingPermission")
@Composable
fun MapSearchScreen1(
    placesClient: PlacesClient,
    fusedLocationProviderClient: FusedLocationProviderClient
) {
    var query by remember { mutableStateOf("") }
    var predictions by remember { mutableStateOf<List<AutocompletePrediction>>(emptyList()) }
    var selectedPlace by remember { mutableStateOf<String?>(null) }
    var selectedLatLng by remember { mutableStateOf<LatLng?>(null) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(20.5937, 78.9629), 4f)
    }

    Box(Modifier.fillMaxSize()) {

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = { latLng ->
                selectedLatLng = latLng
            }
        ) {
            selectedLatLng?.let {
                Marker(state = MarkerState(it), title = "Pinned Location")
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(16.dp)
        )
        {
            OutlinedTextField(
                value = selectedPlace ?: query,
                onValueChange = {
                    selectedPlace = null
                    query = it
                    if (query.length > 2) {
                        fetchGlobalPlaces(query, placesClient) { newPredictions ->
                            predictions = newPredictions
                        }
                    } else {
                        predictions = emptyList()
                    }
                },
                label = { Text("Search location") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            if (predictions.isNotEmpty() && selectedPlace == null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                ) {
                    predictions.forEach { prediction ->
                        Text(
                            text = prediction.getFullText(null).toString(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .noRippleClickable {
                                    selectedPlace = prediction.getFullText(null).toString()
                                    predictions = emptyList()

                                    fetchPlaceLatLngmap(
                                        prediction.placeId,
                                        placesClient
                                    ) { latLng ->
                                        if (latLng != null) {
                                            selectedLatLng = latLng
                                            cameraPositionState.move(
                                                CameraUpdateFactory.newLatLngZoom(latLng, 15f)
                                            )
                                        }
                                    }
                                }
                                .padding(12.dp)
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = {
                ClickHelper.getInstance().clickOnce {
                    fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                        location?.let {
                            val latLng = LatLng(it.latitude, it.longitude)
                            selectedLatLng = latLng
                            cameraPositionState.move(
                                CameraUpdateFactory.newLatLngZoom(latLng, 16f)
                            )
                        }
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Color.Blue,
            contentColor = Color.White
        ) {

        }
    }
}

@Composable
fun MapSearchScreen2(
    placesClient: PlacesClient,
    fusedLocationProviderClient: FusedLocationProviderClient
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var query by remember { mutableStateOf("") }
    var predictions by remember { mutableStateOf<List<AutocompletePrediction>>(emptyList()) }
    var selectedPlace by remember { mutableStateOf<String?>(null) }
    var selectedLatLng by remember { mutableStateOf<LatLng?>(null) }
    var pinnedLocation by remember { mutableStateOf<LatLng?>(null) }
    var addressText by remember { mutableStateOf("") }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(20.0, 77.0), 4f)
    }

    Box(modifier = Modifier.fillMaxSize()) {

        GoogleMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = { latLng ->
                pinnedLocation = latLng
                addressText = getAddressFromLatLng(context, latLng)
            }
        ) {
            pinnedLocation?.let {
                Marker(
                    state = MarkerState(position = it),
                    title = "Pinned Location"
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)

        )
        {
            TextField(
                value = selectedPlace ?: query,
                onValueChange = {
                    selectedPlace = null
                    query = it
                    if (query.length > 2) {
                        fetchGlobalPlaces(query, placesClient) { newPredictions ->
                            predictions = newPredictions
                        }
                    } else {
                        predictions = emptyList()
                    }
                }
                , leadingIcon = {
                    Image(painterResource(R.drawable.left_arrow) , "")
                }
                , placeholder = {
                    Text("search by area, city..")
                },
                singleLine = true
                , colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedTextColor = newBlack,
                    unfocusedTextColor = newBlack,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
                , modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = if (forTab()) 16.dp else rememberNotchHeightDp().value
                    )
            )

            Column {
                if (predictions.isNotEmpty() && selectedPlace == null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                    ) {
                        predictions.forEach { prediction ->
                            Text(
                                text = prediction.getFullText(null).toString(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .noRippleClickable {
                                        selectedPlace = prediction.getFullText(null).toString()
                                        predictions = emptyList()

                                        fetchPlaceLatLngmap(
                                            prediction.placeId,
                                            placesClient
                                        ) { latLng ->
                                            if (latLng != null) {
                                                selectedLatLng = latLng
                                                pinnedLocation = latLng
                                                addressText = getAddressFromLatLng(
                                                    context,
                                                    latLng
                                                )
                                                cameraPositionState.move(
                                                    CameraUpdateFactory.newLatLngZoom(latLng, 15f)
                                                )
                                            }
                                        }

                                    }
                                    .padding(12.dp)
                            )
                        }
                    }
                }
            }
        }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
            ) {

                FloatingActionButton(
                    onClick = {
                        fusedLocationProviderClient.lastLocation
                            .addOnSuccessListener { location: Location? ->
                                location?.let {
                                    val latLng = LatLng(it.latitude, it.longitude)
                                    pinnedLocation = latLng
                                    addressText = getAddressFromLatLng(context, latLng)
                                    coroutineScope.launch {
                                        cameraPositionState.animate(
                                            update = CameraUpdateFactory.newLatLngZoom(latLng, 15f),
                                            durationMs = 1000
                                        )
                                    }
                                }
                            }

                    },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(16.dp),
                    shape = CircleShape
                    , containerColor = Color.White
                ) {
                    Image(painterResource(R.drawable.location) , "")
                }

                if (pinnedLocation != null) {
                    Row(
                        modifier = Modifier

                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                            .background(Color.White)
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Spacer(Modifier.width(8.dp))
                        Column {

                            if (addressText.isNotEmpty()) {
                                Row (
                                    modifier = Modifier
                                        .fillMaxWidth()
                                    , verticalAlignment = Alignment.Top
                                    , horizontalArrangement = Arrangement.Start
                                ){
                                    Image(painterResource(R.drawable.locationpinenquiry) , "")
                                    Text(
                                        text = addressText,
                                        style = MaterialTheme.typography.bodyMedium,

                                    )
                                }
                            }
                        }
                    }

                }
                Static_Bottom(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )
                {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .fillMaxHeight(.65f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(newBlue), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Confirm Location",
                            color = Color.White,
                            fontSize = constants.textUnit(14),
                            fontFamily = constants.fontFamily(0)
                        )
                    }
                }

        }
    }
}

fun getFullAddress(context: Context, latLng: LatLng): String {
    return try {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        if (!addresses.isNullOrEmpty()) {
            val addr = addresses[0]
            listOfNotNull(
                addr.featureName,
                addr.subLocality,
                addr.locality,
                addr.subAdminArea,
                addr.adminArea,
                addr.postalCode,
                addr.countryName
            ).joinToString(", ")
        } else "Unknown Location"
    } catch (e: Exception) {
        "Unknown Location"
    }
}

suspend fun fetchPlaceDetailsForLatLng(
    latLng: LatLng,
    apiKey: String
): Pair<String, String> {
    val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
            "?location=${latLng.latitude},${latLng.longitude}" +
            "&radius=50&key=$apiKey"

    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) return "" to ""

        val json = JSONObject(response.body?.string() ?: "")
        val results = json.optJSONArray("results") ?: return "" to ""
        if (results.length() > 0) {
            val first = results.getJSONObject(0)
            val name = first.optString("name", "")
            val address = first.optString("vicinity", "")
            return name to address
        }
    }
    return "" to ""
}

@Composable
fun PinnedMapScreen(
    placesClient: PlacesClient,
    fusedLocationProviderClient: FusedLocationProviderClient
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var addressText by remember { mutableStateOf("") }
    var currentLatLng by remember { mutableStateOf<LatLng?>(null) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(20.0, 77.0), 4f)
    }

    LaunchedEffect(cameraPositionState) {
        snapshotFlow { cameraPositionState.isMoving }
            .collect { moving ->
                if (!moving) {
                    val target = cameraPositionState.position.target
                    currentLatLng = target
                    addressText = getAddressFromLatLng(context, target)
                }
            }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        GoogleMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(
                isMyLocationEnabled = false,
                isTrafficEnabled = false
            ),
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
                myLocationButtonEnabled = false,
                compassEnabled = true,
                rotationGesturesEnabled = true,
                tiltGesturesEnabled = true
            )
        )

        Image(
            painter = painterResource(R.drawable.locationpinenquiry),
            contentDescription = "Center Pin",
            modifier = Modifier
                .align(Alignment.Center)
                .size(48.dp)
        )

        FloatingActionButton(
            onClick = {
                fusedLocationProviderClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        location?.let {
                            val latLng = LatLng(it.latitude, it.longitude)
                            coroutineScope.launch {
                                cameraPositionState.animate(
                                    update = CameraUpdateFactory.newLatLngZoom(latLng, 16f),
                                    durationMs = 1000
                                )
                            }
                        }
                    }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            shape = CircleShape,
            containerColor = Color.White
        ) {
            Image(painterResource(R.drawable.location), contentDescription = "My Location")
        }

        if (addressText.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(Color.White)
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painterResource(R.drawable.locationpinenquiry),
                        contentDescription = "Pinned",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = addressText,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = {

                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = newBlue)
                ) {
                    Text("Confirm Location", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun PinnedPlacesMapScreen1(
    placesClient: PlacesClient,
    fusedLocationProviderClient: FusedLocationProviderClient
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var placeName by remember { mutableStateOf("") }
    var addressText by remember { mutableStateOf("") }
    var currentLatLng by remember { mutableStateOf<LatLng?>(null) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(20.0, 77.0), 4f)
    }

    LaunchedEffect(cameraPositionState) {
        snapshotFlow { cameraPositionState.isMoving }
            .collect { moving ->
                if (!moving) {
                    val target = cameraPositionState.position.target
                    currentLatLng = target

                    addressText = getAddressFromLatLng(context, target)

                    fetchPlaceDetails(placesClient, target) { name, address ->
                        if (name.isNotEmpty()) {
                            placeName = name
                        }
                        if (address.isNotEmpty()) {
                            addressText = address
                        }
                    }
                }
            }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        GoogleMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(),
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
                myLocationButtonEnabled = false,
                compassEnabled = true,
                rotationGesturesEnabled = true,
                tiltGesturesEnabled = true
            )
        )

        Image(
            painter = painterResource(R.drawable.locationpinenquiry),
            contentDescription = "Center Pin",
            modifier = Modifier
                .align(Alignment.Center)
                .size(48.dp)
        )

        if (addressText.isNotEmpty() || placeName.isNotEmpty()) {

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(Color.White)
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                if (placeName.isNotEmpty()) {
                    Text(placeName, style = MaterialTheme.typography.titleMedium)
                }
                if (addressText.isNotEmpty()) {
                    Text(addressText, style = MaterialTheme.typography.bodyMedium)
                }

                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = {

                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = newBlue)
                ) {
                    Text("Confirm Location", color = Color.White)
                }
            }
        }
    }
}

fun fetchPlaceDetails(
    placesClient: PlacesClient,
    latLng: LatLng,
    callback: (name: String, address: String) -> Unit
) {
    val placeFields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS)

    val request = FindCurrentPlaceRequest.newInstance(placeFields)

    val task = placesClient.findCurrentPlace(request)
    task.addOnSuccessListener { response ->
        if (response.placeLikelihoods.isNotEmpty()) {
            val place = response.placeLikelihoods.first().place
            callback(place.name ?: "", place.address ?: "")
        } else {
            callback("", "")
        }
    }
    task.addOnFailureListener {
        callback("", "")
    }
}

fun haversineDistance(a: LatLng, b: LatLng): Double {
    val R = 6371e3
    val lat1 = Math.toRadians(a.latitude)
    val lat2 = Math.toRadians(b.latitude)
    val dLat = Math.toRadians(b.latitude - a.latitude)
    val dLng = Math.toRadians(b.longitude - a.longitude)

    val h = sin(dLat / 2).pow(2.0) +
            cos(lat1) * cos(lat2) *
            sin(dLng / 2).pow(2.0)

    return 2 * R * asin(sqrt(h))
}

fun fetchPlaceLatLngmap(
    placeId: String,
    placesClient: PlacesClient,
    callback: (LatLng?) -> Unit
) {
    val request = FetchPlaceRequest.newInstance(
        placeId,
        listOf(Place.Field.LAT_LNG)
    )
    placesClient.fetchPlace(request)
        .addOnSuccessListener { response ->
            callback(response.place.latLng)
        }
        .addOnFailureListener {
            callback(null)
        }
}

@Composable
fun PinnedPlacesMapScreen(
    context: Context,
    fusedLocationProviderClient: FusedLocationProviderClient,
    apiKey: String
) {
    val coroutineScope = rememberCoroutineScope()

    var placeName by remember { mutableStateOf("") }
    var addressText by remember { mutableStateOf("") }
    var currentLatLng by remember { mutableStateOf<LatLng?>(null) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(20.0, 77.0), 5f)
    }

    LaunchedEffect(cameraPositionState) {
        snapshotFlow { cameraPositionState.isMoving }
            .collect { moving ->
                if (!moving) {
                    val target = cameraPositionState.position.target
                    currentLatLng = target

                    val fullAddress = getAddressFromLatLng(context, target)
                    addressText = fullAddress

                    coroutineScope.launch(Dispatchers.IO) {
                        val (name, _) = fetchPlaceDetailsForLatLng(target, apiKey)
                        withContext(Dispatchers.Main) {
                            placeName = name
                        }
                    }
                }
            }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        GoogleMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(),
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
                myLocationButtonEnabled = false,
                compassEnabled = true,
                rotationGesturesEnabled = true,
                tiltGesturesEnabled = true
            )
        )

        Image(
            painter = painterResource(R.drawable.locationpinenquiry),
            contentDescription = "Center Pin",
            modifier = Modifier
                .align(Alignment.Center)
                .size(48.dp)
        )

        FloatingActionButton(
            onClick = {
                fusedLocationProviderClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        location?.let {
                            val latLng = LatLng(it.latitude, it.longitude)
                            coroutineScope.launch {
                                cameraPositionState.animate(
                                    update = CameraUpdateFactory.newLatLngZoom(latLng, 16f),
                                    durationMs = 1000
                                )
                            }
                        }
                    }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            shape = CircleShape,
            containerColor = Color.White
        ) {

        }

        if (addressText.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(Color.White)
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {

                if (placeName.isNotEmpty()) {
                    Text(placeName, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(4.dp))
                }

                Text(addressText, style = MaterialTheme.typography.bodyMedium)

                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = {

                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
                ) {
                    Text("Confirm Location", color = Color.White)
                }
            }
        }
    }
}

suspend fun fetchPlaceDetailsForLatLng1(
    latLng: LatLng,
    apiKey: String
): Pair<String, String> {
    val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
            "?location=${latLng.latitude},${latLng.longitude}" +
            "&radius=50" +
            "&key=$apiKey"

    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()

    client.newCall(request).execute().use { response ->
        if (!response.isSuccessful) return "" to ""

        val json = JSONObject(response.body?.string() ?: "")
        val results = json.optJSONArray("results") ?: return "" to ""
        if (results.length() > 0) {
            val first = results.getJSONObject(0)
            val name = first.optString("name", "")
            val address = first.optString("vicinity", "")
            return name to address
        }
    }
    return "" to ""
}

fun getAddressFromLatLng(context: Context, latLng: LatLng): String {
    return try {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        if (!addresses.isNullOrEmpty()) {
            val addr = addresses[0]

            listOfNotNull(
                addr.featureName,
                addr.subLocality,
                addr.locality,
                addr.subAdminArea,
                addr.adminArea,
                addr.postalCode,
                addr.countryName
            ).joinToString(", ")
        } else {
            "Unknown Location"
        }
    } catch (e: Exception) {
        "Unknown Location"
    }
}
