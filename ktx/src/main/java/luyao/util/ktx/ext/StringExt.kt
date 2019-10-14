package luyao.util.ktx.ext

import android.widget.TextView

/**
 * Created by luyao
 * on 2019/7/23 9:25
 */

/**
 * if [String.isNullOrEmpty], invoke f()
 * otherwise invoke t()
 */
fun <T> String?.notNull(f: () -> T, t: () -> T): T {
    return if (isNullOrEmpty()) f() else t()
}

/**
 * whether string only contains digits
 */
fun String.areDigitsOnly() = matches(Regex("[0-9]+"))