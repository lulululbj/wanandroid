package luyao.gayhub.base.mvp

/**
 * Created by Lu
 * on 2018/3/7 19:52
 */
interface IPresenter<V : IView> {

    fun onAttach(view: V?)

    fun onDetach()

    fun getView(): V?
}