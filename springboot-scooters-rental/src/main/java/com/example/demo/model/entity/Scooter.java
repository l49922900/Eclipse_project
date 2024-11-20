package com.example.demo.model.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity  //JPA，實體類與資料表對應
@Table(name = "scooters") //若資料表與實體類一致可以不用設定此行
public class Scooter {

	
	/////////////////////////////////////////////////
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	//scooter_id自動生成，從1開始每次自動+1，過號不補，自動生成的 ID 將從最大值的下一個數字開始。
    @Column(name = "scooter_id")
    private Integer scooterId;
    
    @Column(name = "license_plate", nullable = false, unique = true, length = 20)
    private String licensePlate;
    
    
    //用來表示機車的車型（model），這個欄位通常包括車輛的製造型號，例如「Yamaha Cygnus」
    @Column(name = "model", nullable = false, length = 50)
    private String model;
    
    
    @Column(name = "cc",nullable = false)
    private Integer cc;
    
    //表示車輛的類型，例如「普通」、「電動車」或「速可達」等。此欄位可以幫助系統根據不同的機車類型進行分類、篩選或計費。
    @Column(name = "type", length = 50)
    private String type;
    
    
    
    
    @Enumerated(EnumType.STRING)
    /*
    這個註解將列舉型別 Status 映射到資料庫的欄位。
    EnumType.STRING：這表示將列舉的名稱（字串值）儲存到資料庫中。
    也就是說，當機車的狀態是 available 時，資料庫中儲存的值將是字串 "available"
     */
    @Column(name = "status", columnDefinition = "ENUM('available', 'rented', 'maintenance') DEFAULT 'available'")
    /*
    columnDefinition = "ENUM('available', 'rented', 'maintenance') DEFAULT 'available'":
    用來在資料庫中強制要求這個欄位是一個 ENUM 型別，並提供可用的選項（available, rented, maintenance），預設值為 available。
     */
    private Status status = Status.available;
    //這行程式碼表示當建立 Scooter 實體時，status 欄位的預設值為 Status.available
    
    
    
    @Column(name = "daily_rate", nullable = false)
    private double dailyRate;
    
    
    //用來記錄機車的具體狀態說明，例如「車胎有磨損需要更換」或「前燈損壞」等細節。
    @Column(name = "condition_note")
    private String conditionNote;
    
    @Column(name = "last_maintenance_date")
    private LocalDate lastMaintenanceDate;

    public enum Status {
    	available, rented, maintenance
    }
    
    
//    @PrePersist
//    public void prePersist() {
//        if (scooterId == null) {
//        	scooterId = 1; // 設置預設值
//        }
//    }

    
	/*
	上述程式碼會自動產生以下新增 SQL 語句並交由 MySQL 執行

	CREATE TABLE Scooters (
	    scooter_id INT PRIMARY KEY AUTO_INCREMENT,
	    license_plate VARCHAR(20) NOT NULL UNIQUE,
	    model VARCHAR(50) NOT NULL,
	    type VARCHAR(50),
	    status ENUM('available', 'rented', 'maintenance') DEFAULT 'available',
	    daily_rate DECIMAL(10, 2) NOT NULL,
	    condition_note TEXT,
	    last_maintenance_date DATE
	);
	 */

}
