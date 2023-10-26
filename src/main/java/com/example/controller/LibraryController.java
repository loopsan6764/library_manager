package com.example.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.entity.Library;
import com.example.entity.Log;
import com.example.service.LibraryService;
import com.example.service.LogService;
import com.example.service.LoginUser;

@Controller
@RequestMapping("library")
public class LibraryController {

	private final LibraryService libraryService;
	private final LogService logService;

	@Autowired
	public LibraryController(LibraryService libraryService, LogService logService) {
		this.libraryService = libraryService;
		this.logService = logService;
	}

	@GetMapping
	public String index(Model model) {
		List<Library> libraries = this.libraryService.findAll();
		model.addAttribute("libraries", libraries);
		return "library/index";
	}

	@GetMapping("/borrow")
	public String borrowingForm(@RequestParam("id") Integer id, Model model) {
		Library library = libraryService.getLibraryById(id);

		model.addAttribute("library", library);

		return "library/borrowingForm"; // borrowingForm.html テンプレートを表示
	}

	@PostMapping("/borrow")
	public String borrow(@RequestParam("id") Integer id, @RequestParam("return_due_date") String returnDueDate,
			@AuthenticationPrincipal LoginUser loginUser) {
		// 1. 書籍IDに基づいて書籍情報を取得
		Library library = libraryService.getLibraryById(id);

		if (library != null) {
			// 2. ログインユーザーのIDを取得
			Integer userId = loginUser.getUser().getId();

			// 3. 書籍情報を更新（USER_IDを上書き）
			library.setUserId(userId);
			libraryService.save(library);

			// 4. 新しいログを生成
			Log log = new Log();
			log.setLibraryId(library.getId());
			log.setUserId(userId);
			log.setRentDate(LocalDateTime.now());
			// 返却予定日をパースし、T00:00:00を連結
			LocalDateTime returnDueDateTime = LocalDateTime.parse(returnDueDate + "T00:00:00");
			log.setReturnDueDate(returnDueDateTime);
			log.setReturnDate(null); // return_dateにはNULLを設定
			logService.save(log);
		}

		// 5. リストページにリダイレクト
		return "redirect:/library";
	}

	@PostMapping("/return")
	public String returnBook(@RequestParam("id") Integer id, @AuthenticationPrincipal LoginUser loginUser,
			RedirectAttributes redirectAttributes) {
		// 書籍IDをもとに書籍情報を取得
		Library library = libraryService.getLibraryById(id);

		if (library != null) {
			// 書籍のUSER_IDを0に設定して更新
			library.setUserId(0);
			libraryService.save(library);

			// 最新のログを取得
			List<Log> logs = logService.findLatestLogByLibraryId(id);

			if (!logs.isEmpty()) {
				Log latestLog = logs.get(0); // 最新のログを取得

				// 返却日時を設定
				latestLog.setReturnDate(LocalDateTime.now());
				logService.save(latestLog);
			}
		}
		return "redirect:/library";
	}

	@GetMapping("/history")
	public String history(Model model, @AuthenticationPrincipal LoginUser loginUser) {
		// ログインしているユーザーのIDを取得
		Integer userId = loginUser.getUser().getId();

		// ログインユーザーに紐づく貸し出し履歴を取得（例：LogServiceのメソッドを呼び出して取得する）
		List<Log> borrowHistory = logService.getBorrowHistoryByUserId(userId);

		// Modelに貸し出し履歴をセット
		model.addAttribute("borrowHistory", borrowHistory);

		// borrowHistory.html テンプレートを表示
		return "library/borrowHistory";
	}
}