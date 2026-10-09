package com.beta.bookvault.service;

import com.beta.bookvault.entity.Tag;
import com.beta.bookvault.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Tag saveTag(Tag tag) {
        //Avoids duplicate tag by name
        return tagRepository.findByName(tag.getName())
                .orElseGet(() -> tagRepository.save(tag));
    }

    public Tag getTagById(Long id) {
        return tagRepository.findById(id).orElse(null);
    }
}
