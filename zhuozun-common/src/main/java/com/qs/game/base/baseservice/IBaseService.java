package com.qs.game.base.baseservice;

import java.util.List;
import java.util.Map;

/**
 * Created by zun.wei on 2018/8/3.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public interface IBaseService<R, PK> {

    int insert(R record);

    int insertSelective(R record);

    int updateByPrimaryKeySelective(R record);

    int updateByPrimaryKey(R record);

    int deleteByPrimaryKey(PK id);

    R selectByPrimaryKey(PK id);

    List<R> queryListAll(Map<String, Object> parameter);

    List<R> queryListByPage(Map<String, Object> parameter);

    int count(Map<String, Object> parameter);

}
