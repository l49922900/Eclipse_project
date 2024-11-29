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
@Table(name = "Reservations")
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
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
    
    
    

    @ManyToOne
    @JoinColumn(name = "scooter_id", nullable = false)
    private Scooter scooter;

    @Column(nullable = false)
    private LocalDate reservationDate;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('reserved', 'completed', 'canceled') DEFAULT 'reserved'")
    private Status status = Status.reserved;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('pending', 'paid', 'refunded', 'canceled') DEFAULT 'pending'")
    private PaymentStatus paymentStatus = PaymentStatus.pending;

    private LocalDate paymentDate;

    @Column(nullable = false, precision = 10, scale = 2)
    private double totalAmount;

    public enum Status {
    	reserved, completed, canceled
    }

    public enum PaymentStatus {
    	pending,paid, refunded, canceled
    }

}

