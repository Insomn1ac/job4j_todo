package ru.job4j.todo.persistence;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Item;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class ItemDbStore implements IStore {
    private final SessionFactory sf;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ItemDbStore(SessionFactory sf) {
        this.sf = sf;
    }

    public Item add(Item item) {
        return (Item) tx(session -> session.save(item),
                sf);
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
        return tx(session -> session.createQuery("from Item").list(), sf);
    }

    public Item findById(int id) {
        return (Item) tx(session -> session.createQuery("from Item i where i.id = :fId")
                        .setParameter("fId", id)
                        .getSingleResult(),
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
