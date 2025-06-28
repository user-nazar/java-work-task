package com.example.library.service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book createOrUpdateBook(Book book) {
        Optional<Book> existing = bookRepository.findByTitleAndAuthor(book.getTitle(), book.getAuthor());
        if (existing.isPresent()) {
            Book found = existing.get();
            found.setAmount(found.getAmount() + 1);
            return bookRepository.save(found);
        } else {
            return bookRepository.save(book);
        }
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found with id: " + id));
    }

    public Book updateBook(Long id, Book updatedBook) {
        Book existing = getById(id);
        existing.setTitle(updatedBook.getTitle());
        existing.setAuthor(updatedBook.getAuthor());
        existing.setAmount(updatedBook.getAmount());
        return bookRepository.save(existing);
    }

    public void deleteBook(Long id) {
        Book book = getById(id);
        if (!book.getBorrowedBy().isEmpty()) {
            throw new IllegalStateException("Cannot delete book, it's currently borrowed.");
        }
        bookRepository.delete(book);
    }

    public List<String> getBorrowedBookTitles() {
        return bookRepository.findAll().stream()
                .filter(book -> !book.getBorrowedBy().isEmpty())
                .map(Book::getTitle)
                .distinct()
                .collect(Collectors.toList());
    }

    public Map<String, Long> getBorrowedBookStats() {
        return bookRepository.findAll().stream()
                .filter(book -> !book.getBorrowedBy().isEmpty())
                .collect(Collectors.groupingBy(Book::getTitle, Collectors.counting()));
    }
}
