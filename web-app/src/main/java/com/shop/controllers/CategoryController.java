package com.shop.controllers;

import com.shop.models.Category;
import com.shop.services.CategoryService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = CategoryController.CATEGORIES_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {
    public static final String CATEGORIES_URL = "/web-api/categories";
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> findAllCategories() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public Category findByIdCategory(@PathVariable long id) {
        return categoryService.findById(id);
    }

    @PostMapping
    public Category saveCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    @PostMapping("/{name}")
    public String deleteCategory(@PathVariable String name) {
        categoryService.delete(name);
        return "Category Successfully Deleted";
    }
}
