package com.toletspot.houseforrent

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.toletspot.houseforrent.Custom_Assets.API_Fail_UI
import com.toletspot.houseforrent.Custom_Assets.Backer
import kotlin.collections.isNotEmpty
import kotlin.collections.toTypedArray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Justify_Post(navHostController: NavHostController, postId: String)
{
    val webViewFail = remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current

    val network_status = remember { mutableStateOf(checkForInternet(context)) }

    val url = MainActivity.getPostReportUrl()
        .plus("user_id=${AppPreferences.getUserId()}")
        .plus("&user_post_id=${postId}")
        .plus("&device_type=1")

    val progressState = remember { mutableIntStateOf(0) }
    val keyboardController = LocalSoftwareKeyboardController.current
    Scaffold(
        modifier = Modifier.background(Color.Transparent),

        topBar = {
            CenterAlignedTopAppBar(
                title = {

                }
                , navigationIcon = {
                    Backer(modifier = Modifier) {
                        navHostController.navigateUp()
                    }
                },
            )
        }
        , content = {
                paddingValue ->

            val galleryLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.OpenMultipleDocuments(),
                onResult = { uris ->
                    if (uris != null && uris.isNotEmpty()) {
                        mFilePathCallback1?.invoke(uris.toTypedArray())
                    } else {
                        mFilePathCallback1?.invoke(null)
                    }

                }
            )

            Column(Modifier
                .padding(top = paddingValue.calculateTopPadding())
                .fillMaxSize()
                .background(Color.White)) {
                if (!network_status.value)
                {

                } else
                {
                    if(webViewFail.value)
                    {
                        API_Fail_UI {
                            webViewFail.value = false
                        }
                    }
                    else
                    {
                        Box {
                            class JSInterface {
                                @JavascriptInterface
                                fun closeModal(scrollhide: String) {
                                    if(scrollhide == "formModel"){
                                        navHostController.navigateUp()
                                    }
                                }
                            }
                            AndroidView(factory = {
                                WebView(it).apply {
                                    layoutParams = ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT
                                    )
                                    webViewClient = WebViewClient()
                                    val webSettings = this.settings
                                    webSettings.javaScriptEnabled = true
                                    addJavascriptInterface(JSInterface(), "AndroidInterface")

                                    WebView.setWebContentsDebuggingEnabled(false)
                                    webSettings.allowContentAccess = true
                                    settings.allowFileAccess = true
                                    settings.domStorageEnabled = true
                                    settings.useWideViewPort = true
                                    settings.loadWithOverviewMode = true
                                    settings.mediaPlaybackRequiresUserGesture = false

                                    webViewClient = object : WebViewClient() {
                                        override fun shouldOverrideUrlLoading(
                                            view: WebView,
                                            request: WebResourceRequest
                                        ): Boolean
                                        {
                                            var websiteUrl = request.url.toString()

                                            return if (websiteUrl.startsWith("mailto:")) {
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
                                            } else if (websiteUrl.startsWith("https://www.skyraan.com/")) {
                                                try {
                                                    val i = Intent(Intent.ACTION_VIEW)
                                                    i.setData(Uri.parse(websiteUrl))
                                                    context.startActivity(i)

                                                } catch (e: ActivityNotFoundException) {

                                                    e.printStackTrace()
                                                }
                                                true
                                            } else {
                                                false
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
                                            errorMessage = "Page Load Error: ${error?.description}"

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
                                            view: WebView, newProgress: Int
                                        ) {
                                            progressState.intValue = newProgress
                                        }

                                        override fun onShowFileChooser(
                                            webView: WebView?,
                                            filePathCallback: ValueCallback<Array<Uri?>>?,
                                            fileChooserParams: FileChooserParams?
                                        ): Boolean {

                                            mFilePathCallback1 = { uris ->
                                                if (uris != null) {
                                                    filePathCallback?.onReceiveValue(uris)
                                                } else {
                                                    filePathCallback?.onReceiveValue(null)
                                                }
                                                mFilePathCallback1 = null
                                            }
                                            galleryLauncher.launch(arrayOf("*/*"))

                                            return true
                                        }
                                    }
                                    loadUrl(url)
                                }
                            } ,
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
                                )
                                {
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

        })

    if(mediaAskPermission.value)
        PermissionAskForMedia()

    BackHandler {
        keyboardController!!.hide()
        navHostController.popBackStack()
    }
}
