package luyao.wanandroid.ui.system

import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import luyao.wanandroid.api.WanRetrofitClient
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by Lu
 * on 2018/3/26 21:39
 */
class SystemPresenter(
        private val mView: SystemContract.View,
        private val uiContext: CoroutineContext = UI
) : SystemContract.Presenter {

    init {
        mView.mPresenter = this
    }

    override fun start() {

    }

    override fun getSystemTypeDetail(id: Int, page: Int) {
        launch(uiContext) {
            val result = WanRetrofitClient.service.getSystemTypeDetail(page, id).await()
            mView.getSystemTypeDetail(result.data)
        }
    }

    override fun getSystemTypes() {
        launch(uiContext) {
            val result = WanRetrofitClient.service.getSystemType().await()
            mView.getSystemTypes(result.data)
        }
    }
}