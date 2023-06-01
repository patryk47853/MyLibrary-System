package pl.patrykjava.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.patrykjava.dto.LibraryCardDTO;
import pl.patrykjava.entity.LibraryCard;
import pl.patrykjava.entity.User;
import pl.patrykjava.repository.LibraryCardRepository;
import pl.patrykjava.repository.UserRepository;
import pl.patrykjava.service.LibraryCardService;

@Controller
public class HomeController {

    private final LibraryCardService libraryCardService;
    private final LibraryCardRepository libraryCardRepository;
    private final UserRepository userRepository;

    public HomeController(LibraryCardService libraryCardService, LibraryCardRepository libraryCardRepository, UserRepository userRepository) {
        this.libraryCardService = libraryCardService;
        this.libraryCardRepository = libraryCardRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/create-library-card")
    public String createLibraryCard(Model theModel) {

        theModel.addAttribute("libraryCard", new LibraryCardDTO());

        return "home/createLibraryCard";
    }

    @PostMapping("/process-library-card")
    public String processCreatingLibraryCard(@ModelAttribute("libraryCard") LibraryCardDTO libraryCardDTO,
                                    @AuthenticationPrincipal UserDetails currentUser) {

        libraryCardService.createLibraryCard(libraryCardDTO, currentUser);

        return "redirect:/create-library-card?success";
    }

    @GetMapping("/library-card")
    public String showLibraryCard(Model theModel,
                              @AuthenticationPrincipal UserDetails currentUser) {

        LibraryCard libraryCard = libraryCardService
                .getLibraryCardByUsername(currentUser.getUsername());

        theModel.addAttribute("libraryCard", libraryCard);

        return "home/showLibraryCard";
    }

    @GetMapping("/update-library-card")
    public String updateLibraryCard(Model theModel,
                                @AuthenticationPrincipal UserDetails currentUser) {

        LibraryCard libraryCard = libraryCardService
                .getLibraryCardByUsername(currentUser.getUsername());

        theModel.addAttribute("libraryCard", libraryCard);

        return "home/updateLibraryCard";
    }

    @PostMapping("/process-update-library-card")
    public String processUpdatingLibraryCard(@ModelAttribute("libraryCard") LibraryCardDTO libraryCardDTO,
                                       @AuthenticationPrincipal UserDetails currentUser) {

        User user = userRepository.findByUsername(currentUser.getUsername());
        libraryCardService.updateLibraryCard(libraryCardDTO, user, libraryCardRepository, userRepository);

        return "redirect:/update-library-card?success";
    }

}
