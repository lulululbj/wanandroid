package luyao.wanandroid.ui.login

import android.app.ProgressDialog
import androidx.lifecycle.Observer
import luyao.mvvm.core.base.BaseVMFragment
import luyao.util.ktx.ext.toast
import luyao.wanandroid.R
import luyao.wanandroid.databinding.FragmentLoginBinding
import luyao.wanandroid.model.bean.Title
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Lu
 * on 2018/4/5 07:56
 */
class LoginActivity : BaseVMFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val loginViewModel by viewModel<LoginViewModel>()

    override fun initView() {
        binding.run {
            viewModel = loginViewModel
            title = Title(R.string.login, R.drawable.arrow_back) { activity?.onBackPressed() }
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
                }

                it.isError?.let { err ->
                    dismissProgressDialog()
                    activity?.toast(err)
                }

                if (it.needLogin) loginViewModel.login()
            })
        }
    }

    private var progressDialog: ProgressDialog? = null
    private fun showProgressDialog() {
        if (progressDialog == null)
            progressDialog = ProgressDialog(activity)
        progressDialog?.show()
    }

    private fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }

}