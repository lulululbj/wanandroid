package luyao.wanandroid.ui.navigation

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import luyao.base.BaseViewModel
import luyao.wanandroid.model.repository.NavigationRepository
import luyao.wanandroid.model.bean.Navigation

/**
 * Created by luyao
 * on 2019/4/8 16:21
 */
class NavigationViewModel : BaseViewModel() {

    private val repository by lazy { NavigationRepository() }
    val mNavigationList: MutableLiveData<List<Navigation>> = MutableLiveData()

    fun getNavigation() {
        launch{
            val result = withContext(Dispatchers.IO) { repository.getNavigation() }
            executeResponse(result, { mNavigationList.value = result.data }, {})
        }
    }
}