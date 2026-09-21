package com.toletspot.houseforrent


// GlobalSnackbar.kt
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


// GlobalSnackbarHostComposable.kt
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.toletspot.houseforrent.ui.theme.newBlack
import com.toletspot.houseforrent.ui.theme.newBlue
import com.toletspot.houseforrent.ui.theme.newWhite
import kotlinx.coroutines.flow.collectLatest

object GlobalSnackbarold {
    private val _messages = MutableSharedFlow<SnackbarData>(extraBufferCapacity = 64)
    val messages = _messages.asSharedFlow()

    data class SnackbarData(val message: String, val duration: SnackbarDuration)

    /**
     * Non-suspending, safe call from any thread/composable/ViewModel.
     * It tries to emit synchronously and falls back to posting on Main if buffer is full.
     */
    fun show(message: String, duration: SnackbarDuration = SnackbarDuration.Short) {
        val emitted = _messages.tryEmit(SnackbarData(message, duration))
        if (!emitted) {
            // fallback: ensure the message gets queued on Main
            MainScope().launch { _messages.emit(SnackbarData(message, duration)) }
        }
    }

    /** Suspended variant if you prefer to call from coroutine */
    suspend fun showSuspending(message: String, duration: SnackbarDuration = SnackbarDuration.Short) {
        _messages.emit(SnackbarData(message, duration))
    }
}


object GlobalSnackbar {
    private val _messages = MutableSharedFlow<SnackbarData>(extraBufferCapacity = 64)
    val messages = _messages.asSharedFlow()

    data class SnackbarData(
        val message: String,
        val duration: SnackbarDuration,
        val iconRes: Int? = null
    )

    fun show(
        message: String,
        duration: SnackbarDuration = SnackbarDuration.Short,
        iconRes: Int? = null
    ) {
        val emitted = _messages.tryEmit(SnackbarData(message, duration, iconRes))
        if (!emitted) {
            MainScope().launch { _messages.emit(SnackbarData(message, duration, iconRes)) }
        }
    }
}



/*@Composable
fun GlobalSnackbarHost(
) {
    // One hostState local to the composable (kept during recompositions)
    val hostState = remember { SnackbarHostState() }

    // Collect global messages and forward to hostState.showSnackbar
    LaunchedEffect(Unit) {
        GlobalSnackbar.messages.collectLatest { data ->
            // this runs on Main (LaunchedEffect), so it's safe to call showSnackbar
            hostState.showSnackbar(message = data.message, duration = data.duration)
        }
    }

    // Place the visual host somewhere (bottom center usually)
    // Make sure this composable is added once at the root of your screen/navigation
    Box(modifier = Modifier.fillMaxSize()) {

*/
/*
        SnackbarHost(
            hostState = hostState,
            modifier = Modifier
                .padding(bottom = 48.dp)
                .align(Alignment.BottomCenter),
            snackbar = { data ->
                Snackbar(
                    containerColor = newWhite, // 👈 set background color
                    contentColor = Color.Black, // 👈 set text/icon
                    modifier = Modifier
                        .padding(8.dp)
                        .border(1.dp, newBlue, shape = RoundedCornerShape(4.dp)),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                        , verticalAlignment = Alignment.CenterVertically
                        , horizontalArrangement = Arrangement.Center
                    ) {
                        Image(painter = painterResource())
                        Text(
                            text = data.visuals.message,
                            color = newBlack,
                            fontSize = constants.textUnit(16),
                            fontFamily = constants.fontFamily(0),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        )*/
/*

        SnackbarHost(
            hostState = hostState,
            modifier = Modifier
                .padding(bottom = 48.dp)
                .align(Alignment.BottomCenter),
            snackbar = { data ->
                Snackbar(
                    containerColor = newWhite,
                    contentColor = Color.Black,
                    modifier = Modifier
                        .padding(8.dp)
                        .border(1.dp, newBlue, shape = RoundedCornerShape(4.dp)),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        // 👇 Display only if iconRes is not null
                        val iconRes = data.visuals.actionLabel?.toIntOrNull()  // not needed
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        val extra = GlobalSnackbar.messages.replayCache.lastOrNull()

                        // show icon if exists
                        if (extra?.iconRes != null) {
                            Image(
                                painter = painterResource(extra.iconRes),
                                contentDescription = null,
                                modifier = Modifier.size(22.dp).padding(end = 6.dp)
                            )
                        }

                        Text(
                            text = data.visuals.message,
                            color = newBlack,
                            fontSize = constants.textUnit(16),
                            fontFamily = constants.fontFamily(0),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        )

    }


}*/

@Composable
fun GlobalSnackbarHost() {
    val hostState = remember { SnackbarHostState() }

    // Store most recent custom data locally
    val latestSnackbarData = remember { mutableStateOf<GlobalSnackbar.SnackbarData?>(null) }

    // Collect global events
    LaunchedEffect(Unit) {
        GlobalSnackbar.messages.collectLatest { data ->
            latestSnackbarData.value = data    // 👈 store locally
            hostState.showSnackbar(data.message, duration = data.duration)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        SnackbarHost(
            hostState = hostState,
            modifier = Modifier
                .padding(bottom = 48.dp)
                .align(Alignment.BottomCenter),
            snackbar = { snackbarData ->
                Snackbar(
                    containerColor = newWhite,
                    contentColor = newBlack,
                    modifier = Modifier
                        .padding(8.dp)
                        .border(1.dp, newBlue, shape = RoundedCornerShape(4.dp)),
                    shape = RoundedCornerShape(8.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        // 👇 Now we have access to iconRes
                        latestSnackbarData.value?.iconRes?.let { iconRes ->
                            Image(
                                painter = painterResource(iconRes),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(34.dp)
                                    .padding(end = 6.dp)
                            )
                        }

                        Text(
                            text = snackbarData.visuals.message,
                            fontSize = constants.textUnit(14),
                            fontFamily = constants.fontFamily(0),
                            color = newBlack
                        )
                    }
                }
            }
        )
    }
}


