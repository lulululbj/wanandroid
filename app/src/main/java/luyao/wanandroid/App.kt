package luyao.wanandroid

import android.app.Application
import android.content.Context
import com.squareup.leakcanary.LeakCanary
import kotlin.properties.Delegates

/**
 * Created by luyao
 * on 2018/3/13 13:35
 */
class App : Application() {

    companion object {
        var CONTEXT: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
        if (LeakCanary.isInAnalyzerProcess(this)) return
        LeakCanary.install(this)
    }
}