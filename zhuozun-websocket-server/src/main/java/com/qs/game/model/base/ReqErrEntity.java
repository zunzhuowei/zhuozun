package com.qs.game.model.base;

import com.qs.game.common.ERREnum;

import java.io.Serializable;
import java.util.Objects;

/**
 * 请求参数以及错误实体
 */
public class ReqErrEntity implements Serializable {


    private ReqEntity reqEntity;

    private ERREnum errEnum;

    private String uId;

    public ReqErrEntity(ERREnum errEnum, String uId, ReqEntity... reqEntities) {
        this.errEnum = errEnum;
        if (Objects.nonNull(reqEntities) && reqEntities.length > 0)
            this.reqEntity = reqEntities[0];
        this.uId = uId;
    }

    public ReqEntity getReqEntity() {
        return reqEntity;
    }

    public ERREnum getErrEnum() {
        return errEnum;
    }

    public String getuId() {
        return uId;
    }

}
