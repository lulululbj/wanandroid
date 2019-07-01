package luyao.wanandroid.ui.navigation

import androidx.lifecycle.MutableLiveData
import executeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import luyao.util.ktx.base.BaseViewModel
import luyao.wanandroid.model.bean.Navigation
import luyao.wanandroid.model.repository.NavigationRepository

/**
 * Created by luyao
 * on 2019/4/8 16:21
 */
class NavigationViewModel : BaseViewModel() {

    private val repository by lazy { NavigationRepository() }
    val mNavigationList: MutableLiveData<List<Navigation>> = MutableLiveData()

    fun getNavigation() {
        launch {
            val result = withContext(Dispatchers.IO) { repository.getNavigation() }
            executeResponse(result, { mNavigationList.value = result.data }, {})
        }
    }
}