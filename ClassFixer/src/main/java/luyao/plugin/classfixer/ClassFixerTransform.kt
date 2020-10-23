package luyao.plugin.classfixer

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import luyao.plugin.classfixer.asm.AsmClassVisitor
import org.apache.commons.codec.digest.DigestUtils
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

/**
 * @author: luyao
 * @date：  2020/10/16 17:06
 */
class ClassFixerTransform(val project: Project) : Transform() {
    override fun getName() = "ClassFixer"

    override fun getInputTypes() = TransformManager.CONTENT_CLASS

    override fun getScopes() = TransformManager.SCOPE_FULL_PROJECT

    override fun isIncremental() = false

    override fun transform(transformInvocation: TransformInvocation) {
        val outputProvider = transformInvocation.outputProvider
        val inputs = transformInvocation.inputs
        inputs.forEach {
            it.jarInputs.forEach { jarInput ->
                handleJarInput(jarInput, outputProvider)
            }

            it.directoryInputs.forEach { directoryInput ->
                handleDirectoryInput(directoryInput, outputProvider)
            }
        }
    }

    private fun handleDirectoryInput(directoryInput: DirectoryInput, outputProvider: TransformOutputProvider) {
        println("directory: ${directoryInput.file.path} ${directoryInput.file.isFile}")
        val dir = directoryInput.file
        dir.walk().filter { it.name.endsWith(".class") }.forEach { file ->
//            println("find class: ${file.name}")
            val classReader = ClassReader(file.readBytes())
            val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
            val classVisitor = AsmClassVisitor(classWriter)
            classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
            val bytes = classWriter.toByteArray()
            file.writeBytes(bytes)
        }
        val output = outputProvider.getContentLocation(
                directoryInput.name,
                directoryInput.contentTypes,
                directoryInput.scopes,
                Format.DIRECTORY
        )
        FileUtils.copyDirectory(directoryInput.file, output)

    }

    private fun handleJarInput(jarInput: JarInput, outputProvider: TransformOutputProvider) {
        println("jar: ${jarInput.file.path} ${jarInput.file.isFile}")
        var jarName = jarInput.name
        val md5Name = DigestUtils.md5Hex(jarName)
        if (jarName.endsWith(".jar"))
            jarName = jarName.substring(0, jarName.length - 4)
        val out = outputProvider.getContentLocation("$jarName$md5Name", jarInput.contentTypes, jarInput.scopes, Format.JAR)
//        println("jar: out = $out")
        FileUtils.copyFile(jarInput.file, out)
    }
}