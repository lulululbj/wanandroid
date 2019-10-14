package luyao.util.ktx.core.lifecycle

import android.app.Activity
import java.util.*
import java.lang.Class

/**
 * Created by luyao
 * on 2019/8/6 10:58
 */
object KtxManager {

    private val mActivityList = LinkedList<Activity>()

    val currentActivity: Activity?
        get() =
            if (mActivityList.isEmpty()) null
            else mActivityList.last


    /**
     * push the specified [activity] into the list
     */
    fun pushActivity(activity: Activity) {
        if (mActivityList.contains(activity)) {
            if (mActivityList.last != activity) {
                mActivityList.remove(activity)
                mActivityList.add(activity)
            }
        } else {
            mActivityList.add(activity)
        }
    }

    /**
     * pop the specified [activity] into the list
     */
    fun popActivity(activity: Activity) {
        mActivityList.remove(activity)
    }

    fun finishCurrentActivity() {
        currentActivity?.finish()
    }

    fun finishActivity(activity: Activity) {
        mActivityList.remove(activity)
        activity.finish()
    }

    fun  finishActivity(clazz: Class<*>){
        for (activity in mActivityList)
            if (activity.javaClass == clazz)
                activity.finish()
    }

    fun finishAllActivity() {
        for (activity in mActivityList)
            activity.finish()
    }
}