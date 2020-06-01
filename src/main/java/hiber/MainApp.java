package hiber;

import hiber.config.AppConfig;
import hiber.dao.UserDao;
import hiber.dao.UserDaoImp;
import hiber.model.User;
import hiber.service.ServiceTest;
import hiber.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class MainApp {

    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserServiceImp userServiceImp = context.getBean("userServiceImp", UserServiceImp.class);

        userServiceImp.add(new User("User1", "Lastname1", "user1@mail.ru"));
        userServiceImp.add(new User("User2", "Lastname2", "user2@mail.ru"));
        userServiceImp.add(new User("User3", "Lastname3", "user3@mail.ru"));
        userServiceImp.add(new User("User4", "Lastname4", "user4@mail.ru"));

        List<User> users = userServiceImp.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println();
        }

        context.close();

        Properties property = new Properties();
        try (InputStream fis = new FileInputStream("c:\\ProjectJava\\2.2.1. spring_hibernate\\src\\main\\resources\\db.properties")) {
            property.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(property.getProperty("db.driver"));
        System.out.println(property.getProperty("db.url"));
        System.out.println(property.getProperty("db.username"));
        System.out.println(property.getProperty("db.password"));


        ServiceTest serviceTest = new ServiceTest();
        User user = new User("a", "ww", "ss");
        serviceTest.addUser(user);
        List<User> usersList = serviceTest.getUser(1);
        System.out.println(usersList.toArray());

    }
}
