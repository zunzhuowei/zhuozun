package com.qs.game.mapper;

import com.qs.game.model.User;
import org.apache.ibatis.annotations.Mapper;
import com.qs.game.base.basemapper.IBaseMapper;

@Mapper
public interface UserMapper extends IBaseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}