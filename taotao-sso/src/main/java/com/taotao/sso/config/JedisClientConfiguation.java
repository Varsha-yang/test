package com.taotao.sso.config;

import com.taotao.sso.dao.impl.JedisClientSingle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class JedisClientConfiguation {

    @Bean(name = "jedisClient")
    public JedisClientSingle jedisClientSingle (){
        return new JedisClientSingle();
    }
}
