package com.rz.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Created by as on 2017/12/7.
 * 在开发中我们知道Spring Boot默认会扫描启动类同包以及子包下的注解
 * 解决方案：http://blog.csdn.net/u014695188/article/details/52263903
 */
@SpringBootApplication
@ServletComponentScan
public class DemoApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DemoApplication.class);
    }

    public static void main(String[] args){
        SpringApplication.run(DemoApplication.class,args);
    }

}
