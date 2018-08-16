package com.qs.game.fallback;

import com.qs.game.base.baseentity.BaseResult;
import com.qs.game.constant.StrConst;
import com.qs.game.enum0.Code;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Created by zun.wei on 2018/8/17.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 *
 *  zuul熔断抽象类
 *
 */
public abstract class AbstractFallbackProvider implements FallbackProvider {

    protected String serviceName;

    @Override
    public String getRoute() {
        return serviceName.toLowerCase();
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.OK.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.OK.getReasonPhrase();
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                String content = BaseResult.getBuilder()
                        .setCode(Code.ERROR_1000)
                        .setSuccess(false)
                        .setMessage(AbstractFallbackProvider.this.getRoute() + " not in service")
                        .setContent("")
                        .buildJsonStr();
                return new ByteArrayInputStream(content.getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                MediaType mt = new MediaType(MediaType.APPLICATION_JSON, Charset.forName(StrConst.UTF_8));
                headers.setContentType(mt);
                return headers;
            }
        };
    }

}
