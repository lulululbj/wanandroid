package luyao.util.ktx.ext.listener

import androidx.viewpager.widget.ViewPager

/**
 * Created by luyao
 * on 2019/8/30 16:31
 */

/**
 * Add an action which will be invoked when the ViewPager onPageSelected
 * @see ViewPager.addOnPageChangeListener
 */
fun ViewPager.onPageSelected(action: (newPosition: Int) -> Unit) =
    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {}

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

        override fun onPageSelected(position: Int) {
            action(position)
        }
    })
