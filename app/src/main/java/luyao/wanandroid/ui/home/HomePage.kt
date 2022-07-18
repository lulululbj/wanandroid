package luyao.wanandroid.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import luyao.wanandroid.R
import luyao.wanandroid.ui.hot.HotPage


@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomePage() {

    val pages = remember {
        listOf("热门", "问答", "广场", "体系", "导航")
    }

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
            return (index - startIndex).floorMod(pages.count())
        }

        val currentIndex: State<Int> = remember {
            derivedStateOf { pageMapper(pagerState.currentPage) }
        }

        TabRow(selectedTabIndex = currentIndex.value,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(
                        pagerState,
                        tabPositions,
                        ::pageMapper
                    )
                )
            }) {
            pages.forEachIndexed { index, title ->
                Tab(text = { Text(title) }, selected = currentIndex.value == index, onClick = {
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
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { index ->
            val page = pageMapper(index)
            when (page) {
                0 -> HotPage()
                else -> {
                    Card {
                        Box(Modifier.fillMaxSize()) {
                            Text(
                                text = "Page: ${pages[page]}",
                                style = MaterialTheme.typography.h4,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun Int.floorMod(other: Int): Int = when (other) {
    0 -> this
    else -> this - floorDiv(other) * other
}