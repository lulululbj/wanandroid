package luyao.wanandroid.ui.home

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
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
    override var mPresenter = HomePresenter()

    override fun getLayoutResId() = R.layout.fragment_home

    override fun initView() {
        homeRecycleView.layoutManager = object : LinearLayoutManager(activity, LinearLayout.VERTICAL, false) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        homeRecycleView.addItemDecoration(SpaceItemDecoration(homeRecycleView.dp2px(10f)))
        homeRecycleView.adapter = homeArticleAdapter
        banner.setImageLoader(GlideImageLoader())

        homeArticleAdapter.setOnItemClickListener { _, _, position ->
            val intent=Intent(activity,BrowserActivity::class.java)
            intent.putExtra("url",homeArticleAdapter.data[position].link)
            startActivity(intent)

        }

        banner.setOnBannerListener { position ->
            run {
                val intent = Intent(activity, BrowserActivity::class.java)
                intent.putExtra("url",bannerUrls[position] )
                startActivity(intent)
            }
        }
    }

    override fun initData() {
        mPresenter.getBanners()
        mPresenter.getArticles(0)
    }

    override fun getArticles(articleList: ArticleList) {
        homeArticleAdapter.setNewData(articleList.datas)
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