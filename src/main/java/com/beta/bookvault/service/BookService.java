package com.beta.bookvault.service;

import com.beta.bookvault.entity.Author;
import com.beta.bookvault.entity.Book;
import com.beta.bookvault.excecptions.InvalidRequestException;
import com.beta.bookvault.excecptions.ResourceNotFoundException;
import com.beta.bookvault.helper.BookVaultHelper;
import com.beta.bookvault.repository.AuthorRepository;
import com.beta.bookvault.repository.BookRepository;
import com.beta.bookvault.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private final BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TagRepository tagRepository;

    private final BookVaultHelper helper;

    public BookService(BookVaultHelper helper, BookRepository bookRepository) {
        this.helper = helper;
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAllWithTagsAndAuthor();
    }

    public Book saveBook(Book book) {
        if(book.getTitle() == null || book.getTitle().isBlank()) {
            throw new InvalidRequestException("Book title is required");
        }
        helper.resolveAuthor(book);
        helper.resolveTags(book);
        return bookRepository.save(book);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() ->  new ResourceNotFoundException("Book not found with id: " + id));
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    //Update an Existing book
    public Book updateBook(Long id, Book updated) {
        Book existing = getBookById(id);    // Throw 404 if missing

        if (updated.getTitle() != null) existing.setTitle(updated.getTitle());
        if (updated.getYear() > 0)  existing.setYear(updated.getYear());

        if(updated.getAuthor() != null && updated.getAuthor().getId() != null) {
            Author author = authorRepository.findById(updated.getAuthor().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + updated.getAuthor().getId()));
            existing.setAuthor(author);
        }

        if(updated.getTags() != null){
            existing.getTags().clear();
            existing.getTags().addAll(helper.resolveTagsFrom(updated.getTags()));
        }
        return bookRepository.save(existing);
    }
}
