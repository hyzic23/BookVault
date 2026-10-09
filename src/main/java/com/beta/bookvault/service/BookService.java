package com.beta.bookvault.service;

import com.beta.bookvault.entity.Author;
import com.beta.bookvault.entity.Book;
import com.beta.bookvault.repository.AuthorRepository;
import com.beta.bookvault.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book saveBook(Book book) {
        if(book.getAuthor() != null && book.getAuthor().getId() != null) {
            Author author = authorRepository
                    .findById(book.getAuthor().getId())
                    .orElseThrow(() -> new RuntimeException("Author not found"));
            book.setAuthor(author);
        }
        return bookRepository.save(book);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    //Update an Existing book
    public Book updateBook(Long id, Book updated) {
        Book existing = bookRepository.findById(id).orElse(null);
        if(existing == null) return null;

        existing.setTitle(updated.getTitle());
        existing.setYear(updated.getYear());

        if(updated.getAuthor() !=null && updated.getAuthor().getId() != null)
        {
            Author author = authorRepository.findById(updated.getAuthor().getId()).orElse(null);
            existing.setAuthor(author);
        }
        return bookRepository.save(existing);
    }
}
