package com.game.reload;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassDefinition;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javassist.ClassPool;
import javassist.CtClass;
public class JavaAgent {
    public static Map<String, Integer> classSizeMap = new ConcurrentHashMap<>();

    /**
     * 重新加载类
     *
     * @param classArr
     * @throws Exception
     */
    public static void javaAgent(File file) {
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(file);
            // 1.整理需要重定义的类
            List<String> classNames = getClasssFromJarFile(jarFile);
            ClassPool classPool = new ClassPool();
            classPool.insertClassPath(file.getPath());
            for (String className : classNames) {
                try {
                    if (classSizeMap.containsKey(className)) {
                        String classPath = className.replace(".class", "").replace("/", ".");
                        Class<?> c = Class.forName(classPath);
                        CtClass ctClass = classPool.get(classPath);
                        byte[] bytesFromFile = ctClass.toBytecode();
                        if (classSizeMap.get(className).intValue() != bytesFromFile.length) {
                            System.out.println("class redefined:" + className);
                            ClassDefinition classDefinition = new ClassDefinition(c, bytesFromFile);
                            // redefine
                            JavaDynAgent.getInstrumentation().redefineClasses(classDefinition);
                        }
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (null != jarFile) {
                try {
                    jarFile.close();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static byte[] loadBytesFromStream(InputStream stream) {
        try {
            BufferedInputStream bis = new BufferedInputStream(stream);
            byte[] theData = new byte[10000000];
            int dataReadSoFar = 0;
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = bis.read(buffer)) != -1) {
                System.arraycopy(buffer, 0, theData, dataReadSoFar, read);
                dataReadSoFar += read;
            }
            bis.close();
            // Resize to actual data read
            byte[] returnData = new byte[dataReadSoFar];
            System.arraycopy(theData, 0, returnData, 0, dataReadSoFar);
            return returnData;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<String> getClasssFromJarFile(JarFile jarFile) {
        List<String> clazzs = new ArrayList<String>();
        Enumeration<JarEntry> ee = jarFile.entries();
        while (ee.hasMoreElements()) {
            JarEntry entry = (JarEntry) ee.nextElement();
            // 过滤我们出满足我们需求的东西
            if (entry.getName().endsWith(".class")) {
                clazzs.add(entry.getName());
            }
        }
        return clazzs;
    }

    public static void saveClassSize(String className, int size) {
        classSizeMap.put(className, size);
    }
}