package com.qs.game.model;

import com.alibaba.fastjson.JSON;
import com.qs.game.enum0.Code;
import com.qs.game.enum0.LoggerEnum;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by zun.wei on 2018/8/3 11:19.
 * Description: 返回值统一封装类
 */
public class BaseResult<T> implements Serializable {

    private Boolean success;

    private T content;

    private String message;

    private int code;

    private BaseResult() {
    }


    public Boolean getSuccess() {
        return success;
    }

    public T getContent() {
        return content;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }


    public static class Builder {
        private BaseResult baseResult;

        public Builder setSuccess(Boolean success) {
            baseResult.success = success;
            return this;
        }

        public Builder setContent(Object content) {
            baseResult.content = content;
            return this;
        }

        public Builder setMessage(String message) {
            baseResult.message = message;
            return this;
        }

        public Builder setCode(Code code) {
            baseResult.code = code.err;
            if (StringUtils.isBlank(baseResult.message))
                baseResult.message = code.msg;
            return this;
        }


        //构建对象
        public BaseResult build() {
            String content = Objects.isNull(baseResult.content) ? null : JSON.toJSONString(baseResult.content,true);
            LoggerEnum.BaseResultLog.logger.info("BaseResult-----------::success:{},code:{},message:{},\ncontent:\n{}",
                    baseResult.success, baseResult.code, baseResult.message, content);
            return baseResult;
        }


        private void setBaseResult(BaseResult baseResult) {
            this.baseResult = baseResult;
        }

    }

    //获取建造器
    public static Builder getBuilder() {
        Builder builder = new Builder();
        builder.setBaseResult(new BaseResult());
        return builder;
    }

}
