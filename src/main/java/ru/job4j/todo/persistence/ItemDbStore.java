package ru.job4j.todo.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Item;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Repository
public class ItemDbStore {
    private final SessionFactory sf;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ItemDbStore(SessionFactory sf) {
        this.sf = sf;
    }

    public Item add(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    public boolean update(Item item, int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        int itemUpdated = session.createQuery("update Item i "
                        + "set i.created = :created, i.name = :name, i.description = :description, i.done = :done "
                        + "where i.id = :fId")
                .setParameter("created", LocalDateTime.now().format(formatter))
                .setParameter("name", item.getName())
                .setParameter("description", item.getDescription())
                .setParameter("done", item.isDone())
                .setParameter("fId", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return itemUpdated > 0;
    }

    public boolean updateOnlyState(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        int itemUpdated = session.createQuery("update Item i "
                        + "set i.done = true "
                        + "where i.id = :fId")
                .setParameter("fId", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return itemUpdated > 0;
    }

    public List<Item> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Item> items = session.createQuery("from Item").list();
        session.getTransaction().commit();
        session.close();
        return items;
    }

    public Item findById(int id) {
        Item item;
        Session session = sf.openSession();
        session.beginTransaction();
        item = (Item) session.createQuery("from Item i where i.id = :fId")
                .setParameter("fId", id)
                .getSingleResult();
        session.getTransaction().commit();
        session.close();
        return item;
    }

    public List<Item> findCompletedTasks() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Item> items = session.createQuery("from Item i where i.done = true").list();
        session.getTransaction().commit();
        session.close();
        return items;
    }

    public List<Item> findNewTasks() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Item> items = session.createQuery("from Item i where i.done = false").list();
        session.getTransaction().commit();
        session.close();
        return items;
    }

    public boolean delete(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        int itemDeleted = session.createQuery("delete from Item i where i.id = :fId")
                .setParameter("fId", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return itemDeleted > 0;
    }
}
