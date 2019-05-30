package com.taotao.sso.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfiguation {
    @Value("${redis.server.host}")
    private String host;
    @Value("${redis.server.port}")
    private int port;
    @Value("${redis.server.timeOut}")
    private int timeOut;
    @Value("${redis.server.password}")
    private String password;
    @Value("${redis.server.maxTotal}")
    private int maxTotal;
    @Value("${redis.server.maxIdle}")
    private int maxIdle;
    @Value("${redis.server.maxWaitMillis}")
    private long maxWaitMillis;

    @Bean(name = "jedisPoolConfig")
    public JedisPoolConfig  jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        return jedisPoolConfig;
    }
    @Bean(name = "jedisPool")
    public JedisPool jedisPool(@Qualifier("jedisPoolConfig") JedisPoolConfig jedisPoolConfig){
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,host,port,timeOut,password);
        return jedisPool;
    }
}
