package luyao.wanandroid.ui.system

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.flowlayout.FlowRow

import luyao.wanandroid.model.bean.Article
import luyao.wanandroid.model.bean.SystemChild
import luyao.wanandroid.model.bean.SystemParent
import luyao.wanandroid.navigation.Route
import luyao.wanandroid.navigation.navigateAndArgument

/**
 * Description:
 * Author: luyao
 * Date: 2022/7/19 22:33
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SystemPage(
    navController: NavController,
    viewModel: SystemViewModel = hiltViewModel(),
    onClickArticle: (Article) -> Unit
) {

    val uiState by viewModel.uiState.observeAsState()
    val listSate =
        if (uiState?.showSuccess.isNullOrEmpty()) LazyListState() else viewModel.listState
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState?.showLoading ?: false,
        onRefresh = { viewModel.getSystemTypes() }
    )

    LaunchedEffect(true) {
        viewModel.getSystemTypes()
    }

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp),
            state = listSate
        ) {
            uiState?.showSuccess?.let { list ->
                items(list, key = { item -> item.id }) { systemParent ->
                    Spacer(modifier = Modifier.height(6.dp))
                    SystemItem(systemParent) {
                        val args = listOf(Pair("systemParent", systemParent))
                        navController.navigateAndArgument(Route.SystemDetail, args)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
        PullRefreshIndicator(
            refreshing = uiState?.showLoading ?: false, state = pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun SystemItem(item: SystemParent, itemClick: (SystemParent) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .background(Color.White)
            .clickable { itemClick(item) },
        shape = RoundedCornerShape(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 8.dp)
        ) {
            Text(text = item.name, fontSize = 16.sp, color = Color.Black)

            FlowRow(modifier = Modifier.padding(top = 4.dp)) {
                item.children.forEach { child ->
                    Text(
                        text = child.name,
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SystemItemPreview() {
    val systemParent =
        SystemParent(
            listOf(
                SystemChild(arrayListOf(), 1, 1, "Android Studio 相关", 1, 1, 1),
                SystemChild(arrayListOf(), 1, 1, "Android Studio 相关", 1, 1, 1)
            ), 1, 1, "开发环境", 1, 1, 1, true
        )

    SystemItem(item = systemParent) {}
}