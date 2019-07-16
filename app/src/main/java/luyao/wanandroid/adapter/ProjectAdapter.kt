package luyao.wanandroid.adapter

import android.text.Html
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import fromN
import luyao.wanandroid.App
import luyao.wanandroid.R
import luyao.wanandroid.model.bean.Article

/**
 * Created by Lu
 * on 2018/4/1 18:31
 */
@Suppress("DEPRECATION")
class ProjectAdapter(layoutResId: Int = R.layout.item_project) : BaseQuickAdapter<Article, BaseViewHolder>(layoutResId) {

    override fun convert(helper: BaseViewHolder, item: Article) {
        helper.setText(R.id.projectName,if (fromN()) Html.fromHtml(item.title, Html.FROM_HTML_MODE_LEGACY) else Html.fromHtml(item.title))
                .setText(R.id.projectDesc, item.desc)
                .setText(R.id.projectAuthor, item.author)
                .setText(R.id.projectTime, item.niceDate)
                .setImageResource(R.id.articleStar, if (item.collect) R.drawable.timeline_like_pressed else R.drawable.timeline_like_normal)
                .addOnClickListener(R.id.articleStar)
        Glide.with(App.CONTEXT).load(item.envelopePic).into(helper.getView(R.id.projectImg))
    }
}