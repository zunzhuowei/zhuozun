package com.qs.game.request;

import com.qs.game.base.baseentity.BaseRequest;
import com.qs.game.model.product.GoodsBrand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by zun.wei on 2018/8/5.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 *
 * 产品中心統一請求參數包裝類
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)//链式的操作方式
@ApiModel(description= "产品请求对象")
public class ProductRequest extends BaseRequest {

    private GoodsBrand goodsBrand;

}
