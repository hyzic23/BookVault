package com.beta.bookvault.service;

import com.beta.bookvault.entity.Author;
import com.beta.bookvault.entity.Book;
import com.beta.bookvault.entity.Tag;
import com.beta.bookvault.repository.AuthorRepository;
import com.beta.bookvault.repository.BookRepository;
import com.beta.bookvault.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TagRepository tagRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book saveBook(Book book) {
//        if(book.getAuthor() != null && book.getAuthor().getId() != null) {
//            Author author = authorRepository
//                    .findById(book.getAuthor().getId())
//                    .orElseThrow(() -> new RuntimeException("Author not found"));
//            book.setAuthor(author);
//        }
         resolveAuthor(book);
         resolveTags(book);
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

    //--------------------- Helpers Method --------------- //
    private void resolveAuthor(Book book) {
        if (book.getAuthor() != null && book.getAuthor().getId() != null) {
            authorRepository.findById(book.getAuthor().getId())
                    .ifPresent(book::setAuthor);
        }
    }

    private void resolveTags(Book book) {
        if (book.getTags() == null) return;
        Set<Tag> resolved = new HashSet<>();
        for (Tag t : book.getTags()){
            if (t.getId() != null) {
                tagRepository.findById(t.getId()).ifPresent(resolved::add);
            }else if (t.getName() != null) {
                Tag tag = tagRepository.findByName(t.getName())
                        .orElseGet(() -> tagRepository.save(t));
                resolved.add(tag);
            }
        }
        book.setTags(resolved);
    }

}
