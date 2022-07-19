package luyao.wanandroid.ui

import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint
import luyao.mvvm.core.base.BaseVMActivity
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
        setContent {
            MdcTheme {
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
    var showSplash by remember {
        mutableStateOf(true)
    }

    if (showSplash) {
        SplashPage {
            showSplash = false
        }
    } else {
        WanandroidPage()
    }
}
