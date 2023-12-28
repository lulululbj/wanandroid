import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
}

val baseVersion = "0.0.1"
val verName = "${baseVersion}.${getGitHash()}"
val verCode = getGitCommitCount().toInt()

android {
    namespace = "luyao.wanandroid"
    compileSdk = 34

    defaultConfig {
        applicationId = "luyao.wanandroid"
        minSdk = 24
        targetSdk = 34
        versionCode = verCode
        versionName = verName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

//        ksp {
//            arg("room.schemaLocation", "$projectDir/schemas")
//            arg("room.incremental", "true")
//        }
    }

    signingConfigs {
        create("release") {
            storeFile = file("../key/wanandroid")
            storePassword = "123456"
            keyAlias = "wanandroid"
            keyPassword = "123456"
        }
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "_debug"
            resValue("string", "app_name", "@string/app_name_debug")
            resValue("string", "package_name", "@string/package_name_debug")
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        getByName("release") {
            resValue("string", "app_name", "@string/app_name_release")
            resValue("string", "package_name", "@string/package_name_release")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    applicationVariants.all {
        val variant = this
        val time = SimpleDateFormat("yyyyMMddHHmm", Locale.getDefault()).format(Date())
        variant.outputs.map {
            it as com.android.build.gradle.internal.api.BaseVariantOutputImpl
        }.forEach {
            it.outputFileName =
                "Pay_${variant.flavorName}_${variant.versionName}_" + time + "_" + getGitBranch() + ".apk"
        }
    }
}

dependencies {

    implementation(project(":luyao_ktx"))

    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("com.airbnb.android:lottie-compose:5.2.0")

    val composeBom = platform("androidx.compose:compose-bom:2023.08.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.compose.runtime:runtime-livedata")

    val accompanistVersion = "0.32.0"
    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion")



//    val roomVersion = "2.6.0"
//    implementation("androidx.room:room-runtime:$roomVersion")
//    implementation("androidx.room:room-ktx:$roomVersion")
//    ksp("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.preference:preference-ktx:1.2.1")
//    implementation(platform("com.google.firebase:firebase-bom:32.1.1"))
//    implementation("com.google.firebase:firebase-analytics-ktx")
//    implementation("com.google.firebase:firebase-crashlytics-ktx")
//    implementation("com.google.firebase:firebase-perf-ktx")

    implementation("com.google.dagger:hilt-android:2.46.1")
    kapt("com.google.dagger:hilt-android-compiler:2.46.1")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")
//    implementation("com.github.loper7:DateTimePicker:0.6.3")
//    implementation("com.github.xkzhangsan:xk-time:3.2.4")
//    implementation("de.psdev.licensesdialog:licensesdialog:2.2.0")
//    implementation("com.github.tingyik90:snackprogressbar:6.4.2")
//    implementation("net.lingala.zip4j:zip4j:2.11.2")
//    implementation("com.patrykandpatrick.vico:compose-m3:1.12.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

fun getGitBranch(): String {
    return "git symbolic-ref --short -q HEAD".runCommand()
}

fun getGitHash(): String {
    return "git rev-parse --short HEAD".runCommand()
}

fun getGitCommitCount(): String {
    return "git rev-list --count HEAD".runCommand()
}

fun String.runCommand(
    workingDir: File = File("."),
    timeoutAmount: Long = 60,
    timeoutUnit: TimeUnit = TimeUnit.SECONDS
): String = ProcessBuilder(split("\\s(?=(?:[^'\"`]*(['\"`])[^'\"`]*\\1)*[^'\"`]*$)".toRegex()))
    .directory(workingDir)
    .redirectOutput(ProcessBuilder.Redirect.PIPE)
    .redirectError(ProcessBuilder.Redirect.PIPE)
    .start()
    .apply { waitFor(timeoutAmount, timeoutUnit) }
    .run {
        val error = errorStream.bufferedReader().readText().trim()
        if (error.isNotEmpty()) {
            throw IOException(error)
        }
        inputStream.bufferedReader().readText().trim()
    }
