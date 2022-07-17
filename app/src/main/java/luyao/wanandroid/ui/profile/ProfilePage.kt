package luyao.wanandroid.ui.profile

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.google.android.material.composethemeadapter.MdcTheme
import com.google.gson.Gson
import de.psdev.licensesdialog.LicensesDialog
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20
import de.psdev.licensesdialog.model.Notice
import luyao.util.ktx.ext.openBrowser
import luyao.wanandroid.R
import luyao.wanandroid.model.bean.User
import luyao.wanandroid.util.GITHUB_PAGE
import luyao.wanandroid.util.Preference
import luyao.wanandroid.view.compose.Line

/**
 * Description:
 * Author: luyao
 * Date: 2022/7/15 23:54
 */
@Composable
fun ProfilePage() {
    val hasLogin by Preference(Preference.IS_LOGIN, false)
    val userJson by Preference(Preference.USER_GSON, "")
    var user: User? = null

    val isLogin by remember {
        mutableStateOf(hasLogin)
    }

    if (isLogin) {
        user = Gson().fromJson(userJson, User::class.java)
    }
    val current = LocalContext.current
    ProfileScreen(
        isLogin,
        user,
        onLogin = {
            if (!isLogin) {
//                Navigation.findNavController(composeView)
//                    .navigate(R.id.action_tab_to_login)
            } else {
//                current.startKtxActivity<ComposeMainActivity>()
            }
        },
        showMyCollect = {
//            Navigation.findNavController(composeView)
//                .navigate(R.id.action_tab_to_colect)
        },
        showOwnLicense = { showOwnLicense(current) },
        browserGithub = { current.openBrowser(GITHUB_PAGE) },
        showFeedBack = { showFeedBackMenu(current) },
        showLicenseDialog = { showLicenseDialog(current) },
        showMe = { showMe(current) })
}

@Composable
fun ProfileScreen(
    isLogin: Boolean,
    user: User?,
    onLogin: () -> Unit,
    showMyCollect: () -> Unit,
    showOwnLicense: () -> Unit,
    browserGithub: () -> Unit,
    showFeedBack: () -> Unit,
    showLicenseDialog: () -> Unit,
    showMe: () -> Unit
) {
    MdcTheme {
        Column {
            TitleTv(text = stringResource(R.string.me))
            LoginMenu(isLogin = isLogin, user, clickLogin = onLogin)
            if (isLogin) {
                Line()
                Menu(stringResource(R.string.my_collect), showMyCollect)
            }
            Line()
            Menu(stringResource(R.string.open_license), showOwnLicense)
            Line()
            Menu(stringResource(R.string.source_url), browserGithub)
            Line()
            Menu(stringResource(R.string.feedback), showFeedBack)
            Line()
            Menu(stringResource(R.string.third_lib), showLicenseDialog)
            Line()
            Menu(stringResource(R.string.developer), showMe)
            Line()
            VersionTv()
        }
    }
}

@Composable
fun Menu(text: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable { onClick() }, verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = AnnotatedString(text),
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}

@Composable
fun VersionTv() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "V 1.0.0",
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 20.dp)
        )
    }
}

@Composable
fun TitleTv(text: String) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = MaterialTheme.colors.primary)
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            color = Color.White
        )
    }
}

@Composable
fun LoginMenu(isLogin: Boolean, user: User? = null, clickLogin: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()
        .height(80.dp)
        .clickable {
            clickLogin()
        }) {

        if (isLogin) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user?.icon)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.luyao),
                error = painterResource(R.drawable.luyao),
                contentDescription = stringResource(R.string.login),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .width(48.dp)
                    .height(48.dp)
            )
        } else {
            Image(
                painter = painterResource(R.drawable.luyao),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .width(48.dp)
                    .height(48.dp)
            )
        }

        Text(
            text = if (isLogin) user?.username ?: "" else "登录/注册", color = Color.Black,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 24.dp)
        )


    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        false,
        null,
        onLogin = {},
        showMyCollect = { },
        showOwnLicense = { },
        browserGithub = { },
        showFeedBack = { },
        showLicenseDialog = { }) {
    }
}

private fun showOwnLicense(context: Context) {
    val notice =
        Notice(context.getString(R.string.app_name), GITHUB_PAGE, "", ApacheSoftwareLicense20())
    LicensesDialog.Builder(context)
        .setNotices(notice)
        .build()
        .show()
}

private fun showLicenseDialog(context: Context) {
    LicensesDialog.Builder(context)
        .setNotices(R.raw.licenses)
        .build()
        .show()
}


private fun showFeedBackMenu(context: Context) {
    val uri = Uri.parse(context.getString(R.string.sendto))
    val intent = Intent(Intent.ACTION_SENDTO, uri)
    intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.mail_topic))
//                    intent.putExtra(Intent.EXTRA_TEXT,
//                            getString(R.string.device_model) + Build.MODEL + "\n"
//                                    + getString(R.string.sdk_version) + Build.VERSION.RELEASE + "\n"
//                                    + getString(R.string.version))version
    context.startActivity(intent)
}

private fun showMe(context: Context) {
    context.let {
        MaterialDialog(it).show {
            customView(R.layout.dialog_me)
        }
    }
}