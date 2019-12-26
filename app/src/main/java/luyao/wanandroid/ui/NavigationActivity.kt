package luyao.wanandroid.ui

import android.os.Bundle
import luyao.util.ktx.base.BaseActivity
import luyao.wanandroid.R

/**
 * Created by luyao
 * on 2019/12/26 15:24
 */
class NavigationActivity : BaseActivity(){

    override fun getLayoutResId() = R.layout.activity_navigation


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
    }
    override fun initView() {
    }

    override fun initData() {
    }
}