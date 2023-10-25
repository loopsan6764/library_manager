package com.example.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Logs;
import com.example.repository.LogsRepository;

@Service
public class LogsService {

    private final LogsRepository logsRepository; // LogsRepository のDI

    @Autowired
    public LogsService(LogsRepository logsRepository) {
        this.logsRepository = logsRepository;
    }

    // ログを生成
    public void createLog(int libraryId, int user_Id, String returnDueDate) {
        // Logsエンティティを作成
        Logs log = new Logs();
        log.setLibraryId(libraryId);
        log.setUserId(user_Id);
        
        // 返却予定日をパースし、T00:00:00を連結
        LocalDateTime returnDueDateTime = LocalDateTime.parse(returnDueDate + "T00:00:00");
        log.setReturnDueDate(returnDueDateTime);

        // return_dateにはNULLを設定
        log.setReturnDate(null);
        
        // ログをデータベースに保存
        logsRepository.save(log);
    }
    
    public void save(Logs logs) {
        // Logsエンティティをデータベースに保存
        logsRepository.save(logs);
    }
    
    public List<Logs> getBorrowHistoryByUserId(Integer user_Id) {
        // ロジックを実装して、userIdに基づいて履歴を取得する
        // 例えば、LogsRepositoryを使用してデータベースからデータを取得する
        // 実際のロジックはプロジェクトの要件に合わせて実装してください
        // 以下は仮の例です
        return logsRepository.findByUser_Id(user_Id);
    }
}
