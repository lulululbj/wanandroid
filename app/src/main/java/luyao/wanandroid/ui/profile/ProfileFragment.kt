package luyao.wanandroid.ui.profile

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.Navigation
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.bumptech.glide.Glide
import com.google.android.material.composethemeadapter.MdcTheme
import com.google.gson.Gson
import de.psdev.licensesdialog.LicensesDialog
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20
import de.psdev.licensesdialog.model.Notice
import kotlinx.android.synthetic.main.fragmnet_profile.*
import luyao.mvvm.core.base.BaseFragment
import luyao.util.ktx.ext.gone
import luyao.util.ktx.ext.openBrowser
import luyao.util.ktx.ext.visible
import luyao.wanandroid.R
import luyao.wanandroid.model.bean.User
import luyao.wanandroid.util.GITHUB_PAGE
import luyao.wanandroid.util.Preference
import luyao.wanandroid.view.compose.Line

/**
 * Created by luyao
 * on 2019/12/12 14:12
 */
class ProfileFragment : BaseFragment() {

    private var isLogin by Preference(Preference.IS_LOGIN, false)
    private var userJson by Preference(Preference.USER_GSON, "")

    override fun getLayoutResId() = R.layout.fragmnet_profile

    override fun initView() {
        titleTv.text = getString(R.string.me)

        composeView.setContent {
            MdcTheme {
                Column {
                    Menu(stringResource(R.string.open_license)) {
                        showOwnLicense()
                    }
                    Line()
                    Menu(stringResource(R.string.source_url)) {
                        activity?.openBrowser(GITHUB_PAGE)
                    }
                    Line()
                    Menu(stringResource(R.string.feedback)) {
                        showFeedBackMenu()
                    }
                    Line()
                    Menu(stringResource(R.string.third_lib)) {
                        showLicenseDialog()
                    }
                    Line()
                    Menu(stringResource(R.string.developer)) {
                        showMe()
                    }
                    Line()
                    VersionTv()
                }

            }
        }
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    override fun initData() {
        loginLayout.setOnClickListener {
            if (!isLogin) Navigation.findNavController(loginLayout)
                .navigate(R.id.action_tab_to_login)
        }
        collect.setOnClickListener {
            if (isLogin) Navigation.findNavController(loginLayout)
                .navigate(R.id.action_tab_to_colect)
        }
    }

    private fun refreshData() {
        if (isLogin) {
            val user = Gson().fromJson<User>(userJson, User::class.java)
            Glide.with(icon).load(user.icon).error(R.drawable.ic_dynamic_user).into(icon)
            loginTv.text = user.username
            collect.visible()
        } else {
            loginTv.text = "登录/注册"
            collect.gone()
        }
    }

    private fun showOwnLicense() {
        val notice =
            Notice(getString(R.string.app_name), GITHUB_PAGE, "", ApacheSoftwareLicense20())
        LicensesDialog.Builder(activity)
            .setNotices(notice)
            .build()
            .show()
    }

    private fun showLicenseDialog() {
        LicensesDialog.Builder(activity)
            .setNotices(R.raw.licenses)
            .build()
            .show()
    }

    private fun showFeedBackMenu() {
        val uri = Uri.parse(getString(R.string.sendto))
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_topic))
//                    intent.putExtra(Intent.EXTRA_TEXT,
//                            getString(R.string.device_model) + Build.MODEL + "\n"
//                                    + getString(R.string.sdk_version) + Build.VERSION.RELEASE + "\n"
//                                    + getString(R.string.version))version
        startActivity(intent)
    }

    private fun showMe() {
        context?.let {
            MaterialDialog(it).show {
                customView(R.layout.dialog_me)
            }
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

@Preview
@Composable
fun ProfileScreenPreview() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Menu(stringResource(R.string.open_license)) {

        }
        Spacer(modifier = Modifier.background(colorResource(R.color.light_gray)))
    }
}