package luyao.gayhub.base

import luyao.gayhub.base.mvp.IPresenter
import luyao.gayhub.base.mvp.IView
import toast

/**
 * Created by Lu
 * on 2018/3/7 20:04
 */
abstract class BaseMvpFragment<V : IView, P : IPresenter<V>> : BaseFragment(), IView {

    protected abstract var mPresenter: P

    override fun attachPresenter() {
        super.attachPresenter()
        mPresenter.onAttach(this as V)

    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }

    override fun showError(message: String?) {

    }

}