package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.demo.model.entity.Reservation.PaymentStatus;
import com.example.demo.model.entity.Reservation.Status;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "adminactions")
public class AdminAction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer actionId;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;

    @Column(nullable = false, length = 50)
    private String actionType;

    @Column(nullable = false, length = 50)
    private String targetTable;

    @Column(nullable = false)
    private Integer targetId;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime actionTimestamp = LocalDateTime.now();

}

