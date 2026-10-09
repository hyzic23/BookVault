package com.beta.bookvault.controller;

import com.beta.bookvault.entity.Author;
import com.beta.bookvault.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public List<Author> getAll() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public Author getOne(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @PostMapping
    public Author create(@RequestBody Author author) {
        return authorService.saveAuthor(author);
    }


}
