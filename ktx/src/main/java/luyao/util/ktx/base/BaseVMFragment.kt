package luyao.util.ktx.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

/**
 * Created by luyao
 * on 2019/6/10 10:48
 */
abstract class BaseVMFragment<VM : BaseViewModel> : androidx.fragment.app.Fragment() {

    protected lateinit var mViewModel: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initVM()
        initView()
        initData()
        startObserve()
        super.onViewCreated(view, savedInstanceState)
    }

    open fun startObserve() {
        mViewModel.mException.observe(this, Observer { it?.let { onError(it) } })
    }

    open fun onError(e: Throwable) {}

    abstract fun getLayoutResId(): Int

    abstract fun initView()

    abstract fun initData()

    private fun initVM() {
        providerVMClass()?.let {
            mViewModel = ViewModelProviders.of(this).get(it)
            lifecycle.addObserver(mViewModel)
        }
    }

    open fun providerVMClass(): Class<VM>? = null

    override fun onDestroy() {
        lifecycle.removeObserver(mViewModel)
        super.onDestroy()
    }
}