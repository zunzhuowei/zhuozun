package com.qs.game.mapper.product;

import com.qs.game.base.basemapper.IBaseMapper;
import com.qs.game.model.product.GoodsSpuSpec;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsSpuSpecMapper extends IBaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodsSpuSpec record);

    int insertSelective(GoodsSpuSpec record);

    GoodsSpuSpec selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsSpuSpec record);

    int updateByPrimaryKey(GoodsSpuSpec record);
}