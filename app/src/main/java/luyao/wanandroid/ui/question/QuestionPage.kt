package luyao.wanandroid.ui.question

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import luyao.wanandroid.R
import luyao.wanandroid.model.bean.Article
import luyao.wanandroid.ui.hot.ArticleRefreshList
import luyao.wanandroid.ui.hot.ArticleText
import luyao.wanandroid.ui.hot.TitleText

/**
 * Description:
 * Author: luyao
 * Date: 2022/7/19 15:32
 */
@Composable
fun QuestionPage(
    viewModel: QuestionViewModel = hiltViewModel(),
    onClickArticle: (Article) -> Unit
) {
    ArticleRefreshList(
        viewModel,
        onRefresh = { viewModel.getQuestionList(true) },
        onLoadMore = { viewModel.getQuestionList(false) },
        itemContent = { article ->
            QuestionItem(article, onClickArticle)
        })
}

@Composable
fun QuestionItem(article: Article, onClickArticle: (Article) -> Unit) {
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

@Preview
@Composable
fun QuestionItemPreview() {
    val article = Article(
        1, 1, "测试标题", 1, "Kotlin", "", "", "路遥",
        "origin", System.currentTimeMillis(), 10, "desc", 1, "2022-1-1 10:00", "date", 1, false,
        "", "", 1, "Android", 1, true, 1, "", 1, System.currentTimeMillis(), "shareUser",
        "", 1
    )
    QuestionItem(article = article) {}
}