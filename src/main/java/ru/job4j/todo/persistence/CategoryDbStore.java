package ru.job4j.todo.persistence;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.List;

@Repository
public class CategoryDbStore implements IStore {
    private final SessionFactory sf;

    public CategoryDbStore(SessionFactory sf) {
        this.sf = sf;
    }

    public Category add(Category category) {
        return (Category) tx(session -> session.save(category), sf);
    }

    public List<Category> findAll() {
        return tx(session -> session.createQuery("from Category").list(), sf);
    }

    public Category findById(int id) {
        return (Category) tx(session -> session.createQuery("from Category c where c.id = :fId")
                        .setParameter("fId", id)
                        .getSingleResult(),
                sf);
    }

    public boolean update(Category category, int id) {
        return tx(session -> session.createQuery("update Category c set c.name = :name "
                                + "where c.id = :fId")
                        .setParameter("name", category.getName())
                        .setParameter("fId", id)
                        .executeUpdate(),
                sf) > 0;
    }

    public boolean delete(int id) {
        return tx(session -> session.createQuery("delete from Category c where c.id = :fId")
                        .setParameter("fId", id)
                        .executeUpdate(),
                sf) > 0;
    }
}
