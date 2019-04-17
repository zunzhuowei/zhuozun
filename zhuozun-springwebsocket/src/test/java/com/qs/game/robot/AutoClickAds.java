package com.qs.game.robot;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.util.Random;

/**
 * Created by zun.wei on 2019/1/4.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public class AutoClickAds {

    private Robot robot;


    private volatile boolean stop = false;


    /**
     * Creates a new instance of Main
     */
    public AutoClickAds() {
        try {
            robot = new Robot();
        } catch (AWTException ex) {
            ex.printStackTrace();
        }
    }


    public void init() {
        robot.delay(3000);
        System.out.println("Click Ads start");

        // 在新的浏览器窗口或在已有的浏览器窗口打开指定的URL(JDK 1.6以上)
        Desktop desktop = Desktop.getDesktop();
        if (Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE)) {
            URI uri = URI.create("http://www.baidu.com");
            try {
                desktop.browse(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        stop();

        System.out.println("Click Ads stoped");
    }


    public void run() throws InterruptedException {
        int count = 1;

        while (!stop) {
            robot.delay(8000);

            int x = 576;
            int y = 567;
            Random r = new Random();


            Common.clickLMouse(robot, x, y, 3000);


            // 输入向下箭头，实现翻页
            int[] ks = {KeyEvent.VK_DOWN};
            for (int i = 0; i < 10; i++)
                Common.pressKeys(robot, ks, 0);


            int[][] a = {{500, 103}, {500, 163}, {500, 223},
                    {500, 283}, {500, 343}, {500, 403}, {500, 463},
                    {500, 523}, {500, 583}, {500, 643},};
            int b = r.nextInt(5);
            x = a[b][0];
            y = a[b][1];


            Common.clickLMouse(robot, x, y, 1000);


            // 输入向下箭头，实现翻页
            for (int i = 0; i < 500; i++)
                Common.pressKeys(robot, ks, 0);


            // 输入向下箭头，实现翻页
            int[] kups = {KeyEvent.VK_UP};
            for (int i = 0; i < 3; i++)
                Common.pressKeys(robot, kups, 0);


            x = 900;
            y = 210;
            Common.clickLMouse(robot, x, y, 3000);

            x = 1090;
            y = 15;
            Common.clickLMouse(robot, x, y, 3000);

            x = 900;
            y = 135;
            Common.clickLMouse(robot, x, y, 3000);


            System.out.println("成功点击第" + count + "个广告！");
        }


    }


    public synchronized void stop() {
        stop = true;
    }


    /**
     * * @param args the command line arguments
     *
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        AutoClickAds mc = new AutoClickAds();
        mc.init();
    }

}
