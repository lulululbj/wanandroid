package luyao.wanandroid.ui.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import luyao.mvvm.core.Result
import luyao.mvvm.core.base.BaseViewModel
import luyao.wanandroid.model.bean.SystemParent
import luyao.wanandroid.model.repository.ProjectRepository
import javax.inject.Inject

/**
 * Created by luyao
 * on 2019/4/8 16:28
 */
@HiltViewModel
class ProjectViewModel @Inject constructor() : BaseViewModel() {

    private val _mSystemParentList: MutableLiveData<List<SystemParent>> = MutableLiveData()
    val systemData: LiveData<List<SystemParent>>
        get() = _mSystemParentList

    fun getProjectTypeList() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { ProjectRepository.getProjectTypeList() }
            if (result is Result.Success) _mSystemParentList.value = result.data
        }
    }

    fun getBlogType() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { ProjectRepository.getBlog() }
            if (result is Result.Success) _mSystemParentList.value = result.data
        }
    }
}