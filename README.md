[Wanandroid](https://www.wanandroid.com/) 是鸿洋鸿大大的安卓开源知识网站，包含最新博文，最新项目，常用工具，公众号文章收录等等功能，同时也开源了所有 API 接口，方便大家打造自己的 Wanandroid 客户端。Github 上关于 Wanandroid 的客户端也层出不穷，Java的，Kotlin 的，Flutter 的，Mvp 的，MVMM 的，各种各样，但是还没看到 **Kotlin+MVVM+LiveData+协程** 版本的，加上最近正在看 MVVM 和 LiveData，就着手把我之前写的 Mvp 版本的 Wanandroid 改造成 MVVM，[项目地址](https://github.com/lulululbj/wanandroid) 。注意，`mater` 分支是年久失修的 `Mvp` 版本，不一定保证可以运行。`mvvm-kotlin
` 分支是最新代码。

关于 MVVM，大家应该也比较熟悉了，上一张 MVVM 经典架构图：

![](https://user-gold-cdn.xitu.io/2019/4/15/16a21016df9c76c5?w=960&h=720&f=webp&s=15382)

`Model-View-ViewModel`，`View` 指绿色的 `Activity/Fragment`，主要负责界面显示，不负责任何业务逻辑和数据处理。`Model` 指的是 `Repository` 包含的部分，主要负责数据获取，来组本地数据库或者远程服务器。`ViewModel` 指的是图中蓝色部分，主要负责业务逻辑和数据处理，本身不持有 `View` 层引用，通过 `LiveData` 向 `View` 层发送数据。`Repository` 统一了数据入口，不管来自数据库，还是服务器，统一打包给 `ViewModel` ，我在项目中并没有使用数据库，而是使用缓存代替。

除了 `MMVM` 以外，我用 `协程` 代替了 `RxJava`。这里先不论协程和 RxJava 孰优孰劣，只是用惯了 RxJava，协程的确会给你耳目一新的感觉，用同步的方式写异步代码。在 Java 中并没有协程的概念，Kotlin 中在编译期实现了协程，通过类似状态机的实现。协程可以看做是轻量级的线程，不会存在上下文切换的带来的性能损耗，理论上是比线程效率更高的。

下面以登录页面 `LoginActivity` 为例，看一下数据流程。

## Model

```java
@POST("/user/login")
fun login(@Field("username") userName: String, @Field("password") passWord: String): Deferred<WanResponse<User>>
```

这是登录 Api 接口。

```java
class LoginRepository : BaseRepository() {

    suspend fun login(userName: String, passWord: String): WanResponse<User> {
        return apiCall { WanRetrofitClient.service.login(userName, passWord).await() }
    }
    
}
```

`LoginRepository` 中定义具体的登录逻辑，通过 `Retrofit` 调用登录接口，返回 `WanResponse<User>`。注意，要在协程中使用，所以定义为 `suspend` 方法。

## ViewModel

```java
class LoginViewModel : BaseViewModel() {
    val mLoginUser: MutableLiveData<User> = MutableLiveData()
    val errMsg: MutableLiveData<String> = MutableLiveData()
    private val repository by lazy { LoginRepository() }

    fun login(userName: String, passWord: String) {
        launch {
            val response = withContext(Dispatchers.IO) { repository.login(userName, passWord) }
            executeResponse(response, { mLoginUser.value = response.data }, { errMsg.value = response.errorMsg })
        }
    }
}
```

`LoginViewModel` 持有 `LoginRepository`，并通过它执行具体登录逻辑，这一块使用协程执行。返回结果通过 `executeResponse()` 方法处理，这是我自己封装的方法：

```java
suspend fun executeResponse(response: WanResponse<Any>, successBlock: suspend CoroutineScope.() -> Unit,
                                errorBlock: suspend CoroutineScope.() -> Unit) {
        coroutineScope {
            if (response.errorCode == -1) errorBlock()
            else successBlock()
        }
    }
```

Kotlin 的一些函数式编程语言特性会给我们的开发带来一些便利。`executeResponse()` 提供了统一的响应错误处理。

## View

```java
 mViewModel.apply {
        mLoginUser.observe(this@LoginActivity, Observer {
            dismissProgressDialog()
            startActivity(MainNormalActivity::class.java)
            finish()
        })

        errMsg.observe(this@LoginActivity, Observer {
            dismissProgressDialog()
            it?.run { toast(it) }
        })
    }
```

最后就是 `LoginActivity` 代表的 View 层了，View 层和 ViewModel 层通过 LiveData 进行绑定，上面代码中的 `mLoginUser` 和 `errMsg` 就是 ViewModel 层 “发射” 过来的数据。关于数据绑定，我并没有使用 `DataBinding`，这个纯粹是个人喜好了，我只是不喜欢 DataBinding 带来的代码不易读。

相对 Mvp 繁多的接口来说，个人感觉 Mvvm 的数据流更加清晰。搭配 Kotlin 和协程的使用，进一步简化代码。下面是一些项目截图：


![](https://user-gold-cdn.xitu.io/2019/4/15/16a21b0a4a933a9e?w=1115&h=660&f=png&s=215236)


![](https://user-gold-cdn.xitu.io/2019/4/15/16a21b2e9540185d?w=1118&h=658&f=png&s=238495)

项目地址点这个： [传送门](https://github.com/lulululbj/wanandroid)，记得切换到 `mvvm-kotlin` 分支 ，欢迎带来 star 和 issue 丢过来 ！

推荐一下我的另一个应用，[Box —— 我的开发助手](https://juejin.im/post/5c8a52606fb9a04a05408c94)，添加了查看 logcat 的功能。

最后，也欢迎大家关注我的公众号 **秉心说**，话说公号关注人数还没掘金多，后续会继续 《走进 JDK 系列》以及 Android 相关知识的分享，欢迎大家扫码关注！

> 文章首发微信公众号： **`秉心说`** ， 专注 Java 、 Android 原创知识分享，LeetCode 题解，欢迎关注！

![](https://user-gold-cdn.xitu.io/2019/3/30/169cf046d9579e78?w=258&h=258&f=jpeg&s=27711)