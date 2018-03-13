package luyao.wanandroid

import android.app.Application
import com.squareup.leakcanary.LeakCanary

/**
 * Created by luyao
 * on 2018/3/13 13:35
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this))return
        LeakCanary.install(this)
    }
}