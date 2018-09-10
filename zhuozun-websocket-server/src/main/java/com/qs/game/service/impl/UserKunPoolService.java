package com.qs.game.service.impl;

import com.qs.game.base.basemapper.IBaseMapper;
import com.qs.game.base.baseservice.AbstractBaseService;
import com.qs.game.mapper.game.UserKunPoolMapper;
import com.qs.game.model.game.UserKunPool;
import com.qs.game.service.IUserKunPoolService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zun.wei on 2018/9/10.
 * 玩家鲲池业务接口实现类
 */
@Service
public class UserKunPoolService extends AbstractBaseService<UserKunPool, Long> implements IUserKunPoolService {

    @Resource
    private UserKunPoolMapper userKunPoolMapper;

    @Resource(type = UserKunPoolMapper.class)
    public void setMapper(IBaseMapper<UserKunPool, Long> mapper) {
        super.mapper = mapper;
    }


    @Override
    public List<UserKunPool> queryListByMid(Integer mid) {
        return userKunPoolMapper.queryListByMid(mid);
    }



}
