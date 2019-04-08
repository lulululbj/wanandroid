package luyao.wanandroid.ui.collect

import android.arch.lifecycle.MutableLiveData
import luyao.base.BaseViewModel
import luyao.wanandroid.api.WanRetrofitClient
import luyao.wanandroid.bean.ArticleList
import luyao.wanandroid.ext.launchOnUITryCatch

/**
 * Created by luyao
 * on 2019/4/8 16:03
 */
class CollectViewModel : BaseViewModel() {

    val mArticleList: MutableLiveData<ArticleList> = MutableLiveData()
    val mErrorMsg: MutableLiveData<String> = MutableLiveData()

    fun getCollectArticles(page: Int) {

        launchOnUITryCatch({
            val result = WanRetrofitClient.service.getCollectArticles(page).await()
            result.run {
                if (errorCode == -1) mErrorMsg.value = errorMsg
                else mArticleList.value = data
            }
        }, {}, {}, true)
    }
}