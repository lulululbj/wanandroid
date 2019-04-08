package luyao.wanandroid.ui.login

import android.arch.lifecycle.Observer
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.title_layout.*
import luyao.wanandroid.R
import luyao.wanandroid.ui.MainActivity
import luyao.wanandroid.util.Preference
import toast

/**
 * Created by Lu
 * on 2018/4/5 07:56
 */
class LoginActivity : luyao.base.BaseActivity<LoginViewModel>() {

    override fun providerVMClass(): Class<LoginViewModel>? = LoginViewModel::class.java

    private lateinit var userName: String
    private lateinit var passWord: String
    private var isLogin by Preference(Preference.IS_LOGIN, false)

    override fun getLayoutResId() = R.layout.activity_login

    override fun initView() {
        mToolbar.setTitle(R.string.login)
        mToolbar.setNavigationIcon(R.drawable.arrow_back)
    }

    override fun initData() {
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        login.setOnClickListener(onClickListener)
        register.setOnClickListener(onClickListener)
    }

    override fun startObserve() {
        mViewModel.apply {
            mLoginUser.observe(this@LoginActivity, Observer {
                isLogin = true
                dismissProgressDialog()
                startActivity(MainActivity::class.java)
                finish()
            })

            mRegisterUser.observe(this@LoginActivity, Observer {
                it?.run {
                    mViewModel.login(username, password)
                }
            })

            errMsg.observe(this@LoginActivity, Observer {
                dismissProgressDialog()
                it?.run { toast(it) }
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
        if (checkInput()) {
            showProgressDialog(getString(R.string.is_logining))
            mViewModel.login(userName, passWord)
        }
    }

    private fun register() {
        if (checkInput()) {
            showProgressDialog(getString(R.string.is_registering))
            mViewModel.register(userName, passWord)
        }
    }

    private fun checkInput(): Boolean {
        userName = userNameLayout.editText?.text.toString()
        passWord = pswLayout.editText?.text.toString()
        if (userName.isEmpty()) {
            userNameLayout.error = getString(R.string.please_input_username)
            return false
        }
        if (passWord.isEmpty()) {
            pswLayout.error = getString(R.string.please_input_psw)
            return false
        }
        return true
    }

}