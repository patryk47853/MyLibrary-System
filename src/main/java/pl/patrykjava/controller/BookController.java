package pl.patrykjava.controller;

import com.google.api.services.books.model.Volume;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.patrykjava.service.BookService;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public String searchBooks(@RequestParam("query") String query, Model model)
            throws IOException {
        List<Volume> books = bookService.searchForBooks(query);
        model.addAttribute("books", books);
        return "books";
    }
}
