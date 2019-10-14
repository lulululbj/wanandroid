package luyao.util.ktx.ext

import android.content.ClipData
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.view.View
import androidx.annotation.RequiresApi

/**
 * Created by luyao
 * on 2019/6/14 14:23
 */

/**
 * Whether horizontal layout direction of this view is from Right to Left.
 */
val Context.isRTLLayout: Boolean
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    get() = resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL

/**
 * The absolute width of the available display size in pixels
 */
val Context.screenWidth
    get() = resources.displayMetrics.widthPixels

/**
 * The absolute height of the available display size in pixels
 */
val Context.screenHeight
    get() = resources.displayMetrics.heightPixels


fun fromM() = fromSpecificVersion(Build.VERSION_CODES.M)
fun beforeM() = beforeSpecificVersion(Build.VERSION_CODES.M)
fun fromN() = fromSpecificVersion(Build.VERSION_CODES.N)
fun beforeN() = beforeSpecificVersion(Build.VERSION_CODES.N)
fun fromO() = fromSpecificVersion(Build.VERSION_CODES.O)
fun beforeO() = beforeSpecificVersion(Build.VERSION_CODES.O)
fun fromP() = fromSpecificVersion(Build.VERSION_CODES.P)
fun beforeP() = beforeSpecificVersion(Build.VERSION_CODES.P)
fun fromSpecificVersion(version: Int): Boolean = Build.VERSION.SDK_INT >= version
fun beforeSpecificVersion(version: Int): Boolean = Build.VERSION.SDK_INT < version

fun <T> Any?.notNull(f: () -> T, t: () -> T): T {
    return if (this != null) f() else t()
}

fun Context.dp2px(dp: Int): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

fun Context.px2dp(px: Int): Int {
    val scale = resources.displayMetrics.density
    return (px / scale + 0.5f).toInt()
}

fun View.dp2px(dp: Int): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

fun View.px2dp(px: Int): Int {
    val scale = resources.displayMetrics.density
    return (px / scale + 0.5f).toInt()
}

/**
 * Sets the [text] on the clipboard
 */
fun Context.copyToClipboard(text: String, label: String = "KTX") {
    val clipData = ClipData.newPlainText(label, text)
    clipboardManager?.primaryClip = clipData
}

/**
 * Check if the accessibility Service which name is [serviceName] is enabled
 */
fun Context.checkAccessibilityServiceEnabled(serviceName: String): Boolean {
    val settingValue =
        Settings.Secure.getString(
            applicationContext.contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        )
    return settingValue.notNull({
        var result = false
        val splitter = TextUtils.SimpleStringSplitter(':')
        while (splitter.hasNext()) {
            if (splitter.next().equals(serviceName, true)) {
                result = true
                break
            }
        }
        result
    }, { false })
}

