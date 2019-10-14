package luyao.util.ktx.ext

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import java.io.ByteArrayOutputStream


/**
 * Created by luyao
 * on 2019/6/14 15:43
 */

private val HEX_DIGITS =
    charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')

/**
 * byte array to hex string
 */
fun ByteArray.toHexString(): String {
    val result = CharArray(size shl 1)
    var index = 0
    for (b in this) {
        result[index++] = HEX_DIGITS[b.toInt().shr(4) and 0xf]
        result[index++] = HEX_DIGITS[b.toInt() and 0xf]
    }
    return String(result)
}

/**
 * byte array to int
 */
fun ByteArray.toInt(): Int = TransformUtils.bytes2Int(this)

/**
 * int to byte array
 */
fun Int.toByteArray(): ByteArray = TransformUtils.int2Bytes(this)

/**
 * byte array to short
 */
fun ByteArray.toShort(): Short = TransformUtils.bytes2Short(this)

/**
 * short to byte array
 */
fun Short.toByteArray(): ByteArray = TransformUtils.short2Bytes(this)

/**
 * byte array to long
 */
fun ByteArray.toLong(): Long = TransformUtils.bytes2Long(this)

/**
 * long to byte array
 */
fun Long.toByteArray(): ByteArray = TransformUtils.long2Bytes(this)

/**
 * bitmap to byte array
 */
fun Bitmap.toByteArray(format: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG): ByteArray {
    ByteArrayOutputStream().use {
        compress(format, 100, it)
        return toByteArray()
    }
}

/**
 * byte array to bitmap
 */
fun ByteArray.toBitmap(): Bitmap = BitmapFactory.decodeByteArray(this, 0, size)

/**
 * drawable to bitmap
 */
fun Drawable.toBitmap(): Bitmap {
    if (this is BitmapDrawable) return bitmap

    val bitmap = if (intrinsicHeight <= 0 || intrinsicWidth <= 0) {
        Bitmap.createBitmap(
            1,
            1,
            if (opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
        )
    } else {
        Bitmap.createBitmap(
            intrinsicWidth,
            intrinsicHeight,
            if (opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
        )
    }

    val canvas = Canvas(bitmap)
    setBounds(0, 0, canvas.width, canvas.height)
    draw(canvas)
    return bitmap
}

/**
 * bitmap to drawable
 */
fun Bitmap.toDrawable(context: Context): Drawable = BitmapDrawable(context.resources, this)

/**
 * drawable to byte array
 */
fun Drawable.toByteArray(format: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG): ByteArray =
    toBitmap().toByteArray(format)

/**
 * byte array to drawable
 */
fun ByteArray.toDrawable(context: Context): Drawable = toBitmap().toDrawable(context)

