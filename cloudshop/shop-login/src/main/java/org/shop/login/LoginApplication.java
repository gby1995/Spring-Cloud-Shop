package org.shop.login;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@EnableDiscoveryClient
@SpringBootApplication
@EnableHystrix
public class LoginApplication {
	public static void main(String[] args){
		new SpringApplicationBuilder(LoginApplication.class).web(true).run(args);
	}

}
