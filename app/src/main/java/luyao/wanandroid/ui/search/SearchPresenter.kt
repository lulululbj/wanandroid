package luyao.wanandroid.ui.search

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import luyao.wanandroid.api.WanRetrofitClient

/**
 * Created by Lu
 * on 2018/4/2 21:57
 */
class SearchPresenter(private val mView: SearchContract.View) : SearchContract.Presenter {

    override fun start() {

    }

    init {
        mView.mPresenter = this
    }

    override fun searchHot(page: Int, key: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val result = WanRetrofitClient.service.searchHot(page, key).await()
            mView.searchHot(result.data)
        }
    }

    override fun getWebsites() {
        CoroutineScope(Dispatchers.Main).launch {
            val result = WanRetrofitClient.service.getWebsites().await()
            mView.getWebsites(result.data)
        }
    }

    override fun getHotSearch() {
        CoroutineScope(Dispatchers.Main).launch {
            val result = WanRetrofitClient.service.getHot().await()
            mView.getHotSearch(result.data)
        }
    }
}