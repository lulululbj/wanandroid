package luyao.wanandroid.ui.home

import android.util.Log
import luyao.gayhub.base.BaseMvpFragment
import luyao.wanandroid.R
import luyao.wanandroid.bean.ArticleList

/**
 * Created by luyao
 * on 2018/3/13 14:15
 */
class HomeFragment : BaseMvpFragment<HomeContract.View, HomePresenter>(), HomeContract.View {

    override var mPresenter = HomePresenter()
    override fun getLayoutResId() = R.layout.fragment_home

    override fun initView() {
    }

    override fun initData() {
        mPresenter.getArticles(0)
    }

    override fun getArticles(articleList: ArticleList) {
        val size = articleList.datas!!.size
        Log.e("article", "has $size articles")
    }
}