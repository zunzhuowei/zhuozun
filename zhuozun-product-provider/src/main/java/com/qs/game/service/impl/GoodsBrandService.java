package com.qs.game.service.impl;

import com.qs.game.base.basemapper.IBaseMapper;
import com.qs.game.base.baseservice.AbstractBaseService;
import com.qs.game.mapper.product.GoodsBrandMapper;
import com.qs.game.model.product.GoodsBrand;
import com.qs.game.service.IGoodsBrandService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zun.wei on 2018/8/5.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 * 品牌表业务层接口 实现
 */
@Service
public class GoodsBrandService  extends AbstractBaseService<GoodsBrand,Long> implements IGoodsBrandService {

    @Resource
    private GoodsBrandMapper goodsBrandMapper;

    @Override
    @Resource(type = GoodsBrandMapper.class)
    public void setMapper(IBaseMapper<GoodsBrand, Long> mapper) {
        super.mapper = mapper;
    }


}
