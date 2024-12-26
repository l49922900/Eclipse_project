package com.example.demo.model.entity;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "part_maintenance")
public class PartMaintenance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maintenance_id")
    private Integer maintenanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id", nullable = false)
    private ScooterPart scooterPart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User admin;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", columnDefinition = "ENUM('repair', 'replace') NOT NULL")
    private ActionType actionType;

    @Column(name = "action_date")
    private LocalDate actionDate = LocalDate.now();

    @Column(name = "notes", length = 255)
    private String notes;

    public enum ActionType {
        repair, replace
    }
}

