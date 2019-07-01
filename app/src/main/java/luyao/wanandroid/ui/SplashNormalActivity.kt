package luyao.wanandroid.ui

import luyao.wanandroid.R
import luyao.util.ktx.base.BaseActivity
import luyao.wanandroid.ui.main.NewMainActivity

/**
 * Created by luyao
 * on 2018/3/13 13:06
 */
class SplashNormalActivity : BaseActivity() {

    override fun getLayoutResId()= R.layout.activity_splash

    override fun initView() {
        startActivity(NewMainActivity::class.java)
    }

    override fun initData() {
    }
}