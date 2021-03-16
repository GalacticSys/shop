package com.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2021/3/10 13:00
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class GoodsWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsWebApplication.class);
    }
}
