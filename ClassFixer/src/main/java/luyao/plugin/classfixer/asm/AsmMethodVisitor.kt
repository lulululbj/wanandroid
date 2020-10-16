package luyao.autotrack.plugin.asm

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class AsmMethodVisitor(mv: MethodVisitor, val className: String, val methodName: String) :
    MethodVisitor(Opcodes.ASM5, mv) {


    override fun visitCode() {
        super.visitCode()
        println("MethodVisitor visitCode ===")

        mv.visitLdcInsn("TAG")
        mv.visitLdcInsn("$className --> $methodName")
        mv.visitMethodInsn(
            Opcodes.INVOKESTATIC,
            "android/util/Log",
            "i",
            "(Ljava/lang/String;Ljava/lang/String;)I",
            false
        )
        mv.visitInsn(Opcodes.POP)
    }

}
