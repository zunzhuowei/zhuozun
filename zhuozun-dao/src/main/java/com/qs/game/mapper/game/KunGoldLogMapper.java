package com.qs.game.mapper.game;

import com.qs.game.base.basemapper.IBaseMapper;
import com.qs.game.model.game.KunGoldLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KunGoldLogMapper extends IBaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(KunGoldLog record);

    int insertSelective(KunGoldLog record);

    KunGoldLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(KunGoldLog record);

    int updateByPrimaryKey(KunGoldLog record);
}