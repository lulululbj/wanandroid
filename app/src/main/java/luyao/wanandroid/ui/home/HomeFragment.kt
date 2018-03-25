package luyao.wanandroid.ui.home

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import com.youth.banner.BannerConfig
import dp2px
import kotlinx.android.synthetic.main.fragment_home.*
import luyao.gayhub.base.BaseMvpFragment
import luyao.wanandroid.R
import luyao.wanandroid.adapter.HomeArticleAdapter
import luyao.wanandroid.bean.ArticleList
import luyao.wanandroid.bean.Banner
import luyao.wanandroid.ui.BrowserActivity
import luyao.wanandroid.util.GlideImageLoader
import luyao.wanandroid.view.CustomLoadMoreView
import luyao.wanandroid.view.SpaceItemDecoration


/**
 * Created by luyao
 * on 2018/3/13 14:15
 */
class HomeFragment : BaseMvpFragment<HomeContract.View, HomePresenter>(), HomeContract.View {


    private val homeArticleAdapter by lazy { HomeArticleAdapter() }
    private val bannerImages = mutableListOf<String>()
    private val bannerTitles = mutableListOf<String>()
    private val bannerUrls = mutableListOf<String>()
    private var currentPage = 0
    private val banner by lazy { com.youth.banner.Banner(activity) }
    override var mPresenter = HomePresenter()

    override fun getLayoutResId() = R.layout.fragment_home

    override fun initView() {
        homeRecycleView.layoutManager = LinearLayoutManager(activity)
        homeRecycleView.addItemDecoration(SpaceItemDecoration(homeRecycleView.dp2px(10f)))

        initBanner()
        initAdapter()

        homeRefreshLayout.setOnRefreshListener { refresh() }
        homeRefreshLayout.isRefreshing = true
        refresh()
    }

    private fun initAdapter() {
        homeArticleAdapter.setOnItemClickListener { _, _, position ->
            val intent = Intent(activity, BrowserActivity::class.java)
            intent.putExtra("url", homeArticleAdapter.data[position].link)
            startActivity(intent)
        }
        homeArticleAdapter.setLoadMoreView(CustomLoadMoreView())
        homeArticleAdapter.setOnLoadMoreListener({ loadMore() }, homeRecycleView)

        homeRecycleView.adapter = homeArticleAdapter
    }

    private fun loadMore() {
        mPresenter.getArticles(currentPage)
    }

    private fun initBanner() {
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, banner.dp2px(200f))
        banner.layoutParams = params
        homeArticleAdapter.setLoadMoreView(CustomLoadMoreView())
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
        banner.setImageLoader(GlideImageLoader())
        banner.setOnBannerListener { position ->
            run {
                val intent = Intent(activity, BrowserActivity::class.java)
                intent.putExtra("url", bannerUrls[position])
                startActivity(intent)
            }
        }

        homeArticleAdapter.addHeaderView(banner)
    }

    private fun refresh() {
        homeArticleAdapter.setEnableLoadMore(false)
        homeRefreshLayout.isRefreshing = true
        currentPage = 0
        mPresenter.getArticles(currentPage)
    }

    override fun initData() {
        mPresenter.getBanners()
    }

    override fun getArticles(articleList: ArticleList) {
        homeArticleAdapter.run {
            if (homeRefreshLayout.isRefreshing) replaceData(articleList.datas)
            else addData(articleList.datas)
            setEnableLoadMore(true)
            loadMoreComplete()
        }
        homeRefreshLayout.isRefreshing = false
        currentPage++
    }

    override fun getBanner(bannerList: List<Banner>) {
        for (banner in bannerList) {
            bannerImages.add(banner.imagePath)
            bannerTitles.add(banner.title)
            bannerUrls.add(banner.url)
        }
        banner.setImages(bannerImages)
                .setBannerTitles(bannerTitles)
                .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                .setDelayTime(3000)
        banner.start()
    }

    override fun onStart() {
        super.onStart()
        banner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        banner.stopAutoPlay()
    }

}