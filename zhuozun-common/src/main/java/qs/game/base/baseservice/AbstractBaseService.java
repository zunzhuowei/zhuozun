package qs.game.base.baseservice;

import qs.game.base.basemapper.IBaseMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by zun.wei on 2018/8/3.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public abstract class AbstractBaseService<T,PK> implements IBaseService<T,PK> {


    protected IBaseMapper<T,PK> mapper;

    public void setService(IBaseMapper<T, PK> mapper) {
        this.mapper = mapper;
    }

    @Override
    public int insert(T record) {
        return mapper.insert(record);
    }

    @Override
    public int insertSelective(T record) {
        return mapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(T record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(T record) {
        return mapper.updateByPrimaryKey(record);
    }

    @Override
    public int deleteByPrimaryKey(PK id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public T selectByPrimaryKey(PK id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List queryListAll(Map parameter) {
        return mapper.queryListAll(parameter);
    }

    @Override
    public List queryListByPage(Map parameter) {
        return mapper.queryListByPage(parameter);
    }

    @Override
    public int count(Map parameter) {
        return mapper.count(parameter);
    }

}
