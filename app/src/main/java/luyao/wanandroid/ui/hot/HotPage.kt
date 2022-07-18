package luyao.wanandroid.ui.hot

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import luyao.wanandroid.ui.square.ArticleViewModel

/**
 * Description:
 * Author: luyao
 * Date: 2022/7/18 14:04
 */

@Composable
fun HotPage(viewModel: ArticleViewModel = hiltViewModel()) {

    val uiState = viewModel.uiState.observeAsState()
    val listSate = rememberLazyListState()
    val refreshState = rememberSwipeRefreshState(isRefreshing = false)

    SwipeRefresh(state = refreshState, onRefresh = {
        viewModel.getHomeArticleList(true)
    }) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listSate
        ) {
            uiState.value?.showSuccess?.datas?.let { list ->
                list.forEach {  article ->
                    Log.e("article",article.title)
                }
            }
        }
    }
}