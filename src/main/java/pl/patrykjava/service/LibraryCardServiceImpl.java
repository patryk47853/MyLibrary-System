package pl.patrykjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.patrykjava.dto.LibraryCardDTO;
import pl.patrykjava.entity.LibraryCard;
import pl.patrykjava.entity.Role;
import pl.patrykjava.entity.User;
import pl.patrykjava.entity.UserRole;
import pl.patrykjava.repository.LibraryCardRepository;
import pl.patrykjava.repository.RoleRepository;
import pl.patrykjava.repository.UserRepository;
import pl.patrykjava.repository.UserRoleRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class LibraryCardServiceImpl implements LibraryCardService {

    @Autowired
    LibraryCardRepository libraryCardRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    public LibraryCardServiceImpl(LibraryCardRepository libraryCardRepository) {
        this.libraryCardRepository = libraryCardRepository;
    }

    @Override
    public LibraryCard save(LibraryCardDTO libraryCardDTO) {

        // Get the authentication object for the current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        // We should save library card to existing user
        User user = userRepository.findByUsername(username);

        LibraryCard libraryCard = new LibraryCard(libraryCardDTO.getFirstName(),
                libraryCardDTO.getLastName(),
                libraryCardDTO.getPhoneNumber(),
                libraryCardDTO.getAddress(),
                libraryCardDTO.getPostalCode(),
                libraryCardDTO.getCity());

        libraryCard.setCreatedAt(Timestamp.valueOf(LocalDateTime.now().plusHours(2L)));

        libraryCard.setUser(user);
        user.setLibraryCard(libraryCard);

        // Delete 'User' role (ID:1) --> user becomes 'Reader'
        UserRole usersRoles = new UserRole(user.getId(), 1);
        userRoleRepository.delete(usersRoles);


        // If user got the library card, he should become 'Reader' and have access to the books
        Role roleReader = roleRepository.findByName("READER");
        user.addRole(roleReader);

        userRepository.save(user);

        return libraryCard;
    }

    @Override
    public String createLibraryCard(LibraryCardDTO libraryCardDTO, UserDetails currentUser) {
        User user = userRepository.findByUsername(currentUser.getUsername());
        if (user.getLibraryCard() != null) {
            return "templates/error";
        }

        // Perform the necessary operations to create the library card
        save(libraryCardDTO);

        return "success";
    }

    public LibraryCard getLibraryCardByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user.getLibraryCard();
        }
        return null;
    }

    public void updateLibraryCard(@ModelAttribute("libraryCard") LibraryCardDTO libraryCardDTO, User user,
                                  LibraryCardRepository libraryCardRepository, UserRepository userRepository) {
        LibraryCard thelibraryCard = user.getLibraryCard();

        thelibraryCard.setFirstName(libraryCardDTO.getFirstName());
        thelibraryCard.setLastName(libraryCardDTO.getLastName());
        thelibraryCard.setPhoneNumber(libraryCardDTO.getPhoneNumber());
        thelibraryCard.setAddress(libraryCardDTO.getAddress());
        thelibraryCard.setPostalCode(libraryCardDTO.getPostalCode());
        thelibraryCard.setCity(libraryCardDTO.getCity());

        libraryCardRepository.save(thelibraryCard);

        user.setLibraryCard(thelibraryCard);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
