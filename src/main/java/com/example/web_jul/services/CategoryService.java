package com.example.web_jul.services;

import com.example.web_jul.entities.Category;
import com.example.web_jul.repositories.category.CategoryRepository;

import javax.inject.Inject;
import java.util.List;

/**
 * Created on 10.07.2022. by Andrija inside package com.example.web_jul.services.
 */
public class CategoryService {

    @Inject
    CategoryRepository categoryRepository;

    public List<Category> getCategoriesByPage(Integer page) {
        return this.categoryRepository.getCategories(page);
    }

    public Category findById(Integer id) {
        return this.categoryRepository.findCategoryById(id);
    }

    public Category insertCategory(Category category) {
        return this.categoryRepository.insertCategory(category);
    }
    public Category findByName(String name) {
        return this.categoryRepository.findCategoryByName(name);
    }

    public Boolean updateCategory(Category category) {
        return this.categoryRepository.updateCategory(category);
    }

    public Boolean deleteById(Integer id) {
        return this.categoryRepository.deleteById(id);
    }

}
