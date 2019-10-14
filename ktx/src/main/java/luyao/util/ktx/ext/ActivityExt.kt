package luyao.util.ktx.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import java.io.Serializable

/**
 * Created by luyao
 * on 2019/7/9 14:17
 */


inline fun <reified T : Activity> Activity.startKtxActivity(
    flags: Int? = null,
    extra: Bundle? = null,
    value: Pair<String, Any>? = null,
    values: Collection<Pair<String, Any>?>? = null
) {

    val list = ArrayList<Pair<String, Any>?>()
    value?.let { list.add(it) }
    values?.let { list.addAll(it) }
    startActivity(getIntent<T>(flags, extra, list))
}

inline fun <reified T : Activity> Fragment.startKtxActivity(
    flags: Int? = null,
    extra: Bundle? = null,
    value: Pair<String, Any>? = null,
    values: Collection<Pair<String, Any>?>? = null
) =
    activity?.let {
        val list = ArrayList<Pair<String, Any>?>()
        value?.let { v -> list.add(v) }
        values?.let { v -> list.addAll(v) }
        startActivity(it.getIntent<T>(flags, extra, list))
    }

inline fun <reified T : Activity> Context.startKtxActivity(
    flags: Int? = null,
    extra: Bundle? = null,
    value: Pair<String, Any>? = null,
    values: Collection<Pair<String, Any>?>? = null
) {
    val list = ArrayList<Pair<String, Any>?>()
    value?.let { v -> list.add(v) }
    values?.let { v -> list.addAll(v) }
    startActivity(getIntent<T>(flags, extra, list))
}

inline fun <reified T : Activity> Activity.startKtxActivityForResult(
    requestCode: Int,
    flags: Int? = null,
    extra: Bundle? = null,
    value: Pair<String, Any>? = null,
    values: Collection<Pair<String, Any>?>? = null
) {
    val list = ArrayList<Pair<String, Any>?>()
    value?.let { list.add(it) }
    values?.let { list.addAll(it) }
    startActivityForResult(getIntent<T>(flags, extra, list), requestCode)
}

inline fun <reified T : Activity> Fragment.startKtxActivityForResult(
    requestCode: Int,
    flags: Int? = null,
    extra: Bundle? = null,
    value: Pair<String, Any>? = null,
    values: Collection<Pair<String, Any>?>? = null
) =
    activity?.let {
        val list = ArrayList<Pair<String, Any>?>()
        value?.let { list.add(it) }
        values?.let { list.addAll(it) }
        startActivityForResult(activity?.getIntent<T>(flags, extra, list), requestCode)
    }

inline fun <reified T : Context> Context.getIntent(
    flags: Int? = null,
    extra: Bundle? = null,
    pairs: List<Pair<String, Any>?>? = null
): Intent =
    Intent(this, T::class.java).apply {
        flags?.let { setFlags(flags) }
        extra?.let { putExtras(extra) }
        pairs?.let {
            for (pair in pairs)
                pair?.let {
                    val name = pair.first
                    when (val value = pair.second) {
                        is Int -> putExtra(name, value)
                        is Byte -> putExtra(name, value)
                        is Char -> putExtra(name, value)
                        is Short -> putExtra(name, value)
                        is Boolean -> putExtra(name, value)
                        is Long -> putExtra(name, value)
                        is Float -> putExtra(name, value)
                        is Double -> putExtra(name, value)
                        is String -> putExtra(name, value)
                        is CharSequence -> putExtra(name, value)
                        is Parcelable -> putExtra(name, value)
                        is Array<*> -> putExtra(name, value)
                        is ArrayList<*> -> putExtra(name, value)
                        is Serializable -> putExtra(name, value)
                        is BooleanArray -> putExtra(name, value)
                        is ByteArray -> putExtra(name, value)
                        is ShortArray -> putExtra(name, value)
                        is CharArray -> putExtra(name, value)
                        is IntArray -> putExtra(name, value)
                        is LongArray -> putExtra(name, value)
                        is FloatArray -> putExtra(name, value)
                        is DoubleArray -> putExtra(name, value)
                        is Bundle -> putExtra(name, value)
                        is Intent -> putExtra(name, value)
                        else -> {
                        }
                    }
                }
        }
    }

fun Activity.hideKeyboard() {
    inputMethodManager?.hideSoftInputFromWindow((currentFocus ?: View(this)).windowToken, 0)
    window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    currentFocus?.clearFocus()
}

fun Activity.showKeyboard(et: EditText) {
    et.requestFocus()
    inputMethodManager?.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT)
}

fun Activity.hideKeyboard(view: View) {
    inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
}

