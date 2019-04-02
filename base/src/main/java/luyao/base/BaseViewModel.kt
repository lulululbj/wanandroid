package luyao.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.Job

/**
 * Created by luyao
 * on 2019/1/29 9:58
 */
open class BaseViewModel : ViewModel(),LifecycleObserver{

    private val launchManager:MutableList<Job> = mutableListOf()



    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        launchManager.clear()
    }
}