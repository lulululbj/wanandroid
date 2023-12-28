package luyao.wanandroid.model

import androidx.appcompat.app.AppCompatDelegate
import luyao.ktx.util.MMKVDelegate

/**
 * Description:
 * Author: luyao
 * Date: 2023/11/15 11:08
 */

object MMKVConstants {

    var nightMode by MMKVDelegate("night_mode", AppCompatDelegate.MODE_NIGHT_NO) // 夜间模式

}