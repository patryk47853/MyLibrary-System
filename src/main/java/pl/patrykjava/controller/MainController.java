package pl.patrykjava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.patrykjava.dto.UserDTO;
import pl.patrykjava.service.UserService;

@Controller
public class MainController {

    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String redirectToLoginPage() {

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "main/loginPage";
    }

    @GetMapping("/home")
    public String home(){
        return "main/home";
    }

    @GetMapping("/register")
    public String registerUser(Model theModel) {

        theModel.addAttribute("user", new UserDTO());
        theModel.addAttribute("error", false);

        return "main/registerUser";
    }

    @PostMapping("/process_registration")
    public String processRegistration(@ModelAttribute("user") UserDTO userDTO, Model theModel) {
        if (userService.isUsernameTaken(userDTO.getUsername()) || userService.isEmailTaken(userDTO.getEmail())) {
            theModel.addAttribute("error", true);
            return "main/registerUser";
        }

        userService.save(userDTO);

        return "redirect:/register?success";
    }
}
