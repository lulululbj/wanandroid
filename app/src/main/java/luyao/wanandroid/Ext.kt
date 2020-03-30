package luyao.wanandroid

import luyao.mvvm.core.Result

/**
 * Created by luyao
 * on 2020/3/30 16:19
 */
inline fun <T : Any> Result<T>.checkResult(success: (T) -> Unit, error: (String?) -> Unit) {
    if (this is Result.Success) {
        success(data)
    } else if (this is Result.Error) {
        error(exception.message)
    }
}

inline fun <T : Any> Result<T>.checkSuccess(success: (T) -> Unit) {
    if (this is Result.Success) {
        success(data)
    }
}