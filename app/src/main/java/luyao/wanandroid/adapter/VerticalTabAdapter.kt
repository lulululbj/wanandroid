package luyao.wanandroid.adapter

import q.rorbin.verticaltablayout.adapter.TabAdapter
import q.rorbin.verticaltablayout.widget.ITabView

/**
 * Created by Lu
 * on 2018/3/28 22:24
 */
class VerticalTabAdapter(private val titles: List<String>) : TabAdapter {

    override fun getIcon(position: Int) = null

    override fun getBadge(position: Int) = null

    override fun getBackground(position: Int) = -1

    override fun getTitle(position: Int): ITabView.TabTitle {
        return ITabView.TabTitle.Builder()
                .setContent(titles[position])
                .setTextColor(-0xc94365, -0x8a8a8b)
                .build()
    }

    override fun getCount() = titles.size
}