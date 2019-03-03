package com.wuwei.gardemanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages={"com.wuwei.gardemanager.mapper"})
public class GardemanagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GardemanagerApplication.class, args);
	}

}

