package luyao.wanandroid.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import luyao.util.ktx.base.BaseViewModel
import luyao.wanandroid.core.Result
import luyao.wanandroid.model.bean.User
import luyao.wanandroid.model.repository.LoginRepository

/**
 * Created by luyao
 * on 2019/4/2 16:36
 */
class LoginViewModel : BaseViewModel() {

    private val _uiState = MutableLiveData<LoginUiModel>()
    val uiState: LiveData<LoginUiModel>
        get() = _uiState

    val mRegisterUser: MutableLiveData<User> = MutableLiveData()
    private val repository by lazy { LoginRepository() }

    private fun isInputValid(userName: String, passWord: String) = userName.isNotBlank() && passWord.isNotBlank()

    fun loginDataChanged(userName: String, passWord: String) {
        emitUiState(enableLoginButton = isInputValid(userName, passWord))
    }

    // ViewModel 只处理视图逻辑，数据仓库 Repository 负责业务逻辑
    fun login(userName: String, passWord: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if (userName.isBlank() || passWord.isBlank()) return@launch

            withContext(Dispatchers.Main) { showLoading() }

            val result = repository.login(userName, passWord)

            withContext(Dispatchers.Main) {
                if (result is Result.Success) {
                    emitUiState(showSuccess = result.data,enableLoginButton = true)
                } else if (result is Result.Error) {
                    emitUiState(showError = result.exception.message,enableLoginButton = true)
                }
            }
        }
    }

    fun register(userName: String, passWord: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if (userName.isBlank() || passWord.isBlank()) return@launch

            withContext(Dispatchers.Main) { showLoading() }

            val result = repository.register(userName, passWord)
            withContext(Dispatchers.Main){
                if (result is Result.Success) {
                    emitUiState(showSuccess = result.data,enableLoginButton = true)
                } else if (result is Result.Error) {
                    emitUiState(showError = result.exception.message,enableLoginButton = true)
                }
            }
        }
    }


    private fun showLoading() {
        emitUiState(true)
    }

    private fun emitUiState(
            showProgress: Boolean = false,
            showError: String? = null,
            showSuccess: User? = null,
            enableLoginButton: Boolean = false,
            needLogin: Boolean = false
    ) {
        val uiModel = LoginUiModel(showProgress, showError, showSuccess, enableLoginButton,needLogin)
        _uiState.value = uiModel
    }

    data class LoginUiModel(
            val showProgress: Boolean,
            val showError: String?,
            val showSuccess: User?,
            val enableLoginButton: Boolean,
            val needLogin:Boolean
    )


}