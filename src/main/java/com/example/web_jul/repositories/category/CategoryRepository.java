package com.example.web_jul.repositories.category;

import com.example.web_jul.entities.Category;

import java.util.List;

/**
 * Created on 10.07.2022. by Andrija inside package com.example.web_jul.repositories.category.
 */
public interface CategoryRepository {

    public List<Category> getCategories(int page);
    public Category findCategoryById(int id);
    public Category findCategoryByName(String name);
    public Category insertCategory(Category category);
    public Boolean updateCategory(Category category);
    public Boolean deleteById(Integer id);


}