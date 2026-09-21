package com.toletspot.houseforrent

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.media.MediaCodec
import android.media.MediaCodecInfo
import android.media.MediaFormat
import android.media.MediaMuxer
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.drawToBitmap
import com.toletspot.houseforrent.ui.theme.newWhite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import android.graphics.Canvas
import java.io.File

/*suspend fun exportSlideshowToVideo(
    context: Context,
    composeView: ComposeView,
    outputFile: File,
    width: Int,
    height: Int,
    fps: Int = 30,
    durationSec: Int = 10
) = withContext(Dispatchers.IO) {
    val format = MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_AVC, width, height).apply {
        setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface)
        setInteger(MediaFormat.KEY_BIT_RATE, 5_000_000)
        setInteger(MediaFormat.KEY_FRAME_RATE, fps)
        setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 1)
    }

    val codec = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_VIDEO_AVC)
    codec.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)

    val inputSurface = codec.createInputSurface()
    val muxer = MediaMuxer(outputFile.absolutePath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)

    codec.start()

    val bufferInfo = MediaCodec.BufferInfo()
    var trackIndex = -1
    var presentationTimeUs = 0L
    val frameTimeUs = 1_000_000L / fps

    val surface = codec.createInputSurface()


    repeat(durationSec * fps) { frame ->
        // Render Compose frame to Bitmap on Main thread
        val bitmap = withContext(Dispatchers.Main) {
            composeView.drawToBitmap(Bitmap.Config.ARGB_8888)
        }

        // Lock canvas, draw, unlock
        val canvas: Canvas = surface.lockCanvas(null)
        canvas.drawBitmap(bitmap, null, Rect(0, 0, width, height), null)
        surface.unlockCanvasAndPost(canvas)

        presentationTimeUs = frame * frameTimeUs

        // Drain encoder
        var outputBufferIndex = codec.dequeueOutputBuffer(bufferInfo, 0)
        while (outputBufferIndex >= 0) {
            val encodedData = codec.getOutputBuffer(outputBufferIndex) ?: break

            if (bufferInfo.flags and MediaCodec.BUFFER_FLAG_CODEC_CONFIG != 0) {
                bufferInfo.size = 0
            }

            if (bufferInfo.size > 0) {
                encodedData.position(bufferInfo.offset)
                encodedData.limit(bufferInfo.offset + bufferInfo.size)

                if (trackIndex == -1) {
                    trackIndex = muxer.addTrack(codec.outputFormat)
                    muxer.start()
                }

                bufferInfo.presentationTimeUs = presentationTimeUs
                muxer.writeSampleData(trackIndex, encodedData, bufferInfo)
            }

            codec.releaseOutputBuffer(outputBufferIndex, false)
            outputBufferIndex = codec.dequeueOutputBuffer(bufferInfo, 0)
        }
    }

    codec.signalEndOfInputStream()

    // Finish writing
    codec.stop()
    codec.release()
    muxer.stop()
    muxer.release()
}*/

import android.os.Environment
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.*

/*// 🔹 Export Function
suspend fun exportSlideshowToVideo(
    context: Context,
    composeView: ComposeView,
    outputFile: File,
    width: Int,
    height: Int,
    fps: Int = 30,
    durationSec: Int = 10
) = withContext(Dispatchers.IO) {

    val format = MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_AVC, width, height).apply {
        setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface)
        setInteger(MediaFormat.KEY_BIT_RATE, 5_000_000)
        setInteger(MediaFormat.KEY_FRAME_RATE, fps)
        setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 1)
    }

    val codec = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_VIDEO_AVC)
    codec.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)

    val surface = codec.createInputSurface()
    val muxer = MediaMuxer(outputFile.absolutePath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)

    codec.start()

    val bufferInfo = MediaCodec.BufferInfo()
    var trackIndex = -1
    var presentationTimeUs = 0L
    val frameTimeUs = 1_000_000L / fps

    repeat(durationSec * fps) { frame ->
        // 🔹 Capture frame from Compose
        val bitmap = withContext(Dispatchers.Main) {
            composeView.drawToBitmap(Bitmap.Config.ARGB_8888)
        }

        // ⚠️ Normally, inputSurface is for GPU/EGL rendering.
        // For demo simplicity, we use lockCanvas (works on some devices but EGL path is safer).
        val canvas: Canvas = surface.lockCanvas(null)
        canvas.drawBitmap(bitmap, null, Rect(0, 0, width, height), null)
        surface.unlockCanvasAndPost(canvas)

        presentationTimeUs = frame * frameTimeUs

        // 🔹 Drain encoder
        var outputBufferIndex = codec.dequeueOutputBuffer(bufferInfo, 0)
        while (outputBufferIndex >= 0) {
            val encodedData = codec.getOutputBuffer(outputBufferIndex) ?: break

            if (bufferInfo.flags and MediaCodec.BUFFER_FLAG_CODEC_CONFIG != 0) {
                bufferInfo.size = 0
            }

            if (bufferInfo.size > 0) {
                encodedData.position(bufferInfo.offset)
                encodedData.limit(bufferInfo.offset + bufferInfo.size)

                if (trackIndex == -1) {
                    trackIndex = muxer.addTrack(codec.outputFormat)
                    muxer.start()
                }

                bufferInfo.presentationTimeUs = presentationTimeUs
                muxer.writeSampleData(trackIndex, encodedData, bufferInfo)
            }

            codec.releaseOutputBuffer(outputBufferIndex, false)
            outputBufferIndex = codec.dequeueOutputBuffer(bufferInfo, 0)
        }
    }

    codec.signalEndOfInputStream()
    codec.stop()
    codec.release()
    muxer.stop()
    muxer.release()
}




enum class AnimationType {
    FADE,
    SLIDE_LEFT, SLIDE_RIGHT, SLIDE_UP, SLIDE_DOWN,
    SCALE, ZOOM,
    ROTATE_X, ROTATE_Y, ROTATE_Z,
    FLIP_X, FLIP_Y,
    BOUNCE, SPIN,
    MIXED
}

@Composable
fun New_Video_Flow() {
    val imagesList = remember {
        listOf(
            R.drawable.nature1,
            R.drawable.nature2,
            R.drawable.nature3,
            R.drawable.nature4,
            R.drawable.nature5
        )
    }

    val context = LocalContext.current
    var exporting by remember { mutableStateOf(false) }
    var exportDone by remember { mutableStateOf<File?>(null) }


    var currentIndex by remember { mutableStateOf(0) }
    var animationType by remember { mutableStateOf(AnimationType.FADE) }
    var isPlaying by remember { mutableStateOf(true) }

    // Animatables
    val alpha = remember { Animatable(1f) }
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val scale = remember { Animatable(1f) }
    val rotationX = remember { Animatable(0f) }
    val rotationY = remember { Animatable(0f) }
    val rotationZ = remember { Animatable(0f) }

    // Reset all properties to defaults
    suspend fun resetImageState() {
        alpha.snapTo(1f)
        offsetX.snapTo(0f)
        offsetY.snapTo(0f)
        scale.snapTo(1f)
        rotationX.snapTo(0f)
        rotationY.snapTo(0f)
        rotationZ.snapTo(0f)
    }

    // Animation loop
    LaunchedEffect(animationType, isPlaying) {
        // Reset position when switching animation
        resetImageState()

        while (isPlaying) {
            delay(3000)

            val effect = if (animationType == AnimationType.MIXED) {
                AnimationType.values().random().takeIf { it != AnimationType.MIXED } ?: AnimationType.FADE
            } else animationType

            when (effect) {
                AnimationType.FADE -> {
                    alpha.animateTo(0f, tween(500))
                    currentIndex = (currentIndex + 1) % imagesList.size
                    alpha.animateTo(1f, tween(500))
                }

                AnimationType.SLIDE_LEFT -> {
                    offsetX.animateTo(-1000f, tween(500))
                    currentIndex = (currentIndex + 1) % imagesList.size
                    offsetX.snapTo(1000f)
                    offsetX.animateTo(0f, tween(500))
                }

                AnimationType.SLIDE_RIGHT -> {
                    offsetX.animateTo(1000f, tween(500))
                    currentIndex = (currentIndex + 1) % imagesList.size
                    offsetX.snapTo(-1000f)
                    offsetX.animateTo(0f, tween(500))
                }

                AnimationType.SLIDE_UP -> {
                    offsetY.animateTo(-1000f, tween(500))
                    currentIndex = (currentIndex + 1) % imagesList.size
                    offsetY.snapTo(1000f)
                    offsetY.animateTo(0f, tween(500))
                }

                AnimationType.SLIDE_DOWN -> {
                    offsetY.animateTo(1000f, tween(500))
                    currentIndex = (currentIndex + 1) % imagesList.size
                    offsetY.snapTo(-1000f)
                    offsetY.animateTo(0f, tween(500))
                }

                AnimationType.SCALE -> {
                    scale.animateTo(0.7f, tween(500))
                    currentIndex = (currentIndex + 1) % imagesList.size
                    scale.animateTo(1f, tween(500))
                }

                AnimationType.ZOOM -> {
                    scale.animateTo(1.5f, tween(500))
                    currentIndex = (currentIndex + 1) % imagesList.size
                    scale.snapTo(0.8f)
                    scale.animateTo(1f, tween(500))
                }

                AnimationType.ROTATE_X -> {
                    rotationX.animateTo(180f, tween(500))
                    currentIndex = (currentIndex + 1) % imagesList.size
                    rotationX.snapTo(-180f)
                    rotationX.animateTo(0f, tween(500))
                }

                AnimationType.ROTATE_Y -> {
                    rotationY.animateTo(180f, tween(500))
                    currentIndex = (currentIndex + 1) % imagesList.size
                    rotationY.snapTo(-180f)
                    rotationY.animateTo(0f, tween(500))
                }

                AnimationType.ROTATE_Z -> {
                    rotationZ.animateTo(rotationZ.value + 360f, tween(800))
                    currentIndex = (currentIndex + 1) % imagesList.size
                }

                AnimationType.FLIP_X -> {
                    rotationX.animateTo(90f, tween(300))
                    currentIndex = (currentIndex + 1) % imagesList.size
                    rotationX.animateTo(0f, tween(300))
                }

                AnimationType.FLIP_Y -> {
                    rotationY.animateTo(90f, tween(300))
                    currentIndex = (currentIndex + 1) % imagesList.size
                    rotationY.animateTo(0f, tween(300))
                }

                AnimationType.BOUNCE -> {
                    scale.animateTo(1.3f, spring(dampingRatio = Spring.DampingRatioLowBouncy))
                    currentIndex = (currentIndex + 1) % imagesList.size
                    scale.animateTo(1f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                }

                AnimationType.SPIN -> {
                    rotationZ.animateTo(rotationZ.value + 720f, tween(1000))
                    currentIndex = (currentIndex + 1) % imagesList.size
                }

                AnimationType.MIXED -> Unit
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(newBlack),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Image container with play/pause button overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .background(newBlack),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imagesList[currentIndex]),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(alpha.value)
                    .offset { IntOffset(offsetX.value.toInt(), offsetY.value.toInt()) }
                    .graphicsLayer(
                        scaleX = scale.value,
                        scaleY = scale.value,
                        rotationX = rotationX.value,
                        rotationY = rotationY.value,
                        rotationZ = rotationZ.value
                    ),
                contentScale = ContentScale.FillBounds
            )

            // Play/Pause overlay button
            IconButton(
                onClick = { isPlaying = !isPlaying },
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.Black.copy(alpha = 0.5f), shape = CircleShape)
            )
            {
                Icon(
                    painter = painterResource(if (isPlaying) R.drawable.close else R.drawable.play_arrow),
                    // imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = "Play/Pause",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(newWhite.copy(.2f))
                    .align(Alignment.BottomCenter)
                    .padding(vertical = 24.dp)
                , contentAlignment = Alignment.Center
            ) {
                Text("Places , Can overlay Text", fontWeight = FontWeight.Bold , color = newBlue, fontSize = 20.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Scrollable animation selection
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(AnimationType.values()) { type ->
                Button(
                    onClick = {
                        animationType = type
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (animationType == type) Color.Gray else Color.DarkGray
                    )
                ) {
                    Text(type.name, fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Export Button
        Button(
            onClick = {
                val downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val file = File(downloads, "slideshow_export.mp4")

                val composeView = ComposeView(context).apply {
                    setContent {
                        // Reuse same slideshow UI for rendering frames
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black)
                        ) {
                            Text("Export Rendering", color = Color.White)
                        }
                    }
                }

                exporting = true
                CoroutineScope(Dispatchers.Main).launch {
                    exportSlideshowToVideo(context, composeView, file, 720, 1280, fps = 30, durationSec = 10)
                    exporting = false
                    exportDone = file
                }
            },
            enabled = !exporting
        ) {
            Text(if (exporting) "Exporting..." else "Export Video")
        }

        exportDone?.let { file ->
            Spacer(modifier = Modifier.height(8.dp))
            Text("Exported to: ${file.absolutePath}", color = Color.Green)
        }
    }
}*/


import android.view.View
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.compose.PlayerSurface
import coil.compose.AsyncImage
import com.toletspot.houseforrent.Custom_Assets.ClickHelper
import com.toletspot.houseforrent.Home_Screen.PostProperty_Module.RentoDataclass.RentoMediaDC.Image

// 🔹 Export Function with measure/layout fix
suspend fun exportSlideshowToVideo(
    context: Context,
    composeView: ComposeView,
    outputFile: File,
    width: Int,
    height: Int,
    fps: Int = 30,
    durationSec: Int = 30
) = withContext(Dispatchers.IO) {
    val format = MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_AVC, width, height).apply {
        setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface)
        setInteger(MediaFormat.KEY_BIT_RATE, 5_000_000)
        setInteger(MediaFormat.KEY_FRAME_RATE, fps)
        setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 1)
    }

    val codec = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_VIDEO_AVC)
    codec.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)

    val surface = codec.createInputSurface()
    val muxer = MediaMuxer(outputFile.absolutePath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)

    codec.start()

    val bufferInfo = MediaCodec.BufferInfo()
    var trackIndex = -1
    var presentationTimeUs = 0L
    val frameTimeUs = 1_000_000L / fps

    // 🔹 Force layout once before capture
    withContext(Dispatchers.Main) {
        val widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        composeView.measure(widthSpec, heightSpec)
        composeView.layout(0, 0, width, height)
    }

    repeat(durationSec * fps) { frame ->
        // 🔹 Capture bitmap from Compose
        val bitmap = withContext(Dispatchers.Main) {
            composeView.drawToBitmap(Bitmap.Config.ARGB_8888)
        }

        // Draw into encoder surface
        val canvas: Canvas = surface.lockCanvas(null)
        canvas.drawBitmap(bitmap, null, Rect(0, 0, width, height), null)
        surface.unlockCanvasAndPost(canvas)

        presentationTimeUs = frame * frameTimeUs

        // 🔹 Drain encoder
        var outputBufferIndex = codec.dequeueOutputBuffer(bufferInfo, 0)
        while (outputBufferIndex >= 0) {
            val encodedData = codec.getOutputBuffer(outputBufferIndex) ?: break

            if (bufferInfo.flags and MediaCodec.BUFFER_FLAG_CODEC_CONFIG != 0) {
                bufferInfo.size = 0
            }

            if (bufferInfo.size > 0) {
                encodedData.position(bufferInfo.offset)
                encodedData.limit(bufferInfo.offset + bufferInfo.size)

                if (trackIndex == -1) {
                    trackIndex = muxer.addTrack(codec.outputFormat)
                    muxer.start()
                }

                bufferInfo.presentationTimeUs = presentationTimeUs
                muxer.writeSampleData(trackIndex, encodedData, bufferInfo)
            }

            codec.releaseOutputBuffer(outputBufferIndex, false)
            outputBufferIndex = codec.dequeueOutputBuffer(bufferInfo, 0)
        }
    }

    codec.signalEndOfInputStream()
    codec.stop()
    codec.release()
    muxer.stop()
    muxer.release()
}

// -------------------------
// 🔹 Your Slideshow UI
// -------------------------


enum class AnimationType {
    FADE, SLIDE_LEFT, SLIDE_RIGHT, SLIDE_UP, SLIDE_DOWN,
    SCALE, ZOOM, ROTATE_X, ROTATE_Y, ROTATE_Z,
    FLIP_X, FLIP_Y, BOUNCE, SPIN, MIXED
}

@Composable
fun SlideshowRenderer(
    imagesList: List<Int>,
    currentIndex: Int,
    alpha: Float,
    offsetX: Float,
    offsetY: Float,
    scale: Float,
    rotationX: Float,
    rotationY: Float,
    rotationZ: Float
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imagesList[currentIndex]),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .alpha(alpha)
                .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    rotationX = rotationX,
                    rotationY = rotationY,
                    rotationZ = rotationZ
                ),
            contentScale = ContentScale.FillBounds
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(newWhite.copy(.4f))
                .padding(vertical = 24.dp)
            , contentAlignment = Alignment.Center
        ) {
            Text("OVERLAY ANYTHING", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SlideshowContent(
    modifier: Modifier = Modifier,
    animationType: AnimationType,
    onAnimationTypeChange: (AnimationType) -> Unit,
    isPlaying: Boolean,
    onPlayPauseToggle: () -> Unit,
    imagesList: List<Int>
) {
    // 🔹 Animation states
    var currentIndex by remember { mutableStateOf(0) }
    val alpha = remember { Animatable(1f) }
    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val scale = remember { Animatable(1f) }
    val rotationX = remember { Animatable(0f) }
    val rotationY = remember { Animatable(0f) }
    val rotationZ = remember { Animatable(0f) }

    suspend fun resetImageState() {
        alpha.snapTo(1f)
        offsetX.snapTo(0f)
        offsetY.snapTo(0f)
        scale.snapTo(1f)
        rotationX.snapTo(0f)
        rotationY.snapTo(0f)
        rotationZ.snapTo(0f)
    }

    // 🔹 Animation loop
    LaunchedEffect(animationType, isPlaying) {
        resetImageState()
        while (isPlaying) {
            delay(1000)
            val effect = if (animationType == AnimationType.MIXED) {
                AnimationType.values().random().takeIf { it != AnimationType.MIXED } ?: AnimationType.FADE
            } else animationType

            when (effect) {
                AnimationType.FADE -> {
                    alpha.animateTo(0f, tween(500))
                    currentIndex = (currentIndex + 1) % imagesList.size
                    alpha.animateTo(1f, tween(500))
                }
                AnimationType.SLIDE_LEFT -> {
                    offsetX.animateTo(-1000f, tween(500))
                    currentIndex = (currentIndex + 1) % imagesList.size
                    offsetX.snapTo(1000f)
                    offsetX.animateTo(0f, tween(500))
                }
                AnimationType.SLIDE_RIGHT -> {
                    offsetX.animateTo(1000f, tween(500))
                    currentIndex = (currentIndex + 1) % imagesList.size
                    offsetX.snapTo(-1000f)
                    offsetX.animateTo(0f, tween(500))
                }
                AnimationType.SLIDE_UP -> {
                    offsetY.animateTo(-1000f, tween(500))
                    currentIndex = (currentIndex + 1) % imagesList.size
                    offsetY.snapTo(1000f)
                    offsetY.animateTo(0f, tween(500))
                }
                AnimationType.SLIDE_DOWN -> {
                    offsetY.animateTo(1000f, tween(500))
                    currentIndex = (currentIndex + 1) % imagesList.size
                    offsetY.snapTo(-1000f)
                    offsetY.animateTo(0f, tween(500))
                }
                AnimationType.SCALE -> {
                    scale.animateTo(0.7f, tween(500))
                    currentIndex = (currentIndex + 1) % imagesList.size
                    scale.animateTo(1f, tween(500))
                }
                AnimationType.ZOOM -> {
                    scale.animateTo(1.5f, tween(500))
                    currentIndex = (currentIndex + 1) % imagesList.size
                    scale.snapTo(0.8f)
                    scale.animateTo(1f, tween(500))
                }
                AnimationType.ROTATE_X -> {
                    rotationX.animateTo(180f, tween(500))
                    currentIndex = (currentIndex + 1) % imagesList.size
                    rotationX.snapTo(-180f)
                    rotationX.animateTo(0f, tween(500))
                }
                AnimationType.ROTATE_Y -> {
                    rotationY.animateTo(180f, tween(500))
                    currentIndex = (currentIndex + 1) % imagesList.size
                    rotationY.snapTo(-180f)
                    rotationY.animateTo(0f, tween(500))
                }
                AnimationType.ROTATE_Z -> {
                    rotationZ.animateTo(rotationZ.value + 360f, tween(800))
                    currentIndex = (currentIndex + 1) % imagesList.size
                }
                AnimationType.FLIP_X -> {
                    rotationX.animateTo(90f, tween(300))
                    currentIndex = (currentIndex + 1) % imagesList.size
                    rotationX.animateTo(0f, tween(300))
                }
                AnimationType.FLIP_Y -> {
                    rotationY.animateTo(90f, tween(300))
                    currentIndex = (currentIndex + 1) % imagesList.size
                    rotationY.animateTo(0f, tween(300))
                }
                AnimationType.BOUNCE -> {
                    scale.animateTo(1.3f, spring(dampingRatio = Spring.DampingRatioLowBouncy))
                    currentIndex = (currentIndex + 1) % imagesList.size
                    scale.animateTo(1f, spring(dampingRatio = Spring.DampingRatioMediumBouncy))
                }
                AnimationType.SPIN -> {
                    rotationZ.animateTo(rotationZ.value + 720f, tween(1000))
                    currentIndex = (currentIndex + 1) % imagesList.size
                }
                AnimationType.MIXED -> Unit
            }
        }
    }

    Column(
        modifier = modifier.fillMaxSize().background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 🔹 Only slideshow
        SlideshowRenderer(
            imagesList = imagesList,
            currentIndex = currentIndex,
            alpha = alpha.value,
            offsetX = offsetX.value,
            offsetY = offsetY.value,
            scale = scale.value,
            rotationX = rotationX.value,
            rotationY = rotationY.value,
            rotationZ = rotationZ.value
        )

        Spacer(Modifier.height(16.dp))

        // 🔹 Controls (NOT exported)
        IconButton(
            onClick = onPlayPauseToggle,
            modifier = Modifier
                .size(60.dp)
                .background(Color.Black.copy(alpha = 0.5f), shape = CircleShape)
        ) {
            Icon(
                painter = painterResource(if (isPlaying) R.drawable.close else R.drawable.play_arrow),
                contentDescription = "Play/Pause",
                tint = Color.White,
                modifier = Modifier.size(36.dp)
            )
        }

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(AnimationType.values()) { type ->
                Button(
                    onClick = { onAnimationTypeChange(type) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (animationType == type) Color.Gray else Color.DarkGray
                    )
                ) {
                    Text(type.name, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun New_Video_Flow() {
    val context = LocalContext.current
    var exporting by remember { mutableStateOf(false) }
    var exportDone by remember { mutableStateOf<File?>(null) }

    var animationType by remember { mutableStateOf(AnimationType.FADE) }
    var isPlaying by remember { mutableStateOf(true) }

    var activeComposeView: ComposeView? by remember { mutableStateOf(null) }

    val imagesList = listOf(
        R.drawable.nature1,
        R.drawable.nature2,
        R.drawable.nature3,
        R.drawable.nature4,
        R.drawable.nature5
    )

    Column(Modifier.fillMaxSize()) {
        // Wrap slideshow in AndroidView to get ComposeView reference
        AndroidView(factory = { ctx ->
            ComposeView(ctx).also { cv ->
                activeComposeView = cv
                cv.setContent {
                    SlideshowContent(
                        animationType = animationType,
                        onAnimationTypeChange = { animationType = it },
                        isPlaying = isPlaying,
                        onPlayPauseToggle = { isPlaying = !isPlaying },
                        imagesList = imagesList
                    )
                }
            }
        }, modifier = Modifier.weight(1f))

        Button(
            onClick = {
                val file = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "slideshow_export.mp4"
                )
                exporting = true
                CoroutineScope(Dispatchers.Main).launch {
                    activeComposeView?.let { cv ->
                        // 🔹 Export only the slideshow renderer (no buttons)
//                        cv.setContent {
//                            SlideshowRenderer(
//                                imagesList = imagesList,
//                                currentIndex = 0, // start index
//                                alpha = 1f,
//                                offsetX = 0f,
//                                offsetY = 0f,
//                                scale = 1f,
//                                rotationX = 0f,
//                                rotationY = 0f,
//                                rotationZ = 0f
//                            )
//                        }

                        cv.setContent {
                            SlideshowContent(
                                animationType = animationType,
                                onAnimationTypeChange = {},
                                isPlaying = true, // force playing
                                onPlayPauseToggle = {},
                                imagesList = imagesList
                            )
                        }
                        exportSlideshowToVideo(context, cv, file, 720, 1280, fps = 30, durationSec = 30)
                        exportDone = file
                    }
                    exporting = false
                }
            },
            enabled = !exporting
        ) {
            Text(if (exporting) "Exporting…" else "Export Video")
        }

        exportDone?.let { file ->
            Text("Exported to: ${file.absolutePath}", color = Color.Green)
        }
    }
}


/////////////////////////////////////////////////////////////////////////////////////////////////////



@Composable
fun SingleVideoPlayer(
    videoUri: String,
    modifier: Modifier = Modifier,
)
{
    val context = LocalContext.current

    // Keep single ExoPlayer instance
    val player = remember {
        ExoPlayer.Builder(context).build()
    }

    // Update source when videoUri changes
    LaunchedEffect(videoUri) {
        player.setMediaItem(MediaItem.fromUri(videoUri))
        player.prepare()
        player.playWhenReady = true
    }

    val isBuffering = remember { mutableStateOf(true) }
    val isPlaying = remember { mutableStateOf(false) }

    // Listen for state changes
    DisposableEffect(player) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                isBuffering.value = state != Player.STATE_READY
                if (state == Player.STATE_ENDED) {
                    player.seekTo(0)
                    player.playWhenReady = true
                }
            }

            override fun onIsPlayingChanged(isPlayingNow: Boolean) {
                isPlaying.value = isPlayingNow
            }
        }
        player.addListener(listener)

        onDispose {
            player.removeListener(listener)
            // keep global instance -> don’t release
            // for per-screen lifecycle, uncomment:
            // player.release()
        }
    }

    var currentPosition by remember { mutableStateOf(0L) }
    var totalDuration by remember { mutableStateOf(0L) }

    // Update progress
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

    // UI
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    )
    {
        PlayerSurface(
            player = player,
            modifier = Modifier
                .fillMaxSize()
                .noRippleClickable{
                    ClickHelper.getInstance().clickOnce {
                        if (player.isPlaying) player.pause() else player.play()
                    }
                }
        )

        // Play icon overlay
        if (!isPlaying.value) {
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
                    contentDescription = ""
                )
            }
        }

        // Buffering overlay
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

        // Bottom time bar
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Slider(
                value = if (totalDuration > 0) currentPosition / totalDuration.toFloat() else 0f,
                onValueChange = { newValue ->
                    currentPosition = (totalDuration * newValue).toLong()
                },
                onValueChangeFinished = {
                    player.seekTo(currentPosition)
                },
                modifier = Modifier.weight(1f),
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.Blue,
                    inactiveTrackColor = Color.Gray
                )
            )

            Text(
                text = "${formatTime(currentPosition)} / ${formatTime(totalDuration)}",
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

enum class SlideshowAnimation {
    FADE,          // Crossfade
    SPIN,          // Rotate 360° Z
    ZOOM_IN,       // Grows into view
    ZOOM_OUT,      // Shrinks into place
    FLIP_X,        // Horizontal flip
    FLIP_Y,        // Vertical flip
    SCALE_FADE,    // Zoom + Fade combo
    ROTATE_X,      // 3D tilt on X-axis
    ROTATE_Y,      // 3D tilt on Y-axis
    PULSE          // Grows & shrinks smoothly
}


/*@Composable
fun SingleSlideshow2(
    imagesList: List<String>,
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
    animationType: SlideshowAnimation = SlideshowAnimation.FADE, // Default Fade
    intervalMillis: Long = 2000 // Time per slide
) {
    var currentIndex by remember { mutableStateOf(0) }

    val alpha = remember { Animatable(1f) }
    val rotationZ = remember { Animatable(0f) }

    suspend fun resetState() {
        alpha.snapTo(1f)
        rotationZ.snapTo(0f)
    }

    LaunchedEffect(animationType, isPlaying) {
        resetState()
        while (isPlaying) {
            delay(intervalMillis)

            when (animationType) {
                SlideshowAnimation.FADE -> {
                    alpha.animateTo(0f, tween(500))
                    currentIndex = (currentIndex + 1) % imagesList.size
                    alpha.animateTo(1f, tween(500))
                }

                SlideshowAnimation.SPIN -> {
                    rotationZ.animateTo(
                        rotationZ.value + 360f,
                        animationSpec = tween(1000, easing = LinearEasing)
                    )
                    currentIndex = (currentIndex + 1) % imagesList.size
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {



        println("CONTENT TYPE -- ${imagesList[currentIndex]}")
        AsyncImage(
            model = imagesList[currentIndex],
                    contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    alpha = alpha.value,
                    rotationZ = rotationZ.value
                ),
            contentScale = ContentScale.Crop
        )
//        Image(
//            painter = painterResource(id = imagesList[currentIndex]),
//            contentDescription = null,
//            modifier = Modifier
//                .fillMaxSize()
//                .graphicsLayer(
//                    alpha = alpha.value,
//                    rotationZ = rotationZ.value
//                ),
//            contentScale = ContentScale.Crop
//        )
    }
}*/

@Composable
fun SingleSlideshow(
    imagesList: List<Image>,
    modifier: Modifier = Modifier,
    animationType: SlideshowAnimation = SlideshowAnimation.FADE,
    intervalMillis: Long = 900
) {
    var currentIndex by remember { mutableStateOf(0) }
    var isPlaying by remember { mutableStateOf(true) }

    val alpha = remember { Animatable(1f) }
    val rotationX = remember { Animatable(0f) }
    val rotationY = remember { Animatable(0f) }
    val rotationZ = remember { Animatable(0f) }
    val scale = remember { Animatable(1f) }

    suspend fun resetState() {
        alpha.snapTo(1f)
        rotationX.snapTo(0f)
        rotationY.snapTo(0f)
        rotationZ.snapTo(0f)
        scale.snapTo(1f)
    }

    // Animation loop
    LaunchedEffect(animationType, isPlaying) {
        resetState()
        while (isPlaying) {
            delay(intervalMillis)
            when (animationType) {
                SlideshowAnimation.FADE -> {
                    alpha.animateTo(0f, tween(500))
                    currentIndex = (currentIndex + 1) % imagesList.size
                    alpha.animateTo(1f, tween(500))
                }
                SlideshowAnimation.SPIN -> {
                    rotationZ.animateTo(rotationZ.value + 360f, tween(1000))
                    currentIndex = (currentIndex + 1) % imagesList.size
                }
                SlideshowAnimation.ZOOM_IN -> {
                    scale.snapTo(0.5f)
                    scale.animateTo(1f, tween(600))
                    currentIndex = (currentIndex + 1) % imagesList.size
                }
                SlideshowAnimation.ZOOM_OUT -> {
                    scale.snapTo(1.5f)
                    scale.animateTo(1f, tween(600))
                    currentIndex = (currentIndex + 1) % imagesList.size
                }
                SlideshowAnimation.FLIP_X -> {
                    rotationX.animateTo(rotationX.value + 180f, tween(800))
                    currentIndex = (currentIndex + 1) % imagesList.size
                }
                SlideshowAnimation.FLIP_Y -> {
                    rotationY.animateTo(rotationY.value + 180f, tween(800))
                    currentIndex = (currentIndex + 1) % imagesList.size
                }
                SlideshowAnimation.SCALE_FADE -> {
                    scale.snapTo(0.8f)
                    alpha.animateTo(0f, tween(400))
                    currentIndex = (currentIndex + 1) % imagesList.size
                    scale.animateTo(1f, tween(600))
                    alpha.animateTo(1f, tween(400))
                }
                SlideshowAnimation.ROTATE_X -> {
                    rotationX.animateTo(rotationX.value + 360f, tween(1000))
                    currentIndex = (currentIndex + 1) % imagesList.size
                }
                SlideshowAnimation.ROTATE_Y -> {
                    rotationY.animateTo(rotationY.value + 360f, tween(1000))
                    currentIndex = (currentIndex + 1) % imagesList.size
                }
                SlideshowAnimation.PULSE -> {
                    scale.animateTo(1.2f, tween(400))
                    scale.animateTo(1f, tween(400))
                    currentIndex = (currentIndex + 1) % imagesList.size
                }
            }
        }
    }

    // UI
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(Color.Black)
            .noRippleClickable{
                ClickHelper.getInstance().clickOnce {
                    isPlaying = !isPlaying
                }
                       }, // toggle play/pause on tap
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = imagesList[currentIndex] ?: "",
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    alpha = alpha.value,
                    rotationX = rotationX.value,
                    rotationY = rotationY.value,
                    rotationZ = rotationZ.value,
                    scaleX = scale.value,
                    scaleY = scale.value
                ),
            contentScale = ContentScale.Crop
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






// 🔹 Your Data Class
data class NewVideoFlowDC(
    var id: Int = 0,
    var videoUrl: String = "",
    var content_type: Int = 0, // 0 = Video, 1 = Slideshow
    var imagesList: List<Image> = emptyList(),
    var animationType: SlideshowAnimation = SlideshowAnimation.FADE // default
)

val newImageUrls = listOf(
    "https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707997127964.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707997203288.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707997405228.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707997585413.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707997673444.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707997757813.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478662846_Np26B7T19iXkMd4NNcbcjczA8cqRdWVlKoICwCobrl4ziFHH0qNQvawIGgUkxu.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478662857_oNN5C1yKa3d52SYFO9eVMzEZpJZaBULtVOyEhsSk8SCsRoJkEMhgVcYThNYigJ.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478662951_k170UwV7Whp6c4Ho78ODOc5rCJvYZmdN2uuGNtE7a5nJ5XM7zj5qNnEsBCDUbx.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478663206_qguzs5pohYyykEMY9tpKTAGcEN1dXg7RSQP47SaYSlngFLA1LRvTkibEjEdv3F.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707998275988.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707998378734.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478663375_boqJLZW6s8pFvVGTdQZtOovlMvVWJvGLuQW6QUFfS0r1jDUtQG4Mpr4iTvnn29.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707998499370.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478663790_34LcUCuTxLWhWR5YpQFmooOBuBWtUSJzkt1smXhMC7vCAc1zvzC2rO6QUBUpcG.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707999822157.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708000086995.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708000268683.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708000428858.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478666006_ShPT0sXjE9nu1QVirohxTKe3nNfopkQqHGhwclfBmsoOqUtY7Vwuwaaea0Ezcp.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708000532284.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708000689506.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708000781211.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708000855075.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708001081710.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708001158398.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708001243228.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708001347612.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708001414103.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708001476599.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708001560215.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708001641192.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478667943_PR3UpEN12w0zHciR7vYZ8iXDHOIWoDPz4WpqI779MVeNPQc2QMzVW4I8rzdgrK.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708001762050.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708001833851.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708001986151.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708002080090.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708002178985.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708002366957.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708002286601.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708002486676.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708002616060.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708002707513.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708002798558.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708002922689.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708003006837.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708003119688.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708003206194.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708003321034.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708003428542.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708003675830.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708003741599.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708003825158.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708003892017.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478673604_Ip2r1awovwhzx4ZwL8Naeb1fKy06Zj48PAvj91Wxkk8FDcxtf6pzUVA3WgGbRS.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708004120561.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708004231336.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708004302097.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708004347883.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708004616510.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708004802153.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478674509_uHxo00fJ6HeS4EDsniXTj9ALYZHyncJG7SoVl3zgmKhtkpxUT0kDT3cKA0WyoD.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708005028179.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708005388615.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478674672_Yjlv8q1tWbnXmswFNM3P3VAqF1rgvtaKCsjPdO6mw5zzzYjvx471QndL37DgCn.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708005657560.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708006273284.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708006456238.jpeg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708006684273.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708006812510.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708006866331.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708006961236.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708007154143.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708007366122.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478676823_x9Y3L6d57KKzJRQ1qX5FnVLYOMsmquaYaV6H2ere0BAfWBjyWvcUyXjmvEaAnY.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478676908_23N1g9O0PgD5byPW5bMgJAtBdQhEneGxNiATxDHsb2FIfwKkjYhI6DhUjrV2kh.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478676986_nSZ3sFXo2icjDiEgojmAZRJvIeoZlFK7lyLql5udEOCmqEbsTIlw2kYzao1iwl.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1708057557829.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478677231_CcyDQYcDQLGf0vBQJXXt9B4Wq5hHxDtuEjl7PZTwCishY9pL1RVf7AnZSPW5FY.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478677236_TPAe99kcGt2XvnrJCKAvGSrareoEcni7xWf4mDgLHFu8j5TxzDZhu4GjWSYvPU.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478677359_ncyK22asUModgiAEdTTtWMSSeegdShr23utSBxV54ObWkqg6XsPtRiOQFuuOP9.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478677392_6aCQ3jWopgPEx0llIl0YJYVg6GVpcQB6o32uLv1Plm7icjCzL6lV4j8ScJx304.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478677424_glSF4RDgm9q5mXQIITY5wHHW2CH87Oe74JXTVE4pQQzqZK7r7NA55OUWMcOEMT.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478677512_sBZeBfhajkdzNsIwfYkpPdUO7TD28SXUhmzZxvEFsPZbRNhdN1aoBzlHBabJRg.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478677637_cJJMtIlbIj9LhNbL51snoCQUsmWSEO6iVxjcy6HCuxWzaEkT0FdIAN4ovZYpcv.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478677637_HButYCi315O44zxjnw612K5s6qyV8FSOVsUYY3qkbBGgwZw2uPrpvBsz4WgIUC.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478677759_sjhFow9vFJwHlCudrY0VDBwbM0ExSPh7PmvHm3um37A0zGxi0Fajpb0TuV4hpT.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478677817_PZHgrxph5KDyUeOHelTDCBagtqo6TpNql5BKpXL9ausVuaT5NN5XvSobogiqp1.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478677931_yVn3yHqXCf7ABpqCdnJpcHOvd31QJPrHN2kGa7arhzJooVylT8B30HiZai2AF7.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478678043_vu3ne9Elwsoej0nuimTsMp42tXRLSZQefQab4j93xJD6sTu0LNv43rva4cYNdW.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478678056_e5SoWKMe9N0K93ayA4XouqINquhE8RnL63E8qAe8au0s5ruB7KWPKNbdzMHpXf.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478678190_p4wsMySOzfBCnRofPD1ZzxVjHwzF22gOF6C1gqBd2aobjDECUI1N7Li3BnFoCJ.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478678472_6X5XgAGcawLsxiquGfjH9r7ypq6cvG16gQrsxTqwOgaXOLf3zXqzYxOdLwiMPS.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478679365_W9KITT15siMM0eT985IUeE56nYsQ2hN6nYpo66GWJHGnv2P3wTXZn1EEqRjz3Q.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478679706_FXPKsur7xHNrp3nqsuw8kjPE4mokxZihx02lhTAzWqzjEVqLcymiDF6jgVut2h.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478679775_fh34EFKp6SkeH25FILxL4bqOlbfy9kQshqDSCqeW9QCBfVo04Y3V6Rax7nwJ37.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478679792_BEM0Z1gRDkYVvA0F1exGbuz4jgsVbPXMgK2KyPFoV9QV5ZJOX2v2Cw4iHweu8g.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478679892_EhFoeP64N0pxHH4mVPkQRJRMTVqUlUhbUPDVuWcXs6mJ4Wx9H6an6zAXu0qIha.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478680012_wFWbiewafXzidc0bKgD2EKqUn3DL9avJT0lZ2yKtVopHxmt14tFCUKQJCyCdvw.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478680144_DlwcYgFbwHIRoQ3PCvYcqot3z9RDz8VI2UVl5UminIiRKaF2vinJpqiMchZ1UF.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478680185_Y99jqJzYIqm8oMpQ5STb3ABa2JSgmvXVKlaRz8bHEKBgvbHQiRYHocNBMwWx30.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478680212_8su1vEyv1OAV0UU9dxpoYzonEYaktJn10zv1V4wLNxyrZVZ4w0kOIkzyfNAQp1.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478680299_YNHEovSJ51HKJNqfGoez7ZbBjE5O4HNnbkHMICCsE8yMh8y5ApPvHKjtDlPuwM.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478680333_5sy2SMgQhDPGANK6K9rstjDq4BTyIBJBzsUayn2WdXtcVHX14mSBWwrkQSsCwa.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478680412_UszxMsDFa77Q57Jb8JCyv9Y6mOQlpUQZsKTI45jVUP8VZ1UB1jjCIYRXt6jUOx.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478680556_rzcwua3RgmF17Qckd5Nfn1qZP4Q7SodtpenqtDftAAgGCwZZJUNMOyAUZMiqLM.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478680617_bPcwhtkxZzBzOtBcWnCYzQAOjpGSZRlTtsVBKks6X5F4Kym3DjQmA5sptOvHhc.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478680625_tQYXrkLgPQlCW0CXmrpD6bzrpn3Ir1SyoAiLmVpE2zQ3I8wI443heHanKNA5t7.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478680716_KLqUzTvpKgNdGj5caXvo6BxoYE0H1s9AzLw7Fn6DdAzRG9Ei2S45DnwUKfQxD8.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478680823_VG4Vi52bkRTfl4IetjbalsxQf3YZn00DX4JJUNXNCbelIlgRFJ4OUK4ASFiG53.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478680951_KgQ0TtjCf86bAn0xLSANQnsBlCZDs1GqK6mUQdybURrjl4ewz4TIHhZQ6uRjSo.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478681357_foLnHHvo5ViownNwPKkaGBZYT5SyXgupit8bBwTSNn0S5UYZGe4gxuznzrAFES.jpg","https://d1lzu68iwiw0po.cloudfront.net/Images_category/1707478682496_ZBu2hoxQgoNxXnw1vlm8uDXWJWZiXgRe1j0Q1WASBvisoHk6NFO2myYiT4rbG9.jpg",
    )

fun getRandomImages(): List<String> {
    val sizeOptions = listOf(4, 5, 6 , 7 , 3 , 2)
    val count = sizeOptions.random()
    return newImageUrls.shuffled().take(count)
}

// 🔹 Dummy Data
// 🔹 Main Pager

@Composable
fun VerticalFlowPager(items: List<NewVideoFlowDC>) {
    val pagerState = rememberPagerState(pageCount = ({items.size}))

    VerticalPager(
        //count = items.size,
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        val item = items[page]
        println("CONTENT TYPE -- ${item.content_type}")
        Box(modifier = Modifier
            .fillMaxSize()
            , contentAlignment = Alignment.Center
        ) {

            Text(page.toString() , color = Color.Red, modifier = Modifier.align(Alignment.TopCenter).padding(top = 36.dp))
            when (item.content_type) {
                0 -> SingleVideoPlayer(
                    videoUri = item.videoUrl,
                    modifier = Modifier.fillMaxWidth().height(400.dp)
                )

                1 -> {
                    val animationType = SlideshowAnimation.values()[page % SlideshowAnimation.values().size]

                    SingleSlideshow(
                        imagesList = item.imagesList,
                       // isPlaying = true,
                        animationType = animationType, // Or SPIN
                        modifier = Modifier.fillMaxWidth().height(400.dp)
                    )
                }
            }
        }
    }
}

// 🔹 Usage
@Composable
fun DemoScreen() {
   // VerticalVideoFlowPager(items = dummyItems)
}







