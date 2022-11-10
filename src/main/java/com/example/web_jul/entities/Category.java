package com.example.web_jul.entities;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created on 09.07.2022. by Andrija inside package com.example.web_jul.entities.
 */
public class Category {

    private Integer categoryId ;

    @NotNull(message = "Email field is required")
    @NotEmpty(message = "Email field is required")
    private String name ;

    @NotNull(message = "First name field is required")
    @NotEmpty(message = "First name field is required")
    private String description;


    public Category() {
    }

    public Category(Integer categoryId, String name, String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
    }


    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
