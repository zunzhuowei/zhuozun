package qs.game.enum0;

import org.apache.commons.lang3.time.FastDateFormat;
import qs.game.constant.StrConst;

/**
 * Created by zun.wei on 2018/8/3 14:29.
 * Description: 日期格式枚举
 */
public enum DateEnum {

    YYYYMMDD(StrConst.YYYYMMDD),
    YYYY_MM_DD(StrConst.YYYY_MM_DD),
    YYYY_MM_DD_HH_MM(StrConst.YYYY_MM_DD_HH_MM),
    YYYYMMDDHHMMSS(StrConst.YYYYMMDDHHMMSS),
    YYYY_MM_DD_HH_MM_SS(StrConst.YYYY_MM_DD_HH_MM_SS),
    GMT8(StrConst.GMT8),
    ;


    public String value;


    DateEnum(String value) {
        this.value = value;
    }

    public FastDateFormat getDateFormat() {
        return FastDateFormat.getInstance(this.value);
    }

}
