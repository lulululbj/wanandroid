package luyao.wanandroid

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import luyao.ktx.app.AppInit
import luyao.wanandroid.model.MMKVConstants
import javax.inject.Inject

@HiltAndroidApp
class WanApplication : Application() {

    companion object {
        lateinit var App: Application
    }

    override fun onCreate() {
        super.onCreate()
        App = this
        AppInit.init(this)
        AppCompatDelegate.setDefaultNightMode(MMKVConstants.nightMode)
    }
}