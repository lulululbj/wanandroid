package luyao.plugin.classfixer.asm

import luyao.autotrack.plugin.asm.AsmMethodVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class AsmClassVisitor(cv:ClassVisitor) : ClassVisitor(Opcodes.ASM5,cv) {

    private var className = ""
    private var superName = ""

    override fun visit(
        version: Int,
        access: Int,
        name: String,
        signature: String?,
        superName: String,
        interfaces: Array<out String>?
    ) {
        super.visit(version, access, name, signature, superName, interfaces)
        this.className = name
        this.superName = superName
    }

    override fun visitMethod(
        access: Int,
        name: String,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        println("ClassVisitor visitMethod $name , super name is $superName")
        val mv = cv.visitMethod(access, name, descriptor, signature, exceptions)
        if (superName == "androidx/appcompat/app/AppCompatActivity"){
            if (name.startsWith("onCreate"))
                return AsmMethodVisitor(mv,className,name)
        }
        return mv;
    }
}