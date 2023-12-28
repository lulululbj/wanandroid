package luyao.wanandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import luyao.ktx.model.UiState
import luyao.wanandroid.model.AppConfig
import luyao.wanandroid.model.DarkThemeConfig
import luyao.wanandroid.vm.SplashVM

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val splashVM by viewModels<SplashVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var appState: UiState<AppConfig> by mutableStateOf(UiState.Loading)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                splashVM.appState.onEach {
                    appState = it
                }.collect()
            }
        }

        splashScreen.setKeepOnScreenCondition {
            when (appState) {
                UiState.Loading -> true
                else -> false
            }
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val systemUiController = rememberSystemUiController()
            val darkTheme = shouldUseDarkTheme(appState)

            DisposableEffect(systemUiController, darkTheme) {
                systemUiController.systemBarsDarkContentEnabled = !darkTheme
                onDispose { }
            }

        }
    }
}

@Composable
fun shouldUseDarkTheme(uiState: UiState<AppConfig>) =
    when (uiState) {
        UiState.Loading -> isSystemInDarkTheme()
        is UiState.Success -> {
            when (uiState.data.darkThemeConfig) {
                DarkThemeConfig.LIGHT -> false
                DarkThemeConfig.DARK -> true
                DarkThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
            }
        }
        else -> false
    }

