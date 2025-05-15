package com.practice.boardproject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

@SpringBootTest
class BoardprojectApplicationTests {

    @Test
    void contextLoads() {
    }

    @Bean(name = "jwt.secret")
    public String jwtSecret() {
        return "test-secret";
    }
}
