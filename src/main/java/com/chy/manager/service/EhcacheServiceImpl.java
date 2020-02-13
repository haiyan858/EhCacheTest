package com.chy.manager.service;

import com.chy.manager.entity.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @Author cuihaiyan
 * @Create_Time 2020-02-13 22:03
 * @Description:
 */

@Service
public class EhcacheServiceImpl implements EhcacheService {

    // value的值和ehcache.xml中的配置保持一致
    @Cacheable(value = "HelloWorldCache", key = "#param")
    @Override
    public String getTimestamp(String param) {
        Long timestamp = System.currentTimeMillis();
        return timestamp.toString();
    }

    @Cacheable(value = "HelloWorldCache", key = "#key")
    @Override
    public String getDataFromDB(String key) {
        System.out.println("从数据库中获取数据...");
        return key + ":" + String.valueOf(Math.round(Math.random() * 1000000));
    }

    @CacheEvict(value = "HelloWorldCache", key = "#key")
    @Override
    public void removeDataAtDB(String key) {
        System.out.println("从数据库中删除数据");
    }

    @CachePut(value = "HelloWorldCache", key = "#key")
    @Override
    public String refreshData(String key) {
        System.out.println("模拟从数据库中加载数据");
        return key + "::" + String.valueOf(Math.round(Math.random() * 1000000));
    }


    // ------------------------------------------------------------------------

    //// 将缓存保存到名称为UserCache中，键为"user:"字符串加上userId值，如 'user:1'
    @Cacheable(value = "UserCache", key = "'user:' + #userId")
    public User findById(String userId) {
        System.out.println("模拟从数据库中查询数据");
        return (User) new User("1", "mengdee");
    }

    // 将缓存保存进UserCache中，并当参数userId的长度小于12时才保存进缓存，默认使用参数值及类型作为缓存的key
    // 保存缓存需要指定key，value， value的数据类型，不指定key默认和参数名一样如："1"
    //condition：触发条件，只有满足条件的情况才会加入缓存，默认为空，既表示全部都加入缓存，支持SpEL
    @Cacheable(value = "UserCache", condition = "#userId.length()<12")
    public boolean isReserved(String userId) {
        System.out.println("UserCache:" + userId);
        return false;
    }

    //@CacheEvict表明所修饰的方法是用来删除失效或无用的缓存数据。
    //@CacheEvict表明所修饰的方法是用来删除失效或无用的缓存数据。
    //@CacheEvict表明所修饰的方法是用来删除失效或无用的缓存数据。

    //清除掉UserCache中某个指定key的缓存
    @CacheEvict(value = "UserCache", key = "'user:' + #userId")
    public void removeUser(String userId) {
        System.out.println("UserCache remove:" + userId);
    }

    //清除掉UserCache中全部的缓存
    @CacheEvict(value = "UserCache", allEntries = true)
    public void removeAllUser() {
        System.out.println("UserCache delete all");
    }
}
