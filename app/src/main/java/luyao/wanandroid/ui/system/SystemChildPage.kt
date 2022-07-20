package luyao.wanandroid.ui.system

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import luyao.wanandroid.model.bean.SystemChild
import luyao.wanandroid.ui.hot.ArticleItem
import luyao.wanandroid.ui.hot.ArticleRefreshList

/**
 * Description:
 * Author: luyao
 * Date: 2022/7/19 23:55
 */
@Composable
fun SystemChildPage(child: SystemChild, viewModel: SystemArticleViewModel = hiltViewModel()) {
    ArticleRefreshList(
        viewModel,
        onRefresh = { viewModel.getSystemTypeArticleList(true, child.id) },
        onLoadMore = { viewModel.getSystemTypeArticleList(false, child.id) },
        itemContent = { article ->
            ArticleItem(article)
        })
}