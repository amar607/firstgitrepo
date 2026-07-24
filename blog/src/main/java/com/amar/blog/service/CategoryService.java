package com.amar.blog.service;

import com.amar.blog.dto.CategoryDTO;
import com.amar.blog.entity.Category;

import java.util.List;

public interface CategoryService {
    CategoryDTO addCategory(CategoryDTO categoryDTO);
    CategoryDTO getCategory(Long categoryId);

    List<CategoryDTO> getAllCategories();

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);

    void deleteCategory(Long categoryId);
}
