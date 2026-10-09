package com.beta.bookvault.helper;

import com.beta.bookvault.entity.Book;
import com.beta.bookvault.entity.Tag;
import com.beta.bookvault.repository.AuthorRepository;
import com.beta.bookvault.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class BookVaultHelper {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TagRepository tagRepository;

    public void resolveAuthor(Book book) {
        if (book.getAuthor() != null && book.getAuthor().getId() != null) {
            authorRepository.findById(book.getAuthor().getId())
                    .ifPresent(book::setAuthor);
        }
    }

    public void resolveTags(Book book) {
        if (book.getTags() == null) return;
        Set<Tag> resolved = new HashSet<>();
        for (Tag t : book.getTags()){
            if (t.getId() != null) {
                tagRepository.findById(t.getId()).ifPresent(resolved::add);
            }else if (t.getName() != null) {
                Tag tag = tagRepository.findByName(t.getName())
                        .orElseGet(() -> tagRepository.save(t));
                resolved.add(tag);
            }
        }
        book.setTags(resolved);
    }
}
