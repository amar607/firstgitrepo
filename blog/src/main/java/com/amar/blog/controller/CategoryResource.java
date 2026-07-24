package com.amar.blog.controller;

import com.amar.blog.dto.CategoryDTO;
import com.amar.blog.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
@Tag(
        name = "CRUD Rest APIs for Category Resource"
)
public class CategoryResource {

    private CategoryService categoryService;

    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @SecurityRequirement(
            name="Bearer Authentication"
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategory = categoryService.addCategory(categoryDTO);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable(name = "id") Long categoryId) {
        CategoryDTO category = categoryService.getCategory(categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @SecurityRequirement(
            name="Bearer Authentication"
    )
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable(name = "id") Long categoryId) {
        CategoryDTO updatedCategory = categoryService.updateCategory(categoryDTO, categoryId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @SecurityRequirement(
            name="Bearer Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable(name = "id")Long categoryId){
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>("Category successfully deleted", HttpStatus.OK);
    }
}
