package com.qs.game.http;

import com.qs.game.constant.StrConst;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by zun.wei on 2018/8/15 14:08.
 * Description: HttpClientService is base on http client build
 */
@Component("httpClientService")
public class HttpClientService implements IHttpClientService {


    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private RequestConfig config;


    @Override
    public HttpResult doGet(String url) throws Exception {
        // 声明 http get 请求
        HttpGet httpGet = new HttpGet(url);

        // 装载配置信息
        httpGet.setConfig(config);

        // 发起请求
        CloseableHttpResponse response = httpClient.execute(httpGet);

        // 判断状态码是否为200
        //if (response.getStatusLine().getStatusCode() == 200) {
        // 返回响应体的内容
        //    return EntityUtils.toString(response.getEntity(), StrConst.UTF_8);
        //}
        return new HttpResult().setStatusCode(response.getStatusLine().getStatusCode())
                .setResult(EntityUtils.toString(response.getEntity(), StrConst.UTF_8));
    }


    @Override
    public HttpResult doGet(String url, Map<String, Object> map) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(url);

        if (Objects.nonNull(map) && !map.isEmpty()) {
            // 遍历map,拼接请求参数
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }

        // 调用不带参数的get请求
        return this.doGet(uriBuilder.build().toString());

    }


    @Override
    public HttpResult doPost(String url, Map<String, Object> map) throws Exception {
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(config);

        // 判断map是否为空，不为空则进行遍历，封装from表单对象
        if (Objects.nonNull(map) && !map.isEmpty()) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            // 构造from表单对象
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, StrConst.UTF_8);

            // 把表单放到post里
            httpPost.setEntity(urlEncodedFormEntity);
        }

        // 发起请求
        CloseableHttpResponse response = httpClient.execute(httpPost);
        return new HttpResult().setStatusCode(response.getStatusLine().getStatusCode())
                .setResult(EntityUtils.toString(response.getEntity(), StrConst.UTF_8));
    }


    @Override
    public HttpResult doPost(String url) throws Exception {
        return this.doPost(url, null);
    }

}
