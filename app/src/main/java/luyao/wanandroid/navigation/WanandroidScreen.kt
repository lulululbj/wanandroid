package luyao.wanandroid.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import luyao.wanandroid.R
import luyao.wanandroid.model.bean.SystemParent
import luyao.wanandroid.ui.blog.BlogPage
import luyao.wanandroid.ui.home.HomePage
import luyao.wanandroid.ui.profile.ProfilePage
import luyao.wanandroid.ui.system.SystemDetailPage

/**
 * Description:
 * Author: luyao
 * Date: 2022/7/15 16:11
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WanandroidPage() {
    val navController = rememberAnimatedNavController()
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
        contentColor = Color.White
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
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

val tweenDuration = 700

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationGraph(navController: NavHostController, innerPadding: PaddingValues) {

    AnimatedNavHost(
        navController = navController, startDestination = BottomNavItem.Home.route,
        modifier = Modifier.padding(innerPadding)
//        enterTransition = {
//            slideInHorizontally(initialOffsetX = { 1000 })
//        },
//        exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) },
//        popEnterTransition = {
//            slideInHorizontally(initialOffsetX = { -1000 })
//        },
//        popExitTransition = {
//            slideOutHorizontally(targetOffsetX = { 1000 })
//        }
    ) {
        composable(
            BottomNavItem.Home.route,
//            enterTransition = {
//                slideIntoContainer(
//                    AnimatedContentScope.SlideDirection.Left,
//                    animationSpec = tween(tweenDuration)
//                )
//            },
//            popEnterTransition = {
//                slideIntoContainer(
//                    AnimatedContentScope.SlideDirection.Right,
//                    animationSpec = tween(tweenDuration)
//                )
//            },
//            exitTransition = {
//                slideOutOfContainer(
//                    AnimatedContentScope.SlideDirection.Right,
//                    animationSpec = tween(tweenDuration)
//                )
//            }, popExitTransition = {
//                slideOutOfContainer(
//                    AnimatedContentScope.SlideDirection.Right,
//                    animationSpec = tween(tweenDuration)
//                )
//            }
        ) {
            HomePage(navController)
        }
        composable(BottomNavItem.Blog.route) {
            BlogPage(BottomNavItem.Blog)
        }
        composable(BottomNavItem.Search.route) {
            SearchScreen()
        }
        composable(BottomNavItem.Project.route) {
            BlogPage(BottomNavItem.Project)
        }
        composable(BottomNavItem.Profile.route) {
            ProfilePage()
        }
        composable(route = Route.SystemDetail) {
            val data = it.arguments?.get("systemParent") as SystemParent
            SystemDetailPage(data, navController)
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
    val SystemDetail: String = "systemDetail"
}

fun NavController.navigateAndArgument(
    route: String,
    args: List<Pair<String, Any>>? = null,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,

    ) {
    navigate(route = route, navOptions = navOptions, navigatorExtras = navigatorExtras)

    if (args == null && args?.isEmpty() == true) {
        return
    }

    val bundle = backQueue.lastOrNull()?.arguments
    if (bundle != null) {
        bundle.putAll(bundleOf(*args?.toTypedArray()!!))
    } else {
        println("The last argument of NavBackStackEntry is NULL")
    }
}

