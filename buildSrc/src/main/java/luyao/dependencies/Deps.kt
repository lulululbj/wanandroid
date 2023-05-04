package luyao.dependencies

object Versions {
    const val compileSdk = 33
    const val buildTools = "29.0.3"
    const val minSdk = 21
    const val targetSdk = 29
    const val versionCode = 1
    const val versionName = "1.0"

    const val kotlin = "1.8.21"
    const val coroutines = "1.3.9"
    const val coroutines_android = "1.3.9"
    const val androidxArch = "2.0.0"
    const val mockito = "2.23.0"

    const val appcompat = "1.1.0"
    const val constraintLayout = "1.1.3"
    const val retrofit = "2.9.0"
    const val retrofit_converter_gson = "2.9.0"
    const val okhttp_logging_interceptor = "4.10.0"
    const val swipeRefreshLayout = "1.1.0"
    const val material = "1.2.0-beta01"
    const val circleImageview = "2.2.0"
    const val leakcanary = "2.0-alpha-3"
    const val baseRecyclerViewAdapterHelper = "2.9.50"
    const val banner = "1.4.10"
    const val glide = "4.11.0"
    const val glide_compiler = "4.11.0"
    const val cardView = "1.0.0"
    const val verticalTabLayout = "1.2.5"
    const val flowLayout = "1.1.2"
    const val persistentCookieJar = "v1.0.1"
    const val licensesDialog = "2.1.0"
    const val material_dialogs = "3.3.0"
    const val livedata_ktx = "2.2.0"
    const val viewPager2 = "1.0.0"
    const val koin = "2.0.1"
    const val core_ktx = "1.3.0"
    const val navigation = "2.5.0"
    const val recyclerView = "1.1.0"
    const val viewmodel_ktx = "2.2.0"
    const val lifecycle_extension = "2.2.0"
    const val coil_compose = "2.1.0"
}

object Deps {

    // androidx
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val livedata_ktx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.livedata_ktx}"
    const val viewPager2 = "androidx.viewpager2:viewpager2:${Versions.viewPager2}"
    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
    const val navigation_fragment_ktx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigation_ui_ktx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val viewmodel_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewmodel_ktx}"
    const val lifecycle_extension = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle_extension}"

    // kotlin
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines_android}"

    // network
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofit_converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit_converter_gson}"
    const val okhttp_logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp_logging_interceptor}"
    const val persistentCookieJar = "com.github.franmontiel:PersistentCookieJar:${Versions.persistentCookieJar}"

    // third
    const val circleimageview = "de.hdodenhof:circleimageview:${Versions.circleImageview}"
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakcanary}"
    const val baseRecyclerViewAdapterHelper = "com.github.CymChad:BaseRecyclerViewAdapterHelper:${Versions.baseRecyclerViewAdapterHelper}"
    const val banner = "com.youth.banner:banner:${Versions.banner}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide_compiler}"
    const val cardView = "androidx.cardview:cardview:${Versions.cardView}"
    const val verticalTabLayout = "q.rorbin:VerticalTabLayout:${Versions.verticalTabLayout}"
    const val flowLayout = "com.hyman:flowlayout-lib:${Versions.flowLayout}"
    const val licensesDialog = "de.psdev.licensesdialog:licensesdialog:${Versions.licensesDialog}"
    const val material_dialogs_core = "com.afollestad.material-dialogs:core:${Versions.material_dialogs}"
    const val material_dialogs_input = "com.afollestad.material-dialogs:input:${Versions.material_dialogs}"
    const val koin_android = "org.koin:koin-android:${Versions.koin}"
    const val koin_androidx_scope = "org.koin:koin-androidx-scope:${Versions.koin}"
    const val koin_androidx_viewmodel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    const val coil_compose = "io.coil-kt:coil-compose:${Versions.coil_compose}"

}