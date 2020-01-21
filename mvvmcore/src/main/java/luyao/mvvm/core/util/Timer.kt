package luyao.mvvm.core.util

import android.util.Log

/**
 * Created by luyao
 * on 2020/1/21 14:05
 */
object Timer {

    private val tagMap = hashMapOf<String, Long>()

    fun start(tag: String) {
        tagMap[tag] = System.currentTimeMillis()
    }

    fun stop(tag: String) {
        if (!tagMap.containsKey(tag)) return
        val cost = System.currentTimeMillis() - (tagMap[tag] ?: 0)
        tagMap.remove(tag)
        Log.e("timer", "$tag cost : $cost")
    }
}