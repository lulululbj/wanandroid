package luyao.wanandroid.ui.blog

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import luyao.wanandroid.ProvideViewModels
import luyao.wanandroid.R
import luyao.wanandroid.ui.home.floorMod
import luyao.wanandroid.ui.project.ProjectViewModel

/**
 * Description:
 * Author: luyao
 * Date: 2022/7/20 15:19
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun BlogPage(viewModel: ProjectViewModel = hiltViewModel()) {
    val pages by viewModel.systemData.observeAsState()
//    val listSate =
//        if (uiState.isNullOrEmpty()) LazyListState() else viewModel.listState

    LaunchedEffect(true) {
        viewModel.getBlogType()
    }

    if (!pages.isNullOrEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.white))
        ) {

            val coroutineScope = rememberCoroutineScope()

            val loopingCount = Int.MAX_VALUE
            val startIndex = loopingCount / 2
            val pagerState = rememberPagerState(initialPage = startIndex)

            fun pageMapper(index: Int): Int {
                return (index - startIndex).floorMod(pages?.count() ?: 0)
            }

            val currentIndex: State<Int> = remember {
                derivedStateOf { pageMapper(pagerState.currentPage) }
            }

            ScrollableTabRow(selectedTabIndex = currentIndex.value,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.pagerTabIndicatorOffset(
                            pagerState,
                            tabPositions,
                            ::pageMapper
                        )
                    )
                }) {
                pages?.forEachIndexed { index, child ->
                    Tab(
                        text = { Text(child.name) },
                        selected = currentIndex.value == index,
                        onClick = {
                            coroutineScope.launch {
                                when {
                                    currentIndex.value > index -> {
                                        pagerState.animateScrollToPage(
                                            page = pagerState.currentPage - (currentIndex.value - index)
                                        )
                                    }
                                    currentIndex.value < index -> {
                                        pagerState.animateScrollToPage(
                                            page = pagerState.currentPage + (index - currentIndex.value)
                                        )
                                    }
                                }
                            }
                        })
                }
            }

            HorizontalPager(
                count = loopingCount, state = pagerState,
                key = { page: Int -> page },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) { index ->
                ProvideViewModels {
                    Log.e("pager", "${pageMapper(index)}")
                    BlogDetailPage(pages!![pageMapper(index)].id)
                }
            }
        }
    }
}