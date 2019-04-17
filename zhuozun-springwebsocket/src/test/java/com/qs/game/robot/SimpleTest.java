package com.qs.game.robot;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;

/**
 * Created by zun.wei on 2019/1/4.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public class SimpleTest {


    public static void main(String[] args) throws Exception {


        final Robot rb = new Robot();


        new Thread() {
            public void run() {
                rb.delay(2000); // 模拟回车
                rb.keyPress(KeyEvent.VK_ENTER);
                rb.keyRelease(KeyEvent.VK_ENTER);
            }
        }.start();


        rb.delay(3000);


        // 设置开始菜单的大概位置
        int x = 40;
        int y = Toolkit.getDefaultToolkit().getScreenSize().height - 10; // 鼠标移动到开始菜单,
        rb.mouseMove(x, y);
        rb.delay(500);


        // 单击开始菜单
        Common.clickLMouse(rb, x, y, 500);

        rb.delay(1000);


        // 运行CMD命令cmd enter
        int[] ks = {KeyEvent.VK_C, KeyEvent.VK_M,
                KeyEvent.VK_D, KeyEvent.VK_ENTER,};
        Common.pressKeys(rb, ks, 500);
        rb.mouseMove(400, 400);
        rb.delay(500);


        // 运行DIR命令dir enter
        ks = new int[]{KeyEvent.VK_D, KeyEvent.VK_I, KeyEvent.VK_R,
                KeyEvent.VK_ENTER};
        Common.pressKeys(rb, ks, 500);
        rb.delay(1000);


        // 运行CLS命令cls enter
        ks = new int[]{KeyEvent.VK_C, KeyEvent.VK_L, KeyEvent.VK_S,
                KeyEvent.VK_ENTER};
        Common.pressKeys(rb, ks, 500);
        rb.delay(1000);


        // 运行EXIT命令exit enter
        ks = new int[]{KeyEvent.VK_E, KeyEvent.VK_X, KeyEvent.VK_I,
                KeyEvent.VK_T, KeyEvent.VK_ENTER};
        Common.pressKeys(rb, ks, 500);
        rb.delay(1000);


        // 右键测试
        x = Toolkit.getDefaultToolkit().getScreenSize().width - 50;
        Common.clickRMouse(rb, x, y, 500);


        new Thread() {
            public void run() {
                rb.delay(1000); // 回车
                rb.keyPress(KeyEvent.VK_ENTER);
                rb.keyRelease(KeyEvent.VK_ENTER);
            }
        }.start();


        JOptionPane.showMessageDialog(null, "演示完毕!");
    }

}
