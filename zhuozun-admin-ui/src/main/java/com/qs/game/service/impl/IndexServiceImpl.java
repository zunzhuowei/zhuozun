package com.qs.game.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qs.game.api.UserApi;
import com.qs.game.api.entity.UserRequest;
import com.qs.game.auth.TokenManager;
import com.qs.game.base.baseentity.BaseResult;
import com.qs.game.constant.IntConst;
import com.qs.game.constant.StrConst;
import com.qs.game.enum0.Code;
import com.qs.game.model.user.User;
import com.qs.game.service.IIndexService;
import com.qs.game.utils.Constants;
import com.qs.game.utils.WebContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;

/**
 * index service implements
 */
@Service
public class IndexServiceImpl implements IIndexService {

    @Resource
    private UserApi userApi;

    @Resource(name = "redisTokenManager")
    private TokenManager tokenManager;

    @Override
    public BaseResult login(UserRequest userRequest) {
        if (Objects.isNull(userRequest) || StringUtils.isBlank(userRequest.getUsername()))
            return BaseResult.getBuilder().setMessage("user is null").setCode(Code.ERROR_1).setSuccess(false).build();
        BaseResult baseResult = userApi.findUserByUserName(userRequest.getUsername());

        if (baseResult.getSuccess()){
            Cookie[] cookies = WebContextUtil.getRequest().getCookies();
            Cookie userToken = Arrays.stream(Objects.isNull(cookies) ? new Cookie[]{} : cookies)
                    .filter(e -> StringUtils.equals(e.getName(), Constants.DEFAULT_TOKEN_NAME))
                    .findFirst().orElse(null);
            if (Objects.nonNull(userToken)) {
                String value = userToken.getValue();
                String cookieName = tokenManager.checkTokenGetValue(value);
                if (StringUtils.equals(cookieName, userRequest.getUsername())) {
                    return baseResult;
                }
            }

            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.convertValue(baseResult.getContent(), User.class);
            String username = user.getUsername();
            String password = userRequest.getPassword();
            //如果密码一样
            if (StringUtils.equals(user.getPassword(), password)) {
                // 账号密码设置到cookie中
                HttpServletResponse response = WebContextUtil.getResponse();
                if (StringUtils.equals(StrConst.ON, userRequest.getRemember())) {
                    Cookie userCookie = new Cookie(Constants.USER_NAME, username);
                    userCookie.setMaxAge(IntConst.MONTH / 2);
                    response.addCookie(userCookie);
                    Cookie passCookie = new Cookie(Constants.PASSWORD, password);
                    passCookie.setMaxAge(IntConst.MONTH / 2);
                    response.addCookie(passCookie);
                } else {
                    //删除cookie
                    Cookie userCookie = new Cookie(Constants.USER_NAME, username);
                    userCookie. setMaxAge(0);
                    response.addCookie(userCookie);
                    Cookie passCookie = new Cookie(Constants.PASSWORD, password);
                    passCookie.setMaxAge(0);
                    response.addCookie(passCookie);
                }
                String token = tokenManager.createToken(username);
                Cookie cookie = new Cookie(Constants.DEFAULT_TOKEN_NAME, token);
                cookie.setMaxAge(IntConst.MONTH / 2);
                response.addCookie(cookie);
            } else {
                return BaseResult.getBuilder().setSuccess(false).setCode(Code.ERROR_3).build();
            }
        }
        return baseResult;
    }

    @Override
    public BaseResult register(User user) {
        return userApi.add(user);
    }


}
