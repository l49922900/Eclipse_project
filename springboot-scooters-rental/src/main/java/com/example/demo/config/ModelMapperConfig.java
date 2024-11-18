package com.example.demo.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // Springboot 在啟動完成前會先行執行此配置
public class ModelMapperConfig {
	// 由 Springboot 來管理此物件(IOC)
		// 若有必要其他程式可以透過 @Autowired 取得該實體
		@Bean  
		/*
		@Bean：標註在 modelMapper 方法上，表明此方法將產生一個 Bean，
		並將該方法返回的物件註冊到 Spring 的 IoC（Inversion of Control, 控制反轉）容器中。
		該物件的型別是 ModelMapper。這樣一來，ModelMapper 就可以被 Spring 依賴注入機制管理，
		並在需要時被其他類別使用。
		 */
		
		
		ModelMapper modelMapper() {
		/*
		ModelMapper 是一個物件映射框架，主要用於進行兩個物件間的屬性轉換。
		例如，當需要將 DTO（Data Transfer Object）轉換為 Entity 或反之時，
		可以使用 ModelMapper 來自動進行屬性對應	
		 */
			
			return new ModelMapper();
		}
		
}
