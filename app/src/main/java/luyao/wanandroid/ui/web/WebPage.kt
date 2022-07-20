package luyao.wanandroid.ui.web

import android.webkit.WebView
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.google.accompanist.web.*
import luyao.wanandroid.view.compose.TitleBar

/**
 * Description:
 * Author: luyao
 * Date: 2022/7/20 20:44
 */
@Composable
fun WebPage(url: String, navController: NavController) {

    var titleState by remember {
        mutableStateOf("")
    }
    Column {
        TitleBar(text = titleState, true) { navController.popBackStack() }

        val webState = rememberWebViewState(url = url)
        WebView(state = webState,
            onCreated = {
                it.settings.run {
                    javaScriptEnabled = true
                }
            },
            client = object : AccompanistWebViewClient() {
            },
            chromeClient = object : AccompanistWebChromeClient() {
                override fun onReceivedTitle(view: WebView?, title: String?) {
                    super.onReceivedTitle(view, title)
                    titleState = title ?: ""
                }
            })
    }

}

