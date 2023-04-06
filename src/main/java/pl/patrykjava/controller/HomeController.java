package pl.patrykjava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.patrykjava.dto.LibraryCardDTO;
import pl.patrykjava.dto.UserRegisterDTO;
import pl.patrykjava.entity.LibraryCard;
import pl.patrykjava.entity.User;
import pl.patrykjava.repository.LibraryCardRepository;
import pl.patrykjava.repository.UserRepository;
import pl.patrykjava.service.LibraryCardService;
import pl.patrykjava.service.UserService;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private LibraryCardService libraryCardService;

    @Autowired
    private LibraryCardRepository libraryCardRepository;
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

    @PostMapping("/process-library-card")
    public String createLibraryCard(@ModelAttribute("libraryCard") LibraryCardDTO libraryCardDTO,
                                    @AuthenticationPrincipal UserDetails currentUser) {

        User user = userRepository.findByUsername(currentUser.getUsername());
        if(user.getLibraryCard() != null) {
            return "redirect:/create-library-card?error";
        }

        libraryCardService.save(libraryCardDTO);

        return "redirect:/create-library-card?success";
    }

    @GetMapping("/profile")
    public String showProfile(Model theModel,
                              @AuthenticationPrincipal UserDetails currentUser) {

        User user = userRepository.findByUsername(currentUser.getUsername());
        LibraryCard libraryCard = user.getLibraryCard();

        theModel.addAttribute("user", libraryCard);

        return "profile";
    }

    @GetMapping("/update-profile")
    public String updateProfile(Model theModel,
                                @AuthenticationPrincipal UserDetails currentUser) {

        User user = userRepository.findByUsername(currentUser.getUsername());
        LibraryCard libraryCard = user.getLibraryCard();

        theModel.addAttribute("libraryCard", libraryCard);

        return "updateProfile";
    }

    @PostMapping("/process-update-profile")
    public String processUpdateProfile(@ModelAttribute("libraryCard") LibraryCardDTO libraryCardDTO,
                                       @AuthenticationPrincipal UserDetails currentUser) {

        User user = userRepository.findByUsername(currentUser.getUsername());
        LibraryCard thelibraryCard = user.getLibraryCard();

        thelibraryCard.setFirstName(libraryCardDTO.getFirstName());
        thelibraryCard.setLastName(libraryCardDTO.getLastName());
        thelibraryCard.setPhoneNumber(libraryCardDTO.getPhoneNumber());
        thelibraryCard.setAddress(libraryCardDTO.getAddress());
        thelibraryCard.setPostalCode(libraryCardDTO.getPostalCode());
        thelibraryCard.setCity(libraryCardDTO.getCity());

        libraryCardRepository.save(thelibraryCard);

        user.setLibraryCard(thelibraryCard);
        userRepository.save(user);

        return "redirect:/update-profile?success";
    }

    @GetMapping("/user/{id}")
    public String showUserProfile(@PathVariable("id") int id, Model theModel) {

        User user = userRepository.findUserById(id);
        LibraryCard libraryCard = user.getLibraryCard();

        theModel.addAttribute("user", libraryCard);

        return "showUserProfile";
    }


    @GetMapping("/users")
    public String users(@PageableDefault(size = 5) Pageable pageable, Model theModel) {

        Page<User> userPage = userRepository.findAll(pageable);

        theModel.addAttribute("users", userPage.getContent());
        theModel.addAttribute("page", userPage);
        return "users";
    }


}
