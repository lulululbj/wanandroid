package luyao.gayhub.base.mvp

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by luyao
 * on 2018/1/9 13:51
 */
abstract class BasePresenter<V : IView> : IPresenter<V> {

    private var view: V? = null
    private val isViewAttached: Boolean get() = view != null
    private var compositeDisposable = CompositeDisposable()

    override fun onAttach(view: V?) {
        this.view = view
    }

    override fun onDetach() {
        compositeDisposable.dispose()
        view = null
    }

    fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun getView(): V? = view
}