package luyao.wanandroid

import luyao.wanandroid.model.bean.Result
/**
 * Created by luyao
 * on 2020/3/30 16:19
 */
inline fun <T : Any> Result<T>.checkResult(crossinline onSuccess: (T) -> Unit,crossinline onError: (String?) -> Unit) {
    if (this is Result.Success) {
        onSuccess(data)
    } else if (this is Result.Error) {
        onError(exception.message)
    }
}

inline fun <T : Any> Result<T>.checkSuccess(success: (T) -> Unit) {
    if (this is Result.Success) {
        success(data)
    }
}