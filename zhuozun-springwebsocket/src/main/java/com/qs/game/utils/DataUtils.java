package com.qs.game.utils;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by zun.wei on 2018/12/4.
 */
public class DataUtils implements Serializable {

    //private static ByteBuffer buffer = ByteBuffer.allocate(8);

    //byte 数组与 long 的相互转换
    public static byte[] longToBytes(long x, boolean... isLowHigh) {
        if (isLowHigh.length > 0 && isLowHigh[0])
            return ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong(x).array();
        return ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN).putLong(x).array();
    }

    public static byte[] intToBytes(int x, boolean... isLowHigh) {
        if (isLowHigh.length > 0 && isLowHigh[0])
            return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(x).array();
        return ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(x).array();
    }

    public static byte[] shortToBytes(short x, boolean... isLowHigh) {
        if (isLowHigh.length > 0 && isLowHigh[0])
            return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(x).array();
        return ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).putShort(x).array();
    }

    public static byte[] charToBytes(char x, boolean... isLowHigh) {
        if (isLowHigh.length > 0 && isLowHigh[0])
            return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putChar(x).array();
        return ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).putChar(x).array();
    }

    public static byte[] byteToBytes(byte x, boolean... isLowHigh) {
        if (isLowHigh.length > 0 && isLowHigh[0])
            return ByteBuffer.allocate(1).order(ByteOrder.LITTLE_ENDIAN).put(x).array();
        return ByteBuffer.allocate(1).order(ByteOrder.BIG_ENDIAN).put(x).array();
    }





    public static long bytesToLong(byte[] bytes, boolean... isLowHigh) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(bytes, 0, bytes.length);
        if (isLowHigh.length > 0 && isLowHigh[0]) {
            buffer.order(ByteOrder.LITTLE_ENDIAN);
        } else {
            buffer.order(ByteOrder.BIG_ENDIAN);
        }
        buffer.flip();//need flip
        return buffer.getLong();
    }

    public static int bytesToInt(byte[] bytes, boolean... isLowHigh) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put(bytes, 0, bytes.length);
        if (isLowHigh.length > 0 && isLowHigh[0]) {
            buffer.order(ByteOrder.LITTLE_ENDIAN);
        } else {
            buffer.order(ByteOrder.BIG_ENDIAN);
        }
        buffer.flip();//need flip
        return buffer.getInt();
    }

    public static short bytesToShort(byte[] bytes, boolean... isLowHigh) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.put(bytes, 0, bytes.length);
        if (isLowHigh.length > 0 && isLowHigh[0]) {
            buffer.order(ByteOrder.LITTLE_ENDIAN);
        } else {
            buffer.order(ByteOrder.BIG_ENDIAN);
        }
        buffer.flip();//need flip
        return buffer.getShort();
    }

    public static char bytesToChar(byte[] bytes, boolean... isLowHigh) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.put(bytes, 0, bytes.length);
        if (isLowHigh.length > 0 && isLowHigh[0]) {
            buffer.order(ByteOrder.LITTLE_ENDIAN);
        } else {
            buffer.order(ByteOrder.BIG_ENDIAN);
        }
        buffer.flip();//need flip
        return buffer.getChar();
    }

    public static String bytesToStr(byte[] bytes) {
        return new String(bytes);
    }






    public static long getLongByBuffer(ByteBuffer byteBuffer, boolean... isLowHigh) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        byte[] arr = byteBuffer.array();
        int arrOff = byteBuffer.position();
        buffer.put(arr, arrOff, 8);
        if (isLowHigh.length > 0 && isLowHigh[0]) {
            buffer.order(ByteOrder.LITTLE_ENDIAN);//低位在前
        } else {
            buffer.order(ByteOrder.BIG_ENDIAN);
        }
        buffer.flip();//need flip
        byteBuffer.position(arrOff + 8);
        return buffer.getLong();
    }

    public static int getIntByBuffer(ByteBuffer byteBuffer, boolean... isLowHigh) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        byte[] arr = byteBuffer.array();
        int arrOff = byteBuffer.position();
        buffer.put(arr, arrOff, 4);
        if (isLowHigh.length > 0 && isLowHigh[0]) {
            buffer.order(ByteOrder.LITTLE_ENDIAN);//低位在前
        } else {
            buffer.order(ByteOrder.BIG_ENDIAN);
        }
        buffer.flip();//need flip
        byteBuffer.position(arrOff + 4);
        return buffer.getInt();
    }

    public static short getShortByBuffer(ByteBuffer byteBuffer, boolean... isLowHigh) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        byte[] arr = byteBuffer.array();
        int arrOff = byteBuffer.position();
        buffer.put(arr, arrOff, 2);
        if (isLowHigh.length > 0 && isLowHigh[0]) {
            buffer.order(ByteOrder.LITTLE_ENDIAN);//低位在前
        } else {
            buffer.order(ByteOrder.BIG_ENDIAN);
        }
        buffer.flip();//need flip
        byteBuffer.position(arrOff + 2);
        return buffer.getShort();
    }

    public static char getCharByBuffer(ByteBuffer byteBuffer, boolean... isLowHigh) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        byte[] arr = byteBuffer.array();
        int arrOff = byteBuffer.position();
        buffer.put(arr, arrOff, 2);
        if (isLowHigh.length > 0 && isLowHigh[0]) {
            buffer.order(ByteOrder.LITTLE_ENDIAN);//低位在前
        } else {
            buffer.order(ByteOrder.BIG_ENDIAN);
        }
        buffer.flip();//need flip
        byteBuffer.position(arrOff + 2);
        return buffer.getChar();
    }

    public static byte getByteByBuffer(ByteBuffer byteBuffer, boolean... isLowHigh) {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        byte[] arr = byteBuffer.array();
        int arrOff = byteBuffer.position();
        buffer.put(arr, arrOff, 1);
        if (isLowHigh.length > 0 && isLowHigh[0]) {
            buffer.order(ByteOrder.LITTLE_ENDIAN);//低位在前
        } else {
            buffer.order(ByteOrder.BIG_ENDIAN);
        }
        buffer.flip();//need flip
        byteBuffer.position(arrOff + 1);
        return buffer.get();
    }

    public static String getStrByBuffer(ByteBuffer byteBuffer, int strLen, boolean... isLowHigh) {
        ByteBuffer buffer = ByteBuffer.allocate(strLen);
        byte[] arr = byteBuffer.array();
        int arrOff = byteBuffer.position();
        buffer.put(arr, arrOff, strLen);
        if (isLowHigh.length > 0 && isLowHigh[0]) {
            buffer.order(ByteOrder.LITTLE_ENDIAN);//低位在前
        } else {
            buffer.order(ByteOrder.BIG_ENDIAN);
        }
        buffer.flip();//need flip
        byteBuffer.position(arrOff + strLen);
        return new String(buffer.array());
    }

}
