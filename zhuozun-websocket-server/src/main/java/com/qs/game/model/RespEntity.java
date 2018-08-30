package com.qs.game.model;

import com.alibaba.fastjson.JSON;
import com.qs.game.common.CMD;
import com.qs.game.common.ERREnum;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 *  websocket 统一返回值格式
 */
@Data
@Slf4j
@Accessors(chain = true)//链式的操作方式
public class RespEntity<T> implements Serializable {


    private Integer cmd; //命令

    private Integer err; //错误代码

    private T content; //返回内容

    private String comment; //说明


    private RespEntity(){}

    public Integer getCmd() {
        return cmd;
    }

    public Integer getErr() {
        return err;
    }

    public T getContent() {
        return content;
    }

    public String getComment() {
        return comment;
    }


    public static class Builder {
        private RespEntity respEntity;

        public Builder setCmd(CMD cmd) {
            respEntity.cmd = cmd.VALUE;
            return this;
        }

        public Builder setCmd(Integer cmd) {
            respEntity.cmd = cmd;
            return this;
        }

        public Builder setErr(ERREnum errEnum) {
            respEntity.err = errEnum.CODE;
            if (StringUtils.isBlank(respEntity.comment))
                respEntity.comment = errEnum.MSG;
            return this;
        }

        public Builder setContent(Object content) {
            respEntity.content = content;
            return this;
        }

        public Builder setComment(String comment) {
            respEntity.comment = comment;
            return this;
        }


        //构建对象
        public RespEntity build() {
            String content = Objects.isNull(respEntity.content) ? null : JSON.toJSONString(respEntity.content, true);
            log.info("RespEntityv-----------::cmd:{},err:{},comment:{},\ncontent:\n{}",
                    respEntity.cmd, respEntity.err, respEntity.comment, content);
            return respEntity;
        }

        public String buildJsonStr() {
            String content = Objects.isNull(respEntity.content) ? null : JSON.toJSONString(respEntity.content, true);
            log.info("RespEntity-----------::cmd:{},err:{},comment:{},\ncontent:\n{}",
                    respEntity.cmd, respEntity.err, respEntity.comment, content);
            return JSON.toJSONString(respEntity);
        }



        private void setRespEntity(RespEntity respEntity) {
            this.respEntity = respEntity;
        }

    }

    //获取建造器
    public static Builder getBuilder() {
        Builder builder = new Builder();
        builder.setRespEntity(new RespEntity());
        return builder;
    }

}
