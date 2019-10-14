package luyao.util.ktx.ext

import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

/**
 * Created by luyao
 * on 2019/7/1 14:41
 */
class HashExtKtTest {

    private val origin = "hello"

    @Test
    fun md5() {
        val md5 = origin.hash(Hash.MD5)
        assertEquals("5D41402ABC4B2A76B9719D911017C592".toLowerCase(),md5)
    }

    @Test
    fun sha1() {
        val sha1 = origin.hash(Hash.SHA1)
        assertEquals("AAF4C61DDCC5E8A2DABEDE0F3B482CD9AEA9434D".toLowerCase(),sha1)
    }

    @Test
    fun sha224() {
//        val sha224 = origin.hash(Hash.SHA224)
//        assertEquals("ea09ae9cc6768c50fcee903ed054556e5bfc8347907f12598aa24193",sha224)
    }

    @Test
    fun sha256() {
//        val sha256 = origin.hash(Hash.SHA256)
//        assertEquals("2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824",sha256)
    }

    @Test
    fun sha384() {
//        val sha384 = origin.hash(Hash.SHA384)
//        assertEquals("2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824",sha384)
    }

    @Test
    fun sha512() {
//        val sha512 = origin.hash(Hash.SHA512)
//        assertEquals("9b71d224bd62f3785d96d46ad3ea3d73319bfbc2890caadae2dff72519673ca72323c3d99ba5c11d7c7acc6e14b8c5da0c4663475c2e5c3adef46f73bcdec043",sha512)
    }

    @Test
    fun getFileHash(){
        val file = File("resources/ELF文件系统格式.pdf")
        assertEquals("2111c8982f2524d83832c861034c29e4",file.hash(Hash.MD5))
        assertEquals("0d3e7f3104648225ab68f6c1738b2bb7fb0e83d3",file.hash(Hash.SHA1))
    }
}