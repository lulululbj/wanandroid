package luyao.wanandroid.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import luyao.wanandroid.model.api.WanRetrofitClient
import luyao.wanandroid.model.api.WanService
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object HiltModule {

    @Provides
    @Singleton
    fun provideService(): WanService {
        return WanRetrofitClient.getService(WanService::class.java, WanService.BASE_URL)
    }
}