package edu.icet.service;

import edu.icet.dto.CategoryDTO;
import edu.icet.entity.Category;

public interface CategoryService {

    public Category createCategory(CategoryDTO categoryDTO);
}
