package luyao.wanandroid.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import luyao.wanandroid.R
import luyao.wanandroid.model.bean.Article
import luyao.wanandroid.model.bean.SystemParent
import luyao.wanandroid.ui.blog.BlogPage
import luyao.wanandroid.ui.home.HomePage
import luyao.wanandroid.ui.profile.ProfilePage
import luyao.wanandroid.ui.search.SearchPage
import luyao.wanandroid.ui.system.SystemDetailPage
import luyao.wanandroid.ui.web.WebPage

/**
 * Description:
 * Author: luyao
 * Date: 2022/7/15 16:11
 */
@Composable
fun WanandroidPage() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController = navController) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavigationGraph(navController = navController, innerPadding)
    }
}

val items = listOf(
    BottomNavItem.Home,
    BottomNavItem.Blog,
    BottomNavItem.Search,
    BottomNavItem.Project,
    BottomNavItem.Profile
)

@Composable
fun BottomNavigation(navController: NavController) {
    androidx.compose.material.BottomNavigation(
        backgroundColor = colorResource(R.color.colorPrimary),
        contentColor = colorResource(R.color.white),
        modifier = Modifier.navigationBarsPadding()
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(item.icon),
                        contentDescription = stringResource(item.title)
                    )
                },
                label = { Text(text = stringResource(item.title), fontSize = 9.sp) },
                selectedContentColor = colorResource(R.color.white),
                unselectedContentColor = colorResource(R.color.white).copy(0.4f),
                alwaysShowLabel = true,
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, innerPadding: PaddingValues) {

    val onClickArticle = { article: Article ->
        val args = listOf(Pair("url", article.link))
        navController.navigateAndArgument(Route.Web, args)
    }

    NavHost(
        navController = navController, startDestination = BottomNavItem.Home.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(BottomNavItem.Home.route) {
            HomePage(navController, onClickArticle)
        }
        composable(BottomNavItem.Blog.route) {
            BlogPage(BottomNavItem.Blog, onClickArticle = onClickArticle)
        }
        composable(BottomNavItem.Search.route) {
            SearchPage(onClickArticle = onClickArticle)
        }
        composable(BottomNavItem.Project.route) {
            BlogPage(BottomNavItem.Project, onClickArticle = onClickArticle)
        }
        composable(BottomNavItem.Profile.route) {
            ProfilePage()
        }
        composable(route = Route.SystemDetail) {
            val data = it.arguments?.get("systemParent") as SystemParent
            SystemDetailPage(data, navController, onClickArticle = onClickArticle)
        }
        composable(route = Route.Web) {
            val url = it.arguments?.getString("url") ?: ""
            WebPage(url, navController)
        }
    }
}

sealed class BottomNavItem(val title: Int, val icon: Int, val route: String) {
    object Home : BottomNavItem(R.string.home, R.drawable.ic_home_black_24dp, "home")
    object Blog : BottomNavItem(R.string.blog, R.drawable.ic_blog, "blog")
    object Search : BottomNavItem(R.string.search, R.drawable.search, "search")
    object Project : BottomNavItem(R.string.project, R.drawable.ic_dashboard_black_24dp, "project")
    object Profile : BottomNavItem(R.string.me, R.drawable.ic_profile, "profile")
}

object Route {
    const val SystemDetail: String = "systemDetail"
    const val Web = "web"
}

fun NavController.navigateAndArgument(
    route: String,
    args: List<Pair<String, Any>>? = null,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,

    ) {
    navigate(route = route, navOptions = navOptions, navigatorExtras = navigatorExtras)

    if (args.isNullOrEmpty()) {
        return
    }

    val bundle = backQueue.lastOrNull()?.arguments
    if (bundle != null) {
        bundle.putAll(bundleOf(*args.toTypedArray()))
    } else {
        println("The last argument of NavBackStackEntry is NULL")
    }
}

