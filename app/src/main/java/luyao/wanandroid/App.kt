package luyao.wanandroid

import android.app.Application
import android.content.Context
import android.util.Log
import com.tencent.smtt.sdk.QbSdk
import luyao.wanandroid.model.bean.User
import kotlin.properties.Delegates

/**
 * Created by luyao
 * on 2018/3/13 13:35
 */
class App : Application() {

    companion object {
        var CONTEXT: Context by Delegates.notNull()
        lateinit var CURRENT_USER: User
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext

        //x5内核初始化接口
        QbSdk.initX5Environment(applicationContext, object : QbSdk.PreInitCallback {

            override fun onViewInitFinished(arg0: Boolean) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is $arg0")
            }

            override fun onCoreInitFinished() {
            }
        })
    }
}