package luyao.wanandroid.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader

/**
 * Created by Lu
 * on 2018/3/17 21:30
 */
class GlideImageLoader:ImageLoader() {

    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
        Glide.with(context).load(path).into(imageView)
    }
}