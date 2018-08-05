package com.qs.game.model.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qs.game.constant.StrConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
@Data
@Accessors(chain = true)//链式的操作方式
@ApiModel(description= "品牌表")
public class GoodsBrand implements Serializable {

    @ApiModelProperty(value = "品牌id")
    private Long id;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "用户创建日期，系统时间")
    @JsonFormat(pattern = StrConst.YYYY_MM_DD_HH_MM_SS, timezone = StrConst.GMT8)
    @DateTimeFormat(pattern = StrConst.YYYY_MM_DD_HH_MM_SS)
    private Date gmtCreate;

    @ApiModelProperty(value = "最后修改日期，系统时间")
    @JsonFormat(pattern = StrConst.YYYY_MM_DD_HH_MM_SS, timezone = StrConst.GMT8)
    @DateTimeFormat(pattern = StrConst.YYYY_MM_DD_HH_MM_SS)
    private Date gmtUpdate;

}