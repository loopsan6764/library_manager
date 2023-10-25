package com.example.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.entity.Logs;

@Service
public class LogsService {

    // ログを生成
    public void createLog(int libraryId, int userId, String returnDueDate) {
        // Logsエンティティを作成
        Logs log = new Logs();
        log.setLibraryId(libraryId);
        log.setUserId(userId);
        
        // 返却予定日をパースし、T00:00:00を連結
        LocalDateTime returnDueDateTime = LocalDateTime.parse(returnDueDate + "T00:00:00");
        log.setReturnDueDate(returnDueDateTime);

        // return_dateにはNULLを設定
        log.setReturnDate(null);
        
    }
}
