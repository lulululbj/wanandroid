package luyao.gayhub.base

import luyao.gayhub.base.mvp.IPresenter
import luyao.gayhub.base.mvp.IView

/**
 * Created by luyao
 * on 2018/1/9 14:01
 */
abstract class BaseMvpActivity<V : IView, P : IPresenter<V>> : BaseActivity() {

    protected abstract var mPresenter: P


    override fun attachPresenter() {
        mPresenter.onAttach(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }

}