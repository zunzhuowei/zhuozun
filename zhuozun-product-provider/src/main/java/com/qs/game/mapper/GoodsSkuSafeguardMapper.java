package com.qs.game.mapper;

import com.qs.game.base.basemapper.IBaseMapper;
import com.qs.game.model.GoodsSkuSafeguard;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsSkuSafeguardMapper extends IBaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodsSkuSafeguard record);

    int insertSelective(GoodsSkuSafeguard record);

    GoodsSkuSafeguard selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsSkuSafeguard record);

    int updateByPrimaryKey(GoodsSkuSafeguard record);
}