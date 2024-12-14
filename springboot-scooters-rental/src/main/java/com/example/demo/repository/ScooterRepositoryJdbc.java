/*
為什麼RoomRepositoryJdbc 和 RoomRepository會分開設計:

RoomRepositoryJdbc 使用傳統 JDBC（Java Database Connectivity）來操作資料庫，
適合需要完全控制 SQL 查詢時的場景。例如，當 SQL 查詢相對複雜，或者需要進行批次更新、非同步查詢等
 

而 RoomRepository 則是使用 Spring Data JPA，透過 JpaRepository 提供標準的 CRUD 操作，
並能自動生成查詢語句。在需要簡單 CRUD 操作或是常見查詢語句時，使用 JPA 能夠減少 SQL 編寫的工作量
 */


package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.model.entity.Reservation;
import com.example.demo.model.entity.Scooter;

public interface ScooterRepositoryJdbc {
	int update(Scooter scooter);

	List<Reservation> findConflictingRentalsDays(Integer scooterId, LocalDate startDate, LocalDate endDate);
}
