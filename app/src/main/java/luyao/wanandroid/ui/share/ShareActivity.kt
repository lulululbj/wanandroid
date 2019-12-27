package luyao.wanandroid.ui.share

import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_share.*
import luyao.mvvm.core.base.BaseVMActivity
import luyao.util.ktx.ext.listener.textWatcher
import luyao.util.ktx.ext.toast
import luyao.wanandroid.R
import luyao.wanandroid.databinding.ActivityShareBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 * Created by luyao
 * on 2019/10/15 15:21
 */
class ShareActivity : BaseVMActivity<ShareViewModel>() {


    override fun initVM(): ShareViewModel = getViewModel()

    override fun getLayoutResId() = R.layout.activity_share

    override fun initView() {
        mBinding.lifecycleOwner = this
        (mBinding as ActivityShareBinding).viewModel = mViewModel
    }

    override fun initData() {
    }


    override fun startObserve() {
        mViewModel.uiState.observe(this, Observer {

            it.showSuccess?.let {
                Navigation.findNavController(shareBt).navigateUp()
            }

            it.showError?.let { err -> toast(err) }
        })
    }
}