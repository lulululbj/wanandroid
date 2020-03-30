package luyao.mvvm.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * Created by luyao
 * on 2019/12/27 9:39
 */
abstract class BaseVMFragment<VM : BaseViewModel>(useDataBinding: Boolean = true) : Fragment() {

    private val _useBinding = useDataBinding
    protected lateinit var mBinding: ViewDataBinding
    protected lateinit var mViewModel: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (_useBinding) {
            mBinding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
            mBinding.root
        } else
            inflater.inflate(getLayoutResId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewModel = initVM()
        if (_useBinding) mBinding.lifecycleOwner = this
        initView()
        initData()
        startObserve()
        super.onViewCreated(view, savedInstanceState)
    }

    abstract fun getLayoutResId(): Int
    abstract fun initVM(): VM
    abstract fun initView()
    abstract fun initData()
    abstract fun startObserve()
}