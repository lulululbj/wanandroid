package luyao.wanandroid.ui

import luyao.wanandroid.R
import luyao.wanandroid.base.BaseActivity

/**
 * Created by luyao
 * on 2018/3/13 13:06
 */
class SplashActivity : BaseActivity() {

    override fun getLayoutResId()= R.layout.activity_splash

    override fun initView() {
        startActivity(MainActivity::class.java)
    }

    override fun initData() {
    }
}