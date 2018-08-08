package com.qs.game.base.baseentity;

import com.alibaba.fastjson.JSON;
import com.qs.game.enum0.Code;
import com.qs.game.enum0.LoggerEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by zun.wei on 2018/8/3 11:19.
 * Description: 返回值统一封装类
 */
@Data
@Accessors(chain = true)//链式的操作方式
@ApiModel(description= "基本结果返回值对象")
public class BaseResult<T> implements Serializable {

    @ApiModelProperty(value = "返回结果，成功或者失败")
    private Boolean success;

    @ApiModelProperty(value = "返回内容")
    private T content;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "消息代码")
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
