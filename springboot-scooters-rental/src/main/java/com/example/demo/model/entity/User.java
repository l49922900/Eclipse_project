package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.entity.Scooter.Status;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Users")

public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer userId;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 255)
    private String passwordHash;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('user', 'admin') DEFAULT 'user'")
    private Role role = Role.user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('active', 'inactive') DEFAULT 'active'")
    private AccountStatus accountStatus = AccountStatus.active;
    
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();
/*
這個list可以用在當我們需要用User查詢他的Reservation時

如果我們希望從父類別（例如 User）直接獲取所有的關聯物件（例如該 User 的所有 Reservation），我們必須在 User 裡定義一個集合（例如 List<Reservation>），
並透過 mappedBy 告訴 JPA：這個關聯是由 Reservation 實體的某個欄位來維護的。


為什麼要使用 mappedBy？
1.明確責任端：
	在 Reservation 中，user 是外鍵欄位，負責維護關聯關係。JPA 需要知道由誰來更新這個外鍵值。

2.避免多餘的中間表：
	如果在 User 和 Reservation 之間都標記了關聯而沒有用 mappedBy，JPA 會假設需要建立一個中間表來管理兩者之間的關係。使用 mappedBy 後，JPA 知道不需要中間表，直接用 Reservation 的外鍵欄位即可。

3.方便反向查詢：
	使用者可以透過 User.getReservations() 獲取該使用者的所有預約，實現更方便的業務邏輯。
 */
    
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartMaintenance> partMaintenances = new ArrayList<>();

    
    

    public enum Role {
    	user, admin
    }

    public enum AccountStatus {
    	active, inactive
    }

}

