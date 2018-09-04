package com.qs.game.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zun.wei on 2018/7/31.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 *
 * SHA1Utils base on jdk
 */
public class SHA1Utils {

/*    public static void main(String[] args) {
        System.out.println(getSha1("你若安好，便是晴天"));
    }*/

    public static String getSha1(String str) {
        if (null == str || 0 == str.length()) {
            return null;
        }
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            //创建SHA1算法消息摘要对象
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            //使用指定的字节数组更新摘要。
            mdTemp.update(str.getBytes("UTF-8"));
            //生成的哈希值的字节数组
            byte[] md = mdTemp.digest();
            //SHA1算法生成信息摘要关键过程
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "0";

    }

}
