package luyao.wanandroid

import android.app.Application
import android.content.Context
import com.tencent.mmkv.MMKV
import dagger.hilt.android.HiltAndroidApp
import luyao.wanandroid.model.bean.User
import kotlin.properties.Delegates

/**
 * Created by luyao
 * on 2018/3/13 13:35
 */
@HiltAndroidApp
class App : Application() {

    companion object {
        var CONTEXT: Context by Delegates.notNull()
        lateinit var CURRENT_USER: User
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
        MMKV.initialize(this)
//        startKoin {
//            androidContext(this@App)
//            modules(appModule)
//        }
    }
}