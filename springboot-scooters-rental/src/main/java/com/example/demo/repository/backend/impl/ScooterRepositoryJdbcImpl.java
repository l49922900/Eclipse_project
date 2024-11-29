package com.example.demo.repository.backend.impl;

import java.util.EnumSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Scooter;
import com.example.demo.repository.backend.ScooterRepositoryJdbc;
import com.example.demo.exception.ScooterDataAccessException;



@Repository  
/*
標註這個類別是資料庫存取層，並將其交給 Spring 容器管理。
預設別名 roomRepositoryJdbcImpl (類名,字首小寫)
 */
@PropertySource("classpath:sql.properties") // 自動到 src/main/respurces 找到 sql.properties，來存取sql語句
public class ScooterRepositoryJdbcImpl implements ScooterRepositoryJdbc  {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/*
	透過 @Autowired 將 JdbcTemplate 注入至此類別中，以進行資料庫操作。
	 */
	
	
	private static final Logger log = LoggerFactory.getLogger(ScooterRepositoryJdbcImpl.class);
	/*
	Logger（記錄器）是用來進行 日誌記錄 的工具，它可以幫助你在應用程式中記錄重要訊息
	 */
	
	
	@Value("${scooter.sql.update}")
	private String updateSql;
	@Override
	public int update(Scooter scooter) {
		// 驗證 status 是否為合法值
	    if (!EnumSet.allOf(Scooter.Status.class).contains(scooter.getStatus())) {
	        throw new IllegalArgumentException("Invalid status value: " + scooter.getStatus());
	    }
	    
	    System.out.println(scooter.getStatus());
		
	    try {
	    	return jdbcTemplate.update(updateSql,scooter.getLicensePlate(),scooter.getModel(),scooter.getCc(),scooter.getType(),scooter.getStatus().name(),scooter.getDailyRate(),scooter.getConditionNote(),scooter.getLastMaintenanceDate(),scooter.getScooterId());
		} catch (Exception e) {
			 // 紀錄異常並拋出自定義異常
			log.error("Error updating scooter with ID {}: {}", scooter.getScooterId(), e.getMessage(), e);
		    throw new ScooterDataAccessException("Failed to update scooter", e);
		   
		}
	    
		
		
		
	};
}
