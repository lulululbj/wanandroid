package luyao.base

/**
 * Created by luyao
 * on 2019/4/10 9:41
 */
open class BaseRepository {

    suspend fun <T : Any> apiCall(call: suspend () -> WanResponse<T>): WanResponse<T> {
        return call.invoke()
    }


}