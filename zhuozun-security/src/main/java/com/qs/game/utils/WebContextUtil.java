package com.qs.game.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Created by zun.wei on 2018/8/14 12:39.
 * Description:  Web上下文工具类
 */
public class WebContextUtil {

    /**
     * @return HttpServletRequest
     * @description 获取HTTP请求
     * @author rico
     * @created 2017年7月4日 下午5:18:08
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                .getRequestAttributes())).getRequest();
    }

}
