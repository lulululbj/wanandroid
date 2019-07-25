import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import luyao.util.ktx.ext.getIntent
import luyao.wanandroid.model.bean.WanResponse
import java.io.Serializable

/**
 * Created by Lu
 * on 2018/3/15 21:53
 */

const val TOOL_URL = "http://www.wanandroid.com/tools"
const val GITHUB_PAGE = "https://github.com/lulululbj/wanandroid"
const val ISSUE_URL = "https://github.com/lulululbj/wanandroid/issues"

suspend fun executeResponse(response: WanResponse<Any>, successBlock: suspend CoroutineScope.() -> Unit,
                            errorBlock: suspend CoroutineScope.() -> Unit) {
    coroutineScope {
        if (response.errorCode == -1) errorBlock()
        else successBlock()
    }
}

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


//inline fun <reified T : Activity> Fragment.startKtxActivity(flags: Int? = null, extra: Bundle? = null) =
//        activity?.let {
//            startActivity(activity?.getIntent<T>(flags, extra))
//        }
//
//inline fun <reified T : Activity> Context.startKtxActivity(flags: Int? = null, extra: Bundle? = null) =
//        startActivity(getIntent<T>(flags, extra))
//
//
//inline fun <reified T : Activity> Activity.startKtxActivity(
//        flags: Int? = null,
//        extra: Bundle? = null,
//        value: Pair<String, Any>?
//): Unit =
//        startActivity(getIntent<T>(flags, extra, arrayListOf(value)))
//
//inline fun <reified T : Activity> Fragment.startKtxActivity(
//        flags: Int? = null,
//        extra: Bundle? = null,
//        value: Pair<String, Any>?
//) =
//        activity?.let {
//            startActivity(activity?.getIntent<T>(flags, extra, arrayListOf(value)))
//        }
//
//inline fun <reified T : Activity> Context.startKtxActivity(
//        flags: Int? = null,
//        extra: Bundle? = null,
//        value: Pair<String, Any>?
//): Unit =
//        startActivity(getIntent<T>(flags, extra, arrayListOf(value)))
//
//inline fun <reified T : Activity> Fragment.startKtxActivity(
//        flags: Int? = null,
//        extra: Bundle? = null,
//        values: List<Pair<String, Any>?>?
//) =
//        activity?.let {
//            startActivity(activity?.getIntent<T>(flags, extra, values))
//        }
//
//inline fun <reified T : Activity> Context.startKtxActivity(
//        flags: Int? = null,
//        extra: Bundle? = null,
//        values: List<Pair<String, Any>?>?
//) =
//        startActivity(getIntent<T>(flags, extra, values))
//
//inline fun <reified T : Activity> Fragment.startKtxActivityForResult(
//        requestCode: Int,
//        flags: Int? = null,
//        extra: Bundle? = null
//) =
//        activity?.let {
//            startActivityForResult(activity?.getIntent<T>(flags, extra), requestCode)
//        }
//
//inline fun <reified T : Context> Context.getIntent(flags: Int?, extra: Bundle?): Intent =
//        Intent(this, T::class.java).apply {
//            flags?.let { setFlags(flags) }
//            extra?.let { putExtras(extra) }
//        }
//
//inline fun <reified T : Context> Context.getIntent(
//        flags: Int?,
//        extra: Bundle?,
//        pairs: List<Pair<String, Any>?>?
//): Intent =
//        Intent(this, T::class.java).apply {
//            flags?.let { setFlags(flags) }
//            extra?.let { putExtras(extra) }
//            pairs?.let {
//                for (pair in pairs)
//                    pair?.let {
//                        val name = pair.first
//                        when (val value = pair.second) {
//                            is Int -> putExtra(name, value)
//                            is Byte -> putExtra(name, value)
//                            is Char -> putExtra(name, value)
//                            is Short -> putExtra(name, value)
//                            is Boolean -> putExtra(name, value)
//                            is Long -> putExtra(name, value)
//                            is Float -> putExtra(name, value)
//                            is Double -> putExtra(name, value)
//                            is String -> putExtra(name, value)
//                            is CharSequence -> putExtra(name, value)
//                            is Parcelable -> putExtra(name, value)
//                            is Array<*> -> putExtra(name, value)
//                            is ArrayList<*> -> putExtra(name, value)
//                            is Serializable -> putExtra(name, value)
//                            is BooleanArray -> putExtra(name, value)
//                            is ByteArray -> putExtra(name, value)
//                            is ShortArray -> putExtra(name, value)
//                            is CharArray -> putExtra(name, value)
//                            is IntArray -> putExtra(name, value)
//                            is LongArray -> putExtra(name, value)
//                            is FloatArray -> putExtra(name, value)
//                            is DoubleArray -> putExtra(name, value)
//                            is Bundle -> putExtra(name, value)
//                            is Intent -> putExtra(name, value)
//                            else -> {
//                            }
//                        }
//                    }
//            }
//        }
