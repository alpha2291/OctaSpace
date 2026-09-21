@file:OptIn(ExperimentalMaterial3Api::class)

package com.toletspot.houseforrent.Home_Screen.Video_Module

import com.toletspot.houseforrent.R
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.ui.compose.PlayerSurface
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.toletspot.houseforrent.Custom_Assets.Comment_Structure
import com.toletspot.houseforrent.Navigation.VideosScreenFlow
import com.toletspot.houseforrent.ui.theme.newBlue
import com.toletspot.houseforrent.ui.theme.newWhite
import kotlinx.coroutines.delay
import android.content.Context
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.key
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.VideoSize
import androidx.media3.exoplayer.ExoPlayer
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.Get_Reels_Data
import com.toletspot.houseforrent.API.API_Result_Handling
import com.toletspot.houseforrent.AppPreferences
import com.toletspot.houseforrent.Custom_Assets.Enquiry_Form_Btm_Sheet_Structure
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.*
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.PlaybackException
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.google.android.gms.maps.model.LatLng
import com.toletspot.houseforrent.API.API_Data_Class.StartUp_DCs.PostUser
import com.toletspot.houseforrent.API.StartUp_API.get_Form_Preview_API_CALL
import com.toletspot.houseforrent.Chat.FirebaseRepository
import com.toletspot.houseforrent.CommonText
import com.toletspot.houseforrent.Custom_Assets.API_Fail_UI
import com.toletspot.houseforrent.Custom_Assets.Backer
import com.toletspot.houseforrent.Custom_Assets.ClickHelper
import com.toletspot.houseforrent.Custom_Assets.Common_Popup
import com.toletspot.houseforrent.Custom_Assets.Mark_As_Sold_Flow
import com.toletspot.houseforrent.Custom_Assets.Static_Bottom
import com.toletspot.houseforrent.Custom_Assets.getTimeAgo
import com.toletspot.houseforrent.Custom_Assets.logD
import com.toletspot.houseforrent.Custom_Assets.rememberNotchHeightDp
import com.toletspot.houseforrent.Custom_Assets.toast
import com.toletspot.houseforrent.GlobalSnackbar
import com.toletspot.houseforrent.Home_Screen.Common_H_ViewModel
import com.toletspot.houseforrent.Home_Screen.Enquiry_Module.toGetReelsData
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.PostFlow
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.logger
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.toSelectedOptionsForm4
import com.toletspot.houseforrent.Home_Screen.ProfileModule.ClickGuard
import com.toletspot.houseforrent.Home_Screen.from_DeepLink_Property
import com.toletspot.houseforrent.Home_Screen.set_FDLP_State
import com.toletspot.houseforrent.Navigation.EnquiriesFlow
import com.toletspot.houseforrent.Navigation.ProfileScreenFlow
import com.toletspot.houseforrent.Navigation.UserCredentialsScreenFlow
import com.toletspot.houseforrent.NetworkStatus
import com.toletspot.houseforrent.Notifications.notification_Comment_Clicked
import com.toletspot.houseforrent.SlideshowAnimation
import com.toletspot.houseforrent.UI_DataClass.PP3_API_DC
import com.toletspot.houseforrent.UI_DataClass.Profile_Handle_Back
import com.toletspot.houseforrent.forTab
import com.toletspot.houseforrent.noRippleClickable
import com.toletspot.houseforrent.rememberNetworkStatus
import com.toletspot.houseforrent.ui.theme.newBlack
import com.toletspot.houseforrent.ui.theme.newGray
import com.toletspot.houseforrent.ui.theme.newLightBlue
import com.toletspot.houseforrent.ui.theme.newLightGray
import com.toletspot.houseforrent.ui.theme.newPurpleGradient
import com.toletspot.houseforrent.ui.theme.newPurpleGradientBorder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch


import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.ViewDetailsFlow
import com.toletspot.houseforrent.Home_Screen.ProfileModule.OwnProfileTab
import com.toletspot.houseforrent.constants
import com.toletspot.houseforrent.ui.theme.new5757
import kotlin.text.ifEmpty

/// type 1 -> own profile in reels
/// type 2 -> other profile in reels
/// type 3 or else -> preview screen when posting
@Composable
fun PhotoRequestAssistant(
    type: Int,
    color: Color,
    receiverPostId: Int,
    navController: NavHostController,
    modifier: Modifier,
    postInterest: Int
)
{
    Column(
        modifier = Modifier
            .fillMaxSize()
        , verticalArrangement = Arrangement.Center
        , horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Image(painterResource(R.drawable.photorequestimage), "")

        constants.spacer(8)

        when {
            type == 1 -> {
                Text(
                    "Add photos to boost property\n visibility!",
                    color = color,
                    fontSize = constants.textUnit(14),
                    fontFamily = constants.fontFamily(1)
                    , textAlign = TextAlign.Center
                )
            }

            type == 2 -> {

                Text(
                    "Hi there! This owner hasn't shared \n photos yet. Shall I ask them for you?",
                    color = color,
                    fontSize = constants.textUnit(14),
                    fontFamily = constants.fontFamily(1)
                    , textAlign = TextAlign.Center
                )
            }

            else -> {


                Text(
                    "Hello, I’m your assistant. You haven’t added photos yet, but I’ll inform you when others request them.",
                    color = color,
                    fontSize = constants.textUnit(14),
                    fontFamily = constants.fontFamily(1)
                    , textAlign = TextAlign.Center
                    , modifier = Modifier.padding(horizontal = 24.dp)
                )
            }
        }

        constants.spacer(8)

        when {
            type == 1 -> {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .border(1.dp , Brush.linearGradient(newPurpleGradientBorder), RoundedCornerShape(8.dp))
                    , contentAlignment = Alignment.Center

                )
                {
                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(horizontal = 8.dp , vertical = 8.dp)
                            .noRippleClickable {
                                constants.Reels_ViewModel.clear_view_pro_Details()
                                constants.PostProperty_ViewModel.setPostFlow(PostFlow.REQUESTMEDIA)

                                navController.navigate(ProfileScreenFlow.Edit_Property_Option.route)
                            }
                        , verticalAlignment = Alignment.CenterVertically
                        , horizontalArrangement = Arrangement.Center
                    ) {

                                Text(
                                    "Add Photos",
                                    color = newBlack,
                                    fontSize = constants.textUnit(14),
                                    fontFamily = constants.fontFamily(1)
                                )

                    }
                }
            }

            type == 2 -> {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (postInterest == 1) Color(0xff323232) else Color.White)
                        .border(1.dp , if (postInterest == 1)Brush.linearGradient(listOf(
                            Color(0xff323232),Color(0xff323232)
                        )) else Brush.linearGradient(newPurpleGradientBorder), RoundedCornerShape(8.dp))
                    , contentAlignment = Alignment.Center

                )
                {
                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(horizontal = 8.dp , vertical = 8.dp)
                            .noRippleClickable {
                                if (postInterest == 1){
                                    GlobalSnackbar.show(
                                        "Request Already sent"
                                    )
                                }
                                else {
                                    constants.API_Vm.requestMedia(
                                        user_id = AppPreferences.getUserId(),
                                        //AppPreferences.getUserId(),
                                        receiver_post_id = receiverPostId
                                        //AppPreferences.get_Post_Id(),
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
                                                constants.Reels_ViewModel.toggleRequestPhotos(
                                                    receiverPostId
                                                )
                                                GlobalSnackbar.show(
                                                    "Request Sent. We’ll notify once user uploads.",
                                                    iconRes = R.drawable.requestsenttoasticonrento
                                                )
                                            }

                                            is API_Result_Handling.Deactivated -> {

                                            }
                                        }
                                    }
                                }
                            }
                        , verticalAlignment = Alignment.CenterVertically
                        , horizontalArrangement = Arrangement.Center
                    ) {
                        if (postInterest == 1){
                            Image(painter = painterResource(R.drawable.chat_single_gray_tick) , "",
                                colorFilter = ColorFilter.tint(Color(0xffFFFFFF)))
                        }
                                Text(
                                    if (postInterest == 1)"Already sent" else "Request Photos",
                                    color = if (postInterest == 1)Color(0xffFFFFFF) else newBlack,
                                    fontSize = constants.textUnit(14),
                                    fontFamily = constants.fontFamily(1)
                                )
                    }
                }
            }

            else -> { }
        }


    }
}


class VideoPlayerManager(private val ctx: Context) {
    private val players = mutableMapOf<Int, ExoPlayer>()
    private val players2 = mutableMapOf<String, ExoPlayer>()

    fun getOrCreatePlayer(index: Int, videoId: Int, uri: String): ExoPlayer {
        return players[index] ?: createNewPlayer(index, videoId, uri)
    }


    @androidx.annotation.OptIn(UnstableApi::class)
    fun getOrCreatePlayer2(index: Int, id: String, url: String): ExoPlayer {
        return players2.getOrPut(id) {
            // 1️⃣ Create custom DataSource with headers
            val dataSourceFactory = DefaultHttpDataSource.Factory()
                .setDefaultRequestProperties(
                    mapOf(
                        "User-Agent" to "Mozilla/5.0 (Linux; Android) AppleWebKit/537.36 Chrome/114.0.0.0 Mobile Safari/537.36",
                        "Referer" to "https://yourwebsite.com" // Optional: your domain if CloudFront requires it
                        // "Authorization" to "Bearer <token>" // Uncomment if using signed URLs
                    )
                )

            // 2️⃣ Create MediaSource using the factory
            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(url))

            // 3️⃣ Build ExoPlayer
            ExoPlayer.Builder(ctx).build().apply {
                setMediaSource(mediaSource)
                prepare()
                playWhenReady = true

                // 4️⃣ Add debug logging for errors
                addListener(object : Player.Listener {
                    override fun onPlayerError(error: PlaybackException) {
                        Log.e("VideoPlayerManager", "❌ Player error for video: $url")
                        Log.e("VideoPlayerManager", "Reason: ${error.errorCodeName}")
                        Log.e("VideoPlayerManager", "Message: ${error.message}", error)
                    }

                    override fun onPlaybackStateChanged(state: Int) {
                        when (state) {
                            Player.STATE_BUFFERING -> Log.d("VideoPlayerManager", "⏳ Buffering $url")
                            Player.STATE_READY -> Log.d("VideoPlayerManager", "✅ Ready $url")
                            Player.STATE_ENDED -> Log.d("VideoPlayerManager", "🔁 Ended $url")
                            Player.STATE_IDLE -> Log.d("VideoPlayerManager", "🕸️ Idle $url")
                        }
                    }

                    override fun onIsPlayingChanged(isPlaying: Boolean) {
                        Log.d("VideoPlayerManager", "▶️ Playing = $isPlaying for $url")
                    }
                })
            }
        }
    }


    private fun createNewPlayer(index: Int, videoId: Int, uri: String): ExoPlayer {
        val player = ExoPlayer.Builder(ctx).build().apply {
            val mediaItem = MediaItem.Builder()
                .setUri(uri)
                .setMediaId("${videoId}_$index")
                .build()
            setMediaItem(mediaItem)
            repeatMode = Player.REPEAT_MODE_ONE
            playWhenReady = false
            addDebugListener(uri)
        }
        players[index] = player
        return player
    }

    // 🔥 Add listener for logging errors and playback state changes
    private fun ExoPlayer.addDebugListener(url: String) {
        addListener(object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                Log.e("VideoPlayerManager", "❌ Player error for video: $url")
                Log.e("VideoPlayerManager", "Reason: ${error.errorCodeName}")
                Log.e("VideoPlayerManager", "Message: ${error.message}", error)
            }

            override fun onPlaybackStateChanged(state: Int) {
                when (state) {
                    Player.STATE_BUFFERING -> Log.d("VideoPlayerManager", "⏳ Buffering $url")
                    Player.STATE_READY -> Log.d("VideoPlayerManager", "✅ Ready $url")
                    Player.STATE_ENDED -> Log.d("VideoPlayerManager", "🔁 Ended $url")
                    Player.STATE_IDLE -> Log.d("VideoPlayerManager", "🕸️ Idle $url")
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                Log.d("VideoPlayerManager", "▶️ Playing = $isPlaying for $url")
            }
        })
    }

    fun getPlayerIfExists(index: Int): ExoPlayer? = players[index]

    fun playVideo(index: Int) {
        players[index]?.let { player ->
            when (player.playbackState) {
                Player.STATE_IDLE -> {
                    player.prepare()
                    player.playWhenReady = true
                }
                Player.STATE_READY -> player.playWhenReady = true
                Player.STATE_BUFFERING -> player.playWhenReady = true
                Player.STATE_ENDED -> {
                    player.seekTo(0)
                    player.playWhenReady = true
                }
            }
        }
    }

    fun pauseVideo(index: Int) {
        players[index]?.playWhenReady = false
    }

    fun pauseAllExcept(currentIndex: Int) {
        players.forEach { (index, player) ->
            if (index != currentIndex) player.playWhenReady = false
        }
    }

    fun prepareVideoIfNeeded(index: Int) {
        players[index]?.let { player ->
            if (player.playbackState == Player.STATE_IDLE) player.prepare()
        }
    }



    fun releaseFarPlayers(currentIndex: Int, keepRange: Int = 3) {
        val keepIndices = (currentIndex - keepRange..currentIndex + keepRange).toSet()
        val toRemove = players.keys.filter { it !in keepIndices }
        toRemove.forEach { releasePlayer(it) }
    }

    fun releasePlayer(index: Int) {
        players.remove(index)?.release()
    }

    fun releaseAll() {
        players.values.forEach { it.release() }
        players.clear()
    }
}


@OptIn(FlowPreview::class)
@Composable
fun ReelsView(
    navController: NavHostController,
    viewModel: Common_H_ViewModel,
    onLogout: () -> Unit
) {
    val network = rememberNetworkStatus()
    var showBABars = viewModel.showBABars.collectAsState()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var from_DLP_State = from_DeepLink_Property.collectAsStateWithLifecycle()


    /// post successfull
    var postSuccess = constants.PostProperty_ViewModel.postSuccessfulBtm.collectAsState()

    val videos by constants.Reels_ViewModel.videos.collectAsState()
    var deactivated by remember { mutableStateOf(false) }
    val playerManager = remember { VideoPlayerManager(context) }

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { videos.size.coerceAtLeast(1) }
    )

    var previousPage by remember { mutableIntStateOf(-1) }

    val isBuffering = remember { mutableStateMapOf<Int, Boolean>() }
    val isPrepared = remember { mutableStateMapOf<Int, Boolean>() }

    val isLoading = constants.API_Vm.isLoading_Reels
    val totalPages = constants.API_Vm.totalPages_Reels
    val nxtPage = constants.API_Vm.nextPage_Reels
    val currentPage = constants.API_Vm.currentPage_Reels

    val error = constants.API_Vm.errorMessage_Reels
    val result = constants.API_Vm.result_Reels

    val send_Eq_State = constants.Reels_ViewModel.send_Enquiry_Btm_Sheet.collectAsState()

    var isLike_Loading = remember { mutableStateOf(false) }
    var isSave_Loading = remember { mutableStateOf(false) }

    val reelsBTMSheetState = constants.Common_H_ViewModel.reelsBtm_sheet.collectAsState()
    val reelsBTMSOptions = constants.Reels_ViewModel.reelsBTMSOptions.collectAsState()

    val cmt_btm_Sheet = constants.Common_H_ViewModel.comment_btm_Sheet.collectAsState()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var report_BS = remember { mutableStateOf(false) }


        // rento
    var notInterested_Btm = remember { mutableStateOf(false) }

    val repost_Btm = remember { mutableStateOf(false) }

    var mark_as_Sold = remember { mutableStateOf(false) }
    var delete_Post by remember { mutableStateOf(false) }

    // rento

    val notInterestedOptions = constants.Profile_ViewModel.notInterestedOptions.collectAsState()



    val report_Options = constants.Profile_ViewModel.profile_Report_Options.collectAsState()


    var renewDisable = remember { mutableStateOf(false) }

    val report_success = constants.Profile_ViewModel.report_Submit_Success.collectAsStateWithLifecycle()
    val sense_Liked = constants.Reels_ViewModel.sense_isLiked.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var retry by remember { mutableIntStateOf(0) }
    val hasLoadedInitially = remember { mutableStateOf(false) }
    var app_Exit by remember { mutableStateOf(false) }

    var activateRentedout = remember { mutableStateOf(false) }
    var activaterenew = remember { mutableStateOf(false) }


    val viewdetailFlow = constants.PostProperty_ViewModel.viewDetailsFlow.collectAsState()



    // Initial data load
    if (network.value == NetworkStatus.Online) {
        LaunchedEffect(Unit) {
            if (videos.isEmpty()) {
                if (!hasLoadedInitially.value) {
                    hasLoadedInitially.value = true
                    constants.API_Vm.load_Reels(
                        AppPreferences.getUserId(),
                        AppPreferences.get_Noti_Post_Id(),
                        1
                    )
                }
            }
        }
    }

    // Retry mechanism
    LaunchedEffect(retry) {
        if (videos.isEmpty()) {
            if (retry > 0 && network.value == NetworkStatus.Online) {
                constants.API_Vm.load_Reels(
                    AppPreferences.getUserId(),
                    AppPreferences.get_Noti_Post_Id(),
                    1
                )
            }
        }
    }

    // Pagination
    LaunchedEffect(pagerState.currentPage) {
        if (network.value == NetworkStatus.Offline || videos.isEmpty()) return@LaunchedEffect

        snapshotFlow { pagerState.currentPage }
            .distinctUntilChanged()
            .collect { page ->
                val threshold = when {
                    videos.size < 5 -> 1
                    videos.size < 10 -> 2
                    else -> 3
                }

                if (page >= videos.size - threshold) {
                    if (!isLoading && nxtPage > 0 && nxtPage <= totalPages && nxtPage != currentPage) {
                        constants.API_Vm.load_Reels(
                            AppPreferences.getUserId(),
                            AppPreferences.get_Noti_Post_Id(),
                            nxtPage
                        )
                    }
                }
            }
    }

    // Page change handling
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress, videos.size) {
        if (videos.isEmpty()) return@LaunchedEffect

        snapshotFlow { pagerState.isScrollInProgress }
            .filter { !it }
            .collect {
                val currentIndex = pagerState.currentPage.coerceIn(0, videos.lastIndex)

                if (currentIndex == previousPage) return@collect

                val currentVideo = videos.getOrNull(currentIndex) ?: return@collect

                playerManager.pauseAllExcept(currentIndex)

                if (!currentVideo.post_property.video.isNullOrEmpty()) {
                    playerManager.getOrCreatePlayer(
                        currentIndex,
                        currentVideo.user_post_id,
                        currentVideo.post_property.video.firstOrNull()?.url ?: ""
                    )
                    playerManager.playVideo(currentIndex)
                }

                val isGoingUp = currentIndex < previousPage
                when {
                    currentIndex == 0 -> {
                        viewModel.toggleshowTABars(true)
                        viewModel.toggleshowBABars(true)
                    }
                    isGoingUp -> {
                        viewModel.toggleshowTABars(true)
                        viewModel.toggleshowBABars(true)
                    }
                    else -> {
                        viewModel.toggleshowTABars(false)
                        viewModel.toggleshowBABars(false)
                    }
                }

                previousPage = currentIndex
            }
    }

    // Lifecycle management
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (videos.isEmpty()) return@LifecycleEventObserver

            val currentIndex = pagerState.currentPage.coerceIn(0, videos.lastIndex)
            val currentVideo = videos.getOrNull(currentIndex) ?: return@LifecycleEventObserver

            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    playerManager.pauseVideo(currentIndex)
                }
                Lifecycle.Event.ON_RESUME -> {
                    if (!currentVideo.post_property.video.isNullOrEmpty()) {
                        playerManager.playVideo(currentIndex)
                    }
                }
                Lifecycle.Event.ON_DESTROY -> {
                    playerManager.releaseAll()
                }
                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            playerManager.releaseAll()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            playerManager.releaseAll()
        }
    }

    var commentCloseDisable = constants.Reels_ViewModel.comment_Btm_Close.collectAsState()

    // Main content rendering
    when {
        result == "2" -> {
            deactivated = true
            viewModel.toggleshowBABars(false)
            viewModel.toggleshowTABars(false)

           FirebaseRepository.setAccountDeleted(
               userId = AppPreferences.getUserId().toString(),
               isDeleted = true,
               onComplete = {
                   println("FIREBASE ACCOUNT DELECTED UPDATED")
               }
           )
        }

        network.value == NetworkStatus.Offline -> {
            viewModel.toggleshowBABars(true)
            viewModel.toggleshowTABars(true)
            constants.Common_H_ViewModel.toggleshowBABars(true)
            constants.Common_H_ViewModel.toggleshowTABars(true)
            LaunchedEffect(Unit) {
                viewModel.toggleshowBABars(true)
                viewModel.toggleshowTABars(true)
                constants.Common_H_ViewModel.toggleshowBABars(true)
                constants.Common_H_ViewModel.toggleshowTABars(true)
            }

            playerManager.releaseAll()
            Column(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painterResource(R.drawable.nointernerdesign), "")
                Spacer(modifier = Modifier.padding(16.dp))
                Text(
                    constants.activity.getString(R.string.no_Internet),
                    color = newBlack,
                    fontSize = constants.textUnit(16),
                    fontFamily = constants.fontFamily(0),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            toast(constants.activity.getString(R.string.no_Internet))
        }

        isLoading && videos.isEmpty() -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }

        !error.isNullOrEmpty() -> {
            playerManager.releaseAll()
            viewModel.toggleshowBABars(true)
            viewModel.toggleshowTABars(true)

            playerManager.releaseAll()

//            FirebaseRepository.setAccountDeleted(
//                userId = AppPreferences.getUserId().toString(),
//                isDeleted = false,
//                onComplete = {
//                    println("FIREBASE ACCOUNT DELECTED UPDATED")
//                }
//            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                API_Fail_UI(onReTryClick = {
                    retry++
                })
            }
        }

        videos.isEmpty() && !isLoading -> {


            Column {
                if (from_DLP_State.value) {
                    viewModel.toggleshowTABars(false)
                    viewModel.toggleshowBABars(false)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 16.dp,
                                top = if (forTab()) 16.dp else rememberNotchHeightDp().value
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {

                        Icon(painter = painterResource(R.drawable.left_arrow), "",
                            tint = Color.White,
                            modifier = Modifier//.padding(top = notchPadding.value)
                                .size(24.dp)
                                .noRippleClickable {
                                    AppPreferences.save_Noti_Post_Id("")
                                    set_FDLP_State(false)
                                    viewModel.selectedBABTab(0)
                                    constants.API_Vm.isLoading_Reels = true
                                    constants.API_Vm.totalPages_Reels = 1
                                    navController.navigate(
                                        UserCredentialsScreenFlow.Common_Screen.route
                                    )
                                })
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Column (Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                        Image(painter = painterResource(R.drawable.errorvideorento) , "",
                            modifier = Modifier.size(200.dp))
                        constants.spacer(8)
                        Text("No videos to display", color = Color.White)
                    }
                }
            }
        }

        videos.isNotEmpty() -> {

            FirebaseRepository.setAccountDeleted(
                userId = AppPreferences.getUserId().toString(),
                isDeleted = false,
                onComplete = {
                    println("FIREBASE ACCOUNT DELECTED UPDATED")
                }
            )
            // FIXED: Removed beyondViewportPageCount to prevent preloading adjacent pages
            VerticalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                key = { page -> videos.getOrNull(page)?.user_post_id ?: page }
                // beyondViewportPageCount removed - this prevents adjacent page rendering
            ) { page ->
                val item = videos.getOrNull(page) ?: return@VerticalPager

                val hasVideo = item.post_property.video?.isNotEmpty()
                val hasImages = item.post_property.images?.isNotEmpty()
                       // && item.post_property.images.any { it.isNotBlank()
                        //}

                // FIXED: Only create player for current settled page
                val player = if (hasVideo == true && page == pagerState.settledPage) {
                    remember(page, item.user_post_id, pagerState.settledPage) {
                        playerManager.getOrCreatePlayer(
                            page,
                            item.user_post_id,
                            item.post_property.video.firstOrNull()?.url ?:""
                        ).also {
                            it.playWhenReady = true
                        }
                    }
                } else null

                // FIXED: Dispose player immediately when not on settled page
                DisposableEffect(page, pagerState.settledPage) {
                    onDispose {
                        if (page != pagerState.settledPage) {
                            player?.playWhenReady = false
                        }
                    }
                }

                if (notification_Comment_Clicked.value) {
                    viewModel.toggleshowTABars(false)
                    viewModel.toggleshowBABars(false)

                    LaunchedEffect(notification_Comment_Clicked.value) {
                        constants.Common_H_ViewModel.enable_Cmt_btm_Sheet()
                    }
                }

                // FIXED: Added fillMaxSize and black background to prevent transparency
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                )
                {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(6f)
                    ) {


                        when {

                            hasImages == true -> {

                                player?.release()
                                playerManager.releaseAll()
                                player?.pause()

                                SingleSlideshowReel2(
                                    item = item,
                                    page = page,
                                    modifier = Modifier
                                        .padding(top = 24.dp)
                                        .fillMaxWidth()
                                        .fillMaxHeight(.8f)
                                        .align(Alignment.Center),
                                    videos = videos,
                                    navController = navController,
                                    pagerState = pagerState,
                                    isLike_Loading = isLike_Loading,
                                    isSave_Loading = isSave_Loading,
                                    intervalMillis = 1000L,
                                    viewModel,
                                )
                                if (viewdetailFlow.value == ViewDetailsFlow.RENEW){
                                    if (!renewDisable.value) {
                                        Box(
                                            modifier = Modifier
                                                .padding(top = 80.dp)
                                                .padding(horizontal = 16.dp)
                                                .fillMaxWidth()
                                                .height(60.dp)
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(Color(0xffF7F0DC))
                                                .border(1.dp, Color(0xffB8B8B8), RoundedCornerShape(6.dp))
                                                .padding(horizontal = 16.dp)
                                            , contentAlignment = Alignment.Center
                                        ) {
//                                            Text(
//                                                buildAnnotatedString {
//                                                    withStyle(SpanStyle(color = newBlack)) { append("Your property expires.") }
////                                        withStyle(SpanStyle(color = Color.Red)) { append("* ") }
//                                                    withStyle(SpanStyle(color = newBlue)) { append("Renew now") }
//                                                },
//                                                fontSize = constants.textUnit(16),
//                                                fontFamily = constants.fontFamily(1),
//                                                modifier = Modifier.align(Alignment.CenterStart)
//                                            )
                                            val annotatedText = buildAnnotatedString {
                                                append("Your property expires. ")

                                                pushStringAnnotation(
                                                    tag = "RENEW",
                                                    annotation = "renew_now"
                                                )
                                                withStyle(
                                                    SpanStyle(
                                                        color = newBlue
                                                    )
                                                ) {
                                                    append("Renew now")
                                                }
                                                pop()
                                            }

                                            ClickableText(
                                                text = annotatedText,
                                                style = TextStyle(
                                                    fontSize = constants.textUnit(16),
                                                    fontFamily = constants.fontFamily(1),
                                                    color = newBlack
                                                ),
                                                modifier = Modifier.align(Alignment.CenterStart),
                                                onClick = { offset ->
                                                    annotatedText.getStringAnnotations(
                                                        tag = "RENEW",
                                                        start = offset,
                                                        end = offset
                                                    ).firstOrNull()?.let {
                                                        // 👇 Handle click here
                                                        // Example:
                                                        println("Renew now clicked")
                                                        activateRentedout.value = true
                                                        // navigateToRenewScreen()
                                                    }
                                                }
                                            )


                                            Image(painter = painterResource(R.drawable.close), "",
                                                colorFilter = ColorFilter.tint(Color.White),
                                                modifier = Modifier.align(Alignment.CenterEnd).noRippleClickable{
                                                    renewDisable.value = true
                                                })
                                        }
                                    }
                                }

                                // Back buttons for image slides
                                if (from_DLP_State.value) {
                                    viewModel.toggleshowTABars(false)
                                    viewModel.toggleshowBABars(false)

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = 16.dp,
                                                top = if (forTab()) 16.dp else rememberNotchHeightDp().value
                                            ),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start
                                    ) {
                                        Image(painter = painterResource(R.drawable.left_arrow) , "",
                                            colorFilter = ColorFilter.tint(Color.White),
                                            modifier = Modifier.noRippleClickable{
                                                AppPreferences.save_Noti_Post_Id("")
                                                set_FDLP_State(false)
                                                viewModel.selectedBABTab(0)
                                                navController.navigate(
                                                    UserCredentialsScreenFlow.Common_Screen.route
                                                )
                                            }
                                        )
//                                        Backer(
//                                            modifier = Modifier,
//                                            onBackClick = {
//                                                AppPreferences.save_Noti_Post_Id("")
//                                                set_FDLP_State(false)
//                                                viewModel.selectedBABTab(0)
//                                                navController.navigate(
//                                                    UserCredentialsScreenFlow.Common_Screen.route
//                                                )
//                                            }
//                                        )
                                    }
                                }
                                if (!from_DLP_State.value) {
                                    if (AppPreferences.get_Noti_Post_Id().isNotEmpty()) {
                                        viewModel.toggleshowTABars(false)
                                        viewModel.toggleshowBABars(false)

                                        BackHandler {
                                            println("WERTTY inside  %%%${from_DLP_State.value}-&&&${AppPreferences.get_Noti_Post_Id()}- ${report_BS.value} -- ${cmt_btm_Sheet.value} -- ${reelsBTMSheetState.value} -- ${send_Eq_State.value}")

                                            when {
                                                !from_DLP_State.value && AppPreferences.get_Noti_Post_Id()
                                                    .isNotEmpty() -> {
                                                    retry = retry + 2345
                                                    viewModel.notification_PostId = ""
                                                    AppPreferences.save_Noti_Post_Id("")
                                                    navController.navigate(VideosScreenFlow.In_App_Notification.route)

                                                }
                                            }
                                        }

                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(
                                                    horizontal = 16.dp,
                                                    vertical = if (forTab()) 16.dp else rememberNotchHeightDp().value
                                                ),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Start
                                        ) {

                                            Image(painter = painterResource(R.drawable.left_arrow) , "",
                                                colorFilter = ColorFilter.tint(Color.White),
                                                modifier = Modifier.noRippleClickable{
                                                    retry = retry + 2345
                                                    viewModel.notification_PostId = ""
                                                    AppPreferences.save_Noti_Post_Id("")
                                                    constants.PostProperty_ViewModel.setViewDetailsFlow(
                                                        ViewDetailsFlow.NONE)
                                                    navController.navigate(VideosScreenFlow.In_App_Notification.route)
                                                }
                                            )
//                                            Backer(
//                                                modifier = Modifier,
//                                                onBackClick = {
//                                                    retry = retry + 2345
//                                                    viewModel.notification_PostId = ""
//                                                    AppPreferences.save_Noti_Post_Id("")
//                                                    navController.navigate(VideosScreenFlow.In_App_Notification.route)
//                                                }
//                                            )
                                        }
                                    }
                                }
                            }

                            hasVideo == true && player != null -> {
                                var isPlayerPlaying by remember(page, item.user_post_id) {
                                    mutableStateOf(false)
                                }

                                // Sync playing state only for current page
                                LaunchedEffect(page, pagerState.settledPage, player) {
                                    if (page == pagerState.settledPage) {
                                        snapshotFlow { player.isPlaying }
                                            .collect { playing ->
                                                isPlayerPlaying = playing
                                            }
                                    } else {
                                        isPlayerPlaying = false
                                    }
                                }

                                // Control playback based on current page
                                LaunchedEffect(page, pagerState.settledPage) {
                                    if (page == pagerState.settledPage) {
                                        delay(100)
                                        playerManager.playVideo(page)
                                    } else {
                                        playerManager.pauseVideo(page)
                                    }
                                }

                                // FIXED: Video content with proper containment
                                key("video_${item.user_post_id}_${page}") {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(Color.Black),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        PlayerSurface(
                                            player = player,
                                            modifier = Modifier
                                                .padding(top = 24.dp)
                                                .fillMaxWidth()
                                                .fillMaxHeight(.7f)
                                                //.aspectRatio(6f / 12f)
                                                .pointerInput(item.user_post_id) {
                                                    detectTapGestures(
                                                        onTap = {
                                                            if (player.isPlaying == true) {
                                                                playerManager.pauseVideo(page)
                                                                isPlayerPlaying = false
                                                            } else {
                                                                playerManager.playVideo(page)
                                                                isPlayerPlaying = true
                                                            }
                                                        },
                                                        onDoubleTap = {
                                                            constants.API_Vm.like_Dislike(
                                                                user_id = AppPreferences.getUserId(),
                                                                user_post_id = item.user_post_id,
                                                                status = if (item.is_liked == 1) 2 else 1
                                                            ) { apiResult ->
                                                                if (apiResult is API_Result_Handling.Success) {
                                                                    val alreadyLiked =
                                                                        constants.Reels_ViewModel.videos.value
                                                                            .firstOrNull { it.user_post_id == item.user_post_id }?.is_liked == 1

                                                                    if (alreadyLiked) {
                                                                        constants.Reels_ViewModel.decreaseLikeCount_Reels(
                                                                            item.user_post_id
                                                                        )
                                                                    } else {
                                                                        constants.Reels_ViewModel.increaseLikeCount_Reels(
                                                                            item.user_post_id
                                                                        )
                                                                    }
                                                                    constants.Reels_ViewModel.toggleLike_Reels(
                                                                        item.user_post_id
                                                                    )
                                                                }
                                                            }
                                                        }
                                                    )
                                                }
                                        )
                                    }
                                }

                                // Play icon overlay
                                if (!isPlayerPlaying) {
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(Color.Black.copy(0.5f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.play_arrow),
                                            contentDescription = "Play",
                                            tint = Color.White,
                                            modifier = Modifier.size(32.dp)
                                        )
                                    }
                                }

                                // Track buffering and sync play state
                                DisposableEffect(player, page) {
                                    val listener = object : Player.Listener {
                                        override fun onPlaybackStateChanged(playbackState: Int) {
                                            when (playbackState) {
                                                Player.STATE_BUFFERING -> {
                                                    isBuffering[page] = true
                                                    isPrepared[page] = false
                                                }

                                                Player.STATE_READY -> {
                                                    isBuffering[page] = false
                                                    isPrepared[page] = true
                                                }

                                                Player.STATE_ENDED -> {
                                                    player.seekTo(0)
                                                    if (page == pagerState.settledPage) {
                                                        player.playWhenReady = true
                                                    }
                                                }

                                                Player.STATE_IDLE -> {
                                                    isBuffering[page] = false
                                                    isPrepared[page] = false
                                                }
                                            }
                                        }

                                        override fun onIsPlayingChanged(isPlaying: Boolean) {
                                            if (page == pagerState.settledPage) {
                                                isPlayerPlaying = isPlaying
                                            }
                                        }
                                    }
                                    player.addListener(listener)

                                    onDispose {
                                        player.removeListener(listener)
                                        isBuffering.remove(page)
                                        isPrepared.remove(page)
                                    }
                                }

                                if (viewdetailFlow.value == ViewDetailsFlow.RENEW){
                                    if (!renewDisable.value) {
                                        Box(
                                            modifier = Modifier
                                                .padding(top = 80.dp)
                                                .padding(horizontal = 16.dp)
                                                .fillMaxWidth()
                                                .height(60.dp)
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(Color(0xffF7F0DC))
                                                .border(1.dp, Color(0xffB8B8B8), RoundedCornerShape(6.dp))
                                                .padding(horizontal = 16.dp)
                                            , contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                buildAnnotatedString {
                                                    withStyle(SpanStyle(color = newBlack)) { append("Your property expires.") }
//                                        withStyle(SpanStyle(color = Color.Red)) { append("* ") }
                                                    withStyle(SpanStyle(color = newBlue)) { append("Renew now") }
                                                },
                                                fontSize = constants.textUnit(16),
                                                fontFamily = constants.fontFamily(1),
                                                modifier = Modifier.align(Alignment.CenterStart)
                                            )

                                            Image(painter = painterResource(R.drawable.close), "",
                                                colorFilter = ColorFilter.tint(Color.White),
                                                modifier = Modifier.noRippleClickable{
                                                    renewDisable.value = true
                                                })
                                        }
                                    }
                                }

                                // Back buttons and Reels options remain the same
                                if (from_DLP_State.value) {
                                    viewModel.toggleshowTABars(false)
                                    viewModel.toggleshowBABars(false)
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = 16.dp,
                                                top = if (forTab()) 16.dp else rememberNotchHeightDp().value
                                            ),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start
                                    )
                                    {
                                        Image(painter = painterResource(R.drawable.left_arrow) , "",
                                            colorFilter = ColorFilter.tint(Color.White),
                                            modifier = Modifier.noRippleClickable{
                                                AppPreferences.save_Noti_Post_Id("")
                                                set_FDLP_State(false)
                                                viewModel.selectedBABTab(0)
                                                constants.API_Vm.isLoading_Reels = true
                                                constants.API_Vm.totalPages_Reels = 1
                                                navController.navigate(
                                                    UserCredentialsScreenFlow.Common_Screen.route
                                                )
                                            })
//                                        Backer(
//                                            modifier = Modifier,
//                                            onBackClick = {
//                                                AppPreferences.save_Noti_Post_Id("")
//                                                set_FDLP_State(false)
//                                                viewModel.selectedBABTab(0)
//                                                constants.API_Vm.isLoading_Reels = true
//                                                constants.API_Vm.totalPages_Reels = 1
//                                                navController.navigate(
//                                                    UserCredentialsScreenFlow.Common_Screen.route
//                                                )
//                                            }
//                                        )
                                    }
                                }
                                if (!from_DLP_State.value) {
                                    if (AppPreferences.get_Noti_Post_Id().isNotEmpty()) {
                                        viewModel.toggleshowTABars(false)
                                        viewModel.toggleshowBABars(false)

                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(
                                                    horizontal = 16.dp,
                                                    vertical = if (forTab()) 16.dp else rememberNotchHeightDp().value
                                                ),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Start
                                        ) {
                                            Image(painter = painterResource(R.drawable.left_arrow) , "",
                                                colorFilter = ColorFilter.tint(Color.White),
                                                modifier = Modifier.noRippleClickable{
                                                    retry = retry + 2345
                                                    viewModel.notification_PostId = ""
                                                    AppPreferences.save_Noti_Post_Id("")
                                                    constants.PostProperty_ViewModel.setViewDetailsFlow(
                                                        ViewDetailsFlow.NONE)
                                                    navController.navigate(VideosScreenFlow.In_App_Notification.route)
                                                })
//                                            Backer(
//                                                modifier = Modifier,
//                                                onBackClick = {
//
//                                                }
//                                            )
                                        }
                                    }
                                }

                                Reels_Options(
                                    modifier = Modifier.align(Alignment.BottomCenter),
                                    showBABars,
                                    videos,
                                    page,
                                    navController,
                                    isLike_Loading,
                                    isSave_Loading,
                                    viewModel
                                )
                            }

                            hasVideo == true && player == null -> {
                                player?.release()
                                playerManager.releaseAll()
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }

                            else -> {
                                player?.release()
                                playerManager.releaseAll()
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Black),
                                    contentAlignment = Alignment.Center
                                ) {


                                    if (viewdetailFlow.value == ViewDetailsFlow.RENEW){
                                        if (!renewDisable.value) {
                                            Box(
                                                modifier = Modifier
                                                    .padding(top = 80.dp)
                                                    .padding(horizontal = 16.dp)
                                                    .fillMaxWidth()
                                                    .height(60.dp)
                                                    .clip(RoundedCornerShape(6.dp))
                                                    .background(Color(0xffF7F0DC))
                                                    .border(1.dp, Color(0xffB8B8B8), RoundedCornerShape(6.dp))
                                                    .padding(horizontal = 16.dp)
                                                , contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    buildAnnotatedString {
                                                        withStyle(SpanStyle(color = newBlack)) { append("Your property expires.") }
//                                        withStyle(SpanStyle(color = Color.Red)) { append("* ") }
                                                        withStyle(SpanStyle(color = newBlue)) { append("Renew now") }
                                                    },
                                                    fontSize = constants.textUnit(16),
                                                    fontFamily = constants.fontFamily(1),
                                                    modifier = Modifier.align(Alignment.CenterStart)
                                                )

                                                Image(painter = painterResource(R.drawable.close), "",
                                                    colorFilter = ColorFilter.tint(Color.White),
                                                    modifier = Modifier.noRippleClickable{
                                                        renewDisable.value = true
                                                    })
                                            }
                                        }
                                    }

                                    val type = when {
                                        item.user_id == AppPreferences.getUserId() -> 1
                                        else -> 2
                                    }

                                    PhotoRequestAssistant(type, Color.White ,item.user_post_id, navController, modifier = Modifier.fillMaxSize(), item.post_interest)

                                    Reels_Options(
                                        modifier = Modifier.align(Alignment.BottomCenter),
                                        showBABars,
                                        videos,
                                        page,
                                        navController,
                                        isLike_Loading,
                                        isSave_Loading,
                                        viewModel
                                    )
                                }

                                // Back buttons for error state
                                if (from_DLP_State.value) {
                                    viewModel.toggleshowTABars(false)
                                    viewModel.toggleshowBABars(false)
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                start = 16.dp,
                                                top = if (forTab()) 16.dp else rememberNotchHeightDp().value
                                            ),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start
                                    ) {


                                        Icon(painter = painterResource(R.drawable.left_arrow), "",
                                            tint = Color.White,
                                            modifier = Modifier//.padding(top = notchPadding.value)
                                                .size(24.dp)
                                                .noRippleClickable {
                                                    AppPreferences.save_Noti_Post_Id("")
                                                    set_FDLP_State(false)
                                                    viewModel.selectedBABTab(0)
                                                    navController.navigate(
                                                        UserCredentialsScreenFlow.Common_Screen.route
                                                    )
                                                })
                                    }
                                }
                                if (!from_DLP_State.value) {
                                    if (AppPreferences.get_Noti_Post_Id().isNotEmpty()) {
                                        viewModel.toggleshowTABars(false)
                                        viewModel.toggleshowBABars(false)

                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(
                                                    horizontal = 16.dp,
                                                    vertical = if (forTab()) 16.dp else rememberNotchHeightDp().value
                                                ),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Start
                                        ) {

                                            Icon(painter = painterResource(R.drawable.left_arrow), "",
                                                tint = Color.White,
                                                modifier = Modifier//.padding(top = notchPadding.value)
                                                    .size(24.dp)
                                                    .noRippleClickable {
                                                        retry = retry + 2345
                                                        viewModel.notification_PostId = ""
                                                        AppPreferences.save_Noti_Post_Id("")
                                                        navController.navigate(VideosScreenFlow.In_App_Notification.route)
                                                    })

                                        }
                                    }
                                }
                            }
                        }
                    }

                    Reels_OptionsStatic(
                        modifier = Modifier,
                        showBABars,
                        videos,
                        page,
                        navController,
                        pagerState,
                        player = player,
                        viewModel
                    )
                }
            }
        }
    }


    /// post successful
    if (postSuccess.value){
        ModalBottomSheet(
            sheetState = bottomSheetState,
            onDismissRequest = {
                constants.PostProperty_ViewModel.set_postSuccessful_State(false)
            }
            , containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                , verticalArrangement = Arrangement.spacedBy(8.dp)
                , horizontalAlignment = Alignment.Start
            ) {
                CommonText("Note:",
                    newBlack,
                    18,
                    1
                )

                constants.spacer(8)

                CommonText("Your property has been posted successfully. It will remain active for 2 months, and you can renew it starting 6 days before it expires.",
                    Color(0xff575757),
                    14,
                    3
                )

                constants.spacer(4)

                HorizontalDivider()

                constants.spacer(4)

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(.9f)
                        .height(56.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Brush.verticalGradient(newPurpleGradient))
                        .noRippleClickable{
                            constants.PostProperty_ViewModel.setViewDetailsFlow(ViewDetailsFlow.NONE)
                            constants.PostProperty_ViewModel.setPostFlow(PostFlow.NONE)
                            constants.PostProperty_ViewModel.set_postSuccessful_State(false)
                        }
                    , contentAlignment = Alignment.Center
                ){
                    CommonText("Okay, Got it",
                        Color.White,
                        14,
                        0
                        )
                }

                constants.spacer(4)
            }
        }
    }



    // Enquiry Form Bottom Sheet
    if (videos.isNotEmpty() && pagerState.currentPage < videos.size) {
        Enquiry_Form_Btm_Sheet_Structure(send_Eq_State.value, videos[pagerState.currentPage])
    }


    // Reels Bottom Sheet
    if (reelsBTMSheetState.value) {
        ModalBottomSheet(
            onDismissRequest = {
                constants.Common_H_ViewModel.toggleReelsBTMSheet(false)
            },
            containerColor = newWhite
            , modifier = Modifier.fillMaxWidth()
        )
        {

            println("ISFFIVE RENEWWW -- ${ isWithinLast5DaysOfValidity(videos[pagerState.currentPage]?.post_property?.created_at ?: "")} -- ${videos[pagerState.currentPage].post_property.created_at}")

            LaunchedEffect(Unit) {

                when {

                    isWithinLast5DaysOfValidity(videos[pagerState.currentPage]?.post_property?.created_at ?:"")  &&  videos[pagerState.currentPage].user_id == AppPreferences.getUserId() -> {
//                    viewdetailFlow.value == ViewDetailsFlow.RENEW -> {
                        println("indisddejeididjdjdfj2222222 -- ${ isWithinLast5DaysOfValidity(videos[pagerState.currentPage]?.post_property?.created_at ?:"")}")
                        constants.Reels_ViewModel.removeReelsBTMSOptions(
                            constants.Reels_ViewModel.renewpostOptions
                        )
                    }

                    videos[pagerState.currentPage].user_id != AppPreferences.getUserId() -> {
                        constants.Reels_ViewModel.removeReelsBTMSOptions(constants.Reels_ViewModel.otherIdOptions)
                    }



                    videos[pagerState.currentPage].user_id == AppPreferences.getUserId() -> constants.Reels_ViewModel.removeReelsBTMSOptions(
                        constants.Reels_ViewModel.ownIdOptions
                    )

                    viewdetailFlow.value == ViewDetailsFlow.RENTOUT -> constants.Reels_ViewModel.removeReelsBTMSOptions(
                        constants.Reels_ViewModel.soldoutIdOptions
                    )

                    else -> constants.Reels_ViewModel.removeReelsBTMSOptions(constants.Reels_ViewModel.otherIdOptions)
                }
            }

            val data = reelsBTMSOptions.value

            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .horizontalScroll(rememberScrollState())
            )
            {
                data.forEachIndexed { index, Options ->
                    Column (
                        modifier = Modifier
                           // .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .noRippleClickable {
                                when (Options.id) {
                                    0 -> { /* repost logic */
                                        constants.Reels_ViewModel.clear_view_pro_Details()
                                        //AppPreferences.save_Post_Id(videos[pagerState.currentPage].user_post_id)
                                        repost_Btm.value = true
                                        constants.Common_H_ViewModel.toggleReelsBTMSheet(false)
                                    }
                                    1 -> { /* edit logic */
                                        constants.Reels_ViewModel.clear_view_pro_Details()
                                        navController.navigate(ProfileScreenFlow.Edit_Property_Option.route)
                                        constants.Common_H_ViewModel.toggleReelsBTMSheet(false)
                                    }
                                    2 -> { /* mark as sold */
                                        mark_as_Sold.value = true
                                        constants.Common_H_ViewModel.toggleReelsBTMSheet(false)
                                    }
                                    3 -> { /* delete */
                                        println("COMING INTO HERE")

                                        delete_Post = true
                                        viewModel.toggleReelsBTMSheet(false)

                                        println("COMING INSIDE DELETTING SOLDOUTS")
                                        constants.API_Vm.delete_Post_SM_Drafts(
                                            user_id = AppPreferences.getUserId(),
                                            select_all = 0,
                                            user_post_id = (videos[pagerState.currentPage].user_post_id?: 0).toString(),
                                        )
                                        { aPI_Result_Handling ->
                                            when (aPI_Result_Handling) {
                                                is API_Result_Handling.Loading -> {}
                                                is API_Result_Handling.NoData -> {}
                                                is API_Result_Handling.Deactivated -> {
                                                    // resultCallback(5)
                                                }
                                                is API_Result_Handling.Error -> {}
                                                is API_Result_Handling.Success -> {

                                                    if (constants.Profile_ViewModel.from_SoldOuts.value == true) {
                                                        constants.Profile_ViewModel.deleteByPostId_Profile_SoldOuts(
                                                            videos[pagerState.currentPage].user_post_id
                                                                ?: 0
                                                        )
                                                        navController.navigate(
                                                            ProfileScreenFlow.Sold_Outs.route
                                                        )
                                                    }

                                                    constants.Common_H_ViewModel.toggleReelsBTMSheet(false)

                                                }
                                            }
                                        }
                                    }
                                    4 -> { /* share */
                                        constants.DefaultShare(
                                            "https://toletspot.com/property/${videos[pagerState.currentPage].user_post_id}",
                                            1
                                        )
                                        constants.Common_H_ViewModel.toggleReelsBTMSheet(false)
                                    }
                                    5 -> { /* report */
                                        if (videos[pagerState.currentPage].post_property.is_report != 1) {
                                            constants.Profile_ViewModel.toggle_ReportSucces_True()

                                            report_BS.value = true
                                            constants.Profile_ViewModel.toggle_ProfileReport_Options(
                                                -1
                                            )
                                            constants.Common_H_ViewModel.toggleReelsBTMSheet(false)
                                        } else {
                                            // constants.Profile_ViewModel.toggle_ReportSucces_True()

                                            constants.Common_H_ViewModel.toggleReelsBTMSheet(false)

                                            GlobalSnackbar.show("Post already reported")
                                        }
                                    }
                                    6 -> { /* not interested */
                                        notInterested_Btm.value = true
                                    }

                                    8  -> {
                                        activateRentedout.value = true
                                    }

                                }
                            }
                        //,verticalArrangement = Arrangement.spacedBy(8.dp)
                        , horizontalAlignment = Alignment.CenterHorizontally
                        , verticalArrangement = Arrangement.Center
                    )
                    {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(Color(0xffF7F0DC)),
                            contentAlignment = Alignment.Center
                        ) {
                            SubcomposeAsyncImage(
                                model = Options.icon,
                                contentDescription = "",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(Options.title, color = newBlack, fontSize = constants.textUnit(14))
                    }
                    //Spacer(modifier = Modifier.padding(8.dp))
                }
            }
        }
    }

    // Comment Sheet
    if (cmt_btm_Sheet.value) {
        ModalBottomSheet(
            onDismissRequest = {
                if (!commentCloseDisable.value) {
                    notification_Comment_Clicked.value = false

                    constants.Common_H_ViewModel.dismiss_Cmt_btm_Sheet()
                    constants.Reels_ViewModel.clear_MCommentList()
                    constants.Reels_ViewModel.clear_Reply_Map()
                }
            },
            sheetState = bottomSheetState,
            containerColor = newWhite,
            sheetGesturesEnabled = false
            , modifier = Modifier.fillMaxWidth()
        ) {
            Comment_Structure(videos, pagerState.currentPage, navController, viewModel)
        }
    }

    // Report Sheet
    if (report_BS.value)
    {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

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
            , modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Top
            ) {
                val user_Manual_report = remember { mutableStateOf(false) }
                val user_Manual_report_String = remember { mutableStateOf("") }

                AnimatedContent(targetState = report_success) { targetState ->
                    if (targetState.value) {
                        Column(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Why are you reporting ?",
                                color = newBlack,
                                fontSize = constants.textUnit(24),
                                fontFamily = constants.fontFamily(0),
                                modifier = Modifier.align(Alignment.Start)
                            )

                            report_Options.value.forEachIndexed { index, profileReportOptionsDc ->
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
                    }
                   /* else {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        )
                        {
                            SubcomposeAsyncImage(
                                model = R.drawable.profile_report_submit_success,
                                "",
                                modifier = Modifier.size(150.dp)
                            )

                            Text(
                                text = "Submitted Successfully",
                                color = newBlack,
                                fontSize = constants.textUnit(18),
                                fontFamily = constants.fontFamily(0)
                            )

                            Text(
                                text = "Thank you for bringing this to our attention.",
                                color = newBlack,
                                fontSize = constants.textUnit(12),
                                fontFamily = constants.fontFamily(3)
                            )
                        }
                    }*/
                }

                Spacer(modifier = Modifier.padding(8.dp))

                if (report_success.value) {
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
                                        .background(newBlue)
                                        .noRippleClickable{
                                            if (network.value == NetworkStatus.Online) {
                                                if (videos[pagerState.currentPage].post_property.is_report != 1) {
                                                    if (user_Manual_report_String.value.isEmpty()) {
                                                        user_Manual_report_String.value =
                                                            constants.Profile_ViewModel.getSelectedProfileReportOptionDescription()
                                                                ?: ""
                                                    }

                                                    constants.API_Vm.put_Report_All(
                                                        user_id = AppPreferences.getUserId(),
                                                        user_post_id = videos[pagerState.currentPage].user_post_id.toString(),
                                                        receiver_id = videos[pagerState.currentPage].user_id.toString(),
                                                        comment_id = "",
                                                        report_sentence_id = (constants.Profile_ViewModel.getSelectedProfileReportOptionId()
                                                            ?.plus(1)) ?: 0,
                                                        report_sentence = user_Manual_report_String.value,
                                                        status = 2,
                                                    ) { apiResultHandling ->
                                                        when (apiResultHandling) {
                                                            is API_Result_Handling.Success -> {

                                                                constants.Reels_ViewModel.toggleLike_Report(
                                                                    videos[pagerState.currentPage].user_post_id
                                                                )
//                                                                constants.Profile_ViewModel.toggle_ReportSucces_False()
                                                                report_BS.value = false
                                                                 GlobalSnackbar.show("Reported Successfully")
                                                            }

                                                            is API_Result_Handling.Deactivated -> {
                                                                // Handle deactivation
                                                            }

                                                            is API_Result_Handling.Loading -> {

                                                            }

                                                            else -> {
                                                                toast("Something went wrong")
                                                                // Handle other cases
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    scope.launch {
                                                        snackbarHostState.showSnackbar("Post Already Reported")
                                                        report_BS.value = false
                                                    }
                                                }
                                            }
                                            else {
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
    }

    // Not Interested Sheet
    if (notInterested_Btm.value)
    {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

        ModalBottomSheet(
            onDismissRequest = {
                //constants.Profile_ViewModel.toggle_ReportSucces_True()
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

                //if (report_success.value) {
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
                                        .border(1.dp , Brush.linearGradient(newPurpleGradientBorder) ,RoundedCornerShape(8.dp))
                                        .noRippleClickable{
                                            if (network.value == NetworkStatus.Online) {
                                                if (videos[pagerState.currentPage].post_property.is_report != 1) {
                                                    if (user_Manual_report_String.value.isEmpty()) {
                                                        user_Manual_report_String.value = constants.Profile_ViewModel.getSelected_NotInterested_OptionDescription() ?: ""
                                                    }

                                                   constants.API_Vm.notInterested(
                                                        user_id = AppPreferences.getUserId(),
                                                        user_post_id = videos[pagerState.currentPage].user_post_id,
                                                        land_type_id = videos[pagerState.currentPage]?.post_property?.land_type_id ?: 0,
                                                        land_categorie_id = videos[pagerState.currentPage]?.post_property?.land_categorie_id ?: 0,
                                                       statement = user_Manual_report_String.value
                                                    )
                                                    {
                                                            result_Handling ->
                                                        when (result_Handling){
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

                                                                constants.Reels_ViewModel.deleteVideoById_Profile_Post_Reels(videos[pagerState.currentPage].user_post_id)
                                                                GlobalSnackbar.show("Okay! We’ll hide this property." )
                                                            }
                                                            is API_Result_Handling.Deactivated -> {

                                                            }
                                                        }
                                                    }

                                                } else {
                                                    //scope.launch {
                                                        toast("Post already set to Not Interested")
                                                        notInterested_Btm.value = false
                                                   // }
                                                }
                                            }
                                            else {
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
                //}
            }
        }
    }

    /// activte renew
    if (activaterenew.value) {
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
//                Image(painter = painterResource(R.drawable.repost) , "")



                CommonText(
                    "Renew Property Listing",
                    Color(0xff575757),
                    18,
                    1
                    , modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 16.dp)
                )

                CommonText("Renew keeps your property listing active for more days. Your post is about to expire, and renewing will refresh its validity so more people can view it without creating a new listing.",
                    Color(0xff575757),
                    14,
                    3
                    , modifier = Modifier
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
                                    user_post_id = videos[pagerState.currentPage].user_post_id,
                                )
                                { aPI_Result_Handling ->
                                    when (aPI_Result_Handling) {
                                        is API_Result_Handling.NoData -> {}
                                        is API_Result_Handling.Error -> {

                                            GlobalSnackbar.show("Something went wrong")
                                            //isLoading = false
                                            activaterenew.value = false
                                        }

                                        is API_Result_Handling.Deactivated -> {
                                            //resultCallback(5)
                                        }

                                        is API_Result_Handling.Loading -> {
                                            //isLoading = true
                                        }

                                        is API_Result_Handling.Success -> {

                                            GlobalSnackbar.show("Property Activated Successfully ")
                                            renewDisable.value = true

                                            constants.Reels_ViewModel.setNewTimeStampOnRenew(videos[pagerState.currentPage].user_post_id)
                                            //isLoading = false
                                            activaterenew.value = false

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
    /// activte rented out
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
//                Image(painter = painterResource(R.drawable.repost) , "")


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
                                    user_post_id = videos[pagerState.currentPage].user_post_id,
                                    status = 2,
                                )
                                { aPI_Result_Handling ->
                                    when (aPI_Result_Handling) {
                                        is API_Result_Handling.NoData -> {}
                                        is API_Result_Handling.Error -> {
                                            isLoading = false
                                        }

                                        is API_Result_Handling.Deactivated -> {
                                            //resultCallback(5)
                                        }

                                        is API_Result_Handling.Loading -> {
                                            isLoading = true
                                        }

                                        is API_Result_Handling.Success -> {
                                            isLoading = false
                                            GlobalSnackbar.show("Property Activated Successfully ")

//                                            constants.Reels_ViewModel.setNewTimeStampOnRenew(
//                                                view_Details_Data.value?.user_post_id ?: 0
//                                            )
//                                            constants.Reels_ViewModel.deleteVideoById_Profile_Post_Reels(
//                                                videos[pagerState.currentPage].user_post_id
//                                            )

                                            //isLoading = false
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


    /// rento
    if (mark_as_Sold.value == true) {
        playerManager.pauseVideo(pagerState.currentPage)
        Mark_As_Sold_Flow(mark_as_Sold, videos[pagerState.currentPage].user_post_id, navController)
    }

    /// repost btm
    if (repost_Btm.value) {
        ModalBottomSheet(
            onDismissRequest = {
                repost_Btm.value = false

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
                            .noRippleClickable{

                                constants.PostProperty_ViewModel.clear_Forms()
                                constants.PostProperty_ViewModel.first_Form_selected_PP(-1)
                                constants.PostProperty_ViewModel.select_Land_Cat_Id(-1)
                                constants.PostProperty_ViewModel.LandSubType_Selected_Click(-1)
//                            constants.PostProperty_ViewModel.pp_SecondForm_Residential_Select_Option(
//                                -1
//                            )
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
                                constants.PostProperty_ViewModel.clearAllPostFields()

                                constants.PostProperty_ViewModel.check_Price_Negotiation(false)
                                constants.PostProperty_ViewModel.put_budget_Price_PF5("")

                                constants.PostProperty_ViewModel.clear_Media()
                                constants.PostProperty_ViewModel.goToPPFormPage(0, 7)
                                AppPreferences.save_Post_Id(videos[pagerState.currentPage].user_post_id)

                                get_Form_Preview_API_CALL { result ->
                                    when (result) {
                                        0 -> { state.value = result}
                                        1 -> {}
                                        2 -> {}
                                        3 -> {
                                            println("REPOST CLICKER SUCCESS")
                                            val server_Data = constants.PostProperty_ViewModel.get_previewFormData()

                                            println("Full Server Data --- ${server_Data}")

                                            server_Data?.let { data ->
                                                constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                                    data.toSelectedOptionsForm4()
                                                }
                                            }

                                            AppPreferences.save_Post_Id(server_Data?.user_post_id ?: 0)


                                            println("POST ID -- ${AppPreferences.get_Post_Id()}")

                                            constants.PostProperty_ViewModel.first_Form_selected_PP(
                                                (server_Data?.user_type ?: "0").toInt()
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

                                            constants.PostProperty_ViewModel.set_onSelected_ProType((server_Data?.land_type_id ?: 0) )


                                            constants.PostProperty_ViewModel.pp_SecondForm_Residential_Select_Option(
                                                server_Data?.land_categorie_id ?: 0
                                            )

                                            constants.PostProperty_ViewModel.select_Land_Cat_Id(
                                                server_Data?.land_categorie_id ?: 0
                                            )

                                            constants.PostProperty_ViewModel.LandSubType_Selected_Click(server_Data?.land_categorie_id ?: 0)

                                            println("REPOST FROM PFOILE TOP____ ${server_Data?.land_type_id}")
                                            constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value =  constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value.copy(
                                                first = server_Data?.land_type_id ?: 0,
                                                second = server_Data?.land_categorie_id ?: 0
                                            )

                                            println("REPOST FROM PFOILE TOP____ ${server_Data?.land_type_id ?: 0} (((( ${constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value}")


                                            constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value = Pair((server_Data?.land_type_id ?: 0)  , server_Data?.land_categorie_id ?: 0)

                                            println("Step 2 --- ${(server_Data?.land_type_id ?: 0) - 1 } -***${ constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value}***-- ${server_Data?.land_categorie_id ?: 0}")
//                                            println("LOCARTION DERAIKS sfjngv adsfbvkhjd REPOST ${server_Data.pincode} -__${server_Data.longitude}--${server_Data.latitude}--${server_Data.address}--${server_Data.city}- ${server_Data.country} -- ${data.state}")

                                            // Safely set values if they’re not empty
                                            if (!server_Data?.pincode.isNullOrEmpty()) {
                                                constants.PostProperty_ViewModel.set_pincode3(server_Data?.pincode ?: "")
                                            }
                                            if (!server_Data?.country.isNullOrEmpty()) {
                                                constants.PostProperty_ViewModel.set_country3(server_Data?.country ?: "")
                                            }
                                            if (!server_Data?.state.isNullOrEmpty()) {
                                                constants.PostProperty_ViewModel.set_state3(server_Data?.state ?: "")
                                            }
                                            if (!server_Data?.city.isNullOrEmpty()) {
                                                constants.PostProperty_ViewModel.set_city3(server_Data?.city?:"")
                                            }
                                            if (!server_Data?.locality.isNullOrEmpty()) {
                                                constants.PostProperty_ViewModel.set__selectedLocality3(server_Data?.locality ?:"")
                                            }
//
                                            if (server_Data?.latitude?.isNotEmpty() == true && server_Data?.longitude?.isNotEmpty() == true) {
                                                constants.PostProperty_ViewModel.add_Pinned_Lat_Long(
                                                    LatLng(
                                                        server_Data?.latitude?.toDouble() ?: 0.0,
                                                        server_Data?.longitude?.toDouble() ?: 0.0
                                                    )
                                                )
                                            }

//                                            if (server_Data.images.isNotEmpty()){
//                                                constants.PostProperty_ViewModel.addImages(server_Data?.images ?:"")
//                                            }
//                                            else if(server_Data.video.isNotEmpty()){
//                                                constants.PostProperty_ViewModel.addVideo(server_Data?.images ?:"")
//
//                                            }

                                            println("LOCALOTY CHECK -- ${server_Data?.locality ?:""} -- ${server_Data?.address ?:""}")
                                            constants.PostProperty_ViewModel.add_pp3_Data(
                                                PP3_API_DC(
                                                    pincode = server_Data?.pincode ?: "",
                                                    country = server_Data?.country ?:"",
                                                    state = server_Data?.state ?:"",
                                                    city = server_Data?.city ?:"",
                                                    locality = server_Data?.locality ?:""
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

                                            println("VIDEO ADDED IN THE List CHECH -- ${server_Data?.images}  -- ${server_Data?.video}")
                                            if (!server_Data?.images.isNullOrEmpty()) {
//                                                val imageMediaList = server_Data.images.map { imageUri ->
//                                                    UploadPropertyMedia(uri = Uri.parse(imageUri), isVideo = false)
//                                                }
//                                                constants.PostProperty_ViewModel.addImages(imageMediaList)
                                            }
                                            else if (!server_Data?.video.isNullOrEmpty()) {
                                                println("VIDEO ADDED IN THE List")
//                                                val videoMedia = UploadPropertyMedia(
//                                                    uri = Uri.parse(server_Data.video),
//                                                    isVideo = true
//                                                )
//                                                constants.PostProperty_ViewModel.addVideo(videoMedia)
                                            }


                                            println("VIDEO ADDED IN THE List CHECH -- ${constants.PostProperty_ViewModel.mediaList.value}")

                                            constants.PostProperty_ViewModel.select_Land_Type(server_Data?.land_type_id ?: 0)

                                            constants.PostProperty_ViewModel.loadDraftFromServer(
                                                imageUrls = server_Data?.images ?: emptyList(),
                                                videoUrls = server_Data?.video ?: emptyList(),
                                                coverUrl = server_Data?.thumbnail ?: ""
                                            )

//                                            val budget_Price =
//                                                constants.PostProperty_ViewModel.put_budget_Price_PF5(server_Data?.price ?: "")

                                            println("SERVER DATA -- ${server_Data} ---- cons.${constants.PostProperty_ViewModel.get_Selected_Fields_Form()}")

                                            constants.Profile_ViewModel.set_From_Repost(1)
                                            constants.PostProperty_ViewModel.set_Post_Form_Flow(2)


                                            constants.PostProperty_ViewModel.setPostFlow(PostFlow.REPOST)

                                            // ✅ Navigate after everything is done
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


    // delete post
    Common_Popup(
        delete_Post,
        modifier = Modifier
            .background(Color(0xffF7F0DC))
        , image = "",
        userName = "",
        icon = R.drawable.closeenquiry /// or R.drawable
    )
    {
        Column (
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 24.dp)
            , verticalArrangement = Arrangement.spacedBy(12.dp)
            , horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            Spacer(modifier = Modifier.padding(2.dp))
            constants.spacer(2)

            Text(
                text = "Are you Sure, You want to delete?",
                color = newBlack,
                fontSize = constants.textUnit(16),
                fontFamily = constants.fontFamily(0)
            )

            Text(
                text = "This action cannot be undone. Are you sure you want to delete this property permanently?",
                color = newBlack,
                fontSize = constants.textUnit(12),
                fontFamily = constants.fontFamily(3)
                , textAlign = TextAlign.Center
                , lineHeight = 24.sp
                , modifier = Modifier.padding(horizontal = if (forTab()) 36.dp else 0.dp)
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
                        .height(if (forTab()) 46.dp else 36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xffE8E8E8))
                        .noRippleClickable{
                            delete_Post = false
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
                constants.spacer(2)

                Box(
                    modifier = Modifier
                        .weight(4f)
                        .height(if (forTab()) 46.dp else 36.dp)
                        .noRippleClickable{
                            ClickHelper.getInstance().clickOnce {
                                if (ClickGuard.canClick()) {

                                    if (network.value == NetworkStatus.Online) {
                                        println(" API CALL HIT STATUS __ ue}")
                                        println("COMING INSIDE DELETTING SOLDOUTS")
                                        constants.API_Vm.delete_Post_SM_Drafts(
                                            user_id = AppPreferences.getUserId(),
                                            select_all = 0,
                                            user_post_id = (videos[pagerState.currentPage].user_post_id
                                                ?: 0).toString(),
                                        )
                                        { aPI_Result_Handling ->
                                            when (aPI_Result_Handling) {
                                                is API_Result_Handling.Loading -> {}
                                                is API_Result_Handling.NoData -> {}
                                                is API_Result_Handling.Deactivated -> {
                                                    // resultCallback(5)
                                                }

                                                is API_Result_Handling.Error -> {}
                                                is API_Result_Handling.Success -> {

                                                    if (constants.Profile_ViewModel.from_SoldOuts.value == true) {
                                                        constants.Profile_ViewModel.deleteByPostId_Profile_SoldOuts(
                                                            videos[pagerState.currentPage].user_post_id
                                                                ?: 0
                                                        )
                                                        navController.navigateUp()
                                                        delete_Post = true
                                                    } else {
                                                        println("65212345678987654321234567890-")
                                                        constants.Reels_ViewModel.deleteVideoById_Profile_Post_Reels(
                                                            videos[pagerState.currentPage].user_post_id
                                                                ?: 0
                                                        )
                                                        delete_Post = true

                                                        playerManager.releaseAll()

                                                        // Now navigate away
                                                        AppPreferences.save_Post_Id(0)
                                                        constants.Profile_ViewModel.set_From_SoldOuts(
                                                            false
                                                        )
                                                        constants.API_Vm.totalPages_PS_FF = 0
                                                        navController.navigateUp()
                                                    }

                                                    constants.Common_H_ViewModel.toggleReelsBTMSheet(
                                                        false
                                                    )

                                                }
                                            }
                                        }
                                        /*constants.API_Vm.put_Block_User(
                                    user_id = AppPreferences.getUserId(),
                                    blocker_id = profile_Content.value?.user_id ?: 0,
                                    //profile_Content.value?.user_id ?: 0,
                                    status = blockStatus.value
                                    //if (profile_Content.value?.is_blocked == 0) "1" else "0"
                                )
                                { apiResultHandling ->
                                    when (apiResultHandling) {
                                        is API_Result_Handling.Error -> {
                                            //errror
                                            //constants.Profile_ViewModel.change_Update_profile(false)
                                        }

                                        is API_Result_Handling.Deactivated -> {
                                            // resultCallback(5)
                                        }

                                        is API_Result_Handling.NoData -> {
                                            // no data
                                        }

                                        is API_Result_Handling.Loading -> {
                                            //loading
                                            // constants.Profile_ViewModel.change_Update_profile(true)
                                        }

                                        is API_Result_Handling.Success -> {
                                            if (profile_Content.value?.is_blocked == 0) {
                                                constants.Profile_ViewModel.updateBlockedStatus_Selected_Profile(
                                                    1
                                                )
                                            } else {
                                                constants.Profile_ViewModel.updateBlockedStatus_Selected_Profile(
                                                    0
                                                )
                                            }
                                            block_PopUp = false
                                            //constants.Profile_ViewModel.enable_Edit_Profile()
                                            //constants.Profile_ViewModel.change_Update_profile(false)
                                            //success
                                        }
                                    }
                                }*/
                                    } else {
                                        toast(constants.activity.getString(R.string.no_Internet))
                                        // GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
                                    }
                                }
                            }
                        }
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xffE54C3C))
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

            Spacer(modifier = Modifier.padding(8.dp))
            constants.spacer(2)
        }
    }


    // Exit confirmation dialog
    Common_Popup(
        visible = app_Exit,
        modifier = Modifier.background(newLightBlue),
        image = "",
        icon = R.drawable.one_star_image,
        userName = "",
        content = {
            Column(
                modifier = Modifier.wrapContentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                constants.spacer(2)
                Text("Are your sure. You Want to Exit ?",
                    color = newBlack,
                    fontSize = constants.textUnit(12),
                    fontFamily = constants.fontFamily(3),
                    textAlign = TextAlign.Center
                    , lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.padding(8.dp))
                constants.spacer(2)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Box(
                        modifier = Modifier
                            .height(48.dp)
                            .width(108.dp)
                            .background(newLightGray)
                            .noRippleClickable{
                                ClickHelper.getInstance().clickOnce {
                                    if (ClickGuard.canClick()) {
                                        viewModel.toggleshowBABars(true)
                                        viewModel.toggleshowTABars(true)
                                        app_Exit = false
                                    }
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Cancel", color = newBlack)
                    }

                    Box(
                        modifier = Modifier
                            .height(48.dp)
                            .width(108.dp)
                            .background(newBlue)
                            .noRippleClickable{
                                ClickHelper.getInstance().clickOnce {
                                    if (ClickGuard.canClick()) {
                                        constants.activity.finishAffinity()
                                        constants.activity.finish()
                                        viewModel.toggleshowBABars(true)
                                        viewModel.toggleshowTABars(true)
                                        app_Exit = false
                                    }
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Exit", color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.padding(8.dp))
                constants.spacer(2)
            }
        }
    )

    LaunchedEffect(!deactivated) {
        deactivated = false
    }
    // Deactivated account dialog
    Common_Popup(
        visible = deactivated,
        modifier = Modifier.background(Color(0xffFCEDEC)),
        image = "",
        icon = R.drawable.deactivated,
        userName = "",
        content = {
            Column (
                modifier = Modifier
                , verticalArrangement = Arrangement.spacedBy(8.dp)
                , horizontalAlignment = Alignment.CenterHorizontally
            ){
                //constants.spacer(2)
                Text("Account Restricted"
                    , color = Color.Black
                    , fontSize = constants.textUnit(16)
                    , fontFamily = constants.fontFamily(0)
                )

                //constants.spacer(2)

                Text("Your account has been reported multiple times for violating our community standards. Your account has been temporarily restricted. You can appeal this decision if you believe it was a mistake."
                    , color = Color(0xff484848)
                    , fontSize = constants.textUnit(12)
                    , fontFamily = constants.fontFamily(3)
                    , modifier = Modifier.padding(horizontal = if (forTab()) 36.dp else 0.dp)
                    , lineHeight = 24.sp

                )

                Box(
                    modifier = Modifier
                        .height(32.dp)
                        .fillMaxWidth(.9f)
                        .noRippleClickable{
                            ClickHelper.getInstance().clickOnce {
                                if (ClickGuard.canClick()) {
                                    deactivated = false
                                    constants.Profile_ViewModel.set_open_settings()
                                    constants.Profile_ViewModel.onSet_Settings_Click(0)

                                    constants.Common_H_ViewModel.changeStatus(false)

                                    constants.Common_H_ViewModel.toggleshowBABars(true)
                                    constants.Start_Up_ViewModel.updateLoginState(0)
                                    constants.Profile_ViewModel.onSet_Settings_Click(-1)
                                    constants.Start_Up_ViewModel.phoneNumber = ""
                                    constants.Start_Up_ViewModel.countryCode = "+91"
                                    constants.Start_Up_ViewModel.userName = ""
                                    constants.Start_Up_ViewModel.otp = ""





                                    constants.Profile_ViewModel.dismiss_Logout_PP()
                                    onLogout()
                                }
                            }
                        }
                    , contentAlignment = Alignment.Center
                ){
                    Text("Disagree with Decision"
                        , color = Color.White
                        , fontSize = constants.textUnit(14)
                        , fontFamily = constants.fontFamily(0)
                    )
                }

                Text("Okay"
                    , color = Color(0xff484848)
                    , fontSize = constants.textUnit(14)
                    , fontFamily = constants.fontFamily(0)
                    , modifier = Modifier
                        .noRippleClickable{
                            AppPreferences.clearAll()
                            constants.Profile_ViewModel.set_open_settings()
                            constants.Profile_ViewModel.onSet_Settings_Click(0)

                            constants.Common_H_ViewModel.changeStatus(false)

                            constants.Common_H_ViewModel.toggleshowBABars(true)
                            constants.Start_Up_ViewModel.updateLoginState(0)
                            constants.Profile_ViewModel.onSet_Settings_Click(-1)
                            constants.Start_Up_ViewModel.phoneNumber = ""
                            constants.Start_Up_ViewModel.countryCode = "+91"
                            constants.Start_Up_ViewModel.userName = ""
                            constants.Start_Up_ViewModel.otp = ""


                            constants.Profile_ViewModel.dismiss_Logout_PP()
                            onLogout()
                        }
                )

                Spacer(modifier = Modifier.padding(8.dp))
                constants.spacer(2)
            }
        }
    )

    // Exit confirmation dialog
    Common_Popup(
        visible = app_Exit,
        modifier = Modifier.background(newLightBlue),
        image = "",
        icon = R.drawable.one_star_image,
        userName = "",
        content = {
            Column(
                modifier = Modifier.wrapContentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                constants.spacer(2)
                Text("Are your sure. You Want to Exit ?", color = newBlack,
                    fontSize = constants.textUnit(12),
                    fontFamily = constants.fontFamily(3),
                    textAlign = TextAlign.Center
                    , lineHeight = 24.sp)

                Spacer(modifier = Modifier.padding(8.dp))
                constants.spacer(2)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Box(
                        modifier = Modifier
                            .height(48.dp)
                            .width(108.dp)
                            .background(newLightGray)
                            .noRippleClickable {
                                ClickHelper.getInstance().clickOnce {
                                    if (ClickGuard.canClick()) {
                                        viewModel.toggleshowBABars(true)
                                        viewModel.toggleshowTABars(true)
                                        app_Exit = false
                                    }
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Cancel", color = newBlack)
                    }

                    Box(
                        modifier = Modifier
                            .height(48.dp)
                            .width(108.dp)
                            .background(newBlue)
                            .noRippleClickable {
                                ClickHelper.getInstance().clickOnce {
                                    if (ClickGuard.canClick()) {
                                        constants.activity.finishAffinity()
                                        constants.activity.finish()
                                        viewModel.toggleshowBABars(true)
                                        viewModel.toggleshowTABars(true)
                                        app_Exit = false
                                    }
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Exit", color = Color.White)
                    }
                }
                constants.spacer(2)

                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    )

    BackHandler {
        println("WERTTY %%%${from_DLP_State.value}-&&&${AppPreferences.get_Noti_Post_Id()}- ${report_BS.value} -- ${cmt_btm_Sheet.value} -- ${reelsBTMSheetState.value} -- ${send_Eq_State.value}")

        when {
            !from_DLP_State.value && AppPreferences.get_Noti_Post_Id().isNotEmpty() -> {
                                retry = retry + 2345
                                viewModel.notification_PostId = ""
                                AppPreferences.save_Noti_Post_Id("")
                                navController.navigate(VideosScreenFlow.In_App_Notification.route)

            }


            report_BS.value -> {
                report_BS.value = false
            }
            cmt_btm_Sheet.value -> {
                constants.Common_H_ViewModel.dismiss_Cmt_btm_Sheet()
            }
            reelsBTMSheetState.value -> {
                constants.Common_H_ViewModel.toggleReelsBTMSheet(false)
            }
            send_Eq_State.value -> {
                constants.Reels_ViewModel.dismiss_Send_Eq_Btm_Sheet()
            }
            !app_Exit -> {
                viewModel.toggleshowBABars(false)
                viewModel.toggleshowTABars(false)
                app_Exit = true

            }
        }
    }



}



@Composable
fun Reels_Options(
    modifier: Modifier,
    showBABars: State<Boolean>,
    videos: List<Get_Reels_Data>,
    page: Int,
    navController: NavHostController,
    isLike_Loading: MutableState<Boolean>,
    isSave_Loading: MutableState<Boolean>,
    viewModel: Common_H_ViewModel,
)
{

    val network = rememberNetworkStatus()


    Box (
        modifier = modifier
    ){
        if (videos[page].user_id == AppPreferences.getUserId()) {
            Image(
                painter = painterResource(R.drawable.ownpropertybadgerento), "",
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 16.dp)
                    .zIndex(3f)
                    .align(Alignment.BottomStart)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
                //.padding(bottom = if (showBABars.value) {if (forTab()) 100.dp else 80.dp } else 0.dp),
            ,verticalArrangement = Arrangement.Center
            , horizontalAlignment = Alignment.End
        )
        {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .wrapContentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                constants.Reels_ViewModel.reels_Options_List.forEachIndexed { index, icon ->

                    val currentReel = videos[page]
                    val isLiked = if (currentReel.is_liked == 1) true else false
                    val isSaved = if(currentReel.is_saved == 1) true else false



                    // Optional animation only for like button
                    val scale = remember { Animatable(1f) }

                    LaunchedEffect(icon.isLiked) {
                        if (isLiked && index == 0) {
                            scale.animateTo(1.3f, tween(200))
                            scale.animateTo(1f, tween(200))
                        }
                    }



                    val show_Icon =  when(index){
                        0 -> {
                            if (isLiked) icon.enabled_Icon else icon.icon
                        }
                        2 -> {
                            if (isSaved) icon.enabled_Icon else icon.icon
                        }
                        else -> { icon.icon }
                    }

//                    if ((index == 0 && isLike_Loading.value) || (index == 2 && isSave_Loading.value)){
//                        CircularProgressIndicator(modifier = Modifier.size(25.dp), color = newBlue)
//                    }
//                    else {
                        AsyncImage(
                            model = show_Icon,
                            contentDescription = "",
                            modifier = Modifier
                                .graphicsLayer {
                                    scaleX = scale.value
                                    scaleY = scale.value
                                }
                                .size(if (forTab()) 40.dp else 36.dp)
                                .noRippleClickable(enabled = !isLike_Loading.value || !isSave_Loading.value){

                                    ClickHelper.getInstance().clickOnce {

                                        if (ClickGuard.canClick()) {

                                            when (index) {
                                                0 -> {

//                                                    constants.Reels_ViewModel.toggleLike_Reels(
//                                                        currentReel.user_post_id
//                                                    )

                                                    constants.Reels_ViewModel.toggleLike_Reelsrento(
                                                        currentReel.user_post_id
                                                    )



//                                                    if (isLiked) {
//                                                        constants.Reels_ViewModel.decreaseLikeCount_Reels(
//                                                            currentReel.user_post_id
//                                                        )
//                                                    } else {
//                                                        constants.Reels_ViewModel.increaseLikeCount_Reels(
//                                                            currentReel.user_post_id
//                                                        )
//                                                    }

                                                    if (network.value == NetworkStatus.Online) {
                                                        constants.API_Vm.like_Dislike(
                                                            user_id = AppPreferences.getUserId(),
                                                            user_post_id = currentReel.user_post_id,
                                                            status = if (isLiked) 2 else 1,
                                                        )
                                                        { apiResultHandling ->
                                                            when (apiResultHandling) {
                                                                is API_Result_Handling.Error -> {
                                                                    toast("Something Went Wrong")
                                                                    isLike_Loading.value = false
                                                                    constants.Reels_ViewModel.decreaseLikeCount_Reels(
                                                                        currentReel.user_post_id
                                                                    )
                                                                    //errror
                                                                }

                                                                is API_Result_Handling.NoData -> {
                                                                    // no data
                                                                }

                                                                is API_Result_Handling.Loading -> {
                                                                    isLike_Loading.value = true
                                                                    //loading
                                                                }

                                                                is API_Result_Handling.Deactivated -> {
                                                                    /// resultCallback(5)
                                                                }

                                                                is API_Result_Handling.Success -> {
                                                                    isLike_Loading.value = false




                                                                    //success
                                                                }
                                                            }
                                                        }
                                                    }
                                                }

                                                1 -> {
                                                    if (network.value == NetworkStatus.Online) {
                                                        constants.API_Vm.isLoading_MComments = false
                                                        constants.API_Vm.totalPages_MComments = 1
                                                        constants.Reels_ViewModel.clear_MCommentList()
                                                        constants.Reels_ViewModel.clear_Reply_Map()
                                                        constants.Common_H_ViewModel.enable_Cmt_btm_Sheet()
                                                    }
                                                }

                                                2 -> {
                                                    if (network.value == NetworkStatus.Online) {
                                                        constants.Reels_ViewModel.toggleSave_Reels(
                                                            currentReel.user_post_id
                                                        )

                                                        constants.API_Vm.put_save_UnSave_Property(
                                                            user_id = AppPreferences.getUserId(),
                                                            user_post_id = currentReel.user_post_id,
                                                            status = if (isSaved) 2 else 1,
                                                        )
                                                        { apiResultHandling ->
                                                            when (apiResultHandling) {
                                                                is API_Result_Handling.Error -> {
                                                                    toast("Something Went Wrong")
                                                                    constants.Reels_ViewModel.toggleSave_Reels(
                                                                        currentReel.user_post_id
                                                                    )
                                                                    isSave_Loading.value = false
                                                                    //errror
                                                                }

                                                                is API_Result_Handling.NoData -> {
                                                                    // no data
                                                                }

                                                                is API_Result_Handling.Loading -> {
                                                                    isSave_Loading.value = true
                                                                    //loading
                                                                }

                                                                is API_Result_Handling.Deactivated -> {
                                                                    // resultCallback(5)
                                                                }

                                                                is API_Result_Handling.Success -> {
                                                                    isSave_Loading.value = false


                                                                    //success
                                                                }
                                                            }
                                                        }
                                                    }

                                                }

                                                3 -> {
                                                    AppPreferences.save_Post_Id(videos[page].user_post_id)
                                                    constants.Common_H_ViewModel.toggleReelsBTMSheet(
                                                        true
                                                    )

                                                }
                                            }
                                        }
                                    }

                                }
                        )
                    //}


                    //if (icon.counts.isNotEmpty()) {
                    if (index == 0){
                        Text(
                            text = currentReel.total_likes.toString() ,
                            color = newWhite,
                            fontSize = constants.textUnit(12), fontFamily = constants.fontFamily(2)
                        )
                    }
                    else if (index == 1){
                        Text(
                            text = currentReel.total_comments.toString(),
                            color = newWhite,
                            fontSize = constants.textUnit(12), fontFamily = constants.fontFamily(2)
                        )
                    }

                   // }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

        }
    }
}


@Composable
fun Reels_OptionsStatic(
    modifier: Modifier,
    showBABars: State<Boolean>,
    videos: List<Get_Reels_Data>,
    page: Int,
    navController: NavHostController,
    pagerState: PagerState,
    player: ExoPlayer?,
    viewModel: Common_H_ViewModel,
)
{

    val network = rememberNetworkStatus()


    Box (
        modifier = modifier
    ){
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = if (showBABars.value) {if (forTab()) 100.dp else 80.dp } else 0.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        )
        {


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 24.dp , topEnd = 24.dp))
                    .background(
                        Color.White
//                        brush = Brush.verticalGradient(
//                            colors = listOf(
//                                Color.Transparent,
//                                Color.Black.copy(alpha = 0.6f)
//                            )
//                        )
                    )
            )
            {
                val currentReel = videos[page]
                var vd_Loader by remember { mutableStateOf(false) }
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp)
                    , verticalArrangement = Arrangement.spacedBy(4.dp)
                    , horizontalAlignment = Alignment.Start
                )
                {
                    if (videos[page].post_property.is_sold == 1) {
                        Image(
                            painterResource(R.drawable.soldoutidentifier), "",
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                    ListItem(
                        leadingContent = {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                SubcomposeAsyncImage(
                                    model = currentReel?.profile_image ?: "",
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
                                            //.padding(8.dp)
                                            , contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = currentReel?.username.takeIf { it?.isNotEmpty() == true }
                                                    ?.take(1)?.uppercase() ?: ""
                                            )
                                        }
                                    } else {
                                        SubcomposeAsyncImageContent()
                                    }
                                }
                            }
                        }
                        , headlineContent = {
                            Row(
                                modifier = Modifier
                                    .noRippleClickable{
                                        ClickHelper.getInstance().clickOnce {
                                            if (ClickGuard.canClick()) {
                                                logD("clicks reels profile")
                                                viewModel.toggleshowTABars(false)
                                                constants.Profile_ViewModel.add_Selected_User_Name(
                                                    currentReel.username ?: "username"
                                                )

                                                //new flowwewwwwwww
                                                constants.Profile_ViewModel.add_BF_Handler(
                                                    Profile_Handle_Back(
                                                        current_UsedId = AppPreferences.getUserId(),
                                                        other_UserId = currentReel?.user_id ?: 0,
                                                        ff_User_Name = currentReel?.username ?: "",
                                                        ff_Fw_Count = 999,
                                                        ff_Fg_Count = 999,
                                                        // is_Search_Enabled = is_Search_Enabled.value,
                                                        // search_Text = search_Text.value
                                                    )
                                                )

                                                /// println("ITEM PROFILE STRUCTURE __ ${is_Search_Enabled.value} -- ${constants.Profile_ViewModel.profile_BF_Handler.value}")

                                                println("GIVEN OTHER USER ID -- ${constants.Profile_ViewModel.get_Other_User_Id()}")

                                                constants.Profile_ViewModel.addProfile(
                                                    currentReel?.user_id ?: 0
                                                )
                                                constants.Profile_ViewModel.add_Selected_Profile_Id(
                                                    id = currentReel.user_id ?: 0
                                                )

                                                // if (view_Details_Data.value?.user_id == AppPreferences.)
                                                viewModel.toggleshowBABars(false)
                                                navController.navigate(VideosScreenFlow.Other_Profile_Structure.route)
                                            }
                                        }
                                    }
                                , verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start
                            )
                            {


                                //Spacer(modifier = Modifier.padding(8.dp))

                                Text(
                                    currentReel.name,
                                    color = new5757,
                                    fontSize = constants.textUnit(16),
                                    fontFamily = constants.fontFamily(1)
                                )

                            }
                        },
                        supportingContent = {
                           // Spacer(modifier = Modifier.padding(4.dp))
                            Row(
                                 verticalAlignment = Alignment.CenterVertically
                                , horizontalArrangement = Arrangement.Start
                            ) {
                               // if (currentReel.user_id == AppPreferences.getUserId()){
                                var usertype = if (currentReel.post_property.user_type == "0")"Owner" else "Broker"
                                    Text(
                                        "$usertype \u2022 ",
                                        color = Color(0xff575757),
                                        fontSize = constants.textUnit(12),
                                        fontFamily = constants.fontFamily(1),
                                       // overflow = TextOverflow.Ellipsis
                                    )
                              //  }

                                Text(
                                    "${getTimeAgo(currentReel.post_property?.created_at ?:"")}",
                                    color = Color(0xff575757),
                                    fontSize = constants.textUnit(12),
                                    fontFamily = constants.fontFamily(1),
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                        , trailingContent = {
                            if (currentReel.user_id != AppPreferences.getUserId()) {
                                Box(
                                    modifier = Modifier
                                        .width(if (forTab()) 132.dp else 108.dp)
                                        .height(if (forTab()) 46.dp else 32.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .noRippleClickable {
                                            if (currentReel.enquiry == 0) {
                                                player?.pause()
                                                constants.Reels_ViewModel.enable_Send_Eq_Btm_Sheet()
                                            } else {
                                                GlobalSnackbar.show("Already Enquired")
                                            }
                                        }
                                        .border(
                                            1.dp, Brush.linearGradient(
                                                newPurpleGradientBorder
                                            ), RoundedCornerShape(4.dp)
                                        )
                                        .background(Brush.verticalGradient(newPurpleGradient)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        "Send Enquiry",
                                        color = Color.White,
                                        fontSize = constants.textUnit(14),
                                        fontFamily = constants.fontFamily(0)
                                    )
                                }
                            }
                        }
                        , modifier = Modifier
                            .background(Color.Transparent)
                        , colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                    )

                    ListItem(
                        overlineContent = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Text(
                                        currentReel.post_property.landCategoryText ?: "",
                                        color = newBlack,
                                        fontSize = constants.textUnit(18),
                                        fontFamily = constants.fontFamily(0)
                                    )
                                    Text(
                                        " \u2022 ${currentReel.post_property.is_this_property_for_rent_or_lease}",
                                        color = newBlack,
                                        fontSize = constants.textUnit(14),
                                        fontFamily = constants.fontFamily(1)
                                    )
                                }

                        }
                        , headlineContent = {
                            Column() {
                                Row(
                                    modifier = Modifier
                                    , verticalAlignment = Alignment.CenterVertically
                                    , horizontalArrangement = Arrangement.Start
                                ) {
                                    Image(
                                        painter = painterResource(R.drawable.locationenquiry), "",
                                        modifier = Modifier.size(16.dp)
                                    )

                                    constants.spacer(4)

                                    Text(
                                        "${currentReel.post_property.city}, ${currentReel.post_property.state}",
                                        color = Color(0xff7E7E7E),
                                        fontSize = constants.textUnit(14),
                                        fontFamily = constants.fontFamily(1)
                                    )
                                }
                                constants.spacer(4)
                            }
                        }
                        , supportingContent = {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                , verticalAlignment = Alignment.CenterVertically
                                , horizontalArrangement = Arrangement.SpaceBetween
                            )
                            {
                                Card(
                                    modifier = Modifier
                                        .height(if (forTab())84.dp else 64.dp)
                                        .weight(4.5f)
                                       // .width(138.dp)
                                    , shape = RoundedCornerShape(6.dp)
                                    , colors = CardDefaults.cardColors(
                                        containerColor = Color(0xffCECECE).copy(.2f)
                                    )
                                    //, elevation = CardDefaults.cardElevation(8.dp)
                                )
                                {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(horizontal = 6.dp)
                                            ,verticalArrangement = Arrangement.SpaceEvenly
                                        , horizontalAlignment = Alignment.Start
                                    ) {


                                        BasicText(
                                            text = "\u20B9 ${currentReel.post_property.rent?.ifEmpty { currentReel.post_property.lease_amount }  }",
                                            color =  { newBlack },
                                            style = TextStyle(fontFamily = constants.fontFamily(0)),
                                            autoSize = TextAutoSize.StepBased(minFontSize = 6.sp, constants.textUnit(16), stepSize = 2.sp)
                                        )


                                        Text(
                                            "\u20B9 Amount",
                                            color = Color(0xff323232),
                                            fontSize = constants.textUnit(14),
                                            fontFamily = constants.fontFamily(3)
                                        )
                                    }
                                }

                                constants.spacer(4)

                                Card(
                                    modifier = Modifier
                                        .height(if (forTab())84.dp else 64.dp)
                                        .weight(4.5f)
                                        //.width(138.dp)
                                    , shape = RoundedCornerShape(6.dp)
//                                    , elevation = CardElevation()
                                        //CardDefaults.cardElevation(12.dp)
                                    , colors = CardDefaults.cardColors(
                                        containerColor = Color(0xffCECECE).copy(.2f)
                                    )

                                )
                                {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(horizontal = 6.dp)
                                       , verticalArrangement = Arrangement.SpaceEvenly
                                        , horizontalAlignment = Alignment.Start
                                    ) {
                                        Text(
                                            "${currentReel.post_property.carpet_area} ${currentReel.post_property.carpet_area_unit}",
//                                            "200 sq.ft",
                                            color = newBlack,
                                            fontSize = constants.textUnit(16),
                                            fontFamily = constants.fontFamily(0)
                                        )

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                            , horizontalArrangement = Arrangement.Start
                                        ) {
                                            Image(painter = painterResource(R.drawable.reelscarpetareaicon) , "",
                                                modifier = Modifier.size(12.dp))

                                            constants.spacer(2)

                                            Text(
                                                "Carpet Area",
                                                color = Color(0xff323232),
                                                fontSize = constants.textUnit(14),
                                                fontFamily = constants.fontFamily(3)
                                            )
                                        }
                                    }
                                }

                                constants.spacer(4)

                                Card(
                                    modifier = Modifier
                                        .height(if (forTab())84.dp else 64.dp)
                                        .weight(1f)
                                        //.width(38.dp)
                                        .noRippleClickable {
                                            ClickHelper.getInstance().clickOnce {
                                                constants.Reels_ViewModel.clear_view_pro_Details()
                                                vd_Loader = true
                                                CoroutineScope(Dispatchers.IO).launch {
                                                    delay(1000)
                                                    vd_Loader = false
                                                }
                                                player?.pause()
                                                constants.Reels_ViewModel.add_View_Property_Details(
                                                    videos[pagerState.currentPage]
                                                )
                                                var mode = if (videos[pagerState.currentPage].post_property.video?.isEmpty() == true) 1 else 0

//                                                if (constants.PostProperty_ViewModel.viewDetailsFlow.value == ViewDetailsFlow.NONE) {
                                                    constants.PostProperty_ViewModel.setViewDetailsFlow(
                                                        when {
                                                            constants.PostProperty_ViewModel.viewDetailsFlow.value == ViewDetailsFlow.RENTOUT -> ViewDetailsFlow.RENTOUT
                                                            constants.PostProperty_ViewModel.viewDetailsFlow.value == ViewDetailsFlow.OTHERS -> ViewDetailsFlow.OTHERS
                                                            constants.PostProperty_ViewModel.viewDetailsFlow.value == ViewDetailsFlow.EXPIRY -> ViewDetailsFlow.EXPIRY
                                                            videos[pagerState.currentPage].user_id == AppPreferences.getUserId() -> ViewDetailsFlow.OWN
                                                            else -> ViewDetailsFlow.OTHERS
                                                        }

                                                    )
//                                                }


                                                println("VIEWMODE -- ${mode} --- ${viewModel.view_Property_Details_Mode.value} --- ${if (videos[pagerState.currentPage].post_property.video?.isEmpty() == true) 1 else 0}")
                                                viewModel.view_Property_Details_Mode.value = mode


                                                navController.navigate(VideosScreenFlow.ViewPropertyStructure.route)
                                            }
                                        }
                                        .border(1.dp , Color(0xffCECECE) , RoundedCornerShape(4.dp))
                                    , shape = RoundedCornerShape(4.dp)
                                    , colors = CardDefaults.cardColors(
                                        containerColor = Color(0xffEBEBEB)
                                    )
                                )
                                {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                        , contentAlignment = Alignment.Center
                                    ) {

                                        if (vd_Loader) {
                                            CircularProgressIndicator(
                                                modifier = Modifier.size(24.dp),
                                                color = newBlue
                                            )
                                        } else {
                                            Image(
                                                painter = painterResource(R.drawable.right_arrow), "",
                                                colorFilter = ColorFilter.tint(Color(0xff575757)
                                                )
                                            )
                                        }
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
        }
    }
}









@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReelsView_Search_Flow(
    navController: NavHostController,
    startIndex: Int = 0,
    viewModel: Common_H_ViewModel
)  {

    logger("Reels Udpate media" , "1234567890-")


    viewModel.toggleshowBABars(false)
    viewModel.toggleshowTABars(false)

    var network  = rememberNetworkStatus()

    val BA_Bar_Listener = viewModel.showBABars.collectAsState()
    val TA_Bar_Listener = viewModel.showTABars.collectAsState()

    LaunchedEffect (BA_Bar_Listener , TA_Bar_Listener){
        viewModel.toggleshowBABars(BA_Bar_Listener.value)
        viewModel.toggleshowTABars(TA_Bar_Listener.value)
    }

    val context = LocalContext.current

    val send_Eq_State = constants.Reels_ViewModel.send_Enquiry_Btm_Sheet.collectAsState()


    val videos by constants.Reels_ViewModel.videos.collectAsStateWithLifecycle()
    logger("Reels Udpate media" , "${videos.firstOrNull()?.post_property?.images}")
    println("\"Reels Udpate media\" ${videos.firstOrNull()?.post_property}")
    //constants.Search_ViewModel.search_Result_Content.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val playerManager = remember { VideoPlayerManager(context) }

    val total = videos.size
//    val pagerState = rememberPagerState(
//        initialPage = startIndex.coerceIn(0, total - 1),
//        pageCount = { if (total > 0) total else 1 }
//    )
    val pagerState = rememberPagerState(
        initialPage = if (total > 0) startIndex.coerceIn(0, total - 1) else 0,
        pageCount = { if (total > 0) total else 1 }
    )

    var previousPage by remember { mutableStateOf(-1) }

    // Track loading states
    val isBuffering = remember { mutableStateMapOf<Int, Boolean>() }
    val isPrepared = remember { mutableStateMapOf<Int, Boolean>() }

    // Comment bottom sheet state
    val cmt_btm_Sheet = viewModel.comment_btm_Sheet.collectAsState()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val reelsBTMSheetState = viewModel.reelsBtm_sheet.collectAsState()
    val reelsBTMSOptions = constants.Reels_ViewModel.reelsBTMSOptions.collectAsState()


    var mark_as_Sold = remember { mutableStateOf(false) }
    var delete_Post by remember { mutableStateOf(false) }


    var isLike_Loading = remember { mutableStateOf(false) }
    var isSave_Loading = remember { mutableStateOf(false) }

    var report_BS = remember { mutableStateOf(false) }
    val report_Options = constants.Profile_ViewModel.profile_Report_Options.collectAsState()
    val report_success = constants.Profile_ViewModel.report_Submit_Success.collectAsState()


    // rento
    var notInterested_Btm = remember { mutableStateOf(false) }
    // rento

    val notInterestedOptions = constants.Profile_ViewModel.notInterestedOptions.collectAsState()





    DisposableEffect(Unit) {
        onDispose {
            playerManager.releaseAll()
            viewModel.toggleReelsBTMSheet(false)
        }
    }


    LaunchedEffect(pagerState.currentPage, videos.size) {
        if (videos.isEmpty()) return@LaunchedEffect

        val currentIndex = pagerState.currentPage.coerceIn(0, videos.lastIndex)
        if (currentIndex == previousPage) return@LaunchedEffect

        // Stop previous player ASAP
        if (previousPage != -1) {
            playerManager.pauseVideo(previousPage)
            playerManager.releasePlayer(previousPage)
        }

        // Start current player
        val currentVideo = videos[currentIndex]

        playerManager.playVideo(currentIndex)

        // Preload next
        val preloadIndices = listOf(currentIndex + 1).filter { it in 0 until videos.size }
        preloadIndices.forEach { index ->
            val video = videos[index]
            playerManager.getOrCreatePlayer(index, video.user_post_id, video.post_property.video?.firstOrNull()?.url ?:"")
            playerManager.prepareVideoIfNeeded(index)
        }

        // Release far ones
        playerManager.releaseFarPlayers(currentIndex, keepRange = 1)

        previousPage = currentIndex
    }


    // Lifecycle management
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (videos.isEmpty()) return@LifecycleEventObserver

            val currentIndex = pagerState.currentPage.coerceIn(0, videos.lastIndex)

            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    playerManager.pauseVideo(currentIndex)
                }
                Lifecycle.Event.ON_RESUME -> {
                    playerManager.playVideo(currentIndex)
                }
                Lifecycle.Event.ON_DESTROY -> {
                    playerManager.releaseAll()
                }
                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            playerManager.releaseAll()
        }
    }

    if (videos.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Column (Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Image(painter = painterResource(R.drawable.errorvideorento) , "",
                    modifier = Modifier.size(200.dp))
                constants.spacer(8)
                Text("No videos to display", color = Color.White)
            }
        }
        return
    }

    val coroutineScope = rememberCoroutineScope()

    var activateRentedout = remember { mutableStateOf(false) }
    var activaterenew = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    )
    {
            VerticalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                key = { page -> videos.getOrNull(page)?.user_post_id ?: page } // Important: use unique keys
            )
            { page ->




                val item = videos.getOrNull(page) ?: return@VerticalPager
                val hasVideo = !item.post_property.video.isNullOrEmpty()
                // Get the player for this specific page
                //val player = playerManager.getOrCreatePlayer(page, item.user_post_id, item.video)
                // Create player only if video exists
                val player = if (hasVideo) {
                    remember(page) {
                        playerManager.getOrCreatePlayer(page, item.user_post_id, item.post_property.video.firstOrNull()?.url ?:"")
                    }
                } else null

                //player.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
                //player.surfaceSize = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(6f)
                            .background(Color.Black)
                    )
                    {


                        if (item.post_property.images?.isNotEmpty() == true) {
                            val animationType = SlideshowAnimation.values()[page % SlideshowAnimation.values().size]

                            player?.release()
                            playerManager.releaseAll()

                            SingleSlideshowReel2_Search_Flow(
                                item = item,
                                page = page,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(400.dp)
                                    .align(Alignment.Center),
                                videos = videos,
                                navController = navController,
                                pagerState = pagerState,

                                isLike_Loading = isLike_Loading,
                                isSave_Loading = isSave_Loading,
                                intervalMillis = 1000L,
                                viewModel
                            )


                        }
                        else  if (hasVideo) {
                            // val player = playerManager.getOrCreatePlayer(page, item.user_post_id, item.post_property.video)
//                    var isPlayerPlaying by remember(page, player) { mutableStateOf(player?.isPlaying) }

                            var isPlayerPlaying by remember(page, player) { mutableStateOf(player?.isPlaying) }


                            key(page, item.user_post_id) {
                                // key("player_page_${page}_${item.user_post_id}") {

                                if (page == pagerState.currentPage && player != null) {

                                    PlayerSurface(
                                        player = player,
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .fillMaxWidth()
                                            .aspectRatio(6f / 12f)

                                            .pointerInput(page, item.user_post_id) {
                                                detectTapGestures(
                                                    onTap = {
                                                        if (player.isPlaying == true) {
                                                            playerManager.pauseVideo(page)
                                                            isPlayerPlaying = false
                                                        } else {
                                                            playerManager.playVideo(page)
                                                            isPlayerPlaying = true
                                                        }
                                                    },
                                                    onDoubleTap = {
                                                        // constants.Reels_ViewModel.toggleLike_Reels(item.user_post_id)
                                                    }
                                                )
                                            }
                                    )
                                }
                                //}

                            }

                            // Track buffering
                            DisposableEffect(player, page) {
                                val listener = object : Player.Listener {
                                    override fun onPlaybackStateChanged(playbackState: Int) {
                                        when (playbackState) {
                                            Player.STATE_BUFFERING -> { isBuffering[page] = true; isPrepared[page] = false }
                                            Player.STATE_READY -> { isBuffering[page] = false; isPrepared[page] = true }
                                            Player.STATE_ENDED -> { player?.seekTo(0); if (page == pagerState.currentPage) player?.playWhenReady = true }
                                            Player.STATE_IDLE -> { isBuffering[page] = false; isPrepared[page] = false }
                                        }
                                    }
                                }
                                player?.addListener(listener)
                                onDispose { player?.removeListener(listener); isBuffering.remove(page); isPrepared.remove(page) }
                            }

                            // Show loading indicator only for current video
                            if (isBuffering[page] == true && page == pagerState.currentPage) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .size(48.dp),
                                    color = Color.White
                                )
                            }

                            isPlayerPlaying?.let {
                                if(!it){
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(Color.Black.copy(0.5f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.play_arrow),
                                            contentDescription = "Play",
                                            tint = Color.White,
                                            modifier = Modifier.size(32.dp)
                                        )
                                    }
                                }
                            }

                            // Overlay options (likes, comments, etc.)
                            Reels_Options_Search_Flow(
                                modifier = Modifier.align(Alignment.BottomCenter),
                                videos,
                                page,
                                navController,
                                pagerState,
                                player = player,
                                duration =  remember { mutableStateOf(1L) } ,
                                position = remember { mutableStateOf(0L) } ,
                                isLike_Loading,
                                isSave_Loading,
                                viewModel
                            )

                        }


                        else {
                            player?.release()
                            playerManager.releaseAll()
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black),
                                contentAlignment = Alignment.Center
                            )
                            {

                                    val type = when {
                                        item.user_id == AppPreferences.getUserId() -> 1
                                        else -> 2
                                    }

                                    PhotoRequestAssistant(
                                        type,
                                        Color.White,
                                        item.user_post_id,
                                        navController,
                                        modifier = Modifier.fillMaxSize(),
                                        item.post_interest
                                    )


                                Reels_Options_Search_Flow(
                                    modifier = Modifier.align(Alignment.BottomCenter),
                                    videos,
                                    page,
                                    navController,
                                    pagerState,
                                    player = player,
                                    duration =  remember { mutableStateOf(1L) },
                                    position = remember { mutableStateOf(0L) },
                                    isLike_Loading,
                                    isSave_Loading,
                                    viewModel
                                )
                            }
                        }

                        //Text("${item.user_post_id}", color = Color.Red , modifier = Modifier.align(Alignment.Center))



                        SubcomposeAsyncImage(
                            model = R.drawable.left_arrow,
                            " ",
                            colorFilter = ColorFilter.tint(Color.White),
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = if (forTab())  16.dp else rememberNotchHeightDp().value)
                                .size(24.dp)
                                .align(Alignment.TopStart)
                                .noRippleClickable{
                                    coroutineScope.launch {
                                        // Stop playback smoothly
                                        //playerManager.pa()
                                        delay(100) // give ExoPlayer a brief frame to finish rendering
                                        playerManager.releaseAll()
                                        constants.PostProperty_ViewModel.setViewDetailsFlow(
                                            ViewDetailsFlow.NONE
                                        )

                                        constants.PostProperty_ViewModel.setViewDetailsFlow(
                                            ViewDetailsFlow.NONE)

                                        constants.Profile_ViewModel.change_OwnProfileTab(0)

                                        // Now navigate away
                                        AppPreferences.save_Post_Id(0)
                                        constants.Profile_ViewModel.set_From_SoldOuts(false)
                                        constants.API_Vm.totalPages_PS_FF = 0
                                        constants.PostProperty_ViewModel.setViewDetailsFlow(
                                            ViewDetailsFlow.NONE
                                        )

                                        constants.PostProperty_ViewModel.setPostFlow(PostFlow.NONE)

                                        navController.navigateUp()
                                    }
//                            AppPreferences.save_Post_Id(0)
//                            constants.Profile_ViewModel.set_From_SoldOuts(false)
//                            constants.API_Vm.totalPages_PS_FF = 0
//                            navController.navigateUp()
                                }
                        )


                    }

                    Reels_Options_Search_Flow_Static(
                        modifier = Modifier,
                        videos,
                        page,
                        navController,
                        pagerState,
                        player = player,
                        duration =  remember { mutableStateOf(1L) } ,
                        position = remember { mutableStateOf(0L) } ,
                        isLike_Loading,
                        isSave_Loading,
                        viewModel
                    )
                }

            }
    }


    val repost_Btm = remember { mutableStateOf(false) }

    val viewdetailFlow = constants.PostProperty_ViewModel.viewDetailsFlow.collectAsState()



    if (reelsBTMSheetState.value) {
        ModalBottomSheet(
            onDismissRequest = {
                constants.Common_H_ViewModel.toggleReelsBTMSheet(false)
            },
            containerColor = newWhite
            , modifier = Modifier.fillMaxWidth()
        )
        {

            println("viewDEETAILS FLOW REELS -- ${viewdetailFlow.value}")


            LaunchedEffect(Unit) {
               constants.Reels_ViewModel.resetReelsBTMSOptions()
                when {
                    viewdetailFlow.value == ViewDetailsFlow.EXPIRY -> {
                        println("aqwqwqwqwqw2reels")

                        constants.Reels_ViewModel.removeReelsBTMSOptions(
                            constants.Reels_ViewModel.expiredIdOptions
                        )
                    }


                    viewdetailFlow.value == ViewDetailsFlow.OTHERS -> {
                        println("aqwqwqwqwqw7reels")
                        constants.Reels_ViewModel.removeReelsBTMSOptions(
                            constants.Reels_ViewModel.pviewotherIdOptions
                        )
                    }



                    viewdetailFlow.value == ViewDetailsFlow.RENTOUT -> {
                        println("indisddejeididjdjdfj2222222")
                        println("aqwqwqwqwqw3reels")

                        constants.Reels_ViewModel.removeReelsBTMSOptions(
                            constants.Reels_ViewModel.soldoutIdOptions
                        )
                    }

                    constants.Profile_ViewModel.selectedOwnProfileTab.value == OwnProfileTab.EXPIRED -> {
                        println("aqwqwqwqwqw4reels")
                        constants.Reels_ViewModel.removeReelsBTMSOptions(
                            constants.Reels_ViewModel.expiredIdOptions
                        )
                    }

                    isWithinLast5DaysOfValidity(videos[pagerState.currentPage].post_property?.created_at ?: "") &&  videos[pagerState.currentPage].user_id == AppPreferences.getUserId() -> {
//                        viewdetailFlow.value == ViewDetailsFlow.RENEW -> {
                        println("aqwqwqwqwqw0reels")
                        constants.Reels_ViewModel.removeReelsBTMSOptions(
                            constants.Reels_ViewModel.renewpostOptions
                        )
                    }

                    videos[pagerState.currentPage].user_id == AppPreferences.getUserId() -> {
                        println("aqwqwqwqwqw5reels")
                        constants.Reels_ViewModel.removeReelsBTMSOptions(
                            constants.Reels_ViewModel.ownIdOptions
                        )
                    }



                    else -> {
                        println("aqwqwqwqwqw6reels")
                        constants.Reels_ViewModel.removeReelsBTMSOptions(constants.Reels_ViewModel.pviewotherIdOptions)
                    }
                }
            }

            val data = reelsBTMSOptions.value

            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
            )
            {
                data.forEachIndexed { index, Options ->
                    Column (
                        modifier = Modifier
                            // .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .noRippleClickable {
                                when (Options.id) {
                                    0 -> { /* repost logic */
                                        constants.Reels_ViewModel.clear_view_pro_Details()
                                        //AppPreferences.save_Post_Id(videos[pagerState.currentPage].user_post_id)
                                        repost_Btm.value = true
                                        constants.Common_H_ViewModel.toggleReelsBTMSheet(false)
                                    }
                                    1 -> { /* edit logic */
                                        constants.Reels_ViewModel.clear_view_pro_Details()
                                        constants.PostProperty_ViewModel.setPostFlow(PostFlow.EDIT)
                                        navController.navigate(ProfileScreenFlow.Edit_Property_Option.route)
                                        constants.Common_H_ViewModel.toggleReelsBTMSheet(false)
                                    }
                                    2 -> { /* mark as sold */
                                        mark_as_Sold.value = true
                                        constants.Common_H_ViewModel.toggleReelsBTMSheet(false)
                                    }
                                    3 -> { /* delete */
                                        println("COMING INTO HERE")

                                        delete_Post = true
                                        viewModel.toggleReelsBTMSheet(false)


                                    }
                                    4 -> { /* share */
                                        constants.DefaultShare(
                                            "https://toletspot.com/property/${videos[pagerState.currentPage].user_post_id}",
                                            1
                                        )
                                        constants.Common_H_ViewModel.toggleReelsBTMSheet(false)
                                    }
                                    5 -> { /* report */
                                        if (videos[pagerState.currentPage].post_property.is_report != 1) {
                                            constants.Profile_ViewModel.toggle_ReportSucces_True()

                                            report_BS.value = true
                                            constants.Profile_ViewModel.toggle_ProfileReport_Options(
                                                -1
                                            )
                                            constants.Common_H_ViewModel.toggleReelsBTMSheet(false)
                                        } else {
                                            // constants.Profile_ViewModel.toggle_ReportSucces_True()

                                            constants.Common_H_ViewModel.toggleReelsBTMSheet(false)

                                            GlobalSnackbar.show("Post already reported")
                                        }
                                    }
                                    6 -> { /* not interested */
                                        notInterested_Btm.value = true
                                    }
                                    7 -> {
                                        /* activate */
                                        activateRentedout.value = true
                                    }

                                    8 -> {
                                        activaterenew.value = true
                                    }
                                }
                            }
                        //,verticalArrangement = Arrangement.spacedBy(8.dp)
                        , horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(Color(0xffF7F0DC)),
                            contentAlignment = Alignment.Center
                        ) {
                            SubcomposeAsyncImage(
                                model = Options.icon,
                                contentDescription = "",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(Options.title, color = newBlack, fontSize = constants.textUnit(14))
                    }
                    //Spacer(modifier = Modifier.padding(8.dp))
                }
            }
        }
    }


    if (repost_Btm.value) {
        ModalBottomSheet(
            onDismissRequest = {
                repost_Btm.value = false

                AppPreferences.save_Post_Id(0)
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
//                Image(painter = painterResource(R.drawable.repost) , "")


                CommonText(
                    "Repost Property",
                    newBlack,
                    18,
                    1
                    , modifier = Modifier
                        .padding(horizontal = 8.dp)
                )


                Text("Repost lets you create a new post using details from your existing one. You can update photos, videos, or edit information before posting again. The original post will remain unchanged."
                    , modifier = Modifier
                        .padding(horizontal = 8.dp)
                    , textAlign = TextAlign.Start
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
                            .noRippleClickable{

                                constants.PostProperty_ViewModel.clear_Forms()
                                constants.PostProperty_ViewModel.first_Form_selected_PP(-1)
                                constants.PostProperty_ViewModel.select_Land_Cat_Id(-1)
                                constants.PostProperty_ViewModel.LandSubType_Selected_Click(-1)
//                            constants.PostProperty_ViewModel.pp_SecondForm_Residential_Select_Option(
//                                -1
//                            )
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
                                constants.PostProperty_ViewModel.clearAllPostFields()

                                constants.PostProperty_ViewModel.check_Price_Negotiation(false)
                                constants.PostProperty_ViewModel.put_budget_Price_PF5("")

                                constants.PostProperty_ViewModel.clear_Media()
                                constants.PostProperty_ViewModel.goToPPFormPage(0, 7)
                                AppPreferences.save_Post_Id(videos[pagerState.currentPage].user_post_id)

                                get_Form_Preview_API_CALL { result ->
                                    when (result) {
                                        0 -> { state.value = result}
                                        1 -> {}
                                        2 -> {}
                                        3 -> {
                                            println("REPOST CLICKER SUCCESS")
                                            val server_Data = constants.PostProperty_ViewModel.get_previewFormData()

                                            println("Full Server Data --- ${server_Data}")

                                            server_Data?.let { data ->
                                                constants.PostProperty_ViewModel.update_Selected_Field_Form4 {
                                                    data.toSelectedOptionsForm4()
                                                }
                                            }

                                            AppPreferences.save_Post_Id(server_Data?.user_post_id ?: 0)


                                            println("POST ID -- ${AppPreferences.get_Post_Id()}")

                                            constants.PostProperty_ViewModel.first_Form_selected_PP(
                                                (server_Data?.user_type ?: "0").toInt()
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

                                            constants.PostProperty_ViewModel.set_onSelected_ProType((server_Data?.land_type_id ?: 0) )


                                            constants.PostProperty_ViewModel.pp_SecondForm_Residential_Select_Option(
                                                server_Data?.land_categorie_id ?: 0
                                            )

                                            constants.PostProperty_ViewModel.select_Land_Cat_Id(
                                                server_Data?.land_categorie_id ?: 0
                                            )

                                            constants.PostProperty_ViewModel.LandSubType_Selected_Click(server_Data?.land_categorie_id ?: 0)

                                            println("REPOST FROM PFOILE TOP____ ${server_Data?.land_type_id}")
                                            constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value =  constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value.copy(
                                                first = server_Data?.land_type_id ?: 0,
                                                second = server_Data?.land_categorie_id ?: 0
                                            )

                                            println("REPOST FROM PFOILE TOP____ ${server_Data?.land_type_id ?: 0} (((( ${constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value}")


                                            constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value = Pair((server_Data?.land_type_id ?: 0)  , server_Data?.land_categorie_id ?: 0)

                                            println("Step 2 --- ${(server_Data?.land_type_id ?: 0) - 1 } -***${ constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value}***-- ${server_Data?.land_categorie_id ?: 0}")
//                                            println("LOCARTION DERAIKS sfjngv adsfbvkhjd REPOST ${server_Data.pincode} -__${server_Data.longitude}--${server_Data.latitude}--${server_Data.address}--${server_Data.city}- ${server_Data.country} -- ${data.state}")

                                            // Safely set values if they’re not empty
                                            if (!server_Data?.pincode.isNullOrEmpty()) {
                                                constants.PostProperty_ViewModel.set_pincode3(server_Data?.pincode ?: "")
                                            }
                                            if (!server_Data?.country.isNullOrEmpty()) {
                                                constants.PostProperty_ViewModel.set_country3(server_Data?.country ?: "")
                                            }
                                            if (!server_Data?.state.isNullOrEmpty()) {
                                                constants.PostProperty_ViewModel.set_state3(server_Data?.state ?: "")
                                            }
                                            if (!server_Data?.city.isNullOrEmpty()) {
                                                constants.PostProperty_ViewModel.set_city3(server_Data?.city?:"")
                                            }
                                            if (!server_Data?.locality.isNullOrEmpty()) {
                                                constants.PostProperty_ViewModel.set__selectedLocality3(server_Data?.locality ?:"")
                                            }
//
                                            if (server_Data?.latitude?.isNotEmpty() == true && server_Data?.longitude?.isNotEmpty() == true) {
                                                constants.PostProperty_ViewModel.add_Pinned_Lat_Long(
                                                    LatLng(
                                                        server_Data?.latitude?.toDouble() ?: 0.0,
                                                        server_Data?.longitude?.toDouble() ?: 0.0
                                                    )
                                                )
                                            }

//                                            if (server_Data.images.isNotEmpty()){
//                                                constants.PostProperty_ViewModel.addImages(server_Data?.images ?:"")
//                                            }
//                                            else if(server_Data.video.isNotEmpty()){
//                                                constants.PostProperty_ViewModel.addVideo(server_Data?.images ?:"")
//
//                                            }

                                            println("LOCALOTY CHECK -- ${server_Data?.locality ?:""} -- ${server_Data?.address ?:""}")
                                            constants.PostProperty_ViewModel.add_pp3_Data(
                                                PP3_API_DC(
                                                    pincode = server_Data?.pincode ?: "",
                                                    country = server_Data?.country ?:"",
                                                    state = server_Data?.state ?:"",
                                                    city = server_Data?.city ?:"",
                                                    locality = server_Data?.locality ?:""
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

                                            println("VIDEO ADDED IN THE List CHECH -- ${server_Data?.images}  -- ${server_Data?.video}")
                                            if (!server_Data?.images.isNullOrEmpty()) {
//                                                val imageMediaList = server_Data.images.map { imageUri ->
//                                                    UploadPropertyMedia(uri = Uri.parse(imageUri), isVideo = false)
//                                                }
//                                                constants.PostProperty_ViewModel.addImages(imageMediaList)
                                            }
                                            else if (!server_Data?.video.isNullOrEmpty()) {
                                                println("VIDEO ADDED IN THE List")
//                                                val videoMedia = UploadPropertyMedia(
//                                                    uri = Uri.parse(server_Data.video),
//                                                    isVideo = true
//                                                )
//                                                constants.PostProperty_ViewModel.addVideo(videoMedia)
                                            }


                                            println("VIDEO ADDED IN THE List CHECH -- ${constants.PostProperty_ViewModel.mediaList.value}")

                                            constants.PostProperty_ViewModel.select_Land_Type(server_Data?.land_type_id ?: 0)

                                            constants.PostProperty_ViewModel.loadDraftFromServer(
                                                imageUrls = server_Data?.images ?: emptyList(),
                                                videoUrls = server_Data?.video ?: emptyList(),
                                                coverUrl = server_Data?.thumbnail ?: ""
                                            )

//                                            val budget_Price =
//                                                constants.PostProperty_ViewModel.put_budget_Price_PF5(server_Data?.price ?: "")

                                            println("SERVER DATA -- ${server_Data} ---- cons.${constants.PostProperty_ViewModel.get_Selected_Fields_Form()}")

                                            constants.Profile_ViewModel.set_From_Repost(1)
                                            constants.PostProperty_ViewModel.set_Post_Form_Flow(2)


                                            constants.PostProperty_ViewModel.setPostFlow(PostFlow.REPOST)

                                            // ✅ Navigate after everything is done
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

    // renew
    if (activaterenew.value) {
        ModalBottomSheet(
            onDismissRequest = {
                repost_Btm.value = false

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
//                Image(painter = painterResource(R.drawable.repost) , "")



                CommonText(
                    "Activate Listing",
                    Color(0xff575757),
                    18,
                    1
                    , modifier = Modifier.align(Alignment.Start).padding(horizontal = 16.dp)
                )

                CommonText("When you tap Activate, the same post will be reactivated without making any changes. Everything will remain exactly as it is — we’re just switching it back to active status.",
                    Color(0xff575757),
                    14,
                    3
                    , modifier = Modifier.align(Alignment.Start).padding(horizontal = 16.dp)
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
                            .border(1.dp , Brush.linearGradient(newPurpleGradientBorder) , RoundedCornerShape(6.dp))
                            .noRippleClickable {
                                /*constants.API_Vm.put_Sold_Unsold_Property(
                                    user_id = AppPreferences.getUserId(),
                                    user_post_id = videos[pagerState.currentPage].user_post_id,
                                    status = 2,
                                )
                                { aPI_Result_Handling ->
                                    when (aPI_Result_Handling) {
                                        is API_Result_Handling.NoData -> {}
                                        is API_Result_Handling.Error -> {

                                            GlobalSnackbar.show("Something went wrong")
                                            isLoading = false
                                            activateRentedout.value = false
                                        }

                                        is API_Result_Handling.Deactivated -> {
                                            //resultCallback(5)
                                        }

                                        is API_Result_Handling.Loading -> {
                                            isLoading = true
                                        }

                                        is API_Result_Handling.Success -> {

                                            GlobalSnackbar.show("Property Activated Successfully ")
                                            constants.Reels_ViewModel.setNewTimeStampOnRenew(videos[pagerState.currentPage].user_post_id)

                                            isLoading = false
                                            activateRentedout.value = false
                                            navController.navigateUp()
                                        }
                                    }
                                }
*/
                                constants.API_Vm.activate_RentedOut(
                                    user_id = AppPreferences.getUserId(),
                                    user_post_id = videos[pagerState.currentPage].user_post_id,
                                )
                                { aPI_Result_Handling ->
                                    when (aPI_Result_Handling) {
                                        is API_Result_Handling.NoData -> {}
                                        is API_Result_Handling.Error -> {

                                            GlobalSnackbar.show("Something went wrong")
                                            //isLoading = false
                                            activateRentedout.value = false
                                        }

                                        is API_Result_Handling.Deactivated -> {
                                            //resultCallback(5)
                                        }

                                        is API_Result_Handling.Loading -> {
                                            //isLoading = true
                                        }

                                        is API_Result_Handling.Success -> {

                                            GlobalSnackbar.show("Property Activated Successfully ")

                                            constants.Reels_ViewModel.setNewTimeStampOnRenew(videos[pagerState.currentPage].user_post_id)
                                            constants.Reels_ViewModel.deleteVideoById_Profile_Post_Reels(videos[pagerState.currentPage].user_post_id,)
                                            //isLoading = false
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


    /// activte rented out
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
//                Image(painter = painterResource(R.drawable.repost) , "")


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
                                    user_post_id = videos[pagerState.currentPage].user_post_id,
                                    status = 2,
                                )
                                { aPI_Result_Handling ->
                                    when (aPI_Result_Handling) {
                                        is API_Result_Handling.NoData -> {}
                                        is API_Result_Handling.Error -> {
                                            isLoading = false
                                        }

                                        is API_Result_Handling.Deactivated -> {
                                            //resultCallback(5)
                                        }

                                        is API_Result_Handling.Loading -> {
                                            isLoading = true
                                        }

                                        is API_Result_Handling.Success -> {
                                            isLoading = false
                                            GlobalSnackbar.show("Property Activated Successfully ")

//                                            constants.Reels_ViewModel.setNewTimeStampOnRenew(
//                                                view_Details_Data.value?.user_post_id ?: 0
//                                            )
                                            constants.Reels_ViewModel.deleteVideoById_Profile_Post_Reels(
                                                videos[pagerState.currentPage].user_post_id
                                            )

                                            //isLoading = false
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

    // Report Sheet
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
                                        textStyle = TextStyle(
                                            color = newBlack,
                                            fontSize = constants.textUnit(14),
                                            fontFamily = constants.fontFamily(2)
                                        ),
                                        singleLine = true,
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
                    else {
                        Column(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                            , verticalArrangement = Arrangement.SpaceEvenly
                            , horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            SubcomposeAsyncImage(
                                model = R.drawable.profile_report_submit_success
                                ,""
                                , modifier = Modifier
                                    .size(150.dp)
                            )


                            Text(
                                text = "Submitted Successfully",
                                color = newBlack,
                                fontSize = constants.textUnit(18),
                                fontFamily = constants.fontFamily(0)
                            )


                            Text(
                                text = "Thank you for bringing this to our attention.",
                                color = newBlack,
                                fontSize = constants.textUnit(12),
                                fontFamily = constants.fontFamily(3)
                            )

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
                                        .noRippleClickable{
                                            if (network.value == NetworkStatus.Online) {
                                                if (videos[pagerState.currentPage].post_property.is_report != 1) {

                                                    if (user_Manual_report_String.value.isEmpty()) {
                                                        user_Manual_report_String.value =
                                                            constants.Profile_ViewModel.getSelectedProfileReportOptionDescription()
                                                                ?: ""
                                                    }
                                                    constants.API_Vm.put_Report_All(
                                                        user_id = AppPreferences.getUserId(),
                                                        user_post_id = videos[pagerState.currentPage].user_post_id.toString(),
                                                        //AppPreferences.get_Post_Id(),
                                                        receiver_id = videos[pagerState.currentPage].user_id.toString(),
                                                        comment_id = "",
                                                        report_sentence_id = (constants.Profile_ViewModel.getSelectedProfileReportOptionId()
                                                            ?.plus(1)) ?: 0,
                                                        report_sentence = user_Manual_report_String.value,
                                                        status = 2,
                                                    )
                                                    { apiResultHandling ->

                                                        when (apiResultHandling) {
                                                            is API_Result_Handling.Loading -> {
                                                                // loading
                                                                //constants.PostProperty_ViewModel.change_Status_PFs(true)
                                                            }

                                                            is API_Result_Handling.Error -> {
                                                                // fail
                                                                //constants.PostProperty_ViewModel.change_Status_PFs(false)
                                                            }

                                                            is API_Result_Handling.Success -> {


                                                                constants.Profile_ViewModel.toggleReportSubmissionSuccess()
                                                                constants.Reels_ViewModel.toggleLike_Report(
                                                                    videos[pagerState.currentPage].user_post_id
                                                                )
                                                                //constants.Reels_ViewModel.deleteVideoById_Profile_Post_Reels(videos[pagerState.currentPage].user_id)
                                                                // success
                                                                //constants.PostProperty_ViewModel.change_Status_PFs(false)
                                                            }

                                                            is API_Result_Handling.NoData -> {
                                                                // no data
                                                                //constants.PostProperty_ViewModel.change_Status_PFs(false)
                                                            }

                                                            is API_Result_Handling.Deactivated -> {
                                                                /// resultCallback(5)
                                                            }
                                                        }
                                                    }
                                                } else {

                                                    //constants.Common_H_ViewModel.toggleReelsBTMSheet(false)
//                                                scope.launch {
                                                    GlobalSnackbar.show(" Post Already Reported")
//
//                                                    report_BS.value = false
//                                                }
                                                    //SimpleSnackbar(" Post Already Reported")

                                                }
                                            }else {
                                                toast(constants.activity.getString(R.string.no_Internet))
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


    // Not Interested Sheet
    if (notInterested_Btm.value)
    {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

        ModalBottomSheet(
            onDismissRequest = {
                //constants.Profile_ViewModel.toggle_ReportSucces_True()
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

                //if (report_success.value) {
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
                                    .border(1.dp , Brush.linearGradient(newPurpleGradientBorder) ,RoundedCornerShape(8.dp))
                                    .noRippleClickable{
                                        if (network.value == NetworkStatus.Online) {
                                            if (videos[pagerState.currentPage].post_property.is_report != 1) {
                                                if (user_Manual_report_String.value.isEmpty()) {
                                                    user_Manual_report_String.value =
                                                        constants.Profile_ViewModel.getSelected_NotInterested_OptionDescription()
                                                            ?: ""
                                                }

                                                constants.API_Vm.put_Report_All(
                                                    user_id = AppPreferences.getUserId(),
                                                    user_post_id = videos[pagerState.currentPage].user_post_id.toString(),
                                                    receiver_id = videos[pagerState.currentPage].user_id.toString(),
                                                    comment_id = "",
                                                    report_sentence_id = (constants.Profile_ViewModel.getSelected_NotInterested_OptionId()
                                                        ?.plus(1)) ?: 0,
                                                    report_sentence = user_Manual_report_String.value,
                                                    status = 2,
                                                ) { apiResultHandling ->
                                                    when (apiResultHandling) {
                                                        is API_Result_Handling.Success -> {

                                                            constants.Reels_ViewModel.toggleLike_Report(
                                                                videos[pagerState.currentPage].user_post_id
                                                            )
//                                                                constants.Profile_ViewModel.toggle_ReportSucces_False()

                                                            notInterested_Btm.value = false

                                                            /// GlobalSnackbar.show("Post Reported Successfully")
                                                        }

                                                        is API_Result_Handling.Deactivated -> {
                                                            // Handle deactivation
                                                        }

                                                        is API_Result_Handling.Loading -> {

                                                        }

                                                        else -> {
                                                            toast("Something went wrong")
                                                            // Handle other cases
                                                        }
                                                    }
                                                }
                                            } else {
                                                //scope.launch {
                                                GlobalSnackbar.show("Post Already Reported")
                                                   // snackbarHostState.showSnackbar("Post Already Reported")
                                                    notInterested_Btm.value = false
                                               // }
                                            }
                                        }
                                        else {
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
                //}
            }
        }
    }


    if (send_Eq_State.value) {
        if (videos.isNotEmpty() && pagerState.currentPage <= pagerState.pageCount) {
            Enquiry_Form_Btm_Sheet_Structure(send_Eq_State.value, videos[pagerState.currentPage])
        }
    }

    // Comment sheet
    if (cmt_btm_Sheet.value) {
        playerManager.pauseVideo(pagerState.currentPage)
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.dismiss_Cmt_btm_Sheet()
            },
            sheetState = bottomSheetState,
            containerColor = newWhite,
            sheetGesturesEnabled = false
        ) {
            Comment_Structure(videos, pagerState.currentPage, navController, viewModel)
        }
    }



    if (mark_as_Sold.value == true) {
        playerManager.pauseVideo(pagerState.currentPage)
        Mark_As_Sold_Flow(mark_as_Sold, videos[pagerState.currentPage].user_post_id, navController)
    }

    // delete post
    Common_Popup(
        delete_Post,
        modifier = Modifier
            .background(Color(0xffF7F0DC))
        , image = "",
        userName = "",
        icon = R.drawable.closeenquiry /// or R.drawable
    )
    {
        Column (
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 24.dp)
            , verticalArrangement = Arrangement.spacedBy(12.dp)
            , horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            constants.spacer(4)

            Image(painter = painterResource(R.drawable.deletepopupicon) , "",
                modifier = Modifier.size(64.dp))

            constants.spacer(2)



//            Text(
//                text = "Are you Sure, You want to delete?",
//                color = newBlack,
//                fontSize = constants.textUnit(16),
//                fontFamily = constants.fontFamily(0)
//            )

            Text(
                text = "Are you sure you want to delete this property?",
                color = newBlack,
                fontSize = constants.textUnit(18),
                fontFamily = constants.fontFamily(1)
                , textAlign = TextAlign.Center
               , lineHeight = 24.sp
                , modifier = Modifier.padding(horizontal = if (forTab()) 46.dp else 36.dp)
            )


            constants.spacer(4)

            Row (
                modifier = Modifier
                , horizontalArrangement = Arrangement.Center
                , verticalAlignment = Alignment.CenterVertically
            )
            {
                Box(
                    modifier = Modifier
                        .weight(4f)
                        .height(if (forTab()) 46.dp else 36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xffE8E8E8))
                        .noRippleClickable{
                            delete_Post = false
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
                constants.spacer(2)

                Box(
                    modifier = Modifier
                        .weight(4f)
                        .height(if (forTab()) 46.dp else 36.dp)
                        .noRippleClickable{
                            ClickHelper.getInstance().clickOnce {
                                if (ClickGuard.canClick()) {

                                    if (network.value == NetworkStatus.Online) {
                                        println(" API CALL HIT STATUS __ ue}")
                                        println("COMING INSIDE DELETTING SOLDOUTS")

                                        constants.API_Vm.delete_Post_SM_Drafts(
                                            user_id = AppPreferences.getUserId(),
                                            select_all = 0,
                                            user_post_id = (videos[pagerState.currentPage].user_post_id
                                                ?: 0).toString(),
                                        )
                                        { aPI_Result_Handling ->
                                            when (aPI_Result_Handling) {
                                                is API_Result_Handling.Loading -> {}
                                                is API_Result_Handling.NoData -> {}
                                                is API_Result_Handling.Deactivated -> {
                                                    // resultCallback(5)
                                                }

                                                is API_Result_Handling.Error -> {}
                                                is API_Result_Handling.Success -> {

                                                    if (constants.Profile_ViewModel.from_SoldOuts.value == true) {
                                                        constants.Profile_ViewModel.deleteByPostId_Profile_SoldOuts(
                                                            videos[pagerState.currentPage].user_post_id
                                                                ?: 0
                                                        )
                                                        navController.navigateUp()
                                                        delete_Post = true
                                                    } else {
                                                        println("65212345678987654321234567890-")
                                                        constants.Reels_ViewModel.deleteVideoById_Profile_Post_Reels(
                                                            videos[pagerState.currentPage].user_post_id
                                                                ?: 0
                                                        )
                                                        delete_Post = true

                                                        playerManager.releaseAll()

                                                        // Now navigate away
                                                        AppPreferences.save_Post_Id(0)
                                                        constants.Profile_ViewModel.set_From_SoldOuts(
                                                            false
                                                        )
                                                        constants.API_Vm.totalPages_PS_FF = 0
                                                        navController.navigateUp()
                                                    }

                                                    constants.Common_H_ViewModel.toggleReelsBTMSheet(
                                                        false
                                                    )

                                                }
                                            }
                                        }
                                        /*constants.API_Vm.put_Block_User(
                                    user_id = AppPreferences.getUserId(),
                                    blocker_id = profile_Content.value?.user_id ?: 0,
                                    //profile_Content.value?.user_id ?: 0,
                                    status = blockStatus.value
                                    //if (profile_Content.value?.is_blocked == 0) "1" else "0"
                                )
                                { apiResultHandling ->
                                    when (apiResultHandling) {
                                        is API_Result_Handling.Error -> {
                                            //errror
                                            //constants.Profile_ViewModel.change_Update_profile(false)
                                        }

                                        is API_Result_Handling.Deactivated -> {
                                            // resultCallback(5)
                                        }

                                        is API_Result_Handling.NoData -> {
                                            // no data
                                        }

                                        is API_Result_Handling.Loading -> {
                                            //loading
                                            // constants.Profile_ViewModel.change_Update_profile(true)
                                        }

                                        is API_Result_Handling.Success -> {
                                            if (profile_Content.value?.is_blocked == 0) {
                                                constants.Profile_ViewModel.updateBlockedStatus_Selected_Profile(
                                                    1
                                                )
                                            } else {
                                                constants.Profile_ViewModel.updateBlockedStatus_Selected_Profile(
                                                    0
                                                )
                                            }
                                            block_PopUp = false
                                            //constants.Profile_ViewModel.enable_Edit_Profile()
                                            //constants.Profile_ViewModel.change_Update_profile(false)
                                            //success
                                        }
                                    }
                                }*/
                                    } else {
                                        toast(constants.activity.getString(R.string.no_Internet))
                                        // GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
                                    }
                                }
                                }
                        }
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xffE54C3C))
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

//            Spacer(modifier = Modifier.padding(8.dp))
            constants.spacer(8)
        }
    }

    BackHandler {
        println("WERTTY -- ${report_BS.value} -- ${cmt_btm_Sheet.value} -- ${reelsBTMSheetState.value} -- ${send_Eq_State.value}")

        when {
            report_BS.value -> {
                report_BS.value = false
            }
            cmt_btm_Sheet.value -> {
                constants.Common_H_ViewModel.dismiss_Cmt_btm_Sheet()
            }
            reelsBTMSheetState.value -> {
                constants.Common_H_ViewModel.toggleReelsBTMSheet(false)
            }
            send_Eq_State.value -> {
                constants.Reels_ViewModel.dismiss_Send_Eq_Btm_Sheet()
            }
            else  -> {

                navController.navigateUp()

            }
        }
    }

}




@Composable
fun Reels_Options_Search_Flow(
    modifier: Modifier,
    videos: List<Get_Reels_Data>,
    page: Int,
    navController: NavHostController,
    pagerState: PagerState,
    player: ExoPlayer?,
    duration: MutableState<Long>,
    position: MutableState<Long>,
    isLike_Loading: MutableState<Boolean>,
    isSave_Loading: MutableState<Boolean>,
    viewModel: Common_H_ViewModel,
) {


    Box (
        modifier = modifier
    ){


        println("STSTUS -- ${videos[page].post_property.status} -- ${constants.expired}")

        if (videos[page].post_property.status == "1") {
            Image(
                painter = painterResource(R.drawable.postexpirybadge), "",
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 16.dp)
                    .zIndex(3f)
                    .align(Alignment.BottomStart)
            )
        }
        else if (videos[page].post_property.is_sold == 1){
            Image(
                painter = painterResource(R.drawable.rentedoutbadgerento), "",
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 16.dp)
                    .zIndex(3f)
                    .align(Alignment.BottomStart)
            )
        }
        else  if (videos[page].user_id == AppPreferences.getUserId()) {
            Image(
                painter = painterResource(R.drawable.ownpropertybadgerento), "",
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 16.dp)
                    .zIndex(3f)
                    .align(Alignment.BottomStart)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
                //.padding(bottom = if (showBABars.value) 100.dp else 0.dp),
            ,verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        )
        {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .wrapContentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                constants.Reels_ViewModel.reels_Options_List.forEachIndexed { index, icon ->

                    val currentReel = videos[page]
                    val isLiked = if (currentReel.is_liked == 1) true else false
                    val isSaved = if(currentReel.is_saved == 1) true else false



                    // Optional animation only for like button
                    val scale = remember { Animatable(1f) }

                    LaunchedEffect(icon.isLiked) {
                        if (isLiked && index == 0) {
                            scale.animateTo(1.3f, tween(200))
                            scale.animateTo(1f, tween(200))
                        }
                    }



                    val show_Icon =  when(index){
                        0 -> {
                            if (isLiked) icon.enabled_Icon else icon.icon
                        }
                        2 -> {
                            if (isSaved) icon.enabled_Icon else icon.icon
                        }
                        else -> { icon.icon }
                    }

//                    if ((index == 0 && isLike_Loading.value) || (index == 2 && isSave_Loading.value)){
//                        CircularProgressIndicator(modifier = Modifier.size(25.dp), color = newBlue)
//                    }
//                    else {
                        AsyncImage(
                            model = show_Icon,
                            contentDescription = "",
                            modifier = Modifier
                                .graphicsLayer {
                                    scaleX = scale.value
                                    scaleY = scale.value
                                }
                                .size(if (forTab()) 40.dp else 36.dp)
                                .noRippleClickable{
                                    println("CLIKING COMMENT index ")
                                    when (index) {
                                        0 -> {

                                            constants.Reels_ViewModel.toggleLike_Reelsrento(
                                                currentReel.user_post_id
                                            )
                                            constants.API_Vm.like_Dislike(
                                                user_id = AppPreferences.getUserId(),
                                                user_post_id = currentReel.user_post_id,
                                                status = if (isLiked) 2 else 1,
                                            )
                                            { apiResultHandling ->
                                                when (apiResultHandling) {
                                                    is API_Result_Handling.Error -> {
                                                        isLike_Loading.value = false
                                                        constants.Reels_ViewModel.decreaseLikeCount_Reels(
                                                                currentReel.user_post_id
                                                            )
                                                        toast("Something Went Wrong")
                                                        //errror
                                                    }
                                                    is API_Result_Handling.Deactivated -> {
                                                        //resultCallback(5)
                                                    }

                                                    is API_Result_Handling.NoData -> {
                                                        // no data
                                                    }

                                                    is API_Result_Handling.Loading -> {
                                                        isLike_Loading.value = true
                                                        //loading
                                                    }

                                                    is API_Result_Handling.Success -> {
                                                        isLike_Loading.value = false

//                                                        if (isLiked) {
//                                                            constants.Reels_ViewModel.decreaseLikeCount_Reels(
//                                                                currentReel.user_post_id
//                                                            )
//                                                        } else {
//                                                            constants.Reels_ViewModel.increaseLikeCount_Reels(
//                                                                currentReel.user_post_id
//                                                            )
//                                                        }
//                                                        constants.Reels_ViewModel.toggleLike_Reels(
//                                                            currentReel.user_post_id
//                                                        )

                                                        //success
                                                    }
                                                }
                                            }
                                        }

                                        1 -> {
                                            println("CLIKING COMMENT in ")
                                            constants.API_Vm.isLoading_MComments = false
                                            constants.API_Vm.totalPages_MComments = 1
                                            constants.Reels_ViewModel.clear_MCommentList()
                                            constants.Reels_ViewModel.clear_Reply_Map()
                                            viewModel.enable_Cmt_btm_Sheet()
                                        }

                                        2 -> {
                                            constants.API_Vm.put_save_UnSave_Property(
                                                user_id = AppPreferences.getUserId(),
                                                user_post_id = currentReel.user_post_id,
                                                status = if (isSaved) 2 else 1,
                                            )
                                            { apiResultHandling ->
                                                when (apiResultHandling) {
                                                    is API_Result_Handling.Error -> {
                                                        isSave_Loading.value = false
                                                        toast("Something Went Wrong")
                                                        //errror
                                                    }
                                                    is API_Result_Handling.Deactivated -> {
                                                       // resultCallback(5)
                                                    }

                                                    is API_Result_Handling.NoData -> {
                                                        // no data
                                                    }

                                                    is API_Result_Handling.Loading -> {
                                                        isSave_Loading.value = true
                                                        //loading
                                                    }

                                                    is API_Result_Handling.Success -> {
                                                        isSave_Loading.value = false

                                                        constants.Reels_ViewModel.toggleSave_Reels(
                                                            currentReel.user_post_id
                                                        )
                                                        //success
                                                    }
                                                }
                                            }
                                        }

                                        3 ->{
                                            AppPreferences.save_Post_Id(videos[page].user_post_id)
                                            viewModel.toggleReelsBTMSheet(true)
                                        }
                                    }

                                }
                        )
//                    }


                    //if (icon.counts.isNotEmpty()) {
                    if (index == 0){
                        Text(
                            text = currentReel.total_likes.toString() ,
                            color = newWhite,
                            fontSize = constants.textUnit(12), fontFamily = constants.fontFamily(2)
                        )
                    }
                    else if (index == 1){
                        Text(
                            text = currentReel.total_comments.toString(),
                            color = newWhite,
                            fontSize = constants.textUnit(12), fontFamily = constants.fontFamily(2)
                        )
                    }

                    // }

                    Spacer(modifier = Modifier.height(16.dp))
                }

            }



        }
    }
}


@Composable
fun Reels_Options_Search_Flow_Static(
    modifier: Modifier,
    videos: List<Get_Reels_Data>,
    page: Int,
    navController: NavHostController,
    pagerState: PagerState,
    player: ExoPlayer?,
    duration: MutableState<Long>,
    position: MutableState<Long>,
    isLike_Loading: MutableState<Boolean>,
    isSave_Loading: MutableState<Boolean>,
    viewModel: Common_H_ViewModel,
) {


    Box (
        modifier = modifier
    ){


        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                //.padding(bottom = if (showBABars.value) 100.dp else 0.dp),
            ,verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        )
        {

            Spacer(modifier = Modifier.height(16.dp))



            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                       Color.White
                    )
            )
            {
                var vd_Loader by remember { mutableStateOf(false) }

                if (videos[page].user_id == AppPreferences.getUserId()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 6.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.Start
                    )
                    {
//                        if (videos[page].post_property.is_sold == 1) {
//                            Image(
//                                painterResource(R.drawable.soldoutidentifier), "",
//                                modifier = Modifier.padding(start = 16.dp)
//                            )
//                        }
                        ListItem(
                            leadingContent = {
                                Box(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .clip(CircleShape)
                                        .background(Color.White)
                                        .noRippleClickable{
                                            viewModel.toggleshowTABars(false)
                                            ClickHelper.getInstance().clickOnce {
                                                if (ClickGuard.canClick()) {
                                                    logD("clicks reels profile")
                                                    viewModel.toggleshowTABars(false)
                                                    constants.Profile_ViewModel.add_Selected_User_Name(
                                                        videos[page].username ?: "Username "
                                                    )

                                                    //new flowwewwwwwww
                                                    constants.Profile_ViewModel.add_BF_Handler(
                                                        Profile_Handle_Back(
                                                            current_UsedId = AppPreferences.getUserId(),
                                                            other_UserId = videos[page]?.user_id ?: 0,
                                                            ff_User_Name = videos[page]?.username ?: "",
                                                            ff_Fw_Count = 999,
                                                            ff_Fg_Count = 999,
                                                            // is_Search_Enabled = is_Search_Enabled.value,
                                                            // search_Text = search_Text.value
                                                        )
                                                    )

                                                    /// println("ITEM PROFILE STRUCTURE __ ${is_Search_Enabled.value} -- ${constants.Profile_ViewModel.profile_BF_Handler.value}")

                                                    println("GIVEN OTHER USER ID -- ${constants.Profile_ViewModel.get_Other_User_Id()}")

                                                    constants.Profile_ViewModel.addProfile(
                                                        videos[page]?.user_id ?: 0
                                                    )
                                                    constants.Profile_ViewModel.add_Selected_Profile_Id(
                                                        id = videos[page].user_id ?: 0
                                                    )

                                                    // if (view_Details_Data.value?.user_id == AppPreferences.)
                                                    viewModel.toggleshowBABars(false)
                                                    navController.navigate(VideosScreenFlow.Other_Profile_Structure.route)
                                                }
                                            }
                                        }
                                    , contentAlignment = Alignment.Center
                                )
                                {
//                                        Icon(
//                                            painter = painterResource(R.drawable.person),
//                                            contentDescription = null,
//                                            modifier = Modifier.padding(8.dp),
//                                            tint = Color.White
//                                        )
                                    SubcomposeAsyncImage(
                                        model = videos[page]?.profile_image ?: "",
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                        ,
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
                                                //.padding(8.dp)
                                                , contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = videos[page]?.username.takeIf { it?.isNotEmpty() == true }
                                                        ?.take(1)?.uppercase() ?: ""
                                                )
                                            }
                                        } else {
                                            SubcomposeAsyncImageContent()
                                        }
                                    }
                                }
                            }
                            , headlineContent = {
                                Row(
                                    modifier = Modifier
                                        .noRippleClickable{
                                            viewModel.toggleshowTABars(false)
                                            ClickHelper.getInstance().clickOnce {
                                                if (ClickGuard.canClick()) {
                                                    logD("clicks reels profile")
                                                    viewModel.toggleshowTABars(false)
                                                    constants.Profile_ViewModel.add_Selected_User_Name(
                                                        videos[page].username ?: "UserName"
                                                    )

                                                    //new flowwewwwwwww
                                                    constants.Profile_ViewModel.add_BF_Handler(
                                                        Profile_Handle_Back(
                                                            current_UsedId = AppPreferences.getUserId(),
                                                            other_UserId = videos[page]?.user_id ?: 0,
                                                            ff_User_Name = videos[page]?.username ?: "",
                                                            ff_Fw_Count = 999,
                                                            ff_Fg_Count = 999,
                                                            // is_Search_Enabled = is_Search_Enabled.value,
                                                            // search_Text = search_Text.value
                                                        )
                                                    )

                                                    /// println("ITEM PROFILE STRUCTURE __ ${is_Search_Enabled.value} -- ${constants.Profile_ViewModel.profile_BF_Handler.value}")

                                                    println("GIVEN OTHER USER ID -- ${constants.Profile_ViewModel.get_Other_User_Id()}")

                                                    constants.Profile_ViewModel.addProfile(
                                                        videos[page]?.user_id ?: 0
                                                    )
                                                    constants.Profile_ViewModel.add_Selected_Profile_Id(
                                                        id = videos[page].user_id ?: 0
                                                    )

                                                    // if (view_Details_Data.value?.user_id == AppPreferences.)
                                                    viewModel.toggleshowBABars(false)
                                                    navController.navigate(VideosScreenFlow.Other_Profile_Structure.route)
                                                }
                                            }
                                        }
                                    , verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                )
                                {
                                    //Spacer(modifier = Modifier.padding(8.dp))

                                    Text(
                                        videos[page].username,
                                        color = newBlack,
                                        fontSize = constants.textUnit(16),
                                        fontFamily = constants.fontFamily(1)
                                    )

                                }
                            }
                            , supportingContent = {
                                // Spacer(modifier = Modifier.padding(4.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    // if (currentReel.user_id == AppPreferences.getUserId()){
                                    var text = if (videos[page].post_property.user_type == "0")"Owner" else "Broker"
                                    Text(
                                        "$text \u2022 ",
                                        color = Color(0xff575757),
                                        fontSize = constants.textUnit(12),
                                        fontFamily = constants.fontFamily(1),
                                        // overflow = TextOverflow.Ellipsis
                                    )
                                    //  }

                                    Text(
                                        "Posted ${getTimeAgo(videos[page].post_property?.created_at ?:"")}",
                                        color = Color(0xff575757),
                                        fontSize = constants.textUnit(12),
                                        fontFamily = constants.fontFamily(1),
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                            , modifier = Modifier.background(Color.Transparent),
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                        )

                        ListItem(
                            overlineContent = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Text(
                                        videos[page].post_property.landCategoryText ?: "",
                                        color = newBlack,
                                        fontSize = constants.textUnit(18),
                                        fontFamily = constants.fontFamily(0)
                                    )
                                    Text(
                                        " \u2022 ${videos[page].post_property.is_this_property_for_rent_or_lease}",
                                        color = newBlack,
                                        fontSize = constants.textUnit(14),
                                        fontFamily = constants.fontFamily(1)
                                    )
                                }

                            }
                            , headlineContent = {
                                Column() {
                                    Row(
                                        modifier = Modifier,
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.locationenquiry), "",
                                            modifier = Modifier.size(16.dp)
                                        )

                                        constants.spacer(4)

                                        Text(
                                            "${videos[page].post_property.city}, ${videos[page].post_property.state}",
                                            color = Color(0xff7E7E7E),
                                            fontSize = constants.textUnit(14),
                                            fontFamily = constants.fontFamily(1)
                                        )
                                    }
                                    constants.spacer(4)
                                }
                            }
                            , supportingContent = {

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Card(
                                        modifier = Modifier
                                            .height(64.dp)
                                            .width(138.dp),
                                        shape = RoundedCornerShape(6.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color(0xffCECECE).copy(.3f)
                                        )
                                    )
                                    {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(horizontal = 6.dp),
                                            verticalArrangement = Arrangement.SpaceEvenly,
                                            horizontalAlignment = Alignment.Start
                                        ) {


                                            BasicText(
                                                text = "\u20B9 ${videos[page].post_property.rent?.ifEmpty { videos[page].post_property.lease_amount }}",
                                                color = { newBlack },
                                                style = TextStyle(fontFamily = constants.fontFamily(0)),
                                                autoSize = TextAutoSize.StepBased(
                                                    minFontSize = 6.sp,
                                                    constants.textUnit(16),
                                                    stepSize = 2.sp
                                                )
                                            )


                                            Text(
                                                "\u20B9 Amount",
                                                color = Color(0xff323232),
                                                fontSize = constants.textUnit(14),
                                                fontFamily = constants.fontFamily(3)
                                            )
                                        }
                                    }

                                    Card(
                                        modifier = Modifier
                                            .height(64.dp)
                                            .width(138.dp)
                                        // .padding(horizontal = 8.dp)
                                        ,
                                        shape = RoundedCornerShape(6.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color(0xffCECECE).copy(.3f)
                                        )
                                    )
                                    {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(horizontal = 6.dp),
                                            verticalArrangement = Arrangement.SpaceEvenly,
                                            horizontalAlignment = Alignment.Start
                                        ) {
                                            Text(
                                                "${videos[page].post_property.carpet_area} ${videos[page].post_property.carpet_area_unit}",
                                                color = newBlack,
                                                fontSize = constants.textUnit(16),
                                                fontFamily = constants.fontFamily(0)
                                            )

                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start
                                            ) {
                                                Image(
                                                    painter = painterResource(R.drawable.reelscarpetareaicon),
                                                    "",
                                                    modifier = Modifier.size(12.dp)
                                                )

                                                constants.spacer(2)

                                                Text(
                                                    "Carpet Area",
                                                    color = Color(0xff323232),
                                                    fontSize = constants.textUnit(14),
                                                    fontFamily = constants.fontFamily(3)
                                                )
                                            }
                                        }
                                    }

                                    Card(
                                        modifier = Modifier
                                            .height(64.dp)
                                            .width(38.dp)
                                            .noRippleClickable {
                                                ClickHelper.getInstance().clickOnce {

                                                    constants.Reels_ViewModel.clear_view_pro_Details()
                                                    vd_Loader = true
                                                    CoroutineScope(Dispatchers.IO).launch {
                                                        delay(1000)
                                                        vd_Loader = false
                                                    }
                                                    player?.pause()
                                                    constants.Reels_ViewModel.add_View_Property_Details(
                                                        videos[pagerState.currentPage]
                                                    )

                                                    constants.Profile_ViewModel.set_From_Repost(-1)

                                                    val user_type =
                                                        if (videos[pagerState.currentPage].user_id == AppPreferences.getUserId()) 0 else 1
                                                    constants.Profile_ViewModel.set_Profile_Mode(
                                                        user_type
                                                    )
                                                    println("VIEW PROPERTY DATA -- ${constants.Reels_ViewModel.get_View_Property_Details()}")
                                                    var mode =
                                                        if (videos[pagerState.currentPage].post_property.video?.isEmpty() == true) 1 else 0
                                                    constants.Common_H_ViewModel.view_Property_Details_Mode.value =
                                                        mode

//                                                    if (constants.PostProperty_ViewModel.viewDetailsFlow.value == ViewDetailsFlow.NONE) {
                                                        constants.PostProperty_ViewModel.setViewDetailsFlow(


                                                            when {
                                                                constants.PostProperty_ViewModel.viewDetailsFlow.value == ViewDetailsFlow.RENTOUT -> ViewDetailsFlow.RENTOUT
                                                                constants.PostProperty_ViewModel.viewDetailsFlow.value == ViewDetailsFlow.OTHERS -> ViewDetailsFlow.OTHERS
                                                                constants.PostProperty_ViewModel.viewDetailsFlow.value == ViewDetailsFlow.EXPIRY -> ViewDetailsFlow.EXPIRY
                                                                videos[pagerState.currentPage].user_id == AppPreferences.getUserId() -> ViewDetailsFlow.OWN
                                                                else -> ViewDetailsFlow.OTHERS
                                                            }
                                                        )
//                                                    }

                                                    navController.navigate(VideosScreenFlow.ViewPropertyStructure.route)
                                                }
                                            }
                                            .border(1.dp, Color(0xffCECECE), RoundedCornerShape(4.dp)),
                                        shape = RoundedCornerShape(4.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color(0xffEBEBEB)
                                        )
                                    )
                                    {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize(), contentAlignment = Alignment.Center
                                        ) {

                                            if (vd_Loader) {
                                                CircularProgressIndicator(
                                                    modifier = Modifier.size(24.dp),
                                                    color = newBlue
                                                )
                                            } else {
                                                Image(
                                                    painter = painterResource(R.drawable.right_arrow),
                                                    "",
                                                    colorFilter = ColorFilter.tint(
                                                        Color(0xff575757)
                                                    )
                                                )
                                            }
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
                else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 6.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.Start
                    )
                    {
                        if (videos[page].post_property.is_sold == 1) {
                            Image(
                                painterResource(R.drawable.soldoutidentifier), "",
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                        ListItem(
                            leadingContent = {
                                Box(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .clip(CircleShape)
                                        .background(Color.White)
                                        .noRippleClickable{
                                            viewModel.toggleshowTABars(false)
                                            ClickHelper.getInstance().clickOnce {
                                                if (ClickGuard.canClick()) {
                                                    logD("clicks reels profile")
                                                    viewModel.toggleshowTABars(false)
                                                    constants.Profile_ViewModel.add_Selected_User_Name(
                                                        videos[page].username ?: "Username "
                                                    )

                                                    //new flowwewwwwwww
                                                    constants.Profile_ViewModel.add_BF_Handler(
                                                        Profile_Handle_Back(
                                                            current_UsedId = AppPreferences.getUserId(),
                                                            other_UserId = videos[page]?.user_id ?: 0,
                                                            ff_User_Name = videos[page]?.username ?: "",
                                                            ff_Fw_Count = 999,
                                                            ff_Fg_Count = 999,
                                                            // is_Search_Enabled = is_Search_Enabled.value,
                                                            // search_Text = search_Text.value
                                                        )
                                                    )

                                                    /// println("ITEM PROFILE STRUCTURE __ ${is_Search_Enabled.value} -- ${constants.Profile_ViewModel.profile_BF_Handler.value}")

                                                    println("GIVEN OTHER USER ID -- ${constants.Profile_ViewModel.get_Other_User_Id()}")

                                                    constants.Profile_ViewModel.addProfile(
                                                        videos[page]?.user_id ?: 0
                                                    )
                                                    constants.Profile_ViewModel.add_Selected_Profile_Id(
                                                        id = videos[page].user_id ?: 0
                                                    )

                                                    // if (view_Details_Data.value?.user_id == AppPreferences.)
                                                    viewModel.toggleshowBABars(false)
                                                    navController.navigate(VideosScreenFlow.Other_Profile_Structure.route)
                                                }
                                            }
                                        }
                                    , contentAlignment = Alignment.Center
                                )
                                {
                                    SubcomposeAsyncImage(
                                        model = videos[page]?.profile_image ?: "",
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                        ,
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
                                                //.padding(8.dp)
                                                , contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = videos[page]?.username.takeIf { it?.isNotEmpty() == true }
                                                        ?.take(1)?.uppercase() ?: ""
                                                )
                                            }
                                        } else {
                                            SubcomposeAsyncImageContent()
                                        }
                                    }
                                }
                            }
                            , headlineContent = {
                                Row(
                                    modifier = Modifier
                                        .noRippleClickable{
                                            viewModel.toggleshowTABars(false)
                                            ClickHelper.getInstance().clickOnce {
                                                if (ClickGuard.canClick()) {
                                                    logD("clicks reels profile")
                                                    viewModel.toggleshowTABars(false)
                                                    constants.Profile_ViewModel.add_Selected_User_Name(
                                                        videos[page].username ?: "UserName"
                                                    )

                                                    //new flowwewwwwwww
                                                    constants.Profile_ViewModel.add_BF_Handler(
                                                        Profile_Handle_Back(
                                                            current_UsedId = AppPreferences.getUserId(),
                                                            other_UserId = videos[page]?.user_id ?: 0,
                                                            ff_User_Name = videos[page]?.username ?: "",
                                                            ff_Fw_Count = 999,
                                                            ff_Fg_Count = 999,
                                                            // is_Search_Enabled = is_Search_Enabled.value,
                                                            // search_Text = search_Text.value
                                                        )
                                                    )

                                                    /// println("ITEM PROFILE STRUCTURE __ ${is_Search_Enabled.value} -- ${constants.Profile_ViewModel.profile_BF_Handler.value}")

                                                    println("GIVEN OTHER USER ID -- ${constants.Profile_ViewModel.get_Other_User_Id()}")

                                                    constants.Profile_ViewModel.addProfile(
                                                        videos[page]?.user_id ?: 0
                                                    )
                                                    constants.Profile_ViewModel.add_Selected_Profile_Id(
                                                        id = videos[page].user_id ?: 0
                                                    )

                                                    // if (view_Details_Data.value?.user_id == AppPreferences.)
                                                    viewModel.toggleshowBABars(false)
                                                    navController.navigate(VideosScreenFlow.Other_Profile_Structure.route)
                                                }
                                            }
                                        }
                                    , verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                )
                                {


                                    //Spacer(modifier = Modifier.padding(8.dp))

                                    Text(
                                        videos[page].name,
                                        color = newBlack,
                                        fontSize = constants.textUnit(16),
                                        fontFamily = constants.fontFamily(1)
                                    )

                                }
                            }
                            , supportingContent = {
                                // Spacer(modifier = Modifier.padding(4.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    // if (currentReel.user_id == AppPreferences.getUserId()){

                                    var text = if (videos[page].post_property.user_type == "0") "Owner" else "Broker"

                                    Text(
                                        "$text \u2022 ",
                                        color = Color(0xff575757),
                                        fontSize = constants.textUnit(12),
                                        fontFamily = constants.fontFamily(1),
                                        // overflow = TextOverflow.Ellipsis
                                    )
                                    //  }

                                    Text(
                                        "Posted ${getTimeAgo(videos[page].post_property?.created_at ?:"")}",
                                        color = Color(0xff575757),
                                        fontSize = constants.textUnit(12),
                                        fontFamily = constants.fontFamily(1),
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                            , trailingContent = {
                                if (videos[page].user_id != AppPreferences.getUserId()) {
                                    Box(
                                        modifier = Modifier
                                            .width(108.dp)
                                            .height(32.dp)
                                            .clip(RoundedCornerShape(4.dp))
                                            .noRippleClickable {
                                                player?.pause()
                                                constants.Reels_ViewModel.enable_Send_Eq_Btm_Sheet()
                                            }
                                            .border(
                                                1.dp, Brush.linearGradient(
                                                    newPurpleGradientBorder
                                                ), RoundedCornerShape(4.dp)
                                            )
                                            .background(Brush.verticalGradient(newPurpleGradient)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            "Send Enquiry",
                                            color = Color.White,
                                            fontSize = constants.textUnit(14),
                                            fontFamily = constants.fontFamily(0)
                                        )
                                    }
                                }
                            }
                            , modifier = Modifier.background(Color.Transparent),
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                        )

                        ListItem(
                            overlineContent = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Text(
                                        videos[page].post_property.landCategoryText ?: "",
                                        color = newBlack,
                                        fontSize = constants.textUnit(18),
                                        fontFamily = constants.fontFamily(0)
                                    )


                                    Text(
                                        " \u2022 ${videos[page].post_property.is_this_property_for_rent_or_lease}",
                                        color = newBlack,
                                        fontSize = constants.textUnit(14),
                                        fontFamily = constants.fontFamily(1)
                                    )
                                }

                            }
                            , headlineContent = {
                                Column() {
                                    Row(
                                        modifier = Modifier,
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.locationenquiry), "",
                                            modifier = Modifier.size(16.dp)
                                        )

                                        constants.spacer(4)

                                        Text(
                                            "${videos[page].post_property.city}, ${videos[page].post_property.state}",
                                            color = Color(0xff7E7E7E),
                                            fontSize = constants.textUnit(14),
                                            fontFamily = constants.fontFamily(1)
                                        )
                                    }
                                    constants.spacer(4)
                                }
                            }
                            , supportingContent = {

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                    , verticalAlignment = Alignment.CenterVertically
                                    , horizontalArrangement = Arrangement.SpaceBetween
                                )
                                {
                                    Card(
                                        modifier = Modifier
                                            .height(if (forTab())84.dp else 64.dp)
                                            .weight(4.5f)
                                        // .width(138.dp)
                                        , shape = RoundedCornerShape(6.dp)
                                        , colors = CardDefaults.cardColors(
                                            containerColor = Color(0xffCECECE).copy(.2f)
                                        )
                                        //, elevation = CardDefaults.cardElevation(8.dp)
                                    )
                                    {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(horizontal = 6.dp)
                                            ,verticalArrangement = Arrangement.SpaceEvenly
                                            , horizontalAlignment = Alignment.Start
                                        ) {


                                            BasicText(
                                                text = "\u20B9 ${videos[page].post_property.rent?.ifEmpty { videos[page].post_property.lease_amount }  }",
                                                color =  { newBlack },
                                                style = TextStyle(fontFamily = constants.fontFamily(0)),
                                                autoSize = TextAutoSize.StepBased(minFontSize = 6.sp, constants.textUnit(16), stepSize = 2.sp)
                                            )


                                            Text(
                                                "\u20B9 Amount",
                                                color = Color(0xff323232),
                                                fontSize = constants.textUnit(14),
                                                fontFamily = constants.fontFamily(3)
                                            )
                                        }
                                    }

                                    constants.spacer(4)

                                    Card(
                                        modifier = Modifier
                                            .height(if (forTab())84.dp else 64.dp)
                                            .weight(4.5f)
                                        //.width(138.dp)
                                        , shape = RoundedCornerShape(6.dp)
//                                    , elevation = CardElevation()
                                        //CardDefaults.cardElevation(12.dp)
                                        , colors = CardDefaults.cardColors(
                                            containerColor = Color(0xffCECECE).copy(.2f)
                                        )

                                    )
                                    {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(horizontal = 6.dp)
                                            , verticalArrangement = Arrangement.SpaceEvenly
                                            , horizontalAlignment = Alignment.Start
                                        ) {
                                            Text(
                                                "${videos[page].post_property.carpet_area} ${videos[page].post_property.carpet_area_unit}",
//                                            "200 sq.ft",
                                                color = newBlack,
                                                fontSize = constants.textUnit(16),
                                                fontFamily = constants.fontFamily(0)
                                            )

                                            Row(
                                                verticalAlignment = Alignment.CenterVertically
                                                , horizontalArrangement = Arrangement.Start
                                            ) {
                                                Image(painter = painterResource(R.drawable.reelscarpetareaicon) , "",
                                                    modifier = Modifier.size(12.dp))

                                                constants.spacer(2)

                                                Text(
                                                    "Carpet Area",
                                                    color = Color(0xff323232),
                                                    fontSize = constants.textUnit(14),
                                                    fontFamily = constants.fontFamily(3)
                                                )
                                            }
                                        }
                                    }

                                    constants.spacer(4)

                                    Card(
                                        modifier = Modifier
                                            .height(if (forTab())84.dp else 64.dp)
                                            .weight(1f)
                                            //.width(38.dp)
                                            .noRippleClickable {
                                                ClickHelper.getInstance().clickOnce {

                                                    constants.Reels_ViewModel.clear_view_pro_Details()
                                                    vd_Loader = true
                                                    CoroutineScope(Dispatchers.IO).launch {
                                                        delay(1000)
                                                        vd_Loader = false
                                                    }
                                                    player?.pause()
                                                    constants.Reels_ViewModel.add_View_Property_Details(
                                                        videos[pagerState.currentPage]
                                                    )

                                                    val user_type =
                                                        if (videos[pagerState.currentPage].user_id == AppPreferences.getUserId()) 0 else 1
                                                    constants.Profile_ViewModel.set_Profile_Mode(
                                                        user_type
                                                    )
                                                    println("VIEW PROPERTY DATA -- ${constants.Reels_ViewModel.get_View_Property_Details()}")
                                                    var mode =
                                                        if (videos[pagerState.currentPage].post_property.video?.isEmpty() == true) 1 else 0
                                                    constants.Common_H_ViewModel.view_Property_Details_Mode.value =
                                                        mode


                                                    constants.PostProperty_ViewModel.setViewDetailsFlow(
                                                        when {
                                                            constants.PostProperty_ViewModel.viewDetailsFlow.value == ViewDetailsFlow.RENTOUT -> ViewDetailsFlow.RENTOUT
                                                            videos[pagerState.currentPage].user_id == AppPreferences.getUserId() -> ViewDetailsFlow.OWN
                                                            else -> ViewDetailsFlow.OTHERS
                                                        }
                                                    )
                                                    navController.navigate(VideosScreenFlow.ViewPropertyStructure.route)
                                                }
                                            }
                                            .border(1.dp , Color(0xffCECECE) , RoundedCornerShape(4.dp))
                                        , shape = RoundedCornerShape(4.dp)
                                        , colors = CardDefaults.cardColors(
                                            containerColor = Color(0xffEBEBEB)
                                        )
                                    )
                                    {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                            , contentAlignment = Alignment.Center
                                        ) {

                                            if (vd_Loader) {
                                                CircularProgressIndicator(
                                                    modifier = Modifier.size(24.dp),
                                                    color = newBlue
                                                )
                                            } else {
                                                Image(
                                                    painter = painterResource(R.drawable.right_arrow), "",
                                                    colorFilter = ColorFilter.tint(Color(0xff575757)
                                                    )
                                                )
                                            }
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
            }


            Spacer(modifier = Modifier.padding(2.dp))


        }
    }
}



@Composable
fun SingleVideoPlayerEnquiry(
    video: PostUser, // single video
    modifier: Modifier = Modifier,
    navController: NavHostController, // optional
    reels_Show: MutableState<Boolean>,
    viewModel: Common_H_ViewModel
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


// ✅ ADD THIS: Observe the updated list from ViewModel
    val myLeadsData by constants.Enquiry_ViewModel.my_Leads.collectAsState()
    val selfEnquiryData by constants.Enquiry_ViewModel.self_Enquiry.collectAsState()

    // ✅ Find current video from either list
    // ✅ Find current video from either list
//    val currentVideo = remember(myLeadsData, selfEnquiryData, video) {
//        when {
//            myLeadsData.isNotEmpty() -> {
//                myLeadsData
//                    .firstOrNull { it.post_user.user_post_id == video.user_post_id }?.post_user
//            }
//
//            else -> {
//                selfEnquiryData
//                    .firstOrNull { it?.post_user?.user_post_id == video.user_post_id }?.post_user
//            }
//        }
//    }

    // Debug recomposition
    LaunchedEffect(video) {
//        println("🔄 Recompose -> id:${currentVideo.user_post_id} liked:${currentVideo.is_liked} saved:${currentVideo.is_saved}")
    }

    val reelsBTMSheetState = constants.Common_H_ViewModel.reelsBtm_sheet.collectAsState()
    val reelsBTMSOptions = constants.Reels_ViewModel.reelsBTMSOptions.collectAsState()


    // Comment bottom sheet state
    val cmt_btm_Sheet = constants.Common_H_ViewModel.comment_btm_Sheet.collectAsState()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val send_Eq_State = constants.Reels_ViewModel.send_Enquiry_Btm_Sheet.collectAsState()


    var mark_as_Sold = remember { mutableStateOf(false) }
    var delete_Post by remember { mutableStateOf(false) }


    var report_BS = remember { mutableStateOf(false) }
    val report_Options = constants.Profile_ViewModel.profile_Report_Options.collectAsState()

    val report_success = constants.Profile_ViewModel.report_Submit_Success.collectAsState()

    val repost_Btm = remember { mutableStateOf(false) }


    println("SINGLE VIDEO PLAYER VIDEO URL -- ${video.post_property.video}")
    // Player setup
    val playerManager = remember { VideoPlayerManager(context) }
    val hasVideo = !video.post_property.video.isNullOrEmpty()  // ✅ Fixed: added NOT operator

    val player = if (hasVideo) {
        remember(video.user_post_id) {
            playerManager.getOrCreatePlayer2(0, video.user_post_id.toString(), video.post_property.video.firstOrNull()?.url ?: "").apply {
                playWhenReady = true  // ✅ This starts playback
                prepare()             // ✅ Prepare the media
            }
        }
    } else null

    // Video size tracking
    var videoWidth by remember { mutableStateOf(0) }
    var videoHeight by remember { mutableStateOf(0) }

    DisposableEffect(Unit) {
        onDispose {
            playerManager.releaseAll()
        }
    }


    DisposableEffect(player) {
        val listener = object : Player.Listener {
            override fun onVideoSizeChanged(videoSize: VideoSize) {
                videoWidth = videoSize.width
                videoHeight = videoSize.height
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_ENDED) {
                    player?.seekTo(0)
                    player?.playWhenReady = true
                }
            }
        }
        player?.addListener(listener)
        onDispose {
            player?.removeListener(listener)
            player?.pause()
            player?.release()
        }
    }

    // Progress tracking
    val position = remember { mutableStateOf(0L) }
    val duration = remember { mutableStateOf(1L) }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> player?.pause()
                Lifecycle.Event.ON_RESUME -> {
                    player?.playWhenReady = true  // ✅ Ensure playback resumes
                    player?.play()
                }
                Lifecycle.Event.ON_DESTROY -> player?.release()
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            player?.pause()
            player?.release()
        }
    }

    // Loading states
    val isLikeLoading = remember { mutableStateOf(false) }
    val isSaveLoading = remember { mutableStateOf(false) }
    val showBABars = remember { mutableStateOf(true) }
    val viewdetailFlow = constants.PostProperty_ViewModel.viewDetailsFlow.collectAsState()

    // UI
//    Box(
//        modifier = modifier
//            .fillMaxSize()
//            .background(Color.Black)
//    ) {
    // Video Surface

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(6f)
                .background(Color.Black)
        )
        {
            println("PLAYER COMES -- ${player}")
            if (hasVideo) {
                if (player != null) {
                    PlayerSurface(
                        player = player,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth()
                            .then(
                                if (videoWidth > 0 && videoHeight > 0) {
                                    Modifier.size(
                                        with(density) { videoWidth.toDp() },
                                        with(density) { videoHeight.toDp() }
                                    )
                                } else {
                                    Modifier.aspectRatio(9f / 16f)
                                }
                            )
                            .pointerInput(video.user_post_id) {
                                detectTapGestures(
                                    onTap = { if (player.isPlaying) player.pause() else player.play() },
                                    onDoubleTap = {
//                                        scope.launch {
//                                          //  snackbarHostState.showSnackbar("❤️ Double tapped (Like)")
//                                        }
                                    }
                                )
                            }
                    )

                    // Reels options overlay
                    ReelsOptionsSingle(
                        modifier = Modifier
                            .align(Alignment.BottomCenter), navController = navController,
                        player = player,
                        postId = video.user_post_id,
                        duration = duration,
                        position = position,
                        isLikeLoading = isLikeLoading,
                        isSaveLoading = isSaveLoading,
                        showBABars = showBABars
                    )
                }
            }
            else if (video.post_property.images?.isNotEmpty() == true) {

                //  val animationType = SlideshowAnimation.values()[page % SlideshowAnimation.values().size]
                SingleSlideshowReel_Single(
                    // imagesList = item.post_property.image_urls,
                    //animationType = animationType,
                    postId = video.user_post_id,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .align(Alignment.Center),
                    navController = navController,
                    isLike_Loading = isLikeLoading,
                    isSave_Loading = isSaveLoading,
                    intervalMillis = 1000L,
                    viewModel
                )
            }
            else {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                )
                {

                    val type = when {
                        video.user_id == AppPreferences.getUserId() -> 1
                        else -> 2
                    }

                    PhotoRequestAssistant(
                        type,
                        Color.White,
                        video.user_post_id,
                        navController,
                        modifier = Modifier.fillMaxSize(),
                        video.post_interest
                    )


                    ReelsOptionsSingle(
                        modifier = Modifier
                            .align(Alignment.BottomCenter), navController = navController,
                        player = player,
                        postId = video.user_post_id,
                        duration = duration,
                        position = position,
                        isLikeLoading = isLikeLoading,
                        isSaveLoading = isSaveLoading,
                        showBABars = showBABars
                    )
                }
                /*Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                )
                {


                    //Text("No media available", color = Color.White)
                    Image(
                        painterResource(R.drawable.emptypostsrento),
                        ""
                    )
                    // Reels options overlay





                }*/
            }

            Image(
                painter = painterResource(R.drawable.propertytdetailsback), "",
                modifier = Modifier
                    .noRippleClickable {
                        reels_Show.value = false
                        AppPreferences.save_Post_Id(0)
                        constants.Profile_ViewModel.set_From_SoldOuts(false)
                        constants.PostProperty_ViewModel.setViewDetailsFlow(
                            ViewDetailsFlow.NONE
                        )
                        navController.navigateUp()
                    }
                    .padding(vertical = rememberNotchHeightDp().value, horizontal = 16.dp)
            )


        }

        ReelsOptionsSingle_Static(
            modifier = Modifier
                ///.align(Alignment.BottomCenter)
            , navController = navController
            , player = player,
            postId = video.user_post_id,
            duration = duration,
            position = position,
            isLikeLoading = isLikeLoading,
            isSaveLoading = isSaveLoading,
            showBABars = showBABars
        )


    }

    val network = rememberNetworkStatus()

    Enquiry_Form_Btm_Sheet_Structure(send_Eq_State.value , video.toGetReelsData() )



    if (repost_Btm.value) {
        ModalBottomSheet(
            onDismissRequest = {
                repost_Btm.value = false

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
                            .noRippleClickable{

                                constants.PostProperty_ViewModel.clear_Forms()
                                constants.PostProperty_ViewModel.first_Form_selected_PP(-1)
                                constants.PostProperty_ViewModel.select_Land_Cat_Id(-1)
                                constants.PostProperty_ViewModel.LandSubType_Selected_Click(-1)
//                            constants.PostProperty_ViewModel.pp_SecondForm_Residential_Select_Option(
//                                -1
//                            )
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
                                constants.PostProperty_ViewModel.clearAllPostFields()

                                constants.PostProperty_ViewModel.check_Price_Negotiation(false)
                                constants.PostProperty_ViewModel.put_budget_Price_PF5("")

                                constants.PostProperty_ViewModel.clear_Media()
                                constants.PostProperty_ViewModel.goToPPFormPage(0, 7)
                                AppPreferences.save_Post_Id(video.user_post_id)
                                get_Form_Preview_API_CALL { result ->
                                    when (result) {
                                        0 -> { state.value = result}
                                        1 -> {}
                                        2 -> {}
                                        3 -> {
                                            println("REPOST CLICKER SUCCESS")
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

                                            constants.PostProperty_ViewModel.set_onSelected_ProType((server_Data?.land_type_id ?: 0) )
                                            constants.PostProperty_ViewModel.pp_SecondForm_Residential_Select_Option(
                                                server_Data?.land_categorie_id ?: 0
                                            )

                                            constants.PostProperty_ViewModel.select_Land_Cat_Id(
                                                server_Data?.land_categorie_id ?: 0
                                            )

                                            constants.PostProperty_ViewModel.LandSubType_Selected_Click(server_Data?.land_categorie_id ?: 0)

                                            println("REPOST FROM PFOILE TOP____ ${server_Data?.land_type_id ?: 0}")
                                            constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value =  constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value.copy(
                                                first = server_Data?.land_type_id ?: 0,
                                                second = server_Data?.land_categorie_id ?: 0
                                            )

                                            println("REPOST FROM PFOILE TOP____ ${server_Data?.land_type_id ?: 0} (((( ${constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value}")


                                            constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value = Pair((server_Data?.land_type_id ?: 0)  , server_Data?.land_categorie_id ?: 0)

                                            println("Step 2 --- ${(server_Data?.land_type_id ?: 0) - 1 } -***${ constants.PostProperty_ViewModel._repost_Land_Cat_Type_Ids.value}***-- ${server_Data?.land_categorie_id ?: 0}")
//                                            println("LOCARTION DERAIKS sfjngv adsfbvkhjd REPOST ${server_Data.pincode} -__${server_Data.longitude}--${server_Data.latitude}--${server_Data.address}--${server_Data.city}- ${server_Data.country} -- ${data.state}")

                                            // Safely set values if they’re not empty
                                            if (!server_Data?.pincode.isNullOrEmpty()) {
                                                constants.PostProperty_ViewModel.set_pincode3(server_Data?.pincode ?: "")
                                            }
                                            if (!server_Data?.country.isNullOrEmpty()) {
                                                constants.PostProperty_ViewModel.set_country3(server_Data?.country ?: "")
                                            }
                                            if (!server_Data?.state.isNullOrEmpty()) {
                                                constants.PostProperty_ViewModel.set_state3(server_Data?.state ?: "")
                                            }
                                            if (!server_Data?.city.isNullOrEmpty()) {
                                                constants.PostProperty_ViewModel.set_city3(server_Data?.city?:"")
                                            }
                                            if (!server_Data?.locality.isNullOrEmpty()) {
                                                constants.PostProperty_ViewModel.set__selectedLocality3(server_Data?.locality ?:"")
                                            }
//
                                            if (server_Data?.latitude?.isNotEmpty() == true && server_Data?.longitude?.isNotEmpty() == true) {
                                                constants.PostProperty_ViewModel.add_Pinned_Lat_Long(
                                                    LatLng(
                                                        server_Data?.latitude?.toDouble() ?: 0.0,
                                                        server_Data?.longitude?.toDouble() ?: 0.0
                                                    )
                                                )
                                            }

//                                            if (server_Data.images.isNotEmpty()){
//                                                constants.PostProperty_ViewModel.addImages(server_Data?.images ?:"")
//                                            }
//                                            else if(server_Data.video.isNotEmpty()){
//                                                constants.PostProperty_ViewModel.addVideo(server_Data?.images ?:"")
//
//                                            }
                                            constants.PostProperty_ViewModel.add_pp3_Data(
                                                PP3_API_DC(
                                                    pincode = server_Data?.pincode ?: "",
                                                    country = server_Data?.country ?:"",
                                                    state = server_Data?.state ?:"",
                                                    city = server_Data?.city ?:"",
                                                    locality = server_Data?.locality ?:""
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
//                                                val imageMediaList = server_Data.images.map { imageUri ->
//                                                    UploadPropertyMedia(uri = Uri.parse(imageUri), isVideo = false)
//                                                }
//                                                constants.PostProperty_ViewModel.addImages(imageMediaList)
                                            }
                                            else if (!server_Data?.video.isNullOrEmpty()) {
//                                                val videoMedia = UploadPropertyMedia(
//                                                    uri = Uri.parse(server_Data.video),
//                                                    isVideo = true
//                                                )
//                                                constants.PostProperty_ViewModel.addVideo(videoMedia)
                                            }



                                            constants.PostProperty_ViewModel.select_Land_Type(server_Data?.land_type_id ?: 0)

//                                            val budget_Price =
//                                                constants.PostProperty_ViewModel.put_budget_Price_PF5(server_Data?.price ?: "")

                                            println("SERVER DATA -- ${server_Data} ---- cons.${constants.PostProperty_ViewModel.get_Selected_Fields_Form()}")

                                            constants.Profile_ViewModel.set_From_Repost(1)
                                            constants.PostProperty_ViewModel.set_Post_Form_Flow(2)



                                            // ✅ Navigate after everything is done
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


    if (reelsBTMSheetState.value) {
       // playerManager.pauseVideo(pagerState.currentPage)
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.toggleReelsBTMSheet(false)
            }
            , containerColor = newWhite
        )
        {

            LaunchedEffect(Unit) {
                constants.Reels_ViewModel.resetReelsBTMSOptions()
                when {


//                    !isWithinLast5DaysOfValidity(videos[pagerState.currentPage].post_property?.created_at ?: "") -> {
//                    viewdetailFlow.value == ViewDetailsFlow.RENEW -> {
//                        println("aqwqwqwqwqw0reels")
//                        constants.Reels_ViewModel.removeReelsBTMSOptions(
//                            constants.Reels_ViewModel.renewpostOptions
//                        )
//                    }

                    viewdetailFlow.value == ViewDetailsFlow.EXPIRY -> {
                        println("aqwqwqwqwqw2reels")

                        constants.Reels_ViewModel.removeReelsBTMSOptions(
                            constants.Reels_ViewModel.expiredIdOptions
                        )
                    }

                    viewdetailFlow.value == ViewDetailsFlow.RENTOUT -> {
                        println("indisddejeididjdjdfj2222222")
                        println("aqwqwqwqwqw3reels")

                        constants.Reels_ViewModel.removeReelsBTMSOptions(
                            constants.Reels_ViewModel.soldoutIdOptions
                        )
                    }

                    constants.Profile_ViewModel.selectedOwnProfileTab.value == OwnProfileTab.EXPIRED -> {
                        println("aqwqwqwqwqw4reels")
                        constants.Reels_ViewModel.removeReelsBTMSOptions(
                            constants.Reels_ViewModel.expiredIdOptions
                        )
                    }

                    video.user_id == AppPreferences.getUserId() -> {
                        println("aqwqwqwqwqw5reels")
                        constants.Reels_ViewModel.removeReelsBTMSOptions(
                            constants.Reels_ViewModel.ownIdOptions
                        )
                    }



                    else -> {
                        println("aqwqwqwqwqw6reels")
                        constants.Reels_ViewModel.removeReelsBTMSOptions(constants.Reels_ViewModel.pviewotherIdOptions)
                    }
                }
            }

            val data = reelsBTMSOptions.value

//            val data = when {
//                constants.Profile_ViewModel.from_SoldOuts.value == true -> {
//                    // Third scenario: Show only Repost and Delete
//                    reelsBTMSOptions.value.filter { it.title == "Repost Property" || it.title == "Delete Property" }
//                }
//               video.user_id == AppPreferences.getUserId() -> {
//                    // Owner: Show full list except the last item
//                    reelsBTMSOptions.value.dropLast(1)
//                }
//                else -> {
//                    // Not owner: Show only last 2 items
//                    reelsBTMSOptions.value.takeLast(2)
//                }
//            }


            data.forEachIndexed { index, Options ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(newWhite)
                        .padding(horizontal = 16.dp)
                        .noRippleClickable{
//                            if (index == data.size - 1) {
//                                // report_BS.value = true
//                            } else if (Options.title == "Mark as Sold") {
//                                mark_as_Sold.value = true
//                            }
//                            when(index) {
//                                0 -> {
//
//                                }
//                                1 -> {
//
//                                }
//                            }
                            when {
                                Options.title == "Repost Property" -> {

                                    constants.Reels_ViewModel.clear_view_pro_Details()
                                    //AppPreferences.save_Post_Id(videos[pagerState.currentPage].user_post_id)
                                    repost_Btm.value = true
                                    constants.Common_H_ViewModel.toggleReelsBTMSheet(false)
                                }
                                Options.title == "Edit Property" -> {
                                    constants.Reels_ViewModel.clear_view_pro_Details()
                                    navController.navigate(ProfileScreenFlow.Edit_Property_Option.route)
                                    constants.Common_H_ViewModel.toggleReelsBTMSheet(false)
                                }
                                Options.title == "Delete Property" -> {
                                    println("COMING INTO HERE")

                                    delete_Post = true
                                    viewModel.toggleReelsBTMSheet(false)

                                }
                                Options.title == "Mark as Sold" -> {
                                   mark_as_Sold.value = true
                                    constants.Common_H_ViewModel.toggleReelsBTMSheet(false)
                                }
                                Options.title == "Report" -> {
                                    if (video.post_property.is_report != 1) {
                                        constants.Profile_ViewModel.toggle_ProfileReport_Options(
                                            -1
                                        )

                                        //AppPreferences.getUserId(),


                                        constants.Profile_ViewModel.toggle_ReportSucces_True()
                                        report_BS.value = true

                                        constants.Common_H_ViewModel.toggleReelsBTMSheet(false)
                                    } else {
                                        constants.Common_H_ViewModel.toggleReelsBTMSheet(false)
                                        GlobalSnackbar.show("Post already reported")
                                    }
                                }
                                Options.title == "Share" -> {
                                    //Options.onClick()
                                    constants.DefaultShare("https://toletspot.com/property/${video.user_post_id}" ,1)
                                    constants.Common_H_ViewModel.toggleReelsBTMSheet(false)
                                }
                            }

                        }
                    , verticalAlignment = Alignment.CenterVertically
                    , horizontalArrangement = Arrangement.Start
                )
                {

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(0xffF7F0DC))
                        , contentAlignment = Alignment.Center
                    ){
                        SubcomposeAsyncImage(
                            model = Options.icon
                            ,""
                            , modifier = Modifier
                                .size(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                    Text(Options.title , color = newBlack , fontSize = constants.textUnit(14))

                }
                Spacer(modifier = Modifier.padding(16.dp))
            }

        }
    }

    println("DUMMY LIST MAPPED -- ${video.whatsapp_num}")
    // Comment sheet
    if (cmt_btm_Sheet.value) {

        ModalBottomSheet(
            onDismissRequest = {
                constants.Common_H_ViewModel.dismiss_Cmt_btm_Sheet()
            },
            sheetState = bottomSheetState,
            containerColor = newWhite,
            sheetGesturesEnabled = false
        ) {
            var dlist = listOf(video.toGetReelsData())

            Comment_Structure(dlist, 0, navController, viewModel)
        }
    }

    if (mark_as_Sold.value == true) {
        //playerManager.pauseVideo(pagerState.currentPage)
        Mark_As_Sold_Flow(mark_as_Sold, video.user_post_id, navController)
    }

    // delete post
    Common_Popup(
        delete_Post,
        modifier = Modifier
            .background(Color(0xffF7F0DC))
        , image = "",
        userName = "",
        icon = R.drawable.closeenquiry /// or R.drawable
    )
    {
        Column (
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 24.dp)
            , verticalArrangement = Arrangement.spacedBy(12.dp)
            , horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            Spacer(modifier = Modifier.padding(2.dp))
            //constants.spacer(2)

            Text(
                text = "Are you Sure, You want to delete?",
                color = newBlack,
                fontSize = constants.textUnit(16),
                fontFamily = constants.fontFamily(0)
            )

            //constants.spacer(2)

            Text(
                text = "This action cannot be undone. Are you sure you want to delete this property permanently?",
                color = newBlack,
                fontSize = constants.textUnit(12),
                fontFamily = constants.fontFamily(3)
                , textAlign = TextAlign.Center
                , lineHeight = 24.sp
                ,modifier = Modifier.padding(if (forTab()) 36.dp else 0.dp)
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
                        .height(if (forTab()) 46.dp else 36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xffE8E8E8))
                        .noRippleClickable{
                            delete_Post = false
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
                        .height(if (forTab()) 46.dp else 36.dp)
                        .noRippleClickable{
                            ClickHelper.getInstance().clickOnce {
                                if (ClickGuard.canClick()) {

                                    if (network.value == NetworkStatus.Online) {
                                        println(" API CALL HIT STATUS __ ue}")
                                        println("COMING INSIDE DELETTING SOLDOUTS")
                                        constants.API_Vm.delete_Post_SM_Drafts(
                                            user_id = AppPreferences.getUserId(),
                                            select_all = 0,
                                            user_post_id = video.user_post_id.toString()
                                            ,
                                        )
                                        { aPI_Result_Handling ->
                                            when (aPI_Result_Handling) {
                                                is API_Result_Handling.Loading -> {}
                                                is API_Result_Handling.NoData -> {}
                                                is API_Result_Handling.Deactivated -> {
                                                    // resultCallback(5)
                                                }

                                                is API_Result_Handling.Error -> {}
                                                is API_Result_Handling.Success -> {

                                                    if (constants.Profile_ViewModel.from_SoldOuts.value == true) {
                                                        constants.Profile_ViewModel.deleteByPostId_Profile_SoldOuts(
                                                            video.user_post_id
                                                        )
                                                        navController.navigateUp()
                                                        delete_Post = true
                                                    } else {
                                                        println("65212345678987654321234567890-")
                                                        constants.Reels_ViewModel.deleteVideoById_Profile_Post_Reels(
                                                            video.user_post_id
                                                        )
                                                        delete_Post = true

                                                        playerManager.releaseAll()

                                                        // Now navigate away
                                                        AppPreferences.save_Post_Id(0)
                                                        constants.Profile_ViewModel.set_From_SoldOuts(
                                                            false
                                                        )
                                                        constants.API_Vm.totalPages_PS_FF = 0
                                                        navController.navigateUp()
                                                    }

                                                    constants.Common_H_ViewModel.toggleReelsBTMSheet(
                                                        false
                                                    )

                                                }
                                            }
                                        }
                                        /*constants.API_Vm.put_Block_User(
                                    user_id = AppPreferences.getUserId(),
                                    blocker_id = profile_Content.value?.user_id ?: 0,
                                    //profile_Content.value?.user_id ?: 0,
                                    status = blockStatus.value
                                    //if (profile_Content.value?.is_blocked == 0) "1" else "0"
                                )
                                { apiResultHandling ->
                                    when (apiResultHandling) {
                                        is API_Result_Handling.Error -> {
                                            //errror
                                            //constants.Profile_ViewModel.change_Update_profile(false)
                                        }

                                        is API_Result_Handling.Deactivated -> {
                                            // resultCallback(5)
                                        }

                                        is API_Result_Handling.NoData -> {
                                            // no data
                                        }

                                        is API_Result_Handling.Loading -> {
                                            //loading
                                            // constants.Profile_ViewModel.change_Update_profile(true)
                                        }

                                        is API_Result_Handling.Success -> {
                                            if (profile_Content.value?.is_blocked == 0) {
                                                constants.Profile_ViewModel.updateBlockedStatus_Selected_Profile(
                                                    1
                                                )
                                            } else {
                                                constants.Profile_ViewModel.updateBlockedStatus_Selected_Profile(
                                                    0
                                                )
                                            }
                                            block_PopUp = false
                                            //constants.Profile_ViewModel.enable_Edit_Profile()
                                            //constants.Profile_ViewModel.change_Update_profile(false)
                                            //success
                                        }
                                    }
                                }*/
                                    } else {
                                        toast(constants.activity.getString(R.string.no_Internet))
                                        // GlobalSnackbar.show(constants.activity.getString(R.string.no_Internet))
                                    }
                                }
                            }
                        }
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xffE54C3C))
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
            Spacer(modifier = Modifier.padding(8.dp))
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
                                /* Box(
                                 modifier = Modifier
                                     .fillMaxWidth()
                                     .heightIn(min = 50.dp , max = 80.dp)
                                     .clip(RoundedCornerShape(8.dp))
                                     .background(Color.White)
                                     .border(1.dp , newGray , RoundedCornerShape(8.dp))
                             )
                             {
                                 TextField(
                                     value = user_Manual_report_String.value,
                                     onValueChange = {
                                         user_Manual_report_String.value = it
                                     },
                                     placeholder = {
                                         Text(
                                             text = "What else we need to know...",
                                             color = newBlack,
                                             fontSize = constants.textUnit(12),
                                             fontFamily = constants.fontFamily(3)
                                         )
                                     },
                                     colors = TextFieldDefaults.colors(
                                         focusedContainerColor = Color.White
                                         ,unfocusedContainerColor = Color.White
                                         , focusedIndicatorColor = Color.Transparent
                                         , unfocusedIndicatorColor = Color.Transparent
                                         , focusedTextColor = newBlack
                                         , unfocusedTextColor = newGray
                                     )
                                 )
                             }*/
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
                    else {
                        Column(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                            , verticalArrangement = Arrangement.SpaceEvenly
                            , horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            SubcomposeAsyncImage(
                                model = R.drawable.profile_report_submit_success
                                ,""
                                , modifier = Modifier
                                    .size(150.dp)
                            )


                            Text(
                                text = "Submitted Successfully",
                                color = newBlack,
                                fontSize = constants.textUnit(18),
                                fontFamily = constants.fontFamily(0)
                            )


                            Text(
                                text = "Thank you for bringing this to our attention.",
                                color = newBlack,
                                fontSize = constants.textUnit(12),
                                fontFamily = constants.fontFamily(3)
                            )

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
                                        .noRippleClickable{
                                            if (network.value == NetworkStatus.Online) {
                                                if ((video?.post_property?.is_report
                                                        ?: "") != "1"
                                                ) {
                                                    constants.API_Vm.put_Report_All(
                                                        user_id = AppPreferences.getUserId(),
                                                        user_post_id = video.user_post_id.toString(),
                                                        receiver_id = video.user_id.toString(),
                                                        comment_id = "",
                                                        report_sentence_id = (constants.Profile_ViewModel.getSelectedProfileReportOptionId()
                                                            ?.plus(1)) ?: 0,
                                                        report_sentence = user_Manual_report_String.value,
                                                        status = 2,
                                                    )
                                                    { apiResultHandling ->

                                                        when (apiResultHandling) {
                                                            is API_Result_Handling.Loading -> {
                                                                // loading
                                                                //constants.PostProperty_ViewModel.change_Status_PFs(true)
                                                            }

                                                            is API_Result_Handling.Deactivated -> {
                                                                //resultCallback(5)
                                                            }

                                                            is API_Result_Handling.Error -> {
                                                                // fail
                                                                //constants.PostProperty_ViewModel.change_Status_PFs(false)
                                                            }

                                                            is API_Result_Handling.Success -> {

                                                                constants.Profile_ViewModel.toggleReportSubmissionSuccess()

                                                                constants.Enquiry_ViewModel.toggle__video_Report_Enquiry(video.user_post_id)
                                                                constants.Enquiry_ViewModel.toggle__video_Report_SelfEnquiry(video.user_post_id)
                                                                // success
                                                                //constants.PostProperty_ViewModel.change_Status_PFs(false)
                                                            }

                                                            is API_Result_Handling.NoData -> {
                                                                // no data
                                                                //constants.PostProperty_ViewModel.change_Status_PFs(false)
                                                            }
                                                        }
                                                    }
                                                }
                                                else
                                                {
                                                    report_BS.value = false
                                                    //constants.Common_H_ViewModel.toggleReelsBTMSheet(false)
                                                    scope.launch {
                                                        snackbarHostState.showSnackbar(" Post Already Reported")
                                                    }
                                                }
                                            }
                                            else {
                                                toast(constants.activity.getString(R.string.no_Internet))
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
fun ReelsOptionsSingle(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    player: ExoPlayer?,
    postId: Int,
    duration: MutableState<Long>,
    position: MutableState<Long>,
    isLikeLoading: MutableState<Boolean>,
    isSaveLoading: MutableState<Boolean>,
    showBABars: State<Boolean>
) {
    val myLeads by constants.Enquiry_ViewModel.my_Leads.collectAsState()
    val selfEnquiry by constants.Enquiry_ViewModel.self_Enquiry.collectAsState()

    val currentVideo = remember(myLeads, selfEnquiry, postId) {
        myLeads.firstOrNull { it.post_user.user_post_id == postId }?.post_user
            ?: selfEnquiry.firstOrNull { it?.post_user?.user_post_id == postId }?.post_user
    } ?: return

    val isLiked = currentVideo.is_liked == 1
    val isSaved = currentVideo.is_saved == 1

    Box(modifier = modifier) {

        if (currentVideo.user_id == AppPreferences.getUserId()) {
            Image(
                painter = painterResource(R.drawable.ownpropertybadgerento),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomStart)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
            //  .padding(bottom = if (showBABars.value) 100.dp else 0.dp),
            , verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .wrapContentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
              constants.Reels_ViewModel.reels_Options_List.forEachIndexed { index, icon ->

                val scale = remember { Animatable(1f) }
                LaunchedEffect(isLiked) {
                    if (index == 0 && isLiked) {
                        scale.animateTo(1.3f, tween(150))
                        scale.animateTo(1f, tween(150))
                    }
                }

                val showIcon = when (index) {
                    0 -> if (isLiked) icon.enabled_Icon else icon.icon
                    2 -> if (isSaved) icon.enabled_Icon else icon.icon
                    else -> icon.icon
                }


                AsyncImage(
                    model = showIcon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(if (forTab()) 40.dp else 36.dp)
                        .graphicsLayer {
                            scaleX = scale.value
                            scaleY = scale.value
                        }
                        .noRippleClickable {
                            when (index) {
                                0 -> handleLikeClick(postId, isLiked, isLikeLoading)
                                1 -> handleCommentClick()
                                2 -> handleSaveClick(postId, isSaved, isSaveLoading)
                                3 -> constants.Common_H_ViewModel.toggleReelsBTMSheet(true)
                            }
                        }
                )

                if (index == 0) {
                    Text(currentVideo.total_likes.toString(), color = newWhite)
                } else if (index == 1) {
                    Text(currentVideo.total_comments.toString(), color = newWhite)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
            }
        }
    }
}


/*@Composable
fun ReelsOptionsSingle(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    player: ExoPlayer?,
    currentVideo: PostUser,
    duration: MutableState<Long>,
    position: MutableState<Long>,
    isLikeLoading: MutableState<Boolean>,
    isSaveLoading: MutableState<Boolean>,
    showBABars: State<Boolean>
) {

    // ✅ ADD THIS: Observe the updated list from ViewModel
    val myLeadsData by constants.Enquiry_ViewModel.my_Leads.collectAsState()
    val selfEnquiryData by constants.Enquiry_ViewModel.self_Enquiry.collectAsState()

    // ✅ Find current video from either list
//    val currentVideo = remember(myLeadsData, selfEnquiryData, video) {
//        when {
//            myLeadsData.isNotEmpty() -> {
//                myLeadsData
//                    .firstOrNull { it.post_user.user_post_id == video.user_post_id }?.post_user
//            }
//
//            else -> {
//                selfEnquiryData
//                    .firstOrNull { it?.post_user?.user_post_id == video.user_post_id }?.post_user
//            }
//        }
//
//    }

    // Debug recomposition
    LaunchedEffect(currentVideo) {
//        println("🔄 Recompose -> id:${currentVideo.user_post_id} liked:${currentVideo.is_liked} saved:${currentVideo.is_saved}")
    }


    println("dfghuio")
    Box(modifier = modifier) {
        if (currentVideo.user_id == AppPreferences.getUserId()) {
            Image(
                painter = painterResource(R.drawable.ownpropertybadgerento), "",
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 16.dp)
                    .zIndex(3f)
                    .align(Alignment.BottomStart)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
            //  .padding(bottom = if (showBABars.value) 100.dp else 0.dp),
            , verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            // Right-side actions (like, comment, save, more)
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .wrapContentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                constants.Reels_ViewModel.reels_Options_List.forEachIndexed { index, icon ->

                    val isLiked = currentVideo?.is_liked == 1
                    val isSaved = currentVideo?.is_saved == 1

                    val scale = remember { Animatable(1f) }
                    LaunchedEffect(isLiked) {
                        if (isLiked && index == 0) {
                            scale.animateTo(1.3f, tween(200))
                            scale.animateTo(1f, tween(200))
                        }
                    }

                    val showIcon = when (index) {
                        0 -> if (isLiked) icon.enabled_Icon else icon.icon
                        2 -> if (isSaved) icon.enabled_Icon else icon.icon
                        else -> icon.icon
                    }

//                    if ((index == 0 && isLikeLoading.value) || (index == 2 && isSaveLoading.value)) {
//                        CircularProgressIndicator(
//                            modifier = Modifier.size(25.dp),
//                            color = newBlue
//                        )
//                    } else {
                    AsyncImage(
                        model = showIcon,
                        contentDescription = "",
                        modifier = Modifier
                            .graphicsLayer { scaleX = scale.value; scaleY = scale.value }
                            .size(24.dp)
                            .noRippleClickable {
                                ClickHelper.getInstance().clickOnce {
                                    when (index) {
                                        0 -> handleLikeClick(currentVideo.user_post_id, isLiked, isLikeLoading)
                                        1 -> handleCommentClick()
                                        2 -> handleSaveClick(currentVideo, isSaved, isSaveLoading)
                                        3 -> constants.Common_H_ViewModel.toggleReelsBTMSheet(true)
                                    }
                                }
                            }
                    )
                    // }

                    // Counters
                    if (index == 0) {
                        Text((currentVideo?.total_likes ?: 0).toString(), color = newWhite, fontSize = constants.textUnit(12), fontFamily = constants.fontFamily(2))
                    } else if (index == 1) {
                        Text((currentVideo?.total_comments ?:0 ).toString(), color = newWhite, fontSize = constants.textUnit(12), fontFamily = constants.fontFamily(2))
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}*/



@Composable
fun ReelsOptionsSingle_Static(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    player: ExoPlayer?,
    postId: Int,
    duration: MutableState<Long>,
    position: MutableState<Long>,
    isLikeLoading: MutableState<Boolean>,
    isSaveLoading: MutableState<Boolean>,
    showBABars: State<Boolean>
)
{

    // ✅ ADD THIS: Observe the updated list from ViewModel
    val myLeads by constants.Enquiry_ViewModel.my_Leads.collectAsState()
    val selfEnquiry by constants.Enquiry_ViewModel.self_Enquiry.collectAsState()

    val video = remember(myLeads, selfEnquiry, postId) {
        myLeads.firstOrNull { it.post_user.user_post_id == postId }?.post_user
            ?: selfEnquiry.firstOrNull { it?.post_user?.user_post_id == postId }?.post_user
    } ?: return

    // Debug recomposition
    LaunchedEffect(video) {
//        println("🔄 Recompose -> id:${currentVideo.user_post_id} liked:${currentVideo.is_liked} saved:${currentVideo.is_saved}")
    }


    println("dfghuio")
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
            //  .padding(bottom = if (showBABars.value) 100.dp else 0.dp),
            , verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            // Bottom gradient + user info + actions

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.White
                    )
            )
            {
                var vd_Loader by remember { mutableStateOf(false) }

                if (video.user_id == AppPreferences.getUserId()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 6.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.Start
                    )
                    {
                        if (video.post_property.is_sold == 1) {
                            Image(
                                painterResource(R.drawable.soldoutidentifier), "",
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                        ListItem(
                            leadingContent = {
                                Box(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .clip(CircleShape)
                                        .background(Color.White)
                                        .noRippleClickable{
                                            constants.Common_H_ViewModel.toggleshowTABars(false)
                                            ClickHelper.getInstance().clickOnce {
                                                if (ClickGuard.canClick()) {
                                                    logD("clicks reels profile")
                                                    constants.Common_H_ViewModel.toggleshowTABars(false)
                                                    constants.Profile_ViewModel.add_Selected_User_Name(
                                                        video.username ?: "username"
                                                    )

                                                    //new flowwewwwwwww
                                                    constants.Profile_ViewModel.add_BF_Handler(
                                                        Profile_Handle_Back(
                                                            current_UsedId = AppPreferences.getUserId(),
                                                            other_UserId = video?.user_id ?: 0,
                                                            ff_User_Name = video?.username ?: "",
                                                            ff_Fw_Count = 999,
                                                            ff_Fg_Count = 999,
                                                            // is_Search_Enabled = is_Search_Enabled.value,
                                                            // search_Text = search_Text.value
                                                        )
                                                    )

                                                    /// println("ITEM PROFILE STRUCTURE __ ${is_Search_Enabled.value} -- ${constants.Profile_ViewModel.profile_BF_Handler.value}")

                                                    println("GIVEN OTHER USER ID -- ${constants.Profile_ViewModel.get_Other_User_Id()}")

                                                    constants.Profile_ViewModel.addProfile(
                                                        video?.user_id ?: 0
                                                    )
                                                    constants.Profile_ViewModel.add_Selected_Profile_Id(
                                                        id = video.user_id ?: 0
                                                    )

                                                    // if (view_Details_Data.value?.user_id == AppPreferences.)
                                                    constants.Common_H_ViewModel.toggleshowBABars(false)
                                                    navController.navigate(VideosScreenFlow.Other_Profile_Structure.route)
                                                }
                                            }
                                        }
                                    , contentAlignment = Alignment.Center
                                )
                                {
                                    SubcomposeAsyncImage(
                                        model = video?.profile_image ?: "",
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                        ,
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
                                                //.padding(8.dp)
                                                , contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = video?.username.takeIf { it?.isNotEmpty() == true }
                                                        ?.take(1)?.uppercase() ?: ""
                                                )
                                            }
                                        } else {
                                            SubcomposeAsyncImageContent()
                                        }
                                    }
                                }
                            }
                            , headlineContent = {
                                Row(
                                    modifier = Modifier
                                        .noRippleClickable{
                                            constants.Common_H_ViewModel.toggleshowTABars(false)
                                            ClickHelper.getInstance().clickOnce {
                                                if (ClickGuard.canClick()) {
                                                    logD("clicks reels profile")
                                                    constants.Common_H_ViewModel.toggleshowTABars(false)
                                                    constants.Profile_ViewModel.add_Selected_User_Name(
                                                        video.username ?: "username"
                                                    )

                                                    //new flowwewwwwwww
                                                    constants.Profile_ViewModel.add_BF_Handler(
                                                        Profile_Handle_Back(
                                                            current_UsedId = AppPreferences.getUserId(),
                                                            other_UserId = video?.user_id ?: 0,
                                                            ff_User_Name = video?.username ?: "",
                                                            ff_Fw_Count = 999,
                                                            ff_Fg_Count = 999,
                                                            // is_Search_Enabled = is_Search_Enabled.value,
                                                            // search_Text = search_Text.value
                                                        )
                                                    )

                                                    /// println("ITEM PROFILE STRUCTURE __ ${is_Search_Enabled.value} -- ${constants.Profile_ViewModel.profile_BF_Handler.value}")

                                                    println("GIVEN OTHER USER ID -- ${constants.Profile_ViewModel.get_Other_User_Id()}")

                                                    constants.Profile_ViewModel.addProfile(
                                                        video?.user_id ?: 0
                                                    )
                                                    constants.Profile_ViewModel.add_Selected_Profile_Id(
                                                        id = video.user_id ?: 0
                                                    )

                                                    // if (view_Details_Data.value?.user_id == AppPreferences.)
                                                    constants.Common_H_ViewModel.toggleshowBABars(false)
                                                    navController.navigate(VideosScreenFlow.Other_Profile_Structure.route)
                                                }
                                            }
                                        }
                                    , verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                )
                                {
                                    Text(
                                        video.name.ifEmpty { video.username },
                                        color = Color.Black,
                                        fontSize = constants.textUnit(16),
                                        fontFamily = constants.fontFamily(1)
                                    )

                                }
                            }
                            , supportingContent = {
                                // Spacer(modifier = Modifier.padding(4.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    // if (currentReel.user_id == AppPreferences.getUserId()){

                                    var text = if (video.post_property.user_type == "0") "Owner" else "Broker"
                                    Text(
                                        "$text \u2022 ",
                                        color = Color(0xff575757),
                                        fontSize = constants.textUnit(12),
                                        fontFamily = constants.fontFamily(1),
                                        // overflow = TextOverflow.Ellipsis
                                    )
                                    //  }

                                    Text(
                                        "Posted ${getTimeAgo(video.post_property.created_at ?:"")}",
                                        color = Color(0xff575757),
                                        fontSize = constants.textUnit(12),
                                        fontFamily = constants.fontFamily(1),
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }

                            , modifier = Modifier.background(Color.Transparent),
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                        )

                        ListItem(
                            overlineContent = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Text(
                                        video.post_property.landCategoryText ?: "",
                                        color = newBlack,
                                        fontSize = constants.textUnit(18),
                                        fontFamily = constants.fontFamily(0)
                                    )
                                    Text(
                                        " \u2022 ${video.post_property.is_this_property_for_rent_or_lease}",
                                        color = newBlack,
                                        fontSize = constants.textUnit(14),
                                        fontFamily = constants.fontFamily(1)
                                    )
                                }

                            }
                            , headlineContent = {
                                Column() {
                                    Row(
                                        modifier = Modifier,
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.locationenquiry), "",
                                            modifier = Modifier.size(16.dp)
                                        )

                                        constants.spacer(4)

                                        Text(
                                            "${video.post_property.city}, ${video.post_property.state}",
                                            color = Color(0xff7E7E7E),
                                            fontSize = constants.textUnit(14),
                                            fontFamily = constants.fontFamily(1)
                                        )
                                    }
                                    constants.spacer(4)
                                }
                            }
                            , supportingContent = {

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Card(
                                        modifier = Modifier
                                            .height(64.dp)
                                            .width(138.dp),
                                        shape = RoundedCornerShape(6.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color(0xffCECECE).copy(.3f)
                                        )
                                    )
                                    {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(horizontal = 6.dp),
                                            verticalArrangement = Arrangement.SpaceEvenly,
                                            horizontalAlignment = Alignment.Start
                                        ) {


                                            BasicText(
                                                text = "\u20B9 ${video.post_property.rent?.ifEmpty { video.post_property.lease_amount }}",
                                                color = { newBlack },
                                                style = TextStyle(fontFamily = constants.fontFamily(0)),
                                                autoSize = TextAutoSize.StepBased(
                                                    minFontSize = 6.sp,
                                                    constants.textUnit(16),
                                                    stepSize = 2.sp
                                                )
                                            )


                                            Text(
                                                "\u20B9 Amount",
                                                color = Color(0xff323232),
                                                fontSize = constants.textUnit(14),
                                                fontFamily = constants.fontFamily(3)
                                            )
                                        }
                                    }

                                    Card(
                                        modifier = Modifier
                                            .height(64.dp)
                                            .width(138.dp)
                                        // .padding(horizontal = 8.dp)
                                        ,
                                        shape = RoundedCornerShape(6.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color(0xffCECECE).copy(.3f)
                                        )
                                    )
                                    {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(horizontal = 6.dp),
                                            verticalArrangement = Arrangement.SpaceEvenly,
                                            horizontalAlignment = Alignment.Start
                                        ) {
                                            Text(
                                                "${video.post_property.carpet_area} ${video.post_property.carpet_area_unit}",
                                                color = newBlack,
                                                fontSize = constants.textUnit(16),
                                                fontFamily = constants.fontFamily(0)
                                            )

                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start
                                            ) {
                                                Image(
                                                    painter = painterResource(R.drawable.reelscarpetareaicon),
                                                    "",
                                                    modifier = Modifier.size(12.dp)
                                                )

                                                constants.spacer(2)

                                                Text(
                                                    "Carpet Area",
                                                    color = Color(0xff323232),
                                                    fontSize = constants.textUnit(14),
                                                    fontFamily = constants.fontFamily(3)
                                                )
                                            }
                                        }
                                    }

                                    Card(
                                        modifier = Modifier
                                            .height(64.dp)
                                            .width(38.dp)
                                            .noRippleClickable {
                                                ClickHelper.getInstance().clickOnce {
                                                    constants.Reels_ViewModel.clear_view_pro_Details()
                                                    player?.pause()
                                                    constants.Reels_ViewModel.add_View_Property_Details(
                                                        video.toGetReelsData()
                                                    )
                                                    var mode = if (video.post_property.video?.isEmpty() == true) 1 else 0
                                                    constants.Common_H_ViewModel.view_Property_Details_Mode.value = mode

                                                    constants.PostProperty_ViewModel.setViewDetailsFlow(
                                                        when {
                                                            constants.PostProperty_ViewModel.viewDetailsFlow.value == ViewDetailsFlow.RENTOUT -> ViewDetailsFlow.RENTOUT
                                                            constants.PostProperty_ViewModel.viewDetailsFlow.value == ViewDetailsFlow.EXPIRY -> ViewDetailsFlow.EXPIRY
                                                            video.user_id == AppPreferences.getUserId() -> ViewDetailsFlow.OWN
                                                            else -> ViewDetailsFlow.OTHERS
                                                        }
                                                    )

                                                    navController.navigate(EnquiriesFlow.ViewPropertyStructure.route)
                                                }
                                            }
                                            .border(1.dp, Color(0xffCECECE), RoundedCornerShape(4.dp)),
                                        shape = RoundedCornerShape(4.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color(0xffEBEBEB)
                                        )
                                    )
                                    {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize(), contentAlignment = Alignment.Center
                                        ) {

                                            if (vd_Loader) {
                                                CircularProgressIndicator(
                                                    modifier = Modifier.size(24.dp),
                                                    color = newBlue
                                                )
                                            } else {
                                                Image(
                                                    painter = painterResource(R.drawable.right_arrow),
                                                    "",
                                                    colorFilter = ColorFilter.tint(
                                                        Color(0xff575757)
                                                    )
                                                )
                                            }
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
                else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 6.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.Start
                    )
                    {
                        if (video.post_property.is_sold == 1) {
                            Image(
                                painterResource(R.drawable.soldoutidentifier), "",
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                        ListItem(
                            leadingContent = {
                                Box(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .clip(CircleShape)
                                        .background(Color.White)
                                        .noRippleClickable{
                                            constants.Common_H_ViewModel.toggleshowTABars(false)
                                            ClickHelper.getInstance().clickOnce {
                                                if (ClickGuard.canClick()) {
                                                    logD("clicks reels profile")
                                                    constants.Common_H_ViewModel.toggleshowTABars(false)
                                                    constants.Profile_ViewModel.add_Selected_User_Name(
                                                        video.username ?: "Unknown"
                                                    )

                                                    //new flowwewwwwwww
                                                    constants.Profile_ViewModel.add_BF_Handler(
                                                        Profile_Handle_Back(
                                                            current_UsedId = AppPreferences.getUserId(),
                                                            other_UserId = video?.user_id ?: 0,
                                                            ff_User_Name = video?.username ?: "",
                                                            ff_Fw_Count = 999,
                                                            ff_Fg_Count = 999,
                                                            // is_Search_Enabled = is_Search_Enabled.value,
                                                            // search_Text = search_Text.value
                                                        )
                                                    )

                                                    /// println("ITEM PROFILE STRUCTURE __ ${is_Search_Enabled.value} -- ${constants.Profile_ViewModel.profile_BF_Handler.value}")

                                                    println("GIVEN OTHER USER ID -- ${constants.Profile_ViewModel.get_Other_User_Id()}")

                                                    constants.Profile_ViewModel.addProfile(
                                                        video?.user_id ?: 0
                                                    )
                                                    constants.Profile_ViewModel.add_Selected_Profile_Id(
                                                        id = video.user_id ?: 0
                                                    )

                                                    // if (view_Details_Data.value?.user_id == AppPreferences.)
                                                    constants.Common_H_ViewModel.toggleshowBABars(false)
                                                    navController.navigate(VideosScreenFlow.Other_Profile_Structure.route)
                                                }
                                            }
                                        }
                                    , contentAlignment = Alignment.Center
                                )
                                {
                                    SubcomposeAsyncImage(
                                        model = video?.profile_image ?: "",
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                        ,
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
                                                //.padding(8.dp)
                                                , contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = video?.username.takeIf { it?.isNotEmpty() == true }
                                                        ?.take(1)?.uppercase() ?: ""
                                                )
                                            }
                                        } else {
                                            SubcomposeAsyncImageContent()
                                        }
                                    }
                                }
                            }
                            , headlineContent = {
                                Row(
                                    modifier = Modifier
                                        .noRippleClickable{
                                            constants.Common_H_ViewModel.toggleshowTABars(false)
                                            ClickHelper.getInstance().clickOnce {
                                                if (ClickGuard.canClick()) {
                                                    logD("clicks reels profile")
                                                    constants.Common_H_ViewModel.toggleshowTABars(false)
                                                    constants.Profile_ViewModel.add_Selected_User_Name(
                                                        video.username ?: "Unknown"
                                                    )

                                                    //new flowwewwwwwww
                                                    constants.Profile_ViewModel.add_BF_Handler(
                                                        Profile_Handle_Back(
                                                            current_UsedId = AppPreferences.getUserId(),
                                                            other_UserId = video?.user_id ?: 0,
                                                            ff_User_Name = video?.username ?: "",
                                                            ff_Fw_Count = 999,
                                                            ff_Fg_Count = 999,
                                                            // is_Search_Enabled = is_Search_Enabled.value,
                                                            // search_Text = search_Text.value
                                                        )
                                                    )

                                                    /// println("ITEM PROFILE STRUCTURE __ ${is_Search_Enabled.value} -- ${constants.Profile_ViewModel.profile_BF_Handler.value}")

                                                    println("GIVEN OTHER USER ID -- ${constants.Profile_ViewModel.get_Other_User_Id()}")

                                                    constants.Profile_ViewModel.addProfile(
                                                        video?.user_id ?: 0
                                                    )
                                                    constants.Profile_ViewModel.add_Selected_Profile_Id(
                                                        id = video.user_id ?: 0
                                                    )

                                                    // if (view_Details_Data.value?.user_id == AppPreferences.)
                                                    constants.Common_H_ViewModel.toggleshowBABars(false)
                                                    navController.navigate(VideosScreenFlow.Other_Profile_Structure.route)
                                                }
                                            }
                                        }
                                    , verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                )
                                {
                                    Text(
                                        video.name,
                                        color = Color.White,
                                        fontSize = constants.textUnit(16),
                                        fontFamily = constants.fontFamily(1)
                                    )

                                }
                            }
                            , supportingContent = {
                                // Spacer(modifier = Modifier.padding(4.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    // if (currentReel.user_id == AppPreferences.getUserId()){
                                    Text(
                                        "Owner \u2022 ",
                                        color = Color(0xff575757),
                                        fontSize = constants.textUnit(12),
                                        fontFamily = constants.fontFamily(1),
                                        // overflow = TextOverflow.Ellipsis
                                    )
                                    //  }

                                    Text(
                                        "Posted ${getTimeAgo(video.post_property.created_at ?:"")}",
                                        color = Color(0xff575757),
                                        fontSize = constants.textUnit(12),
                                        fontFamily = constants.fontFamily(1),
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                             , trailingContent = {

                                 if (video.user_id != AppPreferences.getUserId()) {
                                     Box(
                                         modifier = Modifier
                                             .width(108.dp)
                                             .height(32.dp)
                                             .clip(RoundedCornerShape(4.dp))
                                             .noRippleClickable {
                                                 player?.pause()
                                                 constants.Reels_ViewModel.enable_Send_Eq_Btm_Sheet()
                                             }
                                             .border(
                                                 1.dp, Brush.linearGradient(
                                                     newPurpleGradientBorder
                                                 ), RoundedCornerShape(4.dp)
                                             )
                                             .background(Brush.verticalGradient(newPurpleGradient)),
                                         contentAlignment = Alignment.Center
                                     ) {
                                         Text(
                                             "Send Enquiry",
                                             color = Color.White,
                                             fontSize = constants.textUnit(14),
                                             fontFamily = constants.fontFamily(0)
                                         )
                                     }
                                 }
                             }
                            , modifier = Modifier.background(Color.Transparent),
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                        )

                        ListItem(
                            overlineContent = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Text(
                                        video.post_property.landCategoryText ?: "",
                                        color = newBlack,
                                        fontSize = constants.textUnit(18),
                                        fontFamily = constants.fontFamily(0)
                                    )
                                    Text(
                                        " \u2022 Rent",
                                        color = newBlack,
                                        fontSize = constants.textUnit(14),
                                        fontFamily = constants.fontFamily(1)
                                    )
                                }

                            }
                            , trailingContent = {
                                if (video.post_property.is_sold == 1) {
                                    Image(
                                        painterResource(R.drawable.soldoutidentifier), "",
                                        modifier = Modifier.padding(start = 16.dp)//.align(Alignment.CenterEnd)
                                    )
                                }
                            }
                            , headlineContent = {
                                Column() {
                                    Row(
                                        modifier = Modifier,
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.locationenquiry), "",
                                            modifier = Modifier.size(16.dp)
                                        )

                                        constants.spacer(4)

                                        Text(
                                            "${video.post_property.city}, ${video.post_property.state}",
                                            color = Color(0xff7E7E7E),
                                            fontSize = constants.textUnit(14),
                                            fontFamily = constants.fontFamily(1)
                                        )
                                    }
                                    constants.spacer(4)
                                }
                            }
                            , supportingContent = {

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                    , verticalAlignment = Alignment.CenterVertically
                                    , horizontalArrangement = Arrangement.SpaceBetween
                                )
                                {
                                    Card(
                                        modifier = Modifier
                                            .height(if (forTab())84.dp else 64.dp)
                                            .weight(4.5f)
                                        // .width(138.dp)
                                        , shape = RoundedCornerShape(6.dp)
                                        , colors = CardDefaults.cardColors(
                                            containerColor = Color(0xffCECECE).copy(.2f)
                                        )
                                        //, elevation = CardDefaults.cardElevation(8.dp)
                                    )
                                    {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(horizontal = 6.dp)
                                            ,verticalArrangement = Arrangement.SpaceEvenly
                                            , horizontalAlignment = Alignment.Start
                                        ) {


                                            BasicText(
                                                text = "\u20B9 ${video.post_property.rent?.ifEmpty { video.post_property.lease_amount }  }",
                                                color =  { newBlack },
                                                style = TextStyle(fontFamily = constants.fontFamily(0)),
                                                autoSize = TextAutoSize.StepBased(minFontSize = 6.sp, constants.textUnit(16), stepSize = 2.sp)
                                            )


                                            Text(
                                                "\u20B9 Amount",
                                                color = Color(0xff323232),
                                                fontSize = constants.textUnit(14),
                                                fontFamily = constants.fontFamily(3)
                                            )
                                        }
                                    }

                                    constants.spacer(4)

                                    Card(
                                        modifier = Modifier
                                            .height(if (forTab())84.dp else 64.dp)
                                            .weight(4.5f)
                                        //.width(138.dp)
                                        , shape = RoundedCornerShape(6.dp)
//                                    , elevation = CardElevation()
                                        //CardDefaults.cardElevation(12.dp)
                                        , colors = CardDefaults.cardColors(
                                            containerColor = Color(0xffCECECE).copy(.2f)
                                        )

                                    )
                                    {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(horizontal = 6.dp)
                                            , verticalArrangement = Arrangement.SpaceEvenly
                                            , horizontalAlignment = Alignment.Start
                                        ) {
                                            Text(
                                                "${video.post_property.carpet_area} ${video.post_property.carpet_area_unit}",
//                                            "200 sq.ft",
                                                color = newBlack,
                                                fontSize = constants.textUnit(16),
                                                fontFamily = constants.fontFamily(0)
                                            )

                                            Row(
                                                verticalAlignment = Alignment.CenterVertically
                                                , horizontalArrangement = Arrangement.Start
                                            ) {
                                                Image(painter = painterResource(R.drawable.reelscarpetareaicon) , "",
                                                    modifier = Modifier.size(12.dp))

                                                constants.spacer(2)

                                                Text(
                                                    "Carpet Area",
                                                    color = Color(0xff323232),
                                                    fontSize = constants.textUnit(14),
                                                    fontFamily = constants.fontFamily(3)
                                                )
                                            }
                                        }
                                    }

                                    constants.spacer(4)

                                    Card(
                                        modifier = Modifier
                                            .height(if (forTab())84.dp else 64.dp)
                                            .weight(1f)
                                            //.width(38.dp)
                                            .noRippleClickable {
                                                ClickHelper.getInstance().clickOnce {
                                                    constants.Reels_ViewModel.clear_view_pro_Details()
                                                    player?.pause()
                                                    constants.Reels_ViewModel.add_View_Property_Details(
                                                        video.toGetReelsData()
                                                    )
                                                    var mode = if (video.post_property.video?.isEmpty() == true) 1 else 0
                                                    constants.Common_H_ViewModel.view_Property_Details_Mode.value =
                                                        mode

                                                    constants.PostProperty_ViewModel.setViewDetailsFlow(
                                                        when {
                                                            constants.PostProperty_ViewModel.viewDetailsFlow.value == ViewDetailsFlow.RENTOUT -> ViewDetailsFlow.RENTOUT
                                                            constants.PostProperty_ViewModel.viewDetailsFlow.value == ViewDetailsFlow.OTHERS -> ViewDetailsFlow.OTHERS
                                                            constants.PostProperty_ViewModel.viewDetailsFlow.value == ViewDetailsFlow.EXPIRY -> ViewDetailsFlow.EXPIRY
                                                            video.user_id == AppPreferences.getUserId() -> ViewDetailsFlow.OWN
                                                            else -> ViewDetailsFlow.OTHERS
                                                        }

                                                    )

                                                    navController.navigate(EnquiriesFlow.ViewPropertyStructure.route)
                                                }
                                            }
                                            .border(1.dp , Color(0xffCECECE) , RoundedCornerShape(4.dp))
                                        , shape = RoundedCornerShape(4.dp)
                                        , colors = CardDefaults.cardColors(
                                            containerColor = Color(0xffEBEBEB)
                                        )
                                    )
                                    {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                            , contentAlignment = Alignment.Center
                                        ) {

                                            if (vd_Loader) {
                                                CircularProgressIndicator(
                                                    modifier = Modifier.size(24.dp),
                                                    color = newBlue
                                                )
                                            } else {
                                                Image(
                                                    painter = painterResource(R.drawable.right_arrow), "",
                                                    colorFilter = ColorFilter.tint(Color(0xff575757)
                                                    )
                                                )
                                            }
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
            }



        }
    }
}

private fun handleLikeClickold(video: PostUser, isLiked: Boolean, isLikeLoading: MutableState<Boolean>) {

    constants.API_Vm.like_Dislike(
        user_id = AppPreferences.getUserId(),
        user_post_id = video.user_post_id,
        status = if (isLiked) 2 else 1
    ) { result ->
        when (result) {
            is API_Result_Handling.Loading -> isLikeLoading.value = true
            is API_Result_Handling.Success -> {
                isLikeLoading.value = false

                // ✅ Toggle Like in all relevant lists
                constants.Reels_ViewModel.toggleLike_Reels(video.user_post_id)
                constants.Enquiry_ViewModel.toggleLike_Reels_Enquiry(video.user_post_id)
                constants.Enquiry_ViewModel.toggleLike_Reels_SelfEnquiry(video.user_post_id)

                if (isLiked) {
                    constants.Enquiry_ViewModel.decreaseLikeCount_Reels_Enquiry(video.user_post_id)
                    constants.Enquiry_ViewModel.decreaseLikeCount_Reels_SelfEnquiry(video.user_post_id)
                    constants.Reels_ViewModel.decreaseLikeCount_Reels(video.user_post_id)
                } else {
                    constants.Enquiry_ViewModel.increaseLikeCount_SelfReels_Enquiry(video.user_post_id)
                    constants.Enquiry_ViewModel.increaseLikeCount_Reels_Enquiry(video.user_post_id)
                    constants.Reels_ViewModel.increaseLikeCount_Reels(video.user_post_id)
                }
            }

            is API_Result_Handling.Error -> {
                toast("Something Went Wrong")
                isLikeLoading.value = false
            }

            else -> isLikeLoading.value = false
        }
    }
}

private fun handleLikeClick(
    postId: Int,
    isLiked: Boolean,
    isLikeLoading: MutableState<Boolean>
) {
    constants.API_Vm.like_Dislike(
        user_id = AppPreferences.getUserId(),
        user_post_id = postId,
        status = if (isLiked) 2 else 1
    ) { result ->
        when (result) {
            is API_Result_Handling.Loading -> isLikeLoading.value = true

            is API_Result_Handling.Success -> {
                isLikeLoading.value = false
                constants.Enquiry_ViewModel.atomicupdateLikeleadsSelf(postId, isLiked)
            }

            else -> isLikeLoading.value = false
        }
    }
}


private fun handleCommentClick() {
    constants.API_Vm.isLoading_MComments = false
    constants.API_Vm.totalPages_MComments = 1
    constants.Reels_ViewModel.clear_MCommentList()
    constants.Reels_ViewModel.clear_Reply_Map()
    constants.Common_H_ViewModel.enable_Cmt_btm_Sheet()
}

private fun handleSaveClick(postId: Int, isSaved: Boolean, isSaveLoading: MutableState<Boolean>) {
    constants.API_Vm.put_save_UnSave_Property(
        user_id = AppPreferences.getUserId(),
        user_post_id = postId,
        status = if (isSaved) 2 else 1
    ) { result ->
        when (result) {
            is API_Result_Handling.Loading -> isSaveLoading.value = true
            is API_Result_Handling.Success -> {
                isSaveLoading.value = false

                constants.Reels_ViewModel.toggleSave_Reels(postId)
                constants.Enquiry_ViewModel.atomicupdateSaveleadsSelf(postId, isSaved)
//                constants.Enquiry_ViewModel.toggleSave_Reels_Enquiry(video.user_post_id)
            }

            is API_Result_Handling.Error -> {
                toast("Something Went Wrong")
                isSaveLoading.value = false
            }

            else -> isSaveLoading.value = false
        }
    }
}




@OptIn(FlowPreview::class)
@Composable
fun ReelsViewolddddd(
    navController: NavHostController,
    viewModel: Common_H_ViewModel
    ,onLogout: () -> Unit
) {


   var showTABars = viewModel.showTABars.collectAsState()
   var showBABars = viewModel.showBABars.collectAsState()
    val context = LocalContext.current
    val density = LocalDensity.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var from_DLP_State = from_DeepLink_Property.collectAsStateWithLifecycle()

    val videos by constants.Reels_ViewModel.videos.collectAsState()
    var deactivated by remember { mutableStateOf(false) }
    val playerManager = remember { VideoPlayerManager(context) }

    val total = videos.size
    //val pagerState = rememberPagerState(initialPage = 0, pageCount = { if (total > 0) total else 1 })

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { videos.size.coerceAtLeast(1) } // dynamic page count
    )

    var previousPage by remember { mutableStateOf(-1) }

    val isBuffering = remember { mutableStateMapOf<Int, Boolean>() }
    val isPrepared = remember { mutableStateMapOf<Int, Boolean>() }

    val isLoading = constants.API_Vm.isLoading_Reels
    val nxtPage = constants.API_Vm.nextPage_Reels
    val currentPage = constants.API_Vm.currentPage_Reels
    val totalPages = constants.API_Vm.totalPages_Reels
    val error = constants.API_Vm.errorMessage_Reels
    val result = constants.API_Vm.result_Reels

    val send_Eq_State = constants.Reels_ViewModel.send_Enquiry_Btm_Sheet.collectAsState()

    var isLike_Loading = remember { mutableStateOf(false) }
    var isSave_Loading = remember { mutableStateOf(false) }

    val reelsBTMSheetState = constants.Common_H_ViewModel.reelsBtm_sheet.collectAsState()
    val reelsBTMSOptions = constants.Reels_ViewModel.reelsBTMSOptions.collectAsState()

    val cmt_btm_Sheet = constants.Common_H_ViewModel.comment_btm_Sheet.collectAsState()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var report_BS = remember { mutableStateOf(false) }
    val report_Options = constants.Profile_ViewModel.profile_Report_Options.collectAsState()
    val report_success = constants.Profile_ViewModel.report_Submit_Success.collectAsState()
    val sense_Liked = constants.Reels_ViewModel.sense_isLiked.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val network = rememberNetworkStatus()

    var retry by remember { mutableStateOf(0) }

    if (network.value == NetworkStatus.Online) {
        LaunchedEffect(Unit , retry) {
            constants.API_Vm.load_Reels(AppPreferences.getUserId(), AppPreferences.get_Post_Id().toString() ,1)
        }

        // Pagination / load more
        LaunchedEffect(pagerState.currentPage, currentPage, isLoading, totalPages, nxtPage) {
            println("PAGINATION NEW REELS  ${pagerState.currentPage}-- $currentPage  $isLoading $totalPages $nxtPage")
            snapshotFlow { pagerState.currentPage }
                .debounce(300)
                .collect { page ->
                    val loadMoreThreshold = 3
                    val shouldLoadMore =
                        page >= pagerState.pageCount - loadMoreThreshold &&
                                !isLoading &&
                                currentPage < totalPages &&
                                nxtPage > 0

                    println("PAGINATION NEW REELS22 ${constants.API_Vm.isLoading_Reels} $page  $shouldLoadMore  ${pagerState.pageCount} ${pagerState.currentPage}-- $currentPage  $isLoading $totalPages $nxtPage")

                    if (shouldLoadMore) {
                        println("PAGINATION NEW REELS 333$shouldLoadMore ${pagerState.currentPage}-- $currentPage  $isLoading $totalPages $nxtPage")
                        constants.API_Vm.load_Reels(AppPreferences.getUserId(), AppPreferences.get_Post_Id().toString(),nxtPage)
                    }
                }
        }

        // Page change handling
        LaunchedEffect(pagerState.currentPage, videos.size) {
            if (videos.isEmpty()) return@LaunchedEffect
            val currentIndex = pagerState.currentPage.coerceIn(0, videos.lastIndex)
            if (currentIndex == previousPage) return@LaunchedEffect

            val currentVideo = videos[currentIndex]

            // Pause all other videos
            playerManager.pauseAllExcept(currentIndex)

            // Initialize current player if video exists
            if (!currentVideo.post_property.video.isNullOrEmpty()) {
                playerManager.getOrCreatePlayer(
                    currentIndex,
                    currentVideo.user_post_id,
                    currentVideo.post_property.video.firstOrNull()?.url ?: ""
                )
                playerManager.playVideo(currentIndex)
            }

            val isGoingUp = currentIndex < previousPage
            when {
                currentIndex == 0 -> {
                    // First video – show bars
                    viewModel.toggleshowTABars(true)
                    viewModel.toggleshowBABars(true)
                }
                isGoingUp -> {
                    // Swiping up – show bars
                    viewModel.toggleshowTABars(true)
                    viewModel.toggleshowBABars(true)
                }
                else -> {
                    // Scrolling down – hide bars
                    viewModel.toggleshowTABars(false)
                    viewModel.toggleshowBABars(false)
                }
            }

            previousPage = currentIndex
        }

        // Lifecycle management
        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (videos.isEmpty()) return@LifecycleEventObserver
                val currentIndex = pagerState.currentPage.coerceIn(0, videos.lastIndex)
                when (event) {
                    Lifecycle.Event.ON_PAUSE -> playerManager.pauseVideo(currentIndex)
                    Lifecycle.Event.ON_RESUME -> {
                        val currentVideo = videos[currentIndex]
                        if (!currentVideo.post_property.video.isNullOrEmpty())
                            playerManager.playVideo(currentIndex)
                    }
                    Lifecycle.Event.ON_DESTROY -> playerManager.releaseAll()
                    else -> Unit
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose { lifecycleOwner.lifecycle.removeObserver(observer); playerManager.releaseAll() }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            playerManager.releaseAll()
        }
    }

    when {

        result == "2" -> {
            deactivated = true
        }

        // Offline
        !isLoading && network.value == NetworkStatus.Offline -> {
            toast(constants.activity.getString(R.string.no_Internet))
        }


        // Loader state
        isLoading && videos.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize().background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }


        !error.isNullOrEmpty() -> {
            playerManager.releaseAll()
            viewModel.toggleshowBABars(false)
            viewModel.toggleshowTABars(false)
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

        videos.isEmpty() && !isLoading -> {
            // No videos after loading


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Column (Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Image(painter = painterResource(R.drawable.errorvideorento) , "",
                        modifier = Modifier.size(200.dp))
                    constants.spacer(8)
                    Text("No videos to display", color = Color.White)
                }
            }
        }


        videos.isNotEmpty() -> {
                VerticalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize(),
                    key = { page -> videos.getOrNull(page)?.user_post_id ?: page }
                )
                { page ->

                    val item = videos.getOrNull(page) ?: return@VerticalPager
                    val hasVideo = item.post_property.video?.isNotEmpty()
                    println("HAS VIDEOO N111__ ${ item.post_property.video}")

                    // Create player only if video exists
                    val player = if (hasVideo == true) {
                        remember(page) {
                            playerManager.getOrCreatePlayer(page, item.user_post_id, item.post_property.video?.firstOrNull()?.url ?: "")
                        }
                    } else null

                    Box(modifier = Modifier.fillMaxSize()) {

                        println("HAS VIDEOO N4444__ ${ item.post_property.video} -- ${item.post_property.images}")


                        if (hasVideo == true) {
                            // val player = playerManager.getOrCreatePlayer(page, item.user_post_id, item.post_property.video)

                            key(page, item.user_post_id) {
                                if (player != null) {
                                    PlayerSurface(
                                        player = player,
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .fillMaxWidth()
                                            .aspectRatio(9f / 16f)
                                            .pointerInput(page, item.user_post_id) {
                                                detectTapGestures(
                                                    onTap = {
                                                        if (player?.isPlaying == true) playerManager.pauseVideo(
                                                            page
                                                        ) else playerManager.playVideo(page)
                                                    },
                                                    onDoubleTap = {
                                                        constants.API_Vm.like_Dislike(
                                                            user_id = AppPreferences.getUserId(),
                                                            user_post_id = item.user_post_id,
                                                            status = if (item.is_liked == 1) 2 else 1
                                                        ) { apiResult ->
                                                            if (apiResult is API_Result_Handling.Success) {
                                                                val alreadyLiked =
                                                                    constants.Reels_ViewModel.videos.value
                                                                        .firstOrNull { it.user_post_id == item.user_post_id }?.is_liked == 1
                                                                if (alreadyLiked) constants.Reels_ViewModel.decreaseLikeCount_Reels(
                                                                    item.user_post_id
                                                                )
                                                                else constants.Reels_ViewModel.increaseLikeCount_Reels(
                                                                    item.user_post_id
                                                                )
                                                                constants.Reels_ViewModel.toggleLike_Reels(
                                                                    item.user_post_id
                                                                )
                                                            }
                                                        }
                                                    }
                                                )
                                            }
                                    )
                                }
                            }

                            // Track buffering
                            DisposableEffect(player, page) {
                                val listener = object : Player.Listener {
                                    override fun onPlaybackStateChanged(playbackState: Int) {
                                        when (playbackState) {
                                            Player.STATE_BUFFERING -> { isBuffering[page] = true; isPrepared[page] = false }
                                            Player.STATE_READY -> { isBuffering[page] = false; isPrepared[page] = true }
                                            Player.STATE_ENDED -> { player?.seekTo(0); if (page == pagerState.currentPage) player?.playWhenReady = true }
                                            Player.STATE_IDLE -> { isBuffering[page] = false; isPrepared[page] = false }
                                        }
                                    }
                                }
                                player?.addListener(listener)
                                onDispose { player?.removeListener(listener); isBuffering.remove(page); isPrepared.remove(page) }
                            }

                            if (from_DLP_State.value) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                )
                                {
                                    Backer(
                                        modifier = Modifier, onBackClick = {
                                            set_FDLP_State(false)
                                        }
                                    )
                                }
                            }


                            Reels_Options(
                                modifier = Modifier.align(Alignment.BottomCenter),
                                showBABars,
                                videos,
                                page,
                                navController,
                                isLike_Loading,
                                isSave_Loading,
                                viewModel
                            )



                        }
                        else if (item.post_property.images?.isNotEmpty() == true) {
                            val animationType = SlideshowAnimation.values()[page % SlideshowAnimation.values().size]
                            SingleSlideshowReel2(
                                // imagesList = item.post_property.image_urls,
                                //animationType = animationType,
                                item = item,
                                page = page,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(400.dp)
                                    .align(Alignment.Center),
                                videos = videos,
                                navController = navController,
                                pagerState = pagerState,

                                isLike_Loading = isLike_Loading,
                                isSave_Loading = isSave_Loading,
                                intervalMillis = 1000L,
                                viewModel
                            )
                        }
                        else {
                            println("HAS VIDEOO N333__ ${ item.post_property.video} -- ${item.post_property.images}")
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black),
                                contentAlignment = Alignment.Center
                            ) {
                               // Text("No media available", color = Color.White)
                                Image(painterResource(R.drawable.emptypostsrento) ,"")

                                Reels_Options(
                                    modifier = Modifier.align(Alignment.BottomCenter),
                                    showBABars,
                                    videos,
                                    page,
                                    navController,
                                    isLike_Loading,
                                    isSave_Loading,
                                    viewModel
                                )


                            }
                        }


                    }
                }
            }


    }


    if (videos.isNotEmpty() && pagerState.currentPage < videos.size) {
        Enquiry_Form_Btm_Sheet_Structure(send_Eq_State.value, videos[pagerState.currentPage])
    }

    // Reels Bottom Sheet
    if (reelsBTMSheetState.value) {
        ModalBottomSheet(
            onDismissRequest = { constants.Common_H_ViewModel.toggleReelsBTMSheet(false) }
            , containerColor = newWhite
        ) {
            val data =
//                if (videos[pagerState.currentPage].user_id == AppPreferences.getUserId())
//                reelsBTMSOptions.value.dropLast(1)
//            else
                reelsBTMSOptions.value.takeLast(2)

            data.forEachIndexed { index, Options ->


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .noRippleClickable{
                            when(index) {
                                0 -> {
                                    Options.onClick()

                                }
                                1 -> {
                                    if (videos[pagerState.currentPage].post_property.is_report != 1) {
                                        report_BS.value = true
                                        constants.Profile_ViewModel.toggle_ProfileReport_Options(
                                            -1
                                        )
                                    } else {
                                        constants.Common_H_ViewModel.toggleReelsBTMSheet(false)
                                        GlobalSnackbar.show("Post already reported")
                                    }
                                }
                            }


                            //Options.onClick()
//                            if (index == data.size - 1) {
//
//                                }
//                            }
                                   },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(0xffF7F0DC)),
                        contentAlignment = Alignment.Center
                    ) {
                        SubcomposeAsyncImage(model = Options.icon, contentDescription = "", modifier = Modifier.size(16.dp))
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(Options.title, color = newBlack, fontSize = constants.textUnit(14))
                }
                Spacer(modifier = Modifier.padding(16.dp))
            }
        }
    }

    // Comment Sheet
    if (cmt_btm_Sheet.value) {
        ModalBottomSheet(
            onDismissRequest = { constants.Common_H_ViewModel.dismiss_Cmt_btm_Sheet() },
            sheetState = bottomSheetState,
            containerColor = newWhite,
            sheetGesturesEnabled = false
        ) {
            Comment_Structure(videos, pagerState.currentPage, navController ,viewModel)
        }
    }


    // Report Sheet
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
                    else {
                        Column(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                            , verticalArrangement = Arrangement.SpaceEvenly
                            , horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            SubcomposeAsyncImage(
                                model = R.drawable.profile_report_submit_success
                                ,""
                                , modifier = Modifier
                                    .size(150.dp)
                            )


                            Text(
                                text = "Submitted Successfully",
                                color = newBlack,
                                fontSize = constants.textUnit(18),
                                fontFamily = constants.fontFamily(0)
                            )


                            Text(
                                text = "Thank you for bringing this to our attention.",
                                color = newBlack,
                                fontSize = constants.textUnit(12),
                                fontFamily = constants.fontFamily(3)
                            )

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
                                        .noRippleClickable{

                                            if (network.value == NetworkStatus.Online) {
                                                if (videos[pagerState.currentPage].post_property.is_report != 1) {

                                                    if (user_Manual_report_String.value.isEmpty()) {
                                                        user_Manual_report_String.value =
                                                            constants.Profile_ViewModel.getSelectedProfileReportOptionDescription()
                                                                ?: ""
                                                    }
                                                    constants.API_Vm.put_Report_All(
                                                        user_id = AppPreferences.getUserId(),
                                                        user_post_id = videos[pagerState.currentPage].user_post_id.toString(),
                                                        //AppPreferences.get_Post_Id(),
                                                        receiver_id = videos[pagerState.currentPage].user_id.toString(),
                                                        comment_id = "",
                                                        report_sentence_id = (constants.Profile_ViewModel.getSelectedProfileReportOptionId()
                                                            ?.plus(1)) ?: 0,
                                                        report_sentence = user_Manual_report_String.value,
                                                        status = 2,
                                                    )
                                                    { apiResultHandling ->

                                                        when (apiResultHandling) {
                                                            is API_Result_Handling.Loading -> {
                                                                // loading
                                                                //constants.PostProperty_ViewModel.change_Status_PFs(true)
                                                            }

                                                            is API_Result_Handling.Error -> {
                                                                // fail
                                                                //constants.PostProperty_ViewModel.change_Status_PFs(false)
                                                            }

                                                            is API_Result_Handling.Success -> {


                                                                constants.Profile_ViewModel.toggleReportSubmissionSuccess()
                                                                constants.Reels_ViewModel.toggleLike_Report(
                                                                    videos[pagerState.currentPage].user_post_id
                                                                )
                                                                //constants.Reels_ViewModel.deleteVideoById_Profile_Post_Reels(videos[pagerState.currentPage].user_id)
                                                                // success
                                                                //constants.PostProperty_ViewModel.change_Status_PFs(false)
                                                            }

                                                            is API_Result_Handling.NoData -> {
                                                                // no data
                                                                //constants.PostProperty_ViewModel.change_Status_PFs(false)
                                                            }

                                                            is API_Result_Handling.Deactivated -> {
                                                                /// resultCallback(5)
                                                            }
                                                        }
                                                    }
                                                } else {

                                                    //constants.Common_H_ViewModel.toggleReelsBTMSheet(false)
                                                    scope.launch {
                                                        snackbarHostState.showSnackbar(" Post Already Reported")

                                                        report_BS.value = false
                                                    }
                                                    //SimpleSnackbar(" Post Already Reported")

                                                }
                                            }
                                            else {
                                                toast(constants.activity.getString(R.string.no_Internet))
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


    var app_Exit by remember { mutableStateOf(false) }

    BackHandler {
        ClickHelper.getInstance().clickOnce {
            when {
                report_BS.value -> !report_BS.value
                cmt_btm_Sheet.value -> !cmt_btm_Sheet.value
                reelsBTMSheetState.value -> !reelsBTMSheetState.value
                send_Eq_State.value -> constants.Reels_ViewModel.dismiss_Send_Eq_Btm_Sheet()
                !report_BS.value || !cmt_btm_Sheet.value || !reelsBTMSheetState.value || send_Eq_State.value -> {
                    viewModel.toggleshowBABars(false)
                    viewModel.toggleshowTABars(false)
                    app_Exit = true
                }
            }

        }
    }

    Common_Popup(
        visible = app_Exit,
        modifier = Modifier.background(newLightBlue),
        image = "",
        icon = R.drawable.one_star_image,
        userName = "",
        content = {
            Column (
                modifier = Modifier.wrapContentSize()
                , verticalArrangement = Arrangement.Center
                , horizontalAlignment = Alignment.CenterHorizontally
            ){
                constants.spacer(2)
                Text("Are your sure. You Want to Exit ?" ,color = newBlack,
                    fontSize = constants.textUnit(12),
                    fontFamily = constants.fontFamily(3)
                    , textAlign = TextAlign.Center
                   , lineHeight = 24.sp
                )

                constants.spacer(2)
                Spacer(modifier = Modifier.padding(8.dp))

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                    , verticalAlignment = Alignment.CenterVertically
                    , horizontalArrangement = Arrangement.SpaceEvenly
                )
                {
                    Box(
                        modifier = Modifier
                            .height(48.dp)
                            .width(108.dp)
                            .background(newLightGray)
                            .noRippleClickable{
                                ClickHelper.getInstance().clickOnce {
                                   if (ClickGuard.canClick()) {
                                       viewModel.toggleshowBABars(true)
                                       viewModel.toggleshowTABars(true)
                                       app_Exit = false
                                   }
                                }
                            }
                        , contentAlignment = Alignment.Center
                    ){
                        Text("Cancel" , color = newBlack)
                    }
                    Box(
                        modifier = Modifier
                            .height(48.dp)
                            .width(108.dp)
                            .background(newBlue)
                            .noRippleClickable{
                                ClickHelper.getInstance().clickOnce {
                                    if (ClickGuard.canClick()) {
                                        constants.activity.finishAffinity()
                                        constants.activity.finish()
                                        viewModel.toggleshowBABars(true)
                                        viewModel.toggleshowTABars(true)
                                        app_Exit = false
                                    }
                                }
                            }
                        , contentAlignment = Alignment.Center
                    ){
                        Text("Exit" , color = Color.White)
                    }
                }

                constants.spacer(2)
                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    )
    Common_Popup(
        visible = deactivated,
        modifier = Modifier.background(Color(0xffFCEDEC)),
        image = "",
        icon = R.drawable.deactivated,
        userName = "",
        content = {
            Column (
                modifier = Modifier
                , verticalArrangement = Arrangement.spacedBy(8.dp)
                , horizontalAlignment = Alignment.CenterHorizontally
            ){
                constants.spacer(2)
                Text("Account Restricted"
                    , color = Color.Black
                    , fontSize = constants.textUnit(16)
                    , fontFamily = constants.fontFamily(0)
                )
                constants.spacer(2)

                Text("Your account has been reported multiple times for violating our community standards. Your account has been temporarily restricted. You can appeal this decision if you believe it was a mistake."
                    , color = Color(0xff484848)
                    , fontSize = constants.textUnit(12)
                    , fontFamily = constants.fontFamily(3)
                    , modifier = Modifier.padding(horizontal = 16.dp)
                   , lineHeight = 24.sp

                )

                Box(
                    modifier = Modifier
                        .height(32.dp)
                        .fillMaxWidth(.9f)
                        .noRippleClickable{
                            constants.Profile_ViewModel.set_open_settings()
                            constants.Profile_ViewModel.onSet_Settings_Click(0)

                            constants.Common_H_ViewModel.changeStatus(false)

                            constants.Common_H_ViewModel.toggleshowBABars(true)
                            constants.Start_Up_ViewModel.updateLoginState(0)
                            constants.Profile_ViewModel.onSet_Settings_Click(-1)
                            constants.Start_Up_ViewModel.phoneNumber = ""
                            constants.Start_Up_ViewModel.countryCode = "+91"
                            constants.Start_Up_ViewModel.userName = ""
                            constants.Start_Up_ViewModel.otp = ""


                            constants.Profile_ViewModel.dismiss_Logout_PP()
                            onLogout()
                        }
                    , contentAlignment = Alignment.Center
                ){
                    Text("Disagree with Decision"
                        , color = Color.White
                        , fontSize = constants.textUnit(14)
                        , fontFamily = constants.fontFamily(0)
                    )
                }

                Text("Okay"
                    , color = Color(0xff484848)
                    , fontSize = constants.textUnit(14)
                    , fontFamily = constants.fontFamily(0)
                    , modifier = Modifier
                        .noRippleClickable{
                            AppPreferences.clearAll()
                            constants.Profile_ViewModel.set_open_settings()
                            constants.Profile_ViewModel.onSet_Settings_Click(0)

                            constants.Common_H_ViewModel.changeStatus(false)

                            constants.Common_H_ViewModel.toggleshowBABars(true)
                            constants.Start_Up_ViewModel.updateLoginState(0)
                            constants.Profile_ViewModel.onSet_Settings_Click(-1)
                            constants.Start_Up_ViewModel.phoneNumber = ""
                            constants.Start_Up_ViewModel.countryCode = "+91"
                            constants.Start_Up_ViewModel.userName = ""
                            constants.Start_Up_ViewModel.otp = ""


                            constants.Profile_ViewModel.dismiss_Logout_PP()
                            onLogout()
                        }
                )

                constants.spacer(2)

                Spacer(modifier = Modifier.padding(8.dp))
            }
        }
    )
}







@Composable
fun SingleSlideshowReel2(
    item: Get_Reels_Data,
    page: Int,
    modifier: Modifier,
    videos: List<Get_Reels_Data>,
    navController: NavHostController,
    pagerState: PagerState,
    isLike_Loading: MutableState<Boolean>,
    isSave_Loading: MutableState<Boolean>,
    intervalMillis: Long = 1000L,
    viewmodel: Common_H_ViewModel
)
{



    val showBABars = viewmodel.showBABars.collectAsState()

    var currentIndex by remember { mutableStateOf(0) }
    var isPlaying by remember { mutableStateOf(true) }

    // Animations
    val alpha = remember { Animatable(1f) }
    val scale = remember { Animatable(1f) }

    // Slideshow animation loop
    LaunchedEffect(isPlaying) {
        while (isPlaying && item.post_property.images?.isNotEmpty() == true) {
            delay(intervalMillis)
            alpha.animateTo(0f, tween(500))
            currentIndex = (currentIndex + 1) % (item.post_property?.images?.size ?: 0)
            alpha.animateTo(1f, tween(500))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .noRippleClickable{ isPlaying = !isPlaying }, // tap to play/pause
        contentAlignment = Alignment.Center
    ) {

        // Show current image
        if (item.post_property.images?.isNotEmpty() == true) {
            SubcomposeAsyncImage(
                model = item.post_property.images[currentIndex].url,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.9f)
                    .graphicsLayer(
                        alpha = alpha.value,
                        scaleX = scale.value,
                        scaleY = scale.value
                    )
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
                        //.padding(8.dp)
                        , contentAlignment = Alignment.Center
                    ){
                       Image(painterResource(R.drawable.emptypostsrento) , "",
                           modifier = Modifier.size(200.dp))
                    }
                } else {
                    SubcomposeAsyncImageContent()
                }
            }
        }
        else {
            Box(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxSize()
                , contentAlignment = Alignment.Center
            ) {
                //Text("No media available", color = Color.White)
                Image(painterResource(R.drawable.emptypostsrento) ,"")
            }
        }



        // Reels options overlay (like, comment, save, bottom sheet)
        Reels_Options(
            modifier = Modifier.align(Alignment.BottomCenter),
            showBABars = showBABars,
            videos = videos,
            page = page,
            navController = navController,
            isLike_Loading = isLike_Loading,
            isSave_Loading = isSave_Loading,
            viewModel = viewmodel
        )

        // Play icon overlay when paused
        if (!isPlaying) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.play_arrow),
                    contentDescription = "Play",
                    tint = Color.White
                )
            }
        }
    }
}



@Composable
fun SingleSlideshowReel2_Search_Flow23456789(
    item: Get_Reels_Data,
    page: Int,
    modifier: Modifier,
    videos: List<Get_Reels_Data>,
    navController: NavHostController,
    pagerState: PagerState,
    isLike_Loading: MutableState<Boolean>,
    isSave_Loading: MutableState<Boolean>,
    intervalMillis: Long = 1000L,
    viewModel: Common_H_ViewModel
)
{



    val showBABars = viewModel.showBABars.collectAsState()

    var currentIndex by remember { mutableStateOf(0) }
    var isPlaying by remember { mutableStateOf(true) }

    // Animations
    val alpha = remember { Animatable(1f) }
    val scale = remember { Animatable(1f) }


    // Comment bottom sheet state
    val cmt_btm_Sheet = viewModel.comment_btm_Sheet.collectAsState()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    val reelsBTMSheetState = viewModel.reelsBtm_sheet.collectAsState()
    val reelsBTMSOptions = constants.Reels_ViewModel.reelsBTMSOptions.collectAsState()


    var mark_as_Sold = remember { mutableStateOf(false) }


    var isLike_Loading = remember { mutableStateOf(false) }
    var isSave_Loading = remember { mutableStateOf(false) }

    var report_BS = remember { mutableStateOf(false) }
    val report_Options = constants.Profile_ViewModel.profile_Report_Options.collectAsState()
    val report_success = constants.Profile_ViewModel.report_Submit_Success.collectAsState()

    val send_Eq_State = constants.Reels_ViewModel.send_Enquiry_Btm_Sheet.collectAsState()


    // Slideshow animation loop
    LaunchedEffect(isPlaying) {
        while (isPlaying && item.post_property.images?.isNotEmpty() == true) {
            delay(intervalMillis)
            alpha.animateTo(0f, tween(500))
            currentIndex = (currentIndex + 1) % (item.post_property?.images?.size ?:0)
            alpha.animateTo(1f, tween(500))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .noRippleClickable{ isPlaying = !isPlaying }, // tap to play/pause
        contentAlignment = Alignment.Center
    )
    {

        // Show current image
        if (item.post_property.images?.isNotEmpty() == true) {
//            AsyncImage(
//                model = item.post_property.image_urls[currentIndex],
//                contentDescription = null,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .graphicsLayer(
//                        alpha = alpha.value,
//                        scaleX = scale.value,
//                        scaleY = scale.value
//                    ),
//                contentScale = ContentScale.Crop
//            )

            SubcomposeAsyncImage(
                model = item.post_property.images[currentIndex].url,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(
                        alpha = alpha.value,
                        scaleX = scale.value,
                        scaleY = scale.value
                    )
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
                        //.padding(8.dp)
                        , contentAlignment = Alignment.Center
                    ){
                        Image(painterResource(R.drawable.emptypostsrento) , "",
                            modifier = Modifier.size(200.dp))
                    }
                } else {
                    SubcomposeAsyncImageContent()
                }
            }
        }
        else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                //Text("No media available", color = Color.White)
                Image(painterResource(R.drawable.emptypostsrento) ,"")
            }
        }



        Reels_Options_Search_Flow(
            modifier = Modifier.align(Alignment.BottomCenter),
            videos,
            page,
            navController,
            pagerState,
            player = null,
            duration =  remember { mutableStateOf(1L) },
            position = remember { mutableStateOf(0L) },
            isLike_Loading,
            isSave_Loading,
            viewModel
        )

        // Play icon overlay when paused
        if (!isPlaying) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.play_arrow),
                    contentDescription = "Play",
                    tint = Color.White
                )
            }
        }
    }


    if (reelsBTMSheetState.value) {
       // playerManager.pauseVideo(pagerState.currentPage)
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.toggleReelsBTMSheet(false)
            }
            , containerColor = newWhite
        )
        {
//            val data = if (videos[pagerState.currentPage].user_id == AppPreferences.getUserId()) {
//
//                // Show full list
//                reelsBTMSOptions.value.dropLast(1)
//            } else {
//                // Show only last 2 items, but avoid crash if list has < 2 items
//                reelsBTMSOptions.value.takeLast(2)
//            }

            val data = when {
                videos[pagerState.currentPage].user_id == AppPreferences.getUserId() -> {
                    // Owner: Show full list except the last item
                    reelsBTMSOptions.value.dropLast(1)
                }
                constants.Profile_ViewModel.from_SoldOuts.value == true -> {
                    // Third scenario: Show only Repost and Delete
                    reelsBTMSOptions.value.filter { it.title == "Repost Property" || it.title == "Delete Property" }
                }
                else -> {
                    // Not owner: Show only last 2 items
                    reelsBTMSOptions.value.takeLast(2)
                }
            }


            data.forEachIndexed { index, Options ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(newWhite)
                        .padding(horizontal = 16.dp)
                        .noRippleClickable{
//                            if (index == data.size - 1) {
//                                // report_BS.value = true
//                            } else if (Options.title == "Mark as Sold") {
//                                mark_as_Sold.value = true
//                            }
//                            when(index) {
//                                0 -> {
//
//                                }
//                                1 -> {
//
//                                }
//                            }
                            when {
                                Options.title == "Repost Property" -> {}
                                Options.title == "Edit Property" -> {}
                                Options.title == "Delete Property" -> {}
                                Options.title == "Mark as Sold" -> {
                                    mark_as_Sold.value = true
                                }
                                Options.title == "Report" -> {
                                    if (videos[pagerState.currentPage].post_property.is_report != 1) {
                                        report_BS.value = true
                                    } else {
                                        constants.Common_H_ViewModel.toggleReelsBTMSheet(false)
                                        GlobalSnackbar.show("Post already reported")
                                    }
                                }
                                Options.title == "Share" -> {
                                    Options.onClick()
                                }
                            }

                        }
                    , verticalAlignment = Alignment.CenterVertically
                    , horizontalArrangement = Arrangement.Start
                ) {

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(0xffF7F0DC))
                        , contentAlignment = Alignment.Center
                    ){
                        SubcomposeAsyncImage(
                            model = Options.icon
                            ,""
                            , modifier = Modifier
                                .size(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.padding(8.dp))

                    Text(Options.title , color = newBlack , fontSize = constants.textUnit(14))

                }
                Spacer(modifier = Modifier.padding(16.dp))
            }

        }
    }

    var network = rememberNetworkStatus()
    // Report Sheet
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
                    else {
                        Column(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                            , verticalArrangement = Arrangement.SpaceEvenly
                            , horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            SubcomposeAsyncImage(
                                model = R.drawable.profile_report_submit_success
                                ,""
                                , modifier = Modifier
                                    .size(150.dp)
                            )


                            Text(
                                text = "Submitted Successfully",
                                color = newBlack,
                                fontSize = constants.textUnit(18),
                                fontFamily = constants.fontFamily(0)
                            )


                            Text(
                                text = "Thank you for bringing this to our attention.",
                                color = newBlack,
                                fontSize = constants.textUnit(12),
                                fontFamily = constants.fontFamily(3)
                            )

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
                                        .noRippleClickable{
                                            ClickHelper.getInstance().clickOnce {
                                                if (network.value == NetworkStatus.Online) {
                                                    if (videos[pagerState.currentPage].post_property.is_report != 1) {

                                                        if (user_Manual_report_String.value.isEmpty()) {
                                                            user_Manual_report_String.value =
                                                                constants.Profile_ViewModel.getSelectedProfileReportOptionDescription()
                                                                    ?: ""
                                                        }
                                                        constants.API_Vm.put_Report_All(
                                                            user_id = AppPreferences.getUserId(),
                                                            user_post_id = videos[pagerState.currentPage].user_post_id.toString(),
                                                            //AppPreferences.get_Post_Id(),
                                                            receiver_id = videos[pagerState.currentPage].user_id.toString(),
                                                            comment_id = "",
                                                            report_sentence_id = (constants.Profile_ViewModel.getSelectedProfileReportOptionId()
                                                                ?.plus(1)) ?: 0,
                                                            report_sentence = user_Manual_report_String.value,
                                                            status = 2,
                                                        )
                                                        { apiResultHandling ->

                                                            when (apiResultHandling) {
                                                                is API_Result_Handling.Loading -> {
                                                                    // loading
                                                                    //constants.PostProperty_ViewModel.change_Status_PFs(true)
                                                                }

                                                                is API_Result_Handling.Error -> {
                                                                    // fail
                                                                    //constants.PostProperty_ViewModel.change_Status_PFs(false)
                                                                }

                                                                is API_Result_Handling.Success -> {


                                                                    constants.Profile_ViewModel.toggleReportSubmissionSuccess()
                                                                    constants.Reels_ViewModel.toggleLike_Report(
                                                                        videos[pagerState.currentPage].user_post_id
                                                                    )
                                                                    //constants.Reels_ViewModel.deleteVideoById_Profile_Post_Reels(videos[pagerState.currentPage].user_id)
                                                                    // success
                                                                    //constants.PostProperty_ViewModel.change_Status_PFs(false)
                                                                }

                                                                is API_Result_Handling.NoData -> {
                                                                    // no data
                                                                    //constants.PostProperty_ViewModel.change_Status_PFs(false)
                                                                }

                                                                is API_Result_Handling.Deactivated -> {
                                                                    /// resultCallback(5)
                                                                }
                                                            }
                                                        }
                                                    } else {

                                                        //constants.Common_H_ViewModel.toggleReelsBTMSheet(false)
//                                                scope.launch {
                                                        GlobalSnackbar.show(" Post Already Reported")
//
//                                                    report_BS.value = false
//                                                }
                                                        //SimpleSnackbar(" Post Already Reported")

                                                    }
                                                }
                                                else {
                                                    toast(constants.activity.getString(R.string.no_Internet))
                                                }
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

    if (videos.isNotEmpty()) {
        Enquiry_Form_Btm_Sheet_Structure(send_Eq_State.value, videos[pagerState.currentPage]  )
    }

    // Comment sheet
    if (cmt_btm_Sheet.value) {
        //playerManager.pauseVideo(pagerState.currentPage)
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.dismiss_Cmt_btm_Sheet()
            },
            sheetState = bottomSheetState,
            containerColor = newWhite,
            sheetGesturesEnabled = false
        ) {
            Comment_Structure(videos, pagerState.currentPage, navController, viewModel)
        }
    }

    if (mark_as_Sold.value == true) {
        //playerManager.pauseVideo(pagerState.currentPage)
        Mark_As_Sold_Flow(mark_as_Sold, videos[pagerState.currentPage].user_post_id, navController)
    }
}

@Composable
fun SingleSlideshowReel2_Search_Flow(
    item: Get_Reels_Data,
    page: Int,
    modifier: Modifier,
    videos: List<Get_Reels_Data>,
    navController: NavHostController,
    pagerState: PagerState,
    isLike_Loading: MutableState<Boolean>,
    isSave_Loading: MutableState<Boolean>,
    intervalMillis: Long = 1000L,
    viewModel: Common_H_ViewModel  // ADD THIS PARAMETER
)
{
    val showBABars = viewModel.showBABars.collectAsState()

    var currentIndex by remember { mutableStateOf(0) }
    var isPlaying by remember { mutableStateOf(true) }

    // Animations
    val alpha = remember { Animatable(1f) }
    val scale = remember { Animatable(1f) }

    // Comment bottom sheet state
    val cmt_btm_Sheet = viewModel.comment_btm_Sheet.collectAsState()
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    val reelsBTMSheetState = viewModel.reelsBtm_sheet.collectAsState()
    val reelsBTMSOptions = constants.Reels_ViewModel.reelsBTMSOptions.collectAsState()

    var mark_as_Sold = remember { mutableStateOf(false) }

    // REMOVE THESE DUPLICATE DECLARATIONS - THEY'RE ALREADY PARAMETERS
    // var isLike_Loading = remember { mutableStateOf(false) }
    // var isSave_Loading = remember { mutableStateOf(false) }

    var report_BS = remember { mutableStateOf(false) }
    val report_Options = constants.Profile_ViewModel.profile_Report_Options.collectAsState()
    val report_success = constants.Profile_ViewModel.report_Submit_Success.collectAsState()

    val send_Eq_State = constants.Reels_ViewModel.send_Enquiry_Btm_Sheet.collectAsState()

    // Slideshow animation loop
    LaunchedEffect(isPlaying) {
        while (isPlaying && item.post_property.images?.isNotEmpty() == true) {
            delay(intervalMillis)
            alpha.animateTo(0f, tween(500))
            currentIndex = (currentIndex + 1) % (item.post_property?.images?.size ?: 0)
            alpha.animateTo(1f, tween(500))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .noRippleClickable{ isPlaying = !isPlaying }, // tap to play/pause
        contentAlignment = Alignment.Center
    )
    {
        // Show current image
        if (item.post_property.images?.isNotEmpty() == true) {
            SubcomposeAsyncImage(
                model = item.post_property.images[currentIndex].url,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(
                        alpha = alpha.value,
                        scaleX = scale.value,
                        scaleY = scale.value
                    ),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            ) {
                val state = painter.state
                if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painterResource(R.drawable.emptypostsrento), "",
                            modifier = Modifier.size(200.dp)
                        )
                    }
                } else {
                    SubcomposeAsyncImageContent()
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(painterResource(R.drawable.emptypostsrento), "")
            }
        }

        // Reels options overlay (like, comment, save, bottom sheet)
        Reels_Options_Search_Flow(
            modifier = Modifier.align(Alignment.BottomCenter),
            videos,
            page,
            navController,
            pagerState,
            player = null,
            duration = remember { mutableStateOf(1L) },
            position = remember { mutableStateOf(0L) },
            isLike_Loading,
            isSave_Loading,
            viewModel
        )

        // Play icon overlay when paused
        if (!isPlaying) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.play_arrow),
                    contentDescription = "Play",
                    tint = Color.White
                )
            }
        }
    }

}



@Composable
fun SingleSlideshowReel_Single(
    postId: Int,
    modifier: Modifier,
    navController: NavHostController,
    isLike_Loading: MutableState<Boolean>,
    isSave_Loading: MutableState<Boolean>,
    intervalMillis: Long = 1000L,
    viewmodel: Common_H_ViewModel
)
{


    // 🔹 Observe updated data from ViewModel
    val myLeads by constants.Enquiry_ViewModel.my_Leads.collectAsState()
    val selfEnquiry by constants.Enquiry_ViewModel.self_Enquiry.collectAsState()

    // 🔹 Always resolve latest PostUser from Flow
    val currentPost = remember(myLeads, selfEnquiry, postId) {
        myLeads.firstOrNull { it.post_user.user_post_id == postId }?.post_user
            ?: selfEnquiry.firstOrNull { it?.post_user?.user_post_id == postId }?.post_user
    } ?: return


    val showBABars = viewmodel.showBABars.collectAsState()

    var currentIndex by remember { mutableStateOf(0) }
    var isPlaying by remember { mutableStateOf(true) }

    // Animations
    val alpha = remember { Animatable(1f) }
    val scale = remember { Animatable(1f) }



    // Slideshow animation loop
    LaunchedEffect(isPlaying) {
        while (isPlaying && currentPost.post_property.images?.isNotEmpty() == true) {
            delay(intervalMillis)
            alpha.animateTo(0f, tween(500))
            currentPost.post_property.images?.size?.let { currentIndex = (currentIndex + 1) % it }
            alpha.animateTo(1f, tween(500))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .noRippleClickable{ isPlaying = !isPlaying }, // tap to play/pause
        contentAlignment = Alignment.Center
    ) {

        // Show current image
        if (currentPost.post_property.images?.isNotEmpty() == true) {
//            AsyncImage(
//                model = item.post_property.image_urls[currentIndex],
//                contentDescription = null,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .graphicsLayer(
//                        alpha = alpha.value,
//                        scaleX = scale.value,
//                        scaleY = scale.value
//                    ),
//                contentScale = ContentScale.Crop
//            )

            SubcomposeAsyncImage(
                model = currentPost.post_property.images[currentIndex].url,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(
                        alpha = alpha.value,
                        scaleX = scale.value,
                        scaleY = scale.value
                    )
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
                        //.padding(8.dp)
                        , contentAlignment = Alignment.Center
                    ){
                        Image(painterResource(R.drawable.emptypostsrento) , "",
                            modifier = Modifier.size(200.dp))
                    }
                } else {
                    SubcomposeAsyncImageContent()
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                //Text("No media available", color = Color.White)
                Image(painterResource(R.drawable.emptypostsrento) ,"")
            }
        }

        val position = remember { mutableStateOf(0L) }
        val duration = remember { mutableStateOf(1L) }


        // Reels options overlay (like, comment, save, bottom sheet)
        // Reels options overlay
        ReelsOptionsSingle(
            modifier = Modifier
                .align(Alignment.BottomCenter), navController = navController,
            player = null,
            postId = currentPost.user_post_id,
            duration = duration,
            position = position,
            isLikeLoading = isLike_Loading,
            isSaveLoading = isSave_Loading,
            showBABars = showBABars
        )

        // Play icon overlay when paused
        if (!isPlaying) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.play_arrow),
                    contentDescription = "Play",
                    tint = Color.White
                )
            }
        }
    }
}
