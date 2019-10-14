package luyao.util.ktx.core.span

import android.graphics.BlurMaskFilter
import android.graphics.Rect
import android.graphics.Typeface
import android.text.Layout
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.widget.TextView
import androidx.annotation.Px
import androidx.core.content.ContextCompat
import luyao.util.ktx.Ktx
import luyao.util.ktx.core.span.KtxBulletSpan.Companion.STANDARD_BULLET_RADIUS
import luyao.util.ktx.core.span.KtxQuoteSpan.Companion.STANDARD_GAP_WIDTH_PX
import luyao.util.ktx.core.span.KtxQuoteSpan.Companion.STANDARD_STRIPE_WIDTH_PX
import luyao.util.ktx.ext.fromP

/**
 * Created by luyao
 * on 2019/8/6 16:46
 */
class KtxSpan {
    private val LINE_SEPARATOR = System.getProperty("line.separator")
    private var mText: CharSequence = ""

    /**
     * Set the text size to [mTextSize] physical pixels, or to [mTextSize]
     * device-independent pixels if [mIsDp] is true.
     */
    private var mTextSize = -1
    private var mIsDp = true

    /**
     * The font family for this typeface.
     * Examples include "monospace", "serif", and "sans-serif"
     */
    private var mFontFamily = ""

    /**
     * Set the custom typeface
     */
    private var mCustomTypeface: Typeface? = null

    /**
     * Uniformly scales the size of the text to which it's attached by a certain [mFontProportion].
     */
    private var mFontProportion = -1f

    /**
     * Scales horizontally the size of the text to which it's attached by a certain factor.
     * Values > 1.0 will stretch the text wider. Values < 1.0 will stretch the text narrower.
     */
    private var mXFontProportion = -1f

    /**
     * Set the lineHeight
     */
    private var mLineHeight = -1

    /**
     * Changes the color of the text to which the span is attached.
     */
    private var mForegroundColor = -1

    /**
     * Changes the background color of the text to which the span is attached.
     */
    private var mBackgroundColor = -1

    /**
     * add blank line which height is [mBlankLineHeight] after this span
     * if [mAddBlankLineAlways] is true , add black line for each span(not effect previous spans)
     */
    private var mBlankLineHeight = -1
    private var mAddBlankLineAlways = false

    private var mAlignment: Layout.Alignment? = null

    /**
     * Set the style of the text it's attached to
     */
    private var isBold = false
    private var isItalic = false
    private var isBoldAndItalic = false

    /**
     * Strikes through the text it's attached to
     */
    private var isStrikethrough = false

    /**
     * Underlines the text it's attached to
     */
    private var isUnderline = false

    /**
     * Move the position of the text baseline higher
     */
    private var isSuperscript = false

    /**
     * Move the position of the text baseline lower
     */
    private var isSubscript = false

    /**
     * Start in a new line
     */
    private var isNewLine = true

    /**
     * Take separate indents for the first and subsequent lines
     *
     * [mFirst] is the indent for the first line of the paragraph
     * [mRest] is the indent for the remaining lines of the paragraph
     */
    private var mFirst = 0
    private var mRest = 0

    /**
     * Creates a [KtxQuoteSpan] based on a color, a stripe width and the width of the gap
     * between the stripe and the text.
     *
     * [mQuoteColor]       the color of the quote stripe.
     * [mQuoteStripeWidth] the width of the stripe.
     * [mQuoteGapWidth]    the width of the gap between the stripe and the text.
     */
    private var mQuoteColor = -1
    private var mQuoteStripeWidth = STANDARD_STRIPE_WIDTH_PX
    private var mQuoteGapWidth = STANDARD_GAP_WIDTH_PX

    /**
     * Creates a [KtxBulletSpan] based on a gap width and a color integer.
     *
     * [mBulletGapWidth]     the distance, in pixels, between the bullet point and the paragraph.
     * [mBulletColor]        the bullet point color, as a color integer.
     * [mBulletRadius] the radius of the bullet point, in pixels.
     */
    private var mBulletColor = -1
    private var mBulletRadius = STANDARD_BULLET_RADIUS
    private var mBulletGapWidth = STANDARD_GAP_WIDTH_PX

    /**
     * Create a [BlurMaskFilter]
     *
     * [mBlurRadius] The radius to extend the blur from the original mask. Must be > 0.
     * [mBlurStyle] The Blur to use
     */
    private var mBlurRadius = -1f
    private var mBlurStyle: BlurMaskFilter.Blur = BlurMaskFilter.Blur.NORMAL

    private var mClickableSpan: ClickableSpan? = null
    private var mUrl: String? = null

    private var mFlag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE

    lateinit var mTextView: TextView
    val mSpanBuilder by lazy { SpannableStringBuilder() }

    fun with(view: TextView): KtxSpan {
        this.mTextView = view
        return this
    }

    fun text(
        text: CharSequence,
        isNewLine: Boolean = true,
        textSize: Int = -1,
        fontFamily: String = "",
        customTypeFace: Typeface? = null,
        isDp: Boolean = true,
        fontProportion: Float = -1f,
        xFontProportion: Float = -1f,
        lineHeight: Int = -1,
        foregroundColor: Int = -1,
        backgroundColor: Int = -1,
        alignment: Layout.Alignment? = null,
        isBold: Boolean = false,
        isStrikethrough: Boolean = false,
        isUnderline: Boolean = false,
        isItalic: Boolean = false,
        isBoldAndItalic: Boolean = false,
        isSuperscript: Boolean = false,
        isSubscript: Boolean = false,
        first: Int = 0,
        rest: Int = 0,
        quoteColor: Int = -1,
        quoteStripeWidth: Int = STANDARD_STRIPE_WIDTH_PX,
        quoteGapWidth: Int = STANDARD_GAP_WIDTH_PX,
        bulletColor: Int = -1,
        bulletRadius: Int = STANDARD_BULLET_RADIUS,
        bulletGapWidth: Int = STANDARD_GAP_WIDTH_PX,
        clickSpan: ClickableSpan? = null,
        url: String? = null,
        blurRadius: Float = -1f,
        blurStyle: BlurMaskFilter.Blur = BlurMaskFilter.Blur.NORMAL
    ): KtxSpan {
        mText = "$text${if (isNewLine) LINE_SEPARATOR else ""}"
        mTextSize = textSize
        mFontFamily = fontFamily
        mCustomTypeface = customTypeFace
        mIsDp = isDp
        mFontProportion = fontProportion
        mXFontProportion = xFontProportion
        mLineHeight = lineHeight
        mForegroundColor = foregroundColor
        mBackgroundColor = backgroundColor
        mAlignment = alignment
        mFirst = first
        mRest = rest
        mQuoteColor = quoteColor
        mQuoteStripeWidth = quoteStripeWidth
        mQuoteGapWidth = quoteGapWidth
        mBulletColor = bulletColor
        mBulletRadius = bulletRadius
        mBulletGapWidth = bulletGapWidth
        mClickableSpan = clickSpan
        mUrl = url
        mBlurRadius = blurRadius
        mBlurStyle = blurStyle

        this.isBold = isBold
        this.isItalic = isItalic
        this.isBoldAndItalic = isBoldAndItalic
        this.isStrikethrough = isStrikethrough
        this.isUnderline = isUnderline
        this.isSuperscript = isSuperscript
        this.isSubscript = isSubscript
        this.isNewLine = isNewLine

        updateSpan()
        return this
    }

    fun image(
        resId: Int,
        verticalAlignment: Int = KtxImageSpan.ALIGN_MIDDLE,
        marginLeft: Int = 0,
        marginRight: Int = 0,
        offsetY: Int = 0,
        fontWidthMultiple: Float = -1f
    ) {
        mText = "[icon]"
        val start = mSpanBuilder.length
        mSpanBuilder.append(mText)
        val end = mSpanBuilder.length

        val drawable = ContextCompat.getDrawable(Ktx.app, resId)

        drawable?.let {
            it.bounds = Rect(
                0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight
            )
            mSpanBuilder.setSpan(
                KtxImageSpan(drawable, verticalAlignment, fontWidthMultiple, marginLeft, marginRight),
                start,
                end,
                mFlag
            )
        }
    }

    fun blankLine(@Px height: Int, addBlankLineAlways: Boolean = false) {
        mText = "[space]$LINE_SEPARATOR"
        val start = mSpanBuilder.length
        mSpanBuilder.append(mText)
        val end = mSpanBuilder.length

        mBlankLineHeight = height
        mAddBlankLineAlways = addBlankLineAlways

        if (mBlankLineHeight > -1) {
            mSpanBuilder.setSpan(KtxBlockLineSpan(mBlankLineHeight), start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        }
    }

    private fun updateSpan() {
        if (mText.isEmpty()) return
        val start = mSpanBuilder.length
        mSpanBuilder.append(mText)
        val end = mSpanBuilder.length

        mAlignment?.let { mSpanBuilder.setSpan(AlignmentSpan.Standard(it), start, end, mFlag) }

        mClickableSpan?.let {
            if (mTextView.movementMethod == null) mTextView.movementMethod = LinkMovementMethod.getInstance()
            mSpanBuilder.setSpan(it, start, end, mFlag)
        }

        mUrl?.let { mSpanBuilder.setSpan(URLSpan(it), start, end, mFlag) }

        if (mTextSize != -1)
            mSpanBuilder.setSpan(AbsoluteSizeSpan(mTextSize, mIsDp), start, end, mFlag)

        if (mFontFamily.isNotEmpty())
            mSpanBuilder.setSpan(TypefaceSpan(mFontFamily), start, end, mFlag)

        mCustomTypeface?.let {
            mSpanBuilder.setSpan(KtxTypefaceSpan(mCustomTypeface!!), start, end, mFlag)
        }

        if (mFontProportion != -1f)
            mSpanBuilder.setSpan(RelativeSizeSpan(mFontProportion), start, end, mFlag)

        if (mXFontProportion != -1f)
            mSpanBuilder.setSpan(ScaleXSpan(mXFontProportion), start, end, mFlag)

        if (mLineHeight != -1)
            mSpanBuilder.setSpan(KtxLineHeightSpan(mLineHeight, KtxLineHeightSpan.ALIGN_CENTER), start, end, mFlag)

        if (mForegroundColor != -1)
            mSpanBuilder.setSpan(ForegroundColorSpan(mForegroundColor), start, end, mFlag)

        if (mBackgroundColor != -1)
            mSpanBuilder.setSpan(BackgroundColorSpan(mBackgroundColor), start, end, mFlag)

        mSpanBuilder.setSpan(LeadingMarginSpan.Standard(mFirst, mRest), start, end, mFlag)

        if (mQuoteColor != -1)
            mSpanBuilder.setSpan(
                if (fromP()) QuoteSpan(
                    mQuoteColor,
                    mQuoteStripeWidth,
                    mQuoteGapWidth
                ) else KtxQuoteSpan(mQuoteColor, mQuoteStripeWidth, mQuoteGapWidth), start, end, mFlag
            )

        if (mBulletColor != -1)
            mSpanBuilder.setSpan(
                if (fromP()) BulletSpan(mBulletGapWidth, mBulletColor, mBulletRadius)
                else KtxBulletSpan(mBulletGapWidth, mBulletColor, mBulletRadius), start, end, mFlag
            )

        if (mBlurRadius != -1f)
            mSpanBuilder.setSpan(MaskFilterSpan(BlurMaskFilter(mBlurRadius, mBlurStyle)), start, end, mFlag)

        if (isBold)
            mSpanBuilder.setSpan(StyleSpan(Typeface.BOLD), start, end, mFlag)

        if (isItalic)
            mSpanBuilder.setSpan(StyleSpan(Typeface.ITALIC), start, end, mFlag)

        if (isBoldAndItalic)
            mSpanBuilder.setSpan(StyleSpan(Typeface.BOLD_ITALIC), start, end, mFlag)

        if (isStrikethrough)
            mSpanBuilder.setSpan(StrikethroughSpan(), start, end, mFlag)

        if (isUnderline)
            mSpanBuilder.setSpan(UnderlineSpan(), start, end, mFlag)

        if (isSuperscript)
            mSpanBuilder.setSpan(SuperscriptSpan(), start, end, mFlag)

        if (isSubscript)
            mSpanBuilder.setSpan(SubscriptSpan(), start, end, mFlag)

        if (mAddBlankLineAlways && isNewLine)
            blankLine(mBlankLineHeight, mAddBlankLineAlways)

    }


    inline fun show(func: KtxSpan.() -> Unit): KtxSpan {
        func()
        mTextView.text = mSpanBuilder
        return this
    }
}

