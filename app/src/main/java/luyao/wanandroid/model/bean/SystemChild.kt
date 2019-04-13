package luyao.wanandroid.model.bean

import java.io.Serializable

/**
 * Created by Lu
 * on 2018/3/26 21:23
 */
data class SystemChild(val child: List<SystemChild>,
                       val courseId: Int,
                       val id: Int,
                       val name: String,
                       val order: Int,
                       val parentChapterId: Int,
                       val visible: Int):Serializable