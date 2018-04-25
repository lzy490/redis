package com.example.redis.service;

import com.example.redis.model.RedisModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RedisService {

    @Autowired
    @Qualifier("jRedisTemplate")
    private RedisTemplate template;

    private Set<RedisModel> models = new HashSet<>();

    @Autowired
    private KeyGenerator keyGenerator;

    /**
     * @CachePut 确保方法被执行，方法的返回值放到缓存中（每次都会触发真实方法的调用）
     * @Cacheable 重复参数请求方法时，方法被略过，直接从缓存里面拿值
     * @CacheEvict 能够根据一定条件对缓存进行清空操作
     *
     * @Cacheable参数(value,key,condition),
     * value:缓存的名称，必须指定一个，可以指定多个@Cacheable(value={”cache1”,”cache2”})
     * key:缓存的key，可以为空，如果指定要按照SPEL表达式编写，如果不指定，则缺省按照方法的所有参数进行组合。可以自定义key生成器，使用keyGenerator指定。
     * condition：缓存的条件，SPEL表达式编写，为true则缓存。false不缓存。
     *
     * @CachePut和Cacheable参数一致
     *
     * @CacheEvict(value, key, condition, allEntries, beforeInvocation)
     * allEntries:是否清空所有缓存， 默认值为false， 为true则当方法执行完成清空所有缓存
     * beforeInvocation:是否在方法执行之前清空缓存，默认为false， 如果为true，则在方法执行之前清空缓存，在默认情况下，如果方法执行抛出异常，则不清空缓存
     *
     * @CacheConfig 所有@Cacheable都有一个value=XXX属性， 为了方便使用，使用@CacheConfig在类上面统一定义缓存名字，如果在方法上定义了缓存名字，一方法上的为准。
     *
     * @Caching 组合多个缓存注解使用
     * @Caching(put = {
     *          @CachePut(value = "user", key = "#user.id"),
     *          @CachePut(value = "user", key = "#user.username"),
     *          @CachePut(value = "user", key = "#user.email")
     *})
     * */

    @CachePut(value = "redisModel", key = "'RedisModel:' + #model.id")
    public RedisModel add(RedisModel model) {
        models.add(model);
        template.opsForValue().set("1213", model);
        return model;
    }

    @Cacheable(value = "redisModel", keyGenerator = "myKeyGenerator")
    public RedisModel get(String key) {
        System.out.println("============");
        RedisModel model = (RedisModel) template.opsForValue().get("1213");

        return model;
    }
}
