package com.example.demo.model.dto;

import java.time.LocalDate;

import com.example.demo.model.entity.Reservation.PaymentStatus;
import com.example.demo.model.entity.Reservation.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
	
	private Integer cartId;

    private Integer scooterId;
    private Integer userId;

    private LocalDate startDate;
    private LocalDate endDate;

    private double dailyRate;
    private double totalCost;

    // 定義狀態 Enum
    private Status status = Status.reserved;
    private PaymentStatus paymentStatus = PaymentStatus.pending;


}

