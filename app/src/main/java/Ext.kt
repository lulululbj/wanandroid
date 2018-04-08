import android.content.Context
import android.support.annotation.StringRes
import android.view.View
import android.widget.Toast

/**
 * Created by Lu
 * on 2018/3/15 21:53
 */

const val TOOL_URL = "http://www.wanandroid.com/tools"

fun View.dp2px(dp: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

fun View.px2dp(px: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (px / scale + 0.5f).toInt()
}

var showToast: Toast? = null
fun Context.toast(content: String) {
    showToast?.apply {
        setText(content)
        show()
    } ?: run {
        Toast.makeText(this.applicationContext, content, Toast.LENGTH_SHORT).apply {
            showToast = this
        }.show()
    }
}

/**
 * show toast
 * @param id strings.xml
 */
fun Context.toast(@StringRes id: Int) {
    toast(getString(id))
}