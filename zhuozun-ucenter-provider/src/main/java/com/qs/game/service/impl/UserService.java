package com.qs.game.service.impl;

import com.qs.game.mapper.UserMapper;
import com.qs.game.model.User;
import com.qs.game.service.IUserService;
import org.springframework.stereotype.Service;
import qs.game.base.basemapper.IBaseMapper;
import qs.game.base.baseservice.AbstractBaseService;
import qs.game.base.baseservice.IBaseService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 用户业务模块
 */
@Service
public class UserService implements IUserService {


    @Resource
    private UserMapper userMapper;


    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }

    @Override
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public User selectByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public List<User> queryListAll(Map<String, Object> parameter) {
        return userMapper.queryListAll(parameter);
    }

    @Override
    public List<User> queryListByPage(Map<String, Object> parameter) {
        return null;
    }

    @Override
    public int count(Map<String, Object> parameter) {
        return 0;
    }
}
