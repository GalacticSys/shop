package com.hjt.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2021/3/14 23:00
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.hjt.user.mapper")
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class,args);
    }
}
