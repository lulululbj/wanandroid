package luyao.wanandroid.model.bean


/**
 * Created by Lu
 * on 2018/4/5 08:02
 */
data class User(val collectIds: List<Int>,
                val email: String,
                val icon: String,
                val id: Int,
                val password: String,
                val type: Int,
                val username: String)