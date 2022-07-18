package luyao.wanandroid.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import luyao.wanandroid.model.api.WanRetrofitClient
import luyao.wanandroid.model.api.WanService
import javax.inject.Singleton

/**
 * Description:
 * Author: luyao
 * Date: 2022/7/18 15:38
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideWanService(): WanService =
        WanRetrofitClient.getService(WanService::class.java, WanService.BASE_URL)
}