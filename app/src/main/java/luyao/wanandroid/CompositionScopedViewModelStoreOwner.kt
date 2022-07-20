package luyao.wanandroid

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner

// PLEASE, READ
//
// This is a way to scope ViewModels to the Composition.
// However, this doesn't survive configuration changes or procress death on its own.
// You can handle all config changes in compose by making the activity handle those in the Manifest file
// e.g. android:configChanges="colorMode|density|fontScale|keyboard|keyboardHidden|layoutDirection|locale|mcc|mnc|navigation|orientation|screenLayout|screenSize|smallestScreenSize|touchscreen|uiMode">
//
// This is just an exploration to see what's possible in Compose. We don't encourage developers to copy-paste
// this code if they don't fully understand the implications of it and if this actually solves the use case to solve.

/*
 * Composition-aware ViewModelStoreOwner
 */
internal class CompositionScopedViewModelStoreOwner : ViewModelStoreOwner, RememberObserver {

    private val viewModelStore = ViewModelStore()

    override fun getViewModelStore(): ViewModelStore = viewModelStore

    override fun onAbandoned() {
        viewModelStore.clear()
    }

    override fun onForgotten() {
        viewModelStore.clear()
    }

    override fun onRemembered() {
        // Nothing to do here
    }
}

/*
 * Applies a [CompositionScopedViewModelStore] value to [LocalViewModelStoreOwner] 
 * to be able to scope ViewModels to a certain subtree of the Composition.
 *
 * Note: It's not a good idea to use `ProvideViewModels` at a screen level due to 
 * https://twitter.com/ianhlake/status/1395128325811494913. As when the store leaves
 * the Composition, all state and ViewModels are lost.
 * It's a good idea to use `ProvideViewModels` whenever it makes sense, for example,
 * before starting a certain flow of your app that have multiple screens with their own VM.
 * For example, a login flow, registration flow, or the main app flow.
 */
@Composable
fun ProvideViewModels(content: @Composable () -> Unit) {
    val viewModelStoreOwner = remember { CompositionScopedViewModelStoreOwner() }
    CompositionLocalProvider(LocalViewModelStoreOwner provides viewModelStoreOwner) {
        content()
    }
}
