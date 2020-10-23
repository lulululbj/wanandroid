package luyao.plugin.classfixer.asm

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
//        println("ClassVisitor visit , super name is $superName, class name is $className")
    }

    override fun visitMethod(
        access: Int,
        name: String,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
//        println("ClassVisitor visitMethod $name , super name is $superName, class name is $className")
        val mv = cv.visitMethod(access, name, descriptor, signature, exceptions)
        if (className == "luyao/mvvm/core/base/BaseActivity" ||
                className == "luyao/mvvm/core/base/BaseVMActivity"){
            if (name.startsWith("onCreate"))
                return AsmMethodVisitor(mv,className,name)
        }
        return mv;
    }
}