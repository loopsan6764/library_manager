package com.example.entity;

import java.time.LocalDateTime;

@Entity
public class Logs {
    public void setLibraryId(Integer libraryId) {
        this.libraryId = libraryId;
    }

    public void setRentDate(LocalDateTime rentDate) {
        this.rentDate = rentDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public void setReturnDueDate(LocalDateTime returnDueDate) {
        this.returnDueDate = returnDueDate;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}