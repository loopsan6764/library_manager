package com.example.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Library;
import com.example.entity.Logs;
import com.example.service.LibraryService;
import com.example.service.LoginUser;
import com.example.service.LogsService;

@Controller
@RequestMapping("library")
public class LibraryController {

	 private final LibraryService libraryService;
	    private final LogsService logsService;

	    @Autowired
	    public LibraryController(LibraryService libraryService, LogsService logsService) {
	        this.libraryService = libraryService;
	        this.logsService = logsService;
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
    public String borrow(@RequestParam("id") Integer id,
                         @RequestParam("return_due_date") String returnDueDate,
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
            Logs log = new Logs();
            log.setLibraryId(library.getId());
            log.setUserId(userId);
            log.setRentDate(LocalDateTime.now());
            // 返却予定日をパースし、T00:00:00を連結
            LocalDateTime returnDueDateTime = LocalDateTime.parse(returnDueDate + "T00:00:00");
            log.setReturnDueDate(returnDueDateTime);
            log.setReturnDate(null); // return_dateにはNULLを設定
            logsService.save(log);
        }

        // 5. リストページにリダイレクト
        return "redirect:/library";
    }
}