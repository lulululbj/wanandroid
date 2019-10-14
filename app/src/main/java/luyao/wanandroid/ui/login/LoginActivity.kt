package luyao.wanandroid.ui.login

import android.app.ProgressDialog
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.title_layout.*
import luyao.util.ktx.base.BaseVMActivity
import luyao.util.ktx.core.util.contentView
import luyao.util.ktx.ext.listener.textWatcher
import luyao.util.ktx.ext.startKtxActivity
import luyao.util.ktx.ext.toast
import luyao.wanandroid.R
import luyao.wanandroid.databinding.ActivityLoginBinding
import luyao.wanandroid.ui.main.NewMainActivity

/**
 * Created by Lu
 * on 2018/4/5 07:56
 */
class LoginActivity : BaseVMActivity<LoginViewModel>() {

    private val binding by contentView<LoginActivity, ActivityLoginBinding>(R.layout.activity_login)

    override fun providerVMClass(): Class<LoginViewModel>? = LoginViewModel::class.java

    override fun getLayoutResId() = R.layout.activity_login

    override fun initView() {
        mToolbar.setTitle(R.string.login)
        mToolbar.setNavigationIcon(R.drawable.arrow_back)

        binding.lifecycleOwner = this
        binding.viewModel = mViewModel
    }

    override fun initData() {
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        login.setOnClickListener(onClickListener)
        register.setOnClickListener(onClickListener)
        passwordEt.textWatcher { afterTextChanged { mViewModel.loginDataChanged(userNameEt.text.toString(), passwordEt.text.toString()) } }
        userNameEt.textWatcher { afterTextChanged { mViewModel.loginDataChanged(userNameEt.text.toString(), passwordEt.text.toString()) } }
    }

    override fun startObserve() {
        mViewModel.apply {

            mRegisterUser.observe(this@LoginActivity, Observer {
                it?.run {
                    mViewModel.login(username, password)
                }
            })

            uiState.observe(this@LoginActivity, Observer {
                if (it.showProgress) showProgressDialog()

                it.showSuccess?.let {
                    dismissProgressDialog()
                    startKtxActivity<NewMainActivity>()
                    finish()
                }

                it.showError?.let { err ->
                    dismissProgressDialog()
                    toast(err)
                }
            })
        }
    }

    private val onClickListener = View.OnClickListener {
        when (it.id) {
            R.id.login -> login()
            R.id.register -> register()
        }
    }

    private fun login() {
        mViewModel.login(userNameEt.text.toString(), passwordEt.text.toString())
    }

    private fun register() {
        mViewModel.register(userNameEt.text.toString(), passwordEt.text.toString())
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