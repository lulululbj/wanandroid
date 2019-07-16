package luyao.wanandroid.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import luyao.wanandroid.R
import luyao.wanandroid.model.bean.SystemParent

/**
 * Created by Lu
 * on 2018/3/26 21:46
 */
class SystemAdapter(layoutResId: Int = R.layout.item_system) : BaseQuickAdapter<SystemParent, BaseViewHolder>(layoutResId) {

    override fun convert(helper: BaseViewHolder, item: SystemParent) {
        helper.run {
            setText(R.id.systemParent, item.name)
            setText(R.id.systemChild, item.children.joinToString("     ", transform = { child -> child.name }))
        }
    }
}