package com.qs.game.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by zun.wei on 2018/8/15 14:20.
 * Description: httpClient 请求返回值
 */
@Data
@Accessors(chain = true)//链式的操作方式
@AllArgsConstructor
@NoArgsConstructor
public class HttpResult {

    private int statusCode;

    private String result;

}
