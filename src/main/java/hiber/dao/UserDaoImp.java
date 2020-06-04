package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void clearAll() {
        Query query = sessionFactory.getCurrentSession().createQuery("DELETE User");
        query.executeUpdate();
        query = sessionFactory.getCurrentSession().createQuery("DELETE Car");
        query.executeUpdate();
    }

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("FROM User");
        return query.getResultList();
    }

    public void addCar(Car car) {
        sessionFactory.getCurrentSession().save(car);
    }

    public List<Car> listCar() {
        TypedQuery<Car> query = sessionFactory.getCurrentSession().createQuery("FROM Car");
        return query.getResultList();
    }

    public List<Car> listCars() {
        String select = "SELECT c FROM Car c INNER JOIN User u ON c.userID=u.id";
        Query query = sessionFactory.getCurrentSession().createQuery(select);
        return query.getResultList();
    }

    @Override
    public User getUser(String name, int series) {
        User user = null;
        Query query = sessionFactory.getCurrentSession().createQuery(
                "SELECT u FROM User u INNER JOIN Car c ON c.userID = u.id and c.name = " + name + "and c.series =" + series);
        List<User> users = query.list();
        if (!users.isEmpty()) {
            user = users.get(0);
        }
        return user;
    }
}
