package com.beta.bookvault.Dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class BookRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @Min(value = 1450, message = "Year must be after 1450")
    private int year;

    @NotNull(message = "Author id is required")
    private Long authorId;

    // Optional: send tag ids and/or tag names
    private Set<Long> tagIds;
    private Set<String> tagNames;

    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }

    public Set<Long> getTagIds() { return tagIds; }
    public void setTagIds(Set<Long> tagIds) { this.tagIds = tagIds; }

    public Set<String> getTagNames() { return tagNames; }
    public void setTagNames(Set<String> tagNames) { this.tagNames = tagNames; }
}
