package com.toletspot.houseforrent.RentoNewScreens

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.toletspot.houseforrent.API.StartUp_API.put_User_Location_API_Call
import com.toletspot.houseforrent.AppPreferences
import com.toletspot.houseforrent.Custom_Assets.Static_Bottom
import com.toletspot.houseforrent.Custom_Assets.rememberNotchHeightDp
import com.toletspot.houseforrent.Custom_Assets.toast
import com.toletspot.houseforrent.GlobalSnackbar
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.PostFlow
import com.toletspot.houseforrent.Navigation.UserCredentialsScreenFlow
import com.toletspot.houseforrent.NetworkStatus
import com.toletspot.houseforrent.R
import com.toletspot.houseforrent.UI_DataClass.PP3_API_DC
import com.toletspot.houseforrent.constants
import com.toletspot.houseforrent.fetchGlobalPlaces
import com.toletspot.houseforrent.fetchPlaceDetailsForLatLng
import com.toletspot.houseforrent.fetchPlaceLatLngmap
import com.toletspot.houseforrent.forTab
import com.toletspot.houseforrent.getAddressComponents
import com.toletspot.houseforrent.getFullAddress
import com.toletspot.houseforrent.noRippleClickable
import com.toletspot.houseforrent.rememberNetworkStatus
import com.toletspot.houseforrent.ui.theme.newBlack
import com.toletspot.houseforrent.ui.theme.newPurpleGradient
import com.toletspot.houseforrent.ui.theme.newPurpleGradientBorder
import com.toletspot.houseforrent.ui.theme.newWhite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun MapView(
    placesClient: PlacesClient,
    fusedLocationProviderClient: FusedLocationProviderClient,
    apiKey: String,
     initialLatLng: LatLng? = null
    ,navHostController: NavHostController
    ,state : Int
    ,onClose : ()-> Unit = {}
    ,onSkipClick : ()-> Unit = {}
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var network = rememberNetworkStatus()

    var query by remember { mutableStateOf("") }
    var predictions by remember { mutableStateOf<List<AutocompletePrediction>>(emptyList()) }
    var selectedPlace by remember { mutableStateOf<String?>(null) }
    var selectedLatLng by remember { mutableStateOf<LatLng?>(null) }
    var pinnedLocation by remember { mutableStateOf<LatLng?>(null) }

    var searchText by remember { mutableStateOf("") }
    var lastSearchedLatLng by remember { mutableStateOf<LatLng?>(null) }

    var focusManager = LocalFocusManager.current
    var keyboardController = LocalSoftwareKeyboardController.current

    var placeName by remember { mutableStateOf("") }
    var addressText by remember { mutableStateOf("") }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(20.0, 77.0), 4f)
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

    val markerState = remember { MarkerState() }

    LaunchedEffect(pinnedLocation) {
        pinnedLocation?.let {
            markerState.position = it
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        val uiSettings = MapUiSettings(
            zoomControlsEnabled = false
        )

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

        GoogleMap(
            modifier = Modifier.padding(vertical = if (forTab()) 16.dp else rememberNotchHeightDp().value).matchParentSize(),
            properties = mapProperties,
            cameraPositionState = cameraPositionState,
            uiSettings = uiSettings,
            onMapClick = { latLng ->
                if (network.value == NetworkStatus.Online) {
                    pinnedLocation = latLng
                    selectedLatLng = latLng
                    addressText = getFullAddress(context, latLng)
                    coroutineScope.launch(Dispatchers.IO) {
                        val (name, _) = fetchPlaceDetailsForLatLng(latLng, apiKey)
                        withContext(Dispatchers.Main) {
                            placeName = name
                        }
                    }
                } else {
                    GlobalSnackbar.show("Check your Internet connection")
                }
            }
        ) {
            val customPin = remember {
                bitmapDescriptorFromVector(
                    context = context,
                    vectorResId = R.drawable.locationpinrento
                )
            }

            pinnedLocation?.let { location ->

                key(location) {
                    Marker(
                        state = rememberMarkerState(position = location),
                        icon = customPin,
                        anchor = Offset(0.5f, 1f)
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable {}
                .align(Alignment.TopCenter)
        )
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding( vertical = if (forTab()) 16.dp else  rememberNotchHeightDp().value)
                    .background(Brush.verticalGradient(listOf(
                        Color(0xffFCFCFC),
                        Color(0xffFCFCFC),
                        Color(0xffFCFCFC)
                        ,Color.Transparent
                    )))
            )
            {

                Column (
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )
                {

                    if (state == 0) {
                        Text(
                            "Skip",
                            color = newBlack,
                            fontSize = constants.textUnit(12),
                            fontFamily = constants.fontFamily(0),
                            modifier = Modifier
                                .align(Alignment.End)
                                .noRippleClickable {
                                    onSkipClick()

                                }
                        )
                    }
                    else {
                        Image(painter = painterResource(R.drawable.close) , "",
                            modifier = Modifier.align(Alignment.End).noRippleClickable {
                                onClose()
                            }.padding(vertical = 4.dp)
                        )
                    }

                    constants.spacer(8)

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                        , verticalArrangement = Arrangement.Center
                        , horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Where are you looking to rent?"
                            , color = newBlack
                            , fontSize = constants.textUnit(20)
                            , fontFamily = constants.fontFamily(0)
                        )

                        constants.spacer(8)

                        Text(
                            "Your location helps us show the best \n rentals near you."
                            , color = newBlack
                            , fontSize = constants.textUnit(14)
                            , fontFamily = constants.fontFamily(1)
                            , textAlign = TextAlign.Center
                        )

                        constants.spacer(8)

                    }

                    TextField(
                        value = searchText,
                        onValueChange = { text ->
                            searchText = text

                            if (text.length > 2) {
                                if (network.value == NetworkStatus.Online) {
                                    fetchGlobalPlaces(text, placesClient) { newPredictions ->
                                        predictions = newPredictions
                                    }
                                }
                                else {
                                    GlobalSnackbar.show("Check your Internet connection")
                                }
                            } else {
                                predictions = emptyList()
                            }
                        },
                        leadingIcon = {
                            Image(
                                painter = painterResource(R.drawable.searchnotrento),
                                contentDescription = "",
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        trailingIcon = {
                            if (searchText.isNotEmpty()) {
                                Image(
                                    painter = painterResource(R.drawable.close),
                                    contentDescription = "",
                                    modifier = Modifier.noRippleClickable {
                                        searchText = ""
                                        predictions = emptyList()
                                    }
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                if (network.value == NetworkStatus.Online) {

                                    lastSearchedLatLng?.let { latLng ->
                                        pinnedLocation = latLng

                                        cameraPositionState.move(
                                            CameraUpdateFactory.newLatLngZoom(latLng, 15f)
                                        )

                                        addressText = getFullAddress(context, latLng)

                                        coroutineScope.launch(Dispatchers.IO) {
                                            val (name, _) = fetchPlaceDetailsForLatLng(
                                                latLng,
                                                apiKey
                                            )
                                            withContext(Dispatchers.Main) {
                                                placeName = name
                                            }
                                        }
                                    }
                                    focusManager.clearFocus()
                                    keyboardController?.hide()
                                }
                                else {
                                    GlobalSnackbar.show("Check your Internet connection")
                                }

                            }
                        ),
                        placeholder = { Text("Search location") },
                        singleLine = true,
                        shape = RoundedCornerShape(4.dp),
                        textStyle = TextStyle(
                            color = newBlack,
                            fontSize = constants.textUnit(12),
                            fontFamily = constants.fontFamily(2)
                        ),
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
                                            searchText = prediction.getFullText(null).toString()
                                            predictions = emptyList()

                                            fetchPlaceLatLngmap(prediction.placeId, placesClient) { latLng ->
                                                latLng?.let {
                                                    pinnedLocation = it
                                                    lastSearchedLatLng = it
                                                    selectedLatLng = it
                                                    addressText = getFullAddress(context, it)

                                                    coroutineScope.launch(Dispatchers.IO) {
                                                        val (name, _) = fetchPlaceDetailsForLatLng(it, apiKey)
                                                        withContext(Dispatchers.Main) {
                                                            placeName = name
                                                            focusManager.clearFocus()
                                                            keyboardController?.hide()
                                                        }
                                                    }

                                                    cameraPositionState.move(
                                                        CameraUpdateFactory.newLatLngZoom(it, 15f)
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
        )
        {
            val context = LocalContext.current

            var showPermissionDialog by remember { mutableStateOf(false) }
            var showGpsDialog by remember { mutableStateOf(false) }

            FloatingActionButton(
                onClick = {

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
                        .border(1.dp , Brush.linearGradient(newPurpleGradientBorder) , RoundedCornerShape(8.dp))
                        .noRippleClickable{
                            if (addressText.isNotEmpty() && pinnedLocation != null) {

                                val components = getAddressComponents(context, pinnedLocation!!)

                                constants.PostProperty_ViewModel.add_pp3_Data(
                                    PP3_API_DC(
                                        pincode = components.pincode,
                                        country = components.country,
                                        state = components.state,
                                        city = components.city,
                                        locality = components.locality + if (placeName.isNotEmpty()) ", $placeName" else ""
                                    )
                                )

                                constants.PostProperty_ViewModel.add_Pinned_Lat_Long(pinnedLocation!!)
                                constants.PostProperty_ViewModel.set__selectedLocality3(addressText)
                                constants.PostProperty_ViewModel.add_Selected_Locality(addressText)

                                constants.PostProperty_ViewModel.set_country3(components.country)
                                constants.PostProperty_ViewModel.set_country3(components.country)
                                constants.PostProperty_ViewModel.set_state3(components.state)
                                constants.PostProperty_ViewModel.set_city3(components.city)
                                constants.PostProperty_ViewModel.set__selectedLocality3(addressText)
                                constants.PostProperty_ViewModel.set_pincode3(components.pincode)

                                if (constants.PostProperty_ViewModel.postFlow.value == PostFlow.NONE) {

                                    constants.Start_Up_ViewModel.set_Country(components.country)
                                    constants.Start_Up_ViewModel.set_State(components.state)
                                    constants.Start_Up_ViewModel.set_City(components.city)

                                    constants.Start_Up_ViewModel.set_Pincode(components.pincode)
                                    constants.Start_Up_ViewModel.set_Longitude(pinnedLocation!!.longitude)
                                    constants.Start_Up_ViewModel.set_Latitude(pinnedLocation!!.latitude)
                                    AppPreferences.save_Lat_Long(lat = pinnedLocation?.longitude.toString() ?: "", pinnedLocation?.latitude.toString() ?: "")

                                    if (
                                        constants.Start_Up_ViewModel.country.value.isNotEmpty()
                                        && constants.Start_Up_ViewModel.state.value.isNotEmpty()
                                        && constants.Start_Up_ViewModel.city.value.isNotEmpty()
                                        && constants.Start_Up_ViewModel.pincode.value.isNotEmpty()
                                        && constants.Start_Up_ViewModel.latitude.value.isNotEmpty()
                                        && constants.Start_Up_ViewModel.longitude.value.isNotEmpty()
                                    ) {
                                        AppPreferences.save_User_Lcation(constants.Start_Up_ViewModel.city.value)
                                        put_User_Location_API_Call(
                                            resultCallback = { result ->
                                                when (result) {
                                                    0 -> {

                                                        AppPreferences.save_Location_Received(1)
                                                        toast("LOCATION STORED SUCCESSFULLY")

                                                        if (state == 0) {
                                                            navHostController?.navigate(
                                                                UserCredentialsScreenFlow.Common_Screen.route
                                                            ) {
                                                                popUpTo(navHostController.graph.startDestinationId) {
                                                                    inclusive = true
                                                                }
                                                            }
                                                        }
                                                        else {
                                                            AppPreferences.save_User_Lcation(" ${components.city}, ${components.state}")
                                                            onClose()
                                                        }
                                                    }

                                                    1 -> {

                                                        toast("Something went wrong while storing location")
                                                    }

                                                    2 -> {

                                                        constants.Common_H_ViewModel.changeStatus(
                                                            true
                                                        )
                                                    }
                                                }
                                            }
                                        )
                                    } else {
                                        var text = when {
                                            constants.Start_Up_ViewModel.country.value.isEmpty() -> "Unable to update the Country"
                                            constants.Start_Up_ViewModel.state.value.isEmpty() -> "Unable to update the State"
                                            constants.Start_Up_ViewModel.city.value.isEmpty() -> "Unable to update the City"
                                            else -> "Unable to update the Pincode"
                                        }
                                        GlobalSnackbar.show(text)
                                    }
                                }

                            }
                            else {
                                toast("Select Address in the map")
                            }
                        }
                    , contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Choose Location",
                        color = Color.White,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(0)
                    )
                }
            }
        }
    }
}

fun bitmapDescriptorFromVector(
    context: Context,
    @DrawableRes vectorResId: Int,
    sizeDp: Int = 36
): BitmapDescriptor {
    val drawable = ContextCompat.getDrawable(context, vectorResId)!!
    val sizePx = (sizeDp * context.resources.displayMetrics.density).toInt()
    drawable.setBounds(0, 0, sizePx, sizePx)

    val bitmap = Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.draw(canvas)

    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

@Composable
fun AnimatedMapView(enable : MutableState<Boolean>, state: Int, latlong : Pair<String , String>
                    , navHostController: NavHostController
){

    val context = LocalContext.current
    val placesClient = Places.createClient(context)

    val (lat, lon) = latlong

    val initialLatLng = remember {
        if (lat.isNotEmpty() && lon.isNotEmpty()) {
            LatLng(lat.toDouble(), lon.toDouble())
        } else {
            null
        }
    }

        AnimatedVisibility(
            visible = enable.value,
            modifier = Modifier,
            enter = slideInVertically(tween(400)) { it },
            exit = slideOutVertically(tween(400)) { it }
        ) {

            MapView(
                placesClient = placesClient,
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
                    context
                ),
                apiKey = context.getString(R.string.maps_api_key),
                initialLatLng = initialLatLng,
                navHostController,
                state = state,
                onClose = {
                    enable.value = false
                },
                onSkipClick = {
                    enable.value = false
                    AppPreferences.save_Verify_Complete(1)
                    AppPreferences.save_Location_Received(1)
                    UserCredentialsScreenFlow.Common_Screen.route
                    constants.API_Vm.isLoading_Reels = true
                    constants.API_Vm.totalPages_Reels = 1
                    navHostController.navigate(UserCredentialsScreenFlow.Common_Screen.route)
                }
            )

    }
}
