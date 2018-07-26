package com.aprilstore.util.Cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration  
public class RedisConfiguration {
    protected static Log logger = LogFactory.getLog(RedisConfiguration.class);

    @Bean(name= "jedis.pool")  
    @Autowired  
    public JedisPool jedisPool(@Qualifier("jedis.pool.config") JedisPoolConfig config,
                @Value("${jedis.pool.host}")String host,   
                @Value("${jedis.pool.port}")int port,@Value("${jedis.pool.password}")String password,@Value("${jedis.pool.timeout}")int timeout) {
        try {
            if (StringUtils.isEmpty(password)) {
                return new JedisPool(config, host, port);
            } else {
                return new JedisPool(config, host, port, timeout, password);
            }
        }
        catch (Exception e){
            logger.error(e.getMessage());

        }
        return null;
    }  
      
    @Bean(name= "jedis.pool.config")  
    public JedisPoolConfig jedisPoolConfig (@Value("${jedis.pool.config.maxTotal}")int maxTotal,  
                                @Value("${jedis.pool.config.maxIdle}")int maxIdle,  
                                @Value("${jedis.pool.config.maxWaitMillis}")int maxWaitMillis) {  
        JedisPoolConfig config = new JedisPoolConfig();  
        config.setMaxTotal(maxTotal);  
        config.setMaxIdle(maxIdle);  
        config.setMaxWaitMillis(maxWaitMillis);
        return config;  
    }  
      
}  