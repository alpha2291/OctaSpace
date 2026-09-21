package com.toletspot.houseforrent.Custom_Assets

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.DashPathEffect
import android.location.Geocoder
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.compose.PlayerSurface
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_My_Leads_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Reels_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Reels_Property_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.PostUser
import com.toletspot.houseforrent.API.API_Result_Handling
import com.toletspot.houseforrent.API.StartUp_API.Put_User_Interests_API_Call
import com.toletspot.houseforrent.API.StartUp_API.get_Form_Preview_API_CALL
import com.toletspot.houseforrent.API.StartUp_API.put_User_Location_API_Call
import com.toletspot.houseforrent.AppPreferences
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.toSelectedOptionsForm4
import com.toletspot.houseforrent.Navigation.EnquiriesFlow
import com.toletspot.houseforrent.Navigation.ProfileScreenFlow
import com.toletspot.houseforrent.Navigation.UserCredentialsScreenFlow
import com.toletspot.houseforrent.Navigation.VideosScreenFlow
import com.toletspot.houseforrent.R
import com.toletspot.houseforrent.UI_DataClass.Chat_Property_Structure_DC
import com.toletspot.houseforrent.UI_DataClass.Common_DropDown2Options_DC
import com.toletspot.houseforrent.UI_DataClass.Custom_BottomSheetState
import com.toletspot.houseforrent.UI_DataClass.Custom_PopUpState
import com.toletspot.houseforrent.UI_DataClass.Profile_Handle_Back
import com.toletspot.houseforrent.isConnected
import com.toletspot.houseforrent.constants.Companion.activity
import com.toletspot.houseforrent.ui.theme.newBlack
import com.toletspot.houseforrent.ui.theme.newBlue
import com.toletspot.houseforrent.ui.theme.newGray
import com.toletspot.houseforrent.ui.theme.newLightBlue
import com.toletspot.houseforrent.ui.theme.newWhite
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.time.LocalDate
import java.time.YearMonth

import kotlin.math.abs

import java.time.Instant
import java.util.UUID
import kotlin.reflect.full.memberProperties

import java.time.format.DateTimeFormatter
import java.time.ZoneId

import android.graphics.BitmapFactory
import android.location.LocationManager
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView
import com.google.android.gms.location.Priority
import com.toletspot.houseforrent.API.StartUp_API.get_Form_Publish_API_CALL
import com.toletspot.houseforrent.GlobalSnackbar
import com.toletspot.houseforrent.Home_Screen.Common_H_ViewModel
import com.toletspot.houseforrent.Home_Screen.ProfileModule.ClickGuard
import com.toletspot.houseforrent.Navigation.PostPropertyFlow
import com.toletspot.houseforrent.NetworkStatus
import com.toletspot.houseforrent.SingleSlideshow
import com.toletspot.houseforrent.SlideshowAnimation
import com.toletspot.houseforrent.UI_DataClass.PP3_API_DC
import com.toletspot.houseforrent.UI_DataClass.Selected_Dates_Calender
import com.toletspot.houseforrent.constants
import com.toletspot.houseforrent.noRippleClickable
import com.toletspot.houseforrent.rememberNetworkStatus
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.text.NumberFormat
import androidx.compose.runtime.snapshots.SnapshotStateList

import androidx.core.net.toUri
import androidx.media3.common.PlaybackException
import com.bumptech.glide.Glide
import com.toletspot.houseforrent.Chat.FirebaseHelper
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentoMediaDC.Image
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.ViewDetailsFlow
import com.toletspot.houseforrent.Home_Screen.Video_Module.PhotoRequestAssistant
import com.toletspot.houseforrent.Start_Up.CommonText
import com.toletspot.houseforrent.forTab
import com.toletspot.houseforrent.ui.theme.newPurpleGradient
import com.toletspot.houseforrent.ui.theme.newPurpleGradientBorder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.util.Base64
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.PostFlow
import com.toletspot.houseforrent.Home_Screen.Video_Module.isWithinLast5DaysOfValidity
import com.toletspot.houseforrent.S3Uploader
import com.toletspot.houseforrent.Start_Up.NumberInput_Rento
import com.toletspot.houseforrent.ui.theme.newRedGradienBg
import com.toletspot.houseforrent.ui.theme.newRedGradienBorder
import com.toletspot.houseforrent.ui.theme.rentoLightGray
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.Date

import java.util.Locale
import kotlin.text.ifEmpty

fun getDeviceId(context: Context): String {
    val androidId = Settings.Secure.getString(
        activity.contentResolver, Settings.Secure.ANDROID_ID
    )
    val androidId_UUID = UUID.nameUUIDFromBytes(androidId.toByteArray(charset("utf8")))
    val device_UDID = androidId_UUID.toString()
    return device_UDID

}

fun getDeviceType(): String {
    return "Android"
}

private var currentToast: Toast? = null

fun toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    currentToast?.cancel()
    currentToast = Toast.makeText(activity, message, length)
    currentToast?.show()
}

@Composable
fun rememberNotchHeightDp(): State<Dp> {
    val context = LocalContext.current
    val density = LocalDensity.current

    val notchHeight = remember { mutableStateOf(0.dp) }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && context is Activity) {
            val insets = context.window.decorView.rootWindowInsets
            val cutout = insets?.displayCutout
            val notchPx = cutout?.safeInsetTop ?: 0

            notchHeight.value = with(density) { notchPx.toDp() }
        }
    }

    return notchHeight
}

fun Modifier.dashedBorder(
    strokeWidth: Dp,
    dashLength: Dp,
    gapLength: Dp,
    color: Color,
    cornerRadius: Dp = 0.dp
): Modifier = this.then(
    Modifier.drawBehind {
        val paint = Paint().asFrameworkPaint().apply {
            this.isAntiAlias = true
            this.color = color.toArgb()
            this.style = android.graphics.Paint.Style.STROKE
            this.strokeWidth = strokeWidth.toPx()
            this.pathEffect = DashPathEffect(
                floatArrayOf(dashLength.toPx(), gapLength.toPx()), 0f
            )
        }

        val radius = cornerRadius.toPx()
        val inset = strokeWidth.toPx() / 2

        drawContext.canvas.nativeCanvas.drawRoundRect(
            inset,
            inset,
            size.width - inset,
            size.height - inset,
            radius,
            radius,
            paint
        )
    }
)

@Composable
fun PhoneNumberInput233(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    countryCode: String,
    onCountryCodeChange: (String) -> Unit
) {

    val context = LocalContext.current

    val countryCodes = constants.Start_Up_ViewModel.country_Code_Handler.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    var isLimit by remember { mutableStateOf(10) }

    val error = remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp)
            .border(1.dp, newGray, RoundedCornerShape(8.dp))

    ) {

        Box (
            modifier = Modifier
                .weight(1.8f)

            , contentAlignment = Alignment.Center
        ){
            Row (
                modifier = Modifier
                    .wrapContentWidth()
                    .noRippleClickable { expanded = true }
                , verticalAlignment = Alignment.Top
                , horizontalArrangement = Arrangement.SpaceEvenly
            )
            {
                Text(
                    text = countryCode,
                    fontSize = constants.textUnit(12),
                    modifier = Modifier
                    , fontWeight = FontWeight.Bold
                )

                SubcomposeAsyncImage(
                    model = R.drawable.arrowdown, "", modifier = Modifier
                        .size(14.dp)
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
                , containerColor = newWhite
                , modifier = Modifier.height(400.dp)
            )
            {
                countryCodes.value.forEach { code ->
                    DropdownMenuItem(
                        text = { Text(code.country_Code) },
                        onClick = {
                            ClickHelper.getInstance().clickOnce {
                                isLimit = constants.Start_Up_ViewModel.getLimitForCountryCode(
                                    code.country_Code,
                                    countryCodes.value
                                )!!
                                onCountryCodeChange(code.country_Code)
                                expanded = false
                            }
                        }
                        , colors = MenuDefaults.itemColors(
                            textColor = newBlack
                        )
                    )
                }
            }
        }

        VerticalDivider(modifier = Modifier.fillMaxHeight() , thickness = 1.dp , newGray)

        Box(
            modifier = Modifier
                .weight(8.2f)
        )
        {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = phoneNumber,
                    onValueChange = {
                        if (it.length <= isLimit) {
                            if (it.all { char -> char.isDigit() }) {
                                onPhoneNumberChange(it)
                                error.value = false
                            }
                        } else {
                            error.value = true
                        }
                    },
                    placeholder = { Text("Mobile number", fontSize = constants.textUnit(12)) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
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

                if (error.value) {
                    Text(
                        text = "Invalid mobile number",
                        color = Color.Red,
                        fontSize = constants.textUnit(10),
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                }
            }

        }
    }
}

@Composable
fun PhoneNumberInput(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    countryCode: String,
    onCountryCodeChange: (String) -> Unit,
    isError: Boolean
) {
    val countryCodes = constants.Start_Up_ViewModel.country_Code_Handler.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var isLimit by remember { mutableStateOf(10) }

    Column(modifier = Modifier.fillMaxWidth()) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(if (forTab()) 64.dp else 56.dp)
                .padding(horizontal = 16.dp)
                .border(
                    width = 1.dp,
                    color = if (isError) Color.Red else newGray,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {

            Box(
                modifier = Modifier.weight(1.8f),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .noRippleClickable { expanded = true },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = countryCode,
                        fontFamily = constants.fontFamily(0),
                        fontSize = constants.textUnit(12),
                        fontWeight = FontWeight.Bold
                    )
                    SubcomposeAsyncImage(
                        model = if (expanded) R.drawable.arrowup  else R.drawable.arrowdown,
                        contentDescription = "",
                        modifier = Modifier.size(14.dp)
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    containerColor = newWhite,
                    modifier = Modifier.height(400.dp)
                ) {
                    countryCodes.value.forEach { code ->
                        DropdownMenuItem(
                            text = { Text(code.country_Code, fontSize = constants.textUnit(12), fontFamily = constants.fontFamily(0)) },
                            onClick = {
                                ClickHelper.getInstance().clickOnce {
                                    isLimit = constants.Start_Up_ViewModel
                                        .getLimitForCountryCode(
                                            code.country_Code,
                                            countryCodes.value
                                        ) ?: 10
                                    onCountryCodeChange(code.country_Code)
                                    expanded = false
                                }
                            },
                            colors = MenuDefaults.itemColors(textColor = newBlack)
                        )
                    }
                }
            }

            VerticalDivider(
                modifier = Modifier.fillMaxHeight(),
                thickness = 1.dp,
                color = newGray
            )

            Box(modifier = Modifier.weight(8.2f)) {
                TextField(
                    value = phoneNumber,
                    onValueChange = {
                        if (it.length <= isLimit && it.all(Char::isDigit)) {
                            onPhoneNumberChange(it)
                        }
                    },
                    placeholder = {
                        Text("Mobile number",fontFamily = constants.fontFamily(0), fontSize = constants.textUnit(12))
                    },
                    textStyle = TextStyle(
                        fontFamily = constants.fontFamily(0)),
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(
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
            }
        }

        if (isError) {
            Text(
                text = "Invalid mobile number",
                color = Color.Red,
                fontSize = constants.textUnit(10),
                modifier = Modifier.padding(start = 24.dp, top = 4.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneNumberInput1(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    countryCode: String,
    onCountryCodeChange: (String) -> Unit
) {
    val context = LocalContext.current
    val countryCodes = constants.Start_Up_ViewModel.country_Code_Handler.collectAsState()

    var isLimit by remember { mutableStateOf(10) }

    var showSheet by remember { mutableStateOf(false) }

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = bottomSheetState,
            containerColor = newWhite
        )
        {
            LazyColumn (){
                items(countryCodes.value) { code ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .noRippleClickable {
                                isLimit = constants.Start_Up_ViewModel.getLimitForCountryCode(
                                    code.country_Code,
                                    countryCodes.value
                                )!!
                                onCountryCodeChange(code.country_Code)
                                showSheet = false
                            }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = code.country_Name,
                            modifier = Modifier.weight(1f),
                            fontSize = constants.textUnit(14),
                            color = newBlack
                        )
                        Text(
                            text = code.country_Code,
                            fontWeight = FontWeight.Bold,
                            fontSize = constants.textUnit(14),
                            color = newBlack
                        )
                    }
                    Divider(color = newGray.copy(alpha = 0.3f))
                }
            }
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp)
            .border(1.dp, newGray, RoundedCornerShape(8.dp))
    ) {

        Box(
            modifier = Modifier
                .weight(2.3f)
                .padding(end = 4.dp)
                .noRippleClickable { showSheet = true },
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,

                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = countryCode,
                    fontSize = constants.textUnit(12),
                    fontWeight = FontWeight.Bold,
                    color = newBlack
                )

                Spacer(modifier = Modifier.width(4.dp))

                SubcomposeAsyncImage(
                    model = R.drawable.arrowdown,
                    contentDescription = "",
                    modifier = Modifier.size(14.dp)
                )
            }
        }

        VerticalDivider(modifier = Modifier.fillMaxHeight(), thickness = 1.dp, color = newGray)

        Box(modifier = Modifier.weight(7.7f)) {
            TextField(
                value = phoneNumber,
                onValueChange = {
                    if (it.length <= isLimit && it.all { c -> c.isDigit() }) {
                        onPhoneNumberChange(it)
                    }
                },
                placeholder = { Text("Mobile number", fontSize = constants.textUnit(12)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
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
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WA_NumberInput(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    countryCode: String,
    onCountryCodeChange: (String) -> Unit
) {
    val context = LocalContext.current
    val countryCodes = constants.Start_Up_ViewModel.country_Code_Handler.collectAsState()

    var isLimit by remember { mutableStateOf(10) }

    var showSheet by remember { mutableStateOf(false) }

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = bottomSheetState,
            containerColor = newWhite
        )
        {
            LazyColumn {
                items(countryCodes.value) { code ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .noRippleClickable {
                                isLimit = constants.Start_Up_ViewModel.getLimitForCountryCode(
                                    code.country_Code,
                                    countryCodes.value
                                )!!
                                onCountryCodeChange(code.country_Code)
                                showSheet = false
                            }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = code.country_Name,
                            modifier = Modifier.weight(1f),
                            fontSize = constants.textUnit(14),
                            color = newBlack
                        )
                        Text(
                            text = code.country_Code,
                            fontWeight = FontWeight.Bold,
                            fontSize = constants.textUnit(14),
                            color = newBlack
                        )
                    }
                    Divider(color = newGray.copy(alpha = 0.3f))
                }
            }
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)

            .border(1.dp, newGray, RoundedCornerShape(8.dp))
    ) {

        Box(
            modifier = Modifier
                .weight(2.3f)
                .padding(end = 4.dp)
                .noRippleClickable { showSheet = true },
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,

                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = countryCode,
                    fontSize = constants.textUnit(12),
                    fontWeight = FontWeight.Bold,
                    color = newBlack
                )

                Spacer(modifier = Modifier.width(4.dp))

                SubcomposeAsyncImage(
                    model = R.drawable.arrowdown,
                    contentDescription = "",
                    modifier = Modifier.size(14.dp)
                )
            }
        }

        VerticalDivider(modifier = Modifier.fillMaxHeight(), thickness = 1.dp, color = newGray)

        Box(modifier = Modifier.weight(7.7f)) {
            TextField(
                value = phoneNumber,
                onValueChange = {
                    if (it.length <= isLimit && it.all { c -> c.isDigit() }) {
                        onPhoneNumberChange(it)
                    }
                },
                placeholder = { Text("Enter Mobile number", fontSize = constants.textUnit(12)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
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
        }
    }
}

@Composable
fun WA_NumberInput1(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    countryCode: String,
    onCountryCodeChange: (String) -> Unit,
    checkBox1: MutableState<Boolean>
) {

    val context = LocalContext.current

    val countryCodes = constants.Start_Up_ViewModel.country_Code_Handler.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    var isLimit by remember { mutableStateOf(10) }

    DisposableEffect(Unit) {
        onDispose {
            checkBox1.value = false
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(1.dp, newGray, RoundedCornerShape(8.dp))

    ) {

        Box (
            modifier = Modifier
                .weight(1.9f)
                .fillMaxHeight()
                .background(Color(0xffF4F4F4))

            , contentAlignment = Alignment.Center
        ){
            Row (
                modifier = Modifier
                    .wrapContentWidth()
                    .noRippleClickable { expanded = true }
                    , verticalAlignment = Alignment.CenterVertically
                , horizontalArrangement = Arrangement.SpaceEvenly
            ){
                Text(
                    text = countryCode,
                    fontSize = constants.textUnit(12),
                    fontFamily = constants.fontFamily(2),
                    modifier = Modifier
                    , fontWeight = FontWeight.Bold
                )

                SubcomposeAsyncImage(
                    model = R.drawable.arrowdown, "", modifier = Modifier
                        .size(14.dp)

                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
                , containerColor = newWhite
                , modifier = Modifier.height(400.dp)
            )
            {
                countryCodes.value.forEach { code ->
                    DropdownMenuItem(
                        text = { Text(code.country_Code , fontSize = constants.textUnit(14), fontFamily = constants.fontFamily(1)) },
                        onClick = {
                            ClickHelper.getInstance().clickOnce {
                                isLimit = constants.Start_Up_ViewModel.getLimitForCountryCode(
                                    code.country_Code,
                                    countryCodes.value
                                )!!
                                onCountryCodeChange(code.country_Code)
                                expanded = false
                            }
                        }
                        , colors = MenuDefaults.itemColors(
                            textColor = newBlack
                        )
                    )
                }
            }
        }

        VerticalDivider(modifier = Modifier.fillMaxHeight() , thickness = 1.dp , newGray)

        Box(
            modifier = Modifier
                .weight(8.2f)
        ) {
            TextField(
                value = phoneNumber,
                onValueChange = {
                    if (it.length <= isLimit) {
                        if (it.all { char -> char.isDigit() }) onPhoneNumberChange(it)
                    }

                    if (checkBox1.value && phoneNumber != it){
                        checkBox1.value = false
                    }

                },

                textStyle = TextStyle(
                    color = newBlack,
                    fontSize = constants.textUnit(12)
                    , fontFamily = constants.fontFamily(2)
                ),
                placeholder = { Text("Mobile number" , fontSize = constants.textUnit(12) , fontFamily = constants.fontFamily(2)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = newWhite,
                    unfocusedContainerColor = newWhite,

                    focusedPlaceholderColor = newGray,
                    unfocusedPlaceholderColor = newGray,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,

                    disabledContainerColor = newWhite,

                    focusedTextColor = newBlack,
                    unfocusedTextColor = newBlack,
                    disabledTextColor = newBlack,

                    disabledPlaceholderColor = newGray,

                    disabledIndicatorColor = Color.Transparent
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneNumberInput_Settings(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    countryCode: String,
    onCountryCodeChange: (String) -> Unit,
    onClick: () -> Unit
) {
    val countryCodes = constants.Start_Up_ViewModel.country_Code_Handler.collectAsState()
    var isLimit by remember { mutableStateOf(10) }
    var expanded by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(1.dp, newGray, RoundedCornerShape(8.dp))
    ) {

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .noRippleClickable { expanded = true }
                .padding(start = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = countryCode,
                    fontSize = constants.textUnit(12),
                    fontFamily = constants.fontFamily(0),
                    color = newBlack
                )
                Spacer(modifier = Modifier.width(4.dp))
                SubcomposeAsyncImage(
                    model = R.drawable.arrowdown,
                    contentDescription = "",
                    modifier = Modifier.size(14.dp)
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.height(400.dp),
                offset = DpOffset(0.dp, 0.dp),
                properties = PopupProperties(focusable = true),
                containerColor = newWhite
            ) {
                countryCodes.value.forEach { code ->
                    DropdownMenuItem(
                        text = { Text(code.country_Code) },
                        onClick = {
                            ClickHelper.getInstance().clickOnce {
                                isLimit = constants.Start_Up_ViewModel.getLimitForCountryCode(
                                    code.country_Code,
                                    countryCodes.value
                                ) ?: 10
                                onCountryCodeChange(code.country_Code)
                                expanded = false
                            }
                        },
                        colors = MenuDefaults.itemColors(
                            textColor = newBlack
                        )
                    )
                }
            }
        }

        VerticalDivider(
            modifier = Modifier.width(1.dp),
            color = newGray
        )

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
                if (AppPreferences.get_User_WaNumber().isNotEmpty() && constants.Start_Up_ViewModel.WaNumber == AppPreferences.get_User_WaNumber()) {
                    Image(painterResource(R.drawable.phone_verified), "")
                }
            },
            modifier = Modifier
                .weight(3f)
                .padding(horizontal = 4.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
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

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .clip(RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
                .background(
                    if (phoneNumber.length != isLimit) Color(0xffF5F3F3) else if (AppPreferences.get_User_WaNumber() == constants.Start_Up_ViewModel.WaNumber) Color(
                        0xffF5F3F3
                    ) else newBlue
                )
                .padding(
                    start = if (phoneNumber.length == isLimit) 10.dp else 8.dp,
                    end = if (phoneNumber.length == isLimit) 10.dp else 8.dp
                )
                .noRippleClickable {
                    onClick()
                }
            , contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (AppPreferences.get_User_WaNumber().isEmpty()) "Verify" else "Change",
                fontSize = constants.textUnit(12),
                fontFamily = constants.fontFamily(2),
                color = if (phoneNumber.length != isLimit ) newBlack else if(AppPreferences.get_User_WaNumber() == constants.Start_Up_ViewModel.WaNumber)  newBlack else  Color.White,

                modifier = Modifier
            )
        }
    }
}

@Composable
fun LoginSwitchText(onLoginClick: () -> Unit , modifier: Modifier) {
    val normalText = activity.getString(R.string.sign_accountswitch) + " "
    val loginText = activity.getString(R.string.login)

    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = newBlack,
                fontSize = constants.textUnit(14),
                fontFamily = constants.fontFamily(0),

            )
        ) {
            append(normalText)
        }

        pushStringAnnotation(tag = "LOGIN", annotation = "login_click")
        withStyle(
            style = SpanStyle(
                color = newBlue,
                fontSize = constants.textUnit(14),
                fontFamily = constants.fontFamily(0),

            )
        ) {
            append(loginText)
        }
        pop()
    }

    ClickableText(
        text = annotatedString,
        modifier = modifier,
        onClick = { offset ->
            annotatedString
                .getStringAnnotations(tag = "LOGIN", start = offset, end = offset)
                .firstOrNull()?.let {
                    onLoginClick()
                }
        }
    )
}

@Composable
fun SignSwitchText(onSignClick: () -> Unit , modifier: Modifier) {
    val normalText = activity.getString(R.string.login_accountswitch) + " "
    val loginText = "Sign up"

    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = newBlack,
                fontSize = constants.textUnit(14),
                fontFamily = constants.fontFamily(0),

            )
        ) {
            append(normalText)
        }

        pushStringAnnotation(tag = "SIGN", annotation = "sign_click")
        withStyle(
            style = SpanStyle(
                color = newBlue,
                fontSize = constants.textUnit(14),
                fontFamily = constants.fontFamily(0),

            )
        ) {
            append(loginText)
        }
        pop()
    }

    ClickableText(
        text = annotatedString,
        modifier = modifier,
        onClick = { offset ->
            annotatedString
                .getStringAnnotations(tag = "SIGN", start = offset, end = offset)
                .firstOrNull()?.let {
                    onSignClick()
                }
        }
    )
}

@Composable
fun verifyOtp(
    resendOTP: () -> Unit,
    isTimerFinished: Boolean,
    modifier: Modifier = Modifier,
    isResend_Loading: MutableState<Boolean>
) {

    val normalText = "Didn’t receive? "
    val otpResend = "Resend"

    val annotatedString = buildAnnotatedString {
        append(normalText)

        val resendColor = newBlue.copy(alpha = if (isTimerFinished) 1f else 0.5f)
        val underline = if (isTimerFinished) TextDecoration.Underline else TextDecoration.None

        pushStringAnnotation(tag = "OTP", annotation = "Resend_click")
        withStyle(
            style = SpanStyle(
                color = resendColor,
                fontSize = constants.textUnit(14),
                fontWeight = FontWeight.Bold,
                textDecoration = underline
            )
        ) {
            append(otpResend)
        }
        pop()
    }

    Row (modifier){
        ClickableText(
            text = annotatedString,
            modifier = modifier,
            onClick = { offset ->
                if (isResend_Loading.value){

                }
                else {
                    if (isTimerFinished) {
                        annotatedString
                            .getStringAnnotations(tag = "OTP", start = offset, end = offset)
                            .firstOrNull()?.let {
                                resendOTP()
                            }
                    }
                }
            }
        )

        Spacer(modifier = Modifier.padding(4.dp))

        if (isResend_Loading.value) {
            CircularProgressIndicator(Modifier.size(20.dp), color = newBlue)
        }
    }
}

@Composable
fun OtpTextField(
    otp: String,
    onOtpChange: (String) -> Unit,
    isError: Boolean,
    modifier: Modifier = Modifier
) {
    val focusRequesters = List(4) { remember { FocusRequester() } }
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.wrapContentWidth()
    ) {
        (0 until 4).forEach { index ->
            val char = otp.getOrNull(index)?.toString() ?: ""

            OutlinedTextField(
                value = char,
                onValueChange = { value ->
                    if (value.length <= 1 && value.all { it.isDigit() }) {
                        val newOtp = (0 until 4).joinToString("") { i ->
                            if (i == index) value else otp.getOrNull(i)?.toString() ?: ""
                        }
                        onOtpChange(newOtp)

                        if (value.isNotEmpty() && index < 3) {
                            focusRequesters[index + 1].requestFocus()
                        }
                        if (value.isNotEmpty() && index == 3) {
                            keyboardController?.hide()
                        }
                    }
                },
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
                    .focusRequester(focusRequesters[index])
                    .focusProperties {
                        next = if (index < 3) focusRequesters[index + 1] else FocusRequester.Default
                        previous =
                            if (index > 0) focusRequesters[index - 1] else FocusRequester.Default
                    },
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,

                    fontWeight = FontWeight.Bold
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = if (index == 3) ImeAction.Done else ImeAction.Next
                ),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    disabledTextColor = Color.Black,
                    errorTextColor = Color.Black,

                    focusedIndicatorColor = if (isError) Color(0xFFFF5858) else Color.Red,
                    unfocusedIndicatorColor = if (isError) Color(0xFFFF5858) else Color.LightGray,
                    disabledIndicatorColor = Color.LightGray,
                    errorIndicatorColor = Color(0xFFFF5858),
                    focusedContainerColor = if (isError) Color.Red.copy(.6f) else Color.White,
                    unfocusedContainerColor = if (isError) Color.Red.copy(.6f) else Color.White,
                    disabledContainerColor = Color.White,
                    errorContainerColor = Color.Red.copy(.6f)
                )
            )
        }
    }
}

@Composable
fun OTP_TF(
    otp: String,
    onOtpChange: (String) -> Unit,
    isError: Boolean,
    modifier: Modifier = Modifier
)
{
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    BasicTextField(value = otp,
        onValueChange = {
            if (it.length <= 4) {
                onOtpChange(it)

            }
            else it.take(4) },
        modifier = modifier.focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusRequester.freeFocus()
                focusManager.clearFocus()
                keyboardController?.hide()
            }
        ),
        decorationBox = {
            Row(modifier = Modifier
                .fillMaxWidth( 1f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically)
            {
                repeat(4) {
                        index->
                    val char = when{
                        index >= otp.length->""
                        else -> otp[index].toString()
                    }
                    var isFocused = otp.length == index

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(60.dp)
                            .background(
                                Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .border(
                                width = if (isFocused) 2.dp else 1.dp,
                                if (isError) {
                                    Color.Red
                                } else {
                                    if (isFocused) newBlue else if (char.isNotEmpty()) newBlue else Color.LightGray
                                }, shape = RoundedCornerShape(8.dp)
                            )

                    )
                    {
                        Text(
                            text = char,
                            color = Color.Black,
                            textAlign = TextAlign.Center,

                        )
                    }
                }
            }
        },
        textStyle = TextStyle(color = Color.Black,
            fontWeight = FontWeight.Bold,))

}

@Composable
fun OTP_TF_6(
    otp: String,
    onOtpChange: (String) -> Unit,
    isError: State<Boolean>,
    modifier: Modifier = Modifier
)
{
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    BasicTextField(value = otp,
        onValueChange = {
            if (it.length <= 6) {
                onOtpChange(it)
            }
            else it.take(6) },
        modifier = modifier.focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusRequester.freeFocus()
                focusManager.clearFocus()
                keyboardController?.hide()
            }
        ),
        decorationBox = {
            Row(modifier = Modifier
                .fillMaxWidth( 1f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically)
            {
                repeat(6) {
                        index->
                    val char = when{
                        index >= otp.length->""
                        else -> otp[index].toString()
                    }
                    var isFocused = otp.length == index

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )

                    )
                    {
                        Column() {
                            Text(
                                text = char,
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )

                            HorizontalDivider(
                                modifier = Modifier.height(if (isFocused) 2.dp else 1.dp),
                                color = if (isError.value) {
                                    Color.Red
                                } else {
                                    if (isFocused) newBlue else if (char.isNotEmpty()) newBlue else Color.LightGray
                                }
                            )
                        }
                    }
                }
            }
        },
        textStyle = TextStyle(color = Color.Black,
            fontWeight = FontWeight.Bold,))

}

@Composable
fun OtpTextField1(
    otp: String,
    onOtpChange: (String) -> Unit,
    modifier: Modifier
) {
    val focusRequesters = List(4) { remember { FocusRequester() } }
    val keyboardController = LocalSoftwareKeyboardController.current

    val newBlue = Color(0xFF007BFF)
    val lightGray = Color(0xFFCCCCCC)

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.wrapContentWidth(),
    ) {
        (0 until 4).forEach { index ->
            val char = otp.getOrNull(index)?.toString() ?: ""
            val isFilled = char.isNotEmpty()

            OutlinedTextField(
                value = char,
                onValueChange = { value ->
                    if (value.length <= 1 && value.all { it.isDigit() }) {
                        val newOtp = (0 until 4).joinToString("") { i ->
                            if (i == index) value else otp.getOrNull(i)?.toString() ?: ""
                        }
                        onOtpChange(newOtp)

                        if (value.isNotEmpty() && index < 3) {
                            focusRequesters[index + 1].requestFocus()
                        }

                        if (value.isNotEmpty() && index == 3) {
                            keyboardController?.hide()
                        }
                    }
                },
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
                    .focusRequester(focusRequesters[index])
                    .focusProperties {
                        next = if (index < 3) focusRequesters[index + 1] else FocusRequester.Default
                        previous =
                            if (index > 0) focusRequesters[index - 1] else FocusRequester.Default
                    },
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,

                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = if (index == 3) ImeAction.Done else ImeAction.Next
                ),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    disabledTextColor = Color.Black,
                    focusedIndicatorColor = if (isFilled) newBlue else newGray,
                    unfocusedIndicatorColor = if (isFilled) newBlue else newGray,
                    disabledIndicatorColor = lightGray,
                    unfocusedContainerColor = newWhite,
                    focusedContainerColor = newWhite
                )
            )
        }
    }
}

class CountdownTimerState(
    val time: State<String>,
    val isFinished: State<Boolean>,
    val restart: () -> Unit
)

@Composable
fun rememberCountdownTimer(startSeconds: Int = 180): CountdownTimerState {
    var timeLeft by remember { mutableStateOf(startSeconds) }
    var triggerRestart by remember { mutableStateOf(0) }
    var finished by remember { mutableStateOf(false) }

    val restart: () -> Unit = {
        timeLeft = startSeconds
        finished = false
        triggerRestart++
    }

    LaunchedEffect(triggerRestart) {
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft--
        }
        finished = true
    }

    val formattedTime = remember(timeLeft) {
        String.format("%02d:%02d", timeLeft / 60, timeLeft % 60)
    }

    return CountdownTimerState(
        time = rememberUpdatedState(formattedTime),
        isFinished = rememberUpdatedState(finished),
        restart = restart
    )
}

fun compressImageToUnder1MB1(context: Context, uri: Uri): File? {
    try {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val originalBitmap = BitmapFactory.decodeStream(inputStream)
        inputStream.close()

        var quality = 100
        var compressedFile: File? = null
        val outputFile = File(context.cacheDir, "${System.currentTimeMillis()}.jpg")

        do {
            val outputStream = ByteArrayOutputStream()
            outputStream.use {
                originalBitmap.compress(Bitmap.CompressFormat.JPEG, quality, it)
            }

            val byteArray = outputStream.toByteArray()
            if (byteArray.size / 1024 / 1024 <= 1) {

                outputFile.outputStream().use { it.write(byteArray) }
                compressedFile = outputFile
                break
            }

            quality -= 5
        } while (quality > 10)

        return compressedFile
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}

fun compressImageToUnder1MB(context: Context, uri: Uri): File? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        var bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream.close()

        val outputFile = File(context.cacheDir, "${System.currentTimeMillis()}.jpg")

        var quality = 90
        var currentBitmap = bitmap

        while (true) {
            val byteStream = ByteArrayOutputStream()
            currentBitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteStream)
            val byteArray = byteStream.toByteArray()
            byteStream.close()

            if (byteArray.size <= 1 * 1024 * 1024) {
                FileOutputStream(outputFile).use { it.write(byteArray) }
                return outputFile
            }

            if (quality > 50) {
                quality -= 10
            } else {

                val newWidth = (currentBitmap.width * 0.7).toInt()
                val newHeight = (currentBitmap.height * 0.7).toInt()
                currentBitmap = Bitmap.createScaledBitmap(currentBitmap, newWidth, newHeight, true)
            }

            if (currentBitmap.width < 200 || currentBitmap.height < 200) {
                return null
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    } as File?
}

fun getVideoThumbnail(context: Context, uri: Uri): Bitmap? {
    val retriever = MediaMetadataRetriever()
    return try {
        retriever.setDataSource(context, uri)
        retriever.getFrameAtTime(1000000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    } finally {
        retriever.release()
    }
}

suspend fun getVideoThumbnailFP(
    context: Context,
    videoUrl: String,
    headers: Map<String, String>? = null
): Bitmap? = withContext(Dispatchers.IO) {

    try {
        val retriever = MediaMetadataRetriever()

        if (headers != null) {
            retriever.setDataSource(videoUrl, headers)
        } else {
            retriever.setDataSource(videoUrl, HashMap())
        }

        retriever.frameAtTime?.also {
            retriever.release()
            return@withContext it
        }

        retriever.release()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return@withContext try {
        Glide.with(context)
            .asBitmap()
            .load(videoUrl)
            .frame(1_000_000L)
            .submit()
            .get()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

private fun generateVideoThumbnail(
    context: Context,
    videoUrl: String
): Bitmap? {
    val retriever = MediaMetadataRetriever()
    return try {
        retriever.setDataSource(videoUrl, HashMap())
        retriever.getFrameAtTime(
            1_000_000,
            MediaMetadataRetriever.OPTION_CLOSEST_SYNC
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    } finally {
        retriever.release()
    }
}

private fun bitmapToTempUri(
    context: Context,
    bitmap: Bitmap
): Uri {
    val file = File.createTempFile("video_thumb_", ".jpg", context.cacheDir)
    FileOutputStream(file).use { out ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, out)
    }
    return file.toUri()
}

suspend fun generateAndUploadVideoThumbnail(
    context: Context,
    videoUrl: String
): String = withContext(Dispatchers.IO) {

    val bitmap = generateVideoThumbnail(context, videoUrl)
        ?: return@withContext ""

    val thumbnailUri = bitmapToTempUri(context, bitmap)

    val s3Uploader = S3Uploader(
        bucket = constants.BUCKET_NAME,
        cloudFront = constants.CLOUD_FRONT_URL,
        accessId = constants.ACCESS_ID,
        secretKey = constants.SECRET_KEY
    )

    val uploadResult = s3Uploader.uploadSingle(
        context = context,
        userId = AppPreferences.getUserId().toString(),
        uri = thumbnailUri,
        thumbail = true,
        profile = "",
        onProgress = {}
    )

    uploadResult.url
}

fun getVideoThumbnailString(context: Context, videoUrl: String): Bitmap? {
    val retriever = MediaMetadataRetriever()

    return try {
        val uri = Uri.parse(videoUrl)
        retriever.setDataSource(context, uri)
        retriever.getFrameAtTime(1_000_000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    } finally {
        retriever.release()
    }
}

fun Bitmap.toBase64String(): String {
    val outputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
    val byteArray = outputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

fun getVideoThumbnailBase64(context: Context, videoUrl: String): String? {
    val retriever = MediaMetadataRetriever()
    return try {
        val uri = Uri.parse(videoUrl)
        retriever.setDataSource(context, uri)

        val bitmap = retriever.getFrameAtTime(
            1_000_000,
            MediaMetadataRetriever.OPTION_CLOSEST_SYNC
        )

        bitmap?.toBase64String()?.let { "data:image/jpeg;base64,$it" }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    } finally {
        retriever.release()
    }
}

fun getFileSizeInMB(context: Context, uri: Uri): Double {
    val fileDescriptor = context.contentResolver.openFileDescriptor(uri, "r") ?: return 0.0
    val size = fileDescriptor.statSize
    fileDescriptor.close()
    return size.toDouble() / (1024 * 1024)
}

fun getVideoThumbnailold(context: Context, uri: Uri): Bitmap? {
    val retriever = MediaMetadataRetriever()
    return try {
        retriever.setDataSource(context, uri)
        retriever.getFrameAtTime(1000000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    } finally {
        retriever.release()
    }
}

@Composable
fun Backer(modifier: Modifier, onBackClick: () -> Unit){

        Icon(painter = painterResource(R.drawable.left_arrow), "",
            modifier = modifier
                .size(24.dp)
                .noRippleClickable {
                    onBackClick()
                })

}

@Composable
fun Static_Bottom(modifier: Modifier , content: @Composable () -> Unit){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(newWhite)
        , contentAlignment = Alignment.Center
    ) {

        content()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(newGray)
                .align(Alignment.TopCenter)
        )
    }
}

@Composable
fun LottiAnimation(isWhichAnimation : Int){

    val loadAnimation =  when (isWhichAnimation){
        0 -> R.raw.nodatafound
        1 -> R.raw.nointernet
        2 -> R.raw.rentalloading
        3 -> R.raw.nointeretconnection
        4 -> R.raw.buyhome
        5 -> R.raw.searchland
        6 -> R.raw.buildings
        else -> R.raw.nodatafound
    }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(loadAnimation))

    var visible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        while (true) {
            visible = !visible
            delay(800)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(.4f)
        , contentAlignment = Alignment.Center
    )
    {
            if (isWhichAnimation == 2) {
                CircularProgressIndicator(
                    color = newBlue,
                    trackColor = newGray,
                    strokeWidth = 5.dp,
                    modifier = Modifier.size(56.dp)
                )
            } else {
                LottieAnimation(
                    composition = composition,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                    , reverseOnRepeat = true,
                    iterations = LottieConstants.IterateForever
                )
            }

            if (isWhichAnimation != 2) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(vertical = 24.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                    , contentAlignment = Alignment.Center
                )
                {
                    AnimatedVisibility(
                        visible = visible,
                        enter = fadeIn(animationSpec = tween(durationMillis = 500)),
                        exit = fadeOut(animationSpec = tween(durationMillis = 500))
                    ) {
                        var loadText = when (isWhichAnimation) {
                            0 -> "No Data Found"
                            1 -> "It Seems your are offline !!.Refresh again"
                            2 -> "Loading..."
                            3 -> "Internet Unavailable"
                            else -> "UnIdentified Error"
                        }

                        Text(
                            text = loadText,
                            color = newBlack,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        )
                    }
            }

        }
    }
}

fun LazyListScope.customGridItems(
    count: Int,
    nColumns: Int,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    itemContent: @Composable BoxScope.(Int) -> Unit,
) {

    gridItems(
        data = List(count) {it },
        nColumns = nColumns,
        horizontalArrangement = horizontalArrangement,
        itemContent = itemContent,
    )
}

fun <T> LazyListScope.gridItems(
    data: List<T>,
    nColumns: Int,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    key: ((item: T) -> Any)? = null,
    itemContent: @Composable BoxScope.(T) -> Unit,
) {
    val rows = if (data.isEmpty()) 0 else (data.size + nColumns - 1) / nColumns

    items(
        count = rows,
        key = if (key != null) { rowIndex ->
            val itemIndex = rowIndex * nColumns
            if (itemIndex < data.size) key(data[itemIndex]) else rowIndex
        } else null
    ) { rowIndex ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = horizontalArrangement
        ) {
            for (columnIndex in 0 until nColumns) {
                val itemIndex = rowIndex * nColumns + columnIndex
                if (itemIndex < data.size) {
                    Box(
                        modifier = Modifier
                            .weight(1f, fill = true),
                        propagateMinConstraints = true
                    ) {
                        itemContent(data[itemIndex])
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f, fill = true))
                }
            }
        }
    }
}

@Composable
fun LocationButton(
    context: Context,
    modifier: Modifier,
    enabled: Boolean,
    navController: NavHostController
) {

   var no_Click_SaveLocation = remember { mutableStateOf(false) }
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (granted) {
            getCurrentLocation(
                fusedLocationClient,
                context,
                navController,
                0,
                no_Click_SaveLocation
            )
        } else {
            constants.Start_Up_ViewModel.setLocationDenied(false)
            Toast.makeText(context, "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    val is_Loadind = constants.Common_H_ViewModel.status.collectAsState()

    Box(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .height(56.dp)
            .clickable() {

                if (enabled) {
                    if (isConnected.value) {
                        Put_User_Interests_API_Call() { result ->
                            when (result) {
                                0 -> {
                                    AppPreferences.save_Interest_Completed(1)
                                    val fineLocationGranted = ActivityCompat.checkSelfPermission(
                                        context, Manifest.permission.ACCESS_FINE_LOCATION
                                    ) == PackageManager.PERMISSION_GRANTED

                                    val coarseLocationGranted = ActivityCompat.checkSelfPermission(
                                        context, Manifest.permission.ACCESS_COARSE_LOCATION
                                    ) == PackageManager.PERMISSION_GRANTED

                                    if (fineLocationGranted || coarseLocationGranted) {
                                        getCurrentLocation(
                                            fusedLocationClient,
                                            context,
                                            navController,
                                            0,
                                            no_Click_SaveLocation
                                        )
                                    } else {
                                        locationPermissionLauncher.launch(
                                            arrayOf(
                                                Manifest.permission.ACCESS_FINE_LOCATION,
                                                Manifest.permission.ACCESS_COARSE_LOCATION
                                            )
                                        )
                                    }
                                }

                                1 -> {
                                    toast("Something went wrong while saving preferences")
                                }
                            }
                        }
                    } else {
                        GlobalSnackbar.show("It Seems your are offline !!.Refresh again")
                    }
                } else {
                    GlobalSnackbar.show("Select at least 2 Categories")
                }
            }
            , contentAlignment = Alignment.Center
    ) {

        if (is_Loadind.value){
            CircularProgressIndicator(color = Color.White)
        }
        else {
            Text(
                "Next",
                color = newWhite,
                fontSize = constants.textUnit(14)
            )
        }
    }
}

@RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
fun getCurrentLocationold(
    fusedLocationClient: FusedLocationProviderClient,
    context: Context,
    navController: NavHostController
) {
    val geocoder = Geocoder(context, Locale.getDefault())

    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        if (location != null) {
            val latitude = location.latitude
            val longitude = location.longitude

            constants.Start_Up_ViewModel.set_Latitude(latitude)
            constants.Start_Up_ViewModel.set_Longitude(longitude)

            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]

                val city = address.locality ?: "Unknown"
                val state = address.adminArea ?: "Unknown"
                val pincode = address.postalCode ?: "Unknown"
                val country = address.countryName ?: "unknown"

                Log.d("LocationInfo", "City: $city, State: $state, Pincode: $pincode")

                Toast.makeText(context, "$city, $state, $pincode", Toast.LENGTH_LONG).show()

                constants.Start_Up_ViewModel.updateSelectedState(state)
                constants.Start_Up_ViewModel.updateSelectedCity(city)

                constants.Start_Up_ViewModel.set_Country(country )
                constants.Start_Up_ViewModel.set_State(state)
                constants.Start_Up_ViewModel.set_City(city)
                constants.Start_Up_ViewModel.set_Pincode(pincode)

                if (isConnected.value) {
                    if (constants.Start_Up_ViewModel.country.value.isNotEmpty()
                        && constants.Start_Up_ViewModel.state.value.isNotEmpty()
                        && constants.Start_Up_ViewModel.city.value.isNotEmpty()
                        && constants.Start_Up_ViewModel.pincode.value.isNotEmpty()
                        && constants.Start_Up_ViewModel.latitude.value.isNotEmpty()
                        && constants.Start_Up_ViewModel.longitude.value.isNotEmpty()
                    ) {
                        put_User_Location_API_Call(
                            resultCallback = { result ->
                                when (result) {
                                    0 -> {

                                        AppPreferences.save_Location_Received(1)
                                        toast("LOCATION STORED SUCCESSFULLY")
                                        navController.navigate(UserCredentialsScreenFlow.Common_Screen.route)
                                    }

                                    1 -> {

                                        toast("Something went wrong while storing location")
                                    }

                                    2 -> {

                                        constants.Common_H_ViewModel.changeStatus(true)
                                    }
                                }
                            }
                        )
                    }
                }

            }
        }
        else {
            Toast.makeText(context, "Unable to get location", Toast.LENGTH_SHORT).show()

            AppPreferences.save_Location_Received(0)
            constants.Start_Up_ViewModel.setLocationDenied(false)

        }
    }.addOnFailureListener {
        Toast.makeText(context, "Failed to get location", Toast.LENGTH_SHORT).show()
    }
}

@SuppressLint("MissingPermission")
fun getCurrentLocation(
    fusedLocationClient: FusedLocationProviderClient,
    context: Context,
    navController: NavHostController,
    i: Int,
    no_Click_SaveLocation: MutableState<Boolean>
) {
    val geocoder = Geocoder(context, Locale.getDefault())

    fusedLocationClient.getCurrentLocation(
        Priority.PRIORITY_HIGH_ACCURACY,
        null
    ).addOnSuccessListener { location ->
        if (location != null) {
            val latitude = location.latitude
            val longitude = location.longitude

            constants.Start_Up_ViewModel.set_Latitude(latitude)
            constants.Start_Up_ViewModel.set_Longitude(longitude)
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)

            if (no_Click_SaveLocation.value){
               if (!addresses.isNullOrEmpty()) {
                   val address = addresses[0]
                   val city = address.locality ?: "Unknown"
                   val state = address.adminArea ?: "Unknown"
                   val pincode = address.postalCode ?: "Unknown"
                   val country = address.countryName ?: "Unknown"

                   Toast.makeText(context, "$city, $state, $pincode", Toast.LENGTH_LONG).show()

                   constants.Start_Up_ViewModel.updateSelectedState(state)
                   constants.Start_Up_ViewModel.updateSelectedCity(city)
                   constants.Start_Up_ViewModel.set_Country(country)
                   constants.Start_Up_ViewModel.set_State(state)
                   constants.Start_Up_ViewModel.set_City(city)
                   constants.Start_Up_ViewModel.set_Pincode(pincode)

                       if (isConnected.value &&
                           listOf(
                               constants.Start_Up_ViewModel.country.value,
                               constants.Start_Up_ViewModel.state.value,
                               constants.Start_Up_ViewModel.city.value,
                               constants.Start_Up_ViewModel.pincode.value,
                               constants.Start_Up_ViewModel.latitude.value,
                               constants.Start_Up_ViewModel.longitude.value
                           ).all { it.isNotEmpty() }
                       ) {
                           AppPreferences.save_User_Lcation(constants.Start_Up_ViewModel.city.value)
                           put_User_Location_API_Call { result ->
                               when (result) {
                                   0 -> {
                                       AppPreferences.save_Location_Received(1)
                                       toast("LOCATION STORED SUCCESSFULLY")

                                       no_Click_SaveLocation.value = false
                                       navController.navigate(UserCredentialsScreenFlow.Common_Screen.route)
                                   }

                                   1 -> toast("Something went wrong while storing location")
                                   2 -> constants.Common_H_ViewModel.changeStatus(true)
                               }
                           }
                       }

               }
           }
            else {

               AppPreferences.save_Location_Received(0)
               constants.Start_Up_ViewModel.setLocationDenied(false)
           }

            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]
                val city = address.locality ?: "Unknown"
                val state = address.adminArea ?: "Unknown"
                val pincode = address.postalCode ?: "Unknown"
                val country = address.countryName ?: "Unknown"

                Toast.makeText(context, "$city, $state, $pincode", Toast.LENGTH_LONG).show()

                constants.Start_Up_ViewModel.updateSelectedState(state)
                constants.Start_Up_ViewModel.updateSelectedCity(city)
                constants.Start_Up_ViewModel.set_Country(country)
                constants.Start_Up_ViewModel.set_State(state)
                constants.Start_Up_ViewModel.set_City(city)
                constants.Start_Up_ViewModel.set_Pincode(pincode)

                if (!constants.Start_Up_ViewModel.getCurrrentLocation_View.value) {
                    if (isConnected.value &&
                        listOf(
                            constants.Start_Up_ViewModel.country.value,
                            constants.Start_Up_ViewModel.state.value,
                            constants.Start_Up_ViewModel.city.value,
                            constants.Start_Up_ViewModel.pincode.value,
                            constants.Start_Up_ViewModel.latitude.value,
                            constants.Start_Up_ViewModel.longitude.value
                        ).all { it.isNotEmpty() }
                    ) {
                        AppPreferences.save_User_Lcation(constants.Start_Up_ViewModel.city.value)
                        put_User_Location_API_Call { result ->
                            when (result) {
                                0 -> {
                                    AppPreferences.save_Location_Received(1)
                                    toast("LOCATION STORED SUCCESSFULLY")

                                    navController.navigate(UserCredentialsScreenFlow.Common_Screen.route)
                                }

                                1 -> toast("Something went wrong while storing location")
                                2 -> constants.Common_H_ViewModel.changeStatus(true)
                            }
                        }
                    }
                }
            }
            } else {
                Toast.makeText(context, "Unable to get location. Please ensure GPS is on.", Toast.LENGTH_SHORT).show()
                AppPreferences.save_Location_Received(0)
                constants.Start_Up_ViewModel.setLocationDenied(false)
            }
    }.addOnFailureListener {
        Toast.makeText(context, "Failed to get location", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun CheckLocationPermissionOnResume(
    context: Context,
    location_Settings: MutableState<Boolean>
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {

                if (isLocationPermissionGranted(context)) {
                    location_Settings.value = false
                }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

fun isLocationPermissionGranted(context: Context): Boolean {
    val fineLocation = ActivityCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    val coarseLocation = ActivityCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    return fineLocation || coarseLocation
}

@Composable
fun GetCurrentLocationButton(
    context: Context,
    navController: NavHostController,
    no_Click_SaveLocation: MutableState<Boolean>
) {

    val network = rememberNetworkStatus()

    val view = constants.Start_Up_ViewModel.getCurrrentLocation_View.collectAsState()

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (granted) {
            checkGpsAndProceed(context, fusedLocationClient, navController, onGpsDisabled = {
                if (!view.value) {
                    constants.Start_Up_ViewModel.setShowGpsDialog(true)
                }
                else {
                    toast("Check your GPS is turned on !!")
                }
            },no_Click_SaveLocation
            )
        } else {
            constants.Start_Up_ViewModel.setShowLocationSettings(true)
            Toast.makeText(context, "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    Row(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .wrapContentSize()
            .noRippleClickable {
                ClickHelper.getInstance().clickOnce {
                    if (network.value == NetworkStatus.Online) {

                        val fineLocationGranted = ActivityCompat.checkSelfPermission(
                            context, Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED

                        val coarseLocationGranted = ActivityCompat.checkSelfPermission(
                            context, Manifest.permission.ACCESS_COARSE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED

                        if (fineLocationGranted || coarseLocationGranted) {
                            checkGpsAndProceed(
                                context,
                                fusedLocationClient,
                                navController,
                                onGpsDisabled = {
                                    if (!view.value) {
                                        constants.Start_Up_ViewModel.setShowGpsDialog(true)
                                    } else {
                                        toast("Check your GPS is turned on !!")
                                    }
                                },
                                no_Click_SaveLocation
                            )
                        } else {
                            locationPermissionLauncher.launch(
                                arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                )
                            )
                        }
                    } else {
                        toast(activity.getString(R.string.no_Internet))
                    }
                }
            }
            .clip(RoundedCornerShape(4.dp))
            .background(newWhite)
            .border(1.dp, newBlue, RoundedCornerShape(4.dp)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SubcomposeAsyncImage(
            model = R.drawable.location,
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .size(18.dp)
        )

        Text(
            "Use Current Location",
            color = newBlue,
            fontSize = constants.textUnit(14),
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
        )
    }
}

private fun checkGpsAndProceed(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
    navController: NavHostController,
    onGpsDisabled: () -> Unit,
    no_Click_SaveLocation: MutableState<Boolean>
) {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    if (isGpsEnabled || isNetworkEnabled) {

        getCurrentLocation(fusedLocationClient, context, navController, 0 ,no_Click_SaveLocation)
    } else {

        onGpsDisabled()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDaysInMonth(yearMonth: YearMonth): List<LocalDate> {
    val firstDay = yearMonth.atDay(1)
    val lastDay = yearMonth.atEndOfMonth()

    val days = mutableListOf<LocalDate>()

    val startDayOfWeek = firstDay.dayOfWeek.value % 7
    val prevMonthDays = (1..startDayOfWeek).map {
        firstDay.minusDays(it.toLong())
    }.reversed()
    days.addAll(prevMonthDays)

    days.addAll((1..yearMonth.lengthOfMonth()).map { day ->
        yearMonth.atDay(day)
    })

    while (days.size % 7 != 0) {
        days.add(days.last().plusDays(1))
    }

    return days
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RangeCalendar(
    modifier: Modifier = Modifier,
    month: YearMonth,
    selectedStartDate: LocalDate?,
    selectedEndDate: LocalDate?,
    onDayClick: (LocalDate) -> Unit
) {
    val days = getDaysInMonth(month)

    Column(modifier.padding(all = 16.dp)) {

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach {
                Text(
                    it,
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        days.chunked(7).forEach { week ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                week.forEach { date ->
                    val isInMonth = date.month == month.month
                    val isSelected = selectedStartDate != null && selectedEndDate != null &&
                            (date >= selectedStartDate && date <= selectedEndDate)
                    val isStart = date == selectedStartDate
                    val isEnd = date == selectedEndDate

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(2.dp)
                            .background(
                                when {
                                    isStart || isEnd -> newBlue
                                    isSelected -> newBlue.copy(alpha = 0.2f)
                                    else -> Color.Transparent
                                },
                                shape = if (isStart || isEnd) RectangleShape else RectangleShape
                            )
                            .noRippleClickable { onDayClick(date) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${date.dayOfMonth}",
                            color = when {
                                isStart || isEnd -> newWhite
                                isInMonth -> Color.Black
                                else -> newGray
                            },
                            fontWeight = if (isStart || isEnd) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarExampleold(
    modifier: Modifier = Modifier,
    isRangeSelectionEnabled: Boolean = true,
    onDateSelected: (LocalDate?) -> Unit = {},
    initialStartDate: LocalDate? = null,
    initialEndDate: LocalDate? = null,
    onDateRangeSelected: (LocalDate?, LocalDate?) -> Unit = { _, _ -> }
) {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var startDate by remember { mutableStateOf(initialStartDate) }
    var endDate by remember { mutableStateOf(initialEndDate) }

    var hasInitialized by remember { mutableStateOf(false) }

    LaunchedEffect(startDate, endDate) {
        if (!hasInitialized) {
            hasInitialized = true
        } else {
            if (isRangeSelectionEnabled) {
                onDateRangeSelected(startDate, endDate)
            } else {
                onDateSelected(startDate)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .wrapContentHeight()
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, newGray, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.TopCenter
    ) {
        Column {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.left_arrow),
                    contentDescription = "Previous Month",
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                        .noRippleClickable { currentMonth = currentMonth.minusMonths(1) },
                    tint = newBlue
                )

                Text(
                    text = "${currentMonth.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${currentMonth.year}",
                    color = newBlue
                )

                Icon(
                    painter = painterResource(R.drawable.right_arrow),
                    contentDescription = "Next Month",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp)
                        .noRippleClickable { currentMonth = currentMonth.plusMonths(1) },
                    tint = newBlue
                )
            }

            HorizontalDivider(
                color = newGray,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            RangeCalendar(
                modifier = Modifier.fillMaxWidth(),
                month = currentMonth,
                selectedStartDate = startDate,
                selectedEndDate = endDate,
                onDayClick = { date ->

                    if (date.isAfter(LocalDate.now())) {
                        toast("you can't select future dates")
                        return@RangeCalendar
                    }

                    when {
                        startDate == null -> {

                            startDate = date
                            endDate = null

                            onDateSelected(date)
                        }

                        endDate == null -> {
                            if (date == startDate) {

                                onDateSelected(startDate)
                            } else {

                                if (date.isBefore(startDate)) {
                                    endDate = startDate
                                    startDate = date
                                } else {
                                    endDate = date
                                }

                                onDateRangeSelected(startDate, endDate)
                            }
                        }

                        else -> {

                            startDate = date
                            endDate = null

                            onDateSelected(date)
                        }
                    }
                }
            )

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarExample(
    modifier: Modifier = Modifier,
    isRangeSelectionEnabled: Boolean = true,
    onDateSelected: (LocalDate?) -> Unit = {},
    initialStartDate: LocalDate? = null,
    initialEndDate: LocalDate? = null,
    onDateRangeSelected: (LocalDate?, LocalDate?) -> Unit = { _, _ -> }
) {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var startDate by remember { mutableStateOf(initialStartDate) }
    var endDate by remember { mutableStateOf(initialEndDate) }

    var hasInitialized by remember { mutableStateOf(false) }

    val enquiryVm = constants.Enquiry_ViewModel

    LaunchedEffect(startDate, endDate) {
        if (!hasInitialized) {
            hasInitialized = true
        } else {
            if (isRangeSelectionEnabled) {
                onDateRangeSelected(startDate, endDate)
            } else {
                onDateSelected(startDate)
            }
        }
    }

    fun LocalDate.toTimestamp(): String {
        return this.atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
            .toString()
    }

    Column() {

        Row(
            modifier = Modifier.fillMaxWidth()
                , horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(painter = painterResource(R.drawable.left_arrow) ,"")
            CommonText(
                "Custom Date"
                ,newBlack
            ,20
            ,1
            )
        }

        constants.spacer(4)

        if (constants.Enquiry_ViewModel.Selected_Dates_List.isNotEmpty()) {
        Row( modifier = Modifier.fillMaxWidth()
            , horizontalArrangement = Arrangement.SpaceBetween)
        {
            CommonText(
                "Selected Date"
                ,Color(0xff666666)
                ,12
                ,1
            )

                Text(
                    "${formatToShortMonthDay(constants.Enquiry_ViewModel.Selected_Dates_List.firstOrNull()?.start_Date.toString())} ${ if (constants.Enquiry_ViewModel.Selected_Dates_List.firstOrNull()?.end_Date != null ) "-" else "" } ${formatToShortMonthDay(constants.Enquiry_ViewModel.Selected_Dates_List.firstOrNull()?.end_Date.toString())}",
                    color = Color(0xff666666),
                    fontSize = constants.textUnit(12),
                    fontFamily = constants.fontFamily(1),
                )
            }

        }

        constants.spacer(4)

        Box(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, newGray, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.TopCenter
        )
        {
            Column {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.left_arrow),
                        contentDescription = "Previous Month",
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp)
                            .noRippleClickable { currentMonth = currentMonth.minusMonths(1) },
                        tint = newBlue
                    )

                    Text(
                        text = "${
                            currentMonth.month.name.lowercase().replaceFirstChar { it.uppercase() }
                        } ${currentMonth.year}",
                        color = newBlue
                    )

                    Icon(
                        painter = painterResource(R.drawable.right_arrow),
                        contentDescription = "Next Month",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 16.dp)
                            .noRippleClickable { currentMonth = currentMonth.plusMonths(1) },
                        tint = newBlue
                    )
                }

                HorizontalDivider(
                    color = newGray,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                RangeCalendar(
                    modifier = Modifier.fillMaxWidth(),
                    month = currentMonth,
                    selectedStartDate = startDate,
                    selectedEndDate = endDate,
                    onDayClick = { date ->

                        if (date.isAfter(LocalDate.now())) {
                            toast("You can't select future dates")
                            return@RangeCalendar
                        }

                        when {
                            startDate == null -> {

                                startDate = date
                                endDate = null

                                enquiryVm.Selected_Dates_List.clear()
                                enquiryVm.Selected_Dates_List.add(
                                    Selected_Dates_Calender(
                                        start_Date = date,
                                        end_Date = date,
                                        timestamp_Start = date.toTimestamp(),
                                        timeStamp_End = date.toTimestamp()
                                    )
                                )

                                onDateSelected(date)
                            }

                            endDate == null -> {
                                if (date == startDate) {

                                    onDateSelected(startDate)
                                } else {

                                    if (date.isBefore(startDate)) {
                                        endDate = startDate
                                        startDate = date
                                    } else {
                                        endDate = date
                                    }

                                    enquiryVm.Selected_Dates_List.clear()
                                    enquiryVm.Selected_Dates_List.add(
                                        Selected_Dates_Calender(
                                            start_Date = startDate,
                                            end_Date = endDate,
                                            timestamp_Start = startDate!!.toTimestamp(),
                                            timeStamp_End = endDate!!.toTimestamp()
                                        )
                                    )

                                    onDateRangeSelected(startDate, endDate)
                                }
                            }

                            else -> {

                                startDate = date
                                endDate = null

                                enquiryVm.Selected_Dates_List.clear()
                                enquiryVm.Selected_Dates_List.add(
                                    Selected_Dates_Calender(
                                        start_Date = date,
                                        end_Date = date,
                                        timestamp_Start = date.toTimestamp(),
                                        timeStamp_End = date.toTimestamp()
                                    )
                                )

                                onDateSelected(date)
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun Common_DropDown2Options(
    expanded : MutableState<Boolean>
    , mainIcon : Int , content : List<Common_DropDown2Options_DC>
    , onClick1 : () -> Unit
    , onClick2 : () -> Unit
    ,modifier: Modifier
){

    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
            .background(Color.Transparent)
    )
    {

            AsyncImage(
                model = mainIcon,
                contentDescription = "",
                modifier = modifier

                    .noRippleClickable{ expanded.value = true },

            )

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier.background(newWhite)
        ) {
            for (i in 0 until content.size){
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            SubcomposeAsyncImage(
                                model = content[i].icon,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(16.dp)
                                , colorFilter = ColorFilter.tint(newBlack)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                content[i].title
                                ,color = newBlack,
                                fontSize = constants.textUnit(14),
                                fontFamily = constants.fontFamily(2),)
                        }
                    },
                    onClick = {
                        ClickHelper.getInstance().clickOnce {
                            expanded.value = false
                            if (i == 0) {
                                onClick1()
                            } else {
                                onClick2()
                            }
                        }

                    }
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullScreenReels(
    reels_Show: MutableState<Boolean>,
    content: PostUser,
    navController: NavHostController
) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    if (reels_Show.value) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {

            }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Enquiry_Message_Structure(
    content: List<Get_My_Leads_Data?>,
    index: Int,
    reels_Show: MutableState<Boolean>,
    navController: NavHostController,
    onDeleteClick: (Get_My_Leads_Data?) -> Unit,
    from_where: Int,
    keyboardController: SoftwareKeyboardController?,
    focusManager1: FocusManager
) {

    var viewMore by remember { mutableStateOf(false) }

    val isWhich = content[index]?.isWhich

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(16.dp))
            .background(newWhite)
            .border(1.dp, newGray, RoundedCornerShape(16.dp))
    )
    {

        ListItem(
            headlineContent = {
                if (from_where == 0) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                        , verticalAlignment = Alignment.CenterVertically

                    ) {
                        Text(
                            if (isWhich == 3) content[index]?.post_user?.username?:"username" else  content[index]?.enquiry_details?.enquiry_by_username ?: "username",
                            fontSize = constants.textUnit(14),
                            fontFamily = constants.fontFamily(0),
                            color = Color.Black
                        )

                        constants.spacer(4)

                        if (isWithinOneHour(content[index]?.enquiry_details?.created_at ?:"")) {
                            Image(painter = painterResource(R.drawable.enquiresnew), "")
                        }
                    }
                }
                else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                        , verticalAlignment = Alignment.CenterVertically

                    ) {
                        Text((content[index]?.post_user?.name?.ifEmpty { content[index]?.post_user?.username }
                            ?: "").ifEmpty { content[index]?.post_user?.name ?: "" },
                            fontSize = constants.textUnit(14),
                            fontFamily = constants.fontFamily(0),
                            color = Color.Black)

                    }
                }

            },
            leadingContent = {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(newBlue)
                ) {

                    SubcomposeAsyncImage(
                        model =  if (from_where == 0) content[index]?.enquiry_details?.enquiry_by_profile_image ?: "" else content[index]?.post_user?.profile_image ?: "",
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
                                    .background(newLightBlue)

                                , contentAlignment = Alignment.Center
                            ){
                                Text(
                                    text = content[index]?.enquiry_details?.enquiry_by_username.takeIf { it?.isNotEmpty() == true }?.take(1)?.uppercase() ?: "",
                                    fontSize = constants.textUnit(14),
                                    fontFamily = constants.fontFamily(1),
                                    color = Color.Black
                                )

                            }
                        } else {
                            SubcomposeAsyncImageContent()
                        }
                    }
                }
            },
            supportingContent = {

                Text( getTimeAgo(content[index]?.enquiry_details?.created_at ?:""),
                    fontSize = constants.textUnit(14),
                    fontFamily = constants.fontFamily(1),
                    color = newGray
                )

            },
            trailingContent = {

                if (isWhich == 3) {
                    SubcomposeAsyncImage(
                        model = R.drawable.deleteselfenquiry,
                        "",
                        modifier = Modifier
                            .noRippleClickable {
                                constants.Common_H_ViewModel.toggleshowBABars(false)
                                constants.Enquiry_ViewModel.selectedEnquiry = content[index]

                                onDeleteClick(content[index])
                                constants.open_Popup =
                                    Custom_PopUpState(type = "self_enquiry_delete", true)
                            }
                            .size(32.dp)
                    )
                }
                else {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .background(if (isWhich == 1) newLightBlue else Color(0xffFFEAD4)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            if((content[index]?.isWhich ?: 1) == 1) "Enquired" else "Shown Interest",
                            color = if (isWhich == 1) newBlue else Color(0xffC57217),
                            fontSize = constants.textUnit(12),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            },
            colors = ListItemColors(
                containerColor = newWhite,
                headlineColor = Color.Black,
                leadingIconColor = Color.DarkGray,
                overlineColor = Color.Gray,
                supportingTextColor = Color.Gray,
                trailingIconColor = Color.LightGray,
                disabledHeadlineColor = Color.Gray.copy(alpha = 0.5f),
                disabledLeadingIconColor = Color.Gray.copy(alpha = 0.5f),
                disabledTrailingIconColor = Color.Gray.copy(alpha = 0.5f)
            )
            , modifier = Modifier
                .noRippleClickable{

                    constants.API_Vm.isLoading_Profile_Posts = true
                    if (content[index]?.is_deleted == 1){
                        GlobalSnackbar.show("Property Not Available ")
                    }
                    else {
                        if (from_where == 0) {

                            constants.Profile_ViewModel.clear_All_BF_Handler()
                            constants.Profile_ViewModel.clearPosts()

                            constants.Profile_ViewModel.add_Selected_User_Name(
                                content[index]?.enquiry_details?.enquiry_by_username
                                    ?: "Unknown"
                            )

                            constants.Profile_ViewModel.add_BF_Handler(
                                Profile_Handle_Back(
                                    current_UsedId = AppPreferences.getUserId(),
                                    other_UserId = content[index]?.enquiry_details?.enquiry_by_user_id
                                        ?: 0,
                                    ff_User_Name = content[index]?.enquiry_details?.enquiry_by_username
                                        ?: "",
                                    ff_Fw_Count = 999,
                                    ff_Fg_Count = 999,
                                )
                            )

                            constants.Profile_ViewModel.addProfile(
                                content[index]?.enquiry_details?.enquiry_by_user_id ?: 0
                            )
                            constants.Profile_ViewModel.add_Selected_Profile_Id(
                                id = content[index]?.enquiry_details?.enquiry_by_user_id ?: 0
                            )

                            navController.navigate(
                                EnquiriesFlow.Other_Profile_Structure.route
                            )
                        }
                        else {

                            constants.Profile_ViewModel.clear_All_BF_Handler()

                            constants.Profile_ViewModel.clearPosts()
                            constants.Profile_ViewModel.add_Selected_User_Name(
                                content[index]?.post_user?.username ?: "Username"
                            )

                            constants.Profile_ViewModel.add_BF_Handler(
                                Profile_Handle_Back(
                                    current_UsedId = AppPreferences.getUserId(),
                                    other_UserId = content[index]?.post_user?.user_id ?: 0,
                                    ff_User_Name = content[index]?.post_user?.username ?: "",
                                    ff_Fw_Count = 999,
                                    ff_Fg_Count = 999,
                                )
                            )

                            constants.Profile_ViewModel.addProfile(
                                content[index]?.post_user?.user_id ?: 0
                            )
                            constants.Profile_ViewModel.add_Selected_Profile_Id(
                                id = content[index]?.post_user?.user_id ?: 0
                            )

                            navController.navigate(
                                EnquiriesFlow.Other_Profile_Structure.route
                            )
                        }
                    }
                }
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            if (isWhich == 3) {
                Text("Enquired On",
                    fontSize = constants.textUnit(14),
                    fontFamily = constants.fontFamily(0),
                    color = Color.Black)
                Text(getTimeAgo(content[index]?.enquiry_details?.created_at ?: ""), color = newGray, fontSize = constants.textUnit(12))
            }
            else {
                Text("Lead details",fontSize = constants.textUnit(14),
                    fontFamily = constants.fontFamily(1),
                    color = Color.Black)
                Row {
                    SubcomposeAsyncImage(
                        model = R.drawable.enquirycallbuttonicon,
                        contentDescription = "",

                        modifier = Modifier
                            .size(28.dp)
                            .noRippleClickable {
                                openDialer(
                                    context,
                                    content[index]?.enquiry_details?.enquiry_by_user_phone ?: ""
                                )
                            }
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    SubcomposeAsyncImage(
                        model = R.drawable.waenquiry,
                        contentDescription = "",
                        modifier = Modifier
                            .size(28.dp)
                            .noRippleClickable {
                                if (ClickGuard.canClick()) {
                                    openWhatsAppChat(
                                        context,
                                        content[index]?.enquiry_details?.enquiry_by_user_phone
                                            ?: "",
                                        "Hi! I'm contacting you from ${
                                            activity.getString(R.string.app_name)
                                        }"
                                    )
                                }
                            }
                    )
                }
            }
        }

        if (isWhich != 3)
        {
            Spacer(modifier = Modifier.padding(8.dp))

            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, newGray, RoundedCornerShape(8.dp))
            )
            {
                Column(modifier = Modifier.padding(4.dp)) {

                    if (content[index]?.enquiry_details?.enquiry_by_user_phone?.isNotEmpty() == true) {

                        constants.spacer(2)

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SubcomposeAsyncImage(
                                R.drawable.calldetailenquiry, "",
                                modifier = Modifier.size(if (forTab()) 16.dp else 12.dp)
                            )
                            Text(
                                content[index]?.enquiry_details?.enquiry_by_user_phone ?: "",
                                fontSize = constants.textUnit(12)
                            )
                        }
                    }

                    constants.spacer(2)

                    if (content[index]?.enquiry_details?.enquiry_by_user_email?.isNotEmpty() == true) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            SubcomposeAsyncImage(
                                R.drawable.mailenquiry, "",
                                modifier = Modifier.size(if (forTab())16.dp else 12.dp)
                            )
                            Text(
                                content[index]?.enquiry_details?.enquiry_by_user_email ?: "",
                                fontSize = constants.textUnit(12)
                            )
                        }
                    }

                    constants.spacer(2)

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SubcomposeAsyncImage(
                            R.drawable.locationenquiry, "",
                            modifier = Modifier.size(if (forTab())16.dp else 12.dp)
                        )
                        Text(content[index]?.enquiry_details?.address ?:"" ,fontSize = constants.textUnit(12))
                    }

                    constants.spacer(2)
                }
            }
        }

        if (isWhich == 3) {

            if (content[index]?.enquiry_details?.land_category_para?.isNotEmpty() == true) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        "Your Message",
                        fontSize = constants.textUnit(12),
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, newGray, RoundedCornerShape(8.dp))
                    ) {
                        Text(
                            content[index]?.enquiry_details?.land_category_para ?: "",
                            color = Color.Black,
                            fontSize = constants.textUnit(12),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(4.dp))
            }
        }

        Spacer(modifier = Modifier.padding(4.dp))

        AnimatedVisibility(
            visible = viewMore,
            enter = expandVertically(tween(800)),
            exit = shrinkVertically(tween(800))
        )
        {
            if(content[index]?.isWhich != 2) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                {
                    if (isWhich != 3) {

                        if (content[index]?.enquiry_details?.land_category_para?.isNotEmpty() == true) {

                                Text(
                                    "Message",
                                    fontSize = constants.textUnit(14),
                                    fontFamily = constants.fontFamily(1),
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.padding(4.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(1.dp, newGray, RoundedCornerShape(8.dp))
                                ) {
                                    Text(
                                        content[index]?.enquiry_details?.land_category_para ?: "",
                                        color = Color.Black,
                                        fontSize = constants.textUnit(12),
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }

                            Spacer(modifier = Modifier.padding(4.dp))
                        }
                    }

                    Text(
                        "Enquired Property",
                        fontSize = constants.textUnit(14)
                        , fontFamily = constants.fontFamily(1),
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.padding(4.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, newGray, RoundedCornerShape(8.dp))
                    )
                    {
                        ListItem(
                            headlineContent = {
                                Column {
                                    Box(
                                        modifier = Modifier
                                            .wrapContentSize()
                                            .background(Color.Transparent)
                                    ) {
                                        Text(
                                            content[index]?.post_user?.post_property?.landCategoryText ?: "",
                                            color = newBlue,
                                            fontSize = constants.textUnit(10),
                                            fontFamily = constants.fontFamily(1),
                                            modifier = Modifier.padding(horizontal = 4.dp),
                                            maxLines = 2
                                        )
                                    }

                                    constants.spacer(2)

                                    Text(
                                        content[index]?.post_user?.post_property?.property_name ?: "",
                                        color = Color.Black,
                                        fontSize = constants.textUnit(16),
                                        maxLines = 2
                                    )

                                    constants.spacer(2)

                                    Row(
                                        verticalAlignment = Alignment.Top
                                        , horizontalArrangement = Arrangement.Center) {
                                        SubcomposeAsyncImage(
                                            model = R.drawable.locationpinenquiry,
                                            contentDescription = "",
                                            modifier = Modifier
                                                .size(14.dp)
                                                .align(Alignment.Top)
                                        )
                                        Spacer(modifier = Modifier.padding(2.dp))
                                        Text(
                                            "${content[index]?.post_user?.post_property?.locality ?: ""} "
                                            ,
                                            fontSize = constants.textUnit(12)
                                        )
                                    }

                                    constants.spacer(2)

                                    Text(
                                        "₹ ${content[index]?.post_user?.post_property?.rent?.ifEmpty { content[index]?.post_user?.post_property?.lease_amount } ?:""}",
                                        fontSize = constants.textUnit(14)
                                    )
                                    constants.spacer(2)
                                }
                            },
                            supportingContent = {
                                Row {

                                    Text("view property", fontSize = constants.textUnit(12))

                                    Icon(
                                        painter = painterResource(R.drawable.right_arrow),
                                        contentDescription = ""
                                    )
                                }
                            },
                            trailingContent = {
                                Box(
                                    modifier = Modifier
                                        .height(124.dp)
                                        .width(90.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(newGray)

                                ){
                                    SubcomposeAsyncImage(
                                        model = content[index]?.post_user?.thumbnail ?: "",
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
                                                    .background(rentoLightGray)

                                                , contentAlignment = Alignment.Center
                                            ){
                                                Image(painter = painterResource(R.drawable.emptypostsrento) , "")

                                            }
                                        } else {
                                            SubcomposeAsyncImageContent()
                                        }
                                    }
                                }
                            },
                            colors = ListItemColors(
                                containerColor = newWhite,
                                headlineColor = Color.Black,
                                leadingIconColor = Color.DarkGray,
                                overlineColor = Color.Gray,
                                supportingTextColor = Color.Gray,
                                trailingIconColor = Color.LightGray,
                                disabledHeadlineColor = Color.Gray.copy(alpha = 0.5f),
                                disabledLeadingIconColor = Color.Gray.copy(alpha = 0.5f),
                                disabledTrailingIconColor = Color.Gray.copy(alpha = 0.5f)
                            )
                            , modifier = Modifier

                                .noRippleClickable {
                                    if (content[index]?.is_deleted == 1 || content[index]?.post_user?.post_property?.is_sold == 1) {
                                        GlobalSnackbar.show("Property Not Available ")
                                    } else {

                                        keyboardController?.hide()
                                        focusManager1.clearFocus()
                                        constants.Reels_ViewModel.clear_view_pro_Details()

                                        navController.currentBackStackEntry
                                            ?.savedStateHandle
                                            ?.set("post_id", content[index]?.post_user?.user_post_id)

                                        navController.navigate(EnquiriesFlow.SingleVideoPlayerEnquiry.route)

                                    }
                                }
                        )
                    }
                }
            }
            else
            {

                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ){
                    constants.spacer(2)

                    Text(
                        "Searched for",
                        color = Color.Black,
                        fontSize = constants.textUnit(12)
                        , fontFamily = constants.fontFamily(1)
                    )

                    Spacer(modifier = Modifier.padding(8.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, newGray, RoundedCornerShape(8.dp))
                            .padding(horizontal = 16.dp)
                    ){
                        val text = when {
                            content[index]?.searched_for?.property_type == 1 -> "Residential"
                            content[index]?.searched_for?.property_type == 2 -> "Commercial"
                            else ->  "Agriculture"
                        }

                        constants.spacer(2)

                        Text(
                            "Property Type : ${text}",
                            color = Color.Black,
                            fontSize = constants.textUnit(12)
                            , fontFamily = constants.fontFamily(3)
                        )

                        constants.spacer(2)

                        Text(
                            "Price Range : ${content[index]?.searched_for?.price_range ?: ""}",
                            color = Color.Black,
                            fontSize = constants.textUnit(12)
                            , fontFamily = constants.fontFamily(3)
                        )

                        constants.spacer(2)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.padding(8.dp))

        if (isWhich == 3) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(newLightBlue)
            )
            {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(36.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .clip(RoundedCornerShape(4.dp))
                            .noRippleClickable { viewMore = !viewMore }
                            .border(1.dp, Color.Black, RoundedCornerShape(4.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            if (!viewMore) "View more" else "View less",
                            color = Color.Black,
                            fontSize = constants.textUnit(12),
                            fontFamily = constants.fontFamily(0)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .clip(RoundedCornerShape(4.dp))
                            .border(
                                1.dp,
                                Brush.linearGradient(newPurpleGradientBorder),
                                RoundedCornerShape(4.dp)
                            )
                            .background(Brush.verticalGradient(newPurpleGradient))
                            .noRippleClickable {
                                openDialer(
                                    context,
                                    content[index]?.enquiry_details?.enquiry_by_user_phone
                                        ?: ""
                                )
                            }
                        , contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            SubcomposeAsyncImage(
                                model = R.drawable.rentocallenquiryicon,
                                contentDescription = "",
                                modifier = Modifier.size(14.dp),
                                colorFilter = ColorFilter.tint(Color.White)
                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(
                                "Call",
                                color = newWhite,
                                fontSize = constants.textUnit(12)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(newWhite)
                            .noRippleClickable {

                                openWhatsAppChat(
                                    context,
                                    content[index]?.enquiry_details?.enquiry_by_user_phone
                                        ?: "",
                                    "Hi! I'm contacting you from ${
                                        activity.getString(R.string.app_name)
                                    }"
                                )

                            }
                            .border(1.dp, newGray, RoundedCornerShape(4.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        SubcomposeAsyncImage(
                            model = R.drawable.waenquiry,
                            contentDescription = "",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                        )
                    }
                }
            }
        }
        else {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(newLightBlue)
            )
            {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(36.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .clip(RoundedCornerShape(4.dp))
                            .noRippleClickable { viewMore = !viewMore }
                            .border(1.dp, Color.Black, RoundedCornerShape(4.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            if (!viewMore) "View more" else "View less",
                            color = Color.Black,
                            fontSize = constants.textUnit(12)
                        )
                    }

                    if (isWhich == 1) {

                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                                .noRippleClickable {

                                    constants.API_Vm.chat_Config(
                                        user_id = content[index]?.enquiry_details?.enquiry_by_user_id
                                            ?: 0,
                                        user_post_id = content[index]?.post_user?.user_post_id ?: 0,
                                    ) { apiResultHandling ->
                                        when (apiResultHandling) {
                                            is API_Result_Handling.Deactivated -> {

                                            }

                                            is API_Result_Handling.Error -> {
                                                toast("Unable to Unblock ,try again later!")

                                            }

                                            is API_Result_Handling.NoData -> {

                                            }

                                            is API_Result_Handling.Loading -> {

                                            }

                                            is API_Result_Handling.Success -> {

                                                FirebaseHelper.createPropertyChat(
                                                    propertyId = content[index]?.post_user?.user_post_id.toString(),
                                                    sellerId = AppPreferences.getUserId()
                                                        .toString(),
                                                    buyerId = content[index]?.enquiry_details?.enquiry_by_user_id.toString(),
                                                    initialMessage = content[index]?.enquiry_details?.land_category_para
                                                        ?: "",
                                                    onSuccess = {
                                                        navController.navigate(
                                                            EnquiriesFlow.Msg_ChatScreen.route +
                                                                    "/${content[index]?.enquiry_details?.enquiry_by_user_id}" +
                                                                    "/${content[index]?.post_user?.user_post_id}" +
                                                                    "/${AppPreferences.getUserId()}" +
                                                                    "/${
                                                                        content[index]?.enquiry_details?.enquiry_by_username
                                                                    }" +
                                                                    "/${
                                                                        content[index]?.enquiry_details?.cities
                                                                    }" +
                                                                    "/${
                                                                        content[index]?.enquiry_details?.enquiry_by_profile_image
                                                                    }"
                                                        )
                                                    },
                                                    onFailure = {
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                                .clip(RoundedCornerShape(4.dp))
                                .background(Brush.verticalGradient(newPurpleGradient))
                                .border(
                                    1.dp,
                                    Brush.verticalGradient(newPurpleGradientBorder),
                                    RoundedCornerShape(4.dp)
                                )
                            , contentAlignment = Alignment.Center
                        )
                        {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                SubcomposeAsyncImage(
                                    model = R.drawable.messageenquiry,
                                    contentDescription = "",
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.padding(4.dp))
                                Text(
                                    "Message",
                                    color = newWhite,
                                    fontSize = constants.textUnit(12)
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(newWhite)
                                .noRippleClickable {

                                    keyboardController?.hide()
                                    focusManager1.clearFocus()

                                    onDeleteClick(content[index])

                                }
                                .border(1.dp, newGray, RoundedCornerShape(4.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            SubcomposeAsyncImage(
                                model = R.drawable.closeenquiry,
                                contentDescription = "",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Enquiry_Message_Structure2(
    content: Get_My_Leads_Data?,
    navController: NavHostController,
    from_where: Int,
    onDeleteClick: (Get_My_Leads_Data?) -> Unit,
    keyboardController: SoftwareKeyboardController?,
    focusManager1: FocusManager
)
{

    var viewMore by remember { mutableStateOf(false) }

    val isWhich = content?.isWhich

    var expanded = remember { mutableStateOf(false) }

    var expanded_undo by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.linearGradient(
                    listOf(Color(0xffFFF9F9), Color(0xffFFFFFF))
                )
            )
            .border(
                1.dp, brush = Brush.verticalGradient(
                    listOf(Color(0xffE54C3C), Color(0xffFF9A8F))
                ), RoundedCornerShape(16.dp)
            )
    )
    {

        ListItem(
            headlineContent = {
                if (from_where == 0) {
                    Text(content?.enquiry_details?.enquiry_by_username ?: "",
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(0),
                        color = Color.Black)
                }
                else {
                    Text(content?.post_user?.name?.ifEmpty { content?.post_user?.username } ?: "",fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(0),
                        color = Color.Black)
                }

            },
            leadingContent = {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(newBlue)
                ) {

                    SubcomposeAsyncImage(
                        model = if (from_where == 0)  content?.enquiry_details?.enquiry_by_profile_image ?: "" else content?.post_user?.profile_image ?: "",
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
                                    .background(newLightBlue)

                                , contentAlignment = Alignment.Center
                            ){
                                Text(
                                    text = content?.enquiry_details?.enquiry_by_username ?.takeIf { it?.isNotEmpty() == true }?.take(1)?.uppercase() ?: ""
                                )

                            }
                        } else {
                            SubcomposeAsyncImageContent()
                        }
                    }
                }
            },
            supportingContent = {
                Text( getTimeAgo(content?.enquiry_details?.created_at ?:""), fontSize = constants.textUnit(14),
                    fontFamily = constants.fontFamily(1),
                    color = newGray)
            },
            trailingContent = {
                if (isWhich == 5) {
                    Row (
                        modifier = Modifier
                            , verticalAlignment = Alignment.CenterVertically
                        , horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .background(Color(0xffFCEDEC)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Declined",
                                color = Color(0xffE54C3C),
                                fontSize = constants.textUnit(12),
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                        SubcomposeAsyncImage(
                            model = R.drawable.deleteselfenquiry,
                            "",
                            modifier = Modifier
                                .noRippleClickable {
                                    constants.Common_H_ViewModel.toggleshowBABars(false)
                                    constants.Enquiry_ViewModel.selectedEnquiry = content

                                    onDeleteClick(content)

                                    constants.open_Popup =
                                        Custom_PopUpState(type = "self_enquiry_delete", true)
                                }
                                .size(32.dp)
                        )
                    }
                }
                else {

                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .background(Color(0xffFCEDEC)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Declined",
                            color = Color(0xffE54C3C),
                            fontSize = constants.textUnit(12),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            },
            colors = ListItemColors(
                containerColor = Color.Transparent,
                headlineColor = Color.Black,
                leadingIconColor = Color.DarkGray,
                overlineColor = Color.Gray,
                supportingTextColor = Color.Gray,
                trailingIconColor = Color.LightGray,
                disabledHeadlineColor = Color.Gray.copy(alpha = 0.5f),
                disabledLeadingIconColor = Color.Gray.copy(alpha = 0.5f),
                disabledTrailingIconColor = Color.Gray.copy(alpha = 0.5f)
            )
            , modifier = Modifier
                .noRippleClickable{

                    if (content?.is_deleted == 1){
                        GlobalSnackbar.show("Property Not Available ")
                    }
                    else {
                        if (from_where == 0) {
                            constants.Profile_ViewModel.clear_All_BF_Handler()

                            constants.Profile_ViewModel.add_Selected_User_Name(
                                content?.enquiry_details?.enquiry_by_username ?: "UserName"
                            )

                            constants.Profile_ViewModel.add_BF_Handler(
                                Profile_Handle_Back(
                                    current_UsedId = AppPreferences.getUserId(),
                                    other_UserId = content?.enquiry_details?.enquiry_by_user_id
                                        ?: 0,
                                    ff_User_Name = content?.enquiry_details?.enquiry_by_username
                                        ?: "",
                                    ff_Fw_Count = 999,
                                    ff_Fg_Count = 999,
                                )
                            )

                            constants.Profile_ViewModel.addProfile(
                                content?.enquiry_details?.enquiry_by_user_id ?: 0
                            )
                            constants.Profile_ViewModel.add_Selected_Profile_Id(
                                id = content?.enquiry_details?.enquiry_by_user_id ?: 0
                            )

                            navController.navigate(
                                EnquiriesFlow.Other_Profile_Structure.route
                            )
                        } else {

                            constants.Profile_ViewModel.clear_All_BF_Handler()

                            constants.Profile_ViewModel.add_Selected_User_Name(
                                content?.post_user?.username ?: "UserName"
                            )

                            constants.Profile_ViewModel.add_BF_Handler(
                                Profile_Handle_Back(
                                    current_UsedId = AppPreferences.getUserId(),
                                    other_UserId = content?.post_user?.user_id ?: 0,
                                    ff_User_Name = content?.post_user?.username ?: "",
                                    ff_Fw_Count = 999,
                                    ff_Fg_Count = 999,
                                )
                            )

                            constants.Profile_ViewModel.addProfile(content?.post_user?.user_id ?: 0)
                            constants.Profile_ViewModel.add_Selected_Profile_Id(
                                id = content?.post_user?.user_id ?: 0
                            )

                            navController.navigate(
                                EnquiriesFlow.Other_Profile_Structure.route
                            )
                        }
                    }

            }
        )

        Column (
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ){

            if (isWhich == 5 || isWhich == 4) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    if (isWhich == 5) {
                        Text("Enquired On")
                        Text("24 Jun 2025", color = newGray, fontSize = constants.textUnit(12))
                    } else {
                        Text("Lead details")
                        Row {
                            SubcomposeAsyncImage(
                                model = R.drawable.enquirycallbuttonicon,
                                contentDescription = "",

                                modifier = Modifier
                                    .size(28.dp)
                                    .noRippleClickable {
                                        openDialer(
                                            context,
                                            content?.enquiry_details?.enquiry_by_user_phone ?: ""
                                        )
                                    }
                            )
                            Spacer(modifier = Modifier.padding(8.dp))
                            SubcomposeAsyncImage(
                                model = R.drawable.waenquiry,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(28.dp)
                                    .noRippleClickable {
                                        openWhatsAppChat(
                                            context,
                                            content?.enquiry_details?.enquiry_by_user_phone ?: "",
                                            "Hi! I'm contacting you from MyApp"
                                        )

                                    }
                            )
                        }
                    }
                }

                if (isWhich == 4) {
                    constants.spacer(4)

                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)

                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, newGray, RoundedCornerShape(8.dp))
                    )
                    {
                        Column(modifier = Modifier.padding(4.dp)) {

                            if (content?.enquiry_details?.enquiry_by_user_phone?.isNotEmpty() == true) {

                                constants.spacer(2)

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    SubcomposeAsyncImage(
                                        R.drawable.calldetailenquiry, "",
                                        modifier = Modifier.size(if (forTab()) 16.dp else 12.dp)
                                    )
                                    Text(
                                        content?.enquiry_details?.enquiry_by_user_phone
                                            ?: "",
                                        fontSize = constants.textUnit(12)
                                    )
                                }
                            }

                            constants.spacer(2)

                            if (content?.enquiry_details?.enquiry_by_user_email?.isNotEmpty() == true) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    SubcomposeAsyncImage(
                                        R.drawable.mailenquiry, "",
                                        modifier = Modifier.size(if (forTab()) 16.dp else 12.dp)
                                    )
                                    Text(
                                        content?.enquiry_details?.enquiry_by_user_email
                                            ?: "",
                                        fontSize = constants.textUnit(12)
                                    )
                                }
                            }

                            constants.spacer(2)

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                SubcomposeAsyncImage(
                                    R.drawable.locationenquiry, "",
                                    modifier = Modifier.size(if (forTab()) 16.dp else 12.dp)
                                )
                                Text(
                                    content?.enquiry_details?.address ?: "",
                                    fontSize = constants.textUnit(12)
                                )
                            }

                            constants.spacer(2)
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(4.dp))
                if (isWhich == 5) {
                    if (content?.enquiry_details?.land_category_para?.isNotEmpty() == true) {

                        Text(
                            "Your Message",
                            fontSize = constants.textUnit(14),
                            fontFamily = constants.fontFamily(1),
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.padding(4.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, newGray, RoundedCornerShape(8.dp))
                        )
                        {
                            Text(
                                content?.enquiry_details?.land_category_para ?: "",
                                color = Color.Black,
                                fontSize = constants.textUnit(12),
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
        }

            AnimatedVisibility(
                visible = viewMore,
                enter = expandVertically(tween(800)),
                exit = shrinkVertically(tween(800))
            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                {

                    if (isWhich !=5 ) {
                        Spacer(modifier = Modifier.padding(8.dp))

                        if (content?.enquiry_details?.land_category_para?.isNotEmpty() == true){
                        Text( "Message", fontSize = constants.textUnit(12), color = Color.Black)
                        Spacer(modifier = Modifier.padding(4.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, newGray, RoundedCornerShape(8.dp))
                        ) {
                            Text(
                                content?.enquiry_details?.land_category_para ?: "",
                                color = Color.Black,
                                fontSize = constants.textUnit(12),
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                            }
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                    Text(
                        "Enquired for this property",
                        fontSize = constants.textUnit(12),
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.padding(4.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, newGray, RoundedCornerShape(8.dp))
                    )
                    {
                        ListItem(
                            headlineContent = {
                                Column {
                                    constants.spacer(2)
                                    Box(
                                        modifier = Modifier
                                            .wrapContentSize()
                                            .background(newLightBlue)
                                    ) {
                                        Text(
                                            content?.post_user?.post_property?.landCategoryText ?: "",
                                            color = newBlue,
                                            fontSize = constants.textUnit(10),
                                            modifier = Modifier.padding(horizontal = 4.dp),
                                            maxLines = 2
                                        )
                                    }

                                    constants.spacer(2)

                                    Text(
                                        content?.post_user?.post_property?.property_name ?: "",
                                        color = Color.Black,
                                        fontSize = constants.textUnit(16),
                                        maxLines = 2
                                    )

                                    constants.spacer(2)

                                    Row(
                                            verticalAlignment = Alignment.Top
                                        , horizontalArrangement = Arrangement.Center
                                    ) {
                                        SubcomposeAsyncImage(
                                            model = R.drawable.locationpinenquiry,
                                            contentDescription = "",
                                            modifier = Modifier
                                                .size(14.dp)
                                                .align(Alignment.Top)
                                        )
                                        Spacer(modifier = Modifier.padding(2.dp))
                                        Text(
                                            content?.post_user?.post_property?.locality ?: "",
                                            fontSize = constants.textUnit(12)
                                        )
                                    }

                                    constants.spacer(2)

                                    Text(
                                        "₹ ${content?.post_user?.post_property?.rent?.ifEmpty { content?.post_user?.post_property?.lease_amount } ?:""}",
                                        fontSize = constants.textUnit(14)
                                    )

                                    constants.spacer(2)
                                }
                            },
                            supportingContent = {
                                Row {
                                    Text("view property", fontSize = constants.textUnit(12))
                                    Icon(
                                        painter = painterResource(R.drawable.right_arrow),
                                        contentDescription = ""
                                    )
                                }
                            },
                            trailingContent = {
                                Box(
                                    modifier = Modifier
                                        .height(124.dp)
                                        .width(90.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(newGray)
                                        .noRippleClickable {
                                            if (content?.is_deleted == 1 || content?.post_user?.post_property?.is_sold == 1) {
                                                GlobalSnackbar.show("Property Not Available ")
                                            } else {
                                                constants.Reels_ViewModel.clear_view_pro_Details()
                                                val videoJson =
                                                    Uri.encode(Json.encodeToString(content?.post_user))
                                                navController.navigate("${EnquiriesFlow.SingleVideoPlayerEnquiry.route}/$videoJson")
                                            }

                                        }
                                ){
                                    SubcomposeAsyncImage(
                                        model = content?.post_user?.thumbnail ?: "",
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
                                                    .background(newLightBlue)

                                                , contentAlignment = Alignment.Center
                                            ){

                                            }
                                        } else {
                                            SubcomposeAsyncImageContent()
                                        }
                                    }
                                }
                            },
                            colors = ListItemColors(
                                containerColor = newWhite,
                                headlineColor = Color.Black,
                                leadingIconColor = Color.DarkGray,
                                overlineColor = Color.Gray,
                                supportingTextColor = Color.Gray,
                                trailingIconColor = Color.LightGray,
                                disabledHeadlineColor = Color.Gray.copy(alpha = 0.5f),
                                disabledLeadingIconColor = Color.Gray.copy(alpha = 0.5f),
                                disabledTrailingIconColor = Color.Gray.copy(alpha = 0.5f)
                            )
                            , modifier = Modifier

                        )
                    }
                }
            }

            Spacer(modifier = Modifier.padding(8.dp))
        if (isWhich == 4){

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xffFCEDEC))
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(36.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .clip(RoundedCornerShape(4.dp))
                            .noRippleClickable { viewMore = !viewMore }
                            .border(1.dp, Color.Black, RoundedCornerShape(4.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            if (!viewMore) "View more" else "View less",
                            color = Color.Black,
                            fontSize = constants.textUnit(14),
                            fontFamily = constants.fontFamily(0)
                        )
                    }

                    ExposedDropdownMenuBox(
                        expanded = expanded_undo,
                        onExpandedChange = { expanded_undo = !expanded_undo }
                    )
                    {
                        Image(
                            painter = painterResource(R.drawable.undo_decline_enquiry),
                            contentDescription = "More options",
                            modifier = Modifier
                                .size(46.dp)
                                .menuAnchor()
                                .noRippleClickable { expanded_undo = true }
                        )

                        ExposedDropdownMenu(
                            expanded = expanded_undo,
                            onDismissRequest = { expanded_undo = false },
                            containerColor = newWhite,
                            modifier = Modifier
                                .width(IntrinsicSize.Min)

                        ) {
                            val pairList = listOf(
                                Pair(R.drawable.undodecline, "Undo Decline"),
                                Pair(R.drawable.reelsdelete, "Delete Enquiry")
                            )

                            pairList.forEach { (iconRes, title) ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .noRippleClickable {
                                            expanded_undo = false
                                            if (title == "Undo Decline") {
                                                constants.Common_H_ViewModel.toggleshowBABars(true)
                                                constants.API_Vm.put_Enquiry_Decline_Undodecline(
                                                    enquire_id = content?.enquiry_details?.enquire_id
                                                        ?: 0,
                                                    status = "2",
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

                                                            constants.Enquiry_ViewModel.decline_Update_Enquiry(
                                                                content?.enquiry_details?.enquire_id
                                                                    ?: 0
                                                            )
                                                            constants.open_Popup =
                                                                Custom_PopUpState(type = "", false)

                                                        }

                                                        is API_Result_Handling.NoData -> {

                                                        }
                                                    }
                                                }
                                            } else {
                                                constants.Common_H_ViewModel.toggleshowBABars(true)
                                                constants.Enquiry_ViewModel.deleteLeads(
                                                    content?.enquiry_details?.enquire_id
                                                        ?: 0, true
                                                )

                                            }
                                        }
                                        .padding(horizontal = 12.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = iconRes),
                                        contentDescription = title,
                                        tint = newBlack,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = title,
                                        color = newBlack,
                                        fontSize = constants.textUnit(14),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.weight(1f, fill = false)
                                    )
                                }
                            }
                        }
                    }

                }
            }

        }
        else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xffE8E8E8))
            )
            {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .height(36.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .clip(RoundedCornerShape(4.dp))
                            .noRippleClickable { viewMore = !viewMore }
                            .border(1.dp, Color.Black, RoundedCornerShape(4.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            if (!viewMore) "View more" else "View less",
                            color = Color.Black,
                            fontSize = constants.textUnit(12)
                        )
                    }
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Common_BTM_Sheet(
    content: @Composable () -> Unit,
    bottom_Content: @Composable () -> Unit,
    onDismiss : MutableState<Boolean> = mutableStateOf(false)
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        onDismissRequest = {

            onDismiss.value = false

            constants.open_Btm_Sheet = Custom_BottomSheetState(type = "", isVisible = false)

        },
        containerColor = newWhite,
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
        ,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                content()
            }

            Spacer(modifier = Modifier.height(16.dp))

            Static_Bottom(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(92.dp)
            ) {
                bottom_Content()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelfEnquiry_Sort(modifier: Modifier, reload_api_onSORT: MutableState<Int>) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    var selectedOption by remember { mutableStateOf("Newest first") }

    LaunchedEffect(constants.Enquiry_ViewModel.Self_Enquiry_selected_Sort) {
        if (constants.Enquiry_ViewModel.Self_Enquiry_selected_Sort == 0){
            selectedOption = "Newest first"
        }
    }

    val sortOptions = constants.Enquiry_ViewModel.lead_Sort_List

    val selected_Date_Range_List = constants.Enquiry_ViewModel.Selected_Dates_List

    var bring_Calender by rememberSaveable { mutableStateOf(false) }

    var tempSelectedSort by rememberSaveable { mutableStateOf(constants.Enquiry_ViewModel.Self_Enquiry_selected_Sort) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column (
            modifier = Modifier
                , horizontalAlignment = Alignment.Start
        ){
            Text(
                context.getString(R.string.filter_sort),
                color = newBlack,
                fontSize = constants.textUnit(16)
            )
            if (selected_Date_Range_List.isNotEmpty() && constants.Enquiry_ViewModel.Self_Enquiry_selected_Sort == 4) {
                Text(
                    " Sort By ${formatToShortMonthDay(selected_Date_Range_List.firstOrNull()?.start_Date.toString())} ${ if (selected_Date_Range_List.firstOrNull()?.end_Date != null ) "-" else "" } ${formatToShortMonthDay(selected_Date_Range_List.firstOrNull()?.end_Date.toString())}",
                    color = newGray,
                    fontSize = constants.textUnit(14),
                    fontFamily = constants.fontFamily(1),
                )
            }

        }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        )
        {
            Box(
                modifier = Modifier
                    .menuAnchor()
                    .clip(RoundedCornerShape(4.dp))
                    .border(1.dp, newGray, RoundedCornerShape(4.dp))
                    .padding(horizontal = 8.dp, vertical = 6.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        selectedOption,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Icon(painter = painterResource(R.drawable.arrowdown), contentDescription = "")
                }
            }

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
                , containerColor = newWhite
            )
            {
                sortOptions.forEachIndexed { index , option ->
                    DropdownMenuItem(
                        text = { Text(option , fontSize = constants.textUnit(14),
                            fontFamily = constants.fontFamily(1),
                            color = Color.Black) },
                        onClick = {
                            ClickHelper.getInstance().clickOnce {
                                selectedOption = option
                                if (index != 4){
                                    constants.Enquiry_ViewModel.Selected_Dates_List.clear()
                                    constants.API_Vm.isLoading_Self_Enquiry = true
                                    constants.API_Vm.totalPages_Self_Enquiry = 1
                                    reload_api_onSORT.value = reload_api_onSORT.value + 4560
                                }
                                constants.Enquiry_ViewModel.Self_Enquiry_selected_Sort = index
                                if (index == 4){
                                    constants.Enquiry_ViewModel.Self_Enquiry_selected_Sort_Bool = true
                                }
                                constants.API_Vm.isLoading_Self_Enquiry = true

                                expanded = false
                            }

                        }
                    )
                }
            }
        }

    }
}

fun formatToShortMonthDay(dateString: String?): String {
    return try {
        if (dateString.isNullOrEmpty()) return ""
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
        val outputFormatter = DateTimeFormatter.ofPattern("MMM dd", Locale.ENGLISH)
        val parsedDate = LocalDate.parse(dateString, inputFormatter)
        parsedDate.format(outputFormatter)
    } catch (e: Exception) {
        ""
    }
}

@Composable
fun Common_Popup2(
    visible: Boolean,
    modifier: Modifier,
    image : String = "",
    icon : Int = 0,
    userName : String = "",
    content: @Composable () -> Unit
) {
    var isVisibleInUi by remember { mutableStateOf(visible) }

    LaunchedEffect(visible) {
        if (visible) {
            isVisibleInUi = true
        } else {

            delay(600)
            isVisibleInUi = false
        }
    }

    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.8f,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "popupScale"
    )

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "popupAlpha"
    )

    if (isVisibleInUi) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(false) {}
                .background(if (icon == 1) Color.Transparent else newBlack.copy(0.5f * alpha))
            ,contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        alpha = alpha
                    )
                    .fillMaxWidth(0.95f)
                    .fillMaxHeight(0.3f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(newWhite),
                contentAlignment = Alignment.Center
            ) {

                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                        .fillMaxHeight(.4f)
                    , contentAlignment = Alignment.Center
                )
                {
                    Box(
                        modifier = modifier
                            .align(Alignment.TopCenter)
                            .fillMaxWidth()
                            .fillMaxHeight(.6f)

                    )

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color(0xffF4F4F4))
                        , contentAlignment = Alignment.Center
                    ){
                        if (icon == 0) {
                            SubcomposeAsyncImage(
                                model = image,
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentDescription = "",
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
                                        Text(
                                            text = userName, color = newBlue
                                        )
                                    }
                                } else {
                                    SubcomposeAsyncImageContent()
                                }
                            }
                        }
                        else {
                            Image(painterResource(icon) ,  modifier = Modifier
                                .size(32.dp),
                                contentDescription = "",
                                contentScale = ContentScale.FillBounds
                            )
                        }
                    }
                }

                Column (
                    modifier = modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxHeight(.65f)
                        .fillMaxWidth()
                    , verticalArrangement = Arrangement.Center
                    , horizontalAlignment = Alignment.CenterHorizontally
                ){

                    content()
                }
            }
        }
    }
}

@Composable
fun Common_Popup(
    visible: Boolean,
    modifier: Modifier,
    image : String = "",
    icon : Int = 0,
    userName : String = "",
    content: @Composable () -> Unit
) {
    var isVisibleInUi by remember { mutableStateOf(visible) }

    LaunchedEffect(visible) {
        if (visible) {
            isVisibleInUi = true
        } else {

            delay(600)
            isVisibleInUi = false
        }
    }

    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.8f,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "popupScale"
    )

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "popupAlpha"
    )

    if (isVisibleInUi) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(false) {}
                .background(if (icon == 1) Color.Transparent else newBlack.copy(0.5f * alpha))
            ,contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        alpha = alpha
                    )
                    .fillMaxWidth(if (forTab()) 0.75f else 0.95f)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(8.dp))
                    .background(newWhite),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()

                        , verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        content()
                    }
                }
            }

        }
    }
}

fun getTimeAgoold(timestamp: String): String {
    return try {
        val apiInstant = Instant.parse(timestamp)
        val nowInstant = Instant.now()

        val durationMillis = nowInstant.toEpochMilli() - apiInstant.toEpochMilli()

        if (durationMillis <= 0 || durationMillis < 60000) {
            return "Just now"
        }

        val totalMinutes = durationMillis / (1000 * 60)
        val totalHours = totalMinutes / 60
        val totalDays = totalHours / 24

        when {
            totalDays > 0 -> {
                val remainingHours = totalHours % 24
                if (remainingHours > 0) {
                    "${totalDays}d ago"

                } else {
                    if (totalDays == 1L) "1 day ago" else "${totalDays} days ago"
                }
            }
            totalHours > 0 -> {
                val remainingMinutes = totalMinutes % 60
                if (remainingMinutes > 0) {
                    "${totalHours}h ${remainingMinutes}m ago"
                } else {
                    if (totalHours == 1L) "1 hour ago" else "${totalHours} hours ago"
                }
            }
            totalMinutes > 0 -> {
                if (totalMinutes == 1L) "1 min ago" else "${totalMinutes} mins ago"
            }
            else -> "Just now"
        }
    } catch (e: Exception) {
        e.printStackTrace()
        "Just now"
    }
}

fun getTimeAgo(timestamp: String): String {
    return try {
        val apiInstant = Instant.parse(timestamp)
        val nowInstant = Instant.now()

        val durationMillis = nowInstant.toEpochMilli() - apiInstant.toEpochMilli()

        if (durationMillis <= 0 || durationMillis < 60000) {
            return "Just now"
        }

        val totalMinutes = durationMillis / (1000 * 60)
        val totalHours = totalMinutes / 60
        val totalDays = totalHours / 24
        val totalWeeks = totalDays / 7
        val totalMonths = totalDays / 30
        val totalYears = totalDays / 365

        when {
            totalYears > 0 -> if (totalYears == 1L) "1 year ago" else "$totalYears years ago"
            totalMonths > 0 -> if (totalMonths == 1L) "1 month ago" else "$totalMonths months ago"
            totalWeeks > 0 -> if (totalWeeks == 1L) "1 week ago" else "$totalWeeks weeks ago"
            totalDays > 0 -> if (totalDays == 1L) "1 day ago" else "$totalDays days ago"
            totalHours > 0 -> if (totalHours == 1L) "1 hour ago" else "$totalHours hours ago"
            totalMinutes > 0 -> if (totalMinutes == 1L) "1 min ago" else "$totalMinutes mins ago"
            else -> "Just now"
        }
    } catch (e: Exception) {
        "Just now"
    }
}

fun isWithinOneHour(timestamp: String): Boolean {
    return try {
        val apiInstant = Instant.parse(timestamp)
        val nowInstant = Instant.now()

        val duration = Duration.between(apiInstant, nowInstant).abs()
        duration.toHours() <= 1
    } catch (e: Exception) {
        false
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun View_Property_Structure(navController: NavHostController, viewModel: Common_H_ViewModel) {

    viewModel.toggleshowTABars(false)
    viewModel.toggleshowBABars(false)

    var network = rememberNetworkStatus()

    val BA_Bar_Listener = viewModel.showBABars.collectAsState()
    val TA_Bar_Listener = viewModel.showTABars.collectAsState()

    LaunchedEffect (BA_Bar_Listener , TA_Bar_Listener){
        viewModel.toggleshowTABars(TA_Bar_Listener.value)
        viewModel.toggleshowBABars(BA_Bar_Listener.value)
    }
    DisposableEffect(Unit) {
        viewModel.toggleshowTABars(false)
        viewModel.toggleshowBABars(false)
        onDispose {  }
    }

    var onFullView = remember { mutableStateOf(false) }

    val profile_Mode = constants.Profile_ViewModel.switch_Profile_Mode.collectAsState()

    var view_Details_Data = constants.Reels_ViewModel.viewProperty_Details.collectAsState()

    val send_Eq_State = constants.Reels_ViewModel.send_Enquiry_Btm_Sheet.collectAsState()

    LaunchedEffect(Unit) {
        if (view_Details_Data.value?.user_id == AppPreferences.getUserId()) {
            constants.Profile_ViewModel.set_Profile_Mode(0)
        } else {
            constants.Profile_ViewModel.set_Profile_Mode(1)
        }
    }

    val repost_Btm = remember { mutableStateOf(false) }

    val lazyListState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(newWhite)
    )
    {
        Column(
            modifier = Modifier.fillMaxSize()
        )
        {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(8f)
            )
            {
                item(key = "video_image_section") {
                    Column(
                        modifier = Modifier
                            .then(
                                if (constants.PostProperty_ViewModel.get_Post_Form_Flow() == -1) {
                                    Modifier.weight(3.5f)
                                } else {
                                    Modifier.weight(8f)
                                }
                            )
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            key("vps_first_part_${view_Details_Data.value?.user_post_id}") {
                                VPS_FirstPart(
                                    viewModel.view_Property_Details_Mode.value,
                                    onFullView,
                                    navController,
                                    view_Details_Data
                                )
                            }
                        }
                    }
                }

                item(key = "details_section") {
                    Box(
                        modifier = Modifier
                            .then(
                                if (constants.PostProperty_ViewModel.get_Post_Form_Flow() == -1) {
                                    Modifier.weight(5.5f)
                                } else {
                                    Modifier.weight(2.5f)
                                }
                            )
                            .fillMaxWidth()
                    ) {
                        VPS_SecondPart(profile_Mode, navController, view_Details_Data)
                    }
                }
            }

            var loadpost by remember { mutableStateOf(false) }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            {
                Static_Bottom(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    content = {
                        if (constants.PostProperty_ViewModel.get_Post_Form_Flow() == -1) {
                            if (profile_Mode.value == 0) {
                                if (constants.Profile_ViewModel.get_From_Repost() == 1) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight()
                                            .padding(horizontal = 16.dp, vertical = 16.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(newBlue)
                                            .noRippleClickable {
                                                if (network.value == NetworkStatus.Online) {
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
                                                                viewModel.selectedBABTab(0)

                                                                constants.URL_COMPLETED.clear()
                                                                constants.PostProperty_ViewModel.clear_Media()

                                                                navController.navigate(
                                                                    PostPropertyFlow.Common_Screen.route
                                                                )
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    toast(activity.getString(R.string.no_Internet))
                                                }
                                            }
                                        , verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            "Post this Property",
                                            color = Color.White,
                                            fontSize = constants.textUnit(14),
                                            fontFamily = constants.fontFamily(0)
                                        )
                                    }
                                }
                                else {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight()
                                            .background(Color.White)
                                            .padding(horizontal = 16.dp)
                                        , contentAlignment = Alignment.Center
                                    )
                                    {
                                        Text(
                                            "Property Price"
                                            , color = newBlack
                                            , fontSize = constants.textUnit(14)
                                            , fontFamily = constants.fontFamily(2)
                                            , modifier = Modifier.align(Alignment.CenterStart)
                                        )

                                        val formattedPrice = view_Details_Data.value?.post_property?.rent?.ifEmpty { view_Details_Data.value?.post_property?.lease_amount }?.let { price ->
                                            try {
                                                val number = price.toString().toDouble()
                                                NumberFormat.getNumberInstance(Locale("en", "IN")).format(number)
                                            } catch (e: Exception) {
                                                "Unavailable"
                                            }
                                        } ?: "Unavailable"

                                        Text(
                                            text = "\u20B9 $formattedPrice",
                                            color = newBlack
                                            , fontSize = constants.textUnit(24)
                                            , fontFamily = constants.fontFamily(0)
                                            , modifier = Modifier.align(Alignment.CenterEnd)
                                        )
                                    }
                                }
                            }
                            else {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                )
                                {
                                    val formattedPrice = view_Details_Data.value?.post_property?.rent?.ifEmpty { view_Details_Data.value?.post_property?.lease_amount }?.let { price ->
                                        try {
                                            val number = price.toString().toDouble()
                                            NumberFormat.getNumberInstance(Locale("en", "IN")).format(number)
                                        } catch (e: Exception) {
                                            "Unavailable"
                                        }
                                    } ?: "Unavailable"

                                    Text(
                                        "\u20B9 $formattedPrice",
                                        color = newBlack,
                                        fontSize = constants.textUnit(24)
                                        , fontFamily = constants.fontFamily(0)
                                    )

                                    Box(
                                        modifier = Modifier
                                            .background(
                                                Brush.verticalGradient(newPurpleGradient),
                                                RoundedCornerShape(4.dp)
                                            )
                                            .noRippleClickable {
                                                if (view_Details_Data.value?.enquiry == 1) {
                                                    constants.Reels_ViewModel.enable_Send_Eq_Btm_Sheet()
                                                } else {
                                                    GlobalSnackbar.show("Already Enquired")
                                                }
                                            }
                                            .padding(horizontal = 16.dp, vertical = 8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            "Send Enquiry",
                                            color = Color.White,
                                            fontSize = constants.textUnit(14)
                                        )
                                    }
                                }
                            }
                        }
                        else {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .padding(horizontal = 16.dp, vertical = 16.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(newBlue)

                                , verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                if (!loadpost) {
                                    Text(
                                        "Post this Property",
                                        color = Color.White,
                                        fontSize = constants.textUnit(14),
                                        fontFamily = constants.fontFamily(0)
                                    )
                                }
                                else {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(24.dp),
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                )
            }
        }

        val mediaList = buildList<ViewMedia> {
            view_Details_Data.value?.post_property?.images?.forEach {
                add(ViewMedia.ImageItem(url = it.url ?: "", title = it.heading ?: "Image"))
            }
            view_Details_Data.value?.post_property?.video?.forEach {
                add(ViewMedia.VideoItem(url = it.url ?: "", title = it.heading ?: "Video"))
            }
        }

        AnimatedVisibility(
            visible = onFullView.value,
            enter = slideInVertically(animationSpec = tween(900)) { it },
            exit = slideOutVertically(animationSpec = tween(900)) { it }
        )
        {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .noRippleClickable {

                    }
                    .background(Color.White)
            ) {
                var dummy = remember { mutableStateOf(false) }
                VPS_FullView(
                    onFullView,
                    mediaList
                )
            }
        }
    }

    if (repost_Btm.value) {
        ModalBottomSheet(
            onDismissRequest = {
                repost_Btm.value = false
            },
            containerColor = Color.White
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                , verticalArrangement = Arrangement.spacedBy(8.dp)
                , horizontalAlignment = Alignment.CenterHorizontally
            ){
                Image(painter = painterResource(R.drawable.repost) , "")

                Text("Repost lets you create a fresh post using the details from your existing one. Use it if you want to update your photos or videos. Property details will be copied, but you can edit them before posting again. Your previous post will remain unchanged"
                , modifier = Modifier
                    .padding(horizontal = 8.dp)
                    , textAlign = TextAlign.Center
                )

                val state = remember { mutableStateOf(-1) }

                Static_Bottom(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                )
                {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .fillMaxHeight(.6f)
                            .background(newBlue)
                            .noRippleClickable {
                                get_Form_Preview_API_CALL { result ->
                                    when (result) {
                                        0 -> {
                                            state.value = result
                                        }

                                        1 -> {}
                                        2 -> {}
                                        3 -> {
                                            val server_Data =
                                                constants.PostProperty_ViewModel.get_Preview_Data()

                                            server_Data?.let { data ->
                                                constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                                    data.toSelectedOptionsForm4()
                                                }
                                            }

                                            AppPreferences.save_Post_Id(
                                                server_Data?.user_post_id ?: 0
                                            )

                                            constants.PostProperty_ViewModel.first_Form_selected_PP(
                                                server_Data?.land_type_id ?: -1
                                            )
                                            constants.PostProperty_ViewModel.set_country3(
                                                server_Data?.country ?: ""
                                            )
                                            constants.PostProperty_ViewModel.set_state3(
                                                server_Data?.state ?: ""
                                            )
                                            constants.PostProperty_ViewModel.set_city3(
                                                server_Data?.city ?: ""
                                            )
                                            constants.PostProperty_ViewModel.set__selectedLocality3(
                                                server_Data?.address ?: ""
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

                                            constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value =
                                                constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value.copy(
                                                    first = server_Data?.land_type_id ?: 0,
                                                    second = server_Data?.land_categorie_id ?: 0
                                                )

                                            constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value =
                                                constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value.copy(
                                                    (server_Data?.land_type_id ?: 0),
                                                    server_Data?.land_categorie_id ?: 0
                                                )

                                            if (!server_Data?.pincode.isNullOrEmpty()) {
                                                constants.PostProperty_ViewModel.set_pincode3(
                                                    server_Data?.pincode ?: ""
                                                )
                                            }
                                            if (!server_Data?.country.isNullOrEmpty()) {
                                                constants.PostProperty_ViewModel.set_country3(
                                                    server_Data?.country ?: ""
                                                )
                                            }
                                            if (!server_Data?.state.isNullOrEmpty()) {
                                                constants.PostProperty_ViewModel.set_state3(
                                                    server_Data?.state ?: ""
                                                )
                                            }
                                            if (!server_Data?.city.isNullOrEmpty()) {
                                                constants.PostProperty_ViewModel.set_city3(
                                                    server_Data?.city ?: ""
                                                )
                                            }
                                            if (!server_Data?.address.isNullOrEmpty()) {
                                                constants.PostProperty_ViewModel.set__selectedLocality3(
                                                    server_Data?.address ?: ""
                                                )
                                            }

                                            if (server_Data?.latitude?.isNotEmpty() == true && server_Data?.longitude?.isNotEmpty() == true) {
                                                constants.PostProperty_ViewModel.add_Pinned_Lat_Long(
                                                    LatLng(
                                                        server_Data?.latitude?.toDouble() ?: 0.0,
                                                        server_Data?.longitude?.toDouble() ?: 0.0
                                                    )
                                                )
                                            }

                                            constants.PostProperty_ViewModel.add_pp3_Data(
                                                PP3_API_DC(
                                                    pincode = server_Data?.pincode ?: "",
                                                    country = server_Data?.country ?: "",
                                                    state = server_Data?.state ?: "",
                                                    city = server_Data?.city ?: "",
                                                    locality = server_Data?.locality ?: ""
                                                )
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

                                            if (!server_Data?.images.isNullOrEmpty()) {
                                                val imageMediaList =
                                                    server_Data.images.map { imageUri ->

                                                    }

                                            } else if (!server_Data?.video.isNullOrEmpty()) {

                                            }

                                            constants.PostProperty_ViewModel.select_Land_Type(
                                                server_Data?.land_type_id ?: 0
                                            )

                                            constants.Profile_ViewModel.set_From_Repost(1)
                                            constants.PostProperty_ViewModel.set_Post_Form_Flow(2)

                                            navController.navigate(ProfileScreenFlow.Post_Property_Forms.route)
                                        }
                                    }
                                }

                            }
                        , contentAlignment = Alignment.Center
                    ){
                        if (state.value == 0){
                            CircularProgressIndicator(modifier = Modifier.size(24.dp) , color = newWhite)
                        }
                        else {
                            Text("Continue to Repost" , color = newWhite)
                        }
                    }
                }
            }
        }
    }

    val deleteViewDetailsPopup = constants.Enquiry_ViewModel.deleteViewDetailsPopup.collectAsState()

    Common_Popup(
        visible =  deleteViewDetailsPopup.value.second,
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
                            constants.Enquiry_ViewModel.deleteViewDetailsProperty(0, false)
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
                        .border(
                            1.dp,
                            Brush.linearGradient(newRedGradienBorder),
                            RoundedCornerShape(8.dp)
                        )
                        .noRippleClickable {
                            constants.API_Vm.delete_Post_SM_Drafts(
                                user_id = AppPreferences.getUserId(),
                                select_all = 0,
                                user_post_id = deleteViewDetailsPopup.value.first.toString(),
                            )
                            { aPI_Result_Handling ->
                                when (aPI_Result_Handling) {
                                    is API_Result_Handling.Success -> {
                                        constants.Reels_ViewModel.clear_view_pro_Details()
                                        constants.Reels_ViewModel.deleteVideoById_Profile_Post_Reels(
                                            deleteViewDetailsPopup.value.first
                                        )
                                        constants.Enquiry_ViewModel.deleteViewDetailsProperty(
                                            0,
                                            false
                                        )
                                        navController.navigateUp()
                                    }

                                    else -> {}
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

    Enquiry_Form_Btm_Sheet_Structure(send_Eq_State.value, view_Details_Data.value)

    BackHandler {
        ClickHelper.getInstance().clickOnce {
            if (ClickGuard.canClick()) {
                when {
                    send_Eq_State.value -> constants.Reels_ViewModel.dismiss_Send_Eq_Btm_Sheet()

                    repost_Btm.value -> !repost_Btm.value

                    onFullView.value -> !onFullView.value

                    !send_Eq_State.value || !repost_Btm.value || !onFullView.value -> navController.navigateUp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VPS_FirstPart(
    typeOfData: Int,
    onFullView: MutableState<Boolean>,
    navController: NavHostController,
    view_Details_Data: State<Get_Reels_Data?>
){
    var network = rememberNetworkStatus()
    val notchPadding = rememberNotchHeightDp()
    var expanded = remember { mutableStateOf(false) }
    var mark_As_Sold = remember { mutableStateOf(false) }
    val repost_Btm = remember { mutableStateOf(false) }
    var report_BS = remember { mutableStateOf(false) }
    val report_Options = constants.Profile_ViewModel.profile_Report_Options.collectAsState()
    val report_success = constants.Profile_ViewModel.report_Submit_Success.collectAsState()

    var notInterested_Btm = remember { mutableStateOf(false) }

    val compositionKey = remember(view_Details_Data.value?.user_post_id) {
        "vps_${view_Details_Data.value?.user_post_id}_$typeOfData"
    }

    val notInterestedOptions = constants.Profile_ViewModel.notInterestedOptions.collectAsState()

    var activateRenew = remember { mutableStateOf(false) }
    var activateRentedout = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth()
    ){

        val mediaList = remember { mutableStateListOf<ViewDetailsMedia>() }

        LaunchedEffect(view_Details_Data.value) {
            val data = view_Details_Data.value ?: return@LaunchedEffect

            mediaList.clear()

            data.post_property.images?.forEach { img ->
                mediaList.add(
                    ViewDetailsMedia(
                        isVideo = false,
                        uploadedUrl = img.url
                    )
                )
            }

            data.post_property.video?.forEach { vid ->
                mediaList.add(
                    ViewDetailsMedia(
                        isVideo = true,
                        uploadedUrl = vid.url
                    )
                )
            }
        }

        RentoViewDetailsFirstPart(mediaList , view_Details_Data , mark_As_Sold , report_BS , repost_Btm , onFullView, notInterested_Btm ,navController, activateRentedout , activateRenew )
    }

    if (repost_Btm.value) {
        ModalBottomSheet(
            onDismissRequest = {
                repost_Btm.value = false
            },
            containerColor = Color.White
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                , verticalArrangement = Arrangement.spacedBy(8.dp)
                , horizontalAlignment = Alignment.CenterHorizontally
            ){
                Image(painter = painterResource(R.drawable.repost) , "")

                Text("Repost lets you create a fresh post using the details from your existing one. Use it if you want to update your photos or videos. Property details will be copied, but you can edit them before posting again. Your previous post will remain unchanged"
                    , modifier = Modifier
                        .padding(horizontal = 8.dp)
                    , textAlign = TextAlign.Center
                )

                val state = remember { mutableStateOf(-1) }

                Static_Bottom(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .fillMaxHeight(.6f)
                            .background(newBlue)
                            .noRippleClickable {
                                constants.PostProperty_ViewModel.set_Post_Form_Flow(0)
                                constants.PostProperty_ViewModel.clear_Forms()
                                constants.PostProperty_ViewModel.first_Form_selected_PP(-1)
                                constants.PostProperty_ViewModel.select_Land_Cat_Id(-1)
                                constants.PostProperty_ViewModel.LandSubType_Selected_Click(-1)

                                constants.PostProperty_ViewModel.select_Land_Cat_Id(
                                    -1
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

                                constants.PostProperty_ViewModel.clear_Selected_Fields_Form4()

                                constants.PostProperty_ViewModel.check_Price_Negotiation(false)
                                constants.PostProperty_ViewModel.put_budget_Price_PF5("")

                                constants.PostProperty_ViewModel.clear_Media()
                                constants.PostProperty_ViewModel.goToPPFormPage(0, 7)

                                get_Form_Preview_API_CALL { result ->
                                    when (result) {
                                        0 -> {
                                            state.value = result
                                        }

                                        1 -> {}
                                        2 -> {}
                                        3 -> {
                                            val server_Data =
                                                constants.PostProperty_ViewModel.get_Preview_Data()

                                            server_Data?.let { data ->
                                                constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                                    data.toSelectedOptionsForm4()
                                                }
                                            }

                                            AppPreferences.save_Post_Id(
                                                server_Data?.user_post_id ?: 0
                                            )

                                            constants.PostProperty_ViewModel.first_Form_selected_PP(
                                                server_Data?.land_type_id ?: -1
                                            )
                                            constants.PostProperty_ViewModel.set_country3(
                                                server_Data?.country ?: ""
                                            )
                                            constants.PostProperty_ViewModel.set_state3(
                                                server_Data?.state ?: ""
                                            )
                                            constants.PostProperty_ViewModel.set_city3(
                                                server_Data?.city ?: ""
                                            )
                                            constants.PostProperty_ViewModel.set__selectedLocality3(
                                                server_Data?.address ?: ""
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

                                            constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value =
                                                constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value.copy(
                                                    first = server_Data?.land_type_id ?: 0,
                                                    second = server_Data?.land_categorie_id ?: 0
                                                )

                                            constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value =
                                                Pair(
                                                    (server_Data?.land_type_id ?: 0),
                                                    server_Data?.land_categorie_id ?: 0
                                                )

                                            if (!server_Data?.pincode.isNullOrEmpty()) {
                                                constants.PostProperty_ViewModel.set_pincode3(
                                                    server_Data?.pincode ?: ""
                                                )
                                            }
                                            if (!server_Data?.country.isNullOrEmpty()) {
                                                constants.PostProperty_ViewModel.set_country3(
                                                    server_Data?.country ?: ""
                                                )
                                            }
                                            if (!server_Data?.state.isNullOrEmpty()) {
                                                constants.PostProperty_ViewModel.set_state3(
                                                    server_Data?.state ?: ""
                                                )
                                            }
                                            if (!server_Data?.city.isNullOrEmpty()) {
                                                constants.PostProperty_ViewModel.set_city3(
                                                    server_Data?.city ?: ""
                                                )
                                            }
                                            if (!server_Data?.address.isNullOrEmpty()) {
                                                constants.PostProperty_ViewModel.set__selectedLocality3(
                                                    server_Data?.address ?: ""
                                                )
                                            }

                                            if (server_Data?.latitude?.isNotEmpty() == true && server_Data?.longitude?.isNotEmpty() == true) {
                                                constants.PostProperty_ViewModel.add_Pinned_Lat_Long(
                                                    LatLng(
                                                        server_Data?.latitude?.toDouble() ?: 0.0,
                                                        server_Data?.longitude?.toDouble() ?: 0.0
                                                    )
                                                )
                                            }

                                            constants.PostProperty_ViewModel.add_pp3_Data(
                                                PP3_API_DC(
                                                    pincode = server_Data?.pincode ?: "",
                                                    country = server_Data?.country ?: "",
                                                    state = server_Data?.state ?: "",
                                                    city = server_Data?.city ?: "",
                                                    locality = server_Data?.locality ?: ""
                                                )
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

                                            if (!server_Data?.images.isNullOrEmpty()) {
                                                val imageMediaList =
                                                    server_Data.images.map { imageUri ->

                                                    }

                                            } else if (!server_Data?.video.isNullOrEmpty()) {

                                            }

                                            constants.PostProperty_ViewModel.select_Land_Type(
                                                server_Data?.land_type_id ?: 0
                                            )

                                            constants.Profile_ViewModel.set_From_Repost(1)
                                            constants.PostProperty_ViewModel.set_Post_Form_Flow(2)
                                            constants.PostProperty_ViewModel.goToPPFormPage(0, 7)

                                            navController.navigate(ProfileScreenFlow.Post_Property_Forms.route)
                                        }
                                    }
                                }

                            }
                        , contentAlignment = Alignment.Center
                    ){
                        if (state.value == 0){
                            CircularProgressIndicator(modifier = Modifier.size(24.dp) , color = newWhite)
                        }
                        else {
                            Text("Continue to Repost" , color = newWhite)
                        }
                    }
                }
            }
        }
    }

    Mark_As_Sold_Flow(mark_As_Sold, view_Details_Data.value?.user_post_id ?: 0, navController)

    if (notInterested_Btm.value)
    {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

        ModalBottomSheet(
            onDismissRequest = {

                notInterested_Btm.value = false
                constants.Profile_ViewModel.toggle_NotInterested_Options(
                    -1
                )
            },
            sheetState = sheetState
            , containerColor = newWhite
            , sheetGesturesEnabled = false
            , modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Top
            ) {
                val user_Manual_report = remember { mutableStateOf(false) }
                val user_Manual_report_String = remember { mutableStateOf("") }

                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Text(
                        text = "Why are you not interested?",
                        color = newBlack,
                        fontSize = constants.textUnit(24),
                        fontFamily = constants.fontFamily(0),
                        modifier = Modifier.align(Alignment.Start)
                    )

                    notInterestedOptions.value.forEachIndexed { index, profileReportOptionsDc ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
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
                                        constants.Profile_ViewModel.toggle_NotInterested_Options(
                                            profileReportOptionsDc.id
                                        )
                                    } else {
                                        constants.Profile_ViewModel.toggle_NotInterested_Options(
                                            profileReportOptionsDc.id
                                        )
                                        user_Manual_report.value = true
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
                        ) {
                            BasicTextField(
                                value = user_Manual_report_String.value,
                                onValueChange = { user_Manual_report_String.value = it },
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxWidth(),
                                singleLine = true,
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

                Spacer(modifier = Modifier.padding(8.dp))

                Static_Bottom(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(92.dp),
                    content = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(.9f)
                                    .fillMaxHeight(.7f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Brush.verticalGradient(newPurpleGradient))
                                    .border(
                                        1.dp,
                                        Brush.linearGradient(newPurpleGradientBorder),
                                        RoundedCornerShape(8.dp)
                                    )
                                    .noRippleClickable {
                                        if (network.value == NetworkStatus.Online) {
                                            if (view_Details_Data.value?.post_property?.is_report != 1) {
                                                if (user_Manual_report_String.value.isEmpty()) {
                                                    user_Manual_report_String.value =
                                                        constants.Profile_ViewModel.getSelected_NotInterested_OptionDescription()
                                                            ?: ""
                                                }

                                                constants.API_Vm.notInterested(
                                                    user_id = AppPreferences.getUserId(),
                                                    user_post_id = view_Details_Data.value?.user_post_id
                                                        ?: 0,
                                                    land_type_id = view_Details_Data.value?.post_property?.land_type_id
                                                        ?: 0,
                                                    land_categorie_id = view_Details_Data.value?.post_property?.land_categorie_id
                                                        ?: 0,
                                                    statement = user_Manual_report_String.value
                                                )
                                                { result_Handling ->
                                                    when (result_Handling) {
                                                        is API_Result_Handling.Loading -> {

                                                        }

                                                        is API_Result_Handling.Error -> {

                                                            toast("OOPs! Something went wrong , Try again later")
                                                        }

                                                        is API_Result_Handling.NoData -> {

                                                        }

                                                        is API_Result_Handling.Success -> {
                                                            notInterested_Btm.value = false
                                                            constants.Profile_ViewModel.toggle_NotInterested_Options(
                                                                -1
                                                            )

                                                            constants.Reels_ViewModel.deleteVideoById_Profile_Post_Reels(
                                                                view_Details_Data.value?.user_post_id
                                                                    ?: 0
                                                            )
                                                            GlobalSnackbar.show("Okay! We’ll hide this property.")
                                                        }

                                                        is API_Result_Handling.Deactivated -> {

                                                        }
                                                    }
                                                }

                                            } else {

                                                toast("Post Already Set to Not Interested")

                                                notInterested_Btm.value = false

                                            }
                                        } else {
                                            toast(constants.activity.getString(R.string.no_Internet))
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Submit Report",
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

    if (report_BS.value){

        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )

        ModalBottomSheet(
            onDismissRequest = {
                constants.Profile_ViewModel.toggle_ReportSucces_True()

                report_BS.value = false
                constants.Profile_ViewModel.toggle_ProfileReport_Options(
                    -1
                )
            },
            sheetState = sheetState
            , containerColor = newWhite
            , sheetGesturesEnabled = false
        )
        {
            Column (
                modifier = Modifier
                , verticalArrangement = Arrangement.Top
            ){
                val user_Manual_report = remember { mutableStateOf(false) }
                val user_Manual_report_String = remember { mutableStateOf("") }

                AnimatedContent (
                    targetState = report_success
                )
                {
                        targetState ->

                    if (targetState.value) {
                        Column(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(horizontal = 16.dp)
                            , verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        )
                        {
                            Text(
                                text = "Why are you reporting ?",
                                color = newBlack,
                                fontSize = constants.textUnit(24),
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
                                            } else {
                                                constants.Profile_ViewModel.toggle_ProfileReport_Options(
                                                    profileReportOptionsDc.id
                                                )
                                                user_Manual_report.value = true
                                            }
                                        }
                                    )
                                }
                            }

                            AnimatedVisibility(
                                user_Manual_report.value,
                                enter = slideInHorizontally(tween(900)) { it }
                            ) {
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
                                        onValueChange = { user_Manual_report_String.value = it },
                                        modifier = Modifier
                                            .padding(12.dp)
                                            .fillMaxWidth(),
                                        singleLine = true,
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
                    }

                }

                Spacer(modifier = Modifier.padding(8.dp))

                if (report_success.value){
                    Static_Bottom(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(92.dp)
                        , content = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                , contentAlignment = Alignment.Center
                            )
                            {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(.9f)
                                        .fillMaxHeight(.7f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(newBlue)
                                        .noRippleClickable {
                                            if (network.value == NetworkStatus.Online) {
                                                if (view_Details_Data.value?.post_property?.is_report ?: 0 != 1) {

                                                    if (user_Manual_report_String.value.isEmpty()) {
                                                        user_Manual_report_String.value =
                                                            constants.Profile_ViewModel.getSelectedProfileReportOptionDescription()
                                                                ?: ""
                                                    }
                                                    constants.API_Vm.put_Report_All(
                                                        user_id = AppPreferences.getUserId(),
                                                        user_post_id = (view_Details_Data.value?.user_post_id
                                                            ?: 0).toString(),

                                                        receiver_id = (view_Details_Data.value?.user_id
                                                            ?: 0).toString(),
                                                        comment_id = "",
                                                        report_sentence_id = (constants.Profile_ViewModel.getSelectedProfileReportOptionId()
                                                            ?.plus(1)) ?: 0,
                                                        report_sentence = user_Manual_report_String.value,
                                                        status = 2,
                                                    )
                                                    { apiResultHandling ->

                                                        when (apiResultHandling) {
                                                            is API_Result_Handling.Loading -> {

                                                            }

                                                            is API_Result_Handling.Error -> {

                                                            }

                                                            is API_Result_Handling.Success -> {

                                                                constants.Reels_ViewModel.toggleLike_Report(
                                                                    view_Details_Data.value?.user_post_id
                                                                        ?: 0
                                                                )

                                                                constants.Reels_ViewModel.toggle_ReportViewDetails(
                                                                    view_Details_Data.value?.user_post_id
                                                                        ?: 0
                                                                )

                                                            }

                                                            is API_Result_Handling.NoData -> {

                                                            }

                                                            is API_Result_Handling.Deactivated -> {

                                                            }
                                                        }
                                                    }
                                                }
                                                else {
                                                    GlobalSnackbar.show(" Post Already Reported")
                                                }

                                            } else {
                                                toast(activity.getString(R.string.no_Internet))
                                            }

                                        }
                                    , contentAlignment = Alignment.Center
                                ){
                                    Text(
                                        text = "Submit Report",
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

    if (activateRenew.value) {
        ModalBottomSheet(
            onDismissRequest = {
                activateRentedout.value = false

                AppPreferences.save_Post_Id(0)
            },
            containerColor = Color.White
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                , verticalArrangement = Arrangement.spacedBy(8.dp)
                , horizontalAlignment = Alignment.CenterHorizontally
            ){

                com.toletspot.houseforrent.CommonText(
                    "Renew Property Listing",
                    Color(0xff575757),
                    18,
                    1, modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 16.dp)
                )

                com.toletspot.houseforrent.CommonText(
                    "Renew keeps your property listing active for more days. Your post is about to expire, and renewing will refresh its validity so more people can view it without creating a new listing.",
                    Color(0xff575757),
                    14,
                    3, modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 16.dp)
                )

                var isLoading by remember { mutableStateOf(false) }

                Static_Bottom(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .fillMaxHeight(.6f)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Brush.verticalGradient(newPurpleGradient))
                            .border(
                                1.dp,
                                Brush.linearGradient(newPurpleGradientBorder),
                                RoundedCornerShape(6.dp)
                            )
                            .noRippleClickable {

                                constants.API_Vm.activate_RentedOut(
                                    user_id = AppPreferences.getUserId(),
                                    user_post_id = view_Details_Data.value?.user_post_id ?: 0,
                                )
                                { aPI_Result_Handling ->
                                    when (aPI_Result_Handling) {
                                        is API_Result_Handling.NoData -> {}
                                        is API_Result_Handling.Error -> {

                                            GlobalSnackbar.show("Something went wrong")

                                            activateRentedout.value = false
                                        }

                                        is API_Result_Handling.Deactivated -> {

                                        }

                                        is API_Result_Handling.Loading -> {

                                        }

                                        is API_Result_Handling.Success -> {

                                            GlobalSnackbar.show("Property Activated Successfully ")

                                            constants.Reels_ViewModel.setNewTimeStampOnRenew(
                                                view_Details_Data.value?.user_post_id ?: 0
                                            )
                                            constants.Reels_ViewModel.deleteVideoById_Profile_Post_Reels(
                                                view_Details_Data.value?.user_post_id ?: 0
                                            )

                                            activateRenew.value = false

                                            navController.navigateUp()
                                        }
                                    }
                                }
                            }
                        , contentAlignment = Alignment.Center
                    ){
                        if (isLoading){
                            CircularProgressIndicator(modifier = Modifier.size(24.dp) , color = newWhite)
                        }
                        else {
                            Text("Renew Listing" , color = newWhite)
                        }
                    }
                }
            }
        }
    }

    if (activateRentedout.value) {
        ModalBottomSheet(
            onDismissRequest = {
                activateRentedout.value = false

                AppPreferences.save_Post_Id(0)
            },
            containerColor = Color.White
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                , verticalArrangement = Arrangement.spacedBy(8.dp)
                , horizontalAlignment = Alignment.CenterHorizontally
            ){

                com.toletspot.houseforrent.CommonText(
                    "Activate Listing",
                    Color(0xff575757),
                    18,
                    1, modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 16.dp)
                )

                com.toletspot.houseforrent.CommonText(
                    "When you tap Activate, the same post will be reactivated without making any changes. Everything will remain exactly as it is — we’re just switching it back to active status.",
                    Color(0xff575757),
                    14,
                    3, modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 16.dp)
                )

                var isLoading by remember { mutableStateOf(false) }

                Static_Bottom(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .fillMaxHeight(.6f)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Brush.verticalGradient(newPurpleGradient))
                            .border(
                                1.dp,
                                Brush.linearGradient(newPurpleGradientBorder),
                                RoundedCornerShape(6.dp)
                            )
                            .noRippleClickable {

                                constants.API_Vm.put_Sold_Unsold_Property(
                                    user_id = AppPreferences.getUserId(),
                                    user_post_id = view_Details_Data.value?.user_post_id ?: 0,
                                    status = 2,
                                )
                                { aPI_Result_Handling ->
                                    when (aPI_Result_Handling) {
                                        is API_Result_Handling.NoData -> {}
                                        is API_Result_Handling.Error -> {
                                            isLoading = false
                                        }

                                        is API_Result_Handling.Deactivated -> {

                                        }

                                        is API_Result_Handling.Loading -> {
                                            isLoading = true
                                        }

                                        is API_Result_Handling.Success -> {
                                            isLoading = false
                                            GlobalSnackbar.show("Property Activated Successfully ")

                                            constants.Reels_ViewModel.deleteVideoById_Profile_Post_Reels(
                                                view_Details_Data.value?.user_post_id ?: 0
                                            )

                                            activateRentedout.value = false

                                            navController.navigateUp()
                                        }
                                    }
                                }

                            }
                        , contentAlignment = Alignment.Center
                    ){
                        if (isLoading){
                            CircularProgressIndicator(modifier = Modifier.size(24.dp) , color = newWhite)
                        }
                        else {
                            Text("Activate Listing" , color = newWhite)
                        }
                    }
                }
            }
        }
    }
}

data class ViewDetailsMedia(
    val isVideo: Boolean = false,
    val uploadedUrl: String? = null,
    val localUri: Uri? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentoViewDetailsFirstPart(
    mediaList: SnapshotStateList<ViewDetailsMedia>,
    view_Details_Data: State<Get_Reels_Data?>,
    mark_As_Sold: MutableState<Boolean>,
    report_BS: MutableState<Boolean>,
    repost_Btm: MutableState<Boolean>,
    onFullView: MutableState<Boolean>,
    notInterested_Btm: MutableState<Boolean>,
    navController: NavHostController,
    activateRentedout: MutableState<Boolean>,
    activateRenew: MutableState<Boolean>
) {

    var network = rememberNetworkStatus()

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val pagerState = rememberPagerState(pageCount = { maxOf(1, mediaList.size) })

    var expanded = remember { mutableStateOf(false) }

    val viewIdentify = constants.PostProperty_ViewModel.viewDetailsFlow.collectAsState()

    var player by remember { mutableStateOf<ExoPlayer?>(null) }
    var currentPage by remember { mutableStateOf(0) }
    var isPlaying by remember { mutableStateOf(false) }

    LaunchedEffect(pagerState) {

        if (mediaList.isEmpty()) return@LaunchedEffect

        snapshotFlow { pagerState.settledPage }.collect { page ->
            currentPage = page
            val media = mediaList[page]

            if (!media.isVideo) {
                player?.pause()
                return@collect
            }

            player?.release()
            player = null

            player = ExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(media.uploadedUrl ?: ""))
                prepare()
                playWhenReady = true
            }

            player?.addListener(object : Player.Listener {
                override fun onPlayerError(error: PlaybackException) {
                    error.printStackTrace()
                    player?.seekTo(0)
                    player?.prepare()
                    player?.playWhenReady = true
                }

                override fun onIsPlayingChanged(nowPlaying: Boolean) {
                    isPlaying = nowPlaying
                }
            })
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            player?.release()
            player = null
        }
    }

    var postflow = constants.PostProperty_ViewModel.postFlow.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
        , verticalArrangement = Arrangement.Center
        , horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Box() {

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxWidth()
                    .height(400.dp)
            )
            { page ->

                if (mediaList.isEmpty()) {

                    val type = if (view_Details_Data.value?.user_id == AppPreferences.getUserId()) 1 else 2
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(newLightBlue)
                        , contentAlignment = Alignment.Center
                    ) {
                        PhotoRequestAssistant(
                            type,
                            Color.Black,
                            view_Details_Data.value?.user_post_id ?: 0,
                            navController,
                            modifier = Modifier.size(200.dp),
                            view_Details_Data.value?.post_interest ?: 0,
                        )

                    }
                }
                else {
                    val media = mediaList[page]

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (media.isVideo && page == currentPage) {
                            PlayerSurface(
                                player = player,
                                modifier = Modifier.fillMaxSize()
                            )

                            Box(
                                Modifier
                                    .matchParentSize()
                                    .clickable {
                                        player?.playWhenReady = !(player?.isPlaying ?: false)
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
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.Black.copy(.8f),
                                Color.Transparent
                            )
                        )
                    )
            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = if (forTab()) 16.dp else rememberNotchHeightDp().value
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    )
                    {
                        SubcomposeAsyncImage(
                            model = R.drawable.left_arrow,
                            " ",
                            colorFilter = ColorFilter.tint(Color.White),
                            modifier = Modifier
                                .size(24.dp)
                                .noRippleClickable {
                                    constants.Common_H_ViewModel.toggleshowBABars(true)
                                    constants.Common_H_ViewModel.toggleshowTABars(true)
                                    constants.PostProperty_ViewModel.setViewDetailsFlow(
                                        ViewDetailsFlow.NONE
                                    )
                                    navController.navigateUp()
                                }
                        )
                    }

                        Row(
                            modifier = Modifier.wrapContentWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        )
                        {
                            SubcomposeAsyncImage(
                                model = if ((view_Details_Data.value?.is_saved ?: 0) == 1)
                                    R.drawable.saveedrento
                                else
                                    R.drawable.savereelsrento,
                                "",
                                modifier = Modifier
                                    .size(24.dp)
                                    .noRippleClickable {

                                        if (network.value == NetworkStatus.Online) {
                                            constants.API_Vm.put_save_UnSave_Property(
                                                user_id = AppPreferences.getUserId(),
                                                user_post_id = view_Details_Data.value?.user_post_id
                                                    ?: 0,
                                                status = if (((view_Details_Data.value?.is_saved
                                                        ?: "") == 1)
                                                ) 2 else 1,
                                            )
                                            { apiResultHandling ->
                                                when (apiResultHandling) {
                                                    is API_Result_Handling.Error -> {
                                                        toast("Something Went Wrong, Try Again Later")
                                                    }

                                                    is API_Result_Handling.Success -> {
                                                        constants.Reels_ViewModel.toggleSave_Reels(
                                                            view_Details_Data.value?.user_post_id
                                                                ?: 0
                                                        )
                                                        constants.Reels_ViewModel.update_View_Property_Detail { current ->
                                                            current.copy(
                                                                is_saved = if (current.is_saved == 1) 0 else 1
                                                            )
                                                        }
                                                    }

                                                    else -> {}
                                                }
                                            }
                                        } else {
                                            toast(activity.getString(R.string.no_Internet))
                                        }
                                    }
                            )

                            Spacer(modifier = Modifier.padding(8.dp))

                            val dataList = when {

                                viewIdentify.value == ViewDetailsFlow.RENTOUT -> {
                                    listOf(
                                        Common_DropDown2Options_DC(

                                            title = "Edit Property"
                                        ),
                                        Common_DropDown2Options_DC(

                                            title = "Delete Property"
                                        ),
                                        Common_DropDown2Options_DC(

                                            title = "Activate"
                                        ),
                                    )
                                }
                                viewIdentify.value == ViewDetailsFlow.OTHERS -> {

                                        listOf(
                                            Common_DropDown2Options_DC(

                                                title = "Share"
                                            ),
                                            Common_DropDown2Options_DC(

                                                title = "Report"
                                            ),

                                        )

                                }

                                viewIdentify.value == ViewDetailsFlow.EXPIRY -> {
                                    listOf(
                                        Common_DropDown2Options_DC(

                                            title = "Repost Property"
                                        ),
                                        Common_DropDown2Options_DC(

                                            title = "Delete Property"
                                        ),
                                    )
                                }

                                !isWithinLast5DaysOfValidity(view_Details_Data.value?.post_property?.created_at ?:"") -> {

                                    listOf(
                                        Common_DropDown2Options_DC(

                                            title = "Delete Property"
                                        ),
                                        Common_DropDown2Options_DC(

                                            title = "Mark as RentedOut"
                                        ),
                                        Common_DropDown2Options_DC(

                                            title = "Edit Property"
                                        ),
                                        Common_DropDown2Options_DC(

                                            title = "Renew Property"
                                        ),

                                        )
                                }

                                view_Details_Data.value?.user_id == AppPreferences.getUserId() -> {
                                    listOf(
                                        Common_DropDown2Options_DC(

                                            title = "Share"
                                        ),
                                        Common_DropDown2Options_DC(

                                            title = "Edit Property"
                                        ),

                                        Common_DropDown2Options_DC(

                                            title = "Mark as RentedOut"
                                        ),
                                        Common_DropDown2Options_DC(

                                            title = "Delete Property"
                                        ),
                                    )
                                }

                                else -> {
                                    listOf(
                                        Common_DropDown2Options_DC(

                                            title = "Share"
                                        ),
                                        Common_DropDown2Options_DC(

                                            title = "Report"
                                        ),

                                        )
                                }
                            }

                            ExposedDropdownMenuBox(
                                expanded = expanded.value,
                                onExpandedChange = { expanded.value = !expanded.value }
                            )
                            {
                                Image(
                                    painter = painterResource(R.drawable.more_vert),
                                    contentDescription = "More options",
                                    colorFilter = ColorFilter.tint(Color.White),
                                    modifier = Modifier
                                        .size(24.dp)
                                        .menuAnchor()
                                        .noRippleClickable { expanded.value = true }
                                )

                                ExposedDropdownMenu(
                                    expanded = expanded.value,
                                    onDismissRequest = { expanded.value = false },
                                    containerColor = newWhite,
                                    modifier = Modifier.width(IntrinsicSize.Min)
                                )
                                {
                                    dataList.forEach { (iconRes, title) ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .noRippleClickable {
                                                    expanded.value = false
                                                    if (network.value == NetworkStatus.Online) {
                                                        when {
                                                            title == "Delete Property" -> {
                                                                constants.Enquiry_ViewModel.deleteViewDetailsProperty(
                                                                    view_Details_Data.value?.user_post_id
                                                                        ?: 0, true
                                                                )

                                                            }

                                                            title == "Not Interested" -> {
                                                                notInterested_Btm.value = true
                                                            }

                                                            title == "Edit Property" -> {
                                                                constants.PostProperty_ViewModel.setPostFlow(
                                                                    PostFlow.EDIT
                                                                )
                                                                navController.navigate(
                                                                    ProfileScreenFlow.Edit_Property_Option.route
                                                                )
                                                            }

                                                            title == "Mark as RentedOut" -> {

                                                                mark_As_Sold.value = true
                                                            }

                                                            title == "Renew Property" -> {

                                                                activateRenew.value = true
                                                            }

                                                            title == "Repost Property" -> {
                                                                repost_Btm.value = true
                                                            }

                                                            title == "Report" -> {
                                                                if ((view_Details_Data.value?.post_property?.is_report ?: 0) != 1
                                                                ) {
                                                                    report_BS.value = true
                                                                } else {
                                                                    toast("Property Already Reported")
                                                                }
                                                            }

                                                            title == "Activate" -> {
                                                                activateRentedout.value = true
                                                            }

                                                            title == "Share" -> {
                                                                constants.DefaultShare(
                                                                    "https://toletspot.com/property/${view_Details_Data.value?.user_post_id ?: 0}",
                                                                    1
                                                                )
                                                            }
                                                        }
                                                    } else {
                                                        toast(
                                                            activity.getString(
                                                                R.string.no_Internet
                                                            )
                                                        )
                                                    }
                                                }
                                                .padding(
                                                    horizontal = 12.dp,
                                                    vertical = 10.dp
                                                ),
                                            verticalAlignment = Alignment.CenterVertically
                                        )
                                        {

                                            Text(
                                                text = title,
                                                color = newBlack,
                                                fontSize = constants.textUnit(14),
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                modifier = Modifier.weight(1f, fill = false)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                }
            }

            if (mediaList.isNotEmpty()) {
                SubcomposeAsyncImage(
                    model = R.drawable.propertyviewfullscreenvideo,
                    "",
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .size(24.dp)
                        .align(Alignment.BottomEnd)
                        .noRippleClickable {
                            onFullView.value = true
                        }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            repeat(mediaList.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(if (pagerState.currentPage == index) newBlue else Color.Gray)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

    }

}

@Composable
fun VPS_SecondPart(
    profile_Mode: State<Int?>,
    navController: NavHostController,
    view_Details_Data: State<Get_Reels_Data?>
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(newWhite)
            .padding(bottom = 16.dp)
        , contentAlignment = Alignment.Center
    ){
        Column (
            verticalArrangement = Arrangement.Center
        )
        {
            ListItem(
                headlineContent = {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                            , contentAlignment = Alignment.Center
                        )
                        {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(vertical = 8.dp)
                                    .wrapContentSize()
                                    .background(newLightBlue)
                                    .padding(horizontal = 8.dp)
                                , contentAlignment = Alignment.Center
                            )
                            {
                                    Text(
                                        view_Details_Data.value?.post_property?.landCategoryText
                                            ?: "",
                                        color = newBlue,
                                        fontSize = constants.textUnit(12),
                                        fontFamily = constants.fontFamily(0)

                                    )

                            }

                            if (view_Details_Data.value?.post_property?.is_sold == 1) {
                                Image(
                                    painterResource(R.drawable.soldoutidentifier), "",
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                        .padding(start = 16.dp)
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                            , horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                view_Details_Data.value?.post_property?.property_name ?: "",
                                color = newBlack,
                                fontSize = constants.textUnit(20),
                                fontFamily = constants.fontFamily(0)
                            )

                            if (view_Details_Data.value?.post_property?.latitude?.isNotEmpty() == true && view_Details_Data.value?.post_property?.longitude?.isNotEmpty() == true && view_Details_Data.value?.post_property?.map_config ?:"" == "1") {
                                Row(
                                    modifier = Modifier.noRippleClickable {
                                        openMap(context , view_Details_Data.value?.post_property?.latitude ?:"" , view_Details_Data.value?.post_property?.longitude ?:"")
                                    },
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.viewdetailmapiconrento),
                                        "",
                                        modifier = Modifier.size(14.dp)
                                    )

                                    CommonText(
                                        "Map",
                                        newBlack,
                                        14,
                                        1
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.padding(4.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            SubcomposeAsyncImage(
                                model = R.drawable.locationpinenquiry,
                                "",
                                modifier = Modifier
                                    .size(14.dp)
                                    .align(Alignment.Top)
                            )
                            constants.spacer(4)
                            Text(
                                "${ view_Details_Data.value?.post_property?.locality ?: "" } ,${ view_Details_Data.value?.post_property?.state ?: "" },${ view_Details_Data.value?.post_property?.country ?: "" },${ view_Details_Data.value?.post_property?.pincode ?: "" }",
                                color = newBlack,
                                fontSize = constants.textUnit(12),
                                modifier = Modifier.align(Alignment.Top)
                            )
                        }

                    }
                },
                supportingContent = {
                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text(
                                "posted ${getTimeAgo(view_Details_Data.value?.post_property?.created_at ?: "")}",
                                color = Color(0xff969696),
                                fontSize = constants.textUnit(12)
                            )

                        }

                        Spacer(modifier = Modifier.padding(4.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState())
                        ) {
                            if ((view_Details_Data.value?.post_property?.rent_negotiable?: "") == "1") {
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

                                    com.toletspot.houseforrent.CommonText(
                                        "Rent Negotiable", Color(0xff238F38), 12, 1
                                    )
                                }
                            }

                            if ((view_Details_Data.value?.post_property?.pets_allowed?: "" )== "1") {
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

                                    com.toletspot.houseforrent.CommonText(
                                        "Pets Allowed", Color(0xff238F38), 12, 1
                                    )
                                }
                            }

                            if ((view_Details_Data.value?.post_property?.food_preferences?: "" )== "Vegetarian only") {
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

                                    com.toletspot.houseforrent.CommonText(
                                        "Vegetarians only", Color(0xff238F38), 12, 1
                                    )
                                }
                            }
                        }

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
                )
            )

            Spacer(modifier = Modifier.padding(8.dp))

            if (constants.PostProperty_ViewModel.get_Post_Form_Flow() != -1){

            } else {

                if (profile_Mode.value == 0) {
                }
                else
                {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        Text("Contact Person", color = newBlack, fontSize = constants.textUnit(16), fontFamily = constants.fontFamily(0))

                        Row(
                            modifier = Modifier
                                .wrapContentWidth()
                                .noRippleClickable {

                                    constants.Profile_ViewModel.add_Selected_User_Name(
                                        view_Details_Data.value?.username ?: "Unknown"
                                    )

                                    constants.Profile_ViewModel.add_BF_Handler(
                                        Profile_Handle_Back(
                                            current_UsedId = AppPreferences.getUserId(),
                                            other_UserId = view_Details_Data.value?.user_id ?: 0,
                                            ff_User_Name = view_Details_Data.value?.username ?: "",
                                            ff_Fw_Count = 999,
                                            ff_Fg_Count = 999,

                                        )
                                    )

                                    constants.Profile_ViewModel.addProfile(
                                        view_Details_Data.value?.user_id ?: 0
                                    )
                                    constants.Profile_ViewModel.add_Selected_Profile_Id(
                                        id = view_Details_Data.value?.user_id ?: 0
                                    )

                                    navController.navigate(VideosScreenFlow.Other_Profile_Structure.route)
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {

                            Text(
                                "view profile",
                                color = newBlack,
                                fontSize = constants.textUnit(14),
                                fontFamily = constants.fontFamily(1)
                            )

                            Icon(
                                painter = painterResource(R.drawable.right_arrow),
                                "", modifier = Modifier.size(12.dp)
                            )

                        }
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth(0.9f)
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                width = 1.dp,
                                brush = Brush.verticalGradient(
                                    newPurpleGradientBorder
                                ),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .background(
                                Color(0xffFFFFFF)
                            )

                            .padding(horizontal = 8.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        Row(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(horizontal = 8.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        )
                        {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(Color.Gray), contentAlignment = Alignment.Center
                            )
                            {
                                SubcomposeAsyncImage(
                                    model = view_Details_Data.value?.profile_image ?: "",
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
                                                .background(newLightBlue)

                                            , contentAlignment = Alignment.Center
                                        ){
                                            Text(
                                                text = view_Details_Data.value?.username.takeIf { it?.isNotEmpty() == true }?.take(1)?.uppercase() ?: ""
                                            )
                                        }
                                    } else {
                                        SubcomposeAsyncImageContent()
                                    }
                                }
                            }

                            Text(
                                view_Details_Data.value?.username ?: "",
                                color = newBlack,
                                fontSize = constants.textUnit(16)
                                , fontFamily = constants.fontFamily(0)
                                , overflow = TextOverflow.Ellipsis
                            )
                        }

                        Row(
                            modifier = Modifier
                                .wrapContentSize()
                                .clip(RoundedCornerShape(4.dp))
                                .background(Brush.verticalGradient(newPurpleGradient))
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                                .noRippleClickable {
                                    openDialer(context, view_Details_Data.value?.phone_num ?: "")
                                }
                            , verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        )
                        {
                            SubcomposeAsyncImage(
                                model = R.drawable.rentochatcall,
                                "", colorFilter = ColorFilter.tint(Color.White), modifier = Modifier
                                    .size(12.dp)
                            )

                            Text("Call now", color = Color.White, fontSize = constants.textUnit(12))
                        }
                    }
                }

            }

            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                "Property Overview",
                color = newBlack,
                fontSize = constants.textUnit(16),
                 fontFamily = constants.fontFamily(0),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )

            DetailsScreen(view_Details_Data)
        }
    }

}

fun openMap(context: Context, latitudeStr: String, longitudeStr: String) {
    val lat = latitudeStr.toDoubleOrNull()
    val lng = longitudeStr.toDoubleOrNull()

    if (lat == null || lng == null) {
        GlobalSnackbar.show("Invalid location")
        return
    }

    val gmmIntentUri = Uri.parse("geo:$lat,$lng?q=$lat,$lng")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")

    try {
        context.startActivity(mapIntent)
    } catch (e: Exception) {

        val browserIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.google.com/maps/search/?api=1&query=$lat,$lng")
        )
        context.startActivity(browserIntent)
    }
}

@Composable
fun DetailsScreen(view_Details_Data: State<Get_Reels_Data?>) {
    val reelsData = view_Details_Data.value ?: return
    val property = reelsData.post_property

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Property_Overview_Box_Structure(property)

        Spacer(modifier = Modifier.height(16.dp))

        Property_Overview_FlowRow_Structure(property)

        Spacer(modifier = Modifier.height(16.dp))

    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn( ExperimentalMaterial3Api::class)
@Composable
fun VPS_FullViewold(
    onFullView: MutableState<Boolean>,
    videoUri: String,
    imagesList: List<Image>,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()
    val notchPadding = rememberNotchHeightDp()
    val pagerState = rememberPagerState(pageCount = { if (imagesList.isNotEmpty()) imagesList.size else 1 })
    val listState = rememberLazyListState()

    val player = remember(videoUri) {
        ExoPlayer.Builder(context).build().apply {
            if (videoUri.isNotEmpty()) {
                setMediaItem(MediaItem.fromUri(videoUri))
                prepare()
                playWhenReady = true
            }
        }
    }

    val isPlaying = remember { mutableStateOf(player.isPlaying) }
    val isBuffering = remember { mutableStateOf(true) }

    DisposableEffect(Unit) {
        onDispose {
            player.release()
        }
    }

    DisposableEffect(player, lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> player.pause()
                Lifecycle.Event.ON_RESUME -> if (videoUri.isNotEmpty()) player.play()
                Lifecycle.Event.ON_DESTROY -> player.release()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                isBuffering.value = state != Player.STATE_READY
                if (state == Player.STATE_ENDED) {
                    player.seekTo(0)
                    player.playWhenReady = true
                }
            }

            override fun onIsPlayingChanged(playing: Boolean) {
                isPlaying.value = playing
            }
        }
        player.addListener(listener)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            player.removeListener(listener)
            player.release()
        }
    }

    Column {

        Box(
            modifier = Modifier
                .weight(7.5f)
                .fillMaxWidth()
                .background(Color.Black)
                .padding(top = if (forTab()) 16.dp else notchPadding.value),
            contentAlignment = Alignment.Center
        ) {

            if (imagesList.isEmpty()) {

                Box(modifier = Modifier.fillMaxSize()) {
                    PlayerSurface(
                        player = player,
                        modifier = Modifier
                            .fillMaxSize()
                            .noRippleClickable {
                                if (player.isPlaying) player.pause() else player.play()
                            }
                    )

                    if (isBuffering.value) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.4f)),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color.White)
                        }
                    }

                    if (!isPlaying.value && videoUri.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color.Black.copy(0.5f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.play_arrow),
                                contentDescription = "Play",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .height(120.dp)
                            .fillMaxWidth()
                            .background(newWhite),
                        contentAlignment = Alignment.Center
                    )
                    {

                        LazyRow(state = listState) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp)
                                        .width(94.dp)
                                        .height(94.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(newWhite)
                                        .border(
                                            1.dp,
                                            newBlue,
                                            RoundedCornerShape(8.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    SubcomposeAsyncImage(
                                        model = R.drawable.emptypostsrento,
                                        contentDescription = "Thumbnail ",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                        }
                    }
                }
            } else {

                HorizontalPager(state = pagerState) { page ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        SubcomposeAsyncImage(
                            model = imagesList[page],
                            contentDescription = "Image $page",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            SubcomposeAsyncImage(
                model = R.drawable.close_common,
                contentDescription = "",
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .align(Alignment.TopEnd)
                    .size(24.dp)
                    .noRippleClickable {
                        onFullView.value = false
                    }
            )
        }

        if (imagesList.isNotEmpty()) {

            Box(
                modifier = Modifier
                    .weight(.5f)
                    .fillMaxWidth()
                    .background(newWhite),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(imagesList.size) { index ->
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(if (pagerState.currentPage == index) newBlue else newGray)
                        )
                        Spacer(modifier = Modifier.padding(2.dp))
                    }
                }
            }

            Box(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth()
                    .background(newWhite),
                contentAlignment = Alignment.Center
            )
            {
                scope.launch {
                    listState.animateScrollToItem(pagerState.currentPage)
                }
                LazyRow(state = listState) {
                    items(imagesList.size) { itemIndex ->
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .width(94.dp)
                                .height(94.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(newBlack)
                                .border(
                                    1.dp,
                                    if (pagerState.currentPage == itemIndex) newBlue else Color.Transparent,
                                    RoundedCornerShape(8.dp)
                                )
                                .noRippleClickable {
                                    scope.launch {
                                        pagerState.animateScrollToPage(itemIndex)
                                        listState.animateScrollToItem(itemIndex)
                                    }
                                }
                            , contentAlignment = Alignment.Center
                        ) {
                            SubcomposeAsyncImage(
                                model = imagesList[itemIndex],
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
                                            .background(newLightBlue)

                                        , contentAlignment = Alignment.Center
                                    ){
                                        Image(painterResource(R.drawable.emptypostsrento) ,"")
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

    BackHandler {
        ClickHelper.getInstance().clickOnce {
            if(ClickGuard.canClick()) {
                onFullView.value = false
            }
        }
    }
}

sealed class ViewMedia(open val title: String) {
    data class ImageItem(val url: String, override val title: String) : ViewMedia(title)
    data class VideoItem(val url: String, override val title: String) : ViewMedia(title)
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun VPS_FullView(
    onFullView: MutableState<Boolean>,
    mediaList: List<ViewMedia>,
    initialIndex: Int = 0
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState(initialPage = initialIndex, pageCount = { mediaList.size })
    val listState = rememberLazyListState()

    val player = remember {
        ExoPlayer.Builder(context).build()
    }

    val isPlaying = remember { mutableStateOf(false) }
    val isBuffering = remember { mutableStateOf(true) }

    var network = rememberNetworkStatus()

    DisposableEffect(player, lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> player.pause()
                Lifecycle.Event.ON_RESUME -> player.play()
                Lifecycle.Event.ON_DESTROY -> player.release()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                isBuffering.value = state != Player.STATE_READY
            }

            override fun onIsPlayingChanged(playing: Boolean) {
                isPlaying.value = playing
            }
        }
        player.addListener(listener)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            player.removeListener(listener)
            player.release()
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        val item = mediaList[pagerState.currentPage]
        if (item is ViewMedia.VideoItem) {
            player.setMediaItem(MediaItem.fromUri(item.url))
            player.prepare()
            player.playWhenReady = true
        } else {
            player.pause()
        }

        scope.launch {
            listState.animateScrollToItem(pagerState.currentPage)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Box(
            modifier = Modifier
                .padding(top = rememberNotchHeightDp().value)
                .weight(7.5f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (network.value == NetworkStatus.Online) {

                HorizontalPager(state = pagerState) { page ->
                    val item = mediaList[page]

                    if (item is ViewMedia.VideoItem) {

                        Box(Modifier.fillMaxSize()) {
                            PlayerSurface(
                                player = player,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable {
                                        if (player.isPlaying) player.pause() else player.play()
                                    }
                            )

                            if (isBuffering.value) {
                                Box(
                                    Modifier
                                        .fillMaxSize()
                                        .background(Color.Black.copy(alpha = 0.4f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(color = Color.White)
                                }
                            }

                            if (!isPlaying.value) {
                                Icon(
                                    painterResource(R.drawable.play_arrow),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(52.dp)
                                        .align(Alignment.Center)
                                )
                            }
                        }

                    } else if (item is ViewMedia.ImageItem) {
                        SubcomposeAsyncImage(
                            model = item.url,
                            contentDescription = item.title,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds
                        )
                    }
                }

                val currentItem = mediaList[pagerState.currentPage]
                Text(
                    text = currentItem.title,
                    color = Color.White,
                    fontSize = constants.textUnit(20),
                    fontFamily = constants.fontFamily(0),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                )
            }
            else {
                Column(
                    modifier = Modifier

                        .fillMaxSize()
                        .background(Color.Black),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(painterResource(R.drawable.nointernerdesign), "")
                    Spacer(modifier = Modifier.padding(16.dp))
                    Text(
                        constants.activity.getString(R.string.no_Internet),
                        color = Color.White,
                        fontSize = constants.textUnit(16),
                        fontFamily = constants.fontFamily(0),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }

            Image(
                painter = painterResource(R.drawable.close_common),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(24.dp)
                    .clickable { onFullView.value = false }
            )
        }

        Column(
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
                .background(Color.White)
        ) {

            constants.spacer(4)

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                repeat(mediaList.size) { index ->
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(if (pagerState.currentPage == index) newBlue else Color.Gray)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }

            constants.spacer(8)

            LazyRow(
                state = listState,
                modifier = Modifier

                    .fillMaxWidth()
                    .background(Color.White),
                horizontalArrangement = Arrangement.Center
            )
            {
                items(mediaList.size) { index ->
                    val item = mediaList[index]

                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .width(94.dp)
                            .height(94.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.Gray)
                            .border(
                                2.dp,
                                if (pagerState.currentPage == index) Color.Blue else Color.Transparent,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                scope.launch { pagerState.animateScrollToPage(index) }
                            }
                    ) {
                        if (item is ViewMedia.VideoItem) {
                            val thumbnailBitmap = remember(item.url) { mutableStateOf<Bitmap?>(null) }

                            LaunchedEffect(item.url) {
                                if (thumbnailBitmap.value == null) {
                                    thumbnailBitmap.value = getVideoThumbnailFP(context, item.url)
                                }
                            }

                            if (thumbnailBitmap.value != null) {
                                AsyncImage(
                                    model = thumbnailBitmap.value,
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.LightGray),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                                }
                            }

                        } else if (item is ViewMedia.ImageItem) {
                            SubcomposeAsyncImage(
                                model = item.url,
                                contentDescription = "",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
        }

        BackHandler {
            onFullView.value = false
        }
    }
}

fun formatLabel(name: String): String {
    return name
        .replace("_", " ")
        .split(" ")
        .joinToString(" ") { word ->
            word.replaceFirstChar { it.uppercase() }
        }
}

fun mapPropertyFieldsold(
    property: Get_Reels_Property_Data,
    ignoreFields: Set<String> = emptySet()
): List<Pair<String, String>> {
    return Get_Reels_Property_Data::class.memberProperties
        .filterNot { it.name in ignoreFields }
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

fun mapPropertyFields2(
    property: Get_Reels_Property_Data,
    ignoreFields: Set<String> = emptySet()
): List<Pair<String, String>> {

    val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    return Get_Reels_Property_Data::class.memberProperties
        .filterNot { it.name in ignoreFields }
        .mapNotNull { prop ->
            val value = prop.get(property)

            when {
                value == null -> null

                prop.name == "availability_from" && value is String -> {
                    val timestamp = value.toLongOrNull()
                    timestamp?.let {
                        formatLabel(prop.name) to dateFormatter.format(Date(it))
                    }
                }

                value is String -> {
                    if (value.isBlank()) null
                    else formatLabel(prop.name) to value
                }

                value is Int -> {
                    formatLabel(prop.name) to value.toString()
                }

                value is Long -> {
                    formatLabel(prop.name) to value.toString()
                }

                value is List<*> -> {
                    if (value.isEmpty()) null
                    else formatLabel(prop.name) to value.joinToString(", ")
                }

                else -> {
                    formatLabel(prop.name) to value.toString()
                }
            }
        }
}

fun mapPropertyFields(
    property: Get_Reels_Property_Data,
    ignoreFields: Set<String> = emptySet()
): List<Pair<String, String>> {

    val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val result = mutableListOf<Pair<String, String>>()

    fun add(label: String, value: String?) {
        if (!value.isNullOrBlank()) {
            result.add(label to value)
        }
    }

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

    add("Floor Plan", property.bhk_type)

    add(
        "Property Type",
        property.is_this_property_for_rent_or_lease
    )

    property.carpet_area?.takeIf { it.isNotBlank() }?.let {
        add("Carpet Area", "$it ${property.carpet_area_unit}")
    }

    property.built_up_area?.takeIf { it.isNotBlank() }?.let {
        add("Built-up Area", "$it ${property.built_up_area_unit}")
    }

    property.super_built_up_area?.takeIf { it.isNotBlank() }?.let {
        add("Super Built-up Area", "$it ${property.super_built_up_area_unit}")
    }

    Get_Reels_Property_Data::class.memberProperties
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

                else -> Unit
            }
        }

    return result
}

@Composable
fun Property_Overview_Box_Structure(property: Get_Reels_Property_Data) {

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

        ,"built_up_area_unit"
        ,"carpet_area_unit"
        ,"deposit_amount_month_of_rents_type",
        "duration_of_agreement_type","facade_width_unit"
        ,"lease_negotiable","lock_in_period_type","map_config","pantry_size_unit",
        "post_type","property_area_unit","rent_negotiable"
        ,"super_built_up_area_unit" ,"images","video","status","U_ID", "address" ,

        "food_preferences"

        ,"notice_period",
        "pets_allowed",
        "preferred_tenants",
        "rent",
        "lease_amount" ,

    )
    val fields = mapPropertyFields(property, ignoreFields)

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()

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
fun Property_Overview_FlowRow_Structure(property: Get_Reels_Property_Data) {

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

        ,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        sections.forEach { section ->
            if (section.items.isNotEmpty()) {
                PropertySection(
                    title = section.title,
                    items = section.items
                )
            }
        }
    }
}

@Composable
fun PropertySection(
    title: String,
    items: List<String>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            text = title,
            color = Color.Black,
            fontSize = constants.textUnit(16),
            fontFamily = constants.fontFamily(1)
        )

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

data class Section(
    val title: String,
    val items: List<String>
)

@Composable
fun Dp.toPx(): Float {
    return with(LocalDensity.current) { this@toPx.toPx() }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleVideoPlayer22222222222222222(
    videoUri: String,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onFullView: MutableState<Boolean>,
    postid: Int = 0,
    isSave: MutableState<Boolean>,
    what_view: Int,
    imagesList: List<Image>
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var mark_As_Sold = remember { mutableStateOf(false) }

    val onHoverListener = constants.onHoverEnable.collectAsState()
    var expanded = remember { mutableStateOf(false) }

    val player = remember(videoUri) {
        ExoPlayer.Builder(context).build().apply {
            if (videoUri.isNotEmpty()) {
                setMediaItem(MediaItem.fromUri(videoUri))
                prepare()
                playWhenReady = true
            }
        }
    }

    val isPlaying = remember { mutableStateOf(player.isPlaying) }
    val isBuffering = remember { mutableStateOf(true) }

    DisposableEffect(Unit) {
        onDispose {
            player.release()
        }
    }

    DisposableEffect(player, lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> player.pause()
                Lifecycle.Event.ON_RESUME -> if (videoUri.isNotEmpty()) player.play()
                Lifecycle.Event.ON_DESTROY -> player.release()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                isBuffering.value = state != Player.STATE_READY
                if (state == Player.STATE_ENDED) {
                    player.seekTo(0)
                    player.playWhenReady = true
                }
            }

            override fun onIsPlayingChanged(playing: Boolean) {
                isPlaying.value = playing
            }
        }
        player.addListener(listener)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            player.removeListener(listener)
            player.release()
        }
    }

    var currentPosition by remember { mutableStateOf(0L) }
    var totalDuration by remember { mutableStateOf(0L) }

    LaunchedEffect(player) {
        while (true) {
            currentPosition = player.currentPosition
            totalDuration = player.duration
            delay(500)
        }
    }

    fun formatTime(ms: Long): String {
        val totalSeconds = ms / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return "%02d:%02d".format(minutes, seconds)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        if (videoUri.isNotEmpty()) {

            PlayerSurface(
                player = player,
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxSize()
                    .noRippleClickable {
                        if (player.isPlaying) player.pause()
                        else player.play()
                    }
            )

            if (isBuffering.value) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }

            if (!isPlaying.value && videoUri.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.play_arrow),
                        contentDescription = "Play",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.padding(2.dp))

        }
        else if (imagesList.isNotEmpty()){

            val animationType = SlideshowAnimation.entries.random()
            SingleSlideshow(
                imagesList = imagesList,
                animationType = animationType,
                modifier = Modifier.fillMaxSize()
            )
        }
        else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .background(Color.Black)
                , contentAlignment = Alignment.Center
            ){
                Image(painterResource(R.drawable.emptypostsrento) , "")
            }
        }

        Mark_As_Sold_Flow(mark_As_Sold, postid, navController)

        if (!onHoverListener.value){
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.Black.copy(.8f), Color.Transparent
                            )
                        )
                    )
            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = if (forTab()) 16.dp else rememberNotchHeightDp().value
                        )
                    , verticalAlignment = Alignment.CenterVertically
                    , horizontalArrangement = Arrangement.SpaceBetween
                )
                {

                    Row(
                        modifier = Modifier
                            , verticalAlignment = Alignment.CenterVertically
                        , horizontalArrangement = Arrangement.spacedBy(8.dp)
                    )
                    {
                        SubcomposeAsyncImage(
                            model = R.drawable.propertytdetailsback,
                            " ",
                            modifier = Modifier
                                .size(24.dp)
                                .noRippleClickable {
                                    player.release()
                                    navController.navigateUp()
                                }
                        )
                        if (constants.PostProperty_ViewModel.get_Post_Form_Flow() != -1 || constants.PostProperty_ViewModel.get_Post_Form_Flow() == 999){
                            Text("Preview" , fontSize = constants.textUnit(24) , fontFamily = constants.fontFamily(0) , color = Color.White)
                        }
                    }

                    if (constants.PostProperty_ViewModel.get_Post_Form_Flow() != -1 || constants.PostProperty_ViewModel.get_Post_Form_Flow() == 0){}
                    else {
                        Row(
                            modifier = Modifier
                                .wrapContentWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        )
                        {
                            SubcomposeAsyncImage(
                                model = R.drawable.propertyviewsave, "", modifier = Modifier
                                    .size(24.dp)
                                    .noRippleClickable {
                                        constants.API_Vm.put_save_UnSave_Property(
                                            user_id = AppPreferences.getUserId(),
                                            user_post_id = postid,
                                            status = if (isSave.value) 2 else 1,
                                        )
                                        { apiResultHandling ->
                                            when (apiResultHandling) {
                                                is API_Result_Handling.Error -> {

                                                }

                                                is API_Result_Handling.Deactivated -> {

                                                }

                                                is API_Result_Handling.NoData -> {

                                                }

                                                is API_Result_Handling.Loading -> {

                                                }

                                                is API_Result_Handling.Success -> {

                                                    constants.Reels_ViewModel.toggleSave_Reels(
                                                        postid
                                                    )

                                                }
                                            }
                                        }
                                    }
                            )

                            Spacer(modifier = Modifier.padding(8.dp))

                            val dataList = if (what_view == 0) listOf(
                                Common_DropDown2Options_DC(
                                    icon = R.drawable.reelsshare,
                                    title = "Share"
                                ),
                                Common_DropDown2Options_DC(
                                    icon = R.drawable.reelsreport,
                                    title = "Report"
                                )
                            )
                            else if (what_view == 2)
                                listOf(
                                    Common_DropDown2Options_DC(
                                        icon = R.drawable.repost,
                                        title = "Repost"
                                    ),
                                    Common_DropDown2Options_DC(
                                        icon = R.drawable.reelsdelete,
                                        title = "Delete Property"
                                    )
                                )
                            else
                                listOf(
                                    Common_DropDown2Options_DC(
                                        icon = R.drawable.reelsshare,
                                        title = "Share"
                                    ),
                                    Common_DropDown2Options_DC(
                                        icon = R.drawable.reelsedit,
                                        title = "Edit Property"
                                    ),
                                    Common_DropDown2Options_DC(
                                        icon = R.drawable.reelssold,
                                        title = "Mark as Sold"
                                    ),
                                    Common_DropDown2Options_DC(
                                        icon = R.drawable.reelsdelete,
                                        title = "Delete Property"
                                    ),
                                )

                            ExposedDropdownMenuBox(
                                expanded = expanded.value,
                                onExpandedChange = { expanded.value = !expanded.value }
                            )
                            {
                                Image(
                                    painter = painterResource(R.drawable.view_property_morevert),
                                    contentDescription = "More options",
                                    modifier = Modifier
                                        .size(24.dp)
                                        .menuAnchor()
                                        .noRippleClickable { expanded.value = true }
                                )

                                ExposedDropdownMenu(
                                    expanded = expanded.value,
                                    onDismissRequest = { expanded.value = false },
                                    containerColor = newWhite,
                                    modifier = Modifier
                                        .width(IntrinsicSize.Min)

                                ) {

                                    dataList.forEach { (iconRes, title) ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .noRippleClickable {
                                                    expanded.value = false
                                                    if (title == "Edit Property") {
                                                        navController.navigate(ProfileScreenFlow.Edit_Property_Option.route)
                                                    } else if (title == "Mark as Sold") {
                                                        mark_As_Sold.value = true
                                                    }

                                                    when {
                                                        title == "Delete Property" && what_view == 2 -> {
                                                            constants.API_Vm.delete_Post_SM_Drafts(
                                                                user_id = AppPreferences.getUserId(),
                                                                select_all = 0,
                                                                user_post_id = postid.toString(),
                                                            )
                                                            { aPI_Result_Handling ->
                                                                when (aPI_Result_Handling) {
                                                                    is API_Result_Handling.Loading -> {}
                                                                    is API_Result_Handling.NoData -> {}
                                                                    is API_Result_Handling.Deactivated -> {

                                                                    }

                                                                    is API_Result_Handling.Error -> {}
                                                                    is API_Result_Handling.Success -> {
                                                                        constants.Profile_ViewModel.deleteByPostId_Profile_SoldOuts(
                                                                            postid
                                                                        )
                                                                        navController.navigate(
                                                                            ProfileScreenFlow.Sold_Outs.route
                                                                        )

                                                                    }
                                                                }
                                                            }
                                                        }

                                                        title == "Edit" && what_view == 1 -> {
                                                        }

                                                        else -> {

                                                        }
                                                    }

                                                }
                                                .padding(horizontal = 12.dp, vertical = 10.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                painter = painterResource(id = iconRes),
                                                contentDescription = title,
                                                tint = newBlack,
                                                modifier = Modifier.size(20.dp)
                                            )
                                            Spacer(modifier = Modifier.width(12.dp))
                                            Text(
                                                text = title,
                                                color = newBlack,
                                                fontSize = constants.textUnit(14),
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                modifier = Modifier.weight(1f, fill = false)
                                            )
                                        }
                                    }
                                }
                            }

                        }
                    }
                }

            }

            if (constants.PostProperty_ViewModel.get_Post_Form_Flow() != -1){}
            else {
                SubcomposeAsyncImage(
                    model = R.drawable.propertyviewfullscreenvideo, "", modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .size(24.dp)
                        .align(Alignment.BottomEnd)
                        .noRippleClickable {
                            onFullView.value = true
                        }
                )
            }
        }
        else {

            val sliderPosition = remember(currentPosition, totalDuration) {
                if (totalDuration > 0) currentPosition / totalDuration.toFloat() else 0f
            }

            var userIsSeeking by remember { mutableStateOf(false) }

            Box (
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .wrapContentSize()
            )
            {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(
                                    Color.Transparent,
                                    Color.Black
                                )
                            )
                        )
                )

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                )
                {

                    Slider(
                        value = sliderPosition,
                        onValueChange = { newValue ->
                            userIsSeeking = true
                            currentPosition = (totalDuration * newValue).toLong()
                        },
                        onValueChangeFinished = {
                            player.seekTo(currentPosition)
                            userIsSeeking = false
                        },
                        thumb = {
                            Box(
                                modifier = Modifier
                                    .size(15.dp)
                                    .clip(CircleShape)
                                    .background(newWhite)
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        colors = SliderDefaults.colors(
                            thumbColor = Color.White,
                            activeTrackColor = newBlue,
                            inactiveTrackColor = Color.Gray
                        )
                    )

                    Text(
                        text = "${formatTime(currentPosition)} / ${formatTime(totalDuration)}",
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.wrapContentWidth()
                    )
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleVideoPlayer(
    videoUri: String,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onFullView: MutableState<Boolean>,
    postid: Int = 0,
    isSave: MutableState<Boolean>,
    what_view: Int,
    imagesList: List<Image>,
    value: Get_Reels_Data? = null,
    repost_Btm: MutableState<Boolean>,
    report_BS: MutableState<Boolean>
) {

    var network = rememberNetworkStatus()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var mark_As_Sold = remember { mutableStateOf(false) }

    val onHoverListener = constants.onHoverEnable.collectAsState()
    var expanded = remember { mutableStateOf(false) }

    val player = remember(videoUri, postid) {
        ExoPlayer.Builder(context).build().apply {
            repeatMode = Player.REPEAT_MODE_ONE
        }
    }

    val isVideoInitialized = remember(videoUri) { mutableStateOf(false) }

    LaunchedEffect(videoUri, player) {
        if (videoUri.isNotEmpty() && !isVideoInitialized.value) {
            player.setMediaItem(MediaItem.fromUri(videoUri))
            player.prepare()
            player.playWhenReady = true
            isVideoInitialized.value = true
        }
    }

    val isPlaying = remember { mutableStateOf(player.isPlaying) }
    val isBuffering = remember { mutableStateOf(true) }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> player.pause()
                Lifecycle.Event.ON_RESUME -> {
                    if (videoUri.isNotEmpty() && isVideoInitialized.value) {
                        player.play()
                    }
                }
                Lifecycle.Event.ON_DESTROY -> {
                    player.release()
                }
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)

        }
    }

    DisposableEffect(player) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                isBuffering.value = state != Player.STATE_READY
            }

            override fun onIsPlayingChanged(playing: Boolean) {
                isPlaying.value = playing
            }
        }
        player.addListener(listener)

        onDispose {
            player.removeListener(listener)

        }
    }

    DisposableEffect(Unit) {
        onDispose {
            if (!onFullView.value && constants.PostProperty_ViewModel.get_Post_Form_Flow() == -1) {
                player.release()
            }
        }
    }

    var currentPosition by remember { mutableStateOf(0L) }
    var totalDuration by remember { mutableStateOf(0L) }

    LaunchedEffect(player, isPlaying.value) {
        while (isPlaying.value) {
            currentPosition = player.currentPosition
            totalDuration = player.duration.coerceAtLeast(0L)
            delay(500)
        }
    }

    fun formatTime(ms: Long): String {
        val totalSeconds = ms / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return "%02d:%02d".format(minutes, seconds)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        if (videoUri.isNotEmpty()) {

            AndroidView(
                factory = { ctx ->
                    PlayerView(ctx).apply {
                        this.player = player
                        useController = false

                    }
                },
                update = { playerView ->

                    if (playerView.player == null) {
                        playerView.player = player
                    }
                },
                modifier = Modifier
                    .height(400.dp)
                    .fillMaxWidth()
                    .pointerInput(videoUri) {
                        detectTapGestures(
                            onTap = {
                                if (isPlaying.value) player.pause() else player.play()
                            }
                        )
                    }
            )

            if (!isPlaying.value) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.play_arrow),
                        contentDescription = "Play",
                        tint = Color.White
                    )
                }
            }

            if (isBuffering.value && isPlaying.value) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(48.dp),
                    color = Color.White
                )
            }
        }
        else if (imagesList.isNotEmpty()){

            val animationType = SlideshowAnimation.entries.random()
            SingleSlideshow(
                imagesList = imagesList,
                animationType = animationType,
                modifier = Modifier.fillMaxSize()
            )
        }
        else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ){
                Image(painterResource(R.drawable.emptypostsrento), "")
            }
        }

        Mark_As_Sold_Flow(mark_As_Sold, postid, navController)

        if (!onHoverListener.value){
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.Black.copy(.8f), Color.Transparent
                            )
                        )
                    )
            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = if (forTab()) 16.dp else rememberNotchHeightDp().value
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    )
                    {
                        SubcomposeAsyncImage(
                            model = R.drawable.propertytdetailsback,
                            " ",
                            modifier = Modifier
                                .size(24.dp)
                                .noRippleClickable {

                                    constants.Common_H_ViewModel.toggleshowBABars(true)
                                    constants.Common_H_ViewModel.toggleshowTABars(true)
                                    player.release()
                                    navController.navigateUp()
                                }
                        )
                        if (constants.PostProperty_ViewModel.get_Post_Form_Flow() != -1 ||
                            constants.PostProperty_ViewModel.get_Post_Form_Flow() == 999){
                            Text(
                                "Preview",
                                fontSize = constants.textUnit(24),
                                fontFamily = constants.fontFamily(0),
                                color = Color.White
                            )
                        }
                    }

                    if (constants.PostProperty_ViewModel.get_Post_Form_Flow() != -1 ||
                        constants.PostProperty_ViewModel.get_Post_Form_Flow() == 0){}
                    else {
                        Row(
                            modifier = Modifier.wrapContentWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        )
                        {
                            SubcomposeAsyncImage(
                                model = if (value?.is_saved == 1) R.drawable.saved_post else R.drawable.propertyviewsave,
                                "",
                                modifier = Modifier
                                    .size(24.dp)
                                    .noRippleClickable {
                                        if (network.value == NetworkStatus.Online) {
                                            constants.API_Vm.put_save_UnSave_Property(
                                                user_id = AppPreferences.getUserId(),
                                                user_post_id = postid,
                                                status = if (isSave.value) 2 else 1,
                                            )
                                            { apiResultHandling ->
                                                when (apiResultHandling) {
                                                    is API_Result_Handling.Success -> {
                                                        constants.Reels_ViewModel.toggleSave_Reels(
                                                            postid
                                                        )
                                                        constants.Reels_ViewModel.update_View_Property_Detail { current ->
                                                            current.copy(is_saved = if (current.is_saved == 1) 0 else 1)
                                                        }
                                                    }

                                                    else -> {}
                                                }
                                            }
                                        } else {
                                            toast(activity.getString(R.string.no_Internet))
                                        }
                                    }
                            )

                            Spacer(modifier = Modifier.padding(8.dp))

                            val dataList = if (what_view == 0) listOf(
                                Common_DropDown2Options_DC(icon = R.drawable.reelsshare, title = "Share"),
                                Common_DropDown2Options_DC(icon = R.drawable.reelsreport, title = "Report")
                            )
                            else if (what_view == 2) listOf(
                                Common_DropDown2Options_DC(icon = R.drawable.repost, title = "Repost"),
                                Common_DropDown2Options_DC(icon = R.drawable.reelsdelete, title = "Delete Property")
                            )
                            else listOf(
                                Common_DropDown2Options_DC(icon = R.drawable.reelsshare, title = "Share"),
                                Common_DropDown2Options_DC(icon = R.drawable.reelsedit, title = "Edit Property"),
                                Common_DropDown2Options_DC(icon = R.drawable.repost, title = "Repost"),
                                Common_DropDown2Options_DC(icon = R.drawable.reelssold, title = "Mark as Sold"),
                                Common_DropDown2Options_DC(icon = R.drawable.reelsdelete, title = "Delete Property"),
                            )

                            ExposedDropdownMenuBox(
                                expanded = expanded.value,
                                onExpandedChange = { expanded.value = !expanded.value }
                            )
                            {
                                Image(
                                    painter = painterResource(R.drawable.view_property_morevert),
                                    contentDescription = "More options",
                                    modifier = Modifier
                                        .size(24.dp)
                                        .menuAnchor()
                                        .noRippleClickable { expanded.value = true }
                                )

                                ExposedDropdownMenu(
                                    expanded = expanded.value,
                                    onDismissRequest = { expanded.value = false },
                                    containerColor = newWhite,
                                    modifier = Modifier.width(IntrinsicSize.Min)
                                ) {
                                    dataList.forEach { (iconRes, title) ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .noRippleClickable {
                                                    expanded.value = false

                                                    when {
                                                        title == "Delete Property" -> {
                                                            if (network.value == NetworkStatus.Online) {
                                                                constants.API_Vm.delete_Post_SM_Drafts(
                                                                    user_id = AppPreferences.getUserId(),
                                                                    select_all = 0,
                                                                    user_post_id = (value?.user_post_id
                                                                        ?: 0).toString(),
                                                                )
                                                                { aPI_Result_Handling ->
                                                                    when (aPI_Result_Handling) {
                                                                        is API_Result_Handling.Success -> {
                                                                            constants.Profile_ViewModel.deleteByPostId_Profile_SoldOuts(
                                                                                value?.user_post_id
                                                                                    ?: 0
                                                                            )
                                                                            navController.navigate(
                                                                                ProfileScreenFlow.Sold_Outs.route
                                                                            )
                                                                        }

                                                                        else -> {}
                                                                    }
                                                                }
                                                            } else {
                                                                toast(activity.getString(R.string.no_Internet))
                                                            }
                                                        }

                                                        title == "Edit Property" -> {
                                                            if (network.value == NetworkStatus.Online) {
                                                                navController.navigate(
                                                                    ProfileScreenFlow.Edit_Property_Option.route
                                                                )
                                                            } else {
                                                                toast(activity.getString(R.string.no_Internet))
                                                            }
                                                        }

                                                        title == "Mark as Sold" -> {
                                                            if (network.value == NetworkStatus.Online) {
                                                                mark_As_Sold.value = true
                                                            } else {
                                                                toast(activity.getString(R.string.no_Internet))
                                                            }
                                                        }

                                                        title == "Repost" -> {
                                                            if (network.value == NetworkStatus.Online) {
                                                                repost_Btm.value = true
                                                            }
                                                        }

                                                        title == "Report" -> {
                                                            if (value?.post_property?.is_report ?: 0 != 1) {
                                                                if (network.value == NetworkStatus.Online) {
                                                                    report_BS.value = true
                                                                } else {
                                                                    toast(activity.getString(R.string.no_Internet))
                                                                }
                                                            } else {
                                                                toast("Property Already Reported")
                                                            }

                                                        }

                                                        title == "Share" -> {
                                                            constants.DefaultShare(
                                                                "https://toletspot.com/property/${value?.user_post_id ?: 0}",
                                                                1
                                                            )
                                                        }
                                                    }
                                                }
                                                .padding(horizontal = 12.dp, vertical = 10.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                painter = painterResource(id = iconRes),
                                                contentDescription = title,
                                                tint = newBlack,
                                                modifier = Modifier.size(20.dp)
                                            )
                                            Spacer(modifier = Modifier.width(12.dp))
                                            Text(
                                                text = title,
                                                color = newBlack,
                                                fontSize = constants.textUnit(14),
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                                modifier = Modifier.weight(1f, fill = false)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (constants.PostProperty_ViewModel.get_Post_Form_Flow() == -1) {
                SubcomposeAsyncImage(
                    model = R.drawable.propertyviewfullscreenvideo,
                    "",
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .size(24.dp)
                        .align(Alignment.BottomEnd)
                        .noRippleClickable { onFullView.value = true }
                )
            }
        }

        if (onHoverListener.value && videoUri.isNotEmpty()) {
            val sliderPosition = remember(currentPosition, totalDuration) {
                if (totalDuration > 0) currentPosition / totalDuration.toFloat() else 0f
            }

            var userIsSeeking by remember { mutableStateOf(false) }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .wrapContentSize()
            )
            {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .background(
                            brush = Brush.verticalGradient(listOf(Color.Transparent, Color.Black))
                        )
                )

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Slider(
                        value = sliderPosition,
                        onValueChange = { newValue ->
                            userIsSeeking = true
                            currentPosition = (totalDuration * newValue).toLong()
                        },
                        onValueChangeFinished = {
                            player.seekTo(currentPosition)
                            userIsSeeking = false
                        },
                        thumb = {
                            Box(
                                modifier = Modifier
                                    .size(15.dp)
                                    .clip(CircleShape)
                                    .background(newWhite)
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        colors = SliderDefaults.colors(
                            thumbColor = Color.White,
                            activeTrackColor = newBlue,
                            inactiveTrackColor = Color.Gray
                        )
                    )

                    Text(
                        text = "${formatTime(currentPosition)} / ${formatTime(totalDuration)}",
                        color = Color.White,
                        fontSize = 12.sp,
                        modifier = Modifier.wrapContentWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun CustomRangeSliderExact(
    modifier: Modifier = Modifier,
    valueRange: ClosedFloatingPointRange<Float> = 0f..100f,
    initialStart: Float = 0.2f,
    initialEnd: Float = 0.8f,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit = {}
)
{
    val trackHeight = 8.dp
    val thumbRadius = 12.dp
    val borderWidth = 2.dp

    val totalWidth = remember { mutableStateOf(0f) }

    val thumbColor = Color.White
    val borderColor = Color(0xFF007BFF)
    val trackColor = Color(0xFFB0B0B0)
    val activeTrackColor = Color(0xFF007BFF)

    var start by remember { mutableStateOf((initialStart - valueRange.start) / (valueRange.endInclusive - valueRange.start)) }
    var end by remember { mutableStateOf((initialEnd - valueRange.start) / (valueRange.endInclusive - valueRange.start)) }

    LaunchedEffect(initialStart, initialEnd) {
        start = (initialStart - valueRange.start) / (valueRange.endInclusive - valueRange.start)
        end = (initialEnd - valueRange.start) / (valueRange.endInclusive - valueRange.start)
    }

    fun normalizedToActual(value: Float): Float {
        return valueRange.start + value * (valueRange.endInclusive - valueRange.start)
    }

    Box(
        modifier = modifier
            .height(thumbRadius * 2)
            .padding(horizontal = thumbRadius)
            .fillMaxWidth()
            .onGloballyPositioned {
                totalWidth.value = it.size.width.toFloat()
            }
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    val x = change.position.x.coerceIn(0f, totalWidth.value)
                    val newValue = (x / totalWidth.value).coerceIn(0f, 1f)

                    val distanceToStart = abs(newValue - start)
                    val distanceToEnd = abs(newValue - end)

                    if (distanceToStart < distanceToEnd) {
                        start = newValue.coerceAtMost(end - 0.05f)
                    } else {
                        end = newValue.coerceAtLeast(start + 0.05f)
                    }

                    onValueChange(
                        normalizedToActual(start)..normalizedToActual(end)
                    )
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val trackY = canvasHeight / 2

            val startX = start * canvasWidth
            val endX = end * canvasWidth

            drawRoundRect(
                color = trackColor,
                topLeft = Offset(0f, trackY - trackHeight.toPx() / 2),
                size = Size(canvasWidth, trackHeight.toPx()),
                cornerRadius = CornerRadius(trackHeight.toPx() / 2)
            )

            drawRoundRect(
                color = activeTrackColor,
                topLeft = Offset(startX, trackY - trackHeight.toPx() / 2),
                size = Size(endX - startX, trackHeight.toPx()),
                cornerRadius = CornerRadius(trackHeight.toPx() / 2)
            )

            drawCircle(
                color = borderColor,
                radius = thumbRadius.toPx(),
                center = Offset(startX, trackY)
            )
            drawCircle(
                color = thumbColor,
                radius = thumbRadius.toPx() - borderWidth.toPx(),
                center = Offset(startX, trackY)
            )

            drawCircle(
                color = borderColor,
                radius = thumbRadius.toPx(),
                center = Offset(endX, trackY)
            )
            drawCircle(
                color = thumbColor,
                radius = thumbRadius.toPx() - borderWidth.toPx(),
                center = Offset(endX, trackY)
            )
        }
    }
}

@Composable
fun CustomRangeSliderExactold(
    modifier: Modifier = Modifier,
    valueRange: ClosedFloatingPointRange<Float> = 0f..100f,
    initialStart: Float = 0.2f,
    initialEnd: Float = 0.8f,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit = {}
) {

    val trackHeight = 8.dp
    val thumbRadius = 12.dp
    val borderWidth = 2.dp

    val totalWidth = remember { mutableStateOf(0f) }

    val thumbColor = Color.White
    val borderColor = Color(0xFF007BFF)
    val trackColor = Color(0xFFB0B0B0)
    val activeTrackColor = Color(0xFF007BFF)

    var isDragging by remember { mutableStateOf(false) }

    var start by remember { mutableStateOf((initialStart - valueRange.start) / (valueRange.endInclusive - valueRange.start)) }
    var end by remember { mutableStateOf((initialEnd - valueRange.start) / (valueRange.endInclusive - valueRange.start)) }

    LaunchedEffect(initialStart, initialEnd, isDragging) {
        if (!isDragging) {
            val newStart = (initialStart - valueRange.start) / (valueRange.endInclusive - valueRange.start)
            val newEnd = (initialEnd - valueRange.start) / (valueRange.endInclusive - valueRange.start)

            if (abs(newStart - start) > 0.001f || abs(newEnd - end) > 0.001f) {
                start = newStart
                end = newEnd
            }
        }
    }

    fun normalizedToActual(value: Float): Float {
        return valueRange.start + value * (valueRange.endInclusive - valueRange.start)
    }

    Box(
        modifier = modifier
            .height(thumbRadius * 2)
            .padding(horizontal = thumbRadius)
            .fillMaxWidth()
            .onGloballyPositioned {
                totalWidth.value = it.size.width.toFloat()
            }
            .pointerInput(Unit) {
                awaitEachGesture {
                    val down = awaitFirstDown()
                    isDragging = true

                    val initialX = down.position.x.coerceIn(0f, totalWidth.value)
                    val initialValue = (initialX / totalWidth.value).coerceIn(0f, 1f)

                    val distanceToStart = abs(initialValue - start)
                    val distanceToEnd = abs(initialValue - end)
                    val movingStart = distanceToStart < distanceToEnd

                    do {
                        val event = awaitPointerEvent()

                        event.changes.forEach { change ->
                            if (change.pressed) {
                                val x = change.position.x.coerceIn(0f, totalWidth.value)
                                val newValue = (x / totalWidth.value).coerceIn(0f, 1f)

                                if (movingStart) {
                                    start = newValue.coerceAtMost(end - 0.05f)
                                } else {
                                    end = newValue.coerceAtLeast(start + 0.05f)
                                }

                                onValueChange(
                                    normalizedToActual(start)..normalizedToActual(end)
                                )

                                change.consume()
                            }
                        }
                    } while (event.changes.any { it.pressed })

                    isDragging = false
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val trackY = canvasHeight / 2

            val startX = start * canvasWidth
            val endX = end * canvasWidth

            drawRoundRect(
                color = trackColor,
                topLeft = Offset(0f, trackY - trackHeight.toPx() / 2),
                size = Size(canvasWidth, trackHeight.toPx()),
                cornerRadius = CornerRadius(trackHeight.toPx() / 2)
            )

            drawRoundRect(
                color = activeTrackColor,
                topLeft = Offset(startX, trackY - trackHeight.toPx() / 2),
                size = Size(endX - startX, trackHeight.toPx()),
                cornerRadius = CornerRadius(trackHeight.toPx() / 2)
            )

            drawCircle(
                color = borderColor,
                radius = thumbRadius.toPx(),
                center = Offset(startX, trackY)
            )
            drawCircle(
                color = thumbColor,
                radius = thumbRadius.toPx() - borderWidth.toPx(),
                center = Offset(startX, trackY)
            )

            drawCircle(
                color = borderColor,
                radius = thumbRadius.toPx(),
                center = Offset(endX, trackY)
            )
            drawCircle(
                color = thumbColor,
                radius = thumbRadius.toPx() - borderWidth.toPx(),
                center = Offset(endX, trackY)
            )
        }
    }
}

@Composable
fun SliderDemo() {
    var selectedRange by remember { mutableStateOf(20f..80f) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Selected: ${selectedRange.start.toInt()} - ${selectedRange.endInclusive.toInt()}")

        Spacer(Modifier.height(16.dp))

        CustomRangeSliderExact(
            valueRange = 0f..100f,
            initialStart = selectedRange.start,
            initialEnd = selectedRange.endInclusive,
            onValueChange = { selectedRange = it }
        )
    }
}

var isFullTextExpanded by  mutableStateOf(false)

@Composable
fun ExpandableText(
    fullText: String,
    maxCharacters: Int = 80,
    seeMoreText: String = "See More",
    seeLessText: String = "See Less",
    textColor: Color = Color.Black,
    actionColor: Color = newBlue,
    fontSize: TextUnit = constants.textUnit(12),
    fontFamily: FontFamily = constants.fontFamily(3),
    modifier: Modifier
) {

    val annotatedText = buildAnnotatedString {
        val displayText = if (isFullTextExpanded || fullText.length <= maxCharacters) {
            fullText
        } else {
            fullText.take(maxCharacters) + "..."
        }

        append(displayText)

        if (fullText.length > maxCharacters) {
            val clickableText = if (isFullTextExpanded) seeLessText else seeMoreText

            append(" ")
            pushStringAnnotation(tag = "ACTION", annotation = "toggle")
            pushStyle(
                SpanStyle(
                    color = actionColor,
                    fontFamily = fontFamily,
                    fontSize = fontSize,
                )
            )
            append(clickableText)
            pop()
            pop()
        }
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations("ACTION", offset, offset)
                .firstOrNull()?.let {
                    isFullTextExpanded = !isFullTextExpanded
                }
        },
        style = TextStyle(
            color = textColor,
            fontSize = fontSize,
            fontFamily = fontFamily
            , textAlign = TextAlign.Center
        ) ,
        modifier = modifier
            .animateContentSize()
    )
}

@Composable
fun Sell_Gradient_Box(modifier: Modifier){
        Box(
            modifier = modifier
                .height(24.dp)
                .wrapContentWidth()

                .clip(RoundedCornerShape(4.dp))
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            Color(0xffF0E4BC),
                            Color(0xffE6C96A),
                            Color(0xffCFB14E)
                        )
                    )
                )
                .padding(horizontal = 8.dp)
            , contentAlignment = Alignment.Center
        )
        {
            Text("Rent / Lease",
                color = Color.White,
                fontSize = constants.textUnit(12),
                fontFamily = constants.fontFamily(0)
            )
        }
}

@Composable
fun Chat_Property_Show_Structure(content: Chat_Property_Structure_DC){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, newGray, RoundedCornerShape(8.dp))
        )
        {
            Column {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                    , verticalAlignment = Alignment.CenterVertically
                    , horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    val text1 = when(content.enquiry_Type){
                        0 -> "Your Property"
                        1 -> "Your enquired this property"
                        else ->  "Searched for"
                    }

                    Text( text1,
                        color = newBlack,
                        fontSize = constants.textUnit(14)
                        , fontFamily = constants.fontFamily(1)
                    )

                    val tbc2 = when(content.enquiry_Type){
                        0 -> Triple("Enquired" ,Color(0xffF7F0DC) ,newBlue)
                        1 ->  Triple("Self Enquired" ,Color(0xffE9F5EC) ,Color(0xff269D3D))
                        else ->  Triple("Shown Interest" ,Color(0xffFFEAD4) ,Color(0xffC57217))
                    }

                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .background(tbc2.second)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                        , contentAlignment = Alignment.Center
                    ){

                        Text(
                            tbc2.first ,
                            color = tbc2.third,
                            fontSize = constants.textUnit(12)
                            , fontFamily = constants.fontFamily(1)
                        )
                    }
                }

                ListItem(
                    headlineContent = {
                        Text(content.property_Name ,
                            fontSize = constants.textUnit(16)
                            , fontFamily = constants.fontFamily(1)
                        )
                    }
                    , supportingContent = {
                        Row (
                            verticalAlignment = Alignment.Top
                            , horizontalArrangement = Arrangement.Center
                        ){
                            AsyncImage(
                                model = R.drawable.locationpinenquiry
                                ,""
                                , modifier = Modifier
                                    .size(14.dp)
                                    .align(Alignment.Top)
                            )

                            Text(content.property_Location ,
                                fontSize = constants.textUnit(12)
                                , fontFamily = constants.fontFamily(3)
                            )
                        }
                    }
                    , trailingContent = {
                        Box(
                            modifier = Modifier
                                .width(90.dp)
                                .height(60.dp)
                                .background(newBlue)
                        )
                    }
                    , colors = ListItemColors(
                        containerColor = Color(0xffE8E8E8),
                        headlineColor = Color.Black,
                        leadingIconColor = Color.DarkGray,
                        overlineColor = Color.Gray,
                        supportingTextColor = Color.Gray,
                        trailingIconColor = Color.LightGray,
                        disabledHeadlineColor = Color.Gray.copy(alpha = 0.5f),
                        disabledLeadingIconColor = Color.Gray.copy(alpha = 0.5f),
                        disabledTrailingIconColor = Color.Gray.copy(alpha = 0.5f)
                    )
                    , modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, newGray, RoundedCornerShape(8.dp))
                )

                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
                {
                    val colors = listOf(
                        Color.Red , Color.Cyan , Color.Green , Color.DarkGray , Color.Yellow
                    )

                   Row (
                       modifier = Modifier
                           .align(Alignment.CenterStart)
                           , verticalAlignment = Alignment.CenterVertically
                       , horizontalArrangement = Arrangement.spacedBy(8.dp)
                   )
                   {
                       if (content.enquiry_Type == 0){
                           Text("Enquirers" ,
                               color = newBlue,
                               fontSize = constants.textUnit(12)
                               , fontFamily = constants.fontFamily(1)
                           )

                           Box(modifier = Modifier.wrapContentWidth()
                           )
                           {
                               repeat(5) { index ->
                                   Box(
                                       modifier = Modifier
                                           .offset(x = (index * 12).dp)
                                           .size(24.dp)
                                           .clip(CircleShape)
                                           .background(colors[index])
                                   )
                               }
                           }
                       }
                       else {

                           Box(
                               modifier = Modifier
                                   .size(24.dp)
                                   .clip(CircleShape)
                                   .background(newBlue)
                           )

                           Text("user name" ,
                               color = newBlue,
                               fontSize = constants.textUnit(12)
                               , fontFamily = constants.fontFamily(1)
                           )
                       }
                   }

                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                        , verticalAlignment = Alignment.CenterVertically
                        , horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text("New message" ,
                            color = newBlue,
                            fontSize = constants.textUnit(12)
                            , fontFamily = constants.fontFamily(1)
                        )

                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(newBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "1",
                                color = Color.White,
                                fontSize = 10.sp,
                                lineHeight = 10.sp,
                            )
                        }

                        AsyncImage(
                            model = R.drawable.right_arrow
                            ,""
                            , modifier = Modifier
                                .size(14.dp)
                        )
                    }
                }
            }
        }
}

fun openDialer(context: Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
    }

    val packageManager = context.packageManager
    val resolveInfo = intent.resolveActivity(packageManager)

    if (resolveInfo != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, "Dialer not found on this device", Toast.LENGTH_SHORT).show()
    }
}

fun openEmailApp(context: Context, email: String, subject: String = "", body: String = "") {

    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
        `package` = "com.google.android.gm"
    }

    val packageManager = context.packageManager
    val resolveInfo = intent.resolveActivity(packageManager)

    if (resolveInfo != null) {
        context.startActivity(intent)
    } else {

        val fallbackIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }
        val fallbackResolve = fallbackIntent.resolveActivity(packageManager)
        if (fallbackResolve != null) {
            context.startActivity(fallbackIntent)
        } else {
            Toast.makeText(context, "Gmail app not found on this device", Toast.LENGTH_SHORT).show()
        }
    }
}

fun openWhatsApp(context: Context, phoneNumber: String) {
    val cleanNumber = phoneNumber.replace(" ", "").replace("+", "")
    val uri = Uri.parse("https://wa.me/$cleanNumber")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.setPackage("com.whatsapp")

    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "WhatsApp not found on this device", Toast.LENGTH_SHORT).show()
    }
}

fun openGmail(
    context: Context,
    to: String,
    cc: String? = null,

) {
    try {

        val gmailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, to)
            putExtra(Intent.EXTRA_CC, cc)

            setPackage("com.google.android.gm")
        }

        context.startActivity(gmailIntent)
    } catch (e: Exception) {

        val fallbackIntent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, to)
            putExtra(Intent.EXTRA_CC, cc)

        }

        val packageManager = context.packageManager
        val resolveInfo = fallbackIntent.resolveActivity(packageManager)

        if (resolveInfo != null) {
            context.startActivity(Intent.createChooser(fallbackIntent, "Choose an email app"))
        } else {
            Toast.makeText(context, "No email app found on this device", Toast.LENGTH_SHORT).show()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Enquiry_Form_Btm_Sheet_Structure(
    enabled: Boolean,
    videos: Get_Reels_Data?,

){

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var name by mutableStateOf(AppPreferences.get_Real_Name())
    var whatspp_num = remember { mutableStateOf(constants.Start_Up_ViewModel.countryCode +  constants.Start_Up_ViewModel.phoneNumber) }

    var email by remember { mutableStateOf("") }
    var user_request by  mutableStateOf("Hi! Your property seems like the perfect fit I’ve been searching for. I would appreciate it if you could share the rent and any other important details. Looking forward to hearing from you.\n")

    var showError by remember { mutableStateOf(false) }

    val network = rememberNetworkStatus()

    var countryselectedWA = constants.Start_Up_ViewModel.selectedCountryVm.collectAsState()

    var checkBox = remember { mutableStateOf(false) }

    DisposableEffect(Unit) {

        constants.Start_Up_ViewModel.reset_selectedCountry()
        constants.Start_Up_ViewModel.add_selectedCountry(AppPreferences.getCountry())
        constants.Start_Up_ViewModel.countryCode = "+91"
        onDispose {
            name = AppPreferences.get_User_Name()
            whatspp_num.value = ""
            email = ""
            checkBox.value = false
            constants.Start_Up_ViewModel.phoneNumber = ""
            constants.Start_Up_ViewModel.countryCode = "+91"
        }
    }

    LaunchedEffect(countryselectedWA.value?.emoji) {
       if( AppPreferences.getCountry().name != countryselectedWA.value?.name){
           checkBox.value = false
       }
    }

       var  state = constants.PostProperty_ViewModel.status_PFs.collectAsState()

    val scope = rememberCoroutineScope()

    if (enabled) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {

                if (!state.value) {
                    name = ""
                    whatspp_num.value = ""
                    email = ""
                    checkBox.value = false
                    constants.Start_Up_ViewModel.phoneNumber = ""
                    constants.Start_Up_ViewModel.countryCode = "+91"
                    user_request = ""
                    constants.Start_Up_ViewModel.reset_selectedCountry()
                    constants.Start_Up_ViewModel.add_selectedCountry(AppPreferences.getCountry())
                    constants.Reels_ViewModel.dismiss_Send_Eq_Btm_Sheet()
                }

            }, containerColor = newWhite,
            sheetGesturesEnabled = false
        )
        {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()

                    .background(newWhite)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            )
            {
                item {
                    Text(
                        "Enquire about this property",
                        color = newBlack,
                        fontSize = constants.textUnit(24), fontFamily = constants.fontFamily(0)
                    )

                    Text(
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(color = newBlack)) {
                                append("Name")
                            }
                            withStyle(style = SpanStyle(color = Color.Red)) {
                                append("* ")
                            }
                        },
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(1),
                    )

                    Column(modifier = Modifier.fillMaxWidth()) {
                        TextField(
                            value = name,
                            onValueChange = {

                                val filtered = it.filter { it.isLetter() || it.isWhitespace() }

                                name = if (filtered.length <= 20) filtered else ""

                                if (showError && filtered.isNotBlank()) {
                                    showError = false
                                }
                            },
                            placeholder = {
                                Text(
                                    "Enter name",
                                    color = newGray,
                                    fontSize = constants.textUnit(12),
                                    fontFamily = constants.fontFamily(2)
                                )
                            },
                            textStyle = TextStyle(
                                color = newBlack,
                                fontSize = constants.textUnit(12),
                                fontFamily = constants.fontFamily(2)
                            ),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                unfocusedTextColor = newBlack,
                              focusedTextColor = newBlack
                            ),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    1.dp,
                                    if (showError) Color.Red else newGray,
                                    RoundedCornerShape(8.dp)
                                )
                        )

                        if (showError) {
                            Text(
                                text = "Please enter a name",
                                color = Color.Red,
                                fontSize = constants.textUnit(11),
                                modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        Text(
                            "Mobile Number",
                            color = newBlack,
                            fontSize = constants.textUnit(14),
                            fontFamily = constants.fontFamily(1),
                        )

                        AsyncImage(
                            model = R.drawable.frame__1_, "",
                            modifier = Modifier
                                .size(14.dp)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .height(56.dp)
                            .fillMaxWidth()
                            .border(1.dp, newGray, RoundedCornerShape(8.dp))
                    )
                    {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1.8f)
                                .background(Color(0xffF4F4F4)), contentAlignment = Alignment.Center
                        )
                        {
                            Text(
                                AppPreferences.getCountry().emoji ?: "",

                                fontSize = constants.textUnit(20),

                            )
                        }

                        VerticalDivider(
                            modifier = Modifier.fillMaxHeight(),
                            thickness = 1.dp,
                            newGray
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(8.2f), contentAlignment = Alignment.Center
                        )
                        {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    AppPreferences.get_ph_number(),
                                    color = newBlack,
                                    fontSize = constants.textUnit(14),
                                    fontFamily = constants.fontFamily(0),
                                )

                                AsyncImage(
                                    model = R.drawable.phone_verified, "",
                                    modifier = Modifier
                                        .size(18.dp)
                                )

                            }
                        }
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                    Text(
                        "WhatsApp Number",
                        color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(1),
                    )

                    Column {
                        Row(
                            modifier = Modifier
                                .height(56.dp)
                                .fillMaxWidth()

                        )
                        {

                            NumberInput_Rento(mutableStateOf(false)  , constants.Start_Up_ViewModel, isChecked = checkBox)

                        }

                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        )
                        {
                            Checkbox(
                                checked = checkBox.value,
                                onCheckedChange = {
                                    checkBox.value = it
                                    if (it) {
                                        constants.Start_Up_ViewModel.countryCode = AppPreferences.getCountry().dial_code
                                        constants.Start_Up_ViewModel.add_selectedCountry(AppPreferences.getCountry())
                                        constants.Start_Up_ViewModel.phoneNumber = AppPreferences.get_ph_number()
                                    }
                                    else {
                                        constants.Start_Up_ViewModel.phoneNumber = ""
                                    }
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = newBlue,
                                    uncheckedColor = newGray
                                )
                            )

                            Text(
                                "same as mobile number",
                                color = newBlack,
                                fontSize = constants.textUnit(12),
                                fontFamily = constants.fontFamily(2),
                            )

                        }
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                    Text(
                        "Email ID",
                        color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(1),
                    )

                    TextField(
                        value = email,
                        onValueChange = {
                            email = it
                        }, placeholder = {
                            Text(
                                "Enter email address",
                                color = newGray,
                                fontSize = constants.textUnit(12),
                                fontFamily = constants.fontFamily(2)
                            )
                        }, textStyle = TextStyle(
                            color = newBlack,
                            fontSize = constants.textUnit(12), fontFamily = constants.fontFamily(2)
                        ), colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                            ,
                            unfocusedTextColor = newBlack,
                          focusedTextColor = newBlack
                        ), singleLine = true, keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text
                        ),
                        keyboardActions = KeyboardActions {
                            ImeAction.Done
                        }, modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, newGray, RoundedCornerShape(8.dp))
                    )

                    Spacer(modifier = Modifier.padding(8.dp))

                    Text(
                        "Let Us Know What You Need",
                        color = newBlack,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(1),
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth()
                            .border(1.dp, newGray, RoundedCornerShape(8.dp))
                    )
                    {
                        TextField(
                            value = user_request,
                            onValueChange = {
                                user_request = it
                            }, placeholder = {
                                Text(
                                    "Type your message....",
                                    color = newGray,
                                    fontSize = constants.textUnit(12),
                                    fontFamily = constants.fontFamily(2)
                                )
                            }, textStyle = TextStyle(
                                color = newBlack,
                                fontSize = constants.textUnit(12),
                                fontFamily = constants.fontFamily(2)
                            ), colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                unfocusedTextColor = newBlack,
                              focusedTextColor = newBlack
                            ), modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                        )
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                }

            }

            Static_Bottom(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            )
            {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(.6f)
                        .clip(RoundedCornerShape(8.dp))
                        .noRippleClickable {
                            if (network.value == NetworkStatus.Online) {
                                if (name.isBlank()) {
                                    showError = true
                                } else {
                                    if ((videos?.enquiry ?: 0) != 1) {

                                        constants.API_Vm.put_Send_Enquiry(
                                            user_id = AppPreferences.getUserId(),
                                            recever_posts_id = videos?.user_post_id ?: 0,
                                            land_type_id = videos?.post_property?.land_type_id ?: 0,
                                            land_categorie_id = videos?.post_property?.land_categorie_id
                                                ?: 0,
                                            name = name,
                                            phone_num = videos?.phone_num ?: "",
                                            whatsapp_num = whatspp_num.value,
                                            email = email,
                                            land_category_para = user_request,
                                        )
                                        { apiResultHandling ->

                                            when (apiResultHandling) {
                                                is API_Result_Handling.Loading -> {

                                                    constants.PostProperty_ViewModel.change_Status_PFs(
                                                        true
                                                    )
                                                }

                                                is API_Result_Handling.Deactivated -> {

                                                }

                                                is API_Result_Handling.Error -> {

                                                    name = ""
                                                    whatspp_num.value = ""
                                                    email = ""
                                                    constants.Start_Up_ViewModel.phoneNumber = ""
                                                    GlobalSnackbar.show("Something went wrong")
                                                    constants.PostProperty_ViewModel.change_Status_PFs(
                                                        false
                                                    )
                                                    constants.Reels_ViewModel.dismiss_Send_Eq_Btm_Sheet()
                                                }

                                                is API_Result_Handling.Success -> {

                                                    name = ""
                                                    whatspp_num.value = ""
                                                    email = ""
                                                    checkBox.value = false
                                                    constants.Start_Up_ViewModel.phoneNumber = ""
                                                    constants.Start_Up_ViewModel.countryCode = "+91"

                                                    GlobalSnackbar.show("Enquiry Submitted Successfully")
                                                    constants.Reels_ViewModel.toggle_is_Enquired(
                                                        videos?.user_post_id ?: 0
                                                    )
                                                    constants.PostProperty_ViewModel.change_Status_PFs(
                                                        false
                                                    )
                                                    constants.Reels_ViewModel.dismiss_Send_Eq_Btm_Sheet()
                                                }

                                                is API_Result_Handling.NoData -> {

                                                    constants.PostProperty_ViewModel.change_Status_PFs(
                                                        false
                                                    )
                                                }
                                            }
                                        }
                                    } else {
                                        constants.Reels_ViewModel.dismiss_Send_Eq_Btm_Sheet()
                                        name = ""
                                        whatspp_num.value = ""
                                        email = ""
                                        constants.Start_Up_ViewModel.phoneNumber = ""
                                        checkBox.value = false
                                        GlobalSnackbar.show("Already Enquired")
                                    }
                                }
                            } else {
                                toast(activity.getString(R.string.no_Internet))
                            }
                        }
                        .background(newBlue), contentAlignment = Alignment.Center
                ) {

                    if (state.value){
                        CircularProgressIndicator(color = Color.White)
                    }
                    else {
                        Text(
                            "Send Enquiry",
                            color = Color.White,
                            fontSize = constants.textUnit(14),
                            fontFamily = constants.fontFamily(0)
                        )
                    }
                }
            }
        }
    }

}

fun convertDateToTimestamp(
    dateString: String,
    pattern: String = "yyyy-MM-dd",
    zoneId: ZoneId = ZoneId.systemDefault()
): String {
    return try {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        val localDate = LocalDate.parse(dateString, formatter)

        localDate.atStartOfDay(zoneId).toInstant().toEpochMilli().toString()
    } catch (e: Exception) {
        e.printStackTrace()
        "0"
    }
}

fun getTodayLocalDate(): LocalDate {
    return LocalDate.now()
}

@Composable
fun StepCircularProgress(
    totalSteps: Int,
    currentStep: Int,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 8.dp,
    completedColor: Color = newBlue,
    remainingGradient: List<Color> = listOf(newLightBlue , newLightBlue)
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val size = size.minDimension
            val stroke = strokeWidth.toPx()

            drawArc(
                brush = Brush.sweepGradient(remainingGradient),

                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )

            val sweep = (currentStep / totalSteps.toFloat()) * 360f
            drawArc(

                color = completedColor,
                startAngle = -90f,
                sweepAngle = sweep,
                useCenter = false,
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )
        }

        Text(
            text = "$currentStep/$totalSteps",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Mark_As_Sold_Flow(
    onDismiss: MutableState<Boolean>,
    user_PostId: Int,
    navController: NavHostController
){

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var isLoading by remember { mutableStateOf(false) }

    if (onDismiss.value) {
        ModalBottomSheet(
            onDismissRequest = { onDismiss.value = false }, containerColor = Color.White
            , sheetState = bottomSheetState
        )
        {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    buildAnnotatedString {
                        withStyle(SpanStyle(color = newBlack)) {
                            append("Once you mark a property as sold, it will no longer be visible to others. It’ll be moved to the Soldouts  ")
                        }
                        withStyle(SpanStyle(color = newBlue)) {
                            append(" SoldOuts menu")
                        }
                        withStyle(SpanStyle(color = newBlack)) {
                            append(" in profile.")
                        }
                    },
                    fontFamily = constants.fontFamily(2),
                    fontSize = constants.textUnit(14),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )

                Static_Bottom(
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .fillMaxHeight(.6f)
                            .background(newBlue, RoundedCornerShape(8.dp))
                            .noRippleClickable {
                                constants.API_Vm.put_Sold_Unsold_Property(
                                    user_id = AppPreferences.getUserId(),
                                    user_post_id = user_PostId,
                                    status = 1,
                                )
                                { aPI_Result_Handling ->
                                    when (aPI_Result_Handling) {
                                        is API_Result_Handling.NoData -> {}
                                        is API_Result_Handling.Error -> {
                                            isLoading = false
                                        }

                                        is API_Result_Handling.Deactivated -> {

                                        }

                                        is API_Result_Handling.Loading -> {
                                            isLoading = true
                                        }

                                        is API_Result_Handling.Success -> {
                                            isLoading = false
                                            constants.Reels_ViewModel.deleteVideoById_Profile_Post_Reels(
                                                user_PostId
                                            )
                                            constants.Profile_ViewModel.deleteVideoById_Profile_Posts(
                                                user_PostId
                                            )
                                            onDismiss.value = false
                                            constants.Common_H_ViewModel.toggleReelsBTMSheet(false)

                                            constants.API_Vm.totalPages_PS_FF = 0
                                            navController.navigateUp()
                                        }
                                    }
                                }
                            }, contentAlignment = Alignment.Center
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(14.dp),
                                color = newBlue
                            )
                        } else {
                            Text(
                                "Mark as RentedOut",
                                color = Color.White,
                                fontSize = constants.textUnit(14),
                                fontFamily = constants.fontFamily(0)
                            )
                        }
                    }
                }
            }
        }
    }
}
