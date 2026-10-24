package com.beta.bookvault.service;

import com.beta.bookvault.dtos.BookRequest;
import com.beta.bookvault.dtos.BookResponse;
import com.beta.bookvault.entity.Author;
import com.beta.bookvault.repository.AuthorRepository;
import com.beta.bookvault.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BookIntegrationTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate rest;
    @Autowired private AuthorRepository authorRepository;
    @Autowired private BookRepository bookRepository;

    @BeforeEach
    void setup() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();

        Author author = new Author();
        author.setName("Robert Martin");
        author.setNationality("American");
        authorRepository.save(author);
    }

    @Test
    void fullLifecycle_createReadUpdateDelete() {
        Long authorId = authorRepository.findAll().get(0).getId();

        // CREATE
        BookRequest req = new BookRequest();
        req.setTitle("Clean Code");
        req.setYear(2008);
        req.setAuthorId(authorId);

        ResponseEntity<BookResponse> created = rest.postForEntity(
                url("/api/books"), req, BookResponse.class);

        assertThat(created.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(created.getBody()).isNotNull();
        Long bookId = created.getBody().getId();
        assertThat(bookId).isNotNull();

        // READ
        ResponseEntity<BookResponse> fetched = rest.getForEntity(
                url("/api/books/" + bookId), BookResponse.class);
        assertThat(fetched.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(fetched.getBody().getTitle()).isEqualTo("Clean Code");

        // UPDATE
        req.setTitle("Clean Code (2nd Ed)");
        rest.put(url("/api/books/" + bookId), req);
        ResponseEntity<BookResponse> updated = rest.getForEntity(
                url("/api/books/" + bookId), BookResponse.class);
        assertThat(updated.getBody().getTitle()).isEqualTo("Clean Code (2nd Ed)");

        // DELETE
        rest.delete(url("/api/books/" + bookId));
        ResponseEntity<String> afterDelete = rest.getForEntity(
                url("/api/books/" + bookId), String.class);
        assertThat(afterDelete.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }
}
