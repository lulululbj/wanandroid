package luyao.wanandroid.ui.login

import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.title_layout.*
import luyao.gayhub.base.BaseMvpActivity
import luyao.wanandroid.R
import luyao.wanandroid.bean.User
import toast

/**
 * Created by Lu
 * on 2018/4/5 07:56
 */
class LoginActivity : BaseMvpActivity<LoginContract.View, LoginPresenter>(), LoginContract.View {


    private lateinit var userName: String
    private lateinit var passWord: String
    override var mPresenter = LoginPresenter()

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

    override fun login(user: User) {
        dismissProgressDialog()
    }

    override fun register(user: User) {
        dismissProgressDialog()
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
            mPresenter.login(userName, passWord)
        }
    }

    private fun register() {
        if (checkInput()) {
            showProgressDialog(getString(R.string.is_registering))
            mPresenter.register(userName, passWord)
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

    override fun showError(message: String?) {
        dismissProgressDialog()
        message?.let { toast(it) }
    }
}