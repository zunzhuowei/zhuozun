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

    public ReqErrEntity(ERREnum errEnum, ReqEntity... reqEntities) {
        this.errEnum = errEnum;
        if (Objects.nonNull(reqEntities) && reqEntities.length > 0)
            this.reqEntity = reqEntities[0];
    }

    public ReqEntity getReqEntity() {
        return reqEntity;
    }

    public ERREnum getErrEnum() {
        return errEnum;
    }

}
