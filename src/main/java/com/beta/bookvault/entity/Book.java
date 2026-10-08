package com.beta.bookvault.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "published_year")
    private int year;

    //fetch = FetchType.LAZY means don't load the author until I ask for it (Efficient)
    @ManyToOne(fetch = FetchType.LAZY)  // Many books belong to one author
    @JoinColumn(name = "author_id", nullable = false)   //Use this column for foreign key
    @JsonIgnoreProperties("books")  //prevents infinite JSON recursion
    private Author author;

    //Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}