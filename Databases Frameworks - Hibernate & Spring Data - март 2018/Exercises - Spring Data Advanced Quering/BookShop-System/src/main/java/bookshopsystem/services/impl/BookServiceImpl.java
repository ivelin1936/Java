package bookshopsystem.services.impl;

import bookshopsystem.dto.BookDto;
import bookshopsystem.enums.AgeRestriction;
import bookshopsystem.enums.EditionType;
import bookshopsystem.models.entity.Author;
import bookshopsystem.models.entity.Book;
import bookshopsystem.repositories.BookRepository;
import bookshopsystem.services.interfaces.BookService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Override
    public void saveBookInToDb(Book book) {
        this.bookRepository.saveAndFlush(book);
    }

    @Override
    public List<Book> allTitlesAfterYear(Date year) {
        return this.bookRepository.findAllByReleaseDateAfter(year);
    }

    @Override
    public List<Book> allBooksByAuthor(Author author) {
        return this.bookRepository.findAllByAuthor(author);
    }

    @Override
    public List<Book> allByAuthorAndReleaseDateBefore(Author author, Date date) {
        return this.bookRepository.findAllByAuthorAndReleaseDateBefore(author, date);
    }

    @Override
    public List<Book> allByAuthorOrderByReleaseDateDescTitleAsc(Author author) {
        return this.bookRepository.findAllByAuthorOrderByReleaseDateDescTitleAsc(author);
    }

    @Override
    public List<String> allBooksByRestriction(AgeRestriction restriction) {
        return this.bookRepository.allBooksByRestriction(restriction);
    }

    @Override
    public List<String> allGoldenEditionBooksWithLessThan5000Copies(EditionType editionType) {
        return this.bookRepository.allGoldenEditionBooksWithLessThan5000Copies(editionType);
    }

    @Override
    public List<BookDto> allBooksByPrice() {
        return this.bookRepository.allBooksByPrice();
    }

    @Override
    public List<String> notReleasedBooksOnGivenYear(Date date) {
        return this.bookRepository.notReleasedBooksOnGivenYear(date);
    }

    @Override
    public List<BookDto> booksReleasedBeforeDate(Date date) {
        return this.bookRepository.booksReleasedBeforeDate(date);
    }

    @Override
    public List<Book> findAllByTitleContains(String str) {
        return this.bookRepository.findAllByTitleContains(str);
    }

    @Override
    public List<Book> bookTitleSearchByAuthorLastNameStartingWith(String str) {
        String likeExpression = str + "%";
        return this.bookRepository.bookTitleSearchByAuthorLastNameStartingWith(likeExpression);
    }

    @Override
    public int countBookByTitleGreaterThan(Integer number) {
        return this.bookRepository.countBookByTitleGreaterThan(number);
    }
}
