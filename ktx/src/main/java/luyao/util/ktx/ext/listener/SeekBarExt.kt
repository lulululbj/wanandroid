package luyao.util.ktx.ext.listener

import android.widget.SeekBar

/**
 * Created by luyao
 * on 2019/7/10 16:28
 */

/**
 * Add an action which will be invoked when the SeekBar onProgressChanged
 * @see SeekBar.setOnSeekBarChangeListener
 */
fun SeekBar.onProgressBarChanged(action: (Int, Boolean) -> Unit) {
    setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            action(progress, fromUser)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }
    })
}