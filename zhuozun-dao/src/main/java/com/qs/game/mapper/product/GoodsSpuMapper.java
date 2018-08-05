package com.qs.game.mapper.product;

import com.qs.game.base.basemapper.IBaseMapper;
import com.qs.game.model.product.GoodsSpu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsSpuMapper extends IBaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodsSpu record);

    int insertSelective(GoodsSpu record);

    GoodsSpu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsSpu record);

    int updateByPrimaryKey(GoodsSpu record);
}