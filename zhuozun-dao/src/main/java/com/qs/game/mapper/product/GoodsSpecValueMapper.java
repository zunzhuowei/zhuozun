package com.qs.game.mapper.product;

import com.qs.game.base.basemapper.IBaseMapper;
import com.qs.game.model.product.GoodsSpecValue;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsSpecValueMapper extends IBaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodsSpecValue record);

    int insertSelective(GoodsSpecValue record);

    GoodsSpecValue selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsSpecValue record);

    int updateByPrimaryKey(GoodsSpecValue record);
}