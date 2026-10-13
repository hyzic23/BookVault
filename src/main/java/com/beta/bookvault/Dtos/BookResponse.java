package com.beta.bookvault.Dtos;

import java.util.Set;

public class BookResponse {

    private Long id;
    private String title;
    private int year;
    private AuthorSummary author;
    private Set<TagSummary> tags;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public AuthorSummary getAuthor() { return author; }
    public void setAuthor(AuthorSummary author) { this.author = author; }

    public Set<TagSummary> getTags() { return tags; }
    public void setTags(Set<TagSummary> tags) { this.tags = tags; }
}
