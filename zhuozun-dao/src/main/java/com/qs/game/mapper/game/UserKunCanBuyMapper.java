package com.qs.game.mapper.game;

import com.qs.game.base.basemapper.IBaseMapper;
import com.qs.game.model.game.UserKunCanBuy;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserKunCanBuyMapper extends IBaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserKunCanBuy record);

    int insertSelective(UserKunCanBuy record);

    UserKunCanBuy selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserKunCanBuy record);

    int updateByPrimaryKey(UserKunCanBuy record);
}