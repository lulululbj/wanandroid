package luyao.mvvm.core.binding

import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter

/**
 * Created by luyao
 * on 2020/5/26 15:57
 */
@BindingAdapter("title", "icon", "navigationClick", requireAll = false)
fun Toolbar.init(titleResId: Int, iconResId: Int, action: () -> Unit) {
    setTitle(titleResId)
    setNavigationIcon(iconResId)
    setNavigationOnClickListener { action() }
}