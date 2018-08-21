package com.qs.game.service.impl;

import com.qs.game.api.UserApi;
import com.qs.game.base.baseentity.BaseResult;
import com.qs.game.enum0.Code;
import com.qs.game.model.user.User;
import com.qs.game.service.IIndexService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * index service implements
 */
@Service
public class IndexServiceImpl implements IIndexService {

    @Resource
    private UserApi userApi;

    @Override
    public BaseResult login(User user) {
        if (Objects.isNull(user) || StringUtils.isBlank(user.getUsername()))
            return BaseResult.getBuilder().setMessage("user is null")
                    .setCode(Code.ERROR_1).setSuccess(false).build();
        return userApi.findUserByUserName(user.getUsername());
    }

    @Override
    public BaseResult register(User user) {
        return userApi.add(user);
    }


}
