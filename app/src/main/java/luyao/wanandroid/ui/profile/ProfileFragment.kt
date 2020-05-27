package luyao.wanandroid.ui.profile

import android.content.Intent
import android.net.Uri
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.Navigation
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import de.psdev.licensesdialog.LicensesDialog
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20
import de.psdev.licensesdialog.model.Notice
import kotlinx.android.synthetic.main.fragmnet_profile.*
import luyao.mvvm.core.base.BaseFragment
import luyao.util.ktx.ext.gone
import luyao.util.ktx.ext.openBrowser
import luyao.util.ktx.ext.startKtxActivity
import luyao.util.ktx.ext.visible
import luyao.wanandroid.R
import luyao.wanandroid.model.bean.User
import luyao.wanandroid.test.ConstraintLayoutTest
import luyao.wanandroid.util.GITHUB_PAGE
import luyao.wanandroid.util.ISSUE_URL
import luyao.wanandroid.util.Preference

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
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    override fun initData() {

        versionName.setOnClickListener { startKtxActivity<ConstraintLayoutTest>() }
        license.setOnClickListener { showOwnLicense() }
        source.setOnClickListener { activity?.openBrowser(GITHUB_PAGE) }
        feedback.setOnClickListener { showFeedBackMenu() }
        thirdLib.setOnClickListener { showLicenseDialog() }
        developer.setOnClickListener { showMe() }
        loginLayout.setOnClickListener { if (!isLogin) Navigation.findNavController(loginLayout).navigate(R.id.action_tab_to_login) }
        collect.setOnClickListener { if (isLogin) Navigation.findNavController(loginLayout).navigate(R.id.action_tab_to_colect) }
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
        val notice = Notice(getString(R.string.app_name), GITHUB_PAGE, "", ApacheSoftwareLicense20())
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
        val feedbackMenu = context?.let { PopupMenu(it, feedback, Gravity.RIGHT) }
        feedbackMenu?.menuInflater?.inflate(R.menu.menu_feedback, feedbackMenu.menu)
        feedbackMenu?.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menu_issue -> {
                    activity?.openBrowser(ISSUE_URL)
                    true
                }
                R.id.menu_email -> {
                    val uri = Uri.parse(getString(R.string.sendto))
                    val intent = Intent(Intent.ACTION_SENDTO, uri)
                    intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_topic))
//                    intent.putExtra(Intent.EXTRA_TEXT,
//                            getString(R.string.device_model) + Build.MODEL + "\n"
//                                    + getString(R.string.sdk_version) + Build.VERSION.RELEASE + "\n"
//                                    + getString(R.string.version))version
                    startActivity(intent)
                    true
                }
                else -> {
                    true
                }
            }
        }
        feedbackMenu?.show()
    }

    private fun showMe() {
        context?.let {
            MaterialDialog(it).show {
                customView(R.layout.dialog_me)
            }
        }
    }
}