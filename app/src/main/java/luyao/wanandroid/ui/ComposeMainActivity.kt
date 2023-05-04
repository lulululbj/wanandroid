package luyao.wanandroid.ui

import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.accompanist.themeadapter.material3.Mdc3Theme
import dagger.hilt.android.AndroidEntryPoint
import luyao.wanandroid.base.BaseVMActivity
import luyao.wanandroid.navigation.WanandroidPage
import luyao.wanandroid.ui.splash.SplashPage

/**
 * Description:
 * Author: luyao
 * Date: 2022/7/15 15:48
 */
@AndroidEntryPoint
class ComposeMainActivity : BaseVMActivity() {

    override fun initView() {

//        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {

            val systemUiController = rememberSystemUiController()
            val useDarkIcons = MaterialTheme.colors.isLight

            SideEffect {
                // Update all of the system bar colors to be transparent, and use
                // dark icons if we're in light theme
                systemUiController.setSystemBarsColor(
                    color = Color.White,
                    darkIcons = useDarkIcons
                )
            }

            Mdc3Theme {
                AppScreen()
            }
        }
    }

    override fun initData() {
    }

    override fun startObserve() {
    }
}

@Composable
fun AppScreen() {

    val colorPrimary = MaterialTheme.colors.primary
    var showSplash by remember {
        mutableStateOf(true)
    }

    if (showSplash) {
        SplashPage {
            showSplash = false
        }
    } else {
        val systemUiController = rememberSystemUiController()
        val useDarkIcons = !isSystemInDarkTheme()

        DisposableEffect(systemUiController, useDarkIcons) {
            systemUiController.setSystemBarsColor(
                color = colorPrimary,
                darkIcons = useDarkIcons
            )
            onDispose { }
        }
        WanandroidPage()
    }
}
