package luyao.wanandroid.model.repository

import luyao.base.BaseRepository
import luyao.base.WanResponse
import luyao.wanandroid.model.api.WanRetrofitClient
import luyao.wanandroid.model.bean.Navigation

/**
 * Created by luyao
 * on 2019/4/10 14:15
 */
class NavigationRepository : BaseRepository() {


    suspend fun getNavigation(): WanResponse<List<Navigation>> {
        return apiCall { WanRetrofitClient.service.getNavigation().await() }
    }
}