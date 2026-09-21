package com.toletspot.houseforrent

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.Settings
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.toletspot.houseforrent.Custom_Assets.API_Fail_UI
import com.toletspot.houseforrent.Custom_Assets.Backer
import com.toletspot.houseforrent.Custom_Assets.rememberNotchHeightDp
import com.toletspot.houseforrent.Custom_Assets.scaledSp
import com.toletspot.houseforrent.ui.theme.newBlue

var mFilePathCallback1 by mutableStateOf<((Array<Uri?>?) -> Unit)?>(null)
var feedbackWebviewBackhandler : WebView? = null

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Justify(navHostController: NavHostController) {
    val webViewFail = remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current
    val network_status = remember { mutableStateOf(checkForInternet(context)) }

    val url = MainActivity.getprofileReportUrl()
        .plus("phone_num=${AppPreferences.get_ph_number()}")
        .plus("&phone_num_cc=${AppPreferences.getCountry().dial_code}")
        .plus("&device_type=1")

    val progressState = remember { mutableIntStateOf(0) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        modifier = Modifier.background(Color.Transparent),
        topBar = {
            Row(
                modifier = Modifier
                    .padding(start = 16.dp, top = rememberNotchHeightDp().value)
                    .fillMaxWidth()
            ) {
                Backer(modifier = Modifier) {
                    navHostController.navigateUp()
                }
            }
        },
        content = { paddingValue ->

            val galleryLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.OpenMultipleDocuments(),
                onResult = { uris ->
                    if (uris != null && uris.isNotEmpty()) {
                        mFilePathCallback1?.invoke(uris.toTypedArray())
                    } else {
                        mFilePathCallback1?.invoke(null)
                    }
                    mFilePathCallback1 = null
                }
            )

            Column(
                Modifier
                    .padding(top = paddingValue.calculateTopPadding())
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                if (!network_status.value) {

                } else {
                    if (webViewFail.value) {
                        API_Fail_UI {
                            webViewFail.value = false
                        }
                    } else {
                        Box {
                            class JSInterface {
                                @JavascriptInterface
                                fun closeModal(scrollhide: String) {
                                    if (scrollhide == "formModel") {
                                        navHostController.popBackStack()

                                    }
                                }
                            }

                            AndroidView(
                                factory = {
                                    WebView(it).apply {
                                        layoutParams = ViewGroup.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.MATCH_PARENT
                                        )

                                        val webSettings = settings
                                        webSettings.javaScriptEnabled = true
                                        addJavascriptInterface(JSInterface(), "AndroidInterface")
                                        WebView.setWebContentsDebuggingEnabled(false)
                                        webSettings.allowContentAccess = true
                                        webSettings.allowFileAccess = true
                                        webSettings.domStorageEnabled = true
                                        webSettings.loadWithOverviewMode = true
                                        webSettings.useWideViewPort = true
                                        webSettings.loadWithOverviewMode = true
                                        webSettings.mediaPlaybackRequiresUserGesture = false

                                        webViewClient = object : WebViewClient() {
                                            override fun shouldOverrideUrlLoading(
                                                view: WebView,
                                                request: WebResourceRequest
                                            ): Boolean {
                                                val websiteUrl = request.url.toString()

                                                return when {
                                                    websiteUrl.startsWith("mailto:") -> {
                                                        try {
                                                            val mailIntent =
                                                                Intent(Intent.ACTION_SENDTO).apply {
                                                                    data = Uri.parse(websiteUrl)
                                                                }
                                                            context.startActivity(mailIntent)
                                                        } catch (e: ActivityNotFoundException) {
                                                            e.printStackTrace()
                                                        }
                                                        true
                                                    }

                                                    websiteUrl.startsWith("https://www.skyraan.com/") -> {
                                                        try {
                                                            val i = Intent(Intent.ACTION_VIEW)
                                                            i.data = Uri.parse(websiteUrl)
                                                            context.startActivity(i)
                                                        } catch (e: ActivityNotFoundException) {
                                                            e.printStackTrace()
                                                        }
                                                        true
                                                    }

                                                    else -> false
                                                }
                                            }

                                            override fun onPageStarted(
                                                view: WebView?,
                                                url: String?,
                                                favicon: Bitmap?
                                            ) {
                                                super.onPageStarted(view, url, favicon)
                                                webViewFail.value = false
                                                errorMessage = ""
                                            }

                                            override fun onReceivedError(
                                                view: WebView?,
                                                request: WebResourceRequest?,
                                                error: WebResourceError?
                                            ) {
                                                super.onReceivedError(view, request, error)
                                                errorMessage =
                                                    "Page Load Error: ${error?.description}"
                                            }

                                            override fun onReceivedSslError(
                                                view: WebView?,
                                                handler: android.webkit.SslErrorHandler?,
                                                error: android.net.http.SslError?
                                            ) {
                                                super.onReceivedSslError(view, handler, error)
                                                errorMessage = "SSL Error: ${error?.primaryError}"
                                                webViewFail.value = true
                                                handler?.cancel()
                                            }
                                        }

                                        this.webChromeClient = object : WebChromeClient() {

                                            override fun onProgressChanged(
                                                view: WebView,
                                                newProgress: Int
                                            ) {
                                                progressState.intValue = newProgress
                                            }

                                            override fun onShowFileChooser(
                                                webView: WebView?,
                                                filePathCallback: ValueCallback<Array<Uri?>>?,
                                                fileChooserParams: FileChooserParams?
                                            ): Boolean {

                                                mFilePathCallback1?.invoke(null)
                                                mFilePathCallback1 = null

                                                mFilePathCallback1 = { uris ->
                                                    if (uris != null) {
                                                        filePathCallback?.onReceiveValue(uris)
                                                    } else {
                                                        filePathCallback?.onReceiveValue(null)
                                                    }
                                                    mFilePathCallback1 = null
                                                }

                                                try {

                                                    galleryLauncher.launch(arrayOf("*/*"))
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                    mFilePathCallback1?.invoke(null)
                                                    mFilePathCallback1 = null
                                                    return false
                                                }

                                                return true
                                            }
                                        }

                                        loadUrl(url)
                                    }
                                },
                                modifier = Modifier.background(Color.White),
                                update = { webView ->
                                    val backgroundColor = 0xFFFFFFFF.toInt()
                                    webView.setBackgroundColor(backgroundColor)
                                }
                            )

                            if (progressState.intValue < 100) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.background(Color.Transparent),
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )

    if (mediaAskPermission.value)
        PermissionAskForMedia()

    BackHandler {
        keyboardController?.hide()
        navHostController.popBackStack()
    }
}

var mediaAskPermission= mutableStateOf(false)
@Composable
fun PermissionAskForMedia() {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = { mediaAskPermission.value = false },
        modifier = Modifier.width( 300.dp),
        title = {
            Text(
                text = "Allow access to Gallery?",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = FontFamily.Default,
                fontSize =  16.scaledSp
            )
        },

        text = {
            Text(
                text = "Allow this app to access photos/Videos for feedback purpose.",
                color = Color.Black,
                fontSize =  14.scaledSp,
                fontFamily = FontFamily.Default,
                )
        },
        confirmButton = {
            androidx.compose.material3.TextButton(onClick = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                val uri: Uri = Uri.fromParts("package", constants.activity.packageName, null)
                intent.data = uri
                constants.activity.startActivity(intent)
                mediaAskPermission.value = false
            },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = newBlue,
                    contentColor = Color.White
                )) {
                Text(
                    text = "Allow",
                    fontSize = 14.scaledSp,
                    color = Color.White, fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Default,
                )
            }
        },
        dismissButton = {
            androidx.compose.material3.TextButton(
                onClick = {
                    mediaAskPermission.value = false
                },
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black

                )
            ) {
                Text(
                    text = "Don't Allow",
                    fontSize = 14.scaledSp,
                    color = Color.Black,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        shape = RoundedCornerShape(10.dp),
        containerColor = Color.White,
    )

}
