package luyao.wanandroid.ui.collect

import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import luyao.wanandroid.api.WanRetrofitClient
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by Lu
 * on 2018/4/10 22:12
 */
class CollectPresenter(
        private val mView: CollectContract.View,
        private val uiContext: CoroutineContext = UI
) : CollectContract.Presenter {

    override fun start() {

    }

    init {
        mView.mPresenter = this
    }

    override fun getCollectArticles(page: Int) {
        launch(uiContext) {
            val result = WanRetrofitClient.service.getCollectArticles(page).await()
            with(result) {
                if (errorCode == -1) mView.getCollectArticlesError(errorMsg) else mView.getCollectArticles(result.data)
            }
        }
    }
}