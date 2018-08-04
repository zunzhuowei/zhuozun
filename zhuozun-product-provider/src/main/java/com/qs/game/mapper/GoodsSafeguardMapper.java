package com.qs.game.mapper;

import com.qs.game.base.basemapper.IBaseMapper;
import com.qs.game.model.GoodsSafeguard;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsSafeguardMapper extends IBaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodsSafeguard record);

    int insertSelective(GoodsSafeguard record);

    GoodsSafeguard selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodsSafeguard record);

    int updateByPrimaryKey(GoodsSafeguard record);
}