package com.example.demo.model.dto;

import java.time.LocalDate;

import com.example.demo.model.entity.Reservation;
import com.example.demo.model.entity.Scooter;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.Reservation.PaymentStatus;
import com.example.demo.model.entity.Reservation.Status;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {

	
    private Integer reservationId;


    private Integer userId;
/*
將屬性定義為 User :

1.JPA 是基於物件導向設計的框架，強調對實體關係的映射。

當我們說 Reservation 實體「關聯到」一個 User 實體時，JPA 直接將這個關係表示為一個 User 物件，
而不是單純的外鍵數值。

以上程式碼表明，Reservation 實體「擁有」一個 User 實體，而非只是記錄其 user_id

2.外鍵屬性仍然存在
即使在 Java 中表現為 User 或 Scooter，資料庫中依然存儲的是外鍵（如 user_id 和 scooter_id）。

JPA 使用 @JoinColumn 指定外鍵名稱，例如：
@JoinColumn(name = "user_id", nullable = false)

這樣可以確保 JPA 在操作時知道這個關聯對應到資料庫的哪個欄位。
 */
    
    private Integer scooterId;


    private LocalDate reservationDate;


    private LocalDate startDate;


    private LocalDate endDate;


    private Status status = Status.reserved;


    private PaymentStatus paymentStatus = PaymentStatus.pending;

    private LocalDate paymentDate;


    private double totalAmount;
}
