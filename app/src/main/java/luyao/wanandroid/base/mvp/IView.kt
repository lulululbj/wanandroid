package luyao.gayhub.base.mvp

/**
 * Created by Lu
 * on 2018/3/7 19:50
 */
interface IView {

    fun showError(message: String?)

    fun showProgress()

    fun hideProgress()
}