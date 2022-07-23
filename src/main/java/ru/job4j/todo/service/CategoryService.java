package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.persistence.CategoryDbStore;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryDbStore store;

    public CategoryService(CategoryDbStore store) {
        this.store = store;
    }

    public List<Category> findAll() {
        return store.findAll();
    }

    public Category findById(int id) {
        return store.findById(id);
    }

    public Category add(Category category) {
        return store.add(category);
    }

    public boolean update(Category category, int id) {
        return store.update(category, id);
    }

    public boolean delete(int id) {
        return store.delete(id);
    }
}
