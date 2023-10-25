package com.example.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "logs")
public class Log {

	@Id
	@SequenceGenerator(name = "LOG_ID_GENERATOR", sequenceName = "LOG_ID_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOG_ID_GENERATOR")
	@Column(name = "ID")
	private Integer id;
    
    @Column(name = "library_id")
    private Integer libraryId;
    
    @Column(name = "user_id")
    private Integer userId;

	@Column(name = "rent_date")
    private LocalDateTime rentDate;

    @Column(name = "return_due_date")
    private LocalDateTime returnDueDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;
    
    // 多対1のリレーションシップを設定
    @ManyToOne
    @JoinColumn(insertable = false, updatable = false)
    private Library library;
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLibraryId() {
		return libraryId;
	}

	public void setLibraryId(Integer libraryId) {
		this.libraryId = libraryId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public LocalDateTime getRentDate() {
		return rentDate;
	}

	public void setRentDate(LocalDateTime rentDate) {
		this.rentDate = rentDate;
	}

	public LocalDateTime getReturnDueDate() {
		return returnDueDate;
	}

	public void setReturnDueDate(LocalDateTime returnDueDate) {
		this.returnDueDate = returnDueDate;
	}

	public LocalDateTime getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDateTime returnDate) {
		this.returnDate = returnDate;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

    public Library getLibrary() {
        return library;
    }
}