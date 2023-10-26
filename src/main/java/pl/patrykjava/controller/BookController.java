package pl.patrykjava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.patrykjava.entity.Book;
import pl.patrykjava.repository.BookRepository;
import pl.patrykjava.service.BookService;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;
    private final BookService bookService;

    public BookController(BookRepository bookRepository, BookService bookService) {
        this.bookRepository = bookRepository;
        this.bookService = bookService;
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam(value = "query", required = false) String query,
                              @RequestParam(value = "page", defaultValue = "0") int page, Model theModel) {
        List<Book> books = bookService.getPaginatedBooks(query, page, 6);
        int totalPages = bookService.getTotalRecords(query, 6);

        theModel.addAttribute("books", books);
        theModel.addAttribute("query", query);
        theModel.addAttribute("totalPages", totalPages);
        theModel.addAttribute("currentPage", page);

        return "book/searchBooks";
    }


}
