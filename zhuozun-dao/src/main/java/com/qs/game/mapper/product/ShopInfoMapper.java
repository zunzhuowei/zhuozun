package com.qs.game.mapper.product;

import com.qs.game.base.basemapper.IBaseMapper;
import com.qs.game.model.product.ShopInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShopInfoMapper extends IBaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShopInfo record);

    int insertSelective(ShopInfo record);

    ShopInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ShopInfo record);

    int updateByPrimaryKey(ShopInfo record);
}