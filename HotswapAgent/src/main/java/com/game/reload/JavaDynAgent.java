package com.game.reload;
import java.io.File;
import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;
public class JavaDynAgent {
    private static Instrumentation instrumentation;
    private static List<File>      fildList = new ArrayList<>();

    public JavaDynAgent() {
    }

    public static void premain(String options, Instrumentation inst) {
        System.out.println("=========premain方法执行========");
        System.out.println(options);
        System.out.println(JavaAgent.class.getClassLoader().getResource(""));
        String[] paths = JavaAgent.class.getClassLoader().getResource("").getPath().split("/");
        String[] files = options.split("#");
        for (String fileName : files) {
            int num = getPNumbers(fileName);
            fileName = fileName.replaceAll("\\../", "");
            String filePath = "";
            for (int i = 0; i < paths.length - num; i++) {
                filePath += paths[i];
                filePath += "/";
            }
            filePath += fileName;
            System.out.println("add jarfile:" + filePath);
            fildList.add(new File(filePath));
        }
        if (instrumentation != null) {
            return;
        }
        instrumentation = inst;
        inst.addTransformer(new MyTransformer());
        ConfigService cs = new ConfigService(fildList.toArray(new File[fildList.size()]));
        cs.start();
    }

    public static void agentmain(String options, Instrumentation inst) {
        System.out.println("=========agentmain方法执行========");
        System.out.println(options);
        String[] paths = JavaAgent.class.getClassLoader().getResource("").getPath().split("/");
        String[] files = options.split("#");
        for (String fileName : files) {
            int num = getPNumbers(fileName);
            fileName = fileName.replaceAll("\\../", "");
            String filePath = "";
            for (int i = 0; i < paths.length - num; i++) {
                filePath += paths[i];
                filePath += "/";
            }
            filePath += fileName;
            System.out.println("add jarfile:" + filePath);
            fildList.add(new File(filePath));
        }
        if (instrumentation != null) {
            return;
        }
        instrumentation = inst;
        inst.addTransformer(new MyTransformer());
        ConfigService cs = new ConfigService(fildList.toArray(new File[fildList.size()]));
        cs.start();
    }

    public static Instrumentation getInstrumentation() {
        return instrumentation;
    }

    public static List<File> getFildList() {
        return fildList;
    }

    public static int getPNumbers(String str) {
        int counter = 0;
        if (str.indexOf("..") != -1) {
            counter++;
            counter += getPNumbers(str.substring(str.indexOf("..") + 2));
        }
        return counter;
    }
}
