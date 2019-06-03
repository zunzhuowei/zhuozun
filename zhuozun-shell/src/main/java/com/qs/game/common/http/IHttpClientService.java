package com.qs.game.common.http;

import java.util.Map;

/**
 * Created by zun.wei on 2018/8/15 15:07.
 * Description: http client interface
 */
public interface IHttpClientService {

    /**
     * 不带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     */
    HttpResult doGet(String url) throws Exception;

    /**
     * 带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     */
    HttpResult doGet(String url, Map<String, Object> map) throws Exception;

    /**
     * 带参数的post请求
     */
    HttpResult doPost(String url, Map<String, Object> map) throws Exception;

    /**
     * 不带参数post请求
     */
    HttpResult doPost(String url) throws Exception;


}
