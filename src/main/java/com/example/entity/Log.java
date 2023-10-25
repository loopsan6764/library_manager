package com.example.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "logs")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rent_date")
    private LocalDateTime rentDate;

    @Column(name = "return_due_date")
    private LocalDateTime returnDueDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    // 多対1のリレーションシップを設定
    @ManyToOne
    @JoinColumn(name = "library_id")
    private Library library;

    // コンストラクタ、getter、setterなどのメソッド

    // Getter for Library
    public Library getLibrary() {
        return library;
    }
}