import android.content.Context
import android.content.Intent
import android.net.Uri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import luyao.wanandroid.model.bean.WanResponse

/**
 * Created by Lu
 * on 2018/3/15 21:53
 */

const val TOOL_URL = "http://www.wanandroid.com/tools"
const val GITHUB_PAGE = "https://github.com/lulululbj/wanandroid"
const val HOME_PAGE = "http://sunluyao.com"
const val ISSUE_URL = "https://github.com/lulululbj/wanandroid/issues"

fun Context.openBrowser(url: String) {
    Intent(Intent.ACTION_VIEW, Uri.parse(url)).run { startActivity(this) }
}

suspend fun executeResponse(response: WanResponse<Any>, successBlock: suspend CoroutineScope.() -> Unit,
                            errorBlock: suspend CoroutineScope.() -> Unit) {
    coroutineScope {
        if (response.errorCode == -1) errorBlock()
        else successBlock()
    }
}