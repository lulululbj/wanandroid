package luyao.wanandroid.ui.navigation

import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import luyao.wanandroid.api.WanRetrofitClient
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by Lu
 * on 2018/3/28 21:27
 */
class NavigationPresenter(
        private val mView: NavigationContract.View,
        private val uiContext: CoroutineContext = UI
) : NavigationContract.Presenter {

    init {
        mView.mPresenter = this
    }

    override fun start() {

    }

    override fun getNavigation() {
        launch(uiContext) {
            val result = WanRetrofitClient.service.getNavigation().await()
            mView.getNavigation(result.data)
        }
    }
}