package luyao.wanandroid.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by luyao
 * on 2018/9/29 16:17
 */
abstract class BaseFragment2 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        attachPresenter()
        initView()
        initData()
        super.onViewCreated(view, savedInstanceState)
    }

    open fun attachPresenter(){}

    abstract fun getLayoutResId(): Int

    abstract fun initView()

    abstract fun initData()
}