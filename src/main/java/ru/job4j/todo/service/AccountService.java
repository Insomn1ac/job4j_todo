package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Account;
import ru.job4j.todo.persistence.AccountDbStore;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountDbStore store;

    public AccountService(AccountDbStore store) {
        this.store = store;
    }

    public Optional<Account> add(Account account) {
        return store.add(account);
    }

    public Optional<Account> findAccountByName(String name) {
        return store.findAccountByName(name);
    }

    public Optional<Account> findAccountByLoginAndPassword(String login, String password) {
        return store.findAccountByLoginAndPassword(login, password);
    }

    public List<Account> findAll() {
        return store.findAll();
    }
}
