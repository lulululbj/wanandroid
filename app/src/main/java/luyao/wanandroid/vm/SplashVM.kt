package luyao.wanandroid.vm

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import luyao.ktx.base.BaseVM
import luyao.ktx.model.UiState
import luyao.wanandroid.model.AppConfig
import luyao.wanandroid.model.DarkThemeConfig
import luyao.wanandroid.model.MMKVConstants

/**
 * Description:
 * Author: luyao
 * Date: 2023/11/15 13:35
 */
class SplashVM : BaseVM() {

    val appState: StateFlow<UiState<AppConfig>> = flow<UiState<AppConfig>> {
        val config = AppConfig(getDarkThemeConfig())
        emit(UiState.Success(config))
    }.stateIn(
        scope = viewModelScope,
        initialValue = UiState.Loading,
        started = SharingStarted.WhileSubscribed(5000)
    )


    private fun getDarkThemeConfig() =
        when (MMKVConstants.nightMode) {
            AppCompatDelegate.MODE_NIGHT_NO -> DarkThemeConfig.LIGHT
            AppCompatDelegate.MODE_NIGHT_YES -> DarkThemeConfig.DARK
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> DarkThemeConfig.FOLLOW_SYSTEM
            else -> DarkThemeConfig.FOLLOW_SYSTEM
        }
}