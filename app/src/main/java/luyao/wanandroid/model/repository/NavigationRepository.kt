package luyao.wanandroid.model.repository

import luyao.wanandroid.model.bean.Result
import luyao.wanandroid.model.api.BaseRepository
import luyao.wanandroid.model.api.WanRetrofitClient
import luyao.wanandroid.model.bean.Navigation
import javax.inject.Inject

/**
 * Created by luyao
 * on 2019/4/10 14:15
 */
class NavigationRepository @Inject constructor(): BaseRepository() {


    suspend fun getNavigation(): Result<List<Navigation>> {
        return safeApiCall(call = { requestNavigation() }, errorMessage = "获取数据失败")
    }


    private suspend fun requestNavigation(): Result<List<Navigation>> =
            executeResponse(WanRetrofitClient.service.getNavigation())
}