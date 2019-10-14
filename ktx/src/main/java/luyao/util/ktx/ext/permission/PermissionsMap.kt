package luyao.util.ktx.ext.permission

import java.util.concurrent.atomic.AtomicInteger

/**
 * Used for generating request code and hold permission callbacks on a map.
 */
internal object PermissionsMap {

    private val atomicInteger = AtomicInteger(100)

    private val map = mutableMapOf<Int, PermissionsCallback>()

    fun put(callbacks: PermissionsCallback): Int {
        return atomicInteger.getAndIncrement().also {
            map[it] = callbacks
        }
    }

    fun get(requestCode: Int): PermissionsCallback? {
        return map[requestCode].also {
            map.remove(requestCode)
        }
    }

}