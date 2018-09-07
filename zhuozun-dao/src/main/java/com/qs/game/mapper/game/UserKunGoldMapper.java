package com.qs.game.mapper.game;

import com.qs.game.base.basemapper.IBaseMapper;
import com.qs.game.model.game.UserKunGold;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserKunGoldMapper extends IBaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserKunGold record);

    int insertSelective(UserKunGold record);

    UserKunGold selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserKunGold record);

    int updateByPrimaryKey(UserKunGold record);
}