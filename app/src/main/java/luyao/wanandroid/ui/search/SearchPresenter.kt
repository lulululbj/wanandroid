package luyao.wanandroid.ui.search

import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import luyao.wanandroid.api.WanRetrofitClient
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by Lu
 * on 2018/4/2 21:57
 */
class SearchPresenter(
        private val mView: SearchContract.View,
        private val uiContext: CoroutineContext = UI
) : SearchContract.Presenter {

    override fun start() {

    }

    init {
        mView.mPresenter = this
    }

    override fun searchHot(page: Int, key: String) {
        launch(uiContext) {
            val result = WanRetrofitClient.service.searchHot(page, key).await()
            mView.searchHot(result.data)
        }
    }

    override fun getWebsites() {
        launch(uiContext) {
            val result = WanRetrofitClient.service.getWebsites().await()
            mView.getWebsites(result.data)
        }
    }

    override fun getHotSearch() {
        launch(uiContext) {
            val result = WanRetrofitClient.service.getHot().await()
            mView.getHotSearch(result.data)
        }
    }
}