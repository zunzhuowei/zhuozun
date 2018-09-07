package com.qs.game.mapper.game;

import com.qs.game.base.basemapper.IBaseMapper;
import com.qs.game.model.game.UserKunPool;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserKunPoolMapper extends IBaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserKunPool record);

    int insertSelective(UserKunPool record);

    UserKunPool selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserKunPool record);

    int updateByPrimaryKey(UserKunPool record);
}