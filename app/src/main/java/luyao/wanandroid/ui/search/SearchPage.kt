package luyao.wanandroid.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowRow
import luyao.wanandroid.R
import luyao.wanandroid.model.bean.Article
import luyao.wanandroid.ui.hot.ArticleItem
import luyao.wanandroid.ui.hot.ArticleRefreshList
import luyao.wanandroid.ui.square.ArticleViewModel

/**
 * Description:
 * Author: luyao
 * Date: 2022/7/20 18:55
 */
@Composable
fun SearchPage(
    viewModel: SearchViewModel = hiltViewModel(),
    articleViewModel: ArticleViewModel = hiltViewModel(),
    onClickArticle: (Article) -> Unit,
) {

    LaunchedEffect(true) {
        viewModel.getHotSearch()
        viewModel.getWebSites()
    }

    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val uiState by viewModel.uiState.observeAsState()
    val hotState by viewModel.hotState.observeAsState()
    val webState by viewModel.webSite.observeAsState()

    if (textState.value.text.isNotBlank()) {
        viewModel.searchHot(true, key = textState.value.text)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        SearchView(state = textState)

        if (uiState != null && textState.value.text.isNotBlank()) {
            ArticleRefreshList(viewModel = articleViewModel, onRefresh = {
                articleViewModel.searchArticle(true, textState.value.text)
            }, onLoadMore = {
                articleViewModel.searchArticle(false, textState.value.text)
            }) {
                ArticleItem(it, onClickArticle)
            }
        } else {
            if (!hotState.isNullOrEmpty()) {
                Text(
                    text = stringResource(R.string.common_search), fontSize = 16.sp,
                    modifier = Modifier.padding(top = 20.dp, start = 10.dp)
                )

                FlowRow(
                    modifier = Modifier.padding(top = 20.dp, start = 10.dp)
                ) {
                    hotState?.forEach {
                        Card(
                            modifier = Modifier
                                .background(Color.White)
                                .padding(vertical = 4.dp, horizontal = 10.dp)
                                .clickable { textState.value = TextFieldValue(it.name) },
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = it.name,
                                fontSize = 12.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }

            if (!webState.isNullOrEmpty()) {
                Text(
                    text = stringResource(R.string.common_website), fontSize = 16.sp,
                    modifier = Modifier.padding(top = 20.dp, start = 10.dp)
                )

                FlowRow(
                    modifier = Modifier.padding(top = 20.dp, start = 10.dp)
                ) {
                    webState?.forEach {
                        Card(
                            modifier = Modifier
                                .background(Color.White)
                                .padding(vertical = 4.dp, horizontal = 10.dp)
                                .clickable { textState.value = TextFieldValue(it.name) },
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = it.name,
                                fontSize = 12.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SearchPagePreview() {

}