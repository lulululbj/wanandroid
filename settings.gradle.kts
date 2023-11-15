pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "wanandroid"
include(":app")
include(":luyao_ktx")
project(":luyao_ktx").projectDir = File(settingsDir, "../Everywhere/luyao_ktx")
 