package com.game.common.util;

import java.lang.reflect.Field;

public class PrintUtils {
    /**
     * 打印输出变量
     *
     * @param obj
     */
    public static void printVar(Object obj) {
        if (null == obj) {
            System.out.println("null");
            return;
        }
        Class<?> clazz = obj.getClass();
        Field[] fieldArr = clazz.getDeclaredFields();
        System.out.println("---------------------------------------------");
        try {
            for (Field field : fieldArr) {
                field.setAccessible(true);
                System.out.print("-----");
                if (field.getType().getSimpleName().equals("int")) {
                    System.out.println(field.getName() + " int :" + field.getInt(obj));
                } else if (field.getType().getSimpleName().equals("String")) {
                    System.out.println(field.getName() + " String :" + field.get(obj));
                } else if (field.getType().getSimpleName().equals("byte")) {
                    System.out.println(field.getName() + " byte :" + field.getByte(obj));
                } else if (field.getType().getSimpleName().equals("boolean")) {
                    System.out.println(field.getName() + " boolean :" + field.getBoolean(obj));
                } else if (field.getType().getSimpleName().equals("int[]")) {
                    System.out.print(field.getName() + " int[] :");
                    int[] arr = (int[]) field.get(obj);
                    for (int v : arr) {
                        System.out.print(" " + v);
                    }
                    System.out.println();
                } else if (field.getType().getSimpleName().equals("String[]")) {
                    System.out.print(field.getName() + " String[] :");
                    String[] arr = (String[]) field.get(obj);
                    for (String v : arr) {
                        System.out.print(" " + v);
                    }
                    System.out.println();
                } else if (field.getType().getSimpleName().equals("byte[]")) {
                    System.out.print(field.getName() + " byte[] :");
                    byte[] arr = (byte[]) field.get(obj);
                    for (byte v : arr) {
                        System.out.print(" " + v);
                    }
                    System.out.println();
                } else if (field.getType().getSimpleName().equals("boolean[]")) {
                    System.out.print(field.getName() + " boolean[] :");
                    boolean[] arr = (boolean[]) field.get(obj);
                    for (boolean v : arr) {
                        System.out.print(" " + v);
                    }
                    System.out.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("---------------------------------------------");
    }
}
