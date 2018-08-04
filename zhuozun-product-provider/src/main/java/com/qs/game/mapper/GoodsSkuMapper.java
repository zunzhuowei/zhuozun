package com.qs.game.mapper;

import com.qs.game.base.basemapper.IBaseMapper;
import com.qs.game.model.GoodsSku;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsSkuMapper extends IBaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodsSku record);

    int insertSelective(GoodsSku record);

    GoodsSku selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsSku record);

    int updateByPrimaryKey(GoodsSku record);
}