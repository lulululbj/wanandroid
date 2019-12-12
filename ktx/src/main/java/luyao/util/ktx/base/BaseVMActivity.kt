package luyao.util.ktx.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by luyao
 * on 2019/11/15 15:57
 */
abstract class BaseVMActivity<VM : BaseViewModel>(useBinding:Boolean = false) : AppCompatActivity() {

    private val _useBinding = useBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startObserve()
        if (!_useBinding) setContentView(getLayoutResId())
        initView()
        initData()
    }

    open fun getLayoutResId(): Int = 0
    abstract fun initView()
    abstract fun initData()
    abstract fun startObserve()


}