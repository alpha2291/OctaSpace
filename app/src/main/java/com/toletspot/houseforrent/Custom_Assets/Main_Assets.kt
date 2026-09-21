package com.toletspot.houseforrent.Custom_Assets

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Main_Comments_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Reels_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Reply_Comments_Data
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.LastReply
import com.toletspot.houseforrent.API.API_Result_Handling
import com.toletspot.houseforrent.AppPreferences
import com.toletspot.houseforrent.GlobalSnackbar
import com.toletspot.houseforrent.Navigation.VideosScreenFlow
import com.toletspot.houseforrent.NetworkConnectionState
import com.toletspot.houseforrent.NetworkStatus
import com.toletspot.houseforrent.R
import com.toletspot.houseforrent.UI_DataClass.Profile_Handle_Back
import com.toletspot.houseforrent.constants
import com.toletspot.houseforrent.rememberConnectivityState
import com.toletspot.houseforrent.rememberNetworkStatus
import com.toletspot.houseforrent.ui.theme.newBlack
import com.toletspot.houseforrent.ui.theme.newBlue
import com.toletspot.houseforrent.ui.theme.newGray
import com.toletspot.houseforrent.ui.theme.newLightBlue
import com.toletspot.houseforrent.ui.theme.newWhite
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onGloballyPositioned
import com.android.volley.BuildConfig
import com.toletspot.houseforrent.Home_Screen.Common_H_ViewModel
import com.toletspot.houseforrent.forTab
import com.toletspot.houseforrent.noRippleClickable
import com.toletspot.houseforrent.ui.theme.newPurpleGradient
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.zIndex
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.PostFlow
import com.toletspot.houseforrent.ui.theme.newPurpleGradientBorder

fun logD(message: String, tag: String = " \u2753  Clicks") {
    if (BuildConfig.DEBUG) {
        Log.d(tag, message)
    }
}
class ClickHelper private constructor() {
    private val now: Long
        get() = System.currentTimeMillis()
    private var lastEventTimeMs: Long = 0
    fun clickOnce(event: () -> Unit) {
        if (now - lastEventTimeMs >= 300L) {
            event.invoke()
        }
        lastEventTimeMs = now
    }

    companion object {
        @Volatile
        private var instance: ClickHelper? = null
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: ClickHelper().also { instance = it }
            }
    }
}

    private fun sanitizePhoneNumber(raw: String): String {

    return raw.filter { it.isDigit() }
}

fun openWhatsAppChat(context: Context, rawPhone: String, message: String? = null) {
    val phone = sanitizePhoneNumber(rawPhone)
    if (phone.isEmpty()) return

    val encodedMessage = message?.let {
        URLEncoder.encode(it, StandardCharsets.UTF_8.toString())
    } ?: ""

    val nativeUri = Uri.parse("whatsapp://send?phone=$phone${if (encodedMessage.isNotEmpty()) "&text=$encodedMessage" else ""}")
    val nativeIntent = Intent(Intent.ACTION_VIEW, nativeUri)

    try {
        context.startActivity(nativeIntent)
    } catch (e: ActivityNotFoundException) {

        val webUrl = "https://api.whatsapp.com/send?phone=$phone${if (encodedMessage.isNotEmpty()) "&text=$encodedMessage" else ""}"
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(webUrl))

        try {
            context.startActivity(webIntent)
        } catch (_: Exception) {

        }
    } catch (ex: Exception) {

    }
}

val Int.scaledSp  @Composable   get() = (this / LocalDensity.current.fontScale).sp

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.noRippleClickableWithScale(
    interactionSource: MutableInteractionSource,
    enable : Boolean = true,
    onClick: () -> Unit,
): Modifier = composed {
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed && enable) 0.75f else 1f,
        animationSpec = tween(100)
    )

    this
        .scale(scale)
        .clickable(
            indication = null,
            interactionSource = interactionSource
        ) {
            onClick()
        }
}

class TopSemiCircleShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {

        val width = size.width

        val radius = width / 2f

        val path = Path().apply {

            moveTo(0f, size.height)

            lineTo(0f, radius)

            arcTo(
                rect = Rect(
                    left = 0f,
                    top = 0f,
                    right = width,
                    bottom = width
                ),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 180f,
                forceMoveTo = false
            )

            lineTo(width, size.height)

            close()
        }

        return Outline.Generic(path)
    }
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    onTabTapped: (Int) -> Unit
) {
    val connectionState by rememberConnectivityState()
    val isConnected = connectionState == NetworkConnectionState.Available

    val items = constants.Common_H_ViewModel.BB_Items
    val itemCount = items.size
    val itemWidth = remember { mutableStateOf(0f) }

    val indicatorOffset by animateDpAsState(
        targetValue = with(LocalDensity.current) {
            (itemWidth.value * selectedIndex).toDp()
        },
        animationSpec = tween(500, easing = FastOutSlowInEasing),
        label = "indicatorOffset"
    )

    Box(
        modifier = modifier
            .height(if(forTab()) 100.dp else 80.dp)
            .fillMaxWidth()
            .background(newWhite)
    ) {

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = indicatorOffset, y = if (forTab()) 40.dp else 15.dp)
                .width(with(LocalDensity.current) { itemWidth.value.toDp() })
                .height(if (forTab()) 70.dp else 40.dp)
                .clip(TopSemiCircleShape())
                .background(
                    brush = Brush.verticalGradient(
                        listOf(newBlue.copy(alpha = 0.9f), newBlue.copy(alpha = 0.6f))
                    )
                )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(if(forTab()) 90.dp else 70.dp)
                .onGloballyPositioned { layoutCoordinates ->
                    itemWidth.value =
                        layoutCoordinates.size.width.toFloat() / itemCount.toFloat()
                },
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = selectedIndex == index
                val scale = remember { Animatable(1f) }

                LaunchedEffect(isSelected) {
                    if (isSelected) {
                        scale.animateTo(1.3f, tween(250, easing = FastOutSlowInEasing))
                        scale.animateTo(1f, tween(250, easing = FastOutSlowInEasing))
                    } else {
                        scale.snapTo(1f)
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .noRippleClickable {

                            onTabTapped(index)
                            when (index) {
                                0 -> AppPreferences.save_Post_Id(0)
                                1 -> constants.Search_ViewModel.search_State.value = 1
                                2 -> {
                                    constants.PostProperty_ViewModel.setPostFlow(PostFlow.NEW)

                                }
                                4 -> constants.Profile_ViewModel.add_Selected_Profile_Id(0)
                            }
                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SubcomposeAsyncImage(
                        model = if (isSelected) item.selectedIcon else item.unSelectedIcon,
                        contentDescription = "",
                        modifier = Modifier
                            .graphicsLayer {
                                scaleX = scale.value
                                scaleY = scale.value
                            }
                            .size(if (index == 2) 30.dp else 22.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Reels_TopBar_LS(
    modifier: Modifier,
    onlocation_Changr_Click: () -> Unit,
    navHostController: NavHostController
){

    var notchPadding = rememberNotchHeightDp()
    var city = constants.Start_Up_ViewModel.city.collectAsState()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(newWhite)
    )
    {
        ListItem(
            headlineContent = {
                Text(constants.activity.getString(R.string.app_name)
                    , color = Color.Black
                    , fontSize = constants.textUnit(24)
                    , fontFamily = constants.fontFamily(0)
                )
            },
            supportingContent = {
                Row (
                    modifier = Modifier
                        .noRippleClickable{
                            onlocation_Changr_Click()
                        }
                    , verticalAlignment = Alignment.CenterVertically
                    , horizontalArrangement = Arrangement.Center
                ){
                    SubcomposeAsyncImage(
                        model = R.drawable.locationpinenquiry,
                        "",
                        modifier = Modifier
                            .size(12.dp)
                    )

                    Spacer(modifier = Modifier.padding(2.dp))

                    Text(AppPreferences.get_User_Location()
                        , color = newGray
                        , fontSize = constants.textUnit(12)
                        , fontFamily = constants.fontFamily(2)
                    )

                    Image(painterResource(R.drawable.arrowdown) , "")
                }
            }
            , trailingContent = {
                Box {
                    SubcomposeAsyncImage(
                        model = R.drawable.notification,
                        "",
                        modifier = Modifier
                            .size(if (forTab()) 32.dp else 24.dp)
                            .noRippleClickable {
                                constants.sharedHelper.putBoolean(
                                    constants.activity,
                                    constants.notificationiconEnable,
                                    false
                                )
                                AppPreferences.save_Noti_Post_Id("")
                                navHostController.navigate(VideosScreenFlow.In_App_Notification.route)
                            }
                    )

                    if(constants.sharedHelper.getBoolean(constants.activity, constants.notificationiconEnable)) {
                        Image(
                            painter = painterResource(R.drawable.baseline_circle_24),
                            contentDescription = "",
                            modifier = Modifier
                                .size(10.dp)
                                .align(Alignment.TopEnd)
                        )
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
            , modifier = Modifier
                .then(
                    if (forTab()) {
                        Modifier.height(110.dp)
                    } else Modifier
                )
                .padding(top = if (forTab()) 16.dp else notchPadding.value)
        )
    }
}

@Composable
fun Reels_TopBar(
    modifier: Modifier,
    onlocation_Changr_Click: () -> Unit,
    navHostController: NavHostController
){

    var notchPadding = rememberNotchHeightDp()
    var city = constants.Start_Up_ViewModel.city.collectAsState()

    Box(
        modifier = modifier
            .fillMaxWidth()

            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color.Transparent,
                    )
                )
            )
    )
    {
        ListItem(
            headlineContent = {
                Text(constants.activity.getString(R.string.app_name)
                    , color = Color.White
                    , fontSize = constants.textUnit(24)
                    , fontFamily = constants.fontFamily(0)
                )
            },
            supportingContent = {
                Row (
                    modifier = Modifier
                        .noRippleClickable{
                            onlocation_Changr_Click()
                        }
                    , verticalAlignment = Alignment.CenterVertically
                    , horizontalArrangement = Arrangement.Center
                ){
                    SubcomposeAsyncImage(
                        model = R.drawable.locationpinrento,
                        "",
                        modifier = Modifier
                            .size(12.dp)
                    )

                    Spacer(modifier = Modifier.padding(2.dp))

                    Text(AppPreferences.get_User_Location()
                        , color = Color.White
                        , fontSize = constants.textUnit(12)
                        , fontFamily = constants.fontFamily(2)
                    )

                    Image(painterResource(R.drawable.arrowdown) , "",
                        colorFilter = ColorFilter.tint(Color.White))
                }
            }
            , trailingContent = {
                Box {
                    SubcomposeAsyncImage(
                        model = R.drawable.homenotirento,
                        "",
                        modifier = Modifier
                            .size(if (forTab()) 32.dp else 24.dp)
                            .noRippleClickable {
                                constants.sharedHelper.putBoolean(
                                    constants.activity,
                                    constants.notificationiconEnable,
                                    false
                                )
                                AppPreferences.save_Noti_Post_Id("")
                                navHostController.navigate(VideosScreenFlow.In_App_Notification.route)
                            }
                    )

                    if(constants.sharedHelper.getBoolean(constants.activity, constants.notificationiconEnable)) {
                        Image(
                            painter = painterResource(R.drawable.baseline_circle_24),
                            contentDescription = "",
                            modifier = Modifier
                                .size(10.dp)
                                .align(Alignment.TopEnd)
                        )
                    }

                }
            }
            , colors = ListItemColors(
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
                .then(
                    if (forTab()) {
                        Modifier.height(110.dp)
                    } else Modifier
                )
                .padding(top = if (forTab()) 16.dp else notchPadding.value)
        )
    }
}

fun String?.toInstantOrNull(): Instant? =
    try {
        if (this.isNullOrBlank()) null else Instant.parse(this)
    } catch (e: DateTimeParseException) {
        null
    }

fun LastReply.toMainComment(): Get_Main_Comments_Data {
    return Get_Main_Comments_Data(
        comment_id = this.comment_id ?: 0,
        user_id = this.user_id?:0,
        comment = this.comment ?: "",
        created_at = this.created_at ?: "",
        username = this.username ?: "",
        profile_image = this.profile_image ?: "",
        like_count = this.like_count?: 0,
        is_liked = this.is_liked?: 0,
        author = this.author?: 0,
        total_reply = 0,
        last_reply = emptyList(),
        is_report = 0
    )
}

fun Get_Reply_Comments_Data.toMainComment2(): Get_Main_Comments_Data {
    return Get_Main_Comments_Data(
        comment_id = this.comment_id ?: 0,
        user_id = this.user_id?:0,
        comment = this.comment ?: "",
        created_at = this.created_at ?: "",
        username = this.username ?: "",
        profile_image = this.profile_image ?: "",
        like_count = this.like_count?: 0,
        is_liked = this.is_liked?: 0,
        author = this.author?: 0,
        total_reply = 0,
        last_reply = emptyList(),
        is_report = 0
    )
}

@Composable
fun CustomOutlinedTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    leadingIcon: (@Composable (() -> Unit))? = null,
    focusRequester: FocusRequester = FocusRequester(),
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        leadingIcon = leadingIcon,
        modifier = modifier.focusRequester(focusRequester),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        shape = RoundedCornerShape(8.dp),
        singleLine = false,
        maxLines = 4,

    )
}

@Composable
fun TrulyCustomOutlinedTextField1(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    leadingIcon: (@Composable (() -> Unit))? = null,
    focusRequester: FocusRequester = FocusRequester(),
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .focusRequester(focusRequester)
            .border(
                width = 1.dp,
                color = if (value.text.isEmpty()) Color(0xffB8B8B8) else Color(0xFF1D9BF0),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp),
        textStyle = LocalTextStyle.current.copy(color = Color.Black),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (leadingIcon != null) {
                    leadingIcon()
                    Spacer(Modifier.width(8.dp))
                }
                Box(Modifier.weight(1f)) {
                    if (value.text.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = Color.Gray
                        )
                    }
                    innerTextField()
                }
            }
        }
    )
}

@Composable
fun TrulyCustomOutlinedTextField2(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    leadingIcon: (@Composable (() -> Unit))? = null,
    focusRequester: FocusRequester = FocusRequester(),
    onBackspaceAtEmpty: (() -> Unit)? = null
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .focusRequester(focusRequester)
            .border(
                width = 1.dp,
                color = if (value.text.isEmpty()) Color(0xffB8B8B8) else Color(0xFF1D9BF0),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp)
            .onKeyEvent { keyEvent ->
                if (
                    keyEvent.type == KeyEventType.KeyDown &&
                    keyEvent.key == Key.Backspace &&
                    value.text.isEmpty()
                ) {
                    onBackspaceAtEmpty?.invoke()
                    true
                } else {
                    false
                }
            },
        textStyle = LocalTextStyle.current.copy(color = Color.Black),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (leadingIcon != null) {
                    leadingIcon()
                    Spacer(Modifier.width(8.dp))
                }
                Box(Modifier.weight(1f)) {
                    if (value.text.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = Color.Gray
                        )
                    }
                    innerTextField()
                }
            }
        }
    )
}

@Composable
fun TrulyCustomOutlinedTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    leadingIcon: (@Composable (() -> Unit))? = null,
    focusRequester: FocusRequester = FocusRequester(),
    onBackspaceAtEmpty: (() -> Unit)? = null
) {
    var oldValue by remember { mutableStateOf(value) }

    BasicTextField(
        value = value,
        onValueChange = { newValue ->

            if (oldValue.text.isNotEmpty() && newValue.text.isEmpty()) {
                onBackspaceAtEmpty?.invoke()
            }
            oldValue = newValue
            onValueChange(newValue)
        },
        modifier = modifier
            .focusRequester(focusRequester)
            .border(
                width = 1.dp,
                color = if (value.text.isEmpty()) Color(0xffB8B8B8) else Color(0xFF1D9BF0),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp)
            .onKeyEvent { keyEvent ->
                if (
                    keyEvent.type == KeyEventType.KeyDown &&
                    keyEvent.key == Key.Backspace &&
                    value.text.isEmpty()
                ) {
                    onBackspaceAtEmpty?.invoke()
                    true
                } else {
                    false
                }
            },
        textStyle = LocalTextStyle.current.copy(color = Color.Black),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (leadingIcon != null) {
                    leadingIcon()
                    Spacer(Modifier.width(8.dp))
                }
                Box(Modifier.weight(1f)) {
                    if (value.text.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = Color.Gray
                        )
                    }
                    innerTextField()
                }
            }
        }
    )
}

private const val SENTINEL = "\u200B"

@Composable
fun SentinelTextField4(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onBackspaceAtEmpty: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    leadingIcon: (@Composable (() -> Unit))? = null,
    focusRequester: FocusRequester = FocusRequester()
) {

    var internalValue by remember {
        mutableStateOf(TextFieldValue(text = SENTINEL, selection = TextRange(SENTINEL.length)))
    }

    LaunchedEffect(key1 = value.text) {
        val currentExposed = internalValue.text.replace(SENTINEL, "")
        if (value.text != currentExposed) {
            internalValue = if (value.text.isEmpty()) {
                TextFieldValue(text = SENTINEL, selection = TextRange(SENTINEL.length))
            } else {
                val newInternal = SENTINEL + value.text

                TextFieldValue(text = newInternal, selection = TextRange(newInternal.length))
            }
        }
    }

    BasicTextField(
        value = internalValue,
        onValueChange = { newValue ->

            if (newValue.text.isEmpty()) {
                onBackspaceAtEmpty()

                internalValue = TextFieldValue(text = SENTINEL, selection = TextRange(SENTINEL.length))

                onValueChange(TextFieldValue(""))
                return@BasicTextField
            }

            if (newValue.text == SENTINEL) {
                internalValue = TextFieldValue(text = SENTINEL, selection = TextRange(SENTINEL.length))
                onValueChange(TextFieldValue(""))
                return@BasicTextField
            }

            internalValue = newValue

            val exposedText = newValue.text.replace(SENTINEL, "")

            val selStart = (newValue.selection.min - SENTINEL.length).coerceAtLeast(0)
            val selEnd = (newValue.selection.max - SENTINEL.length).coerceAtLeast(0)
            onValueChange(TextFieldValue(text = exposedText, selection = TextRange(selStart, selEnd)))
        },
        modifier = modifier
            .focusRequester(focusRequester)
            .border(
                1.dp,
                if (value.text.isEmpty()) Color(0xffB8B8B8) else Color(0xFF1D9BF0),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            )
            .padding(12.dp),
        textStyle = LocalTextStyle.current.copy(color = Color.Black),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (leadingIcon != null) {
                    leadingIcon()
                    Spacer(Modifier.width(8.dp))
                }

                Box(Modifier.weight(1f)) {

                    if (internalValue.text == SENTINEL) {
                        Text(text = placeholder, color = Color.Gray)
                    }
                    innerTextField()
                }
            }
        }
        , singleLine = true
    )
}

@Composable
fun SentinelTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onBackspaceAtEmpty: () -> Unit,
    mentionName: String? = null,
    isLoading : Boolean,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    leadingIcon: (@Composable (() -> Unit))? = null,
    focusRequester: FocusRequester = FocusRequester()
) {

    var internalValue by remember {
        mutableStateOf(TextFieldValue(text = SENTINEL, selection = TextRange(SENTINEL.length)))
    }

    LaunchedEffect(key1 = value.text) {
        val currentExposed = internalValue.text.replace(SENTINEL, "")
        if (value.text != currentExposed) {
            internalValue = if (value.text.isEmpty()) {
                TextFieldValue(text = SENTINEL, selection = TextRange(SENTINEL.length))
            } else {
                val newInternal = SENTINEL + value.text
                TextFieldValue(text = newInternal, selection = TextRange(newInternal.length))
            }
        }
    }

    BasicTextField(
        value = internalValue,
        onValueChange = { newValue ->
            if (!isLoading) {

                val prevText = internalValue.text
                val prevSelStart = internalValue.selection.start

                val newText = newValue.text
                val newSelStart = newValue.selection.start

                if (newText == prevText) {
                    if (newSelStart != prevSelStart) {
                        val exposed = prevText.removePrefix(SENTINEL)
                        val sel = (newSelStart - SENTINEL.length).coerceAtLeast(0)
                        internalValue = newValue.copy(
                            selection = TextRange(newSelStart.coerceAtLeast(SENTINEL.length))
                        )
                        onValueChange(TextFieldValue(exposed, TextRange(sel)))
                    }
                    return@BasicTextField
                }

                if (newText.isEmpty() || !newText.startsWith(SENTINEL)) {
                    onBackspaceAtEmpty()
                    internalValue =
                        TextFieldValue(text = SENTINEL, selection = TextRange(SENTINEL.length))
                    onValueChange(TextFieldValue(""))
                    return@BasicTextField
                }

                val isDeletion = newText.length < prevText.length
                if (isDeletion) {
                    val minLen = minOf(prevText.length, newText.length)
                    var diffStart = 0
                    while (diffStart < minLen && prevText[diffStart] == newText[diffStart]) diffStart++

                    if (diffStart < SENTINEL.length || (prevSelStart == SENTINEL.length && diffStart == SENTINEL.length)) {
                        onBackspaceAtEmpty()
                        internalValue =
                            TextFieldValue(text = SENTINEL, selection = TextRange(SENTINEL.length))
                        onValueChange(TextFieldValue(""))
                        return@BasicTextField
                    }
                }

                internalValue = newValue.copy(
                    selection = TextRange(newSelStart.coerceAtLeast(SENTINEL.length))
                )
                val exposedText = newText.removePrefix(SENTINEL)
                val selStart = (newValue.selection.min - SENTINEL.length).coerceAtLeast(0)
                val selEnd = (newValue.selection.max - SENTINEL.length).coerceAtLeast(0)
                onValueChange(
                    TextFieldValue(
                        text = exposedText,
                        selection = TextRange(selStart, selEnd)
                    )
                )
            }
        }

        ,

        modifier = modifier
            .focusRequester(focusRequester)
            .border(
                1.dp,
                if (value.text.isEmpty()) Color(0xffB8B8B8) else Color(0xFF1D9BF0),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            )
            .padding(12.dp),
        textStyle = LocalTextStyle.current.copy(color = Color.Black),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (leadingIcon != null) {
                    leadingIcon()
                    Spacer(Modifier.width(8.dp))
                }

                Box(Modifier.weight(1f)) {
                    if (internalValue.text == SENTINEL) {
                        Text(text = placeholder, color = Color.Gray)
                    }
                    innerTextField()
                }
            }
        },
        singleLine = true
    )
}

@Composable
fun isKeyboardOpen(): Boolean {
    val ime = WindowInsets.ime
    return ime.getBottom(LocalDensity.current) > 0
}

data class ReplyTarget(
    val parentCommentId: Int,
    val replyToCommentId: Int,
    val replyToUsername: String,
    val replyToUserId: Int

)

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("RememberReturnType")
@Composable
fun Comment_Structure(
    videos: List<Get_Reels_Data>,
    pagerState: Int,
    navController: NavHostController,
    viewModel: Common_H_ViewModel
) {

    val network = rememberNetworkStatus()

    var retry by remember { mutableStateOf(0) }
    var failure = remember { mutableStateOf(false) }

    val isLoadingMC = constants.API_Vm.isLoading_MComments
    val errorMessageMC = constants.API_Vm.errorMessage_MComments
    val currentPageMC = constants.API_Vm.currentPage_MComments
    val totalPagesMC = constants.API_Vm.totalPages_MComments

    val isLoadingRC = constants.API_Vm.isLoading_RComments
    val errorMessageRC = constants.API_Vm.errorMessage_RComments
    val currentPageRC = constants.API_Vm.currentPage_RComments
    val totalPagesRC = constants.API_Vm.totalPages_RComments
    val currentLoadingCommentId = constants.API_Vm.currentLoadingCommentId

    val listState = rememberLazyListState()

    var cmt_Id = remember { mutableStateOf(0) }

    val comment_Btm_Close = constants.Reels_ViewModel.comment_Btm_Close.collectAsState()

    val scope = rememberCoroutineScope()

    if (network.value == NetworkStatus.Online) {
        LaunchedEffect(constants.Reels_ViewModel.get_what_api() , retry) {

            constants.API_Vm.load_Reels_MComments(
                user_id = AppPreferences.getUserId(),
                user_post_id = videos[pagerState].user_post_id,
                page = 1
            )
        }

        LaunchedEffect(listState, currentPageMC, isLoadingMC, totalPagesMC ) {
            snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                .collect { lastVisibleItemIndex ->
                    val totalItems = listState.layoutInfo.totalItemsCount
                    val loadMoreThreshold = 2

                    if (
                        lastVisibleItemIndex != null &&
                        totalItems > 0 &&
                        lastVisibleItemIndex >= totalItems - loadMoreThreshold &&
                        !isLoadingMC &&
                        currentPageMC < totalPagesMC
                    ) {
                        constants.API_Vm.load_Reels_MComments(
                            user_id = AppPreferences.getUserId(),
                            user_post_id = videos[pagerState].user_post_id,
                            page = currentPageMC + 1
                        )
                    }
                }

        }

    }
    else {
        GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
    }

    val commentList = constants.Reels_ViewModel.main_Comments.collectAsStateWithLifecycle()
    val replyCommentsMap = constants.Reels_ViewModel.reply_CommentsMap.collectAsStateWithLifecycle()

    val expandedCommentIds = remember { mutableStateSetOf<Int>() }

    var inputText by remember { mutableStateOf(TextFieldValue("")) }

    var replyingToIndex by remember { mutableStateOf<Pair<Int, Int?>?>(null) }

    var replyTarget by remember { mutableStateOf<ReplyTarget?>(null) }

    val focusRequester = remember { FocusRequester() }

    val isEditMode by constants.Reels_ViewModel.edit_Comment_State.collectAsStateWithLifecycle()

    var report_BS = remember { mutableStateOf(false) }

    val report_Options = constants.Profile_ViewModel.profile_Report_Options.collectAsState()

    val report_success = constants.Profile_ViewModel.report_Submit_Success.collectAsState()

    var commentapiloading by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        onDispose {
            constants.Reels_ViewModel.edit_Comment_Disable()
            inputText = TextFieldValue("")
            replyingToIndex = null
        }
    }

    Column(modifier = Modifier
        .fillMaxHeight(.8f)
        .fillMaxWidth()

        .background(newWhite)
    )
    {

        Text("Comment" ,
            fontSize = constants.textUnit(20),
            fontFamily = constants.fontFamily(0)
            , modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

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
                            Text(constants.activity.getString(R.string.no_Internet)
                                , color = newBlack,fontSize = constants.textUnit(16)
                                , fontFamily = constants.fontFamily(0)
                                , textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(16.dp)
                            )
                        }
                    }

                    isLoadingMC && currentPageMC == 1 -> {
                        Column (
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(6f)
                                .padding(8.dp)
                            , verticalArrangement = Arrangement.Center
                            , horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                           LottiAnimation(2)
                        }
                    }

                    commentList.value.isEmpty() && !isLoadingMC-> {
                        Column (
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(6f)
                                .padding(8.dp)
                            , verticalArrangement = Arrangement.Center
                            , horizontalAlignment = Alignment.CenterHorizontally
                        )
                        {
                            AsyncImage(
                                model = R.drawable.emptycommentsrento,
                                "",
                                modifier = Modifier.size(100.dp)
                            )

                            Spacer(modifier = Modifier.padding(8.dp))

                            Text("No comments to show" ,
                                fontSize = constants.textUnit(16),
                                fontFamily = constants.fontFamily(0)
                                , modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                            )

                            Spacer(modifier = Modifier.padding(8.dp))

                            Text("Post the first comment to get the conversation going." ,
                                fontSize = constants.textUnit(14),
                                fontFamily = constants.fontFamily(2)
                                , textAlign = TextAlign.Center
                                , modifier = Modifier
                                    .padding(horizontal = 24.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                        }
                    }

                    !errorMessageMC.isNullOrEmpty()  -> {
                        Box (
                            modifier = Modifier
                                .weight(6f)
                                .fillMaxWidth()
                                .padding(8.dp)
                            ,contentAlignment = Alignment.Center
                        ) {
                            API_Fail_UI(onReTryClick = {
                                retry = retry + 213435
                                constants.API_Vm.errorMessage_MComments = ""
                            })
                        }
                    }

                    commentList.value.isNotEmpty() -> {
                        val sortedComments = commentList.value.sortedByDescending {
                            it.created_at.toInstantOrNull() ?: Instant.EPOCH
                        }

                        LazyColumn(
                            state = listState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(6f)

                                .padding(8.dp)
                        )
                        {
                            itemsIndexed(
                                sortedComments,
                                key = { _, comment -> comment.comment_id }

                            )
                            { index, comment ->
                                val repliesSorted = (replyCommentsMap.value[comment.comment_id] ?: emptyList())
                                    .sortedBy { it.created_at.toInstantOrNull() ?: Instant.EPOCH }

                                val isExpanded = expandedCommentIds.contains(comment.comment_id)

                                val isThisCommentLoading = isLoadingRC && currentLoadingCommentId == comment.comment_id

                                CommentItemView(
                                    commentText = comment.comment,
                                    comment = comment,
                                    onReplyClick = {
                                        replyingToIndex = index to null

                                        replyTarget = ReplyTarget(
                                            parentCommentId = comment.comment_id,
                                            replyToCommentId = comment.comment_id,
                                            replyToUsername = comment.username,
                                            replyToUserId = comment.user_id
                                        )
                                    },
                                    userPostId = videos[pagerState].user_post_id,
                                    navController = navController,
                                    mentionUsername = "",
                                    report_BS = report_BS,
                                    post_User_Id = videos[pagerState].user_id,
                                    viewModel = viewModel,
                                    isEditMode = isEditMode
                                )

                                comment.last_reply.firstOrNull()?.let { preview ->
                                    if (!preview.comment.isNullOrEmpty()) {

                                        preview.toMainComment().let {
                                            CommentItemView(
                                                commentText = preview.comment ?: "",
                                                it,
                                                isReply = true,
                                                onReplyClick = {
                                                    replyingToIndex = index to null
                                                    replyTarget = ReplyTarget(
                                                        parentCommentId = preview?.comment_id ?: 0,
                                                        replyToCommentId = preview?.comment_id ?: 0,
                                                        replyToUsername = preview?.username ?: "",
                                                        replyToUserId = preview?.user_id ?: 0
                                                    )
                                                },
                                                userPostId = videos[pagerState].user_post_id,
                                                navController,
                                                preview.mention_username,
                                                report_BS,
                                                post_User_Id = videos[pagerState].user_id
                                                ,viewModel,
                                                isEditMode = isEditMode
                                            )

                                        }

                                        AnimatedVisibility(
                                            visible = isExpanded
                                            , exit = slideOutVertically (tween(300)){ -it }
                                        )
                                        {
                                            Column {

                                                repliesSorted.filter { it.comment_id != preview?.comment_id }
                                                    .forEach { reply2 ->

                                                        CommentItemView(
                                                            commentText = reply2.comment,
                                                            comment = reply2.toMainComment2(),
                                                            isReply = true,
                                                            onReplyClick = {
                                                                replyingToIndex = index to null
                                                                replyTarget = ReplyTarget(
                                                                    parentCommentId = comment.comment_id,
                                                                    replyToCommentId = reply2.comment_id,
                                                                    replyToUsername = reply2.username,
                                                                    replyToUserId = reply2.user_id
                                                                )
                                                            },
                                                            userPostId = videos[pagerState].user_post_id,
                                                            navController = navController,
                                                            mentionUsername = preview.mention_username,
                                                            report_BS = report_BS,
                                                            post_User_Id = videos[pagerState].user_id
                                                            ,viewModel,
                                                            isEditMode = isEditMode
                                                        )
                                                    }
                                            }
                                        }

                                        if (comment.total_reply > 1) {
                                            if (!isExpanded) {

                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(
                                                            start = 48.dp,
                                                            top = 4.dp,
                                                            bottom = 8.dp
                                                        )
                                                        .noRippleClickable {
                                                            if (network.value == NetworkStatus.Online) {

                                                                constants.API_Vm.isLoading_MComments =
                                                                    false
                                                                constants.API_Vm.isLoading_RComments =
                                                                    true
                                                                constants.API_Vm.resetReplyPagination()
                                                                constants.Reels_ViewModel.disable_what_api()

                                                                constants.API_Vm.load_Reels_RComments(
                                                                    user_id = AppPreferences.getUserId(),
                                                                    user_post_id = videos[pagerState].user_post_id,
                                                                    comment_id = comment.comment_id,
                                                                    page = 1
                                                                )
                                                                expandedCommentIds.add(comment.comment_id)
                                                            } else {
                                                                GlobalSnackbar.show(
                                                                    constants.activity.getString(
                                                                        R.string.no_Internet
                                                                    )
                                                                )
                                                            }
                                                        },
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    HorizontalDivider(modifier = Modifier.weight(1f))
                                                    if (isThisCommentLoading || isLoadingRC) {
                                                        CircularProgressIndicator(
                                                            modifier = Modifier.size(20.dp),
                                                            color = newBlue
                                                        )
                                                    } else {
                                                        Text(
                                                            "View ${comment.total_reply} replies",
                                                            fontSize = constants.textUnit(12),
                                                            color = Color.Gray,
                                                            modifier = Modifier.padding(horizontal = 8.dp)
                                                        )
                                                    }
                                                }
                                            }
                                            else {

                                                val hasMorePages = currentPageRC < totalPagesRC

                                                Column {

                                                    if (hasMorePages) {
                                                        Row(
                                                            modifier = Modifier
                                                                .fillMaxWidth()
                                                                .padding(
                                                                    start = 48.dp,
                                                                    top = 4.dp,
                                                                    bottom = 8.dp
                                                                )
                                                                .noRippleClickable {
                                                                    if (network.value == NetworkStatus.Online && !isThisCommentLoading) {
                                                                        constants.API_Vm.load_Reels_RComments(
                                                                            user_id = AppPreferences.getUserId(),
                                                                            user_post_id = videos[pagerState].user_post_id,
                                                                            comment_id = comment.comment_id,
                                                                            page = currentPageRC + 1
                                                                        )
                                                                    } else if (network.value != NetworkStatus.Online) {
                                                                        GlobalSnackbar.show(
                                                                            constants.activity.getString(
                                                                                R.string.no_Internet
                                                                            )
                                                                        )
                                                                    }
                                                                },
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            HorizontalDivider(modifier = Modifier.weight(1f))
                                                            if (isThisCommentLoading) {
                                                                CircularProgressIndicator(
                                                                    modifier = Modifier.size(20.dp),
                                                                    color = newBlue
                                                                )
                                                            } else {
                                                                Text(
                                                                    "Load more replies",
                                                                    fontSize = constants.textUnit(12),
                                                                    color = Color.Gray,
                                                                    modifier = Modifier.padding(horizontal = 8.dp)
                                                                )
                                                            }
                                                        }
                                                    }
                                                    else {

                                                        Row(
                                                            modifier = Modifier
                                                                .fillMaxWidth()
                                                                .padding(
                                                                    start = 48.dp,
                                                                    top = 4.dp,
                                                                    bottom = 8.dp
                                                                )
                                                                .noRippleClickable {
                                                                    expandedCommentIds.remove(
                                                                        comment.comment_id
                                                                    )
                                                                    constants.API_Vm.resetReplyPagination()
                                                                },
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            HorizontalDivider(modifier = Modifier.weight(1f))
                                                            Text(
                                                                "View less replies",
                                                                fontSize = constants.textUnit(12),
                                                                color = Color.Gray,
                                                                modifier = Modifier.padding(horizontal = 8.dp)
                                                            )
                                                        }
                                                    }

                                                }
                                            }
                                        }

                                    }
                                }

                            }

                        }
                    }

                }

            HorizontalDivider()

        val replyToName = replyTarget?.replyToUsername

        LaunchedEffect(replyingToIndex) {
            replyToName?.let {
                focusRequester.requestFocus()
            }
        }

        LaunchedEffect(isEditMode) {
            if (isEditMode) {
                val editId = constants.Reels_ViewModel.get_Edit_Clicked_Comment_Id()

                val txt = constants.Reels_ViewModel
                    .getCommentTextById(editId)
                    .orEmpty()

                inputText = TextFieldValue(txt)

                focusRequester.requestFocus()

            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(if (isKeyboardOpen()) 1.5f else 1f)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
            )
            {
                SentinelTextField(
                    value = inputText,
                    onValueChange = {
                        if (!commentapiloading) {
                            inputText = it
                        }
                    },
                    placeholder = "Add a comment...",
                    leadingIcon = if (replyToName != null) {
                        {
                            Text(
                                "@${replyToName}",
                                color = newBlue,
                                modifier = Modifier
                                    .horizontalScroll(rememberScrollState())
                                    .padding(start = 4.dp)
                            )
                        }
                    } else null,
                    onBackspaceAtEmpty = {

                        replyingToIndex = null
                        replyTarget = null
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .border(
                            1.dp,
                            if (inputText.text.isEmpty()) Color(0xffB8B8B8) else newBlue,
                            RoundedCornerShape(8.dp)
                        )
                        .focusRequester(focusRequester),
                    mentionName = replyToName,
                    isLoading = commentapiloading,

                )

                if (commentapiloading){
                    CircularProgressIndicator(color = newBlue)
                }
                else {
                    AsyncImage(
                        model = R.drawable.rentosend,
                        contentDescription = "",
                        modifier = Modifier
                            .size(48.dp)
                            .noRippleClickable {
                                val text = inputText.text.trim()

                                if (text.isNotEmpty()) {

                                    val parentCommentId = replyingToIndex?.first?.let { topIndex ->
                                        commentList.value.getOrNull(topIndex)?.comment_id
                                    } ?: 0

                                    val expectedMention = replyToName?.let { "@$it " } ?: ""
                                    val hasValidMention =
                                        replyingToIndex != null && inputText.text.startsWith(
                                            expectedMention
                                        )

                                    val finalComment = if (hasValidMention) {
                                        inputText
                                    } else {
                                        inputText
                                    }

                                    val mentionId = replyingToIndex?.let { (topIndex, subIndex) ->
                                        if (subIndex != null) {

                                            replyCommentsMap.value[commentList.value[topIndex].comment_id]
                                                ?.getOrNull(subIndex)?.user_id ?: 0
                                        } else {

                                            commentList.value.getOrNull(topIndex)?.user_id ?: 0
                                        }
                                    } ?: 0

                                    val parentCommentId2 = replyTarget?.parentCommentId ?: 0
                                    val replyToCommentId = replyTarget?.replyToCommentId ?: 0
                                    val mentionUsername = replyTarget?.replyToUsername.orEmpty()

                                    if (network.value == NetworkStatus.Online) {
                                        constants.API_Vm.put_Comment(
                                            user_id = AppPreferences.getUserId(),
                                            user_post_id = videos[pagerState].user_post_id,
                                            status = if (!isEditMode) "1" else "2",
                                            comment = finalComment.text,
                                            comment_id = if (!isEditMode) 0 else constants.Reels_ViewModel.get_Edit_Clicked_Comment_Id(),

                                            replies_comment_id = parentCommentId2,
                                            mention_id = replyTarget?.replyToUserId
                                                ?: 0
                                        )
                                        { result ->
                                            when (result) {
                                                is API_Result_Handling.NoData -> {
                                                    commentapiloading = false

                                                }

                                                is API_Result_Handling.Error -> {
                                                    GlobalSnackbar.show("Something went wrong")
                                                    constants.Reels_ViewModel.disable_Close_CommentBtm()
                                                    inputText = TextFieldValue("")

                                                    replyTarget = null
                                                    commentapiloading = false
                                                }

                                                is API_Result_Handling.Success -> {
                                                    val newComment =
                                                        constants.Reels_ViewModel.get_new_Comment()

                                                    if (!constants.Reels_ViewModel.get_Edit_Comment_State()) {
                                                        if (parentCommentId == 0) {

                                                            constants.Reels_ViewModel.set_MComments_Content(
                                                                listOf(
                                                                    Get_Main_Comments_Data(
                                                                        author = newComment?.author
                                                                            ?: 0,
                                                                        comment = newComment?.comment
                                                                            ?: "",
                                                                        comment_id = newComment?.comment_id
                                                                            ?: 0,
                                                                        created_at = newComment?.created_at
                                                                            ?: "",
                                                                        is_liked = newComment?.is_liked
                                                                            ?: 0,
                                                                        last_reply = emptyList(),
                                                                        like_count = newComment?.like_count
                                                                            ?: 0,
                                                                        profile_image = newComment?.profile_image
                                                                            ?: "",
                                                                        user_id = newComment?.user_id
                                                                            ?: 0,
                                                                        username = newComment?.username
                                                                            ?: "",
                                                                        total_reply = newComment?.total_reply
                                                                            ?: 0,
                                                                        is_report = newComment?.is_report
                                                                            ?: 0
                                                                    )
                                                                ) + commentList.value
                                                            )

                                                            constants.Search_ViewModel.increaseCommentCount_Reels_Search(
                                                                constants.Search_ViewModel.get_Post_Id_Search_Cmt_Clicked.value
                                                            )
                                                            constants.Reels_ViewModel.increaseCommentCount_Reels(
                                                                videos[pagerState].user_post_id
                                                            )
                                                            constants.Enquiry_ViewModel.increaseCommentCount_Enquiry(
                                                                videos[pagerState].user_post_id
                                                            )
                                                            constants.Enquiry_ViewModel.increaseCommentCount_SelfEnquiry(
                                                                videos[pagerState].user_post_id
                                                            )

                                                            scope.launch {
                                                                listState.animateScrollToItem(0)
                                                            }
                                                        } else {

                                                            constants.Reels_ViewModel.addReplyToMainComment(
                                                                parentId = parentCommentId,
                                                                newReply = LastReply(
                                                                    author = newComment?.author
                                                                        ?: 0,
                                                                    comment = newComment?.comment
                                                                        ?: "",
                                                                    comment_id = newComment?.comment_id
                                                                        ?: 0,
                                                                    created_at = newComment?.created_at
                                                                        ?: "",
                                                                    is_liked = newComment?.is_liked
                                                                        ?: 0,

                                                                    like_count = newComment?.like_count
                                                                        ?: 0,
                                                                    profile_image = newComment?.profile_image
                                                                        ?: "",
                                                                    user_id = newComment?.user_id
                                                                        ?: 0,
                                                                    username = newComment?.username
                                                                        ?: "",
                                                                    parent_comment_id = parentCommentId,
                                                                    mention_id = newComment?.mention_id
                                                                        ?: 0,
                                                                    mention_username = newComment?.username

                                                                        ?: "",
                                                                    is_report = newComment?.is_report
                                                                        ?: 0
                                                                )
                                                            )

                                                            cmt_Id.value = parentCommentId
                                                            constants.API_Vm.isLoading_RComments =
                                                                false
                                                            constants.API_Vm.totalPages_RComments =
                                                                1

                                                            if (network.value == NetworkStatus.Online) {
                                                                constants.API_Vm.load_Reels_RComments(
                                                                    user_id = AppPreferences.getUserId(),
                                                                    user_post_id = videos[pagerState].user_post_id,
                                                                    comment_id = parentCommentId,
                                                                    page = 1
                                                                )
                                                            }

                                                            if (parentCommentId != 0) {

                                                                constants.API_Vm.resetReplyPagination()
                                                                constants.API_Vm.load_Reels_RComments(
                                                                    user_id = AppPreferences.getUserId(),
                                                                    user_post_id = videos[pagerState].user_post_id,
                                                                    comment_id = parentCommentId,
                                                                    page = 1
                                                                )

                                                                expandedCommentIds.add(
                                                                    parentCommentId2
                                                                )
                                                            }

                                                            constants.Reels_ViewModel.edit_Comment_Disable()
                                                            constants.Search_ViewModel.increaseCommentCount_Reels_Search(
                                                                constants.Search_ViewModel.get_Post_Id_Search_Cmt_Clicked.value
                                                            )
                                                            constants.Reels_ViewModel.increaseCommentCount_Reels(
                                                                videos[pagerState].user_post_id
                                                            )
                                                            constants.Enquiry_ViewModel.increaseCommentCount_Enquiry(
                                                                videos[pagerState].user_post_id
                                                            )
                                                            constants.Enquiry_ViewModel.increaseCommentCount_SelfEnquiry(
                                                                videos[pagerState].user_post_id
                                                            )

                                                        }
                                                    } else {
                                                        constants.Reels_ViewModel.editCommentById(
                                                            newComment?.comment_id ?: 0,
                                                            newComment?.comment ?: ""
                                                        )
                                                        constants.Reels_ViewModel.edit_Comment_Disable()
                                                        inputText = TextFieldValue("")
                                                    }

                                                    constants.Reels_ViewModel.disable_Close_CommentBtm()
                                                    inputText = TextFieldValue("")
                                                    replyTarget = null
                                                    commentapiloading = false

                                                }

                                                is API_Result_Handling.Deactivated -> {

                                                    constants.Reels_ViewModel.disable_Close_CommentBtm()
                                                    commentapiloading = false
                                                }

                                                is API_Result_Handling.Loading -> {
                                                    commentapiloading = true
                                                    constants.Reels_ViewModel.enable_Close_CommentBtm()
                                                }
                                            }
                                        }

                                    }
                                    else {
                                        GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
                                    }

                                    inputText = TextFieldValue("")
                                    replyingToIndex = null

                                }
                                else {
                                    toast("Type Any Comment")
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
                constants.Profile_ViewModel.clearProfileReportSelections()
                report_BS.value = false
            },
            sheetState = sheetState
            , containerColor = newWhite
        )
        {
            Column (
                modifier = Modifier
                    .fillMaxHeight(.8f)
                    .fillMaxWidth()
                , verticalArrangement = Arrangement.SpaceBetween
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
                                            ClickHelper.getInstance().clickOnce {
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
                                                constants.API_Vm.put_Report_All(
                                                    user_id = constants.Profile_ViewModel.user_Id_Report.value,
                                                    user_post_id = videos[pagerState].user_post_id.toString(),
                                                    receiver_id = videos[pagerState].user_id.toString(),
                                                    comment_id = constants.Profile_ViewModel.comment_Id_Report.value.toString(),
                                                    report_sentence_id = (constants.Profile_ViewModel.getSelectedProfileReportOptionId()
                                                        ?.plus(1)) ?: 0,
                                                    report_sentence = user_Manual_report_String.value.ifEmpty { constants.Profile_ViewModel.getSelectedProfileReportOptionDescription()?:"" },
                                                    status = 3,
                                                )
                                                { apiResultHandling ->

                                                    when (apiResultHandling) {
                                                        is API_Result_Handling.Loading -> {

                                                        }

                                                        is API_Result_Handling.Deactivated -> {

                                                        }

                                                        is API_Result_Handling.Error -> {

                                                            report_BS.value = false
                                                            GlobalSnackbar.show("Something went wrong")

                                                        }

                                                        is API_Result_Handling.Success -> {

                                                            toast("Reported Successfully")
                                                            constants.Profile_ViewModel.clearProfileReportSelections()

                                                            constants.Reels_ViewModel.MCommentsReport(constants.Profile_ViewModel.comment_Id_Report.value)

                                                            report_BS.value = false

                                                        }

                                                        is API_Result_Handling.NoData -> {

                                                        }
                                                    }
                                                }

                                            } else {
                                                GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
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
}

@Composable
fun CommentItemView(
    commentText: String,
    comment: Get_Main_Comments_Data,
    isReply: Boolean = false,
    onReplyClick: (() -> Unit)? = null,
    userPostId: Int,
    navController: NavHostController,
    mentionUsername: String?,
    report_BS: MutableState<Boolean>,
    post_User_Id: Int,
    viewModel: Common_H_ViewModel,
    isEditMode : Boolean
) {

    var likeLoad = remember { mutableStateOf(false) }
    val showMenu = remember { mutableStateOf(false) }

    val network = rememberNetworkStatus()

    ListItem(
        headlineContent = {
            Row (
                modifier = Modifier
                    .noRippleClickable{

                        constants.Profile_ViewModel.add_Selected_User_Name(
                            comment.username ?: "username"
                        )

                        constants.Profile_ViewModel.add_BF_Handler(Profile_Handle_Back(
                            current_UsedId = AppPreferences.getUserId(),
                            other_UserId = comment.user_id,
                            ff_User_Name = comment.username ,
                            ff_Fw_Count = 999,
                            ff_Fg_Count = 999,

                        ))

                        constants.Profile_ViewModel.addProfile(comment.user_id)
                        constants.Profile_ViewModel.add_Selected_Profile_Id(id = comment.user_id)

                        viewModel.toggleshowBABars(false)
                        viewModel.toggleshowTABars(false)

                        navController.navigate(
                            VideosScreenFlow.Other_Profile_Structure.route
                        )
                    }
            ){
                Text(
                    text = comment.username,
                    fontSize = constants.textUnit(14)
                )

                if (post_User_Id == AppPreferences.getUserId()){
                    Text(
                        text = "• Owner",
                        fontSize = constants.textUnit(12),
                        color = newBlue
                    )
                }
                else {
                    if (comment.author == 1) {
                        Text(
                            text = "• You",
                            fontSize = constants.textUnit(12),
                            color = newBlue
                        )
                    }
                }

            }

        },
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .noRippleClickable {

                        constants.Profile_ViewModel.add_Selected_User_Name(
                            comment.username ?: "username"
                        )

                        constants.Profile_ViewModel.add_BF_Handler(
                            Profile_Handle_Back(
                                current_UsedId = AppPreferences.getUserId(),
                                other_UserId = comment.user_id,
                                ff_User_Name = comment.username,
                                ff_Fw_Count = 999,
                                ff_Fg_Count = 999,

                            )
                        )

                        constants.Profile_ViewModel.addProfile(comment.user_id)
                        constants.Profile_ViewModel.add_Selected_Profile_Id(id = comment.user_id)

                        viewModel.toggleshowBABars(false)
                        viewModel.toggleshowTABars(false)

                        navController.navigate(
                            VideosScreenFlow.Other_Profile_Structure.route
                        )
                    }
                , contentAlignment = Alignment.Center
            )
            {
                SubcomposeAsyncImage(
                    model = comment.profile_image,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                    , contentDescription = ""
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
                                text = comment.username.takeIf { it.isNotEmpty() }?.take(1)?.uppercase() ?: ""
                            )

                        }
                    } else {
                        SubcomposeAsyncImageContent()
                    }
                }

            }
        },
        supportingContent = {
            Column {
                constants.spacer(2)
                val useColumn = (mentionUsername?.length ?: 0) > 10 || (commentText?.length ?: 0) > 5

                if (useColumn) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                        , horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (!mentionUsername.isNullOrEmpty()) {
                            Text(
                                "@${mentionUsername}",
                                fontSize = constants.textUnit(12),
                                color = newBlue
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                        Text(commentText ?: "", fontSize = constants.textUnit(14))
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            , verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        if (!mentionUsername.isNullOrEmpty()) {
                            Text(
                                "@${mentionUsername}",
                                fontSize = constants.textUnit(12),
                                color = newBlue
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                        Text(commentText ?: "", fontSize = constants.textUnit(14))
                    }
                }

                constants.spacer(2)

                Row(verticalAlignment = Alignment.CenterVertically) {

                    if (likeLoad.value){
                        CircularProgressIndicator(modifier = Modifier.size(12.dp), color = newBlue)
                    }
                    else {
                        Image(painter = painterResource(if ( comment.is_liked == 0) R.drawable.bordered_favorite else R.drawable.reelsliked)
                            , contentDescription = " likeee"
                            , modifier = Modifier
                                .size(14.dp)
                                .noRippleClickable {

                                    if (network.value == NetworkStatus.Online) {
                                        constants.API_Vm.put_Cmt_Like_Dislike(
                                            user_id = comment.user_id,
                                            user_post_id = userPostId,
                                            comment_id = comment.comment_id,
                                            status = if (comment.is_liked == 0) 1 else 2,
                                        )
                                        { apiResultHandling ->
                                            when (apiResultHandling) {
                                                is API_Result_Handling.Error -> {
                                                    likeLoad.value = false
                                                    GlobalSnackbar.show("Something went wrong")
                                                }

                                                is API_Result_Handling.Deactivated -> {

                                                }

                                                is API_Result_Handling.Success -> {
                                                    likeLoad.value = false
                                                    constants.Reels_ViewModel.toggleLike_MComments(
                                                        comment.comment_id
                                                    )
                                                }

                                                is API_Result_Handling.NoData -> {}
                                                is API_Result_Handling.Loading -> {
                                                    likeLoad.value = true
                                                }
                                            }
                                        }

                                    } else {
                                        GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
                                    }

                                }
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${comment.like_count}", fontSize = constants.textUnit(12))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        "Reply",
                        fontSize = constants.textUnit(12),
                        modifier = Modifier.noRippleClickable{ onReplyClick?.invoke() }
                    )
                }
            }
        },
        trailingContent = {

            var replycmtId = if(isReply){
             comment.comment_id
            }
            else {
                0
            }

            val timechange = getTimeAgo(comment.created_at)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text( timechange, fontSize = constants.textUnit(10))
                Spacer(modifier = Modifier.padding(4.dp))
                Icon(painter = painterResource(R.drawable.more_vert)
                    , contentDescription = "Cancel Reply",
                    modifier = Modifier
                        .size(14.dp)
                        .noRippleClickable {
                            if (!isEditMode) {
                            showMenu.value = true
                            }
                        }
                )
            }
            Comments_DropDown(showMenu, comment , userPostId ,report_BS , replycmtId , isEditMode)
        },
        modifier = Modifier
            .padding(start = if (isReply) 48.dp else 0.dp)
            .background(newWhite)
        , colors = ListItemColors(
            containerColor = newWhite,
            headlineColor = Color(0xff666666),
            leadingIconColor = Color.DarkGray,
            overlineColor = Color.Gray,
            supportingTextColor = Color.Black,
            trailingIconColor = Color.Black,
            disabledHeadlineColor = Color.Gray.copy(alpha = 0.5f),
            disabledLeadingIconColor = Color.Gray.copy(alpha = 0.5f),
            disabledTrailingIconColor = Color.Gray.copy(alpha = 0.5f)
        )
    )

}

fun isOlderThan5Min(apiTimestamp: String): Boolean {
    return try {

        val createdAt = Instant.from(DateTimeFormatter.ISO_INSTANT.parse(apiTimestamp))
        val now = Instant.now()

        val diffMinutes = Duration.between(createdAt, now).toMinutes()

        diffMinutes >= 5
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

@Composable
fun Comments_DropDown(
    showMenu: MutableState<Boolean>,
    comment: Get_Main_Comments_Data,
    userPostId: Int,
    report_BS: MutableState<Boolean>,
    replycmtId: Int,
    isEditMode : Boolean
) {

    val network = rememberNetworkStatus()

    data class dropDownMenuItem(
        val title : String
    )
    val dropDownList = remember { mutableListOf("") }
    if (comment.user_id == AppPreferences.getUserId()) {
        dropDownList.clear()

        if (isOlderThan5Min(comment.created_at) == false){
            dropDownList.add("Edit")
        }
         dropDownList.add("Delete")
    }
    else {
        dropDownList.clear()
        dropDownList.add("Report")
    }

    DropdownMenu(
        expanded = showMenu.value,
        onDismissRequest = { showMenu.value = false } ,
        modifier = Modifier
            .wrapContentWidth()

            .background(Color.White)
    ) {
        dropDownList.forEachIndexed { index, item ->
            DropdownMenuItem(
                text = {
                    Row(
                        modifier = Modifier
                            .wrapContentWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            item,
                            fontSize = constants.textUnit(14),

                            color = Color.Black
                        )
                    }
                },
                onClick = {
                    ClickHelper.getInstance().clickOnce {
                        when (item){
                            "Edit" -> {
                                constants.Reels_ViewModel.add_Edit_Clicked_Comment_Id(comment.comment_id)
                                constants.Reels_ViewModel.edit_Comment_Enable()
                            }
                            "Delete" -> {
                                if (network.value == NetworkStatus.Online) {
                                    constants.API_Vm.put_Comment(
                                        user_id = AppPreferences.getUserId(),
                                        user_post_id = userPostId,
                                        status = "3",
                                        comment = "",
                                        comment_id = comment.comment_id,
                                        replies_comment_id = replycmtId,
                                        mention_id = 0
                                    )
                                    { result ->
                                        when (result) {
                                            is API_Result_Handling.NoData -> {}
                                            is API_Result_Handling.Error -> {
                                                toast("Something went wrong")
                                            }
                                            is API_Result_Handling.Deactivated -> {

                                            }
                                            is API_Result_Handling.Success -> {
                                                constants.Reels_ViewModel.deleteCommentById(
                                                    comment.comment_id
                                                )

                                                if (constants.Search_ViewModel.search_Results.value.isNotEmpty()) {
                                                    constants.Search_ViewModel.decreaseCommentCount_Reels_Search(
                                                        userPostId
                                                    )

                                                    constants.Search_ViewModel.decreaseCommentCount_Reels_Search_More(userPostId, comment.total_reply)

                                                }

                                                constants.Enquiry_ViewModel.decreaseCommentCount_Enquiry(userPostId)
                                                constants.Enquiry_ViewModel.decreaseCommentCount_SelfEnquiry(userPostId)

                                                if (comment.total_reply != 0 ){
                                                constants.Reels_ViewModel.decreaseCommentCount_Reels_More(userPostId, comment.total_reply)
                                                }
                                                else {
                                                    constants.Reels_ViewModel.decreaseCommentCount_Reels(userPostId)
                                                }

                                                toast("Comment Deleted Successfully")

                                            }

                                            is API_Result_Handling.Loading -> {}
                                        }
                                    }

                                } else {
                                    GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
                                }
                            }
                            "Report" -> {
                                if (comment.is_report == 0) {
                                    constants.Profile_ViewModel.user_Id_Report.value =
                                        comment.user_id

                                    constants.Profile_ViewModel.comment_Id_Report.value =
                                        if (replycmtId == 0) {
                                            comment.comment_id
                                        }
                                    else {
                                            replycmtId
                                    }

                                    report_BS.value = true

                                    constants.Profile_ViewModel.toggle_ReportSucces_True()
                                }
                                else {
                                    toast("Comment Already Reported")

                                }
                            }
                        }

                        showMenu.value = false
                    }
                }
            )
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverlayPullToRefreshTopOnlyWithVerticalPager() {
    val isRefreshing = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { 5 })
    val pullToRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        state = pullToRefreshState,
        isRefreshing = isRefreshing.value,

        onRefresh = {
            scope.launch {
                isRefreshing.value = true
                delay(2000)
                isRefreshing.value = false
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {

        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(if (page % 2 == 0) Color(0xFFEFEFEF) else Color(0xFFDADADA)),
                contentAlignment = Alignment.Center
            ) {
                Text("Page $page", fontSize = 24.sp)
            }
        }
    }
}

@Composable
fun Common_API_Fail(
    visible: MutableState<Boolean>,
    onReTryClick: (() -> Unit)?
) {
    var isVisibleInUi by remember { mutableStateOf(visible.value) }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.apifailure))

    LaunchedEffect(visible) {
        if (visible.value) {
            isVisibleInUi = true
        } else {

            delay(600)
            isVisibleInUi = false
        }
    }

    val scale by animateFloatAsState(
        targetValue = if (visible.value) 1f else 0.8f,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "popupScale"
    )

    val alpha by animateFloatAsState(
        targetValue = if (visible.value) 1f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "popupAlpha"
    )

    if (isVisibleInUi) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .clickable(false) {}
                .background(Color.Transparent)
            ,contentAlignment = Alignment.Center
        ) {

        }
    }
}

@Composable
fun API_Fail_UI(onReTryClick: (() -> Unit)?){

    Card(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .wrapContentHeight()
            .clip(RoundedCornerShape(8.dp))
        , colors = CardDefaults.cardColors(
            containerColor = newWhite
        ),
        elevation = CardDefaults.cardElevation(48.dp)
    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
            , verticalArrangement = Arrangement.Center
            , horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                , verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painter = painterResource(R.drawable.somethingwentwrongrento) , "")
            }

            constants.spacer(2)

            Text("OOPS ! Something went wrong")

            constants.spacer(2)

            Box(modifier = Modifier
                .width(64.dp)
                .height(36.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(Brush.verticalGradient(newPurpleGradient))
                .border(1.dp , linearGradient(newPurpleGradientBorder),RoundedCornerShape(6.dp) )
                , contentAlignment = Alignment.Center
            ){
                Text("Retry", color = Color.White ,
                    modifier = Modifier.noRippleClickable{
                        onReTryClick?.invoke()
                    }
                )
            }
        }

    }
}

@Composable
fun Common_NoInternet(
    visible: Boolean
) {
    var isVisibleInUi by remember { mutableStateOf(visible) }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.nointernet))

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
                .background(Color.Transparent)
            ,contentAlignment = Alignment.Center
        ) {

            Card(
                modifier = Modifier
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        alpha = alpha
                    )
                    .fillMaxWidth(0.95f)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(8.dp))
                , colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                border = BorderStroke(1.dp , newGray),
                elevation = CardDefaults.cardElevation(48.dp)

            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                    , verticalArrangement = Arrangement.Center
                    , horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                        , verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LottieAnimation(
                            composition = composition,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                            , reverseOnRepeat = true,
                            iterations = LottieConstants.IterateForever
                        )
                    }

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(newBlue.copy(.5f))
                        , contentAlignment = Alignment.Center
                    ){
                        Text("OOPS ! constants.activity.getString(R.string.no_Internet) ")
                    }
                }

            }

        }
    }
}

@Composable
fun LoadingShimmerEffect(type: Int)
{
    val gradient = listOf(
        Color.LightGray.copy(alpha = 0.6f) ,
        Color.LightGray.copy(alpha = 0.2f) ,
        Color.LightGray.copy(alpha = 0.6f)
    )

    val transition = rememberInfiniteTransition()

    val translateAnimation_right_toleft = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            )
        )
    )

    val brush_cross = linearGradient(
        colors = gradient,
        start = Offset.Zero,
        end = Offset(x = translateAnimation_right_toleft.value,
            y = translateAnimation_right_toleft.value)
    )

    when(type)
    {
        1 -> Shimmer(brush = brush_cross)
    }

}
@Composable
fun Shimmer(brush: Brush) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xffF7F0DC),
                            Color(0xffE6C96A)
                        )
                    )
                )

        )
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0xffF7F0DC),
                                Color(0xffE6C96A)
                            )
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .padding(top = 20.dp)
            ) {

                Box(
                    modifier = Modifier.zIndex(2f)
                        .align(Alignment.TopCenter)
                        .clip(CircleShape)
                        .wrapContentSize()
                        .background(Color.White)
                        .padding(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .size(if (forTab()) 92.dp else 80.dp)
                            .clip(CircleShape)
                            .background(brush )
                    )
                    {

                    }
                }

                Column(
                    modifier = Modifier
                        .padding(top = if (forTab()) 46.dp else 40.dp)
                        .zIndex(0f)
                        .fillMaxWidth()
                        .wrapContentHeight()

                        .background(Color.White)
                        .padding(horizontal = 16.dp)
                        .padding(top = 40.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    constants.spacer(2)

                    Text(
                        text = "",
                        color = newBlack,
                        fontSize = constants.textUnit(18),
                        fontFamily = constants.fontFamily(0)
                        , modifier = Modifier .background(brush )
                    )

                    constants.spacer(2)

                    Text(
                        text =  "",
                        color = newGray,
                        fontSize = constants.textUnit(14),
                        fontFamily = constants.fontFamily(2)
                        , modifier = Modifier .background(brush )
                    )

                    ExpandableText(
                        fullText =  "",
                        maxCharacters = 80,
                        modifier = Modifier.fillMaxWidth(.9f)
                            .background(brush )
                    )

                    Spacer(modifier = Modifier.padding(4.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(.9f)
                            .height(if (forTab())64.dp else 56.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                1.dp,
                                newGray,
                                RoundedCornerShape(8.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    )
                    {
                        repeat(3) { itemIndex ->
                            Column(
                                modifier = Modifier.background(brush),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = when (itemIndex) {
                                        0 -> ""
                                        1 -> ""
                                        2 -> ""
                                        else -> "48"
                                    },
                                    color = newBlack,
                                    fontSize = constants.textUnit(14),
                                    fontFamily = constants.fontFamily(0)
                                )

                                Text(
                                    text = when (itemIndex) {
                                        0 -> "Properties"
                                        1 -> "Followers"
                                        2 -> "Following"
                                        else -> "Extra"
                                    },
                                    color = newBlack,
                                    fontSize = constants.textUnit(12),
                                    fontFamily = constants.fontFamily(3)
                                )
                            }

                            if (itemIndex != 2) {
                                VerticalDivider(
                                    modifier = Modifier.padding(
                                        vertical = 8.dp
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.padding(4.dp))

                    constants.spacer(2)
                    constants.spacer(2)

                    Row(
                        modifier = Modifier
                            .background(brush )
                            .fillMaxWidth(.9f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                    }

                    constants.spacer(2)
                    constants.spacer(2)
                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}
