package luyao.wanandroid.model.repository

import luyao.wanandroid.model.api.BaseRepository
import luyao.wanandroid.model.api.WanRetrofitClient
import luyao.wanandroid.model.bean.Navigation
import luyao.wanandroid.model.bean.WanResponse

/**
 * Created by luyao
 * on 2019/4/10 14:15
 */
class NavigationRepository : BaseRepository() {


    suspend fun getNavigation(): WanResponse<List<Navigation>> {
        return apiCall { WanRetrofitClient.service.getNavigation() }
    }
}