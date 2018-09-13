package com.qs.game.common.game;

/**
 *  鲲的金币常量
 */
public interface KunGold {


    /**
     *  根据类型获取每秒产的金币额
     * @param kunType 鲲的类型
     * @return 每秒产出的金币额
     */
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
            case KunType.TYPE_11:
                return 110;
            case KunType.TYPE_12:
                return 120;
            case KunType.TYPE_13:
                return 130;
            case KunType.TYPE_14:
                return 140;
            case KunType.TYPE_15:
                return 150;
            case KunType.TYPE_16:
                return 160;
            case KunType.TYPE_17:
                return 170;
            case KunType.TYPE_18:
                return 180;
            case KunType.TYPE_19:
                return 190;
            case KunType.TYPE_20:
                return 200;
            case KunType.TYPE_21:
                return 210;
            case KunType.TYPE_22:
                return 220;
            case KunType.TYPE_23:
                return 230;
            case KunType.TYPE_24:
                return 240;
            case KunType.TYPE_25:
                return 250;
            case KunType.TYPE_26:
                return 260;
            case KunType.TYPE_27:
                return 270;
            case KunType.TYPE_28:
                return 280;
            case KunType.TYPE_29:
                return 290;
            case KunType.TYPE_30:
                return 300;
            default:
                return 0;
        }
    }

}
