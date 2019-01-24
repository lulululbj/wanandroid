package luyao.wanandroid.ui.project

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import luyao.wanandroid.api.WanRetrofitClient

/**
 * Created by Lu
 * on 2018/4/1 16:46
 */
class ProjectPresenter(private val mView: ProjectContract.View) : ProjectContract.Presenter {


    override fun start() {

    }

    init {
        mView.mPresenter = this
    }

    override fun getProjectTypeDetailList(page: Int, cid: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = WanRetrofitClient.service.getProjectTypeDetail(page, cid).await()
            mView.getProjectTypeDetailList(result.data)
        }
    }

    override fun getProjectTypeList() {
        CoroutineScope(Dispatchers.Main).launch {
            val result = WanRetrofitClient.service.getProjectType().await()
            mView.getProjectTypeList(result.data)
        }
    }
}