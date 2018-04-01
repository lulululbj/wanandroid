import android.view.View

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