package edu.icet.controller;


import edu.icet.dto.CategoryDTO;
import edu.icet.entity.Category;
import edu.icet.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/category")
@RequiredArgsConstructor
public class CategoryController {
    final CategoryService categoryService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDTO categoryDTO ){
        System.out.println("category eka"+ categoryDTO);
            Category category=categoryService.createCategory(categoryDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

}
