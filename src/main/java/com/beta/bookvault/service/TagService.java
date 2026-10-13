package com.beta.bookvault.service;

import com.beta.bookvault.entity.Tag;
import com.beta.bookvault.excecptions.DuplicateResourceException;
import com.beta.bookvault.excecptions.InvalidRequestException;
import com.beta.bookvault.excecptions.ResourceNotFoundException;
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
        if(tag.getName() == null || tag.getName().isBlank()) {
            throw new InvalidRequestException("Tag name is required");
        }
        tagRepository.findByName(tag.getName())
                .ifPresent(existing -> {
                    throw new DuplicateResourceException("Tag already exists: " + tag.getName());
                });
        return tagRepository.save(tag);
    }

    public Tag getTagById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + id));
    }
}
