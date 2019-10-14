package luyao.util.ktx.core.span

import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.style.LeadingMarginSpan
import androidx.annotation.ColorInt
import androidx.annotation.NonNull
import androidx.annotation.Px

/**
 * copy form aosp
 * Created by luyao
 * on 2019/8/9 15:50
 */

class KtxQuoteSpan : LeadingMarginSpan {

    /**
     * Get the color of the quote stripe.
     *
     * @return the color of the quote stripe.
     */
    @ColorInt
    @get:ColorInt
    val color: Int
    /**
     * Get the width of the quote stripe.
     *
     * @return the width of the quote stripe.
     */
    @Px
    val stripeWidth: Int
    /**
     * Get the width of the gap between the stripe and the text.
     *
     * @return the width of the gap between the stripe and the text.
     */
    @Px
    val gapWidth: Int

    /**
     * @hide
     */
//    val spanTypeIdInternal: Int
//        get() = TextUtils.QUOTE_SPAN

    /**
     * Creates a [QuoteSpan] based on a color, a stripe width and the width of the gap
     * between the stripe and the text.
     *
     * @param color       the color of the quote stripe.
     * @param stripeWidth the width of the stripe.
     * @param gapWidth    the width of the gap between the stripe and the text.
     */
    @JvmOverloads
    constructor(
        @ColorInt color: Int = STANDARD_COLOR, stripeWidth: Int = STANDARD_STRIPE_WIDTH_PX,
        gapWidth: Int = STANDARD_GAP_WIDTH_PX
    ) {
        this.color = color
        this.stripeWidth = stripeWidth
        this.gapWidth = gapWidth
    }

    override fun getLeadingMargin(first: Boolean): Int {
        return stripeWidth + gapWidth
    }

    override fun drawLeadingMargin(
        @NonNull c: Canvas, @NonNull p: Paint, x: Int, dir: Int,
        top: Int, baseline: Int, bottom: Int,
        @NonNull text: CharSequence, start: Int, end: Int,
        first: Boolean, @NonNull layout: Layout
    ) {
        val style = p.style
        val color = p.color

        p.style = Paint.Style.FILL
        p.color = this.color

        c.drawRect(x.toFloat(), top.toFloat(), (x + dir * stripeWidth).toFloat(), bottom.toFloat(), p)

        p.style = style
        p.color = color
    }

    companion object {
        /**
         * Default stripe width in pixels.
         */
        val STANDARD_STRIPE_WIDTH_PX = 2

        /**
         * Default gap width in pixels.
         */
        val STANDARD_GAP_WIDTH_PX = 2

        /**
         * Default color for the quote stripe.
         */
        @ColorInt
        val STANDARD_COLOR = -0xffff01
    }
}

