package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Log;

public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findByUserId(Integer userId);
    
    // 書籍IDに基づいて最新のログを取得
    Page<Log> findByLibraryIdOrderByRentDateDesc(Integer libraryId, Pageable pageable);
}