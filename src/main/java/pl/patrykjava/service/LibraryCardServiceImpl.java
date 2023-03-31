package pl.patrykjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.patrykjava.dto.LibraryCardDTO;
import pl.patrykjava.entity.LibraryCard;
import pl.patrykjava.repository.LibraryCardRepository;
import pl.patrykjava.repository.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class LibraryCardServiceImpl implements LibraryCardService {

    @Autowired
    LibraryCardRepository libraryCardRepository;

    @Autowired
    UserRepository userRepository;

    public LibraryCardServiceImpl(LibraryCardRepository libraryCardRepository) {
        this.libraryCardRepository = libraryCardRepository;
    }

    @Override
    public LibraryCard save(LibraryCardDTO libraryCardDTO) {
        LibraryCard libraryCard = new LibraryCard(libraryCardDTO.getFirstName(),
                libraryCardDTO.getLastName(),
                libraryCardDTO.getPhoneNumber(),
                libraryCardDTO.getAddress(),
                libraryCardDTO.getPostalCode(),
                libraryCardDTO.getCity());

        libraryCard.setCreatedAt(Timestamp.valueOf(LocalDateTime.now().plusHours(2L)));

        return libraryCardRepository.save(libraryCard);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
