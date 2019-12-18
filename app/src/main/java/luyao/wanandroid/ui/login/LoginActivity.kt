package luyao.wanandroid.ui.login

import android.app.ProgressDialog
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.title_layout.*
import luyao.util.ktx.ext.toast
import luyao.wanandroid.R
import luyao.wanandroid.databinding.ActivityLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Lu
 * on 2018/4/5 07:56
 */
class LoginActivity : luayo.mvvm.core.base.BaseVMActivity<LoginViewModel, ActivityLoginBinding>() {

    val mViewModel: LoginViewModel by viewModel()

    override fun getLayoutResId() = R.layout.activity_login

    override fun initView() {
        mBinding.lifecycleOwner = this
        mBinding.viewModel = mViewModel
        mToolbar.setTitle(R.string.login)
        mToolbar.setNavigationIcon(R.drawable.arrow_back)
    }

    override fun initData() {
        mToolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun startObserve() {
        mViewModel.apply {

            mRegisterUser.observe(this@LoginActivity, Observer {
                it?.run {
                    mViewModel.login()
                }
            })

            uiState.observe(this@LoginActivity, Observer {
                if (it.showProgress) showProgressDialog()

                it.showSuccess?.let {
                    dismissProgressDialog()
//                    startKtxActivity<MainFragment>()
                    finish()
                }

                it.showError?.let { err ->
                    dismissProgressDialog()
                    toast(err)
                }
            })
        }
    }

    private var progressDialog: ProgressDialog? = null
    private fun showProgressDialog() {
        if (progressDialog == null)
            progressDialog = ProgressDialog(this)
        progressDialog?.show()
    }

    private fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }

}