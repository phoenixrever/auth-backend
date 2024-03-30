package com.phoenixhell.authbackend.config;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis json 格式配置类
 */
@Configuration
public class RedisConfig {

    //@Bean
    //public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
    //    RedisTemplate<Object, Object> template = new RedisTemplate<>();
    //    template.setConnectionFactory(connectionFactory);
    //    template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
    //
    //
    //    template.setKeySerializer(new StringRedisSerializer());
    //    template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    //
    //    template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
    //    template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
    //
    //    return template;
    //}

    //上面写法参数autowired的方式  idea 会报错
    @Resource
    private RedisConnectionFactory connectionFactory;

    @Bean
    public RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        //默认情况下，RedisTemplate使用JDK的序列化器（JdkSerializationRedisSerializer）来序列化Java对象
        //这个序列化器用于将Java对象序列化为JSON格式存储到Redis中，并在需要时将其反序列化回Java对象。
        //貌似下创建了的话可以省略不写
        //template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        //确保了在RedisTemplate对象被Spring容器正式使用之前，所有的必要设置都已经完成。
        template.afterPropertiesSet();
        return template;
    }
}
