package luyao.wanandroid.ui.login

import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.title_layout.*
import luyao.wanandroid.R
import luyao.wanandroid.base.BaseActivity
import luyao.wanandroid.bean.User
import luyao.wanandroid.ui.MainActivity
import luyao.wanandroid.util.Preference
import toast

/**
 * Created by Lu
 * on 2018/4/5 07:56
 */
class LoginActivity : BaseActivity(), LoginContract.View {

    private lateinit var userName: String
    private lateinit var passWord: String
    private var isLogin by Preference(Preference.IS_LOGIN, false)
    override var mPresenter: LoginContract.Presenter = LoginPresenter(this)

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
        isLogin = true
        dismissProgressDialog()
        startActivity(MainActivity::class.java)
        finish()
    }

    override fun register(user: User) {
        mPresenter.login(user.username, user.password)
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

    override fun registerError(message: String) {
        dismissProgressDialog()
        message.let { toast(it) }
    }

    override fun loginError(message: String) {
        dismissProgressDialog()
        message.let { toast(it) }
    }


}