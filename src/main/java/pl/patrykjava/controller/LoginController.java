package pl.patrykjava.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.patrykjava.entity.User;
import pl.patrykjava.repository.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String loginPage() {
        return "loginPage";
    }

    @PostMapping("/home")
    public String home(){

        return "home";
    }

    @GetMapping("/register")
    public String registerUser(Model theModel) {

        theModel.addAttribute("user", new User());

        return "registerUser";
    }

    @PostMapping("/process_register")
    public String processRegistration(User user) {

        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        user.setActive(false);

        userRepository.save(user);

        return "registerSuccess";
    }
}
