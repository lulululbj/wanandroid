package luyao.wanandroid.model.repository

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import luyao.mvvm.core.Result
import luyao.wanandroid.model.api.WanService
import luyao.wanandroid.model.bean.User
import luyao.wanandroid.model.bean.WanResponse
import org.junit.Test

/**
 * Created by luyao
 * on 2019/12/20 11:17
 */
class LoginRepositoryTest {



    private val service: WanService = mock()
    private val repo = LoginRepository(service)
    private val user = User(admin = false, chapterTops = arrayListOf(), collectIds = arrayListOf(), email = "", icon = "", id = 22057, nickname = "", password = "", publicName = "", token = "", type = 0, username = "")

    private val response: WanResponse<User> = WanResponse(errorCode = 0, errorMsg = "",
            data = user)

    @ExperimentalCoroutinesApi
    @Test
    fun loginSuccess() = runBlockingTest {
        whenever(service.login(any(), any())).thenReturn(response)

//        val result = repo.login("test","test")
//        assertEquals(Result.Success(user),result)
    }
}