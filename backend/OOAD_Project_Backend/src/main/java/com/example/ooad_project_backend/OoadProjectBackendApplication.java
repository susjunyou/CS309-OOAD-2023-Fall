package com.example.ooad_project_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
//@MapperScan("com.example.ooad_project_backend.dao")
public class OoadProjectBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(OoadProjectBackendApplication.class, args);
    }

}
