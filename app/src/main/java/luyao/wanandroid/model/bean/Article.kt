package luyao.wanandroid.model.bean

import java.io.Serializable

/**
 * Created by luyao
 * on 2018/3/13 14:51
 */
data class Article( val id: Int,
                    val originId: Int,
                    val title: String,
                    val chapterId: Int,
                    val chapterName: String,
                    val envelopePic: String,
                    val link: String,
                    val author: String,
                    val origin: String,
                    val publishTime: Long,
                    val zan: Int,
                    val desc: String,
                    val visible: Int,
                    val niceDate: String,
                    val courseId: Int,
                    var collect: Boolean,
                    val apkLink:String,
                    val projectLink:String,
                    val superChapterId:Int,
                    val superChapterName:String?,
                    val type:Int,
                    val fresh:Boolean):Serializable