package luyao.wanandroid.ui.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import luyao.wanandroid.util.executeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import luyao.util.ktx.base.BaseViewModel
import luyao.wanandroid.core.Result
import luyao.wanandroid.model.bean.ArticleList
import luyao.wanandroid.model.bean.SystemParent
import luyao.wanandroid.model.repository.CollectRepository
import luyao.wanandroid.model.repository.ProjectRepository

/**
 * Created by luyao
 * on 2019/4/8 16:28
 */
class ProjectViewModel : BaseViewModel() {

    private val repository by lazy { ProjectRepository() }
    private val _mSystemParentList: MutableLiveData<List<SystemParent>> = MutableLiveData()
    val systemData : LiveData<List<SystemParent>>
        get() = _mSystemParentList

    fun getProjectTypeList() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { repository.getProjectTypeList() }
            if (result is Result.Success) _mSystemParentList.value = result.data
        }
    }

    fun getBlogType() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = withContext(Dispatchers.IO) { repository.getBlog() }
            if (result is Result.Success) _mSystemParentList.value = result.data
        }
    }
}