package com.qs.game.service.impl;

import com.qs.game.base.basemapper.IBaseMapper;
import com.qs.game.base.baseservice.AbstractBaseService;
import com.qs.game.mapper.game.UserKunPoolMapper;
import com.qs.game.model.game.Kuns;
import com.qs.game.model.game.PoolCell;
import com.qs.game.model.game.UserKunPool;
import com.qs.game.service.IUserKunPoolService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * Created by zun.wei on 2018/9/10.
 * 玩家鲲池业务接口实现类
 */
@Service
public class UserKunPoolService extends AbstractBaseService<UserKunPool, Long> implements IUserKunPoolService {

    @Resource
    private UserKunPoolMapper userKunPoolMapper;

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    @Resource(type = UserKunPoolMapper.class)
    public void setMapper(IBaseMapper<UserKunPool, Long> mapper) {
        super.mapper = mapper;
    }


    @Override
    public List<UserKunPool> queryListByMid(Integer mid) {
        return userKunPoolMapper.queryListByMid(mid);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int insertBatch(String mid, List<PoolCell> poolCellList) {
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        UserKunPoolMapper userKunPoolMapper = sqlSession.getMapper(UserKunPoolMapper.class);
        Optional.ofNullable(poolCellList).ifPresent(e ->
                e.forEach(pc -> {
                    UserKunPool userKunPool = new UserKunPool();
                    userKunPool.setIsRun(pc.getKuns().getWork());
                    userKunPool.setType(pc.getKuns().getType());
                    userKunPool.setRunTime((int) pc.getKuns().getTime());
                    userKunPool.setPosition(pc.getNo());
                    userKunPool.setMid(Integer.valueOf(mid));
                    userKunPoolMapper.insertSelective(userKunPool);
                }));
        sqlSession.commit();
        sqlSession.close();
        return 0;
    }

}
