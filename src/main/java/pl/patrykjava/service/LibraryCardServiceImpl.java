package pl.patrykjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.patrykjava.dto.LibraryCardDTO;
import pl.patrykjava.entity.LibraryCard;
import pl.patrykjava.entity.Role;
import pl.patrykjava.entity.User;
import pl.patrykjava.entity.UsersRoles;
import pl.patrykjava.repository.LibraryCardRepository;
import pl.patrykjava.repository.RoleRepository;
import pl.patrykjava.repository.UserRepository;
import pl.patrykjava.repository.UsersRolesRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Service
public class LibraryCardServiceImpl implements LibraryCardService {

    @Autowired
    LibraryCardRepository libraryCardRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UsersRolesRepository usersRolesRepository;

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

        // Delete 'User' role --> user becomes 'Reader'
        UsersRoles usersRoles = new UsersRoles(user.getId(), 1);
        usersRolesRepository.delete(usersRoles);


        // If user got the library card, he should become 'Reader' and have access to the books
        Role roleReader = roleRepository.findByName("READER");
        user.addRole(roleReader);

        userRepository.save(user);

        return libraryCard;
    }

    private void addRoleAndCard(LibraryCard libraryCard, RoleRepository roleRepository, @AuthenticationPrincipal UserDetails currentUser) {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
