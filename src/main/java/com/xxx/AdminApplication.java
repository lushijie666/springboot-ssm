package com.xxx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xxx.dao")
public class AdminApplication{

	public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}