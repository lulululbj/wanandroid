package luyao.wanandroid.ui.blog

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import luyao.wanandroid.model.bean.SystemChild
import luyao.wanandroid.ui.hot.ArticleItem
import luyao.wanandroid.ui.hot.ArticleRefreshList
import luyao.wanandroid.ui.project.ProjectViewModel
import luyao.wanandroid.ui.square.ArticleViewModel

/**
 * Description:
 * Author: luyao
 * Date: 2022/7/19 23:55
 */
@Composable
fun BlogDetailPage(cid: Int, viewModel: ArticleViewModel = hiltViewModel()) {
    ArticleRefreshList(
        viewModel,
        onRefresh = { viewModel.getBlogArticleList(true, cid) },
        onLoadMore = { viewModel.getBlogArticleList(false, cid) },
        itemContent = { article ->
            ArticleItem(article)
        })
}