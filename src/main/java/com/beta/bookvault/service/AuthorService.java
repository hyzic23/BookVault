package com.beta.bookvault.service;

import com.beta.bookvault.entity.Author;
import com.beta.bookvault.excecptions.InvalidRequestException;
import com.beta.bookvault.excecptions.ResourceNotFoundException;
import com.beta.bookvault.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    private static final Set<String> ALLOWED_SORTS = Set.of("title", "year", "author.name", "id");

    @Transactional(readOnly = true)
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

    private Pageable sanitize(Pageable pageable) {
        List<Sort.Order> valid = pageable.getSort().stream()
                .filter(o -> ALLOWED_SORTS.contains(o.getProperty()))
                .toList();
        return PageRequest.of(
                pageable.getPageNumber(),
                Math.min(pageable.getPageSize(), 100),
                Sort.by(valid)
        );
    }

}
