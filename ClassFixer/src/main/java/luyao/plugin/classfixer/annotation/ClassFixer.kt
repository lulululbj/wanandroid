package luyao.plugin.classfixer.annotation

import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy

/**
 * @author: luyao
 * @date：  2020/10/19 13:58
 */
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS)
annotation class ClassFixer(val value: String)