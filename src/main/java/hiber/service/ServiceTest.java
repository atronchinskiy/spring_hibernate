package hiber.service;

import hiber.dao.DaoTest;
import hiber.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;


public class ServiceTest {

    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "create";
//    private static final String hibernate_hbm2ddl_auto = "validate";

    private final SessionFactory sessionFactory;

    public ServiceTest() {
        Configuration configuration = getH2Configuration();
        sessionFactory = createSessionFactory(configuration);
    }

    private Configuration getH2Configuration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);

        Properties property = new Properties();
        try (InputStream fis = new FileInputStream("c:\\ProjectJava\\2.2.1. spring_hibernate\\src\\main\\resources\\db.properties")){
            property.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        configuration.setProperty("hibernate.dialect", property.getProperty("hibernate.dialect"));
        configuration.setProperty("hibernate.connection.driver_class", property.getProperty("db.driver"));
        configuration.setProperty("hibernate.connection.url", property.getProperty("db.url"));
        configuration.setProperty("hibernate.connection.username", property.getProperty("db.username"));
        configuration.setProperty("hibernate.connection.password", property.getProperty("db.password"));
        configuration.setProperty("hibernate.show_sql", property.getProperty("hibernate.show_sql"));
        configuration.setProperty("hibernate.hbm2ddl.auto", property.getProperty("hibernate.hbm2ddl.auto"));
        return configuration;
    }

    public List<User> getUser(long id) {
        List<User> dataSet = null;
    try{
            Session session = sessionFactory.openSession();
            DaoTest dao = new DaoTest(session);
            dataSet = dao.get(id);
            session.close();

        } catch (HibernateException e) {
        }
        return dataSet;
    }
    public boolean addUser(User user){
        Session session = sessionFactory.openSession();
        DaoTest dao = new DaoTest(session);
        try {
            dao.addUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        session.close();
        return true;
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
