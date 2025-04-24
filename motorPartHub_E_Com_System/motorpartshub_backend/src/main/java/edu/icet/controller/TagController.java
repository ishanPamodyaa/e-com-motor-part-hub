package edu.icet.controller;

import edu.icet.entity.Tag;
import edu.icet.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/tag")
@RequiredArgsConstructor
public class TagController {

    final TagService tagService;

    @PostMapping("/create")
    public ResponseEntity<Tag> createTag(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        Tag tag = tagService.createTag(name);
        return new ResponseEntity<>(tag, HttpStatus.CREATED);
    }
} 