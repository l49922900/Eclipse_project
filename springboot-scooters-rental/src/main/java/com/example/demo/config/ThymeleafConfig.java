package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

@Configuration
//Spring 會將此類別視為 配置檔（Configuration File），並自動進行 元件掃描（Component Scan） 和 Bean 管理。

public class ThymeleafConfig {

    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    //SpringSecurityDialect，這是 Thymeleaf 與 Spring Security 的整合模組，允許在 Thymeleaf 模板中使用 Spring Security 提供的屬性和標籤，例如判斷用戶是否已登入、是否具有某些權限等。
        
        
    }
}
