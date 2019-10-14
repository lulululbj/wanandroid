package luyao.util.ktx.ext

import org.junit.Test

import org.junit.Assert.*

/**
 * Created by luyao
 * on 2019/9/18 14:33
 */
class TransformExtKtTest {

    val integer = 207689539
    val intBytes = byteArrayOf(12,97,23,67)

    val short : Short = 32767
    val shortBytes = byteArrayOf(-1,127)


     val long = 2223372036854775807L
     val longBytes = byteArrayOf(-1, -1, 67, 108, 22, 1, -37, 30)

    @Test
    fun test() {

        val intToBytes =integer.toByteArray()
        assertArrayEquals(intBytes,intToBytes)

        val bytesToInt = intBytes.toInt()
        assertEquals(integer,bytesToInt)


        val shortToBytes = short.toByteArray()
        assertArrayEquals(shortBytes,shortToBytes)

        val bytesToShort = shortToBytes.toShort()
        assertEquals(short,bytesToShort)

        val longToBytes = long.toByteArray()
        assertArrayEquals(longBytes,longToBytes)

        val bytesToLong = longToBytes.toLong()
        assertEquals(long,bytesToLong)
    }

}