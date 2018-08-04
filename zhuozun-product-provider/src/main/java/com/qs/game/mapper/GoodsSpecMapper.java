package com.qs.game.mapper;

import com.qs.game.base.basemapper.IBaseMapper;
import com.qs.game.model.GoodsSpec;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsSpecMapper extends IBaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodsSpec record);

    int insertSelective(GoodsSpec record);

    GoodsSpec selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsSpec record);

    int updateByPrimaryKey(GoodsSpec record);
}