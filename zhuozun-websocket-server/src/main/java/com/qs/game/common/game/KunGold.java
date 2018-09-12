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
                return 30;
            case KunType.TYPE_4:
                return 40;
            case KunType.TYPE_5:
                return 50;
            case KunType.TYPE_6:
                return 60;
            case KunType.TYPE_7:
                return 70;
            case KunType.TYPE_8:
                return 80;
            case KunType.TYPE_9:
                return 90;
            case KunType.TYPE_10:
                return 100;
            default:
                return 0;
        }
    }

}
