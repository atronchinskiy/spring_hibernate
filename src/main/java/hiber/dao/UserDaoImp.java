package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;
    private Session session;

    @Override
    public void clearAll() {
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query query;
            query = session.createQuery("DELETE User");
            query.executeUpdate();
            query = session.createQuery("DELETE Car");
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void add(User user) {
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        Transaction transaction = null;
        List<User> users = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            TypedQuery<User> query = session.createQuery("FROM User");
            users = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
            return users;
        }
    }

    public void addCar(Car car) {
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(car);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<Car> listCar() {
        Transaction transaction = null;
        List<Car> cars = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            TypedQuery<Car> query = session.createQuery("FROM Car");
            cars = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
            return cars;
        }
    }

    public List<Car> listCars() {
        Transaction transaction = null;
        List<Car> cars = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            TypedQuery<Car> query = session.createQuery("SELECT c FROM Car c INNER JOIN User u ON c.userID=u.id");
            cars = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
            return cars;
        }
    }

    @Override
    public User getUser(String name, int series) {
        User user = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery(
                "SELECT u FROM User u INNER JOIN Car c ON c.userID = u.id and c.name = :name and c.series = :series");
            query.setParameter("name", name);
            query.setParameter("series", series);
            List<User> users = query.list();
            transaction.commit();
            if (!users.isEmpty()) {
                user = users.get(0);
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
            return user;
        }
    }
}
