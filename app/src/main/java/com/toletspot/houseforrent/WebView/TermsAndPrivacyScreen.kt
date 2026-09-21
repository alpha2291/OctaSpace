package com.toletspot.houseforrent.WebView

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
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.toletspot.houseforrent.Custom_Assets.API_Fail_UI
import com.toletspot.houseforrent.Custom_Assets.Backer
import com.toletspot.houseforrent.Custom_Assets.ClickHelper
import com.toletspot.houseforrent.MainActivity
import com.toletspot.houseforrent.NetworkStatus
import com.toletspot.houseforrent.R
import com.toletspot.houseforrent.checkForInternet
import com.toletspot.houseforrent.constants
import com.toletspot.houseforrent.rememberNetworkStatus
import com.toletspot.houseforrent.ui.theme.newBlack


var aboutUSWebView : WebView? = null
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsAndPrivacyScreen(navHostController: NavHostController)
{
    val context = LocalContext.current

    val webViewFail = remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf("") }

    val progressState = remember { mutableIntStateOf(0) }

    val network_status = remember { mutableStateOf(checkForInternet(context)) }

    var main_url =
        when(constants.Common_H_ViewModel.webView_Type)
        {
            0 -> MainActivity.getTermsUrl()
            1 -> MainActivity.getPrivacyUrl()
            2 -> MainActivity.getFAQUrl()
            else -> MainActivity.getdiscalimerUrl()
        }


    var main_name = when(constants.Common_H_ViewModel.webView_Type)
    {
        0 -> "Terms And Conditions"
        1 -> "Privacy Policy"
        2 -> "FAQ"
        else -> "Disclaimer"
    }

    val network = rememberNetworkStatus()


    val url = main_url

    Scaffold(
        modifier = Modifier.background(Color.Transparent),

        topBar = {
            Column(modifier = Modifier) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = main_name,
                            color = newBlack,
                            fontSize = constants.textUnit(24),
                            fontFamily = constants.fontFamily(0),
                            modifier = Modifier
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(Color.White),
                    navigationIcon = {

                        Backer(Modifier) {
                            ClickHelper
                                .getInstance()
                                .clickOnce {
                                    if (aboutUSWebView != null && aboutUSWebView?.canGoBack() == true) {
                                        aboutUSWebView?.goBack()
                                    } else {
                                        navHostController.navigateUp()
                                        constants.Profile_ViewModel.onSet_Settings_Click(-1)
                                    }

                                }
                        }
                    }
                )
            }
        },
        content = {
                paddingValues ->

            Column(modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .fillMaxSize()
                .background(Color.White))
            {
                if ( network.value == NetworkStatus.Offline ) {

                    /// constants.activity.getString(R.string.no_Internet)
                    //GlobalSnackbar.show("It Seems your are offline !!.Refresh again")
                    Column(
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .fillMaxSize(), verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(painterResource(R.drawable.nointernerdesign), "")

                        Spacer(modifier = Modifier.padding(16.dp))

                        Text(
                            constants.activity.getString(R.string.no_Internet),
                            modifier = Modifier
                                .padding(horizontal = 56.dp)
                            , color = newBlack,fontSize = constants.textUnit(16)
                            , fontFamily = constants.fontFamily(0)
                            , textAlign = TextAlign.Center
                        )

                    }
                }
                else
                {
                    if(webViewFail.value)
                    {
                        Column(
                            modifier = Modifier
                                .padding(top = 24.dp)
                                .fillMaxSize()
                            , verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            API_Fail_UI({
                                webViewFail.value = false
                            })
                        }
                    }
                    else
                    {
                        Box {
                            AndroidView(factory = {
                                WebView(it).apply {

                                    aboutUSWebView = this
                                    layoutParams = ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT
                                    )
                                    webViewClient = WebViewClient()
                                    val webSettings = this.settings
                                    webSettings.javaScriptEnabled = true

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
                                        ): Boolean {
                                            websiteUrl = request.url.toString()

                                            return if (websiteUrl.startsWith("mailto:")) {
                                                try {
                                                    val mailIntent = Intent(Intent.ACTION_SENDTO).apply {
                                                        data = Uri.parse(websiteUrl)
                                                    }
                                                    context.startActivity(mailIntent)
                                                } catch (e: ActivityNotFoundException) {
                                                    // Handle the case where no email app is available
                                                    e.printStackTrace()
                                                }
                                                true // Indicate the URL was handled
                                            }
                                            else if (websiteUrl.startsWith("https://www.skyraan.com/")) {
                                                try {

                                                    println("Website URL ${websiteUrl}")

                                                    val i = Intent(Intent.ACTION_VIEW)
                                                    i.setData(Uri.parse(websiteUrl))
                                                    context.startActivity(i)

                                                } catch (e: ActivityNotFoundException) {
                                                    // Handle the case where no email app is available
                                                    e.printStackTrace()
                                                }
                                                true // Indicate the URL was handled
                                            }
                                            else {
                                                false // Let WebView handle the URL
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
                                            webViewFail.value = true
                                            println("Error111--${errorMessage}")
                                        }

                                        override fun onReceivedSslError(
                                            view: WebView?,
                                            handler: android.webkit.SslErrorHandler?,
                                            error: android.net.http.SslError?
                                        ) {
                                            super.onReceivedSslError(view, handler, error)
                                            errorMessage = "SSL Error: ${error?.primaryError}"
                                            webViewFail.value = true
                                            handler?.cancel() // Prevent loading the page
                                        }
                                    }


                                    this.webChromeClient = object : WebChromeClient() {

                                        override fun onProgressChanged(
                                            view: WebView, newProgress: Int
                                        ) {
                                            progressState.intValue = newProgress
                                        }

                                    }

                                    loadUrl(url)
                                }
                            })


                            BackHandler {

                                if (aboutUSWebView != null && aboutUSWebView?.canGoBack() == true) {
                                    aboutUSWebView?.goBack()
                                } else {
                                    navHostController.navigateUp()
                                    constants.Profile_ViewModel.onSet_Settings_Click(-1)
                                }

                            }

                            if (progressState.intValue != 100) {
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
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
        }
    )
}