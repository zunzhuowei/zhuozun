package com.qs.game.service.impl;

import com.qs.game.base.basemapper.IBaseMapper;
import com.qs.game.base.baseservice.AbstractBaseService;
import com.qs.game.mapper.user.UserMapper;
import com.qs.game.model.user.User;
import com.qs.game.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 用户业务模块
 */
@Slf4j
@Transactional
@Service
public class UserService extends AbstractBaseService<User,Long> implements IUserService {


    @Resource
    private UserMapper userMapper;

    @Resource(type = UserMapper.class)
    public void setMapper(IBaseMapper<User, Long> mapper) {
        log.info("---------------:: inject userMapper to super!!");
        super.mapper = mapper;
    }

    @Override
    @Cacheable("userInfo")
    public List<User> queryListAll(Map parameter) {
        log.warn("get in user query list all method ---::");
        return super.queryListAll(parameter);
    }

    @Override
    @CacheEvict(value = "userInfo", allEntries = true)
    public int insertSelective(User record) {
        log.warn("get in user insert selective method ---::"+ record.toString());
        return super.insertSelective(record);
    }

    @Override
    public User queryBeanByUserName(String username) {
        return userMapper.queryBeanByUserName(username);
    }


    /**
    // 因为配置文件继承了CachingConfigurerSupport，所以没有指定key的话就是用默认KEY生成策略生成,我们这里指定了KEY
    @Cacheable(value = "userInfo", key = "'findUser' + #id", condition = "#id%2==0")
    // value属性指定Cache名称
    // 使用key属性自定义key
    // condition属性指定发生的条件(如上示例表示只有当user的id为偶数时才会进行缓存)
    @Override
    public UserDto findProperty(String id) {
        UserDto user = userDao.findUser(id);
        return user;
    }

    /**
     *
     * @CachePut也可以声明一个方法支持缓存功能。
     * 与@Cacheable不同的是使用@CachePut标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，
     * 而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。
     *
     */
    /**
    @CachePut("users")
    // 每次都会执行方法，并将结果存入指定的缓存中
    @Override
    public List<UserDto> mybatisQueryAll() {
        return userDao.selectAll();
    }

    @Override
    // @CacheEvict(value="propertyInfo",allEntries=true) 清空全部
    // 删除指定缓存
    @CacheEvict(value = "propertyInfo", key = "'findUser' + #id")
    // 其他属性
    // allEntries是boolean类型，表示是否需要清除缓存中的所有元素。默认为false，表示不需要。当指定了allEntries为true时，Spring Cache将忽略指定的key。
    // 清除操作默认是在对应方法成功执行之后触发的，即方法如果因为抛出异常而未能成功返回时也不会触发清除操作。
    // 使用beforeInvocation可以改变触发清除操作的时间，当我们指定该属性值为true时，Spring会在调用该方法之前清除缓存中的指定元素。
    public String cacheEvict(String id) {
        log.info("删除缓存" + id);
        return null;
    }

    /**
     * @Caching注解可以让我们在一个方法或者类上同时指定多个Spring Cache相关的注解。
     * 其拥有三个属性：cacheable、put和evict，分别用于指定@Cacheable、@CachePut和@CacheEvict。
     */

    /**
    @Caching(cacheable = @Cacheable("users"), evict = { @CacheEvict("cache2"),
            @CacheEvict(value = "cache3", allEntries = true) })
    public UserDto find(Integer id) {
        return null;
    }

     */
}
