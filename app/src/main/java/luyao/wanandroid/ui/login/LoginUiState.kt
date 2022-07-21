package luyao.wanandroid.ui.login

import luyao.wanandroid.base.BaseViewModel


/**
 * Created by luyao
 * on 2020/3/30 15:34
 */
class LoginUiState<T>(
        isLoading: Boolean = false,
        isSuccess: T? = null,
        isError: String? = null,
        val enableLoginButton: Boolean = false,
        val needLogin: Boolean = false
) : BaseViewModel.UiState<T>(isLoading, false, isSuccess, isError)
