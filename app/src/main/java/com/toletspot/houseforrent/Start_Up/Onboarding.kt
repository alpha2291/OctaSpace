package com.toletspot.houseforrent.Start_Up

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.toletspot.houseforrent.AppPreferences
import com.toletspot.houseforrent.Navigation.UserCredentialsScreenFlow
import com.toletspot.houseforrent.R
import com.toletspot.houseforrent.constants
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.toletspot.houseforrent.noRippleClickable
import com.toletspot.houseforrent.ui.theme.newPurpleGradient
import com.toletspot.houseforrent.ui.theme.newPurpleGradientBorder
import kotlinx.coroutines.launch

data class OnboardingData(
    var image: Int,
    var text : String,
    var text2 : String,
)

@Composable
fun Onboarding(navController: NavHostController) {

    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()

    val pages = listOf(
        OnboardingData(
            image = R.raw.buyhome,
            text = "Where comfort lives",
            text2 = "Find spaces that suit your lifestyle, making the process simple, smooth, and enjoyable."
        ),
        OnboardingData(
            image = R.raw.searchland,
            text = "Talk to owners",
            text2 = "Chat with owners or brokers to discuss property details and availability"
        ),
        OnboardingData(
            image = R.raw.buildings,
            text = "Smart choices. Better living",
            text2 = "Save spaces you love, request property photos, and choose the perfect space."
        )
    )

    var allowPagerScroll = remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(7f)
                   , userScrollEnabled = allowPagerScroll.value
        ) { page ->
            OnboardingImagePage(pages[page], allowPagerScroll)
        }

        OnboardingTextSection(
            data = pages[pagerState.currentPage],
            modifier = Modifier.weight(2f),

        )

        constants.spacer(4)

        PageIndicator(pagerState.currentPage)

        Spacer(Modifier.height(12.dp))

        BottomButtons(
            page = pagerState.currentPage,
            modifier = Modifier.weight(2f),
            onNext = {
                scope.launch {
                    if (pagerState.currentPage < 2) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    } else {

                    }
                }
            },
            onSkip = {
                AppPreferences.save_Onboarding_Completed(true)
                navController.navigate(UserCredentialsScreenFlow.UserCredentials.route)
            }
        )

        Spacer(Modifier.height(18.dp))
    }
}

@Composable
fun OnboardingImagePage(
    data: OnboardingData,
    allowPagerScroll: MutableState<Boolean>
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(data.image))

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        LottieAnimation(
            composition = composition,
            modifier = Modifier.fillMaxSize(),
            iterations = LottieConstants.IterateForever
        )
    }
}

@Composable
fun OnboardingTextSection2(data: OnboardingData, modifier: Modifier = Modifier) {

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = data.text,
            color = Color(0xff323232),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = data.text2,
            color = Color(0xff575757),
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun OnboardingTextSection(
    data: OnboardingData,
    modifier: Modifier = Modifier,
) {
    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(data) {
        animatedProgress.snapTo(0f)
        animatedProgress.animateTo(
            1f,
            animationSpec = tween(durationMillis = 1200, easing = FastOutSlowInEasing)
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        , verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AnimatedTypingText(
            text = data.text,
            fontSize = 20.sp,
            color = Color(0xff323232),
            progress = animatedProgress.value,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        AnimatedTypingText(
            text = data.text2,
            fontSize = 16.sp,
            color = Color(0xff575757),
            progress = animatedProgress.value,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AnimatedTypingText(
    text: String,
    fontSize: TextUnit,
    color: Color,
    progress: Float,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign? = null
) {
    val visibleChars = (text.length * progress).toInt().coerceAtLeast(1)

    val shownText = text.take(visibleChars)

    Text(
        text = shownText,
        fontSize = fontSize,
        color = color,
        fontWeight = fontWeight,
        textAlign = textAlign,
        modifier = modifier.graphicsLayer {
            val scale = 0.9f + (progress * 0.1f)
            scaleX = scale
            scaleY = scale
            alpha = progress
        }
    )
}

@Composable
fun PageIndicator2(currentPage: Int, totalPages: Int = 3) {

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(totalPages) { index ->

            val isSelected = index == currentPage

            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .height(8.dp)
                    .animateContentSize()
                    .width(if (isSelected) 24.dp else 8.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(
                        if (isSelected) Color(0xff8856FF)
                        else Color(0xffD3D3D3)
                    )
            )
        }
    }
}

@Composable
fun PageIndicator(currentPage: Int, totalPages: Int = 3) {

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(totalPages) { index ->

            val isSelected = index == currentPage

            val width by animateDpAsState(
                targetValue = if (isSelected) 24.dp else 8.dp,
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            )

            val color by animateColorAsState(
                targetValue = if (isSelected) Color(0xff8856FF) else Color(0xffD3D3D3),
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .height(8.dp)
                    .width(width)
                    .clip(RoundedCornerShape(5.dp))
                    .background(color)
            )
        }
    }
}

@Composable
fun BottomButtons2(
    page: Int,
    modifier: Modifier,
    onNext: () -> Unit,
    onSkip: () -> Unit,

) {
    Box(
        modifier = modifier.background(Color.Red)
    ) {
        Box(
            modifier = modifier
                .align(Alignment.BottomCenter)
                .background(Color.Green)
                .fillMaxWidth()

                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.BottomCenter
        )
        {

            if (page != 2) {
                Text(
                    text = "Skip",
                    color = Color(0xff575757),
                    fontSize = 14.sp,
                    modifier = Modifier.align(Alignment.CenterStart).noRippleClickable {
                        onSkip()
                    }
                )
            } else {

            }

            Box(
                modifier = Modifier
                    .align(if (page == 2) Alignment.Center else Alignment.CenterEnd)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xff8856FF))
                    .clickable { onNext() }
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            )
            {

                if (page == 2) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Take Me In", color = Color.White, fontSize = 14.sp)
                        Spacer(Modifier.width(6.dp))
                        Image(
                            painter = painterResource(R.drawable.onboardinggooin),
                            contentDescription = "",

                            modifier = Modifier.size(20.dp)
                        )
                    }
                } else {
                    Text("Next", color = Color.White, fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
fun BottomButtons(
    page: Int,
    modifier: Modifier = Modifier,
    onNext: () -> Unit,
    onSkip: () -> Unit,
) {

    val fadeIn by animateFloatAsState(
        targetValue = if (page == 2) 1f else 0f,
        animationSpec = tween(300, easing = FastOutSlowInEasing)
    )

    val fadeOut by animateFloatAsState(
        targetValue = if (page != 2) 1f else 0f,
        animationSpec = tween(300, easing = FastOutSlowInEasing)
    )

    Box(
        modifier = modifier

            .padding(horizontal = 24.dp, vertical = 12.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {

        Box(
            modifier = Modifier

                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.CenterStart
        ) {
            if (page != 2) {
                Text(
                    text = "Skip",
                    color = Color(0xff575757).copy(alpha = fadeOut),
                    fontSize = 14.sp,
                    modifier = Modifier.noRippleClickable { onSkip() }
                )
            } else {

                Text(
                    text = "Skip",
                    color = Color.Transparent,
                    fontSize = 16.sp
                )
            }
        }

        Box(
            modifier = Modifier
                .align(if (page == 2) Alignment.BottomCenter else Alignment.BottomEnd)
                .clip(RoundedCornerShape(6.dp))
                .background(Brush.verticalGradient(newPurpleGradient))
                .border(1.dp , Brush.linearGradient(newPurpleGradientBorder) , RoundedCornerShape(6.dp))
                .noRippleClickable { onNext() }
                .padding(horizontal = 28.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {

            if (page != 2) {
                Text(
                    text = "Next",
                    color = Color.White.copy(alpha = fadeOut),
                    fontSize = 14.sp,
                    fontFamily = constants.fontFamily(0),
                    modifier = Modifier.graphicsLayer {
                        scaleX = 0.9f + fadeOut * 0.1f
                        scaleY = 0.9f + fadeOut * 0.1f
                    }
                )
            }

            if (page == 2) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .noRippleClickable {
                            onSkip()
                        }
                        .graphicsLayer {
                        alpha = fadeIn
                        scaleX = 0.9f + fadeIn * 0.1f
                        scaleY = 0.9f + fadeIn * 0.1f
                    }
                ) {
                    Text("Take Me In", color = Color.White, fontSize = 14.sp)

                    Spacer(Modifier.width(6.dp))

                    Image(
                        painter = painterResource(R.drawable.onboardinggooin),
                        contentDescription = "",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
