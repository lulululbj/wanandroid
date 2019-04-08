package luyao.wanandroid.ui.navigation

import android.arch.lifecycle.MutableLiveData
import luyao.base.BaseViewModel
import luyao.wanandroid.api.WanRetrofitClient
import luyao.wanandroid.bean.Navigation
import luyao.wanandroid.ext.launchOnUITryCatch

/**
 * Created by luyao
 * on 2019/4/8 16:21
 */
class NavigationViewModel : BaseViewModel() {

    val mNavigationList: MutableLiveData<List<Navigation>> = MutableLiveData()

    fun getNavigation() {

        launchOnUITryCatch({
            val result = WanRetrofitClient.service.getNavigation().await()
            mNavigationList.value = result.data
        }, {}, {}, true)
    }
}