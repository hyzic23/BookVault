package com.beta.bookvault.service;

import com.beta.bookvault.dtos.BookRequest;
import com.beta.bookvault.dtos.BookResponse;
import com.beta.bookvault.entity.Author;
import com.beta.bookvault.entity.Book;
import com.beta.bookvault.excecptions.InvalidRequestException;
import com.beta.bookvault.excecptions.ResourceNotFoundException;
import com.beta.bookvault.helper.BookVaultHelper;
import com.beta.bookvault.mapper.BookMapper;
import com.beta.bookvault.repository.AuthorRepository;
import com.beta.bookvault.repository.BookRepository;
import com.beta.bookvault.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private final BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private BookMapper bookMapper;

    private final BookVaultHelper helper;

    public BookService(BookVaultHelper helper, BookRepository bookRepository) {
        this.helper = helper;
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAllWithTagsAndAuthor()
                .stream()
                .map(bookMapper::toResponse)
                .toList();
    }

    @Transactional
    public BookResponse saveBook(BookRequest request) {
        Book book = bookMapper.toEntity(request);

        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Author not found with id: " + request.getAuthorId()));
        book.setAuthor(author);
        book.setTags(helper.resolveTags(request.getTagIds(),
                request.getTagNames()));
        Book saved = bookRepository.save(book);
        return bookMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() ->  new ResourceNotFoundException("Book not found with id: " + id));
        return bookMapper.toResponse(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    //Update an Existing book
    public BookResponse updateBook(Long id, BookRequest request) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        existing.setTitle(request.getTitle());
        existing.setYear(request.getYear());

        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Author not found with id: " + request.getAuthorId()));
        existing.setAuthor(author);

        existing.getTags().clear();
        existing.getTags().addAll(helper.resolveTags(request.getTagIds(), request.getTagNames()));

        // save() not strictly needed with @Transactional — see explanation below
        return bookMapper.toResponse(existing);
    }
}
