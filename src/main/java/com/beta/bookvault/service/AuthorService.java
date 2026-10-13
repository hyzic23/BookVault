package com.beta.bookvault.service;

import com.beta.bookvault.entity.Author;
import com.beta.bookvault.excecptions.InvalidRequestException;
import com.beta.bookvault.excecptions.ResourceNotFoundException;
import com.beta.bookvault.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author saveAuthor(Author author) {
        if(author.getName() == null || author.getName().isBlank()) {
            throw new InvalidRequestException("Author name is required");
        }
        return authorRepository.save(author);
    }

    public Author getAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
    }
}
