package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.demo.model.entity.Scooter.Status;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MaintenanceRecords")
public class MaintenanceRecord implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maintenanceId;

    @ManyToOne
    @JoinColumn(name = "scooter_id", nullable = false)
    private Scooter scooter;
/*
解釋：表示多個實體對應一個其他實體，即「多對一」的關係。
應用場景：例如，多個「訂單」（Order）屬於一個「用戶」（User）。
預設@ManyToOne 是 立即加載（Eager）。    
 */
    
    

    @Column(nullable = false)
    private LocalDate maintenanceDate;

    @Column(nullable = false, length = 50)
    private String maintenanceStatus;

    @Column(columnDefinition = "TEXT")
    private String maintenanceDetails;

    @Column(precision = 10, scale = 2)
    private double cost;

    // Getters and setters
}
