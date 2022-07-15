package ru.job4j.todo.persistence;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Account;

import java.util.List;
import java.util.Optional;

@Repository
public class AccountDbStore implements IStore {
    private final SessionFactory sf;

    public AccountDbStore(SessionFactory sf) {
        this.sf = sf;
    }

    public Optional<Account> add(Account account) {
        try {
            return Optional.of((Account) tx(session -> session.save(account), sf));
        } catch (HibernateException e) {
            return Optional.empty();
        }
    }

    public Optional<Account> findAccountByLoginAndPassword(String login, String password) {
        return tx(session -> session.createQuery("from Account where login = :fLogin and password = :fPass")
                        .setParameter("fLogin", login)
                        .setParameter("fPass", password)
                        .uniqueResultOptional(),
                sf);
    }

    public Optional<Account> findAccountByName(String name) {
        return tx(session -> session.createQuery("from Account where name = :fName")
                        .setParameter("fName", name)
                        .uniqueResultOptional(),
                sf);
    }

    public List<Account> findAll() {
        return tx(session -> session.createQuery("from Account ").getResultList(), sf);
    }
}
