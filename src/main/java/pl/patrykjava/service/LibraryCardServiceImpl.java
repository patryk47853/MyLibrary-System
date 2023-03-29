package pl.patrykjava.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.patrykjava.dto.LibraryCardDTO;
import pl.patrykjava.entity.LibraryCard;

@Service
public class LibraryCardServiceImpl implements LibraryCardService {

    @Override
    public LibraryCard save(LibraryCardDTO libraryCardDTO) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
