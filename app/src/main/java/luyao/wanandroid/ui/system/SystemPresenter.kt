package luyao.wanandroid.ui.system

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import luyao.wanandroid.api.WanRetrofitClient

/**
 * Created by Lu
 * on 2018/3/26 21:39
 */
class SystemPresenter(private val mView: SystemContract.View) : SystemContract.Presenter {

    init {
        mView.mPresenter = this
    }

    override fun start() {

    }

    override fun getSystemTypeDetail(id: Int, page: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = WanRetrofitClient.service.getSystemTypeDetail(page, id).await()
            mView.getSystemTypeDetail(result.data)
        }
    }

    override fun getSystemTypes() {
        CoroutineScope(Dispatchers.Main).launch {
            val result = WanRetrofitClient.service.getSystemType().await()
            mView.getSystemTypes(result.data)
        }
    }
}