package pl.patrykjava.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.patrykjava.dto.LibraryCardDTO;
import pl.patrykjava.dto.UserDTO;
import pl.patrykjava.entity.LibraryCard;
import pl.patrykjava.entity.User;
import pl.patrykjava.repository.LibraryCardRepository;
import pl.patrykjava.repository.UserRepository;
import pl.patrykjava.service.LibraryCardService;
import pl.patrykjava.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final LibraryCardRepository libraryCardRepository;
    private final LibraryCardService libraryCardService;
    private final UserRepository userRepository;
    private final UserService userService;

    public AdminController(LibraryCardRepository libraryCardRepository,
                           LibraryCardService libraryCardService, UserRepository userRepository,
                           UserService userService) {
        this.libraryCardRepository = libraryCardRepository;
        this.libraryCardService = libraryCardService;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/accounts/{id}")
    public String showUserAccount(@PathVariable("id") int id, Model theModel) {
        User user = userRepository.findUserById(id);
        theModel.addAttribute("user", user);

        return "admin/showUserAccount";
    }

    @GetMapping("/update-user-account/{id}")
    public String updateUserAccount(@PathVariable("id") int id, Model theModel) {
        User user = userRepository.findUserById(id);
        theModel.addAttribute("user", user);

        return "admin/updateUserAccount";
    }

    @PostMapping("/process-update-user-account/{id}")
    public String processUpdateUserAccount(@ModelAttribute("user") UserDTO userDTO,
                                           @PathVariable("id") int id) {
        userService.updateUser(userDTO, id);

        return "redirect:/admin/update-user-account/{id}?success";
    }

    @GetMapping("/users/{id}")
    public String showUserProfile(@PathVariable("id") int id, Model theModel) {
        LibraryCard libraryCard = libraryCardService.getLibraryCardById(id);
        theModel.addAttribute("libraryCard", libraryCard);

        return "admin/showLibraryCard";
    }

    @GetMapping("/update-user-profile/{id}")
    public String updateUserLibraryCard(@PathVariable("id") int id, Model theModel) {
        LibraryCard libraryCard = libraryCardService.getLibraryCardById(id);
        theModel.addAttribute("libraryCard", libraryCard);

        return "admin/updateLibraryCardByAdmin";
    }

    @PostMapping("/process-update-library-card/{id}")
    public String processUpdateLibraryCard(@ModelAttribute("libraryCard") LibraryCardDTO libraryCardDTO,
                                           @PathVariable("id") int id) {
        libraryCardService.updateLibraryCard(libraryCardDTO, id, libraryCardRepository, userRepository);

        return "redirect:/admin/update-user-profile/{id}?success";
    }


    @GetMapping("/users")
    public String showUsers(@RequestParam(value = "username", required = false) String username, @PageableDefault(size = 5) Pageable pageable, Model theModel) {
        Page<User> userPage;

        if (username != null && !username.isEmpty()) {
            userPage = userRepository.findByUsernameContainingIgnoreCase(username, pageable);
        } else {
            userPage = userRepository.findAll(pageable);
        }

        theModel.addAttribute("users", userPage.getContent());
        theModel.addAttribute("page", userPage);

        long totalUsers = userRepository.count();
        theModel.addAttribute("totalUsers", totalUsers);

        return "admin/users";
    }
}
