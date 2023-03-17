package pl.patrykjava.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.patrykjava.entity.User;


@Controller
public class LoginController {

    @GetMapping("/")
    public String loginPage() {
        return "loginPage";
    }

    @GetMapping("/register")
    public String registerUser(Model theModel) {

        theModel.addAttribute("user", new User());

        return "registerUser";
    }
}
