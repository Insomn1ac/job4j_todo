package ru.job4j.todo.persistence;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Account;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
public class AccountDbStore {
    private final SessionFactory sf;

    public AccountDbStore(SessionFactory sf) {
        this.sf = sf;
    }

    public Optional<Account> add(Account account) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(account);
            session.getTransaction().commit();
            return Optional.of(account);
        } catch (HibernateException e) {
            return Optional.empty();
        }
    }

    public Optional<Account> findAccountByLoginAndPassword(String login, String password) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Optional<Account> account = Optional.of((Account)
                    session.createQuery("from Account where login = :fLogin and password = :fPass")
                            .setParameter("fLogin", login)
                            .setParameter("fPass", password)
                            .getSingleResult());
            session.getTransaction().commit();
            return account;
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<Account> findAccountByName(String name) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Optional<Account> account = Optional.of((Account)
                    session.createQuery("from Account where name = :fName")
                    .setParameter("fName", name)
                    .getSingleResult());
            session.getTransaction().commit();
            return account;
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<Account> findAll() {
        List<Account> accounts;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            accounts = session.createQuery("from Account ").getResultList();
            session.getTransaction().commit();
        }
        return accounts;
    }
}
