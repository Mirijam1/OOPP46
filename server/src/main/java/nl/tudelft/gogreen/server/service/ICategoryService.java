package nl.tudelft.gogreen.server.service;

import nl.tudelft.gogreen.server.models.activity.Category;

import java.util.Collection;

public interface ICategoryService {
    Collection<Category> getAllCategories();

    Category getCategory(String name);
}
