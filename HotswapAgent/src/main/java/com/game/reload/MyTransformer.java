package com.game.reload;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
public class MyTransformer implements ClassFileTransformer {
    public MyTransformer() {
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            if (null != className && className.indexOf("com/game") > -1) {
                // System.out.println("className:" + className + " size:" + classfileBuffer.length);
                className += ".class";
                JavaAgent.saveClassSize(className, classfileBuffer.length);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return classfileBuffer;
    }
}
