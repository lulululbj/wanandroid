package luyao.wanandroid.ui

import androidx.activity.compose.setContent
import com.google.android.material.composethemeadapter.MdcTheme
import dagger.hilt.android.AndroidEntryPoint
import luyao.mvvm.core.base.BaseVMActivity
import luyao.wanandroid.navigation.WanandroidScreen

/**
 * Description:
 * Author: luyao
 * Date: 2022/7/15 15:48
 */
@AndroidEntryPoint
class ComposeMainActivity : BaseVMActivity() {

    override fun initView() {
        setContent {
            MdcTheme {
                WanandroidScreen()
            }
        }
    }

    override fun initData() {
    }

    override fun startObserve() {
    }
}