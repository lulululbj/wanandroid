package luyao.wanandroid.ui.share

import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_share.*
import luyao.util.ktx.base.BaseVMActivity
import luyao.util.ktx.core.util.contentView
import luyao.util.ktx.ext.listener.textWatcher
import luyao.util.ktx.ext.toast
import luyao.wanandroid.R
import luyao.wanandroid.databinding.ActivityShareBinding

/**
 * Created by luyao
 * on 2019/10/15 15:21
 */
class ShareActivity : BaseVMActivity<ShareViewModel>() {

    private val binding by contentView<ShareActivity, ActivityShareBinding>(R.layout.activity_share)

    override fun providerVMClass() = ShareViewModel::class.java

    override fun getLayoutResId() = R.layout.activity_share

    override fun initView() {
        binding.lifecycleOwner = this
        binding.viewModel = mViewModel
    }

    override fun initData() {
        inputTitle.textWatcher { afterTextChanged { mViewModel.shareDataChanged(inputTitle.text.toString(), inputUrl.text.toString()) } }
        inputUrl.textWatcher { afterTextChanged { mViewModel.shareDataChanged(inputTitle.text.toString(), inputUrl.text.toString()) } }
        shareBt.setOnClickListener { mViewModel.shareArticle(inputTitle.text.toString(), inputUrl.text.toString()) }
    }


    override fun startObserve() {
        super.startObserve()

        mViewModel.uiState.observe(this, Observer {

            it.showSuccess?.let { finish() }

            it.showError?.let { err -> toast(err) }
        })
    }
}