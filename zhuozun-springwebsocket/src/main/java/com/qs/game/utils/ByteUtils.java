package com.qs.game.utils;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Created by zun.wei on 2018/11/20 16:35.
 * Description: websocket 请求工具类
 */
@Data
@Accessors(chain = true)
public class ByteUtils implements Serializable {

    private byte[] connent;

    //java 合并两个byte数组
    public static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
        byte[] byte_3 = new byte[byte_1.length + byte_2.length];
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
        return byte_3;
    }

    private static int byteArrayToInt(byte[] b) {
        return b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    private static byte[] intToByteArray(int a) {
        return new byte[]{
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }

    private static byte[] charToByte(char c) {
        byte[] b = new byte[2];
        b[0] = (byte) ((c & 0xFF00) >> 8);
        b[1] = (byte) (c & 0xFF);
        return b;
    }

    private static char byteToChar(byte[] b) {
        return (char) (((b[0] & 0xFF) << 8) | (b[1] & 0xFF));
    }


    private static byte[] getBytes(short s, boolean asc) {
        byte[] buf = new byte[2];
        if (asc)
            for (int i = buf.length - 1; i >= 0; i--) {
                buf[i] = (byte) (s & 0x00ff);
                s >>= 8;
            }
        else
            for (int i = 0; i < buf.length; i++) {
                buf[i] = (byte) (s & 0x00ff);
                s >>= 8;
            }
        return buf;
    }

    private static byte[] getBytes(int s, boolean asc) {
        byte[] buf = new byte[4];
        if (asc)
            for (int i = buf.length - 1; i >= 0; i--) {
                buf[i] = (byte) (s & 0x000000ff);
                s >>= 8;
            }
        else
            for (int i = 0; i < buf.length; i++) {
                buf[i] = (byte) (s & 0x000000ff);
                s >>= 8;
            }
        return buf;
    }

    private static byte[] getBytes(long s, boolean asc) {
        byte[] buf = new byte[8];
        if (asc)
            for (int i = buf.length - 1; i >= 0; i--) {
                buf[i] = (byte) (s & 0x00000000000000ff);
                s >>= 8;
            }
        else
            for (int i = 0; i < buf.length; i++) {
                buf[i] = (byte) (s & 0x00000000000000ff);
                s >>= 8;
            }
        return buf;
    }

    private static short getShort(byte[] buf, boolean asc) {
        if (buf == null) {
            throw new IllegalArgumentException("byte array is null!");
        }
        if (buf.length > 2) {
            throw new IllegalArgumentException("byte array size > 2 !");
        }
        short r = 0;
        if (asc)
            for (int i = buf.length - 1; i >= 0; i--) {
                r <<= 8;
                r |= (buf[i] & 0x00ff);
            }
        else
            for (byte aBuf : buf) {
                r <<= 8;
                r |= (aBuf & 0x00ff);
            }
        return r;
    }

    private static int getInt(byte[] buf, boolean asc) {
        if (buf == null) {
            throw new IllegalArgumentException("byte array is null!");
        }
        if (buf.length > 4) {
            throw new IllegalArgumentException("byte array size > 4 !");
        }
        int r = 0;
        if (asc)
            for (int i = buf.length - 1; i >= 0; i--) {
                r <<= 8;
                r |= (buf[i] & 0x000000ff);
            }
        else
            for (byte aBuf : buf) {
                r <<= 8;
                r |= (aBuf & 0x000000ff);
            }
        return r;
    }

    private static long getLong(byte[] buf, boolean asc) {
        if (buf == null) {
            throw new IllegalArgumentException("byte array is null!");
        }
        if (buf.length > 8) {
            throw new IllegalArgumentException("byte array size > 8 !");
        }
        long r = 0;
        if (asc)
            for (int i = buf.length - 1; i >= 0; i--) {
                r <<= 8;
                r |= (buf[i] & 0x00000000000000ff);
            }
        else
            for (byte aBuf : buf) {
                r <<= 8;
                r |= (aBuf & 0x00000000000000ff);
            }
        return r;
    }

    private static ByteBuffer buffer = ByteBuffer.allocate(8);

    //byte 数组与 long 的相互转换
    private static byte[] longToBytes(long x) {
        buffer.putLong(0, x);
        return buffer.array();
    }

    /**
     * 将16位的short转换成byte数组
     */
    public static byte[] shortToByteArray(short s) {
        byte[] targets = new byte[2];
        for (int i = 0; i < 2; i++) {
            int offset = (targets.length - 1 - i) * 8;
            targets[i] = (byte) ((s >>> offset) & 0xff);
        }
        return targets;
    }


    public static ByteUtils beginBuild() {
        return new ByteUtils().setConnent(new byte[]{});
    }

/*
    public ByteUtils init(byte[] srcByte) {
        return this.setConnent(srcByte);
    }

    public ByteUtils init(char srcChar) {
        return this.setConnent(charToByte(srcChar));
    }

    public ByteUtils init(int srcInt) {
        return this.setConnent(intToByteArray(srcInt));
    }

    public ByteUtils init(short srcShort) {
        return this.setConnent(getBytes(srcShort, false));
    }

    public ByteUtils init(long srcLong) {
        return this.setConnent(getBytes(srcLong, false));
    }

    public ByteUtils init(String srcStr) {
        return this.setConnent(srcStr.getBytes());
    }
*/


    public ByteUtils append(String str) {
        return this.setConnent(byteMerger(this.getConnent(), str.getBytes()));
    }

    public ByteUtils append(long number) {
        return this.setConnent(byteMerger(this.getConnent(), longToBytes(number)));
    }

    public ByteUtils append(int number) {
        this.setConnent(byteMerger(this.getConnent(), intToByteArray(number)));
        return this;
    }

    public ByteUtils append(byte[] bytes) {
        return this.setConnent(byteMerger(this.getConnent(), bytes));
    }

    public ByteUtils append(short number) {
        return this.setConnent(byteMerger(this.getConnent(), shortToByteArray(number)));
    }

    public ByteUtils append(char chars) {
        return this.setConnent(byteMerger(this.getConnent(), charToByte(chars)));
    }

    public byte[] buildByteArr() {
        return this.getConnent();
    }

    public ByteBuffer buildBuffer() {
        return ByteBuffer.wrap(this.getConnent());
    }

    /**
     *  读取 ByteBuffer 中指定长度的字符串
     * @param byteBuffer socket 中的 byteBuffer
     * @param strLen 要读取字符串的长度
     * @return  读取到指定长度的字符串
     */
    public static String getStr(ByteBuffer byteBuffer, int strLen) {
        byte[] b = new byte[strLen];
        byteBuffer.get(b, 0, strLen);
        return new String(b);
    }


}
