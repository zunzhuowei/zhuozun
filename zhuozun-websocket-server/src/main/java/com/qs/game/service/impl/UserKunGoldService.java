package com.qs.game.service.impl;

import com.qs.game.base.basemapper.IBaseMapper;
import com.qs.game.base.baseservice.AbstractBaseService;
import com.qs.game.mapper.game.UserKunGoldMapper;
import com.qs.game.model.game.UserKunGold;
import com.qs.game.service.IUserKunGoldService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by zun.wei on 2018/9/8.
 * 用户养鲲金币表业务接口实现类
 */
@Slf4j
@Service
public class UserKunGoldService extends AbstractBaseService<UserKunGold, Long> implements IUserKunGoldService {

    @Resource
    private UserKunGoldMapper userKunGoldMapper;

    @Resource(type = UserKunGoldMapper.class)
    public void setMapper(IBaseMapper<UserKunGold, Long> mapper) {
        log.info("---------------:: inject userKunGoldMapper to super!!");
        super.mapper = mapper;
    }


}
