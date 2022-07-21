package luyao.wanandroid.ui

import kotlinx.android.synthetic.main.activity_browser.*
import kotlinx.android.synthetic.main.activity_system_detail.*
import luyao.mvvm.core.base.BaseActivity
import luyao.wanandroid.R

/**
 * Created by Lu
 * on 2018/3/25 21:47
 */
class BrowserActivity : BaseActivity() {

    companion object {
        const val URL = "url"
    }

    override fun getLayoutResId() = R.layout.activity_browser

    override fun initView() {
        mToolbar.title = getString(R.string.is_loading)
        mToolbar.setNavigationIcon(R.drawable.arrow_back)
        initWebView()
    }

    override fun initData() {
        mToolbar.setNavigationOnClickListener { onBackPressed() }

        intent?.extras?.getString(URL).let {
        }
    }

    private fun initWebView() {
        progressBar.progressDrawable = this.resources
            .getDrawable(R.drawable.color_progressbar)

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


}