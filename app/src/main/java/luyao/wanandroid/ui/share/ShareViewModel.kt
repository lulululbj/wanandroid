package luyao.wanandroid.ui.share

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import luyao.mvvm.core.Result
import luyao.mvvm.core.base.BaseViewModel
import luyao.wanandroid.model.repository.ShareRepository

/**
 * Created by luyao
 * on 2019/10/15 15:21
 */
class ShareViewModel(private val repository: ShareRepository) : BaseViewModel() {

    val title = ObservableField<String>("")
    val url = ObservableField<String>("")

    private val _uiState = MutableLiveData<ShareUiModel>()
    val uiState: LiveData<ShareUiModel>
        get() = _uiState


    val verifyInput: (String) -> Unit = { shareDataChanged() }

    private fun shareDataChanged() {
        enableShare((title.get()?.isNotBlank() ?: false) && (url.get()?.isNotBlank() ?:false))
    }

    fun shareArticle() {
        viewModelScope.launch(Dispatchers.Default) {

            withContext(Dispatchers.Main){ emitUiState(showProgress = true)}

            val result = repository.shareArticle(title.get()?:"", url.get()?:"")


            withContext(Dispatchers.Main){
                if (result is Result.Success){
                    emitUiState(showProgress = false,showSuccess = result.data,enableShareButton = true)
                }else if (result is Result.Error){
                    emitUiState(showProgress = false,showError = result.exception.message,enableShareButton = true)
                }
            }
        }
    }

    private fun enableShare(enable: Boolean) {
        emitUiState(enableShareButton = enable)
    }

    private fun emitUiState(
            showProgress: Boolean = false,
            showError: String? = null,
            showSuccess: String? = null,
            enableShareButton: Boolean = false
    ) {
        val uiModel = ShareUiModel(showProgress, showSuccess, showError, enableShareButton)
        _uiState.value = uiModel
    }
}


data class ShareUiModel(
        val showProgress: Boolean,
        val showSuccess: String?,
        val showError: String?,
        val enableShareButton: Boolean
)