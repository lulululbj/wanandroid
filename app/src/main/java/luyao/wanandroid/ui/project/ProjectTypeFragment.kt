package luyao.wanandroid.ui.project

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.chad.library.adapter.base.BaseQuickAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_projecttype.*
import kotlinx.android.synthetic.main.fragment_systemtype.*
import luyao.mvvm.core.base.BaseVMFragment
import luyao.wanandroid.BR
import luyao.wanandroid.R
import luyao.wanandroid.adapter.BaseBindAdapter
import luyao.wanandroid.databinding.FragmentProjecttypeBinding
import luyao.wanandroid.model.bean.Article
import luyao.wanandroid.ui.BrowserActivity
import luyao.wanandroid.ui.square.ArticleViewModel
import luyao.wanandroid.util.Preference
import luyao.wanandroid.view.CustomLoadMoreView

/**
 * 最新项目/项目分类
 * Created by Lu
 * on 2018/4/1 17:06
 */
@AndroidEntryPoint
class ProjectTypeFragment : BaseVMFragment<FragmentProjecttypeBinding>(R.layout.fragment_projecttype) {

    private val articleViewModel : ArticleViewModel by viewModels()

    private val isLogin by Preference(Preference.IS_LOGIN, false)
    private val cid by lazy { arguments?.getInt(CID) }
    private val isLasted by lazy { arguments?.getBoolean(LASTED) } // 区分是最新项目 还是项目分类
    private val projectAdapter by lazy { BaseBindAdapter<Article>(R.layout.item_project, BR.article) }

    companion object {
        private const val CID = "projectCid"
        private const val LASTED = "lasted"
        fun newInstance(cid: Int, isLasted: Boolean): ProjectTypeFragment {
            val fragment = ProjectTypeFragment()
            val bundle = Bundle()
            bundle.putInt(CID, cid)
            bundle.putBoolean(LASTED, isLasted)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView() {
        binding.adapter = projectAdapter
        initRecycleView()
    }

    override fun initData() {
        refresh()
    }

    fun refresh() {
        projectAdapter.setEnableLoadMore(false)
        loadData(true)
    }

    private fun initRecycleView() {
        projectRefreshLayout.setOnRefreshListener { refresh() }
        projectAdapter.run {
            setOnItemClickListener { _, _, position ->
                Navigation.findNavController(projectRecycleView).navigate(R.id.action_tab_to_browser, bundleOf(BrowserActivity.URL to projectAdapter.data[position].link))
            }
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({ loadMore() }, typeRecycleView)
            onItemChildClickListener = this@ProjectTypeFragment.onItemChildClickListener
        }
    }

    private fun loadMore() {
        loadData(false)
    }


    private fun loadData(isRefresh: Boolean) {
        isLasted?.run {
            if (this) {
                articleViewModel.getLatestProjectList(isRefresh)
            } else {
                cid?.let {
                    articleViewModel.getProjectTypeDetailList(isRefresh, it)
                }
            }
        }
    }

    private val onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
        when (view.id) {
            R.id.articleStar -> {
                if (isLogin) {
                    projectAdapter.run {
                        data[position].run {
                            collect = !collect
                            articleViewModel.collectArticle(id, collect)
                        }
                        notifyDataSetChanged()
                    }
                } else {
                    Navigation.findNavController(projectRecycleView).navigate(R.id.action_tab_to_login)
                }
            }
        }
    }

    override fun startObserve() {
        articleViewModel.uiState.observe(viewLifecycleOwner, Observer {
            projectRefreshLayout.isRefreshing = it.showLoading

            it.showSuccess?.let { list ->
                projectAdapter.run {
                    if (it.isRefresh) replaceData(list.datas)
                    else addData(list.datas)
                    setEnableLoadMore(true)
                    loadMoreComplete()
                }
            }

            if (it.showEnd) projectAdapter.loadMoreEnd()
        })
    }

}