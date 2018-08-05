package com.qs.game.mapper.product;

import com.qs.game.base.basemapper.IBaseMapper;
import com.qs.game.model.product.GoodsSkuSpecValue;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsSkuSpecValueMapper extends IBaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodsSkuSpecValue record);

    int insertSelective(GoodsSkuSpecValue record);

    GoodsSkuSpecValue selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsSkuSpecValue record);

    int updateByPrimaryKey(GoodsSkuSpecValue record);
}