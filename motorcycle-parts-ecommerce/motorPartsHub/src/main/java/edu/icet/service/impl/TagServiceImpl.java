package edu.icet.service.impl;

import edu.icet.entity.Tag;
import edu.icet.repository.TagRepository;
import edu.icet.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    final TagRepository tagRepository;

    @Override
    public Tag createTag(String name) {
        Tag tag = new Tag();
        tag.setName(name);

       return tagRepository.save(tag);
    }
}