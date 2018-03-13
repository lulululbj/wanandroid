package luyao.gayhub.base.rx

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by luyao
 * on 2018/1/19 14:35
 */
class UIScheduler<T> : RxSchedulers<T>(Schedulers.io(), AndroidSchedulers.mainThread())