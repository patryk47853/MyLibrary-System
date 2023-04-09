package pl.patrykjava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.patrykjava.dto.LibraryCardDTO;
import pl.patrykjava.dto.UserRegisterDTO;
import pl.patrykjava.entity.LibraryCard;
import pl.patrykjava.entity.User;
import pl.patrykjava.repository.LibraryCardRepository;
import pl.patrykjava.repository.UserRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private LibraryCardRepository libraryCardRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/account/{id}")
    public String showUserAccount(@PathVariable("id") int id, Model theModel) {

        User user = userRepository.findUserById(id);

        theModel.addAttribute("user", user);

        return "showUserAccount";
    }

    @GetMapping("/update-user-account/{id}")
    public String updateUserAccount(@PathVariable("id") int id, Model theModel) {

        User user = userRepository.findUserById(id);

        theModel.addAttribute("user", user);

        return "updateUserAccount";
    }

    @PostMapping("/process-update-user-account/{id}")
    public String processUpdateUserAccount(@ModelAttribute("user") UserRegisterDTO userRegisterDTO,
                                           @PathVariable("id") int id) {

        User user = userRepository.findUserById(id);

        user.setUsername(userRegisterDTO.getUsername());
        user.setEmail(userRegisterDTO.getEmail());

        userRepository.save(user);

        return "redirect:/admin/update-user-account/{id}?success";
    }

    @GetMapping("/user/{id}")
    public String showUserProfile(@PathVariable("id") int id, Model theModel) {

        User user = userRepository.findUserById(id);
        LibraryCard libraryCard = user.getLibraryCard();

        theModel.addAttribute("libraryCard", libraryCard);

        return "showUserProfile";
    }

    @GetMapping("/update-user-profile/{id}")
    public String updateUserProfile(@PathVariable("id") int id, Model theModel) {

        User user = userRepository.findUserById(id);
        LibraryCard libraryCard = user.getLibraryCard();

        theModel.addAttribute("libraryCard", libraryCard);

        return "updateUserProfile";
    }

    @PostMapping("/process-update-user-profile/{id}")
    public String processUpdateUserProfile(@ModelAttribute("libraryCard") LibraryCardDTO libraryCardDTO,
                                           @PathVariable("id") int id) {

        User user = userRepository.findUserById(id);
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

        return "redirect:/admin/update-user-profile/{id}?success";
    }


    @GetMapping("/users")
    public String users(@PageableDefault(size = 5) Pageable pageable, Model theModel) {

        Page<User> userPage = userRepository.findAll(pageable);

        theModel.addAttribute("users", userPage.getContent());
        theModel.addAttribute("page", userPage);
        return "users";
    }
}
