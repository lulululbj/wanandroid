package luyao.util.ktx.ext

import android.util.Log
import luyao.util.ktx.BuildConfig

/**
 * Created by luyao
 * on 2019/7/3 15:37
 */

const val TAG = "ktx"

var showLog = BuildConfig.DEBUG

private enum class LEVEL {
    V, D, I, W, E
}

fun String.logv(tag: String = TAG) = log(LEVEL.V, tag, this)
fun String.logd(tag: String = TAG) = log(LEVEL.D, tag, this)
fun String.logi(tag: String = TAG) = log(LEVEL.I, tag, this)
fun String.logw(tag: String = TAG) = log(LEVEL.W, tag, this)
fun String.loge(tag: String = TAG) = log(LEVEL.E, tag, this)

private fun log(level: LEVEL, tag: String, message: String) {
    if (!showLog) return
    when (level) {
        LEVEL.V -> Log.v(tag, message)
        LEVEL.D -> Log.d(tag, message)
        LEVEL.I -> Log.i(tag, message)
        LEVEL.W -> Log.w(tag, message)
        LEVEL.E -> Log.e(tag, message)
    }
}