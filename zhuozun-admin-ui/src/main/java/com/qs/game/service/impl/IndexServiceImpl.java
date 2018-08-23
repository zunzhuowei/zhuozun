package com.qs.game.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qs.game.api.UserApi;
import com.qs.game.api.entity.UserRequest;
import com.qs.game.auth.JWTUtils;
import com.qs.game.auth.TokenManager;
import com.qs.game.base.baseentity.BaseResult;
import com.qs.game.constant.SecurityConstants;
import com.qs.game.enum0.Code;
import com.qs.game.model.user.User;
import com.qs.game.service.IIndexService;
import com.qs.game.utils.WebContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;
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

        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.convertValue(baseResult.getContent(), User.class);
        if (baseResult.getSuccess() && Objects.nonNull(user)) {//说明根据用户名查到了用户
            String token = WebContextUtil.getRequest().getHeader(SecurityConstants.DEFAULT_TOKEN_NAME);
            String password = userRequest.getPassword();

            Map<String, Object> result = new LinkedHashMap<>();
            result.put("user", user);
            //如果token校验成功，则直接登录成功；否则往下验证密码
            if (StringUtils.isNotBlank(token)) {
                Long userId = JWTUtils.getAppUID(token);
                if (Objects.equals(user.getId(), userId)) {
                    String newToken = JWTUtils.createToken(user.getId());
                    result.put(SecurityConstants.DEFAULT_TOKEN_NAME, newToken);
                    return BaseResult.getBuilder().setSuccess(true)
                            .setContent(result).setCode(Code.ERROR_0).build();
                }
                //TODO 返回用户信息的时候应该加密返回，等提交上来的时候拿原明文密码加密比较
            }

            //如果密码一样
            if (StringUtils.equals(user.getPassword(), password)) {
                String newToken = JWTUtils.createToken(user.getId());
                result.put(SecurityConstants.DEFAULT_TOKEN_NAME, newToken);
                return BaseResult.getBuilder().setSuccess(true)
                        .setContent(result).setCode(Code.ERROR_0).build();
            } else {
                return BaseResult.getBuilder().setSuccess(false).setCode(Code.ERROR_3).build();
            }
        } else {
            return BaseResult.getBuilder().setSuccess(false).setCode(Code.ERROR_3).build();
        }
    }

    @Override
    public BaseResult register(User user) {
        return userApi.add(user);
    }

}
