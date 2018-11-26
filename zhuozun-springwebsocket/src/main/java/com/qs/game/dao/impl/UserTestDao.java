package com.qs.game.dao.impl;

import com.qs.game.dao.IUserTestDao;
import com.qs.game.model.communication.UserTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;


/**
 * Created by zun.wei on 2018/11/27.
 */
@Repository
public class UserTestDao implements IUserTestDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建对象
     *
     * @param user
     */
    @Override
    public void saveUser(UserTest user) {
        mongoTemplate.save(user);
    }

    /**
     * 根据用户名查询对象
     *
     * @param userName
     * @return
     */
    @Override
    public UserTest findUserByUserName(String userName) {
        Query query = new Query(Criteria.where("userName").is(userName));
        UserTest user = mongoTemplate.findOne(query, UserTest.class);
        return user;
    }

    /**
     * 更新对象
     *
     * @param user
     */
    @Override
    public void updateUser(UserTest user) {
        Query query = new Query(Criteria.where("id").is(user.getId()));
        Update update = new Update().set("userName", user.getUserName()).set("passWord", user.getPassWord());
        //更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query, update, UserTest.class);
        //更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,UserEntity.class);
    }

    /**
     * 删除对象
     *
     * @param id
     */
    @Override
    public void deleteUserById(Long id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, UserTest.class);
    }

}
