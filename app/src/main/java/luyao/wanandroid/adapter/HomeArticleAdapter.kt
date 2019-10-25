package luyao.wanandroid.adapter

import luyao.wanandroid.BR
import luyao.wanandroid.R
import luyao.wanandroid.model.bean.Article

/**
 * Created by luyao
 * on 2018/3/14 15:52
 */
@Suppress("DEPRECATION")
class HomeArticleAdapter(layoutResId: Int = R.layout.item_article) : BaseBindAdapter<Article>(layoutResId, BR.article) {

    private var showStar = true

    fun showStar(showStar: Boolean) {
        this.showStar = showStar
    }

    override fun convert(helper: BindViewHolder, item: Article) {
        super.convert(helper, item)
//        helper.setText(R.id.articleTitle, if (fromN()) Html.fromHtml(item.title, Html.FROM_HTML_MODE_LEGACY) else Html.fromHtml(item.title))
//                .setText(R.id.articleAuthor, item.author)
//                .setText(R.id.articleTag, "${item.superChapterName ?: ""} ${item.chapterName}")
//                .setText(R.id.articleTime, item.niceDate)
//                .addOnClickListener(R.id.articleStar)
        helper.addOnClickListener(R.id.articleStar)
        if (showStar) helper.setImageResource(R.id.articleStar, if (item.collect) R.drawable.timeline_like_pressed else R.drawable.timeline_like_normal)
        else helper.setVisible(R.id.articleStar, false)
    }
}