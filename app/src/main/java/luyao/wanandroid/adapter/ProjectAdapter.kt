package luyao.wanandroid.adapter

import android.text.Html
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import luyao.wanandroid.App
import luyao.wanandroid.R
import luyao.wanandroid.bean.Article

/**
 * Created by Lu
 * on 2018/4/1 18:31
 */
class ProjectAdapter(layoutResId: Int = R.layout.item_project) : BaseQuickAdapter<Article, BaseViewHolder>(layoutResId) {

    override fun convert(helper: BaseViewHolder, item: Article) {
        helper.setText(R.id.projectName, Html.fromHtml(item.title))
        helper.setText(R.id.projectDesc, item.desc)
        helper.setText(R.id.projectAuthor, item.author)
        helper.setText(R.id.projectTime, item.niceDate)
        Glide.with(App.CONTEXT).load(item.envelopePic).into(helper.getView(R.id.projectImg))
    }
}