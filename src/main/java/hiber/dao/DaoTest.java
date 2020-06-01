package hiber.dao;

import hiber.model.User;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.sql.SQLException;
import java.util.List;

public class DaoTest {
    private Session session;

    public DaoTest (Session session) {
        this.session = session;
    }

    public List<User> get(long id) throws HibernateException {
        List<User> users = session.createQuery("FROM User").list();
        return users;
    }

    public boolean addUser(User user) throws SQLException {
//        Session session = session.openSession();
        boolean flag = false;
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            if (user != null) {
                session.save(new User(user.getFirstName(), user.getLastName(), user.getEmail()));
            }
            transaction.commit();
            flag = true;
        }catch (HibernateException e) {
            if(transaction != null) {
                transaction.rollback();
            }
        } finally {
            try {
                session.close();
            } catch (Exception e) {
                throw new SQLException();
            }
            return flag;
        }
    }
/*
    public long getUserId(String name) throws HibernateException {
        Criteria criteria = session.createCriteria(User.class);
        return ((User) criteria.add(Restrictions.eq("name", name)).uniqueResult()).getId();
    }

    public long insertUser(String name) throws HibernateException {
        return 1; //(Long) session.save(new User(name));
    }*/
}
