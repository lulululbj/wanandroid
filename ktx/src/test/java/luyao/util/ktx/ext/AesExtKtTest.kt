package luyao.util.ktx.ext

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.File

/**
 * Created by luyao
 * on 2019/7/1 16:24
 */
class AesExtKtTest {

    private val plainText = "luyao"
    var key: ByteArray = ByteArray(128)
    var iv: ByteArray = ByteArray(128)

    @Before
    fun setUp() {
        key = initAESKey()
        iv = initAESKey()
    }

    @Test
    fun aesCFB() {
        val byteEncrypt = plainText.toByteArray().aesEncrypt(key, iv)
        val byteDecrypt = byteEncrypt.aesDecrypt(key, iv)
        assertEquals(plainText, String(byteDecrypt))
    }

    @Test
    fun aesCBC() {
        val byteEncrypt = plainText.toByteArray().aesEncrypt(key, iv, "AES/CBC/PKCS5Padding")
        val byteDecrypt = byteEncrypt.aesDecrypt(key, iv, "AES/CBC/PKCS5Padding")
        assertEquals(plainText, String(byteDecrypt))
    }


    @Test
    fun aesECB() {
        val byteEncrypt =
            plainText.toByteArray().aesEncrypt(key, algorithm = "AES/ECB/PKCS5Padding")
        val byteDecrypt = byteEncrypt.aesDecrypt(key, algorithm = "AES/ECB/PKCS5Padding")
        assertEquals(plainText, String(byteDecrypt))
    }

    @Test
    fun aesCTR() {
        val byteEncrypt = plainText.toByteArray().aesEncrypt(key, iv, "AES/CTR/PKCS5Padding")
        val byteDecrypt = byteEncrypt.aesDecrypt(key, iv, "AES/CTR/PKCS5Padding")
        assertEquals(plainText, String(byteDecrypt))
    }

    @Test
    fun handleFile() {
        val sourceFilePath = "resources/ELF文件系统格式.pdf"
        val encFilePath = "resources/enc"
        val decFilePath = "resources/dec"

        File(sourceFilePath).aesEncrypt(key, iv, encFilePath)
        File(encFilePath).aesDecrypt(key, iv, decFilePath)

        assertEquals(
            File(sourceFilePath).hash(),
            File(decFilePath).hash()
        )
    }

}