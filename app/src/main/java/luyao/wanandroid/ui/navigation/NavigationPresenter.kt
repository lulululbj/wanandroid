package luyao.wanandroid.ui.navigation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import luyao.wanandroid.api.WanRetrofitClient

/**
 * Created by Lu
 * on 2018/3/28 21:27
 */
class NavigationPresenter(private val mView: NavigationContract.View) : NavigationContract.Presenter {

    init {
        mView.mPresenter = this
    }

    override fun start() {

    }

    override fun getNavigation() {
        CoroutineScope(Dispatchers.Main).launch {
            val result = WanRetrofitClient.service.getNavigation().await()
            mView.getNavigation(result.data)
        }
    }
}