package com.qs.game.constant;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * Created by zun.wei on 2018/12/10 15:38.
 * Description:
 */
@Slf4j
@Data
@Accessors(chain = true)
public class CMD implements Serializable {

    private static int[] cmds = new int[]{999, 1000, 1001, 1002, 1003};

    public static boolean existCmd(int cmd) {
        for (int i : cmds) {
            if (i == cmd) return true;
        }
        return false;
    }

    public static int[] getCmds() {
        return cmds;
    }

    public static int getLoginCmd() {
        return cmds[1];
    }



}
