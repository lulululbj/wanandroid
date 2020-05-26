package luyao.wanandroid.ui.login

import android.app.ProgressDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.title_layout.*
import luyao.mvvm.core.base.BaseVMFragment
import luyao.util.ktx.ext.toast
import luyao.wanandroid.R
import luyao.wanandroid.databinding.ActivityLoginBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 * Created by luyao
 * on 2020/5/25 16:36
 */
class LoginFragment : BaseVMFragment<LoginViewModel>() {

    override fun getLayoutResId() = R.layout.activity_login

    override fun initVM(): LoginViewModel = getViewModel()

    override fun initView() {
        (mBinding as ActivityLoginBinding).viewModel = mViewModel
        mToolbar.setTitle(R.string.login)
        mToolbar.setNavigationIcon(R.drawable.arrow_back)
    }

    override fun initData() {
        mToolbar.setNavigationOnClickListener { findNavController().navigateUp() }
    }

    override fun startObserve() {
        mViewModel.apply {

            uiState.observe(viewLifecycleOwner, Observer {
                if (it.isLoading) showProgressDialog()

                it.isSuccess?.let {
                    dismissProgressDialog()
                    findNavController().navigateUp()
                }

                it.isError?.let { err ->
                    dismissProgressDialog()
                    activity?.toast(err)
                }
            })
        }
    }

    private var progressDialog: ProgressDialog? = null
    private fun showProgressDialog() {
        if (progressDialog == null)
            progressDialog = ProgressDialog(context)
        progressDialog?.show()
    }

    private fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }
}