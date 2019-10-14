package luyao.util.ktx.ext

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import luyao.util.ktx.bean.AppInfo
import java.io.File

/**
 * Created by luyao
 * on 2019/6/12 10:53
 */

val Context.versionName: String
    get() = packageManager.getPackageInfo(packageName, 0).versionName

val Context.versionCode: Long
    get() = with(packageManager.getPackageInfo(packageName, 0)) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) longVersionCode else versionCode.toLong()
    }

/**
 * Return [AppInfo] by apk file path [apkPath]
 */
fun Context.getAppInfo(apkPath: String): AppInfo {
    val packageInfo = packageManager.getPackageArchiveInfo(apkPath, PackageManager.GET_META_DATA)
    packageInfo.applicationInfo.sourceDir = apkPath
    packageInfo.applicationInfo.publicSourceDir = apkPath

    val packageName = packageInfo.packageName
    val appName = packageManager.getApplicationLabel(packageInfo.applicationInfo).toString()
    val versionName = packageInfo.versionName
    val versionCode = packageInfo.versionCode
    val icon = packageManager.getApplicationIcon(packageInfo.applicationInfo)
    return AppInfo(apkPath, packageName, versionName, versionCode.toLong(), appName, icon)
}

fun Context.getAppInfos(apkFolderPath: String): List<AppInfo> {
    val appInfoList = ArrayList<AppInfo>()
    for (file in File(apkFolderPath).listFiles())
        appInfoList.add(getAppInfo(file.path))
    return appInfoList
}

/**
 * Get app signature by [packageName]
 */
fun Context.getAppSignature(packageName: String = this.packageName): ByteArray? {
    val packageInfo: PackageInfo =
        packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
    val signatures = packageInfo.signatures
    return signatures[0].toByteArray()
}

/**
 * Whether the application is installed
 */
fun Context.isPackageInstalled(pkgName: String): Boolean {
    return try {
        packageManager.getPackageInfo(pkgName, 0)
        true
    } catch (e: Exception) {
        false
    }
}


