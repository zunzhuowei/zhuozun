package com.qs.game.utils;

import java.util.Random;

/**
 * Created by zun.wei on 2018/9/4 13:27.
 * Description:
 */
public class AuthUtils {

    private static final char charr[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890$@#_".toCharArray();

    /**
     * @param len 获取的随机密码长度
     * @return String 密码
     */
    public static String createPassWord(int len) {
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int x = 0; x < len; ++x) {
            sb.append(charr[r.nextInt(charr.length)]);
        }
        return sb.toString();
    }



}
