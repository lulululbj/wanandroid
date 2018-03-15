package luyao.wanandroid.ui.home

import android.support.v7.widget.LinearLayoutManager
import dp2px
import kotlinx.android.synthetic.main.fragment_home.*
import luyao.gayhub.base.BaseMvpFragment
import luyao.wanandroid.R
import luyao.wanandroid.adapter.HomeArticleAdapter
import luyao.wanandroid.bean.ArticleList
import luyao.wanandroid.view.SpaceItemDecoration
import px2dp

/**
 * Created by luyao
 * on 2018/3/13 14:15
 */
class HomeFragment : BaseMvpFragment<HomeContract.View, HomePresenter>(), HomeContract.View {

    private val homeArticleAdapter by lazy { HomeArticleAdapter() }
    override var mPresenter = HomePresenter()

    override fun getLayoutResId() = R.layout.fragment_home

    override fun initView() {
        homeRecycleView.layoutManager = LinearLayoutManager(activity)
        homeRecycleView.addItemDecoration(SpaceItemDecoration(homeRecycleView.dp2px(10f)))
        homeRecycleView.adapter = homeArticleAdapter
    }

    override fun initData() {
        mPresenter.getArticles(0)
    }

    override fun getArticles(articleList: ArticleList) {
        homeArticleAdapter.setNewData(articleList.datas)
    }
}