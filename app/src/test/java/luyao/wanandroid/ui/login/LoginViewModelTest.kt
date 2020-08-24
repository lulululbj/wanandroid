package luyao.wanandroid.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import luyao.mvvm.core.Result
import luyao.wanandroid.model.bean.User
import luyao.wanandroid.model.repository.LoginRepository
import luyao.wanandroid.provideFakeCoroutinesDispatcherProvider
import luyao.wanandroid.test.getOrAwaitValue
import org.junit.Rule
import org.junit.Test
import java.io.IOException

/**
 * Created by luyao
 * on 2019/12/16 10:13
 */
class LoginViewModelTest {

//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()
//
//
//    private val userName = "秉心说___"
//    private val passWord = "123456"
//    private val errorPassWord = "123456789"
//    private val initialUiModel = LoginUiState(showProgress = false, showError = null, showSuccess = null, enableLoginButton = false, needLogin = false)
//
//    private val loginRepo: LoginRepository = mock()
//
//    @Test
//    fun loginSuccessfully() = runBlocking {
//        val viewModel = LoginViewModel(loginRepo, provideFakeCoroutinesDispatcherProvider())
//        viewModel.userName.set(userName)
//        viewModel.passWord.set(passWord)
//
//        val user = User(admin = false, chapterTops = arrayListOf(), collectIds = arrayListOf(), email = "", icon = "", id = 22057, nickname = "", password = "", publicName = "", token = "", type = 0, username = "")
//
//        whenever(loginRepo.login(userName, passWord)).thenReturn(Result.Success(user))
//
//        viewModel.login()
//
//        val expected = LoginUiModel(
//                showProgress = false, showError = null, showSuccess = user, enableLoginButton = true, needLogin = false
//        )
//
//        val uiState = viewModel.uiState.getOrAwaitValue()
//
//        assertEquals(expected.enableLoginButton, uiState.enableLoginButton)
//        assertEquals(expected.needLogin, uiState.needLogin)
//        assertEquals(expected.showSuccess?.id, uiState.showSuccess?.id)
//    }
//
//    @Test
//    fun loginFailed() = runBlocking {
//        val viewModel = LoginViewModel(loginRepo, provideFakeCoroutinesDispatcherProvider())
//        viewModel.userName.set(userName)
//        viewModel.passWord.set(errorPassWord)
//
//        whenever(loginRepo.login(userName, errorPassWord)).thenReturn(Result.Error(IOException("登录失败!")))
//        viewModel.login()
//
//        val uiState = viewModel.uiState.getOrAwaitValue()
//        val expected = LoginUiModel(showProgress = false,showSuccess = null,showError = "登录失败!",enableLoginButton = true,needLogin = false)
//        assertEquals(expected,uiState)
//    }
//
//    @Test
//    fun disableLogin() = runBlocking {
//        val viewModel = LoginViewModel(loginRepo, provideFakeCoroutinesDispatcherProvider())
//        viewModel.login()
//        val uiState = viewModel.uiState.getOrAwaitValue()
//        assertEquals(false,uiState.enableLoginButton)
//    }
//
//    @Test
//    fun loginDataChanged() = runBlocking {
//        val viewModel = LoginViewModel(loginRepo, provideFakeCoroutinesDispatcherProvider())
//        viewModel.userName.set(userName)
//        viewModel.passWord.set(passWord)
//
//        viewModel.loginDataChanged()
//
//        var uiState = viewModel.uiState.getOrAwaitValue()
//        assertEquals(true,uiState.enableLoginButton)
//
//        viewModel.userName.set("")
//        viewModel.loginDataChanged()
//
//         uiState = viewModel.uiState.getOrAwaitValue()
//        assertEquals(false,uiState.enableLoginButton)
//    }


}