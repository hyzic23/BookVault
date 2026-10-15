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

    public Set<Tag> resolveTags(Set<Long> tagIds, Set<String> tagNames) {
        Set<Tag> resolved = new HashSet<>();
        if (tagIds != null) {
            for (Long tid : tagIds) {
                tagRepository.findById(tid).ifPresent(resolved::add);
            }
        }
        if (tagNames != null) {
            for (String name : tagNames) {
                Tag tag = tagRepository.findByName(name)
                        .orElseGet(() -> {
                            Tag t = new Tag();
                            t.setName(name);
                            return tagRepository.save(t);
                        });
                resolved.add(tag);
            }
        }
        return resolved;
    }

    public Set<Tag> resolveTagsFrom(Set<Tag> input) {
        Set<Tag> resolved = new HashSet<>();
        for (Tag t  : input){
            if (t.getId() != null) {
                tagRepository.findById(t.getId())
                        .ifPresent(resolved::add);
            } else if (t.getName() != null && !t.getName().isBlank()) {
                Tag tag = tagRepository.findByName(t.getName())
                        .orElseGet(() -> tagRepository.save(t));
                resolved.add(tag);
            }
        }
        return resolved;
    }
}
