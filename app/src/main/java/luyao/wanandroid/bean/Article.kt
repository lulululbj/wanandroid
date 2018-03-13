package luyao.wanandroid.bean

/**
 * Created by luyao
 * on 2018/3/13 14:51
 */
data class Article( val id: Int,
                    val originId: Int,
                    val title: String,
                    val chapterId: Int,
                    val chapterName: String?,
                    val envelopePic: Any,
                    val link: String,
                    val author: String,
                    val origin: Any,
                    val publishTime: Long,
                    val zan: Any,
                    val desc: Any,
                    val visible: Int,
                    val niceDate: String,
                    val courseId: Int,
                    val collect: Boolean)