package luyao.mvvm.core.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Lu
 * on 2018/3/15 21:45
 */
class SpaceItemDecoration(private val top: Int = 0, private val left: Int = 0, private val bottom: Int = 0, private val right: Int = 0) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (top > 0) outRect.top = top
        if (left > 0) outRect.left = left
        if (bottom > 0) outRect.bottom = bottom
        if (right > 0) outRect.right = right
    }
}