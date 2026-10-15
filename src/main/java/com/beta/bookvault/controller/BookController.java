package com.beta.bookvault.controller;

import com.beta.bookvault.dtos.BookRequest;
import com.beta.bookvault.dtos.BookResponse;
import com.beta.bookvault.dtos.PageResponse;
import com.beta.bookvault.entity.Book;
import com.beta.bookvault.service.AuthorService;
import com.beta.bookvault.service.BookService;
import com.beta.bookvault.service.TagService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private TagService tagService;

    @GetMapping
    public PageResponse<BookResponse> getAllBooks(
            @PageableDefault(size = 10, sort = "title") Pageable pageable) {
        return bookService.getAllBooks(pageable);
    }

    @GetMapping("/{id}")
    public BookResponse getBook(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest book) {
        BookResponse saved = bookService.saveBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved); // 201
    }

    @PutMapping("/{id}")
    public BookResponse updateBook(@PathVariable("id") Long id, @RequestBody BookRequest book) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();  // 204
    }
}
