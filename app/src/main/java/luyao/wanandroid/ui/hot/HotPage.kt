package luyao.wanandroid.ui.hot

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import luyao.wanandroid.R
import luyao.wanandroid.model.bean.Article
import luyao.wanandroid.ui.square.ArticleViewModel

/**
 * Description:
 * Author: luyao
 * Date: 2022/7/18 14:04
 */

@Composable
fun HotPage(viewModel: ArticleViewModel = hiltViewModel(), onClickArticle: (Article) -> Unit) {
    ArticleRefreshList(
        viewModel,
        onRefresh = { viewModel.getHomeArticleList(true) },
        onLoadMore = { viewModel.getHomeArticleList(false) },
        itemContent = { article ->
            ArticleItem(article, onClickArticle = onClickArticle)
        })
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticleRefreshList(
    viewModel: ArticleViewModel,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    itemContent: @Composable (Article) -> Unit,
) {
    val uiState by viewModel.uiState.observeAsState()
    val listSate =
        if (uiState?.showSuccess.isNullOrEmpty()) LazyListState() else viewModel.lazyListState
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState?.showLoading ?: false,
        onRefresh = onRefresh
    )
    LaunchedEffect(true) {
        onRefresh()
    }

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp),
            state = listSate
        ) {
            uiState?.showSuccess?.let { list ->
                items(list, key = { item -> item.id }) { article ->
                    Spacer(modifier = Modifier.height(6.dp))
                    itemContent(article)
                    Spacer(modifier = Modifier.height(6.dp))
                }
                item {
                    if (uiState?.showEnd == false) {
                        LoadingItem()
                        LaunchedEffect(Unit) {
                            delay(500)
                            onLoadMore()
                        }
                    }
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
fun ArticleItem(article: Article, onClickArticle: (Article) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .background(Color.White)
            .clickable { onClickArticle(article) },
        shape = RoundedCornerShape(6.dp)
    ) {
        ConstraintLayout(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
            val (icon, author, tag, title, clock, date, star) = createRefs()
            Image(
                painter = painterResource(R.drawable.home_hot),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .constrainAs(icon) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .clip(CircleShape)
                    .width(14.dp)
                    .height(14.dp)
            )

            ArticleText(text = article.getAuthorName(),
                modifier = Modifier
                    .constrainAs(author) {
                        start.linkTo(icon.end)
                        top.linkTo(icon.top)
                        bottom.linkTo(icon.bottom)
                    }
                    .padding(start = 2.dp))

            ArticleText(
                text = "${article.superChapterName}/${article.chapterName}",
                modifier = Modifier
                    .constrainAs(tag) {
                        start.linkTo(author.end)
                        baseline.linkTo(author.baseline)
                    }
                    .padding(start = 15.dp)
            )

            TitleText(text = article.title,
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(icon.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(vertical = 5.dp)
            )

            Image(
                painter = painterResource(R.drawable.ic_time),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .constrainAs(clock) {
                        top.linkTo(title.bottom)
                        start.linkTo(parent.start)
                    }
                    .clip(CircleShape)
                    .width(14.dp)
                    .height(14.dp)
            )

            ArticleText(text = article.niceDate, modifier = Modifier.constrainAs(date) {
                start.linkTo(clock.end)
                top.linkTo(clock.top)
                bottom.linkTo(clock.bottom)
            })

            Image(
                painter = painterResource(if (article.collect) R.drawable.timeline_like_pressed else R.drawable.timeline_like_normal),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .constrainAs(star) {
                        start.linkTo(date.end)
                        top.linkTo(clock.top)
                        bottom.linkTo(clock.bottom)
                    }
                    .padding(start = 10.dp)
                    .clip(CircleShape)
                    .width(14.dp)
                    .height(14.dp)
            )
        }
    }
}

@Composable
fun ArticleText(text: String, modifier: Modifier) {
    Text(
        text = text,
        fontSize = 12.sp,
        color = colorResource(R.color.color_8e9dac),
        modifier = modifier
    )
}

@Composable
fun TitleText(text: String, modifier: Modifier) {
    Text(
        text = text,
        fontSize = 16.sp,
        color = colorResource(R.color.color_1c1c1e),
        modifier = modifier
    )
}

@Composable
fun LoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
@Preview
fun ArticleItemPreview() {
    val article = Article(
        1, 1, "测试标题", 1, "Kotlin", "", "", "路遥",
        "origin", System.currentTimeMillis(), 10, "desc", 1, "2022-1-1 10:00", "date", 1, false,
        "", "", 1, "Android", 1, true, 1, "", 1, System.currentTimeMillis(), "shareUser",
        "", 1
    )
    ArticleItem(article = article) {}
}