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
                if (field.getType().getSimpleName().endsWith("[]")) {
                    // 数组类型
                    Object[] subobjs = (Object[]) field.get(obj);
                    System.out.print(field.getName() + " " + field.getType().getSimpleName());
                    for (Object subobj : subobjs) {
                        System.out.print(" " + subobj);
                    }
                    System.out.println();
                } else {
                    // 其他类型
                    System.out.println(field.getName() + " " + field.getType().getSimpleName() + ":" + field.get(obj));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("---------------------------------------------");
    }
}
