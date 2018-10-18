package com.rpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author:wjy
 * @Date: 2018/8/16.
 */
@SpringBootApplication
@EnableAutoConfiguration
public class Start {

	public static void main(String[] args) {
		SpringApplication.run(Start.class,args);
	}
}
