package edu.icet.controller;


import edu.icet.dto.CategoryDTO;
import edu.icet.service.CategoryService;
import lombok.RequiredArgsConstructor;
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
    public void createCategory(@RequestBody CategoryDTO categoryDTO ){
            categoryService.createCategory(categoryDTO);
    }

}
