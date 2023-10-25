package pl.patrykjava.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.patrykjava.dto.BookDTO;
import pl.patrykjava.entity.Book;
import pl.patrykjava.entity.LibraryCard;
import pl.patrykjava.repository.BookRepository;
import pl.patrykjava.repository.LibraryCardRepository;
import pl.patrykjava.service.LibrarianService;

@Controller
@RequestMapping("/librarian")
public class LibrarianController {

    private final LibrarianService librarianService;
    private final LibraryCardRepository libraryCardRepository;
    private final BookRepository bookRepository;

    public LibrarianController(LibrarianService librarianService, LibraryCardRepository libraryCardRepository, BookRepository bookRepository) {
        this.librarianService = librarianService;
        this.libraryCardRepository = libraryCardRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/dashboard")
    public String showDashboard() {
        return "librarian/librarianDashboard";
    }

    @GetMapping("/books")
    public String showSearchResults(@RequestParam(value = "query", required = false) String query, @RequestParam(value = "page", defaultValue = "0") int page, Model model) {
        Long startIndex = page * 8L;
        int booksPerPage = 8;
        List<BookDTO> books = new ArrayList<>();
        int totalRecords = 0;

        if (query != null) {
            books = librarianService.searchBooks(query, startIndex);
            totalRecords = 60;
        }

        model.addAttribute("books", books);
        model.addAttribute("query", query);
        model.addAttribute("totalPages", (int) Math.ceil(totalRecords / (double) booksPerPage));
        model.addAttribute("currentPage", page);

        return "librarian/librarianSearchBooks";
    }

    @GetMapping("/check-details")
    public String checkBookDetails(@RequestParam(value = "query") String query, Model model) {
        BookDTO bookDTO = librarianService.searchBookByGoogleBooksId(query);

        Book book = bookRepository.findBookByGoogleBooksId(query);
        boolean bookExistsInLibrary = book != null;

        model.addAttribute("query", query);
        model.addAttribute("book", bookDTO);

        model.addAttribute("bookExistsInLibrary", bookExistsInLibrary);

        return "librarian/librarianCheckBookDetails";
    }

    @PostMapping("/add-book")
    public String addBook(@RequestParam(value = "query") String query, Model model) {
        BookDTO bookDTO = new BookDTO();

        if (query != null) bookDTO = librarianService.searchBookByGoogleBooksId(query);

        librarianService.addBookToLibrary(bookDTO);

        model.addAttribute("query", query);
        model.addAttribute("book", bookDTO);

        return "redirect:/librarian/check-details?query=" + query + "&success";
    }

    @PostMapping("/delete-book")
    public String deleteBook(@RequestParam(value = "query") String query, Model model) {
        Book book = bookRepository.findBookByGoogleBooksId(query);

        librarianService.deleteBookFromLibrary(book);

        model.addAttribute("query", query);
        model.addAttribute("book", book);

        return "redirect:/librarian/check-details?query=" + query + "&deleted";
    }

    @GetMapping("/readers")
    public String showReaders(@RequestParam(value = "phoneNumber", required = false) String phoneNumber, @PageableDefault(size = 5) Pageable pageable, Model theModel) {
        Page<LibraryCard> libraryCards;

        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            libraryCards = libraryCardRepository.findByPhoneNumber(phoneNumber, pageable);
        } else {
            libraryCards = libraryCardRepository.findAll(pageable);
        }

        theModel.addAttribute("readers", libraryCards.getContent());
        theModel.addAttribute("libraryCards", libraryCards);

        long totalReaders = libraryCardRepository.count();
        theModel.addAttribute("totalReaders", totalReaders);

        return "librarian/librarianReaders";
    }
}
