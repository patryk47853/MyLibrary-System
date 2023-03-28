package pl.patrykjava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.patrykjava.service.UserService;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/books")
    public String books() {

        return "books";
    }

    @GetMapping("/library-card")
    public String libraryCard() {

        return "createLibraryCard";
    }

    @PostMapping("process_library_card")
    public String createLibraryCard() {

        return "redirect:/library-card";
    }
}
