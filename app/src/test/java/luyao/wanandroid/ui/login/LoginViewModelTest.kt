package luyao.wanandroid.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import luyao.wanandroid.core.Result
import luyao.wanandroid.model.bean.User
import luyao.wanandroid.model.repository.LoginRepository
import luyao.wanandroid.test.getOrAwaitValue
import org.junit.Rule
import org.junit.Test

/**
 * Created by luyao
 * on 2019/12/16 10:13
 */
class LoginViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    private val userName = "秉心说___"
    private val passWord = "123456"
    private val initialUiModel = LoginUiModel(showProgress = false, showError = null, showSuccess = null, enableLoginButton = false, needLogin = false)

    private val loginRepo: LoginRepository = mock()

    @Test
    fun loginSuccessfully() = runBlocking {
        val viewModel = LoginViewModel(loginRepo)

        val user = User(admin = false, chapterTops = listOf(), collectIds = listOf(), email = "", icon = "", id = 22057, nickname = "", password = "", publicName = "", token = "", type = 0, username = "")

        whenever(loginRepo.login(userName, passWord)).thenReturn(Result.Success(user))

        viewModel.login(userName, passWord)

        val expected = LoginUiModel(
                showProgress = false, showError = null, showSuccess = null, enableLoginButton = false, needLogin = false
        )

        val uiState = viewModel.uiState.getOrAwaitValue()

        assertEquals(expected, uiState)
    }


}