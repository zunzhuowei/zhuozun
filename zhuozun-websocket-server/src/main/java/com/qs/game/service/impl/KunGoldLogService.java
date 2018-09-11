package com.qs.game.service.impl;

import com.qs.game.base.basemapper.IBaseMapper;
import com.qs.game.base.baseservice.AbstractBaseService;
import com.qs.game.mapper.game.KunGoldLogMapper;
import com.qs.game.model.game.KunGoldLog;
import com.qs.game.service.IKunGoldLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zun.wei on 2018/9/12.
 *  用户金币流水日志业务接口实现类
 */
@Service
public class KunGoldLogService extends AbstractBaseService<KunGoldLog, Long> implements IKunGoldLogService {


    @Resource
    private KunGoldLogMapper kunGoldLogMapper;


    @Resource(type = KunGoldLogMapper.class)
    public void setMapper(IBaseMapper<KunGoldLog, Long> mapper) {
        super.mapper = mapper;
    }

}
