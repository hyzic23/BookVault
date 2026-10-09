package com.beta.bookvault.service;

import com.beta.bookvault.entity.Book;
import com.beta.bookvault.entity.Tag;
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
        helper.resolveAuthor(book);
        helper.resolveTags(book);
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

        //Author
        if(updated.getAuthor() !=null && updated.getAuthor().getId() != null)
        {
            authorRepository.findById(updated.getAuthor().getId()).ifPresent(existing::setAuthor);
        }

        //Tags
        if (updated.getTags() != null) {
            existing.getTags().clear();
            for (Tag t : updated.getTags())
            {
                if (t.getId() != null) {
                    tagRepository.findById(t.getId()).ifPresent(existing.getTags()::add);
                }else if(t.getName() != null) {
                    existing.getTags().add(
                            tagRepository.findByName(t.getName())
                                    .orElseGet(() -> tagRepository.save(t))
                    );
                }
            }
        }
        return bookRepository.save(existing);
    }
}
