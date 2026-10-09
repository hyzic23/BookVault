package com.beta.bookvault.repository;

import com.beta.bookvault.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthor(String author);
    List<Book> findByYearGreaterThan(int year);

    //The JOIN FETCH loads everything in one SQL Query - No N+1 problem, no lazy errors
    @Query("SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.tags LEFT JOIN FETCH b.author")
    List<Book> findAllWithTagsAndAuthor();
}
