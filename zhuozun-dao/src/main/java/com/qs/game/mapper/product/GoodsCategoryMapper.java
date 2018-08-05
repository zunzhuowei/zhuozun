package com.qs.game.mapper.product;

import com.qs.game.base.basemapper.IBaseMapper;
import com.qs.game.model.product.GoodsCategory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsCategoryMapper extends IBaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodsCategory record);

    int insertSelective(GoodsCategory record);

    GoodsCategory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsCategory record);

    int updateByPrimaryKey(GoodsCategory record);
}