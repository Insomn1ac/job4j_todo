package ru.job4j.todo.persistence;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Repository
public class ItemDbStore implements IStore {
    private final SessionFactory sf;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ItemDbStore(SessionFactory sf) {
        this.sf = sf;
    }

    public Item add(Item item, List<String> categoryId) {
        tx(session -> {
                    for (String id : categoryId) {
                        Category category = session.get(Category.class, Integer.parseInt(id));
                        item.getCategories().add(category);
                    }
                    return session.save(item);
                },
                sf);
        return item;
    }

    public boolean update(Item item, int id) {
        return tx(session -> session.createQuery("update Item i "
                                + "set i.created = :created, i.name = :name, i.description = :description, i.done = :done "
                                + "where i.id = :fId")
                        .setParameter("created", LocalDateTime.now().format(formatter))
                        .setParameter("name", item.getName())
                        .setParameter("description", item.getDescription())
                        .setParameter("done", item.isDone())
                        .setParameter("fId", id)
                        .executeUpdate(),
                sf) > 0;
    }

    public boolean updateOnlyState(int id) {
        return tx(session -> session.createQuery("update Item i "
                                + "set i.done = true "
                                + "where i.id = :fId")
                        .setParameter("fId", id)
                        .executeUpdate(),
                sf) > 0;
    }

    public List<Item> findAll() {
        return tx(session -> session.createQuery("select distinct i from Item i join fetch i.categories order by i.created").list(), sf);
    }

    public Item findById(int id) {
        return (Item) tx(session -> session.createQuery("from Item i where i.id = :fId")
                        .setParameter("fId", id)
                        .getSingleResult(),
                sf);
    }

    public List<Item> findByAccountId(int accountId) {
        return tx(session -> session.createQuery("from Item i where i.account.id = :fAccId")
                        .setParameter("fAccId", accountId)
                        .getResultList(),
                sf);
    }

    public List<Item> findCompletedTasks() {
        return tx(session -> session.createQuery("from Item i where i.done = true").list(), sf);
    }

    public List<Item> findNewTasks() {
        return tx(session -> session.createQuery("from Item i where i.done = false").list(), sf);
    }

    public boolean delete(int id) {
        return tx(session -> session.createQuery("delete from Item i where i.id = :fId")
                        .setParameter("fId", id)
                        .executeUpdate(),
                sf) > 0;
    }
}
