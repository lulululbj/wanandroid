package luyao.wanandroid.ui.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import luyao.wanandroid.R
import luyao.wanandroid.model.bean.Article
import luyao.wanandroid.ui.hot.ArticleRefreshList
import luyao.wanandroid.ui.square.ArticleViewModel

/**
 * Description:
 * Author: luyao
 * Date: 2022/7/20 15:48
 */
@Composable
fun ProjectDetailPage(
    cid: Int, viewModel: ArticleViewModel = hiltViewModel(),
    onClickArticle: (Article) -> Unit
) {
    ArticleRefreshList(
        viewModel,
        onRefresh = { viewModel.getProjectTypeDetailList(true, cid) },
        onLoadMore = { viewModel.getProjectTypeDetailList(false, cid) },
        itemContent = { article ->
            ProjectItem(article, onClickArticle)
        })
}

@Composable
fun ProjectItem(article: Article, onClickArticle: (Article) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .background(Color.White)
            .clickable { onClickArticle(article) },
        shape = RoundedCornerShape(6.dp)
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (image, title, desc, author, date, star) = createRefs()

            val guideLine = createGuidelineFromStart(0.4f)

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(article.envelopePic)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.luyao),
                error = painterResource(R.drawable.luyao),
                contentDescription = stringResource(R.string.login),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .constrainAs(image) {
                        start.linkTo(parent.start, 20.dp)
                        end.linkTo(guideLine, 10.dp)
                        top.linkTo(parent.top, 10.dp)
                        bottom.linkTo(parent.bottom, 10.dp)
                        width = Dimension.fillToConstraints
                    }
                    .aspectRatio(3f / 5f)
            )

            Text(text = article.title, fontSize = 14.sp, color = Color.Black,
                modifier = Modifier
                    .constrainAs(title) {
                        start.linkTo(guideLine, 10.dp)
                        end.linkTo(parent.end, 20.dp)
                        top.linkTo(parent.top, 20.dp)
                        width = Dimension.fillToConstraints
                    }
            )

            Text(text = article.desc, fontSize = 12.sp, color = colorResource(R.color.gray),
                modifier = Modifier
                    .constrainAs(desc) {
                        start.linkTo(title.start)
                        end.linkTo(parent.end, 20.dp)
                        top.linkTo(title.bottom, margin = 2.dp)
                        width = Dimension.fillToConstraints
                    }
            )

            Text(text = article.author, fontSize = 12.sp, color = colorResource(R.color.gray),
                modifier = Modifier
                    .constrainAs(author) {
                        start.linkTo(title.start)
                        bottom.linkTo(parent.bottom, 10.dp)
                    }
            )

            Text(
                text = article.niceDate, fontSize = 12.sp, color = colorResource(R.color.gray),
                modifier = Modifier
                    .constrainAs(date) {
                        top.linkTo(author.top)
                        baseline.linkTo(author.baseline)
                        start.linkTo(author.end)
                    }
            )

            Image(
                painter = painterResource(if (article.collect) R.drawable.timeline_like_pressed else R.drawable.timeline_like_normal),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .constrainAs(star) {
                        top.linkTo(author.top)
                        bottom.linkTo(author.bottom)
                        end.linkTo(parent.end, 20.dp)
                    }
                    .width(14.dp)
                    .height(14.dp)
            )
        }
    }
}

@Preview
@Composable
fun ProjectItemPreview() {
    val article = Article(
        1,
        1,
        "测试标题测试标题测试标题测试标题测试标题测试标题",
        1,
        "Kotlin",
        "",
        "",
        "路遥",
        "origin",
        System.currentTimeMillis(),
        10,
        "测试标题测试标题测试标题测试标题测试标题测试标题",
        1,
        "2022-1-1 10:00",
        "date",
        1,
        false,
        "",
        "",
        1,
        "Android",
        1,
        true,
        1,
        "",
        1,
        System.currentTimeMillis(),
        "shareUser",
        "",
        1
    )
    ProjectItem(article = article) {}
}