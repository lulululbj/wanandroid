package luyao.wanandroid.adapter

import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import luyao.wanandroid.R
import luyao.wanandroid.bean.Article

/**
 * Created by luyao
 * on 2018/3/14 15:52
 */
class HomeArticleAdapter(layoutResId: Int = R.layout.item_article) : BaseQuickAdapter<Article, BaseViewHolder>(layoutResId) {

    override fun convert(helper: BaseViewHolder, item: Article) {
        helper.setText(R.id.articleTitle, Html.fromHtml(item.title))
                .setText(R.id.articleAuthor, item.author)
                .setText(R.id.articleTag, "${item.superChapterName ?: ""} ${item.chapterName}")
                .setText(R.id.articleTime, item.niceDate)
                .setImageResource(R.id.articleStar, if (item.collect) R.drawable.timeline_like_pressed else R.drawable.timeline_like_normal)
                .addOnClickListener(R.id.articleStar)
    }
}