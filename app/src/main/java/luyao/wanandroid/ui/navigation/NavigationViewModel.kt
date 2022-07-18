package luyao.wanandroid.ui.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import luyao.mvvm.core.base.BaseViewModel
import luyao.wanandroid.checkSuccess
import luyao.wanandroid.model.bean.Navigation
import luyao.wanandroid.model.repository.NavigationRepository
import javax.inject.Inject

/**
 * Created by luyao
 * on 2019/4/8 16:21
 */
@HiltViewModel
class NavigationViewModel @Inject constructor(private val navigationRepository: NavigationRepository) : BaseViewModel() {

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