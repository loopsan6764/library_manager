package com.example.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Logs {
    @Id
    private Long id; // ログの一意の識別子

    private Integer library_Id; // 書籍ID
    private Integer user_Id; // ユーザーID
    private LocalDateTime rent_Date; // 貸し出し日時
    private LocalDateTime return_Date; // 返却日時
    private LocalDateTime return_Due_Date; // 返却予定日時
    
    public void setLibraryId(Integer libraryId) {
        this.library_Id = libraryId;
    }

    public void setRentDate(LocalDateTime rentDate) {
        this.rent_Date = rentDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.return_Date = returnDate;
    }

    public void setReturnDueDate(LocalDateTime returnDueDate) {
        this.return_Due_Date = returnDueDate;
    }

    public void setUserId(Integer userId) {
        this.user_Id = userId;
    }
}