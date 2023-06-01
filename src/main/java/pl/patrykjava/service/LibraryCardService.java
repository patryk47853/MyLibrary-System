package pl.patrykjava.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.patrykjava.dto.LibraryCardDTO;
import pl.patrykjava.entity.LibraryCard;
import pl.patrykjava.entity.User;
import pl.patrykjava.repository.LibraryCardRepository;
import pl.patrykjava.repository.UserRepository;

public interface LibraryCardService extends UserDetailsService {

    LibraryCard save(LibraryCardDTO libraryCardDTO);
    String createLibraryCard(LibraryCardDTO libraryCardDTO, UserDetails currentUser);
    LibraryCard getLibraryCardByUsername(String username);
    void updateLibraryCard(@ModelAttribute("libraryCard") LibraryCardDTO libraryCardDTO, User user,
                                  LibraryCardRepository libraryCardRepository, UserRepository userRepository);
}
