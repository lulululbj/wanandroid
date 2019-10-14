package luyao.util.ktx.ext

import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.charset.Charset
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Created by luyao
 * on 2019/6/14 15:28
 */
private fun hash(data: ByteArray, algorithm: Hash): ByteArray {
    val messageDigest = MessageDigest.getInstance(algorithm.name)
    return messageDigest.digest(data)
}

fun ByteArray.hash(algorithm: Hash): String {
    return hash(this, algorithm).toHexString()
}

fun String.hash(algorithm: Hash, charset: Charset = Charset.forName("utf-8")): String {
    return toByteArray(charset).hash(algorithm)
}

fun ByteArray.md5Bytes(): ByteArray = hash(this, Hash.MD5)
fun ByteArray.md5(): String = hash(this, Hash.MD5).toHexString()
fun String.md5(charset: Charset = Charset.forName("utf-8")): String = toByteArray(charset).md5()
fun ByteArray.sha1Bytes(): ByteArray = hash(this, Hash.SHA1)
fun ByteArray.sha1(): String = hash(this, Hash.SHA1).toHexString()
fun String.sha1(charset: Charset = Charset.forName("utf-8")): String = toByteArray(charset).sha1()
fun ByteArray.sha224Bytes(): ByteArray = hash(this, Hash.SHA224)
fun ByteArray.sha224(): String = hash(this, Hash.SHA224).toHexString()
fun String.sha224(charset: Charset = Charset.forName("utf-8")): String = toByteArray(charset).sha224()
fun ByteArray.sha256Bytes(): ByteArray = hash(this, Hash.SHA256)
fun ByteArray.sha256(): String = hash(this, Hash.SHA256).toHexString()
fun String.sha256(charset: Charset = Charset.forName("utf-8")): String = toByteArray(charset).sha256()
fun ByteArray.sha384Bytes(): ByteArray = hash(this, Hash.SHA384)
fun ByteArray.sha384(): String = hash(this, Hash.SHA384).toHexString()
fun String.sha384(charset: Charset = Charset.forName("utf-8")): String = toByteArray(charset).sha384()
fun ByteArray.sha512Bytes(): ByteArray = hash(this, Hash.SHA512)
fun ByteArray.sha512(): String = hash(this, Hash.SHA512).toHexString()
fun String.sha512(charset: Charset = Charset.forName("utf-8")): String = toByteArray(charset).sha512()

fun File.hash(algorithm: Hash = Hash.SHA1): String {
    if (!exists() || !isFile) return ""
    val fin: FileInputStream
    val messageDigest: MessageDigest
    val buffer = ByteArray(1024)
    var len: Int
    try {
        messageDigest = MessageDigest.getInstance(algorithm.name)
        fin = FileInputStream(this)
        do {
            len = fin.read(buffer, 0, 1024)
            if (len != -1) messageDigest.update(buffer, 0, len)
        } while (len != -1)
        fin.close()
        val result = messageDigest.digest()
        return result.toHexString()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return ""
}

enum class Hash {
    MD5,
    SHA1,
    SHA224,
    SHA256,
    SHA384,
    SHA512,
}


