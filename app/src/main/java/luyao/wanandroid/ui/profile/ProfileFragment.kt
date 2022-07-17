package luyao.wanandroid.ui.profile

import kotlinx.android.synthetic.main.fragmnet_profile.*
import luyao.mvvm.core.base.BaseFragment
import luyao.util.ktx.ext.startKtxActivity
import luyao.wanandroid.R
import luyao.wanandroid.ui.ComposeMainActivity

/**
 * Created by luyao
 * on 2019/12/12 14:12
 */
class ProfileFragment : BaseFragment() {


    override fun getLayoutResId() = R.layout.fragmnet_profile

    override fun initView() {

        jumpCompose.setOnClickListener {
            startKtxActivity<ComposeMainActivity>()
        }
    }

    override fun initData() {
    }


}

