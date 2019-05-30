package com.taotao.sso;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaotaoSsoApplication {
    @Value("${redis.server.port}")
    private static  String host;

    public static void main(String[] args) {

        System.out.println("&&"+ host );
        SpringApplication.run(TaotaoSsoApplication.class, args);
    }

}
