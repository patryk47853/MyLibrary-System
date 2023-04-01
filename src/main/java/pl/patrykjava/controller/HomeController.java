package pl.patrykjava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.patrykjava.dto.LibraryCardDTO;
import pl.patrykjava.entity.User;
import pl.patrykjava.repository.UserRepository;
import pl.patrykjava.service.LibraryCardService;
import pl.patrykjava.service.UserService;

@Controller
public class HomeController {
    @Autowired
    private LibraryCardService libraryCardService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/books")
    public String books() {

        return "books";
    }

    @GetMapping("/create-library-card")
    public String libraryCard(Model theModel) {

        theModel.addAttribute("libraryCard", new LibraryCardDTO());

        return "createLibraryCard";
    }

    @PostMapping("process-library-card")
    public String createLibraryCard(@ModelAttribute("libraryCard") LibraryCardDTO libraryCardDTO) {

        libraryCardService.save(libraryCardDTO);

        return "redirect:/create-library-card?success";
    }

    @GetMapping("/users")
    public String users(Model theModel, @AuthenticationPrincipal UserDetails currentUser) {
        User user = userRepository.findByUsername(currentUser.getUsername());
        theModel.addAttribute("currentUser", user);

        return "users";
    }
}
