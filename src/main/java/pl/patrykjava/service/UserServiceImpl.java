package pl.patrykjava.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.patrykjava.dto.UserDTO;
import pl.patrykjava.entity.Role;
import pl.patrykjava.entity.User;
import pl.patrykjava.repository.RoleRepository;
import pl.patrykjava.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User save(UserDTO userDTO) {
        User user = new User(
                userDTO.getUsername(),
                userDTO.getEmail(),
                passwordEncoder.encode(userDTO.getPassword()),
                userDTO.getCreatedAt()
        );

        // add USER role when processing registration
        Role userRole = roleRepository.findByName("USER");
        user.addRole(userRole);

        return userRepository.save(user);
    }

    @Override
    public String validateRegistration(UserDTO userDTO, Model theModel) {
        if (isUsernameTaken(userDTO.getUsername()) || isEmailTaken(userDTO.getEmail())) {
            theModel.addAttribute("error", true);
            return "main/registerUser";
        }

        return "redirect:/register?success";
    }

    @Override
    public boolean isUsernameTaken(String username) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(username));
        return userOptional.isPresent();
    }

    @Override
    public boolean isEmailTaken(String email) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(email));
        return userOptional.isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }

        return new org.springframework.security.core.userdetails
                .User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {

        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}