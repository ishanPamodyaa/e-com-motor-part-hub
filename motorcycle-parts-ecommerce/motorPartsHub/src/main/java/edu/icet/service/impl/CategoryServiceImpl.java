package edu.icet.service.impl;

import edu.icet.dto.CategoryDTO;
import edu.icet.entity.Category;
import edu.icet.repository.CategoryRepository;
import edu.icet.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    final CategoryRepository categoryRepository;
    final ModelMapper mapper;

    @Override
    public void createCategory(CategoryDTO categoryDTO){
        categoryRepository.save(mapper.map(categoryDTO , Category.class));
    }
}
