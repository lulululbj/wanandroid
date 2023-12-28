package luyao.wanandroid.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import luyao.wanandroid.R

/**
 * Description:
 * Author: luyao
 * Date: 2023/11/15 13:24
 */
@Composable
fun SplashPage(onFinish: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.android_logo))
        val animationState = animateLottieCompositionAsState(composition = composition)
        LottieAnimation(composition = composition, progress = { animationState.progress })
        if (animationState.isAtEnd && animationState.isPlaying) {
            onFinish()
        }
    }
}

@Preview
@Composable
fun SplashPagePreview() {
    SplashPage {}
}