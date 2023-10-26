package pl.patrykjava.service;

import com.google.api.services.books.model.Volume;
import org.springframework.stereotype.Service;
import pl.patrykjava.dto.BookDTO;
import pl.patrykjava.entity.Author;
import pl.patrykjava.entity.Book;
import pl.patrykjava.entity.Category;
import pl.patrykjava.repository.AuthorRepository;
import pl.patrykjava.repository.BookRepository;
import pl.patrykjava.repository.CategoryRepository;

import java.util.*;

@Service
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;

    public BookServiceImpl(AuthorRepository authorRepository, CategoryRepository categoryRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Book convertBookDtoToBookEntity(BookDTO bookDTO) {
        Book book = new Book();

        Set<Author> authors = new HashSet<>();
        for (String authorName : bookDTO.getAuthors()) {
            Author author = authorRepository.findByName(authorName);
            if (author == null) {
                author = new Author(authorName);
                authorRepository.save(author);
            }
            authors.add(author);
        }

        Set<Category> categories = new HashSet<>();
        for (String categoryName : bookDTO.getCategories()) {
            Category category = categoryRepository.findByName(categoryName);
            if (category == null) {
                category = new Category(categoryName);
                categoryRepository.save(category);
            }
            categories.add(category);
        }

        book.setGoogleBooksId(bookDTO.getGoogleBooksId());
        book.setTitle(bookDTO.getTitle());
        book.setAuthors(authors);
        book.setPublisher(bookDTO.getPublisher());
        book.setPublishedDate(bookDTO.getPublishedDate());
        book.setPageCount(bookDTO.getPageCount());
        book.setCategories(categories);
        book.setAverageRating(bookDTO.getAverageRating());
        book.setCoverImageUrl(bookDTO.getCoverImageUrl());
        book.setDescription(bookDTO.getDescription());
        book.setSelfLink(bookDTO.getSelfLink());

        return book;
    }

    @Override
    public BookDTO convertBookEntityToBookDto(Book book) {
        BookDTO bookDTO = new BookDTO();

        Set<String> authors = new HashSet<>();
        for (Author author : book.getAuthors()) {
            authors.add(author.getName());
        }

        Set<String> categories = new HashSet<>();
        for (Category category : book.getCategories()) {
            categories.add(category.getName());
        }

        bookDTO.setGoogleBooksId(book.getGoogleBooksId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthors(authors);
        bookDTO.setPublisher(book.getPublisher());
        bookDTO.setPublishedDate(book.getPublishedDate());
        bookDTO.setPageCount(book.getPageCount());
        bookDTO.setCategories(categories);
        bookDTO.setAverageRating(book.getAverageRating());
        bookDTO.setCoverImageUrl(book.getCoverImageUrl());
        bookDTO.setDescription(book.getDescription());
        bookDTO.setSelfLink(book.getSelfLink());

        return bookDTO;
    }


    @Override
    public BookDTO displayBookInfoFromVolume(Volume volume) {
        return getBook(volume);
    }

    public BookDTO getBook(Volume volume) {
        BookDTO bookDTO = new BookDTO();

        List<String> categories = volume.getVolumeInfo().getCategories();
        Set<String> categorySet = new HashSet<>(categories != null ? categories : Collections.singletonList("no data"));
        bookDTO.setCategories(categorySet);

        List<String> authors = volume.getVolumeInfo().getAuthors();
        Set<String> authorSet = new HashSet<>(authors != null ? authors : Collections.singletonList("no data"));
        bookDTO.setAuthors(authorSet);

        String title = volume.getVolumeInfo().getTitle();
        bookDTO.setTitle(title != null ? title : "no data");

        String coverImageUrl = (volume.getVolumeInfo().getImageLinks() != null)
                ? volume.getVolumeInfo().getImageLinks().getThumbnail()
                : "https://upload.wikimedia.org/wikipedia/commons/9/9b/No_cover.JPG";
        bookDTO.setCoverImageUrl(coverImageUrl);

        String publisher = volume.getVolumeInfo().getPublisher();
        bookDTO.setPublisher(publisher != null ? publisher : "no data");

        String publishedDate = volume.getVolumeInfo().getPublishedDate();
        bookDTO.setPublishedDate(publishedDate != null ? publishedDate : "no data");

        Integer pageCount = volume.getVolumeInfo().getPageCount();
        bookDTO.setPageCount(pageCount != null && pageCount != 0 ? pageCount.toString() : "no data");

        String googleBooksId = volume.getId();
        bookDTO.setGoogleBooksId(googleBooksId != null ? googleBooksId : "");

        Double averageRating = volume.getVolumeInfo().getAverageRating();
        bookDTO.setAverageRating(averageRating != null ? averageRating : 5.0);

        String description = volume.getVolumeInfo().getDescription();
        bookDTO.setDescription(description != null ? description : "No description available.");

        String selfLink = volume.getSelfLink();
        bookDTO.setSelfLink(selfLink != null ? selfLink : "http://localhost:8080/home");

        return bookDTO;
    }

    @Override
    public List<Book> searchBooks(String query) {
        List<Book> books = new ArrayList<>();

        if (query != null) books = bookRepository.findBooksByTitleContaining(query);

        return books;
    }

    @Override
    public List<Book> getPaginatedBooks(String query, int page, int booksPerPage) {
        List<Book> allBooks = searchBooks(query);
        booksPerPage = 6;

        int totalRecords = allBooks.size();

        int startIndex = page * booksPerPage;
        int endIndex = Math.min(startIndex + booksPerPage, totalRecords);

        if (startIndex > endIndex) {
            startIndex = Math.max(0, totalRecords - booksPerPage);
            endIndex = totalRecords;
        }

        return allBooks.subList(startIndex, endIndex);
    }

    @Override
    public int getTotalRecords(String query, int booksPerPage) {
        return (int) Math.ceil(bookRepository.findBooksByTitleContaining(query).size() / (double) booksPerPage);
    }
}
