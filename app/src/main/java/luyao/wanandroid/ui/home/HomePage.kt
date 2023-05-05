package luyao.wanandroid.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.launch
import luyao.wanandroid.R
import luyao.wanandroid.model.bean.Article
import luyao.wanandroid.ui.hot.HotPage
import luyao.wanandroid.ui.question.QuestionPage
import luyao.wanandroid.ui.square.SquarePage
import luyao.wanandroid.ui.system.SystemPage


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePage(navController: NavController, onClickArticle: (Article) -> Unit) {

    val pages = remember {
        listOf("热门", "问答", "广场", "体系")
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
            pageCount = loopingCount, state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { index ->
            when (pageMapper(index)) {
                0 -> HotPage(onClickArticle = onClickArticle)
                1 -> QuestionPage(onClickArticle = onClickArticle)
                2 -> SquarePage(onClickArticle = onClickArticle)
                3 -> SystemPage(navController,onClickArticle = onClickArticle)
            }
        }
    }
}

fun Int.floorMod(other: Int): Int = when (other) {
    0 -> this
    else -> this - floorDiv(other) * other
}