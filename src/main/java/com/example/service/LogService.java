package com.example.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.entity.Log;
import com.example.repository.LogRepository;

@Service
public class LogService {

	private final LogRepository logRepository; // LogsRepository のDI

	@Autowired
	public LogService(LogRepository logRepository) {
		this.logRepository = logRepository;
	}

	// ログを生成
	public void createLog(int libraryId, int userId, String returnDueDate) {
		// Logsエンティティを作成
		Log log = new Log();
		log.setLibraryId(libraryId);
		log.setUserId(userId);

		// 返却予定日をパースし、T00:00:00を連結
		LocalDateTime returnDueDateTime = LocalDateTime.parse(returnDueDate + "T00:00:00");
		log.setReturnDueDate(returnDueDateTime);

		// return_dateにはNULLを設定
		log.setReturnDate(null);

		// ログをデータベースに保存
		logRepository.save(log);
	}

	public void save(Log logs) {
		// Logsエンティティをデータベースに保存
		logRepository.save(logs);
	}

	public List<Log> getBorrowHistoryByUserId(Integer userId) {
		// ロジックを実装して、userIdに基づいて履歴を取得する
		// 例えば、LogsRepositoryを使用してデータベースからデータを取得する
		// 実際のロジックはプロジェクトの要件に合わせて実装してください
		// 以下は仮の例です
		return logRepository.findByUserId(userId);
	}

	public List<Log> findLatestLogByLibraryId(Integer libraryId) {
        Pageable pageable = PageRequest.of(0, 1);
        Page<Log> logs = logRepository.findByLibraryIdOrderByRentDateDesc(libraryId, pageable);
        return logs.getContent();
    }
}