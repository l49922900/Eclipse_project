package com.example.demo.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "scooter_parts")
public class ScooterPart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "part_id")
    private Integer partId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scooter_id", nullable = false)
    private Scooter scooter;

    @Column(name = "part_name", nullable = false, length = 50)
    private String partName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "ENUM('normal', 'damaged') DEFAULT 'normal'")
    private PartStatus status = PartStatus.normal;

    @Column(name = "last_update")
    private LocalDate lastUpdate = LocalDate.now();

    @OneToMany(mappedBy = "scooterPart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartMaintenance> maintenanceRecords = new ArrayList<>();
    
    @Column(name = "part_note", length = 255)
    private String partNote;
    
    
    @Column(name = "is_active")
    private Boolean isActive = true;  // 標記當前是否為使用中的部件

    public enum PartStatus {
        normal, damaged
    }
}

