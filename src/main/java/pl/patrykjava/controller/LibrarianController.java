package pl.patrykjava.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.patrykjava.entity.Book;
import pl.patrykjava.service.LibrarianBookService;

@Controller
@RequestMapping("/librarian")
public class LibrarianController {

    private final LibrarianBookService librarianBookService;

    public LibrarianController(LibrarianBookService librarianBookService) {
        this.librarianBookService = librarianBookService;
    }

    @GetMapping("/dashboard")
    public String showDashboard() {
        return "librarian/librarianDashboard";
    }

    @GetMapping("/books")
    public String showSearchResults(@RequestParam("query") String query, @RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        Long startIndex = page * 8L;
        int booksPerPage = 8;
        List<Book> books = librarianBookService.searchBooks(query, startIndex);
        int totalRecords = 60; // assuming there are 60 books for the given query

        model.addAttribute("books", books);
        model.addAttribute("query", query);
        model.addAttribute("totalPages", (int) Math.ceil(totalRecords / (double) booksPerPage));
        model.addAttribute("currentPage", page);

        return "librarian/librarianSearchBooks";
    }
}
