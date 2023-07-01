package pl.patrykjava.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import pl.patrykjava.dto.UserDTO;
import pl.patrykjava.entity.User;

public interface UserService extends UserDetailsService {

    User save(UserDTO userDTO);
    boolean isUsernameTaken(String username);
    boolean isEmailTaken(String email);
    String validateRegistration(UserDTO userDTO, Model theModel);

}
