package com.toletspot.houseforrent.Start_Up

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.toletspot.houseforrent.API.API_Result_Handling
import com.toletspot.houseforrent.AppPreferences
import com.toletspot.houseforrent.Custom_Assets.Backer
import com.toletspot.houseforrent.Custom_Assets.ClickHelper
import com.toletspot.houseforrent.Custom_Assets.Common_Popup
import com.toletspot.houseforrent.Custom_Assets.LoginSwitchText
import com.toletspot.houseforrent.Custom_Assets.OTP_TF
import com.toletspot.houseforrent.Custom_Assets.OTP_TF_6
import com.toletspot.houseforrent.Custom_Assets.PhoneNumberInput
import com.toletspot.houseforrent.Custom_Assets.SignSwitchText
import com.toletspot.houseforrent.Custom_Assets.getDeviceId
import com.toletspot.houseforrent.Custom_Assets.getDeviceType
import com.toletspot.houseforrent.Custom_Assets.rememberCountdownTimer
import com.toletspot.houseforrent.Custom_Assets.rememberNotchHeightDp
import com.toletspot.houseforrent.Custom_Assets.toast
import com.toletspot.houseforrent.Custom_Assets.verifyOtp
import com.toletspot.houseforrent.GlobalSnackbar
import com.toletspot.houseforrent.Home_Screen.ProfileModule.ClickGuard
import com.toletspot.houseforrent.Navigation.UserCredentialsScreenFlow
import com.toletspot.houseforrent.NetworkStatus
import com.toletspot.houseforrent.R
import com.toletspot.houseforrent.constants
import com.toletspot.houseforrent.constants.Companion.spacer
import com.toletspot.houseforrent.deviceToken
import com.toletspot.houseforrent.forTab
import com.toletspot.houseforrent.noRippleClickable
import com.toletspot.houseforrent.rememberNetworkStatus
import com.toletspot.houseforrent.ui.theme.newBlack
import com.toletspot.houseforrent.ui.theme.newBlue
import com.toletspot.houseforrent.ui.theme.newGray
import com.toletspot.houseforrent.ui.theme.newPurpleGradient
import com.toletspot.houseforrent.ui.theme.newPurpleGradientBorder
import com.toletspot.houseforrent.ui.theme.newWhite
import kotlinx.coroutines.delay

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun User_Credentialsls(navController: NavHostController, viewModel: Start_Up_ViewModel) {

   val isSignUp = viewModel.isLoginOrSignVerify.collectAsStateWithLifecycle()

   var notchPadding = rememberNotchHeightDp()
   val context = LocalContext.current

   var deactivated by remember { mutableStateOf(false) }
   var reactivate by remember { mutableStateOf(false) }
   var appeal_Sueccessful by remember { mutableStateOf(false) }
    var network = rememberNetworkStatus()
   val focusManager = LocalFocusManager.current

   var keyboardController = LocalSoftwareKeyboardController.current

   LaunchedEffect(isSignUp.value) {
      Log.d("AuthState", "State changed: ${isSignUp.value}")
   }

   Column(
      modifier = Modifier
          .fillMaxSize()
          .background(newBlue),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally
   )
   {
      Box(
         modifier = Modifier
             .fillMaxWidth()
             .weight(3f),
         contentAlignment = Alignment.Center
      )
      {
         if (isSignUp.value == 2) {

            if (forTab()){
               Spacer(modifier = Modifier.padding(16.dp))
            }

            Backer(
               modifier = Modifier
                   .align(Alignment.TopStart)
                   .padding(top = if (forTab()) 16.dp else notchPadding.value, start = 16.dp),
               onBackClick = {

                  viewModel.otp = ""
                  viewModel.phoneNumber = ""
                  viewModel.updateLoginState(0)

               }
            )
         }

         AnimatedContent(
            targetState = isSignUp.value,
            transitionSpec = {
               when (targetState) {
                  0 -> slideInHorizontally(tween(800)) { -it } with ExitTransition.None
                  1 -> slideInHorizontally(tween(800)) { it } with ExitTransition.None
                  2 -> slideInVertically(tween(800)) { it } with ExitTransition.None
                  else -> EnterTransition.None with ExitTransition.None
               }
            },
            label = "AuthSwitch"
         ) { targetState ->
            when (targetState) {
               0 -> {
                  SubcomposeAsyncImage(
                     model = R.drawable.login,
                     modifier = Modifier.size(if (forTab())160.dp else 100.dp),
                     contentDescription = ""
                  ) {
                     val state = painter.state
                     if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                        Box(
                           modifier = Modifier
                               .fillMaxSize()
                               .padding(8.dp)
                        ) {
                           Image(
                              painter = painterResource(id = R.drawable.ic_launcher_foreground),
                              contentDescription = "",
                              modifier = Modifier.matchParentSize()
                           )
                        }
                     } else {
                        SubcomposeAsyncImageContent()
                     }
                  }
               }
               1 -> {
                  SubcomposeAsyncImage(
                     model = R.drawable.register,
                     modifier = Modifier.size(if (forTab())160.dp else 100.dp),
                     contentDescription = ""
                  ) {
                     val state = painter.state
                     if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                        Box(
                           modifier = Modifier
                               .fillMaxSize()
                               .padding(8.dp)
                        ) {
                           Image(
                              painter = painterResource(id = R.drawable.ic_launcher_foreground),
                              contentDescription = "",
                              modifier = Modifier.matchParentSize()
                           )
                        }
                     } else {
                        SubcomposeAsyncImageContent()
                     }
                  }
               }
               2 -> {
                  SubcomposeAsyncImage(
                     model = R.drawable.verify,
                     modifier = Modifier.size(if (forTab())160.dp else 100.dp),
                     contentDescription = ""
                  ) {
                     val state = painter.state
                     if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                        Box(
                           modifier = Modifier
                               .fillMaxSize()
                               .padding(8.dp)
                        ) {
                           Image(
                              painter = painterResource(id = R.drawable.ic_launcher_foreground),
                              contentDescription = "",
                              modifier = Modifier.matchParentSize()
                           )
                        }
                     } else {
                        SubcomposeAsyncImageContent()
                     }
                  }
               }
            }
         }
      }

      LazyColumn(
         modifier = Modifier
             .fillMaxWidth()
             .weight(if (forTab()) 5f else 7f)
             .clip(RoundedCornerShape(topStart = 56.dp, topEnd = 56.dp))
             .background(newWhite),
         verticalArrangement = Arrangement.Top,
         horizontalAlignment = Alignment.CenterHorizontally
      )
      {

         item {

            Box(
               modifier = Modifier
                   .padding(top = if (forTab()) 56.dp else 0.dp)
                   .fillParentMaxSize()
                   .background(newWhite),
               contentAlignment = Alignment.Center
            ) {
               when (isSignUp.value) {
                  0 -> Login_Content(
                     onLoginClicked = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        AppPreferences.save_User_Verify_Otp("")
                        if (network.value == NetworkStatus.Online) {
                           constants.API_Vm.user_Login(
                              phone_num = viewModel.phoneNumber,
                              phone_num_cc = viewModel.get_Country_Code(),
                              device_id = getDeviceId(constants.activity),
                              device_type = getDeviceType(),
                              device_token = deviceToken,
                           )
                           { apiResultHandling ->
                              when (apiResultHandling) {
                                 is API_Result_Handling.Loading -> {
                                    constants.Common_H_ViewModel.changeStatus(true)
                                 }
                                 is API_Result_Handling.Deactivated -> {

                                    constants.Common_H_ViewModel.changeStatus(false)

                                    when {
                                       apiResultHandling.code == "2" ->   deactivated = true
                                       apiResultHandling.code == "3" ->   reactivate = true
                                    }
                                 }
                                 is API_Result_Handling.NoData -> {
                                    constants.Common_H_ViewModel.changeStatus(false)
                                    toast("Something went wrong, No Records found")
                                 }
                                 is API_Result_Handling.Error -> {
                                    keyboardController?.hide()
                                    constants.Common_H_ViewModel.changeStatus(false)
                                    toast(apiResultHandling.message)
                                 }
                                 is API_Result_Handling.Success -> {
                                    constants.Common_H_ViewModel.changeStatus(false)

                                    keyboardController?.hide()
                                    viewModel.updateLoginState(2)
                                    viewModel.is_Error_OTP_Reset()
                                    reactivate = false

                                    viewModel.otp = ""

                                 }
                              }
                           }
                        } else {
                           toast("It Seems your are offline !!.Refresh again")
                        }
                     },
                     modifier = Modifier.fillMaxSize(),
                     viewModel,
                     keyboardController
                  )

                  1 -> SignUp_Content(
                     onSignClicked = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        AppPreferences.save_User_Verify_Otp("")
                        if (network.value == NetworkStatus.Online) {

                           constants.API_Vm.user_Register(
                              name = viewModel.userName,
                              phone_num = viewModel.phoneNumber,
                              device_id = getDeviceId(constants.activity),
                              device_type = getDeviceType(),
                              device_token = deviceToken,
                              phone_num_cc = viewModel.get_Country_Code(),
                           )
                           { apiResultHandling ->
                              when (apiResultHandling) {
                                 is API_Result_Handling.Loading -> {

                                    constants.Common_H_ViewModel.changeStatus(true)
                                 }
                                 is API_Result_Handling.Deactivated -> {

                                 }

                                 is API_Result_Handling.NoData -> {

                                    constants.Common_H_ViewModel.changeStatus(false)
                                    toast("Something went wrong , No Records found")
                                 }

                                 is API_Result_Handling.Error -> {

                                    constants.Common_H_ViewModel.changeStatus(false)
                                    toast(apiResultHandling.message)
                                 }

                                 is API_Result_Handling.Success -> {

                                    constants.Common_H_ViewModel.changeStatus(false)

                                    viewModel.updateLoginState(2)

                                 }
                              }
                           }
                        } else {
                           toast("It Seems your are offline !!.Refresh again")
                        }
                     },
                     modifier = Modifier.fillMaxSize(),
                     viewModel,
                     keyboardController
                  )

                  2 -> Verify_Content(
                     onVerifyCLick = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        if (network.value == NetworkStatus.Online) {

                           constants.API_Vm.verify_OTP(
                               user_id = AppPreferences.getUserId(),
                               phone_num = viewModel.phoneNumber,
                               whatsapp_num = "",
                               email = "",
                               otp = viewModel.otp,

                               phone_num_cc = viewModel.get_Country_Code(),
                               whatsapp_num_cc = "",
                               device_id = getDeviceId(constants.activity),
                               device_type = "Android",
                               device_token = deviceToken,
                           )
                           { apiResultHandling ->
                              when (apiResultHandling) {
                                 is API_Result_Handling.Loading -> {

                                    constants.Common_H_ViewModel.change_Verify_Status(true)
                                 }
                                 is API_Result_Handling.Deactivated -> {

                                 }

                                 is API_Result_Handling.NoData -> {

                                    constants.Common_H_ViewModel.change_Verify_Status(false)
                                    toast("Something went wrong , No Records found")
                                 }

                                 is API_Result_Handling.Error -> {

                                    constants.Common_H_ViewModel.change_Verify_Status(false)
                                    toast(apiResultHandling.message)
                                 }

                                 is API_Result_Handling.Success -> {

                                    constants.Common_H_ViewModel.change_Verify_Status(false)
                                    toast("Success , verified")
                                    if (AppPreferences.get_Interest_Completed() == 0 || AppPreferences.get_Location_Received() == 0){

                                       AppPreferences.save_Verify_Complete(1)
                                       navController.navigate(UserCredentialsScreenFlow.UserInterests.route)
                                    }
                                    else {
                                       AppPreferences.save_Verify_Complete(1)
                                       UserCredentialsScreenFlow.Common_Screen.route
                                       constants.API_Vm.isLoading_Reels = true
                                       constants.API_Vm.totalPages_Reels = 1
                                       navController.navigate(UserCredentialsScreenFlow.Common_Screen.route)
                                    }

                                 }
                              }
                           }

                        } else {
                           toast("It Seems your are offline !!.Refresh again")
                        }
                     },
                     modifier = Modifier.fillMaxSize(),
                     viewModel,
                     keyboardController
                  )
               }
            }
         }
      }
   }

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

            Text("Account Restricted"
               , color = Color.Black
               , fontSize = constants.textUnit(16)
               , fontFamily = constants.fontFamily(0)
            )

            Text("Your account has been reported multiple times for violating our community standards. Your account has been temporarily restricted. You can appeal this decision if you believe it was a mistake."
               , color = Color(0xff484848)
               , fontSize = constants.textUnit(12)
               , fontFamily = constants.fontFamily(3)
               , modifier = Modifier.padding(horizontal = if (forTab()) 36.dp else 16.dp)
            )

             spacer(2)

            Box(
               modifier = Modifier
                   .height(32.dp)
                   .fillMaxWidth(.9f)
                   .background(newBlue)
                   .noRippleClickable {
                       navController.navigate(UserCredentialsScreenFlow.Justify.route)
                   }
               , contentAlignment = Alignment.Center
            ){
               Text("Disagree with Decision"
                  , color = Color.White
                  , fontSize = constants.textUnit(14)
                  , fontFamily = constants.fontFamily(0)
               )
            }

            Text("Cancel"
               , color = Color(0xff484848)
               , fontSize = constants.textUnit(14)
               , fontFamily = constants.fontFamily(0)
               , modifier = Modifier.noRippleClickable{
                  deactivated = false
               }
            )

           spacer(8)

         }
      }
   )

   Common_Popup(
      visible = reactivate,
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

            Text("Account Deletion in Progress"
               , color = Color.Black
               , fontSize = constants.textUnit(16)
               , fontFamily = constants.fontFamily(0)
            )

            Text("You requested to delete your account. It’s still within the 30-day deletion period. If you log in now, your deletion request will be canceled, and your account will stay active."
               , color = Color(0xff484848)
               , fontSize = constants.textUnit(12)
               , fontFamily = constants.fontFamily(3)
               , modifier = Modifier.padding(horizontal = 16.dp)
            )

             spacer(2)
            Box(
               modifier = Modifier
                   .height(32.dp)
                   .fillMaxWidth(.9f)
                   .background(newBlue)
                   .noRippleClickable {
                       ClickHelper.getInstance().clickOnce {
                           if (ClickGuard.canClick()) {

                               constants.API_Vm.Put_account_Activate_Deactivate(
                                   user_id = AppPreferences.getUserId(),
                                   account_delete_type = "",
                                   account_delete_sentence = "",
                                   status = 2,
                               )
                               { aPI_Result_Handling ->
                                   when (aPI_Result_Handling) {
                                       is API_Result_Handling.Error -> {
                                           toast("Something Went Wrong")
                                       }

                                       is API_Result_Handling.Deactivated -> {

                                       }

                                       is API_Result_Handling.Loading -> {}
                                       is API_Result_Handling.Success -> {
                                           constants.API_Vm.user_Login(
                                               phone_num = viewModel.phoneNumber,
                                               phone_num_cc = viewModel.get_Country_Code(),
                                               device_id = getDeviceId(constants.activity),
                                               device_type = getDeviceType(),
                                               device_token = deviceToken,
                                           )
                                           { apiResultHandling ->
                                               when (apiResultHandling) {
                                                   is API_Result_Handling.Loading -> {
                                                       constants.Common_H_ViewModel.changeStatus(
                                                           true
                                                       )
                                                   }

                                                   is API_Result_Handling.Deactivated -> {

                                                       constants.Common_H_ViewModel.changeStatus(
                                                           false
                                                       )
                                                       deactivated = true
                                                   }

                                                   is API_Result_Handling.NoData -> {
                                                       constants.Common_H_ViewModel.changeStatus(
                                                           false
                                                       )
                                                       toast("Something went wrong, No Records found")
                                                   }

                                                   is API_Result_Handling.Error -> {
                                                       constants.Common_H_ViewModel.changeStatus(
                                                           false
                                                       )
                                                       toast(apiResultHandling.message)
                                                   }

                                                   is API_Result_Handling.Success -> {
                                                       reactivate = false
                                                       constants.Common_H_ViewModel.changeStatus(
                                                           false
                                                       )

                                                       viewModel.updateLoginState(2)
                                                       viewModel.is_Error_OTP_Reset()

                                                       viewModel.otp = ""

                                                   }
                                               }
                                           }
                                       }

                                       is API_Result_Handling.NoData -> {

                                       }
                                   }
                               }
                           }
                       }
                   }
               , contentAlignment = Alignment.Center
            ){
               Text("Reactivate Account"
                  , color = Color.White
                  , fontSize = constants.textUnit(14)
                  , fontFamily = constants.fontFamily(0)
               )
            }

            Text("Cancel"
               , color = Color(0xff484848)
               , fontSize = constants.textUnit(14)
               , fontFamily = constants.fontFamily(0)
               , modifier = Modifier.noRippleClickable{
                  reactivate = false
               }
            )

           spacer(8)
         }
      }
   )

   Common_Popup(
      visible = appeal_Sueccessful,
      modifier = Modifier.background(Color(0xffF7F0DC)),
      image = "",
      icon = R.drawable.appealsubmitted,
      userName = "",
      content = {
         Column (
            modifier = Modifier
            , verticalArrangement = Arrangement.spacedBy(8.dp)
            , horizontalAlignment = Alignment.CenterHorizontally
         ){
            Text("Appeal Already Submitted"
               , color = Color.Black
               , fontSize = constants.textUnit(16)
               , fontFamily = constants.fontFamily(0)
            )
            Text(" Your appeal has been received. Our team will review it and update you at the email address you provided ${viewModel.email}"
               , color = Color(0xff484848)
               , fontSize = constants.textUnit(12)
               , fontFamily = constants.fontFamily(3)
            )

            Box(
               modifier = Modifier
                   .height(32.dp)
                   .fillMaxWidth(.9f)
               , contentAlignment = Alignment.Center
            ){
               Text("Okay"
                  , color = Color.White
                  , fontSize = constants.textUnit(14)
                  , fontFamily = constants.fontFamily(0)
               )
            }

           spacer(8)
         }
      }
   )

}

@Composable
fun Login_Content(
   onLoginClicked: () -> Unit,
   modifier: Modifier,
   viewModel: Start_Up_ViewModel,
   keyboardController: SoftwareKeyboardController?
) {
   val status = constants.Common_H_ViewModel.status.collectAsState()
   var showError by remember { mutableStateOf(false) }

   val countryCodes = constants.Start_Up_ViewModel.country_Code_Handler.collectAsState()

   val selectedCountry = countryCodes.value.find {
      it.country_Code == viewModel.countryCode
   }

   val expectedLimit = selectedCountry?.limit ?: 10

   Column(
      modifier = modifier
          .padding(horizontal = if (forTab()) 56.dp else 0.dp)
          .fillMaxSize(),
      verticalArrangement = Arrangement.spacedBy(8.dp)
   ) {

      spacer(20)

      Text(
         viewModel._isLoginOrSign.first().title,
         textAlign = TextAlign.Center,
         fontSize = constants.textUnit(24),
         color = newBlue,
         fontFamily = constants.fontFamily(0),
         modifier = Modifier.align(Alignment.CenterHorizontally)
      )

      Text(
         viewModel._isLoginOrSign.first().description,
         textAlign = TextAlign.Center,
         fontSize = constants.textUnit(15),
         fontFamily = constants.fontFamily(2),
         modifier = Modifier
             .padding(horizontal = 48.dp)
             .align(Alignment.CenterHorizontally)
      )

      spacer(16)

      Text(
         viewModel._isLoginOrSign.first().cont_No,
         textAlign = TextAlign.Start,
         fontSize = constants.textUnit(15),
         fontFamily = constants.fontFamily(0),
         modifier = Modifier.padding(horizontal = 16.dp)
      )

      PhoneNumberInput(
         phoneNumber = viewModel.phoneNumber,
         onPhoneNumberChange = { viewModel.phoneNumber = it },
         countryCode = viewModel.countryCode,
         onCountryCodeChange = {
            viewModel.phoneNumber = ""
            viewModel.countryCode = it
            viewModel.put_Country_Code(it)
         },
         isError = showError
      )

     spacer(8)

      Box(
         modifier = Modifier
             .fillMaxWidth(.9f)
             .height(56.dp)
             .clickable(enabled = !status.value) {
                 ClickHelper.getInstance().clickOnce {

                     if (viewModel.phoneNumber.length != expectedLimit) {
                         showError = true
                     } else {
                         showError = false
                         AppPreferences.save_ph_number(viewModel.phoneNumber)
                         onLoginClicked()
                     }
                 }
             }
             .align(Alignment.CenterHorizontally)
             .background(newBlue, RoundedCornerShape(8.dp)),
         contentAlignment = Alignment.Center
      ) {
         if (status.value) {
            CircularProgressIndicator(color = Color.White)
         } else {
            Text(
               viewModel._isLoginOrSign.first().title,
               color = newWhite,
               fontSize = constants.textUnit(15),
               fontFamily = constants.fontFamily(0)
            )
         }
      }

      Spacer(modifier = Modifier.padding(60.dp))

      SignSwitchText(
         onSignClick = {
            viewModel.userName = ""
            viewModel.phoneNumber = ""
            constants.Start_Up_ViewModel.phoneNumber = ""
            viewModel.updateLoginState(1)
         },
         modifier = Modifier.align(Alignment.CenterHorizontally)
      )
   }
}

@Composable
fun SignUp_Content(
   onSignClicked: () -> Unit,
   modifier: Modifier,
   viewModel: Start_Up_ViewModel,
   keyboardController: SoftwareKeyboardController?
) {
   val status = constants.Common_H_ViewModel.status.collectAsState()
   var showError by remember { mutableStateOf(false) }

   val countryCodes = constants.Start_Up_ViewModel.country_Code_Handler.collectAsState()

   val selectedCountry = countryCodes.value.find {
      it.country_Code == viewModel.countryCode
   }

   val expectedLimit = selectedCountry?.limit ?: 10

   LaunchedEffect(Unit) {
      viewModel.phoneNumber = ""
   }

   Column(
      modifier = modifier
          .padding(horizontal = if (forTab()) 56.dp else 0.dp)
          .fillMaxSize(),
      verticalArrangement = Arrangement.spacedBy(8.dp)
   ) {
      spacer(16)

      Text(
         viewModel._isLoginOrSign.last().title,
         textAlign = TextAlign.Center,
         fontSize = constants.textUnit(24),
         fontFamily = constants.fontFamily(0),
         color = newBlue,
         modifier = Modifier.align(Alignment.CenterHorizontally)
      )

      Text(
         viewModel._isLoginOrSign.last().description,
         textAlign = TextAlign.Center,
         fontSize = constants.textUnit(15),
         fontFamily = constants.fontFamily(2),
         modifier = Modifier
             .align(Alignment.CenterHorizontally)
             .padding(horizontal = 24.dp)
      )

     spacer(8)

      Text(
         viewModel._isLoginOrSign.last().user_Name,
         textAlign = TextAlign.Start,
         fontSize = constants.textUnit(15),
         fontFamily = constants.fontFamily(0),
         modifier = Modifier.padding(horizontal = 16.dp)
      )

      TextField(
         value = viewModel.userName,
         onValueChange = {
            if (it.length <= 20) {
               viewModel.userName = it
            }
         },
         placeholder = { Text("Enter Name", fontFamily = constants.fontFamily(0),fontSize = constants.textUnit(12)) },
         textStyle = TextStyle(
            fontFamily = constants.fontFamily(0),
         ),
         modifier = Modifier
             .padding(horizontal = 16.dp)
             .align(Alignment.CenterHorizontally)
             .fillMaxWidth()
             .border(1.dp, newGray, RoundedCornerShape(8.dp)),
         keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
         singleLine = true,
          maxLines = 1,
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

     spacer(8)

      Text(
         viewModel._isLoginOrSign.last().cont_No,
         textAlign = TextAlign.Start,
         fontSize = constants.textUnit(15),
         fontFamily = constants.fontFamily(0),
         modifier = Modifier.padding(horizontal = 16.dp)
      )

      PhoneNumberInput(
         phoneNumber = viewModel.phoneNumber,
         onPhoneNumberChange = { viewModel.phoneNumber = it },
         countryCode = viewModel.countryCode,
         onCountryCodeChange = {
            viewModel.phoneNumber = ""
            viewModel.countryCode = it
            viewModel.put_Country_Code(it)
         },
         isError = showError
      )

     spacer(8)

      Box(
         modifier = Modifier
             .fillMaxWidth(.9f)
             .height(56.dp)
             .align(Alignment.CenterHorizontally)
             .clickable(enabled = !status.value) {
                 ClickHelper.getInstance().clickOnce {
                     when {
                         viewModel.userName.isEmpty() -> {
                             GlobalSnackbar.show("Enter your full name")
                         }

                         viewModel.phoneNumber.isEmpty() -> {
                             showError = true
                             GlobalSnackbar.show("Enter your mobile number")
                         }

                         viewModel.phoneNumber.length != expectedLimit -> {
                             showError = true
                             GlobalSnackbar.show("Invalid mobile number for ${viewModel.countryCode}")
                         }

                         else -> {
                             showError = false
                             onSignClicked()
                         }
                     }
                 }
             }
             .background(newBlue, RoundedCornerShape(8.dp)),
         contentAlignment = Alignment.Center
      ) {
         if (status.value) {
            CircularProgressIndicator(color = Color.White)
         } else {
            Text(
               viewModel._isLoginOrSign.last().title,
               color = newWhite,
               fontSize = constants.textUnit(14)
            )
         }
      }

      Spacer(modifier = Modifier.fillMaxHeight(.1f))

      LoginSwitchText(
         onLoginClick = {
            viewModel.userName = ""
            viewModel.phoneNumber = ""
            viewModel.updateLoginState(0)
         },
         modifier = Modifier.align(Alignment.CenterHorizontally)
      )
   }
}

@Composable
fun Verify_Content(
   onVerifyCLick: () -> Unit,
   modifier: Modifier,
   viewModel: Start_Up_ViewModel,
   keyboardController: SoftwareKeyboardController?
) {
   val time = rememberCountdownTimer(180)
   val isError = viewModel.error_OTP.collectAsState()
   val status = constants.Common_H_ViewModel.status.collectAsState()
   var network = rememberNetworkStatus()
   val isResend_Loading = remember { mutableStateOf(false) }

   Column(
      modifier = modifier.fillMaxSize(),
      verticalArrangement = Arrangement.spacedBy(8.dp)
   ) {
     spacer(8)

      Text(
         "Verification",
         textAlign = TextAlign.Center,
         fontSize = constants.textUnit(24),
         color = newBlue,
         fontFamily = constants.fontFamily(0),
         modifier = Modifier.align(Alignment.CenterHorizontally)
      )

     spacer(8)

      Text(
         constants.activity.getString(R.string.verify_desc),
         textAlign = TextAlign.Center,
         fontSize = constants.textUnit(14),
         modifier = Modifier
             .padding(horizontal = 16.dp)
             .align(Alignment.CenterHorizontally)
      )

     spacer(8)

      OTP_TF(
         otp = viewModel.otp,
         onOtpChange = {
            viewModel.otp = it

         },
         modifier = Modifier.align(Alignment.CenterHorizontally),
         isError = isError.value
      )

      spacer(8)

      Row(
         modifier = Modifier
             .align(Alignment.CenterHorizontally)
             .wrapContentSize()
             .clip(RoundedCornerShape(4.dp))
             .border(1.dp, newGray, RoundedCornerShape(4.dp))
             .padding(vertical = 4.dp, horizontal = 4.dp),
         verticalAlignment = Alignment.CenterVertically,
         horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
         SubcomposeAsyncImage(
            model = R.drawable.otptimer,
            modifier = Modifier.size(16.dp),
            contentDescription = ""
         ) {
            val state = painter.state
            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
               Box(
                  modifier = Modifier
                      .fillMaxSize()
                      .padding(8.dp)
               ) {
                  Image(
                     painter = painterResource(id = R.drawable.ic_launcher_foreground),
                     contentDescription = "",
                     modifier = Modifier.matchParentSize()
                  )
               }
            } else {
               SubcomposeAsyncImageContent()
            }
         }

         Text(time.time.value, fontSize = constants.textUnit(14), color = newBlue)
      }

     spacer(8)

      verifyOtp(
         resendOTP = {
            AppPreferences.save_User_Verify_Otp("")
            if (network.value == NetworkStatus.Online) {

               constants.API_Vm.user_Login(
                  phone_num = viewModel.phoneNumber,
                  phone_num_cc = viewModel.get_Country_Code(),
                  device_id = getDeviceId(constants.activity),
                  device_type = getDeviceType(),
                  device_token = deviceToken,
               )
               { apiResultHandling ->
                  when (apiResultHandling) {
                     is API_Result_Handling.Loading -> {

                        isResend_Loading.value = true
                     }

                     is API_Result_Handling.Deactivated -> {

                     }

                     is API_Result_Handling.NoData -> {

                        constants.Common_H_ViewModel.changeStatus(false)
                        toast("Something went wrong , No Records found")
                     }

                     is API_Result_Handling.Error -> {

                        toast("Resend OTP Failed to Initiate!")
                     }

                     is API_Result_Handling.Success -> {

                        isResend_Loading.value = false
                        viewModel.is_Error_OTP_Reset()
                        viewModel.otp = ""
                        time.restart()
                     }
                  }
               }
            } else {
               toast("It Seems your are offline !!.Refresh again")
            }

         },
         isTimerFinished = time.isFinished.value,
         modifier = Modifier.align(Alignment.CenterHorizontally),
         isResend_Loading
      )

     spacer(8)

      Box(
         modifier = Modifier
             .fillMaxWidth(.9f)
             .height(56.dp)
             .clickable(enabled = !status.value) {
                 ClickHelper
                     .getInstance()
                     .clickOnce {
                         if (viewModel.otp.isNotEmpty()) {
                             onVerifyCLick()
                         } else {
                             viewModel.is_Error_OTP()
                             GlobalSnackbar.show("Enter the OTP Received")
                         }
                     }
             }
             .align(Alignment.CenterHorizontally)
             .background(newBlue, RoundedCornerShape(8.dp)),
         contentAlignment = Alignment.Center
      ) {
         if (status.value) {
            CircularProgressIndicator(color = Color.White)
         } else {
            Text("Verify", color = newWhite, fontSize = constants.textUnit(14))
         }
      }
   }
}

@Composable
fun User_Credentials(navController: NavHostController, viewModel: Start_Up_ViewModel){

    var index = remember { mutableStateOf(0) }

    var deactivated = remember { mutableStateOf(false) }
    var reactivate = remember { mutableStateOf(false) }
    var appeal_Sueccessful by remember { mutableStateOf(false) }

    var selectedCountry = constants.Start_Up_ViewModel.selectedCountryVm.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.reset_selectedCountry()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffFCFCFC))
            .graphicsLayer { clip = false },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)

                .zIndex(1f)
            , verticalArrangement = Arrangement.Center
            , horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            Image(painter = painterResource(R.drawable.dummylogo) ,"")

            spacer(8)

            AnimatedContent(
                targetState = index.value,
                modifier = Modifier
                    .background(Color.White)
                    .graphicsLayer { clip = false },
                transitionSpec = {

                    if (index.value <= 1) {

                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> -fullWidth }
                        ) + fadeIn(animationSpec = tween(300)) togetherWith
                                slideOutHorizontally(
                                    targetOffsetX = { fullWidth -> fullWidth }
                                ) + fadeOut(animationSpec = tween(300))

                    } else {

                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> fullWidth }
                        ) + fadeIn(animationSpec = tween(300)) togetherWith
                                slideOutHorizontally(
                                    targetOffsetX = { fullWidth -> -fullWidth }
                                ) + fadeOut(animationSpec = tween(300))
                    }
                },
                contentAlignment = Alignment.Center,
                label = "Card Slide Animation"
            ) { targetIndex ->

                LaunchedEffect(targetIndex) {
                    delay(200)
                }

                val rotation by animateFloatAsState(
                    targetValue = if (targetIndex == index.value) 0f else -12f,
                    animationSpec = tween(600, easing = FastOutSlowInEasing),
                    label = "rotation"
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .wrapContentHeight()
                        .padding(20.dp)
                        .graphicsLayer {
                            rotationZ = rotation
                            cameraDistance = 12f * density
                            clip = false
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.elevatedCardElevation(20.dp)
                    ) {

                                when (targetIndex) {
                                    0 -> Login_Rento(
                                        index,
                                        viewModel,
                                        navController,
                                        deactivated,
                                        reactivate,
                                    )

                                    1 -> SignUp_Rento(
                                        index,
                                        viewModel,
                                        navController,
                                    )

                                    2 -> Verify_Rento(index, viewModel, navController)
                                }

                    }
                }
            }

        }

        Image(
            painter = painterResource(R.drawable.credientialsbottom)
            , ""
            , contentScale = ContentScale.FillWidth ,
            modifier = Modifier
                .zIndex(0f)
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }

    Common_Popup(
        visible = deactivated.value,
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

                Image(painter = painterResource(R.drawable.accountrestrictedrento) , "",
                    modifier = Modifier.size(64.dp))

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
                    , textAlign = TextAlign.Center
                    , modifier = Modifier.padding(horizontal = if (forTab()) 36.dp else 16.dp)
                )

                spacer(2)

                Box(
                    modifier = Modifier
                        .height(32.dp)
                        .fillMaxWidth(.9f)
                        .background(Brush.verticalGradient(newPurpleGradient))
                        .noRippleClickable {
                            navController.navigate(UserCredentialsScreenFlow.Justify.route)
                        }
                    , contentAlignment = Alignment.Center
                ){
                    Text("Disagree"
                        , color = Color.White
                        , fontSize = constants.textUnit(14)
                        , fontFamily = constants.fontFamily(0)
                    )
                }

                constants.spacer(2)

                Text("Cancel"
                    , color = Color(0xff484848)
                    , fontSize = constants.textUnit(14)
                    , fontFamily = constants.fontFamily(0)
                    , modifier = Modifier.noRippleClickable{
                        deactivated.value = false
                    }
                )

                spacer(8)

            }
        }
    )

    Common_Popup(
        visible = reactivate.value,
        modifier = Modifier.background(Color(0xffFCEDEC)),
        image = "",
        icon = 0,

        userName = "",
        content = {
            Column (
                modifier = Modifier
                , verticalArrangement = Arrangement.spacedBy(8.dp)
                , horizontalAlignment = Alignment.CenterHorizontally
            ){

                spacer(2)

                Image(painter = painterResource(R.drawable.accoundeletedrento) , "",
                    modifier = Modifier.size(64.dp))

                spacer(2)

                Text("Account Reactivation"
                    , color = Color.Black
                    , fontSize = constants.textUnit(16)
                    , fontFamily = constants.fontFamily(0)
                )

                Text("You've asked to delete your account, which is still in the 30-day period. Logging in now will cancel your request and keep your account active."
                    , color = Color(0xff484848)
                    , fontSize = constants.textUnit(12)
                    , fontFamily = constants.fontFamily(3)
                    , textAlign = TextAlign.Center
                    , modifier = Modifier.padding(horizontal = 16.dp)
                )

                spacer(4)

                Box(
                    modifier = Modifier
                        .height(46.dp)
                        .fillMaxWidth(.9f)
                        .background(Brush.verticalGradient(newPurpleGradient))
                        .noRippleClickable {
                            ClickHelper.getInstance().clickOnce {
                                if (ClickGuard.canClick()) {

                                    constants.API_Vm.Put_account_Activate_Deactivate(
                                        user_id = AppPreferences.getUserId(),
                                        account_delete_type = "",
                                        account_delete_sentence = "",
                                        status = 2,
                                    )
                                    { aPI_Result_Handling ->
                                        when (aPI_Result_Handling) {
                                            is API_Result_Handling.Error -> {
                                                toast("Something Went Wrong")
                                            }

                                            is API_Result_Handling.Deactivated -> {

                                            }

                                            is API_Result_Handling.Loading -> {}
                                            is API_Result_Handling.Success -> {
                                                constants.API_Vm.user_Login(
                                                    phone_num = viewModel.phoneNumber,
                                                    phone_num_cc = selectedCountry.value?.dial_code?:"",
                                                    device_id = getDeviceId(constants.activity),
                                                    device_type = getDeviceType(),
                                                    device_token = deviceToken,
                                                )
                                                { apiResultHandling ->
                                                    when (apiResultHandling) {
                                                        is API_Result_Handling.Loading -> {
                                                            constants.Common_H_ViewModel.changeStatus(
                                                                true
                                                            )
                                                        }

                                                        is API_Result_Handling.Deactivated -> {

                                                            constants.Common_H_ViewModel.changeStatus(
                                                                false
                                                            )
                                                            deactivated.value = true
                                                        }

                                                        is API_Result_Handling.NoData -> {
                                                            constants.Common_H_ViewModel.changeStatus(
                                                                false
                                                            )
                                                            toast("Something went wrong, No Records found")
                                                        }

                                                        is API_Result_Handling.Error -> {
                                                            constants.Common_H_ViewModel.changeStatus(
                                                                false
                                                            )
                                                            toast(apiResultHandling.message)
                                                        }

                                                        is API_Result_Handling.Success -> {
                                                            reactivate.value = false
                                                            constants.Common_H_ViewModel.changeStatus(
                                                                false
                                                            )

                                                            AppPreferences.save_User_Verify_Otp("")
                                                            index.value = 2
                                                            viewModel.updateLoginState(2)
                                                            viewModel.is_Error_OTP_Reset()

                                                            viewModel.otp = ""

                                                        }
                                                    }
                                                }
                                            }

                                            is API_Result_Handling.NoData -> {

                                            }
                                        }
                                    }
                                }
                            }
                        }
                    , contentAlignment = Alignment.Center
                ){
                    Text("Reactivate Account"
                        , color = Color.White
                        , fontSize = constants.textUnit(14)
                        , fontFamily = constants.fontFamily(0)
                    )
                }

                spacer(2)

                Text("Cancel"
                    , color = Color(0xff484848)
                    , fontSize = constants.textUnit(14)
                    , fontFamily = constants.fontFamily(0)
                    , modifier = Modifier.noRippleClickable{
                        reactivate.value = false
                    }
                )

                spacer(8)
            }
        }
    )

    Common_Popup(
        visible = appeal_Sueccessful,
        modifier = Modifier.background(Color(0xffF7F0DC)),
        image = "",
        icon = R.drawable.appealsubmitted,
        userName = "",
        content = {
            Column (
                modifier = Modifier
                , verticalArrangement = Arrangement.spacedBy(8.dp)
                , horizontalAlignment = Alignment.CenterHorizontally
            ){

                Image(painter = painterResource(R.drawable.appealsuccessfulrento) , "",
                    modifier = Modifier.size(64.dp))

                Text("Appeal Already Submitted"
                    , color = Color.Black
                    , fontSize = constants.textUnit(16)
                    , fontFamily = constants.fontFamily(0)
                )
                Text(" Your appeal has been received. Our team will review it and update you at the email address you provided ${viewModel.email}"
                    , color = Color(0xff484848)
                    , fontSize = constants.textUnit(12)
                    , fontFamily = constants.fontFamily(3)
                )

                Box(
                    modifier = Modifier
                        .height(32.dp)
                        .fillMaxWidth(.9f)
                        .background(Brush.verticalGradient(newPurpleGradient))
                    , contentAlignment = Alignment.Center
                ){
                    Text("Got it!"
                        , color = Color.White
                        , fontSize = constants.textUnit(14)
                        , fontFamily = constants.fontFamily(0)
                    )
                }

                spacer(8)
            }
        }
    )
}

@Composable
fun Login_Rento(
    index: MutableState<Int>,
    viewModel: Start_Up_ViewModel,
    navController: NavHostController,
    deactivated: MutableState<Boolean>,
    reactivate: MutableState<Boolean>,
) {

    var errorInput = remember { mutableStateOf(false) }

    var network = rememberNetworkStatus()
    val focusManager = LocalFocusManager.current

    var keyboardController = LocalSoftwareKeyboardController.current

    val status = constants.Common_H_ViewModel.status.collectAsState()

    var country = viewModel.selectedCountryVm.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.reset_selectedCountry()
    }

    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .padding(horizontal = 16.dp)
        , verticalArrangement = Arrangement.spacedBy(12.dp)
    )
    {
        item {

            Column(
                modifier = Modifier.imePadding(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                spacer(8)

                Image(
                    painter = painterResource(R.drawable.loginheader),
                    "",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                spacer(4)

                CommonText(
                    "Welcome Back!",
                    newBlack,
                    20,
                    0,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                CommonText(
                    "Login to explore the best properties near you.",
                    Color(0xff575757),
                    12,
                    3,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                spacer(4)

                CommonText(
                    "Mobile number", newBlack, 14, 1
                )

                NumberInput_Rento(errorInput, viewModel)

                spacer(4)

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .height(56.dp)
                        .noRippleClickable(!status.value) {
                            if (viewModel.phoneNumber.isNotEmpty() && country.value?.limit == viewModel.phoneNumber.length) {

                                AppPreferences.save_User_Verify_Otp("")
                                if (network.value == NetworkStatus.Online) {
                                    constants.API_Vm.user_Login(
                                        phone_num = viewModel.phoneNumber,
                                        phone_num_cc = country.value?.dial_code ?: "",
                                        device_id = getDeviceId(constants.activity),
                                        device_type = getDeviceType(),
                                        device_token = deviceToken,
                                    )
                                    { apiResultHandling ->
                                        when (apiResultHandling) {
                                            is API_Result_Handling.Loading -> {
                                                constants.Common_H_ViewModel.changeStatus(true)
                                            }

                                            is API_Result_Handling.Deactivated -> {

                                                constants.Common_H_ViewModel.changeStatus(false)

                                                when {
                                                    apiResultHandling.code == "2" -> deactivated.value =
                                                        true

                                                    apiResultHandling.code == "3" -> reactivate.value =
                                                        true
                                                }

                                                AppPreferences.save_ph_number(viewModel.phoneNumber)
                                            }

                                            is API_Result_Handling.NoData -> {
                                                constants.Common_H_ViewModel.changeStatus(false)
                                                toast("Something went wrong, No Records found")
                                            }

                                            is API_Result_Handling.Error -> {
                                                keyboardController?.hide()
                                                constants.Common_H_ViewModel.changeStatus(false)
                                                toast(apiResultHandling.message)
                                            }

                                            is API_Result_Handling.Success -> {
                                                constants.Common_H_ViewModel.changeStatus(false)

                                                keyboardController?.hide()

                                                index.value = 2
                                                viewModel.is_Error_OTP_Reset()
                                                reactivate.value = false
                                                constants.Start_Up_ViewModel.currentCredintialState.value =
                                                    UserCredintialState.LOGIN

                                                viewModel.otp = ""

                                            }
                                        }
                                    }
                                } else {
                                    toast("It Seems your are offline !!.Refresh again")
                                }
                            } else {
                                errorInput.value = true
                            }

                            keyboardController?.hide()
                            focusManager.clearFocus()

                        }
                        .background(
                            Brush.verticalGradient(newPurpleGradient),
                            RoundedCornerShape(8.dp)
                        )
                        .border(
                            1.dp,
                            Brush.verticalGradient(newPurpleGradientBorder),
                            RoundedCornerShape(8.dp)
                        ), contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Login",
                        color = newWhite,
                        fontSize = constants.textUnit(15),
                        fontFamily = constants.fontFamily(0)
                    )

                }

                spacer(4)

                SignSwitchText(
                    onSignClick = {

                        viewModel.reset_selectedCountry()
                        constants.Common_H_ViewModel.change_Verify_Status(false)
                        viewModel.userName = ""
                        viewModel.phoneNumber = ""
                        constants.Start_Up_ViewModel.phoneNumber = ""
                        index.value = 1
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                spacer(4)
            }

        }

    }
}

@Composable
fun SignUp_Rento(
    index: MutableState<Int>,
    viewModel: Start_Up_ViewModel,
    navController: NavHostController,
    ) {

    var errorInput = remember { mutableStateOf(false) }
    var network = rememberNetworkStatus()
    val focusManager = LocalFocusManager.current

    var keyboardController = LocalSoftwareKeyboardController.current

    val status = constants.Common_H_ViewModel.status.collectAsState()

    var country = viewModel.selectedCountryVm.collectAsState()

    var errorName = remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .padding(horizontal = 16.dp)
        , verticalArrangement = Arrangement.spacedBy(   12.dp)
    )
    {
        item {

            Column(
                modifier = Modifier.imePadding()
                    .background(Color.White)

                , verticalArrangement = Arrangement.spacedBy(12.dp)
            )
            {

                spacer(8)

                Image(
                    painter = painterResource(R.drawable.signupheader),
                    "",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                spacer(4)

                CommonText(
                    "Find. Rent. Relax.",
                    newBlack,
                    20,
                    0,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                CommonText(
                    "Sign Up to Explore Spaces You’ll Love!",
                    Color(0xff575757),
                    12,
                    3,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                spacer(4)

                CommonText(
                    "Fullname", newBlack, 14, 1
                )

                Column() {
                    TextField(
                        value = viewModel.userName,
                        onValueChange = { input ->
                            errorName.value = false

                            val filtered = input
                                .replace(
                                    Regex("[^A-Za-z_ ]"),
                                    ""
                                )
                                .replace(Regex("\\s+"), " ")
                                .trimStart()

                            if (filtered.length <= 30) {
                                viewModel.userName = filtered
                            }
                        },
                        placeholder = {
                            CommonText(
                                "Enter your name", Color(0xffA7A7A7), 14, 1
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .border(
                                1.dp,
                                if (errorName.value) Color.Red else Color(0xffCECECE),
                                RoundedCornerShape(4.dp)
                            ),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )

                    if (errorName.value) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Image(
                                painter = painterResource(R.drawable.errorinforento), "",
                                modifier = Modifier.size(14.dp)
                            )

                            constants.spacer(4)

                            CommonText(
                                "Enter your fullname", Color.Red, 12, 3
                            )

                        }
                    }
                }

                spacer(4)

                CommonText(
                    "Mobile number", newBlack, 14, 1
                )

                NumberInput_Rento(errorInput, viewModel)

                spacer(4)

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .height(56.dp)
                        .noRippleClickable(!status.value) {
                            keyboardController?.hide()
                            focusManager.clearFocus()

                            when {

                                country.value?.limit != viewModel.phoneNumber.length && viewModel.userName.isEmpty() -> {
                                    errorName.value = true
                                    errorInput.value = true
                                }

                                country.value?.limit != viewModel.phoneNumber.length -> {
                                    errorInput.value = true
                                }

                                viewModel.userName.isEmpty() -> {
                                    errorName.value = true
                                }

                                else -> {
                                    AppPreferences.save_User_Verify_Otp("")
                                    if (network.value == NetworkStatus.Online) {

                                        constants.API_Vm.user_Register(
                                            name = viewModel.userName,
                                            phone_num = viewModel.phoneNumber,
                                            device_id = getDeviceId(constants.activity),
                                            device_type = getDeviceType(),
                                            device_token = deviceToken,
                                            phone_num_cc = country.value?.dial_code ?: "",
                                        )
                                        { apiResultHandling ->
                                            when (apiResultHandling) {
                                                is API_Result_Handling.Loading -> {

                                                    constants.Common_H_ViewModel.changeStatus(true)
                                                }

                                                is API_Result_Handling.Deactivated -> {

                                                }

                                                is API_Result_Handling.NoData -> {

                                                    constants.Common_H_ViewModel.changeStatus(false)
                                                    toast("Something went wrong , No Records found")
                                                }

                                                is API_Result_Handling.Error -> {

                                                    constants.Common_H_ViewModel.changeStatus(false)
                                                    toast(apiResultHandling.message)
                                                }

                                                is API_Result_Handling.Success -> {

                                                    constants.Common_H_ViewModel.changeStatus(false)

                                                    constants.Start_Up_ViewModel.currentCredintialState.value =
                                                        UserCredintialState.REGISTER
                                                    index.value = 2

                                                }
                                            }
                                        }
                                    } else {
                                        toast("It Seems your are offline !!.Refresh again")
                                    }
                                }

                            }

                        }
                        .background(
                            Brush.verticalGradient(newPurpleGradient),
                            RoundedCornerShape(8.dp)
                        )
                        .border(
                            1.dp,
                            Brush.verticalGradient(newPurpleGradientBorder),
                            RoundedCornerShape(8.dp)
                        ), contentAlignment = Alignment.Center
                ) {
                    Text(
                        "SignUp",
                        color = newWhite,
                        fontSize = constants.textUnit(15),
                        fontFamily = constants.fontFamily(0)
                    )

                }

                spacer(4)

                LoginSwitchText(
                    onLoginClick = {
                        viewModel.reset_selectedCountry()
                        constants.Common_H_ViewModel.change_Verify_Status(false)
                        viewModel.userName = ""
                        viewModel.phoneNumber = ""
                        constants.Start_Up_ViewModel.phoneNumber = ""
                        index.value = 0

                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                spacer(4)

            }

        }
    }
}

@Composable
fun Verify_Rento(
    index: MutableState<Int>,
    viewModel: Start_Up_ViewModel,
    navController: NavHostController
) {

    val time = rememberCountdownTimer(180)

    var network = rememberNetworkStatus()
    val focusManager = LocalFocusManager.current

    var keyboardController = LocalSoftwareKeyboardController.current

    var selectedCountryData = viewModel.selectedCountryVm.collectAsState()

    val otpError = viewModel.error_OTP.collectAsState()

    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .padding(horizontal = 16.dp)
        , verticalArrangement = Arrangement.spacedBy(8.dp)
    )
    {
        item {

            Column(
                modifier = Modifier
                    .imePadding()

            )
            {

                spacer(8)

                Image(
                    painter = painterResource(R.drawable.left_arrow),
                    "",
                    modifier = Modifier.noRippleClickable {
                        constants.Common_H_ViewModel.change_Verify_Status(false)
                        viewModel.is_Error_OTP_Reset()
                        viewModel.otp = ""
                        constants.Start_Up_ViewModel.currentCredintialState.value =
                            UserCredintialState.NONE
                        index.value = 0
                    }
                )

                Image(
                    painter = painterResource(R.drawable.verificationheader),
                    "",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                spacer(4)

                CommonText(
                    "Verification",
                    newBlack,
                    20,
                    0,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                CommonText(
                    "We sent a code to your entered mobile \n number ${viewModel.selectedCountryVm.value?.emoji} ${viewModel.selectedCountryVm.value?.dial_code} ${viewModel.phoneNumber} ",
                    newBlack,
                    12,
                    3,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                spacer(4)

                OTP_TF_6(
                    otp = viewModel.otp,
                    onOtpChange = {
                        viewModel.is_Error_OTP_Reset()
                        viewModel.otp = it
                    },
                    isError = otpError,
                    modifier = Modifier
                )

                if (otpError.value) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Image(
                            painter = painterResource(R.drawable.errorinforento),
                            "",
                            modifier = Modifier.size(14.dp),

                        )

                        constants.spacer(4)

                        CommonText("Verification code is expired or incorrect.", Color.Red, 12, 3)

                    }
                } else {
                    spacer(16)
                }

                spacer(4)

                if (!time.isFinished.value) {
                    Text(
                        "Resend Code in ${time.time.value}",
                        fontSize = constants.textUnit(14),
                        color = newBlack,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                } else {
                    Text(
                        "Resend Code",
                        fontSize = constants.textUnit(14),
                        color = newBlue,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                            .noRippleClickable {
                                AppPreferences.save_User_Verify_Otp("")
                                viewModel.otp = ""
                                if (network.value == NetworkStatus.Online) {

                                    if (constants.Start_Up_ViewModel.currentCredintialState.value == UserCredintialState.LOGIN) {
                                        constants.API_Vm.user_Login(
                                            phone_num = viewModel.phoneNumber,
                                            phone_num_cc = selectedCountryData.value?.dial_code
                                                ?: "",
                                            device_id = getDeviceId(constants.activity),
                                            device_type = getDeviceType(),
                                            device_token = deviceToken,
                                        )
                                        { apiResultHandling ->
                                            when (apiResultHandling) {
                                                is API_Result_Handling.Loading -> {

                                                }

                                                is API_Result_Handling.Deactivated -> {

                                                }

                                                is API_Result_Handling.NoData -> {

                                                    constants.Common_H_ViewModel.changeStatus(false)
                                                    toast("Something went wrong , No Records found")
                                                }

                                                is API_Result_Handling.Error -> {

                                                    toast("Resend OTP Failed to Initiate!")
                                                }

                                                is API_Result_Handling.Success -> {

                                                    viewModel.is_Error_OTP_Reset()
                                                    viewModel.otp = ""
                                                    time.restart()
                                                }
                                            }
                                        }
                                    } else {
                                        constants.API_Vm.user_Register(
                                            name = viewModel.userName,
                                            phone_num = viewModel.phoneNumber,
                                            device_id = getDeviceId(constants.activity),
                                            device_type = getDeviceType(),
                                            device_token = deviceToken,
                                            phone_num_cc = selectedCountryData.value?.dial_code
                                                ?: "",
                                        )
                                        { apiResultHandling ->
                                            when (apiResultHandling) {
                                                is API_Result_Handling.Loading -> {

                                                    constants.Common_H_ViewModel.changeStatus(true)
                                                }

                                                is API_Result_Handling.Deactivated -> {

                                                }

                                                is API_Result_Handling.NoData -> {

                                                    constants.Common_H_ViewModel.changeStatus(false)
                                                    toast("Something went wrong , No Records found")
                                                }

                                                is API_Result_Handling.Error -> {

                                                    constants.Common_H_ViewModel.changeStatus(false)
                                                    toast(apiResultHandling.message)
                                                }

                                                is API_Result_Handling.Success -> {

                                                    viewModel.is_Error_OTP_Reset()
                                                    viewModel.otp = ""
                                                    time.restart()

                                                }
                                            }
                                        }
                                    }
                                } else {
                                    toast("It Seems your are offline !!.Refresh again")
                                }

                            }
                    )
                }

                spacer(4)

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .height(56.dp)
                        .noRippleClickable {

                            keyboardController?.hide()
                            focusManager.clearFocus()
                            if (network.value == NetworkStatus.Online) {

                                constants.API_Vm.verify_OTP(
                                    user_id = AppPreferences.getUserId(),
                                    phone_num = viewModel.phoneNumber,
                                    whatsapp_num = "",
                                    email = "",
                                    otp = viewModel.otp,

                                    phone_num_cc = selectedCountryData.value?.dial_code ?: "",
                                    whatsapp_num_cc = "",
                                    device_id = getDeviceId(constants.activity),
                                    device_type = "Android",
                                    device_token = deviceToken,
                                )
                                { apiResultHandling ->
                                    when (apiResultHandling) {
                                        is API_Result_Handling.Loading -> {

                                            constants.Common_H_ViewModel.change_Verify_Status(true)
                                        }

                                        is API_Result_Handling.Deactivated -> {

                                        }

                                        is API_Result_Handling.NoData -> {

                                            constants.Common_H_ViewModel.change_Verify_Status(false)
                                            toast("Something went wrong , No Records found")
                                        }

                                        is API_Result_Handling.Error -> {

                                            viewModel.is_Error_OTP()

                                            constants.Common_H_ViewModel.change_Verify_Status(false)

                                            toast(apiResultHandling.message)
                                        }

                                        is API_Result_Handling.Success -> {
                                            constants.Start_Up_ViewModel.currentCredintialState.value =
                                                UserCredintialState.NONE

                                            constants.Common_H_ViewModel.change_Verify_Status(false)
                                            viewModel.is_Error_OTP_Reset()

                                            selectedCountryData.value?.let {
                                                AppPreferences.saveCountry(
                                                    it
                                                )

                                                constants.Start_Up_ViewModel.put_Country_Code(it.dial_code)
                                            }

                                            AppPreferences.save_ph_number(viewModel.phoneNumber)

                                            toast("Success , verified")
                                            if (AppPreferences.get_Interest_Completed() == 0 || AppPreferences.get_Location_Received() == 0) {

                                                AppPreferences.save_Verify_Complete(1)
                                                navController.navigate(UserCredentialsScreenFlow.UserInterests.route)
                                            } else {
                                                AppPreferences.save_Verify_Complete(1)
                                                UserCredentialsScreenFlow.Common_Screen.route
                                                constants.API_Vm.isLoading_Reels = true
                                                constants.API_Vm.totalPages_Reels = 1
                                                navController.navigate(UserCredentialsScreenFlow.Common_Screen.route)
                                            }

                                        }
                                    }
                                }

                            } else {
                                toast("It Seems your are offline !!.Refresh again")
                            }
                        }
                        .background(
                            Brush.verticalGradient(newPurpleGradient),
                            RoundedCornerShape(8.dp)
                        )
                        .border(
                            1.dp,
                            Brush.verticalGradient(newPurpleGradientBorder),
                            RoundedCornerShape(8.dp)
                        ), contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Verify",
                        color = newWhite,
                        fontSize = constants.textUnit(15),
                        fontFamily = constants.fontFamily(0)
                    )

                }

                spacer(16)

            }
        }
    }
}

@Composable
fun  NumberInput_Rento(
    errorInput: MutableState<Boolean>
    , viewModel: Start_Up_ViewModel
    ,isChecked : MutableState<Boolean> = mutableStateOf(false)
) {

    var showSheet by remember { mutableStateOf(false) }

    var selectedCountry = viewModel.selectedCountryVm.collectAsState()

    val context = LocalContext.current

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column() {
        TextField(
            value = viewModel.phoneNumber, onValueChange = {
                if (it.length <= selectedCountry?.value?.limit ?: 0) {
                    errorInput.value = false
                    viewModel.phoneNumber = it

                    if (viewModel.phoneNumber != it){
                        isChecked.value = false
                    }

                } else {
                    toast("This is the limit of number according to your country selection")
                }
            },
            placeholder = {
                CommonText("Enter your mobile number",
                    Color(0xffA7A7A7),
                    12,
                    3
                    )
            }

            , singleLine = true
            , leadingIcon = {
                Row(
                    modifier = Modifier
                        .noRippleClickable {
                            showSheet = true
                        }
                        .background(Color(0xffEBEBEB)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    spacer(4)

                        Text(
                            text = selectedCountry.value?.emoji ?: "",
                            fontSize = 20.sp,
                        )

                    spacer(4)

                    Image(painter = painterResource(R.drawable.arrowdown), "")

                    spacer(4)

                    VerticalDivider()
                }
            }
            , keyboardActions = KeyboardActions (
                onDone = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
            )
            , modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(
                    1.dp,
                    if (errorInput.value) Color.Red else Color(0xffCECECE),
                    RoundedCornerShape(4.dp)
                )
            , keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
            , colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        spacer(2)

        if (errorInput.value) {
            Row(
                verticalAlignment = Alignment.CenterVertically
                , horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(R.drawable.errorinforento),
                    "",
                    modifier = Modifier.size(14.dp)
                )

                constants.spacer(4)

                CommonText("Enter valid mobile number" , Color.Red,12 ,3)

            }
        }
    }

    CountryPickerBottomSheet(
        context = context,
        showSheet = showSheet,
        onDismiss = { showSheet = false },
        onSelect = {
            viewModel.add_selectedCountry(it) },
        viewModel
    )

}

@Composable
fun CommonText(
    text: String,
    color: Color,
    fontSize: Int,
    fontFamily: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = color,
        fontSize = constants.textUnit(fontSize),
        fontFamily = constants.fontFamily(fontFamily),
        modifier = modifier
    )
}
