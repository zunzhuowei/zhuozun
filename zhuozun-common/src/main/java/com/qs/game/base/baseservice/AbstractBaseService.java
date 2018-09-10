package com.qs.game.base.baseservice;

import com.qs.game.base.basemapper.IBaseMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by zun.wei on 2018/8/3.
 *
 * 基本业务层抽象类
 */
public abstract class AbstractBaseService<T,PK> implements IBaseService<T,PK> {


    protected IBaseMapper<T,PK> mapper;

    public abstract void setMapper(IBaseMapper<T, PK> mapper);

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int insert(T record) {
        return mapper.insert(record);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int insertSelective(T record) {
        return mapper.insertSelective(record);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateByPrimaryKeySelective(T record) {
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateByPrimaryKey(T record) {
        return mapper.updateByPrimaryKey(record);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteByPrimaryKey(PK id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public T selectByPrimaryKey(PK id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> queryListAll(Map parameter) {
        return mapper.queryListAll(parameter);
    }

    @Override
    public List<T> queryListByPage(Map parameter) {
        return mapper.queryListByPage(parameter);
    }

    @Override
    public int count(Map parameter) {
        return mapper.count(parameter);
    }

    /*
    只读事务（@Transactional(readOnly = true)）
        【注意是一次执行多次查询来统计某些信息，这时为了保证数据整体的一致性，要用只读事务】
     */
}
