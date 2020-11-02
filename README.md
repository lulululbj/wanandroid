之前的 README 文件（在这里可以看到 [真香！Kotlin+MVVM+LiveData+协程 打造 Wanandroid！](https://blog.csdn.net/sunluyao_/article/details/101318799)) 和现有代码其实已经有较大出入，也包含了一些不正确的观点，回来更新一下。

翻一下提交记录，第一次 commit 是在 **13 Mar 2018** ，没记错的话当时是基于 MVP 模式搭建的基础框架，到现在经过 100 多次 commit，整个框架已经完全更新。最新的代码在 **mvvm-kotlin** 分支。

刚从 MVP 过渡到 MVVM ，挺兴奋的，一股脑刷刷的写，其实犯了很多错误。第一次认识到关于 MVVM 的一些错误理解是通过这篇文章 [关于MVC/MVP/MVVM的一些错误认识](https://juejin.im/post/6844903938873901064) ，大家可以仔细阅读这篇文章。

随着对架构的逐步深入认识，对 MVVM 的概念又逐渐模糊起来。在 Android 开发中，到底什么是 MVVM 架构？

> 数据驱动 UI ？
>
> ViewModel + LiveData ？
>
> 不使用 Databinding 是不是 MVVM ？
>
> 不使用双向绑定是不是 MVVM ？

一时之间好像遍地都是 Jetpack MVVM 的相关文章和开源项目，但我却愈发觉得这算不上真正的 MVVM 。我更愿意称之为 **变种 MVP** ，或者它就是 **Jetpack 架构** 。

![](https://user-gold-cdn.xitu.io/2019/4/15/16a21016df9c76c5?w=960&h=720&f=webp&s=15382)

上面这张图就可以清晰的表达它的架构，**它不是 MVP，也不是 MVVM** 。

当然，每个人心中都有自己的架构，欢迎到 issue 区表达自己的看法。

最后简单罗列一下项目的技术点：

* LiveData 作为数据容器，由 ViewModel 进行保存
* Databinding 负责数据绑定工作
* 使用 Kotlin Coroutines 完成网络请求等耗时异步任务，其中登录页面尝试使用了 flow 
* koin 负责依赖注入工作
* ......

作为一个试错项目，后续可能继续引入各种新奇类库或者 gradle 插件，欢迎继续关注。

最后给我的专栏打个广告：

> Android 面试进阶指南目录
>
> **计算机网络**
> 1. [http 速查](https://blog.csdn.net/sunluyao_/article/details/109267554)
>
> **Android**
> 1. [唠唠任务栈，返回栈和启动模式](https://blog.csdn.net/sunluyao_/article/details/107948153)
> 2. [唠唠 Activity 的生命周期](https://blog.csdn.net/sunluyao_/article/details/108067935)
> 3. [扒一扒 Context](https://blog.csdn.net/sunluyao_/article/details/108162604)
> 4. [面试官：为什么不能使用 Application Context 显示 Dialog？](https://blog.csdn.net/sunluyao_/article/details/108373573)
> 5. [面试官：OOM 可以被 try catch 吗？](https://blog.csdn.net/sunluyao_/article/details/108656480)
> 6. [面试官：Activity.finish() 之后十秒才回调 onDestroy ？](https://blog.csdn.net/sunluyao_/article/details/109110737)
> 7. [面试官：如何监测应用的 FPS ？](https://blog.csdn.net/sunluyao_/article/details/109440338)





> 添加微信 **bingxinshuo_** ，加入技术交流群 。

![](http://cdn.luyao.tech/wechat/green.png)
