package luyao.wanandroid.ui.about

import GITHUB_PAGE
import ISSUE_URL
import android.content.Intent
import android.net.Uri
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.widget.PopupMenu
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import de.psdev.licensesdialog.LicensesDialog
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20
import de.psdev.licensesdialog.model.Notice
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.title_layout.*
import luyao.util.ktx.base.BaseActivity
import luyao.util.ktx.ext.openBrowser
import luyao.wanandroid.R

class AboutActivity : BaseActivity() {

    override fun getLayoutResId() = R.layout.activity_about

    override fun initView() {
        mToolbar.title = getString(R.string.about)
        mToolbar.setNavigationIcon(R.drawable.arrow_back)
    }

    override fun initData() {
        mToolbar.setNavigationOnClickListener { onBackPressed() }
        license.setOnClickListener { showOwnLicense() }
        source.setOnClickListener { openBrowser(GITHUB_PAGE) }
        feedback.setOnClickListener { showFeedBackMenu() }
        thirdLib.setOnClickListener { showLicenseDialog() }
        developer.setOnClickListener { showMe() }
    }

    private fun showOwnLicense() {
        val license = ApacheSoftwareLicense20()
        val notice = Notice(getString(R.string.app_name), GITHUB_PAGE, "", license)
        LicensesDialog.Builder(this)
                .setNotices(notice)
                .build()
                .show()
    }

    private fun showLicenseDialog() {
        LicensesDialog.Builder(this)
                .setNotices(R.raw.licenses)
                .build()
                .show()
    }

    private fun showFeedBackMenu() {
        val feedbackMenu = PopupMenu(this, feedback, Gravity.RIGHT)
        feedbackMenu.menuInflater.inflate(R.menu.menu_feedback, feedbackMenu.menu)
        feedbackMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menu_issue -> {
                    openBrowser(ISSUE_URL)
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
        feedbackMenu.show()
    }

    private fun showMe() {
        MaterialDialog(this).show {
            customView(R.layout.dialog_me)
        }
    }
}