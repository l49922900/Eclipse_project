package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@SpringBootApplication 註解標註在主類別上，表示這個類別是 Spring Boot 應用程式的進入點
public class SpringbootScootersRentalApplication {
	/*
	這段 Java 程式碼是用來啟動一個 Spring Boot 應用程式的主類別。
	在這段程式碼中，SpringbootScootersRentalApplication 的類別，
	並使用 Spring Boot 的 @SpringBootApplication 註解來自動配置並啟動應用程式。
	 */

	public static void main(String[] args) {
		SpringApplication.run(SpringbootScootersRentalApplication.class, args);
		//此方法會啟動 Spring Boot 應用程式的內嵌伺服器 (例如 Tomcat)，並開始運行。
	}

}
