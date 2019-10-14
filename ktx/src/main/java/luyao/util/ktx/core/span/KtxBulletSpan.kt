package luyao.util.ktx.core.span

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.os.Parcel
import android.text.Layout
import android.text.Spanned
import android.text.style.BulletSpan
import android.text.style.LeadingMarginSpan
import androidx.annotation.ColorInt
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.Px

/**
 * Created by luyao
 * on 2019/8/9 16:06
 */

class KtxBulletSpan : LeadingMarginSpan {

    /**
     * Get the distance, in pixels, between the bullet point and the paragraph.
     *
     * @return the distance, in pixels, between the bullet point and the paragraph.
     */
    @Px
    val gapWidth: Int
    /**
     * Get the radius, in pixels, of the bullet point.
     *
     * @return the radius, in pixels, of the bullet point.
     */
    @Px
    val bulletRadius: Int
    private var mBulletPath: Path? = null
    /**
     * Get the bullet point color.
     *
     * @return the bullet point color
     */
    @ColorInt
    val color: Int
    private val mWantColor: Boolean

    /**
     * Creates a [BulletSpan] with the default values.
     */
    constructor() : this(STANDARD_GAP_WIDTH, STANDARD_COLOR, false, STANDARD_BULLET_RADIUS) {}

    /**
     * Creates a [BulletSpan] based on a gap width
     *
     * @param gapWidth the distance, in pixels, between the bullet point and the paragraph.
     */
    constructor(gapWidth: Int) : this(gapWidth, STANDARD_COLOR, false, STANDARD_BULLET_RADIUS) {}

    /**
     * Creates a [BulletSpan] based on a gap width and a color integer.
     *
     * @param gapWidth the distance, in pixels, between the bullet point and the paragraph.
     * @param color    the bullet point color, as a color integer
     * @see android.content.res.Resources.getColor
     */
    constructor(gapWidth: Int, @ColorInt color: Int) : this(gapWidth, color, true, STANDARD_BULLET_RADIUS) {}

    /**
     * Creates a [BulletSpan] based on a gap width and a color integer.
     *
     * @param gapWidth     the distance, in pixels, between the bullet point and the paragraph.
     * @param color        the bullet point color, as a color integer.
     * @param bulletRadius the radius of the bullet point, in pixels.
     * @see android.content.res.Resources.getColor
     */
    constructor(gapWidth: Int, @ColorInt color: Int, bulletRadius: Int) : this(
        gapWidth,
        color,
        true,
        bulletRadius
    )

    private constructor(
        gapWidth: Int, @ColorInt color: Int, wantColor: Boolean,
        bulletRadius: Int
    ) {
        this.gapWidth = gapWidth
        this.bulletRadius = bulletRadius
        this.color = color
        mWantColor = wantColor
    }

    /**
     * Creates a [BulletSpan] from a parcel.
     */
    constructor(@NonNull src: Parcel) {
        gapWidth = src.readInt()
        mWantColor = src.readInt() != 0
        color = src.readInt()
        bulletRadius = src.readInt()
    }


    override fun getLeadingMargin(first: Boolean): Int {
        return 2 * bulletRadius + gapWidth
    }

    override fun drawLeadingMargin(
        @NonNull canvas: Canvas, @NonNull paint: Paint, x: Int, dir: Int,
        top: Int, baseline: Int, bottom: Int,
        @NonNull text: CharSequence, start: Int, end: Int,
        first: Boolean, @Nullable layout: Layout?
    ) {
        val bottom = bottom
        if ((text as Spanned).getSpanStart(this) == start) {
            val style = paint.style
            var oldcolor = 0

            if (mWantColor) {
                oldcolor = paint.color
                paint.color = color
            }

            paint.style = Paint.Style.FILL

//            if (layout != null) {
//                // "bottom" position might include extra space as a result of line spacing
//                // configuration. Subtract extra space in order to show bullet in the vertical
//                // center of characters.
//                val line = layout.getLineForOffset(start)
//                bottom = bottom - layout.getLineExtra(line)
//            }

            val yPosition = (top + bottom) / 2f
            val xPosition = (x + dir * bulletRadius).toFloat()

            if (canvas.isHardwareAccelerated) {
                if (mBulletPath == null) {
                    mBulletPath = Path()
                    mBulletPath!!.addCircle(0.0f, 0.0f, bulletRadius.toFloat(), Path.Direction.CW)
                }

                canvas.save()
                canvas.translate(xPosition, yPosition)
                canvas.drawPath(mBulletPath!!, paint)
                canvas.restore()
            } else {
                canvas.drawCircle(xPosition, yPosition, bulletRadius.toFloat(), paint)
            }

            if (mWantColor) {
                paint.color = oldcolor
            }

            paint.style = style
        }
    }

    companion object {
        // Bullet is slightly bigger to avoid aliasing artifacts on mdpi devices.
         val STANDARD_BULLET_RADIUS = 4
        val STANDARD_GAP_WIDTH = 2
        private val STANDARD_COLOR = 0
    }
}
