package luyao.mvvm.core.binding

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * Created by luyao
 * on 2020/3/3 14:29
 */
@BindingAdapter("isVisible")
fun View.isVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}