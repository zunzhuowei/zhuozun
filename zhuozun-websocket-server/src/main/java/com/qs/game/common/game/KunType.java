package com.qs.game.common.game;

/**
 * 鲲的类型常量
 */
public interface KunType {

    int TYPE_0 = 0;//类型为0 表示不存在

    int TYPE_1 = 1;
    int TYPE_2 = 2;
    int TYPE_3 = 3;
    int TYPE_4 = 4;
    int TYPE_5 = 5;
    int TYPE_6 = 6;
    int TYPE_7 = 7;
    int TYPE_8 = 8;
    int TYPE_9 = 9;
    int TYPE_10 = 10;
    int TYPE_11 = 11;
    int TYPE_12 = 12;
    int TYPE_13 = 13;
    int TYPE_14 = 14;
    int TYPE_15 = 15;
    int TYPE_16 = 16;
    int TYPE_17 = 17;
    int TYPE_18 = 18;
    int TYPE_19 = 19;
    int TYPE_20 = 20;
    int TYPE_21 = 21;
    int TYPE_22 = 22;
    int TYPE_23 = 23;
    int TYPE_24 = 24;
    int TYPE_25 = 25;
    int TYPE_26 = 26;
    int TYPE_27 = 27;
    int TYPE_28 = 28;
    int TYPE_29 = 29;
    int TYPE_30 = 30;


    /**
     *  等级合成
     * @param kunType 原等级
     * @return 合成之后的等级
     */
    static int mergeType(int kunType) {
        switch (kunType) {
            case KunType.TYPE_1:
                return KunType.TYPE_2;
            case KunType.TYPE_2:
                return KunType.TYPE_3;
            case KunType.TYPE_3:
                return KunType.TYPE_4;
            case KunType.TYPE_4:
                return KunType.TYPE_5;
            case KunType.TYPE_5:
                return KunType.TYPE_6;
            case KunType.TYPE_6:
                return KunType.TYPE_7;
            case KunType.TYPE_7:
                return KunType.TYPE_8;
            case KunType.TYPE_8:
                return KunType.TYPE_9;
            case KunType.TYPE_9:
                return KunType.TYPE_10;
            case KunType.TYPE_10:
                return KunType.TYPE_11;
            case KunType.TYPE_11:
                return KunType.TYPE_12;
            case KunType.TYPE_12:
                return KunType.TYPE_13;
            case KunType.TYPE_13:
                return KunType.TYPE_14;
            case KunType.TYPE_14:
                return KunType.TYPE_15;
            case KunType.TYPE_15:
                return KunType.TYPE_16;
            case KunType.TYPE_16:
                return KunType.TYPE_17;
            case KunType.TYPE_17:
                return KunType.TYPE_18;
            case KunType.TYPE_18:
                return KunType.TYPE_19;
            case KunType.TYPE_19:
                return KunType.TYPE_20;
            case KunType.TYPE_20:
                return KunType.TYPE_21;
            case KunType.TYPE_21:
                return KunType.TYPE_22;
            case KunType.TYPE_22:
                return KunType.TYPE_23;
            case KunType.TYPE_23:
                return KunType.TYPE_24;
            case KunType.TYPE_24:
                return KunType.TYPE_25;
            case KunType.TYPE_25:
                return KunType.TYPE_26;
            case KunType.TYPE_26:
                return KunType.TYPE_27;
            case KunType.TYPE_27:
                return KunType.TYPE_28;
            case KunType.TYPE_28:
                return KunType.TYPE_29;
            case KunType.TYPE_29:
                return KunType.TYPE_30;
            default:
                return KunType.TYPE_0;
        }
    }

}
