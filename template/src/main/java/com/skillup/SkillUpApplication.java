package com.skillup;

import org.jooq.meta.derby.sys.Sys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SkillUpApplication {
    public static void main(String[] args) {
        SpringApplication.run(SkillUpApplication.class, args);
        System.out.println("Welcome to Runze E-commerce platform");
    }
}