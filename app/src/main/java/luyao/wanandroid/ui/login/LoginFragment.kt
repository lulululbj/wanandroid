package luyao.wanandroid.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.fragment.findNavController
import com.google.android.material.composethemeadapter.MdcTheme
import luyao.mvvm.core.base.BaseVMFragment
import luyao.util.ktx.ext.toast
import luyao.wanandroid.R
import luyao.wanandroid.databinding.FragmentLoginBinding
import luyao.wanandroid.model.bean.Title
import luyao.wanandroid.view.compose.LoadingDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Lu
 * on 2018/4/5 07:56
 */
class LoginFragment : BaseVMFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    private val loginVM by viewModel<LoginViewModel>()

    override fun initView() {
        binding.run {
            viewModel = loginVM
            title = Title(R.string.login, R.drawable.arrow_back) { activity?.onBackPressed() }

            composeView.setContent {
                LoginScreenStateless(
                    loginSuccess = { findNavController().popBackStack() },
                    showError = { message -> activity?.toast(message) }
                )
            }
        }
    }

    override fun initData() {
    }

    override fun startObserve() {
    }

}

@Composable
fun LoginScreenStateless(
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    loginSuccess: () -> Unit,
    showError: (String) -> Unit
) {

    var userName by remember { mutableStateOf("") }
    var passWord by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.observeAsState()

    LoginScreen(
        userName = userName,
        onUserNameChange = { value -> userName = value },
        passWord = passWord,
        onPassWordChange = { value -> passWord = value },
        showPassword = showPassword,
        changePasswordVisibility = { showPassword = !showPassword },
        clickLogin = { viewModel.login(userName, passWord) },
        clickRegister = { viewModel.register(userName, passWord) })

    if (uiState?.isLoading == true) {
        LoadingDialog()
    }

    uiState?.isSuccess?.let {
        loginSuccess()
    }

    uiState?.isError?.let { err ->
        LaunchedEffect(err){
            showError(err)
        }
    }
}

@Composable
fun LoginScreen(
    userName: String,
    onUserNameChange: (String) -> Unit,
    passWord: String,
    onPassWordChange: (String) -> Unit,
    showPassword: Boolean,
    changePasswordVisibility: () -> Unit,
    clickLogin: () -> Unit,
    clickRegister: () -> Unit
) {
    MdcTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 40.dp)
        ) {

            UserNameInput(userName = userName, onUserNameChange = onUserNameChange)

            PasswordInput(
                passWord = passWord,
                onPassWordChange = onPassWordChange,
                showPassword = showPassword,
                changePasswordVisibility = changePasswordVisibility
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = clickLogin,
                enabled = userName.isNotEmpty() && passWord.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                Text(stringResource(R.string.login), fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = clickRegister,
                enabled = userName.isNotEmpty() && passWord.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                Text(stringResource(R.string.register), fontSize = 16.sp)
            }

        }
    }
}

@Composable
fun UserNameInput(userName: String, onUserNameChange: (String) -> Unit) {
    OutlinedTextField(
        value = userName,
        maxLines = 1,
        onValueChange = onUserNameChange, label = {
            Text(
                stringResource(
                    R.string.username
                )
            )
        }, modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun PasswordInput(
    passWord: String,
    onPassWordChange: (String) -> Unit,
    showPassword: Boolean,
    changePasswordVisibility: () -> Unit
) {
    OutlinedTextField(
        value = passWord,
        maxLines = 1,
        onValueChange = onPassWordChange,
        label = { Text(stringResource(R.string.password)) },
        trailingIcon = {
            IconButton(onClick = changePasswordVisibility) {
                Icon(
                    if (showPassword) painterResource(R.drawable.ic_visibility) else painterResource(
                        R.drawable.ic_visibility_off
                    ),
                    contentDescription = ""
                )
            }
        },
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    )
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        userName = "",
        onUserNameChange = { },
        passWord = "",
        onPassWordChange = { },
        showPassword = false,
        changePasswordVisibility = { },
        clickLogin = { }) {
    }
}