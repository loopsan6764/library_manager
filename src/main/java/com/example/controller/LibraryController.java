package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Library;
import com.example.service.LibraryService;

@Controller
@RequestMapping("library")
public class LibraryController {

    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    public String index(Model model) {
        List<Library> libraries = this.libraryService.findAll();
        model.addAttribute("libraries", libraries);
        return "library/index";
    }
    
    @GetMapping("/borrow")
    public String borrowingForm(@RequestParam("id") Integer id, Model model) {
        // 書籍IDに基づいて書籍情報を取得する処理を実装する（未記述の部分）
        Library library = libraryService.getLibraryById(id);
        
        // モデルに書籍情報を追加
        model.addAttribute("library", library);
        
        return "borrowingForm"; // borrowingForm.html テンプレートを表示
    }
}