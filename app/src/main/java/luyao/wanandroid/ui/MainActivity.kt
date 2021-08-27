package luyao.wanandroid.ui

import android.os.Bundle
import luyao.mvvm.core.base.BaseActivity
import luyao.wanandroid.R

/**
 * Created by luyao
 * on 2019/12/26 15:24
 */
class MainActivity : BaseActivity() {

    override fun getLayoutResId() = R.layout.activity_main


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
    }

    override fun initData() {
    }
}