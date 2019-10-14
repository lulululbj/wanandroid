package luyao.util.ktx.ext.listener

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView

/**
 * Created by luyao
 * on 2019/7/9 16:13
 */

/**
 * Add a DSL listener to simplify [TextView.addTextChangedListener]
 */
fun TextView.textWatcher(watcher: KtxTextWatcher.() -> Unit) =
    addTextChangedListener(KtxTextWatcher().apply(watcher))

class KtxTextWatcher : TextWatcher {

    private var _afterTextChanged: ((Editable?) -> Unit)? = null
    private var _beforeTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null
    private var _onTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null


    fun afterTextChanged(listener: ((Editable?) -> Unit)) {
        _afterTextChanged = listener
    }

    fun beforeTextChanged(listener: (CharSequence?, Int, Int, Int) -> Unit) {
        _beforeTextChanged = listener
    }

    fun onTextChanged(listener: (CharSequence?, Int, Int, Int) -> Unit) {
        _onTextChanged = listener
    }

    override fun afterTextChanged(s: Editable?) {
        _afterTextChanged?.invoke(s)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        _beforeTextChanged?.invoke(s, start, count, after)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        _onTextChanged?.invoke(s, start, before, count)
    }

}