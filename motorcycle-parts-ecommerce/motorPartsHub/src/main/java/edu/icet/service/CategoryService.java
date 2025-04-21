package edu.icet.service;

import edu.icet.dto.CategoryDTO;
import edu.icet.entity.Category;

import java.util.Locale;

public interface CategoryService {

    public Category createCategory(CategoryDTO categoryDTO);
}
