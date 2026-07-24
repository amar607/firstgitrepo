package com.amar.blog.service;

import com.amar.blog.dto.CategoryDTO;
import com.amar.blog.entity.Category;
import com.amar.blog.exceptions.ResourceNotFoundException;
import com.amar.blog.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);
        CategoryDTO saveCategoryDTO = modelMapper.map(savedCategory, CategoryDTO.class);
        return saveCategoryDTO;
    }

    @Override
    public CategoryDTO getCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category", "id", categoryId));
        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
        return categoryDTO;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoriesDTOS = categories.stream().map((category -> modelMapper.map(category, CategoryDTO.class))).collect(Collectors.toList());
        return categoriesDTOS;
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category", "id", categoryId));

        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());

        Category updatedCategory = categoryRepository.save(category);
        CategoryDTO updatedCategoryDTO = modelMapper.map(updatedCategory, CategoryDTO.class);
        return updatedCategoryDTO;
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category", "id", categoryId));
        categoryRepository.delete(category);
    }
}
