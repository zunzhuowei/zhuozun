package com.qs.game.service.impl;

import com.qs.game.api.UserApi;
import com.qs.game.auth.TokenManager;
import com.qs.game.base.baseentity.BaseResult;
import com.qs.game.enum0.Code;
import com.qs.game.model.user.User;
import com.qs.game.service.IIndexService;
import com.qs.game.utils.Constants;
import com.qs.game.utils.WebContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
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
    public BaseResult login(User user) {
        if (Objects.isNull(user) || StringUtils.isBlank(user.getUsername()))
            return BaseResult.getBuilder().setMessage("user is null")
                    .setCode(Code.ERROR_1).setSuccess(false).build();
        System.out.println("user = " + user.getUsername());
        BaseResult baseResult = userApi.findUserByUserName(user.getUsername());
        System.out.println("baseResult = " + baseResult);
        if (baseResult.getSuccess()){
            String token = tokenManager.createToken(user.getUsername());
            WebContextUtil.getResponse()
                    .addCookie(new Cookie(Constants.DEFAULT_TOKEN_NAME,token));
        }
        return baseResult;
    }

    @Override
    public BaseResult register(User user) {
        return userApi.add(user);
    }


}
