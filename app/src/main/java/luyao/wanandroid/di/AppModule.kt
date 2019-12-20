package luyao.wanandroid.di

import luyao.wanandroid.CoroutinesDispatcherProvider
import luyao.wanandroid.model.repository.*
import luyao.wanandroid.ui.login.LoginViewModel
import luyao.wanandroid.ui.navigation.NavigationViewModel
import luyao.wanandroid.ui.project.ProjectViewModel
import luyao.wanandroid.ui.search.SearchViewModel
import luyao.wanandroid.ui.square.ArticleViewModel
import luyao.wanandroid.ui.system.SystemViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by luyao
 * on 2019/11/15 15:44
 */

val viewModelModule = module {
    viewModel { LoginViewModel(get(),get()) }
    viewModel { ArticleViewModel(get(), get(), get(), get(), get()) }
    viewModel { SystemViewModel(get(), get()) }
    viewModel { NavigationViewModel(get()) }
    viewModel { ProjectViewModel(get()) }
    viewModel { SearchViewModel(get(), get()) }
}

val repositoryModule = module {
    single { CoroutinesDispatcherProvider() }
    single { LoginRepository() }
    single { SquareRepository() }
    single { HomeRepository() }
    single { ProjectRepository() }
    single { CollectRepository() }
    single { SystemRepository() }
    single { NavigationRepository() }
    single { SearchRepository() }
}

val appModule = listOf(viewModelModule, repositoryModule)