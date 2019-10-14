package luyao.util.ktx.core.span

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.style.ImageSpan

/**
 * Created by luyao
 * on 2019/8/14 9:17
 */
class KtxImageSpan(
    drawable: Drawable,
    verticalAlignment: Int = ALIGN_MIDDLE,
    fontWidthMultiple: Float = -1f,
    marginLeft: Int = 0,
    marginRight: Int = 0
) : ImageSpan(drawable, verticalAlignment) {


    companion object {
        const val ALIGN_MIDDLE = -100 // 不要和父类重复
    }

    private val sVerticalAlignment = verticalAlignment
    private val mFontWidthMultiple = fontWidthMultiple
    private val mMarginLeft = marginLeft
    private val mMarginRight = marginRight
    private var mAvoidSuperChangeFontMetrics = false
    private var mWidth: Int = 0

    override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        if (mAvoidSuperChangeFontMetrics) mWidth = drawable.bounds.right
        else mWidth = super.getSize(paint, text, start, end, fm)

        if (mFontWidthMultiple > 0) {
            mWidth = (paint.measureText("子") * mFontWidthMultiple).toInt()
        }

        if ((mMarginLeft > 0) or (mMarginRight > 0)) {
            mWidth = drawable.intrinsicWidth + mMarginLeft + mMarginRight
        }

        return mWidth
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        if (sVerticalAlignment == ALIGN_MIDDLE) {
            canvas.save()
            val fontMetricsInt = paint.fontMetricsInt
            val fontTop = y + fontMetricsInt.top
            val fontMetricsHeight = fontMetricsInt.bottom - fontMetricsInt.top
            val iconHeight = drawable.bounds.bottom - drawable.bounds.top
            val iconTop = fontTop + (fontMetricsHeight - iconHeight) / 2
            canvas.translate(x+mMarginLeft, iconTop.toFloat())
            drawable.draw(canvas)
            canvas.restore()
        } else
            super.draw(canvas, text, start, end, x+mMarginLeft, top, y, bottom, paint)
    }
}