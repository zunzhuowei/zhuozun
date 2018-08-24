package com.qs.game.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qs.game.api.UserApi;
import com.qs.game.api.entity.UserRequest;
import com.qs.game.auth.JWTUtils;
import com.qs.game.auth.TokenManager;
import com.qs.game.base.baseentity.BaseResult;
import com.qs.game.constant.SecurityConstants;
import com.qs.game.constant.SystemConst;
import com.qs.game.enum0.Code;
import com.qs.game.model.user.User;
import com.qs.game.service.IIndexService;
import com.qs.game.utils.MD5Utils;
import com.qs.game.utils.WebContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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
            //如果token校验成功，校验token以及加密的密码
            if (StringUtils.isNotBlank(token) && !StringUtils.equals(token, "null")) {
                Long userId = JWTUtils.getAppUID(token);
                if (Objects.equals(user.getId(), userId)) {//token中id 与 查询到的id相同
                    Date nowDate = new Date();
                    Date expiresAt = JWTUtils.getExpiresAt(token);
                    assert expiresAt != null;
                    if (expiresAt.getTime() - nowDate.getTime() < 86400000 * 5) {//过期时间小于五天时重新生成token
                        String dbPassword = user.getPassword();
                        dbPassword = MD5Utils.getMD5Code(dbPassword);
                        if (StringUtils.equals(dbPassword, password)) {//密码相同
                            String newToken = JWTUtils.createToken(user.getId());
                            result.put(SecurityConstants.DEFAULT_TOKEN_NAME, newToken);
                            result.put(SystemConst.user, user.setPassword(dbPassword));
                            return BaseResult.getBuilder().setSuccess(true)
                                    .setContent(result).setCode(Code.ERROR_0).build();
                        } else {
                            return BaseResult.getBuilder().setSuccess(false).setCode(Code.ERROR_3).build();
                        }
                    } else {
                        String dbPassword = user.getPassword();
                        dbPassword = MD5Utils.getMD5Code(dbPassword);
                        if (StringUtils.equals(dbPassword, password)) {//密码相同
                            result.put(SecurityConstants.DEFAULT_TOKEN_NAME, token);
                            result.put(SystemConst.user, user.setPassword(dbPassword));
                            return BaseResult.getBuilder().setSuccess(true)
                                    .setContent(result).setCode(Code.ERROR_0).build();
                        } else {
                            //如果密码一样
                            return this.createNewToken(user, password, result);
                        }
                    }
                } else {
                    return BaseResult.getBuilder().setSuccess(false).setCode(Code.ERROR_3).build();
                }
            }

            //如果密码一样
            return this.createNewToken(user, password, result);
        } else {
            return BaseResult.getBuilder().setSuccess(false).setCode(Code.ERROR_3).build();
        }
    }

    //生成新token
    private BaseResult createNewToken(User user, String password, Map<String, Object> result) {
        if (StringUtils.equals(user.getPassword(), password)) {
            String newToken = JWTUtils.createToken(user.getId());
            result.put(SecurityConstants.DEFAULT_TOKEN_NAME, newToken);
            String dbPw = user.getPassword();
            dbPw = MD5Utils.getMD5Code(dbPw);
            result.put(SystemConst.user, user.setPassword(dbPw));
            return BaseResult.getBuilder().setSuccess(true)
                    .setContent(result).setCode(Code.ERROR_0).build();
        } else {
            return BaseResult.getBuilder().setSuccess(false).setCode(Code.ERROR_3).build();
        }
    }

    @Override
    public BaseResult register(User user) {
        return userApi.add(user);
    }

}
