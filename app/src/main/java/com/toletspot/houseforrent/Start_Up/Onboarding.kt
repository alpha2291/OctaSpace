package com.toletspot.houseforrent.Start_Up

//import androidx.compose.ui.draw.paint
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
            image = R.drawable.onboardone,
            text = "Where comfort lives",
            text2 = "Find spaces that suit your lifestyle, making the process simple, smooth, and enjoyable."
        ),
        OnboardingData(
            image = R.drawable.onboardtwo,
            text = "Talk to owners",
            text2 = "Chat with owners or brokers to discuss property details and availability"
        ),
        OnboardingData(
            image = R.drawable.onboardthree,
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

        //-------------------------
        // TOP IMAGE PAGER
        //-------------------------
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(7f)
                   , userScrollEnabled = allowPagerScroll.value     // 🔥 disable when zoomed
        ) { page ->
            OnboardingImagePage(pages[page], allowPagerScroll)
        }

        //-------------------------
        // PAGE TEXT
        //-------------------------
        OnboardingTextSection(
            data = pages[pagerState.currentPage],
            modifier = Modifier.weight(2f),

        )

        constants.spacer(4)

        //-------------------------
        // INDICATOR (STATIC POSITION)
        //-------------------------
        PageIndicator(pagerState.currentPage)



        Spacer(Modifier.height(12.dp))

        //-------------------------
        // BOTTOM BUTTONS (NEVER MOVE)
        //-------------------------
        BottomButtons(
            page = pagerState.currentPage,
            modifier = Modifier.weight(2f),
            onNext = {
                scope.launch {
                    if (pagerState.currentPage < 2) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    } else {
                        // navigate to next screen
                       // navController.navigate("home")
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
    println("ttttt--111---${data}")
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(data.image),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}


@Composable
fun OnboardingTextSection2(data: OnboardingData, modifier: Modifier = Modifier) {
    println("ttttt--222---${data}")

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
            val scale = 0.9f + (progress * 0.1f)  // Slight scale pop effect
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
//                .fillMaxHeight()
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.BottomCenter
        )
        {

            // LEFT SIDE (Skip)
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
                //Spacer(modifier = Modifier.width(48.dp)) // keeps layout stable
            }

            // RIGHT BUTTON
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
//                        tint = Color.White,
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

    // Animate appearance of "Take Me In"
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
            //.background(Color.Green)
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {

        // ---------- LEFT SIDE (Skip) ----------
        Box(
            modifier = Modifier
                //.background(Color.Red)
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
                // Keep space stable but invisible
                Text(
                    text = "Skip",
                    color = Color.Transparent,
                    fontSize = 16.sp
                )
            }
        }

        // ---------- RIGHT SIDE BUTTON ----------
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

            // "Next"
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

            // "Take Me In" + icon
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





/*
@Composable
fun OnboardingPage(page: Int, onNext: () -> Unit, data: OnboardingData, pagerState: PagerState, ) {


    Column(modifier = Modifier
        .fillMaxSize()
        , verticalArrangement = Arrangement.SpaceBetween
        , horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(6f)
            , contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(data.image),
                contentDescription = null,
                contentScale = ContentScale.FillBounds, // fills nicely
                modifier = Modifier.matchParentSize() // ensures it covers whole Box
            )
            //LottiAnimation2(page)
        }

        Column(
            modifier = Modifier
                .weight(3f)
                .fillMaxWidth(.8f)
            , verticalArrangement = Arrangement.Center
            , horizontalAlignment = Alignment.CenterHorizontally
        ) {
            constants.spacer(4)

            CommonText(
                data.text,
                Color(0xff323232),
                20,
                0
            )

            constants.spacer(4)

            CommonText(
                data.text2,
                Color(0xff575757),
                16,
                1
            )

            constants.spacer(4)

        }

        constants.spacer(4)

        /// page indicator
        PageIndicator(pagerState = pagerState)

        constants.spacer(4)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
            , verticalAlignment = Alignment.CenterVertically
            , horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            if (page != 2) {
                CommonText(
                    "Skip",
                    Color(0xff575757),
                    14,
                    1
                )


                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .border(
                            1.dp,
                            Brush.linearGradient(newPurpleGradientBorder),
                            RoundedCornerShape(6.dp)
                        )
                        .background(Brush.verticalGradient(newPurpleGradient))
                        .noRippleClickable {
                            onNext()
                        }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CommonText(
                        "Next",
                        Color.White,
                        14,
                        1
                    )
                }
            }
            else {
                Row (
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .border(
                            1.dp,
                            Brush.linearGradient(newPurpleGradientBorder),
                            RoundedCornerShape(6.dp)
                        )
                        .background(Brush.verticalGradient(newPurpleGradient))
                        .noRippleClickable {
                            onNext()
                        }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                    , horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CommonText(
                        "Take Me In",
                        Color.White,
                        14,
                        1
                    )

                    Image(painter = painterResource(R.drawable.onboardinggooin) , "",
                        modifier = Modifier.size(24.dp))
                }
            }
        }
    }
}


@Composable
fun PageIndicator(pagerState: PagerState) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(pagerState.pageCount) { index ->
            val size = if (pagerState.currentPage == index) 10.dp else 6.dp
            val color = if (pagerState.currentPage == index) newBlue else Color(0xFFBEBEBE)

            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(size)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}




@Composable
fun LottiAnimation2(isWhichAnimation : Int){


    val loadAnimation =  when (isWhichAnimation){
        0 -> R.raw.buyhome
        1 -> R.raw.searchland
        else -> R.raw.buildings
    }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(loadAnimation))

    var visible by remember { mutableStateOf(true) }



    Box(
        modifier = Modifier
        , contentAlignment = Alignment.Center
    ){
        LottieAnimation(
            composition = composition,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
            , reverseOnRepeat = true,
            iterations = LottieConstants.IterateForever
        )

    }
}

*/


