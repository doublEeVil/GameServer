package com.game.reload;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;
/**
 * 所有配置都在这个类里管理。 当配置文件发生变化时会重新读取。
 * 
 * @author zengxc
 */
public class ConfigService implements Runnable {
    private Map<File, Long> fileMap = new HashMap<>();
    private Map<File, Long> timeMap = new HashMap<>();
    private Map<File, Long> chanMap = new HashMap<>();

    public void start() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        System.out.println(loader.toString());
        // 预加载所有class
        for (File file : fileMap.keySet()) {
            JarFile jarFile = null;
            try {
                jarFile = new JarFile(file);
                // 1.整理需要重定义的类
                List<String> classNames = JavaAgent.getClasssFromJarFile(jarFile);
                for (String className : classNames) {
                    String classPath = className.replace(".class", "").replace("/", ".");
                    loader.loadClass(classPath);
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
        Thread thread = new Thread(this);
        thread.setName("ConfigService-Thread");
        thread.start();
    }

    public ConfigService(File... jars) {
        for (File file : jars) {
            System.out.println("FileModified:" + file.getPath());
            long t = file.lastModified();
            fileMap.put(file, t);
            timeMap.put(file, t);
            chanMap.put(file, System.currentTimeMillis() - t);
        }
    }

    public void run() {
        while (true) {
            try {
                for (File file : fileMap.keySet()) {
                    if (isFileModified(file)) {
                        System.out.println("FileModified:" + file.getPath());
                        JavaAgent.javaAgent(file);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected boolean isFileModified(File file) {
        long t = file.lastModified();
        if (t != fileMap.get(file).longValue()) {
            if (t != timeMap.get(file).longValue()) {
                timeMap.put(file, t);
                chanMap.put(file, System.currentTimeMillis() - t);
            }
            // 10秒都为改变则更新jar
            if ((System.currentTimeMillis() - t) - chanMap.get(file) > 10000) {
                fileMap.put(file, t);
                return true;
            }
        }
        return false;
    }
}
