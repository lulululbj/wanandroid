package luyao.plugin.classfixer

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author: luyao
 * @date：  2020/10/16 17:05
 */
class ClassFixerPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val appExtension = target.extensions.getByType(AppExtension::class.java)
        val transform = ClassFixerTransform(target)
        appExtension.registerTransform(transform)
    }
}