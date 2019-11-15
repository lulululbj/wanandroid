package luyao.util.ktx.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by luyao
 * on 2019/11/15 15:57
 */
abstract class BaseVMActivity<VM : BaseViewModel> : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startObserve()
        setContentView(getLayoutResId())
        initView()
        initData()
    }

    abstract fun getLayoutResId(): Int
    abstract fun initView()
    abstract fun initData()
    abstract fun startObserve()


}