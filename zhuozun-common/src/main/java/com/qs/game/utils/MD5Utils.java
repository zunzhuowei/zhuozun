package com.qs.game.utils;

import java.security.MessageDigest;

/**
 * Created by zun.wei on 2018/7/31.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 * <p>
 * MD5 utils base on jdk
 */
public class MD5Utils {

    // test
    public static void main(String[] args) {
        System.out.println(getMD5Code("你若安好，便是晴天"));
    }

    private MD5Utils() {
    }


    // md5加密
    public static String getMD5Code(String message) {
        String md5Str = "";
        try {
            //创建MD5算法消息摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            //生成的哈希值的字节数组
            byte[] md5Bytes = md.digest(message.getBytes());
            md5Str = bytes2Hex(md5Bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5Str;
    }

    // 2进制转16进制
    public static String bytes2Hex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        int temp;
        try {
            for (int i = 0; i < bytes.length; i++) {
                temp = bytes[i];
                if (temp < 0) {
                    temp += 256;
                }
                if (temp < 16) {
                    result.append("0");
                }
                result.append(Integer.toHexString(temp));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

}
