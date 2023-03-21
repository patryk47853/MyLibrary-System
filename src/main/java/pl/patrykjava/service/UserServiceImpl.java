package pl.patrykjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.patrykjava.MyUserDetails;
import pl.patrykjava.dto.UserRegisterDTO;
import pl.patrykjava.entity.User;
import pl.patrykjava.repository.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(UserRegisterDTO userRegisterDTO) {
        User user = new User(userRegisterDTO.getUsername(),
                userRegisterDTO.getEmail(),
                userRegisterDTO.getPassword());

        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now().plusHours(1L)));

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new MyUserDetails(user);
    }
}
