package com.qs.game.service.impl;

import com.qs.game.base.basemapper.IBaseMapper;
import com.qs.game.base.baseservice.AbstractBaseService;
import com.qs.game.mapper.game.UserKunBuyLogMapper;
import com.qs.game.model.game.UserKunBuyLog;
import com.qs.game.service.IUserKunBuyLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zun.wei on 2018/9/12.
 *  用户购买鲲日志业务接口实现类
 */
@Service
public class UserKunBuyLogService extends AbstractBaseService<UserKunBuyLog,Long> implements IUserKunBuyLogService {


    @Resource
    private UserKunBuyLogMapper userKunBuyLogMapper;

    @Resource(type = UserKunBuyLogMapper.class)
    public void setMapper(IBaseMapper<UserKunBuyLog, Long> mapper) {
        super.mapper = mapper;
    }


}
