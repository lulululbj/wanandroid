package luyao.wanandroid.ui.project

import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import luyao.wanandroid.api.WanRetrofitClient
import kotlin.coroutines.experimental.CoroutineContext

/**
 * Created by Lu
 * on 2018/4/1 16:46
 */
class ProjectPresenter(
        private val mView: ProjectContract.View,
        private val uiContext: CoroutineContext = UI
) : ProjectContract.Presenter {


    override fun start() {

    }

    init {
        mView.mPresenter = this
    }

    override fun getProjectTypeDetailList(page: Int, cid: Int) {
        launch(uiContext) {
            val result = WanRetrofitClient.service.getProjectTypeDetail(page, cid).await()
            mView.getProjectTypeDetailList(result.data)
        }
    }

    override fun getProjectTypeList() {
        launch(uiContext){
            val result=WanRetrofitClient.service.getProjectType().await()
            mView.getProjectTypeList(result.data)
        }
    }
}