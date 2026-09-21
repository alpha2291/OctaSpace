package com.toletspot.houseforrent

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

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

    fun show(message: String, duration: SnackbarDuration = SnackbarDuration.Short) {
        val emitted = _messages.tryEmit(SnackbarData(message, duration))
        if (!emitted) {

            MainScope().launch { _messages.emit(SnackbarData(message, duration)) }
        }
    }

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

@Composable
fun GlobalSnackbarHost() {
    val hostState = remember { SnackbarHostState() }

    val latestSnackbarData = remember { mutableStateOf<GlobalSnackbar.SnackbarData?>(null) }

    LaunchedEffect(Unit) {
        GlobalSnackbar.messages.collectLatest { data ->
            latestSnackbarData.value = data
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
