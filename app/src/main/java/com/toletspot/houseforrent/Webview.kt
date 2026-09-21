 package com.toletspot.houseforrent

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController

var urlFailed = mutableStateOf(false)
var HelpAndSupportWebView : WebView? = null
val privacy_page =  mutableStateOf(0)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpAndSupportWebview(navHostController: NavHostController) {

    val context = LocalContext.current
//            if (aboutUSWebView != null && aboutUSWebView?.canGoBack() == true) { "#BF4049"} else {"#FFFFFF"})
    val network_status = remember { mutableStateOf(checkForInternet(context)) }
    val faqurl = ""




    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Scaffold (
            topBar = {
                Column(modifier = Modifier
                )
                {
                    val scope = rememberCoroutineScope()
                    val stateIsGood = rememberLazyListState()

//                    scope.launch {
//                        stateIsGood.animateScrollToItem(privacy_page.value, -200)
//                    }

                    CenterAlignedTopAppBar(
                        title = {
                            //
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                           Color.White),
                        navigationIcon = {
//                            .noRipplenoRippleClickable{
//                            ClickHelper
//                                .getInstance()
//                                .clickOnce {
//                                    if (HelpAndSupportWebView != null && HelpAndSupportWebView?.canGoBack() == true) {
//                                        HelpAndSupportWebView?.goBack()
//                                    } else
//                                    {
//                                        if (Utils.suspend_Logout_Flag.value == 5) {
//                                            localClear()
//                                            Utils.navHostController.navigate(NavScreens.SignUp_Login_Screen.route)
//                                        } else {
//                                            getSubPlan_Initial = false
//                                            navHostController.popBackStack()
//                                        }
//                                    }
//                                }
//                        }
                        }
                    )

                }
            },
            content = {
                    paddingValues ->

                Column(modifier = Modifier.padding(top = paddingValues.calculateTopPadding())
                    .fillMaxSize())
                {
                    if (!network_status.value) {
//                        NoInternet(onClick = {
//                            network_status.value = if(checkForInternet(Utils.activity)) true else false
//                        })
                    } else
                    {
                        if(urlFailed.value)
                        {
                            //ApiFailUI({urlFailed.value = false})
                        }
                        else
                        {
                            WebViewScreen(url = faqurl)
                        }
                    }
                }
            },
            containerColor= Color.White
        )
    }



    BackHandler {

        if (HelpAndSupportWebView != null && HelpAndSupportWebView?.canGoBack() == true) {
            HelpAndSupportWebView?.goBack()
        } else {
//            println("ELSEeeeee---$HelpAndSupportWebView")
//            if(Utils.suspend_Logout_Flag.value == 5)
//            {
//                localClear()
//                Utils.navHostController.navigate(NavScreens.SignUp_Login_Screen.route)
//            }
//            else
//            {
//                getSubPlan_Initial = false
//                navHostController.popBackStack()
//            }
        }


    }

}

@Composable
fun WebViewScreen(url: String) {
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var progressstate by remember { mutableStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(factory = { context ->
            WebView(context).apply {

                HelpAndSupportWebView = this
                settings.javaScriptEnabled = true
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                val webSettings = this.settings
                webSettings.javaScriptEnabled = true
                this.webViewClient = WebViewClient()
                WebView.setWebContentsDebuggingEnabled(false)



                settings.javaScriptEnabled = true

                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView,
                        request: WebResourceRequest
                    ): Boolean {
                        val url = request.url.toString()
                        return if (url.startsWith("mailto:")) {
                            try {
                                val mailIntent = Intent(Intent.ACTION_SENDTO).apply {
                                    data = Uri.parse(url)
                                }
                                context.startActivity(mailIntent)
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
                        urlFailed.value = false
                        errorMessage = null
                    }
                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        super.onReceivedError(view, request, error)
                        errorMessage = "Page Load Error: ${error?.description}"
                        urlFailed.value = true
                        println("Error111--${errorMessage}")
                    }

                    override fun onReceivedSslError(
                        view: WebView?,
                        handler: android.webkit.SslErrorHandler?,
                        error: android.net.http.SslError?
                    ) {
                        println("SSlError")
                        super.onReceivedSslError(view, handler, error)
                        errorMessage = "SSL Error: ${error?.primaryError}"
                        urlFailed.value = true
                        handler?.cancel()
                    }
                }

                webChromeClient = object : WebChromeClient() {
                    override fun onProgressChanged(view: WebView, newProgress: Int) {
                        progressstate = newProgress
                    }

                }

                loadUrl(url)
            }
        })

        if (progressstate != 100) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Black)
            }
        }
    }
}