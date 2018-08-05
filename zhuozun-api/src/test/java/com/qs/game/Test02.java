package com.qs.game;

import com.qs.game.lambda.test02.GreetingService;
import com.qs.game.lambda.test02.MathOperation;
import org.junit.Test;

/**
 * Created by zun.wei on 2018/8/1.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public class Test02 {


    @Test
    public void test01() {

        GreetingService greetingService = msg -> System.out.println(msg + "world");

        GreetingService greetingService2 = msg -> {
            msg += "   ";
            System.out.println(msg + "world");
        };

        greetingService.sayMessage("hello,");
        greetingService2.sayMessage("hello2");

    }

    @Test
    public void test02() {
        MathOperation mathOperation = (a, b) -> a + b;
        MathOperation mathOperation2 = (a, b) -> a * b;
        MathOperation mathOperation3 = (a, b) -> a / b;
        MathOperation mathOperation4 = (a, b) -> a - b;

        int a = mathOperation.operation(1, 1);
        int a1 = mathOperation2.operation(2, 3);
        int a2 = mathOperation3.operation(4, 5);
        int a3 = mathOperation4.operation(22, 1);

        System.out.printf("result a1,a2,a3,a4   %s,%s,%s,%s", a, a1, a2, a3);
    }

}
