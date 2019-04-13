package luyao.wanandroid.model.bean

/**
 * Created by Lu
 * on 2018/3/17 17:18
 */

//　"desc":"一起来做个App吧",
//　　　　　　"id":10,
//　　　　　　"imagePath":"http://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png",
//　　　　　　"isVisible":1,
//　　　　　　"order":0,
//　　　　　　"title":"一起来做个App吧",
//　　　　　　"type":0,
//　　　　　　"url":"http://www.wanandroid.com/blog/show/2"
data class Banner(val desc: String,
                  val id: Int,
                  val imagePath: String,
                  val isVisible: Int,
                  val order: Int,
                  val title: String,
                  val type: Int,
                  val url: String)