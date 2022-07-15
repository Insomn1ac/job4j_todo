package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.persistence.ItemDbStore;

import java.util.List;

@Service
public class ItemService {
    private final ItemDbStore store;

    public ItemService(ItemDbStore store) {
        this.store = store;
    }

    public Item add(Item item) {
        return store.add(item);
    }

    public boolean update(Item item, int id) {
        return store.update(item, id);
    }

    public boolean updateState(int id) {
        return store.updateOnlyState(id);
    }

    public List<Item> findAll() {
        return store.findAll();
    }

    public Item findById(int id) {
        return store.findById(id);
    }

    public List<Item> findByAccountId(int accountId) {
        return store.findByAccountId(accountId);
    }

    public List<Item> findCompletedTasks() {
        return store.findCompletedTasks();
    }

    public List<Item> findNewTasks() {
        return store.findNewTasks();
    }

    public boolean delete(int id) {
        return store.delete(id);
    }
}
