package com.qs.game.common;

/**
 * 鲲的类型常量
 */
public interface KunType {

    int TYPE_0 = 0;
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
                return KunType.TYPE_10;
            default:
                return KunType.TYPE_0;
        }
    }

}
