package luyao.util.ktx.core.span

import android.graphics.Paint
import android.text.style.LineHeightSpan

/**
 * Created by luyao
 * on 2019/8/7 16:07
 */
class KtxLineHeightSpan(val height: Int, private val verticalAlignment:Int) : LineHeightSpan {

    companion object{
        const val ALIGN_CENTER = 2
        const val ALIGN_TOP = 3
    }


    override fun chooseHeight(
        text: CharSequence?,
        start: Int,
        end: Int,
        spanstartv: Int,
        lineHeight: Int,
        fm: Paint.FontMetricsInt
    ) {
        var need = height - (lineHeight + fm.descent - fm.ascent - spanstartv)
        if (need > 0) {
            when (verticalAlignment) {
                ALIGN_TOP -> fm.descent += need
                ALIGN_CENTER -> {
                    fm.descent += need / 2
                    fm.ascent -= need / 2
                }
                else -> fm.ascent -= need
            }
        }
        need = height - (lineHeight + fm.bottom - fm.top - spanstartv)
        if (need > 0) {
            when (verticalAlignment) {
                ALIGN_TOP -> fm.bottom += need
                ALIGN_CENTER -> {
                    fm.bottom += need / 2
                    fm.top -= need / 2
                }
                else -> fm.top -= need
            }
        }
    }


}