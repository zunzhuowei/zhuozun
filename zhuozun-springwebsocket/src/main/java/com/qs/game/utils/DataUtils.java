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
    public static byte[] longToBytes(long x) {
        return ByteBuffer.allocate(8).putLong(x).array();
    }

    public static byte[] intToBytes(int x) {
        return ByteBuffer.allocate(4).putInt(x).array();
    }

    public static byte[] shortToBytes(short x) {
        return ByteBuffer.allocate(2).putShort(x).array();
    }

    public static byte[] charToBytes(char x) {
        return ByteBuffer.allocate(2).putChar(x).array();
    }

    public static byte[] byteToBytes(byte x) {
        return ByteBuffer.allocate(1).put(x).array();
    }





    public static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getLong();
    }

    public static int bytesToInt(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getInt();
    }

    public static short bytesToShort(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getShort();
    }

    public static char bytesToChar(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getChar();
    }

    public static String bytesToStr(byte[] bytes) {
        return new String(bytes);
    }






    public static long getLongByBuffer(ByteBuffer byteBuffer) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        byte[] arr = byteBuffer.array();
        int arrOff = byteBuffer.position();
        buffer.put(arr, arrOff, 8);
        buffer.flip();//need flip
        buffer.order(ByteOrder.LITTLE_ENDIAN);//低位在前
        byteBuffer.position(arrOff + 8);
        return buffer.getLong();
    }

    public static int getIntByBuffer(ByteBuffer byteBuffer) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        byte[] arr = byteBuffer.array();
        int arrOff = byteBuffer.position();
        buffer.put(arr, arrOff, 4);
        buffer.flip();//need flip
        buffer.order(ByteOrder.LITTLE_ENDIAN);//低位在前
        byteBuffer.position(arrOff + 4);
        return buffer.getInt();
    }

    public static short getShortByBuffer(ByteBuffer byteBuffer) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        byte[] arr = byteBuffer.array();
        int arrOff = byteBuffer.position();
        buffer.put(arr, arrOff, 2);
        buffer.flip();//need flip
        buffer.order(ByteOrder.LITTLE_ENDIAN);//低位在前
        byteBuffer.position(arrOff + 2);
        return buffer.getShort();
    }

    public static char getCharByBuffer(ByteBuffer byteBuffer) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        byte[] arr = byteBuffer.array();
        int arrOff = byteBuffer.position();
        buffer.put(arr, arrOff, 2);
        buffer.flip();//need flip
        buffer.order(ByteOrder.LITTLE_ENDIAN);//低位在前
        byteBuffer.position(arrOff + 2);
        return buffer.getChar();
    }

    public static byte getByteByBuffer(ByteBuffer byteBuffer) {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        byte[] arr = byteBuffer.array();
        int arrOff = byteBuffer.position();
        buffer.put(arr, arrOff, 1);
        buffer.flip();//need flip
        buffer.order(ByteOrder.LITTLE_ENDIAN);//低位在前
        byteBuffer.position(arrOff + 1);
        return buffer.get();
    }

    public static String getStrByBuffer(ByteBuffer byteBuffer, int strLen) {
        ByteBuffer buffer = ByteBuffer.allocate(strLen);
        byte[] arr = byteBuffer.array();
        int arrOff = byteBuffer.position();
        buffer.put(arr, arrOff, strLen);
        buffer.flip();//need flip
        byteBuffer.position(arrOff + strLen);
        return new String(buffer.array());
    }

}
