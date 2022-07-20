package luyao.wanandroid.ui.system

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import luyao.wanandroid.model.bean.Article
import luyao.wanandroid.model.bean.SystemChild
import luyao.wanandroid.ui.hot.ArticleItem
import luyao.wanandroid.ui.hot.ArticleRefreshList

/**
 * Description:
 * Author: luyao
 * Date: 2022/7/19 23:55
 */
@Composable
fun SystemChildPage(cid: Int, viewModel: SystemArticleViewModel = hiltViewModel(),onClickArticle : (Article) -> Unit) {
    ArticleRefreshList(
        viewModel,
        onRefresh = { viewModel.getSystemTypeArticleList(true, cid) },
        onLoadMore = { viewModel.getSystemTypeArticleList(false, cid) },
        itemContent = { article ->
            ArticleItem(article, onClickArticle)
        })
}