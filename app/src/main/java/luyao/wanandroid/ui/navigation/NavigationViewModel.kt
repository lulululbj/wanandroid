package luyao.wanandroid.ui.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import luyao.util.ktx.base.BaseViewModel
import luyao.wanandroid.core.Result
import luyao.wanandroid.model.bean.Navigation
import luyao.wanandroid.model.repository.NavigationRepository

/**
 * Created by luyao
 * on 2019/4/8 16:21
 */
class NavigationViewModel(private val navigationRepository: NavigationRepository) : BaseViewModel() {

    private val _navigationList: MutableLiveData<List<Navigation>> = MutableLiveData()
    val navigationListState : LiveData<List<Navigation>>
        get() = _navigationList

    fun getNavigation() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { navigationRepository.getNavigation() }
            if (result is Result.Success)
                _navigationList.value = result.data
        }
    }
}