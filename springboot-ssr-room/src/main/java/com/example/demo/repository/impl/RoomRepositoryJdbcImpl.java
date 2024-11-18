package com.example.demo.repository.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Room;
import com.example.demo.repository.RoomRepositoryJdbc;

// 實現 RoomRepositoryJdbc 介面
@Repository  
/*
標註這個類別是資料庫存取層，並將其交給 Spring 容器管理。
預設別名 roomRepositoryJdbcImpl (類名,字首小寫)
 */
@PropertySource("classpath:sql.properties") // 自動到 src/main/respurces 找到 sql.properties，來存取sql語句
public class RoomRepositoryJdbcImpl implements RoomRepositoryJdbc {
	/*
	使用 Spring Framework 的 JdbcTemplate 進行 SQL 操作，包含查詢、插入、更新和刪除。
	 */
	
	
	private static final Logger logger = LoggerFactory.getLogger(RoomRepositoryJdbcImpl.class);
	//日誌紀錄
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/*
	透過 @Autowired 將 JdbcTemplate 注入至此類別中，以進行資料庫操作。
	 */
	
	
	@Value("${room.sql.findAll}") // ${} SpringEL 語法
	private String findAllSql;
	
	@Value("${room.sql.findById}")
	private String findByIdSql;
	
	@Value("${room.sql.save}")
	private String saveSql;
	
	@Value("${room.sql.update}")
	private String updateSql;
	
	@Value("${room.sql.deleteById}")
	private String deleteByIdSql;
	
	@Override
	public List<Room> findAll() {
		return jdbcTemplate.query(findAllSql, new BeanPropertyRowMapper<>(Room.class));
		/*
		jdbcTemplate.query 是 Spring JDBC 中提供的一個方法，用於執行 SQL 查詢語句並將結果處理為 Java 物件或集合。
		
		BeanPropertyRowMapper 透過反射機制來將查詢結果中的欄位名稱（如 room_id、room_name、room_size）對應到 Room 類別中對應的屬性。
		 */	
	}

	@Override
	public Optional<Room> findById(Integer roomId) {
		// 因為 queryForObject 若沒有找到資料會自動拋出例外, 所以要 try-catch 保護
		try {
			Room room = jdbcTemplate.queryForObject(findByIdSql, new BeanPropertyRowMapper<>(Room.class), roomId);
			/*
			queryForObject 用於執行查詢並返回單一物件。與 jdbcTemplate.query 的不同在於，它適合用於查詢返回一行資料的情境。
			
			sql的 ? 會由後面的 roomId 參數取代。
			 */	
			
			return Optional.of(room);
			//如果查詢成功並找到資料，則將結果封裝在 Optional.of(room) 中返回。	
		} catch (Exception e) {
			logger.info(e.toString());
		}
		return Optional.empty();
		//若查不到資料或發生其他例外，則返回 Optional.empty()，表示此查詢沒有結果。	
	}

	@Override
	public int save(Room room) {
		return jdbcTemplate.update(saveSql, room.getRoomId(), room.getRoomName(), room.getRoomSize());
	}

	@Override
	public int update(Room room) {
		return jdbcTemplate.update(updateSql, room.getRoomName(), room.getRoomSize(), room.getRoomId());
	}

	@Override
	public int deleteById(Integer roomId) {
		return jdbcTemplate.update(deleteByIdSql ,roomId);
	}
	
}