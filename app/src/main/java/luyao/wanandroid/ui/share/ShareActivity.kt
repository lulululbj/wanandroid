//package luyao.wanandroid.ui.share
//
//import androidx.activity.viewModels
//import androidx.lifecycle.Observer
//import androidx.navigation.Navigation
//import kotlinx.android.synthetic.main.activity_share.*
//import luyao.mvvm.core.base.BaseVMActivity
//import luyao.util.ktx.ext.toast
//import luyao.wanandroid.R
//import luyao.wanandroid.databinding.ActivityShareBinding
//
///**
// * Created by luyao
// * on 2019/10/15 15:21
// */
//class ShareActivity : BaseVMActivity() {
//
//    private val shareViewModel: ShareViewModel by viewModels()
//    private val binding by binding<ActivityShareBinding>(R.layout.activity_share)
//
//    override fun initView() {
//        binding.viewModel = shareViewModel
//    }
//
//    override fun initData() {
//    }
//
//
//    override fun startObserve() {
//        shareViewModel.uiState.observe(this, Observer {
//
//            it.showSuccess?.let {
//                Navigation.findNavController(shareBt).navigateUp()
//            }
//
//            it.showError?.let { err -> toast(err) }
//        })
//    }
//}