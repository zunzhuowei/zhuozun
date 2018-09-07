package com.qs.game.mapper.game;

import com.qs.game.base.basemapper.IBaseMapper;
import com.qs.game.model.game.UserKunBuyLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserKunBuyLogMapper extends IBaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserKunBuyLog record);

    int insertSelective(UserKunBuyLog record);

    UserKunBuyLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserKunBuyLog record);

    int updateByPrimaryKey(UserKunBuyLog record);
}