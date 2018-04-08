package luyao.gayhub.base

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import kotlinx.android.synthetic.main.title_layout.*
import luyao.gayhub.base.mvp.IView

/**
 * Created by luyao
 * on 2018/1/9 14:01
 */
abstract class BaseActivity : AppCompatActivity(), IView {

    private var mDialog: MaterialDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        attachPresenter()
        initView()
        setSupportActionBar(mToolbar)
        initData()
    }

    open fun attachPresenter(){}

    abstract fun getLayoutResId(): Int
    abstract fun initView()
    abstract fun initData()

    protected fun startActivity(z: Class<*>) {
        startActivity(Intent(this, z))
    }

    protected fun showProgressDialog(content: String) {
        if (mDialog == null)
            mDialog = MaterialDialog.Builder(this)
                    .content(content).progress(true, 1)
                    .canceledOnTouchOutside(false).build()
        else
            mDialog?.setContent(content)
        mDialog?.show()
    }

    protected fun showProgressDialog(resId: Int) {
        if (mDialog == null)
            mDialog = MaterialDialog.Builder(this)
                    .content(getString(resId)).progress(true, 1)
                    .canceledOnTouchOutside(false).build()
        else
            mDialog?.setContent(getString(resId))
        mDialog?.show()
    }

    protected fun dismissProgressDialog() {
        mDialog?.let { it.dismiss() }
    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    override fun showError(message: String?) {

    }

}