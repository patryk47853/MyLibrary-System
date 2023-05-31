package pl.patrykjava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.patrykjava.dto.UserRegisterDTO;
import pl.patrykjava.service.UserService;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String redirectToLoginPage() {

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "home/loginPage";
    }

    @GetMapping("/home")
    public String home(){
        return "home/home";
    }

    @GetMapping("/register")
    public String registerUser(Model theModel) {

        theModel.addAttribute("user", new UserRegisterDTO());

        return "home/registerUser";
    }

    @PostMapping("/process_registration")
    public String processRegistration(@ModelAttribute("user") UserRegisterDTO userRegisterDTO) {

        userService.save(userRegisterDTO);

        return "redirect:/register?success";
    }
}
