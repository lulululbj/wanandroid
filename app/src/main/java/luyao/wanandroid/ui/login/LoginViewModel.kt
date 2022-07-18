package luyao.wanandroid.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import luyao.mvvm.core.base.BaseViewModel
import luyao.wanandroid.model.bean.User
import luyao.wanandroid.model.repository.LoginRepository
import javax.inject.Inject

/**
 * Created by luyao
 * on 2019/4/2 16:36
 */
@HiltViewModel
class LoginViewModel @Inject constructor(val repository: LoginRepository) :
    BaseViewModel() {

//    val userName = ObservableField<String>("")
//    val passWord = ObservableField<String>("")

    private val _uiState = MutableLiveData<LoginUiState<User>>()
    val uiState: LiveData<LoginUiState<User>>
        get() = _uiState

    private fun isInputValid(userName: String, passWord: String) =
        userName.isNotBlank() && passWord.isNotBlank()

//    fun loginDataChanged() {
//        _uiState.value = LoginUiState(enableLoginButton = isInputValid(userName.get()
//                ?: "", passWord.get() ?: ""))
//    }

    fun login(userName: String, passWord: String) {
        launchOnUI {
            // repo 返回的是一个 flow
            repository.loginFlow(userName, passWord)
                .collect {
                    _uiState.postValue(it)
                }
        }
    }

    fun register(userName: String, passWord: String) {
        launchOnUI {
            repository.registerFlow(userName, passWord)
                .collect {
                    _uiState.postValue(it)
                }
        }
    }

//    val verifyInput: (String) -> Unit = { loginDataChanged() }
}