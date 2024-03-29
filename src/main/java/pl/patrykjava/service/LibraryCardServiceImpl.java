package pl.patrykjava.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.webjars.NotFoundException;
import pl.patrykjava.dto.LibraryCardDTO;
import pl.patrykjava.entity.LibraryCard;
import pl.patrykjava.entity.Role;
import pl.patrykjava.entity.User;
import pl.patrykjava.entity.UserToRoleMapping;
import pl.patrykjava.repository.LibraryCardRepository;
import pl.patrykjava.repository.RoleRepository;
import pl.patrykjava.repository.UserRepository;
import pl.patrykjava.repository.UserToRoleMappingRepository;

import java.util.Optional;

@Service
public class LibraryCardServiceImpl implements LibraryCardService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserToRoleMappingRepository userToRoleMappingRepository;

    public LibraryCardServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserToRoleMappingRepository userToRoleMappingRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userToRoleMappingRepository = userToRoleMappingRepository;
    }

    @Override
    public String createLibraryCard(LibraryCardDTO libraryCardDTO, UserDetails currentUser) {
        User user = userRepository.findByUsername(currentUser.getUsername());

        if (user.getLibraryCard() != null) {
            return "templates/error";
        }

        save(libraryCardDTO);

        return "success";
    }

    public LibraryCard getLibraryCardByUsername(String username) {
        User user = userRepository.findByUsername(username);

        if (user != null) {
            return user.getLibraryCard();
        }

        throw new NotFoundException("User " + username + " not found.");
    }

    @Override
    public LibraryCard getLibraryCardByUserId(int id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            return user.get().getLibraryCard();
        }

        throw new NotFoundException("User with ID: " + id + " not found.");
    }

    @Override
    public LibraryCard save(LibraryCardDTO libraryCardDTO) {

        // Get the authentication object for the current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // We should save library card to existing user
        User user = userRepository.findByUsername(userDetails.getUsername());

        LibraryCard libraryCard = new LibraryCard(
                libraryCardDTO.getFirstName(),
                libraryCardDTO.getLastName(),
                libraryCardDTO.getPhoneNumber(),
                libraryCardDTO.getAddress(),
                libraryCardDTO.getPostalCode(),
                libraryCardDTO.getCity(),
                libraryCardDTO.getCreatedAt()
        );

        libraryCard.setUser(user);
        user.setLibraryCard(libraryCard);

        // Delete 'User' role (ID:1) --> user becomes 'Reader'
        userToRoleMappingRepository.delete(new UserToRoleMapping(user.getId(), 1));

        // If user got the library card, he should become 'Reader' and have access to the books
        user.addRole(roleRepository.findByName("READER"));

        userRepository.save(user);

        return libraryCard;
    }

    public void updateLibraryCard(@ModelAttribute("libraryCard") LibraryCardDTO libraryCardDTO,
                                  int id,
                                  LibraryCardRepository libraryCardRepository,
                                  UserRepository userRepository) {
        User user = userRepository.findUserById(id);
        LibraryCard libraryCard = user.getLibraryCard();

        libraryCard.setFirstName(libraryCardDTO.getFirstName());
        libraryCard.setLastName(libraryCardDTO.getLastName());
        libraryCard.setPhoneNumber(libraryCardDTO.getPhoneNumber());
        libraryCard.setAddress(libraryCardDTO.getAddress());
        libraryCard.setPostalCode(libraryCardDTO.getPostalCode());
        libraryCard.setCity(libraryCardDTO.getCity());

        libraryCardRepository.save(libraryCard);

        user.setLibraryCard(libraryCard);
        userRepository.save(user);
    }

    // Currently: no usages
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
