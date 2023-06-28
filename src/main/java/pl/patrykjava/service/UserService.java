package pl.patrykjava.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.patrykjava.dto.UserDTO;
import pl.patrykjava.entity.User;

public interface UserService extends UserDetailsService {

    User save(UserDTO userDTO);

}
