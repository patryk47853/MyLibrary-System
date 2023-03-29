package pl.patrykjava.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.patrykjava.dto.LibraryCardDTO;
import pl.patrykjava.entity.LibraryCard;

public interface LibraryCardService extends UserDetailsService {

    LibraryCard save(LibraryCardDTO libraryCardDTO);
}
