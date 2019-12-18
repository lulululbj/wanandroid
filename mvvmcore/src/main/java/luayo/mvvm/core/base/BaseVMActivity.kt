package luayo.mvvm.core.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import luyao.util.ktx.base.BaseViewModel

/**
 * Created by luyao
 * on 2019/12/18 14:46
 */
abstract class BaseVMActivity<VM :BaseViewModel,T : ViewDataBinding> : AppCompatActivity(){

    protected lateinit var mBinding : T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        startObserve()
        initView()
        initData()
    }

    private fun initDataBinding(){
        mBinding = DataBindingUtil.setContentView(this,getLayoutResId())
    }

    abstract fun getLayoutResId(): Int
    abstract fun initView()
    abstract fun initData()
    abstract fun startObserve()
}