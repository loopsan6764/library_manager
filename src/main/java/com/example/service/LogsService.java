package com.example.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Logs;
import com.example.repository.LogsRepository; // LogsRepository のインポートが必要

@Service
public class LogsService {

    private final LogsRepository logsRepository; // LogsRepository のDI

    @Autowired
    public LogsService(LogsRepository logsRepository) {
        this.logsRepository = logsRepository;
    }

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
        
        // ログをデータベースに保存
        logsRepository.save(log);
    }
}
