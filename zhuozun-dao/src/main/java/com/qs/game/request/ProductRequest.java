package com.qs.game.request;

import com.qs.game.base.baseentity.BaseRequest;
import com.qs.game.model.product.GoodsBrand;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by zun.wei on 2018/8/5.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 *
 * 产品中心統一請求參數包裝類
 *
 */
@Data
@Accessors(chain = true)//链式的操作方式
public class ProductRequest extends BaseRequest {

    private GoodsBrand goodsBrand;

}
