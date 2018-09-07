package com.qs.game.mapper.game;

import com.qs.game.base.basemapper.IBaseMapper;
import com.qs.game.model.game.Kun;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KunMapper extends IBaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Kun record);

    int insertSelective(Kun record);

    Kun selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Kun record);

    int updateByPrimaryKey(Kun record);
}