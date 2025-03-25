package com.motorcycleparts.ecommerce.services;

import com.motorcycleparts.ecommerce.exception.BadRequestException;
import com.motorcycleparts.ecommerce.exception.ResourceNotFoundException;
import com.motorcycleparts.ecommerce.models.Tag;
import com.motorcycleparts.ecommerce.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Tag getTagById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found"));
    }

    public Tag getTagByName(String name) {
        return tagRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with name: " + name));
    }

    @Transactional
    public Tag createTag(String name) {
        Optional<Tag> existingTag = tagRepository.findByName(name);
        if (existingTag.isPresent()) {
            throw new BadRequestException("Tag with name '" + name + "' already exists");
        }
        Tag tag = new Tag(name);
        return tagRepository.save(tag);
    }

    @Transactional
    public Tag updateTag(Long id, String name) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found"));
        
        // Check if another tag with the same name already exists
        Optional<Tag> existingTag = tagRepository.findByName(name);
        if (existingTag.isPresent() && !existingTag.get().getId().equals(id)) {
            throw new BadRequestException("Another tag with name '" + name + "' already exists");
        }
        
        tag.setName(name);
        return tagRepository.save(tag);
    }

    @Transactional
    public void deleteTag(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found"));
        tagRepository.delete(tag);
    }
} 