package com.qs.game.net;

import com.qs.game.common.http.HttpResult;
import com.qs.game.common.http.IHttpClientService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zun.wei on 2019/6/3 14:07.
 * Description:
 */
@ShellComponent
public class HttpCommands {

    @Resource
    private IHttpClientService httpClientService;

    @ShellMethod(key = {"hGet","get"},value = "http get request.")
    public String httpGet(@ShellOption() String param1, @ShellOption("p2") String param2) throws Exception {

        Map<String, Object> requests = new HashMap<>();
        requests.put("aa", param1);
        requests.put("bb", param2);
        HttpResult httpResult = httpClientService.doGet("http://www.baidu.com", requests);
        System.out.println("httpResult = " + httpResult);
        return httpResult.toString();
    }

    @ShellMethod(key = {"hget"}, value = "http get request 2.")
    public String httpGet2(String url, @ShellOption(value = "p", defaultValue = "") String... params) throws Exception {
        Map<String, Object> requests = new HashMap<>();
        for (String param : params) {
            String[] ps = param.split("=");
            if (ps.length < 2) throw new RuntimeException("param must be key=value format!");
            requests.put(ps[0], ps[1]);
        }
        url = !url.startsWith("http://") && !url.startsWith("https://") ? "http://" + url : url;
        HttpResult httpResult = httpClientService.doGet(url, requests);
        return httpResult.toString();
    }

}
