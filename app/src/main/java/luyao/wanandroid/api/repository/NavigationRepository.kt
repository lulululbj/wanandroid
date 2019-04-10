package luyao.wanandroid.api.repository

import luyao.base.BaseRepository
import luyao.base.WanResponse
import luyao.wanandroid.api.WanRetrofitClient
import luyao.wanandroid.bean.Navigation

/**
 * Created by luyao
 * on 2019/4/10 14:15
 */
class NavigationRepository : BaseRepository() {


    suspend fun getNavigation(): WanResponse<List<Navigation>> {
        return apiCall { WanRetrofitClient.service.getNavigation().await() }
    }
}