package luyao.wanandroid.ui.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import luyao.mvvm.core.base.BaseViewModel
import luyao.mvvm.core.Result
import luyao.wanandroid.checkResult
import luyao.wanandroid.checkSuccess
import luyao.wanandroid.model.bean.Navigation
import luyao.wanandroid.model.repository.NavigationRepository

/**
 * Created by luyao
 * on 2019/4/8 16:21
 */
class NavigationViewModel(private val navigationRepository: NavigationRepository) : BaseViewModel() {

    private val _uiState = MutableLiveData<List<Navigation>>()
    val uiState : LiveData<List<Navigation>>
        get() = _uiState

    fun getNavigation() {
        launchOnUI {
            val result = withContext(Dispatchers.IO) { navigationRepository.getNavigation() }
            result.checkSuccess {
                _uiState.value = it
            }
        }
    }
}