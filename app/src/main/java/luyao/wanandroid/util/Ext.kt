package luyao.wanandroid.util

import android.app.Activity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import luyao.util.ktx.ext.toast
import luyao.wanandroid.R
import luyao.wanandroid.model.bean.SystemChild
import luyao.wanandroid.model.bean.WanResponse
import retrofit2.HttpException

/**
 * Created by Lu
 * on 2018/3/15 21:53
 */

const val TOOL_URL = "http://www.wanandroid.com/tools"
const val GITHUB_PAGE = "https://github.com/lulululbj/wanandroid"
const val ISSUE_URL = "https://github.com/lulululbj/wanandroid/issues"

suspend fun executeResponse(response: WanResponse<Any>, successBlock: suspend CoroutineScope.() -> Unit,
                            errorBlock: suspend CoroutineScope.() -> Unit)  {
    coroutineScope {
        if (response.errorCode == -1) errorBlock()
        else successBlock()
    }
}

fun Activity.onNetError(e: Throwable, func: (e: Throwable) -> Unit) {
    if (e is HttpException) {
        toast(R.string.net_error)
        func(e)
    }
}

fun WanResponse<Any>.isSuccess(): Boolean = this.errorCode == 0

fun transFormSystemChild(children: List<SystemChild>): String {
//用于将集合（或数组）的元素连接成一个字符串。
    return children.joinToString("     ", transform = { child -> child.name })
}