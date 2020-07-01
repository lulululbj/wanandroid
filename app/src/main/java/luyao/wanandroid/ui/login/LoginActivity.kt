package luyao.wanandroid.ui.login

import android.app.ProgressDialog
import androidx.activity.viewModels
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import luyao.mvvm.core.base.BaseVMActivity
import luyao.util.ktx.ext.toast
import luyao.wanandroid.R
import luyao.wanandroid.model.bean.Title
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Lu
 * on 2018/4/5 07:56
 */
class LoginActivity : BaseVMActivity<LoginViewModel>() {

    private val loginViewModel by viewModels<LoginViewModel>()

    override fun getLayoutResId() = R.layout.activity_login

    override fun initVM(): LoginViewModel = getViewModel()

    override fun initView() {
        mBinding.run {
            setVariable(BR.viewModel, loginViewModel)
            setVariable(BR.title, Title(R.string.login, R.drawable.arrow_back) { onBackPressed() })
        }
    }

    override fun initData() {
    }

    override fun startObserve() {
        loginViewModel.apply {

            uiState.observe(this@LoginActivity, Observer {
                if (it.isLoading) showProgressDialog()

                it.isSuccess?.let {
                    dismissProgressDialog()
                    finish()
                }

                it.isError?.let { err ->
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