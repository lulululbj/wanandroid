package luyao.wanandroid.ui

import androidx.activity.compose.setContent
import luyao.mvvm.core.base.BaseVMActivity
import luyao.wanandroid.navigation.WanandroidScreen

/**
 * Description:
 * Author: luyao
 * Date: 2022/7/15 15:48
 */
class ComposeMainActivity : BaseVMActivity() {

    override fun initView() {
        setContent {
            WanandroidScreen()
        }
    }

    override fun initData() {
    }

    override fun startObserve() {
    }
}