package luyao.wanandroid.ui.login

import android.app.ProgressDialog
import androidx.lifecycle.Observer
import luyao.mvvm.core.base.BaseVMActivity
import luyao.util.ktx.ext.toast
import luyao.wanandroid.R
import luyao.wanandroid.databinding.ActivityLoginBinding
import luyao.wanandroid.model.bean.Title
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Lu
 * on 2018/4/5 07:56
 */
class LoginActivity : BaseVMActivity() {

    private val loginViewModel by viewModel<LoginViewModel>()
    private val binding by binding<ActivityLoginBinding>(R.layout.activity_login)

    override fun initView() {
        binding.run {
            viewModel = loginViewModel
            title =  Title(R.string.login, R.drawable.arrow_back) { onBackPressed() }
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