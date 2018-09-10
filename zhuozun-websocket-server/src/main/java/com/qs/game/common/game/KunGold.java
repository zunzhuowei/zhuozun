package com.qs.game.common.game;

/**
 *  鲲的金币常量
 */
public interface KunGold {


    static long goldByType(int kunType) {
        switch (kunType) {
            case KunType.TYPE_1:
                return 10;
            case KunType.TYPE_2:
                return 20;
            case KunType.TYPE_3:
                return 40;
            case KunType.TYPE_4:
                return 80;
            case KunType.TYPE_5:
                return 160;
            case KunType.TYPE_6:
                return 32;
            case KunType.TYPE_7:
                return 64;
            case KunType.TYPE_8:
                return 125;
            case KunType.TYPE_9:
                return 256;
            case KunType.TYPE_10:
                return 512;
            default:
                return 0;
        }
    }

}
