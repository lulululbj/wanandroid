package luyao.gayhub.base

import android.os.Bundle
import android.view.View
import luyao.gayhub.base.mvp.IPresenter
import luyao.gayhub.base.mvp.IView

/**
 * Created by Lu
 * on 2018/3/7 20:04
 */
abstract class BaseMvpFragment<V : IView, P : IPresenter<V>> : BaseFragment(), IView {

    protected abstract var mPresenter: P

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mPresenter.onAttach(this as V)
        initView()
        initData()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }
}