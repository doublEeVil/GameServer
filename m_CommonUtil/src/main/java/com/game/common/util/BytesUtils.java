package com.game.common.util;

import io.netty.buffer.ByteBuf;
import org.apache.commons.beanutils.PropertyUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * 字节码与基本数据之间的转换
 */
public class BytesUtils {

    public static void writeIntArr(ByteBuf buf, int[] arr) {
        int len = arr.length;
        buf.writeShort(len);
        for (int i = 0; i < len; i++) {
            buf.writeInt(arr[i]);
        }
    }

    public static void writeCharArr(ByteBuf buf, char[] arr) {
        int len = arr.length;
        buf.writeShort(len);
        for (int i = 0; i < len; i++) {
            buf.writeChar(arr[i]);
        }
    }


    public static void writeShortArr(ByteBuf buf, short[] arr) {
        int len = arr.length;
        buf.writeShort(len);
        for (int i = 0; i < len; i++) {
            buf.writeShort(arr[i]);
        }
    }

    public static void writeBooleanArr(ByteBuf buf, boolean[] arr) {
        int len = arr.length;
        buf.writeShort(len);
        for (int i = 0; i < len; i++) {
            buf.writeBoolean(arr[i]);
        }
    }

    public static void writeString(ByteBuf buf, String arr) {
        buf.writeShort(arr.length());
        try {
            buf.writeBytes(arr.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void writeStringArr(ByteBuf buf, String[] arr) {
        int len = arr.length;
        buf.writeShort(len);
        for (int i = 0; i < len; i++) {
            writeString(buf, arr[i]);
        }
    }

    public static int[] readIntArr(ByteBuf buf) {
        short len = buf.readShort();
        int[] ret = new int[len];
        for (int i = 0; i < len; i++) {
            ret[i] = buf.readInt();
        }
        return ret;
    }

    public static char[] readCharArr(ByteBuf buf) {
        short len = buf.readShort();
        char[] ret = new char[len];
        for (int i = 0; i < len; i++) {
            ret[i] = buf.readChar();
        }
        return ret;
    }

    public static short[] readShortArr(ByteBuf buf) {
        short len = buf.readShort();
        short[] ret = new short[len];
        for (int i = 0; i < len; i++) {
            ret[i] = buf.readShort();
        }
        return ret;
    }

    public static boolean[] readBooleanArr(ByteBuf buf) {
        short len = buf.readShort();
        boolean[] ret = new boolean[len];
        for (int i = 0; i < len; i++) {
            ret[i] = buf.readBoolean();
        }
        return ret;
    }

    public static String readString(ByteBuf buf) {
        short len = buf.readShort();
        byte[] data = new byte[len];
        buf.readBytes(data);
        try {
            return new String(data, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String[] readStringArr(ByteBuf buf) {
        short len = buf.readShort();
        String[] ret = new String[len];
        for (int i = 0; i < len; i++) {
            ret[i] = readString(buf);
        }
        return ret;
    }

    public static Object getValue(Field f, ByteBuf buf) {
        switch (f.getType().getSimpleName()) {
            case "int":
                return buf.readInt();
            case "int[]":
                return readIntArr(buf);
            case "char":
                return buf.readChar();
            case "char[]":
                return readCharArr(buf);
            case "short":
                return buf.readShort();
            case "short[]":
                return readShortArr(buf);
            case "boolean":
                return buf.readBoolean();
            case "boolean[]":
                return readBooleanArr(buf);
            case "String":
                return readString(buf);
            case "String[]":
                return readStringArr(buf);
            default: return null;
        }
    }

    public static void setValue(Field f, Object val, ByteBuf buf) {
        switch (f.getType().getSimpleName()) {
            case "int":
                buf.writeInt((int)val);
                break;
            case "int[]":
                writeIntArr(buf, (int[]) val);
                break;
            case "char":
                buf.writeChar((char)val);
                break;
            case "char[]":
                writeCharArr(buf, (char[]) val);
                break;
            case "short":
                buf.writeShort((short)val);
                break;
            case "short[]":
                writeShortArr(buf, (short[]) val);
                break;
            case "boolean":
                buf.writeBoolean((boolean) val);
                break;
            case "boolean[]":
                writeBooleanArr(buf, (boolean[]) val);
                break;
            case "String":
                writeString(buf, (String) val);
                break;
            case "String[]":
                writeStringArr(buf, (String[]) val);
                break;
            default: return;
        }
    }

}
